package tools.vitruv.framework.change.description

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI
import java.util.List

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
	/**
	 * @return a list containing only
	 */
	override Iterable<VURI> getChangedVURIs()
	
	override ConcreteChange copy()
}