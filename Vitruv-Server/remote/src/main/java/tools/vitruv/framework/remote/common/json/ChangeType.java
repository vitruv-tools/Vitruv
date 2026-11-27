package tools.vitruv.framework.remote.common.json;

import tools.vitruv.change.composite.description.CompositeChange;
import tools.vitruv.change.composite.description.TransactionalChange;
import tools.vitruv.change.composite.description.VitruviusChange;

/** An enumeration representing different types of Vitruvius changes. */
public enum ChangeType {
  /** Transaltional change type. */
  TRANSACTIONAL,
  /** Composite change type. */
  COMPOSITE,
  /** Unknown change type. */
  UNKNOWN;

  /**
   * Returns the type of the given {@link VitruviusChange}.
   *
   * @param change The change to obtain the type from.
   * @return The type of the change.
   */
  public static ChangeType getChangeTypeOf(VitruviusChange<?> change) {
    if (change instanceof TransactionalChange) {
      return TRANSACTIONAL;
    }
    if (change instanceof CompositeChange<?, ?>) {
      return COMPOSITE;
    }
    return UNKNOWN;
  }
}
