package tools.vitruv.framework.vsum.filtered

import accesscontrol.OneSidedCorrespondence
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.framework.change.echange.id.IdResolver
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.URI

@FinalFieldsConstructor
class IdCrossResolverImpl implements IdResolver {

	final IdResolver filtered
	final IdResolver unfiltered
	final OneSidedCorrespondence correspondentResources

	override getEObject(String id) {
		if (filtered.hasEObject(id)) {
			if(correspondentResources.getCorrespondentObject(filtered.getEObject(id)) !== null) {
				return correspondentResources.getCorrespondentObject(filtered.getEObject(id))
			}
		}
		return unfiltered.getEObject(id)
	}
	
	def boolean isNew(String id) {
		return !filtered.hasEObject(id)
	}
	
	override getAndUpdateId(EObject object) {
		filtered.getAndUpdateId(object)
		return unfiltered.getAndUpdateId(object)
	}
	
	

	override hasEObject(String id) {
		return filtered.hasEObject(id) || unfiltered.hasEObject(id)
	}
	
	override getResource(URI uri) {
		// TODO is this appropriate? unfiltered contains at least as much as filtered, so probably yes
		unfiltered.getResource(uri)
	}
	
	override endTransaction() {
		filtered.endTransaction
		unfiltered.endTransaction
	}
	
}
