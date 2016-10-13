package kiss.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.InputStream;

public class Reflect {
/**
     * Get the underlying class for a type, or null if the type is a variable type.
     * @param type the type
     * @return the underlying class
     */
    private static Class<?> getClass(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        else if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        }
        else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> componentClass = getClass(componentType);
            if (componentClass != null ) {
                return Array.newInstance(componentClass, 0).getClass();
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    /**
     *
     * Rarified code used to discover event listeners for generic Generator
     *
     * Almost verbatim from:
     *
     * http://www.artima.com/weblogs/viewpost.jsp?thread=208860
     *
     */
    public static <T> List<Class<?>> getTypeArguments(
                                                      Class<T> baseClass, Class<? extends T> childClass) {
        Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
        Type type = childClass;
        // start walking up the inheritance hierarchy until we hit baseClass
        while (! getClass(type).equals(baseClass)) {
            if (type instanceof Class) {
                // there is no useful information for us in raw types, so just keep going.
                type = ((Class) type).getGenericSuperclass();
            }
            else {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Class<?> rawType = (Class) parameterizedType.getRawType();

                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
                for (int i = 0; i < actualTypeArguments.length; i++) {
                    resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
                }

                if (!rawType.equals(baseClass)) {
                    type = rawType.getGenericSuperclass();
                }
            }
        }

        // finally, for each actual type argument provided to baseClass, determine (if possible)
        // the raw class for that type argument.
        Type[] actualTypeArguments;
        if (type instanceof Class) {
            actualTypeArguments = ((Class) type).getTypeParameters();
        }
        else {
            actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        }
        List<Class<?>> typeArgumentsAsClasses = new ArrayList<Class<?>>();
        // resolve types by chasing down type variables.
        for (Type baseType: actualTypeArguments) {
            while (resolvedTypes.containsKey(baseType)) {
                baseType = resolvedTypes.get(baseType);
            }
            typeArgumentsAsClasses.add(getClass(baseType));
        }
        return typeArgumentsAsClasses;
    }

    private static class MethodOffset implements Comparable<MethodOffset> {
        MethodOffset(Method _method, int _offset) {
            method=_method;
            offset=_offset;
        }

        @Override
        public int compareTo(MethodOffset target) {
            return offset-target.offset;
        }

        Method method;
        int offset;
    }

    static class ByLength implements Comparator<Method> {

        @Override
        public int compare(Method a, Method b) {
            return b.getName().length()-a.getName().length();
        }
    }

    /** Grok the bytecode to get the declared order */
    public static Method[] getDeclaredMethodsInOrder(Class clazz) {
        Method[] methods = null;
        try {
            String resource = clazz.getName().replace('.', '/')+".class";

            methods = clazz.getDeclaredMethods();

            InputStream is = clazz.getClassLoader()
                .getResourceAsStream(resource);

            if (is == null) {
                return methods;
            }

            java.util.Arrays.sort(methods,new ByLength());
            ArrayList<byte[]> blocks = new ArrayList<byte[]>();
            int length = 0;
            for (;;) {
                byte[] block = new byte[16*1024];
                int n = is.read(block);
                if (n > 0) {
                    if (n < block.length) {
                        block = java.util.Arrays.copyOf(block,n);
                    }
                    length += block.length;
                    blocks.add(block);
                } else {
                    break;
                }
            }

            byte[] data = new byte[length];
            int offset = 0;
            for (byte[] block : blocks) {
                System.arraycopy(block,0,data,offset,block.length);
                offset += block.length;
            }

            String sdata = new String(data,java.nio.charset.Charset.forName("UTF-8"));
            int lnt = sdata.indexOf("LineNumberTable");
            if (lnt != -1) sdata = sdata.substring(lnt+"LineNumberTable".length()+3);
            int cde = sdata.lastIndexOf("SourceFile");
            if (cde != -1) sdata = sdata.substring(0,cde);
            
            MethodOffset mo[] = new MethodOffset[methods.length];

            
            for (int i=0; i<methods.length; ++i) {
                int pos = -1;
                for (;;) {
                    pos=sdata.indexOf(methods[i].getName(),pos);
                    if (pos == -1) break;
                    boolean subset = false;
                    for (int j=0; j<i; ++j) {
                        if (mo[j].offset >= 0 &&
                            mo[j].offset <= pos &&
                            pos < mo[j].offset + mo[j].method.getName().length()) {
                            subset = true;
                            break;
                        }
                    }
                    if (subset) {
                        pos += methods[i].getName().length();
                    } else {
                        break;
                    }
                }
                mo[i] = new MethodOffset(methods[i],pos);
            }
            java.util.Arrays.sort(mo);
            for (int i=0; i<mo.length; ++i) {
                methods[i]=mo[i].method;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return methods;
    }
    
}
