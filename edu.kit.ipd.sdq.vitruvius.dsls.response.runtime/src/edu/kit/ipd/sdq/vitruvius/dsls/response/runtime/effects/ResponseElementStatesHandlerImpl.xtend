package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseElementStatesHandler
import java.util.Map
import org.eclipse.emf.ecore.EObject
import java.util.HashMap
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModel

class ResponseElementStatesHandlerImpl implements ResponseElementStatesHandler {
	private final Map<EObject, AbstractResponseElementState> elementStates;
	private final CorrespondenceModel correspondenceInstance;
	
	public new(CorrespondenceModel correspondenceInstance) {
		this.correspondenceInstance = correspondenceInstance;
		this.elementStates = new HashMap<EObject, AbstractResponseElementState>();
	}
	
	override void addCorrespondenceBetween(EObject firstElement, EObject secondElement, String tag) {
		if (!elementStates.containsKey(firstElement)) {
			elementStates.put(firstElement, new RetrieveResponseElementState(firstElement, correspondenceInstance));
		}
		this.elementStates.get(firstElement).addCorrespondingElement(secondElement, tag);
	}
	
	override void deleteObject(EObject element) {
		this.elementStates.get(element).delete();
	}
	
	override void removeCorrespondenceBetween(EObject firstElement, EObject secondElement) {
		if (!elementStates.containsKey(firstElement)) {
			elementStates.put(firstElement, new RetrieveResponseElementState(firstElement, correspondenceInstance));
		}
		this.elementStates.get(firstElement).removeCorrespondingElement(secondElement);
	}
	
	override initializeCreateElementState(EObject element) {
		this.elementStates.put(element, new CreateResponseElementState(element, correspondenceInstance));
	}
	
	override initializeRetrieveElementState(EObject element) {
		this.elementStates.put(element, new RetrieveResponseElementState(element, correspondenceInstance));
	}
	
	override preprocessElementStates() {
		for (state : elementStates.values) {
			state.preprocess();
		}
	}
	
	override postprocessElementStates() {
		for (state : elementStates.values) {
			state.postprocess();
			state.updateTUID();
		}
	}
}