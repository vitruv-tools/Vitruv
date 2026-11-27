package tools.vitruv.change.testutils.changevisualization.tree;

/** Interface for classes that can be highlighted in a tab. */
public interface TabHighlighting {
  /**
   * Sets the highlightID of the object.
   *
   * @param highlightID The highlightID
   */
  public void setHighlightID(String highlightID);

  /**
   * Returns the highlightID of the object.
   *
   * @return The highlightID
   */
  public String getHighlightID();
}
