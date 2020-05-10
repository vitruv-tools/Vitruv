package tools.vitruv.dsls.commonalities.generator

import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.resources.IntermediateResourceBridge

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

@GenerationScoped
package class ResourceBridgeHelper extends ReactionsGenerationHelper {

	package new() {
	}

	// Initialization of an already partially initialized ResourceBridge for an existing resource
	def package initExistingResourceBridge(extension TypeProvider typeProvider, ParticipationClass resourceClass,
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
		val commonality = resourceClass.containingCommonality
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
						value = commonality.concept.vitruvDomain.nsUris.head
					]
				]
			)
		]
	}
}
