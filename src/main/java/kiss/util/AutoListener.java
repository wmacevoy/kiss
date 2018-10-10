package kiss.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import kiss.API.Generator;
import kiss.API.Listener;

public class AutoListener<Message> implements Listener<Message> {
    private static final HashMap< Class, Class> unbox = new HashMap<>();

    static {
        unbox.put(Boolean.class, boolean.class);
        unbox.put(Character.class, char.class);
        unbox.put(Byte.class, byte.class);
        unbox.put(Short.class, short.class);
        unbox.put(Integer.class, int.class);
        unbox.put(Long.class, long.class);
        unbox.put(Float.class, float.class);
        unbox.put(Double.class, double.class);
    }

    public static class Exception extends RuntimeException {

        Exception(java.lang.Exception cause) {
            super(cause);
        }
    }
    private Object object;
    private Method method;

    public AutoListener(Class<Message> type, Object _object) {
        try {
            object = _object;
            method = object.getClass().getDeclaredMethod("receive", type);
            method.setAccessible(true);
        } catch (NoSuchMethodException | SecurityException ex) {
            Class unboxed = unbox.get(type);
            if (unboxed != null) {
                try {
                    method = object.getClass().getDeclaredMethod("receive", unboxed);
                    method.setAccessible(true);
                    return;
                } catch (NoSuchMethodException | SecurityException ex2) {
                    throw new Exception(ex2);
                }
            }
            throw new Exception(ex);
        }
    }

    @Override
    public void receive(Message message) {
        try {
            method.invoke(object, message);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new Exception(ex);
        }
    }
}
