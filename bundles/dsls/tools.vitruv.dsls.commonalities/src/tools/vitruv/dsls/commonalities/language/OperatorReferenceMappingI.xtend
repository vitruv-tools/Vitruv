package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.impl.OperatorReferenceMappingImpl

package class OperatorReferenceMappingI extends OperatorReferenceMappingImpl {

	override isRead() {
		return super.isRead || super.isReadAndWrite
	}

	override isWrite() {
		return super.isWrite || super.isReadAndWrite
	}
}
