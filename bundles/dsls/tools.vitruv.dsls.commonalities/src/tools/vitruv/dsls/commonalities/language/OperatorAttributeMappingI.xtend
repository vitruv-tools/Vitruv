package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.impl.OperatorAttributeMappingImpl

package class OperatorAttributeMappingI extends OperatorAttributeMappingImpl {
	override isRead() {
		return super.isRead || super.isReadAndWrite
	}

	override isWrite() {
		return super.isWrite || super.isReadAndWrite
	}
}
