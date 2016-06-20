package edu.kit.ipd.sdq.vitruvius.codeintegration.change2command;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.codeintegration.change2command.IntegrationChange2CommandResult;
import edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.IntegrationCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.VitruviusTransformationRecordingCommand;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import mir.responses.responsesJavaTo5_1.rename.ExecutorJavaTo5_1;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.core.entity.NamedElement;

@SuppressWarnings("all")
public class IntegrationChange2CommandTransformer {
  private UserInteracting userInteracting;
  
  public IntegrationChange2CommandTransformer(final UserInteracting userInteracting) {
    this.userInteracting = userInteracting;
  }
  
  public IntegrationChange2CommandResult compute(final EChange change, final Blackboard blackboard) {
    final List<? extends Command> commands = this.createCommandsList(change, blackboard);
    return new IntegrationChange2CommandResult(commands);
  }
  
  private List<? extends Command> createCommandsList(final EChange change, final Blackboard blackboard) {
    final List<Command> responseCommands = this.createResponseCommands(change, blackboard);
    final VitruviusTransformationRecordingCommand newClassOrInterfaceInIntegratedAreaCommand = this.createNewClassOrInterfaceInIntegratedAreaCommand(change, blackboard);
    boolean _notEquals = (!Objects.equal(newClassOrInterfaceInIntegratedAreaCommand, null));
    if (_notEquals) {
      boolean _notEquals_1 = (!Objects.equal(responseCommands, null));
      if (_notEquals_1) {
        return responseCommands;
      }
      List<VitruviusTransformationRecordingCommand> _list = CollectionBridge.<VitruviusTransformationRecordingCommand>toList(newClassOrInterfaceInIntegratedAreaCommand);
      final List<? extends Command> commands = ((List<? extends Command>) _list);
      return commands;
    }
    final VitruviusTransformationRecordingCommand defaultIntegrationChangeCommand = this.getDefaultIntegrationChangeCommand(change, blackboard);
    boolean _notEquals_2 = (!Objects.equal(defaultIntegrationChangeCommand, null));
    if (_notEquals_2) {
      boolean _notEquals_3 = (!Objects.equal(responseCommands, null));
      if (_notEquals_3) {
        return responseCommands;
      }
      List<VitruviusTransformationRecordingCommand> _list_1 = CollectionBridge.<VitruviusTransformationRecordingCommand>toList(defaultIntegrationChangeCommand);
      final List<? extends Command> commands_1 = ((List<? extends Command>) _list_1);
      return commands_1;
    }
    return new ArrayList();
  }
  
  public List<Command> createResponseCommands(final EChange change, final Blackboard blackboard) {
    final ExecutorJavaTo5_1 executor = new ExecutorJavaTo5_1(this.userInteracting);
    final List<Command> commands = executor.generateCommandsForEvent(change, blackboard);
    boolean _and = false;
    boolean _notEquals = (!Objects.equal(commands, null));
    if (!_notEquals) {
      _and = false;
    } else {
      int _size = commands.size();
      boolean _greaterThan = (_size > 0);
      _and = _greaterThan;
    }
    if (_and) {
      return commands;
    }
    return null;
  }
  
  private VitruviusTransformationRecordingCommand createNewClassOrInterfaceInIntegratedAreaCommand(final EChange eChange, final Blackboard blackboard) {
    if ((eChange instanceof CreateNonRootEObjectInList<?>)) {
      final CreateNonRootEObjectInList<?> change = ((CreateNonRootEObjectInList<?>) eChange);
      EObject _newAffectedEObject = change.getNewAffectedEObject();
      EClass _eClass = _newAffectedEObject.eClass();
      final Class<?> classOfAffected = _eClass.getInstanceClass();
      EObject _newValue = change.getNewValue();
      EClass _eClass_1 = _newValue.eClass();
      final Class<?> classOfCreated = _eClass_1.getInstanceClass();
      boolean _and = false;
      boolean _equals = Objects.equal(classOfAffected, CompilationUnit.class);
      if (!_equals) {
        _and = false;
      } else {
        boolean _or = false;
        boolean _equals_1 = classOfCreated.equals(org.emftext.language.java.classifiers.Class.class);
        if (_equals_1) {
          _or = true;
        } else {
          boolean _equals_2 = classOfCreated.equals(Interface.class);
          _or = _equals_2;
        }
        _and = _or;
      }
      if (_and) {
        EObject _newAffectedEObject_1 = change.getNewAffectedEObject();
        final CompilationUnit cu = ((CompilationUnit) _newAffectedEObject_1);
        final CorrespondenceInstanceDecorator ci = blackboard.getCorrespondenceInstance();
        final TUID newCompilationUnitTuid = ci.calculateTUIDFromEObject(cu);
        final String packagePartOfNewTuid = this.getPackagePart(newCompilationUnitTuid);
        List<Correspondence> _allCorrespondences = ci.getAllCorrespondences();
        for (final Correspondence corr : _allCorrespondences) {
          if ((corr instanceof IntegrationCorrespondence)) {
            final IntegrationCorrespondence integrationCorr = ((IntegrationCorrespondence) corr);
            boolean _isCreatedByIntegration = integrationCorr.isCreatedByIntegration();
            if (_isCreatedByIntegration) {
              final ArrayList<TUID> allTUIDs = new ArrayList<TUID>();
              EList<TUID> _aTUIDs = ((IntegrationCorrespondence)corr).getATUIDs();
              allTUIDs.addAll(_aTUIDs);
              EList<TUID> _bTUIDs = ((IntegrationCorrespondence)corr).getBTUIDs();
              allTUIDs.addAll(_bTUIDs);
              for (final TUID tuid : allTUIDs) {
                {
                  final String packagePart = this.getPackagePart(tuid);
                  boolean _startsWith = packagePartOfNewTuid.startsWith(packagePart);
                  if (_startsWith) {
                    final VitruviusTransformationRecordingCommand command = EMFCommandBridge.createVitruviusTransformationRecordingCommand(new Callable<TransformationResult>() {
                      @Override
                      public TransformationResult call() throws Exception {
                        IntegrationChange2CommandTransformer.this.showNewTypeInIntegratedAreaDialog();
                        return new TransformationResult();
                      }
                    });
                    return command;
                  }
                }
              }
            }
          }
        }
      }
    }
    return null;
  }
  
  private String getPackagePart(final TUID newCompilationUnitTuid) {
    final String originalTuidAsString = newCompilationUnitTuid.toString();
    final int lastSlashIndex = originalTuidAsString.lastIndexOf("/");
    return originalTuidAsString.substring(0, lastSlashIndex);
  }
  
  private void showNewTypeInIntegratedAreaDialog() {
    final StringBuffer buffer = new StringBuffer();
    buffer.append("Created class or interface in area with integrated objects.\n");
    buffer.append("Please handle consistency manually.");
    String _string = buffer.toString();
    this.userInteracting.showMessage(UserInteractionType.MODAL, _string);
  }
  
  private VitruviusTransformationRecordingCommand getDefaultIntegrationChangeCommand(final EChange eChange, final Blackboard blackboard) {
    final HashSet<EObject> correspondingIntegratedEObjects = this.getCorrespondingEObjectsIfIntegrated(eChange, blackboard);
    boolean _notEquals = (!Objects.equal(correspondingIntegratedEObjects, null));
    if (_notEquals) {
      final StringBuffer buffer = new StringBuffer();
      buffer.append("Elements in change were integrated into Vitruvius.\n");
      buffer.append("Please fix manually. Corresponding object(s):\n");
      for (final EObject obj : correspondingIntegratedEObjects) {
        {
          final String name = this.getReadableName(obj);
          buffer.append("\n");
          buffer.append(name);
        }
      }
      final VitruviusTransformationRecordingCommand command = EMFCommandBridge.createVitruviusTransformationRecordingCommand(
        new Callable<TransformationResult>() {
          @Override
          public TransformationResult call() throws Exception {
            String _string = buffer.toString();
            IntegrationChange2CommandTransformer.this.userInteracting.showMessage(UserInteractionType.MODAL, _string);
            return new TransformationResult();
          }
        });
      return command;
    }
    return null;
  }
  
  private String getReadableName(final EObject obj) {
    String name = this.getDirectNameIfNamed(obj);
    EClass _eClass = obj.eClass();
    final String className = _eClass.getName();
    EObject container = obj.eContainer();
    while (Objects.equal(name, null)) {
      boolean _equals = Objects.equal(container, null);
      if (_equals) {
        name = className;
      } else {
        final String containerName = this.getDirectNameIfNamed(container);
        boolean _notEquals = (!Objects.equal(containerName, null));
        if (_notEquals) {
          EClass _eClass_1 = container.eClass();
          final String containerClassName = _eClass_1.getName();
          name = ((((className + " in ") + containerClassName) + ": ") + containerName);
        } else {
          EObject _eContainer = container.eContainer();
          container = _eContainer;
        }
      }
    }
    return name;
  }
  
  private String getDirectNameIfNamed(final EObject obj) {
    String name = null;
    EClass _eClass = obj.eClass();
    final String className = _eClass.getName();
    if ((obj instanceof NamedElement)) {
      final NamedElement named = ((NamedElement) obj);
      String _entityName = named.getEntityName();
      String _plus = ((className + ": ") + _entityName);
      name = _plus;
    } else {
      if ((obj instanceof org.emftext.language.java.commons.NamedElement)) {
        final org.emftext.language.java.commons.NamedElement named_1 = ((org.emftext.language.java.commons.NamedElement) obj);
        String _name = named_1.getName();
        String _plus_1 = ((className + ": ") + _name);
        name = _plus_1;
      }
    }
    return name;
  }
  
  /**
   * @return set of corresponding EObjects if integrated, else null
   */
  private HashSet<EObject> getCorrespondingEObjectsIfIntegrated(final EChange eChange, final Blackboard blackboard) {
    final CorrespondenceInstanceDecorator ci = blackboard.getCorrespondenceInstance();
    EObject eObj = null;
    if ((eChange instanceof EFeatureChange<?>)) {
      EObject _newAffectedEObject = ((EFeatureChange<?>)eChange).getNewAffectedEObject();
      eObj = _newAffectedEObject;
    } else {
      if ((eChange instanceof ReplaceRootEObject<?>)) {
        EObject _newValue = ((ReplaceRootEObject<?>)eChange).getNewValue();
        eObj = _newValue;
      } else {
        if ((eChange instanceof DeleteRootEObject<?>)) {
          EObject _oldValue = ((DeleteRootEObject<?>)eChange).getOldValue();
          eObj = _oldValue;
        }
      }
    }
    final CorrespondenceInstance<IntegrationCorrespondence> integrationView = ci.<IntegrationCorrespondence>getView(IntegrationCorrespondence.class);
    boolean _notEquals = (!Objects.equal(eObj, null));
    if (_notEquals) {
      final Set<EObject> set = CollectionBridge.<EObject>toSet(eObj);
      final Set<IntegrationCorrespondence> correspondences = integrationView.getCorrespondencesThatInvolveAtLeast(set);
      final HashSet<EObject> correspondingObjects = new HashSet<EObject>();
      for (final IntegrationCorrespondence integratedCorrespondence : correspondences) {
        boolean _isCreatedByIntegration = integratedCorrespondence.isCreatedByIntegration();
        if (_isCreatedByIntegration) {
          final EList<EObject> aList = integratedCorrespondence.getAs();
          final EList<EObject> bList = integratedCorrespondence.getAs();
          boolean _contains = aList.contains(eObj);
          if (_contains) {
            correspondingObjects.addAll(bList);
          } else {
            correspondingObjects.addAll(aList);
          }
          return correspondingObjects;
        }
      }
    }
    return null;
  }
}
