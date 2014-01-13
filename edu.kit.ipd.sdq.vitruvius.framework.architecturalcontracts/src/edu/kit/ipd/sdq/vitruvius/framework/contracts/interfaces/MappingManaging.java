package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public interface MappingManaging {
	void addMapping(Mapping mapping);
	Mapping getMapping(VURI... metamodelURIs);
}
