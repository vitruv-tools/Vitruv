package tools.vitruv.framework.vsum.internal.messages;

/** Constants for error messages. */
public class ErrorMessages {
  public static final String CHANGE_NULL = "change to propagate";
  public static final String CHANGE_HAS_NO_CONCRETE_CHANGE =
      "This change contains no concrete change:%s%s";
  public static final String PROPAGATION_LISTENER_NULL = "propagationListener";
  public static final String PROPAGATION_OBSERVER_NULL = "propagationObserver";
  public static final String CONSTRAINT_CHECKER_NULL = "constraintChecker";
  public static final String MODEL_PERSISTENCE_NULL = "modelPersistence";
  public static final String DUPLICATE_CONSTRAINT_ID =
      "There is already another constraint with the id %s";
  public static final String CHECKER_CONSTRAINTS_NULL =
      "Constraint checker %s returned no constraints";
  public static final String CHECKER_FAILED = "Constraint checker failed: %s";
  public static final String CHECKER_RETURNED_NO_RESULT = "Constraint checker returned no result";
  public static final String DUPLICATE_CONSTRAINT_LANGUAGE =
      "There is already another editable constraint checker for the language %s";
}
