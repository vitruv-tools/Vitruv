package tools.vitruv.framework.views.selection;

import static java.util.Collections.emptySet;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import allElementTypes.Root;
import tools.vitruv.framework.views.ModifiableViewSelection;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;

@ExtendWith({ TestLogging.class, RegisterMetamodelsInStandalone.class })
public class ElementViewSelectionTest {
	@Nested
	@DisplayName("inialize")
	class Initialize {
		@Test
		@DisplayName("empty")
		public void empty() {
			ModifiableViewSelection selection = new ElementViewSelection(emptySet());
			assertThat(selection.getSelectableElements(), is(emptySet()));
		}

		@Test
		@DisplayName("with single element")
		public void withSingleElement() {
			Root root = aet.Root();
			ModifiableViewSelection selection = new ElementViewSelection(Set.of(root));
			assertThat(selection.getSelectableElements(), is(Set.of(root)));
		}

		@Test
		@DisplayName("with multiple elements")
		public void withMultipleElements() {
			Root firstRoot = aet.Root();
			Root secondRoot = aet.Root();
			ModifiableViewSelection selection = new ElementViewSelection(Set.of(firstRoot, secondRoot));
			assertThat(selection.getSelectableElements(), is(Set.of(firstRoot, secondRoot)));
		}
	}

	@Nested
	@DisplayName("selectable")
	class Selectable {
		ModifiableViewSelection selection;
		List<EObject> selectableElements;

		@BeforeEach
		public void setupSelectionWithTwoElements() {
			selectableElements = new ArrayList<>();
			selectableElements.add(aet.Root());
			selectableElements.add(aet.Root());
			selection = new ElementViewSelection(selectableElements);
		}

		@Test
		@DisplayName("element in selection")
		public void elementInSelection() {
			assertThat("all added elements must be selectable",
					selectableElements.stream().allMatch(element -> selection.isSelectable(element)));
		}

		@Test
		@DisplayName("element not in selection")
		public void elementNotInSelection() {
			assertThat("elements not added to selection must not be selectable", !selection.isSelectable(aet.Root()));
		}

		@Test
		@DisplayName("null element")
		public void nullElement() {
			assertThat("null elements must not be selectable", !selection.isSelectable(null));
		}

	}

	@Nested
	@DisplayName("select")
	class Select {
		ModifiableViewSelection selection;
		List<EObject> selectableElements;

		@BeforeEach
		public void setupSelectionWithTwoElements() {
			selectableElements = new ArrayList<>();
			selectableElements.add(aet.Root());
			selectableElements.add(aet.Root());
			selection = new ElementViewSelection(selectableElements);
		}

		@Test
		@DisplayName("no element")
		public void noElement() {
			assertThat("unselected elements must not be selected by default",
					!selection.isSelected(selectableElements.get(0)));
			assertThat("unselected elements must not be selected by default",
					!selection.isSelected(selectableElements.get(1)));
		}

		@Test
		@DisplayName("first element")
		public void firstElement() {
			selection.setSelected(selectableElements.get(0), true);
			assertThat("element must be selected after selection", selection.isSelected(selectableElements.get(0)));
			assertThat("unselected elements must not be selected by default",
					!selection.isSelected(selectableElements.get(1)));
		}

		@Test
		@DisplayName("second element")
		public void secondElement() {
			selection.setSelected(selectableElements.get(1), true);
			assertThat("unselected elements must not be selected by default",
					!selection.isSelected(selectableElements.get(0)));
			assertThat("element must be selected after selection", selection.isSelected(selectableElements.get(1)));
		}

		@Test
		@DisplayName("all elements")
		public void allElements() {
			selection.setSelected(selectableElements.get(0), true);
			selection.setSelected(selectableElements.get(1), true);
			assertThat("element must be selected after selection", selection.isSelected(selectableElements.get(0)));
			assertThat("element must be selected after selection", selection.isSelected(selectableElements.get(1)));
		}

		@Test
		@DisplayName("element that is not selectable")
		public void unselectableElement() {
			assertThrows(IllegalStateException.class, () -> selection.setSelected(aet.Root(), true));
		}

	}

	@Nested
	@DisplayName("unselect")
	class Unselect {
		ModifiableViewSelection selection;
		List<EObject> selectableElements;

		@BeforeEach
		public void setupSelectionWithTwoSelectedElements() {
			selectableElements = new ArrayList<>();
			selectableElements.add(aet.Root());
			selectableElements.add(aet.Root());
			selection = new ElementViewSelection(selectableElements);
			selectableElements.forEach(element -> selection.setSelected(element, true));
		}

		@Test
		@DisplayName("first element")
		public void firstElement() {
			selection.setSelected(selectableElements.get(0), false);
			assertThat("element must be unselected after deselection",
					!selection.isSelected(selectableElements.get(0)));
			assertThat("element must be selected without deselection", selection.isSelected(selectableElements.get(1)));
		}

		@Test
		@DisplayName("second element")
		public void secondElement() {
			selection.setSelected(selectableElements.get(1), false);
			assertThat("element must be selected without deselection", selection.isSelected(selectableElements.get(0)));
			assertThat("element must be unselected after deselection",
					!selection.isSelected(selectableElements.get(1)));
		}

		@Test
		@DisplayName("all elements")
		public void allElements() {
			selection.setSelected(selectableElements.get(0), false);
			selection.setSelected(selectableElements.get(1), false);
			assertThat("element must be unselected after deselection",
					!selection.isSelected(selectableElements.get(0)));
			assertThat("element must be unselected after deselection",
					!selection.isSelected(selectableElements.get(1)));
		}

		@Test
		@DisplayName("element that is not selectable")
		public void unselectableElement() {
			assertThrows(IllegalStateException.class, () -> selection.setSelected(aet.Root(), false));
		}

	}

	@Nested
	@DisplayName("validate view element selection")
	class ViewElementSelection {
		ModifiableViewSelection selection;
		List<EObject> selectableElements;

		@BeforeEach
		public void setupSelectionWithFirstOfTwoElementsSelected() {
			selectableElements = new ArrayList<>();
			selectableElements.add(aet.Root());
			selectableElements.add(aet.Root());
			selection = new ElementViewSelection(selectableElements);
			selection.setSelected(selectableElements.get(0), true);
		}

		@Test
		@DisplayName("matching selected element")
		public void matchingSelectedElement() {
			assertThat("view element must be validated as selected after selecting it",
					selection.isViewObjectSelected(selectableElements.get(0)));
		}

		@Test
		@DisplayName("matching unselected element")
		public void matchingUnselectedElement() {
			assertThat("view element must be validated as unselected when not selecting it",
					!selection.isViewObjectSelected(selectableElements.get(1)));
		}

		@Test
		@DisplayName("matching no element")
		public void matchingNoElement() {
			assertThat("view element must be validated as unselected when it cannot be selected",
					!selection.isViewObjectSelected(aet.Root()));
		}

	}

	@Nested
	@DisplayName("copy")
	class Copy {
		List<EObject> selectableElements;

		@BeforeEach
		public void setupSelectionWithFirstOfTwoElementsSelected() {
			selectableElements = new ArrayList<>();
			selectableElements.add(aet.Root());
			selectableElements.add(aet.Root());
		}

		@Test
		@DisplayName("empty")
		public void empty() {
			ModifiableViewSelection originalSelection = new ElementViewSelection(emptySet());
			ModifiableViewSelection copy = new ElementViewSelection(originalSelection);
			assertThat(copy.getSelectableElements(), is(emptySet()));
		}

		@Test
		@DisplayName("with single element")
		public void withSingleElement() {
			Root root = aet.Root();
			ModifiableViewSelection originalSelection = new ElementViewSelection(Set.of(root));
			ModifiableViewSelection copy = new ElementViewSelection(originalSelection);
			assertThat(copy.getSelectableElements(), is(Set.of(root)));
		}

		@Test
		@DisplayName("with multiple elements")
		public void withMultipleElements() {
			Root firstRoot = aet.Root();
			Root secondRoot = aet.Root();
			ModifiableViewSelection originalSelection = new ElementViewSelection(Set.of(firstRoot, secondRoot));
			ModifiableViewSelection copy = new ElementViewSelection(originalSelection);
			assertThat(copy.getSelectableElements(), is(Set.of(firstRoot, secondRoot)));
		}

	}

}
