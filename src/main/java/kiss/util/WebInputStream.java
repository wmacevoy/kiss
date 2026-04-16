package kiss.util;

import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * InputStream for browser use. Reads input from sequenced files
 * written by JavaScript via cheerpOSAddStringFile.
 *
 * Protocol: JS writes /str/_pipe_1, /str/_pipe_2, etc.
 * This stream polls for the next file in sequence.
 * No file overwriting (avoids CheerpJ /str/ corruption).
 */
public class WebInputStream extends InputStream {
    private byte[] buf = new byte[0];
    private int pos = 0;
    private int fileSeq = 0;

    @Override
    public int read() throws IOException {
        while (pos >= buf.length) {
            if (!pollNext()) {
                try { Thread.sleep(100); } catch (InterruptedException e) { return -1; }
            }
        }
        return buf[pos++] & 0xFF;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (len == 0) return 0;
        while (pos >= buf.length) {
            if (!pollNext()) {
                try { Thread.sleep(100); } catch (InterruptedException e) { return -1; }
            }
        }
        int n = Math.min(len, buf.length - pos);
        System.arraycopy(buf, pos, b, off, n);
        pos += n;
        return n;
    }

    private boolean pollNext() {
        int next = fileSeq + 1;
        File f = new File("/str/_pipe_" + next);
        if (!f.exists()) return false;
        try {
            FileInputStream fis = new FileInputStream(f);
            buf = new byte[(int) f.length()];
            int read = fis.read(buf);
            fis.close();
            if (read > 0) {
                if (read < buf.length) buf = java.util.Arrays.copyOf(buf, read);
                pos = 0;
                fileSeq = next;
                return true;
            }
        } catch (Exception e) {}
        return false;
    }

    @Override
    public int available() {
        return Math.max(0, buf.length - pos);
    }
}
