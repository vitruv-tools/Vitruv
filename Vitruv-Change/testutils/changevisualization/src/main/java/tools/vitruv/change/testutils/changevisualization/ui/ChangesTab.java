package tools.vitruv.change.testutils.changevisualization.ui;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import tools.vitruv.change.testutils.changevisualization.ChangeVisualizationUI;
import tools.vitruv.change.testutils.changevisualization.common.ChangeDataSet;
import tools.vitruv.change.testutils.changevisualization.common.ChangeDataSetGenerationListener;
import tools.vitruv.change.testutils.changevisualization.common.ModelRepositoryChanges;
import tools.vitruv.change.testutils.changevisualization.tree.ChangeTree;
import tools.vitruv.change.testutils.changevisualization.tree.TabHighlighting;

/**
 * The changes tab is responsible for displaying the tab of a single model. It
 * storage the added
 * changeDataSets, holds the component performing the actual visualizuation and
 * a ChangeDataSetTable
 * to displaying general information.
 */
public class ChangesTab extends JPanel
    implements ListSelectionListener, ChangeDataSetGenerationListener, TabHighlighting, TabColours {
  private static final long serialVersionUID = -5293272783862251463L;

  /** The ChangeComponent implementing the actual visualization. */
  private ChangeTree visualization;

  /**
   * The table responsible for the display of the general changeDataSet
   * information.
   */
  private ChangeDataSetTableView changeDataSetTable;

  /** The affectedEOject id to highlight. */
  private String highlightID;

  private final String title;

  private final ModelRepositoryChanges displayedChanges;

  /**
   * Creates a new ChangesTab.
   *
   * @param displayedChanges The changes to display
   * @param loadedFromFile   Whether the changes were loaded from a file
   */
  public ChangesTab(ModelRepositoryChanges displayedChanges, boolean loadedFromFile) {
    super(new BorderLayout());
    this.title = (loadedFromFile ? "* " : "") + displayedChanges.getRepositoryName();
    createUI();
    this.displayedChanges = displayedChanges;
    this.displayedChanges.registerChangeDataSetGenerationListener(this);
    this.displayedChanges.getChangeDataSets().forEach(this::changeDataSetGenerated);
  }

  /**
   * Sets the active state of the tab.
   *
   * @param isActive The new state
   */
  public void setActive(boolean isActive) {
    visualization.setEnabled(isActive);
  }

  /** Creates the ui of the ChangesTab. */
  private void createUI() {

    // add changeDataSet selection
    changeDataSetTable = new ChangeDataSetTable();
    TitledBorder cdsTitleBorder = BorderFactory.createTitledBorder("Change Selection");
    cdsTitleBorder.setTitleFont(ChangeVisualizationUI.DEFAULT_TITLED_BORDER_FONT);
    JSplitPane panel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    changeDataSetTable.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5), cdsTitleBorder));
    panel.add((ChangeDataSetTable) changeDataSetTable);

    changeDataSetTable.addListSelectionListener(this);

    // add visualization
    visualization = new ChangeTree(this);
    panel.add(visualization);

    add(panel, BorderLayout.CENTER);
    panel.setDividerLocation(300);
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    // Reacts to ListSelectionEvents of the changeDataSetTable and updates the
    // visualization
    if (e.getValueIsAdjusting()) {
      return;
    }
    int row = changeDataSetTable.getSelectedRow();
    if (row == -1) {
      visualization.setData(null);
    } else {
      ChangeDataSet changeDataSet = displayedChanges.getChangeDataSets().get(row);
      visualization.setData(changeDataSet);
    }
  }

  /**
   * Set the (affectedEObject) id to highlight.
   *
   * @param highlightID the id to highlight
   */
  public void setHighlightID(String highlightID) {
    this.highlightID = highlightID;
    this.changeDataSetTable.setHighlightedCdsIDs(
        visualization.determineHighlightedCdsIds(
            highlightID, displayedChanges.getChangeDataSets()));
    this.visualization.repaint();
    this.changeDataSetTable.repaint();
  }

  /**
   * Gets the (affectedEObject) id highlighted.
   *
   * @return The highlighted id, may be null
   */
  public String getHighlightID() {
    return highlightID;
  }

  /**
   * Gets the title of the changeTab.
   *
   * @return The title
   */
  public String getTitle() {
    return title;
  }

  @Override
  public void changeDataSetGenerated(ChangeDataSet changeDataSet) {
    changeDataSetTable.appendCds(changeDataSet);
    repaint();
  }
}
