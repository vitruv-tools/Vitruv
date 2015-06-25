package edu.kit.ipd.sdq.vitruvius.framework.mir.inferrer;

import org.eclipse.xtext.xbase.XExpression;

import com.google.inject.Inject;

public class InvariantInferrer implements InvariantInferrerProviding {
	/** used to get closures to append to for an XExpression. */ 
	@Inject private ClosureProvider closureProvider;
	
	@Override
	public void infer(XExpression expression) {
		TreeAppendableClosure invariantClosure = closureProvider.getInvariantClosure(expression);

		// analyse expression
		invariantClosure.addCode("...");
	}

}
