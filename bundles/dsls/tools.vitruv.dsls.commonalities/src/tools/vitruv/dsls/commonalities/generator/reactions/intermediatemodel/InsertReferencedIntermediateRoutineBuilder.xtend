package tools.vitruv.dsls.commonalities.generator.reactions.intermediatemodel

import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.EcorePackage
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsSegmentScopedProvider
import tools.vitruv.dsls.commonalities.language.CommonalityReference
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.EmfAccessExpressions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class InsertReferencedIntermediateRoutineBuilder extends ReactionsGenerationHelper {

	static class Provider extends ReactionsSegmentScopedProvider<InsertReferencedIntermediateRoutineBuilder> {

		protected override createFor(FluentReactionsSegmentBuilder segment) {
			return new InsertReferencedIntermediateRoutineBuilder(segment).injectMembers
		}

		def getInsertReferencedIntermediateRoutine(FluentReactionsSegmentBuilder segment, CommonalityReference reference) {
			return getFor(segment).getInsertReferencedIntermediateRoutine(reference)
		}
	}

	val Map<CommonalityReference, FluentRoutineBuilder> insertReferencedIntermediateRoutines = new HashMap

	private new(FluentReactionsSegmentBuilder segment) {
		checkNotNull(segment, "segment is null")
		// Note: The reactions segment is unused here. But having the provider
		// require it ensures that we only create one instance of this class
		// per reactions segment.
	}

	// Dummy constructor for Guice
	package new() {
		throw new IllegalStateException("Use the Provider to get instances of this class!")
	}

	def getInsertReferencedIntermediateRoutine(CommonalityReference reference) {
		return insertReferencedIntermediateRoutines.computeIfAbsent(reference) [
			val referencedCommonality = reference.referenceType
			val referencingCommonality = reference.containingCommonality
			create.routine('''insertReferencedIntermediate_«reference.reactionName»''')
				.input [
					model(referencedCommonality.changeClass, REFERENCED_INTERMEDIATE)
					model(EcorePackage.eINSTANCE.EObject, REFERENCE_ROOT)
				].match [
					vall(REFERENCING_INTERMEDIATE).retrieveAsserted(referencingCommonality.changeClass)
						.correspondingTo(REFERENCE_ROOT)
				].action [
					update(REFERENCING_INTERMEDIATE) [
						insertFeatureValue(variable(REFERENCING_INTERMEDIATE), reference.correspondingEReference,
							variable(REFERENCED_INTERMEDIATE))
					]
				]
		]
	}
}
