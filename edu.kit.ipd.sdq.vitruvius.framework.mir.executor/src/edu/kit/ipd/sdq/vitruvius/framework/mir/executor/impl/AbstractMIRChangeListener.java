package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.Collection;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateEObject;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.EChangeListener;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Invariant;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.InvariantEvaluationResult;
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
	public void onEChange(EChange change) {
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
		}
	}

}
