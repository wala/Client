package com.ibm.wala.teavm.loader;

import java.io.InputStream;
import java.util.Iterator;

import org.teavm.jso.core.JSString;

import com.ibm.wala.classLoader.Module;
import com.ibm.wala.classLoader.ModuleEntry;
import com.ibm.wala.util.collections.NonNullSingletonIterator;

public class JSStringModule implements Module, ModuleEntry {
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
}

