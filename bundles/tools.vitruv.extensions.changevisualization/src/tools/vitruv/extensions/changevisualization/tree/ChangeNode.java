package tools.vitruv.extensions.changevisualization.tree;

import java.awt.Component;
import java.io.Serializable;

import tools.vitruv.extensions.changevisualization.ui.LabelValuePanel;

/**
 * Collects all information regarding EChange-Nodes
 * 
 * @author Andreas Loeffler
 *
 */
public class ChangeNode implements Serializable{

	/**
	 * A serial version id for java object serialization
	 */
	private static final long serialVersionUID = 5499249335308821465L;
	
	/**
	 * The different types of eChanges
	 * @author Andreas Loeffler
	 */
	public static enum EChangeClass{
		REFERENCE_ECHANGE,
		EXISTENCE_ECHANGE,
		ATTRIBUTE_ECHANGE,
		ROOT_ECHANGE
	}
		
	/**
	 * The text to display for the node in the jtree
	 */
	private final String individualText;
	
	/**
	 * The id of the affected eObject
	 */
	private final String eObjectID;
	
	/**
	 * Details in the form of a String[][] are stored here
	 */
	private final String[][] structuralFeatureLabelValues;
	
	/**
	 * The type of the echange
	 */
	private final EChangeClass changeClass;
	
	/**
	 * Simple text state
	 */
	private boolean simpleEChangeText=false;
	
	/**
	 * Show id state
	 */
	private boolean showID=false;

	/**
	 * The eClassName of the echange
	 */
	private final String eClassName;

	/**
	 *	The eClassName of the affected eObject 
	 */
	private final String affectedClass;

	/**
	 * Constrcuts a change node used to display the text in a jtree
	 * 
	 * @param individualText Individual text of the node, if any
	 * @param structuralFeatureLabelValues Name/Value pairs holding all structural feature info of the echange
	 * @param changeClass The type of the echange
	 * @param eClassName The eClassName of the echange
	 * @param affectedClass The eClassName of the affeced eObject
	 * @param eObjectID The id of the affected eObject
	 */
	public ChangeNode(String individualText,String[][] structuralFeatureLabelValues, EChangeClass changeClass, String eClassName, String affectedClass, String eObjectID){
		this.individualText=individualText;
		this.structuralFeatureLabelValues=structuralFeatureLabelValues;
		this.changeClass=changeClass;
		this.eClassName=eClassName;
		this.affectedClass=affectedClass;
		this.eObjectID=eObjectID;
	}
	
	/**
	 * The detailsUI component
	 * 
	 * @return The detailsUI component
	 */
	public Component getDetailsUI() {
		return new LabelValuePanel(this.structuralFeatureLabelValues);
	}

	@Override
	public String toString() {
		//The tree renderer uses the toString() method to get the text to display		
		if(isSimpleEChangeText()) {
			if(isShowID()) {
				//Show id and simple text
				return eClassName+(affectedClass!=null?" : "+affectedClass:"")+" <"+getEObjectID()+">";
			}else{
				//Don't show id and show simple text
				return eClassName+(affectedClass!=null?" : "+affectedClass:"");
			}	
		}else{
			if(isShowID()) {
				//Show id and individual text
				return eClassName+(affectedClass!=null?" : "+affectedClass:"")+" <"+getEObjectID()+"> | "+individualText;
			}else{
				//Don't show id and show individual text
				return eClassName+(affectedClass!=null?" : "+affectedClass:"")+" | "+individualText;
			}
		}
	}

	/**
	 * Returns the type of the echange
	 * 
	 * @return The type
	 */
	public EChangeClass getChangeClass() {
		return changeClass;
	}

	/**
	 * Gets the id of the eobject affected by the visualized echange
	 * @return The id of the affected eobject
	 */
	public String getEObjectID() {
		return eObjectID;
	}
	
	/**
	 * Returns whether simple text is active
	 * @return True if active
	 */
	public boolean isSimpleEChangeText() {
		return simpleEChangeText;
	}

	/**
	 * Sets the simple change text mode
	 * 
	 * @param simpleEChangeText True to enable
	 */
	public void setSimpleEChangeText(boolean simpleEChangeText) {
		this.simpleEChangeText=simpleEChangeText;
	}
	
	/**
	 * Returns whether show id is active
	 * @return True if active
	 */
	public boolean isShowID() {
		return showID;
	}

	/**
	 * Sets the show id 
	 * @param showID True to show the id
	 */
	public void setShowID(boolean showID) {
		this.showID=showID;
	}


}
