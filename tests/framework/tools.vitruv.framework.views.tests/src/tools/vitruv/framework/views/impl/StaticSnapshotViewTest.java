package tools.vitruv.framework.views.impl;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import allElementTypes.Root;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ModifiableViewSelection;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;

@ExtendWith({ TestLogging.class, RegisterMetamodelsInStandalone.class })
public class StaticSnapshotViewTest {
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
					() -> new StaticSnapshotView(null, mockChangeableViewSource, mockViewSelection));
		}

		@Test
		@DisplayName("with null view source")
		public void withNullViewSource() {
			assertThrows(IllegalArgumentException.class,
					() -> new StaticSnapshotView(mockViewType, null, mockViewSelection));
		}

		@Test
		@DisplayName("with null view selection")
		public void withNullViewSelection() {
			assertThrows(IllegalArgumentException.class,
					() -> new StaticSnapshotView(mockViewType, mockChangeableViewSource, null));
		}

		@Test
		@DisplayName("with proper arguments")
		public void withEmptySource() throws Exception {
			try (StaticSnapshotView view = new StaticSnapshotView(mockViewType, mockChangeableViewSource,
					mockViewSelection)) {
				verify(mockViewType).updateView(view);
				assertThat(view.isClosed(), is(false));
				assertThat(view.getRootObjects(), not(hasItem(anything())));
			}
		}
	}

	@Test
	@DisplayName("invalid update operation")
	public void update() throws Exception {
		try (StaticSnapshotView view = new StaticSnapshotView(mockViewType, mockChangeableViewSource,
				mockViewSelection)) {
			assertThrows(IllegalStateException.class, () -> view.update());
		}
	}

	@Test
	@DisplayName("invalid register root operation")
	public void registerRoot() throws Exception {
		try (StaticSnapshotView view = new StaticSnapshotView(mockViewType, mockChangeableViewSource,
				mockViewSelection)) {
			Root root = aet.Root();
			assertThrows(UnsupportedOperationException.class,
					() -> view.registerRoot(root, URI.createURI("test://test.aet")));
		}
	}

	@Test
	@DisplayName("invalid move root operation")
	public void moveRoot() throws Exception {
		try (StaticSnapshotView view = new StaticSnapshotView(mockViewType, mockChangeableViewSource,
				mockViewSelection)) {
			Root root = aet.Root();
			assertThrows(UnsupportedOperationException.class,
					() -> view.moveRoot(root, URI.createURI("test://test.aet")));
		}
	}

	@Test
	@DisplayName("invalid commit operation")
	public void commitChanges() throws Exception {
		try (StaticSnapshotView view = new StaticSnapshotView(mockViewType, mockChangeableViewSource,
				mockViewSelection)) {
			assertThrows(UnsupportedOperationException.class, () -> view.commitChanges());
		}
	}

	@Nested
	@DisplayName("close")
	public class Close {
		@Test
		@DisplayName("and is closed afterwards")
		public void isClosed() throws Exception {
			StaticSnapshotView view = new StaticSnapshotView(mockViewType, mockChangeableViewSource, mockViewSelection);
			view.close();
			assertThat("view should be closed", view.isClosed());
		}

		@Test
		@DisplayName("can be called multiple times")
		public void callMultipleTimes() throws Exception {
			StaticSnapshotView view = new StaticSnapshotView(mockViewType, mockChangeableViewSource, mockViewSelection);
			view.close();
			view.close();
			assertThat("view should be closed", view.isClosed());
		}

		@Test
		@DisplayName("and does not allow further operations")
		public void noOperations() throws Exception {
			StaticSnapshotView view = new StaticSnapshotView(mockViewType, mockChangeableViewSource, mockViewSelection);
			view.close();
			assertThrows(IllegalStateException.class, () -> view.getRootObjects());
			assertThrows(IllegalStateException.class, () -> view.getRootObjects(Root.class));
		}
	}
}
