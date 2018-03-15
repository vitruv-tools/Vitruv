package tools.vitruv.extensions.changevisualization.utils;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import tools.vitruv.extensions.changevisualization.ui.ChangeVisualizationUI;

/**
 * A dialog displaying some information the user can select 
 * 
 * @author Andreas Loeffler
 */
public class SelectionDialog extends JDialog {
	
	/**
	 * The checkBoxes the user can de/select
	 */
	private JCheckBox[] boxes;
	
	/**
	 * When closing via the done-button this value is set to true. This way a valid exit
	 * can be distinguished from just closing the dialog or other cancel-behavior
	 */
	private boolean validClose=false;

	/**
	 * Create a dialog that displays the given strings for selection. All are selected by default.
	 * 
	 * @param frame Used to position the dialog on screen and to return the input-focus to after closing.
	 * @param values The values presented to the user for selection
	 */
	public SelectionDialog(JFrame frame, String[] values) {
		this(frame,values,null);		
	}
	
	/**
	 * Create a dialog that displays the given strings for selection.
	 * 
	 * The initial selection state is given in die initialSelections array. It this array is null, all are selected by default. 
	 * 
	 * @param frame Used to position the dialog on screen and to return the input-focus to after closing.
	 * @param values The values presented to the user for selection
	 * @param initialSelections The initial selection states. Either null or of same size as values.
	 */
	public SelectionDialog(JFrame frame, String[] values, boolean[] initialSelections) {
		super(frame,true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		createUI(values,initialSelections);		
		pack();
		setLocationRelativeTo(frame);
	}

	/**
	 * Creates the ui.
	 * 
	 * @param values The values displayed for selection
	 * @param initialSelections Initial selection state, may be null
	 */
	private void createUI(String[] values, boolean[] initialSelections) {
		setLayout(new GridLayout(values.length+2,1));
		boxes=new JCheckBox[values.length];
		for(int n=0;n<values.length;n++) {
			boolean selected=initialSelections==null?true:initialSelections[n];
			boxes[n]=new JCheckBox(values[n],selected);
			boxes[n].setFont(ChangeVisualizationUI.DEFAULT_CHECKBOX_FONT);
			add(boxes[n]);
		}
		add(new JLabel());
		
		//If closed via the done button, the selection is marked as valid
		JButton done=new JButton("Done");
		done.setFont(ChangeVisualizationUI.DEFAULT_BUTTON_FONT);
		done.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				validClose=true;
				setVisible(false);
			}			
		});
		add(done);
	}

	/**
	 * Return an array of booleans holding the final selection state of the user.
	 * If the user cancelled the dialog (not exited via "done" button), nothing is marked as selected, even if the
	 * user had selected something prior to canceling the dialog.
	 * 
	 * @return The selection state of the values displayed
	 */
	public boolean[] getResult() {
		boolean[] result=new boolean[boxes.length];
		for(int n=0;n<boxes.length;n++) {
			result[n]=validClose&&boxes[n].isSelected();
		}
		return result;
	}

}
