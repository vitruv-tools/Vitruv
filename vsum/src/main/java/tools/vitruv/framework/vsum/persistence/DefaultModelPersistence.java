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

  /**
   * The system property that overrides when the delta-based persistence writes the model files:
   * {@code ON_SNAPSHOT} or {@code EVERY_PROPAGATION}, see {@link
   * DeltaBasedModelPersistence.ModelFiles}. Lets test suites be measured or run in either mode.
   */
  public static final String MODEL_FILES_PROPERTY = "vitruv.persistence.modelFiles";

  private DefaultModelPersistence() {}

  /**
   * Creates a new instance of the default persistence strategy, for one virtual model.
   *
   * @return the new persistence strategy
   * @throws IllegalArgumentException if the system property {@value #PROPERTY} has an unknown
   *     value
   */
  public static ModelPersistence create() {
    return create(DeltaBasedModelPersistence.Options.defaults());
  }

  /**
   * Creates the default persistence strategy as test infrastructure uses it: delta-based persistence
   * writes the model files after every propagation, so that tests can read them. The system
   * properties {@value #PROPERTY} and {@value #MODEL_FILES_PROPERTY} override this as for {@link
   * #create()}.
   *
   * @return the new persistence strategy
   */
  public static ModelPersistence createForTests() {
    return create(
        DeltaBasedModelPersistence.Options.defaults()
            .withModelFiles(DeltaBasedModelPersistence.ModelFiles.EVERY_PROPAGATION));
  }

  /**
   * Creates a new instance of the default persistence strategy, for one virtual model, with the
   * given options for the delta-based persistence. Test infrastructure uses this to have the model
   * files written after every propagation.
   *
   * @param deltaOptions the options if the delta-based persistence is chosen
   * @return the new persistence strategy
   * @throws IllegalArgumentException if the system property {@value #PROPERTY} has an unknown
   *     value
   */
  public static ModelPersistence create(DeltaBasedModelPersistence.Options deltaOptions) {
    String mode = System.getProperty(PROPERTY, "delta").trim();
    String modelFiles = System.getProperty(MODEL_FILES_PROPERTY, "").trim();
    DeltaBasedModelPersistence.Options options =
        modelFiles.isEmpty()
            ? deltaOptions
            : deltaOptions.withModelFiles(
                DeltaBasedModelPersistence.ModelFiles.valueOf(modelFiles));
    return switch (mode) {
      case "delta", "" -> new DeltaBasedModelPersistence(options);
      case "state" -> new StateBasedModelPersistence();
      default ->
          throw new IllegalArgumentException(
              "unknown value '" + mode + "' of system property " + PROPERTY
                  + "; use 'delta' or 'state'");
    };
  }
}
