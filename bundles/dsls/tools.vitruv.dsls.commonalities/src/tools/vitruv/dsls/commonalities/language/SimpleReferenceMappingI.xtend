package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.impl.SimpleReferenceMappingImpl

package class SimpleReferenceMappingI extends SimpleReferenceMappingImpl {

	override isRead() {
		return super.isRead || super.isReadAndWrite
	}

	override isWrite() {
		return super.isWrite || super.isReadAndWrite
	}
}
