package tools.vitruv.framework.vsum;

import static com.google.common.base.Preconditions.checkNotNull;
import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceUtil.getFirstRootEObject;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.containsModelOf;
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet;
import static tools.vitruv.framework.vsum.VirtualModelTestUtil.createAndLoadTestVirtualModel;
import static tools.vitruv.framework.vsum.VirtualModelTestUtil.createAndLoadTestVirtualModelWithConfirmedConsistencyPreservation;
import static tools.vitruv.framework.vsum.VirtualModelTestUtil.createAndLoadTestVirtualModelWithConsistencyPreservation;
import static tools.vitruv.framework.vsum.VirtualModelTestUtil.recordChanges;

import allElementTypes.Root;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tools.vitruv.change.atomic.eobject.CreateEObject;
import tools.vitruv.change.atomic.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.change.atomic.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.change.atomic.root.InsertRootEObject;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeFactory;
import tools.vitruv.change.composite.description.VitruviusChangeResolverFactory;
import tools.vitruv.change.composite.propagation.ChangePropagationListener;
import tools.vitruv.change.composite.recording.ChangeRecorder;
import tools.vitruv.change.interaction.ConfirmationUserInteraction;
import tools.vitruv.change.interaction.InteractionFactory;
import tools.vitruv.change.interaction.PendingUserInteraction;
import tools.vitruv.change.interaction.UserInteractionBase;
import tools.vitruv.change.propagation.ChangePropagationMode;
import tools.vitruv.change.propagation.PropagationBlockReason;
import tools.vitruv.change.propagation.QueuedChange;
import tools.vitruv.change.propagation.QueuedPropagationStep;
import tools.vitruv.change.propagation.QueuedPropagationStep.Outcome;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.ViewTypeFactory;
import tools.vitruv.framework.vsum.VirtualModelTestUtil.ConfirmingRedundancyChangePropagationSpecification;
import tools.vitruv.framework.vsum.VirtualModelTestUtil.RedundancyChangePropagationSpecification;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

/** Tests for virtual models. */
@ExtendWith(TestProjectManager.class)
class VirtualModelTest {
  private static final String NON_ROOT_ID = "NonRootId";
  private static final String ROOT_ID = "RootId";

  private Path projectFolder;

  /**
   * Initializes the project folder.
   *
   * @param projectFolder The project folder.
   */
  @BeforeEach
  void initializeProjectFolder(@TestProject Path projectFolder) {
    this.projectFolder = projectFolder;
  }

  @Test
  @DisplayName("propagate a simple change into a virtual model")
  void propagateIntoVirtualModel() throws Exception {
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    var changeRecorder = new ChangeRecorder(resourceSet);
    changeRecorder.addToRecording(resourceSet);
    changeRecorder.beginRecording();
    var monitoredResource = resourceSet.createResource(createTestModelResourceUri(""));
    var root = aet.Root();
    root.setId("root");
    monitoredResource.getContents().add(root);
    var uuidResolver = UuidResolver.create(resourceSet);
    var recordedChange = endRecording(changeRecorder, uuidResolver);
    InternalVirtualModel virtualModel =
        (InternalVirtualModel) createAndLoadTestVirtualModel(getPathToVirtualModelProjectFolder());
    virtualModel.propagateChange(recordedChange);
    var vsumModel = virtualModel.getModelInstance(createTestModelResourceUri(""));
    assertThat(vsumModel.getResource(), containsModelOf(monitoredResource));
  }

  @Test
  @DisplayName("propagate a simple change into a virtual model and preserve consistency")
  void propagateIntoVirtualModelWithConsistency() throws Exception {
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    var changeRecorder = new ChangeRecorder(resourceSet);
    changeRecorder.addToRecording(resourceSet);
    changeRecorder.beginRecording();
    var monitoredResource = resourceSet.createResource(createTestModelResourceUri(""));
    var root = aet.Root();
    root.setId("root");
    monitoredResource.getContents().add(root);
    var virtualModel =
        (InternalVirtualModel)
            createAndLoadTestVirtualModelWithConsistencyPreservation(
                getPathToVirtualModelProjectFolder());
    var uuidResolver = UuidResolver.create(resourceSet);
    var recordedChange = endRecording(changeRecorder, uuidResolver);
    virtualModel.propagateChange(recordedChange);
    var sourceModel = virtualModel.getModelInstance(createTestModelResourceUri(""));
    var targetModel =
        virtualModel.getModelInstance(
            RedundancyChangePropagationSpecification.getTargetResourceUri(
                createTestModelResourceUri("")));
    assertThat(targetModel.getResource(), containsModelOf(monitoredResource));
    assertEquals(
        1,
        virtualModel
            .getCorrespondenceModel()
            .getCorrespondingEObjects(sourceModel.getResource().getContents().get(0))
            .size());
  }

  @Test
  @DisplayName("persist element as resource root also contained in other persisted element")
  void singleChangeForRootElementInMultipleResource() throws Exception {
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    var changeRecorder = new ChangeRecorder(resourceSet);
    changeRecorder.addToRecording(resourceSet);
    changeRecorder.beginRecording();
    var containedRoot = aet.Root();
    containedRoot.setId("containedRoot");
    var root = aet.Root();
    root.setId("root");
    root.setRecursiveRoot(containedRoot);
    resourceSet.createResource(createTestModelResourceUri("")).getContents().add(root);
    resourceSet
        .createResource(createTestModelResourceUri("Contained"))
        .getContents()
        .add(containedRoot);
    var uuidResolver = UuidResolver.create(resourceSet);
    var recordedChange = endRecording(changeRecorder, uuidResolver);
    var virtualModel =
        (InternalVirtualModel)
            createAndLoadTestVirtualModelWithConsistencyPreservation(
                getPathToVirtualModelProjectFolder());
    var propagatedChanges = virtualModel.propagateChange(recordedChange);
    var consequentialChanges =
        propagatedChanges.stream()
            .flatMap(pc -> pc.getConsequentialChanges().getEChanges().stream())
            .toList();
    assertEquals(2, consequentialChanges.stream().filter(CreateEObject.class::isInstance).count());
    assertEquals(
        2, consequentialChanges.stream().filter(InsertRootEObject.class::isInstance).count());
    assertEquals(
        1,
        consequentialChanges.stream()
            .filter(ReplaceSingleValuedEReference.class::isInstance)
            .count());
    assertEquals(
        2,
        consequentialChanges.stream()
            .filter(ReplaceSingleValuedEAttribute.class::isInstance)
            .count());
    assertEquals(7, consequentialChanges.size());
  }

  @Test
  @DisplayName("add element to containment of element persisted in two resources")
  void singleChangeForElementContainedInRootElementInMultipleResource() throws Exception {
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    var changeRecorder = new ChangeRecorder(resourceSet);
    changeRecorder.addToRecording(resourceSet);
    changeRecorder.beginRecording();
    var containedInContainedRoot = aet.Root();
    containedInContainedRoot.setId("containedInContained");
    var containedRoot = aet.Root();
    containedRoot.setId("containedRoot");
    containedRoot.setRecursiveRoot(containedInContainedRoot);
    var root = aet.Root();
    root.setId("root");
    root.setRecursiveRoot(containedRoot);
    resourceSet.createResource(createTestModelResourceUri("")).getContents().add(root);
    resourceSet
        .createResource(createTestModelResourceUri("Contained"))
        .getContents()
        .add(containedRoot);
    resourceSet
        .createResource(createTestModelResourceUri("ContainedInContained"))
        .getContents()
        .add(containedInContainedRoot);
    var uuidResolver = UuidResolver.create(resourceSet);
    var recordedChange = endRecording(changeRecorder, uuidResolver);
    var virtualModel =
        (InternalVirtualModel)
            createAndLoadTestVirtualModelWithConsistencyPreservation(
                getPathToVirtualModelProjectFolder());
    var propagatedChanges = virtualModel.propagateChange(recordedChange);
    var consequentialChanges =
        propagatedChanges.stream()
            .flatMap(pc -> pc.getConsequentialChanges().getEChanges().stream())
            .toList();
    assertEquals(3, consequentialChanges.stream().filter(CreateEObject.class::isInstance).count());
    assertEquals(
        3, consequentialChanges.stream().filter(InsertRootEObject.class::isInstance).count());
    assertEquals(
        2,
        consequentialChanges.stream()
            .filter(ReplaceSingleValuedEReference.class::isInstance)
            .count());
    assertEquals(
        3,
        consequentialChanges.stream()
            .filter(ReplaceSingleValuedEAttribute.class::isInstance)
            .count());
    assertEquals(11, consequentialChanges.size());
  }

  @Test
  @DisplayName(
      "load resource that should have been saved after propagating a change into a virtual model")
  void savedVirtualModel() throws Exception {
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    var changeRecorder = new ChangeRecorder(resourceSet);
    changeRecorder.addToRecording(resourceSet);
    changeRecorder.beginRecording();
    var monitoredResource = resourceSet.createResource(createTestModelResourceUri(""));
    var root = aet.Root();
    root.setId("root");
    monitoredResource.getContents().add(root);
    var uuidResolver = UuidResolver.create(resourceSet);
    var recordedChange = endRecording(changeRecorder, uuidResolver);
    var virtualModel =
        (InternalVirtualModel) createAndLoadTestVirtualModel(getPathToVirtualModelProjectFolder());
    virtualModel.propagateChange(recordedChange);
    var reloadedResource =
        withGlobalFactories(new ResourceSetImpl())
            .getResource(createTestModelResourceUri(""), true);
    assertThat(reloadedResource, containsModelOf(monitoredResource));
  }

  @Test
  @DisplayName("reload a virtual model to which a simple change was propagated")
  void reloadVirtualModel() throws Exception {
    ResourceSet resourceSet = withGlobalFactories(new ResourceSetImpl());
    var changeRecorder = new ChangeRecorder(resourceSet);
    changeRecorder.addToRecording(resourceSet);
    changeRecorder.beginRecording();
    var root = aet.Root();
    root.setId("root");
    var monitoredResource = resourceSet.createResource(createTestModelResourceUri(""));
    monitoredResource.getContents().add(root);
    var uuidResolver = UuidResolver.create(resourceSet);
    var recordedChange = endRecording(changeRecorder, uuidResolver);
    var virtualModel =
        (InternalVirtualModel) createAndLoadTestVirtualModel(getPathToVirtualModelProjectFolder());
    virtualModel.propagateChange(recordedChange);
    var originalModel = virtualModel.getModelInstance(createTestModelResourceUri(""));
    var reloadedVirtualModel =
        (InternalVirtualModel) createAndLoadTestVirtualModel(getPathToVirtualModelProjectFolder());
    var reloadedModel = reloadedVirtualModel.getModelInstance(createTestModelResourceUri(""));
    assertThat(reloadedModel.getResource(), containsModelOf(monitoredResource));
    assertNotEquals(originalModel, reloadedModel);
    // Propagate another change to reloaded virtual model to ensure that everything is loaded
    // correctly
    changeRecorder.beginRecording();
    root.setSingleValuedEAttribute(1);
    var secondRecordedChange = endRecording(changeRecorder, uuidResolver);
    var propagatedChange = reloadedVirtualModel.propagateChange(secondRecordedChange);
    assertEquals(1, propagatedChange.size());
  }

  @Test
  @DisplayName(
      "reload a virtual model with consistency preservation to which a simple change was"
          + " propagated")
  void reloadVirtualModelWithConsistency() throws Exception {
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    var changeRecorder = new ChangeRecorder(resourceSet);
    changeRecorder.addToRecording(resourceSet);
    changeRecorder.beginRecording();
    var root = aet.Root();
    root.setId("root");
    var monitoredResource = resourceSet.createResource(createTestModelResourceUri(""));
    monitoredResource.getContents().add(root);
    var uuidResolver = UuidResolver.create(resourceSet);
    var recordedChange = endRecording(changeRecorder, uuidResolver);
    var virtualModel =
        (InternalVirtualModel)
            createAndLoadTestVirtualModelWithConsistencyPreservation(
                getPathToVirtualModelProjectFolder());
    virtualModel.propagateChange(recordedChange);
    var originalModel = virtualModel.getModelInstance(createTestModelResourceUri(""));
    var reloadedVirtualModel =
        (InternalVirtualModel) createAndLoadTestVirtualModel(getPathToVirtualModelProjectFolder());
    var reloadedModel = reloadedVirtualModel.getModelInstance(createTestModelResourceUri(""));
    assertThat(reloadedModel.getResource(), containsModelOf(monitoredResource));
    assertNotEquals(originalModel, reloadedModel);
    var reloadedTargetModel =
        reloadedVirtualModel.getModelInstance(
            RedundancyChangePropagationSpecification.getTargetResourceUri(
                createTestModelResourceUri("")));
    assertThat(reloadedTargetModel.getResource(), containsModelOf(monitoredResource));
    assertEquals(
        1,
        reloadedVirtualModel
            .getCorrespondenceModel()
            .getCorrespondingEObjects(reloadedModel.getResource().getContents().get(0))
            .size());
  }

  @Test
  @DisplayName(
      "move element such that corresponding element is moved from one resource to another and back")
  void moveCorrespondingToOtherResourceAndBack() throws Exception {
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    var changeRecorder = new ChangeRecorder(resourceSet);
    changeRecorder.addToRecording(resourceSet);
    changeRecorder.beginRecording();
    var root = aet.Root();
    root.setId("root");
    var testUri = createTestModelResourceUri("");
    var monitoredResource = resourceSet.createResource(testUri);
    monitoredResource.getContents().add(root);
    var uuidResolver = UuidResolver.create(resourceSet);
    var virtualModel =
        (InternalVirtualModel)
            createAndLoadTestVirtualModelWithConsistencyPreservation(
                getPathToVirtualModelProjectFolder());
    virtualModel.propagateChange(endRecording(changeRecorder, uuidResolver));
    changeRecorder.beginRecording();
    var testIntermediateUri = createTestModelResourceUri("intermediate");
    resourceSet.createResource(testIntermediateUri).getContents().add(root);
    virtualModel.propagateChange(endRecording(changeRecorder, uuidResolver));
    // There must not be the old and the old corresponding model
    assertNull(virtualModel.getModelInstance(testUri));
    assertNull(
        virtualModel.getModelInstance(
            RedundancyChangePropagationSpecification.getTargetResourceUri(testUri)));
    changeRecorder.beginRecording();
    monitoredResource.getContents().add(root);
    virtualModel.propagateChange(endRecording(changeRecorder, uuidResolver));
    assertNull(virtualModel.getModelInstance(testIntermediateUri));
    assertNull(
        virtualModel.getModelInstance(
            RedundancyChangePropagationSpecification.getTargetResourceUri(testIntermediateUri)));
  }

  @SuppressWarnings("null")
  @Test
  @DisplayName("create a view for a virtual model")
  void createView() throws Exception {
    var virtualModel =
        (InternalVirtualModel) createAndLoadTestVirtualModel(getPathToVirtualModelProjectFolder());
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    var uuidResolver = UuidResolver.create(resourceSet);
    createAndPropagateRoot(virtualModel, resourceSet, uuidResolver, ROOT_ID);
    var testView = createTestView(virtualModel);
    // Check initial state:
    assertThat(new HashSet<>(testView.getRootObjects()).isEmpty(), is(false));
    assertEquals(
        claimOne(testView.getRootObjects()), claimOne(testView.getRootObjects(Root.class)));
    assertThat(claimOne(testView.getRootObjects(Root.class)).getId(), is(ROOT_ID));
    assertThat("view source must not have been changed", !testView.isOutdated());
    assertThat("view must not have been modified", !testView.isModified());
  }

  @SuppressWarnings("null")
  @Test
  @DisplayName("update view after a change in the virtual model")
  void updateView() throws Exception {
    var virtualModel =
        (InternalVirtualModel) createAndLoadTestVirtualModel(getPathToVirtualModelProjectFolder());
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    var uuidResolver = UuidResolver.create(resourceSet);
    createAndPropagateRoot(virtualModel, resourceSet, uuidResolver, ROOT_ID);
    var testView = createTestView(virtualModel);

    // Modify model
    virtualModel.propagateChange(
        recordChanges(
            resourceSet,
            uuidResolver,
            () -> {
              var resource = claimOne(resourceSet.getResources());
              Root rootObj = (Root) getFirstRootEObject(resource);
              var nonRoot = aet.NonRoot();
              nonRoot.setId(NON_ROOT_ID);
              rootObj.getMultiValuedContainmentEReference().add(nonRoot);
            }));

    // Assert VSUM changed but view not modified:
    assertThat("view source must have been changed", testView.isOutdated());
    assertThat("view must not have been modified", !testView.isModified());
    assertThat(
        claimOne(testView.getRootObjects(Root.class))
            .getMultiValuedContainmentEReference()
            .isEmpty(),
        is(true));

    // Update view and assert view was updated correctly
    testView.update();
    assertThat("view source must not have been changed", !testView.isOutdated());
    assertThat("view must not have been modified", !testView.isModified());
    @SuppressWarnings("null")
    var viewRoot = claimOne(testView.getRootObjects(Root.class));
    assertThat(claimOne(viewRoot.getMultiValuedContainmentEReference()).getId(), is(NON_ROOT_ID));
  }

  @Test
  @DisplayName("change view and commit changes")
  void commitView() throws Exception {
    var virtualModel =
        (InternalVirtualModel) createAndLoadTestVirtualModel(getPathToVirtualModelProjectFolder());
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    var uuidResolver = UuidResolver.create(resourceSet);
    createAndPropagateRoot(virtualModel, resourceSet, uuidResolver, ROOT_ID);
    var testView = createTestView(virtualModel).withChangeRecordingTrait();

    // Modify view:
    assertThat("view must not have been modified", !testView.isModified());
    @SuppressWarnings("null")
    var rootObj = claimOne(testView.getRootObjects(Root.class));
    var nonRoot = aet.NonRoot();
    nonRoot.setId(NON_ROOT_ID);
    rootObj.getMultiValuedContainmentEReference().add(nonRoot);

    // Assert view modified but VSUM not changed:
    assertThat("view source must not have been changed", !testView.isOutdated());
    assertThat("view must have been modified", testView.isModified());

    // Commit changes and assert VSUM was updated correctly
    testView.commitChanges();
    assertThat("view source must have been changed", testView.isOutdated());
    assertThat("view must not have been modified", !testView.isModified());

    testView.update();
    assertThat("view source must not have been changed", !testView.isOutdated());
    assertThat("view must not have been modified", !testView.isModified());

    @SuppressWarnings("null")
    var reopenedViewRoot = claimOne(createTestView(virtualModel).getRootObjects(Root.class));
    assertThat(
        claimOne(reopenedViewRoot.getMultiValuedContainmentEReference()).getId(), is(NON_ROOT_ID));
  }

  @Test
  @DisplayName("propagate an enqueued change into a virtual model step by step")
  void propagateQueuedChangeIntoVirtualModel() throws Exception {
    var virtualModel =
        (InternalVirtualModel)
            createAndLoadTestVirtualModelWithConsistencyPreservation(
                getPathToVirtualModelProjectFolder());
    var listener = new RecordingChangePropagationListener();
    virtualModel.addChangePropagationListener(listener);
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    var uuidResolver = UuidResolver.create(resourceSet);
    int taskId = enqueueRootCreation(virtualModel, resourceSet, uuidResolver, "");
    var view = createTestView(virtualModel);

    // Enqueuing alone does not touch the models.
    assertTrue(virtualModel.isChangePending(taskId));
    assertNull(virtualModel.getModelInstance(createTestModelResourceUri("")));
    assertFalse(view.isOutdated());

    var steps = propagateQueuedChanges(virtualModel);

    assertEquals(taskId, steps.get(0).taskId());
    assertTrue(steps.stream().allMatch(step -> step.outcome() == Outcome.PROPAGATED));
    assertTrue(virtualModel.propagateNextQueuedChange().isEmpty());
    assertFalse(virtualModel.isChangePending(taskId));
    // Same result as propagating the change synchronously ...
    var sourceModel = virtualModel.getModelInstance(createTestModelResourceUri(""));
    var targetResourceUri =
        RedundancyChangePropagationSpecification.getTargetResourceUri(
            createTestModelResourceUri(""));
    var monitoredResource = claimOne(resourceSet.getResources());
    assertThat(sourceModel.getResource(), containsModelOf(monitoredResource));
    assertThat(
        virtualModel.getModelInstance(targetResourceUri).getResource(),
        containsModelOf(monitoredResource));
    assertEquals(
        1,
        virtualModel
            .getCorrespondenceModel()
            .getCorrespondingEObjects(sourceModel.getResource().getContents().get(0))
            .size());
    // ... saved to disk ...
    var reloadingResourceSet = withGlobalFactories(new ResourceSetImpl());
    assertThat(
        reloadingResourceSet.getResource(createTestModelResourceUri(""), true),
        containsModelOf(monitoredResource));
    assertThat(
        reloadingResourceSet.getResource(targetResourceUri, true),
        containsModelOf(monitoredResource));
    // ... and listeners hear about every step, so views know they are outdated.
    assertEquals(steps.size(), listener.finishedPropagations.size());
    assertEquals(
        steps.stream().flatMap(step -> step.propagatedChanges().stream()).toList(),
        listener.finishedPropagations.stream().flatMap(List::stream).toList());
    assertEquals(0, listener.startedPropagations);
    assertThat("view source must have been changed", view.isOutdated());
  }

  @ParameterizedTest(name = "answered with {0}")
  @ValueSource(booleans = {true, false})
  @DisplayName("defer an enqueued change until the user interaction it needs is answered")
  void deferQueuedChangeUntilUserInteractionIsAnswered(boolean confirmed) throws Exception {
    var virtualModel =
        (InternalVirtualModel)
            createAndLoadTestVirtualModelWithConfirmedConsistencyPreservation(
                getPathToVirtualModelProjectFolder());
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    var uuidResolver = UuidResolver.create(resourceSet);
    int taskId = enqueueRootCreation(virtualModel, resourceSet, uuidResolver, "");

    var stepsBeforeAnswer = propagateQueuedChanges(virtualModel);

    assertEquals(List.of(Outcome.DEFERRED), outcomesOf(stepsBeforeAnswer));
    QueuedChange waitingChange = claimOne(virtualModel.getQueuedChanges());
    assertEquals(taskId, waitingChange.taskId());
    assertEquals(PropagationBlockReason.USER_INTERACTION, waitingChange.blockReason());
    assertEquals(
        PendingUserInteraction.Kind.CONFIRMATION, waitingChange.pendingInteraction().kind());
    assertEquals(
        ConfirmingRedundancyChangePropagationSpecification.QUESTION,
        waitingChange.pendingInteraction().message());

    // Each answer is used once, and propagating the copy, if there is one, asks again.
    virtualModel.registerUserInteractions(taskId, confirmation(confirmed), confirmation(confirmed));
    var stepsAfterAnswer = propagateQueuedChanges(virtualModel);

    assertTrue(stepsAfterAnswer.stream().allMatch(step -> step.outcome() == Outcome.PROPAGATED));
    assertFalse(virtualModel.isChangePending(taskId));
    assertThat(
        virtualModel.getModelInstance(createTestModelResourceUri("")).getResource(),
        containsModelOf(claimOne(resourceSet.getResources())));
    // The copy only exists if the user agreed to it.
    var targetModel =
        virtualModel.getModelInstance(
            RedundancyChangePropagationSpecification.getTargetResourceUri(
                createTestModelResourceUri("")));
    assertEquals(confirmed, targetModel != null);
  }

  @Test
  @DisplayName("mark views outdated when a deferred step has applied an enqueued change")
  void markViewsOutdatedWhenDeferredStepAppliedEnqueuedChange() throws Exception {
    var virtualModel =
        (InternalVirtualModel)
            createAndLoadTestVirtualModelWithConfirmedConsistencyPreservation(
                getPathToVirtualModelProjectFolder());
    var listener = new RecordingChangePropagationListener();
    virtualModel.addChangePropagationListener(listener);
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    var uuidResolver = UuidResolver.create(resourceSet);
    int taskId = enqueueRootCreation(virtualModel, resourceSet, uuidResolver, "");
    var view = createTestView(virtualModel);
    assertFalse(view.isOutdated());

    assertEquals(List.of(Outcome.DEFERRED), outcomesOf(propagateQueuedChanges(virtualModel)));

    // The change waits for the answer, but it is in the models already.
    assertEquals(taskId, claimOne(virtualModel.getQueuedChanges()).taskId());
    assertNotNull(virtualModel.getModelInstance(createTestModelResourceUri("")));
    assertThat("view source must have been changed", view.isOutdated());
    assertEquals(List.of(List.of()), listener.finishedPropagations);

    // Asking again without a matching answer applies nothing, so views stay up to date.
    view.update();
    var unrelatedAnswer = InteractionFactory.eINSTANCE.createConfirmationUserInteraction();
    unrelatedAnswer.setMessage("Some other question?");
    virtualModel.registerUserInteractions(taskId, unrelatedAnswer);
    assertEquals(List.of(Outcome.DEFERRED), outcomesOf(propagateQueuedChanges(virtualModel)));
    assertFalse(view.isOutdated());
    assertEquals(1, listener.finishedPropagations.size());

    virtualModel.registerUserInteractions(taskId, confirmation(true));
    var steps = propagateQueuedChanges(virtualModel);

    // The answer is used up by the step that creates the copy, so propagating the copy asks again.
    // Only the first step of an enqueued change applies it, so this deferral notifies nobody.
    assertEquals(List.of(Outcome.PROPAGATED, Outcome.DEFERRED), outcomesOf(steps));
    assertEquals(2, listener.finishedPropagations.size());
    assertThat("view source must have been changed", view.isOutdated());
  }

  @Test
  @DisplayName("propagate other enqueued changes while one waits for a user interaction")
  void propagateOtherQueuedChangesWhileOneWaitsForUserInteraction() throws Exception {
    var virtualModel =
        (InternalVirtualModel)
            createAndLoadTestVirtualModelWithConfirmedConsistencyPreservation(
                getPathToVirtualModelProjectFolder());
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    var uuidResolver = UuidResolver.create(resourceSet);
    int waitingTaskId = enqueueRootCreation(virtualModel, resourceSet, uuidResolver, "");
    assertEquals(List.of(Outcome.DEFERRED), outcomesOf(propagateQueuedChanges(virtualModel)));
    assertEquals(waitingTaskId, claimOne(virtualModel.getQueuedChanges()).taskId());

    // This change asks nothing, so it must not wait for the answer to the first one.
    int otherTaskId =
        virtualModel.enqueueChange(
            recordChanges(
                resourceSet,
                uuidResolver,
                () ->
                    ((Root) getFirstRootEObject(claimOne(resourceSet.getResources())))
                        .setSingleValuedEAttribute(1)));
    var steps = propagateQueuedChanges(virtualModel);

    assertEquals(List.of(Outcome.SKIPPED_BLOCKED, Outcome.PROPAGATED), outcomesOf(steps));
    assertFalse(virtualModel.isChangePending(otherTaskId));
    assertTrue(virtualModel.isChangePending(waitingTaskId));
    var vsumRoot =
        (Root)
            getFirstRootEObject(
                virtualModel.getModelInstance(createTestModelResourceUri("")).getResource());
    assertEquals(1, vsumRoot.getSingleValuedEAttribute());
  }

  @Test
  @DisplayName("hold back the rest of an enqueued change while one part waits for an answer")
  void holdBackRestOfQueuedChangeWhileOnePartWaitsForUserInteraction() throws Exception {
    var virtualModel =
        (InternalVirtualModel)
            createAndLoadTestVirtualModelWithConfirmedConsistencyPreservation(
                getPathToVirtualModelProjectFolder());
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    var uuidResolver = UuidResolver.create(resourceSet);
    // Two root insertions in one change, and the user is asked before each of them is copied.
    int taskId =
        virtualModel.enqueueChange(
            VitruviusChangeFactory.getInstance()
                .createCompositeChange(
                    List.of(
                        recordRootCreation(resourceSet, uuidResolver, ""),
                        recordRootCreation(resourceSet, uuidResolver, "Second"))));

    assertEquals(List.of(Outcome.DEFERRED), outcomesOf(propagateQueuedChanges(virtualModel)));

    // The second insertion must not overtake the first one, so it waits without asking.
    var queuedChanges = virtualModel.getQueuedChanges();
    assertEquals(2, queuedChanges.size());
    QueuedChange askingChange = queuedChanges.get(0);
    QueuedChange waitingChange = queuedChanges.get(1);
    assertEquals(taskId, askingChange.taskId());
    assertNotNull(askingChange.pendingInteraction());
    assertEquals(taskId, waitingChange.parentTaskId());
    assertEquals(PropagationBlockReason.USER_INTERACTION, waitingChange.blockReason());
    assertNull(waitingChange.pendingInteraction());

    // The answer is used up by the first insertion, so the second one asks once it is its turn.
    virtualModel.registerUserInteractions(taskId, confirmation(false));
    assertEquals(
        List.of(Outcome.PROPAGATED, Outcome.DEFERRED),
        outcomesOf(propagateQueuedChanges(virtualModel)));
    QueuedChange secondAskingChange = claimOne(virtualModel.getQueuedChanges());
    assertEquals(waitingChange.taskId(), secondAskingChange.taskId());
    assertEquals(
        ConfirmingRedundancyChangePropagationSpecification.QUESTION,
        secondAskingChange.pendingInteraction().message());

    virtualModel.registerUserInteractions(taskId, confirmation(false));
    assertEquals(List.of(Outcome.PROPAGATED), outcomesOf(propagateQueuedChanges(virtualModel)));
    assertFalse(virtualModel.isChangePending(taskId));
  }

  @Test
  @DisplayName("keep the rest of an enqueued change queued if a listener fails after a step")
  void keepRestOfQueuedChangeIfListenerFailsAfterStep() throws Exception {
    var virtualModel =
        (InternalVirtualModel)
            createAndLoadTestVirtualModelWithConsistencyPreservation(
                getPathToVirtualModelProjectFolder());
    var failure = new IllegalStateException("listener failed");
    var failed = new AtomicBoolean();
    virtualModel.addChangePropagationListener(
        new ChangePropagationListener() {
          @Override
          public void startedChangePropagation(VitruviusChange<Uuid> changeToPropagate) {}

          @Override
          public void finishedChangePropagation(Iterable<PropagatedChange> propagatedChanges) {
            if (failed.compareAndSet(false, true)) {
              throw failure;
            }
          }
        });
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    var uuidResolver = UuidResolver.create(resourceSet);
    int taskId =
        virtualModel.enqueueChange(
            VitruviusChangeFactory.getInstance()
                .createCompositeChange(
                    List.of(
                        recordRootCreation(resourceSet, uuidResolver, ""),
                        recordRootCreation(resourceSet, uuidResolver, "Second"))));

    var thrown = assertThrows(IllegalStateException.class, virtualModel::propagateNextQueuedChange);

    // The listener failed after the first insertion had been propagated, so the change is not
    // dropped, and its second insertion goes on in the next steps.
    assertSame(failure, thrown);
    assertTrue(virtualModel.isChangePending(taskId));
    assertNotNull(virtualModel.getModelInstance(createTestModelResourceUri("")));
    var steps = propagateQueuedChanges(virtualModel);
    assertTrue(steps.stream().allMatch(step -> step.outcome() == Outcome.PROPAGATED));
    assertFalse(virtualModel.isChangePending(taskId));
    assertNotNull(virtualModel.getModelInstance(createTestModelResourceUri("Second")));
  }

  @Test
  @DisplayName("change the propagation mode only while no changes are queued")
  void setChangePropagationModeOnlyWithoutQueuedChanges() throws Exception {
    var virtualModel =
        (InternalVirtualModel)
            createAndLoadTestVirtualModelWithConsistencyPreservation(
                getPathToVirtualModelProjectFolder());
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    var uuidResolver = UuidResolver.create(resourceSet);
    enqueueRootCreation(virtualModel, resourceSet, uuidResolver, "");

    assertThrows(
        IllegalStateException.class,
        () -> virtualModel.setChangePropagationMode(ChangePropagationMode.SINGLE_STEP));

    // Transitive by default: the copy created in the first step is propagated in a second one.
    assertEquals(
        List.of(Outcome.PROPAGATED, Outcome.PROPAGATED),
        outcomesOf(propagateQueuedChanges(virtualModel)));

    virtualModel.setChangePropagationMode(ChangePropagationMode.SINGLE_STEP);
    enqueueRootCreation(virtualModel, resourceSet, uuidResolver, "Second");

    // Now the copy is still created, but not propagated any further.
    assertEquals(List.of(Outcome.PROPAGATED), outcomesOf(propagateQueuedChanges(virtualModel)));
    assertNotNull(
        virtualModel.getModelInstance(
            RedundancyChangePropagationSpecification.getTargetResourceUri(
                createTestModelResourceUri("Second"))));
  }

  @Test
  @DisplayName("reject enqueuing a null change or a change without concrete changes")
  void enqueueInvalidChange() throws Exception {
    var virtualModel = createAndLoadTestVirtualModel(getPathToVirtualModelProjectFolder());
    VitruviusChange<Uuid> emptyChange =
        VitruviusChangeFactory.getInstance().createTransactionalChange(Set.of());

    assertThrows(NullPointerException.class, () -> virtualModel.enqueueChange(null));
    assertThrows(IllegalArgumentException.class, () -> virtualModel.enqueueChange(emptyChange));
    assertThrows(IllegalArgumentException.class, () -> virtualModel.enqueueChange(emptyChange, 42));
    assertTrue(virtualModel.getQueuedChanges().isEmpty());
  }

  private org.eclipse.emf.common.util.URI createTestModelResourceUri(String suffix) {
    return VirtualModelTestUtil.createTestModelResourceUri(projectFolder, suffix);
  }

  private Path getPathToVirtualModelProjectFolder() {
    return projectFolder.resolve("vsum");
  }

  private VitruviusChange<Uuid> endRecording(
      ChangeRecorder changeRecorder, UuidResolver uuidResolver) {
    var change = changeRecorder.endRecording();
    var changeResolver = VitruviusChangeResolverFactory.forUuids(uuidResolver);
    return changeResolver.assignIds(change);
  }

  private void createAndPropagateRoot(
      VirtualModel virtualModel,
      ResourceSet resourceSet,
      UuidResolver uuidResolver,
      String rootId) {
    virtualModel.propagateChange(
        recordChanges(
            resourceSet,
            uuidResolver,
            () -> {
              var resource = resourceSet.createResource(createTestModelResourceUri(""));
              var root = aet.Root();
              root.setId(rootId);
              resource.getContents().add(root);
            }));
  }

  private int enqueueRootCreation(
      VirtualModel virtualModel,
      ResourceSet resourceSet,
      UuidResolver uuidResolver,
      String uriSuffix) {
    return virtualModel.enqueueChange(recordRootCreation(resourceSet, uuidResolver, uriSuffix));
  }

  private VitruviusChange<Uuid> recordRootCreation(
      ResourceSet resourceSet, UuidResolver uuidResolver, String uriSuffix) {
    return recordChanges(
        resourceSet,
        uuidResolver,
        () -> {
          var resource = resourceSet.createResource(createTestModelResourceUri(uriSuffix));
          var root = aet.Root();
          root.setId(ROOT_ID);
          resource.getContents().add(root);
        });
  }

  /** Takes propagation steps the way a worker would: while any queued change is not blocked. */
  private static List<QueuedPropagationStep> propagateQueuedChanges(VirtualModel virtualModel) {
    List<QueuedPropagationStep> steps = new ArrayList<>();
    while (virtualModel.getQueuedChanges().stream().anyMatch(change -> !change.blocked())) {
      steps.add(virtualModel.propagateNextQueuedChange().orElseThrow());
    }
    return steps;
  }

  private static List<Outcome> outcomesOf(List<QueuedPropagationStep> steps) {
    return steps.stream().map(QueuedPropagationStep::outcome).toList();
  }

  private static UserInteractionBase confirmation(boolean confirmed) {
    ConfirmationUserInteraction answer =
        InteractionFactory.eINSTANCE.createConfirmationUserInteraction();
    answer.setMessage(ConfirmingRedundancyChangePropagationSpecification.QUESTION);
    answer.setConfirmed(confirmed);
    return answer;
  }

  /** Remembers the notifications it receives. */
  private static class RecordingChangePropagationListener implements ChangePropagationListener {
    private int startedPropagations;
    private final List<List<PropagatedChange>> finishedPropagations = new ArrayList<>();

    @Override
    public void startedChangePropagation(VitruviusChange<Uuid> changeToPropagate) {
      startedPropagations++;
    }

    @Override
    public void finishedChangePropagation(Iterable<PropagatedChange> propagatedChanges) {
      List<PropagatedChange> changes = new ArrayList<>();
      propagatedChanges.forEach(changes::add);
      finishedPropagations.add(changes);
    }
  }

  @SuppressWarnings("null")
  private static View createTestView(VirtualModel virtualModel) {
    ViewType<?> viewType =
        checkNotNull(ViewTypeFactory.createIdentityMappingViewType(""), "cannot create view type");
    var selector =
        checkNotNull(
            ((InternalVirtualModel) virtualModel).createSelector(viewType),
            "cannot create selector");

    selector.getSelectableElements().forEach(element -> selector.setSelected(element, true));
    return checkNotNull(selector.createView(), "Cannot create view from selector!");
  }
}
