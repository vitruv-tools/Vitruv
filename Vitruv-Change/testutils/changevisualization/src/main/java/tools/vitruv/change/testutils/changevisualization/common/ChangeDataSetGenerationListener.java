package tools.vitruv.change.testutils.changevisualization.common;

/** A listener for the generation of a ChangeDataSet. */
public interface ChangeDataSetGenerationListener {
  /**
   * This method is called when a new ChangeDataSet is generated.
   *
   * @param changeDataSet The new ChangeDataSet.
   */
  public void changeDataSetGenerated(ChangeDataSet changeDataSet);
}
