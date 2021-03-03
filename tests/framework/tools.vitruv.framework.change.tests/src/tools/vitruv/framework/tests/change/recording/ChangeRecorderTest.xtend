package tools.vitruv.framework.tests.change.recording

import org.junit.jupiter.api.Test
import tools.vitruv.framework.change.recording.ChangeRecorder
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.resource.ResourceSet
import org.junit.jupiter.api.DisplayName
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.is
import static org.junit.jupiter.api.Assertions.assertThrows
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.eclipse.emf.common.util.URI
import static extension tools.vitruv.framework.util.ResourceSetUtil.withGlobalFactories
import tools.vitruv.framework.change.echange.EChange
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import java.util.List
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import tools.vitruv.testutils.TestProject
import java.nio.file.Path
import allElementTypes.Root
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.testutils.TestProjectManager
import tools.vitruv.testutils.RegisterMetamodelsInStandalone
import static org.junit.jupiter.api.Assertions.assertTrue
import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import org.eclipse.emf.ecore.InternalEObject
import static tools.vitruv.framework.uuid.UuidGeneratorAndResolverFactory.createUuidGeneratorAndResolver

@ExtendWith(TestProjectManager, RegisterMetamodelsInStandalone)
class ChangeRecorderTest {
	// this test only covers general behaviour of ChangeRecorder. Whether it always produces correct change sequences
	// is covered by other tests
	val ResourceSet resourceSet = new ResourceSetImpl().withGlobalFactories()
	var ChangeRecorder changeRecorder = new ChangeRecorder(createUuidGeneratorAndResolver(resourceSet))
	
	@Test
	@DisplayName("records direct changes to an object")
	def void recordOnObject() {
		val root = aet.Root
		changeRecorder.addToRecording(root)
		changeRecorder.beginRecording()
		root.id = 'test'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(ReplaceSingleValuedEAttribute))
	}
	
	@Test
	@DisplayName("records direct changes to a resource")
	def void recordOnResource() {
		val resource = resourceSet.createResource(URI.createURI('test://test.aet'))
		changeRecorder.addToRecording(resource)
		changeRecorder.beginRecording()
		resource.contents += aet.Root
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(CreateEObject, InsertRootEObject, ReplaceSingleValuedEAttribute))
	}
	
	@Test
	@DisplayName("stops recording changes for an object")
	def void stopRecordingOnObject() {
		val root = aet.Root
		changeRecorder.addToRecording(root)
		changeRecorder.beginRecording()
		changeRecorder.removeFromRecording(root)
		root.id = 'test'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasNoChanges)
	}
	
	@Test
	@DisplayName("stops recording changes for a resource")
	def void stopRecordingOnResource() {
		val resource = resourceSet.createResource(URI.createURI('test://test.aet'))
		changeRecorder.addToRecording(resource)
		changeRecorder.beginRecording()
		changeRecorder.removeFromRecording(resource)
		resource.contents += aet.Root
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasNoChanges)
	}
	
	@Test
	@DisplayName("records changes to all children of an object")
	def void recordsOnObjectChildren() {
		val inner = aet.NonRoot
		val root = aet.Root => [
			nonRootObjectContainerHelper = aet.NonRootObjectContainerHelper => [
				nonRootObjectsContainment += aet.NonRoot
				nonRootObjectsContainment += inner
			]
		]
		changeRecorder.addToRecording(root)
		changeRecorder.beginRecording()
		inner.id = 'test'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(ReplaceSingleValuedEAttribute))
	}
	
	@Test
	@DisplayName("records changes to all children of a resource")
	def void recordsOnResourceChildren() {
		val inner = aet.NonRoot
		val resource = resourceSet.createResource(URI.createURI('test://test.aet')) => [
			contents += aet.Root => [
				nonRootObjectContainerHelper = aet.NonRootObjectContainerHelper => [
					nonRootObjectsContainment += aet.NonRoot
					nonRootObjectsContainment += inner
				]
			]
		]
		changeRecorder.addToRecording(resource)
		changeRecorder.beginRecording()
		inner.id = 'test'
		changeRecorder.endRecording()

		assertThat(changeRecorder.changes, hasEChanges(ReplaceSingleValuedEAttribute))
	}
	
	@Test
	@DisplayName("records changes to all children of a resource set")
	def void recordsOnResourceSetChildren() {
		val inner = aet.NonRoot
		val resource = resourceSet.createResource(URI.createURI('test://test.aet')) => [
			contents += aet.Root => [
				nonRootObjectContainerHelper = aet.NonRootObjectContainerHelper => [
					nonRootObjectsContainment += aet.NonRoot
					nonRootObjectsContainment += inner
				]
			]
		]
		changeRecorder.addToRecording(resourceSet)
		changeRecorder.beginRecording()
		inner.id = 'test'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(ReplaceSingleValuedEAttribute))
		
		changeRecorder.beginRecording()
		resource.contents.clear()
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(RemoveRootEObject, DeleteEObject))
	}
	
	@ParameterizedTest(name = "while isRecording={0}")
	@DisplayName("adds an object set as containment to the recording")
	@ValueSource(booleans = #[false, true])
	def void recordsOnSetContainment(boolean isRecordingWhileAddingObject) {
		val root = aet.Root
		changeRecorder.addToRecording(root)
		val nonRoot = aet.NonRoot
		if (isRecordingWhileAddingObject) changeRecorder.beginRecording()
		root.singleValuedContainmentEReference = nonRoot
		if (isRecordingWhileAddingObject) changeRecorder.endRecording()
		
		changeRecorder.beginRecording()
		nonRoot.id = 'foobar'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(ReplaceSingleValuedEAttribute))
	}
	
	@ParameterizedTest(name = "while isRecording={0}")
	@DisplayName("adds an object added as containment to the recording")
	@ValueSource(booleans = #[false, true])
	def void recordsOnAddedContainment(boolean isRecordingWhileAddingObject) {
		val root = aet.Root
		changeRecorder.addToRecording(root)
		val nonRoot = aet.NonRoot
		if (isRecordingWhileAddingObject) changeRecorder.beginRecording()
		root.multiValuedContainmentEReference += nonRoot
		if (isRecordingWhileAddingObject) changeRecorder.endRecording()
		
		changeRecorder.beginRecording()
		nonRoot.id = 'foobar'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(ReplaceSingleValuedEAttribute))
	}
	
	@ParameterizedTest(name = "while isRecording={0}")
	@DisplayName("adds an object that was resolved from its proxy to the recording")
	@ValueSource(booleans = #[false, true])
	def void recordsOnResolvedProxyInContainment(boolean isRecordingWhileAddingObject, @TestProject Path testDir) {
		val savedNonRoot = aet.NonRoot
		resourceSet.createResource(URI.createFileURI(testDir.resolve('test.aet').toString)) => [
			contents += aet.Root => [
				singleValuedContainmentEReference = savedNonRoot
			]
			save(emptyMap)
		]
		val root = aet.Root => [
			singleValuedContainmentEReference = aet.NonRoot => [
				(it as InternalEObject).eSetProxyURI(savedNonRoot.URI)
			]
		]
		// proxy resolving should be done in resources
		resourceSet.createResource(URI.createURI('test://test2.aet')) => [contents += root]
		if (isRecordingWhileAddingObject) changeRecorder.beginRecording()
		// we currently resolve containment proxies when adding an object
		changeRecorder.addToRecording(root)
		val nonRoot = root.singleValuedContainmentEReference
		if (isRecordingWhileAddingObject) changeRecorder.endRecording()
		
		changeRecorder.beginRecording()
		nonRoot.id = 'foobar'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(ReplaceSingleValuedEAttribute))
	}
	
	@ParameterizedTest(name = "while isRecording={0}")
	@DisplayName("adds multiple objects added as containments to the recording")
	@ValueSource(booleans = #[false, true])
	def void recordsOnMultipleAddedContainment(boolean isRecordingWhileAddingObject) {
		val root = aet.Root
		changeRecorder.addToRecording(root)
		val nonRoot1 = aet.NonRoot
		val nonRoot2 = aet.NonRoot
		if (isRecordingWhileAddingObject) changeRecorder.beginRecording()
		root.multiValuedContainmentEReference += List.of(nonRoot1, nonRoot2)
		if (isRecordingWhileAddingObject) changeRecorder.endRecording()
		
		changeRecorder.beginRecording()
		nonRoot1.id = 'foobar1'
		nonRoot2.id = 'foobar2'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(ReplaceSingleValuedEAttribute, ReplaceSingleValuedEAttribute))
	}
	
	@ParameterizedTest(name = "while isRecording={0}")
	@DisplayName("adds an object added as root to the recording")
	@ValueSource(booleans = #[false, true])
	def void recordsOnAddedRoot(boolean isRecordingWhileAddingObject) {
		val resource = resourceSet.createResource(URI.createURI('test://test.aet'))
		val root = aet.Root
		changeRecorder.addToRecording(resource)
		if (isRecordingWhileAddingObject) changeRecorder.beginRecording()
		resource.contents += root
		if (isRecordingWhileAddingObject) changeRecorder.endRecording()
		
		changeRecorder.beginRecording()
		root.id = 'foobar'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(ReplaceSingleValuedEAttribute))
	}
	
	@ParameterizedTest(name = "while isRecording={0}")
	@DisplayName("adds multiple objects added as roots to the recording")
	@ValueSource(booleans = #[false, true])
	def void recordsOnMultipleAddedRoot(boolean isRecordingWhileAddingObject) {
		val resource = resourceSet.createResource(URI.createURI('test://test.aet'))
		val root1 = aet.Root
		val root2 = aet.Root
		changeRecorder.addToRecording(resource)
		if (isRecordingWhileAddingObject) changeRecorder.beginRecording()
		resource.contents += List.of(root1, root2)
		if (isRecordingWhileAddingObject) changeRecorder.endRecording()
		
		changeRecorder.beginRecording()
		root1.id = 'foobar1'
		root2.id = 'foobar2'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(ReplaceSingleValuedEAttribute, ReplaceSingleValuedEAttribute))
	}
	
	@ParameterizedTest(name = "while isRecording={0}")
	@DisplayName("adds loaded objects to the recording")
	@ValueSource(booleans = #[false, true])
	def void recordesOnLoadedObject(boolean isRecordingWhileAddingObject, @TestProject Path testProject) {
		val resourceUri = URI.createFileURI(testProject.resolve("test.aet").toString)
		resourceSet.createResource(resourceUri) => [
			contents += aet.Root => [
				singleValuedContainmentEReference = aet.NonRoot
			]
			save(emptyMap)
		]
		resourceSet.resources.clear()
		val resource = resourceSet.createResource(resourceUri)
		changeRecorder.addToRecording(resource)
		if (isRecordingWhileAddingObject) changeRecorder.beginRecording()
		resource.load(emptyMap)
		if (isRecordingWhileAddingObject) changeRecorder.endRecording()
		
		changeRecorder.beginRecording()
		(resource.contents.get(0) as Root).singleValuedContainmentEReference.id = 'test'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(ReplaceSingleValuedEAttribute))
	}
	
	@ParameterizedTest(name = "while isRecording={0}")
	@DisplayName("adds a resource to the recording")
	@ValueSource(booleans = #[false, true])
	def void recordsOnAddedResource(boolean isRecordingWhileAddingObject) {
		changeRecorder.addToRecording(resourceSet)
		if (isRecordingWhileAddingObject) changeRecorder.beginRecording()
		val resource = resourceSet.createResource(URI.createURI('test://test.aet'))
		if (isRecordingWhileAddingObject) changeRecorder.endRecording()
		
		changeRecorder.beginRecording()
		resource.contents += aet.Root
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(CreateEObject, InsertRootEObject, ReplaceSingleValuedEAttribute))
	}
	
	@ParameterizedTest(name = "while isRecording={0}")
	@DisplayName("adds multiple added resources to the recording")
	@ValueSource(booleans = #[false, true])
	def void recordsOnMultipleAddedResource(boolean isRecordingWhileAddingObject) {
		val foreignResourceSet = new ResourceSetImpl().withGlobalFactories()
		val resource1 = foreignResourceSet.createResource(URI.createURI('test://test1.aet'))
		val resource2 = foreignResourceSet.createResource(URI.createURI('test://test2.aet'))
		changeRecorder.addToRecording(resourceSet)
		if (isRecordingWhileAddingObject) changeRecorder.beginRecording()
		resourceSet.resources += List.of(resource1, resource2)
		if (isRecordingWhileAddingObject) changeRecorder.endRecording()
		
		changeRecorder.beginRecording()
		resource1.contents += aet.Root
		resource2.contents += aet.Root
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(
			CreateEObject, InsertRootEObject, ReplaceSingleValuedEAttribute,
			CreateEObject, InsertRootEObject, ReplaceSingleValuedEAttribute
		))
	}
	
	@ParameterizedTest(name = "while isRecording={0}")
	@DisplayName("removes an object unset from a containment reference from the recording")
	@ValueSource(booleans = #[false, true])
	def void removeAfterContainmentUnset(boolean isRecordingWhileRemovingObject) {
		val nonRoot = aet.NonRoot
		val root = aet.Root => [
			singleValuedContainmentEReference = nonRoot
		]
		changeRecorder.addToRecording(root)
		if (isRecordingWhileRemovingObject) changeRecorder.beginRecording()
		root.singleValuedContainmentEReference = null
		if (isRecordingWhileRemovingObject) changeRecorder.endRecording()
		
		changeRecorder.beginRecording()
		nonRoot.id = 'foobar'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasNoChanges)
	}
	
	@ParameterizedTest(name = "while isRecording={0}")
	@DisplayName("removes an object removed from a containment reference from the recording")
	@ValueSource(booleans = #[false, true])
	def void removeAfterContainmentRemove(boolean isRecordingWhileRemovingObject) {
		val nonRoot = aet.NonRoot
		val root = aet.Root => [
			multiValuedContainmentEReference += nonRoot
		]
		changeRecorder.addToRecording(root)
		if (isRecordingWhileRemovingObject) changeRecorder.beginRecording()
		root.multiValuedContainmentEReference.clear()
		if (isRecordingWhileRemovingObject) changeRecorder.endRecording()
		
		changeRecorder.beginRecording()
		nonRoot.id = 'foobar'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasNoChanges)
	}
	
	@ParameterizedTest(name = "while isRecording={0}")
	@DisplayName("removes multiple objects removed from a containment reference from the recording")
	@ValueSource(booleans = #[false, true])
	def void removeAfterContainmentMultipleRemove(boolean isRecordingWhileRemovingObject) {
		val nonRoot1 = aet.NonRoot
		val nonRoot2 = aet.NonRoot
		val nonRoot3 = aet.NonRoot
		val root = aet.Root => [
			multiValuedContainmentEReference += List.of(nonRoot1, nonRoot2, nonRoot3)
		]
		changeRecorder.addToRecording(root)
		if (isRecordingWhileRemovingObject) changeRecorder.beginRecording()
		root.multiValuedContainmentEReference -= List.of(nonRoot1, nonRoot3)
		if (isRecordingWhileRemovingObject) changeRecorder.endRecording()
		
		changeRecorder.beginRecording()
		nonRoot1.id = 'foobar1'
		nonRoot2.id = 'foobar2'
		nonRoot3.id = 'foobar3'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(ReplaceSingleValuedEAttribute))
	}
	
	@ParameterizedTest(name = "while isRecording={0}")
	@DisplayName("removes a removed resource from the recording")
	@ValueSource(booleans = #[false, true])
	def void removeAfterRemovedResource(boolean isRecordingWhileRemovingObject) {
		changeRecorder.addToRecording(resourceSet)
		val resource = resourceSet.createResource(URI.createURI('test://test1.aet'))
		
		if (isRecordingWhileRemovingObject) changeRecorder.beginRecording()
		resourceSet.resources.clear()
		if (isRecordingWhileRemovingObject) changeRecorder.endRecording()
		
		changeRecorder.beginRecording()
		resource.contents += aet.Root
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasNoChanges)
	}
	
	@ParameterizedTest(name = "while isRecording={0}")
	@DisplayName("removes multiple removed resources from the recording")
	@ValueSource(booleans = #[false, true])
	def void removeAfterMultipleRemovedResource(boolean isRecordingWhileRemovingObject) {
		val resource1 = resourceSet.createResource(URI.createURI('test://test1.aet'))
		val resource2 = resourceSet.createResource(URI.createURI('test://test2.aet'))
		val resource3 = resourceSet.createResource(URI.createURI('test://test3.aet'))
		changeRecorder.addToRecording(resourceSet)
		if (isRecordingWhileRemovingObject) changeRecorder.beginRecording()
		resourceSet.resources -= List.of(resource1, resource3)
		if (isRecordingWhileRemovingObject) changeRecorder.endRecording()
		
		changeRecorder.beginRecording()
		resource1.contents += aet.Root
		resource2.contents += aet.Root
		resource3.contents += aet.Root
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(CreateEObject, InsertRootEObject, ReplaceSingleValuedEAttribute))
	}
	
	@ParameterizedTest(name = "while isRecording={0}")
	@DisplayName("removes unloaded objects from the recording")
	@ValueSource(booleans = #[false, true])
	def void removeAfterUnload(boolean isRecordingWhileRemovingObject) {
		val nonRoot = aet.NonRoot
		val resource = resourceSet.createResource(URI.createURI('test://test1.aet')) => [
			contents += aet.Root => [
				singleValuedContainmentEReference = nonRoot
			]
		]
		changeRecorder.addToRecording(resource)
		if (isRecordingWhileRemovingObject) changeRecorder.beginRecording()
		resource.unload()
		if (isRecordingWhileRemovingObject) changeRecorder.endRecording()
		
		changeRecorder.beginRecording()
		nonRoot.id = 'test'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasNoChanges)
	}
	
	@ParameterizedTest(name = "while isRecording={0}")
	@DisplayName("removes an object removed as root from the recording")
	@ValueSource(booleans = #[false, true])
	def void removeAfterRemovedRoot(boolean isRecordingWhileRemovingObject) {
		val root = aet.Root
		val resource = resourceSet.createResource(URI.createURI('test://test.aet')) => [
			contents += root
		]
		changeRecorder.addToRecording(resource)
		if (isRecordingWhileRemovingObject) changeRecorder.beginRecording()
		resource.contents.clear()
		if (isRecordingWhileRemovingObject) changeRecorder.endRecording()
		
		changeRecorder.beginRecording()
		root.id = 'foobar'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasNoChanges)
	}
	
	@ParameterizedTest(name = "while isRecording={0}")
	@DisplayName("removes multiple objects removed as roots from the recording")
	@ValueSource(booleans = #[false, true])
	def void removeAfterMultipleRemovedRoot(boolean isRecordingWhileAddingObject) {
		val root1 = aet.Root
		val root2 = aet.Root
		val root3 = aet.Root
		val resource = resourceSet.createResource(URI.createURI('test://test.aet')) => [
			contents += List.of(root1, root2, root3)
		]
		changeRecorder.addToRecording(resource)
		if (isRecordingWhileAddingObject) changeRecorder.beginRecording()
		resource.contents -= List.of(root1, root3)
		if (isRecordingWhileAddingObject) changeRecorder.endRecording()
		
		changeRecorder.beginRecording()
		root1.id = 'foobar1'
		root2.id = 'foobar2'
		root3.id = 'foobar3'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(ReplaceSingleValuedEAttribute))
	}
	
	@Test
	@DisplayName("does not remove explicitly added child objects")
	def void dontRemoveExplicitlyAddedChild() {
		val nonRoot = aet.NonRoot
		changeRecorder.addToRecording(nonRoot)
		val root = aet.Root => [
			singleValuedContainmentEReference = nonRoot
		]
		changeRecorder.addToRecording(root)
		root.singleValuedContainmentEReference = null
		
		changeRecorder.beginRecording()
		nonRoot.id = 'testid'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(ReplaceSingleValuedEAttribute))
	}
	
	@Test
	@DisplayName("does not remove explicitly added root objects")
	def void dontRemoveExplicitlyAddedRoot() {
		val nonRoot = aet.NonRoot
		val root = aet.Root => [
			singleValuedContainmentEReference = nonRoot
		]
		changeRecorder.addToRecording(root)
		val resource = resourceSet.createResource(URI.createURI('test://test.aet')) => [
			contents += root
		]
		changeRecorder.addToRecording(resource)
		resource.contents.clear()
		
		changeRecorder.beginRecording()
		root.id = 'rootid'
		nonRoot.id = 'testid'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(ReplaceSingleValuedEAttribute, ReplaceSingleValuedEAttribute))
	}
	
	@Test
	@DisplayName("resets the recorded changes after ending the recording")
	def void resetsChangesAfterEndRecording() {
		val root = aet.Root
		changeRecorder.addToRecording(root)
		changeRecorder.beginRecording()
		root.id = 'test'
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(ReplaceSingleValuedEAttribute))
		
		changeRecorder.beginRecording()
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasNoChanges)
		
		changeRecorder.beginRecording()
		root.multiValuedNonContainmentEReference += aet.NonRoot
		changeRecorder.endRecording()
		
		assertThat(changeRecorder.changes, hasEChanges(CreateEObject, InsertEReference, ReplaceSingleValuedEAttribute))
	}
	
	@Test
	@DisplayName("refuses to record changes on a different resource set than the one of the UUID resolver")
	def void differentResourceSet() {
		assertThrows(IllegalArgumentException) [
			changeRecorder.addToRecording(new ResourceSetImpl)
		]
	}
	
	@Test
	@DisplayName("refuses to record changes on a resource from a different resource set than the one of the UUID resolver")
	def void resourceFromDifferentResourceSet() {
		val foreignResourceSet = new ResourceSetImpl().withGlobalFactories()
		assertThrows(IllegalArgumentException) [
			changeRecorder.addToRecording(foreignResourceSet.createResource(URI.createURI('test://test.aet')))
		]
	}
	
	@Test
	@DisplayName("refuses to record changes on an object from a different resource set than the one of the UUID resolver")
	def void objectFromDifferentResourceSet() {
		val foreignResourceSet = new ResourceSetImpl().withGlobalFactories()
		val root = aet.Root
		foreignResourceSet.createResource(URI.createURI('test://test.aet')) => [
			contents += root
		]
		assertThrows(IllegalArgumentException) [
			changeRecorder.addToRecording(root)
		]
	}
	
	@Test
	@DisplayName("tolerates an object without a resource")
	def void toleratesObjectWithoutResource() {
		assertDoesNotThrow [changeRecorder.addToRecording(aet.Root)]
	}
	
	@Test
	@DisplayName("tolerates an object without a resource set")
	def void toleratesObjectWithoutResourceSet() {
		val uri = URI.createURI('test://test.aet')
		val root = aet.Root
		// we need to record the creation so an UUID will be assigned, because the UUID cannot be 
		// resolved later
		changeRecorder.addToRecording(resourceSet)
		changeRecorder.beginRecording()
		resourceSet.createResource(uri) => [
			contents += root 
		]
		changeRecorder.endRecording()
		changeRecorder.removeFromRecording(resourceSet)
		resourceSet.resources.clear()
		
		assertDoesNotThrow [changeRecorder.addToRecording(root)]
	}
	
	@Test
	@DisplayName("tolerates a resource without a resource set")
	def void toleratesResourceWithoutResourceSet() {
		val uri = URI.createURI('test://test.aet')
		val resource = resourceSet.resourceFactoryRegistry.getFactory(uri).createResource(uri)
		assertDoesNotThrow [changeRecorder.addToRecording(resource)]
	}
	
	@Test
	@DisplayName("allows no interactions after being closed")
	def void noInteractionsAfterClose() {
		changeRecorder.addToRecording(aet.Root)
		changeRecorder.beginRecording()
		changeRecorder.close()
		
		assertThat(changeRecorder.isRecording, is(false))
		assertThrows(IllegalStateException) [changeRecorder.beginRecording()]
		assertThrows(IllegalStateException) [changeRecorder.endRecording()]
		assertThrows(IllegalStateException) [changeRecorder.changes]
		assertThrows(IllegalStateException) [changeRecorder.addToRecording(aet.Root)]
		assertThrows(IllegalStateException) [changeRecorder.removeFromRecording(aet.Root)]
	}
	
	@Test
	@DisplayName("registers the recorded object and all its contents at the UUID resolver")
	def void registersAtUuidResolver() {
		val parentResolver = createUuidGeneratorAndResolver(new ResourceSetImpl())
		val localResolver = createUuidGeneratorAndResolver(parentResolver, resourceSet)
		var ChangeRecorder changeRecorder = new ChangeRecorder(localResolver)
		
		val root = aet.Root => [
			parentResolver.registerEObject("uuid1", it)
			singleValuedContainmentEReference = aet.NonRoot => [
				parentResolver.registerEObject("uuid2", it)
			]
			nonRootObjectContainerHelper = aet.NonRootObjectContainerHelper => [
				parentResolver.registerEObject("uuid3", it)
				nonRootObjectsContainment += aet.NonRoot => [
					parentResolver.registerEObject("uuid4", it)
				]
				nonRootObjectsContainment += aet.NonRoot => [
					parentResolver.registerEObject("uuid5", it)
				]
			]
		]
		resourceSet.createResource(URI.createURI('test://test.aet')) => [
			contents += root 
		]
		root.nonRootObjectContainerHelper
		changeRecorder.addToRecording(resourceSet)
		
		assertTrue(localResolver.hasUuid(root))
		assertTrue(localResolver.hasUuid(root.singleValuedContainmentEReference))
		assertTrue(localResolver.hasUuid(root.nonRootObjectContainerHelper))
		assertTrue(localResolver.hasUuid(root.nonRootObjectContainerHelper.nonRootObjectsContainment.get(0)))
		assertTrue(localResolver.hasUuid(root.nonRootObjectContainerHelper.nonRootObjectsContainment.get(1)))
	}
	
	@Test
	@DisplayName("can be closed twice")
	def void closeTwice() {
		changeRecorder.close()
		assertDoesNotThrow [changeRecorder.close()]
	}
	
	def private static hasEChanges(Class<? extends EChange>... expectedTypes) {
		new EChangeSequenceMatcher(expectedTypes)
	}
	
	def private static hasNoChanges() {
		new EChangeSequenceMatcher(emptyList)
	}
	
	@FinalFieldsConstructor
	private static class EChangeSequenceMatcher extends TypeSafeMatcher<Iterable<? extends TransactionalChange>> {
		val List<Class<? extends EChange>> expectedTypes
		
		override describeTo(Description description) {
			if (expectedTypes.isEmpty) {
				description.appendText("no changes")
			} else {
				description.appendText("this sequence of EChanges: ")
					.appendText(expectedTypes.join("[", ", ", "]") [simpleName])
			}
		}
		
		override protected matchesSafely(Iterable<? extends TransactionalChange> item) {
			val actualTypes = item.flatMap [EChanges].map [class].iterator
			for (val expectedTypesIt = expectedTypes.iterator; expectedTypesIt.hasNext;) {
				if (!actualTypes.hasNext || !expectedTypesIt.next.isAssignableFrom(actualTypes.next)) {
					return false	
				}
			}
			return !actualTypes.hasNext
		}
	}
}