package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.uuid.UuidResolver
import tools.vitruv.framework.change.interaction.UserInteractionBase
import java.util.List
import java.util.ArrayList
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

class CompositeTransactionalChangeImpl extends AbstractCompositeChangeImpl<TransactionalChange> implements CompositeTransactionalChange {
	List<UserInteractionBase> userInteractions = new ArrayList<UserInteractionBase>
	
	new(List<? extends TransactionalChange> changes) {
		super(changes)
	}
	
	override resolveAndApply(UuidResolver uuidResolver) {
		for (c : changes) {
			c.resolveAndApply(uuidResolver)
		}
	}

	override getUserInteractions() {
		val subInteraction = subUserInteractions;
		if (!subInteraction.empty) {
			if (!userInteractions.empty) {
				throw new IllegalStateException("User interactions were defined for both the container change and the contained changes");
			}
			return subInteraction;
		}
		return userInteractions
	}
	
	private def getSubUserInteractions() {
		return changes.flatMap [userInteractions]	
	}
	
	override setUserInteractions(Iterable<UserInteractionBase> userInputs) {
		if (userInputs === null) {
			throw new IllegalArgumentException("User interactions must not be null");
		}
		if (!subUserInteractions.empty) {
			throw new IllegalStateException("User interactions must only be defined for a container change or its contained changes");
		}
		userInteractions.clear;
		userInteractions += userInputs;
	}
	
	override CompositeTransactionalChangeImpl copy() {
		new CompositeTransactionalChangeImpl(changes.mapFixed [copy()])
	}
}
