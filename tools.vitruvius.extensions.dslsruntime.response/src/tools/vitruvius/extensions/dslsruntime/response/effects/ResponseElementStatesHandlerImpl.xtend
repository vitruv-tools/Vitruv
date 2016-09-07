package tools.vitruvius.extensions.dslsruntime.response.effects

import tools.vitruvius.extensions.dslsruntime.response.ResponseElementStatesHandler
import java.util.Map
import org.eclipse.emf.ecore.EObject
import java.util.HashMap
import tools.vitruvius.framework.correspondence.CorrespondenceModel

class ResponseElementStatesHandlerImpl implements ResponseElementStatesHandler {
	private final Map<EObject, AbstractResponseElementState> elementStates;
	private final CorrespondenceModel correspondenceModel;
	
	public new(CorrespondenceModel correspondenceModel) {
		this.correspondenceModel = correspondenceModel;
		this.elementStates = new HashMap<EObject, AbstractResponseElementState>();
	}
	
	override void addCorrespondenceBetween(EObject firstElement, EObject secondElement, String tag) {
		if (!elementStates.containsKey(firstElement)) {
			elementStates.put(firstElement, new RetrieveResponseElementState(firstElement, correspondenceModel));
		}
		this.elementStates.get(firstElement).addCorrespondingElement(secondElement, tag);
	}
	
	override void deleteObject(EObject element) {
		this.elementStates.get(element).delete();
	}
	
	override void removeCorrespondenceBetween(EObject firstElement, EObject secondElement) {
		if (!elementStates.containsKey(firstElement)) {
			elementStates.put(firstElement, new RetrieveResponseElementState(firstElement, correspondenceModel));
		}
		this.elementStates.get(firstElement).removeCorrespondingElement(secondElement);
	}
	
	override initializeCreateElementState(EObject element) {
		this.elementStates.put(element, new CreateResponseElementState(element, correspondenceModel));
	}
	
	override initializeRetrieveElementState(EObject element) {
		this.elementStates.put(element, new RetrieveResponseElementState(element, correspondenceModel));
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