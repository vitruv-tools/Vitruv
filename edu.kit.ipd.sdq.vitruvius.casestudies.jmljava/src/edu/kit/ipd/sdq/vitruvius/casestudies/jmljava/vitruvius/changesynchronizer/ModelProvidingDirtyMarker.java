package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public interface ModelProvidingDirtyMarker {

	public void markAllModelInstancesDirty(VURI mmUri);
	
}
