package tools.vitruvius.framework.metamodel;

import java.util.Collection;

import tools.vitruvius.framework.metamodel.Metamodel;
import tools.vitruvius.framework.util.datatypes.VURI;

public interface MappingManaging {
    void addMapping(Mapping mapping);

    Mapping getMapping(VURI... metamodelURIs);

    // @HideableComfort
    Mapping getMapping(Metamodel... metamodels);

    Collection<Mapping> getAllMappings(Metamodel metamodel);
}
