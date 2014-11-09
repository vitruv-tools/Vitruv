package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Invariant;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.InvariantEvaluationResult;

public class InvariantEvaluationResultImpl implements InvariantEvaluationResult {
	private final boolean violated;
	private final Invariant invariant;
	private final Map<String, EObject> violationState;
	
	
	public InvariantEvaluationResultImpl(Invariant invariant, boolean violated,
			Map<String, EObject> violationState) {
		this.invariant = invariant;
		this.violated = violated;
		this.violationState = violationState;
	}

	public InvariantEvaluationResultImpl(Invariant invariant, boolean violated) {
		this.invariant = invariant;
		this.violated = violated;
		this.violationState = null;
	}
	
	public InvariantEvaluationResultImpl(boolean violated) {
		this.invariant = null;
		this.violated = violated;
		this.violationState = null;
	}

	@Override
	public boolean isViolated() {
		return violated;
	}

	@Override
	public Invariant getInvariant() {
		return invariant;
	}

	@Override
	public Map<String, EObject> getViolationState() {
		return violationState;
	}

}
