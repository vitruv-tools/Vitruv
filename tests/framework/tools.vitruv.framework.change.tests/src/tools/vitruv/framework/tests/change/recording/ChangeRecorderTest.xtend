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
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
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
import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.EObject
import static tools.vitruv.testutils.matchers.ModelMatchers.*
import org.eclipse.emf.ecore.util.EcoreUtil

@ExtendWith(TestProjectManager, RegisterMetamodelsInStandalone)
class ChangeRecorderTest {
	// this test only covers general behaviour of ChangeRecorder. Whether it always produces correct change sequences
	// is covered by other tests
	val ResourceSet resourceSet = new ResourceSetImpl().withGlobalFactories()
	var ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet)

	private def <T extends EObject> T wrapIntoRecordedResource(T object) {
		val resource = resourceSet.createResource(URI.createURI('test://test.aet'))
		changeRecorder.addToRecording(resource)
		record [
			resource.contents += object
		]
		return object
	}

	private def record(()=>void changes) {
		changeRecorder.beginRecording()
		changes.apply
		changeRecorder.endRecording()
	}

	private def recordIf(boolean condition, ()=>void changes) {
		if(condition) changeRecorder.beginRecording()
		changes.apply
		if(condition) changeRecorder.endRecording()
	}

	@Test
	@DisplayName("does not allow end recording twice")
	def void endRecordingTwice() {
		changeRecorder.beginRecording()
		changeRecorder.endRecording()
		assertThrows(IllegalStateException)[changeRecorder.endRecording()]
	}

	@Test
	@DisplayName("records direct changes to an object")
	def void recordOnObject() {
		val root = aet.Root
		changeRecorder.addToRecording(root)
		record [
			root.id = 'test'
		]

		assertThat(changeRecorder.change, hasEChanges(ReplaceSingleValuedEAttribute))
	}

	@Test
	@DisplayName("records direct changes to a resource")
	def void recordOnResource() {
		val resource = resourceSet.createResource(URI.createURI('test://test.aet'))
		changeRecorder.addToRecording(resource)
		record [
			resource.contents += aet.Root
		]

		assertThat(changeRecorder.change, hasEChanges(CreateEObject, InsertRootEObject, ReplaceSingleValuedEAttribute))
	}

	@Test
	@DisplayName("stops recording changes for an object")
	def void stopRecordingOnObject() {
		val root = aet.Root.wrapIntoRecordedResource()
		record [
			changeRecorder.removeFromRecording(root)
			root.id = 'test'
		]

		assertThat(changeRecorder.change, hasNoChanges)
	}

	@Test
	@DisplayName("stops recording changes for a resource")
	def void stopRecordingOnResource() {
		val resource = resourceSet.createResource(URI.createURI('test://test.aet'))
		changeRecorder.addToRecording(resource)
		record [
			changeRecorder.removeFromRecording(resource)
			resource.contents += aet.Root
		]

		assertThat(changeRecorder.change, hasNoChanges)
	}

	@Test
	@DisplayName("records changes to all children of an object")
	def void recordsOnObjectChildren() {
		val inner = aet.NonRoot
		aet.Root.wrapIntoRecordedResource() => [
			record [
				nonRootObjectContainerHelper = aet.NonRootObjectContainerHelper => [
					nonRootObjectsContainment += aet.NonRoot
					nonRootObjectsContainment += inner
				]
			]
		]
		record [
			inner.id = 'test'
		]

		assertThat(changeRecorder.change, hasEChanges(ReplaceSingleValuedEAttribute))
	}

	@Test
	@DisplayName("records changes to all children of a resource")
	def void recordsOnResourceChildren() {
		val inner = aet.NonRoot
		resourceSet.createResource(URI.createURI('test://test.aet')) => [
			changeRecorder.addToRecording(it)
			record [
				contents += aet.Root => [
					nonRootObjectContainerHelper = aet.NonRootObjectContainerHelper => [
						nonRootObjectsContainment += aet.NonRoot
						nonRootObjectsContainment += inner
					]
				]
			]
		]
		record [
			inner.id = 'test'
		]

		assertThat(changeRecorder.change, hasEChanges(ReplaceSingleValuedEAttribute))
	}

	@Test
	@DisplayName("records changes to all children of a resource set")
	def void recordsOnResourceSetChildren() {
		val inner = aet.NonRoot
		changeRecorder.addToRecording(resourceSet)
		val resource = resourceSet.createResource(URI.createURI('test://test.aet')) => [
			record [
				contents += aet.Root => [
					nonRootObjectContainerHelper = aet.NonRootObjectContainerHelper => [
						nonRootObjectsContainment += aet.NonRoot
						nonRootObjectsContainment += inner
					]
				]
			]
		]
		record [
			inner.id = 'test'
		]

		assertThat(changeRecorder.change, hasEChanges(ReplaceSingleValuedEAttribute))

		record [
			resource.contents.clear()
		]

		assertThat(changeRecorder.change, hasEChanges(RemoveRootEObject, DeleteEObject))
	}

	@DisplayName("adds an object set as containment to the recording")
	def void recordsOnSetContainment() {
		val root = aet.Root.wrapIntoRecordedResource()
		val nonRoot = aet.NonRoot
		record [
			root.singleValuedContainmentEReference = nonRoot
		]

		record [
			nonRoot.id = 'foobar'
		]

		assertThat(changeRecorder.change, hasEChanges(ReplaceSingleValuedEAttribute))
	}

	@DisplayName("adds an object added as containment to the recording")
	def void recordsOnAddedContainment() {
		val root = aet.Root.wrapIntoRecordedResource()
		val nonRoot = aet.NonRoot
		record [
			root.multiValuedContainmentEReference += nonRoot
		]

		record [
			nonRoot.id = 'foobar'
		]

		assertThat(changeRecorder.change, hasEChanges(ReplaceSingleValuedEAttribute))
	}

	@DisplayName("adds an object that was resolved from its proxy to the recording")
	def void recordsOnResolvedProxyInContainment(@TestProject Path testDir) {
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
		resourceSet.createResource(URI.createURI('test://test2.aet')) => [
			// we currently resolve containment proxies when adding an object
			changeRecorder.addToRecording(it)
			contents += root
		]
		val nonRoot = root.singleValuedContainmentEReference

		record [
			nonRoot.id = 'foobar'
		]

		assertThat(changeRecorder.change, hasEChanges(ReplaceSingleValuedEAttribute))
	}

	@DisplayName("adds multiple objects added as containments to the recording")
	def void recordsOnMultipleAddedContainment() {
		val root = aet.Root.wrapIntoRecordedResource()
		val nonRoot1 = aet.NonRoot
		val nonRoot2 = aet.NonRoot
		record [
			root.multiValuedContainmentEReference += List.of(nonRoot1, nonRoot2)
		]

		record [
			nonRoot1.id = 'foobar1'
			nonRoot2.id = 'foobar2'
		]

		assertThat(changeRecorder.change, hasEChanges(ReplaceSingleValuedEAttribute, ReplaceSingleValuedEAttribute))
	}

	@DisplayName("adds an object added as root to the recording")
	def void recordsOnAddedRoot() {
		val resource = resourceSet.createResource(URI.createURI('test://test.aet'))
		val root = aet.Root
		changeRecorder.addToRecording(resource)
		record [
			resource.contents += root
		]

		record [
			root.id = 'foobar'
		]

		assertThat(changeRecorder.change, hasEChanges(ReplaceSingleValuedEAttribute))
	}

	@DisplayName("removes a root object while recording")
	def void recordsOnRemovedRoot() {
		val resource = resourceSet.createResource(URI.createURI('test://test.aet'))
		val root = aet.Root
		changeRecorder.addToRecording(resource)
		resource.contents += root
		record [
			EcoreUtil.delete(root)
		]
		assertThat(changeRecorder.change, hasEChanges(RemoveRootEObject, DeleteEObject))
	}

	@DisplayName("adds multiple objects added as roots to the recording")
	def void recordsOnMultipleAddedRoot() {
		val resource = resourceSet.createResource(URI.createURI('test://test.aet'))
		val root1 = aet.Root
		val root2 = aet.Root
		changeRecorder.addToRecording(resource)
		record [
			resource.contents += List.of(root1, root2)
		]

		record [
			root1.id = 'foobar1'
			root2.id = 'foobar2'
		]

		assertThat(changeRecorder.change, hasEChanges(ReplaceSingleValuedEAttribute, ReplaceSingleValuedEAttribute))
	}

	@DisplayName("adds loaded objects to the recording")
	def void recordsOnLoadedObject(@TestProject Path testProject) {
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
		record [
			resource.load(emptyMap)
		]

		record [
			(resource.contents.get(0) as Root).singleValuedContainmentEReference.id = 'test'
		]

		assertThat(changeRecorder.change, hasEChanges(ReplaceSingleValuedEAttribute))
	}

	@ParameterizedTest(name="while isRecording={0}")
	@DisplayName("adds a resource to the recording")
	@ValueSource(booleans=#[false, true])
	def void recordsOnAddedResource(boolean isRecordingWhileAddingObject) {
		changeRecorder.addToRecording(resourceSet)
		if(isRecordingWhileAddingObject) changeRecorder.beginRecording()
		val resource = resourceSet.createResource(URI.createURI('test://test.aet'))
		if(isRecordingWhileAddingObject) changeRecorder.endRecording()

		record[
			resource.contents += aet.Root
		]

		assertThat(changeRecorder.change, hasEChanges(CreateEObject, InsertRootEObject, ReplaceSingleValuedEAttribute))
	}

	@Test
	@DisplayName("deletes a resource and records model deletion")
	def void recordsOnDeletedResource(@TestProject Path testDir) {
		changeRecorder.addToRecording(resourceSet)
		val resource = resourceSet.createResource(URI.createFileURI(testDir.resolve("test.aet").toString)) => [
			contents += aet.Root => [
				singleValuedContainmentEReference = aet.NonRoot
			]
		]
		record [
			resource.delete(emptyMap)
		]
		assertThat(changeRecorder.change, hasEChanges(RemoveRootEObject, DeleteEObject))
	}

	@ParameterizedTest(name="while isRecording={0}")
	@DisplayName("adds multiple added resources to the recording")
	@ValueSource(booleans=#[false, true])
	def void recordsOnMultipleAddedResource(boolean isRecordingWhileAddingObject) {
		val foreignResourceSet = new ResourceSetImpl().withGlobalFactories()
		val resource1 = foreignResourceSet.createResource(URI.createURI('test://test1.aet'))
		val resource2 = foreignResourceSet.createResource(URI.createURI('test://test2.aet'))
		changeRecorder.addToRecording(resourceSet)
		recordIf(isRecordingWhileAddingObject) [
			resourceSet.resources += List.of(resource1, resource2)
		]

		record [
			resource1.contents += aet.Root
			resource2.contents += aet.Root
		]

		assertThat(changeRecorder.change, hasEChanges(
			CreateEObject,
			InsertRootEObject,
			ReplaceSingleValuedEAttribute,
			CreateEObject,
			InsertRootEObject,
			ReplaceSingleValuedEAttribute
		))
	}

	@ParameterizedTest(name="while isRecording={0}")
	@DisplayName("does not record loading an existing resource in a resource set with demand load")
	@ValueSource(booleans=#[false, true])
	def void doesntRecordLoadingExistingResourceOnResourceSetWithDemandLoad(boolean isRecordingLoadingResource,
		@TestProject Path testDir) {
		val resourceUri = URI.createFileURI(testDir.resolve("test.aet").toString)
		val originalResource = new ResourceSetImpl().withGlobalFactories().createResource(resourceUri) => [
			contents += aet.Root => [
				nonRootObjectContainerHelper = aet.NonRootObjectContainerHelper => [
					nonRootObjectsContainment += aet.NonRoot
				]
			]
			save(emptyMap)
		]

		changeRecorder.addToRecording(resourceSet)
		recordIf(isRecordingLoadingResource) [
			resourceSet.getResource(resourceUri, true)
		]
		assertThat(changeRecorder.change, hasNoChanges)
		val loadedResource = resourceSet.getResource(resourceUri, false)
		assertThat(loadedResource, containsModelOf(originalResource))
	}

	@ParameterizedTest(name="while isRecording={0}")
	@DisplayName("does not record loading an existing resource in a resource set with explicit loading")
	@ValueSource(booleans=#[false, true])
	def void doesntRecordLoadingExistingResourceOnResourceSetWithExplicitLoading(boolean isRecordingLoadingResource,
		@TestProject Path testDir) {
		val resourceUri = URI.createFileURI(testDir.resolve("test.aet").toString)
		val originalResource = new ResourceSetImpl().withGlobalFactories().createResource(resourceUri) => [
			contents += aet.Root => [
				nonRootObjectContainerHelper = aet.NonRootObjectContainerHelper => [
					nonRootObjectsContainment += aet.NonRoot
				]
			]
			save(emptyMap)
		]

		changeRecorder.addToRecording(resourceSet)
		val loadedResource = resourceSet.createResource(resourceUri)
		recordIf(isRecordingLoadingResource) [
			loadedResource.load(emptyMap)
		]
		assertThat(changeRecorder.change, hasNoChanges)
		assertThat(loadedResource, containsModelOf(originalResource))
	}

	@ParameterizedTest(name="while isRecording={0}")
	@DisplayName("does not record loading a pathmap resource in a resource set")
	@ValueSource(booleans=#[false, true])
	def void doesntRecordPathmapResourceOnResourceSet(boolean isRecordingLoadingResource, @TestProject Path testDir) {
		val originalResource = new ResourceSetImpl().withGlobalFactories().createResource(
			URI.createFileURI(testDir.resolve("test.aet").toString)) => [
			contents += aet.Root => [
				nonRootObjectContainerHelper = aet.NonRootObjectContainerHelper => [
					nonRootObjectsContainment += aet.NonRoot
				]
			]
			save(emptyMap)
		]

		// Register pathmap entry (it is important to have target URI end with a "/")
		val pathmapResourcesUri = URI.createURI("pathmap://CHANGE_RECORDER_TEST_MODELS/")
		resourceSet.URIConverter.URIMap.put(pathmapResourcesUri, URI.createFileURI(testDir.toString + "/"))
		val pathmapResourceURI = pathmapResourcesUri.appendSegment("test.aet")
		changeRecorder.addToRecording(resourceSet)
		recordIf(isRecordingLoadingResource) [
			resourceSet.getResource(pathmapResourceURI, true)
		]
		assertThat(changeRecorder.change, hasNoChanges)
		val pathmapResource = resourceSet.getResource(pathmapResourceURI, false)
		assertThat("resource should be loaded via pathmap instead of resolving it", pathmapResource.URI,
			is(pathmapResourceURI))
		assertThat(pathmapResource, containsModelOf(originalResource))
	}

	@ParameterizedTest(name="while isRecording={0}")
	@DisplayName("does not record unloading a resource")
	@ValueSource(booleans=#[false, true])
	def void doesntRecordUnloading(boolean isRecordingUnloadingResource, @TestProject Path testDir) {
		changeRecorder.addToRecording(resourceSet)
		val resource = resourceSet.createResource(URI.createFileURI(testDir.resolve("test.aet").toString)) => [
			contents += aet.Root => [
				nonRootObjectContainerHelper = aet.NonRootObjectContainerHelper => [
					nonRootObjectsContainment += aet.NonRoot
				]
			]
			save(emptyMap)
		]
		recordIf(isRecordingUnloadingResource) [
			resource.unload()
		]
		assertThat(changeRecorder.change, hasNoChanges)
	}

	@ParameterizedTest(name="while isRecording={0}")
	@DisplayName("removes an object unset from a containment reference from the recording")
	@ValueSource(booleans=#[false, true])
	def void removeAfterContainmentUnset(boolean isRecordingWhileRemovingObject) {
		val nonRoot = aet.NonRoot
		val root = aet.Root.wrapIntoRecordedResource() => [
			record [
				singleValuedContainmentEReference = nonRoot
			]
		]
		recordIf(isRecordingWhileRemovingObject) [
			root.singleValuedContainmentEReference = null
		]

		record [
			nonRoot.id = 'foobar'
		]

		assertThat(changeRecorder.change, hasNoChanges)
	}

	@ParameterizedTest(name="while isRecording={0}")
	@DisplayName("removes an object removed from a containment reference from the recording")
	@ValueSource(booleans=#[false, true])
	def void removeAfterContainmentRemove(boolean isRecordingWhileRemovingObject) {
		val nonRoot = aet.NonRoot
		val root = aet.Root.wrapIntoRecordedResource() => [
			record [
				multiValuedContainmentEReference += nonRoot
			]
		]
		recordIf(isRecordingWhileRemovingObject) [
			root.multiValuedContainmentEReference.clear()
		]

		record [
			nonRoot.id = 'foobar'
		]

		assertThat(changeRecorder.change, hasNoChanges)
	}

	@ParameterizedTest(name="while isRecording={0}")
	@DisplayName("removes multiple objects removed from a containment reference from the recording")
	@ValueSource(booleans=#[false, true])
	def void removeAfterContainmentMultipleRemove(boolean isRecordingWhileRemovingObject) {
		val nonRoot1 = aet.NonRoot
		val nonRoot2 = aet.NonRoot
		val nonRoot3 = aet.NonRoot
		val root = aet.Root.wrapIntoRecordedResource() => [
			record [
				multiValuedContainmentEReference += List.of(nonRoot1, nonRoot2, nonRoot3)
			]
		]
		recordIf(isRecordingWhileRemovingObject) [
			root.multiValuedContainmentEReference -= List.of(nonRoot1, nonRoot3)
		]

		record [
			nonRoot1.id = 'foobar1'
			nonRoot2.id = 'foobar2'
			nonRoot3.id = 'foobar3'
		]

		assertThat(changeRecorder.change, hasEChanges(ReplaceSingleValuedEAttribute))
	}

	@ParameterizedTest(name="while isRecording={0}")
	@DisplayName("removes a removed resource from the recording")
	@ValueSource(booleans=#[false, true])
	def void removeAfterRemovedResource(boolean isRecordingWhileRemovingObject) {
		changeRecorder.addToRecording(resourceSet)
		val resource = resourceSet.createResource(URI.createURI('test://test1.aet'))

		recordIf(isRecordingWhileRemovingObject) [
			resourceSet.resources.clear()
		]

		record [
			resource.contents += aet.Root
		]

		assertThat(changeRecorder.change, hasNoChanges)
	}

	@ParameterizedTest(name="while isRecording={0}")
	@DisplayName("removes multiple removed resources from the recording")
	@ValueSource(booleans=#[false, true])
	def void removeAfterMultipleRemovedResource(boolean isRecordingWhileRemovingObject) {
		val resource1 = resourceSet.createResource(URI.createURI('test://test1.aet'))
		val resource2 = resourceSet.createResource(URI.createURI('test://test2.aet'))
		val resource3 = resourceSet.createResource(URI.createURI('test://test3.aet'))
		changeRecorder.addToRecording(resourceSet)
		recordIf(isRecordingWhileRemovingObject) [
			resourceSet.resources -= List.of(resource1, resource3)
		]

		record [
			resource1.contents += aet.Root
			resource2.contents += aet.Root
			resource3.contents += aet.Root
		]

		assertThat(changeRecorder.change, hasEChanges(CreateEObject, InsertRootEObject, ReplaceSingleValuedEAttribute))
	}

	@ParameterizedTest(name="while isRecording={0}")
	@DisplayName("removes unloaded objects from the recording")
	@ValueSource(booleans=#[false, true])
	def void removeAfterUnload(boolean isRecordingWhileRemovingObject) {
		val nonRoot = aet.NonRoot
		val resource = resourceSet.createResource(URI.createURI('test://test1.aet')) => [
			changeRecorder.addToRecording(it)
			record [
				contents += aet.Root => [
					singleValuedContainmentEReference = nonRoot
				]
			]
		]
		recordIf(isRecordingWhileRemovingObject) [
			resource.unload()
		]

		record [
			nonRoot.id = 'test'
		]

		assertThat(changeRecorder.change, hasNoChanges)
	}

	@ParameterizedTest(name="while isRecording={0}")
	@DisplayName("removes an object removed as root from the recording")
	@ValueSource(booleans=#[false, true])
	def void removeAfterRemovedRoot(boolean isRecordingWhileRemovingObject) {
		val root = aet.Root
		val resource = resourceSet.createResource(URI.createURI('test://test.aet')) => [
			changeRecorder.addToRecording(it)
			record [
				contents += root
			]
		]
		recordIf(isRecordingWhileRemovingObject) [
			resource.contents.clear()
		]

		record [
			root.id = 'foobar'
		]

		assertThat(changeRecorder.change, hasNoChanges)
	}

	@ParameterizedTest(name="while isRecording={0}")
	@DisplayName("removes multiple objects removed as roots from the recording")
	@ValueSource(booleans=#[false, true])
	def void removeAfterMultipleRemovedRoot(boolean isRecordingWhileAddingObject) {
		val root1 = aet.Root
		val root2 = aet.Root
		val root3 = aet.Root
		val resource = resourceSet.createResource(URI.createURI('test://test.aet')) => [
			changeRecorder.addToRecording(it)
			record [
				contents += List.of(root1, root2, root3)
			]
		]
		if(isRecordingWhileAddingObject) changeRecorder.beginRecording()
		resource.contents -= List.of(root1, root3)
		if(isRecordingWhileAddingObject) changeRecorder.endRecording()

		record [
			root1.id = 'foobar1'
			root2.id = 'foobar2'
			root3.id = 'foobar3'
		]

		assertThat(changeRecorder.change, hasEChanges(ReplaceSingleValuedEAttribute))
	}

	@Test
	@DisplayName("does not remove explicitly added child objects")
	def void dontRemoveExplicitlyAddedChild() {
		val nonRoot = aet.NonRoot
		val root = aet.Root.wrapIntoRecordedResource() => [
			record [
				singleValuedContainmentEReference = nonRoot
			]
		]
		changeRecorder.addToRecording(nonRoot)
		root.singleValuedContainmentEReference = null

		record [
			nonRoot.id = 'testid'
		]

		assertThat(changeRecorder.change, hasEChanges(ReplaceSingleValuedEAttribute))
	}

	@Test
	@DisplayName("does not remove explicitly added root objects")
	def void dontRemoveExplicitlyAddedRoot() {
		val nonRoot = aet.NonRoot
		val root = aet.Root => [
			singleValuedContainmentEReference = nonRoot
		]
		val resource = resourceSet.createResource(URI.createURI('test://test.aet')) => [
			changeRecorder.addToRecording(it)
			record [
				contents += root
			]
		]
		changeRecorder.addToRecording(root)
		resource.contents.clear()

		record[
			root.id = 'rootid'
			nonRoot.id = 'testid'
		]

		assertThat(changeRecorder.change, hasEChanges(ReplaceSingleValuedEAttribute, ReplaceSingleValuedEAttribute))
	}

	@Test
	@DisplayName("resets the recorded changes after ending the recording")
	def void resetsChangesAfterEndRecording() {
		val root = aet.Root.wrapIntoRecordedResource()
		record [
			root.id = 'test'
		]

		assertThat(changeRecorder.change, hasEChanges(ReplaceSingleValuedEAttribute))

		record []

		assertThat(changeRecorder.change, hasNoChanges)

		record [
			root.multiValuedNonContainmentEReference += aet.NonRoot
		]

		assertThat(changeRecorder.change, hasEChanges(CreateEObject, InsertEReference, ReplaceSingleValuedEAttribute))
	}

	@Test
	@DisplayName("refuses to record changes on a different resource set than the one of the ID resolver")
	def void differentResourceSet() {
		assertThrows(IllegalArgumentException) [
			changeRecorder.addToRecording(new ResourceSetImpl)
		]
	}

	@Test
	@DisplayName("refuses to record changes on a resource from a different resource set than the one of the ID resolver")
	def void resourceFromDifferentResourceSet() {
		val foreignResourceSet = new ResourceSetImpl().withGlobalFactories()
		assertThrows(IllegalArgumentException) [
			changeRecorder.addToRecording(foreignResourceSet.createResource(URI.createURI('test://test.aet')))
		]
	}

	@Test
	@DisplayName("refuses to record changes on an object from a different resource set than the one of the ID resolver")
	def void objectFromDifferentResourceSet() {
		val foreignResourceSet = new ResourceSetImpl().withGlobalFactories()
		val root = aet.Root
		val foreignResource = foreignResourceSet.createResource(URI.createURI('test://test.aet')) => [
			contents += root
		]
		assertThrows(IllegalArgumentException) [
			changeRecorder.addToRecording(foreignResource)
		]
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
		aet.Root.wrapIntoRecordedResource()
		changeRecorder.beginRecording()
		changeRecorder.close()

		assertThat(changeRecorder.isRecording, is(false))
		assertThrows(IllegalStateException)[changeRecorder.beginRecording()]
		assertThrows(IllegalStateException)[changeRecorder.endRecording()]
		assertThrows(IllegalStateException)[changeRecorder.change]
		assertThrows(IllegalStateException)[changeRecorder.addToRecording(aet.Root)]
		assertThrows(IllegalStateException)[changeRecorder.removeFromRecording(aet.Root)]
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
	private static class EChangeSequenceMatcher extends TypeSafeMatcher<TransactionalChange> {
		val List<Class<? extends EChange>> expectedTypes

		override describeTo(Description description) {
			if (expectedTypes.isEmpty) {
				description.appendText("no changes")
			} else {
				description.appendText("this sequence of EChanges: ") //
				.appendText(expectedTypes.join("[", ", ", "]")[simpleName])
			}
		}

		override protected matchesSafely(TransactionalChange item) {
			val actualTypes = item.EChanges.map[class].iterator
			for (val expectedTypesIt = expectedTypes.iterator; expectedTypesIt.hasNext;) {
				if (!actualTypes.hasNext || !expectedTypesIt.next.isAssignableFrom(actualTypes.next)) {
					return false
				}
			}
			return !actualTypes.hasNext
		}
	}
}
