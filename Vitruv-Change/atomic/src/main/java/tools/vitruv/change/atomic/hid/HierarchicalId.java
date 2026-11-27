package tools.vitruv.change.atomic.hid;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * A hierarchical id is a volatile identifier which identifies an element based on its hierarchical
 * location in a resource. A hierarchical id is not bound to a resource set, i.e. two elements at
 * the same location in identical resources of different resource sets will have the same
 * hierarchical id.
 */
public final class HierarchicalId extends EObjectImpl implements Comparable<HierarchicalId> {
  public static final String CACHE_PREFIX = "cache:/";

  private String id;

  /**
   * Creates a new hierarchical id with the given raw value.
   *
   * @param id the raw value of the hierarchical id.
   */
  public HierarchicalId(String id) {
    this.id = id;
  }

  /**
   * Returns the raw value of the hierarchical id.
   *
   * @return the raw value of the hierarchical id.
   */
  public String getId() {
    return id;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof HierarchicalId other) {
      return other.getId().equals(id);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public int compareTo(HierarchicalId o) {
    return id.compareTo(o.getId());
  }

  @Override
  public String toString() {
    return "Id(" + id + ")";
  }

  /**
   * Tests if the given id stands for a cached {@link EObject}.
   *
   * @return if id has the CACHE_PREFIX.
   */
  public boolean isCache() {
    return id.startsWith(CACHE_PREFIX);
  }
}
