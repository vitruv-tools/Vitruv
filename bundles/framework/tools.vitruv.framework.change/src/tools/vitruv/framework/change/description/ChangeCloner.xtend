package tools.vitruv.framework.change.description

import tools.vitruv.framework.change.description.impl.CompositeTransactionalChangeImpl
import tools.vitruv.framework.change.description.impl.ConcreteApplicableChangeImpl
import tools.vitruv.framework.change.echange.EChange
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.description.impl.CompositeContainerChangeImpl
import tools.vitruv.framework.change.description.impl.ConcreteChangeImpl
import tools.vitruv.framework.change.description.impl.EmptyChangeImpl

class ChangeCloner {
	def dispatch VitruviusChange clone(VitruviusChange change) {
		throw new IllegalArgumentException('''«change» has unsupported change type''')
	}
	
	def dispatch VitruviusChange clone(CompositeContainerChangeImpl containerChange) {
		val clone = new CompositeContainerChangeImpl();
		containerChange.changes.forEach[clone.addChange(it.clone)];
		return clone;
	}
	
	def dispatch VitruviusChange clone(CompositeTransactionalChangeImpl transactionalChange) {
		val clone = new CompositeTransactionalChangeImpl();
		transactionalChange.changes.forEach[clone.addChange(it.clone as TransactionalChange)];
		return clone;
	}
	
	def dispatch VitruviusChange clone(ConcreteApplicableChangeImpl applicableChange) {
		return new ConcreteApplicableChangeImpl(applicableChange.EChange.cloneEChange);
	}
	
	def dispatch VitruviusChange clone(ConcreteChangeImpl concreteChange) {
		return new ConcreteChangeImpl(concreteChange.EChange.cloneEChange);
	}
	
	def dispatch VitruviusChange clone(EmptyChangeImpl emptyChange) {
		return new EmptyChangeImpl(emptyChange.URI);
	}
		
	def EChange cloneEChange(EChange change) {
		return EcoreUtil.copy(change);
	}
}