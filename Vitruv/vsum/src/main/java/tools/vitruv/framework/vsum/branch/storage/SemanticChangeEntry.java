package tools.vitruv.framework.vsum.branch.storage;

import java.util.List;
import java.util.Objects;
import lombok.Getter;

/**
 * Represents a single atomic model change within a semantic changelog JSON entry.
 *
 * <p>Each entry corresponds to one EMF {@code EChange} and captures enough information to:
 * <ul>
 *   <li>Understand the change without EMF knowledge (via {@link #changeType} and
 *       {@link #changeDescription}).</li>
 *   <li>Replay or reverse the change (via {@link #elementUuid}, {@link #feature},
 *       {@link #from}, {@link #to}).</li>
 *   <li>Detect semantic conflicts during branch merges (via {@link #elementUuid} and
 *       {@link #feature}).</li>
 * </ul>
 *
 * <p>Conflict detection key: two entries from different branches conflict when their
 * {@code elementUuid} and {@code feature} values match and the change types indicate
 * incompatible operations (see {@link SemanticChangeType} javadoc for severity guidance).
 *
 * <p>Fields that are not applicable for a given change type are {@code null}.
 * For example, {@code feature} is {@code null} for {@link SemanticChangeType#ELEMENT_CREATED}
 * and {@link SemanticChangeType#ELEMENT_DELETED}, and {@code referencedElementUuid} is only
 * set for reference changes.
 */
@Getter
public class SemanticChangeEntry {

  /**
   * Zero-based position of this change within the ordered list of changes for its file.
   * Preserves the recording order, which is required for correct replay.
   */
  private final int index;

  /**
   * Human-readable category of the change.
   */
  private final SemanticChangeType changeType;

  /**
   * Plain description of {@link #changeType}, copied from
   * {@link SemanticChangeType#getDescription()} for readers who are not familiar with EMF.
   */
  private final String changeDescription;

  /**
   * The internal EMF {@code EChange} class name (e.g. {@code "ReplaceSingleValuedEAttribute"}).
   * Included for debugging and for tooling that works directly with the Vitruv change model.
   */
  private final String emfType;

  /**
   * Stable Vitruvius UUID of the element that was directly affected by this change
   * (e.g. the element whose attribute was modified, or the element that was created/deleted).
   * Used as the primary key for cross-branch conflict detection.
   */
  private final String elementUuid;

  /**
   * Fully qualified type of the affected element in {@code "metamodel::ClassName"} format
   * (e.g. {@code "entities::Entity"}). {@code null} for changes whose affected element
   * type cannot be determined.
   */
  private final String eClass;

  /**
   * Name of the structural feature (attribute or reference) that was changed.
   * {@code null} for lifecycle changes ({@link SemanticChangeType#ELEMENT_CREATED},
   * {@link SemanticChangeType#ELEMENT_DELETED}).
   */
  private final String feature;

  /**
   * Previous value before the change was applied.
   * <ul>
   *   <li>For attribute changes: the string representation of the old attribute value.</li>
   *   <li>For single-valued reference changes: the UUID of the previously referenced
   *       element.</li>
   *   <li>For multi-valued insertions and lifecycle changes: {@code null}.</li>
   * </ul>
   */
  private final String from;

  /**
   * New value after the change was applied.
   * <ul>
   *   <li>For attribute changes: the string representation of the new attribute value.</li>
   *   <li>For single-valued reference changes: the UUID of the newly referenced element.</li>
   *   <li>For multi-valued removals and lifecycle changes: {@code null}.</li>
   * </ul>
   */
  private final String to;

  /**
   * UUID of the element that was inserted into or removed from a multi-valued reference.
   * Only set for {@link SemanticChangeType#REFERENCE_VALUE_INSERTED} and
   * {@link SemanticChangeType#REFERENCE_VALUE_REMOVED}; {@code null} for all other types.
   */
  private final String referencedElementUuid;

  /**
   * UUID of the immediate EMF container (parent) of the affected element at the time the
   * change was recorded. {@code null} for root elements and lifecycle changes
   * ({@link SemanticChangeType#ELEMENT_CREATED}, {@link SemanticChangeType#ELEMENT_DELETED}).
   *
   * <p>Used as the foundation for cascading-deletion detection: if an element is deleted
   * and its UUID is tombstoned, any change entry whose {@code containerUuid} matches the
   * tombstoned UUID can be identified as a now-invalid orphan change.
   */
  private final String containerUuid;

  /**
   * UUIDs of all descendant elements that are implicitly deleted when this element is
   * removed. Only populated for {@link SemanticChangeType#ELEMENT_DELETED} entries, where
   * the capturing code walks {@code eAllContents()} of the deleted element before
   * detachment. {@code null} or empty for all other change types.
   *
   * <p>Used by conflict detection to identify modifications on other branches that target
   * elements which were transitively deleted, even when the modified element's UUID differs
   * from the directly deleted element's UUID.
   */
  private final List<String> cascadeDeletedUuids;

  /**
   * Hierarchical position of the affected element within its resource at the time the change
   * was recorded, expressed as a {@code HierarchicalId} string
   * (e.g. {@code "//elements/0/children/2"}).
   *
   * <p>Complements {@link #elementUuid} for cross-branch element identity: two entries from
   * different branches refer to the same structural position when their {@code hierarchicalId}
   * values match, even if UUID resolution is unavailable at merge time.
   * {@code null} if the hierarchical resolver was not available when this entry was produced.
   */
  private final String hierarchicalId;

  /**
   * Origin of this change relative to the developer's direct action.
   * <ul>
   *   <li>{@code "original"}: directly triggered by the developer (user-initiated change).</li>
   *   <li>{@code "consequential"}: triggered by a consistency rule / reaction in response to
   *       an original change.</li>
   * </ul>
   *
   * <p>Used during conflict resolution: when two branches modify the same element-feature pair
   * and one side's change is {@code "original"} while the other is {@code "consequential"},
   * the original change takes precedence automatically.
   */
  private final String changeOrigin;

  /**
   * Zero-based index within the list at which the value was inserted or removed.
   * Only meaningful for multi-valued attribute and reference changes
   * ({@link SemanticChangeType#ATTRIBUTE_VALUE_INSERTED},
   * {@link SemanticChangeType#ATTRIBUTE_VALUE_REMOVED},
   * {@link SemanticChangeType#REFERENCE_VALUE_INSERTED},
   * {@link SemanticChangeType#REFERENCE_VALUE_REMOVED}).
   * {@code -1} for changes that do not involve a list position.
   */
  private final int position;

  private SemanticChangeEntry(Builder builder) {
    this.index = builder.index;
    this.changeType = Objects.requireNonNull(builder.changeType, "changeType must not be null");
    this.changeDescription = changeType.getDescription();
    this.emfType = Objects.requireNonNull(builder.emfType, "emfType must not be null");
    this.elementUuid = builder.elementUuid;
    this.eClass = builder.eClass;
    this.feature = builder.feature;
    this.from = builder.from;
    this.to = builder.to;
    this.referencedElementUuid = builder.referencedElementUuid;
    this.containerUuid = builder.containerUuid;
    this.cascadeDeletedUuids = builder.cascadeDeletedUuids;
    this.position = builder.position;
    this.hierarchicalId = builder.hierarchicalId;
    this.changeOrigin = builder.changeOrigin;
  }

  /**
   * Returns the change origin as a typed enum. Parses the stored String field so that
   * consumers expecting a {@link ChangeOrigin} value (e.g. conflict resolution logic)
   * can use this without requiring a field type change.
   */
  public ChangeOrigin getOrigin() {
    return ChangeOrigin.fromString(changeOrigin);
  }

  /**
   * Returns a new {@link Builder} for constructing a {@link SemanticChangeEntry}.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builder for {@link SemanticChangeEntry}.
   */
  public static class Builder {

    private int index;

    private SemanticChangeType changeType;

    private String emfType;

    private String elementUuid;

    private String eClass;

    private String feature;

    private String from;

    private String to;

    private String referencedElementUuid;

    private String containerUuid;

    private List<String> cascadeDeletedUuids;

    private int position = -1;

    private String hierarchicalId;

    private String changeOrigin;

    private Builder() {
    }

    /**
     * Sets the zero-based position of this change in the ordered list for its file.
     */
    public Builder index(int index) {
      this.index = index;
      return this;
    }

    /**
     * Sets the semantic category of the change.
     */
    public Builder changeType(SemanticChangeType changeType) {
      this.changeType = changeType;
      return this;
    }

    /**
     * Sets the EMF {@code EChange} class name for debugging.
     */
    public Builder emfType(String emfType) {
      this.emfType = emfType;
      return this;
    }

    /**
     * Sets the Vitruvius UUID of the directly affected element.
     */
    public Builder elementUuid(String elementUuid) {
      this.elementUuid = elementUuid;
      return this;
    }

    /**
     * Sets the fully qualified EClass in {@code "nsPrefix::ClassName"} format.
     */
    public Builder eClass(String eClass) {
      this.eClass = eClass;
      return this;
    }

    /**
     * Sets the name of the structural feature that was changed.
     */
    public Builder feature(String feature) {
      this.feature = feature;
      return this;
    }

    /**
     * Sets the previous value before the change was applied.
     */
    public Builder from(String from) {
      this.from = from;
      return this;
    }

    /**
     * Sets the new value after the change was applied.
     */
    public Builder to(String to) {
      this.to = to;
      return this;
    }

    /**
     * Sets the UUID of the element inserted into or removed from a multi-valued reference.
     */
    public Builder referencedElementUuid(String uuid) {
      this.referencedElementUuid = uuid;
      return this;
    }

    /**
     * Sets the UUID of the immediate EMF container of the affected element.
     */
    public Builder containerUuid(String containerUuid) {
      this.containerUuid = containerUuid;
      return this;
    }

    /**
     * Sets the UUIDs of all descendant elements implicitly deleted with this element.
     * Only meaningful for {@link SemanticChangeType#ELEMENT_DELETED} entries.
     */
    public Builder cascadeDeletedUuids(List<String> cascadeDeletedUuids) {
      this.cascadeDeletedUuids = cascadeDeletedUuids;
      return this;
    }

    /**
     * Sets the zero-based list index at which the value was inserted or removed.
     */
    public Builder position(int position) {
      this.position = position;
      return this;
    }

    /**
     * Sets the hierarchical position string of the affected element within its resource.
     */
    public Builder hierarchicalId(String hierarchicalId) {
      this.hierarchicalId = hierarchicalId;
      return this;
    }

    /**
     * Sets the origin of the change: {@code "original"} for developer-initiated changes,
     * {@code "consequential"} for reaction-triggered consistency changes.
     */
    public Builder changeOrigin(String changeOrigin) {
      this.changeOrigin = changeOrigin;
      return this;
    }

    /**
     * Builds and returns a new {@link SemanticChangeEntry}.
     */
    public SemanticChangeEntry build() {
      return new SemanticChangeEntry(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SemanticChangeEntry that = (SemanticChangeEntry) o;
    return index == that.index
        && position == that.position
        && changeType == that.changeType
        && Objects.equals(emfType, that.emfType)
        && Objects.equals(elementUuid, that.elementUuid)
        && Objects.equals(eClass, that.eClass)
        && Objects.equals(feature, that.feature)
        && Objects.equals(from, that.from)
        && Objects.equals(to, that.to)
        && Objects.equals(referencedElementUuid, that.referencedElementUuid)
        && Objects.equals(containerUuid, that.containerUuid)
        && Objects.equals(cascadeDeletedUuids, that.cascadeDeletedUuids)
        && Objects.equals(hierarchicalId, that.hierarchicalId)
        && Objects.equals(changeOrigin, that.changeOrigin);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        index, changeType, emfType, elementUuid, eClass, feature, from, to,
        referencedElementUuid, containerUuid, cascadeDeletedUuids, position,
        hierarchicalId, changeOrigin);
  }

  @Override
  public String toString() {
    return "SemanticChangeEntry{"
        + "index=" + index
        + ", changeType=" + changeType
        + ", changeOrigin='" + changeOrigin + '\''
        + ", elementUuid='" + elementUuid + '\''
        + ", hierarchicalId='" + hierarchicalId + '\''
        + ", containerUuid='" + containerUuid + '\''
        + ", cascadeDeletedUuids=" + cascadeDeletedUuids
        + ", feature='" + feature + '\''
        + ", from='" + from + '\''
        + ", to='" + to + '\''
        + '}';
  }
}
