package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel.classgenerators

import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.ModelElement
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.RetrieveModelElement

class ResponseElementsCompletionChecker {
	public def boolean isComplete(RetrieveModelElement retrieveElement) {
		return retrieveElement?.correspondenceSource?.code != null && retrieveElement?.element.complete;
	}
	
	public def boolean isComplete(ModelElement element) {
		return element?.element?.instanceClass != null;
	}
	
}