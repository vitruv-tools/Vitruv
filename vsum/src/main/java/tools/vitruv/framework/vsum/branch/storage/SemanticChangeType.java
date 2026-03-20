package tools.vitruv.framework.vsum.branch.storage;

/**
 * Categorizes atomic model changes in a way that is both human-readable and precise enough
 * for conflict detection during branch merges.
 *
 * <p>Each constant maps to one or more EMF {@code EChange} subtypes (recorded in {@code emfType}
 * on {@link SemanticChangeEntry}) and carries a plain description that is written to the
 * changelog so readers unfamiliar with EMF can still understand what happened.
 *
 * <p>Conflict severity guidance (used by a future TODO: {@code ConflictClassifier}):
 * <ul>
 *   <li><b>Low</b>  – {@link #ELEMENT_CREATED}, {@link #ATTRIBUTE_VALUE_INSERTED},
 *       {@link #ATTRIBUTE_VALUE_REMOVED} when both branches affect different elements or features.</li>
 *   <li><b>Medium</b> – {@link #ATTRIBUTE_CHANGED} / {@link #REFERENCE_CHANGED} on the same
 *       {@code (elementUuid, feature)} pair; {@link #ELEMENT_REORDERED} on the same containment.</li>
 *   <li><b>High</b>  – {@link #ELEMENT_DELETED} combined with any change on the same
 *       {@code elementUuid}; conflicting {@link #REFERENCE_VALUE_INSERTED} /
 *       {@link #REFERENCE_VALUE_REMOVED}.</li>
 * </ul>
 */
public enum SemanticChangeType {

    // 1.Type: Object lifecycle
    /**
     * A new model element was created. Maps to {@code CreateEObject}.
     */
    ELEMENT_CREATED("A new model element was added"),

    /**
     * A model element was removed. Maps to {@code DeleteEObject}.
     */
    ELEMENT_DELETED("A model element was removed"),

    /**
     * A root element was placed into a resource. Maps to {@code InsertRootEObject}.
     */
    ROOT_INSERTED("A root element was added to a resource"),

    /**
     * A root element was removed from a resource. Maps to {@code RemoveRootEObject}.
     */
    ROOT_REMOVED("A root element was removed from a resource"),

    // 2.Type: Single-valued attributes
    /**
     * An attribute received its first value (previous value was unset / default).
     * Maps to {@code ReplaceSingleValuedEAttribute} where {@code oldValue == null}.
     */
    ATTRIBUTE_SET("An attribute was given its initial value"),

    /**
     * An attribute value was replaced with a different value.
     * Maps to {@code ReplaceSingleValuedEAttribute} where both old and new values are non-null.
     */
    ATTRIBUTE_CHANGED("An attribute value was changed"),

    /**
     * An attribute value was cleared / reset to the default.
     * Maps to {@code ReplaceSingleValuedEAttribute} where {@code newValue == null}.
     */
    ATTRIBUTE_CLEARED("An attribute was cleared"),

    // 3.Type: Multi-valued attributes
    /**
     * A value was appended or inserted into a multi-valued attribute list.
     * Maps to {@code InsertEAttributeValue}.
     */
    ATTRIBUTE_VALUE_INSERTED("A value was added to an attribute list"),

    /**
     * A value was removed from a multi-valued attribute list.
     * Maps to {@code RemoveEAttributeValue}.
     */
    ATTRIBUTE_VALUE_REMOVED("A value was removed from an attribute list"),

    // 4.Type: Single-valued references
    /**
     * A reference was linked to a target element for the first time.
     * Maps to {@code ReplaceSingleValuedEReference} where {@code oldValue == null}.
     */
    REFERENCE_SET("A reference was linked to an element"),

    /**
     * A reference target was swapped for a different element.
     * Maps to {@code ReplaceSingleValuedEReference} where both old and new values are non-null.
     */
    REFERENCE_CHANGED("A reference target was changed"),

    /**
     * A reference was unlinked from its target.
     * Maps to {@code ReplaceSingleValuedEReference} where {@code newValue == null}.
     */
    REFERENCE_CLEARED("A reference was unlinked"),

    // 5.Type: Multi-valued references / containment
    /**
     * An element was added to a multi-valued reference or containment list.
     * Maps to {@code InsertEReference}.
     */
    REFERENCE_VALUE_INSERTED("An element was added to a reference list"),

    /**
     * An element was removed from a multi-valued reference or containment list.
     * Maps to {@code RemoveEReference}.
     */
    REFERENCE_VALUE_REMOVED("An element was removed from a reference list"),

    // 6.Type: Ordering
    /**
     * Elements within an ordered containment were rearranged without adding or removing them.
     * Maps to {@code PermuteContainmentEReference}.
     */
    ELEMENT_REORDERED("Elements were reordered within a collection");

    private final String description;

    SemanticChangeType(String description) {
        this.description = description;
    }

    /**
     * Returns the plain-English description written to the JSON changelog.
     * Intended for readers who are not familiar with the EMF change model.
     */
    public String getDescription() {
        return description;
    }
}
