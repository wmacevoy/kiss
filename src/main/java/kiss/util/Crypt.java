package kiss.util;

import java.security.SecureRandom;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.GCMParameterSpec;
import java.util.Arrays;
import java.nio.charset.Charset;

public class Crypt {
    static char [] codes = new char [] { 
        '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' 
    };
    static void hex(StringBuilder sb, byte[] bin, int offset, int length) {
        for (int i=0; i<length; ++i) {
            byte b=bin[i+offset];
            sb.append(codes[((b>>4) & 0xF)]);
            sb.append(codes[((b>>0) & 0xF)]);
        }
    }

    static String hex(byte[] bin) {
        StringBuilder sb = new StringBuilder(bin.length*2);
        hex(sb,bin,0,bin.length);
        return sb.toString();
    }

    static byte[] bin(CharSequence hex) {
        byte[] ans = new byte[hex.length()/2];
        int j=0;
        byte b=0;
        for (int i=0; i<hex.length(); ++i) {
            char d=hex.charAt(i);
            if ('0' <= d && d <= '9') {
                b = (byte)((b<<4) + (d-'0'));
            } else if ('a' <= d && d <= 'f') {
                b = (byte)((b<<4) + (d-'a'+10));
            } else if ('A' <= d && d <= 'F') {
                b = (byte)((b<<4) + (d-'A'+10));
            } else {
                continue;
            }
            if ((j % 2) == 1) {
                ans[j/2]=b;
                b=0;
            }
            ++j;
        }
        if (j < ans.length) {
            ans = java.util.Arrays.copyOf(ans,j);
        }
        return ans;
    }

    public static final byte[] sha256(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(data);
            return md.digest();
        } catch (Exception e) {
            throw new Error("sha-256 digest failed: " + e);
        }
    }

    public static final String sha256(String data) {
        return hex(sha256(asBytes(data)));
    }

    // AES-GCM parameters
    public static final int AES_KEY_BITS = 128;
    public static final int GCM_NONCE_BITS = 96;
    public static final int GCM_TAG_BITS = 128;

    static final AESPRNG rng = new AESPRNG();

    static final byte[] pad = new byte[] { 1, 0, 0, 0,
                                           0, 0, 0, 0,
                                           0, 0, 0, 0,
                                           0, 0, 0, 0 };

    public static final byte[] encrypt(byte[] key, byte[] plain) {
        int padlen = ((plain.length+16) & ~15) - plain.length;
        int enclen = GCM_NONCE_BITS/8 + plain.length + padlen + GCM_TAG_BITS/8;
        byte[] enc = new byte[enclen];

        try {
            SecretKeySpec aesKey = 
                new SecretKeySpec(Arrays.copyOf(sha256(key),AES_KEY_BITS/8),
                                  "AES");

            rng.nextBytes(enc,0,GCM_NONCE_BITS/8);
            
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            final byte[] nonce = new byte[GCM_NONCE_BITS/8];
            rng.nextBytes(nonce);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_BITS, nonce);
            cipher.init(Cipher.ENCRYPT_MODE, aesKey, spec);
            System.arraycopy(nonce,0,enc,0,nonce.length);
            int n = cipher.update(plain,0,plain.length,enc,nonce.length);
            cipher.doFinal(pad,0,padlen,enc,n+nonce.length);
            return enc;
        } catch (Exception e) {
            throw new Error("encrypt failed: " + e);
        }
    }

    public static final String encrypt(String key, String plain) {
        return hex(encrypt(asBytes(key),asBytes(plain)));
    }

    public static final byte[] decrypt(byte[] key, byte[] enc) {
        if (enc == null || enc.length < GCM_NONCE_BITS/8 + 16 + GCM_TAG_BITS/8) {
            return null;
        }
        try {
            int plainPadLen = enc.length - (GCM_NONCE_BITS/8 + GCM_TAG_BITS/8);
            byte[] plainPad = new byte[plainPadLen];
            SecretKeySpec aesKey = 
                new SecretKeySpec(Arrays.copyOf(sha256(key),AES_KEY_BITS/8),
                                  "AES");

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");        
            byte[] nonce = Arrays.copyOf(enc,GCM_NONCE_BITS/8);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_BITS, nonce);
            cipher.init(Cipher.DECRYPT_MODE, aesKey, spec);
            cipher.doFinal(enc,nonce.length,enc.length-nonce.length,plainPad,0);
            int pad = plainPad.length-1;
            while (pad > 0 && plainPad[pad] != 1) --pad;
            return Arrays.copyOf(plainPad,pad);
        } catch (Exception e) {
            return null;
        }
    }

    static final byte[] asBytes(String string) {
        if (string != null) {
            return string.getBytes(Charset.forName("UTF-8"));
        } else { 
            return null;
        }
    }

    static final String asString(byte[] bytes) {
        if (bytes != null) {
            return new String(bytes,Charset.forName("UTF-8"));
        } else {
            return null;
        }
    }

    public static final String decrypt(String key, String enc) {
        return asString(decrypt(asBytes(key),bin(enc)));
    }

}
