package tools.vitruv.change.atomic.command.internal;

import java.util.List;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import tools.vitruv.change.atomic.EChange;
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
 * An interface for "command switches", which translate Vitruvius {@link EChange}s 
 * into EMF {@link Command}s.
 */
abstract class ApplyCommandSwitch {
  /**
   * "Static dispatch" method. Returns the correct commands for change to apply it forward.
   *
   * @param change - EChange
   * @return a List of Commands
   */
  List<Command> getCommands(EChange<EObject> change) {
    if (change instanceof UnsetFeature) {
      return getCommands((UnsetFeature<EObject, EStructuralFeature>) change);
    }
    if (change instanceof InsertEAttributeValue) {
      return getCommands((InsertEAttributeValue<EObject, ?>) change);
    }
    if (change instanceof RemoveEAttributeValue) {
      return getCommands((RemoveEAttributeValue<EObject, EStructuralFeature>) change);
    }
    if (change instanceof ReplaceSingleValuedEAttribute) {
      return getCommands((ReplaceSingleValuedEAttribute<EObject, ?>) change);
    }
    if (change instanceof InsertEReference) {
      return getCommands((InsertEReference<EObject>) change);
    }
    if (change instanceof RemoveEReference) {
      return getCommands((RemoveEReference<EObject>) change);
    }
    if (change instanceof ReplaceSingleValuedEReference) {
      return getCommands((ReplaceSingleValuedEReference<EObject>) change);
    }
    if (change instanceof InsertRootEObject) {
      return getCommands((InsertRootEObject<EObject>) change);
    }
    if (change instanceof RemoveRootEObject) {
      return getCommands((RemoveRootEObject<EObject>) change);
    }
    return List.of();
  }


  abstract List<Command> getCommands(UnsetFeature<EObject, EStructuralFeature> unsetChange);

  abstract List<Command> getCommands(InsertEAttributeValue<EObject, ?> insertAttributeChange);

  abstract List<Command> getCommands(
      RemoveEAttributeValue<EObject, EStructuralFeature> removeAttributeChange);

  abstract List<Command> getCommands(
      ReplaceSingleValuedEAttribute<EObject, ?> replaceAttributeChange);

  abstract List<Command> getCommands(InsertEReference<EObject> insertReferenceChange);

  abstract List<Command> getCommands(RemoveEReference<EObject> removeReferenceChange);

  abstract List<Command> getCommands(
      ReplaceSingleValuedEReference<EObject> replaceReferenceChange);

  abstract List<Command> getCommands(InsertRootEObject<EObject> insertRootChange);

  abstract List<Command> getCommands(RemoveRootEObject<EObject> removeRootChange);
}
