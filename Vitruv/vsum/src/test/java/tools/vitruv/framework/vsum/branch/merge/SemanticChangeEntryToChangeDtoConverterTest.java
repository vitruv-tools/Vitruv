package tools.vitruv.framework.vsum.branch.merge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SemanticChangeEntryToChangeDtoConverter}.
 *
 * <p>Tests verify the field mapping for attribute changes, reference changes,
 * namespace-prefix stripping on eClass, and null/missing field handling.
 */
class SemanticChangeEntryToChangeDtoConverterTest {

    private static SemanticChangeEntry attrEntry(String uuid, String feature,
                                                  String from, String to) {
        return SemanticChangeEntry.builder()
                .index(2)
                .changeType(SemanticChangeType.ATTRIBUTE_CHANGED)
                .emfType("ReplaceSingleValuedEAttribute")
                .elementUuid(uuid)
                .eClass("entities::Entity")
                .hierarchicalId("hid-" + uuid)
                .feature(feature)
                .from(from)
                .to(to)
                .position(3)
                .build();
    }

    private static SemanticChangeEntry refEntry(String uuid, String feature,
                                                 String from, String to) {
        return SemanticChangeEntry.builder()
                .index(1)
                .changeType(SemanticChangeType.REFERENCE_CHANGED)
                .emfType("ReplaceSingleValuedEReference")
                .elementUuid(uuid)
                .eClass("entities::Entity")
                .hierarchicalId("hid-" + uuid)
                .feature(feature)
                .from(from)
                .to(to)
                .position(0)
                .build();
    }

    @Test
    @DisplayName("Attribute change: emfType, elementUuid, hierarchicalId, feature, and position are mapped correctly")
    void attributeChangeBasicFieldMapping() {
        var entry = attrEntry("uuid-1", "name", "old", "new");
        var dto = SemanticChangeEntryToChangeDtoConverter.convert(entry);

        assertEquals("ReplaceSingleValuedEAttribute", dto.changeType);
        assertEquals("uuid-1", dto.affectedElementUuid);
        assertEquals("hid-uuid-1", dto.affectedElementId);
        assertEquals("name", dto.featureName);
        assertEquals(3, dto.index);
    }

    @Test
    @DisplayName("Attribute change: from/to are mapped to oldLiteralValue/newLiteralValue, not reference fields")
    void attributeChangeFromToAreLiteralValues() {
        var dto = SemanticChangeEntryToChangeDtoConverter.convert(
                attrEntry("u", "age", "0", "42"));

        assertEquals("0", dto.oldLiteralValue);
        assertEquals("42", dto.newLiteralValue);
        assertNull(dto.oldValueId, "reference fields must be null for attribute changes");
        assertNull(dto.newValueId);
    }

    @Test
    @DisplayName("Reference change: from/to are mapped to oldValueId/newValueId, not literal fields")
    void referenceChangeFromToAreValueIds() {
        var dto = SemanticChangeEntryToChangeDtoConverter.convert(
                refEntry("u", "parent", "old-uuid", "new-uuid"));

        assertEquals("old-uuid", dto.oldValueId);
        assertEquals("new-uuid", dto.newValueId);
        assertNull(dto.oldLiteralValue, "literal fields must be null for reference changes");
        assertNull(dto.newLiteralValue);
    }

    @Test
    @DisplayName("eClass with namespace prefix is stripped to just the class name")
    void eClassNsPrefixIsStripped() {
        var dto = SemanticChangeEntryToChangeDtoConverter.convert(
                attrEntry("u", "f", null, null));

        assertEquals("Entity", dto.affectedEClassName,
                "the 'entities::' prefix must be stripped");
    }

    @Test
    @DisplayName("eClass without namespace prefix is kept as-is")
    void eClassWithoutPrefixIsKeptAsIs() {
        var entry = SemanticChangeEntry.builder()
                .index(0).changeType(SemanticChangeType.ATTRIBUTE_CHANGED)
                .emfType("RSV").elementUuid("u").eClass("Entity").build();
        var dto = SemanticChangeEntryToChangeDtoConverter.convert(entry);

        assertEquals("Entity", dto.affectedEClassName);
    }

    @Test
    @DisplayName("Null eClass results in null affectedEClassName")
    void nullEClassResultsInNull() {
        var entry = SemanticChangeEntry.builder()
                .index(0).changeType(SemanticChangeType.ATTRIBUTE_CHANGED)
                .emfType("RSV").elementUuid("u").build();
        var dto = SemanticChangeEntryToChangeDtoConverter.convert(entry);

        assertNull(dto.affectedEClassName);
    }

    @Test
    @DisplayName("ELEMENT_CREATED is treated as an attribute/lifecycle change (not reference)")
    void elementCreatedIsNotReferenceChange() {
        var entry = SemanticChangeEntry.builder()
                .index(0).changeType(SemanticChangeType.ELEMENT_CREATED)
                .emfType("CreateEObject").elementUuid("u")
                .from("old-val").to("new-val").build();
        var dto = SemanticChangeEntryToChangeDtoConverter.convert(entry);

        assertEquals("old-val", dto.oldLiteralValue);
        assertEquals("new-val", dto.newLiteralValue);
        assertNull(dto.oldValueId);
        assertNull(dto.newValueId);
    }
}
