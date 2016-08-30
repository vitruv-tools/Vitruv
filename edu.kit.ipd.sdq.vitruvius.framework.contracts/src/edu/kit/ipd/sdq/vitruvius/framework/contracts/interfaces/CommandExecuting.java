package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange;

public interface CommandExecuting {
    List<VitruviusChange> executeCommands(Blackboard blackboard);

    void rollbackCommands(Blackboard blackboard);
}
