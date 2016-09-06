package edu.kit.ipd.sdq.vitruvius.applications.jmljava.vitruvius.changesynchronizer.extensions;

import edu.kit.ipd.sdq.vitruvius.framework.metamodel.Metamodel;

/**
 * A provider for meta models.
 */
public interface MetaModelProvider {

	/**
	 * @return The meta-model instance.
	 */
	Metamodel getMetaModel();

	/**
	 * Registers the necessary factories for the meta-model. This is only
	 * necessary if the models are not yet plugins in the Eclipse instance.
	 */
	void registerFactory();

}
