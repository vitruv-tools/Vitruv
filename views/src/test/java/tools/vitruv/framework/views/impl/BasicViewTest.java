package tools.vitruv.framework.views.impl;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.change.testutils.TestLogging;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ModifiableViewSelection;

/** Test class for the BasicView class. */
@ExtendWith({TestLogging.class, RegisterMetamodelsInStandalone.class})
class BasicViewTest {
  @Mock ViewCreatingViewType<?, HierarchicalId> mockViewType;
  @Mock ChangeableViewSource mockChangeableViewSource;
  @Mock ModifiableViewSelection mockViewSelection;

  /** Initializes the mocks before each test. */
  @BeforeEach
  void initializeMocks() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Nested class for testing the constructor of the BasicView class. This class is tested in the
   * nested classes Initialize.
   */
  @Nested
  @DisplayName("initialize")
  class Initialize {
    @Test
    @DisplayName("with null view type")
    void withNullViewType() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new BasicView(null, mockChangeableViewSource, mockViewSelection));
    }

    @Test
    @DisplayName("with null view source")
    void withNullViewSource() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new BasicView(mockViewType, null, mockViewSelection));
    }

    @Test
    @DisplayName("with null view selection")
    void withNullViewSelection() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new BasicView(mockViewType, mockChangeableViewSource, null));
    }

    @Test
    @DisplayName("with proper arguments")
    void withEmptySource() throws Exception {
      try (BasicView view =
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
        verify(mockViewType).updateView(view);
        assertThat(view.isClosed(), is(false));
        assertThat(view.getRootObjects(), not(hasItem(anything())));
      }
    }
  }

  /**
   * Nested class for testing the retrieveRootObjects method. This method is tested in the nested
   * classes RetrieveRootElements and RetrieveRootElementsOfType.
   */
  @Nested
  @DisplayName("retrieve roots")
  class RetrieveRootElements {
    @Test
    @DisplayName("all of same type")
    void allOfSameType() throws Exception {
      try (BasicView view =
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
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
      try (BasicView view =
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
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
      try (BasicView view =
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
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

  /** Nested class for testing the modifyContents method. */
  @Nested
  @DisplayName("update")
  class Update {
    @Test
    @DisplayName("without previous modification")
    void withoutPreviousModification() throws Exception {
      try (BasicView view =
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
        view.update();
        verify(mockViewType, times(2)).updateView(view);
      }
    }

    @Test
    @DisplayName("with previous modification")
    void withPreviousModification() throws Exception {
      try (BasicView view =
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
        view.modifyContents(
            (resourceSet) -> resourceSet.createResource(URI.createURI("test://test.aet")));
        assertThrows(IllegalStateException.class, () -> view.update());
      }
    }
  }

  /** Nested class for testing the registerRoot method. */
  @Nested
  @DisplayName("add root")
  class AddRoot {
    @Test
    @DisplayName("being null")
    void nullElement() throws Exception {
      try (BasicView view =
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
        assertThrows(
            IllegalArgumentException.class,
            () -> view.registerRoot(null, URI.createURI("test://test.aet")));
      }
    }

    @Test
    @DisplayName("with null URI")
    void nullUri() throws Exception {
      try (BasicView view =
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
        Root root = aet.Root();
        assertThrows(IllegalArgumentException.class, () -> view.registerRoot(root, null));
      }
    }

    @Test
    @DisplayName("with proper arguments")
    void properArguments() throws Exception {
      try (BasicView view =
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
        Root root = aet.Root();
        String testResourceUriString = "test://test.aet";
        view.registerRoot(root, URI.createURI(testResourceUriString));
        assertThat(view.getRootObjects(), hasItem(root));
      }
    }
  }

  /** Nested class for testing the moveRoot method. */
  @Nested
  @DisplayName("move root")
  class MoveRoot {
    @Test
    @DisplayName("being null")
    void nullElement() throws Exception {
      try (BasicView view =
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
        assertThrows(
            IllegalArgumentException.class,
            () -> view.moveRoot(null, URI.createURI("test://test.aet")));
      }
    }

    @Test
    @DisplayName("with null URI")
    void nullUri() throws Exception {
      try (BasicView view =
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
        Root root = aet.Root();
        view.registerRoot(root, URI.createURI("test://test.aet"));
        assertThrows(IllegalArgumentException.class, () -> view.moveRoot(root, null));
      }
    }

    @Test
    @DisplayName("with element not beeing root")
    void notBeingRoot() throws Exception {
      try (BasicView view =
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
        Root root = aet.Root();
        assertThrows(
            IllegalStateException.class,
            () -> view.moveRoot(root, URI.createURI("test://test.aet")));
      }
    }

    @Test
    @DisplayName("with proper arguments")
    void properArguments() throws Exception {
      try (BasicView view =
          new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
        Root root = aet.Root();
        view.registerRoot(root, URI.createURI("test://test.aet"));
        view.moveRoot(root, URI.createURI("test://test2.aet"));
        assertThat(view.getRootObjects().size(), is(1));
        assertThat(view.getRootObjects(), hasItem(root));
      }
    }
  }

  /** Nested class for testing the close method. */
  @Nested
  @DisplayName("close")
  class Close {
    @Test
    @DisplayName("and is closed afterwards")
    void isClosed() throws Exception {
      BasicView view = new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection);
      view.close();
      assertThat("view should be closed", view.isClosed());
    }

    @Test
    @DisplayName("can be called multiple times")
    void callMultipleTimes() throws Exception {
      BasicView view = new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection);
      view.close();
      view.close();
      assertThat("view should be closed", view.isClosed());
    }

    @Test
    @DisplayName("and does not allow further operations")
    void noOperations() throws Exception {
      BasicView view = new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection);
      view.close();
      assertThrows(IllegalStateException.class, view :: getRootObjects);
      assertThrows(IllegalStateException.class, () -> view.getRootObjects(Root.class));
      assertThrows(IllegalStateException.class, view :: update);
      assertThrows(IllegalStateException.class, () -> view.registerRoot(null, null));
      assertThrows(IllegalStateException.class, () -> view.moveRoot(null, null));
    }
  }
}
