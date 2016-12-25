package com.ibm.wala.teavm;

public class JSWrapper {
	private final Hashable obj;

	public JSWrapper(Hashable obj) {
		this.obj = obj;
	}

	public Hashable getObj() {
		return obj;
	}

	public boolean equals(Object o) {
		if (o.getClass().equals(getClass())) {
			return obj == ((JSWrapper)o).obj;
		} else {
			return false;
		}
	}
	
	public int hashCode() {
		return obj.hashCode();
	}
	
	public String toString() {
		return obj.toString();
	}
}
