package kiss.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class IO {
    static ThreadLocal<LinkedList<Scanner>> ins = new ThreadLocal<LinkedList<Scanner>>() {
    	@Override public LinkedList<Scanner> initialValue() {
    		LinkedList<Scanner> ans = new LinkedList<Scanner>();
    		ans.add(new Scanner(System.in));
    		return ans;
        }
    };
    
    static ThreadLocal<LinkedList<PrintStream>> outs = new ThreadLocal<LinkedList<PrintStream>>() {
       	@Override public LinkedList<PrintStream> initialValue() {
    		LinkedList<PrintStream> ans = new LinkedList<PrintStream>();
    		ans.add(System.out);
    		return ans;
        }
    };
    
    static class OutClose implements Closeable {

		@Override
		public void close() throws IOException {
			outClose();
		} 
    	
    };
    static OutClose OUT_CLOSE = new OutClose();
    
    static class InClose implements Closeable {

		@Override
		public void close() throws IOException {
			inClose();
		} 
    	
    }
    static InClose IN_CLOSE = new InClose();

    public static Closeable outVerify(String filename) {
        try {
            outs.get().addLast(new PrintStream(new VerifyOutputStream(new FileInputStream(filename))));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return OUT_CLOSE;

    }
    
    public static Closeable outOpen(String filename) {
    	return outOpen(new File(filename));
    }

    public static Closeable outOpen(File file) {
        try {
            outs.get().addLast(new PrintStream(new FileOutputStream(file)));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return OUT_CLOSE;
    }
    public static void outClose() {
        outs.get().removeLast().close();
    }

    static PrintStream out() { return outs.get().getLast(); }
    static Scanner in() { return ins.get().getLast(); }

    public static Closeable inOpen(File file) {
        try {
			ins.get().addLast(new Scanner(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
        return IN_CLOSE;
    }
    
    public static Closeable inOpen(String filename) {
    	return inOpen(new File(filename));
    }

    public static void inClose() {
        ins.get().removeLast().close();
    }

    public static void format(StringBuilder ans, boolean value) {
        ans.append(Boolean.toString(value));
    }

    public static void format(StringBuilder ans, byte value) {
        ans.append(Byte.toString(value));
    }

    public static void format(StringBuilder ans, char value) {
        ans.append(Character.toString(value));
    }

    public static void format(StringBuilder ans, short value) {
        ans.append(Short.toString(value));
    }

    public static void format(StringBuilder ans, int value) {
        ans.append(Integer.toString(value));
    }

    public static void format(StringBuilder ans, long value) {
        ans.append(Long.toString(value));
    }

    public static void format(StringBuilder ans, float value) {
        ans.append(Float.toString(value));
    }

    public static void format(StringBuilder ans, double value) {
        ans.append(Double.toString(value));
    }

    // format a (possibly nested) object
    public static void format(StringBuilder ans, Object object) {
        if (object.getClass().isArray()) {
            if (object instanceof Object[]) {
                Object[] array = (Object[]) object;
                ans.append("[");
                boolean first = true;
                for (int i = 0; i < array.length; ++i) {
                    if (first) {
                        first = false;
                    } else {
                        ans.append(",");
                    }
                    format(ans, array[i]);
                }
                ans.append("]");
            } else {
                if (object instanceof boolean[]) {
                    ans.append(Arrays.toString((boolean[]) object));
                } else if (object instanceof byte[]) {
                    ans.append(Arrays.toString((byte[]) object));
                } else if (object instanceof char[]) {
                    ans.append(Arrays.toString((char[]) object));
                } else if (object instanceof short[]) {
                    ans.append(Arrays.toString((short[]) object));
                } else if (object instanceof int[]) {
                    ans.append(Arrays.toString((int[]) object));
                } else if (object instanceof long[]) {
                    ans.append(Arrays.toString((long[]) object));
                } else if (object instanceof float[]) {
                    ans.append(Arrays.toString((float[]) object));
                } else if (object instanceof double[]) {
                    ans.append(Arrays.toString((double[]) object));
                } else {
                    throw new IllegalArgumentException("Unknown array type (should not happen)");
                }
            }
        } else if (object instanceof Map) {
            Set<Map.Entry> set = ((Map) object).entrySet();
            ans.append("[");
            boolean first = true;
            for (Map.Entry entry : set) {
                if (first)
                    first = false;
                else
                    ans.append(",");
                format(ans, entry.getKey());
                ans.append(":");
                format(ans, entry.getValue());
            }
            ans.append("]");
        } else if (object instanceof Collection) {
            Collection collection = (Collection) object;
            ans.append("[");
            boolean first = true;
            for (Object element : collection) {
                if (first)
                    first = false;
                else
                    ans.append(",");
                format(ans, element);
            }
            ans.append("]");
        } else {
            ans.append(object.toString());
        }
    }

    public static String format(boolean value) {
        return (Boolean.toString(value));
    }

    public static String format(byte value) {
        return (Byte.toString(value));
    }

    public static String format(char value) {
        return (Character.toString(value));
    }

    public static String format(short value) {
        return (Short.toString(value));
    }

    public static String format(int value) {
        return (Integer.toString(value));
    }

    public static String format(long value) {
        return (Long.toString(value));
    }

    public static String format(float value) {
        return (Float.toString(value));
    }

    public static String format(double value) {
        return (Double.toString(value));
    }

    public static String format(Object object) {
        StringBuilder ans = new StringBuilder();
        format(ans, object);
        return ans.toString();
    }

    public static void print(Object value) {
        out().print(format(value));
    }

    public static void print(boolean value) {
        out().print(value);

    }

    public static void print(byte value) {
        out().print(value);
    }

    public static void print(char value) {
        out().print(value);
    }

    public static void print(short value) {
        out().print(value);
    }

    public static void print(int value) {
        out().print(value);
    }

    public static void print(long value) {
        out().print(value);
    }

    public static void print(float value) {
        out().print(value);
    }

    public static void print(double value) {
        out().print(value);
    }

    public static void println(Object value) {
        out().println(format(value));
    }

    public static void println(boolean value) {
        out().println(value);
    }

    public static void println(byte value) {
        out().println(value);
    }

    public static void println(char value) {
        out().println(value);
    }

    public static void println(short value) {
        out().println(value);
    }

    public static void println(int value) {
        out().println(value);
    }

    public static void println(long value) {
        out().println(value);
    }

    public static void println(float value) {
        out().println(value);
    }

    public static void println(double value) {
        out().println(value);
    }

    static void printva(PrintStream ps, Object args[]) {
        for (int i=0; i<args.length; ++i) {
            if (i>0) ps.print(",");
            ps.print(format(args[i]));
        }
    }

    public static void printva(Object[] args) {
        PrintStream ps = out();
        printva(ps,args);
    }

    public static void printlnva(Object[] args) {
        PrintStream ps = out();
        printva(ps,args);
        ps.println();
    }
    
    public static String readLine() {
    	Scanner in=in();
    	if (in.hasNextLine()) {
    		String value = in.nextLine();
    		if (ins.get().size() > 1 || outs.get().size() > 1) println(value);
    		return value;
    	} else {
    		return null;
    	}
    }

    public static Boolean readBoolean() {
    	Scanner in=in();
    	if (in.hasNextBoolean()) {
    		boolean value = in.nextBoolean();
    		if (ins.get().size() > 1 || outs.get().size() > 1) println(value);
    		return value;
    	} else {
    		return null;
    	}
    }

    public static Byte readByte() {
    	Scanner in=in();
    	if (in.hasNextByte()) {
    		byte value = in.nextByte();
    		if (ins.get().size() > 1 || outs.get().size() > 1) println(value);
    		return value;
    	} else {
    		return null;
    	}
    }

    public static Integer readInteger() {
    	Scanner in=in();
    	if (in.hasNextInt()) {
    		int value = in.nextInt();
    		if (ins.get().size() > 1 || outs.get().size() > 1) println(value);
    		return value;
    	} else {
    		return null;
    	}
    }

    public static Long readLong() {
    	Scanner in=in();
    	if (in.hasNextLong()) {
    		long value = in.nextLong();
    		if (ins.get().size() > 1 || outs.get().size() > 1) println(value);
    		return value;
    	} else {
    		return null;
    	}
    }

    public static Float readFloat() {
    	Scanner in=in();
    	if (in.hasNextFloat()) {
    		float value = in.nextFloat();
    		if (ins.get().size() > 1 || outs.get().size() > 1) println(value);
    		return value;
    	} else {
    		return null;
    	}
    }

    public static Double readDouble() {
    	Scanner in=in();
    	if (in.hasNextDouble()) {
    		double value = in.nextDouble();
    		if (ins.get().size() > 1 || outs.get().size() > 1) println(value);
    		return value;
    	} else {
    		return null;
    	}
    }
}
