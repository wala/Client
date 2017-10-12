package com.ibm.wala.teavm.loader;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.Iterator;

import org.teavm.jso.core.JSString;

import com.ibm.wala.classLoader.Module;
import com.ibm.wala.classLoader.ModuleEntry;
import com.ibm.wala.classLoader.SourceModule;
import com.ibm.wala.util.collections.NonNullSingletonIterator;

public class JSStringModule implements Module, ModuleEntry, SourceModule {
	private final JSString fileName;
	private final JSString contents;

	public JSStringModule(JSString fileName, JSString contents) {
		this.fileName = fileName;
		this.contents = contents;
	}

	@Override
	public String getName() {
		return fileName.stringValue();
	}

	@Override
	public boolean isClassFile() {
		return false;
	}

	@Override
	public boolean isSourceFile() {
		return true;
	}

	@Override
	public InputStream getInputStream() {
		return new InputStream() {
			private int i = 0;
			@Override
			public int read() {
				if (i >= contents.getLength()) {
					return -1;
				} else {
					return contents.charCodeAt(i++);
				}
			}
		};
	}

	@Override
	public boolean isModuleFile() {
		return false;
	}

	@Override
	public Module asModule() {
		return null;
	}

	@Override
	public String getClassName() {
		return null;
	}

	@Override
	public Module getContainer() {
		return null;
	}

	@Override
	public Iterator<? extends ModuleEntry> getEntries() {
		return new NonNullSingletonIterator<JSStringModule>(this);
	}

	@Override
	public Reader getInputReader() {
		return new Reader() {
			int i = 0;

			@Override
			public int read(char[] cbuf, int off, int len) throws IOException {
				if (i >= contents.getLength()) {
					return -1;
				}

				int j;
				for(j = 0; j < len; j++) {
					if (i == contents.getLength()) {
						return j;
					}
					cbuf[j+off] = (char) contents.charCodeAt(i++);
				}
				return j;
			}

			@Override
			public void close() throws IOException {
				// do nothing
			}
		};
	}

	@Override
	public URL getURL() {
		assert false;
		return null;
	}
}

