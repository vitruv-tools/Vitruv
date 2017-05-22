package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.extensions.dslsruntime.reactions.correspondenceFailHandler.CorrespondenceFailException
import tools.vitruv.extensions.dslsruntime.reactions.correspondenceFailHandler.CorrespondenceFailDefaultDialog
import tools.vitruv.extensions.dslsruntime.reactions.correspondenceFailHandler.CorrespondenceFailCustomDialog
import tools.vitruv.extensions.dslsruntime.reactions.correspondenceFailHandler.CorrespondenceFailDoNothing

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