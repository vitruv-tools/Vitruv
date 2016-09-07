package tools.vitruvius.extensions.dslsruntime.response

import org.eclipse.emf.ecore.EObject
import tools.vitruvius.framework.userinteraction.UserInteracting

interface CorrespondenceFailHandler {
	/** 
	 * Returns whether the execution shall be continued or not 
	 */
	public def boolean handle(Iterable<? extends EObject> foundObjects, EObject sourceElement, Class<?> expectedType, UserInteracting userInteracting);
}