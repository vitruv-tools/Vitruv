package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.VitruviusChange

abstract class AbstractVitruviusChangeImpl implements VitruviusChange {
	
	override getAffectedResource() {
		if (!validate()) {
			throw new IllegalStateException('''«this» is invalid and does not affect a unique resource.''')
		}
		return affectedEObjects.map[eResource].filterNull.findFirst[it.URI == this.URI.EMFUri]
	}
	
}