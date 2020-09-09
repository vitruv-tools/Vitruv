package tools.vitruv.dsls.reactions.codegen.classgenerators

import tools.vitruv.dsls.reactions.reactionsLanguage.RetrieveModelElement
import org.eclipse.emf.ecore.EClass

class ReactionElementsCompletionChecker {
	def boolean isComplete(RetrieveModelElement retrieveElement) {
		return retrieveElement?.correspondenceSource?.code !== null && retrieveElement.metaclass.complete;
	}
	
	def boolean isComplete(EClass eClass) {
		return eClass?.instanceClass !== null;
	}
	
}