package tools.vitruv.framework.change.description.impl

import java.util.ArrayList
import java.util.LinkedList
import java.util.List
import tools.vitruv.framework.change.description.CompositeChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.uuid.UuidResolver

abstract class AbstractCompositeChangeImpl<C extends VitruviusChange> implements CompositeChange<C> {
    List<C> changes;

    new() {
        this.changes = new LinkedList<C>();
    }

    new(List<? extends C> changes) {
        this.changes = new LinkedList<C>(changes);
    }

    override List<C> getChanges() {
        return this.changes;
    }

    override addChange(C change) {
		if (change !== null) this.changes.add(change);
    }
	
	override removeChange(C change) {
		if (change !== null) this.changes.remove(change);
	}
				
	override containsConcreteChange() {
		var containsConcreteChange = false;
		for (change : changes) {
			if (change instanceof CompositeChange<?>) {
				containsConcreteChange = containsConcreteChange || change.containsConcreteChange();
			} else {
				containsConcreteChange = containsConcreteChange || true;
			}
		}
		return containsConcreteChange;
	}
	
	override getURI() {
		val uris = this.changes.map[URI].filterNull
		if (!uris.empty) {
			return uris.get(0);
		} else {
			return null;
		}
	}
	
	override validate() {
		if (!this.containsConcreteChange()) {
			return false;
		}

		var lastURI = changes.get(0).URI;
		for (change : changes) {
			if (lastURI !== null && change.URI !== null && change.URI != lastURI) {
				return false;
			}
			lastURI = change.URI;
		}
		return true;
	}
	
	override getEChanges() {
		return changes.fold(new ArrayList<EChange>(), 
			[eChangeList, change | 
				eChangeList.addAll(change.EChanges);
				return eChangeList;
			]
		);
	}
	
	override applyBackward() throws IllegalStateException {
		for (change : changes.reverseView) {
			change.applyBackward();
		}
	}
	
	override applyForward() throws IllegalStateException {
		for (change : changes.reverseView) {
			change.applyForward();
		}
	}
		
	override resolveBeforeAndApplyForward(UuidResolver uuidResolver) {
		for (c : changes) {
			c.resolveBeforeAndApplyForward(uuidResolver)
		}
	}
	
	override resolveAfterAndApplyBackward(UuidResolver uuidResolver) {
		for (c : changes.reverseView) {
			c.resolveAfterAndApplyBackward(uuidResolver)
		}
	}
	
	override getAffectedEObjects() {
		return changes.fold(newArrayList, [list, element | list += element.affectedEObjects; return list]).filterNull;
	}
	
	override unresolveIfApplicable() {
		changes.forEach[unresolveIfApplicable]
	}
	
}
