 package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces;

import java.util.Map;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;


public interface Response {
	ResponseAction getResponseAction();
	EClassifier getContextClassifier();
	String getInvariantName();
	
	void invoke(EObject context, Map<String, EObject> parameters);
}
