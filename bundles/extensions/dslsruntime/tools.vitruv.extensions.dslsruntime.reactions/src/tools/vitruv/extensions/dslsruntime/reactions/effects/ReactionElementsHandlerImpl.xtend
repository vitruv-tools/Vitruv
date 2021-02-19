package tools.vitruv.extensions.dslsruntime.reactions.effects

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import org.eclipse.emf.ecore.util.EcoreUtil
import org.apache.log4j.Logger
import tools.vitruv.extensions.dslsruntime.reactions.ReactionElementsHandler

class ReactionElementsHandlerImpl implements ReactionElementsHandler {
	static val logger = Logger.getLogger(ReactionElementsHandlerImpl);
	
	final CorrespondenceModel correspondenceModel;
	
	new(CorrespondenceModel correspondenceModel) {
		this.correspondenceModel = correspondenceModel;
	}
	
	override void addCorrespondenceBetween(EObject firstElement, EObject secondElement, String tag) {
		ReactionsCorrespondenceHelper.addCorrespondence(correspondenceModel, firstElement, secondElement, tag);
	}
	
	override void deleteObject(EObject element) {
		if (element === null) {
			return;
		}
		ReactionsCorrespondenceHelper.removeCorrespondencesOfObject(correspondenceModel, element);
		logger.debug("Removing object " + element + " from container " + element.eContainer());
		EcoreUtil.remove(element);
	}
	
	override void removeCorrespondenceBetween(EObject firstElement, EObject secondElement, String tag) {
		ReactionsCorrespondenceHelper.removeCorrespondencesBetweenElements(correspondenceModel, 
			firstElement, secondElement, tag);
	}
	
}