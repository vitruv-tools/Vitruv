package tools.vitruv.framework.tests.domains

import tools.vitruv.framework.tests.domains.StateChangePropagationTest
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.containsModelOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.util.Capture
import static extension tools.vitruv.framework.util.Capture.operator_doubleGreaterThan
import static extension tools.vitruv.framework.domains.repository.DomainAwareResourceSet.awareOfDomains
import tools.vitruv.testutils.domains.TestDomainsRepository
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import tools.vitruv.framework.change.id.IdResolverAndRepositoryFactory

class BasicStateChangePropagationTest extends StateChangePropagationTest {
	private def getTestUri() {
		getModelURI("Test.allElementTypes")
	}

	@Test
	@DisplayName("create new resource and calculate state-based difference")
	def void createNewResource() {
		val modelResource = new ResourceSetImpl().createResource(testUri) => [
			contents += aet.Root => [
				id = "Root"
			]
		]
		
		val changes = strategyToTest.getChangeSequenceForCreated(modelResource)
		assertEquals(3, changes.EChanges.size)
		assertEquals(1, changes.EChanges.filter(InsertRootEObject).size)
		assertEquals(1, changes.EChanges.filter(CreateEObject).size)
		assertEquals(1, changes.EChanges.filter(ReplaceSingleValuedEAttribute).size)

		// Create empty resource to apply generated changes to
		changes.unresolve()
		val validationResourceSet = new ResourceSetImpl()
		val validationResolver = IdResolverAndRepositoryFactory.createIdResolver(validationResourceSet)
		changes.resolveAndApply(validationResolver)
		
		modelResource.save(null)
		assertEquals(1, validationResourceSet.resources.size)
		assertThat(validationResourceSet.resources.get(0), containsModelOf(modelResource))
	}

	@Test
	@DisplayName("delete existing resource and calculate state-based difference")
	def void deleteResource() {
		val modelResource = new Capture<Resource>
		resourceSet.record [
			createResource(testUri) => [
				contents += aet.Root => [
					id = "Root"
				]
			] >> modelResource
		]
		(-modelResource).save(null)
		val changes = strategyToTest.getChangeSequenceForDeleted(-modelResource)
		assertEquals(2, changes.EChanges.size)
		assertEquals(1, changes.EChanges.filter(RemoveRootEObject).size)
		assertEquals(1, changes.EChanges.filter(DeleteEObject).size)

		// Load resource to apply generated changes to
		changes.unresolve()
		val validationResourceSet = new ResourceSetImpl()
		val validationResolver = IdResolverAndRepositoryFactory.createIdResolver(validationResourceSet)
		validationResourceSet.getResource(testUri, true)
		changes.resolveAndApply(validationResolver)

		assertEquals(1, validationResourceSet.resources.size)
		assertTrue(validationResourceSet.resources.get(0).contents.empty)
	}

	@Test
	@DisplayName("replace root element and calculate state-based difference")
	def void replaceRootElement() {
		val modelResource = new Capture<Resource>
		resourceSet.record [
			createResource(testUri) => [
				contents += aet.Root => [
					id = "Root"
				]
			] >> modelResource
		]
		(-modelResource).save(null)

		(-modelResource).record [
			contents.clear()
			contents += aet.Root => [
					id = "Root2"
				]
		]

		val validationResourceSet = new ResourceSetImpl().withGlobalFactories().awareOfDomains(
			TestDomainsRepository.INSTANCE)
		val validationResolver = IdResolverAndRepositoryFactory.createIdResolver(validationResourceSet)
		val oldState = validationResourceSet.getResource(testUri, true)
		val changes = strategyToTest.getChangeSequenceBetween(-modelResource, oldState)

		changes.unresolve()
		changes.resolveAndApply(validationResolver)

		assertEquals(1, validationResourceSet.resources.size)
		assertThat(validationResourceSet.resources.get(0), containsModelOf(-modelResource))
	}

	@Test
	@DisplayName("change a root element property and calculate state-based difference")
	def void changeRootElementFeature() {
		val modelResource = new Capture<Resource>
		val root = aet.Root
		resourceSet.record [
			createResource(testUri) => [
				contents += root => [
					id = "Root"
				]
			] >> modelResource
		]
		(-modelResource).save(null)

		resourceSet.record [
			root.singleValuedEAttribute = 2
		]

		val validationResourceSet = new ResourceSetImpl().withGlobalFactories().awareOfDomains(
			TestDomainsRepository.INSTANCE)
		val validationResolver = IdResolverAndRepositoryFactory.createIdResolver(validationResourceSet)
		val oldState = validationResourceSet.getResource(testUri, true)
		oldState.allContents.forEach[validationResolver.getAndUpdateId(it)]
		val changes = strategyToTest.getChangeSequenceBetween(-modelResource, oldState)
		assertEquals(1, changes.EChanges.size)
		assertEquals(1, changes.EChanges.filter(ReplaceSingleValuedEAttribute).size)

		changes.unresolve()
		changes.resolveAndApply(validationResolver)

		assertEquals(1, validationResourceSet.resources.size)
		assertThat(validationResourceSet.resources.get(0), containsModelOf(-modelResource))
	}

	@Test
	@DisplayName("change a non-root element property and calculate state-based difference")
	def void changeNonRootElementFeature() {
		val modelResource = new Capture<Resource>
		val root = aet.Root
		val containedRoot = aet.Root
		resourceSet.record [
			createResource(testUri) => [
				contents += root => [
					id = "Root"
					recursiveRoot = containedRoot => [
						id = "ContainedRoot"
						singleValuedEAttribute = 0
					]
				]
			] >> modelResource
		]
		(-modelResource).save(null)

		resourceSet.record [
			containedRoot.singleValuedEAttribute = 1
		]

		val validationResourceSet = new ResourceSetImpl().withGlobalFactories().awareOfDomains(
			TestDomainsRepository.INSTANCE)
		val validationResolver = IdResolverAndRepositoryFactory.createIdResolver(validationResourceSet)
		val oldState = validationResourceSet.getResource(testUri, true)
		oldState.allContents.forEach[validationResolver.getAndUpdateId(it)]
		val changes = strategyToTest.getChangeSequenceBetween(-modelResource, oldState)

		changes.unresolve()
		changes.resolveAndApply(validationResolver)

		assertEquals(1, validationResourceSet.resources.size)
		assertThat(validationResourceSet.resources.get(0), containsModelOf(-modelResource))
	}

	@Test
	@DisplayName("move a resource to new location and calculate state-based difference")
	def void moveResource() {
		val modelResource = new Capture<Resource>
		val root = aet.Root
		resourceSet.record [
			createResource(testUri) => [
				contents += root => [
					id = "Root"
				]
			] >> modelResource
		]
		(-modelResource).save(null)

		val validationResourceSet = new ResourceSetImpl().withGlobalFactories().awareOfDomains(
			TestDomainsRepository.INSTANCE)
		val validationResolver = IdResolverAndRepositoryFactory.createIdResolver(validationResourceSet)
		val oldState = validationResourceSet.getResource(testUri, true)

		val movedResourceUri = getModelURI("moved.allElementTypes")
		resourceSet.record [
			createResource(movedResourceUri) => [
				contents += root
			] >> modelResource
		]

		val changes = strategyToTest.getChangeSequenceBetween(-modelResource, oldState)
		assertEquals(2, changes.EChanges.size)
		assertEquals(1, changes.EChanges.filter(RemoveRootEObject).size)
		assertEquals(1, changes.EChanges.filter(InsertRootEObject).size)
		
		changes.unresolve()
		changes.resolveAndApply(validationResolver)

		(-modelResource).save(null)
		assertEquals(2, validationResourceSet.resources.size)
		assertThat(validationResourceSet.getResource(movedResourceUri, false), containsModelOf(-modelResource))
	}

	@Test
	@DisplayName("move a resource to new location changing root feature and calculate state-based difference")
	def void moveResourceAndChangeRootFeature() {
		val modelResource = new Capture<Resource>
		val root = aet.Root
		resourceSet.record [
			createResource(testUri) => [
				contents += root => [
					id = "Root"
				]
			] >> modelResource
		]
		(-modelResource).save(null)

		val validationResourceSet = new ResourceSetImpl().withGlobalFactories().awareOfDomains(
			TestDomainsRepository.INSTANCE)
		val validationResolver = IdResolverAndRepositoryFactory.createIdResolver(validationResourceSet)
		val oldState = validationResourceSet.getResource(testUri, true)

		val movedResourceUri = getModelURI("moved.allElementTypes")
		resourceSet.record [
			(-modelResource).contents -= root
			root.singleValuedEAttribute = 2
			createResource(movedResourceUri) => [
				contents += root
			] >> modelResource
		]
		
		val changes = strategyToTest.getChangeSequenceBetween(-modelResource, oldState)
		assertEquals(3, changes.EChanges.size)
		assertEquals(1, changes.EChanges.filter(RemoveRootEObject).size)
		assertEquals(1, changes.EChanges.filter(InsertRootEObject).size)
		assertEquals(1, changes.EChanges.filter(ReplaceSingleValuedEAttribute).size)

		changes.unresolve()
		changes.resolveAndApply(validationResolver)

		(-modelResource).save(null)
		assertEquals(2, validationResourceSet.resources.size)
		assertThat(validationResourceSet.getResource(movedResourceUri, false), containsModelOf(-modelResource))
	}

}
