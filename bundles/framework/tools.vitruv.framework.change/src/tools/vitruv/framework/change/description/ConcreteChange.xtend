package tools.vitruv.framework.change.description

import tools.vitruv.framework.change.echange.EChange
import java.util.List
import java.util.Set
import org.eclipse.emf.common.util.URI

interface ConcreteChange extends TransactionalChange {
	def EChange getEChange()
	override getEChanges() {
		List.of(EChange)
	}
	
	/**
	 * The URI of the unique resource that is changed by this change. May be null if the changed resource is 
	 * not determined yet.
	 */
	def URI getChangedURI()
	override Set<URI> getChangedURIs()
	
	override ConcreteChange copy()
}