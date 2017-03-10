package tools.vitruv.extensions.dslsruntime.reactions.effects

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.util.EcoreUtil
import org.apache.log4j.Logger
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.extensions.dslsruntime.reactions.ReactionElementsHandler

class ReactionElementsHandlerImpl implements ReactionElementsHandler {
	private static val logger = Logger.getLogger(ReactionElementsHandlerImpl);
	
	private final CorrespondenceModel correspondenceModel;
	private final ChangePropagationResult transformationResult;
	
	public new(CorrespondenceModel correspondenceModel, ChangePropagationResult transformationResult) {
		this.correspondenceModel = correspondenceModel;
		this.transformationResult = transformationResult;
	}
	
	override void addCorrespondenceBetween(EObject firstElement, EObject secondElement, String tag) {
		registerObjectUnderModification(firstElement);
		registerObjectUnderModification(secondElement);
		ReactionsCorrespondenceHelper.addCorrespondence(correspondenceModel, firstElement, secondElement, tag);
	}
	
	override void deleteObject(EObject element) {
		if (element == null) {
			return;
		}
		ReactionsCorrespondenceHelper.removeCorrespondencesOfObject(correspondenceModel, element);
		if (element.eContainer() == null) {
			if (element.eResource() != null) {
				logger.debug("Deleting root object: " + element);
				transformationResult.addVuriToDeleteIfNotNull(VURI.getInstance(element.eResource()));
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
		ReactionsCorrespondenceHelper.removeCorrespondencesBetweenElements(correspondenceModel, 
			firstElement, secondElement);
	}
	
	override registerObjectUnderModification(EObject element) {
		TuidManager.instance.registerObjectUnderModification(element);
	}
	
	override postprocessElements() {
		// Modifications are finished, so update the TUIDs
		TuidManager.instance.updateTuidsOfRegisteredObjects();
	}
	
}