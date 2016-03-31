package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.correspondenceFailHandler.CorrespondenceFailException
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.correspondenceFailHandler.CorrespondenceFailDefaultDialog
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.correspondenceFailHandler.CorrespondenceFailCustomDialog
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.correspondenceFailHandler.CorrespondenceFailDoNothing

class CorrespondenceFailHandlerFactory {
	static def CorrespondenceFailHandler createExceptionHandler() {
		return new CorrespondenceFailException();
	}
	
	static def CorrespondenceFailHandler createDefaultUserDialogHandler(boolean abortEffect) {
		return new CorrespondenceFailDefaultDialog(abortEffect);
	}
	
	static def CorrespondenceFailHandler createCustomUserDialogHandler(boolean abortEffect, String message) {
		return new CorrespondenceFailCustomDialog(abortEffect, message);	
	}
	
	static def CorrespondenceFailHandler createDoNothingHandler(boolean abortEffect) {
		return new CorrespondenceFailDoNothing(abortEffect);
	}
}