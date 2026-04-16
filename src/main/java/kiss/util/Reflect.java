package kiss.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.DataInputStream;
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

    /** Parse the class file LineNumberTable to get source declaration order */
    public static Method[] getDeclaredMethodsInOrder(Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        try {
            String resource = clazz.getName().replace('.', '/') + ".class";
            InputStream raw = clazz.getClassLoader().getResourceAsStream(resource);
            if (raw == null) return methods;

            DataInputStream dis = new DataInputStream(raw);
            try {
                if (dis.readInt() != 0xCAFEBABE) return methods;
                dis.readUnsignedShort(); // minor version
                dis.readUnsignedShort(); // major version

                int cpCount = dis.readUnsignedShort();
                String[] cp = new String[cpCount];
                for (int i = 1; i < cpCount; i++) {
                    int tag = dis.readUnsignedByte();
                    switch (tag) {
                        case 1: cp[i] = dis.readUTF(); break;
                        case 3: case 4: dis.readInt(); break;
                        case 5: case 6: dis.readLong(); i++; break;
                        case 7: case 8: case 16: dis.readUnsignedShort(); break;
                        case 9: case 10: case 11: case 12: case 18:
                            dis.readUnsignedShort(); dis.readUnsignedShort(); break;
                        case 15: dis.readUnsignedByte(); dis.readUnsignedShort(); break;
                        default: return methods;
                    }
                }

                dis.readUnsignedShort(); // access flags
                dis.readUnsignedShort(); // this class
                dis.readUnsignedShort(); // super class
                int ifaceCount = dis.readUnsignedShort();
                for (int i = 0; i < ifaceCount; i++) dis.readUnsignedShort();

                skipMembers(dis); // fields

                int methodCount = dis.readUnsignedShort();
                HashMap<String, Integer> lineNumbers = new HashMap<String, Integer>();
                for (int i = 0; i < methodCount; i++) {
                    dis.readUnsignedShort(); // access flags
                    String name = cp[dis.readUnsignedShort()];
                    String desc = cp[dis.readUnsignedShort()];
                    int line = firstLineNumber(dis, cp);
                    if (line >= 0) lineNumbers.put(name + desc, line);
                }

                MethodOffset[] mo = new MethodOffset[methods.length];
                for (int i = 0; i < methods.length; i++) {
                    String key = methods[i].getName() + descriptor(methods[i]);
                    Integer line = lineNumbers.get(key);
                    mo[i] = new MethodOffset(methods[i],
                        line != null ? line : Integer.MAX_VALUE);
                }
                java.util.Arrays.sort(mo);
                for (int i = 0; i < mo.length; i++) {
                    methods[i] = mo[i].method;
                }
            } finally {
                dis.close();
            }
        } catch (Exception ex) {
            // fall back to unordered
        }
        return methods;
    }

    private static void skipMembers(DataInputStream dis) throws java.io.IOException {
        int count = dis.readUnsignedShort();
        for (int i = 0; i < count; i++) {
            dis.readUnsignedShort(); // access flags
            dis.readUnsignedShort(); // name index
            dis.readUnsignedShort(); // descriptor index
            skipAttributes(dis);
        }
    }

    private static void skipAttributes(DataInputStream dis) throws java.io.IOException {
        int count = dis.readUnsignedShort();
        for (int i = 0; i < count; i++) {
            dis.readUnsignedShort(); // name index
            skipN(dis, dis.readInt());
        }
    }

    private static int firstLineNumber(DataInputStream dis, String[] cp) throws java.io.IOException {
        int firstLine = -1;
        int attrCount = dis.readUnsignedShort();
        for (int i = 0; i < attrCount; i++) {
            String attrName = cp[dis.readUnsignedShort()];
            int attrLen = dis.readInt();
            if ("Code".equals(attrName)) {
                dis.readUnsignedShort(); // max stack
                dis.readUnsignedShort(); // max locals
                skipN(dis, dis.readInt()); // bytecode
                skipN(dis, dis.readUnsignedShort() * 8); // exception table
                int subCount = dis.readUnsignedShort();
                for (int j = 0; j < subCount; j++) {
                    String subName = cp[dis.readUnsignedShort()];
                    int subLen = dis.readInt();
                    if ("LineNumberTable".equals(subName)) {
                        int entries = dis.readUnsignedShort();
                        for (int k = 0; k < entries; k++) {
                            dis.readUnsignedShort(); // start pc
                            int line = dis.readUnsignedShort();
                            if (firstLine < 0 || line < firstLine) firstLine = line;
                        }
                    } else {
                        skipN(dis, subLen);
                    }
                }
            } else {
                skipN(dis, attrLen);
            }
        }
        return firstLine;
    }

    private static void skipN(DataInputStream dis, int n) throws java.io.IOException {
        while (n > 0) {
            int skipped = dis.skipBytes(n);
            if (skipped > 0) {
                n -= skipped;
            } else {
                dis.readByte();
                n--;
            }
        }
    }

    private static String descriptor(Method m) {
        StringBuilder sb = new StringBuilder("(");
        for (Class<?> p : m.getParameterTypes()) typeDesc(sb, p);
        sb.append(")");
        typeDesc(sb, m.getReturnType());
        return sb.toString();
    }

    private static void typeDesc(StringBuilder sb, Class<?> c) {
        if      (c == void.class)    sb.append("V");
        else if (c == boolean.class) sb.append("Z");
        else if (c == byte.class)    sb.append("B");
        else if (c == char.class)    sb.append("C");
        else if (c == short.class)   sb.append("S");
        else if (c == int.class)     sb.append("I");
        else if (c == long.class)    sb.append("J");
        else if (c == float.class)   sb.append("F");
        else if (c == double.class)  sb.append("D");
        else if (c.isArray()) { sb.append("["); typeDesc(sb, c.getComponentType()); }
        else sb.append("L").append(c.getName().replace('.', '/')).append(";");
    }
}
