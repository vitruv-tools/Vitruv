package tools.vitruv.framework.change.description

import tools.vitruv.framework.change.description.impl.CompositeContainerChangeImpl
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.description.impl.TransactionalChangeImpl

class VitruviusChangeFactory {
	static VitruviusChangeFactory instance;
	
	private new() {}
	
	static def VitruviusChangeFactory getInstance() {
		if (instance === null) {
			instance = new VitruviusChangeFactory();
		}
		return instance;
	}
	
	def TransactionalChange createTransactionalChange(Iterable<? extends EChange> changes) {
		return new TransactionalChangeImpl(changes);
	}
	
	def CompositeContainerChange createCompositeChange(Iterable<? extends VitruviusChange> innerChanges) {
		new CompositeContainerChangeImpl(innerChanges.toList)
	}
	
}