package tools.vitruv.extensions.changevisualization;

public final class StandaloneChangeVisualization {
	public StandaloneChangeVisualization() {
	}

	/**
	 * Main method to an start offline instance of the ChangeVisualization
	 * @param args Command line arguments, are ignored
	 */
	public static void main(String[] args) {
		new ChangeVisualizationUI(new ChangeVisualizationDataModel()).setVisible(true);
	}

}
