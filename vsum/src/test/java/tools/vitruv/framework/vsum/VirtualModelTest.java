package tools.vitruv.framework.vsum;

import java.nio.file.Path;
import java.util.HashSet;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.google.common.base.Preconditions.checkNotNull;

import allElementTypes.Root;
import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceUtil.getFirstRootEObject;
import tools.vitruv.change.atomic.eobject.CreateEObject;
import tools.vitruv.change.atomic.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.change.atomic.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.change.atomic.root.InsertRootEObject;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeResolverFactory;
import tools.vitruv.change.composite.recording.ChangeRecorder;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.containsModelOf;
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.ViewTypeFactory;
import tools.vitruv.framework.vsum.VirtualModelTestUtil.RedundancyChangePropagationSpecification;
import static tools.vitruv.framework.vsum.VirtualModelTestUtil.createAndLoadTestVirtualModel;
import static tools.vitruv.framework.vsum.VirtualModelTestUtil.createAndLoadTestVirtualModelWithConsistencyPreservation;
import static tools.vitruv.framework.vsum.VirtualModelTestUtil.recordChanges;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

/**
 * Tests for virtual models.
 */
@ExtendWith(TestProjectManager.class)
public class VirtualModelTest {
    private static final String NON_ROOT_ID = "NonRootId";
    private static final String ROOT_ID = "RootId";

    private Path projectFolder;

    @BeforeEach
    public void initializeProjectFolder(@TestProject Path projectFolder) {
        this.projectFolder = projectFolder;
    }

    @Test
    @DisplayName("propagate a simple change into a virtual model")
    public void propagateIntoVirtualModel() throws Exception {
        InternalVirtualModel virtualModel = (InternalVirtualModel) createAndLoadTestVirtualModel(
                getPathToVirtualModelProjectFolder());
        var resourceSet = withGlobalFactories(new ResourceSetImpl());
        var uuidResolver = UuidResolver.create(resourceSet);
        var changeRecorder = new ChangeRecorder(resourceSet);
        changeRecorder.addToRecording(resourceSet);
        changeRecorder.beginRecording();
        var monitoredResource = resourceSet.createResource(createTestModelResourceUri(""));
        var root = aet.Root();
        root.setId("root");
        monitoredResource.getContents().add(root);
        var recordedChange = endRecording(changeRecorder, uuidResolver);
        virtualModel.propagateChange(recordedChange);
        var vsumModel = virtualModel.getModelInstance(createTestModelResourceUri(""));
        assertThat(vsumModel.getResource(), containsModelOf(monitoredResource));
    }

    @Test
    @DisplayName("propagate a simple change into a virtual model and preserve consistency")
    public void propagateIntoVirtualModelWithConsistency() throws Exception {
        var virtualModel =
                (InternalVirtualModel) createAndLoadTestVirtualModelWithConsistencyPreservation(
                        getPathToVirtualModelProjectFolder());
        var resourceSet = withGlobalFactories(new ResourceSetImpl());
        var uuidResolver = UuidResolver.create(resourceSet);
        var changeRecorder = new ChangeRecorder(resourceSet);
        changeRecorder.addToRecording(resourceSet);
        changeRecorder.beginRecording();
        var monitoredResource = resourceSet.createResource(createTestModelResourceUri(""));
        var root = aet.Root();
        root.setId("root");
        monitoredResource.getContents().add(root);
        var recordedChange = endRecording(changeRecorder, uuidResolver);
        virtualModel.propagateChange(recordedChange);
        var sourceModel = virtualModel.getModelInstance(createTestModelResourceUri(""));
        var targetModel = virtualModel.getModelInstance(RedundancyChangePropagationSpecification
                .getTargetResourceUri(createTestModelResourceUri("")));
        assertThat(targetModel.getResource(), containsModelOf(monitoredResource));
        assertEquals(1, virtualModel.getCorrespondenceModel()
                .getCorrespondingEObjects(sourceModel.getResource().getContents().get(0)).size());
    }

    @Test
    @DisplayName("persist element as resource root also contained in other persisted element")
    public void singleChangeForRootElementInMultipleResource() throws Exception {
        var virtualModel =
                (InternalVirtualModel) createAndLoadTestVirtualModelWithConsistencyPreservation(
                        getPathToVirtualModelProjectFolder());
        var resourceSet = withGlobalFactories(new ResourceSetImpl());
        var uuidResolver = UuidResolver.create(resourceSet);
        var changeRecorder = new ChangeRecorder(resourceSet);
        changeRecorder.addToRecording(resourceSet);
        changeRecorder.beginRecording();
        var containedRoot = aet.Root();
        containedRoot.setId("containedRoot");
        var root = aet.Root();
        root.setId("root");
        root.setRecursiveRoot(containedRoot);
        resourceSet.createResource(createTestModelResourceUri("")).getContents().add(root);
        resourceSet.createResource(createTestModelResourceUri("Contained")).getContents()
                .add(containedRoot);
        var recordedChange = endRecording(changeRecorder, uuidResolver);
        var propagatedChanges = virtualModel.propagateChange(recordedChange);
        var consequentialChanges = propagatedChanges.stream()
                .flatMap(pc -> pc.getConsequentialChanges().getEChanges().stream()).toList();
        assertEquals(2,
                consequentialChanges.stream().filter(c -> c instanceof CreateEObject).count());
        assertEquals(2,
                consequentialChanges.stream().filter(c -> c instanceof InsertRootEObject).count());
        assertEquals(1, consequentialChanges.stream()
                .filter(c -> c instanceof ReplaceSingleValuedEReference).count());
        assertEquals(2, consequentialChanges.stream()
                .filter(c -> c instanceof ReplaceSingleValuedEAttribute).count());
        assertEquals(7, consequentialChanges.size());
    }

    @Test
    @DisplayName("add element to containment of element persisted in two resources")
    public void singleChangeForElementContainedInRootElementInMultipleResource() throws Exception {
        var virtualModel =
                (InternalVirtualModel) createAndLoadTestVirtualModelWithConsistencyPreservation(
                        getPathToVirtualModelProjectFolder());
        var resourceSet = withGlobalFactories(new ResourceSetImpl());
        var uuidResolver = UuidResolver.create(resourceSet);
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
        resourceSet.createResource(createTestModelResourceUri("Contained")).getContents()
                .add(containedRoot);
        resourceSet.createResource(createTestModelResourceUri("ContainedInContained")).getContents()
                .add(containedInContainedRoot);
        var recordedChange = endRecording(changeRecorder, uuidResolver);
        var propagatedChanges = virtualModel.propagateChange(recordedChange);
        var consequentialChanges = propagatedChanges.stream()
                .flatMap(pc -> pc.getConsequentialChanges().getEChanges().stream()).toList();
        assertEquals(3,
                consequentialChanges.stream().filter(c -> c instanceof CreateEObject).count());
        assertEquals(3,
                consequentialChanges.stream().filter(c -> c instanceof InsertRootEObject).count());
        assertEquals(2, consequentialChanges.stream()
                .filter(c -> c instanceof ReplaceSingleValuedEReference).count());
        assertEquals(3, consequentialChanges.stream()
                .filter(c -> c instanceof ReplaceSingleValuedEAttribute).count());
        assertEquals(11, consequentialChanges.size());
    }

    @Test
    @DisplayName("load resource that should have been saved after propagating a change into a virtual model")
    public void savedVirtualModel() throws Exception {
        var virtualModel = (InternalVirtualModel) createAndLoadTestVirtualModel(
                getPathToVirtualModelProjectFolder());
        var resourceSet = withGlobalFactories(new ResourceSetImpl());
        var uuidResolver = UuidResolver.create(resourceSet);
        var changeRecorder = new ChangeRecorder(resourceSet);
        changeRecorder.addToRecording(resourceSet);
        changeRecorder.beginRecording();
        var monitoredResource = resourceSet.createResource(createTestModelResourceUri(""));
        var root = aet.Root();
        root.setId("root");
        monitoredResource.getContents().add(root);
        var recordedChange = endRecording(changeRecorder, uuidResolver);
        virtualModel.propagateChange(recordedChange);
        var reloadedResource = withGlobalFactories(new ResourceSetImpl())
                .getResource(createTestModelResourceUri(""), true);
        assertThat(reloadedResource, containsModelOf(monitoredResource));
    }

    @Test
    @DisplayName("reload a virtual model to which a simple change was propagated")
    public void reloadVirtualModel() throws Exception {
        var virtualModel = (InternalVirtualModel) createAndLoadTestVirtualModel(
                getPathToVirtualModelProjectFolder());
        ResourceSet resourceSet = withGlobalFactories(new ResourceSetImpl());
        var uuidResolver = UuidResolver.create(resourceSet);
        var changeRecorder = new ChangeRecorder(resourceSet);
        changeRecorder.addToRecording(resourceSet);
        changeRecorder.beginRecording();
        var root = aet.Root();
        root.setId("root");
        var monitoredResource = resourceSet.createResource(createTestModelResourceUri(""));
        monitoredResource.getContents().add(root);
        var recordedChange = endRecording(changeRecorder, uuidResolver);
        virtualModel.propagateChange(recordedChange);
        var originalModel = virtualModel.getModelInstance(createTestModelResourceUri(""));
        var reloadedVirtualModel = (InternalVirtualModel) createAndLoadTestVirtualModel(
                getPathToVirtualModelProjectFolder());
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
    @DisplayName("reload a virtual model with consistency preservation to which a simple change was propagated")
    public void reloadVirtualModelWithConsistency() throws Exception {
        var virtualModel =
                (InternalVirtualModel) createAndLoadTestVirtualModelWithConsistencyPreservation(
                        getPathToVirtualModelProjectFolder());
        var resourceSet = withGlobalFactories(new ResourceSetImpl());
        var uuidResolver = UuidResolver.create(resourceSet);
        var changeRecorder = new ChangeRecorder(resourceSet);
        changeRecorder.addToRecording(resourceSet);
        changeRecorder.beginRecording();
        var root = aet.Root();
        root.setId("root");
        var monitoredResource = resourceSet.createResource(createTestModelResourceUri(""));
        monitoredResource.getContents().add(root);
        var recordedChange = endRecording(changeRecorder, uuidResolver);
        virtualModel.propagateChange(recordedChange);
        var originalModel = virtualModel.getModelInstance(createTestModelResourceUri(""));
        var reloadedVirtualModel = (InternalVirtualModel) createAndLoadTestVirtualModel(
                getPathToVirtualModelProjectFolder());
        var reloadedModel = reloadedVirtualModel.getModelInstance(createTestModelResourceUri(""));
        assertThat(reloadedModel.getResource(), containsModelOf(monitoredResource));
        assertNotEquals(originalModel, reloadedModel);
        var reloadedTargetModel =
                reloadedVirtualModel.getModelInstance(RedundancyChangePropagationSpecification
                        .getTargetResourceUri(createTestModelResourceUri("")));
        assertThat(reloadedTargetModel.getResource(), containsModelOf(monitoredResource));
        assertEquals(1, reloadedVirtualModel.getCorrespondenceModel()
                .getCorrespondingEObjects(reloadedModel.getResource().getContents().get(0)).size());
    }

    @Test
    @DisplayName("move element such that corresponding element is moved from one resource to another and back")
    public void moveCorrespondingToOtherResourceAndBack() throws Exception {
        var virtualModel =
                (InternalVirtualModel) createAndLoadTestVirtualModelWithConsistencyPreservation(
                        getPathToVirtualModelProjectFolder());
        var resourceSet = withGlobalFactories(new ResourceSetImpl());
        var uuidResolver = UuidResolver.create(resourceSet);
        var changeRecorder = new ChangeRecorder(resourceSet);
        changeRecorder.addToRecording(resourceSet);
        changeRecorder.beginRecording();
        var root = aet.Root();
        root.setId("root");
        var testUri = createTestModelResourceUri("");
        var monitoredResource = resourceSet.createResource(testUri);
        monitoredResource.getContents().add(root);
        virtualModel.propagateChange(endRecording(changeRecorder, uuidResolver));
        changeRecorder.beginRecording();
        var testIntermediateUri = createTestModelResourceUri("intermediate");
        resourceSet.createResource(testIntermediateUri).getContents().add(root);
        virtualModel.propagateChange(endRecording(changeRecorder, uuidResolver));
        // There must not be the old and the old corresponding model
        assertNull(virtualModel.getModelInstance(testUri));
        assertNull(virtualModel.getModelInstance(
                RedundancyChangePropagationSpecification.getTargetResourceUri(testUri)));
        changeRecorder.beginRecording();
        monitoredResource.getContents().add(root);
        virtualModel.propagateChange(endRecording(changeRecorder, uuidResolver));
        assertNull(virtualModel.getModelInstance(testIntermediateUri));
        assertNull(virtualModel.getModelInstance(RedundancyChangePropagationSpecification
                .getTargetResourceUri(testIntermediateUri)));
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("create a view for a virtual model")
    public void createView() throws Exception {
        var virtualModel = (InternalVirtualModel) createAndLoadTestVirtualModel(
                getPathToVirtualModelProjectFolder());
        var resourceSet = withGlobalFactories(new ResourceSetImpl());
        var uuidResolver = UuidResolver.create(resourceSet);
        createAndPropagateRoot(virtualModel, resourceSet, uuidResolver, ROOT_ID);
        var testView = createTestView(virtualModel);
        // Check initial state:
        assertThat(new HashSet<>(testView.getRootObjects()).isEmpty(), is(false));
        assertEquals(claimOne(testView.getRootObjects()),
                claimOne(testView.getRootObjects(Root.class)));
        assertThat(claimOne(testView.getRootObjects(Root.class)).getId(), is(ROOT_ID));
        assertThat("view source must not have been changed", !testView.isOutdated());
        assertThat("view must not have been modified", !testView.isModified());
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("update view after a change in the virtual model")
    public void updateView() throws Exception {
        var virtualModel = (InternalVirtualModel) createAndLoadTestVirtualModel(
                getPathToVirtualModelProjectFolder());
        var resourceSet = withGlobalFactories(new ResourceSetImpl());
        var uuidResolver = UuidResolver.create(resourceSet);
        createAndPropagateRoot(virtualModel, resourceSet, uuidResolver, ROOT_ID);
        var testView = createTestView(virtualModel);

        // Modify model
        virtualModel.propagateChange(recordChanges(resourceSet, uuidResolver, () -> {
            var resource = claimOne(resourceSet.getResources());
            Root rootObj = (Root) getFirstRootEObject(resource);
            var nonRoot = aet.NonRoot();
            nonRoot.setId(NON_ROOT_ID);
            rootObj.getMultiValuedContainmentEReference().add(nonRoot);
        }));

        // Assert VSUM changed but view not modified:
        assertThat("view source must have been changed", testView.isOutdated());
        assertThat("view must not have been modified", !testView.isModified());
        assertThat(claimOne(testView.getRootObjects(Root.class))
                .getMultiValuedContainmentEReference().isEmpty(), is(true));

        // Update view and assert view was updated correctly
        testView.update();
        assertThat("view source must not have been changed", !testView.isOutdated());
        assertThat("view must not have been modified", !testView.isModified());
        @SuppressWarnings("null")
        var viewRoot = claimOne(testView.getRootObjects(Root.class));
        assertThat(claimOne(viewRoot.getMultiValuedContainmentEReference()).getId(),
                is(NON_ROOT_ID));
    }

    @Test
    @DisplayName("change view and commit changes")
    public void commitView() throws Exception {
        var virtualModel = (InternalVirtualModel) createAndLoadTestVirtualModel(
                getPathToVirtualModelProjectFolder());
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
        assertThat(claimOne(reopenedViewRoot.getMultiValuedContainmentEReference()).getId(),
                is(NON_ROOT_ID));
    }

    private org.eclipse.emf.common.util.URI createTestModelResourceUri(String suffix) {
        return VirtualModelTestUtil.createTestModelResourceUri(projectFolder, suffix);
    }

    private Path getPathToVirtualModelProjectFolder() {
        return projectFolder.resolve("vsum");
    }

    private VitruviusChange<Uuid> endRecording(ChangeRecorder changeRecorder,
            UuidResolver uuidResolver) {
        var change = changeRecorder.endRecording();
        var changeResolver = VitruviusChangeResolverFactory.forUuids(uuidResolver);
        return changeResolver.assignIds(change);
    }

    private void createAndPropagateRoot(VirtualModel virtualModel, ResourceSet resourceSet,
            UuidResolver uuidResolver, String rootId) {
        virtualModel.propagateChange(recordChanges(resourceSet, uuidResolver, () -> {
            var resource = resourceSet.createResource(createTestModelResourceUri(""));
            var root = aet.Root();
            root.setId(rootId);
            resource.getContents().add(root);
        }));
    }

    @SuppressWarnings("null")
    private static View createTestView(VirtualModel virtualModel) {
        ViewType<?> viewType = checkNotNull(ViewTypeFactory.createIdentityMappingViewType(""),
                "cannot create view type");
        var selector = checkNotNull(((InternalVirtualModel) virtualModel).createSelector(viewType),
                "cannot create selector");

        selector.getSelectableElements().forEach(element -> selector.setSelected(element, true));
        return checkNotNull(selector.createView(), "Cannot create view from selector!");
    }
}
