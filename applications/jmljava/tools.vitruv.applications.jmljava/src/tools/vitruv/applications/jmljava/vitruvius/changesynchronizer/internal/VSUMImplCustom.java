package tools.vitruv.applications.jmljava.vitruvius.changesynchronizer.internal;

import tools.vitruv.framework.metamodel.ModelInstance;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.applications.jmljava.vitruvius.changesynchronizer.ModelProvidingDirtyMarker;
import tools.vitruv.framework.metamodel.MappingManaging;
import tools.vitruv.framework.metamodel.MetamodelManaging;
import tools.vitruv.framework.vsum.VSUMImpl;

public class VSUMImplCustom extends VSUMImpl implements ModelProvidingDirtyMarker {

	public VSUMImplCustom(MetamodelManaging metamodelManaging,
			MappingManaging mappingManaging) {
		super(metamodelManaging, mappingManaging);
	}

	@Override
	public void markAllModelInstancesDirty(VURI mmUri) {
		for (ModelInstance mi : modelInstances.values()) {
			if (mmUri.equals(mi.getMetamodeURI())) {
				mi.getResource().setModified(true);
			}
		}
	}
}
