package tools.vitruv.framework.vsum.views.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet;
import allElementTypes.AllElementTypesPackage;
import allElementTypes.NonRoot;
import allElementTypes.Root;
import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.CreateEObject;
import tools.vitruv.framework.change.echange.eobject.DeleteEObject;
import tools.vitruv.framework.change.echange.eobject.EobjectPackage;
import tools.vitruv.framework.change.echange.feature.FeaturePackage;
import tools.vitruv.framework.change.echange.feature.attribute.AttributeFactory;
import tools.vitruv.framework.change.echange.feature.attribute.AttributePackage;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.framework.change.echange.root.InsertRootEObject;
import tools.vitruv.framework.change.echange.root.RootFactory;
import tools.vitruv.framework.change.echange.root.RootPackage;
import tools.vitruv.framework.vsum.views.ChangeableViewSource;
import tools.vitruv.framework.vsum.views.ModifiableViewSelection;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply;
import static tools.vitruv.testutils.matchers.ModelMatchers.ignoringFeatures;;

@ExtendWith({ TestLogging.class, RegisterMetamodelsInStandalone.class })
public class RecordingViewTest {
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
					() -> new RecordingView(null, mockChangeableViewSource, mockViewSelection));
		}

		@Test
		@DisplayName("with null view source")
		public void withNullViewSource() {
			assertThrows(IllegalArgumentException.class,
					() -> new RecordingView(mockViewType, null, mockViewSelection));
		}

		@Test
		@DisplayName("with null view selection")
		public void withNullViewSelection() {
			assertThrows(IllegalArgumentException.class,
					() -> new RecordingView(mockViewType, mockChangeableViewSource, null));
		}

		@Test
		@DisplayName("with proper arguments")
		public void withEmptySource() throws Exception {
			try (RecordingView view = new RecordingView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
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
			try (RecordingView view = new RecordingView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
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
			try (RecordingView view = new RecordingView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
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
			try (RecordingView view = new RecordingView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
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
			try (RecordingView view = new RecordingView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
				view.update();
				verify(mockViewType, times(2)).updateView(view);
			}
		}

		@Test
		@DisplayName("with previous modification")
		public void withPreviousModification() throws Exception {
			try (RecordingView view = new RecordingView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
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
			try (RecordingView view = new RecordingView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
				assertThrows(IllegalArgumentException.class,
						() -> view.registerRoot(null, URI.createURI("test://test.aet")));
			}
		}

		@Test
		@DisplayName("with null URI")
		public void nullUri() throws Exception {
			try (RecordingView view = new RecordingView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
				Root root = aet.Root();
				assertThrows(IllegalArgumentException.class, () -> view.registerRoot(root, null));
			}
		}

		@Test
		@DisplayName("with proper arguments")
		public void properArguments() throws Exception {
			try (RecordingView view = new RecordingView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
				Root root = aet.Root();
				String testResourceUriString = "test://test.aet";
				view.registerRoot(root, URI.createURI(testResourceUriString));
				assertThat(view.getRootObjects(), hasItem(root));
			}
		}

		@Test
		@DisplayName("committing changes")
		public void commitChanges() throws Exception {
			try (RecordingView view = new RecordingView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
				Root root = aet.Root();
				root.setId("root");
				String testResourceUriString = "test://test.aet";
				view.registerRoot(root, URI.createURI(testResourceUriString));
				assertThat(view.getRootObjects(), hasItem(root));
				ArgumentCaptor<VitruviusChange> argument = ArgumentCaptor.forClass(VitruviusChange.class);
				view.commitChanges();
				verify(mockChangeableViewSource).propagateChange(argument.capture());
				InsertRootEObject<EObject> expectedChange = RootFactory.eINSTANCE.createInsertRootEObject();
				expectedChange.setNewValue(root);
				expectedChange.setUri(testResourceUriString);
				assertThat(argument.getValue().getEChanges().size(), is(3)); // Create, Insert, ReplaceId
				assertThat(argument.getValue().getEChanges().get(1),
						equalsDeeply(expectedChange,
								ignoringFeatures(EobjectPackage.eINSTANCE.getEObjectAddedEChange_NewValueID(),
										RootPackage.eINSTANCE.getRootEChange_Resource())));
				assertThat(argument.getValue().getEChanges().get(2), instanceOf(ReplaceSingleValuedEAttribute.class));
			}
		}
	}

	@Nested
	@DisplayName("move root")
	public class MoveRoot {
		@Test
		@DisplayName("being null")
		public void nullElement() throws Exception {
			try (RecordingView view = new RecordingView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
				assertThrows(IllegalArgumentException.class,
						() -> view.moveRoot(null, URI.createURI("test://test.aet")));
			}
		}

		@Test
		@DisplayName("with null URI")
		public void nullUri() throws Exception {
			try (RecordingView view = new RecordingView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
				Root root = aet.Root();
				view.registerRoot(root, URI.createURI("test://test.aet"));
				assertThrows(IllegalArgumentException.class, () -> view.moveRoot(root, null));
			}
		}

		@Test
		@DisplayName("with element not beeing root")
		public void notBeingRoot() throws Exception {
			try (RecordingView view = new RecordingView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
				Root root = aet.Root();
				assertThrows(IllegalStateException.class, () -> view.moveRoot(root, URI.createURI("test://test.aet")));
			}
		}

		@Test
		@DisplayName("with proper arguments")
		public void properArguments() throws Exception {
			try (RecordingView view = new RecordingView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
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
			try (RecordingView view = new RecordingView(mockViewType, mockChangeableViewSource, mockViewSelection)) {
				Root root = aet.Root();
				String movedResourceUriString = "test://test2.aet";
				view.registerRoot(root, URI.createURI("test://test.aet"));
				view.commitChanges();
				reset(mockChangeableViewSource);
				view.moveRoot(root, URI.createURI(movedResourceUriString));
				assertThat(view.getRootObjects(), hasItem(root));
				view.commitChanges();
				ArgumentCaptor<VitruviusChange> argument = ArgumentCaptor.forClass(VitruviusChange.class);
				verify(mockChangeableViewSource).propagateChange(argument.capture());
				List<EChange> capturedEChanges = argument.getValue().getEChanges();
				InsertRootEObject<EObject> expectedChange = RootFactory.eINSTANCE.createInsertRootEObject();
				expectedChange.setNewValue(root);
				expectedChange.setUri(movedResourceUriString);
				assertThat(capturedEChanges.size(), is(2)); // Remove, Insert
				assertThat(capturedEChanges.get(1),
						equalsDeeply(expectedChange,
								ignoringFeatures(EobjectPackage.eINSTANCE.getEObjectAddedEChange_NewValueID(),
										RootPackage.eINSTANCE.getRootEChange_Resource())));
			}
		}
	}

	@Nested
	@DisplayName("commit")
	public class Commit {
		RecordingView view;
		Root root;

		@BeforeEach
		public void prepareViewWithRootElement() {
			view = new RecordingView(mockViewType, mockChangeableViewSource, mockViewSelection);
			root = aet.Root();
			view.registerRoot(root, URI.createURI("test://test.aet"));
			view.commitChanges();
			reset(mockChangeableViewSource);
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
			root.setSingleValuedContainmentEReference(aet.NonRoot());
			view.commitChanges();
			ArgumentCaptor<VitruviusChange> argument = ArgumentCaptor.forClass(VitruviusChange.class);
			verify(mockChangeableViewSource).propagateChange(argument.capture());
			List<EChange> capturedEChanges = argument.getValue().getEChanges();
			assertThat(capturedEChanges.size(), is(3)); // Create, Insert, ReplaceId
			assertThat(capturedEChanges.get(0), instanceOf(CreateEObject.class));
			assertThat(capturedEChanges.get(1), instanceOf(ReplaceSingleValuedEReference.class));
			assertThat(capturedEChanges.get(2), instanceOf(ReplaceSingleValuedEAttribute.class));
		}

		@Test
		@DisplayName("twice")
		public void twice() {
			NonRoot firstNonRoot = aet.NonRoot();
			firstNonRoot.setId("first");
			root.setSingleValuedContainmentEReference(firstNonRoot);
			view.commitChanges();
			reset(mockChangeableViewSource);
			NonRoot secondNonRoot = aet.NonRoot();
			secondNonRoot.setId("second");
			root.setSingleValuedContainmentEReference(secondNonRoot);
			view.commitChanges();
			ArgumentCaptor<VitruviusChange> argument = ArgumentCaptor.forClass(VitruviusChange.class);
			verify(mockChangeableViewSource).propagateChange(argument.capture());
			List<EChange> capturedEChanges = argument.getValue().getEChanges();
			assertThat(capturedEChanges.size(), is(4)); // Create, Insert, Delete, ReplaceValue
			assertThat(capturedEChanges.get(0), instanceOf(CreateEObject.class));
			assertThat(capturedEChanges.get(1), instanceOf(ReplaceSingleValuedEReference.class));
			assertThat(capturedEChanges.get(2), instanceOf(DeleteEObject.class));
			ReplaceSingleValuedEAttribute<EObject, Object> replaceIdChange = AttributeFactory.eINSTANCE
					.createReplaceSingleValuedEAttribute();
			replaceIdChange.setAffectedEObject(secondNonRoot);
			replaceIdChange.setAffectedFeature(AllElementTypesPackage.eINSTANCE.getIdentified_Id());
			replaceIdChange.setNewValue("second");
			assertThat(capturedEChanges.get(3),
					equalsDeeply(replaceIdChange,
							ignoringFeatures(AttributePackage.eINSTANCE.getSubtractiveAttributeEChange_OldValue(),
									FeaturePackage.eINSTANCE.getFeatureEChange_AffectedEObjectID())));
		}

		@Test
		@DisplayName("without changes")
		public void withoutChanges() {
			view.commitChanges();
			ArgumentCaptor<VitruviusChange> argument = ArgumentCaptor.forClass(VitruviusChange.class);
			verify(mockChangeableViewSource).propagateChange(argument.capture());
			assertThat(argument.getValue().getEChanges(), not(hasItem(anything())));
		}
	}

	@Nested
	@DisplayName("close")
	public class Close {
		@Test
		@DisplayName("and is closed afterwards")
		public void isClosed() throws Exception {
			RecordingView view = new RecordingView(mockViewType, mockChangeableViewSource, mockViewSelection);
			view.close();
			assertThat("view should be closed", view.isClosed());
		}

		@Test
		@DisplayName("can be called multiple times")
		public void callMultipleTimes() throws Exception {
			RecordingView view = new RecordingView(mockViewType, mockChangeableViewSource, mockViewSelection);
			view.close();
			view.close();
			assertThat("view should be closed", view.isClosed());
		}

		@Test
		@DisplayName("and does not allow further operations")
		public void noOperations() throws Exception {
			RecordingView view = new RecordingView(mockViewType, mockChangeableViewSource, mockViewSelection);
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
