package edu.kit.ipd.sdq.vitruvius.applications.jmljava.vitruvius.changesynchronizer.internal;

import edu.kit.ipd.sdq.vitruvius.framework.metamodel.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.vitruvius.changesynchronizer.ModelProvidingDirtyMarker;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.MappingManaging;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.MetamodelManaging;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

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
