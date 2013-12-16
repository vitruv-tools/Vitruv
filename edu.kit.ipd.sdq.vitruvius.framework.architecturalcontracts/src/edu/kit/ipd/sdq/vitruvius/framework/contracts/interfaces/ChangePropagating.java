package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;

public interface ChangePropagating {
	void propagateChange(Change change, ModelInstance sourceModel, CorrespondenceModelInstance correspondenceModelInstance, ModelInstance targetModel);
}
