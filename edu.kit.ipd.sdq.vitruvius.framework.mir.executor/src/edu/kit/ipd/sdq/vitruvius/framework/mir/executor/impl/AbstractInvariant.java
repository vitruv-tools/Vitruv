package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Invariant;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.InvariantEvaluationResult;

/**
 * @author Dominik Werle
 */
public abstract class AbstractInvariant implements Invariant {

	private EClassifier contextClassifier;
	private String name;
	private List<Parameter> signature;
	
	private Map<String, EObject> invariantState;
	private boolean invariantViolated;
	
	public AbstractInvariant(EClassifier contextClassifier, String name,
			List<Parameter> signature) {
		super();
		this.contextClassifier = contextClassifier;
		this.name = name;
		this.signature = signature;
	}

	@Override
	public EClassifier getContextClassifier() {
		return contextClassifier;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Parameter> getSignature() {
		return signature;
	}

	protected void setContextClassifier(EClassifier contextClassifier) {
		this.contextClassifier = contextClassifier;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected void setSignature(List<Parameter> signature) {
		this.signature = signature;
	}

	@Override
	public InvariantEvaluationResult check(EObject context) {
		invariantViolated = false;
		invariantState = new HashMap<>();
		if (!getContextClassifier().isInstance(context))
			throw new IllegalArgumentException("Argument is not instance of context type");
		else {
			doApply(context);
			
			return new InvariantEvaluationResultImpl(this, invariantViolated, invariantState);
		}
	}

	protected void putInState(String name, EObject object) {
		this.invariantState.put(name, object);
	}
	
	protected void removeFromState(String name) {
		this.invariantState.remove(name);
	}
	
	protected void setViolated(boolean isViolated) {
		this.invariantViolated = isViolated;
	}
	
	/**
	 * This method must be implemented by subclasses to perform the actual check.
	 * The argument is an instance of the context classifier.
	 * <p>
	 * The implementation can use the methods
	 * <ul>
	 * <li>{@link #putInState(String, EObject)},
	 * <li>{@link #removeFromState(String)} and
	 * <li>{@link #setViolated(boolean)} 
	 * </ul>
	 * to set up the result that is returned by the check.
	 * @param object the object to check. Has already been checked for conformance to the context classifier.
	 */
	protected abstract void doApply(EObject object);
}