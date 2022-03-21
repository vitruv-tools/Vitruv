package tools.vitruv.framework.views.impl;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet;

import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ModifiableViewSelection;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;

@ExtendWith({ TestLogging.class, RegisterMetamodelsInStandalone.class })
public class BasicViewTest {
	@Mock
	ViewCreatingViewType<?> mockViewType;
	@Mock
	ChangeableViewSource mockChangeableViewSource;
	@Mock
	ModifiableViewSelection mockViewSelection;

	@BeforeEach
	public void initializeMocks() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Nested
	@DisplayName("initialize")
	public class Initialize {
		@Test
		@DisplayName("with null view type")
		public void withNullViewType() {
			assertThrows(IllegalArgumentException.class,
					() -> new BasicView(null, mockChangeableViewSource, mockViewSelection));
		}

		@Test
		@DisplayName("with null view source")
		public void withNullViewSource() {
			assertThrows(IllegalArgumentException.class,
					() -> new BasicView(mockViewType, null, mockViewSelection));
		}

		@Test
		@DisplayName("with null view selection")
		public void withNullViewSelection() {
			assertThrows(IllegalArgumentException.class,
					() -> new BasicView(mockViewType, mockChangeableViewSource, null));
		}

		@Test
		@DisplayName("with proper arguments")
		public void withEmptySource() throws Exception {
			try (BasicView view = new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
				verify(mockViewType).updateView(view);
				assertThat(view.isClosed(), is(false));
				assertThat(view.getRootObjects(), not(hasItem(anything())));
			}
		}
	}
	
	@Nested
	@DisplayName("retrieve roots")
	public class RetrieveRootElements {
		@Test
		@DisplayName("all of same type")
		public void allOfSameType() throws Exception {
			try (BasicView view = new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
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
		public void containingAllOfOneType() throws Exception {
			try (BasicView view = new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
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
		public void containingNoneOfType() throws Exception {
			try (BasicView view = new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
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

	@Nested
	@DisplayName("update")
	public class Update {
		@Test
		@DisplayName("without previous modification")
		public void withoutPreviousModification() throws Exception {
			try (BasicView view = new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
				view.update();
				verify(mockViewType, times(2)).updateView(view);
			}
		}

		@Test
		@DisplayName("with previous modification")
		public void withPreviousModification() throws Exception {
			try (BasicView view = new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
				view.modifyContents((resourceSet) -> resourceSet.createResource(URI.createURI("test://test.aet")));
				assertThrows(IllegalStateException.class, () -> view.update());
			}
		}
	}

	@Nested
	@DisplayName("add root")
	public class AddRoot {
		@Test
		@DisplayName("being null")
		public void nullElement() throws Exception {
			try (BasicView view = new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
				assertThrows(IllegalArgumentException.class,
						() -> view.registerRoot(null, URI.createURI("test://test.aet")));
			}
		}

		@Test
		@DisplayName("with null URI")
		public void nullUri() throws Exception {
			try (BasicView view = new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
				Root root = aet.Root();
				assertThrows(IllegalArgumentException.class, () -> view.registerRoot(root, null));
			}
		}

		@Test
		@DisplayName("with proper arguments")
		public void properArguments() throws Exception {
			try (BasicView view = new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
				Root root = aet.Root();
				String testResourceUriString = "test://test.aet";
				view.registerRoot(root, URI.createURI(testResourceUriString));
				assertThat(view.getRootObjects(), hasItem(root));
			}
		}
	}

	@Nested
	@DisplayName("move root")
	public class MoveRoot {
		@Test
		@DisplayName("being null")
		public void nullElement() throws Exception {
			try (BasicView view = new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
				assertThrows(IllegalArgumentException.class,
						() -> view.moveRoot(null, URI.createURI("test://test.aet")));
			}
		}

		@Test
		@DisplayName("with null URI")
		public void nullUri() throws Exception {
			try (BasicView view = new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
				Root root = aet.Root();
				view.registerRoot(root, URI.createURI("test://test.aet"));
				assertThrows(IllegalArgumentException.class, () -> view.moveRoot(root, null));
			}
		}

		@Test
		@DisplayName("with element not beeing root")
		public void notBeingRoot() throws Exception {
			try (BasicView view = new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
				Root root = aet.Root();
				assertThrows(IllegalStateException.class, () -> view.moveRoot(root, URI.createURI("test://test.aet")));
			}
		}

		@Test
		@DisplayName("with proper arguments")
		public void properArguments() throws Exception {
			try (BasicView view = new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
				Root root = aet.Root();
				view.registerRoot(root, URI.createURI("test://test.aet"));
				view.moveRoot(root, URI.createURI("test://test2.aet"));
				assertThat(view.getRootObjects().size(), is(1));
				assertThat(view.getRootObjects(), hasItem(root));
			}
		}
	}

	@Nested
	@DisplayName("close")
	public class Close {
		@Test
		@DisplayName("and is closed afterwards")
		public void isClosed() throws Exception {
			BasicView view = new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection);
			view.close();
			assertThat("view should be closed", view.isClosed());
		}

		@Test
		@DisplayName("can be called multiple times")
		public void callMultipleTimes() throws Exception {
			BasicView view = new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection);
			view.close();
			view.close();
			assertThat("view should be closed", view.isClosed());
		}

		@Test
		@DisplayName("and does not allow further operations")
		public void noOperations() throws Exception {
			BasicView view = new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection);
			view.close();
			assertThrows(IllegalStateException.class, () -> view.getRootObjects());
			assertThrows(IllegalStateException.class, () -> view.getRootObjects(Root.class));
			assertThrows(IllegalStateException.class, () -> view.update());
			assertThrows(IllegalStateException.class, () -> view.registerRoot(null, null));
			assertThrows(IllegalStateException.class, () -> view.moveRoot(null, null));
		}
	}
}
