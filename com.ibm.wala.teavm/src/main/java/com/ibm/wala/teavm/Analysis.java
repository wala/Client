package com.ibm.wala.teavm;

import java.io.IOException;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSString;

import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.util.CancelException;

public class Analysis {

	@JSBody(params = { "s" }, script = "analyzer = s;")
	public static native void setAnalyzer(Analyzer s);


	@JSFunctor 
	public interface Analyzer extends JSObject {
		JSString analyze(JSString fileName, JSString contents) throws CancelException, ClassHierarchyException, IOException;
	}
	
}
