package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;

public interface CommandExecuting {
    void executeCommands(Blackboard blackboard);

    void rollbackCommands(Blackboard blackboard);
}
