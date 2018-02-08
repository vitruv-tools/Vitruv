/**
 * 
 */
package tools.vitruv.extensions.changevisualization.tree.decoder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author andreas
 *
 */
public class EObjectFeatureDecoder implements FeatureDecoder {

	@Override 	
	public Class getDecodedClass(){
		return EObject.class;
	}

	@Override
	public String decodeSimple(Object obj) {
		org.eclipse.emf.ecore.EObject eObj=(org.eclipse.emf.ecore.EObject)obj;
		String name=getName(eObj);
		if(name==null) {
			return eObj.eClass().getName();
		}else {
			return eObj.eClass().getName()+" ["+name+"]";
		}		
	}		
	
	private String getName(EObject eObj) {
		for (EStructuralFeature feature:eObj.eClass().getEAllStructuralFeatures()) {
			if(feature==null) {
				//Why are null features listed? this may/should never happen
				continue;
			}
			if(feature.getName().equals("name")){
				Object fObj=eObj.eGet(feature);
				return fObj.toString();
			}
			if(feature.getName().equals("entityName")){
				Object fObj=eObj.eGet(feature);
				return fObj.toString();
			}
		}
		return null;
	}
	
	@Override
	public String decodeDetailed(Object obj) {
		return null;
	}
	
	@Override
	public Component decodeDetailedUI(Object obj) {
		JPanel pane = new JPanel(new BorderLayout());
		JPanel left = new JPanel(new GridLayout(1,1));
		left.setBorder(new EmptyBorder(5,5,5,5));
		JPanel center=new JPanel(new GridLayout(1,1));
		center.setBorder(new EmptyBorder(5,5,5,5));				

		org.eclipse.emf.ecore.EObject eObj=(org.eclipse.emf.ecore.EObject)obj;

		//add general information
		{//eClass info
			JLabel label=new JLabel("eClass",JLabel.RIGHT);
			JTextField field=new JTextField(eObj.eClass().getName());
			field.setEditable(false);
			((GridLayout)left.getLayout()).setRows(((GridLayout)left.getLayout()).getRows()+1);
			left.add(label);
			((GridLayout)center.getLayout()).setRows(((GridLayout)center.getLayout()).getRows()+1);
			center.add(field);
		}
		{//runtime class info
			JLabel label=new JLabel("runtime class",JLabel.RIGHT);
			JTextField field=new JTextField(eObj.getClass().getName());
			field.setEditable(false);
			((GridLayout)left.getLayout()).setRows(((GridLayout)left.getLayout()).getRows()+1);
			left.add(label);
			((GridLayout)center.getLayout()).setRows(((GridLayout)center.getLayout()).getRows()+1);
			center.add(field);
		}

		for (EStructuralFeature feature:eObj.eClass().getEAllStructuralFeatures()) {
			if(feature==null) {
				//Why are null features listed? this may/should never happen
				continue;
			}
			Object fObj=eObj.eGet(feature);
			if(fObj==null) {
				fObj="not existent";
			}

			JLabel label=new JLabel(feature.getName(),JLabel.RIGHT);
			JTextField field=new JTextField(fObj.toString());
			field.setEditable(false);
			((GridLayout)left.getLayout()).setRows(((GridLayout)left.getLayout()).getRows()+1);
			left.add(label);
			((GridLayout)center.getLayout()).setRows(((GridLayout)center.getLayout()).getRows()+1);
			center.add(field);
		}				
		pane.add(left,BorderLayout.WEST);
		pane.add(center,BorderLayout.CENTER);
		return new JScrollPane(pane);
	}		

}
