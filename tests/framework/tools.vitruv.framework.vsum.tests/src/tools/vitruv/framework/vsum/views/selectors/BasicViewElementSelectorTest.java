package tools.vitruv.framework.vsum.views.selectors;

import static java.util.Collections.emptySet;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pcm_mockup.Pcm_mockupFactory;
import pcm_mockup.Repository;
import tools.vitruv.framework.vsum.views.ChangeableViewSource;
import tools.vitruv.framework.vsum.views.ViewSelector;
import tools.vitruv.framework.vsum.views.impl.ModifiableView;
import tools.vitruv.framework.vsum.views.impl.ViewCreatingViewType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BasicViewElementSelectorTest {
	@Mock
	ViewCreatingViewType<BasicViewElementSelector> mockViewType;

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
						() -> new BasicViewElementSelector(null, mockViewSource, emptySet()));
			}

			@Test
			@DisplayName("with null view source")
			public void nullViewSource() {
				assertThrows(IllegalArgumentException.class,
						() -> new BasicViewElementSelector(mockViewType, null, emptySet()));
			}

			@Test
			@DisplayName("with null selectable elements")
			public void nullElements() {
				assertThrows(IllegalArgumentException.class,
						() -> new BasicViewElementSelector(mockViewType, mockViewSource, null));
			}
		}

		@Test
		@DisplayName("with no selectable elements")
		public void empty() {
			ViewSelector selector = new BasicViewElementSelector(mockViewType, mockViewSource, emptySet());
			assertThat(selector.getSelectableElements(), is(emptySet()));
			assertThat("BasicViewElementSelectors must always be valid", selector.isValid());
		}

		@Test
		@DisplayName("with single selectable element")
		public void withSingleSelectableElement() {
			Repository repository = Pcm_mockupFactory.eINSTANCE.createRepository();
			ViewSelector selector = new BasicViewElementSelector(mockViewType, mockViewSource, Set.of(repository));
			assertThat(selector.getSelectableElements(), is(Set.of(repository)));
			assertThat("BasicViewElementSelectors must always be valid", selector.isValid());
		}

		@Test
		@DisplayName("with multiple selectable elements")
		public void withMultipleSelectableElements() {
			Repository repository1 = Pcm_mockupFactory.eINSTANCE.createRepository();
			Repository repository2 = Pcm_mockupFactory.eINSTANCE.createRepository();
			ViewSelector selector = new BasicViewElementSelector(mockViewType, mockViewSource,
					Set.of(repository1, repository2));
			assertThat(selector.getSelectableElements(), is(Set.of(repository1, repository2)));
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
			selectableElements.add(Pcm_mockupFactory.eINSTANCE.createRepository());
			selectableElements.add(Pcm_mockupFactory.eINSTANCE.createRepository());
			selector = new BasicViewElementSelector(mockViewType, mockViewSource, selectableElements);
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
					!selector.getSelection().isViewObjectSelected(Pcm_mockupFactory.eINSTANCE.createRepository()));
		}
	}

	@Nested
	@DisplayName("returns")
	class Returns {
		@Test
		@DisplayName("view created from view type")
		public void createView() {
			BasicViewElementSelector selector = new BasicViewElementSelector(mockViewType, mockViewSource, emptySet());
			ModifiableView view = mock(ModifiableView.class);
			when(mockViewType.createView(selector)).thenReturn(view);
			assertThat(selector.createView(), is(view));
		}

		@Test
		@DisplayName("view source")
		public void getViewSource() {
			BasicViewElementSelector selector = new BasicViewElementSelector(mockViewType, mockViewSource, emptySet());
			assertThat(selector.getViewSource(), is(mockViewSource));
		}

	}

}
