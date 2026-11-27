package tools.vitruv.change.propagation.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.composite.MetamodelDescriptor;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;
import tools.vitruv.change.interaction.UserInteractor;
import tools.vitruv.change.propagation.ChangePropagationObserver;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.utils.ResourceAccess;

/**
 * A {@link CompositeChangePropagationSpecification} combines multiple 
 * {@link ChangePropagationSpecification}s, and executes them as one.
 * 
 * <p>It distinguishes between preprocessors and main processors, and executes the
 * preprocessors first.
 */
public class CompositeChangePropagationSpecification
    extends AbstractChangePropagationSpecification implements ChangePropagationObserver {
  private static final Logger logger 
      = LogManager.getLogger(CompositeChangePropagationSpecification.class);

  @Getter(AccessLevel.PROTECTED)
  private final List<ChangePropagationSpecification> changePreprocessors;

  @Getter(AccessLevel.PROTECTED)
  private final List<ChangePropagationSpecification> changeMainprocessors;

  /**
   * Creates a new CompositeChangePropagationSpecification from models with
   * <code>sourceMetamodel</code> as metamodel 
   * to models with <code>targetMetamodel</code> as metamodel.
   *
   * @param sourceMetamodel - {@link MetamodelDescriptor}
   * @param targetMetamodel - {@link MetamodelDescriptor}
   */
  public CompositeChangePropagationSpecification(
      MetamodelDescriptor sourceMetamodel, MetamodelDescriptor targetMetamodel) {
    super(sourceMetamodel, targetMetamodel);
    changePreprocessors = new ArrayList<>();
    changeMainprocessors = new ArrayList<>();
  }

  /**
   * Adds the specified change processor as a preprocessor, 
   * which is executed before the main processors.
   * The preprocessors are executed in the order in which they are added.
   *
   * @param changePreprocessor - {@link ChangePropagationSpecification}
   *      <code>changePreprocessor</code> must be compatible to this specification,
   *      i.e. source and target metamodel descriptions must match.
   */
  public void addChangePreprocessor(
      ChangePropagationSpecification changePreprocessor) {
    assertMetamodelsCompatible(changePreprocessor);
    changePreprocessors.add(changePreprocessor);
    changePreprocessor.setUserInteractor(this.getUserInteractor());
    changePreprocessor.registerObserver(this);
  }

  /**
   * Adds the specified change processor as a main processor, 
   * which is executed after the preprocessors.
   * The main processors are executed in the order in which they are added.
   *
   * @param changeMainProcessor - {@link ChangePropagationSpecification}
   *      <code>changeMainProcessor</code> must be compatible to this specification,
   *      i.e. source and target metamodel descriptions must match.
   */
  public void addChangeMainprocessor(ChangePropagationSpecification changeMainProcessor) {
    assertMetamodelsCompatible(changeMainProcessor);
    changeMainprocessors.add(changeMainProcessor);
    changeMainProcessor.setUserInteractor(this.getUserInteractor());
    changeMainProcessor.registerObserver(this);
  }

  private void assertMetamodelsCompatible(
      ChangePropagationSpecification potentialChangeProcessor) {
    if (!getSourceMetamodelDescriptor()
          .equals(potentialChangeProcessor.getSourceMetamodelDescriptor()) 
          || 
        !getTargetMetamodelDescriptor()
        .equals(potentialChangeProcessor.getTargetMetamodelDescriptor())) {
      throw new IllegalArgumentException("ChangeProcessor metamodels are not compatible");
    }
  }

  @Override
  public void propagateChange(
      EChange<EObject> change,
      EditableCorrespondenceModelView<Correspondence> correspondenceModel,
      ResourceAccess resourceAccess) {
    this.propagateChangeViaPreprocessors(change, correspondenceModel, resourceAccess);
    this.propagateChangeViaMainprocessors(change, correspondenceModel, resourceAccess);
  }

  /**
   * Propagates <code>change</code> via the preprocessors.
   *
   * @param change - {@link EChange}
   * @param correspondenceModel - {@link EditableCorrespondenceModelView}
   * @param resourceAccess - {@link ResourceAccess}
   */
  protected void propagateChangeViaPreprocessors(
      EChange<EObject> change,
      EditableCorrespondenceModelView<Correspondence> correspondenceModel,
      ResourceAccess resourceAccess) {
    for (var changeProcessor : changePreprocessors) {
      logger.trace("Calling change preprocessor %s for change event %s", changeProcessor, change);
      changeProcessor.propagateChange(change, correspondenceModel, resourceAccess);
    }
  }

  /**
   * Propagates <code>change</code> via the main processors.
   *
   * @param change - {@link EChange}
   * @param correspondenceModel - {@link EditableCorrespondenceModelView}
   * @param resourceAccess - {@link ResourceAccess}
   */
  protected void propagateChangeViaMainprocessors(
      EChange<EObject> change,
      EditableCorrespondenceModelView<Correspondence> correspondenceModel,
      ResourceAccess resourceAccess) {
    for (var changeProcessor : this.changeMainprocessors) {
      logger.trace("Calling change main processor %s for change event %s", changeProcessor, change);
      changeProcessor.propagateChange(change, correspondenceModel, resourceAccess);
    }
  }

  @Override
  public boolean doesHandleChange(
      EChange<EObject> change, 
      EditableCorrespondenceModelView<Correspondence> correspondenceModel) {
    return getAllProcessors()
        .stream()
        .anyMatch(processor -> processor.doesHandleChange(change, correspondenceModel));
  }

  @Override
  public void setUserInteractor(final UserInteractor userInteractor) {
    super.setUserInteractor(userInteractor);
    getAllProcessors().forEach(processor -> processor.setUserInteractor(userInteractor));
  }

  private ArrayList<ChangePropagationSpecification> getAllProcessors() {
    var processors = new ArrayList<ChangePropagationSpecification>();
    processors.addAll(changePreprocessors);
    processors.addAll(changeMainprocessors);
    return processors;
  }

  @Override
  public void objectCreated(final EObject createdObject) {
    notifyObjectCreated(createdObject);
  }

  @Override
  public void changePropagationStarted(
      ChangePropagationSpecification specification, EChange<EObject> change) {
    notifyChangePropagationStarted(specification, change);
  }

  @Override
  public void changePropagationStopped(
      ChangePropagationSpecification specification, EChange<EObject> change) {
    notifyChangePropagationStopped(specification, change);
  }
}
