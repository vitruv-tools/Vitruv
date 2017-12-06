package tools.vitruv.dsls.mappings.generator

import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsSegment
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder

class MappingReactionsGenerator extends MappingsReactionsFileGenerator {
	val Mapping mapping
	
	new(String basePackage, MappingsSegment segment, boolean left2right, FluentReactionsLanguageBuilder create, Mapping mapping) {
		super(basePackage, segment, left2right, create)
		this.mapping = mapping
	}
	
	def void generateReactionsAndRoutines() {
		reactionToAnyChange()
		deleteRoutines()
	}
	
	private def reactionToAnyChange() {
		create.reaction("AnyChange")
			.afterAnyChange
			.call("ensureAllMappings", [
				action[
//					mappings.forEach[
//						call("ensure" + mapping.genName, [
//							action[
//								val routineNameSuffix = mapping.genName + "Instances"
//								call("delete" + routineNameSuffix, [
//									action[
//										call[
////											val invalidatedInstances = adRootXReRootMapping.removeInvalidatedLeftInstances()
////											for (invalidatedInstance : invalidatedInstances) {
////												deleteAdRootXReRootMappingElements(invalidatedInstance.ARoot)
////											}
//										]
//									]
//								])
//								call("create" + routineNameSuffix, [
////									val newInstances = adRootXReRootMapping.promoteValidatedLeftCandidatesToInstances()
////									for (newInstance : newInstances) {
////										createAdRootXReRootMappingInstance(newInstance.ARoot)
////									}
//								])
//								call("update" + routineNameSuffix, [
////									val instances = adRootXReRootMapping.getLeftInstances()
////									for (instance : instances) {
////										updateAdRootXReRootMappingInstance(instance.ARoot)
////									}
//								])
//							]
//						])
//					]
				]
			])
	}
	
	private def String getGenName(Mapping mapping) '''«mapping.name»Mapping'''
	
	private def String getDeleteRoutineName(Mapping mapping) '''delete«mapping.genName»Elements'''
	
	private def deleteRoutines() {
		create.routine(mapping.deleteRoutineName)
			.match[

			]
	}
}