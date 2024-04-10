package tools.vitruv.framework.views.impl;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply;
import static tools.vitruv.testutils.matchers.ModelMatchers.ignoringFeatures;
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import tools.vitruv.change.atomic.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.atomic.root.InsertRootEObject;
import tools.vitruv.change.atomic.root.RootFactory;
import tools.vitruv.change.atomic.root.RootPackage;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeResolver;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ModifiableViewSelection;
import tools.vitruv.framework.views.changederivation.DefaultStateBasedChangeResolutionStrategy;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;

@ExtendWith({ TestLogging.class, RegisterMetamodelsInStandalone.class })
public class ChangeDerivingViewTest {
	@Mock
	ViewCreatingViewType<?, HierarchicalId> mockViewType;
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
			assertThrows(IllegalArgumentException.class, () -> new ChangeDerivingView(null, new DefaultStateBasedChangeResolutionStrategy()));
		}
		
		@Test
		@DisplayName("with null change resolution strategy")
		public void withNullChangeResolutionStrategy() {
			assertThrows(IllegalArgumentException.class,
					() -> new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource, mockViewSelection), null));
		}

		@Test
		@DisplayName("with proper arguments")
		public void withEmptySource() throws Exception {
			try (ChangeDerivingView view = new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource,
					mockViewSelection), new DefaultStateBasedChangeResolutionStrategy())) {
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
			try (ChangeDerivingView view = new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource,
					mockViewSelection), new DefaultStateBasedChangeResolutionStrategy())) {
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
			try (ChangeDerivingView view = new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource,
					mockViewSelection), new DefaultStateBasedChangeResolutionStrategy())) {
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
			try (ChangeDerivingView view = new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource,
					mockViewSelection), new DefaultStateBasedChangeResolutionStrategy())) {
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
			try (ChangeDerivingView view = new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource,
					mockViewSelection), new DefaultStateBasedChangeResolutionStrategy())) {
				view.update();
			}
		}

		@Test
		@DisplayName("with previous modification")
		public void withPreviousModification() throws Exception {
			try (ChangeDerivingView view = new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource,
					mockViewSelection), new DefaultStateBasedChangeResolutionStrategy())) {
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
			try (ChangeDerivingView view = new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource,
					mockViewSelection), new DefaultStateBasedChangeResolutionStrategy())) {
				assertThrows(IllegalArgumentException.class,
						() -> view.registerRoot(null, URI.createURI("test://test.aet")));
			}
		}

		@Test
		@DisplayName("with null URI")
		public void nullUri() throws Exception {
			try (ChangeDerivingView view = new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource,
					mockViewSelection), new DefaultStateBasedChangeResolutionStrategy())) {
				Root root = aet.Root();
				assertThrows(IllegalArgumentException.class, () -> view.registerRoot(root, null));
			}
		}

		@Test
		@DisplayName("with proper arguments")
		public void properArguments() throws Exception {
			try (ChangeDerivingView view = new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource,
					mockViewSelection), new DefaultStateBasedChangeResolutionStrategy())) {
				Root root = aet.Root();
				String testResourceUriString = "test://test.aet";
				view.registerRoot(root, URI.createURI(testResourceUriString));
				assertThat(view.getRootObjects(), hasItem(root));
			}
		}

		@Test
		@DisplayName("committing changes")
		public void commitChanges() throws Exception {
			try (ChangeDerivingView view = new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource,
					mockViewSelection), new DefaultStateBasedChangeResolutionStrategy())) {
				Root root = aet.Root();
				root.setId("root");
				String testResourceUriString = "test://test.aet";
				view.registerRoot(root, URI.createURI(testResourceUriString));
				assertThat(view.getRootObjects(), hasItem(root));
				ArgumentCaptor<ChangeDerivingView> viewArgument = ArgumentCaptor.forClass(ChangeDerivingView.class);
				ArgumentCaptor<VitruviusChange<HierarchicalId>> changeArgument = ArgumentCaptor.forClass(VitruviusChange.class);
				view.commitChanges();
				verify(mockViewType).commitViewChanges(viewArgument.capture(), changeArgument.capture());
				assertThat(viewArgument.getValue(), is(view));
				VitruviusChange<EObject> change = VitruviusChangeResolver.forHierarchicalIds(root.eResource().getResourceSet()).resolveAndApply(changeArgument.getValue());
				InsertRootEObject<EObject> expectedChange = RootFactory.eINSTANCE.createInsertRootEObject();
				expectedChange.setNewValue(root);
				expectedChange.setUri(testResourceUriString);
				assertThat(change.getEChanges().size(), is(3)); // Create, Insert, ReplaceId
				assertThat(change.getEChanges().get(1),
						equalsDeeply(expectedChange,
								ignoringFeatures(RootPackage.eINSTANCE.getRootEChange_Resource())));
				assertThat(change.getEChanges().get(2), instanceOf(ReplaceSingleValuedEAttribute.class));
			}
		}
	}

	@Nested
	@DisplayName("move root")
	public class MoveRoot {
		@Test
		@DisplayName("being null")
		public void nullElement() throws Exception {
			try (ChangeDerivingView view = new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource,
					mockViewSelection), new DefaultStateBasedChangeResolutionStrategy())) {
				assertThrows(IllegalArgumentException.class,
						() -> view.moveRoot(null, URI.createURI("test://test.aet")));
			}
		}

		@Test
		@DisplayName("with null URI")
		public void nullUri() throws Exception {
			try (ChangeDerivingView view = new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource,
					mockViewSelection), new DefaultStateBasedChangeResolutionStrategy())) {
				Root root = aet.Root();
				view.registerRoot(root, URI.createURI("test://test.aet"));
				assertThrows(IllegalArgumentException.class, () -> view.moveRoot(root, null));
			}
		}

		@Test
		@DisplayName("with element not beeing root")
		public void notBeingRoot() throws Exception {
			try (ChangeDerivingView view = new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource,
					mockViewSelection), new DefaultStateBasedChangeResolutionStrategy())) {
				Root root = aet.Root();
				assertThrows(IllegalStateException.class, () -> view.moveRoot(root, URI.createURI("test://test.aet")));
			}
		}

		@Test
		@DisplayName("with proper arguments")
		public void properArguments() throws Exception {
			try (ChangeDerivingView view = new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource,
					mockViewSelection), new DefaultStateBasedChangeResolutionStrategy())) {
				Root root = aet.Root();
				view.registerRoot(root, URI.createURI("test://test.aet"));
				view.moveRoot(root, URI.createURI("test://test2.aet"));
				assertThat(view.getRootObjects().size(), is(1));
				assertThat(view.getRootObjects(), hasItem(root));
			}
		}

		@Test
		@DisplayName("committing changes")
		public void commitChanges() throws Exception {
			try (ChangeDerivingView view = new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource,
					mockViewSelection), new DefaultStateBasedChangeResolutionStrategy())) {
				Root root = aet.Root();
				String movedResourceUriString = "test://test2.aet";
				view.registerRoot(root, URI.createURI("test://test.aet"));
				view.commitChanges();
				reset(mockChangeableViewSource, mockViewType);
				view.moveRoot(root, URI.createURI(movedResourceUriString));
				assertThat(view.getRootObjects(), hasItem(root));
				ArgumentCaptor<ChangeDerivingView> viewArgument = ArgumentCaptor.forClass(ChangeDerivingView.class);
				ArgumentCaptor<VitruviusChange<HierarchicalId>> changeArgument = ArgumentCaptor.forClass(VitruviusChange.class);
				view.commitChangesAndUpdate();
				verify(mockViewType).commitViewChanges(viewArgument.capture(), changeArgument.capture());
				assertThat(viewArgument.getValue(), is(view));
				assertTrue(changeArgument.getValue().containsConcreteChange(), "change must contain some concrete change");
				assertThat(view.getRootObjects().size(), is(1));
				Root updatedRoot = (Root)view.getRootObjects().iterator().next();
				assertThat(updatedRoot.eResource().getURI(), is(URI.createURI(movedResourceUriString)));
			}
		}
	}

	@Nested
	@DisplayName("commit")
	public class Commit {
		ChangeDerivingView view;
		Root root;

		@BeforeEach
		public void prepareViewWithRootElement() {
			view = new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource,
					mockViewSelection), new DefaultStateBasedChangeResolutionStrategy());
			root = aet.Root();
			view.registerRoot(root, URI.createURI("test://test.aet"));
			view.commitChangesAndUpdate();
			reset(mockChangeableViewSource, mockViewType);
		}

		@AfterEach
		public void closeView() throws Exception {
			view.close();
		}

		@Test
		@DisplayName("once")
		public void once() {
			NonRoot nonRoot = aet.NonRoot();
			nonRoot.setId("nonRoot");
			root.setSingleValuedContainmentEReference(nonRoot);
			ArgumentCaptor<ChangeDerivingView> viewArgument = ArgumentCaptor.forClass(ChangeDerivingView.class);
			ArgumentCaptor<VitruviusChange<HierarchicalId>> changeArgument = ArgumentCaptor.forClass(VitruviusChange.class);
			view.commitChangesAndUpdate();
			verify(mockViewType).commitViewChanges(viewArgument.capture(), changeArgument.capture());
			assertThat(viewArgument.getValue(), is(view));
			assertTrue(changeArgument.getValue().containsConcreteChange(), "change must contain some concrete change");
			assertThat(view.getRootObjects().size(), is(1));
			Root root = (Root)view.getRootObjects().iterator().next();
			assertThat(root.eContents().size(), is(1));
			assertThat(root.eContents(), hasItem(nonRoot));
		}

		@Test
		@DisplayName("twice")
		public void twice() {
			NonRoot firstNonRoot = aet.NonRoot();
			firstNonRoot.setId("first");
			root.setSingleValuedContainmentEReference(firstNonRoot);
			view.commitChanges();
			reset(mockChangeableViewSource, mockViewType);
			NonRoot secondNonRoot = aet.NonRoot();
			secondNonRoot.setId("second");
			root.setSingleValuedContainmentEReference(secondNonRoot);
			ArgumentCaptor<ChangeDerivingView> viewArgument = ArgumentCaptor.forClass(ChangeDerivingView.class);
			ArgumentCaptor<VitruviusChange<HierarchicalId>> changeArgument = ArgumentCaptor.forClass(VitruviusChange.class);
			view.commitChangesAndUpdate();
			verify(mockViewType).commitViewChanges(viewArgument.capture(), changeArgument.capture());
			assertThat(viewArgument.getValue(), is(view));
			assertTrue(changeArgument.getValue().containsConcreteChange(), "change must contain some concrete change");
			assertThat(view.getRootObjects().size(), is(1));
			Root root = (Root)view.getRootObjects().iterator().next();
			assertThat(root.eContents().size(), is(1));
			assertThat(root.eContents(), hasItem(secondNonRoot));
		}

		@Test
		@DisplayName("without changes")
		public void withoutChanges() {
			ArgumentCaptor<ChangeDerivingView> viewArgument = ArgumentCaptor.forClass(ChangeDerivingView.class);
			ArgumentCaptor<VitruviusChange<HierarchicalId>> changeArgument = ArgumentCaptor.forClass(VitruviusChange.class);
			view.commitChangesAndUpdate();
			verify(mockViewType).commitViewChanges(viewArgument.capture(), changeArgument.capture());
			assertThat(viewArgument.getValue(), is(view));
			assertFalse(changeArgument.getValue().containsConcreteChange(), "change must be empty");
			assertThat(view.getRootObjects().size(), is(1));
		}
	}

	@Nested
	@DisplayName("close")
	public class Close {
		@Test
		@DisplayName("and is closed afterwards")
		public void isClosed() throws Exception {
			ChangeDerivingView view = new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource,
					mockViewSelection), new DefaultStateBasedChangeResolutionStrategy());
			view.close();
			assertThat("view should be closed", view.isClosed());
		}

		@Test
		@DisplayName("can be called multiple times")
		public void callMultipleTimes() throws Exception {
			ChangeDerivingView view = new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource,
					mockViewSelection), new DefaultStateBasedChangeResolutionStrategy());
			view.close();
			view.close();
			assertThat("view should be closed", view.isClosed());
		}

		@Test
		@DisplayName("and does not allow further operations")
		public void noOperations() throws Exception {
			ChangeDerivingView view = new ChangeDerivingView(new BasicView(mockViewType, mockChangeableViewSource,
					mockViewSelection), new DefaultStateBasedChangeResolutionStrategy());
			view.close();
			assertThrows(IllegalStateException.class, () -> view.getRootObjects());
			assertThrows(IllegalStateException.class, () -> view.getRootObjects(Root.class));
			assertThrows(IllegalStateException.class, () -> view.update());
			assertThrows(IllegalStateException.class, () -> view.commitChanges());
			assertThrows(IllegalStateException.class, () -> view.registerRoot(null, null));
			assertThrows(IllegalStateException.class, () -> view.moveRoot(null, null));
		}
	}
}
