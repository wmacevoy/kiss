package kiss.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.text.DecimalFormat;
import java.util.Date;

public class Run {
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
        
        String className = "App"; // default class is "App" in default package
        String[] args;
                
        if (System.getenv("JAVA_APP") != null) { // env override
            className = System.getenv("JAVA_APP");
        }
        
        if (_args.length > 1 && _args[0].equals("--app")) { 
            className = _args[1];
            args = new String[_args.length-2];
            System.arraycopy(_args,2,args,0,args.length);
        } else {
            args = new String[_args.length];
            System.arraycopy(args,0,_args,0,args.length);
        }

        Class<?> appClass = Class.forName(className);
        
        kiss.API.APP_NAME=className;
        kiss.API.APP_ARGS=java.util.Arrays.copyOf(args,args.length);

        Object app = null;
        Method run = null;
        Method close = null;
        
        try {
            Constructor<?> constructor = appClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            app = constructor.newInstance();

            kiss.API.APP=app;

            DecimalFormat df = new DecimalFormat("0.00");

            for (Method method : app.getClass().getDeclaredMethods()) {
                if (method.getName().equals("run")) {
                    run = method;
                } else if (method.getName().equals("close")) {
                    close = method;
                } else if (method.getName().startsWith("test")
                           && method.getParameterTypes().length == 0) {

                    kiss.API.seed(1);
                    method.setAccessible(true);
                    
                    Date started = new Date();
                    System.out.println(started+" "+method.getName()+": started");
                    method.invoke(app);

                    Date ended = new Date();
                    
                    double elapsed = (ended.getTime()-started.getTime())/1000.0;
                    
                    System.out.println(ended+" "+method.getName()+": ended in " + df.format(elapsed) + " second(s)");
                }
            }

            if (run != null) {
                kiss.API.seed();
                run.setAccessible(true);                    
                run.invoke(app);
            }
        } finally {
            if (app != null && close != null) {
                close.setAccessible(true);
                close.invoke(app);
            }
        }
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
