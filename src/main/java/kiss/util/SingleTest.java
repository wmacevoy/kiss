package kiss.util;

import static kiss.API.*;
import java.lang.reflect.*;
import java.text.DecimalFormat;
import java.util.Date;

/** Runs a single test method matching Run.testAlways() output format.
    Usage: kiss.API --app kiss.util.SingleTest --notest -- ClassName testMethod */
public class SingleTest {
    void run() {
        String cls = APP_ARGS.length > 0 ? APP_ARGS[0] : "App";
        String method = APP_ARGS.length > 1 ? APP_ARGS[1] : "testRun";
        DecimalFormat df = new DecimalFormat("0.00");
        try {
            Class<?> c = Class.forName(cls);
            Constructor<?> ctor = c.getDeclaredConstructor();
            ctor.setAccessible(true);
            Object obj = ctor.newInstance();
            Method m = c.getDeclaredMethod(method);
            m.setAccessible(true);

            seed(1);
            Date started = new Date();
            System.out.println(started + " " + method + ": started");
            m.invoke(obj);
            Date ended = new Date();
            double elapsed = (ended.getTime() - started.getTime()) / 1000.0;
            System.out.println(ended + " " + method + ": ended in " + df.format(elapsed) + " second(s)");
        } catch (InvocationTargetException e) {
            throw new AssertionError("test(s) failed", e.getCause());
        } catch (Exception e) {
            throw new AssertionError("test(s) failed", e);
        }
    }
}
