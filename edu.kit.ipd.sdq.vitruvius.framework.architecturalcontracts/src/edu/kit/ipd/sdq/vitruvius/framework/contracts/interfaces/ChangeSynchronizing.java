package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;

public interface ChangeSynchronizing {
	void synchronizeChange(Change change, ModelInstance sourceModel);
}
