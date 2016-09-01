package kiss.util;

import static kiss.API.*;

class TestCipher {
    void testHex() {
        assert Cipher.hex(null) == null;
        assert Cipher.hex(new byte[] { }).equals("");
        assert Cipher.hex(new byte[] { (byte) 0x00 }).equals("00");
        assert Cipher.hex(new byte[] { (byte) 0x09 }).equals("09");
        assert Cipher.hex(new byte[] { (byte) 0x0a }).equals("0a");
        assert Cipher.hex(new byte[] { (byte) 0x0f }).equals("0f");
        assert Cipher.hex(new byte[] { (byte) 0x90 }).equals("90");
        assert Cipher.hex(new byte[] { (byte) 0x99 }).equals("99");
        assert Cipher.hex(new byte[] { (byte) 0x9a }).equals("9a");
        assert Cipher.hex(new byte[] { (byte) 0x9f }).equals("9f");
        assert Cipher.hex(new byte[] { (byte) 0xa0 }).equals("a0");
        assert Cipher.hex(new byte[] { (byte) 0xa9 }).equals("a9");
        assert Cipher.hex(new byte[] { (byte) 0xaa }).equals("aa");
        assert Cipher.hex(new byte[] { (byte) 0xaf }).equals("af");
        assert Cipher.hex(new byte[] { (byte) 0xf0 }).equals("f0");
        assert Cipher.hex(new byte[] { (byte) 0xf9 }).equals("f9");
        assert Cipher.hex(new byte[] { (byte) 0xfa }).equals("fa");
        assert Cipher.hex(new byte[] { (byte) 0xff }).equals("ff");

        assert Cipher.hex(new byte[] { (byte) 0xde,
                                       (byte) 0xad,
                                       (byte) 0xbe,
                                       (byte) 0xef }).equals("deadbeef");

    }

    void testBin() {
        assert Cipher.bin(null) == null;
        assert asString(Cipher.bin("")).equals("[]");
        assert asString(Cipher.bin("807f00")).equals("[-128, 127, 0]");
        assert asString(Cipher.bin("80_7f_00 ")).equals("[-128, 127, 0]");
        assert asString(Cipher.bin("a")).equals("[10]");
        assert asString(Cipher.bin("7fa")).equals("[127, 10]");
    }

    void testSha256() {
        assert Cipher.sha256("").equals("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
        assert Cipher.sha256("a").equals("ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb");
        assert Cipher.sha256("x").equals("2d711642b726b04401627ca9fbac32f5c8530fb1903cc4db02258717921a4881");
    }

    void testEncrypt() {
        String[] keys = new String[] { "", "a", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                                       "b", "ab", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab" };

        String[] plains = new String[] { "", "x", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
                                         "y", "xy", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxy" };

        // never encrypts twice to same cipher
        for (String key : keys) {
            for (String plain : plains) {
                assert !Cipher.encrypt(key,plain).equals(Cipher.encrypt(key,plain));
            }
        }

        // right key works
        for (String key : keys) {
            for (String plain : plains) {
                assert Cipher.decrypt(key,Cipher.encrypt(key,plain)).equals(plain);
            }
        }

        // wrong key fails
        for (String key1 : keys) {
            for (String key2 : keys) {
                if (!key1.equals(key2)) {
                    for (String plain : plains) {
                        assert Cipher.decrypt(key1,Cipher.encrypt(key2,plain)) == null;
                    }
                }
            }
        }

    }
}
