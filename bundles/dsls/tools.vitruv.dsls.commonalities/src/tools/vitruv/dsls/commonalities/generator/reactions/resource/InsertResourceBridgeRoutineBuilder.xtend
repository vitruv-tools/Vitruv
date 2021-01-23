package tools.vitruv.dsls.commonalities.generator.reactions.resource

import java.util.HashMap
import java.util.Map
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsSegmentScopedProvider
import tools.vitruv.dsls.commonalities.language.Concept
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.IntermediateModelManagement
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage
import tools.vitruv.extensions.dslruntime.commonalities.resources.ResourcesPackage

import static com.google.common.base.Preconditions.*
import static tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*
import static tools.vitruv.dsls.commonalities.generator.reactions.intermediatemodel.IntermediateModelHelper.*
import static tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class InsertResourceBridgeRoutineBuilder extends ReactionsGenerationHelper {

	static class Provider extends ReactionsSegmentScopedProvider<InsertResourceBridgeRoutineBuilder> {
		protected override createFor(FluentReactionsSegmentBuilder segment) {
			return new InsertResourceBridgeRoutineBuilder(segment).injectMembers
		}

		def getInsertResourceBridgeRoutine(FluentReactionsSegmentBuilder segment, ParticipationClass resourceClass) {
			return getFor(segment).getInsertResourceBridgeRoutine(resourceClass)
		}
	}

	// One routine per intermediate model (concept) is sufficient (keyed by concept name):
	val Map<String, FluentRoutineBuilder> insertResourceBridgeRoutines = new HashMap

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

	def getInsertResourceBridgeRoutine(ParticipationClass resourceClass) {
		checkNotNull(resourceClass, "resourceClass is null")
		checkArgument(resourceClass.isForResource, "The given resourceClass does to refer to the Resource metaclass")
		val concept = resourceClass.declaringCommonality.concept
		return insertResourceBridgeRoutines.computeIfAbsent(concept.name) [
			create.routine('''insertResourceBridge_«concept.name»''')
				.input [
					model(ResourcesPackage.eINSTANCE.intermediateResourceBridge, RESOURCE_BRIDGE)
					model(IntermediateModelBasePackage.eINSTANCE.intermediate, INTERMEDIATE)
				]
				.action [
					execute [
						insertResourceBridge(concept, variable(RESOURCE_BRIDGE), variable(INTERMEDIATE))
					]
				]
		]
	}

	private def insertResourceBridge(extension TypeProvider typeProvider, Concept concept, XFeatureCall resourceBridge,
		XFeatureCall intermediate) {
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
					feature = typeProvider.findMethod(IntermediateModelManagement, 'addResourceBridge')
						.staticExtensionWildcardImported
					memberCallArguments += #[resourceBridge, intermediate]
					explicitOperationCall = true
				]
			)
		]
	}
}
