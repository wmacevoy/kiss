package kiss.util;

class TestApp {
    int constructed = 0;
    TestApp() { ++constructed; }
    
    int tested = 0;
    void testEd() { ++tested; }
    
    int ran = 0;
    
    void run() { ++ran; }
    
    int closed = 0;
    void close() { ++closed; }
}
