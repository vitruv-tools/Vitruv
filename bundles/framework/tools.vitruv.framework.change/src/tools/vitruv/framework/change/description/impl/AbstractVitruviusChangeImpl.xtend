package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.VitruviusChange

abstract class AbstractVitruviusChangeImpl implements VitruviusChange {
	override getChangedResource() {
		if (!validate()) {
			throw new IllegalStateException('''«this» is invalid and does not change a unique resource.''')
		}
		return if (this.URI === null) {
			null
		} else {
			val ourEmfUri = this.URI.EMFUri
			return affectedEObjects.map[eResource].filterNull.findFirst[it.URI == ourEmfUri]
		}
	}
}