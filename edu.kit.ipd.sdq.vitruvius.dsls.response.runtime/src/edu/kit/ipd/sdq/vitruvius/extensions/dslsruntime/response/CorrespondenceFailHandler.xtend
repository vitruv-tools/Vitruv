package edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response

import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting

interface CorrespondenceFailHandler {
	/** 
	 * Returns whether the execution shall be continued or not 
	 */
	public def boolean handle(Iterable<? extends EObject> foundObjects, EObject sourceElement, Class<?> expectedType, UserInteracting userInteracting);
}