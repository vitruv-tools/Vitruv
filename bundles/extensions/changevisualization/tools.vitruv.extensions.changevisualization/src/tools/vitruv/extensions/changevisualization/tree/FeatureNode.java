package tools.vitruv.extensions.changevisualization.tree;

import java.awt.Component;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.extensions.changevisualization.tree.decoder.EObjectFeatureDecoder;
import tools.vitruv.extensions.changevisualization.tree.decoder.FeatureDecoder;
import tools.vitruv.extensions.changevisualization.tree.decoder.ObjectFeatureDecoder;

/**
 * Collects all information used to display a StructuralFeature as a node in a JTree.
 * It is also the place where additional FeatureDecoders can be registered for usage.
 * 
 * @author Andreas Loeffler
 *
 */
public class FeatureNode implements Serializable{	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2466695811442401829L;

	/**
	 * Decoders which extract the information to display from given Object of specific classes
	 */
	private static Map<Class<?>,FeatureDecoder> decoders=new Hashtable<Class<?>,FeatureDecoder>();

	/**
	 * The fallback decoder suitable for java.lang.Object (==all java classes)
	 */
	private static FeatureDecoder objectFallbackDecoder=new ObjectFeatureDecoder();

	//register additional decoders
	static {		
		//EObject for all ECore elements
		registerFeatureDecoder(org.eclipse.emf.ecore.EObject.class,new EObjectFeatureDecoder());	

		//info: i will implement a way to autoload all classes implementing FeatureDecoder which reside in the decoder package
		//until then, they get registered manually
	}

	/**
	 * Can be called to register new decoders for given classes
	 * 
	 * @param cl The class to decode
	 * @param dec The decoder used to decode objects of the class
	 */
	public static void registerFeatureDecoder(Class<?> cl, FeatureDecoder dec) {
		decoders.put(cl,dec);
	}

	/**
	 * The structural feature name
	 */
	private final String featureName;

	/**
	 * Value is the simple and short string used to display as the nodes text
	 */
	private String value;

	/**
	 * If details in form of a (longer) String exist, they are stored here
	 */
	private String details;
	
	/**
	 * If details in the form of a String[][] exist, they are stored here
	 */
	private String[][] detailsArray;

	/**
	 * If details in the form of a Component exist, they are stored here
	 */
	private Component detailsUI;

	/**
	 * Constructs a feature node decoding a given structural feature and its object
	 * @param feature The StrucutralFeature
	 * @param obj The associated Object
	 */
	public FeatureNode(EStructuralFeature feature, Object obj) {
		this(feature.getName(),obj);
	}

	/**
	 * Constructs a feature node decoding a given structural feature name and its object
	 * @param featureName The StrucutralFeature's name
	 * @param obj The associated Object
	 */
	public FeatureNode(String featureName, Object obj) {
		this.featureName=featureName;
		decode(obj);
	}

	/**
	 * Gets the structural feature's name
	 * @return The feature name
	 */
	public String getFeatureName() {
		return featureName;
	}

	/**
	 * The simple String suitable as the text of a tree node
	 * @return The text for the tree node, not null
	 */
	public String getValue() {
		return value;
	}

	/**
	 * The details String, if any. Either a details String exists or a detailsUI. Or none of both.
	 * 
	 * @return The details String, may be null
	 */
	public String getDetails() {
		return details;
	}

	@Override
	public String toString() {	
		//toString() is called by the tree node renderer. This text is displayed in the tree.
		return featureName+" : "+value;
	}

	/**
	 * The detailsUI component, if any. Either a details String exists or a detailsUI. Or none of both.
	 * 
	 * @return The detailsUI component, may be null
	 */
	public Component getDetailsUI() {
		return detailsUI;
	}

	/**
	 * Extracts all necessary information for this feature node from a given object
	 * 
	 * @param obj, should not be null
	 */
	private void decode(Object obj) {
		if(obj==null) {			
			value="Should not happen";
		}else {
			List<Class<?>> candidates=determineCandidates(obj);

			if(candidates.isEmpty()) {
				//If no candidate exists, use objectFallback
				value=objectFallbackDecoder.decodeSimple(obj);
				details=objectFallbackDecoder.decodeDetailed(obj);
				detailsArray=objectFallbackDecoder.decodeDetailedArray(obj);
				detailsUI=objectFallbackDecoder.decodeDetailedUI(obj);
			}else if(candidates.size()==1) {
				//If one candidate exists, use it
				value=decoders.get(candidates.get(0)).decodeSimple(obj);
				details=decoders.get(candidates.get(0)).decodeDetailed(obj);
				detailsArray=decoders.get(candidates.get(0)).decodeDetailedArray(obj);
				detailsUI=decoders.get(candidates.get(0)).decodeDetailedUI(obj);
			}else {
				//if multiple decoders fit, use the one that is most specific
				Class<?> mostSpecificClass=determineMostSpecificClass(candidates,obj.getClass());
				if(mostSpecificClass==null) {
					//This case should not happen, use object as fallback				
					value=objectFallbackDecoder.decodeSimple(obj);
					details=objectFallbackDecoder.decodeDetailed(obj);
					detailsArray=objectFallbackDecoder.decodeDetailedArray(obj);
					detailsUI=objectFallbackDecoder.decodeDetailedUI(obj);
				}else {
					//Use the most specific decoder				
					value=decoders.get(mostSpecificClass).decodeSimple(obj);	
					details=decoders.get(mostSpecificClass).decodeDetailed(obj);
					detailsArray=decoders.get(mostSpecificClass).decodeDetailedArray(obj);
					detailsUI=decoders.get(mostSpecificClass).decodeDetailedUI(obj);
				}				
			}
		}	
	}

	/**
	 * Determine all classes for which decoders exist and the given object is an instance of
	 * 
	 * @param obj The given Object
	 * @return All implemented classes for which decoders exist
	 */
	private List<Class<?>> determineCandidates(Object obj) {
		List<Class<?>> candidates = new Vector<Class<?>>();
		for(Class<?> cl:decoders.keySet()) {
			if(cl.isInstance(obj)) {
				candidates.add(cl);
			}
		}
		return candidates;
	}

	/**
	 * Walks the class hierarchy of refCl and returns the first found occurence of the class or a parent
	 * wihtin candidates
	 * 
	 * @param candidates The candidate classes the given object is an instance of
	 * @param refCl The class of the given object
	 * @return
	 */	 
	private Class<?> determineMostSpecificClass(List<Class<?>> candidates, Class<?> refCl) {
		//All candidate classes must be in the superclass hierarchy of refCl.
		//Since java has no multiple inheritance and all candidates are different classes
		//they also have to be in an ordered hierachy.
		//This is not true for interfaces. If this is an issue here, has to be determined
		java.util.Collections.sort(candidates,new Comparator<Class<?>>() {
			@Override
			public int compare(Class<?> o1, Class<?> o2) {
				if(o1.isInstance(o2)) {
					return 1;
				}else {//The case where none is an instance of the other cannot happen. One must be true
					return -1;		
				}
			}			
		});
		return candidates.get(0);		
	}

	public String[][] getDetailsArray() {
		return detailsArray;
	}

}
