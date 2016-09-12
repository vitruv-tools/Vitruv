package tools.vitruv.extensions.dslsruntime.response.effects

import tools.vitruv.extensions.dslsruntime.response.ResponseElementStatesHandler
import java.util.Map
import org.eclipse.emf.ecore.EObject
import java.util.HashMap
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.command.TransformationResult

class ResponseElementStatesHandlerImpl implements ResponseElementStatesHandler {
	private final Map<EObject, AbstractResponseElementState> elementStates;
	private final CorrespondenceModel correspondenceModel;
	private final TransformationResult transformationResult;
	
	public new(CorrespondenceModel correspondenceModel, TransformationResult transformationResult) {
		this.correspondenceModel = correspondenceModel;
		this.elementStates = new HashMap<EObject, AbstractResponseElementState>();
		this.transformationResult = transformationResult;
	}
	
	override void addCorrespondenceBetween(EObject firstElement, EObject secondElement, String tag) {
		if (!elementStates.containsKey(firstElement)) {
			elementStates.put(firstElement, new RetrieveResponseElementState(firstElement, correspondenceModel, transformationResult));
		}
		this.elementStates.get(firstElement).addCorrespondingElement(secondElement, tag);
	}
	
	override void deleteObject(EObject element) {
		this.elementStates.get(element).delete();
	}
	
	override void removeCorrespondenceBetween(EObject firstElement, EObject secondElement) {
		if (!elementStates.containsKey(firstElement)) {
			elementStates.put(firstElement, new RetrieveResponseElementState(firstElement, correspondenceModel, transformationResult));
		}
		this.elementStates.get(firstElement).removeCorrespondingElement(secondElement);
	}
	
	override initializeCreateElementState(EObject element) {
		this.elementStates.put(element, new CreateResponseElementState(element, correspondenceModel, transformationResult));
	}
	
	override initializeRetrieveElementState(EObject element) {
		this.elementStates.put(element, new RetrieveResponseElementState(element, correspondenceModel, transformationResult));
	}
	
	override preprocessElementStates() {
		for (state : elementStates.values) {
			state.preprocess();
		}
	}
	
	override postprocessElementStates() {
		// Modifications are finished, so update the TUIDs
		TuidManager.instance.updateTuidsOfRegisteredObjects();
		
		for (state : elementStates.values) {
			state.postprocess();
		}
	}
}