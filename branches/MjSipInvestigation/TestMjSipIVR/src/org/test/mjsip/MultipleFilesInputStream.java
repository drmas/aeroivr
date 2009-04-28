package org.test.mjsip;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultipleFilesInputStream extends InputStream {

	private List<String> audio_files = new ArrayList<String>();

	private FileInputStream current_file = null;

	public void addFileToPlay(String file_name) {
		synchronized (audio_files) {
			audio_files.add(file_name);
		}
	}

	@Override
	public int read() throws IOException {
		byte[] buffer = new byte[1];
		int count = read(buffer, 0, 1);
		if (count > 0) {
			return buffer[0];
		}
		else {
			if (0 == count) {
				return 0;
			}
			else {
				return -1;
			}
		}
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {

		if (null == current_file) {
			synchronized (audio_files) {
				if (audio_files.size() > 0) {
					String file_name = audio_files.get(0);
					audio_files.remove(0);
					current_file = new FileInputStream(file_name);
				}
			}
		}
		if (null != current_file) {
			return current_file.read(b, off, len);
		}
		return 0;
	}

	@Override
	public void close() throws IOException {
		if (null != current_file) {
			current_file.close();
		}
		synchronized (audio_files) {
			audio_files.clear();
		}
		super.close();
	}
}
