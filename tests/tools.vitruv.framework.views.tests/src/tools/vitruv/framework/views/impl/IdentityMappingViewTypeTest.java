package tools.vitruv.framework.views.impl;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static java.util.Collections.emptySet;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply;
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.FluentIterable;

import allElementTypes.Root;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeFactory;
import tools.vitruv.change.composite.description.VitruviusChangeResolver;
import tools.vitruv.change.composite.recording.ChangeRecorder;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.selectors.DirectViewElementSelector;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;

@ExtendWith({ TestLogging.class, RegisterMetamodelsInStandalone.class })
public class IdentityMappingViewTypeTest {
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
			DirectViewElementSelector<HierarchicalId> selector = basicViewType
					.createSelector(mock(ChangeableViewSource.class));
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
			DirectViewElementSelector<HierarchicalId> selector = basicViewType.createSelector(viewSource);
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
			DirectViewElementSelector<HierarchicalId> selector = basicViewType.createSelector(viewSource);
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
			DirectViewElementSelector<HierarchicalId> selector = basicViewType.createSelector(viewSource);
			try (View view = basicViewType.createView(selector)) {
				assertThat(view.getRootObjects(), not(hasItem(anything())));
			}
		}

		@Test
		@DisplayName("with selector from other view type")
		public void withSelectorFromOtherViewtype() {
			ChangeableViewSource viewSource = mock(ChangeableViewSource.class);
			DirectViewElementSelector<HierarchicalId> selector = new IdentityMappingViewType("other")
					.createSelector(viewSource);
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
			DirectViewElementSelector<HierarchicalId> selector = basicViewType.createSelector(viewSource);
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
			DirectViewElementSelector<HierarchicalId> selector = basicViewType.createSelector(viewSource);
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
			DirectViewElementSelector<HierarchicalId> selector = basicViewType.createSelector(viewSource);
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
			DirectViewElementSelector<HierarchicalId> selector = basicViewType.createSelector(viewSource);
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
			DirectViewElementSelector<HierarchicalId> selector = basicViewType.createSelector(viewSource);
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
			DirectViewElementSelector<HierarchicalId> selector = basicViewType.createSelector(viewSource);
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
			DirectViewElementSelector<HierarchicalId> selector = basicViewType.createSelector(viewSource);
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
			DirectViewElementSelector<HierarchicalId> selector = basicViewType.createSelector(viewSource);
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
			DirectViewElementSelector<HierarchicalId> selector = basicViewType.createSelector(viewSource);
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

	@Nested
	@DisplayName("commit view changes")
	class CommitViewChanges {
		private IdentityMappingViewType basicViewType;
		private ResourceSet viewSourceResourceSet;
		private ResourceSet viewResourceSet;
		private ModifiableView view;
		private ViewSelection viewSelection;
		private ChangeableViewSource viewSource;
		private UuidResolver uuidResolver;

		@BeforeEach
		void initializeViewTypeAndResourceSetAndViewSource() {
			this.basicViewType = new IdentityMappingViewType("name");
			this.viewSourceResourceSet = withGlobalFactories(new ResourceSetImpl());
			this.viewResourceSet = withGlobalFactories(new ResourceSetImpl());
			this.view = mock(ModifiableView.class);
			this.viewSource = mock(ChangeableViewSource.class);
			this.viewSelection = mock(ViewSelection.class);
			this.uuidResolver = UuidResolver.create(viewSourceResourceSet);
			when(view.getViewSource()).thenReturn(viewSource);
			when(view.getSelection()).thenReturn(viewSelection);
			when(viewSource.getViewSourceModels()).thenReturn(viewSourceResourceSet.getResources());
			when(viewSource.getUuidResolver()).thenReturn(uuidResolver);
			when(viewSelection.isViewObjectSelected(any(EObject.class))).thenReturn(true);
		}

		private Root createResourceWithSingleRoot(URI uri) {
			Resource resource = viewSourceResourceSet.createResource(uri);
			Root rootElement = aet.Root();
			uuidResolver.registerEObject(rootElement);
			rootElement.setId("testid");
			resource.getContents().add(rootElement);

			Resource viewResource = viewResourceSet.createResource(uri);
			Root viewRootElement = EcoreUtil.copy(rootElement);
			viewResource.getContents().add(viewRootElement);
			return viewRootElement;
		}

		@Test
		@DisplayName("with null changes")
		void withNull() {
			assertThrows(NullPointerException.class, () -> basicViewType.commitViewChanges(view, null));
		}

		@Test
		@DisplayName("with null view")
		void withNullView() {
			VitruviusChange<HierarchicalId> someChange = VitruviusChangeFactory.getInstance()
					.createTransactionalChange(Set.of());
			assertThrows(NullPointerException.class, () -> basicViewType.commitViewChanges(null, someChange));
		}

		@ParameterizedTest
		@MethodSource("testEmptyChanges")
		@DisplayName("with empty changes")
		void withEmptyChanges(VitruviusChange<HierarchicalId> change) {
			ArgumentCaptor<VitruviusChange<Uuid>> changeArgument = ArgumentCaptor.forClass(VitruviusChange.class);
			basicViewType.commitViewChanges(view, change);
			verify(viewSource).propagateChange(changeArgument.capture());
			assertEquals(changeArgument.getValue(), change);
		}

		private static Stream<VitruviusChange<HierarchicalId>> testEmptyChanges() {
			VitruviusChangeFactory factory = VitruviusChangeFactory.getInstance();
			return Stream.of(factory.createTransactionalChange(Set.of()), factory.createCompositeChange(Set.of()));
		}

		@Test
		@DisplayName("with non-empty change")
		void withNonEmptyChange() {
			Root root = createResourceWithSingleRoot(URI.createURI("test://test.aet"));
			try (ChangeRecorder changeRecorder = new ChangeRecorder(viewResourceSet)) {
				changeRecorder.addToRecording(root);
				changeRecorder.beginRecording();
				root.setId("testid2");
				changeRecorder.endRecording();
				VitruviusChange<EObject> change = changeRecorder.getChange();
				VitruviusChange<HierarchicalId> idAssignedChange = assignIds(change);

				ArgumentCaptor<VitruviusChange<Uuid>> changeArgument = ArgumentCaptor.forClass(VitruviusChange.class);
				basicViewType.commitViewChanges(view, idAssignedChange);
				verify(viewSource).propagateChange(changeArgument.capture());
				List<EChange<Uuid>> eChanges = changeArgument.getValue().getEChanges();
				assertThat(eChanges.size(), is(1));
				assertThat(eChanges.get(0), instanceOf(ReplaceSingleValuedEAttribute.class));
				var attributeChange = (ReplaceSingleValuedEAttribute<?, ?>) eChanges.get(0);
				assertThat(attributeChange.getOldValue(), is("testid"));
				assertThat(attributeChange.getNewValue(), is("testid2"));
			}
		}

		private VitruviusChange<HierarchicalId> assignIds(VitruviusChange<EObject> change) {
			VitruviusChangeResolver<HierarchicalId> idChangeResolver = VitruviusChangeResolver
					.forHierarchicalIds(viewResourceSet);
			return idChangeResolver.assignIds(change);
		}
	}
}
