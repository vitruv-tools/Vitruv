package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.util.EcoreUtil

import tools.vitruv.framework.change.description.ChangeCloner
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.impl.CompositeContainerChangeImpl
import tools.vitruv.framework.change.description.impl.CompositeTransactionalChangeImpl
import tools.vitruv.framework.change.description.impl.ConcreteApplicableChangeImpl
import tools.vitruv.framework.change.description.impl.ConcreteChangeImpl
import tools.vitruv.framework.change.description.impl.EMFModelChangeImpl
import tools.vitruv.framework.change.description.impl.EmptyChangeImpl
import tools.vitruv.framework.change.description.impl.LegacyEMFModelChangeImpl
import tools.vitruv.framework.change.echange.EChange

class ChangeClonerImpl implements ChangeCloner {
	override cloneVitruviusChange(VitruviusChange vitruviusChange) {
		cloneImpl(vitruviusChange)
	}

	override cloneEChange(EChange change) {
		EcoreUtil::copy(change)
	}

	private def dispatch VitruviusChange cloneImpl(CompositeContainerChangeImpl containerChange) {
		val clone = new CompositeContainerChangeImpl
		containerChange.changes.forEach[clone.addChange(cloneImpl)]
		return clone
	}

	private def dispatch VitruviusChange cloneImpl(CompositeTransactionalChangeImpl transactionalChange) {
		val clone = new CompositeTransactionalChangeImpl
		transactionalChange.changes.forEach[clone.addChange(cloneImpl as TransactionalChange)]
		return clone
	}

	private def dispatch VitruviusChange cloneImpl(ConcreteApplicableChangeImpl applicableChange) {
		return new ConcreteApplicableChangeImpl(applicableChange.EChange.cloneEChange)
	}

	private def dispatch VitruviusChange cloneImpl(ConcreteChangeImpl concreteChange) {
		return new ConcreteChangeImpl(concreteChange.EChange.cloneEChange)
	}

	private def dispatch VitruviusChange cloneImpl(EMFModelChangeImpl modelChange) {
		return new EMFModelChangeImpl(modelChange.EChanges.map[cloneEChange])
	}

	private def dispatch VitruviusChange cloneImpl(
		LegacyEMFModelChangeImpl modelChange
	) throws NoSuchFieldException , IllegalAccessException {
		val clone = new LegacyEMFModelChangeImpl(null, modelChange.EChanges.map[cloneEChange])
		val backwardAppliedField = LegacyEMFModelChangeImpl.getDeclaredField("canBeBackwardsApplied")
		backwardAppliedField.accessible = true
		backwardAppliedField.set(clone, backwardAppliedField.get(modelChange))
		val changeDescriptionField = LegacyEMFModelChangeImpl.getDeclaredField("changeDescription")
		changeDescriptionField.accessible = true
		changeDescriptionField.set(clone, changeDescriptionField.get(modelChange))
		return clone
	}

	private def dispatch VitruviusChange cloneImpl(EmptyChangeImpl emptyChange) {
		new EmptyChangeImpl(emptyChange.URI)
	}
}
