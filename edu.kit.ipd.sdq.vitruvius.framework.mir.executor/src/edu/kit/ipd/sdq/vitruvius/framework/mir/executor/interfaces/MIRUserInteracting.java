package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces;

import org.eclipse.emf.common.util.URI;

/**
 * A user interacting for MIR Change2CommandTransformings. 
 * @author Dominik Werle
 */
public interface MIRUserInteracting {
	/**
	 * Ask for a resource.
	 * @param message the message to display to the user.
	 * @return the URI of the resource.
	 */
	URI askForNewResource(String message);
}
