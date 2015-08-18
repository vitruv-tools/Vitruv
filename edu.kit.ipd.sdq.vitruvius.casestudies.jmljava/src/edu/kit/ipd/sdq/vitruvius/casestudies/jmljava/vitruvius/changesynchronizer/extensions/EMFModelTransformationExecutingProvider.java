package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.extensions;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;

/**
 * A provider for transformation executings. 
 */
public interface EMFModelTransformationExecutingProvider {

	/**
	 * @return A set of transformation executings.
	 */
    Iterable<Change2CommandTransforming> getEMFModelTransformationExecutings();
    
}
