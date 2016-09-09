package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.description.ConcreteChange

abstract class AbstractFileChange extends AbstractConcreteChange implements ConcreteChange {
    new(Resource changedFileResource) {
    	super(VURI.getInstance(changedFileResource));
    	this.eChanges += generateEChange(changedFileResource);
    }

	override prepare() {
		// Nothing to do
	}
	
	override isPrepared() {
		return true;
	}
	
	protected def EChange generateEChange(Resource resource);
}
