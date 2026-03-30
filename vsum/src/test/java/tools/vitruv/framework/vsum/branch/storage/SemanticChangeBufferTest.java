package tools.vitruv.framework.vsum.branch.storage;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.eobject.EObjectExistenceEChange;
import tools.vitruv.change.atomic.feature.FeatureEChange;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.VitruviusChange;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link SemanticChangeBuffer}.
 *
 * <p>Tests verify the buffer's role as a {@link tools.vitruv.change.composite.propagation.ChangePropagationListener}:
 * that it collects only the {@code originalChange} from each {@link PropagatedChange}, groups
 * changes by resource URI, and is fully cleared by {@link SemanticChangeBuffer#drainChanges()}.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
class SemanticChangeBufferTest {

    private SemanticChangeBuffer buffer;

    private FeatureEChange<EObject, EStructuralFeature> featureChange;
    private FeatureEChange<EObject, EStructuralFeature> featureChangeB;
    private EObjectExistenceEChange existenceChange;
    private EObject elementA;
    private EObject elementB;
    private Resource resourceA;
    private Resource resourceB;
    private PropagatedChange propagatedChange;
    private PropagatedChange propagatedChangeB;
    private VitruviusChange<EObject> originalChangeA;
    private VitruviusChange<EObject> originalChangeB;

    @BeforeEach
    void setUp() {
        buffer = new SemanticChangeBuffer();
        featureChange = mock(FeatureEChange.class);
        featureChangeB = mock(FeatureEChange.class);
        existenceChange = mock(EObjectExistenceEChange.class);
        elementA = mock(EObject.class);
        elementB = mock(EObject.class);
        resourceA = mock(Resource.class);
        resourceB = mock(Resource.class);
        propagatedChange = mock(PropagatedChange.class);
        propagatedChangeB = mock(PropagatedChange.class);
        originalChangeA = mock(VitruviusChange.class);
        originalChangeB = mock(VitruviusChange.class);
    }

    /**
     * Verifies that a freshly created buffer reports no changes and zero size.
     */
    @Test
    @DisplayName("Freshly created buffer has no changes")
    void freshBufferHasNoChanges() {
        assertFalse(buffer.hasChanges(), "a new buffer must report hasChanges() as false");
        assertEquals(0, buffer.size(), "a new buffer must have size 0");
    }

    /**
     * Verifies that draining an empty buffer returns an empty map without throwing.
     */
    @Test
    @DisplayName("drainChanges on empty buffer returns empty map")
    void drainOnEmptyBufferReturnsEmptyMap() {
        assertTrue(buffer.drainChanges().isEmpty());
    }

    /**
     * Verifies that {@code startedChangePropagation} does not affect the buffer state.
     */
    @Test
    @DisplayName("startedChangePropagation does not modify buffer state")
    void startedChangePropagationIsNoOp() {
        buffer.startedChangePropagation(null);
        assertFalse(buffer.hasChanges());
        assertEquals(0, buffer.size());
    }

    /**
     * Verifies that a single feature change is collected under the resource URI of its affected
     * element, and that {@code hasChanges()} and {@code size()} reflect the collected change.
     */
    @Test
    @DisplayName("Feature change is filed under its resource URI")
    void featureChangeIsFiledUnderResourceUri() {
        setupFeatureChange(featureChange, elementA, resourceA, "file:///models/A.xmi");
        setupPropagatedChange(propagatedChange, originalChangeA, List.of(featureChange));

        buffer.finishedChangePropagation(List.of(propagatedChange));

        assertTrue(buffer.hasChanges());
        assertEquals(1, buffer.size());
        var result = buffer.drainChanges();
        assertTrue(result.containsKey("file:///models/A.xmi"));
        assertEquals(1, result.get("file:///models/A.xmi").size());
    }

    /**
     * Verifies that an existence change (CreateEObject / DeleteEObject) is correctly grouped by
     * the resource URI of its affected element.
     */
    @Test
    @DisplayName("Existence change is filed under its resource URI")
    void existenceChangeIsFiledUnderResourceUri() {
        when(existenceChange.getAffectedElement()).thenReturn(elementA);
        when(elementA.eResource()).thenReturn(resourceA);
        when(resourceA.getURI()).thenReturn(URI.createURI("file:///models/A.xmi"));
        when(propagatedChange.getOriginalChange()).thenReturn(originalChangeA);
        when(originalChangeA.getEChanges()).thenReturn(List.of(existenceChange));

        buffer.finishedChangePropagation(List.of(propagatedChange));

        var result = buffer.drainChanges();
        assertTrue(result.containsKey("file:///models/A.xmi"));
        assertEquals(1, result.get("file:///models/A.xmi").size());
    }

    /**
     * Verifies that multiple calls to {@code finishedChangePropagation} accumulate in the same
     * buffer, preserving order.
     */
    @Test
    @DisplayName("Multiple propagations to the same resource accumulate in order")
    void multiplePropagationsAccumulateInOrder() {
        setupFeatureChange(featureChange, elementA, resourceA, "file:///models/A.xmi");
        setupFeatureChange(featureChangeB, elementA, resourceA, "file:///models/A.xmi");
        setupPropagatedChange(propagatedChange, originalChangeA, List.of(featureChange));
        setupPropagatedChange(propagatedChangeB, originalChangeB, List.of(featureChangeB));

        buffer.finishedChangePropagation(List.of(propagatedChange));
        buffer.finishedChangePropagation(List.of(propagatedChangeB));

        assertEquals(2, buffer.size());
        assertEquals(2, buffer.drainChanges().get("file:///models/A.xmi").size());
    }

    /**
     * Verifies that changes to different resources are stored in separate lists.
     */
    @Test
    @DisplayName("Changes to different resources are stored in separate lists")
    void changesToDifferentResourcesAreSeparated() {
        setupFeatureChange(featureChange, elementA, resourceA, "file:///models/A.xmi");
        setupFeatureChange(featureChangeB, elementB, resourceB, "file:///models/B.xmi");
        setupPropagatedChange(propagatedChange, originalChangeA, List.of(featureChange, featureChangeB));

        buffer.finishedChangePropagation(List.of(propagatedChange));

        var result = buffer.drainChanges();
        assertEquals(2, result.size());
        assertEquals(1, result.get("file:///models/A.xmi").size());
        assertEquals(1, result.get("file:///models/B.xmi").size());
    }

    /**
     * Verifies that {@code drainChanges()} returns a snapshot and clears the buffer so that
     * a second call returns an empty map.
     */
    @Test
    @DisplayName("drainChanges returns snapshot and clears the buffer")
    void drainClearsBuffer() {
        setupFeatureChange(featureChange, elementA, resourceA, "file:///models/A.xmi");
        setupPropagatedChange(propagatedChange, originalChangeA, List.of(featureChange));
        buffer.finishedChangePropagation(List.of(propagatedChange));

        var firstDrain = buffer.drainChanges();
        assertFalse(firstDrain.isEmpty());
        assertFalse(buffer.hasChanges(), "buffer must be empty after drain");
        assertEquals(0, buffer.size());
        assertTrue(buffer.drainChanges().isEmpty(), "second drain must return empty map");
    }

    /**
     * Verifies that the map returned by {@code drainChanges()} is unmodifiable.
     */
    @Test
    @DisplayName("drainChanges returns an unmodifiable map")
    void drainReturnsUnmodifiableMap() {
        setupFeatureChange(featureChange, elementA, resourceA, "file:///models/A.xmi");
        setupPropagatedChange(propagatedChange, originalChangeA, List.of(featureChange));
        buffer.finishedChangePropagation(List.of(propagatedChange));

        var result = buffer.drainChanges();
        assertThrows(UnsupportedOperationException.class, () -> result.put("k", List.of()));
    }

    /**
     * Verifies the buffer can be reused across multiple commits without side effects.
     */
    @Test
    @DisplayName("Buffer can be reused after a drain for the next commit")
    void bufferCanBeReusedAfterDrain() {
        setupFeatureChange(featureChange, elementA, resourceA, "file:///models/A.xmi");
        setupPropagatedChange(propagatedChange, originalChangeA, List.of(featureChange));
        buffer.finishedChangePropagation(List.of(propagatedChange));
        buffer.drainChanges();

        // second commit
        setupFeatureChange(featureChangeB, elementB, resourceB, "file:///models/B.xmi");
        setupPropagatedChange(propagatedChangeB, originalChangeB, List.of(featureChangeB));
        buffer.finishedChangePropagation(List.of(propagatedChangeB));

        assertEquals(1, buffer.size());
        assertTrue(buffer.drainChanges().containsKey("file:///models/B.xmi"));
    }

    /**
     * Verifies that a change whose element has no resource is filed under "unknown-resource"
     * rather than being dropped.
     */
    @Test
    @DisplayName("Change with no resource is filed under 'unknown-resource'")
    void changeWithNoResourceIsFiledUnderUnknown() {
        when(featureChange.getAffectedElement()).thenReturn(elementA);
        when(elementA.eResource()).thenReturn(null);
        setupPropagatedChange(propagatedChange, originalChangeA, List.of(featureChange));

        buffer.finishedChangePropagation(List.of(propagatedChange));

        assertTrue(buffer.drainChanges().containsKey("unknown-resource"));
    }

    private void setupFeatureChange(FeatureEChange<EObject, EStructuralFeature> change,
                                    EObject element, Resource resource, String uriStr) {
        when(change.getAffectedElement()).thenReturn(element);
        when(element.eResource()).thenReturn(resource);
        when(resource.getURI()).thenReturn(URI.createURI(uriStr));
    }

    private void setupPropagatedChange(PropagatedChange propagated,
                                       VitruviusChange<EObject> original, List<EChange<EObject>> eChanges) {
        when(propagated.getOriginalChange()).thenReturn(original);
        when(original.getEChanges()).thenReturn(eChanges);
    }
}
