package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.URI;

public interface MappingManaging {
	void addMapping(Mapping mapping);
	Mapping getMapping(URI... metamodelURIs);
}
