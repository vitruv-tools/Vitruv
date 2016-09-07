package tools.vitruvius.framework.change.description.impl

import java.util.List
import java.util.LinkedList
import tools.vitruvius.framework.change.description.GenericCompositeChange
import tools.vitruvius.framework.change.echange.EChange
import java.util.ArrayList
import tools.vitruvius.framework.change.description.VitruviusChange
import tools.vitruvius.framework.change.preparation.ChangePreparing

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
