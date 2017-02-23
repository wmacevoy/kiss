package kiss.util;

import javax.crypto.AEADBadTagException;
import java.security.MessageDigest;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.GCMParameterSpec;
import java.util.Arrays;
import java.nio.charset.Charset;

public class Cipher {
    static char [] codes = new char [] { 
        '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' 
    };
    public static void hex(StringBuilder sb, byte[] bin, int offset, int length) {
        for (int i=0; i<length; ++i) {
            byte b=bin[i+offset];
            sb.append(codes[((b>>4) & 0xF)]);
            sb.append(codes[((b>>0) & 0xF)]);
        }
    }

    public static String hex(byte[] bin) {
        if (bin == null) return null;
        StringBuilder sb = new StringBuilder(bin.length*2);
        hex(sb,bin,0,bin.length);
        return sb.toString();
    }

    public static byte[] bin(CharSequence hex) {
        if (hex == null) return null;
        byte[] ans = new byte[(hex.length()+1)/2];
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
        if ((j % 2) == 1) {
            ans[j/2]=b;
            ++j;
        }
        j = j/2;
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
    public static final int AES_KEY_LEN = 16;
    public static final int GCM_NONCE_LEN = 12;
    public static final int GCM_TAG_LEN = 16;
    public static final int PKCS5_LEN = 16;

    static final AESPRNG rng = new AESPRNG();
    private static void random(byte[] buf, int offset, int length) {
	rng.nextBytes(buf,offset,length);
    }

    private static final SecretKeySpec getKey(byte[] nonce, byte[] key) throws Exception {
	if (key.length == AES_KEY_LEN) {
	    return new SecretKeySpec(key,"AES");
	}

	byte[] _key = new byte[AES_KEY_LEN];

	System.arraycopy(nonce,0,_key,0,GCM_NONCE_LEN);
	System.arraycopy(nonce,0,_key,GCM_NONCE_LEN,AES_KEY_LEN-GCM_NONCE_LEN);

	byte[] tmp = encrypt(nonce,_key,key);
	System.arraycopy(tmp,tmp.length-AES_KEY_LEN,_key,0,AES_KEY_LEN);
	Arrays.fill(tmp,(byte) 0);
	     
	SecretKeySpec ans = new SecretKeySpec(_key,"AES");
	Arrays.fill(_key,(byte) 0);
	return ans;
    }

    private static final byte[] encrypt(byte[] nonce, byte[] key, byte[] plain) {
        byte padlen = (byte)(PKCS5_LEN-(plain.length%PKCS5_LEN));
        int enclen = GCM_NONCE_LEN + plain.length + padlen + GCM_TAG_LEN;
	byte [] enc = new byte[enclen];
	if (nonce == null) {
	    random(enc,0,GCM_NONCE_LEN);
	} else {
	    System.arraycopy(nonce,0,enc,0,GCM_NONCE_LEN);
	}

        try {
	    SecretKeySpec aesKey=getKey(enc,key);

            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LEN*8, enc, 0, GCM_NONCE_LEN);
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, aesKey, spec);
            int n = cipher.update(plain,0,plain.length,enc,GCM_NONCE_LEN);
            byte[] pad = new byte[PKCS5_LEN];
            for (int i=0; i<padlen; ++i) {
                pad[i]=padlen;
            }
            cipher.doFinal(pad,0,padlen,enc,n+GCM_NONCE_LEN);
            return enc;
        } catch (Exception e) {
            throw new Error("encrypt failed: " + e);
        }
    }

    public static final byte[] encrypt(byte[] key, byte[] plain) {
	return encrypt(null,key,plain);
    }

    public static final String encrypt(String key, String plain) {
        return hex(encrypt(asBytes(key),asBytes(plain)));
    }

    public static final byte[] decrypt(byte[] key, byte[] enc) {
        if (enc == null || 
            enc.length < GCM_NONCE_LEN + PKCS5_LEN + GCM_TAG_LEN) {
            return null;
        }
	int plainPadLen = enc.length - (GCM_NONCE_LEN + GCM_TAG_LEN);
	byte[] plainPad = new byte[plainPadLen];
	byte [] plain = null;
	
        try {
	    SecretKeySpec aesKey=getKey(enc,key);	    
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");        
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LEN*8, enc, 0, GCM_NONCE_LEN);
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, aesKey, spec);
            cipher.doFinal(enc,GCM_NONCE_LEN,
                           enc.length-GCM_NONCE_LEN,plainPad,0);
            byte padlen = plainPad[plainPad.length-1];
	    plain = Arrays.copyOf(plainPad,plainPad.length-padlen);
	} catch (AEADBadTagException e) {
            plain = null;
	} catch (Exception e) {
	    throw new Error("decrypt failed: " + e);
	} finally {
	    if (plain != plainPad) {
		Arrays.fill(plainPad,(byte) 0);
	    }
        }
	return plain;
    }

    public static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

    public static final byte[] asBytes(String string) {
        if (string != null) {
            return string.getBytes(CHARSET_UTF8);
        } else { 
            return null;
        }
    }

    public static final String asString(byte[] bytes) {
        if (bytes != null) {
            return new String(bytes,CHARSET_UTF8);
        } else {
            return null;
        }
    }

    public static final String decrypt(String key, String enc) {
        return asString(decrypt(asBytes(key),bin(enc)));
    }

}
