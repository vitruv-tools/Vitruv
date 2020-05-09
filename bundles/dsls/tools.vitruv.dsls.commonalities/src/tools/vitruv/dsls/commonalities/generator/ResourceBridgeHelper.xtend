package tools.vitruv.dsls.commonalities.generator

import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.IntermediateModelManagement
import tools.vitruv.extensions.dslruntime.commonalities.resources.IntermediateResourceBridge
import tools.vitruv.extensions.dslruntime.commonalities.resources.ResourcesPackage

import static com.google.common.base.Preconditions.*
import static tools.vitruv.dsls.commonalities.generator.ReactionsHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

@GenerationScoped
package class ResourceBridgeHelper extends ReactionsGenerationHelper {

	package new() {
	}

	def package generateSetupAndInsertResourceBridgeRoutine(ParticipationClass resourceClass) {
		checkNotNull(resourceClass, "resourceClass is null")
		checkArgument(resourceClass.isForResource, "The given resourceClass does to refer to the Resource metaclass")
		return create.routine('''setupAndInsertResourceBridge''')
			.input [
				model(ResourcesPackage.eINSTANCE.intermediateResourceBridge, RESOURCE_BRIDGE)
				model(resourceClass.containingCommonality.changeClass, INTERMEDIATE)
			]
			.action [
				update(RESOURCE_BRIDGE) [
					initExistingResourceBridge(resourceClass, variable(RESOURCE_BRIDGE))
				]
				execute [insertResourceBridge(resourceClass, variable(RESOURCE_BRIDGE), variable(INTERMEDIATE))]
				addCorrespondenceBetween(RESOURCE_BRIDGE).and(INTERMEDIATE)
					.taggedWith(resourceClass.correspondenceTag)
		]
	}

	// Initialization of an already partially initialized ResourceBridge for an existing resource
	def private initExistingResourceBridge(extension TypeProvider typeProvider, ParticipationClass resourceClass,
		XFeatureCall resourceBridge) {
		return resourceClass.setupResourceBridge(resourceBridge, typeProvider)
	}

	// Initialization of a new ResourceBridge for a new resource
	def package initNewResourceBridge(ParticipationClass resourceClass, XFeatureCall resourceBridge, TypeProvider typeProvider) {
		return resourceClass.setupResourceBridge(resourceBridge, typeProvider) => [
			expressions += XbaseFactory.eINSTANCE.createXAssignment => [
				assignable = resourceBridge.copy
				feature = typeProvider.findMethod(IntermediateResourceBridge, 'setFileExtension')
				value = XbaseFactory.eINSTANCE.createXStringLiteral => [
					value = resourceClass.superMetaclass.domain.vitruvDomain.fileExtensions.head
				]
			]
		]
	}

	/**
	 * The ResourceBridge setup that is common for both newly created resources
	 * and already existing resources.
	 */
	def private setupResourceBridge(ParticipationClass resourceClass, XFeatureCall resourceBridge,
		extension TypeProvider typeProvider) {
		return XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions += expressions(
				XbaseFactory.eINSTANCE.createXAssignment => [
					assignable = resourceBridge.copy
					feature = typeProvider.findMethod(IntermediateResourceBridge, 'setResourceAccess')
					value = resourceAccess
				],
				XbaseFactory.eINSTANCE.createXAssignment => [
					assignable = resourceBridge.copy
					feature = typeProvider.findMethod(IntermediateResourceBridge, 'setCorrespondenceModel')
					value = correspondenceModel
				],
				XbaseFactory.eINSTANCE.createXAssignment => [
					assignable = resourceBridge.copy
					feature = typeProvider.findMethod(IntermediateResourceBridge, 'setIntermediateNS')
					value = XbaseFactory.eINSTANCE.createXStringLiteral => [
						value = resourceClass.containingCommonality.concept.vitruvDomain.nsUris.head
					]
				]
			)
		]
	}

	def private insertResourceBridge(extension TypeProvider typeProvider, ParticipationClass resourceClass,
		XFeatureCall resourceBridge, XFeatureCall intermediate) {
		val intermediateModelURIVariable = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
			name = 'intermediateModelURI'
			right = callGetMetadataModelURI(typeProvider, resourceClass.containingCommonality.concept)
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
