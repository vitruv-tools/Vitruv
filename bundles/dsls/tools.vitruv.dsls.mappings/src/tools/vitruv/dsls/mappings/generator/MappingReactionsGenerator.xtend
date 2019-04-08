package tools.vitruv.dsls.mappings.generator

import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsSegment
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference

class MappingReactionsGenerator extends MappingsReactionsFileGenerator {
	val Mapping mapping
	
	new(String basePackage, MappingsSegment segment, boolean left2right, FluentReactionsLanguageBuilder create, Mapping mapping) {
		super(basePackage, segment, left2right, create, null)
		this.mapping = mapping
	}
	
	def generateReactionsAndRoutines(ReactionGeneratorContext context) {
		segment.mappings.forEach[
			val	from = getFromParameters(it).get(0)
			val	to = getToParameters(it).get(0)
			context.segment += onDelete(from,to)				
		]
	//	deleteRoutines()
	}
	
	
	private def onDelete(NamedMetaclassReference from, NamedMetaclassReference to){
		create.reaction('''on«from.name»Delete''')
			.afterElement(from.eClass).deleted
			.call [
				match [
						vall('''corresponding_«to.name»''').retrieveAsserted(to.eClass)
							.correspondingTo.affectedEObject
							.taggedWith(getCorrespondenceTag(from,to))
				]
				action [
						delete('''corresponding_«to.name»''')
				]
			]
	}
	
	private def getCorrespondenceTag(NamedMetaclassReference from, NamedMetaclassReference to){
		var left = from.name
		var right = to.name
		if(!left2right){
			left = to.name
			right = from.name
		}
		'''«left»/«right»'''.toString
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