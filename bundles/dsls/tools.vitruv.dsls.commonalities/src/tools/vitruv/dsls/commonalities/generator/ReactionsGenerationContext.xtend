package tools.vitruv.dsls.commonalities.generator

import java.util.HashMap
import java.util.Map
import javax.inject.Inject
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.Concept
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.elements.NamedElement
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.IntermediateModelManagement
import tools.vitruv.extensions.dslruntime.commonalities.resources.IntermediateResourceBridge
import tools.vitruv.extensions.dslruntime.commonalities.resources.ResourcesPackage
import tools.vitruv.framework.util.VitruviusConstants

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.dsls.commonalities.generator.GeneratorConstants.*
import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

@GenerationScoped
package class ReactionsGenerationContext {

	@Inject extension GenerationContext generationContext
	@Accessors(PACKAGE_GETTER)
	@Inject FluentReactionsLanguageBuilder create

	val Map<Participation, FluentRoutineBuilder> intermediateResourcePrepareRoutineCache = new HashMap
	// Since a commonality may have other commonalities as participations, commonality insert routines can potentially
	// be found for all pairs of a commonality and its participations.
	val Map<Pair<NamedElement, NamedElement>, FluentRoutineBuilder> commonalityInsertRoutineCache = new HashMap

	def private getMetadataModelKey(Concept concept) {
		return #['commonalities', concept.name + VitruviusConstants.fileExtSeparator + concept.intermediateModelFileExtension]
	}

	def private getInsertRoutine(NamedElement fromParticipationOrCommonality, Commonality toCommonality) {
		commonalityInsertRoutineCache.computeIfAbsent(Pair.of(fromParticipationOrCommonality, toCommonality), [
			create.routine('''intermediateInsert_«toCommonality.name»''')
				.input [model(EcorePackage.eINSTANCE.EObject, newValue)]
				.match [
					vall(INTERMEDIATE).retrieveAsserted(toCommonality.changeClass).correspondingTo.newValue
				].action [
					execute [insertIntermediate(variable(INTERMEDIATE), toCommonality)]
				]
		])
	}

	def package getInsertRoutine(Participation fromParticipation, Commonality toCommonality) {
		getInsertRoutine(fromParticipation as NamedElement, toCommonality)
	}

	def package getInsertRoutine(Commonality fromCommonality, Commonality toCommonality) {
		getInsertRoutine(fromCommonality as NamedElement, toCommonality)
	}

	def package getIntermediateResourceBridgeRoutine(ParticipationClass participationClass) {
		intermediateResourcePrepareRoutineCache.computeIfAbsent(participationClass.participation, [ participation |
			if (participation.hasResourceClass) {
				create.routine('''rootInsertIntermediateResoureBridge''')
					.input [model(EcorePackage.eINSTANCE.EObject, newValue)]
					.match [
						vall(INTERMEDIATE).retrieve(commonality.changeClass).correspondingTo.newValue
					]
					.action [
						vall(RESOURCE_BRIDGE).create(ResourcesPackage.eINSTANCE.intermediateResourceBridge).andInitialize [
							initIntermediateResourceBridge(variable(RESOURCE_BRIDGE), newValue)
						]
						execute [insertResourceBridge(variable(RESOURCE_BRIDGE), variable(INTERMEDIATE))]
						addCorrespondenceBetween(RESOURCE_BRIDGE).and(INTERMEDIATE)
							.taggedWith(participation.resourceClass.correspondenceTag)
				]
			}
		])
	}

	def private initIntermediateResourceBridge(extension TypeProvider typeProvider, XFeatureCall resourceBridge,
		XFeatureCall modelElement) {
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions += expressions(
				XbaseFactory.eINSTANCE.createXAssignment => [
					assignable = resourceBridge.copy
					feature = typeProvider.findMethod(IntermediateResourceBridge, 'setCorrespondenceModel')
					value = correspondenceModel
				],
				XbaseFactory.eINSTANCE.createXAssignment => [
					assignable = resourceBridge.copy
					feature = typeProvider.findMethod(IntermediateResourceBridge, 'setResourceAccess')
					value = resourceAccess
				],
				XbaseFactory.eINSTANCE.createXAssignment => [
					assignable = resourceBridge.copy
					feature = typeProvider.findMethod(IntermediateResourceBridge, 'setIntermediateNS')
					value = XbaseFactory.eINSTANCE.createXStringLiteral => [
						value = concept.vitruvDomain.nsUris.head
					]
				],
				XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
					memberCallTarget = resourceBridge.copy
					feature = typeProvider.findMethod(IntermediateResourceBridge, 'initialiseForModelElement')
					explicitOperationCall = true
					memberCallArguments += modelElement
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

	def private callGetMetadataModelURI(extension TypeProvider typeProvider, Concept concept) {
		resourceAccess.memberFeatureCall => [
			feature = resourceAccessType.findMethod('getMetadataModelURI')
			explicitOperationCall = true
			memberCallArguments += concept.metadataModelKey.map[stringLiteral]
		]
	}

	def private callGetModelResource(extension TypeProvider typeProvider, XFeatureCall vuri) {
		resourceAccess.memberFeatureCall => [
			feature = resourceAccessType.findMethod('getModelResource')
			explicitOperationCall = true
			memberCallArguments += vuri
		]
	}
}
