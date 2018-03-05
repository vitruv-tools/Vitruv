/**
 * 
 */
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
 * @author andreas
 *
 */
public class SelectionDialog extends JDialog {
	
	private JCheckBox[] boxes;
	private boolean validClose=false;

	public SelectionDialog(JFrame frame, String[] titles) {
		this(frame,titles,null);		
	}
	
	public SelectionDialog(JFrame frame, String[] titles, boolean[] initialValues) {
		super(frame,true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		createUI(titles,initialValues);		
		pack();
		setLocationRelativeTo(frame);
	}

	private void createUI(String[] titles, boolean[] initialValues) {
		setLayout(new GridLayout(titles.length+2,1));
		boxes=new JCheckBox[titles.length];
		for(int n=0;n<titles.length;n++) {
			boolean selected=initialValues==null?true:initialValues[n];
			boxes[n]=new JCheckBox(titles[n],selected);
			boxes[n].setFont(ChangeVisualizationUI.DEFAULT_CHECKBOX_FONT);
			add(boxes[n]);
		}
		add(new JLabel());
		
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

	public boolean[] getResult() {
		boolean[] result=new boolean[boxes.length];
		for(int n=0;n<boxes.length;n++) {
			result[n]=validClose&&boxes[n].isSelected();
		}
		return result;
	}

}
