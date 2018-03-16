/**
 * 
 */
package tools.vitruv.extensions.changevisualization.tree.decoder;

import java.awt.Component;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.extensions.changevisualization.tree.ChangeNode;
import tools.vitruv.extensions.changevisualization.tree.FeatureNode;
import tools.vitruv.extensions.changevisualization.tree.decoder.echange.ChangeDecoder;
import tools.vitruv.extensions.changevisualization.tree.decoder.feature.EObjectFeatureDecoder;
import tools.vitruv.extensions.changevisualization.tree.decoder.feature.FeatureDecoder;
import tools.vitruv.extensions.changevisualization.tree.decoder.feature.ObjectFeatureDecoder;
import tools.vitruv.framework.change.echange.EChange;

/**
 * Helper class to generate {@link FeatureNode}s from eChanges. Central place to register new {@link FeatureDecoder} 
 * 
 * @author Andreas Loeffler
 */
public class FeatureNodeDecoder {
	/**
	 * Decoders which extract the information to display from given Object of specific classes
	 */
	private static Map<Class<?>,FeatureDecoder> decoders=new Hashtable<Class<?>,FeatureDecoder>();

	/**
	 * The fallback decoder suitable for java.lang.Object (==all java classes)
	 */
	private static FeatureDecoder objectFallbackDecoder=new ObjectFeatureDecoder();

	//register additional decoders, default instances
	static {		
		//EObject for all ECore elements
		registerFeatureDecoder(org.eclipse.emf.ecore.EObject.class,new EObjectFeatureDecoder());	
	}

	/**
	 * Can be called to register new decoders for given classes.
	 * 
	 * The actual implementation of {@link FeatureNodeDecoder} has not been tested to work with interfaces. 
	 * 
	 * @param cl The class to decode (no interfaces)
	 * @param dec The decoder used to decode objects of the class
	 */
	public static void registerFeatureDecoder(Class<?> cl, FeatureDecoder dec) {
		if(cl.isInterface()) {
			throw new RuntimeException("please do not use interfaces or adapt "+
					"FeatureNodeDecoder.determineMostSpecificClass() and this method");
		}
		decoders.put(cl,dec);
	}
	
	/**
	 * Constructs a |@link FeatureNode} for a given {@link EStructuralFeature}
	 * 
	 * @param feature The structural feature, not null
	 * @param obj The value of the feature, not null
	 * @return The featureNode representing the feature	  
	 */
	public static FeatureNode generateFeatureNode(EStructuralFeature feature, Object obj) {
		String value=null;
		String details=null;
		String[][] detailsArray=null;
		Component detailsUI=null;
		String featureName=feature.getName();
		
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
		
		return new FeatureNode(featureName, value, details, detailsArray, detailsUI);
	}

	/**
	 * Determine all classes for which decoders exist and the given object is an instance of
	 * 
	 * @param obj The given Object
	 * @return All implemented classes for which decoders exist
	 */
	private static List<Class<?>> determineCandidates(Object obj) {
		List<Class<?>> candidates = new Vector<Class<?>>();
		for(Class<?> cl:decoders.keySet()) {
			if(cl.isInstance(obj)) {
				candidates.add(cl);
			}
		}
		return candidates;
	}

	/**
	 * Walks the class hierarchy of refCl and returns the first found occurrence of the class or a parent
	 * within candidates
	 * 
	 * @param candidates The candidate classes the given object is an instance of
	 * @param refCl The class of the given object
	 * @return The most specific class
	 */	 
	private static Class<?> determineMostSpecificClass(List<Class<?>> candidates, Class<?> refCl) {
		//All candidate classes must be in the superclass hierarchy of refCl.
		//Since java has no multiple inheritance and all candidates are different classes
		//they also have to be in an ordered hierarchy.
		//Update : This is not true for interfaces. Yet no interface decoders are used, this may become an issue in the future
		//         but may not be solvable. One cannot decide which interface to prefer if multiple interfaces are implemented. 
		java.util.Collections.sort(candidates,new Comparator<Class<?>>() {
			@Override
			public int compare(Class<?> o1, Class<?> o2) {				
				if(o1.isInstance(o2)) {
					return 1;
				}else if(o2.isInstance(o1)){
					return -1;		
				}else {
					return 0;
				}
			}			
		});
		return candidates.get(0);		
	}

	
}
