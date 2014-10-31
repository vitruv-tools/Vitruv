package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;

public interface ChangePropagating {
    ChangeResult propagateChange(Change change, CorrespondenceInstance correspondenceInstance);

    ChangeResult propagateChange(CompositeChange compositeChange, CorrespondenceInstance correspondenceInstance);
}
