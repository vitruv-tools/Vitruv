package tools.vitruv.extensions.changevisualization.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import tools.vitruv.extensions.changevisualization.common.ChangeDataSet;
import tools.vitruv.extensions.changevisualization.common.VisualizationMode;
import tools.vitruv.extensions.changevisualization.tree.ChangeTree;

/**
 * The changes tab is responsible for displaying the tab of a single model.
 * It storage the added changeDataSets, holds the component performing the actual visualizuation
 * and a ChangeDataSetTable to displaying general information
 *  
 * @author Andreas Loeffler
 *
 */
public class ChangesTab extends JPanel implements ListSelectionListener{

	/**
	 * Needed for eclipse to stop warning about serialVersionIds. This feature will never been used. 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The color used to highlight objects
	 */
	public static final Color HIGHLIGHT_COLOR=Color.BLUE;

	/**
	 * The ChangeComponent implementing the actual visualization
	 */
	private ChangeComponent visualization;

	/**
	 * List of ChangeDataSets that exist for this ChangeComponent
	 */
	private final List<ChangeDataSet> changeDataSets = new Vector<ChangeDataSet>();

	/**
	 * The table responsible for the display of the general changeDataSet information
	 */
	private ChangeDataSetTable changeDataSetTable;

	/**
	 * The active visualization mode
	 */
	private final VisualizationMode visualizationMode;

	/**
	 * The affectedEOject id to highlight
	 */
	private String highlightID;
	
	/**
	 * The title of the tab
	 */
	private final String title;	

	/**
	 * True if the tab was created from a saved file
	 */
	private final boolean loadedFromFile;

	/**
	 * Create a ChangesTab with a given ChangeDataSet as initial value and a given visualization mode
	 * @param changeDataSet The initial changeDataSet
	 * @param mode The visualization mode
	 * @param title The title
	 * @param loadedFromFile True if loaded from file, false otherwise
	 */
	public ChangesTab(ChangeDataSet changeDataSet, VisualizationMode mode, String title, boolean loadedFromFile) {
		super(new BorderLayout());		
		this.visualizationMode=mode;
		this.loadedFromFile=loadedFromFile;
		this.title=title;

		createUI();

		addChanges(changeDataSet);

		changeDataSetTable.setSelected(0);
	}

	/**
	 * Creates the ui of the ChangesTab
	 */
	private void createUI() {

		JSplitPane panel=new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		//add changeDataSet selection	
		changeDataSetTable=new ChangeDataSetTable();
		TitledBorder cdsTitleBorder = BorderFactory.createTitledBorder("Change Selection");
		cdsTitleBorder.setTitleFont(ChangeVisualizationUI.DEFAULT_TITLED_BORDER_FONT);
		changeDataSetTable.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),cdsTitleBorder));
		panel.add(changeDataSetTable);

		changeDataSetTable.addListSelectionListener(this);

		//add visualization
		switch(visualizationMode) {
		case TREE:
			visualization=new ChangeTree();
			break;
		default:
			//As long as all cases are covered in switch statement, this code is never reached
			throw new RuntimeException("Uncovered case in witch statement, missing mode "+visualizationMode);
		}

		panel.add(visualization);		

		add(panel,BorderLayout.CENTER);
		panel.setDividerLocation(300);
	}

	/**
	 * Adds a ChangeDataSet to this ChangesTab
	 * @param changeDataSet The changeDataSet to add
	 */
	public void addChanges(ChangeDataSet changeDataSet) {
		changeDataSets.add(changeDataSet);		
		changeDataSetTable.appendCds(changeDataSet);
	}

	/**
	 * Gets the ChangeDataSets of this ChangesTab
	 * @return
	 */
	public List<ChangeDataSet> getChanges() {
		return changeDataSets;
	}


	@Override
	public void valueChanged(ListSelectionEvent e) {
		//Reacts to ListSelectionEvents of the changeDataSetTable and updates the visualization
		if(e.getValueIsAdjusting()) return;
		int row=changeDataSetTable.getSelectedRow();
		if(row==-1) {
			visualization.setData(null);
		}else{
			//System.out.println("Changing to row "+row);			
			ChangeDataSet changeDataSet = changeDataSets.get(row);
			visualization.setData(changeDataSet);
		}
	}

	/**
	 * Returns the visualizationMode
	 * @return The visualization mode
	 */
	public VisualizationMode getVisualizationMode() {
		return visualizationMode;
	}

	/**
	 * Set the (affectedEObject) id to highlight
	 * @param highlightID the id to highlight
	 */
	public void setHighlightID(String highlightID) {
		this.highlightID=highlightID;
		this.changeDataSetTable.setHighlightedCdsIDs(visualization.determineHighlightedCdsIds(highlightID,changeDataSets));
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

	/**
	 * Returns whether this changeTab was loaded from a file
	 * @return True if loaded from file, false otherwise
	 */
	public boolean isLoadedFromFile() {
		return loadedFromFile;
	}
		
}
