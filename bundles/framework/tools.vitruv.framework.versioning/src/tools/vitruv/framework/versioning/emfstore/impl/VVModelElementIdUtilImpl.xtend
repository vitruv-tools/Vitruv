package tools.vitruv.framework.versioning.emfstore.impl

import tools.vitruv.framework.versioning.emfstore.VVModelElementIdUtil
import java.util.Map
import org.eclipse.emf.ecore.EObject
import java.util.UUID
import tools.vitruv.framework.versioning.emfstore.VVModelElementId

class VVModelElementIdUtilImpl implements VVModelElementIdUtil {
	val Map<EObject, VVModelElementId> idMap

	static def VVModelElementIdUtil init() {
		new VVModelElementIdUtilImpl
	}

	private new() {
		idMap = newHashMap
	}

	override getModelElementId(EObject object) {
		if (idMap.containsKey(object))
			return idMap.get(object)
		val id = UUID.randomUUID.toString
		val modelElementId = new VVModelElementIdImpl(id)
		idMap.put(object, modelElementId)
		return modelElementId
	}

}
