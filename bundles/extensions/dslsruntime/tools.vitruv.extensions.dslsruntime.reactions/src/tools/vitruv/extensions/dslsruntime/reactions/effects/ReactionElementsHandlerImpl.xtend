package tools.vitruv.extensions.dslsruntime.reactions.effects

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import org.eclipse.emf.ecore.util.EcoreUtil
import org.apache.log4j.Logger
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.extensions.dslsruntime.reactions.ReactionElementsHandler

class ReactionElementsHandlerImpl implements ReactionElementsHandler {
	static extension TuidManager tm = TuidManager::instance
	static extension Logger = Logger.getLogger(ReactionElementsHandlerImpl)

	val CorrespondenceModel correspondenceModel;
	val ChangePropagationResult transformationResult;

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
		if (element === null) {
			return;
		}
		ReactionsCorrespondenceHelper.removeCorrespondencesOfObject(correspondenceModel, element);
		debug("Removing object " + element + " from container " + element.eContainer());
		EcoreUtil.remove(element);
		// If we delete an object, we have to update Tuids because Tuids of child elements 
		// may have to be resolved for removing correspondences as well and must therefore be up-to-date
		updateTuidsOfRegisteredObjects();
	}

	override void removeCorrespondenceBetween(EObject firstElement, EObject secondElement) {
		ReactionsCorrespondenceHelper.removeCorrespondencesBetweenElements(correspondenceModel, firstElement,
			secondElement);
	}

	override registerObjectUnderModification(EObject element) {
		if (element !== null) {
			tm.registerObjectUnderModification(element);
			if (element.eContainer !== null) {
				tm.registerObjectUnderModification(element.eContainer);
			}

		}
	}

	override postprocessElements() {
		// Modifications are finished, so update the Tuids
		updateTuidsOfRegisteredObjects();
	}

}
