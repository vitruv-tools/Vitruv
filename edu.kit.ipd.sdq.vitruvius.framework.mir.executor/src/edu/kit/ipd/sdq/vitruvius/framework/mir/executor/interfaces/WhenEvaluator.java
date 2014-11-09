package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces;

import org.eclipse.emf.ecore.EObject;

public interface WhenEvaluator {
	public boolean check(EObject source, EObject target);
}
