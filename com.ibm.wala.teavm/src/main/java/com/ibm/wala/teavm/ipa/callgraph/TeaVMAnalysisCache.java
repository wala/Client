package com.ibm.wala.teavm.ipa.callgraph;

import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.ipa.callgraph.Context;
import com.ibm.wala.ipa.callgraph.IAnalysisCacheView;
import com.ibm.wala.ipa.callgraph.impl.Everywhere;
import com.ibm.wala.ssa.DefUse;
import com.ibm.wala.ssa.IR;
import com.ibm.wala.ssa.IRFactory;
import com.ibm.wala.ssa.SSAOptions;

public class TeaVMAnalysisCache implements IAnalysisCacheView {
		private final IRFactory<IMethod> irFactory;
		private final SSAOptions ssaOptions;
		
		public TeaVMAnalysisCache(IRFactory<IMethod> irFactory, SSAOptions ssaOptions) {
			this.irFactory = irFactory;
			this.ssaOptions = ssaOptions;
		}
		
		@Override
		public void invalidate(IMethod method, Context C) {
			// TODO Auto-generated method stub			
		}

		@Override
		public IRFactory<IMethod> getIRFactory() {
			return irFactory;
		}

		@Override
		public IR getIR(IMethod method) {
			return getIR(method,  Everywhere.EVERYWHERE);
		}

		@Override
		public DefUse getDefUse(IR ir) {
			return new DefUse(ir);
		}

		@Override
		public IR getIR(IMethod method, Context context) {
			return irFactory.makeIR(method, context, ssaOptions);
		}

		@Override
		public void clear() {
			// TODO Auto-generated method stub
			
		}
	}
