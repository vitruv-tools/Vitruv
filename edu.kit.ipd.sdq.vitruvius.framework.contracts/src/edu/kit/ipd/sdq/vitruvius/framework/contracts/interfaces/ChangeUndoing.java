package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;

public interface ChangeUndoing {
    void undoChanges(Blackboard blackboard);
}
