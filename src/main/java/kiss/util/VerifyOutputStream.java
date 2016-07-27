package kiss.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class VerifyOutputStream extends OutputStream {
	InputStream verify;
	long at = 0L;
	long mismatch = Long.MAX_VALUE;
	
	public boolean matches() { return mismatch == Long.MAX_VALUE; }
	public long getMismatch() { return mismatch; }

	public VerifyOutputStream(InputStream _verify) {
		verify = _verify;
	}

	public void flush() throws IOException {
	};

	public void write(byte[] data, int offset, int length) throws IOException {
		if (offset != 0 || length != data.length) {
			byte[] writedata = new byte[length];
			System.arraycopy(data, offset, writedata, 0, length);
			write(writedata);
		} else {
			write(data);
		}
	}

	public void write(byte[] data) throws IOException {
		byte[] rdata = new byte[data.length];
		if (mismatch == Long.MAX_VALUE) {
			long readBytes = verify.read(rdata);
			if ((readBytes == data.length) && java.util.Arrays.equals(data, rdata)) {
				at += readBytes;
				return;
			}
			int i = 0;
			for (i = 0; i < readBytes; ++i) {
				if (data[i] != rdata[i])
					break;
			}
			mismatch = at + i;
                        verify.close();
			throw new VerifyRuntimeException("mismatch at byte offset " + mismatch);                        
		}
	}

	public void write(int b) throws IOException {
		byte[] data = new byte[1];
		data[0] = (byte) b;
		write(data);
	}

	public void close() throws IOException {
		if (mismatch == Long.MAX_VALUE) {
			int ch = verify.read();
			if (ch != -1) {
				mismatch = at;
			}
		}
		verify.close();
		if (!matches()) {
			throw new VerifyRuntimeException("mismatch at byte offset " + mismatch);
		}
	}
}
