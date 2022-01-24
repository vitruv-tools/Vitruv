package tools.vitruv.framework.vsum.views.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.common.collect.FluentIterable;

import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet;
import allElementTypes.Root;
import tools.vitruv.framework.vsum.views.ChangeableViewSource;
import tools.vitruv.framework.vsum.views.View;
import tools.vitruv.framework.vsum.views.ViewType;
import tools.vitruv.framework.vsum.views.selectors.BasicViewElementSelector;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import static java.util.Collections.emptySet;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static tools.vitruv.testutils.matchers.ModelMatchers.*;

@ExtendWith({ TestLogging.class, RegisterMetamodelsInStandalone.class })
public class BasicViewTypeTest {
	@Nested
	@DisplayName("initialize")
	class Initialize {
		@Test
		@DisplayName("with proper name")
		public void withName() {
			ViewType<?> viewType = new IdentityMappingViewType("name");
			assertThat(viewType.getName(), is("name"));
		}

		@Test
		@DisplayName("with empty name")
		public void withEmptyName() {
			ViewType<?> viewType = new IdentityMappingViewType("");
			assertThat(viewType.getName(), is(""));
		}

		@Test
		@DisplayName("with null name")
		public void withNullName() {
			assertThrows(IllegalArgumentException.class, () -> new IdentityMappingViewType(null));
		}
	}

	@Nested
	@DisplayName("create selector")
	class CreateSelector {
		private IdentityMappingViewType basicViewType;

		@BeforeEach
		public void initializeViewType() {
			this.basicViewType = new IdentityMappingViewType("name");
		}

		@Test
		@DisplayName("for empty source")
		public void forEmptySource() {
			BasicViewElementSelector selector = basicViewType.createSelector(mock(ChangeableViewSource.class));
			assertThat(selector.getSelectableElements(), is(emptySet()));
		}

		@Test
		@DisplayName("for source containing single element")
		public void forSourceContainingElement() {
			Resource resource = withGlobalFactories(new ResourceSetImpl())
					.createResource(URI.createURI("test:///test.aet"));
			Root rootElement = aet.Root();
			resource.getContents().add(rootElement);
			ChangeableViewSource viewSource = mock(ChangeableViewSource.class);
			when(viewSource.getViewSourceModels()).thenReturn(Set.of(resource));
			BasicViewElementSelector selector = basicViewType.createSelector(viewSource);
			assertThat(selector.getSelectableElements(), is(Set.of(rootElement)));
		}

		@Test
		@DisplayName("for source containing non-root elements")
		public void forSourceContainingNonRootElements() {
			Resource resource = withGlobalFactories(new ResourceSetImpl())
					.createResource(URI.createURI("test:///test.aet"));
			Root rootElement = aet.Root();
			rootElement.setSingleValuedContainmentEReference(aet.NonRoot());
			resource.getContents().add(rootElement);
			ChangeableViewSource viewSource = mock(ChangeableViewSource.class);
			when(viewSource.getViewSourceModels()).thenReturn(Set.of(resource));
			BasicViewElementSelector selector = basicViewType.createSelector(viewSource);
			assertThat(selector.getSelectableElements(), is(Set.of(rootElement)));
		}
	}

	@Nested
	@DisplayName("create view")
	public class CreateView {
		private IdentityMappingViewType basicViewType;
		private ResourceSet testResourceSet;

		@BeforeEach
		public void initializeViewTypeAndResourceSet() {
			this.basicViewType = new IdentityMappingViewType("name");
			this.testResourceSet = withGlobalFactories(new ResourceSetImpl());
		}

		@Test
		@DisplayName("with empty source")
		public void withNoElements() throws Exception {
			ChangeableViewSource viewSource = mock(ChangeableViewSource.class);
			BasicViewElementSelector selector = basicViewType.createSelector(viewSource);
			try (View view = basicViewType.createView(selector)) {
				assertThat(view.getRootObjects(), not(hasItem(anything())));
			}
		}

		@Test
		@DisplayName("with selector from other view type")
		public void withSelectorFromOtherViewtype() {
			ChangeableViewSource viewSource = mock(ChangeableViewSource.class);
			BasicViewElementSelector selector = new IdentityMappingViewType("other").createSelector(viewSource);
			assertThrows(IllegalArgumentException.class, () -> basicViewType.createView(selector));
		}

		@Test
		@DisplayName("with no selected element")
		public void withNoSelectedElement() throws Exception {
			Resource resource = testResourceSet.createResource(URI.createURI("test://test.aet"));
			Root rootElement = aet.Root();
			rootElement.setId("testid");
			resource.getContents().add(rootElement);
			ChangeableViewSource viewSource = mock(ChangeableViewSource.class);
			when(viewSource.getViewSourceModels()).thenReturn(Set.of(resource));
			BasicViewElementSelector selector = basicViewType.createSelector(viewSource);
			try (View view = basicViewType.createView(selector)) {
				assertThat(view.getRootObjects(), not(hasItem(anything())));
			}
		}

		@Test
		@DisplayName("with single selected element")
		public void withSingleSelectedElement() throws Exception {
			Resource resource = testResourceSet.createResource(URI.createURI("test://test.aet"));
			Root rootElement = aet.Root();
			rootElement.setId("testid");
			resource.getContents().add(rootElement);
			ChangeableViewSource viewSource = mock(ChangeableViewSource.class);
			when(viewSource.getViewSourceModels()).thenReturn(Set.of(resource));
			BasicViewElementSelector selector = basicViewType.createSelector(viewSource);
			selector.setSelected(rootElement, true);
			try (View view = basicViewType.createView(selector)) {
				assertThat(view.getRootObjects().size(), is(1));
				assertThat(view.getRootObjects(), hasItem(equalsDeeply(rootElement)));
			}
		}

		@Test
		@DisplayName("with one of two elements selected")
		public void withOneOfTwoElementsSelected() throws Exception {
			Resource firstResource = testResourceSet.createResource(URI.createURI("test://test.aet"));
			Resource secondResource = testResourceSet.createResource(URI.createURI("test://test2.aet"));
			Root firstRootElement = aet.Root();
			firstRootElement.setId("firstElementId");
			Root secondRootElement = aet.Root();
			secondRootElement.setId("secondElementId");
			firstResource.getContents().add(firstRootElement);
			secondResource.getContents().add(secondRootElement);
			ChangeableViewSource viewSource = mock(ChangeableViewSource.class);
			when(viewSource.getViewSourceModels()).thenReturn(Set.of(firstResource, secondResource));
			BasicViewElementSelector selector = basicViewType.createSelector(viewSource);
			selector.setSelected(firstRootElement, true);
			try (View view = basicViewType.createView(selector)) {
				assertThat(view.getRootObjects().size(), is(1));
				assertThat(view.getRootObjects(), hasItem(equalsDeeply(firstRootElement)));
			}
		}

		@Test
		@DisplayName("with both of two elements selected")
		public void withBothOfTwoElementsSelected() throws Exception {
			Resource firstResource = testResourceSet.createResource(URI.createURI("test://test.aet"));
			Resource secondResource = testResourceSet.createResource(URI.createURI("test://test2.aet"));
			Root firstRootElement = aet.Root();
			firstRootElement.setId("firstElementId");
			Root secondRootElement = aet.Root();
			secondRootElement.setId("secondElementId");
			firstResource.getContents().add(firstRootElement);
			secondResource.getContents().add(secondRootElement);
			ChangeableViewSource viewSource = mock(ChangeableViewSource.class);
			when(viewSource.getViewSourceModels()).thenReturn(Set.of(firstResource, secondResource));
			BasicViewElementSelector selector = basicViewType.createSelector(viewSource);
			selector.setSelected(firstRootElement, true);
			selector.setSelected(secondRootElement, true);
			try (View view = basicViewType.createView(selector)) {
				assertThat(view.getRootObjects().size(), is(2));
				assertThat(view.getRootObjects(), hasItem(equalsDeeply(firstRootElement)));
				assertThat(view.getRootObjects(), hasItem(equalsDeeply(secondRootElement)));
			}
		}

		/**
		 * This test validates that if there are two resources with the root of one of
		 * them being contained in the other resource (like a Java compilation unit
		 * being root of a resource but also contained in its packages represented in a
		 * further resource), the root element that is also contained in the other
		 * resource is not duplicated but copied properly.
		 * 
		 * @throws Exception
		 */
		@Test
		@DisplayName("for two resources with containment in between")
		public void forTwoResourcesWithContainmentInBetween() throws Exception {
			Resource firstResource = testResourceSet.createResource(URI.createURI("test://test.aet"));
			Resource secondResource = testResourceSet.createResource(URI.createURI("test://test2.aet"));
			Root firstRootElement = aet.Root();
			firstRootElement.setId("firstElementId");
			Root secondRootElement = aet.Root();
			secondRootElement.setId("secondElementId");
			firstRootElement.setRecursiveRoot(secondRootElement);
			firstResource.getContents().add(firstRootElement);
			secondResource.getContents().add(secondRootElement);
			ChangeableViewSource viewSource = mock(ChangeableViewSource.class);
			when(viewSource.getViewSourceModels()).thenReturn(Set.of(firstResource, secondResource));
			BasicViewElementSelector selector = basicViewType.createSelector(viewSource);
			selector.setSelected(firstRootElement, true);
			selector.setSelected(secondRootElement, true);
			try (View view = basicViewType.createView(selector)) {
				assertThat(view.getRootObjects().size(), is(2));
				assertThat(view.getRootObjects(), hasItem(equalsDeeply(firstRootElement)));
				assertThat(view.getRootObjects(), hasItem(equalsDeeply(secondRootElement)));
				Root rootWithContainment = FluentIterable.from(view.getRootObjects(Root.class))
						.filter((root) -> root.getRecursiveRoot() != null).first().get();
				Root rootWithoutContainment = FluentIterable.from(view.getRootObjects(Root.class))
						.filter((root) -> root.getRecursiveRoot() == null).first().get();
				assertThat(rootWithContainment.getRecursiveRoot(), is(rootWithoutContainment));
			}
		}

	}

	@Nested
	@DisplayName("update view")
	class UpdateView {
		private IdentityMappingViewType basicViewType;
		private ResourceSet testResourceSet;
		private ChangeableViewSource viewSource;

		@BeforeEach
		public void initializeViewTypeAndResourceSetAndViewSource() {
			this.basicViewType = new IdentityMappingViewType("name");
			this.testResourceSet = withGlobalFactories(new ResourceSetImpl());
			this.viewSource = mock(ChangeableViewSource.class);
			when(viewSource.getViewSourceModels()).thenReturn(testResourceSet.getResources());
		}

		private Root createResourceWithSingleRoot(URI uri) {
			Resource resource = testResourceSet.createResource(uri);
			Root rootElement = aet.Root();
			rootElement.setId("testid");
			resource.getContents().add(rootElement);
			return rootElement;
		}

		@Test
		@DisplayName("adding a non-root element")
		public void addingANonRootElement() throws Exception {
			Root root = createResourceWithSingleRoot(URI.createURI("test://test.aet"));
			BasicViewElementSelector selector = basicViewType.createSelector(viewSource);
			selector.getSelectableElements().forEach((element) -> selector.setSelected(element, true));
			try (ModifiableView view = basicViewType.createView(selector)) {
				root.setSingleValuedContainmentEReference(aet.NonRoot());
				assertThat((view.getRootObjects(Root.class).iterator().next()).getSingleValuedContainmentEReference(),
						equalTo(null));
				basicViewType.updateView(view);
				assertThat(view.getRootObjects().size(), is(1));
				assertThat((view.getRootObjects(Root.class).iterator().next()).getSingleValuedContainmentEReference(),
						is(anything()));
			}
		}

		@Test
		@DisplayName("adding a root element")
		public void addingARootElement() throws Exception {
			Root root = createResourceWithSingleRoot(URI.createURI("test://test.aet"));
			BasicViewElementSelector selector = basicViewType.createSelector(viewSource);
			selector.getSelectableElements().forEach((element) -> selector.setSelected(element, true));
			try (ModifiableView view = basicViewType.createView(selector)) {
				root.setId("secondId");
				Root secondRoot = createResourceWithSingleRoot(URI.createURI("test://test2.aet"));
				basicViewType.updateView(view);
				assertThat(view.getRootObjects().size(), is(1));
				assertThat(view.getRootObjects(), not(hasItem(equalsDeeply(secondRoot))));
			}
		}

		@Test
		@DisplayName("removing a selected root element")
		public void removingSelectedRoot() throws Exception {
			Root root = createResourceWithSingleRoot(URI.createURI("test://test.aet"));
			BasicViewElementSelector selector = basicViewType.createSelector(viewSource);
			selector.getSelectableElements().forEach((element) -> selector.setSelected(element, true));
			try (ModifiableView view = basicViewType.createView(selector)) {
				EcoreUtil.delete(root);
				assertThat(view.getRootObjects().size(), is(1));
				basicViewType.updateView(view);
				assertThat(view.getRootObjects().size(), is(0));
			}
		}

		@Test
		@DisplayName("removing an unselected root element")
		public void removingUnselectedRoot() throws Exception {
			Root firstRoot = createResourceWithSingleRoot(URI.createURI("test://test.aet"));
			Root secondRoot = createResourceWithSingleRoot(URI.createURI("test://test2.aet"));
			BasicViewElementSelector selector = basicViewType.createSelector(viewSource);
			selector.setSelected(firstRoot, true);
			try (ModifiableView view = basicViewType.createView(selector)) {
				EcoreUtil.delete(secondRoot);
				assertThat(view.getRootObjects().size(), is(1));
				basicViewType.updateView(view);
				assertThat(view.getRootObjects().size(), is(1));
				assertThat(view.getRootObjects(), hasItem(equalsDeeply(firstRoot)));
			}
		}
	}
}
