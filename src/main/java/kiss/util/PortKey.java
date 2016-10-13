package kiss.util;

import java.lang.reflect.Method;

import javax.crypto.spec.SecretKeySpec;

/**
 * Created by wmacevoy on 10/12/16.
 */

public class PortKey implements kiss.API.Close {

    // forward portable to JDK 1.8 to destroy keys
    // but usable in older JDK's

    static final Method destroyKey;

    static {
        Method _destroyKey = null;
        try {
            _destroyKey = SecretKeySpec.class.getMethod("destroy");
        } catch (Exception ex) {
        }
        destroyKey = _destroyKey;
    }

    static void destroy(SecretKeySpec key) {
        if (key != null && destroyKey != null) {
            try {
                destroyKey.invoke(key);
            } catch (Exception ex) {
            }
        }
    }

    public final SecretKeySpec data;

    PortKey(SecretKeySpec _data) {
        data = _data;
    }

    public void close() {
        destroy(data);
    }
}
