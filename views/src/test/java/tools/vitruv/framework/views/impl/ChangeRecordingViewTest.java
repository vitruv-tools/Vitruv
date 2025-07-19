package tools.vitruv.framework.views.impl;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.equalsDeeply;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.ignoringFeatures;
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet;

import allElementTypes.AllElementTypesPackage;
import allElementTypes.NonRoot;
import allElementTypes.Root;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.eobject.CreateEObject;
import tools.vitruv.change.atomic.eobject.DeleteEObject;
import tools.vitruv.change.atomic.feature.attribute.AttributeFactory;
import tools.vitruv.change.atomic.feature.attribute.AttributePackage;
import tools.vitruv.change.atomic.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.change.atomic.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.atomic.root.InsertRootEObject;
import tools.vitruv.change.atomic.root.RootFactory;
import tools.vitruv.change.atomic.root.RootPackage;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeResolverFactory;
import tools.vitruv.change.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.change.testutils.TestLogging;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ModifiableViewSelection;

/** Tests for the {@link ChangeRecordingView} class. */
@ExtendWith({ TestLogging.class, RegisterMetamodelsInStandalone.class })
public class ChangeRecordingViewTest {
  @Mock
  ViewCreatingViewType<?, HierarchicalId> mockViewType;
  @Mock
  ChangeableViewSource mockChangeableViewSource;
  @Mock
  ModifiableViewSelection mockViewSelection;

  /** Initialize the mocks before each test. */
  @BeforeEach
  public void initializeMocks() {
    MockitoAnnotations.openMocks(this);
  }

  /** Tests for the constructor of the {@link ChangeRecordingView} class. */
  @Nested
  @DisplayName("initialize")
  class Initialize {
    @Test
    @DisplayName("with null view")
    void withNullViewType() {
      assertThrows(IllegalArgumentException.class, () -> new ChangeRecordingView(null));
    }

    @Test
    @DisplayName("with proper arguments")
    void withEmptySource() throws Exception {
      try (ChangeRecordingView view = new ChangeRecordingView(
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection))) {
        assertThat(view.isClosed(), is(false));
        assertThat(view.getRootObjects(), not(hasItem(anything())));
      }
    }
  }

  /**
   * Tests for the {@link ChangeRecordingView#modifyContents(Consumer)} method.
   */
  @Nested
  @DisplayName("retrieve roots")
  class RetrieveRootElements {
    @Test
    @DisplayName("all of same type")
    void allOfSameType() throws Exception {
      try (ChangeRecordingView view = new ChangeRecordingView(
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection))) {
        Root root = aet.Root();
        view.registerRoot(root, URI.createURI("test://test.aet"));
        Root root2 = aet.Root();
        view.registerRoot(root2, URI.createURI("test://test2.aet"));
        assertThat(view.getRootObjects().size(), is(2));
        assertThat(view.getRootObjects(Root.class).size(), is(2));
        assertThat(view.getRootObjects(), hasItem(root));
        assertThat(view.getRootObjects(Root.class), hasItem(root2));
        assertThat(view.getRootObjects(), hasItem(root));
        assertThat(view.getRootObjects(Root.class), hasItem(root2));
      }
    }

    @Test
    @DisplayName("all of one out of two types")
    void containingAllOfOneType() throws Exception {
      try (ChangeRecordingView view = new ChangeRecordingView(
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection))) {
        Root root = aet.Root();
        view.registerRoot(root, URI.createURI("test://test.aet"));
        NonRoot otherRoot = aet.NonRoot();
        view.registerRoot(otherRoot, URI.createURI("test://test2.aet"));
        assertThat(view.getRootObjects().size(), is(2));
        assertThat(view.getRootObjects(Root.class).size(), is(1));
        assertThat(view.getRootObjects(), hasItem(root));
        assertThat(view.getRootObjects(Root.class), hasItem(root));
        assertThat(view.getRootObjects(), hasItem(otherRoot));
      }
    }

    @Test
    @DisplayName("containing none of a type")
    void containingNoneOfType() throws Exception {
      try (ChangeRecordingView view = new ChangeRecordingView(
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection))) {
        Root root = aet.Root();
        view.registerRoot(root, URI.createURI("test://test.aet"));
        Root otherRoot = aet.Root();
        view.registerRoot(otherRoot, URI.createURI("test://test2.aet"));
        assertThat(view.getRootObjects().size(), is(2));
        assertThat(view.getRootObjects(), hasItem(root));
        assertThat(view.getRootObjects(), hasItem(otherRoot));
        assertThat(view.getRootObjects(NonRoot.class), not(hasItem(anything())));
      }
    }
  }

  /**
   * Tests for the {@link ChangeRecordingView#modifyContents(Consumer)} method.
   */
  @Nested
  @DisplayName("update")
  class Update {
    @Test
    @DisplayName("without previous modification")
    void withoutPreviousModification() throws Exception {
      try (ChangeRecordingView view = new ChangeRecordingView(
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection))) {
        view.update();
      }
    }

    @Test
    @DisplayName("with previous modification")
    void withPreviousModification() throws Exception {
      try (ChangeRecordingView view = new ChangeRecordingView(
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection))) {
        view.modifyContents(
            (resourceSet) -> resourceSet.createResource(URI.createURI("test://test.aet")));
        assertThrows(IllegalStateException.class, () -> view.update());
      }
    }
  }

  /** Tests for the {@link ChangeRecordingView#registerRoot(Root, URI)} method. */
  @Nested
  @DisplayName("add root")
  class AddRoot {
    @Test
    @DisplayName("being null")
    void nullElement() throws Exception {
      try (ChangeRecordingView view = new ChangeRecordingView(
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection))) {
        URI testUri = URI.createURI("test://test.aet");
        assertThrows(
            IllegalArgumentException.class,
            () -> view.registerRoot(null, testUri));
      }
    }

    @Test
    @DisplayName("with null URI")
    void nullUri() throws Exception {
      try (ChangeRecordingView view = new ChangeRecordingView(
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection))) {
        Root root = aet.Root();
        assertThrows(IllegalArgumentException.class, () -> view.registerRoot(root, null));
      }
    }

    @Test
    @DisplayName("with proper arguments")
    void properArguments() throws Exception {
      try (ChangeRecordingView view = new ChangeRecordingView(
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection))) {
        Root root = aet.Root();
        String testResourceUriString = "test://test.aet";
        view.registerRoot(root, URI.createURI(testResourceUriString));
        assertThat(view.getRootObjects(), hasItem(root));
      }
    }

    @Test
    @DisplayName("committing changes")
    void commitChanges() throws Exception {
      try (ChangeRecordingView view = new ChangeRecordingView(
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection))) {
        Root root = aet.Root();
        root.setId("root");
        String testResourceUriString = "test://test.aet";
        view.registerRoot(root, URI.createURI(testResourceUriString));
        assertThat(view.getRootObjects(), hasItem(root));
        ArgumentCaptor<ChangeRecordingView> viewArgument = ArgumentCaptor.forClass(ChangeRecordingView.class);
        ArgumentCaptor<VitruviusChange<HierarchicalId>> changeArgument = ArgumentCaptor.forClass(VitruviusChange.class);
        view.commitChanges();
        verify(mockViewType).commitViewChanges(viewArgument.capture(), changeArgument.capture());

        assertThat(viewArgument.getValue(), is(view));
        ResourceSet resolveInResourceSet = new ResourceSetImpl();
        VitruviusChange<EObject> resolvedChange = VitruviusChangeResolverFactory
            .forHierarchicalIds(resolveInResourceSet)
            .resolveAndApply(changeArgument.getValue());
        InsertRootEObject<EObject> expectedChange = RootFactory.eINSTANCE.createInsertRootEObject();
        expectedChange.setNewValue(root);
        expectedChange.setUri(testResourceUriString);
        assertThat(resolvedChange.getEChanges().size(), is(3)); // Create, Insert, ReplaceId
        assertThat(
            resolvedChange.getEChanges().get(1),
            equalsDeeply(
                expectedChange, ignoringFeatures(RootPackage.eINSTANCE.getRootEChange_Resource())));
        assertThat(
            resolvedChange.getEChanges().get(2), instanceOf(ReplaceSingleValuedEAttribute.class));
      }
    }
  }

  /** Tests for the {@link ChangeRecordingView#moveRoot(Root, URI)} method. */
  @Nested
  @DisplayName("move root")
  class MoveRoot {
    @Test
    @DisplayName("being null")
    void nullElement() throws Exception {
      try (ChangeRecordingView view = new ChangeRecordingView(
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection))) {
        URI testUri = URI.createURI("test://test.aet");
        assertThrows(
            IllegalArgumentException.class,
            () -> view.moveRoot(null, testUri));
      }
    }

    @Test
    @DisplayName("with null URI")
    void nullUri() throws Exception {
      try (ChangeRecordingView view = new ChangeRecordingView(
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection))) {
        Root root = aet.Root();
        view.registerRoot(root, URI.createURI("test://test.aet"));
        assertThrows(IllegalArgumentException.class, () -> view.moveRoot(root, null));
      }
    }

    @Test
    @DisplayName("with element not beeing root")
    void notBeingRoot() throws Exception {
      try (ChangeRecordingView view = new ChangeRecordingView(
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection))) {
        Root root = aet.Root();
        URI testUri = URI.createURI("test://test.aet");
        assertThrows(
            IllegalStateException.class,
            () -> view.moveRoot(root, testUri));
      }
    }

    @Test
    @DisplayName("with proper arguments")
    void properArguments() throws Exception {
      try (ChangeRecordingView view = new ChangeRecordingView(
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection))) {
        Root root = aet.Root();
        view.registerRoot(root, URI.createURI("test://test.aet"));
        view.moveRoot(root, URI.createURI("test://test2.aet"));
        assertThat(view.getRootObjects().size(), is(1));
        assertThat(view.getRootObjects(), hasItem(root));
      }
    }

    @Test
    @DisplayName("committing changes")
    void commitChanges() throws Exception {
      try (ChangeRecordingView view = new ChangeRecordingView(
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection))) {
        Root root = aet.Root();
        view.registerRoot(root, URI.createURI("test://test.aet"));
        view.commitChanges();
        reset(mockChangeableViewSource, mockViewType);

        ResourceSet resolveInResourceSet = new ResourceSetImpl();
        resolveInResourceSet.createResource(root.eResource().getURI());
        resolveInResourceSet
            .getResource(root.eResource().getURI(), false)
            .getContents()
            .add(EcoreUtil.copy(root));

        String movedResourceUriString = "test://test2.aet";
        view.moveRoot(root, URI.createURI(movedResourceUriString));
        assertThat(view.getRootObjects(), hasItem(root));
        ArgumentCaptor<ChangeRecordingView> viewArgument = ArgumentCaptor.forClass(ChangeRecordingView.class);
        ArgumentCaptor<VitruviusChange<HierarchicalId>> changeArgument = ArgumentCaptor.forClass(VitruviusChange.class);
        view.commitChanges();
        verify(mockViewType).commitViewChanges(viewArgument.capture(), changeArgument.capture());
        assertThat(viewArgument.getValue(), is(view));
        VitruviusChange<EObject> resolvedChange = VitruviusChangeResolverFactory
            .forHierarchicalIds(resolveInResourceSet)
            .resolveAndApply(changeArgument.getValue());
        List<EChange<EObject>> capturedEChanges = resolvedChange.getEChanges();
        InsertRootEObject<EObject> expectedChange = RootFactory.eINSTANCE.createInsertRootEObject();
        expectedChange.setNewValue(root);
        expectedChange.setUri(movedResourceUriString);
        assertThat(capturedEChanges.size(), is(2)); // Remove, Insert
        assertThat(
            capturedEChanges.get(1),
            equalsDeeply(
                expectedChange, ignoringFeatures(RootPackage.eINSTANCE.getRootEChange_Resource())));
      }
    }
  }

  /** Tests for the {@link ChangeRecordingView#commitChanges()} method. */
  @Nested
  @DisplayName("commit")
  class Commit {
    ChangeRecordingView view;
    Root root;

    /** Prepare the view with a root element. */
    @BeforeEach
    public void prepareViewWithRootElement() {
      view = new ChangeRecordingView(
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection));
      root = aet.Root();
      view.registerRoot(root, URI.createURI("test://test.aet"));
      view.commitChanges();
      reset(mockChangeableViewSource, mockViewType);
    }

    /**
     * Close the view after each test.
     *
     * @throws Exception if closing the view fails.
     */
    @AfterEach
    public void closeView() throws Exception {
      view.close();
    }

    @Test
    @DisplayName("once")
    void once() {
      NonRoot nonRoot = aet.NonRoot();
      nonRoot.setId("nonRoot");
      root.setSingleValuedContainmentEReference(nonRoot);
      ArgumentCaptor<ChangeRecordingView> viewArgument = ArgumentCaptor.forClass(ChangeRecordingView.class);
      ArgumentCaptor<VitruviusChange<HierarchicalId>> changeArgument = ArgumentCaptor.forClass(VitruviusChange.class);
      view.commitChanges();
      verify(mockViewType).commitViewChanges(viewArgument.capture(), changeArgument.capture());
      assertThat(viewArgument.getValue(), is(view));
      List<EChange<HierarchicalId>> capturedEChanges = changeArgument.getValue().getEChanges();
      assertThat(capturedEChanges.size(), is(3)); // Create, Insert, ReplaceId
      assertThat(capturedEChanges.get(0), instanceOf(CreateEObject.class));
      assertThat(capturedEChanges.get(1), instanceOf(ReplaceSingleValuedEReference.class));
      assertThat(capturedEChanges.get(2), instanceOf(ReplaceSingleValuedEAttribute.class));
    }

    @Test
    @DisplayName("twice")
    void twice() {
      NonRoot firstNonRoot = aet.NonRoot();
      firstNonRoot.setId("first");
      root.setSingleValuedContainmentEReference(firstNonRoot);
      view.commitChanges();

      ResourceSet resolveInResourceSet = new ResourceSetImpl();
      resolveInResourceSet.createResource(root.eResource().getURI());
      resolveInResourceSet
          .getResource(root.eResource().getURI(), false)
          .getContents()
          .add(EcoreUtil.copy(root));

      reset(mockChangeableViewSource, mockViewType);
      NonRoot secondNonRoot = aet.NonRoot();
      secondNonRoot.setId("second");
      root.setSingleValuedContainmentEReference(secondNonRoot);
      ArgumentCaptor<ChangeRecordingView> viewArgument = ArgumentCaptor.forClass(ChangeRecordingView.class);
      ArgumentCaptor<VitruviusChange<HierarchicalId>> changeArgument = ArgumentCaptor.forClass(VitruviusChange.class);
      view.commitChanges();
      verify(mockViewType).commitViewChanges(viewArgument.capture(), changeArgument.capture());

      VitruviusChange<EObject> resolvedChange = VitruviusChangeResolverFactory.forHierarchicalIds(resolveInResourceSet)
          .resolveAndApply(changeArgument.getValue());
      List<EChange<EObject>> capturedEChanges = resolvedChange.getEChanges();
      assertThat(capturedEChanges.size(), is(4)); // Create, Insert, ReplaceValue, Delete
      assertThat(capturedEChanges.get(0), instanceOf(CreateEObject.class));
      assertThat(capturedEChanges.get(1), instanceOf(ReplaceSingleValuedEReference.class));
      ReplaceSingleValuedEAttribute<EObject, Object> replaceIdChange = AttributeFactory.eINSTANCE
          .createReplaceSingleValuedEAttribute();
      replaceIdChange.setAffectedElement(secondNonRoot);
      replaceIdChange.setAffectedFeature(AllElementTypesPackage.eINSTANCE.getIdentified_Id());
      replaceIdChange.setNewValue("second");
      assertThat(
          capturedEChanges.get(2),
          equalsDeeply(
              replaceIdChange,
              ignoringFeatures(
                  AttributePackage.eINSTANCE.getSubtractiveAttributeEChange_OldValue())));
      assertThat(capturedEChanges.get(3), instanceOf(DeleteEObject.class));
    }

    @Test
    @DisplayName("without changes")
    void withoutChanges() {
      ArgumentCaptor<ChangeRecordingView> viewArgument = ArgumentCaptor.forClass(ChangeRecordingView.class);
      ArgumentCaptor<VitruviusChange<HierarchicalId>> changeArgument = ArgumentCaptor.forClass(VitruviusChange.class);
      view.commitChanges();
      verify(mockViewType).commitViewChanges(viewArgument.capture(), changeArgument.capture());
      assertThat(viewArgument.getValue(), is(view));
      assertThat(changeArgument.getValue().getEChanges(), not(hasItem(anything())));
    }
  }

  /** Tests for the {@link ChangeRecordingView#close()} method. */
  @Nested
  @DisplayName("close")
  class Close {
    @Test
    @DisplayName("and is closed afterwards")
    void isClosed() throws Exception {
      ChangeRecordingView view = new ChangeRecordingView(
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection));
      view.close();
      assertThat("view should be closed", view.isClosed());
    }

    @Test
    @DisplayName("can be called multiple times")
    void callMultipleTimes() throws Exception {
      ChangeRecordingView view = new ChangeRecordingView(
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection));
      view.close();
      view.close();
      assertThat("view should be closed", view.isClosed());
    }

    @Test
    @DisplayName("and does not allow further operations")
    void noOperations() throws Exception {
      ChangeRecordingView view = new ChangeRecordingView(
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection));
      view.close();
      assertThrows(IllegalStateException.class, view::getRootObjects);
      assertThrows(IllegalStateException.class, () -> view.getRootObjects(Root.class));
      assertThrows(IllegalStateException.class, view::update);
      assertThrows(IllegalStateException.class, view::commitChanges);
      assertThrows(IllegalStateException.class, () -> view.registerRoot(null, null));
      assertThrows(IllegalStateException.class, () -> view.moveRoot(null, null));
    }
  }
}
