package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects

import org.eclipse.emf.ecore.EObject
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.helper.PersistenceHelper.*;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.helper.CorrespondenceHelper
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState

class EffectElementCreate extends EffectElement implements PersistableEffectElement {
	private PersistenceInformation persistenceInformation; 
	private final String tag;
	
	new(EObject element, EObject correspondenceSource, ResponseExecutionState executionState, String tag) {
		super(element, correspondenceSource, executionState);
		this.tag = tag;
	}

	private def void initializeCorrespondence() {
		if (correspondenceSource == null || element == null) {
			return;
		}
		CorrespondenceHelper.addCorrespondence(blackboard.getCorrespondenceInstance(), 
				correspondenceSource, element, tag);
	}
	
	private def persistModel() {
		val persistencePath = persistenceInformation.pathSupplier.apply();
		if (correspondenceSource == null || element == null || persistencePath == null) {
			throw new IllegalArgumentException("correspondenceSource, element and persistancePath must be specified");
		}
		val _resourceURI = if (persistenceInformation.pathIsSourceRelative) {
			getURIFromSourceResourceFolder(correspondenceSource, persistencePath, blackboard);
		} else {
			getURIFromSourceProjectFolder(correspondenceSource, persistencePath, blackboard);
		}
		transformationResult.addRootEObjectToSave(element, VURI.getInstance(_resourceURI));
	}
	
	override public preProcess() {
		// Do nothing
	}
	
	override public postProcess() {
		initializeCorrespondence();
		if (persistenceInformation != null) {
			persistModel();
		}
	}
	
	override public updateTUIDs() {
		// Do nothing
	}
	
	override setPersistenceInformation(PersistenceInformation persistenceInformation) {
		this.persistenceInformation = persistenceInformation;
	}
	
}