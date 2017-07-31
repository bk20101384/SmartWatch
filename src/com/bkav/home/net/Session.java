package com.bkav.home.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Session {
	public InputStream getInputStream() throws IOException;
	public OutputStream getOutputStream() throws IOException;
	public void close();
}
