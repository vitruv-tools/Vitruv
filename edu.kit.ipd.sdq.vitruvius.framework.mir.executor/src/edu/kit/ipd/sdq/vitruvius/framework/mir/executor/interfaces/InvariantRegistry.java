package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces;

import java.util.Collection;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;

public interface InvariantRegistry {
	void addInvariant(Invariant invariant);
	void removeInvariant(Invariant invariant);
	
	Collection<Invariant> getInvariantsForEClassifier(EClassifier classifier);
	Invariant getInvariant(String name);
		
	InvariantEvaluationResult check(EObject object);
}
