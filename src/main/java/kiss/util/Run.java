package kiss.util;

import static kiss.API.*;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.ArrayList;
import java.io.InputStream;

public class Run {
    // modified by TestRun so not private
    static HashSet<Class<?>> testedAlready = new HashSet<Class<?>>();

    public static final void main(String[] _args) throws Exception {

        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);

        // 0. The default app object is App (default package).  You can
        //    choose another object using the --app <class> command line
        //    option or the JAVA_APP environment variable.
        //
        // 1. Contruct app object.  Use the String[] args constructor
        //    if it is available.  Otherwise, use the default constructor.
        // 
        //
        // 2. Invoke testXXX() methods.  For reproducability, the
        //    random number seed is set to 1 before each test.
        //
        // 3. Call the run() method.
        //
        // 4. Call the close() method (even if a test or run fails)

        boolean doTest = true;
        boolean doRun = true;
        String className = "App"; // default class is "App" in default package
        String[] args;
                
        if (System.getenv("JAVA_APP") != null) { // env override
            className = System.getenv("JAVA_APP");
        }

        int argi;
        for (argi=0; argi < _args.length; ++argi) {
            if (_args[argi].equals("--")) {
                ++argi;
                break;
            }

            if (_args[argi].equals("--norun")) {
                doRun = false;
                continue;
            }

            if (_args[argi].equals("--notest")) {
                doTest = false;
                continue;
            }

            if (_args[argi].equals("--app")) {
                className = _args[++argi];
                continue;
            }

            break;
        }

        args = new String[_args.length - argi];
        System.arraycopy(_args,argi,args,0,args.length);
        
        Class<?> appClass = Class.forName(className);
        
        kiss.API.APP_NAME=className;
        kiss.API.APP_ARGS=java.util.Arrays.copyOf(args,args.length);

        Object app = null;
        
        try {
            Constructor<?> constructor = appClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            app = constructor.newInstance();

            kiss.API.APP=app;

            if (doTest) {
                test(app);
            }

            if (doRun) {
                try {
                    Method run = app.getClass().getDeclaredMethod("run");
                    kiss.API.seed();
                    run.setAccessible(true);                    
                    run.invoke(app);
                } catch (NoSuchMethodException e) {}
            }
        } finally {
            if (app != null) {
                try {
                    Method close = app.getClass().getDeclaredMethod("close");
                    close.setAccessible(true);
                    close.invoke(app);
                } catch (NoSuchMethodException e) {}
            }
        }
    }

    private static final boolean testEnabled(Object object) {
        if (object == null) return false;
        if (testedAlready.contains(object.getClass())) return false;
        try {
            Field TESTED=object.getClass().getDeclaredField("TESTED");
            TESTED.setAccessible(true);
            return TESTED.getBoolean(object);
        } catch(Exception e) {}
        return true;
    }
    
    /** test if object of this type has not been already tested */
    public static final <T> T test(T object) {
        if (testEnabled(object)) {
            testAlways(object);
        }
        return object;
    }

    public static final <T> T untest(T object) {
        return object;
    }

    public static final <T> T testAlways(T object) {
        try {        
            testedAlready.add(object.getClass());
            DecimalFormat df = new DecimalFormat("0.00");
            Method[] methods = Reflect.getDeclaredMethodsInOrder(object.getClass());
        
            for (Method method : methods) {
                if (method.getName().startsWith("test")
                    && method.getParameterTypes().length == 0) {
                
                    kiss.API.seed(1);
                    method.setAccessible(true);
                
                    Date started = new Date();
                    System.out.println(started+" "+method.getName()+": started");
                    method.invoke(object);
                
                    Date ended = new Date();
                
                    double elapsed = (ended.getTime()-started.getTime())/1000.0;
                
                    System.out.println(ended+" "+method.getName()+": ended in " + df.format(elapsed) + " second(s)");
                }
            }
            return object;
        } catch (Exception e) {
            throw new AssertionError("test(s) failed",e);
        }
        
    }

    public static final <T> T untestAlways(T object) throws Exception {
        return object;
    }

    public static final void pause(double duration) {
        if (duration > 0) {
            try {
                duration *= 1000.0;
                long millis = (long) (duration);
                int nanos =  (int) ((duration-millis)*1_000_000.0);
                Thread.sleep(millis,nanos);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Thread.yield();
        }
    }

    public static final double time() {
        return java.lang.System.currentTimeMillis()/1000.0;
    }
}
