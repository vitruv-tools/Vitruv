package edu.kit.ipd.sdq.vitruvius.framework.commandexecutor;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.commandexecutor.blackboard.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.commandexecutor.blackboard.BlackboardImpl;

public interface CommandExecuting {
    List<VitruviusChange> executeCommands(Blackboard blackboard);

    void rollbackCommands(Blackboard blackboard);
}
