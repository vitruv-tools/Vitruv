package edu.kit.ipd.sdq.vitruvius.applications.jmljava.vitruvius.changesynchronizer;

import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;

public interface ModelProvidingDirtyMarker {

	public void markAllModelInstancesDirty(VURI mmUri);
	
}
