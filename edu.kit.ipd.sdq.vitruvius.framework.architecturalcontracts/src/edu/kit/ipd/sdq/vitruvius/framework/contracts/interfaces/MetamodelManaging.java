package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.URI;

public interface MetamodelManaging {
	void addMetamodel(Metamodel metamodel);
	Metamodel getMetamodel(URI uri);
	// TODO decide whether MetamodelManaging.remove(String uri) is needed
}
