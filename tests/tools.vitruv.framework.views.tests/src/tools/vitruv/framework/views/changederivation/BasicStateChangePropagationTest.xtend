package tools.vitruv.framework.views.changederivation

import allElementTypes.Root
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import tools.vitruv.change.atomic.eobject.CreateEObject
import tools.vitruv.change.atomic.eobject.DeleteEObject
import tools.vitruv.change.atomic.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.change.atomic.root.InsertRootEObject
import tools.vitruv.change.atomic.root.RemoveRootEObject
import tools.vitruv.testutils.Capture

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.instanceOf
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.testutils.matchers.ModelMatchers.containsModelOf
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.testutils.Capture.operator_doubleGreaterThan
import tools.vitruv.change.atomic.uuid.UuidResolver

class BasicStateChangePropagationTest extends StateChangePropagationTest {
    private def getTestUri() {
        getModelURI("Test.allElementTypes")
    }

    @ParameterizedTest()
    @DisplayName("create new resource and calculate state-based difference")
    @MethodSource("strategiesToTest")
    def void createNewResource(StateBasedChangeResolutionStrategy strategyToTest) {
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
        val validationResourceSet = new ResourceSetImpl()
        val validationUuidResolver = UuidResolver.create(validationResourceSet)
        changes.unresolve().resolveAndApply(validationUuidResolver)

        modelResource.save(null)
        assertEquals(1, validationResourceSet.resources.size)
        assertThat(validationResourceSet.resources.get(0), containsModelOf(modelResource))
    }

    @ParameterizedTest()
    @DisplayName("delete existing resource and calculate state-based difference")
    @MethodSource("strategiesToTest")
    def void deleteResource(StateBasedChangeResolutionStrategy strategyToTest) {
        val modelResource = new Capture<Resource>
        resourceSet.record [
            createResource(testUri) => [
                contents += aet.Root => [
                    id = "Root"
                ]
            ] >> modelResource
        ]
        (-modelResource).save(null)

        val validationResourceSet = new ResourceSetImpl()
        val oldResource = validationResourceSet.getResource(testUri, true)
        val validationUuidResolver = UuidResolver.create(validationResourceSet)
        uuidResolver.resolveResource(-modelResource, oldResource, validationUuidResolver)

        val changes = strategyToTest.getChangeSequenceForDeleted(-modelResource, uuidResolver)
        assertEquals(2, changes.EChanges.size)
        assertEquals(1, changes.EChanges.filter(RemoveRootEObject).size)
        assertEquals(1, changes.EChanges.filter(DeleteEObject).size)

        changes.unresolve().resolveAndApply(validationUuidResolver)

        assertEquals(1, validationResourceSet.resources.size)
        assertTrue(validationResourceSet.resources.get(0).contents.empty)
    }

    @ParameterizedTest()
    @DisplayName("replace root element and calculate state-based difference")
    @MethodSource("strategiesToTest")
    def void replaceRootElement(StateBasedChangeResolutionStrategy strategyToTest) {
        val modelResource = new Capture<Resource>
        resourceSet.record [
            createResource(testUri) => [
                contents += aet.Root => [
                    id = "Root"
                ]
            ] >> modelResource
        ]
        (-modelResource).save(null)

        val validationResourceSet = new ResourceSetImpl().withGlobalFactories()
        val oldState = validationResourceSet.getResource(testUri, true)
        val validationUuidResolver = UuidResolver.create(validationResourceSet)
        uuidResolver.resolveResource(-modelResource, oldState, validationUuidResolver)

        (-modelResource).record [
            contents.clear()
            contents += aet.Root => [
                id = "Root2"
            ]
        ]

        val changes = strategyToTest.getChangeSequenceBetween(-modelResource, oldState, validationUuidResolver)
        changes.unresolve().resolveAndApply(validationUuidResolver)

        assertEquals(1, validationResourceSet.resources.size)
        assertThat(validationResourceSet.resources.get(0), containsModelOf(-modelResource))
    }

    @ParameterizedTest()
    @DisplayName("change a root element property and calculate state-based difference")
    @MethodSource("strategiesToTest")
    def void changeRootElementFeature(StateBasedChangeResolutionStrategy strategyToTest) {
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

        val validationResourceSet = new ResourceSetImpl().withGlobalFactories()
        val oldState = validationResourceSet.getResource(testUri, true)
        val validationUuidResolver = UuidResolver.create(validationResourceSet)
        uuidResolver.resolveResource(-modelResource, oldState, validationUuidResolver)

        resourceSet.record [
            root.singleValuedEAttribute = 2
        ]

        val changes = strategyToTest.getChangeSequenceBetween(-modelResource, oldState, validationUuidResolver)
        assertEquals(1, changes.EChanges.size)
        assertEquals(1, changes.EChanges.filter(ReplaceSingleValuedEAttribute).size)

        changes.unresolve().resolveAndApply(validationUuidResolver)

        assertEquals(1, validationResourceSet.resources.size)
        assertThat(validationResourceSet.resources.get(0), containsModelOf(-modelResource))
    }

    @ParameterizedTest()
    @DisplayName("change a root element's id and calculate state-based difference")
    @MethodSource("strategiesToTest")
    def void changeRootElementId(DefaultStateBasedChangeResolutionStrategy strategyToTest) {
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

        val validationResourceSet = new ResourceSetImpl().withGlobalFactories()
        val oldState = validationResourceSet.getResource(testUri, true)
        val validationUuidResolver = UuidResolver.create(validationResourceSet)
        uuidResolver.resolveResource(-modelResource, oldState, validationUuidResolver)

        resourceSet.record [
            root.id = "Root2"
        ]

        val changes = strategyToTest.getChangeSequenceBetween(-modelResource, oldState, validationUuidResolver)
        switch (strategyToTest.useIdentifiers) {
            case ONLY,
            case WHEN_AVAILABLE: {
                val Iterable<DeleteEObject<?>> deleteChanges = newArrayList(changes.EChanges.filter(DeleteEObject))
                assertEquals(1, deleteChanges.size)
                assertThat(deleteChanges.head.affectedEObject, instanceOf(Root))
                assertEquals("Root", (deleteChanges.head.affectedEObject as Root).id)

                val Iterable<CreateEObject<?>> createChanges = newArrayList(changes.EChanges.filter(CreateEObject))
                assertEquals(1, createChanges.size)
                assertThat(createChanges.head.affectedEObject, instanceOf(Root))
                assertEquals("Root2", (createChanges.head.affectedEObject as Root).id)
            }
            case NEVER: {
                assertEquals(1, changes.EChanges.size)
                assertEquals(1, changes.EChanges.filter(ReplaceSingleValuedEAttribute).size)
            }
        }

        changes.unresolve().resolveAndApply(validationUuidResolver)

        assertEquals(1, validationResourceSet.resources.size)
        assertThat(validationResourceSet.resources.get(0), containsModelOf(-modelResource))
    }

    @ParameterizedTest()
    @DisplayName("change a non-root element property and calculate state-based difference")
    @MethodSource("strategiesToTest")
    def void changeNonRootElementFeature(StateBasedChangeResolutionStrategy strategyToTest) {
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

        val validationResourceSet = new ResourceSetImpl().withGlobalFactories()
        val oldState = validationResourceSet.getResource(testUri, true)
        val validationUuidResolver = UuidResolver.create(validationResourceSet)
        uuidResolver.resolveResource(-modelResource, oldState, validationUuidResolver)

        resourceSet.record [
            containedRoot.singleValuedEAttribute = 1
        ]

        val changes = strategyToTest.getChangeSequenceBetween(-modelResource, oldState, validationUuidResolver)
        assertEquals(1, changes.EChanges.size)
        assertEquals(1, changes.EChanges.filter(ReplaceSingleValuedEAttribute).size)

        changes.unresolve().resolveAndApply(validationUuidResolver)

        assertEquals(1, validationResourceSet.resources.size)
        assertThat(validationResourceSet.resources.get(0), containsModelOf(-modelResource))
    }

    @ParameterizedTest()
    @DisplayName("change a non-root element's id and calculate state-based difference")
    @MethodSource("strategiesToTest")
    def void changeNonRootElementId(DefaultStateBasedChangeResolutionStrategy strategyToTest) {
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

        val validationResourceSet = new ResourceSetImpl().withGlobalFactories()
        val oldState = validationResourceSet.getResource(testUri, true)
        val validationUuidResolver = UuidResolver.create(validationResourceSet)
        uuidResolver.resolveResource(-modelResource, oldState, validationUuidResolver)

        resourceSet.record [
            containedRoot.id = "ContainedRoot2"
        ]

        val changes = strategyToTest.getChangeSequenceBetween(-modelResource, oldState, validationUuidResolver)
        switch (strategyToTest.useIdentifiers) {
            case ONLY,
            case WHEN_AVAILABLE: {
                val Iterable<DeleteEObject<?>> deleteChanges = newArrayList(changes.EChanges.filter(DeleteEObject))
                assertEquals(1, deleteChanges.size)
                assertThat(deleteChanges.head.affectedEObject, instanceOf(Root))
                assertEquals("ContainedRoot", (deleteChanges.head.affectedEObject as Root).id)

                val Iterable<CreateEObject<?>> createChanges = newArrayList(changes.EChanges.filter(CreateEObject))
                assertEquals(1, createChanges.size)
                assertThat(createChanges.head.affectedEObject, instanceOf(Root))
                assertEquals("ContainedRoot2", (createChanges.head.affectedEObject as Root).id)
            }
            case NEVER: {
                assertEquals(1, changes.EChanges.size)
                assertEquals(1, changes.EChanges.filter(ReplaceSingleValuedEAttribute).size)
            }
        }

        changes.unresolve().resolveAndApply(validationUuidResolver)

        assertEquals(1, validationResourceSet.resources.size)
        assertThat(validationResourceSet.resources.get(0), containsModelOf(-modelResource))
    }

    @ParameterizedTest()
    @DisplayName("move a resource to new location and calculate state-based difference")
    @MethodSource("strategiesToTest")
    def void moveResource(StateBasedChangeResolutionStrategy strategyToTest) {
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

        val validationResourceSet = new ResourceSetImpl().withGlobalFactories()
        val oldState = validationResourceSet.getResource(testUri, true)
        val validationUuidResolver = UuidResolver.create(validationResourceSet)
        uuidResolver.resolveResource(-modelResource, oldState, validationUuidResolver)

        val movedResourceUri = getModelURI("moved.allElementTypes")
        resourceSet.record [
            createResource(movedResourceUri) => [
                contents += root
            ] >> modelResource
        ]

        val changes = strategyToTest.getChangeSequenceBetween(-modelResource, oldState, validationUuidResolver)
        assertEquals(2, changes.EChanges.size)
        assertEquals(1, changes.EChanges.filter(RemoveRootEObject).size)
        assertEquals(1, changes.EChanges.filter(InsertRootEObject).size)

        changes.unresolve().resolveAndApply(validationUuidResolver)

        (-modelResource).save(null)
        assertEquals(2, validationResourceSet.resources.size)
        assertThat(validationResourceSet.getResource(movedResourceUri, false), containsModelOf(-modelResource))
    }

    @ParameterizedTest()
    @DisplayName("move a resource to new location changing root feature and calculate state-based difference")
    @MethodSource("strategiesToTest")
    def void moveResourceAndChangeRootFeature(StateBasedChangeResolutionStrategy strategyToTest) {
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

        val validationResourceSet = new ResourceSetImpl().withGlobalFactories()
        val oldState = validationResourceSet.getResource(testUri, true)
        val validationUuidResolver = UuidResolver.create(validationResourceSet)
        uuidResolver.resolveResource(-modelResource, oldState, validationUuidResolver)

        val movedResourceUri = getModelURI("moved.allElementTypes")
        resourceSet.record [
            (-modelResource).contents -= root
            root.singleValuedEAttribute = 2
            createResource(movedResourceUri) => [
                contents += root
            ] >> modelResource
        ]

        val changes = strategyToTest.getChangeSequenceBetween(-modelResource, oldState, validationUuidResolver)
        assertEquals(3, changes.EChanges.size)
        assertEquals(1, changes.EChanges.filter(RemoveRootEObject).size)
        assertEquals(1, changes.EChanges.filter(InsertRootEObject).size)
        assertEquals(1, changes.EChanges.filter(ReplaceSingleValuedEAttribute).size)

        changes.unresolve().resolveAndApply(validationUuidResolver)

        (-modelResource).save(null)
        assertEquals(2, validationResourceSet.resources.size)
        assertThat(validationResourceSet.getResource(movedResourceUri, false), containsModelOf(-modelResource))
    }
}
