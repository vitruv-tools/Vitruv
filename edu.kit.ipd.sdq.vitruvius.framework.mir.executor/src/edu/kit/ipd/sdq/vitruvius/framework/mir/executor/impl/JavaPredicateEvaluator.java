package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.WhenEvaluator;

public class JavaPredicateEvaluator implements WhenEvaluator {
	private Object dynamicWhen;
	
	public JavaPredicateEvaluator(Class<?> delegateClass) {
		try {
			this.dynamicWhen = delegateClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean check(EObject source, EObject target) {
		throw new UnsupportedOperationException();
	}
}
