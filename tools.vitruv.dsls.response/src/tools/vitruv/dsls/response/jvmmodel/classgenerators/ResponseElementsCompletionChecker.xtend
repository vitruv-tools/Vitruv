package tools.vitruv.dsls.response.jvmmodel.classgenerators

import tools.vitruv.dsls.mirbase.mirBase.ModelElement
import tools.vitruv.dsls.response.responseLanguage.RetrieveModelElement

class ResponseElementsCompletionChecker {
	public def boolean isComplete(RetrieveModelElement retrieveElement) {
		return retrieveElement?.correspondenceSource?.code != null && retrieveElement?.element.complete;
	}
	
	public def boolean isComplete(ModelElement element) {
		return element?.element?.instanceClass != null;
	}
	
}