package tools.vitruv.change.propagation;

/** The mode in which changes are propagated by executing change propagation specifications. */
public enum ChangePropagationMode {
  /** Only executes transformations adjacent to the changed model's metamodel. */
  SINGLE_STEP,

  /** Executes transformations transitively and cyclic until no further changes are produced. */
  TRANSITIVE_CYCLIC,

  /**
   * Executes transformations transitively, but does not further propagate changes to a leaf model
   * (i.e., a model with only one transformation from and to another model). Provides specific
   * behavior that is currently required for the Commonalities language and should be removed after
   * adapting that language.
   */
  @Deprecated
  TRANSITIVE_EXCEPT_LEAVES
}
