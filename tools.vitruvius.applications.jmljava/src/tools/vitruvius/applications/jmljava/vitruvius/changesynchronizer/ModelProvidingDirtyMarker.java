package tools.vitruvius.applications.jmljava.vitruvius.changesynchronizer;

import tools.vitruvius.framework.util.datatypes.VURI;

public interface ModelProvidingDirtyMarker {

	public void markAllModelInstancesDirty(VURI mmUri);
	
}
