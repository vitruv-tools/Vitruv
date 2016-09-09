package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.framework.change.echange.EChange
import java.util.ArrayList
import tools.vitruv.framework.util.datatypes.VURI
import org.apache.log4j.Logger

abstract class AbstractConcreteChange implements ConcreteChange {
	private static val logger = Logger.getLogger(AbstractConcreteChange);
	
	protected EChange eChange;
	final VURI vuri;
	
	new(VURI vuri) {
		this.vuri = vuri;
	}
	
	override containsConcreteChange() {
		return true;
	}
	
	override validate() {
		return containsConcreteChange() && URI != null;
	}
	
	override getEChanges() {
		return new ArrayList<EChange>(#[eChange]);
	}
	
	override getURI() {
		return vuri;
	}
		
	override getEChange() {
		return eChange;
	}
	
	override applyBackward() {
		logger.warn("The applyBackward method is not implemented for " + this.class.simpleName + " yet.");
	}
	
	override applyForward() {
		logger.warn("The applyForward method is not implemented for " + this.class.simpleName + " yet.");
	}
	
}