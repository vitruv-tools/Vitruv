package tools.vitruvius.extensions.dslsruntime.response

import tools.vitruvius.extensions.dslsruntime.response.correspondenceFailHandler.CorrespondenceFailException
import tools.vitruvius.extensions.dslsruntime.response.correspondenceFailHandler.CorrespondenceFailDefaultDialog
import tools.vitruvius.extensions.dslsruntime.response.correspondenceFailHandler.CorrespondenceFailCustomDialog
import tools.vitruvius.extensions.dslsruntime.response.correspondenceFailHandler.CorrespondenceFailDoNothing

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