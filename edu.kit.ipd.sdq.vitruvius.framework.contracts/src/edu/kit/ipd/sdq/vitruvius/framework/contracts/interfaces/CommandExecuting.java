package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;

public interface CommandExecuting {
    List<Change> executeCommands(Blackboard blackboard);

    void rollbackCommands(Blackboard blackboard);
}
