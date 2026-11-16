package tools.vitruv.framework.vsum.persistence;

import tools.vitruv.framework.vsum.persistence.delta.DeltaBasedModelPersistence;

/**
 * Creates the persistence strategy a virtual model uses unless one is set explicitly: {@link
 * DeltaBasedModelPersistence} with its default options.
 *
 * <p>The system property {@value #PROPERTY} overrides the default for the whole JVM: {@code state}
 * selects {@link StateBasedModelPersistence}, {@code delta} the delta-based one. This lets test
 * suites run in both modes without changing their code.
 */
public final class DefaultModelPersistence {
  /** The system property that selects the default persistence: {@code delta} or {@code state}. */
  public static final String PROPERTY = "vitruv.persistence";

  private DefaultModelPersistence() {}

  /**
   * Creates a new instance of the default persistence strategy, for one virtual model.
   *
   * @return the new persistence strategy
   * @throws IllegalArgumentException if the system property {@value #PROPERTY} has an unknown
   *     value
   */
  public static ModelPersistence create() {
    String mode = System.getProperty(PROPERTY, "delta").trim();
    return switch (mode) {
      case "delta", "" -> new DeltaBasedModelPersistence();
      case "state" -> new StateBasedModelPersistence();
      default ->
          throw new IllegalArgumentException(
              "unknown value '" + mode + "' of system property " + PROPERTY
                  + "; use 'delta' or 'state'");
    };
  }
}
