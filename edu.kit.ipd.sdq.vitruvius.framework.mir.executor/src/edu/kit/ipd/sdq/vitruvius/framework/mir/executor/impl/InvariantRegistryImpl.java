package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Invariant;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.InvariantEvaluationResult;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.InvariantRegistry;

public class InvariantRegistryImpl implements InvariantRegistry {
	private List<Invariant> invariants;
	
	@Override
	public void addInvariant(Invariant invariant) {
		invariants.add(invariant);
	}

	@Override
	public void removeInvariant(Invariant invariant) {
		invariants.remove(invariant);
	}

	@Override
	public InvariantEvaluationResult check(EObject object) {
		List<Invariant> invariants = getInvariantsForEClassifier(object.eClass());
		for (Invariant inv : invariants) {
			InvariantEvaluationResult result = inv.check(object);
			if (result.isViolated())
				return result;
		}
		
		return new InvariantEvaluationResultImpl(false);
	}

	@Override
	public List<Invariant> getInvariantsForEClassifier(EClassifier classifier) {
		// TODO: inheritance
		List<Invariant> resultCollection = new ArrayList<Invariant>();
		for (Invariant i : invariants)
			if (i.getContextClassifier().equals(classifier))
				resultCollection.add(i);
		
		return resultCollection;
	}

	@Override
	public Invariant getInvariant(String name) {
		for (Invariant i : invariants)
			if (i.getName().equals(name))
				return i;
		
		return null;
	}

}
