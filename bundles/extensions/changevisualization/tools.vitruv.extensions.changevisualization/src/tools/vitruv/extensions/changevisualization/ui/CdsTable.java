package tools.vitruv.extensions.changevisualization.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import tools.vitruv.extensions.changevisualization.ChangeDataSet;

public class CdsTable extends JPanel implements MouseWheelListener{

	private JTable table;

	public CdsTable() {
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
				
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("ID");
		columnNames.add("Time");
		columnNames.add("Propagated changes");
		columnNames.add("Original changes");
		columnNames.add("Consequential changes");
		
		Vector<Vector> rowData=new Vector<Vector>();	
		table.setModel(new DefaultTableModel(rowData, columnNames){

			public boolean isCellEditable(int row, int column) {
				return false;
			}	

			/*
			 * JTable uses this method to determine the default renderer/
			 * editor for each cell.*/
			public Class getColumnClass(int c) {
				switch(c){
				case 2:
				case 3:
				case 4:
					return Integer.class;
				case 1:
					return Date.class;
				default:
					return String.class;
				}
			}

		});		

		table.getColumnModel().getColumn( 0 ).setPreferredWidth( 350 );//ID
		table.getColumnModel().getColumn( 1 ).setPreferredWidth( 70 );//Time
		table.getColumnModel().getColumn( 2 ).setPreferredWidth( 70 );//ID
		table.getColumnModel().getColumn( 3 ).setPreferredWidth( 70 );//ID
		table.getColumnModel().getColumn( 4 ).setPreferredWidth( 70 );//ID
		
		table.setDefaultRenderer(Date.class,new DefaultTableCellRenderer(){
			
			private SimpleDateFormat df=new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss");
			
			 /* Sets the <code>String</code> object for the cell being rendered to
		     * <code>value</code>.
		     * 
		     * @param value  the string value for this cell; if value is
		     *		<code>null</code> it sets the text value to an empty string
		     * @see JLabel#setText
		     * 
		     */
		    protected void setValue(Object value) {
		    	setText((value == null) ? "" : df.format((Date)value));
		    }
		});
		
		JScrollPane scroller=new JScrollPane(table);
		add(scroller,BorderLayout.CENTER);
		scroller.addMouseWheelListener(this);
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {		
		//System.out.println(e.getModifiersExText(e.getModifiersEx()));
		if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == 0) return;
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

	public void setSelected(int row) {
		table.getSelectionModel().setSelectionInterval(row, row);
	}	

	public void addListSelectionListener(ListSelectionListener listener) {
		table.getSelectionModel().addListSelectionListener(listener);
	}

	public void appendCds(ChangeDataSet cds) {
		Vector line=encode(cds);
		((DefaultTableModel)table.getModel()).addRow(line);	
	}

	private Vector encode(ChangeDataSet cds) {
		Vector line=new Vector();
		line.add(cds.getCdsID());
		line.add(cds.getCreationTime());
		line.add(cds.getNrPChanges());
		line.add(cds.getNrOChanges());
		line.add(cds.getNrCChanges());
		return line;
	}

	public int getSelectedRow() {
		return table.getSelectedRow();
	}

}
