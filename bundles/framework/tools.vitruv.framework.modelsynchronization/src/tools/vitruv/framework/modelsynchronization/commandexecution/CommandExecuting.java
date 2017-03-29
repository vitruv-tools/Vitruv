package tools.vitruv.framework.modelsynchronization.commandexecution;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.modelsynchronization.blackboard.Blackboard;

public interface CommandExecuting {
    List<VitruviusChange> executeCommands(Blackboard blackboard, Resource sourceResource);
}
