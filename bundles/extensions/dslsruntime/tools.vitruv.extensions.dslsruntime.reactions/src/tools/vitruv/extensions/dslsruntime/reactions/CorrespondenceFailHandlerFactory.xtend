package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.extensions.dslsruntime.reactions.correspondenceFailHandler.CorrespondenceFailException
import tools.vitruv.extensions.dslsruntime.reactions.correspondenceFailHandler.CorrespondenceFailDefaultDialog
import tools.vitruv.extensions.dslsruntime.reactions.correspondenceFailHandler.CorrespondenceFailCustomDialog
import tools.vitruv.extensions.dslsruntime.reactions.correspondenceFailHandler.CorrespondenceFailDoNothing

class CorrespondenceFailHandlerFactory {
	static def CorrespondenceFailHandler createExceptionHandler() {
		new CorrespondenceFailException
	}

	static def CorrespondenceFailHandler createDefaultUserDialogHandler(boolean abortEffect) {
		new CorrespondenceFailDefaultDialog(abortEffect)
	}

	static def CorrespondenceFailHandler createCustomUserDialogHandler(boolean abortEffect, String message) {
		new CorrespondenceFailCustomDialog(abortEffect, message)
	}

	static def CorrespondenceFailHandler createDoNothingHandler(boolean abortEffect) {
		new CorrespondenceFailDoNothing(abortEffect)
	}
}
