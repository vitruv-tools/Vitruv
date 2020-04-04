package tools.vitruv.dsls.commonalities.generator

import java.util.HashMap
import java.util.Map
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.Commonality
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

class InsertIntermediateRoutineBuilder extends ReactionsGenerationHelper {

	@GenerationScoped
	static class Provider extends InjectingFactoryBase {

		val Map<FluentReactionsSegmentBuilder, InsertIntermediateRoutineBuilder> bySegment = new HashMap

		def getFor(FluentReactionsSegmentBuilder segment) {
			return bySegment.computeIfAbsent(segment) [
				createFor(segment)
			]
		}

		def private createFor(FluentReactionsSegmentBuilder segment) {
			return new InsertIntermediateRoutineBuilder(segment).injectMembers
		}
	}

	val Map<Commonality, FluentRoutineBuilder> insertIntermediateRoutines = new HashMap

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

	def package getRoutine(Commonality commonality) {
		insertIntermediateRoutines.computeIfAbsent(commonality) [
			create.routine('''insertIntermediate_«commonality.name»''')
				.input [model(commonality.changeClass, INTERMEDIATE)]
				.action [
					execute [insertIntermediate(variable(INTERMEDIATE), commonality)]
				]
		]
	}

	def private insertIntermediate(extension TypeProvider typeProvider, XFeatureCall intermediate,
		Commonality commonality) {
		val intermediateModelURIVariable = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
			name = 'intermediateModelURI'
			right = callGetMetadataModelURI(typeProvider, commonality.concept)
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
					feature = typeProvider.findMethod(IntermediateModelManagement, 'addIntermediate').
						staticExtensionWildcardImported
					memberCallArguments += intermediate
					explicitOperationCall = true
				]
			)
		]
	}
}