package tools.vitruv.framework.vsum.filtered.internal

import accesscontrol.OneSidedCorrespondence
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.framework.change.echange.id.IdResolver
import tools.vitruv.framework.vsum.filtered.IdCrossResolver

@FinalFieldsConstructor
class IdCrossResolverImpl implements IdCrossResolver {

	val IdResolver filtered
	val IdResolver unfiltered
	val OneSidedCorrespondence correspondentResources

	override getEObject(String id) {
		if (filtered.hasEObject(id)) {
			if(correspondentResources.getCorrespondentObject(filtered.getEObject(id)) !== null) {
				return correspondentResources.getCorrespondentObject(filtered.getEObject(id))
			}
		}
		return unfiltered.getEObject(id)
	}
	
	override isNew(String id) {
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
