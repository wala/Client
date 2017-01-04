package com.ibm.wala.teavm.bridge;

import org.teavm.jso.JSIndexer;
import org.teavm.jso.JSObject;
import org.teavm.jso.JSProperty;

public interface Constraints extends JSObject{
	
	@JSIndexer
	Constraint constraint(int i);
	
	@JSProperty
	int getSize();
	
}
