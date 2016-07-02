package edu.coloradomesa.cs.kiss.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Run {
    public static void main(String[] _args) throws ClassNotFoundException {
        String[] args = null;

        String mainClassName = "Main"; // default class is "Main" in default package
                
        if (System.getenv("JAVA_MAIN") != null) { // env override
            mainClassName = System.getenv("JAVA_MAIN");
        }
        
        if (_args.length > 1 && _args[0].equals("--main")) { 
            mainClassName = _args[1];
            args = new String[_args.length-2];
            System.arraycopy(_args,2,args,0,args.length);
        } else {
            args = new String[_args.length];
            System.arraycopy(args,0,_args,0,args.length);
        }

        Class mainClass = Class.forName(mainClassName);

        Object main = null;
		try {
			main = mainClass.getConstructor(String[].class).newInstance(new Object[] { args });
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e2) {
		}

		if (main == null) {
			try { // construct with default (no arg) constructor
				main = mainClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException("Could not construct " + mainClass.getName()
						+ ". Is the default or String[] constructor not public?");
			}
		}

		for (Method method : main.getClass().getDeclaredMethods()) {
			if (method.getName().startsWith("test") && method.getParameterTypes().length == 0) {
				try {
					method.invoke(main);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}

		Method run = null;
		try {
			run = main.getClass().getMethod("run");
		} catch (NoSuchMethodException | SecurityException e1) {
		}

		if (run != null) {
			try {
				run.invoke(main);
			} catch (IllegalAccessException e) {
				throw new RuntimeException("Make " + run + " in " + main.getClass().getName() + " public");
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
