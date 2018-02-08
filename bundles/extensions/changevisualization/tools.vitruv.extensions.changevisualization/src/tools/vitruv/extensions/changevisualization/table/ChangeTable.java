package tools.vitruv.extensions.changevisualization.table;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import tools.vitruv.extensions.changevisualization.ChangeDataSet;
import tools.vitruv.extensions.changevisualization.ui.ChangeComponent;

/**
 * The table visualization was the first approach and displays most information wrong
 * Do not consider it during code review, it will either be updated or removed in the final plugin
 * 
 * @author Andreas Löffler
 *
 */

public class ChangeTable extends ChangeComponent implements MouseWheelListener{	

	private JTable table;

	public ChangeTable() {
		super(new BorderLayout());	
		createUI();
	}

	private void createUI() {

		table = new JTable();

		table.setAutoCreateRowSorter(true);
		table.setFont(table.getFont().deriveFont(14f));
		table.setRowHeight(24);
		table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(16f).deriveFont(Font.BOLD));
		table.setShowGrid(true);

		table.setFillsViewportHeight(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
		JScrollPane scroller=new JScrollPane(table);
		add(scroller,BorderLayout.CENTER);
		scroller.addMouseWheelListener(this);

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {		
		//System.out.println(e.getModifiersExText(e.getModifiersEx()));
		if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == 0) {
			return;
		}
		if(e.getWheelRotation()<=-1) {
			float newSize=table.getFont().getSize()+2;
			if(newSize>30) newSize=30;
			table.setFont(table.getFont().deriveFont(newSize));
			table.setRowHeight((int)(newSize+10));
		}else if(e.getWheelRotation()>=1) {
			float newSize=table.getFont().getSize()-2;
			if(newSize<5) newSize=5;
			table.setFont(table.getFont().deriveFont(newSize));
			table.setRowHeight((int)(newSize+10));
		}
	}


	@Override
	public void setData(ChangeDataSet cds) {

		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Type");
		columnNames.add("vChange");
		columnNames.add("eChange");
		columnNames.add("eObject");
		columnNames.add("runtime class");
		columnNames.add("name");
		columnNames.add("Old");
		columnNames.add("New");

		Vector<Vector> rowData=cds==null?new Vector<Vector>():(Vector<Vector>)cds.getData();	

		table.setModel(new DefaultTableModel(rowData, columnNames){

			public boolean isCellEditable(int row, int column) {
				return false;
			}	

			/*
			 * JTable uses this method to determine the default renderer/
			 * editor for each cell.*/
			public Class getColumnClass(int c) {
				switch(c){
				default:
					return String.class;
				}
			}

		});		

		table.getColumnModel().getColumn( 0 ).setPreferredWidth( 70 );//type
		table.getColumnModel().getColumn( 1 ).setPreferredWidth( 120 );//vChange
		table.getColumnModel().getColumn( 2 ).setPreferredWidth( 70 );//eChange
		table.getColumnModel().getColumn( 3 ).setPreferredWidth( 70 );//eObject
		table.getColumnModel().getColumn( 4 ).setPreferredWidth( 280 );//runtime class
		table.getColumnModel().getColumn( 5 ).setPreferredWidth( 70 );//name
		table.getColumnModel().getColumn( 6 ).setPreferredWidth( 70 );//old
		table.getColumnModel().getColumn( 7 ).setPreferredWidth( 70 );//new

	}

}
