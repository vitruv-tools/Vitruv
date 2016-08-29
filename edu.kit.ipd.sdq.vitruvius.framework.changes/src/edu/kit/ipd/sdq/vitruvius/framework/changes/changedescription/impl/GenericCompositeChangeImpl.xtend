package edu.kit.ipd.sdq.vitruvius.framework.changes.changedescription.impl

import java.util.List
import java.util.LinkedList
import edu.kit.ipd.sdq.vitruvius.framework.changes.changedescription.GenericCompositeChange
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.EChange
import java.util.ArrayList
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePreparing
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange

class GenericCompositeChangeImpl<C extends VitruviusChange> implements GenericCompositeChange<C> {
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
			if (change instanceof GenericCompositeChange<?>) {
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
	
	override isPrepared() {
		return changes.fold(true, [prepared, change | prepared && change.isPrepared]);
	}
	
	override getEChanges() {
		return changes.fold(new ArrayList<EChange>(), 
			[eChangeList, change | 
				eChangeList.addAll(change.EChanges);
				return eChangeList;
			]
		);
	}
	
	override prepare(ChangePreparing preparer) {
		for (change : changes) {
			change.prepare(preparer);
		}
	}
	
}
