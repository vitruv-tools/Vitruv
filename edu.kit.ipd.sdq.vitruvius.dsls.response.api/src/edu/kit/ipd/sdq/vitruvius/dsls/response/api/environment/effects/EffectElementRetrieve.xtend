package edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.effects

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime.ResponseExecutionState
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime.ResponseRuntimeHelper.*;
import org.eclipse.emf.ecore.util.EcoreUtil
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.effects.PersistableEffectElement.PersistenceInformation

class EffectElementRetrieve extends EffectElement implements PersistableEffectElement {
	private final TUID oldTUID;
	private PersistenceInformation moveInformation;
	
	new(EObject element, EObject correspondenceSource, ResponseExecutionState executionState) {
		super(element, correspondenceSource, executionState);
		this.oldTUID = blackboard.correspondenceInstance.calculateTUIDFromEObject(element);
	}
	
	private def void updateTUID() {
		if (element != null) {
			blackboard.correspondenceInstance.updateTUID(oldTUID, element);
		}
	}
	
	protected def renameModel() {
		val movePath = moveInformation.pathSupplier.apply();
		if (correspondenceSource.eResource() == null) {
			throw new IllegalStateException("Element must be in a resource to determine the containing project.");			
		}
		val sourceRoot = element.modelRoot;
		val oldVURI = if (sourceRoot.eResource() != null) {
			VURI.getInstance(sourceRoot.eResource());
		}
		val newVURI = if (moveInformation.pathIsSourceRelative) {
			// TODO HK This can eventually go wrong, if the renamed model is not persisted yet
			VURI.getInstance(getURIFromSourceResourceFolder(element, movePath, blackboard));
		} else {
			VURI.getInstance(getURIFromSourceProjectFolder(correspondenceSource, movePath, blackboard));
		}
		EcoreUtil.remove(sourceRoot);
		transformationResult.addRootEObjectToSave(sourceRoot, newVURI);
		transformationResult.addVURIToDeleteIfNotNull(oldVURI);
	}
	
	
	override protected preProcess() {
		// Do nothing
	}
	
	override protected postProcess() {
		if (moveInformation != null) {
			renameModel();
		}
	}
	
	override protected updateTUIDs() {
		updateTUID();
	}
	
	override setPersistenceInformation(PersistenceInformation moveInformation) {
		this.moveInformation = moveInformation;
	}
	
}