package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.uuid.UuidResolver
import java.util.Collection
import tools.vitruv.framework.change.interaction.UserInteractionBase

class CompositeTransactionalChangeImpl extends AbstractCompositeChangeImpl<TransactionalChange> implements CompositeTransactionalChange {
	private Collection<UserInteractionBase> userInteractions
	
	override removeChange(TransactionalChange change) {
		if (change !== null && this.changes.contains(change)) {
			if (changes.size == 1) {
				val emptyChange = VitruviusChangeFactory.instance.createEmptyChange(change.URI);
				this.changes += emptyChange;	
			}
		}
		super.removeChange(change);
	}
	
	override resolveBeforeAndApplyForward(UuidResolver uuidResolver) {
		for (c : changes) {
			c.resolveBeforeAndApplyForward(uuidResolver)
		}
	}
	
	override getUserInteractions() {
        return userInteractions
    }
    
    override setUserInteractions(Collection<UserInteractionBase> userInteractions) {
        this.userInteractions = userInteractions
    }
}