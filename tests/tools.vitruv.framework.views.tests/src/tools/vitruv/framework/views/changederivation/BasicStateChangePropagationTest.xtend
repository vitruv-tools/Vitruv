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
import tools.vitruv.change.composite.description.VitruviusChangeResolver
import tools.vitruv.testutils.Capture

import static org.hamcrest.CoreMatchers.instanceOf
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.testutils.matchers.ModelMatchers.containsModelOf
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.testutils.Capture.operator_doubleGreaterThan

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
        VitruviusChangeResolver.forHierarchicalIds(validationResourceSet).resolveAndApply(changes)

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
        val changes = strategyToTest.getChangeSequenceForDeleted(-modelResource)
        assertEquals(2, changes.EChanges.size)
        assertEquals(1, changes.EChanges.filter(RemoveRootEObject).size)
        assertEquals(1, changes.EChanges.filter(DeleteEObject).size)

        // Load resource to apply generated changes to
        val validationResourceSet = new ResourceSetImpl()
        validationResourceSet.getResource(testUri, true)
        VitruviusChangeResolver.forHierarchicalIds(validationResourceSet).resolveAndApply(changes)

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

        (-modelResource).record [
            contents.clear()
            contents += aet.Root => [
                id = "Root2"
            ]
        ]

        val validationResourceSet = new ResourceSetImpl().withGlobalFactories()
        val oldState = validationResourceSet.getResource(testUri, true)
        val changes = strategyToTest.getChangeSequenceBetween(-modelResource, oldState)

        VitruviusChangeResolver.forHierarchicalIds(validationResourceSet).resolveAndApply(changes)

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

        resourceSet.record [
            root.singleValuedEAttribute = 2
        ]

        val validationResourceSet = new ResourceSetImpl().withGlobalFactories()
        val oldState = validationResourceSet.getResource(testUri, true)
        val changes = strategyToTest.getChangeSequenceBetween(-modelResource, oldState)
        assertEquals(1, changes.EChanges.size)
        assertEquals(1, changes.EChanges.filter(ReplaceSingleValuedEAttribute).size)

        VitruviusChangeResolver.forHierarchicalIds(validationResourceSet).resolveAndApply(changes)

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

        resourceSet.record [
            root.id = "Root2"
        ]

        val validationResourceSet = new ResourceSetImpl().withGlobalFactories()
        val oldState = validationResourceSet.getResource(testUri, true)
        val unresolvedChanges = strategyToTest.getChangeSequenceBetween(-modelResource, oldState)
        val changes = VitruviusChangeResolver.forHierarchicalIds(validationResourceSet).resolveAndApply(unresolvedChanges)
        switch (strategyToTest.useIdentifiers) {
            case ONLY,
            case WHEN_AVAILABLE: {
                val Iterable<DeleteEObject<?>> deleteChanges = newArrayList(changes.EChanges.filter(DeleteEObject))
                assertEquals(1, deleteChanges.size)
                assertThat(deleteChanges.head.affectedElement, instanceOf(Root))
                assertEquals("Root", (deleteChanges.head.affectedElement as Root).id)

                val Iterable<CreateEObject<?>> createChanges = newArrayList(changes.EChanges.filter(CreateEObject))
                assertEquals(1, createChanges.size)
                assertThat(createChanges.head.affectedElement, instanceOf(Root))
                assertEquals("Root2", (createChanges.head.affectedElement as Root).id)
            }
            case NEVER: {
                assertEquals(1, changes.EChanges.size)
                assertEquals(1, changes.EChanges.filter(ReplaceSingleValuedEAttribute).size)
            }
        }

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

        resourceSet.record [
            containedRoot.singleValuedEAttribute = 1
        ]

        val validationResourceSet = new ResourceSetImpl().withGlobalFactories()
        val oldState = validationResourceSet.getResource(testUri, true)
        val changes = strategyToTest.getChangeSequenceBetween(-modelResource, oldState)
        assertEquals(1, changes.EChanges.size)
        assertEquals(1, changes.EChanges.filter(ReplaceSingleValuedEAttribute).size)

        VitruviusChangeResolver.forHierarchicalIds(validationResourceSet).resolveAndApply(changes)

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

        resourceSet.record [
            containedRoot.id = "ContainedRoot2"
        ]

        val validationResourceSet = new ResourceSetImpl().withGlobalFactories()
        val oldState = validationResourceSet.getResource(testUri, true)
        val unresolvedChanges = strategyToTest.getChangeSequenceBetween(-modelResource, oldState)
        val changes = VitruviusChangeResolver.forHierarchicalIds(validationResourceSet).resolveAndApply(unresolvedChanges)
        switch (strategyToTest.useIdentifiers) {
            case ONLY,
            case WHEN_AVAILABLE: {
                val Iterable<DeleteEObject<?>> deleteChanges = newArrayList(changes.EChanges.filter(DeleteEObject))
                assertEquals(1, deleteChanges.size)
                assertThat(deleteChanges.head.affectedElement, instanceOf(Root))
                assertEquals("ContainedRoot", (deleteChanges.head.affectedElement as Root).id)

                val Iterable<CreateEObject<?>> createChanges = newArrayList(changes.EChanges.filter(CreateEObject))
                assertEquals(1, createChanges.size)
                assertThat(createChanges.head.affectedElement, instanceOf(Root))
                assertEquals("ContainedRoot2", (createChanges.head.affectedElement as Root).id)
            }
            case NEVER: {
                assertEquals(1, changes.EChanges.size)
                assertEquals(1, changes.EChanges.filter(ReplaceSingleValuedEAttribute).size)
            }
        }

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

        VitruviusChangeResolver.forHierarchicalIds(validationResourceSet).resolveAndApply(changes)

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

        VitruviusChangeResolver.forHierarchicalIds(validationResourceSet).resolveAndApply(changes)

        (-modelResource).save(null)
        assertEquals(2, validationResourceSet.resources.size)
        assertThat(validationResourceSet.getResource(movedResourceUri, false), containsModelOf(-modelResource))
    }
}
