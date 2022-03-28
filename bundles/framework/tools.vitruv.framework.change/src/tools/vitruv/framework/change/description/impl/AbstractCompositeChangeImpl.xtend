package tools.vitruv.framework.change.description.impl

import java.util.List
import tools.vitruv.framework.change.description.CompositeChange
import tools.vitruv.framework.change.description.VitruviusChange
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import java.util.LinkedHashSet
import java.util.Set
import java.util.HashSet
import tools.vitruv.framework.change.MetamodelDescriptor

abstract class AbstractCompositeChangeImpl<C extends VitruviusChange> implements CompositeChange<C> {
	List<C> changes

	new(List<? extends C> changes) {
		this.changes = List.copyOf(changes)
	}

	override List<C> getChanges() {
		return this.changes
	}

	override containsConcreteChange() {
		changes.exists [containsConcreteChange]
	}
	
	override getChangedURIs() {
		changes.flatMapFixedTo(new LinkedHashSet) [changedURIs]
	}
	
	override Set<MetamodelDescriptor> getAffectedEObjectsMetamodelDescriptors() {
		changes.flatMapFixedTo(new HashSet) [affectedEObjectsMetamodelDescriptors]
	}

	override getEChanges() {
		return changes.flatMapFixed [EChanges]
	}
	
	override getAffectedEObjects() {
		changes.flatMapFixedTo(new LinkedHashSet) [affectedEObjects]
	}
	
	override getAffectedAndReferencedEObjects() {
		changes.flatMapFixedTo(new LinkedHashSet) [affectedAndReferencedEObjects]
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
}
