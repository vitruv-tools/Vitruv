package edu.kit.ipd.sdq.vitruvius.applications.jmljava.vitruvius.changesynchronizer.extensions;

import edu.kit.ipd.sdq.vitruvius.framework.change.processing.Change2CommandTransforming ;

/**
 * A provider for transformation executings. 
 */
public interface Change2CommandTransformingProvider {

	/**
	 * @return A set of transformation executings.
	 */
    Iterable<Change2CommandTransforming> getEMFModelTransformationExecutings();
    
}
