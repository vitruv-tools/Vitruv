package edu.kit.ipd.sdq.vitruvius.commandexecuter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CommandExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VitruviusRecordingCommand;

public class CommandExecutingImpl implements CommandExecuting {
    @Override
    public void executeCommands(final Blackboard blackboard) {
    	ModelProviding modelProviding = blackboard.getModelProviding();
    	modelProviding.attachTransactionalEditingDomain();
    	TransactionalEditingDomain domain = modelProviding.getTransactionalEditingDomain();
    	
        final ArrayList<Object> affectedObjects = new ArrayList<>();
        for (final Command command : blackboard.getAndArchiveCommandsForExecution()) {
			if (command instanceof VitruviusRecordingCommand) {
        		((VitruviusRecordingCommand) command).setTransactionDomain(domain);
        	}
        	domain.getCommandStack().execute(command);
            affectedObjects.addAll(command.getAffectedObjects());
        }
        
        this.saveAffectedEObjects(affectedObjects);
        modelProviding.detachTransactionalEditingDomain();
    }

    private void saveAffectedEObjects(final ArrayList<Object> affectedObjects) {
        final Set<Resource> resourcesToSave = new HashSet<Resource>();
        for (final Object object : affectedObjects) {
            if (object instanceof EObject) {
                final EObject eObject = (EObject) object;
                if (null != eObject.eResource()) {
                    resourcesToSave.add(eObject.eResource());
                }
            }
        }
    }

    /**
     * undo all commands on the blackboard
     */
    @Override
    public void rollbackCommands(final Blackboard blackboard) {
        final Pair<List<Change>, List<Command>> commandAndChangePairs = blackboard
                .getArchivedChangesAndCommandsForUndo();
        final ArrayList<Object> affectedObjects = new ArrayList<>();
        for (final Command command : commandAndChangePairs.getSecond()) {
            command.undo();
            affectedObjects.addAll(command.getAffectedObjects());
        }
        this.saveAffectedEObjects(affectedObjects);
    }

}
