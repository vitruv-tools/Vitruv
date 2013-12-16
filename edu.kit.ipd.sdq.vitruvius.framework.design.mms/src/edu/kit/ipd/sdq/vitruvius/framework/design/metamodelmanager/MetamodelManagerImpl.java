package edu.kit.ipd.sdq.vitruvius.framework.design.metamodelmanager;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.URI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MetamodelManaging;

public class MetamodelManagerImpl implements MetamodelManaging {

	private final MetamodelManaging metamodelManaging;

	public MetamodelManagerImpl(MetamodelManaging metamodelManaging) {
		this.metamodelManaging = metamodelManaging;
	}

	@Override
	public void addMetamodel(Metamodel metamodel) {
		metamodelManaging.addMetamodel(metamodel);
	}

	@Override
	public Metamodel getMetamodel(URI uri) {
		return metamodelManaging.getMetamodel(uri);
	}

}
