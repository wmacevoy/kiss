package kiss.util;

import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * InputStream for browser use. Java code blocks on read() when empty.
 * JavaScript calls feed() to push data, unblocking the reader.
 * CheerpJ converts Thread.sleep to async yield, so the JS event loop
 * runs between polls and can call feed() via cheerpjRunLibrary proxy.
 */
public class WebInputStream extends InputStream {
    private static byte[] buf = new byte[0];
    private static int pos = 0;
    private static final Charset UTF8 = Charset.forName("UTF-8");

    /** Called from JavaScript to push a line of input. */
    public static void feed(String data) {
        byte[] add = data.getBytes(UTF8);
        int remaining = buf.length - pos;
        byte[] next = new byte[remaining + add.length];
        if (remaining > 0) System.arraycopy(buf, pos, next, 0, remaining);
        System.arraycopy(add, 0, next, remaining, add.length);
        buf = next;
        pos = 0;
    }

    /** Reset buffer between runs. */
    public static void clear() {
        buf = new byte[0];
        pos = 0;
    }

    @Override
    public int read() throws IOException {
        while (pos >= buf.length) {
            try { Thread.sleep(50); } catch (InterruptedException e) { return -1; }
        }
        return buf[pos++] & 0xFF;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (len == 0) return 0;
        while (pos >= buf.length) {
            try { Thread.sleep(50); } catch (InterruptedException e) { return -1; }
        }
        int n = Math.min(len, buf.length - pos);
        System.arraycopy(buf, pos, b, off, n);
        pos += n;
        return n;
    }

    @Override
    public int available() {
        return Math.max(0, buf.length - pos);
    }
}
