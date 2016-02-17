package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondingModelElementSpecification
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.ModelElement

class ResponseElementsCompletionChecker {
	public def boolean isComplete(CorrespondingModelElementSpecification spec) {
		return !spec?.name.nullOrEmpty && spec?.correspondenceSource?.code != null && spec?.elementType.complete;
	}
	
	public def boolean isComplete(ModelElement element) {
		return element?.element?.instanceClass != null;
	}
	
}