package tools.vitruv.change.propagation.impl;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.composite.MetamodelDescriptor;
import tools.vitruv.change.interaction.UserInteractor;
import tools.vitruv.change.propagation.ChangePropagationObserver;
import tools.vitruv.change.propagation.ChangePropagationSpecification;

/**
 * An extensible implementation of a {@link ChangePropagationSpecification}.
 */
public abstract class AbstractChangePropagationSpecification 
    implements ChangePropagationSpecification {
  private final List<ChangePropagationObserver> propagationObservers;
  private UserInteractor userInteractor;
  private MetamodelDescriptor sourceMetamodelDescriptor;
  private MetamodelDescriptor targetMetamodelDescriptor;

  /**
   * Creates a new AbstractChangePropagationSpecification.
   *
   * @param sourceMetamodelDescriptor - {@link MetamodelDescriptor}
   * @param targetMetamodelDescriptor - {@link MetamodelDescriptor}
   */
  protected AbstractChangePropagationSpecification(
      MetamodelDescriptor sourceMetamodelDescriptor, 
      MetamodelDescriptor targetMetamodelDescriptor) {
    this.sourceMetamodelDescriptor = sourceMetamodelDescriptor;
    this.targetMetamodelDescriptor = targetMetamodelDescriptor;
    this.propagationObservers = new ArrayList<>();
  }

  protected UserInteractor getUserInteractor() {
    return userInteractor;
  }

  @Override
  public MetamodelDescriptor getSourceMetamodelDescriptor() {
    return sourceMetamodelDescriptor;
  }

  @Override
  public MetamodelDescriptor getTargetMetamodelDescriptor() {
    return targetMetamodelDescriptor;
  }

  @Override
  public void setUserInteractor(UserInteractor userInteractor) {
    this.userInteractor = userInteractor;
  }

  @Override
  public void registerObserver(ChangePropagationObserver observer) {
    if (observer != null) {
      propagationObservers.add(observer);
    }
  }

  @Override
  public void deregisterObserver(ChangePropagationObserver observer) {
    propagationObservers.remove(observer);
  }

  @Override
  public void notifyObjectCreated(EObject createdObject) {
    propagationObservers.forEach(observer -> observer.objectCreated(createdObject));
  }

  @Override
  public void notifyChangePropagationStarted(
      ChangePropagationSpecification specification, EChange<EObject> change) {
    propagationObservers.forEach(observer ->
        observer.changePropagationStarted(specification, change));
  }

  @Override
  public void notifyChangePropagationStopped(
      ChangePropagationSpecification specification, EChange<EObject> change) {
    propagationObservers.forEach(observer ->
        observer.changePropagationStopped(specification, change));
  }
}
