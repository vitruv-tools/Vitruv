package tools.vitruv.change.testutils.changevisualization;

/**
 * This class provides a standalone application for visualizing changes. It initializes the
 * ChangeVisualizationUI with a ChangeVisualizationDataModel and makes the UI visible.
 */
public final class StandaloneChangeVisualization {
  /** No instances of StandaloneChangeVisualization are used. */
  public StandaloneChangeVisualization() {
    // Intentionally empty: no instance needed; only used via static main method
  }

  /**
   * Main method to an start offline instance of the ChangeVisualization.
   *
   * @param args Command line arguments, are ignored
   */
  public static void main(String[] args) {
    new ChangeVisualizationUI(new ChangeVisualizationDataModel()).setVisible(true);
  }
}
