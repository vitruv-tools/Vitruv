package tools.vitruv.change.atomic.command.internal;

import static tools.vitruv.change.atomic.command.internal.ChangeCommandUtil.alreadyContainsObject;
import static tools.vitruv.change.atomic.command.internal.ChangeCommandUtil.getEditingDomain;

import edu.kit.ipd.sdq.activextendannotations.Utility;
import java.util.List;
import lombok.val;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import tools.vitruv.change.atomic.command.RemoveAtCommand;
import tools.vitruv.change.atomic.feature.UnsetFeature;
import tools.vitruv.change.atomic.feature.attribute.InsertEAttributeValue;
import tools.vitruv.change.atomic.feature.attribute.RemoveEAttributeValue;
import tools.vitruv.change.atomic.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.change.atomic.feature.reference.InsertEReference;
import tools.vitruv.change.atomic.feature.reference.RemoveEReference;
import tools.vitruv.change.atomic.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.change.atomic.root.InsertRootEObject;
import tools.vitruv.change.atomic.root.RemoveRootEObject;

/**
 * Switch to create commands for all EChange classes.
 * The commands applies the EChanges backward.
 */
@Utility
class ApplyBackwardCommandSwitch extends ApplyCommandSwitch {
  private static final String CONTAINMENT_OPPOSITE_WARNING =
      "but although there is no opposite feature it was already contained in %s";
  private static final Logger logger = LogManager.getLogger(ApplyBackwardCommandSwitch.class);

  ApplyBackwardCommandSwitch() {}

  /**
   * Method to create commands to apply a {@link UnsetFeature} change backward.
   *
   * @param change The change for which commands should be created.
   * @return List of SetCommand
   */
  @Override
  List<Command> getCommands(UnsetFeature<EObject, EStructuralFeature> change) {
    val editingDomain = getEditingDomain(change.getAffectedElement());
    return List.of(new SetCommand(editingDomain,
        change.getAffectedElement(), change.getAffectedFeature(), SetCommand.UNSET_VALUE));
  }

  /**
   * Method to create commands to apply a {@link InsertEAttributeValue} change backward.
   *
   * @param change The change for which commands should be created.
   * @return List of RemoveAtCommand, or SetCommand,
   *      depending on whether change inserts an actual value, or not.
   */
  @Override
  List<Command> getCommands(InsertEAttributeValue<EObject, ?> change) {
    val element = change.getAffectedElement();
    val feature = change.getAffectedFeature();
    val editingDomain = getEditingDomain(element);

    val command = change.isWasUnset()
        ? new SetCommand(editingDomain, element,
            feature, SetCommand.UNSET_VALUE) 
        : new RemoveAtCommand(editingDomain, element,
            feature, change.getNewValue(), change.getIndex());
    return List.of(command);
  } 

  /**
   * Method to create commands to apply a {@link RemoveEAttributeValue} change backward.
   *
   * @param change The change for which commands should be created.
   * @return List of AddCommand
   */
  @Override
  List<Command> getCommands(
      RemoveEAttributeValue<EObject, EStructuralFeature> change) {
    val element = change.getAffectedElement();
    val editingDomain = getEditingDomain(element);
    return List.of(new AddCommand(editingDomain, element, change.getAffectedFeature(), 
        change.getOldValue(), change.getIndex()));
  }

  /**
   * Method to create commands to apply a {@link ReplaceSingleValuedEAttribute} change backward.
   *
   * @param change The change for which commands should be created.
   * @return List of SetCommand
   */
  @Override
  List<Command> getCommands(ReplaceSingleValuedEAttribute<EObject, ?> change) {
    val element = change.getAffectedElement();
    val editingDomain = getEditingDomain(element);
    val value = change.isWasUnset() ? SetCommand.UNSET_VALUE : change.getOldValue();
    return List.of(new SetCommand(editingDomain, element, change.getAffectedFeature(), value));
  }

  /**
   * Method to create commands to apply a {@link InsertEReference} change backward.
   *
   * @param change The change for which commands should be created.
   * @return List of RemoveAtCommand, or an empty list
   */
  @Override
  List<Command> getCommands(InsertEReference<EObject> change) {
    val elememt = change.getAffectedElement();
    val feature = change.getAffectedFeature();
    val editingDomain = getEditingDomain(elememt);
    if (!alreadyContainsObject(elememt, feature, change.getNewValue())) {
      if (feature.getEOpposite() == null) {
        logger.warn("Tried to remove value %s," + CONTAINMENT_OPPOSITE_WARNING,
            change.getNewValue(), feature);
      } 
      return List.of();
    }
    if (change.isWasUnset()) {
      return List.of(new SetCommand(editingDomain, elememt, feature, SetCommand.UNSET_VALUE));
    } else {
      return List.of(new RemoveAtCommand(editingDomain, elememt, feature,
        change.getNewValue(), change.getIndex()));
    }
  }

  /**
   * Method to create commands to apply a {@link InsertEReference} change forward.
   *
   * @param change The change for which commands should be created.
   * @return List of AddCommand, or an empty list
   */
  @Override
  List<Command> getCommands(RemoveEReference<EObject> change) {
    val element = change.getAffectedElement();
    val feature = change.getAffectedFeature();
    val editingDomain = getEditingDomain(element);
  
    if (alreadyContainsObject(element, feature, change.getOldValue())) {
      if (feature.getEOpposite() == null) {
        logger.warn("Tried to add value %s," + CONTAINMENT_OPPOSITE_WARNING,
            change.getOldValue(), feature);
      }
      return List.of();
    }
    return List.of(new AddCommand(editingDomain, element, feature, change.getOldValue(), 
      change.getIndex()));
  }

  /**
   * Method to create commands to apply a {@link ReplaceSingleValuedEReference} change backward.
   *
   * @param change The change for which commands should be created.
   * @return List of SetCommand, or an empty list
   */
  @Override
  List<Command> getCommands(ReplaceSingleValuedEReference<EObject> change) {
    val elememt = change.getAffectedElement();
    val feature = change.getAffectedFeature();
    val editingDomain = getEditingDomain(elememt);
    val value = change.getOldValue();

    if (alreadyContainsObject(elememt, feature, value)) {
      if (feature.getEOpposite() == null) {
        logger.warn("Tried to set value %s," + CONTAINMENT_OPPOSITE_WARNING, value, feature);
      } 
      return List.of();
    }

    return List.of(new SetCommand(editingDomain, elememt, feature,
        change.isWasUnset() ? SetCommand.UNSET_VALUE : value));
  }

  /**
   * Method to create commands to apply a {@link InsertRootEObject} change backward.
   *
   * @param change The change for which commands should be created.
   * @return List of RemoveCommand
   */
  @Override
  List<Command> getCommands(InsertRootEObject<EObject> change) {
    return List.of(new RemoveCommand(getEditingDomain(change.getNewValue()),
        change.getResource().getContents(), change.getNewValue()));
  }

  /**
   * Method to create commands to apply a {@link RemoveRootEObject} change backward.
   *
   * @param change The change for which commands should be created.
   * @return List of AddCommand
   */
  @Override
  List<Command> getCommands(RemoveRootEObject<EObject> change) {
    // Will be automatically removed from resource because object can only be in one resource.
    return List.of(new AddCommand(getEditingDomain(change.getOldValue()), 
        change.getResource().getContents(), change.getOldValue(), change.getIndex()));
  }
}
