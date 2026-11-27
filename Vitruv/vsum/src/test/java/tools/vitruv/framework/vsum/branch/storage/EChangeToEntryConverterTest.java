package tools.vitruv.framework.vsum.branch.storage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tools.vitruv.change.atomic.eobject.CreateEObject;
import tools.vitruv.change.atomic.eobject.DeleteEObject;
import tools.vitruv.change.atomic.feature.attribute.InsertEAttributeValue;
import tools.vitruv.change.atomic.root.InsertRootEObject;
import tools.vitruv.change.atomic.root.RemoveRootEObject;
import tools.vitruv.change.atomic.feature.attribute.RemoveEAttributeValue;
import tools.vitruv.change.atomic.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.change.atomic.feature.reference.InsertEReference;
import tools.vitruv.change.atomic.feature.reference.RemoveEReference;
import tools.vitruv.change.atomic.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;

import java.lang.reflect.Constructor;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link EChangeToEntryConverter}.
 *
 * <p>Tests verify that each EMF EChange subtype is correctly mapped to the appropriate
 * {@link SemanticChangeType}, that the three-way null check for {@link ReplaceSingleValuedEAttribute}
 * and {@link ReplaceSingleValuedEReference} produces the correct SET/CHANGED/CLEARED distinction,
 * and that UUID resolution failures degrade gracefully to {@code "unknown"}.
 */
@SuppressWarnings({"unchecked"})
class EChangeToEntryConverterTest {

    private UuidResolver uuidResolver;
    private EObject element;
    private EObject referencedElement;
    private EClass eClass;
    private EPackage ePackage;
    private EAttribute eAttribute;
    private EReference eReference;
    private EChangeToEntryConverter converter;

    /** Reflects into Uuid's package-private constructor so tests can create Uuid instances. */
    static Uuid uuid(String raw) {
        try {
            Constructor<Uuid> ctor = Uuid.class.getDeclaredConstructor(String.class);
            ctor.setAccessible(true);
            return ctor.newInstance(raw);
        } catch (Exception e) {
            throw new RuntimeException("Cannot create Uuid via reflection", e);
        }
    }

    /** Creates a properly parameterized mock for a raw EMF EChange interface. */
    private <T> T typedMock(Class<?> clazz) {
        return (T) mock(clazz);
    }

    @BeforeEach
    void setUp() {
        uuidResolver      = mock(UuidResolver.class);
        element           = mock(EObject.class);
        referencedElement = mock(EObject.class);
        eClass            = mock(EClass.class);
        ePackage          = mock(EPackage.class);
        eAttribute        = mock(EAttribute.class);
        eReference        = mock(EReference.class);
        converter         = new EChangeToEntryConverter(uuidResolver);

        // Default element EClass setup for most tests
        when(element.eClass()).thenReturn(eClass);
        when(eClass.getEPackage()).thenReturn(ePackage);
        when(ePackage.getNsPrefix()).thenReturn("entities");
        when(eClass.getName()).thenReturn("Entity");
    }

    /** Configures the UUID resolver to return a known UUID for the given element. */
    void withUuid(EObject obj, String rawValue) {
        when(uuidResolver.hasUuid(obj)).thenReturn(true);
        when(uuidResolver.getUuid(obj)).thenReturn(uuid(rawValue));
    }

    @Test
    @DisplayName("convert throws on null input list")
    void convertThrowsOnNullInput() {
        assertThrows(NullPointerException.class, () -> converter.convert(null));
    }

    @Test
    @DisplayName("convert returns empty list for empty input")
    void convertEmptyInputReturnsEmptyList() {
        assertTrue(converter.convert(List.of()).isEmpty());
    }

    @Nested
    @DisplayName("CreateEObject")
    class CreateEObjectTests {
        /**
         * Verifies CreateEObject maps to ELEMENT_CREATED with UUID and EClass, no feature/from/to.
         */
        @Test
        @DisplayName("Maps to ELEMENT_CREATED with UUID and eClass, no feature/from/to")
        void mapsToElementCreated() {
            CreateEObject<EObject> createChange = typedMock(CreateEObject.class);
            when(createChange.getAffectedElement()).thenReturn(element);
            withUuid(element, "uuid-create-001");

            var entry = converter.convert(List.of(createChange)).get(0);

            assertEquals(0, entry.getIndex());
            assertEquals(SemanticChangeType.ELEMENT_CREATED, entry.getChangeType());
            assertEquals("CreateEObject", entry.getEmfType());
            assertTrue(entry.getElementUuid().contains("uuid-create-001"));
            assertEquals("entities::Entity", entry.getEClass());
            assertNull(entry.getFeature());
            assertNull(entry.getFrom());
            assertNull(entry.getTo());
        }
    }

    @Nested
    @DisplayName("DeleteEObject")
    class DeleteEObjectTests {

        /**
         * Verifies DeleteEObject maps to ELEMENT_DELETED and falls back to "unknown" UUID when
         * the element is no longer registered (common after deletion).
         */
        @Test
        @DisplayName("Maps to ELEMENT_DELETED; degrades to 'unknown' UUID if unregistered")
        void mapsToElementDeleted() {
            DeleteEObject<EObject> deleteChange = typedMock(DeleteEObject.class);
            when(deleteChange.getAffectedElement()).thenReturn(element);
            when(uuidResolver.hasUuid(element)).thenReturn(false);

            var entry = converter.convert(List.of(deleteChange)).get(0);

            assertEquals(SemanticChangeType.ELEMENT_DELETED, entry.getChangeType());
            assertEquals("unknown", entry.getElementUuid());
        }
    }

    @Nested
    @DisplayName("ReplaceSingleValuedEAttribute")
    class ReplaceAttributeTests {

        private ReplaceSingleValuedEAttribute<EObject, Object> replaceAttr;

        @BeforeEach
        void setUpAttr() {
            replaceAttr = typedMock(ReplaceSingleValuedEAttribute.class);
            when(replaceAttr.getAffectedElement()).thenReturn(element);
            when(replaceAttr.getAffectedFeature()).thenReturn(eAttribute);
            when(eAttribute.getName()).thenReturn("status");
            withUuid(element, "uuid-attr-001");
        }

        /** null -> value = ATTRIBUTE_SET (first assignment). */
        @Test
        @DisplayName("null->value maps to ATTRIBUTE_SET")
        void nullToValueMapsToAttributeSet() {
            when(replaceAttr.getOldValue()).thenReturn(null);
            when(replaceAttr.getNewValue()).thenReturn("ACTIVE");

            var entry = converter.convert(List.of(replaceAttr)).get(0);

            assertEquals(SemanticChangeType.ATTRIBUTE_SET, entry.getChangeType());
            assertNull(entry.getFrom());
            assertEquals("ACTIVE", entry.getTo());
            assertEquals("status", entry.getFeature());
        }

        /** value -> value = ATTRIBUTE_CHANGED (replacement). */
        @Test
        @DisplayName("value->value maps to ATTRIBUTE_CHANGED")
        void valueToValueMapsToAttributeChanged() {
            when(replaceAttr.getOldValue()).thenReturn("DRAFT");
            when(replaceAttr.getNewValue()).thenReturn("PUBLISHED");

            var entry = converter.convert(List.of(replaceAttr)).get(0);

            assertEquals(SemanticChangeType.ATTRIBUTE_CHANGED, entry.getChangeType());
            assertEquals("DRAFT", entry.getFrom());
            assertEquals("PUBLISHED", entry.getTo());
        }

        /** value -> null = ATTRIBUTE_CLEARED (reset to default). */
        @Test
        @DisplayName("value->null maps to ATTRIBUTE_CLEARED")
        void valueToNullMapsToAttributeCleared() {
            when(replaceAttr.getOldValue()).thenReturn("OBSOLETE");
            when(replaceAttr.getNewValue()).thenReturn(null);

            var entry = converter.convert(List.of(replaceAttr)).get(0);

            assertEquals(SemanticChangeType.ATTRIBUTE_CLEARED, entry.getChangeType());
            assertEquals("OBSOLETE", entry.getFrom());
            assertNull(entry.getTo());
        }
    }

    @Nested
    @DisplayName("InsertEAttributeValue / RemoveEAttributeValue")
    class MultiValuedAttributeTests {

        @Test
        @DisplayName("InsertEAttributeValue maps to ATTRIBUTE_VALUE_INSERTED with correct position")
        void insertAttributeValueMapsToInserted() {
            InsertEAttributeValue<EObject, Object> insertAttr = typedMock(InsertEAttributeValue.class);
            when(insertAttr.getAffectedElement()).thenReturn(element);
            when(insertAttr.getNewValue()).thenReturn("tagA");
            when(insertAttr.getAffectedFeature()).thenReturn(eAttribute);
            when(eAttribute.getName()).thenReturn("tags");
            when(insertAttr.getIndex()).thenReturn(1);
            withUuid(element, "uuid-insert-001");

            var entry = converter.convert(List.of(insertAttr)).get(0);

            assertEquals(SemanticChangeType.ATTRIBUTE_VALUE_INSERTED, entry.getChangeType());
            assertEquals("tagA", entry.getTo());
            assertEquals(1, entry.getPosition());
        }

        @Test
        @DisplayName("RemoveEAttributeValue maps to ATTRIBUTE_VALUE_REMOVED with correct position")
        void removeAttributeValueMapsToRemoved() {
            RemoveEAttributeValue<EObject, Object> removeAttr = typedMock(RemoveEAttributeValue.class);
            when(removeAttr.getAffectedElement()).thenReturn(element);
            when(removeAttr.getOldValue()).thenReturn("tagB");
            when(removeAttr.getAffectedFeature()).thenReturn(eAttribute);
            when(eAttribute.getName()).thenReturn("tags");
            when(removeAttr.getIndex()).thenReturn(0);
            withUuid(element, "uuid-remove-001");

            var entry = converter.convert(List.of(removeAttr)).get(0);

            assertEquals(SemanticChangeType.ATTRIBUTE_VALUE_REMOVED, entry.getChangeType());
            assertEquals("tagB", entry.getFrom());
            assertEquals(0, entry.getPosition());
        }
    }

    @Nested
    @DisplayName("ReplaceSingleValuedEReference")
    class ReplaceReferenceTests {

        private ReplaceSingleValuedEReference<EObject> replaceRef;

        @BeforeEach
        void setUpRef() {
            replaceRef = typedMock(ReplaceSingleValuedEReference.class);
            when(replaceRef.getAffectedElement()).thenReturn(element);
            when(replaceRef.getAffectedFeature()).thenReturn(eReference);
            when(eReference.getName()).thenReturn("parent");
            withUuid(element, "uuid-ref-owner");
        }

        @Test
        @DisplayName("null->element maps to REFERENCE_SET")
        void nullToElementMapsToReferenceSet() {
            withUuid(referencedElement, "uuid-ref-target");
            when(replaceRef.getOldValue()).thenReturn(null);
            when(replaceRef.getNewValue()).thenReturn(referencedElement);

            var entry = converter.convert(List.of(replaceRef)).get(0);

            assertEquals(SemanticChangeType.REFERENCE_SET, entry.getChangeType());
            assertNull(entry.getFrom());
            assertTrue(entry.getTo().contains("uuid-ref-target"));
        }

        @Test
        @DisplayName("element->element maps to REFERENCE_CHANGED")
        void elementToElementMapsToReferenceChanged() {
            EObject oldTarget = mock(EObject.class);
            withUuid(oldTarget, "uuid-old-target");
            withUuid(referencedElement, "uuid-new-target");
            when(replaceRef.getOldValue()).thenReturn(oldTarget);
            when(replaceRef.getNewValue()).thenReturn(referencedElement);

            var entry = converter.convert(List.of(replaceRef)).get(0);

            assertEquals(SemanticChangeType.REFERENCE_CHANGED, entry.getChangeType());
            assertTrue(entry.getFrom().contains("uuid-old-target"));
            assertTrue(entry.getTo().contains("uuid-new-target"));
        }

        @Test
        @DisplayName("element->null maps to REFERENCE_CLEARED")
        void elementToNullMapsToReferenceCleared() {
            EObject oldTarget = mock(EObject.class);
            withUuid(oldTarget, "uuid-old-target");
            when(replaceRef.getOldValue()).thenReturn(oldTarget);
            when(replaceRef.getNewValue()).thenReturn(null);

            var entry = converter.convert(List.of(replaceRef)).get(0);

            assertEquals(SemanticChangeType.REFERENCE_CLEARED, entry.getChangeType());
            assertTrue(entry.getFrom().contains("uuid-old-target"));
            assertNull(entry.getTo());
        }
    }

    @Nested
    @DisplayName("InsertEReference / RemoveEReference")
    class MultiValuedReferenceTests {

        @Test
        @DisplayName("InsertEReference maps to REFERENCE_VALUE_INSERTED with referencedElementUuid")
        void insertReferenceValueMapsToInserted() {
            InsertEReference<EObject> insertRef = typedMock(InsertEReference.class);
            when(insertRef.getAffectedElement()).thenReturn(element);
            when(insertRef.getNewValue()).thenReturn(referencedElement);
            when(insertRef.getAffectedFeature()).thenReturn(eReference);
            when(eReference.getName()).thenReturn("children");
            when(insertRef.getIndex()).thenReturn(0);
            withUuid(element, "uuid-parent");
            withUuid(referencedElement, "uuid-child");

            var entry = converter.convert(List.of(insertRef)).get(0);

            assertEquals(SemanticChangeType.REFERENCE_VALUE_INSERTED, entry.getChangeType());
            assertTrue(entry.getReferencedElementUuid().contains("uuid-child"));
            assertTrue(entry.getTo().contains("uuid-child"));
            assertEquals(0, entry.getPosition());
        }

        @Test
        @DisplayName("RemoveEReference maps to REFERENCE_VALUE_REMOVED with referencedElementUuid")
        void removeReferenceValueMapsToRemoved() {
            RemoveEReference<EObject> removeRef = typedMock(RemoveEReference.class);
            when(removeRef.getAffectedElement()).thenReturn(element);
            when(removeRef.getOldValue()).thenReturn(referencedElement);
            when(removeRef.getAffectedFeature()).thenReturn(eReference);
            when(eReference.getName()).thenReturn("children");
            when(removeRef.getIndex()).thenReturn(2);
            withUuid(element, "uuid-parent");
            withUuid(referencedElement, "uuid-child");

            var entry = converter.convert(List.of(removeRef)).get(0);

            assertEquals(SemanticChangeType.REFERENCE_VALUE_REMOVED, entry.getChangeType());
            assertTrue(entry.getReferencedElementUuid().contains("uuid-child"));
            assertTrue(entry.getFrom().contains("uuid-child"));
            assertEquals(2, entry.getPosition());
        }
    }

    @Nested
    @DisplayName("InsertRootEObject / RemoveRootEObject")
    class RootEObjectTests {

        @Test
        @DisplayName("InsertRootEObject maps to ROOT_INSERTED with resource URI in 'to' field")
        void insertRootEObjectMapsToRootInserted() {
            InsertRootEObject<EObject> insertRoot = typedMock(InsertRootEObject.class);
            when(insertRoot.getNewValue()).thenReturn(element);
            when(insertRoot.getUri()).thenReturn("file:///models/A.xmi");
            withUuid(element, "uuid-root-insert");

            var entry = converter.convert(List.of(insertRoot)).get(0);

            assertEquals(SemanticChangeType.ROOT_INSERTED, entry.getChangeType());
            assertEquals("InsertRootEObject", entry.getEmfType());
            assertTrue(entry.getElementUuid().contains("uuid-root-insert"));
            assertEquals("file:///models/A.xmi", entry.getTo());
            assertNull(entry.getFrom());
            assertNull(entry.getFeature());
        }

        @Test
        @DisplayName("RemoveRootEObject maps to ROOT_REMOVED with resource URI in 'from' field")
        void removeRootEObjectMapsToRootRemoved() {
            RemoveRootEObject<EObject> removeRoot = typedMock(RemoveRootEObject.class);
            when(removeRoot.getOldValue()).thenReturn(element);
            when(removeRoot.getUri()).thenReturn("file:///models/A.xmi");
            withUuid(element, "uuid-root-remove");

            var entry = converter.convert(List.of(removeRoot)).get(0);

            assertEquals(SemanticChangeType.ROOT_REMOVED, entry.getChangeType());
            assertEquals("RemoveRootEObject", entry.getEmfType());
            assertTrue(entry.getElementUuid().contains("uuid-root-remove"));
            assertEquals("file:///models/A.xmi", entry.getFrom());
            assertNull(entry.getTo());
            assertNull(entry.getFeature());
        }
    }

    /**
     * Verifies that the {@code index} field on each entry reflects its position in the input list.
     */
    @Test
    @DisplayName("Index field reflects position in input list")
    void indexFieldReflectsPositionInInputList() {
        CreateEObject<EObject> create1 = typedMock(CreateEObject.class);
        CreateEObject<EObject> create2 = typedMock(CreateEObject.class);
        when(create1.getAffectedElement()).thenReturn(element);
        when(create2.getAffectedElement()).thenReturn(element);
        when(uuidResolver.hasUuid(element)).thenReturn(false);

        var entries = converter.convert(List.of(create1, create2));

        assertEquals(0, entries.get(0).getIndex());
        assertEquals(1, entries.get(1).getIndex());
    }

    /**
     * Verifies that when {@link UuidResolver#hasUuid} returns false, elementUuid is "unknown".
     */
    @Test
    @DisplayName("UUID resolution failure produces 'unknown' elementUuid without throwing")
    void uuidResolutionFailureProducesUnknown() {
        CreateEObject<EObject> create = typedMock(CreateEObject.class);
        when(create.getAffectedElement()).thenReturn(element);
        when(uuidResolver.hasUuid(element)).thenReturn(false);

        var entry = converter.convert(List.of(create)).get(0);

        assertEquals("unknown", entry.getElementUuid());
    }

    /**
     * Verifies that a null element resolves to null elementUuid without throwing.
     */
    @Test
    @DisplayName("Null affected element resolves to null elementUuid")
    void nullElementResolvesToNullUuid() {
        CreateEObject<EObject> create = typedMock(CreateEObject.class);
        when(create.getAffectedElement()).thenReturn(null);

        var entry = converter.convert(List.of(create)).get(0);

        assertNull(entry.getElementUuid());
    }

    /**
     * Verifies that the eClass field is formatted as {@code "nsPrefix::ClassName"}.
     */
    @Test
    @DisplayName("eClass is formatted as nsPrefix::ClassName")
    void eClassIsFormattedWithNsPrefix() {
        CreateEObject<EObject> create = typedMock(CreateEObject.class);
        when(create.getAffectedElement()).thenReturn(element);
        when(uuidResolver.hasUuid(element)).thenReturn(false);
        when(ePackage.getNsPrefix()).thenReturn("pcm");
        when(eClass.getName()).thenReturn("Component");

        var entry = converter.convert(List.of(create)).get(0);

        assertEquals("pcm::Component", entry.getEClass());
    }
}
