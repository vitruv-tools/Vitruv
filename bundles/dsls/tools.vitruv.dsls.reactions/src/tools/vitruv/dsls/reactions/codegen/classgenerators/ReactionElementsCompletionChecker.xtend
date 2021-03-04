package tools.vitruv.dsls.reactions.codegen.classgenerators

import tools.vitruv.dsls.reactions.language.RetrieveModelElement
import org.eclipse.emf.ecore.EClass

class ReactionElementsCompletionChecker {
	def boolean isComplete(RetrieveModelElement retrieveElement) {
		return retrieveElement.correspondenceSource?.code !== null && retrieveElement.elementType?.metaclass.complete;
	}
	
	def boolean isComplete(EClass eClass) {
		return eClass?.instanceClass !== null;
	}
	
}