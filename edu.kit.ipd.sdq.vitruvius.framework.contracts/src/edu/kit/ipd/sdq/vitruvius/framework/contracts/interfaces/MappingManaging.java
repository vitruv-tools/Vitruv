package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.Collection;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;

public interface MappingManaging {
    void addMapping(Mapping mapping);

    Mapping getMapping(VURI... metamodelURIs);

    // @HideableComfort
    Mapping getMapping(Metamodel... metamodels);

    Collection<Mapping> getAllMappings(Metamodel metamodel);
}
