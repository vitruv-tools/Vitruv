package tools.vitruvius.applications.jmljava.vitruvius.changesynchronizer.extensions;

import tools.vitruvius.framework.metamodel.Mapping;
import tools.vitruvius.framework.metamodel.MetamodelManaging;

/**
 * Provider for mappings between meta-models.
 */
public interface MappingProvider {

	/**
	 * Creates the mappings for meta-models contained in the meta-model
	 * managing. A mapping is always bidirectional, so only one pair per
	 * bidirectional relation is necessary. Please keep in mind that the order
	 * of the meta-models in the set has an influence on other parts of the
	 * framework (e.g. the correspondence instance):
	 * 
	 * @param metaModelManaging
	 *            An initialized implementation of a meta-model managing.
	 * @return A set of mappings.
	 */
	Iterable<Mapping> getMappings(MetamodelManaging metaModelManaging);

}
