package kiss.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.text.DecimalFormat;
import java.util.Date;

public class Run {
    public static void main(String[] _args) throws ClassNotFoundException, java.lang.reflect.InvocationTargetException {

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

        Class appClass = Class.forName(className);
        
        kiss.API.APP_NAME=className;
        kiss.API.APP_ARGS=java.util.Arrays.copyOf(args,args.length);

        Object app = null;
        try {
            try { // construct with default (no arg) constructor
                Constructor constructor = appClass.getConstructor();
                try {
                    constructor.setAccessible(true);
                } catch (Exception e) {}
                
                app = constructor.newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                throw new RuntimeException("Could not construct "
                                           + appClass.getName()
                                           + ". Is the default or String[] constructor not public?");
            }

            kiss.API.APP=app;

            DecimalFormat df = new DecimalFormat("0.00");
        
            for (Method method : app.getClass().getDeclaredMethods()) {
                if (method.getName().startsWith("test") && method.getParameterTypes().length == 0) {
                    try {
                        Date started = new Date();
                        System.out.println(started+" "+method.getName()+": started");
                        kiss.API.seed();
                        try {
                            method.setAccessible(true);
                        } catch (Exception e) {}
                        
                        method.invoke(app);

                        Date ended = new Date();
                        double elapsed =
                            (ended.getTime()-started.getTime())/1000.0;


                        System.out.println(ended+" "+method.getName()+": ended in " + df.format(elapsed) + " second(s)");
                    } catch (IllegalAccessException | IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            }

            Method run = null;
            try {
                run = app.getClass().getMethod("run");
            } catch (NoSuchMethodException | SecurityException e1) {
            }
        
            if (run != null) {
                try {
                    kiss.API.seed(kiss.API.time());
                    try {
                        run.setAccessible(true);
                    } catch (Exception e) {}
                    run.invoke(app);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Make " + run + " in " + app.getClass().getName() + " public");
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } finally {
            if (app != null) {
                Method close = null;
                try {
                    close = app.getClass().getMethod("close");
                } catch (NoSuchMethodException | SecurityException e1) {
                }
        
                if (close != null) {
                    try {
                        try {
                            close.setAccessible(true);
                        } catch (Exception e) {}
                        close.invoke(app);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Make " + close + " in " + app.getClass().getName() + " public");
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
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
