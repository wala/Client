package com.ibm.wala.teavm.bridge;

import org.teavm.jso.JSObject;

public interface Hashable extends JSObject {

	int hashCode();

	String toString();

}
