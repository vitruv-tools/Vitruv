package tools.vitruv.change.testutils.changevisualization.ui;

import java.awt.LayoutManager;
import java.util.List;
import javax.swing.JPanel;
import tools.vitruv.change.testutils.changevisualization.common.ChangeDataSet;

/**
 * Base class for different visualization implementations. If additional implementation are needed,
 * they have to subclass this class to be usable by the change visualization framework.
 *
 * @author Andreas Loeffler
 */
public abstract class ChangeComponent extends JPanel {

  /**
   * Needed for eclipse to stop warning about serialVersionIds. This feature will never been used.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Mandatory constructor to use for implementations.
   *
   * @param layout The layout manager to use
   */
  protected ChangeComponent(LayoutManager layout) {
    super(layout);
  }

  /**
   * Visualizes a given ChangeDataSet.
   *
   * @param cds The ChangeDataSet to visualize
   */
  public abstract void setData(ChangeDataSet cds);

  /**
   * Returns a list of ChangeDataSet-ids that have a at least one highlighted node under the given
   * highlightID.
   *
   * @param highlightID The (affectedEObject)id to look for highlighting in the given ChangeDataSets
   * @param changeDataSets The changeDataSets to process
   * @return List of changeDataSet ids
   */
  public abstract List<String> determineHighlightedCdsIds(
      String highlightID, List<ChangeDataSet> changeDataSets);
}
