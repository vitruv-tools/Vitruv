package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.LinkedList
import java.util.function.Supplier
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.AttributeDeclaration
import tools.vitruv.dsls.commonalities.language.AttributeEqualitySpecification
import tools.vitruv.dsls.commonalities.language.AttributeMappingSpecifiation
import tools.vitruv.dsls.commonalities.language.AttributeReadSpecification
import tools.vitruv.dsls.commonalities.language.AttributeSetSpecification
import tools.vitruv.dsls.commonalities.language.elements.Participation
import tools.vitruv.dsls.commonalities.language.elements.ParticipationClass
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.ReactionTypeProvider
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.IntermediateModelManagement
import tools.vitruv.framework.domains.VitruvDomainProviderRegistry

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension org.eclipse.xtext.EcoreUtil2.isAssignableFrom
import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.generator.ParticipationRelationUtil.*
import tools.vitruv.dsls.commonalities.language.ParticipationRelationDeclaration
import org.eclipse.xtext.common.types.JvmOperation
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import java.util.Map
import java.util.HashMap
import tools.vitruv.extensions.dslruntime.commonalities.resources.ResourcesPackage
import tools.vitruv.extensions.dslruntime.commonalities.resources.IntermediateResourceBridge
import tools.vitruv.dsls.commonalities.language.elements.ResourceMetaclass
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage
import org.eclipse.emf.ecore.EcorePackage

package class ReactionsGenerator extends SubGenerator {

	static val DEBUG_WRITE_REACTIONS = false

	Supplier<IReactionsGenerator> reactionsGeneratorProvider
	@Inject FluentReactionsLanguageBuilder create
	val Map<Participation, FluentRoutineBuilder> intermediateResourcePrepareRoutineCache = new HashMap
	val Map<Participation, FluentRoutineBuilder> participationClassInsertRoutineCache = new HashMap

	@Inject
	new(Provider<IReactionsGenerator> reactionsGeneratorProvider) {
		this.reactionsGeneratorProvider = [
			reactionsGeneratorProvider.get() => [
				useResourceSet(commonalityFile.eResource.resourceSet)
			]
		]
	}

	override generate() {
		val generator = reactionsGeneratorProvider.get()

		val reactionFile = create.reactionsFile(commonality.name)
		for (participation : commonalityFile.commonality.participations) {
			reactionFile +=
				create.reactionsSegment('''«commonality.name»To«participation.name»''').
					inReactionToChangesIn(commonalityFile.conceptDomain)
					.executeActionsIn(participation.domain.vitruvDomain) +=
						commonalityChangeReactions(participation)

			reactionFile +=
				create.reactionsSegment('''«commonality.name»From«participation.name»''')
					.inReactionToChangesIn(participation.domain.vitruvDomain)
					.executeActionsIn(commonalityFile.concept.name) +=
						participationChangeReactions(participation)

		}

		VitruvDomainProviderRegistry.registerDomainProvider(commonalityFile.concept.name,
			commonalityFile.conceptDomain.provider)
		// TODO participation domains
		try {
			generator.addReactionsFile(reactionFile)
			generator.generate(fsa)

			if (DEBUG_WRITE_REACTIONS) {
				generator.writeReactions(fsa)
			}
		} finally {
			VitruvDomainProviderRegistry.unregisterDomainProvider(commonalityFile.concept.name)
		}
	}

	def private commonalityChangeReactions(Participation participation) {
		(#[
			reactionForCommonalityCreate(participation),
			reactionForCommonalityDelete(participation),
			reactionForCommonalityInsert(participation)
		] 
		+ participation.reactionsForCommonalityAttributeChange).filterNull
	}

	def private participationChangeReactions(Participation participation) {
		(participation.classes.flatMap [
			#[reactionForParticipationClassDelete, reactionForParticipationClassCreate] +
				reactionsForParticipationInsert
		] + participation.reactionsForParticipationAttributeChange).filterNull

	}

	def private reactionForCommonalityDelete(Participation participation) {
		create.reaction('''«commonality.name»Delete''')
			.afterElement(commonalityFile.changeClass).deleted
			.call [
				match [
					for (participationClass : participation.classes) {
						vall('''corresponding_«participationClass.name»''').retrieve(participationClass.changeClass)
							.correspondingTo.affectedEObject
							.taggedWith(participationClass.correspondenceTag)
					}
				]
				action [
					for (participationClass : participation.classes) {
						delete('''corresponding_«participationClass.name»''')
					}
				]
			]
	}

	def private reactionForParticipationClassDelete(ParticipationClass participationClass) {
		create.reaction('''«participationClass.name»Delete''')
			.afterElement(participationClass.changeClass).deleted
			.call [
				match [
					vall('corresponding_intermediate').retrieve(commonalityFile.changeClass)
						.correspondingTo.affectedEObject
				]
				action [
					delete('corresponding_intermediate')
				]
			]
	}

	def private String correspondingVariableName(
		ParticipationClass participationClass) '''corresponding_«participationClass.name»'''

	def private reactionForCommonalityCreate(Participation participation) {
		create.reaction('''«commonality.name»Create''')
			.afterElement(commonalityFile.changeClass).created
			.call [
				match [
					for (participationClass : participation.classes) {
						requireAbsenceOf(participationClass.changeClass).correspondingTo.affectedEObject
							.taggedWith(participationClass.correspondenceTag)
					}
				]
				.action [
					for (participationClass : participation.classes) {
						val corresponding = participationClass.correspondingVariableName
						val specialInitBuilder = new ParticipationClassSpecialInitializationBuilder(participationClass)
						vall(corresponding).create(participationClass.changeClass) => [
							if (specialInitBuilder.hasSpecialInitialization) {
								andInitialize [ typeProvider |
									specialInitBuilder.getSpecialInitializer(typeProvider, [
										typeProvider.variable(correspondingVariableName)
									])
								]
							}
						]
						addCorrespondenceBetween.affectedEObject.and(corresponding)
							.taggedWith(participationClass.correspondenceTag)
					}
				]
			]
	}

	def private reactionForParticipationClassCreate(ParticipationClass participationClass) {
		create.reaction('''«participationClass.name»Create''')
			.afterElement(participationClass.changeClass).created
			.call [
				action [
					vall('newIndermediate').create(commonalityFile.changeClass).andInitialize [
						assignStagingId(variable('newIndermediate'))
					]
					addCorrespondenceBetween('newIndermediate').and.affectedEObject
						.taggedWith(participationClass.correspondenceTag)
				]
			]
	}

	def private reactionsForParticipationInsert(ParticipationClass participationClass) {
		val reactions = new LinkedList<FluentReactionBuilder>

		reactions += create.reaction('''«participationClass.name»RootInsert''')
			.afterElement(participationClass.changeClass).insertedAsRoot
			.call(#[participationClass.intermediateResourceBridgeRoutine, participationClass.insertRoutine].filterNull)

		for (insertionPoint : participationClass.potentialInsertionPoints) {
			val referenceName = insertionPoint.name
			val className = insertionPoint.EContainingClass.name
			val packageName = insertionPoint.EContainingClass.EPackage.name
			reactions += create.reaction('''«participationClass.name»InsertIn«packageName.toFirstUpper»«className.toFirstUpper»«referenceName.toFirstUpper»''')
				.afterElement(participationClass.changeClass).insertedIn(insertionPoint)
				.with[hasResource(newValue)]
				.call(participationClass.insertRoutine)
		}
		return reactions
	}
	
	def private getInsertRoutine(ParticipationClass participationClass) {
		participationClassInsertRoutineCache.computeIfAbsent(participationClass.participation) [ participation |
			create.routine('''«participationClass.name.toFirstLower»RecursiveInsert''')
				.input [model(EcorePackage.eINSTANCE.EObject, newValue)]
				.match [
					vall('intermediate').retrieve(commonalityFile.changeClass).correspondingTo.newValue
					for (partClass : participation.classes) {
						vall(partClass.correspondingVariableName).retrieve(partClass.changeClass).correspondingTo('intermediate')
							.taggedWith(partClass.correspondenceTag)
					}
				]
				.action [
					execute [insertIntermediate(variable('intermediate'))]
				]
			]
	}
	
	def private getIntermediateResourceBridgeRoutine(ParticipationClass participationClass) {
		intermediateResourcePrepareRoutineCache.computeIfAbsent(participationClass.participation, [ participation |
			if (participation.hasResourceParticipation) {
				create.routine('''rootInsertIntermediateResoureBridge''')
					.input [model(EcorePackage.eINSTANCE.EObject, newValue)]
					.match [
						vall('contentIntermediate').retrieve(commonalityFile.changeClass).correspondingTo.newValue
					]
					.action [
						vall('resourceBridge').create(ResourcesPackage.eINSTANCE.intermediateResourceBridge)
							.andInitialize [
								initIntermediateResourceBridge(variable('resourceBridge'), newValue)
							]
						execute [insertResourceBridge(variable('resourceBridge'), variable('contentIntermediate'))]
						addCorrespondenceBetween('resourceBridge').and('contentIntermediate')
							.taggedWith(participation.resourceParticipation.correspondenceTag)
					]
			}
		])
	}
	
	def private reactionForCommonalityInsert(Participation participation) {
		val relations = newHashMap(participation.classes.map [optionalParticipationRelation]
			.filterNull
			.toSet
			.map [it -> operator.findOptionalImplementedMethod('afterInserted')]
			.filter[value !== null])
		
		if (relations.size > 0) {
			create.reaction('''«commonality.name»Insert''')
				.afterElement(commonalityFile.changeClass).insertedIn(IntermediateModelBasePackage.eINSTANCE.root_Intermediates)
				.call [
					match [
						for (partClass : relations.keySet.flatMap [participationClasses]) {
							vall(partClass.correspondingVariableName).retrieve(partClass.changeClass).correspondingTo.newValue
								.taggedWith(partClass.correspondenceTag)
						}
					]
					.action [
						for (entry : relations.entrySet) {
							val relation = entry.key
							val insertOperation = entry.value
							execute [
								callOperationOnRelation(relation, insertOperation)
							]
						}
					]
				]
		}	
	}

	def private reactionsForCommonalityAttributeChange(Participation participation) {
		commonality.attributes.map[reactionForAttributeLeftChange(participation)]
	}

	def private reactionForAttributeLeftChange(AttributeDeclaration attribute, Participation participation) {
		val relevantMappings = attribute.getWriteMappingsOfParticipation(participation)
		if (relevantMappings.size == 0) return null
		
		create.reaction('''«commonality.name»«attribute.name.toFirstUpper»Change''')
			.afterAttributeReplacedAt(attribute.EAttributeToReference)
			.call [
				input [newValue]
				.match [
					for (mapping : relevantMappings) {
						val participationClass = mapping.attribute.participationClass
						vall('''corresponding_«participationClass.name»''').retrieve(participationClass.changeClass)
							.correspondingTo.affectedEObject
							.taggedWith(participationClass.correspondenceTag)
					}
				]
				.action [
					for (mapping : relevantMappings) {
						val participationClass = mapping.attribute.participationClass
						val corresponding = '''corresponding_«participationClass.name»'''
						update(corresponding) [
							setAttribute(variable(corresponding), mapping.attribute.name,	newValue)
						]
					}
				]
			]
	}

	def private static getWriteMappingsOfParticipation(AttributeDeclaration attribute, Participation participation) {
		attribute.mappings.filter [
			isWrite && it.participation == participation
		].toList
	}

	def private reactionsForParticipationAttributeChange(Participation participation) {
		commonality.attributes.flatMap[mappings].filter [
			attribute.participationClass.participation == participation
		].map[reactionForAttributeMappingRightChange]

	}

	def private dispatch reactionForAttributeMappingRightChange(AttributeSetSpecification spec) {}

	def private dispatch reactionForAttributeMappingRightChange(AttributeReadSpecification spec) {
		spec.reactionForAttributeReadChange
	}

	def private dispatch reactionForAttributeMappingRightChange(AttributeEqualitySpecification spec) {
		spec.reactionForAttributeReadChange
	}

	def private reactionForAttributeReadChange(AttributeMappingSpecifiation spec) {
		create.reaction('''«spec.participation.name»«spec.attribute.name.toFirstUpper»Change''').
			afterAttributeReplacedAt(spec.attribute.changeAttribute.EAttributeToReference).call [
				input [newValue]
				.match [
					vall('intermediate').retrieve(commonalityFile.changeClass).correspondingTo.affectedEObject
				]
				.action [
					update('intermediate') [
						setAttribute(variable('intermediate'), spec.declaringAttribute.name, newValue)
					]
				]
			]
	}

	def hasResource(extension ReactionTypeProvider typeProvider, XFeatureCall element) {
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
				memberCallTarget = element
				feature = typeProvider.findMethod(EClass, 'eResource')
			]
			feature = typeProvider.findMethod(ObjectExtensions, 'operator_tripleNotEquals')
			rightOperand = XbaseFactory.eINSTANCE.createXNullLiteral
		]
	}

	def private insertIntermediate(extension RoutineTypeProvider typeProvider, XFeatureCall intermediate) {
		val resourceVariableDeclaration = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
			name = 'intermediateModelResource'
			right = createMetadataResource(typeProvider)
		]
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions += expressions(
				resourceVariableDeclaration,
				XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
					memberCallTarget = XbaseFactory.eINSTANCE.createXFeatureCall => [
						feature = resourceVariableDeclaration
					]
					feature = typeProvider.findMethod(IntermediateModelManagement, 'addIntermediate').staticExtensionWildcardImported
					memberCallArguments += intermediate
					explicitOperationCall = true
				]
			)
		]
	}
	
	def private createMetadataResource(extension RoutineTypeProvider typeProvider) {
		XbaseFactory.eINSTANCE.createXFeatureCall => [
			implicitReceiver = routineUserExecution
			feature = routineUserExecutionType.findMethod('getMetadataResource')
			// this string is intentionally hardcoded into the reactions
			// and not computed by a runtime class, as this allows to
			// change the way the identifier is computed without breaking
			// existing intermediate models
			featureCallArguments += expressions(
				XbaseFactory.eINSTANCE.createXStringLiteral => [
					value = 'commonalities'
				],
				XbaseFactory.eINSTANCE.createXStringLiteral => [
					value = commonalityFile.concept.name
				],
				XbaseFactory.eINSTANCE.createXStringLiteral => [
					value = commonality.name + '.intermediate'
				]
			)
			explicitOperationCall = true
		]
	}

	def private assignStagingId(extension RoutineTypeProvider typeProvider, XFeatureCall element) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = element
			feature = typeProvider.findMethod(IntermediateModelManagement, 'claimStagingId').staticExtensionWildcardImported
			explicitOperationCall = true
		]
	}
	
	def private initIntermediateResourceBridge(extension RoutineTypeProvider typeProvider, XFeatureCall resourceBridge,
		XFeatureCall modelElement) {
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions += expressions(
				XbaseFactory.eINSTANCE.createXAssignment => [
					assignable = resourceBridge.newFeatureCall
					feature = typeProvider.findMethod(IntermediateResourceBridge, 'setCorrespondenceModel')
					value = XbaseFactory.eINSTANCE.createXFeatureCall => [
						feature = routineUserExecutionType.findAttribute('correspondenceModel')
						implicitReceiver = routineUserExecution
					]
				],
				XbaseFactory.eINSTANCE.createXAssignment => [
					assignable = resourceBridge.newFeatureCall
					feature = typeProvider.findMethod(IntermediateResourceBridge, 'setResourceAccess')
					value = XbaseFactory.eINSTANCE.createXFeatureCall => [
						feature = routineUserExecutionType.findAttribute('resourceAccess')
						implicitReceiver = routineUserExecution
					]
				],
				XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
					memberCallTarget = resourceBridge.newFeatureCall
					feature = typeProvider.findMethod(IntermediateResourceBridge, 'initialiseForModelElement')
					explicitOperationCall = true
					memberCallArguments += modelElement
				]
			)
		]
	}
	
	def private insertResourceBridge(extension RoutineTypeProvider typeProvider, XFeatureCall resourceBridge,
		XFeatureCall intermediate) {
		val resourceVariableDeclaration = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
			name = 'intermediateModelResource'
			right = createMetadataResource(typeProvider)
		]
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions += expressions(
				resourceVariableDeclaration,
				XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
					memberCallTarget = XbaseFactory.eINSTANCE.createXFeatureCall => [
						feature = resourceVariableDeclaration
					]
					feature = typeProvider.findMethod(IntermediateModelManagement, 'addResourceBridge').staticExtensionWildcardImported
					memberCallArguments += #[resourceBridge, intermediate]
					explicitOperationCall = true
				]
			)
		]
	}


	def private setAttribute(extension RoutineTypeProvider typeProvider, XFeatureCall element, String attributeName,
		XExpression newValue) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = element.newFeatureCall
			feature = typeProvider.findMethod(EObject, 'eSet')
			explicitOperationCall = true
			memberCallArguments += XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
				memberCallTarget = XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
					memberCallTarget = element.newFeatureCall
					feature = typeProvider.findMethod(EObject, 'eClass')
				]
				feature = typeProvider.findMethod(EClass, 'getEStructuralFeature', String)
				explicitOperationCall = true
				memberCallArguments += XbaseFactory.eINSTANCE.createXStringLiteral => [
					value = attributeName
				]
			]
			memberCallArguments += newValue
		]
	}
	
	def private hasResourceParticipation(Participation participation) {
		participation.classes.containsAny [isForResource]
	}
	
	def private getResourceParticipation(Participation participation) {
		participation.classes.findFirst [isForResource]
	}
	
	def private isForResource(ParticipationClass participationClass) {
		participationClass.superMetaclass instanceof ResourceMetaclass
	}

	def private getPotentialInsertionPoints(ParticipationClass participationClass) {
		participationClass.superMetaclass.domain.metaclasses.map[changeClass].flatMap[EStructuralFeatures].filter(
			EReference).filter[isContainment].filter [
			(EType as EClass).isAssignableFrom(participationClass.superMetaclass.changeClass)
		]
	}
	
	def private callOperationOnRelation(extension RoutineTypeProvider typeProvider,
		ParticipationRelationDeclaration relation, JvmOperation operation) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = createOperatorConstructorCall(relation, typeProvider, [variable(correspondingVariableName)])
			feature = operation
			explicitOperationCall = true
		]
	}
	
	def private getChangeClass(ParticipationClass participationClass) {
		participationClass.superMetaclass.changeClass
	}
	
	def private String getCorrespondenceTag(ParticipationClass participationClass) '''
		«commonalityFile.concept.name».«commonality.name»/«participationClass.participation.name».«participationClass.name»'''
}
