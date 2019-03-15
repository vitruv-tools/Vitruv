package tools.vitruv.framework.change.description.impl

import java.util.ArrayList
import java.util.LinkedList
import java.util.List
import tools.vitruv.framework.change.description.CompositeChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.uuid.UuidResolver

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
		for (change : changes) {
			if (change.containsConcreteChange) {
				return true
			}
		}
		return false
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
		return changes.fold(
			new ArrayList<EChange>(),
			[ eChangeList, change |
				eChangeList.addAll(change.EChanges);
				return eChangeList;
			]
		);
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
		return changes.fold(newArrayList, [list, element|list += element.affectedEObjects; return list]).filterNull;
	}

	override getAffectedEObjectIds() {
		return changes.fold(newArrayList, [list, element|list += element.affectedEObjectIds; return list]).filterNull;
	}

	override unresolveIfApplicable() {
		changes.forEach[unresolveIfApplicable]
	}

	override getUserInteractions() {
		return changes.map[userInteractions].flatten
	}

	override toString() '''
		«this.class.simpleName», VURI: «URI»
			«FOR change : changes»
				«change»
			«ENDFOR»
	'''

	/**
	 * Indicates whether some other object is "equal to" this composite change.
	 * This means it is a composite change which contains the same changes as this one in no particular order.
	 * @param other is the object to compare with.
	 * @return true, if the object is a composite change and has the same changes in any order.
	 */
	override equals(Object other) {
		return other.isEqual // delegates to dynamic dispatch
	}

	private def dispatch boolean isEqual(Object object) { super.equals(object) }

	private def dispatch boolean isEqual(CompositeChange<C> compositeChange) {
		if (changes.size != compositeChange.changes.size) {
			return false
		}
		val remainingChanges = new LinkedList(compositeChange.changes)
		for (ownChange : changes) {
			if (remainingChanges.contains(ownChange)) {
				remainingChanges.remove(ownChange)
			}
		}
		return remainingChanges.empty
	}

	override boolean changedEObjectEquals(VitruviusChange change) {
		return change.isChangedEObjectEqual
	}

	private def dispatch boolean isChangedEObjectEqual(VitruviusChange change) { super.equals(change) } // use super implementation if anything else than ConcreteApplicableChangeImpl

	private def dispatch boolean isChangedEObjectEqual(CompositeChange<C> compositeChange) {
		if (changes.size != compositeChange.changes.size) {
			return false
		}
		val remainingChanges = new LinkedList(compositeChange.changes)
		for (ownChange : changes) { // TODO TS remove code dupliction
			for (otherChange : compositeChange.changes) {
				if (ownChange.changedEObjectEquals(otherChange)) {
					remainingChanges.remove(ownChange)
				}
			}
		}
		return remainingChanges.empty
	}
}
