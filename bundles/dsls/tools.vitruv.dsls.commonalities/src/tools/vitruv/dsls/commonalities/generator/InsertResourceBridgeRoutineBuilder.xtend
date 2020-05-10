package tools.vitruv.dsls.commonalities.generator

import java.util.HashMap
import java.util.Map
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.Concept
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.IntermediateModelManagement
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage
import tools.vitruv.extensions.dslruntime.commonalities.resources.ResourcesPackage

import static com.google.common.base.Preconditions.*
import static tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static tools.vitruv.dsls.commonalities.generator.ReactionsHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class InsertResourceBridgeRoutineBuilder extends ReactionsGenerationHelper {

	@GenerationScoped
	static class Provider extends ReactionsSegmentScopedProvider<InsertResourceBridgeRoutineBuilder> {
		protected override createFor(FluentReactionsSegmentBuilder segment) {
			return new InsertResourceBridgeRoutineBuilder(segment).injectMembers
		}

		def getInsertResourceBridgeRoutine(FluentReactionsSegmentBuilder segment, ParticipationClass resourceClass) {
			return getFor(segment).getInsertResourceBridgeRoutine(resourceClass)
		}
	}

	// One routine per intermediate model (concept) is sufficient:
	val Map<String, FluentRoutineBuilder> insertResourceBridgeRoutinesByConceptName = new HashMap

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

	def package getInsertResourceBridgeRoutine(ParticipationClass resourceClass) {
		checkNotNull(resourceClass, "resourceClass is null")
		checkArgument(resourceClass.isForResource, "The given resourceClass does to refer to the Resource metaclass")
		val concept = resourceClass.containingCommonality.concept
		return insertResourceBridgeRoutinesByConceptName.computeIfAbsent(concept.name) [
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

	def private insertResourceBridge(extension TypeProvider typeProvider, Concept concept, XFeatureCall resourceBridge,
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
