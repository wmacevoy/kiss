package kiss.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ByteOrder;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Random;

/**
 * When using bulk random numbers, AESPRNG is about twice as fast as
 * Random on reasonably modern AES-accelerated hardware.  And AESPRNG
 * produces cryptographically strong psuedo-random number sequences.
 */

public class AESPRNG extends Random implements kiss.API.Close {
    private Cipher aesecb;
    private final int MIN_PAGE = 64;
    private final int MAX_PAGE = 8192;
    private int page = MIN_PAGE;
    private byte[] data;
    private ByteBuffer dataBuffer;
    private LongBuffer dataLongs;
    private IntBuffer dataInts;
    private boolean constructed = false;


    public synchronized void close() {
        if (gaussianQueue != null) {
            java.util.Arrays.fill(gaussianQueue, 0, page / 16, (double) 0);
            gaussianQueue = null;
            gaussianAt = 0;
            gaussianN = 0;
        }
        if (data != null) {
            java.util.Arrays.fill(data, 0, page, (byte) 0);
            data = null;
        }

        page = MIN_PAGE;
        at = page;
        ctr = 0;
        ctr0 = 0;

        if (aesecb != null) {
            byte[] zero = new byte[16];
            try (PortKey key = new PortKey(new SecretKeySpec(zero, "AES"))) {
                aesecb.init(Cipher.ENCRYPT_MODE, key.data);
            } catch (Exception e) {
                throw new Error("zero key failure: " + e.getMessage());
            }
            aesecb = null;
        }
    }

    public AESPRNG() {
        // so Random's setSeed, called in
        // Random's constructor, does not
        // break our implementation
        constructed = true;
    }

    private volatile int at = page;
    private long ctr0 = 0;
    private long ctr = 0;

    private final void readPage() {
        readPage(false);
    }

    public synchronized void skip(long length) {
        while (length > 0) {
            if (at >= page) {
                readPage(length > MAX_PAGE);
            }
            int n = page - at;
            if (((long) n) > length) n = (int) length;
            at += n;
            length -= n;
        }
    }

    private final void readPage(boolean skip) {
        if (data == null) {
            data = new byte[MAX_PAGE];
            dataBuffer = ByteBuffer.wrap(data);
            dataBuffer.order(ByteOrder.LITTLE_ENDIAN);
            dataLongs = dataBuffer.asLongBuffer();
            dataInts = dataBuffer.asIntBuffer();
        }
        if (page < MAX_PAGE) {
            page *= 2;
        }

        for (int i = skip ? 1 : (page / 16 - 1); i >= 0; --i) {
            dataLongs.put(2 * i + 0, ctr + i);
            dataLongs.put(2 * i + 1, ctr0);
        }
        ctr += page / 8;
        at = 24;
        try {
            if (aesecb == null) seed();
            aesecb.doFinal(data, 0, skip ? 32 : page, data, 0);
            try (PortKey key = new PortKey(new SecretKeySpec(data, 0, 16, "AES"))) {
                aesecb.init(Cipher.ENCRYPT_MODE, key.data);
            }
            ctr0 = dataLongs.get(2);
        } catch (Exception e) {
            throw new Error("Block encryption failure.");
        }
    }

    public synchronized final void nextBytes(byte[] buf, int offset, int length) {
        while (length > 0) {
            if (at >= page) readPage();
            int n = page - at;
            if (n > length) n = length;
            System.arraycopy(data, at, buf, offset, n);
            at += n;
            offset += n;
            length -= n;
        }
    }

    public synchronized final byte nextByte() {
        if (at >= page) readPage();
        byte ans = data[at];
        at += 1;
        return ans;
    }

    /**
     * [Long.MIN_VALUE,Long.MAX_VALUE]
     */
    public synchronized final void nextLongs(long[] buf, int offset, int length) {
        at = (at + 7) & ~7;
        while (length > 0) {
            if (at >= page) readPage();
            int n = (page - at) / 8;
            if (n > length) n = length;
            dataLongs.position(at / 8);
            dataLongs.get(buf, offset, n);
            at += n * 8;
            offset += n;
            length -= n;
        }
    }

    /**
     * [0,Long.MAX_VALUE]
     */
    public final void nextNonNegativeLongs(long[] buf, int offset, int length) {
        nextLongs(buf, offset, length);
        for (int i = 0; i < length; ++i) {
            buf[offset + i] &= Long.MAX_VALUE;
        }
    }

    /**
     * [Long.MIN_VALUE,Long.MAX_VALUE]
     */
    public synchronized final long nextLong() {
        at = (at + 7) & ~7;
        if (at >= page) readPage();
        long ans = dataLongs.get(at / 8);
        at += 8;
        return ans;
    }

    /**
     * [0,Long.MAX_VALUE]
     */
    public final long nextNonNegativeLong() {
        return nextLong() & Long.MAX_VALUE;
    }

    /**
     * [Integer.MIN_VALUE,Integer.MAX_VALUE]
     */
    public synchronized final void nextInts(int[] buf, int offset, int length) {
        at = (at + 3) & ~3;
        while (length > 0) {
            if (at >= page) readPage();
            int n = (page - at) / 4;
            if (n > length) n = length;
            dataInts.position(at / 4);
            dataInts.get(buf, offset, n);
            at += n * 4;
            offset += n;
            length -= n;
        }
    }

    /**
     * [0,Integer.MAX_VALUE]
     */
    public final void nextNonNegativeInts(int[] buf, int offset, int length) {
        nextInts(buf, offset, length);
        for (int i = 0; i < length; ++i) {
            buf[offset + i] &= Integer.MAX_VALUE;
        }
    }

    /**
     * [Integer.MIN_VALUE,Integer.MAX_VALUE]
     */
    public synchronized final int nextInt() {
        at = (at + 3) & ~3;
        if (at >= page) readPage();
        int ans = dataInts.get(at / 4);
        at += 4;
        return ans;
    }

    /**
     * [0,Integer.MAX_VALUE]
     */
    public final int nextNonNegativeInt() {
        return nextInt() & Integer.MAX_VALUE;
    }

    /**
     * [0,1)
     */
    public synchronized final void nextDoubles(double[] buf, int offset, int length) {
        at = (at + 7) & ~7;
        while (length > 0) {
            if (at >= page) readPage();
            int n = (page - at) / 8;
            if (n > length) n = length;
            dataLongs.position(at / 8);
            for (int i = 0; i < n; ++i) {
                buf[offset + i] = (dataLongs.get() & ((1L << 53) - 1)) / ((double) (1L << 53));
            }
            at += 8 * n;
            offset += n;
            length -= n;
        }
    }

    /**
     * [0,1)
     */
    public final double nextDouble() {
        // IEEE 754 double has 52 bits in the mantessa,
        // so 53 bits of precision.  To this precision,
        // this returns a uniformly random double in [0,1).
        return (nextLong() & ((1L << 53) - 1)) / ((double) (1L << 53));
    }

    /**
     * [0,1)
     */
    public synchronized final void nextFloats(float[] buf, int offset, int length) {
        at = (at + 3) & ~3;
        while (length > 0) {
            if (at >= page) readPage();
            int n = (page - at) / 4;
            if (n > length) n = length;
            dataInts.position(at / 4);
            for (int i = 0; i < n; ++i) {
                buf[offset + i] = (dataInts.get() & ((1L << 24) - 1)) / ((float) (1L << 24));
            }
            at += 4 * n;
            offset += n;
            length -= n;
        }
    }

    /**
     * [0,1)
     */
    public final float nextFloat() {
        // IEEE 754 double has 23 bits in the mantessa,
        // so 24 bits of precision.  To this precision,
        // this returns a uniformly random float in [0,1).
        return (nextInt() & ((1 << 24) - 1)) / ((float) (1 << 24));
    }

    /**
     * [min,max]
     */
    public synchronized final int nextInt(int min, int max) {
        if (min < max) {
            at = (at + 7) & ~7;
            long D = ((long) max) - ((long) min) + ((long) 1);
            long x;
            for (; ; ) {
                if (at >= page) readPage();
                x = dataLongs.get(at / 8) & Long.MAX_VALUE;
                at += 8;

                if (x < (Long.MAX_VALUE - (1L << 32))) break;
                // this almost never happens (less than 1:1 billion),
                // but ensures perfect binning of the random range.
                //
                // B is computed efficiently using Java's operations
                // and relies on known overflow behavior.
                long B = Long.MIN_VALUE - (Long.MIN_VALUE % D) - 1;
                if (x <= B) break;
            }
            return (int) ((x % D) + min);
        } else {
            return min;
        }
    }

    /**
     * [min,max]
     */
    public final synchronized void nextInts(int[] buf, int offset, int length,
                                            int min, int max) {
        if (max <= min) {
            java.util.Arrays.fill(buf, offset, offset + length, min);
            return;
        }
        long D = ((long) max - (long) min + 1L);

        at = (at + 7) & ~7;
        while (length > 0) {
            if (at >= page) readPage();
            int n = (page - at) / 8;
            if (n > length) n = length;
            dataLongs.position(at / 8);
            int k = 0;
            for (int i = 0; i < n; ++i) {
                long x = (dataLongs.get() & Long.MAX_VALUE);
                if (x > (Long.MAX_VALUE - (1L << 32))) {
                    // this almost never happens (less than 1:1 billion),
                    // but ensures perfect binning of the random range.
                    //
                    // B is computed efficiently using Java's operations
                    // and relies on known overflow behavior.
                    long B = Long.MIN_VALUE - (Long.MIN_VALUE % D) - 1;
                    if (x > B) continue;
                }
                buf[offset + k] = (int) ((x % D) + min);
                ++k;
            }
            at += 8 * k;
            offset += k;
            length -= k;
        }
    }

    /**
     * [min,max)
     */
    public final double nextDouble(double min, double max) {
        return (min < max) ? min + (max - min) * nextDouble() : min;
    }

    /**
     * [min,max)
     */
    public final void nextDoubles(double[] buf, int offset, int length,
                                  double min, double max) {
        if (max <= min) {
            java.util.Arrays.fill(buf, offset, offset + length, min);
            return;
        }
        double D = max - min;
        nextDoubles(buf, offset, length);
        for (int i = 0; i < length; ++i) {
            buf[offset + i] = D * buf[offset + i] + min;
        }
    }

    /**
     * [min,max)
     */
    public final float nextFloat(float min, float max) {
        return (min < max) ? (float) (min + (max - min) * nextFloat()) : min;
    }

    /**
     * [min,max)
     */
    public final void nextFloats(float[] buf, int offset, int length,
                                 float min, float max) {
        if (min >= max) {
            java.util.Arrays.fill(buf, offset, offset + length, min);
            return;
        }
        float D = max - min;
        nextFloats(buf, offset, length);
        for (int i = 0; i < length; ++i) {
            buf[offset + i] = D * buf[offset + i] + min;
        }
    }

    public final void seed() {
        byte[] value = new byte[24];
        try {
            try (FileInputStream in = new FileInputStream("/dev/urandom")) {
                if (in.read(value) == 24) {
                    seed(value);
                    return;
                }
            }
        } catch (Exception e) {
        }
        try {
            SecureRandom srng = new SecureRandom();
            srng.nextBytes(value);
            seed(value);
        } catch (Exception e) {
            throw new Error("Could not seed random number generator.");
        }
    }

    /**
     * value is zeroed after initialization for safety
     */
    public final synchronized void seed(byte[] value) {

        if (value.length != 24) {
            throw new UnsupportedOperationException("seed must be 24 bytes long.");
        }

        if (aesecb == null) {
            try {
                aesecb = Cipher.getInstance("AES/ECB/NoPadding");
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new Error("No AES/ECB/NoPadding cipher is inconceivable.");
            }
        }

        try {
            try (PortKey key = new PortKey(new SecretKeySpec(value, 0, 16, "AES"))) {
                aesecb.init(Cipher.ENCRYPT_MODE, key.data);
            }
            aesecb.doFinal(value, 8, 16, value, 8);
            try (PortKey key = new PortKey(new SecretKeySpec(value, 8, 16, "AES"))) {
                aesecb.init(Cipher.ENCRYPT_MODE, key.data);
            }

            ctr0 =
                    (((long) (value[0] ^ value[8])) >> (0 * 8))
                            | (((long) (value[1] ^ value[9])) >> (1 * 8))
                            | (((long) (value[2] ^ value[10])) >> (2 * 8))
                            | (((long) (value[3] ^ value[11])) >> (3 * 8))
                            | (((long) (value[4] ^ value[12])) >> (4 * 8))
                            | (((long) (value[5] ^ value[13])) >> (5 * 8))
                            | (((long) (value[6] ^ value[14])) >> (6 * 8))
                            | (((long) (value[7] ^ value[15])) >> (7 * 8));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Error("Failing to set aes key is inconceivable.");
        } finally {
            java.util.Arrays.fill(value, (byte) 0);
        }
        page = MIN_PAGE;
        at = page;
        ctr = 0;
        gaussianAt = 0;
        gaussianN = 0;
    }

    protected final int next(int bits) {
        return (bits <= 8) ? nextByte() : nextInt();
    }

    public final boolean nextBoolean() {
        return (nextByte() & 1) != 0;
    }

    public final void nextBytes(byte[] bytes) {
        nextBytes(bytes, 0, bytes.length);
    }

    private double[] gaussianQueue = null;
    private int gaussianAt = 0;
    private int gaussianN = 0;

    private final void nextGaussianPage() {
        if (gaussianQueue == null) gaussianQueue = new double[MAX_PAGE / 16];
        gaussianAt = 0;
        gaussianN = 0;
        while (gaussianN == 0) {
            int n = page / 16;
            nextDoubles(gaussianQueue, 0, n);
            for (int i = 0; i < n; i += 2) {
                double v1, v2, s;
                v1 = 2 * gaussianQueue[i] - 1;   // between -1.0 and 1.0
                v2 = 2 * gaussianQueue[i + 1] - 1;   // between -1.0 and 1.0
                s = v1 * v1 + v2 * v2;
                if (s >= 1 || s == 0) continue;

                double multiplier = Math.sqrt(-2 * Math.log(s) / s);
                gaussianQueue[gaussianN + 0] = v1 * multiplier;
                gaussianQueue[gaussianN + 1] = v2 * multiplier;

                gaussianN += 2;
            }
        }
    }

    public synchronized final void nextGaussians(double[] buf, int offset, int length) {
        while (length > 0) {
            if (gaussianAt >= gaussianN) nextGaussianPage();
            int n = gaussianN - gaussianAt;
            if (n > length) n = length;
            System.arraycopy(gaussianQueue, gaussianAt, buf, offset, n);
            gaussianAt += n;
            offset += n;
            length -= n;
        }
    }

    public synchronized final double nextGaussian() {
        if (gaussianAt >= gaussianN) nextGaussianPage();
        double ans = gaussianQueue[gaussianAt];
        ++gaussianAt;
        return ans;
    }

    public final void setSeed(long value) {
        super.setSeed(value);
        if (constructed) seed(value);
    }

    public final void seed(long _value) {

        byte[] value = new byte[24];

        value[0] = (byte) (_value >> (0 * 8));
        value[1] = (byte) (_value >> (1 * 8));
        value[2] = (byte) (_value >> (2 * 8));
        value[3] = (byte) (_value >> (3 * 8));
        value[4] = (byte) (_value >> (4 * 8));
        value[5] = (byte) (_value >> (5 * 8));
        value[6] = (byte) (_value >> (6 * 8));
        value[7] = (byte) (_value >> (7 * 8));

        seed(value);
    }

    class Stream extends InputStream {
        public final void insecure() {
            throw new
                    UnsupportedOperationException("supporting this operation is insecure");
        }

        @Override
        public final long skip(long n) {
            AESPRNG.this.skip(n);
            return n;
        }

        @Override
        public final int available() {
            return Integer.MAX_VALUE;
        }


        @Override
        public final void mark(int readlimit) {
            insecure();
        }

        @Override
        public final void reset() {
            insecure();
        }

        @Override
        public final boolean markSupported() {
            return false;
        }

        @Override
        public int read() {
            return 0xff & (int) nextByte();
        }

        @Override
        public int read(byte[] b) {
            nextBytes(b, 0, b.length);
            return b.length;
        }

        @Override
        public int read(byte[] b, int offset, int length) {
            nextBytes(b, offset, length);
            return length;
        }
    }

    InputStream stream() {
        return new Stream();
    }
}
