package tools.vitruv.framework.remote.server;

import tools.vitruv.framework.vsum.VirtualModel;

/**
 * Interface for virtual model initialization.
 */
@FunctionalInterface
public interface VirtualModelInitializer {	
	
	/**
	 * Initializes the virtual model and returns it.
	 * 
	 * @return the initialized model.
	 */
	VirtualModel init();
}
