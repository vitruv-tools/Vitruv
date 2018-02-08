/**
 * 
 */
package tools.vitruv.extensions.changevisualization.ui;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import tools.vitruv.extensions.changevisualization.ChangeDataSet;
import tools.vitruv.extensions.changevisualization.ChangeVisualization.VisualizationMode;
import tools.vitruv.extensions.changevisualization.table.ChangeTable;
import tools.vitruv.extensions.changevisualization.tree.ChangeTree;

/**
 * @author andreas
 *
 */
public class ChangesTab extends JPanel implements ListSelectionListener{
	
	private ChangeComponent visualization;

	private final Vector<ChangeDataSet> dataVector = new Vector<ChangeDataSet>();

	private CdsTable cdsTable;

	private final VisualizationMode visualizationMode;

	
	public ChangesTab(ChangeDataSet cds, VisualizationMode mode) {
		super(new BorderLayout());		
		this.visualizationMode=mode;
		
		createUI();
		addChanges(cds);
		
		cdsTable.setSelected(0);
	}
	
	private void createUI() {
		
		JSplitPane panel=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
				
		//add cds selection	
		cdsTable=new CdsTable();
		cdsTable.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),BorderFactory.createTitledBorder("Change Selection")));
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

	public void addChanges(ChangeDataSet cds) {
		dataVector.add(cds);		
		cdsTable.appendCds(cds);
	}

	public Vector<ChangeDataSet> getChanges() {
		return dataVector;
	}

	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		//System.out.println(e.toString());
		if(e.getValueIsAdjusting()) return;
		int row=cdsTable.getSelectedRow();
		if(row==-1) {
			visualization.setData(null);
		}else{
			//System.out.println("Changing to row "+row);			
			ChangeDataSet cds = dataVector.elementAt(row);
			visualization.setData(cds);
		}
	}	
}
