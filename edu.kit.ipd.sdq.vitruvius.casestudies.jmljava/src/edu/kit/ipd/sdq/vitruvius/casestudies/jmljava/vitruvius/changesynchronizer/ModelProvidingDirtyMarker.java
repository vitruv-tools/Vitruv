package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer;

import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;

public interface ModelProvidingDirtyMarker {

	public void markAllModelInstancesDirty(VURI mmUri);
	
}
