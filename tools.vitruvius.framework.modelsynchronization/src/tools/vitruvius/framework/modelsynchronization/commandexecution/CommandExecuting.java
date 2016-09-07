package tools.vitruvius.framework.modelsynchronization.commandexecution;

import java.util.List;

import tools.vitruvius.framework.change.description.VitruviusChange;
import tools.vitruvius.framework.modelsynchronization.blackboard.Blackboard;

public interface CommandExecuting {
    List<VitruviusChange> executeCommands(Blackboard blackboard);

    void rollbackCommands(Blackboard blackboard);
}
