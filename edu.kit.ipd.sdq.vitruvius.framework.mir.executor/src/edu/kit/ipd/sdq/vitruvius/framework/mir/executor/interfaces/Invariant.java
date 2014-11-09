package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;

public interface Invariant {
	public class Parameter {
		final public String name;
		final public EClassifier type;
		public Parameter(String name, EClassifier type) {
			this.name = name;
			this.type = type;
		}
	}
	
	EClassifier getContextClassifier();
	String getName();
	List<Parameter> getSignature();
	
	InvariantEvaluationResult check(EObject context);
}
