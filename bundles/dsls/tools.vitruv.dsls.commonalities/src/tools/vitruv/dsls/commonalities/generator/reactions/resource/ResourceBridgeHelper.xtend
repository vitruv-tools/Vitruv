package tools.vitruv.dsls.commonalities.generator.reactions.resource

import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.resources.IntermediateResourceBridge

import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class ResourceBridgeHelper extends ReactionsGenerationHelper {

	package new() {
	}

	// Initialization of an already partially initialized ResourceBridge for an existing resource
	def initExistingResourceBridge(extension TypeProvider typeProvider, ParticipationClass resourceClass,
		XFeatureCall resourceBridge) {
		return resourceClass.setupResourceBridge(resourceBridge, typeProvider)
	}

	// Initialization of a new ResourceBridge for a new resource
	def initNewResourceBridge(ParticipationClass resourceClass, XFeatureCall resourceBridge,
		TypeProvider typeProvider) {
		return resourceClass.setupResourceBridge(resourceBridge, typeProvider) => [
			expressions += XbaseFactory.eINSTANCE.createXAssignment => [
				assignable = resourceBridge.copy
				feature = typeProvider.findMethod(IntermediateResourceBridge, 'setFileExtension')
				value = XbaseFactory.eINSTANCE.createXStringLiteral => [
					value = resourceClass.superMetaclass.domain.vitruvDomain.fileExtensions.head
				]
			]
			// We initially disable the automatic persistence for new resource bridges and enable it again at the end
			// of the participation creation, after all resource bridge attributes have reached their final state. This
			// avoids that each individual change to the resource bridge's attributes (such as the path, name, etc.)
			// triggers a root removal and root insertion change event due to the contained root object being moved to
			// a new resource.
			expressions += setIsPersistenceEnabled(typeProvider, resourceBridge.copy, false)
		]
	}

	def setIsPersistenceEnabled(TypeProvider typeProvider, XFeatureCall resourceBridge,
		boolean newIsPersistenceEnabled) {
		return XbaseFactory.eINSTANCE.createXAssignment => [
			assignable = resourceBridge
			feature = typeProvider.findMethod(IntermediateResourceBridge, 'setIsPersistenceEnabled')
			value = booleanLiteral(newIsPersistenceEnabled)
		]
	}

	/**
	 * The ResourceBridge setup that is common for both newly created resources
	 * and already existing resources.
	 */
	private def setupResourceBridge(ParticipationClass resourceClass, XFeatureCall resourceBridge,
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
