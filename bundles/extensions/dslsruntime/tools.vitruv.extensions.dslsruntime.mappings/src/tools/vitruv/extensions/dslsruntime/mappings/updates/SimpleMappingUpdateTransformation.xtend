package tools.vitruv.extensions.dslsruntime.mappings.updates

class SimpleMappingUpdateTransformation implements MappingUpdateTransformation {
	// just return the same value
	override transformToInterchangeableValue() {
		[it]
	}

	override transformToTargetValue() {
		[it]
	}

}
