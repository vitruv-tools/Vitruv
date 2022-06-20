package tools.vitruv.extensions.changevisualization.tree.decoder;

import java.util.Hashtable;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.extensions.changevisualization.tree.ChangeNode;
import tools.vitruv.extensions.changevisualization.tree.ChangeNode.EChangeClass;
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
import tools.vitruv.extensions.changevisualization.utils.ModelHelper;
import tools.vitruv.framework.change.echange.EChange;

/**
 * Helper class to generate {@link ChangeNode}s from eChanges. Central place to register new {@link ChangeDecoder} 
 * 
 * @author Andreas Loeffler
 */
public class ChangeNodeDecoder {
		
	/**
	 * Decoders which extract the information to display from given Object of specific eChanges
	 */
	private static Map<String,ChangeDecoder> decoders=new Hashtable<String,ChangeDecoder>();


	//register additional decoders, default instances
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
	 * Constructs a |@link ChangeNode} for a given {@link EChange}
	 * 
	 * @param eChange The change to visualize, not null
	 * @return The changeNode representing the change
	 */
	public static ChangeNode generateChangeNode(EChange eChange) {
		
		String eClassName=eChange.eClass().getName();
	
		String individualText=null;		
		if(decoders.containsKey(eChange.eClass().getName())) {
			//Use the special decoder to derive the full text
			individualText=decoders.get(eChange.eClass().getName()).decode(eChange);			
		}	

		String[][] structuralFeatureLabelValues = ModelHelper.extractStructuralFeatureArray(eChange);

		EChangeClass changeClass=determineEChangeClass(eChange);	
		
		String affectedClass=getAffectedClass(eChange);

		//extract eObjectID
		String eObjectID = getAffectedEObjectID(eChange);

		ChangeNode changeNode = new ChangeNode(individualText, structuralFeatureLabelValues, changeClass, eClassName, affectedClass, eObjectID);
		
		return changeNode;
	}

	/**
	 * Determine the type of eChange for a given instance
	 * 
	 * @param eChange The eChange
	 * @return The type of eChange
	 */
	private static EChangeClass determineEChangeClass(EChange eChange) {
		//No ReferenceEChange or AttributeEChange class found to check for instanceof
		//so class recognition is done by name so far
		switch(eChange.eClass().getName()){
		case "CreateEObject":
		case "DeleteEObject":
			return EChangeClass.EXISTENCE_ECHANGE;
		case "InsertRootEObject":
		case "RemoveRootEObject":
			return EChangeClass.ROOT_ECHANGE;					
		case "InsertEReference":
		case "ReplaceSingleValuedEReference":
		case "RemoveEReference":
			return EChangeClass.REFERENCE_ECHANGE;					
		case "InsertEAttributeValue":
		case "ReplaceSingleValuedEAttribute":
		case "RemoveEAttributeValue":
			return EChangeClass.ATTRIBUTE_ECHANGE;
		default:
			return null;
		}
	}

	/**
	 * Returns the affectedEObjectID of a given echange.
	 * 	
	 * @param eChange The eChange
	 * @return The affectedEObjects id
	 */
	private static String getAffectedEObjectID(EChange eChange) {
		String featureName=null;
		switch(eChange.eClass().getName()) {
		case "InsertRootEObject":
			//InsertRootEObject eChanges don't have affectedEObjectIDs, they use newValueID
			featureName="newValueID";
			break;
		case "RemoveRootEObject":
			//RemoveRootEObject eChanges don't have affectedEObjectIDs, they use oldValueID
			featureName="oldValueID";
			break;
		default:	
			featureName="affectedEObjectID";
			break;
		}		
		return (String)ModelHelper.getStructuralFeatureValue(eChange, featureName);
	}
	
	/**
	 * Returns the classname of the affectedEObject of the given eChange. 
	 * For EObjects, this is the eClass-name, for all other objects the java class name.
	 * 
	 * @param eChange The eChange
	 * @return The classname
	 */
	private static String getAffectedClass(EChange eChange) {		
		String featureName=null;
		switch(eChange.eClass().getName()) {		
		case "InsertRootEObject":			
		case "RemoveRootEObject":
			//Insert- and RemoveRootEObject eChanges don't have affectedEObject, they use resource
			featureName="resource";
			break;
		default:	
			featureName="affectedEObject";
			break;
		}
		
		Object obj=ModelHelper.getStructuralFeatureValue(eChange,featureName);
		
		if(obj==null) {
			//feature not existent or the feature's value is null
			return null;
		}else if(obj instanceof EObject) {
			//for eObjects, use their eclasses name
			return ((EObject)obj).eClass().getName();
		}else {
			//for all others, use the simple java class name
			return obj.getClass().getSimpleName();						
		}
	}
	
	/**
	 * Constructor not used
	 */
	private ChangeNodeDecoder() {
		//not used
	}

}
