package tools.vitruv.framework.views.selectors;

import static java.util.Collections.emptySet;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import allElementTypes.Root;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.impl.ModifiableView;
import tools.vitruv.framework.views.impl.ViewCreatingViewType;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;

@ExtendWith({ TestLogging.class, RegisterMetamodelsInStandalone.class })
public class DirectViewElementSelectorTest {
	@Mock
	ViewCreatingViewType<DirectViewElementSelector<HierarchicalId>, HierarchicalId> mockViewType;

	@Mock
	ChangeableViewSource mockViewSource;

	@BeforeEach
	public void initializeMocks() {
		MockitoAnnotations.openMocks(this);
	}

	@Nested
	@DisplayName("initalize")
	class Initialize {
		@Nested
		@DisplayName("with null elements")
		class WithNullElements {
			@Test
			@DisplayName("with null view type")
			public void nullViewType() {
				assertThrows(IllegalArgumentException.class,
						() -> new DirectViewElementSelector<>(null, mockViewSource, emptySet()));
			}

			@Test
			@DisplayName("with null view source")
			public void nullViewSource() {
				assertThrows(IllegalArgumentException.class,
						() -> new DirectViewElementSelector<>(mockViewType, null, emptySet()));
			}

			@Test
			@DisplayName("with null selectable elements")
			public void nullElements() {
				assertThrows(IllegalArgumentException.class,
						() -> new DirectViewElementSelector<>(mockViewType, mockViewSource, null));
			}
		}

		@Test
		@DisplayName("with no selectable elements")
		public void empty() {
			ViewSelector selector = new DirectViewElementSelector<>(mockViewType, mockViewSource, emptySet());
			assertThat(selector.getSelectableElements(), is(emptySet()));
			assertThat("BasicViewElementSelectors must always be valid", selector.isValid());
		}

		@Test
		@DisplayName("with single selectable element")
		public void withSingleSelectableElement() {
			Root root = aet.Root();
			ViewSelector selector = new DirectViewElementSelector<>(mockViewType, mockViewSource, Set.of(root));
			assertThat(selector.getSelectableElements(), is(Set.of(root)));
			assertThat("BasicViewElementSelectors must always be valid", selector.isValid());
		}

		@Test
		@DisplayName("with multiple selectable elements")
		public void withMultipleSelectableElements() {
			Root firstRoot = aet.Root();
			Root secondRoot = aet.Root();
			ViewSelector selector = new DirectViewElementSelector<>(mockViewType, mockViewSource,
					Set.of(firstRoot, secondRoot));
			assertThat(selector.getSelectableElements(), is(Set.of(firstRoot, secondRoot)));
			assertThat("BasicViewElementSelectors must always be valid", selector.isValid());
		}

	}

	@Nested
	@DisplayName("provides view selection")
	class ViewSelection {
		ViewSelector selector;
		List<EObject> selectableElements;

		@BeforeEach
		public void setupSelectionWithFirstOfTwoElementsSelected() {
			selectableElements = new ArrayList<>();
			selectableElements.add(aet.Root());
			selectableElements.add(aet.Root());
			selector = new DirectViewElementSelector<>(mockViewType, mockViewSource, selectableElements);
			selector.setSelected(selectableElements.get(0), true);
			assertThat("BasicViewElementSelectors must always be valid", selector.isValid());
		}

		@Test
		@DisplayName("matching selected element")
		public void matchingSelectedElement() {
			assertThat("view element must be validated as selected after selecting it",
					selector.getSelection().isViewObjectSelected(selectableElements.get(0)));
		}

		@Test
		@DisplayName("matching unselected element")
		public void matchingUnselectedElement() {
			assertThat("view element must be validated as unselected when not selecting it",
					!selector.getSelection().isViewObjectSelected(selectableElements.get(1)));
		}

		@Test
		@DisplayName("matching no element")
		public void matchingNoElement() {
			assertThat("view element must be validated as unselected when it cannot be selected",
					!selector.getSelection().isViewObjectSelected(aet.Root()));
		}
	}

	@Nested
	@DisplayName("returns")
	class Returns {
		@Test
		@DisplayName("view created from view type")
		public void createView() {
			DirectViewElementSelector<HierarchicalId> selector = new DirectViewElementSelector<>(mockViewType,
					mockViewSource, emptySet());
			ModifiableView view = mock(ModifiableView.class);
			when(mockViewType.createView(selector)).thenReturn(view);
			assertThat(selector.createView(), is(view));
		}

		@Test
		@DisplayName("view source")
		public void getViewSource() {
			DirectViewElementSelector<HierarchicalId> selector = new DirectViewElementSelector<>(mockViewType,
					mockViewSource, emptySet());
			assertThat(selector.getViewSource(), is(mockViewSource));
		}

	}

}
