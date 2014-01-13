package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ViewType;

public interface ViewTypeManaging {
	void addViewType(ViewType viewType);
	ViewType getViewType(VURI uri);
	// TODO decide whether the method ViewTypeManaging.getAllInvolvedViewTypesForMM(..) is needed
}
