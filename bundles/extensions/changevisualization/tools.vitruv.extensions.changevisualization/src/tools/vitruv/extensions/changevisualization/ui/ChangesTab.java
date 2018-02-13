package tools.vitruv.extensions.changevisualization.ui;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import tools.vitruv.extensions.changevisualization.ChangeDataSet;
import tools.vitruv.extensions.changevisualization.ChangeVisualization.VisualizationMode;
import tools.vitruv.extensions.changevisualization.table.ChangeTable;
import tools.vitruv.extensions.changevisualization.tree.ChangeTree;

/**
 * The changes tab is responsible for displaying the tab of a single model.
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
	 * The ChangeComponent implementing the actual visualization
	 */
	private ChangeComponent visualization;

	/**
	 * List of ChangeDataSets that exist for this ChangeComponent
	 */
	private final List<ChangeDataSet> changeDataSets = new Vector<ChangeDataSet>();

	/**
	 * The table responsible for the display of the dataVector information
	 */
	private CdsTable cdsTable;

	/**
	 * The active visualization mode
	 */
	private final VisualizationMode visualizationMode;

	/**
	 * Create a ChangesTab with a given ChangeDataSet as initial value and a given visualization mode
	 * @param cds The initial cds
	 * @param mode The visualization mode
	 */
	public ChangesTab(ChangeDataSet cds, VisualizationMode mode) {
		super(new BorderLayout());		
		this.visualizationMode=mode;

		createUI();

		addChanges(cds);

		cdsTable.setSelected(0);
	}

	/**
	 * Creates the ui of the ChangesTab
	 */
	private void createUI() {

		JSplitPane panel=new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		//add cds selection	
		cdsTable=new CdsTable();
		TitledBorder cdsTitleBorder = BorderFactory.createTitledBorder("Change Selection");
		cdsTitleBorder.setTitleFont(ChangeVisualizationUI.DEFAULT_TITLED_BORDER_FONT);
		cdsTable.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),cdsTitleBorder));
		panel.add(cdsTable);

		cdsTable.addListSelectionListener(this);

		//add visualization
		switch(visualizationMode) {
		case TABLE:
			visualization=new ChangeTable();
			break;
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
	 * @param cds The cds to add
	 */
	public void addChanges(ChangeDataSet cds) {
		changeDataSets.add(cds);		
		cdsTable.appendCds(cds);
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
		//Reacts to ListSelectionEvents of the cdsTable and updates the visualization
		if(e.getValueIsAdjusting()) return;
		int row=cdsTable.getSelectedRow();
		if(row==-1) {
			visualization.setData(null);
		}else{
			//System.out.println("Changing to row "+row);			
			ChangeDataSet cds = changeDataSets.get(row);
			visualization.setData(cds);
		}
	}	
}
