package kiss.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Date;

public class Run {
    public static void main(String[] _args) throws ClassNotFoundException, java.lang.reflect.InvocationTargetException {
        String[] args = null;

        String className = "App"; // default class is "App" in default package
                
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

        Object app = null;
        try {
            app = appClass.getConstructor(String[].class).newInstance(new Object[] { args });
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException e2) {
        }
        
        if (app == null) {
            try { // construct with default (no arg) constructor
                app = appClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Could not construct "
                                           + appClass.getName()
                                           + ". Is the default or String[] constructor not public?");
            }
        }

        kiss.util.RNG.seed(0);
        DecimalFormat df = new DecimalFormat("0.00");
        
        for (Method method : app.getClass().getDeclaredMethods()) {
            if (method.getName().startsWith("test") && method.getParameterTypes().length == 0) {
                try {
                    Date started = new Date();
                    System.out.println(started+" "+method.getName()+": started");
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

        kiss.util.RNG.seed(java.lang.System.currentTimeMillis());

        Method run = null;
        try {
            run = app.getClass().getMethod("run");
        } catch (NoSuchMethodException | SecurityException e1) {
        }
        
        if (run != null) {
            try {
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
    }

    public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
