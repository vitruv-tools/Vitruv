package tools.vitruv.framework.applications;

import java.util.Set;
import tools.vitruv.change.propagation.ChangePropagationSpecification;

/** Interface for Vitruv applications. */
public interface VitruvApplication {

  /**
   * Returns the change propagation specifications of this Vitruv application.
   *
   * @return the change propagation specifications
   */
  public Set<ChangePropagationSpecification> getChangePropagationSpecifications();

  /**
   * Returns the name of this Vitruv application.
   *
   * @return the name
   */
  public String getName();
}
