package tools.vitruv.extensions.dslsruntime.mappings.updates

interface AbstractMappingUpdateParameter {
	def Object currentValue()

	def void updateValue(Object value)
}
