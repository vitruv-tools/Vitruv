package tools.vitruv.dsls.commonalities.generator

import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.IntermediateModelManagement
import tools.vitruv.extensions.dslruntime.commonalities.resources.IntermediateResourceBridge
import tools.vitruv.extensions.dslruntime.commonalities.resources.ResourcesPackage

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class ResourceBridgeHelper extends ReactionsGenerationHelper {

	val Map<Participation, FluentRoutineBuilder> insertResourceBridgeRoutineCache = new HashMap

	package new() {
	}

	def package getInsertResourceBridgeRoutine(Participation participation) {
		insertResourceBridgeRoutineCache.computeIfAbsent(participation) [
			val resourceClass = participation.resourceClass
			if (resourceClass !== null) {
				create.routine('''insertResoureBridge''')
					.input [model(EcorePackage.eINSTANCE.EObject, newValue)]
					.match [
						vall(INTERMEDIATE).retrieve(participation.containingCommonality.changeClass).correspondingTo.newValue
					]
					.action [
						vall(RESOURCE_BRIDGE).create(ResourcesPackage.eINSTANCE.intermediateResourceBridge).andInitialize [
							initExistingResourceBridge(resourceClass, variable(RESOURCE_BRIDGE), newValue)
						]
						execute [insertResourceBridge(variable(RESOURCE_BRIDGE), variable(INTERMEDIATE))]
						addCorrespondenceBetween(RESOURCE_BRIDGE).and(INTERMEDIATE)
							.taggedWith(resourceClass.correspondenceTag)
				]
			}
		]
	}

	// Initialization of a new ResourceBridge for an existing resource
	def private initExistingResourceBridge(extension TypeProvider typeProvider, ParticipationClass resourceClass,
		XFeatureCall resourceBridge, XFeatureCall modelElement) {
		return resourceBridge.setupResourceBridge(resourceClass, typeProvider) => [
			expressions += XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
				memberCallTarget = resourceBridge.copy
				feature = typeProvider.findMethod(IntermediateResourceBridge, 'initialiseForModelElement')
				explicitOperationCall = true
				memberCallArguments += modelElement
			]
		]
	}

	// Initialization of a new ResourceBridge for a new resource
	def package initNewResourceBridge(ParticipationClass resourceClass, XFeatureCall resourceBridge, TypeProvider typeProvider) {
		return resourceBridge.setupResourceBridge(resourceClass, typeProvider) => [
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
	def private setupResourceBridge(XFeatureCall resourceBridge, ParticipationClass resourceClass,
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

	def private insertResourceBridge(extension TypeProvider typeProvider, XFeatureCall resourceBridge,
		XFeatureCall intermediate) {
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
					feature = typeProvider.findMethod(IntermediateModelManagement, 'addResourceBridge').
						staticExtensionWildcardImported
					memberCallArguments += #[resourceBridge, intermediate]
					explicitOperationCall = true
				]
			)
		]
	}
}