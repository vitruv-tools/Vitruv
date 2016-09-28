package tools.vitruv.extensions.dslsruntime.response.effects

import tools.vitruv.extensions.dslsruntime.response.ResponseElementStatesHandler
import java.util.Map
import org.eclipse.emf.ecore.EObject
import java.util.HashMap
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.command.TransformationResult
import tools.vitruv.extensions.dslsruntime.response.helper.ResponseCorrespondenceHelper
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.util.EcoreUtil
import org.apache.log4j.Logger

class ResponseElementStatesHandlerImpl implements ResponseElementStatesHandler {
	private static val logger = Logger.getLogger(ResponseElementStatesHandlerImpl);
	
	private final Map<EObject, AbstractResponseElementState> elementStates;
	private final CorrespondenceModel correspondenceModel;
	private final TransformationResult transformationResult;
	
	public new(CorrespondenceModel correspondenceModel, TransformationResult transformationResult) {
		this.correspondenceModel = correspondenceModel;
		this.elementStates = new HashMap<EObject, AbstractResponseElementState>();
		this.transformationResult = transformationResult;
	}
	
	override void addCorrespondenceBetween(EObject firstElement, EObject secondElement, String tag) {
//		ResponseCorrespondenceHelper.addCorrespondence(correspondenceModel, 
//			firstElement, secondElement, tag);
		if (!elementStates.containsKey(firstElement)) {
			elementStates.put(firstElement, new RetrieveResponseElementState(firstElement, correspondenceModel));
		}
		this.elementStates.get(firstElement).addCorrespondingElement(secondElement, tag);
	}
	
	override void deleteObject(EObject element) {
		if (element == null) {
			return;
		}
		ResponseCorrespondenceHelper.removeCorrespondencesOfObject(correspondenceModel, element, null);
		if (element.eContainer() == null) {
			if (element.eResource() != null) {
				logger.debug("Deleting root object: " + element);
				transformationResult.addVURIToDeleteIfNotNull(VURI.getInstance(element.eResource()));
			} else {
				logger.warn("The element to delete was already removed: " + element);
			}
		} else {
			logger.debug("Removing non-root object: " + element);
			EcoreUtil.remove(element);
		}
		// If we delete an object, we have to update TUIDs because TUIDs of child elements 
		// may have to be resolved for removing correspondences as well and must therefore be up-to-date
		TuidManager.instance.updateTuidsOfRegisteredObjects();
	}
	
	override void removeCorrespondenceBetween(EObject firstElement, EObject secondElement) {
		ResponseCorrespondenceHelper.removeCorrespondencesBetweenElements(correspondenceModel, 
			firstElement, null, secondElement, null);
	}
	
	override initializeCreateElementState(EObject element) {
		this.elementStates.put(element, new CreateResponseElementState(element, correspondenceModel));
	}
	
	override initializeRetrieveElementState(EObject element) {
		this.elementStates.put(element, new RetrieveResponseElementState(element, correspondenceModel));
	}
	
	override postprocessElementStates() {
		// Modifications are finished, so update the TUIDs
		TuidManager.instance.updateTuidsOfRegisteredObjects();
		
		for (state : elementStates.values) {
			state.postprocess();
		}
	}
}