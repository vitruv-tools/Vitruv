package tools.vitruv.applications.jmljava.vitruvius.changesynchronizer;

import tools.vitruv.framework.util.datatypes.VURI;

public interface ModelProvidingDirtyMarker {

	public void markAllModelInstancesDirty(VURI mmUri);
	
}
