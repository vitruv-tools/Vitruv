package tools.vitruv.extensions.changevisualization.tree;

import java.awt.Component;
import java.util.Hashtable;

import tools.vitruv.extensions.changevisualization.tree.decoder.ChangeDecoder;
import tools.vitruv.extensions.changevisualization.tree.decoder.ReplaceSingleValuedEAttributeDecoder;
import tools.vitruv.framework.change.echange.EChange;

/**
 * Collects all information regarding EChange-Nodes
 * It is also the place where additional ChangeDecoders can be registered for usage.
 * 
 * @author Andreas Loeffler
 *
 */
public class ChangeNode {

	/**
	 * Decoders which extract the information to display from given Object of specific eChanges
	 */
	private static Hashtable<String,ChangeDecoder> decoders=new Hashtable<String,ChangeDecoder>();

	//register additional decoders
	static {		
		//ReplaceSingleValuedEAttributeDecoder
		registerChangeDecoder("ReplaceSingleValuedEAttribute",new ReplaceSingleValuedEAttributeDecoder());	

		//info: i will implement a way to autoload all classes implementing ChangeDecoder which reside in the decoder package
		//until then, they get registered manually
	}

	/**
	 * Can be called to register new decoders for given EChange classes
	 * 
	 * @param className The eClass-name to decode
	 * @param dec The decoder used to decode objects of the class
	 */
	public static void registerChangeDecoder(String className, ChangeDecoder dec) {
		decoders.put(className,dec);
	}

	/**
	 * The name is the text to display for the node in the jtree
	 */
	private final String name;

	/**
	 * The details ui for this eChange
	 */
	private final EObjectStructuralFeaturePanel detailsUI;

	/**
	 * Constructs a Changenode for a given eChange
	 * @param eChange The change to visualize, not null
	 */
	public ChangeNode(EChange eChange) {
		if(decoders.containsKey(eChange.eClass().getName())) {
			//Use the special decoder to derive the name
			name=decoders.get(eChange.eClass().getName()).decode(eChange);
		}else {
			//The default behaviour is just display the classname
			name=eChange.eClass().getName();
		}

		//Create an default details ui for all eChanges
		detailsUI=new EObjectStructuralFeaturePanel(eChange);
	}

	/**
	 * The detailsUI component
	 * 
	 * @return The detailsUI component
	 */
	public Component getDetailsUI() {
		return detailsUI;
	}

	@Override
	public String toString() {
		//The tree renderer uses the toString() method to get the text to display
		return name;
	}

}
