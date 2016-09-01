package kiss.util;

import static kiss.API.*;

class TestCipher {
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
