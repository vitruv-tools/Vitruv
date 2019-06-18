package tools.vitruv.dsls.mappings.generator

import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsSegment
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import org.eclipse.emf.common.util.EList
import java.util.ArrayList

class MappingReactionsGenerator extends MappingsReactionsFileGenerator {
	val Mapping mapping

	new(String basePackage, MappingsSegment segment, boolean left2right, IReactionsGenerator generator,
		FluentReactionsLanguageBuilder create, Mapping mapping) {
		super(basePackage, segment, left2right, generator, create, null)
		this.mapping = mapping
	}

	def generateReactionsAndRoutines(ReactionGeneratorContext context) {
		segment.mappings.forEach [	
			val from = fromParameters
			val to = toParameters
			val fromConditions = fromConditions
			val mappingsConditions = it.bidirectionalizableConditions
			val reactionsBuilder = new ReactionsBuilder(from, to)
			reactionsBuilder.generate(context, fromConditions, mappingsConditions)
		//	context.getSegmentBuilder += onDelete(from, to)
		//	context.getSegmentBuilder += onCreate(from, to)	
		]
	}

	private def onDelete(NamedMetaclassReference from, NamedMetaclassReference to) {		
		create.reaction('''«from.name»Delete''').afterElement(from.metaclass).deleted.call [
			match [
				vall('''corresponding_«to.name»''').retrieveAsserted(to.metaclass).correspondingTo.affectedEObject.
					taggedWith(getCorrespondenceTag(from, to))
			]
			action [
				delete('''corresponding_«to.name»''')
			]
		]
	}
	
	private def onCreate(NamedMetaclassReference from, NamedMetaclassReference to) {		
		create.reaction('''«from.name»Create''').afterElement(from.metaclass).created.call [
			action [
				vall('''corresponding_«to.name»''').create(to.metaclass)
				addCorrespondenceBetween('''corresponding_«to.name»''').and.affectedEObject.taggedWith(getCorrespondenceTag(from, to))
			]
		]
	}

	private def getCorrespondenceTag(NamedMetaclassReference from, NamedMetaclassReference to) {
		var left = from.metaclass.name +':'+from.name
		var right = to.metaclass.name +':'+to.name
		if (!left2right) {
			left = to.name
			right = from.name
		}
		'''«left»/«right»'''.toString
	}

	private def reactionToAnyChange() {
		create.reaction("AnyChange").afterAnyChange.call("ensureAllMappings", [
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

}
