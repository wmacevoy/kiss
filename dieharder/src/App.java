import static kiss.API.*;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Closeable;

class App {

    OutputStream open(String name) throws Exception {
	if (name.equals("-")) return System.out;
	return new FileOutputStream(name);
    }

    void run() throws Exception {
	long size = APP_ARGS.length > 1 ? asLong(APP_ARGS[1]) : Long.MAX_VALUE;
	kiss.util.AESPRNG rng = new kiss.util.AESPRNG();
	byte[] block = new byte[1024*1024];
	try (OutputStream out = open(APP_ARGS[0])) {
	    while (size > 0) {
		int n = block.length;
		if (asLong(n) > size) {
		    n = asInt(size);
		}
		rng.nextBytes(block,0,n);
		out.write(block,0,n);
		size -= n;
	    }
	}

    }
}
