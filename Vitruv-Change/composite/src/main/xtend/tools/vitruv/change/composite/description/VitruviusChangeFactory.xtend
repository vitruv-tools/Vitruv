package tools.vitruv.change.composite.description

import tools.vitruv.change.composite.description.impl.CompositeContainerChangeImpl
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.composite.description.impl.TransactionalChangeImpl

class VitruviusChangeFactory {
	static VitruviusChangeFactory instance;
	
	private new() {}
	
	static def VitruviusChangeFactory getInstance() {
		if (instance === null) {
			instance = new VitruviusChangeFactory();
		}
		return instance;
	}
	
	 def <Element> TransactionalChange<Element> createTransactionalChange(Iterable<? extends EChange<Element>> changes) {
		return new TransactionalChangeImpl(changes);
	}
	
	def <Element> CompositeContainerChange<Element> createCompositeChange(Iterable<? extends VitruviusChange<Element>> innerChanges) {
		new CompositeContainerChangeImpl(innerChanges.toList)
	}
	
}