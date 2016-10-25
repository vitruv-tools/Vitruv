package tools.vitruv.dsls.response.jvmmodel.classgenerators

import tools.vitruv.dsls.response.responseLanguage.RetrieveModelElement
import org.eclipse.emf.ecore.EClass

class ResponseElementsCompletionChecker {
	public def boolean isComplete(RetrieveModelElement retrieveElement) {
		return retrieveElement?.correspondenceSource?.code != null && retrieveElement.metaclass.complete;
	}
	
	public def boolean isComplete(EClass eClass) {
		return eClass?.instanceClass != null;
	}
	
}