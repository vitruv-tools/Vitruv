/**
 * 
 */
package tools.vitruv.extensions.changevisualization.tree;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.extensions.changevisualization.tree.decoder.EObjectFeatureDecoder;
import tools.vitruv.extensions.changevisualization.tree.decoder.FeatureDecoder;
import tools.vitruv.extensions.changevisualization.tree.decoder.ObjectFeatureDecoder;

/**
 * @author andreas
 *
 */
public class FeatureNode {	

	private static Hashtable<Class,FeatureDecoder> decoders=new Hashtable<Class,FeatureDecoder>();
	
	private static FeatureDecoder objectFallbackDecoder=new ObjectFeatureDecoder();

	//register additional decoders
	static {		
		//EObject for all ECore elements
		registerFeatureDecoder(org.eclipse.emf.ecore.EObject.class,new EObjectFeatureDecoder());	
		
		//info: i will implement a way to autoload all classes implementing FeatureDecoder which reside in the decoder package
		//until then, they get registered manually
	}

	public static void registerFeatureDecoder(Class cl, FeatureDecoder dec) {
		decoders.put(cl,dec);
	}

	private final String featureName;
	private String value;
	private String details;
	private Component detailsUI;
	
	public FeatureNode(EStructuralFeature feature, Object obj) {
		featureName=feature.getName();
		decode(obj);
	}
	
	public FeatureNode(String featureName, Object obj) {
		this.featureName=featureName;
		decode(obj);
	}
	
	public String getFeatureName() {
		return featureName;
	}

	public String getValue() {
		return value;
	}

	public String getDetails() {
		return details;
	}
	
	@Override
	public String toString() {		
		return featureName+" : "+value;
	}

	public Component getDetailsUI() {
		return detailsUI;
	}

	private void decode(Object obj) {
		if(obj==null) {			
			value="Should not happen";
		}else {
			Vector<Class> candidates=determineCandidates(obj);
			
			if(candidates.isEmpty()) {
				//If no candidate exists, use objectFallback
				value=objectFallbackDecoder.decodeSimple(obj);
				details=objectFallbackDecoder.decodeDetailed(obj);
				detailsUI=objectFallbackDecoder.decodeDetailedUI(obj);
			}else if(candidates.size()==1) {
				//If one candidate exists, use it
				value=decoders.get(candidates.firstElement()).decodeSimple(obj);
				details=decoders.get(candidates.firstElement()).decodeDetailed(obj);
				detailsUI=decoders.get(candidates.firstElement()).decodeDetailedUI(obj);
			}else {
				//if multiple decoders fit, use the one that is most specific
				Class mostSpecificClass=determineMostSpecificClass(candidates,obj.getClass());
				if(mostSpecificClass==null) {
					//This case should not happen, use object as fallback				
					value=objectFallbackDecoder.decodeSimple(obj);
					details=objectFallbackDecoder.decodeDetailed(obj);
					detailsUI=objectFallbackDecoder.decodeDetailedUI(obj);
				}else {
					//Use the most specific decoder				
					value=decoders.get(mostSpecificClass).decodeSimple(obj);	
					details=decoders.get(mostSpecificClass).decodeDetailed(obj);
					detailsUI=decoders.get(mostSpecificClass).decodeDetailedUI(obj);
				}				
			}
		}	
	}

	private Vector<Class> determineCandidates(Object obj) {
		Vector<Class> candidates = new Vector<Class>();
		for(Class cl:decoders.keySet()) {
			if(cl.isInstance(obj)) {
				candidates.add(cl);
			}
		}
		return candidates;
	}

	/*
	 * Walks the class hierarchy of refCl and returns the first found occurence of the class or a parent
	 * wihtin candidates
	 */
	private Class determineMostSpecificClass(Vector<Class> candidates, Class<? extends Object> refCl) {
		//All candidate classes must be in the superclass hierarchy of refCl.
		//Since java has no multiple inheritance and all candidates are different classes
		//they also have to be in an ordered hierachy.
		//This is not true for interfaces, if this is an issue here has to be determined
		java.util.Collections.sort(candidates,new Comparator<Class>() {
			@Override
			public int compare(Class o1, Class o2) {
				if(o1.isInstance(o2)) {
					return 1;
				}else {//The case where none is an instance of the other cannot happen.One must be true
					return -1;		
				}
			}			
		});
		return candidates.firstElement();		
	}
	
}
