package tools.vitruv.framework.change.description

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI
import java.util.List
import java.util.Set

interface ConcreteChange extends TransactionalChange {
	def EChange getEChange()
	override getEChanges() {
		List.of(EChange)
	}
	
	/**
	 * The VURI of the unique resource that is changed by this change. May be null if the changed resource is 
	 * not determined yet.
	 */
	def VURI getChangedVURI()
	override Set<VURI> getChangedVURIs()
	
	override ConcreteChange copy()
}