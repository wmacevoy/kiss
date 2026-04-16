package kiss.util;

import static kiss.API.*;
import java.lang.reflect.*;

/** Runs a single test method by name. Used by the web playground.
    Usage: kiss.API --app kiss.util.SingleTest --notest -- ClassName testMethod */
public class SingleTest {
    void run() {
        String cls = APP_ARGS.length > 0 ? APP_ARGS[0] : "App";
        String method = APP_ARGS.length > 1 ? APP_ARGS[1] : "testRun";
        seed(1);
        try {
            Class<?> c = Class.forName(cls);
            Constructor<?> ctor = c.getDeclaredConstructor();
            ctor.setAccessible(true);
            Object obj = ctor.newInstance();
            Method m = c.getDeclaredMethod(method);
            m.setAccessible(true);
            m.invoke(obj);
            System.out.println("PASS: " + cls + "." + method);
        } catch (InvocationTargetException e) {
            System.out.println("FAIL: " + cls + "." + method + " - " + e.getCause());
            e.getCause().printStackTrace();
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            e.printStackTrace();
        }
    }
}
