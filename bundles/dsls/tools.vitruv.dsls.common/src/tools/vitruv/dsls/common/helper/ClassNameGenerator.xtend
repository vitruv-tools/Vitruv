package tools.vitruv.dsls.common.helper

interface ClassNameGenerator {
	def String getQualifiedName() '''
		«packageName».«simpleName»'''
			
	def String getSimpleName()
	def String getPackageName()
}