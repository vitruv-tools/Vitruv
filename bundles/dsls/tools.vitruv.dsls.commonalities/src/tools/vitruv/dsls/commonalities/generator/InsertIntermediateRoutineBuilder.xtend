package tools.vitruv.dsls.commonalities.generator

import java.util.HashMap
import java.util.Map
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.Concept
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.IntermediateModelManagement

import static com.google.common.base.Preconditions.*
import static tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static tools.vitruv.dsls.commonalities.generator.ReactionsHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class InsertIntermediateRoutineBuilder extends ReactionsGenerationHelper {

	@GenerationScoped
	static class Provider extends ReactionsSegmentScopedProvider<InsertIntermediateRoutineBuilder> {

		protected override createFor(FluentReactionsSegmentBuilder segment) {
			return new InsertIntermediateRoutineBuilder(segment).injectMembers
		}

		def getInsertIntermediateRoutine(FluentReactionsSegmentBuilder segment, Commonality commonality) {
			return getFor(segment).getInsertIntermediateRoutine(commonality)
		}
	}

	// One routine per intermediate model (concept) is sufficient (keyed by concept name):
	val Map<String, FluentRoutineBuilder> insertIntermediateRoutines = new HashMap

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

	def getInsertIntermediateRoutine(Commonality commonality) {
		val concept = commonality.concept
		return insertIntermediateRoutines.computeIfAbsent(concept.name) [
			create.routine('''insertIntermediate_«concept.name»''')
				.input [model(commonality.changeClass, INTERMEDIATE)]
				.action [
					execute [insertIntermediate(concept, variable(INTERMEDIATE))]
				]
		]
	}

	def private insertIntermediate(extension TypeProvider typeProvider, Concept concept, XFeatureCall intermediate) {
		val intermediateModelURIVariable = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
			name = 'intermediateModelURI'
			right = callGetMetadataModelURI(typeProvider, concept)
		]
		val intermediateModelResourceVariable = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
			name = 'intermediateModelResource'
			right = callGetModelResource(typeProvider, intermediateModelURIVariable.featureCall)
		]
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions += expressions(
				intermediateModelURIVariable,
				intermediateModelResourceVariable,
				intermediateModelResourceVariable.featureCall.memberFeatureCall => [
					feature = typeProvider.findMethod(IntermediateModelManagement, 'addIntermediate')
						.staticExtensionWildcardImported
					memberCallArguments += intermediate
					explicitOperationCall = true
				]
			)
		]
	}
}
