package edu.kit.ipd.sdq.vitruvius.framework.mir.inferrer;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.xbase.XExpression;

import com.google.inject.Inject;

public class WithBlockInferrer implements IWithBlockInferrer {
	@Inject private ClosureProvider closureProvider;
	
	@Override
	public void infer(XExpression expression) {
		System.out.println("WithBlockInferrer for " + expression);
		
		Map<EPackage, TreeAppendableClosure> closuresMap = closureProvider.getAssignmentClosureMap(expression);
		
		for (TreeAppendableClosure closure : closuresMap.values()) {
			closure.addCode("/* inferred code from " + expression.toString() + "\n*/");
		}
	}

}
