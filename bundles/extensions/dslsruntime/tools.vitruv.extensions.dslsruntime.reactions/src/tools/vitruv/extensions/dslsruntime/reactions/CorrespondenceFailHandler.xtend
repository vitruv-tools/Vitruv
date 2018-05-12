package tools.vitruv.extensions.dslsruntime.reactions

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.userinteraction.UserInteractor

interface CorrespondenceFailHandler {
	/** 
	 * Returns whether the execution shall be continued or not 
	 */
	public def boolean handle(Iterable<? extends EObject> foundObjects, EObject sourceElement, Class<?> expectedType, UserInteractor userInteractor);
}