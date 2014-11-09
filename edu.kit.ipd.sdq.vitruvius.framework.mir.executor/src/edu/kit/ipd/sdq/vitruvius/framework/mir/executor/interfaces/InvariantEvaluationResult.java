package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * Encapsulates the result of the application of an {@link Invariant} 
 * @author Dominik Werle
 */
public interface InvariantEvaluationResult {
	public boolean isViolated();
	
	/**
	 * Returns the invariant that created this result.
	 * @return the invariant that created this result
	 */
	public Invariant getInvariant();
	
	
	/**
	 * Gets a mapping of variable identifiers to EObjects that
	 * lead to the violation of the invariant.
	 * 
	 * There should be at least an entry for each element of the
	 * signature of the corresponding invariant.
	 * 
	 * @return the state of the invariant at the time 
	 */
	public Map<String, EObject> getViolationState();
}
