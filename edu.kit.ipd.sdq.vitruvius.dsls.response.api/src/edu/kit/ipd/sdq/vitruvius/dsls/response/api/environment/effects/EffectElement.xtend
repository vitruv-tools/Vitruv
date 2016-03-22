package edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.effects

import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime.ResponseExecutionState
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.Loggable

abstract class EffectElement extends Loggable {
	protected final EObject element;
	protected final extension ResponseExecutionState _executionState;
	public final EObject correspondenceSource;
	
	public new(EObject element, EObject correspondenceSource, ResponseExecutionState executionState) {
		this.element = element;
		this.correspondenceSource = correspondenceSource;
		this._executionState = executionState;
	}
	
	protected def void preProcess();
	protected def void postProcess();
	protected def void updateTUIDs();
}