package tools.vitruv.extensions.changevisualization.tree;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Displays all structural features of an EObject in a scrollable UI. It shows the names of the
 * structural features on the left and their values as TextField on the right. This information
 * is extended by the eClass name and the runtime class name of the eObject.
 * 
 * @author Andreas Loeffler
 */
public class EObjectStructuralFeaturePanel extends JScrollPane{

	/**
	 * Needed for eclipse to stop warning about serialVersionIds. This feature will never been used. 
	 */
	private static final long serialVersionUID = 1L;

	public EObjectStructuralFeaturePanel(EObject eObj) {
		//Create the basic layout and panel structure
		JPanel pane = new JPanel(new BorderLayout());
		JPanel left = new JPanel(new GridLayout(1,1));
		left.setBorder(new EmptyBorder(5,5,5,5));
		JPanel center=new JPanel(new GridLayout(1,1));
		center.setBorder(new EmptyBorder(5,5,5,5));			
		pane.add(left,BorderLayout.WEST);
		pane.add(center,BorderLayout.CENTER);

		//add general information
		createEClassInformation(center,left,eObj);

		//Add runtime class information
		createRuntimeClassInformation(center,left,eObj);

		//add strucutral feature information
		createStructuralFeatureInformation(center,left,eObj);		

		//Put it into this scrollPane
		setViewportView(pane);
	}

	/**
	 * Creates the lines for all strucutral features
	 * 
	 * @param center The center panel
	 * @param left The left panel
	 * @param eObj The eObject
	 */
	private void createStructuralFeatureInformation(JPanel center, JPanel left, EObject eObj) {
		for (EStructuralFeature feature:eObj.eClass().getEAllStructuralFeatures()) {
			if(feature==null) {
				continue;
			}
			Object fObj=eObj.eGet(feature);
			if(fObj==null) {
				fObj="";
			}
			createLine(center,left,feature.getName(),String.valueOf(fObj));
		}	
	}

	/**
	 * Creates the line with the runtime class information
	 * 
	 * @param center The center panel
	 * @param left The left panel
	 * @param eObj The eObject
	 */
	private void createRuntimeClassInformation(JPanel center, JPanel left, EObject eObj) {
		createLine(center,left,"runtime class",eObj.getClass().getName());
	}

	/**
	 * Creates the line with the eClass information
	 * 
	 * @param center The center panel
	 * @param left The left panel
	 * @param eObj The eObject
	 */
	private void createEClassInformation(JPanel center, JPanel left, EObject eObj) {
		createLine(center,left,"eClass",eObj.eClass().getName());
	}		

	/**
	 * Create a line in the center and left panel with the given texts
	 * 
	 * @param center The center panel
	 * @param left The left panel
	 * @param labelText The text for the label
	 * @param fieldText The text for the field
	 */
	private void createLine(JPanel center,JPanel left,String labelText,String fieldText) {
		//Create the label
		JLabel label=new JLabel(labelText,JLabel.RIGHT);

		//Create the field
		JTextField field=new JTextField(fieldText);
		field.setEditable(false);

		//Add label to left and increase the layout dimension
		((GridLayout)left.getLayout()).setRows(((GridLayout)left.getLayout()).getRows()+1);
		left.add(label);

		//Add field to center and increase the layout dimension
		((GridLayout)center.getLayout()).setRows(((GridLayout)center.getLayout()).getRows()+1);
		center.add(field);
	}

}
