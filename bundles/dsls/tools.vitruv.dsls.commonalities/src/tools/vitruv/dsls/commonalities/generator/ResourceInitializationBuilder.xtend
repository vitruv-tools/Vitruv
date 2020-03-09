package tools.vitruv.dsls.commonalities.generator

import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.elements.ResourceMetaclass
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.resources.IntermediateResourceBridge

import static tools.vitruv.dsls.commonalities.generator.XbaseHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*

package class ResourceInitializationBuilder extends ReactionsSubGenerator<ResourceInitializationBuilder> {

	ParticipationClass participationClass

	def forParticipationClass(ParticipationClass participationClass) {
		this.participationClass = participationClass
		return this
	}

	def hasInitializer() {
		return participationClass.isForResourceMetaclass
	}

	def XExpression getInitializer(XFeatureCall resource, TypeProvider typeProvider) {
		if (!hasInitializer) return null
		return registerResource(resource, typeProvider)
	}

	@Pure
	def private static isForResourceMetaclass(ParticipationClass participationClass) {
		return (participationClass.superMetaclass instanceof ResourceMetaclass)
	}

	def private registerResource(XFeatureCall resource, extension TypeProvider typeProvider) {
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions += expressions(
				XbaseFactory.eINSTANCE.createXAssignment => [
					assignable = XbaseFactory.eINSTANCE.createXFeatureCall => [
						feature = resource.feature
					]
					feature = typeProvider.findMethod(IntermediateResourceBridge, 'setResourceAccess')
					value = resourceAccess
				],
				XbaseFactory.eINSTANCE.createXAssignment => [
					assignable = XbaseFactory.eINSTANCE.createXFeatureCall => [
						feature = resource.feature
					]
					feature = typeProvider.findMethod(IntermediateResourceBridge, 'setCorrespondenceModel')
					value = correspondenceModel
				],
				XbaseFactory.eINSTANCE.createXAssignment => [
					assignable = XbaseFactory.eINSTANCE.createXFeatureCall => [
						feature = resource.feature
					]
					feature = typeProvider.findMethod(IntermediateResourceBridge, 'setFileExtension')
					value = XbaseFactory.eINSTANCE.createXStringLiteral => [
						value = participationClass.superMetaclass.domain.vitruvDomain.fileExtensions.head
					]
				],
				XbaseFactory.eINSTANCE.createXAssignment => [
					assignable = XbaseFactory.eINSTANCE.createXFeatureCall => [
						feature = resource.feature
					]
					feature = typeProvider.findMethod(IntermediateResourceBridge, 'setIntermediateNS')
					value = XbaseFactory.eINSTANCE.createXStringLiteral => [
						value = concept.vitruvDomain.nsUris.head
					]
				]
			)
		]
	}
}