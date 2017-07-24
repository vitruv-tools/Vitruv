package tools.vitruv.dsls.commonalities.generator

import org.eclipse.xtext.generator.IGenerator2
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import tools.vitruv.dsls.commonalities.language.elements.Participation
import java.util.HashMap
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.api.generator.ReactionBuilderFactory
import com.google.inject.Inject
import tools.vitruv.dsls.commonalities.language.CommonalityFile
import tools.vitruv.dsls.reactions.api.generator.IReactionsEnvironmentGenerator
import tools.vitruv.dsls.commonalities.language.AttributeDeclaration
import tools.vitruv.dsls.commonalities.language.AttributeMappingSpecifiation
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class CommonalityReactionsGenerator extends CommonalityFileGenerator {

	val participationReactions = new HashMap<Participation, Reaction>
	@Inject ReactionBuilderFactory reactions
	// @Inject IReactionsEnvironmentGenerator reactionsEnvironmentGenerator;

	def private reaction(Participation participation) {
	}

	def private createReaction(Participation participation) {
	}

	def private attributeReactions(AttributeMappingSpecifiation mappingSpecification) {
		mappingSpecification.attributeDeclaration
		reactions.createReactionBuilder().
			setName('''«commonalityFile.concept.name»_«commonalityFile.commonality.name»$«mappingSpecification.attributeDeclaration.name»<->«mappingSpecification.participation.name»''')
			.setTrigger(null)
			.setTargetChange(null)
			.generateReaction()
	}

	override generate() {
	}

}
