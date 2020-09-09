package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.impl.SimpleAttributeMappingImpl

package class SimpleAttributeMappingI extends SimpleAttributeMappingImpl {

	override isRead() {
		return super.isRead || super.isReadAndWrite
	}

	override isWrite() {
		return super.isWrite || super.isReadAndWrite
	}
}
