package tools.vitruv.framework.vsum.filtered.internal

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.framework.change.echange.id.IdResolver
import tools.vitruv.framework.vsum.filtered.IdCrossResolver
import tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol.ViewSourceCorrespondence

@FinalFieldsConstructor
class IdCrossResolverImpl implements IdCrossResolver {

	val IdResolver filtered
	val IdResolver unfiltered
	val ViewSourceCorrespondence correspondentResources

	override getEObject(String id) {
		if (filtered.hasEObject(id)) {
			if(correspondentResources.getSourceEObject(filtered.getEObject(id)) !== null) {
				return correspondentResources.getSourceEObject(filtered.getEObject(id))
			}
		}
		return unfiltered.getEObject(id)
	}
	
	override notPresentInSource(String id) {
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
		unfiltered.getResource(uri)
	}
	
	override endTransaction() {
		filtered.endTransaction
		unfiltered.endTransaction
	}
	
}
