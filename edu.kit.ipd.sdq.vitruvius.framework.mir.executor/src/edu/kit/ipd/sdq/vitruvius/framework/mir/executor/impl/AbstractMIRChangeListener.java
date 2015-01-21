package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.EChangeListener;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Invariant;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.InvariantRegistry;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Response;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.ResponseRegistry;

public abstract class AbstractMIRChangeListener implements EChangeListener {
	private ResponseRegistry responseRegistry;
	private InvariantRegistry invariantRegistry;
	
	public AbstractMIRChangeListener() {
		responseRegistry = new ResponseRegistryImpl();
		invariantRegistry = new InvariantRegistryImpl();
	}
	
	public void addResponse(Response response) {
		responseRegistry.addResponse(response);
	}
	
	public void addInvariant(Invariant invariant) {
		invariantRegistry.addInvariant(invariant);
	}
	
	protected abstract void setup();
	
	
	@Override
	public List<EChange> handleChange(EChange change) {
	/*
		if (change instanceof CreateNonRootEObjectSingle<?>) {
			CreateNonRootEObjectSingle createEObject = (CreateNonRootEObjectSingle<?>) change;
		} else if (change instanceof UpdateEAttribute<?>) {
			UpdateEAttribute<?> update = (UpdateEAttribute<?>) change;
			
			EObject context = update.getNewAffectedEObject();
			EClassifier classifier = context.eClass();
			Collection<Response> responses = responseRegistry.getResponsesFor(classifier);
			
			for (Response r : responses) {
				Invariant inv = invariantRegistry.getInvariant(r.getInvariantName());
				InvariantEvaluationResult evaluationResult = inv.check(context);
				if (evaluationResult.isViolated()) {
					r.invoke(context, evaluationResult.getViolationState());
					break;
				}
			}
		}*/
		
		return null;
	}

}
