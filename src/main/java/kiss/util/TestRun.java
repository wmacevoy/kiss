package kiss.util;

import static kiss.API.*;

public class TestRun {
    void testTime() {
        double s=java.lang.System.currentTimeMillis()/1000.0;
        double t=time();
        assert abs(s-t)<0.01;
    }

    void testPause() {
        double t0=time();
        pause(0.1);
        double t=time()-t0;
        assert abs(t-0.1) < 0.01;
    }

    void testMain() throws Exception {
        // save globals to restore after test
        String[] _APP_ARGS = kiss.API.APP_ARGS;
        String   _APP_NAME = kiss.API.APP_NAME;
        Object   _APP      = kiss.API.APP;
        boolean appWasTested = Run.testedAlready.contains(TestApp.class);

        kiss.API.APP_NAME=null;
        kiss.API.APP_ARGS=null;
        kiss.API.APP=null;

        Run.testedAlready.remove(TestApp.class);
        Run.main(new String[] { "--app", "kiss.util.TestApp","a","b","c" });

        assert kiss.API.APP_NAME.equals("kiss.util.TestApp");
        assert java.util.Arrays.equals(kiss.API.APP_ARGS,
                                       new String[] { "a","b","c" });
        assert kiss.API.APP instanceof TestApp;
        {
            TestApp app = (TestApp) APP;
            assert app.constructed == 1;
            assert app.tested == 1;
            assert app.ran == 1;
            assert app.closed == 1;
        }
        
        kiss.API.APP_NAME=null;
        kiss.API.APP_ARGS=null;
        kiss.API.APP=null;

        Run.testedAlready.remove(TestApp.class);
        Run.main(new String[] { "--notest","--app", "kiss.util.TestApp","a","b","c" });

        assert kiss.API.APP_NAME.equals("kiss.util.TestApp");
        assert java.util.Arrays.equals(kiss.API.APP_ARGS,
                                       new String[] { "a","b","c" });
        assert kiss.API.APP instanceof TestApp;
        {
            TestApp app = (TestApp) APP;
            assert app.constructed == 1;
            assert app.tested == 0;
            assert app.ran == 1;
            assert app.closed == 1;
        }

        kiss.API.APP_NAME=null;
        kiss.API.APP_ARGS=null;
        kiss.API.APP=null;
        Run.testedAlready.remove(TestApp.class);        
        Run.main(new String[] { "--norun","--app", "kiss.util.TestApp","a","b","c" });
        assert kiss.API.APP_NAME.equals("kiss.util.TestApp");
        assert java.util.Arrays.equals(kiss.API.APP_ARGS,
                                       new String[] { "a","b","c" });
        assert kiss.API.APP instanceof TestApp;
        {
            TestApp app = (TestApp) APP;
            assert app.constructed == 1;
            assert app.tested == 1;
            assert app.ran == 0;
            assert app.closed == 1;
        }
            
        // restore globals 
        kiss.API.APP_ARGS = _APP_ARGS;
        kiss.API.APP_NAME = _APP_NAME;
        kiss.API.APP      = _APP;

        if (appWasTested) {
            Run.testedAlready.add(TestApp.class);
        } else {
            Run.testedAlready.remove(TestApp.class);            
        }
        
    }

    void testTest() {
        boolean appWasTested = Run.testedAlready.contains(TestApp.class);
        Run.testedAlready.remove(TestApp.class);
        TestApp app = new TestApp();
        assert app.constructed == 1;
        test(app);
        assert app.constructed == 1;        
        assert app.tested == 1;
        assert app.ran == 0;
        assert app.closed == 0;

        test(app);
        assert app.constructed == 1;        
        assert app.tested == 1;
        assert app.ran == 0;
        assert app.closed == 0;

        testAlways(app);
        assert app.constructed == 1;        
        assert app.tested == 2;
        assert app.ran == 0;
        assert app.closed == 0;

        if (appWasTested) {
            Run.testedAlready.add(TestApp.class);
        } else {
            Run.testedAlready.remove(TestApp.class);            
        }
    }

    class Sample1 {
        java.util.ArrayList<String> order
            = new java.util.ArrayList<String>();

        void testA() {
            order.add("a");
        }
        void testX() {
            order.add("b");
        }
        void testQ() {
            order.add("c");
        }
    }

    class Sample2 {
        java.util.ArrayList<String> order
            = new java.util.ArrayList<String>();

        void testC() {
            order.add("a");
        }
        void testB() {
            order.add("b");
        }
        void testAAA() {
            order.add("c");
        }
    }

    class Sample3 {
        java.util.ArrayList<String> order
            = new java.util.ArrayList<String>();

        protected void testCxkdmY() {
            order.add("a");
        }

        void some() { }
        void test12444() {
            order.add("b");
        }

        void other() {} 
        private void testUUAAA() {
            order.add("c");
        }

        void stuff() {}
    }

    class Sample4 {
        java.util.ArrayList<String> order
            = new java.util.ArrayList<String>();

        protected void testA() {
            order.add("a");
        }

        void some() { }
        private void testAtestAtestA() {
            order.add("b");
        }

        void other() {} 
        void testAtestA() {
            order.add("c");
        }

        void stuff() {}
    }
    
    
    
    void testOrder() {
        assert format(test(new Sample1()).order).equals("[a,b,c]");
        assert format(test(new Sample2()).order).equals("[a,b,c]");
        assert format(test(new Sample3()).order).equals("[a,b,c]");
        assert format(test(new Sample4()).order).equals("[a,b,c]");        
    }
}
