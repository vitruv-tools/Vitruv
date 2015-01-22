package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.internal;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.ModelProvidingDirtyMarker;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MappingManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MetamodelManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ViewTypeManaging;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

public class VSUMImplCustom extends VSUMImpl implements ModelProvidingDirtyMarker {

	public VSUMImplCustom(MetamodelManaging metamodelManaging,
			ViewTypeManaging viewTypeManaging, MappingManaging mappingManaging) {
		super(metamodelManaging, viewTypeManaging, mappingManaging);
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
