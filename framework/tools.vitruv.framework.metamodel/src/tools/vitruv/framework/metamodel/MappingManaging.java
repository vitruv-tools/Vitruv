package tools.vitruv.framework.metamodel;

import java.util.Collection;

import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.util.datatypes.VURI;

public interface MappingManaging {
    void addMapping(Mapping mapping);

    Mapping getMapping(VURI... metamodelURIs);

    // @HideableComfort
    Mapping getMapping(Metamodel... metamodels);

    Collection<Mapping> getAllMappings(Metamodel metamodel);
}
