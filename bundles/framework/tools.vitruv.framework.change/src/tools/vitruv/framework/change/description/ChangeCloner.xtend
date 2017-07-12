package tools.vitruv.framework.change.description

import tools.vitruv.framework.change.description.impl.CompositeTransactionalChangeImpl
import tools.vitruv.framework.change.description.impl.ConcreteApplicableChangeImpl
import tools.vitruv.framework.change.echange.EChange
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.description.impl.EMFModelChangeImpl
import tools.vitruv.framework.change.description.impl.CompositeContainerChangeImpl
import tools.vitruv.framework.change.description.impl.ConcreteChangeImpl
import tools.vitruv.framework.change.description.impl.EmptyChangeImpl
import tools.vitruv.framework.change.description.impl.LegacyEMFModelChangeImpl

class ChangeCloner {
	def dispatch VitruviusChange clone(CompositeContainerChangeImpl containerChange) {
		val clone = new CompositeContainerChangeImpl();
		containerChange.changes.forEach[clone.addChange(it.clone as VitruviusChange)];
		return clone;
	}
	
	def dispatch VitruviusChange clone(CompositeTransactionalChangeImpl transactionalChange) {
		val clone = new CompositeTransactionalChangeImpl();
		transactionalChange.changes.forEach[clone.addChange(it.clone as TransactionalChange)];
		return clone;
	}
	
	def dispatch VitruviusChange clone(ConcreteApplicableChangeImpl applicableChange) {
		return new ConcreteApplicableChangeImpl(applicableChange.EChange.cloneEChange, applicableChange.URI);
	}
	
	def dispatch VitruviusChange clone(ConcreteChangeImpl concreteChange) {
		return new ConcreteChangeImpl(concreteChange.EChange.cloneEChange, concreteChange.URI);
	}
	
	def dispatch VitruviusChange clone(EMFModelChangeImpl modelChange) {
		return new EMFModelChangeImpl(modelChange.EChanges.map[it.cloneEChange], modelChange.URI);
	}
	
	def dispatch VitruviusChange clone(LegacyEMFModelChangeImpl modelChange) {
		val clone = new LegacyEMFModelChangeImpl(null, modelChange.EChanges.map[it.cloneEChange], modelChange.URI);
		val backwardAppliedField = LegacyEMFModelChangeImpl.getDeclaredField("canBeBackwardsApplied");
		backwardAppliedField.accessible = true;
		backwardAppliedField.set(clone, backwardAppliedField.get(modelChange));
		val changeDescriptionField = LegacyEMFModelChangeImpl.getDeclaredField("changeDescription");
		changeDescriptionField.accessible = true;
		changeDescriptionField.set(clone, changeDescriptionField.get(modelChange));
		return clone;
	}
	
	def dispatch VitruviusChange clone(EmptyChangeImpl emptyChange) {
		return new EmptyChangeImpl(emptyChange.URI);
	}
		
	def EChange cloneEChange(EChange change) {
		return EcoreUtil.copy(change);
	}
}