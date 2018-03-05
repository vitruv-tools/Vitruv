package tools.vitruv.extensions.changevisualization.tree;

import java.awt.Component;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.extensions.changevisualization.tree.decoder.echange.ChangeDecoder;
import tools.vitruv.extensions.changevisualization.tree.decoder.echange.CreateEObjectDecoder;
import tools.vitruv.extensions.changevisualization.tree.decoder.echange.DeleteEObjectDecoder;
import tools.vitruv.extensions.changevisualization.tree.decoder.echange.InsertEAttributeValueDecoder;
import tools.vitruv.extensions.changevisualization.tree.decoder.echange.InsertEReferenceDecoder;
import tools.vitruv.extensions.changevisualization.tree.decoder.echange.InsertRootEObjectDecoder;
import tools.vitruv.extensions.changevisualization.tree.decoder.echange.RemoveEAttributeValueDecoder;
import tools.vitruv.extensions.changevisualization.tree.decoder.echange.RemoveEReferenceDecoder;
import tools.vitruv.extensions.changevisualization.tree.decoder.echange.RemoveRootEObjectDecoder;
import tools.vitruv.extensions.changevisualization.tree.decoder.echange.ReplaceSingleValuedEAttributeDecoder;
import tools.vitruv.extensions.changevisualization.tree.decoder.echange.ReplaceSingleValuedEReferenceDecoder;
import tools.vitruv.extensions.changevisualization.ui.LabelValuePanel;
import tools.vitruv.extensions.changevisualization.utils.ModelHelper;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange;
import tools.vitruv.framework.change.echange.root.RootEChange;

/**
 * Collects all information regarding EChange-Nodes
 * It is also the place where additional ChangeDecoders can be registered for usage.
 * 
 * @author Andreas Loeffler
 *
 */
public class ChangeNode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5499249335308821465L;
	
	public static enum EChangeClass{
		REFERENCE_ECHANGE,
		EXISTENCE_ECHANGE,
		ATTRIBUTE_ECHANGE,
		ROOT_ECHANGE
	}

	/**
	 * Decoders which extract the information to display from given Object of specific eChanges
	 */
	private static Map<String,ChangeDecoder> decoders=new Hashtable<String,ChangeDecoder>();

	private static boolean simpleEChangeText=false;
	
	public static boolean isSimpleEChangeText() {
		return simpleEChangeText;
	}

	public static void setSimpleEChangeText(boolean selected) {
		simpleEChangeText=selected;
	}


	//register additional decoders
	static {		
		registerChangeDecoder(new ReplaceSingleValuedEAttributeDecoder());	
		registerChangeDecoder(new ReplaceSingleValuedEReferenceDecoder());
		registerChangeDecoder(new CreateEObjectDecoder());
		registerChangeDecoder(new InsertRootEObjectDecoder());
		registerChangeDecoder(new InsertEReferenceDecoder());
		registerChangeDecoder(new InsertEAttributeValueDecoder());
		registerChangeDecoder(new RemoveEAttributeValueDecoder());
		registerChangeDecoder(new DeleteEObjectDecoder());
		registerChangeDecoder(new RemoveRootEObjectDecoder());
		registerChangeDecoder(new RemoveEReferenceDecoder());
	}

	/**
	 * Can be called to register new decoders for given EChange classes
	 * 
	 * @param className The eClass-name to decode
	 * @param dec The decoder used to decode objects of the class
	 */
	public static void registerChangeDecoder(ChangeDecoder dec) {
		decoders.put(dec.getDecodedEClassName(),dec);
	}

	/**
	 * The text to display for the node in the jtree when simpleName is enabled
	 */
	private final String simpleText;
	
	/**
	 * The text to display for the node in the jtree
	 */
	private final String text;
	
	/**
	 * The id of the affected eObject
	 */
	private final String eObjectID;
	
	private final String[][] structuralFeatureLabelValues;
	
	private final EChangeClass changeClass;

	/**
	 * Constructs a Changenode for a given eChange
	 * @param eChange The change to visualize, not null
	 */
	public ChangeNode(EChange eChange) {
		if(decoders.containsKey(eChange.eClass().getName())) {
			//Use the special decoder to derive the name
			text=decoders.get(eChange.eClass().getName()).decode(eChange);
			simpleText=eChange.eClass().getName();
		}else {
			//The default behaviour is just display the classname
			text=eChange.eClass().getName();
			simpleText=eChange.eClass().getName();
		}	
		
		structuralFeatureLabelValues=ModelHelper.extractStructuralFeatureArray(eChange);
				
		//No ReferenceEChange or AttributeEChange class found to check for instanceof
		//so class recognition is done by name so far
		switch(eChange.eClass().getName()){
		case "CreateEObject":
		case "DeleteEObject":
			changeClass=EChangeClass.EXISTENCE_ECHANGE;
			break;
		case "InsertRootEObject":
		case "RemoveRootEObject":
			changeClass=EChangeClass.ROOT_ECHANGE;
			break;
		case "InsertEReference":
		case "ReplaceSingleValuedEReference":
		case "RemoveEReference":
			changeClass=EChangeClass.REFERENCE_ECHANGE;
			break;
		case "InsertEAttributeValue":
		case "ReplaceSingleValuedEAttribute":
		case "RemoveEAttributeValue":
			changeClass=EChangeClass.ATTRIBUTE_ECHANGE;
			break;
		default:
			changeClass=null;//unknown eChange
			break;
		}
		
		//extract eObjectID
		eObjectID=getAffectedEObjectID(eChange);
	
	}
	
	private String getAffectedEObjectID(EChange eChange) {
		String featureName=null;
		switch(eChange.eClass().getName()) {
		case "InsertRootEObject":
			//InsertRootEObject eChanges don't have affectedEObjectIDs, they use newValueID
			featureName="newValueID";
			break;
		case "RemoveRootEObject":
			//InsertRootEObject eChanges don't have affectedEObjectIDs, they use oldValueID
			featureName="oldValueID";
			break;
		default:	
			featureName="affectedEObjectID";
			break;
		}
		for(EStructuralFeature feature:eChange.eClass().getEAllStructuralFeatures()) {
			if(feature.getName().equals(featureName)) {
				return (String) eChange.eGet(feature); 
			}
		}
		return null;
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
		return isSimpleEChangeText()?simpleText:text;
	}

	public EChangeClass getChangeClass() {
		return changeClass;
	}

	public String getEObjectID() {
		return eObjectID;
	}

}
