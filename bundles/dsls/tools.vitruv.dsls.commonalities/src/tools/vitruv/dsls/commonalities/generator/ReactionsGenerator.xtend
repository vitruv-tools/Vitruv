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
import tools.vitruv.dsls.commonalities.language.elements.ResourceMetaclass
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

package class ReactionsGenerator extends SubGenerator {

	static val DEBUG_WRITE_REACTIONS = true

	Supplier<IReactionsGenerator> reactionsGeneratorProvider
	@Inject FluentReactionsLanguageBuilder create

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
				create.reactionsSegment('''«commonality.name»To«participation.name»''').inReactionToChangesIn(
					commonalityFile.conceptDomain).executeActionsIn(participation.domain.vitruvDomain) +=
					commonalityChangeReactions(participation)

			reactionFile +=
				create.reactionsSegment('''«commonality.name»From«participation.name»''').inReactionToChangesIn(
					participation.domain.vitruvDomain).executeActionsIn(commonalityFile.concept.name) +=
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
		(#[reactionForCommonalityCreate(participation), reactionForCommonalityDelete(participation)] +
			participation.reactionsForCommonalityAttributeChange).filterNull
	}

	def private participationChangeReactions(Participation participation) {
		(participation.classes.flatMap [
			#[reactionForParticipationClassDelete, reactionForParticipationClassCreate] +
				reactionsForParticipationInsert
		] + participation.reactionsForParticipationAttributeChange).filterNull

	}

	def private reactionForCommonalityDelete(Participation participation) {
		create.reaction('''«commonality.name»Delete''').afterElement(commonalityFile.changeClass).deleted.call [
			match [
				for (participationClass : participation.classes) {
					vall('''corresponding_«participationClass.name»''').retrieve(participationClass.changeClass).
						correspondingTo.affectedEObject
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
		if (participationClass.forResourceMetaclass) return null
		create.reaction('''«participationClass.name»Delete''').afterElement(participationClass.changeClass).deleted.call [
			match [
				vall("corresponding_intermediate").retrieve(commonalityFile.changeClass).correspondingTo.affectedEObject
			]
			action [
				delete("corresponding_intermediate")
			]
		]
	}

	def private String commonalityCreateReactionCorrespondingName(
		ParticipationClass participationClass) '''corresponding_«participationClass.name»'''

	def private reactionForCommonalityCreate(Participation participation) {
		create.reaction('''«commonality.name»Create''').afterElement(commonalityFile.changeClass).created.call [
			action [
				for (participationClass : participation.classes) {
					val corresponding = participationClass.commonalityCreateReactionCorrespondingName
					val specialInitConstruct = new ParticipationClassSpecialInitializationConstructor(participationClass)
					vall(corresponding).create(participationClass.changeClass) => [
						if (specialInitConstruct.hasSpecialInitialization) {
							andInitialize [ typeProvider |
								specialInitConstruct.getSpecialInitializer(typeProvider, [
									typeProvider.variable(commonalityCreateReactionCorrespondingName)
								])
							]
						}
					]
					addCorrespondenceBetween.affectedEObject.and(corresponding)
				}
			]
		]
	}

	def private reactionForParticipationClassCreate(ParticipationClass participationClass) {
		if (participationClass.forResourceMetaclass) return null
		create.reaction('''«participationClass.name»Create''').afterElement(participationClass.changeClass).created.call [
			action [
				vall("newCorrespondence").create(commonalityFile.changeClass).andInitialize [
					assignStagingId(variable("newCorrespondence"))
				]
				addCorrespondenceBetween("newCorrespondence").and.affectedEObject
			]
		]
	}

	def private reactionForCommonalityRootInsert() {
	}

	def private reactionsForParticipationInsert(ParticipationClass participationClass) {
		val insertRoutine = create.routine('''«participationClass.name.toFirstLower»RecursiveInsert''').match [
			vall("nonRoot").retrieve(commonalityFile.intermediateModelNonRootType).correspondingTo.newValue
		].action [
			execute [insertIntermediateNonRoot(variable("nonRoot"))]
		]

		val reactions = new LinkedList<FluentReactionBuilder>

		reactions +=
			create.reaction('''«participationClass.name»RootInsert''').afterElement(participationClass.changeClass).
				insertedAsRoot.call(insertRoutine)

		for (insertionPoint : participationClass.potentialInsertionPoints) {
			val referenceName = insertionPoint.name
			val className = insertionPoint.EContainingClass.name
			val packageName = insertionPoint.EContainingClass.EPackage.name
			reactions +=
				create.
					reaction('''«participationClass.name»InsertIn«packageName.toFirstUpper»«className.toFirstUpper»«referenceName.toFirstUpper»''').
					afterElement(participationClass.changeClass).insertedIn(insertionPoint).with[resource(newValue)].
					call(insertRoutine)
		}
		return reactions
	}

	def private reactionsForCommonalityAttributeChange(Participation participation) {
		commonality.attributes.map[reactionForAttributeLeftChange(participation)]
	}

	def private reactionForAttributeLeftChange(AttributeDeclaration attribute, Participation participation) {
		val relevantMappings = attribute.getWriteMappingsOfParticipation(participation)
		if (relevantMappings.size == 0) return null
		create.reaction('''«commonality.name»«attribute.name.toFirstUpper»Change''').afterAttributeReplacedAt(
			attribute.EAttributeToReference).call [
			input [newValue]
			match [
				for (mapping : relevantMappings) {
					val participationClass = mapping.attribute.participationClass
					vall('''corresponding_«participationClass.name»''').retrieve(participationClass.changeClass).
						correspondingTo.affectedEObject
				}
			]
			action [
				for (mapping : relevantMappings) {
					val participationClass = mapping.attribute.participationClass
					update('''corresponding_«participationClass.name»''') [
						setAttribute(variable('''corresponding_«participationClass.name»'''), mapping.attribute.name,
							newValue)
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

	def private reactionForAttributeWriteChange(AttributeMappingSpecifiation spec) {
		val participationClass = spec.attribute.participationClass
		val corresponding = '''corresponding_«participationClass.name»'''
		create.
			reaction('''«commonality.name»«spec.declaringAttribute.name.toFirstUpper»ChangeTo«spec.participation.name»«spec.attribute.name.toFirstUpper»''').
			afterAttributeReplacedAt(spec.declaringAttribute.EAttributeToReference).call [
				input [newValue]
				match [
					vall(corresponding).retrieve(participationClass.changeClass).correspondingTo.affectedEObject
				]
				action [
					update(corresponding) [
						setAttribute(variable(corresponding), spec.attribute.name, newValue)
					]
				]
			]
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
				match [
					vall('correspondingIntermediate').retrieve(commonalityFile.intermediateModelNonRootType).
						correspondingTo.affectedEObject
				]
				action [
					update('correspondingIntermediate') [
						setAttribute(variable('correspondingIntermediate'), spec.declaringAttribute.name, newValue)
					]
				]
			]
	}

	def private getChangeClass(ParticipationClass participationClass) {
		participationClass.superMetaclass.changeClass
	}

	def private static resource(extension ReactionTypeProvider typeProvider, XFeatureCall element) {
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
				memberCallTarget = element
				feature = typeProvider.findMethod(EClass, 'eResource')
			]
			feature = typeProvider.findMethod(ObjectExtensions, 'operator_tripleNotEquals')
			rightOperand = XbaseFactory.eINSTANCE.createXNullLiteral
		]
	}

	def private insertIntermediateNonRoot(extension RoutineTypeProvider typeProvider, XFeatureCall nonRoot) {
		val resourceVariableDeclaration = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
			name = 'intermediateModelResource'
			right = XbaseFactory.eINSTANCE.createXFeatureCall => [
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
		]
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions += expressions(
				resourceVariableDeclaration,
				XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
					memberCallTarget = XbaseFactory.eINSTANCE.createXFeatureCall => [
						feature = resourceVariableDeclaration
					]
					feature = typeProvider.findMethod(IntermediateModelManagement, 'addNonRoot').staticExtensionWildcardImported
					memberCallArguments += nonRoot
					explicitOperationCall = true
				]
			)
		]

	}

	def private assignStagingId(extension RoutineTypeProvider typeProvider, XFeatureCall element) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = element
			feature = typeProvider.findMethod(IntermediateModelManagement, 'assignStagingId').staticExtensionWildcardImported
			explicitOperationCall = true
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

	@Pure
	def private static forResourceMetaclass(ParticipationClass participationClass) {
		participationClass.superMetaclass instanceof ResourceMetaclass
	}

	def private getPotentialInsertionPoints(ParticipationClass participationClass) {
		participationClass.superMetaclass.domain.metaclasses.map[changeClass].flatMap[EStructuralFeatures].filter(
			EReference).filter[isContainment].filter [
			(EType as EClass).isAssignableFrom(participationClass.superMetaclass.changeClass)
		]
	}
}
