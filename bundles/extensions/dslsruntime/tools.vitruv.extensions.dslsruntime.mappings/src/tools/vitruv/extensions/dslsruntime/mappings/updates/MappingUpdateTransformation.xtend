package tools.vitruv.extensions.dslsruntime.mappings.updates

import java.util.function.Function

interface MappingUpdateTransformation {
	def Function<Object, Object> transformToInterchangeableValue()

	def Function<Object, Object> transformToTargetValue()
}