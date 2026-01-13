package tools.vitruv.framework.testutils.integration;

import tools.vitruv.change.testutils.views.TestView;
import tools.vitruv.framework.vsum.VirtualModel;

/**
 * A {@link TestView} that uses a {@link VirtualModel} for model management and change propagation.
 */
public interface VirtualModelBasedTestView extends TestView {
  /**
   * Gets the virtual model used by this test view.
   *
   * @return the virtual model
   */
  VirtualModel getVirtualModel();
}
