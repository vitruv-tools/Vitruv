package tools.vitruv.extensions.changevisualization.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import tools.vitruv.extensions.changevisualization.ChangeVisualizationUI;
import tools.vitruv.extensions.changevisualization.common.ChangeDataSet;
import tools.vitruv.extensions.changevisualization.common.ChangeDataSetGenerationListener;
import tools.vitruv.extensions.changevisualization.common.ModelRepositoryChanges;
import tools.vitruv.extensions.changevisualization.tree.ChangeTree;
import tools.vitruv.extensions.changevisualization.tree.TabHighlighting;

/**
 * The changes tab is responsible for displaying the tab of a single model. It storage the added changeDataSets, holds
 * the component performing the actual visualizuation and a ChangeDataSetTable to displaying general information
 */
public class ChangesTab extends JPanel implements ListSelectionListener, ChangeDataSetGenerationListener, TabHighlighting {
	private static final long serialVersionUID = -5293272783862251463L;

	/**
	 * The color used to highlight objects
	 */
	public static final Color HIGHLIGHT_COLOR = Color.BLUE;

	/**
	 * The ChangeComponent implementing the actual visualization
	 */
	private ChangeTree visualization;

	/**
	 * The table responsible for the display of the general changeDataSet information
	 */
	private ChangeDataSetTable changeDataSetTable;

	/**
	 * The affectedEOject id to highlight
	 */
	private String highlightID;

	private final String title;

	private final ModelRepositoryChanges displayedChanges;

	public ChangesTab(ModelRepositoryChanges displayedChanges, boolean loadedFromFile) {
		super(new BorderLayout());
		this.title = (loadedFromFile ? "* " : "") + displayedChanges.getRepositoryName();
		createUI();
		this.displayedChanges = displayedChanges;
		this.displayedChanges.registerChangeDataSetGenerationListener(this);
		this.displayedChanges.getChangeDataSets().forEach(dataSet -> changeDataSetGenerated(dataSet));
	}

	public void setActive(boolean isActive) {
		visualization.setEnabled(isActive);
	}

	/**
	 * Creates the ui of the ChangesTab
	 */
	private void createUI() {
		JSplitPane panel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		// add changeDataSet selection
		changeDataSetTable = new ChangeDataSetTable();
		TitledBorder cdsTitleBorder = BorderFactory.createTitledBorder("Change Selection");
		cdsTitleBorder.setTitleFont(ChangeVisualizationUI.DEFAULT_TITLED_BORDER_FONT);
		changeDataSetTable.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), cdsTitleBorder));
		panel.add(changeDataSetTable);

		changeDataSetTable.addListSelectionListener(this);

		// add visualization
		visualization = new ChangeTree(this);
		panel.add(visualization);

		add(panel, BorderLayout.CENTER);
		panel.setDividerLocation(300);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// Reacts to ListSelectionEvents of the changeDataSetTable and updates the visualization
		if (e.getValueIsAdjusting())
			return;
		int row = changeDataSetTable.getSelectedRow();
		if (row == -1) {
			visualization.setData(null);
		} else {
			// System.out.println("Changing to row "+row);
			ChangeDataSet changeDataSet = displayedChanges.getChangeDataSets().get(row);
			visualization.setData(changeDataSet);
		}
	}

	/**
	 * Set the (affectedEObject) id to highlight
	 * @param highlightID the id to highlight
	 */
	public void setHighlightID(String highlightID) {
		this.highlightID = highlightID;
		this.changeDataSetTable.setHighlightedCdsIDs(visualization.determineHighlightedCdsIds(highlightID, displayedChanges.getChangeDataSets()));
		this.visualization.repaint();
		this.changeDataSetTable.repaint();
	}

	/**
	 * Gets the (affectedEObject) id highlighted
	 * @return The highlighted id, may be null
	 */
	public String getHighlightID() {
		return highlightID;
	}

	/**
	 * Gets the title of the changeTab
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
