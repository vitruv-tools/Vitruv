package tools.vitruvius.applications.jmljava.vitruvius.changesynchronizer.internal;

import tools.vitruvius.framework.metamodel.ModelInstance;
import tools.vitruvius.framework.util.datatypes.VURI;
import tools.vitruvius.applications.jmljava.vitruvius.changesynchronizer.ModelProvidingDirtyMarker;
import tools.vitruvius.framework.metamodel.MappingManaging;
import tools.vitruvius.framework.metamodel.MetamodelManaging;
import tools.vitruvius.framework.vsum.VSUMImpl;

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
