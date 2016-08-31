package edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization.commandexecution;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization.blackboard.Blackboard;

public interface CommandExecuting {
    List<VitruviusChange> executeCommands(Blackboard blackboard);

    void rollbackCommands(Blackboard blackboard);
}
