package com.ibm.wala.teavm;

import java.io.IOException;
import java.util.Iterator;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSString;

import com.ibm.wala.cfg.ControlFlowGraph;
import com.ibm.wala.classLoader.CallSiteReference;
import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.classLoader.NewSiteReference;
import com.ibm.wala.ipa.callgraph.CGNode;
import com.ibm.wala.ipa.callgraph.IAnalysisCacheView;
import com.ibm.wala.ipa.callgraph.impl.Everywhere;
import com.ibm.wala.ipa.callgraph.propagation.SSAContextInterpreter;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.ssa.DefUse;
import com.ibm.wala.ssa.IR;
import com.ibm.wala.ssa.IRView;
import com.ibm.wala.ssa.ISSABasicBlock;
import com.ibm.wala.ssa.SSAInstruction;
import com.ibm.wala.types.FieldReference;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.collections.EmptyIterator;

public class Analysis {

	@JSBody(params = { "s" }, script = "analyzer = s;")
	public static native void setAnalyzer(Analyzer s);


	@JSFunctor 
	public interface Analyzer extends JSObject {
		JSString analyze(JSString fileName, JSString contents) throws CancelException, ClassHierarchyException, IOException;
	}
	
	protected static final class TeaVMContextInterpreter implements SSAContextInterpreter {
		private final IAnalysisCacheView cache;
		
		public TeaVMContextInterpreter(IAnalysisCacheView cache2) {
			this.cache = cache2;
		}
		
		@Override
		public IR getIR(CGNode node) {
			return cache.getIR(node.getMethod(), Everywhere.EVERYWHERE);
		}

		@Override
		  public Iterator<CallSiteReference> iterateCallSites(CGNode N) {
		    IR ir = getIR(N);
		    if (ir == null) {
		      return EmptyIterator.instance();
		    } else {
		      return ir.iterateCallSites();
		    }
		  }

		@Override
		  public int getNumberOfStatements(CGNode node) {
		    IR ir = getIR(node);
		    return (ir == null) ? -1 : ir.getInstructions().length;
		  }

		@Override
		  public boolean recordFactoryType(CGNode node, IClass klass) {
		    return false;
		  }

		@Override
		  public ControlFlowGraph<SSAInstruction, ISSABasicBlock> getCFG(CGNode N) {
		    IR ir = getIR(N);
		    if (ir == null) {
		      return null;
		    } else {
		      return ir.getControlFlowGraph();
		    }
		  }

		@Override
		  public Iterator<NewSiteReference> iterateNewSites(CGNode N) {
		    IR ir = getIR(N);
		    if (ir == null) {
		      return EmptyIterator.instance();
		    } else {
		      return ir.iterateNewSites();
		    }
		  }

		@Override
		public Iterator<FieldReference> iterateFieldsRead(CGNode node) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Iterator<FieldReference> iterateFieldsWritten(CGNode node) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean understands(CGNode node) {
				return true;
		}

		@Override
		public DefUse getDU(CGNode node) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IRView getIRView(CGNode node) {
			return getIR(node);
		}
	}

}
