package kiss.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;

public class Run {
    private static HashSet<Class<?>> testedAlready = new HashSet<Class<?>>();

    public static void main(String[] _args) throws Exception {

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

    private static boolean testEnabled(Object object) {
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
    public static <T> T test(T object) {
        if (testEnabled(object)) {
            testAlways(object);
        }
        return object;
    }

    public static <T> T untest(T object) {
        return object;
    }

    public static <T> T testAlways(T object) {
        try {        
        testedAlready.add(object.getClass());
        DecimalFormat df = new DecimalFormat("0.00");

        for (Method method : object.getClass().getDeclaredMethods()) {
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

    public static <T> T untestAlways(T object) throws Exception {
        return object;
    }

    public static void sleep(double duration) {
        if (duration > 0) {
            try {
                Thread.sleep((int)java.lang.Math.round(duration*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Thread.yield();
        }
    }

    public static double time() {
        return java.lang.System.currentTimeMillis()/1000.0;
    }
}
