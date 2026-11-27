package tools.vitruv.change.atomic.uuid;

/**
 * A UUID is a permanent identifier which is associated with an element for its entire lifetime. A
 * UUID is bound to a resource set, i.e. two elements at the same location in identical resources of
 * different resource sets will <b>not</b> have the same UUID.
 */
public final class Uuid {
  private String rawValue;

  Uuid(String rawValue) {
    this.rawValue = rawValue;
  }

  String getRawValue() {
    return rawValue;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Uuid other) {
      return other.getRawValue().equals(rawValue);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return rawValue.hashCode();
  }

  @Override
  public String toString() {
    return "Uuid(" + rawValue + ")";
  }
}
