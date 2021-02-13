package tools.vitruv.framework.change.description.impl

import java.util.LinkedList
import java.util.List
import tools.vitruv.framework.change.description.CompositeChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.uuid.UuidResolver
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import java.util.LinkedHashSet
import java.util.Collection

abstract class AbstractCompositeChangeImpl<C extends VitruviusChange> implements CompositeChange<C> {
	List<C> changes

	new(Collection<? extends C> changes) {
		this.changes = List.copyOf(changes)
	}

	override List<C> getChanges() {
		return this.changes
	}

	override containsConcreteChange() {
		changes.exists [containsConcreteChange]
	}
	
	override getChangedVURIs() {
		changes.flatMapFixedTo(new LinkedHashSet) [changedVURIs]
	}

	override getEChanges() {
		return changes.flatMapFixed [EChanges]
	}
	
	override resolveBeforeAndApplyForward(UuidResolver uuidResolver) {
		changes.forEach [resolveBeforeAndApplyForward(uuidResolver)]
	}

	override resolveAfterAndApplyBackward(UuidResolver uuidResolver) {
		changes.reverseView.forEach [resolveAfterAndApplyBackward(uuidResolver)]
	}
	
	override getAffectedEObjects() {
		changes.flatMapFixedTo(new LinkedHashSet) [affectedEObjects]
	}
	
	override getAffectedEObjectIds() {
		changes.flatMapFixedTo(new LinkedHashSet) [affectedEObjectIds]
	}

	override getAffectedAndReferencedEObjects() {
		changes.flatMapFixedTo(new LinkedHashSet) [affectedAndReferencedEObjects]
	}

	override getAffectedAndReferencedEObjectIds() {
		changes.flatMapFixedTo(new LinkedHashSet) [affectedAndReferencedEObjectIds]
	}

	override unresolveIfApplicable() {
		changes.forEach [unresolveIfApplicable]
	}

	override getUserInteractions() {
		return changes.flatMap [userInteractions]
	}

	override toString() {
		if (changes.isEmpty) '''«class.simpleName» (empty)'''
		else '''
			«class.simpleName»: [
				«FOR change : changes»
					«change»
				«ENDFOR»
			]
			'''
	}

	/**
	 * Indicates whether some other object is "equal to" this composite change.
	 * This means it is a composite change which contains the same changes as this one in no particular order.
	 * @param other is the object to compare with.
	 * @return true, if the object is a composite change and has the same changes in any order.
	 */
	override equals(Object other) {
		if (other === this) true
		else if (other === null) false
		else if (other instanceof CompositeChange<?>) {
			changes == other.changes
		}
		else false
	}
	
	override hashCode() {
		changes.hashCode()
	}

	override boolean changedEObjectEquals(VitruviusChange change) {
		if (change instanceof CompositeChange<?>) {
			if (changes.size != change.changes.size) {
			 	false
			} else {
				val remainingChanges = new LinkedList(change.changes)
				remainingChanges.removeIf [theirChange|changes.exists [ourChange|theirChange.changedEObjectEquals(ourChange)]]
				remainingChanges.isEmpty
			}
		}
		else false
	}
}
