package edu.kit.ipd.sdq.vitruvius.framework.contracts.change.impl

import java.util.List
import java.util.LinkedList
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.CompositeChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change

class CompositeChangeImpl<C extends Change> implements CompositeChange<C> {
    List<C> changes;

    new() {
        this.changes = new LinkedList<C>();
    }

    new(List<? extends C> changes) {
        this.changes = new LinkedList<C>(changes);
    }

    override List<C> getChanges() {
        return new LinkedList<C>(this.changes);
    }

    override addChange(C change) {
		if (change != null) this.changes.add(change);
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
		if (changes.empty) {
			return null;
		} else {
			return changes.get(0).URI;
		}
	}
	
	override validate() {
		if (!this.containsConcreteChange()) {
			return false;
		}

		var lastURI = changes.get(0).URI;
		for (change : changes) {
			if (change.URI != lastURI) {
				return false;
			}
			lastURI = change.URI;
		}
		return true;
	}
	
}
