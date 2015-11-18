package edu.kit.ipd.sdq.vitruvius.commandexecuter;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CommandExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.VitruviusTransformationRecordingCommand;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class CommandExecutingImpl implements CommandExecuting {
  private final static Logger logger = Logger.getLogger(CommandExecutingImpl.class.getSimpleName());
  
  @Override
  public List<Change> executeCommands(final Blackboard blackboard) {
    final ModelProviding modelProviding = blackboard.getModelProviding();
    final TransactionalEditingDomain domain = modelProviding.getTransactionalEditingDomain();
    final ArrayList<Object> affectedObjects = new ArrayList<Object>();
    final ArrayList<TransformationResult> transformationResults = new ArrayList<TransformationResult>();
    List<Command> _andArchiveCommandsForExecution = blackboard.getAndArchiveCommandsForExecution();
    for (final Command command : _andArchiveCommandsForExecution) {
      {
        if ((command instanceof VitruviusTransformationRecordingCommand)) {
          final TransformationResult transformationResult = EMFCommandBridge.executeVitruviusRecordingCommand(modelProviding, ((VitruviusTransformationRecordingCommand) command));
          transformationResults.add(transformationResult);
        } else {
          CommandStack _commandStack = domain.getCommandStack();
          _commandStack.execute(command);
        }
        Collection<?> _affectedObjects = command.getAffectedObjects();
        final Function1<Object, Boolean> _function = (Object eObj) -> {
          return Boolean.valueOf(((!(eObj instanceof Correspondence)) && (!(eObj instanceof Correspondences))));
        };
        Iterable<?> _filter = IterableExtensions.filter(_affectedObjects, _function);
        Iterables.<Object>addAll(affectedObjects, _filter);
      }
    }
    this.executeTransformationResults(transformationResults, blackboard);
    ModelProviding _modelProviding = blackboard.getModelProviding();
    this.saveAffectedEObjects(affectedObjects, _modelProviding);
    modelProviding.detachTransactionalEditingDomain();
    return Collections.<Change>emptyList();
  }
  
  /**
   * undo all commands on the blackboard
   */
  @Override
  public void rollbackCommands(final Blackboard blackboard) {
    final Pair<List<Change>, List<Command>> commandAndChangePairs = blackboard.getArchivedChangesAndCommandsForUndo();
    final ArrayList<Object> affectedObjects = new ArrayList<Object>();
    List<Command> _second = commandAndChangePairs.getSecond();
    for (final Command command : _second) {
      {
        command.undo();
        Collection<?> _affectedObjects = command.getAffectedObjects();
        affectedObjects.addAll(_affectedObjects);
      }
    }
    ModelProviding _modelProviding = blackboard.getModelProviding();
    this.saveAffectedEObjects(affectedObjects, _modelProviding);
  }
  
  private void executeTransformationResults(final ArrayList<TransformationResult> transformationResults, final Blackboard blackboard) {
    if ((null == transformationResults)) {
      return;
    }
    for (final TransformationResult transformationResult : transformationResults) {
      {
        if ((null == transformationResult)) {
          CommandExecutingImpl.logger.info("Current TransformationResult is null. Can not save new root EObjects or delete VURIs.");
          return;
        }
        List<VURI> _vUIRsToDelete = transformationResult.getVUIRsToDelete();
        for (final VURI vuriToDelete : _vUIRsToDelete) {
          ModelProviding _modelProviding = blackboard.getModelProviding();
          _modelProviding.deleteModelInstanceOriginal(vuriToDelete);
        }
        List<Pair<EObject, VURI>> _rootEObjectsToSave = transformationResult.getRootEObjectsToSave();
        for (final Pair<EObject, VURI> createdEObjectVURIPair : _rootEObjectsToSave) {
          {
            CorrespondenceInstance _correspondenceInstance = blackboard.getCorrespondenceInstance();
            EObject _first = createdEObjectVURIPair.getFirst();
            List<EObject> _list = CollectionBridge.<EObject>toList(_first);
            List<TUID> _calculateTUIDsFromEObjects = _correspondenceInstance.calculateTUIDsFromEObjects(_list);
            final TUID oldTUID = CollectionBridge.<TUID>claimOne(_calculateTUIDsFromEObjects);
            ModelProviding _modelProviding_1 = blackboard.getModelProviding();
            VURI _second = createdEObjectVURIPair.getSecond();
            EObject _first_1 = createdEObjectVURIPair.getFirst();
            _modelProviding_1.saveModelInstanceOriginalWithEObjectAsOnlyContent(_second, _first_1, oldTUID);
          }
        }
      }
    }
  }
  
  private void saveAffectedEObjects(final ArrayList<Object> affectedObjects, final ModelProviding modelProviding) {
    final Set<VURI> vurisToSave = new HashSet<VURI>();
    for (final Object object : affectedObjects) {
      if ((object instanceof EObject)) {
        final EObject eObject = ((EObject) object);
        Resource _eResource = eObject.eResource();
        boolean _tripleNotEquals = (null != _eResource);
        if (_tripleNotEquals) {
          Resource _eResource_1 = eObject.eResource();
          VURI _instance = VURI.getInstance(_eResource_1);
          vurisToSave.add(_instance);
        }
      }
    }
    for (final VURI vuri : vurisToSave) {
      modelProviding.saveExistingModelInstanceOriginal(vuri);
    }
  }
}
