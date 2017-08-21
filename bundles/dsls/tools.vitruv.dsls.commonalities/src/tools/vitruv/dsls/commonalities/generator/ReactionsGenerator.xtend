package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.Arrays
import java.util.function.Supplier
import org.eclipse.xtext.common.types.JvmConstructor
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.common.types.JvmType
import org.eclipse.xtext.common.types.access.IJvmTypeProvider
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.CommonalityFile
import tools.vitruv.dsls.commonalities.language.elements.Participation
import tools.vitruv.dsls.commonalities.language.elements.ParticipationClass
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.TypeProvider
import tools.vitruv.framework.domains.VitruvDomainProviderRegistry

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.dsls.commonalities.generator.GeneratorConstants.*

package class ReactionsGenerator extends SubGenerator {

	static val IntermediateModelUtil = 'tools.vitruv.extensions.dslruntime.commonalities.IntermediateModelUtil'
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
				create.reactionsSegment('''«commonality.name»To«participation.name»''')
					.inReactionToChangesIn(commonalityFile.conceptDomain)
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

	def private reactionForCommonalityDelete(ParticipationClass participationClass) {
		create.reaction('''«commonality.name»Delete''')
			.afterElement(commonalityFile.changeClass).deleted
			.call [
				match [
					vall("danglingCorrespondence").retrieve(participationClass.changeClass).correspondingTo.affectedEObject
				]
				action [
					delete("danglingCorrespondence")
				]
			]
	}

	def private reactionForParticipationDelete(ParticipationClass participationClass) {
		create.reaction('''«participationClass.name»Delete''')
			.afterElement(participationClass.changeClass).deleted
			.call [
				match [
					// checkAll [domainProviderIsRegistered(commonalityFile)]
					vall("danglingCorrespondence").retrieve(commonalityFile.changeClass).correspondingTo.affectedEObject
				]
				action [
					delete("danglingCorrespondence")
				// execute [unregisterDomainProvider(commonalityFile)]
				]
			]
	}

	def private reactionForCommonalityCreate(ParticipationClass participationClass) {
		create.reaction('''«commonality.name»Create''')
			.afterElement(commonalityFile.changeClass).created
			.call [
				action [
					vall("newCorrespondence").create(participationClass.changeClass)
					addCorrespondenceBetween.affectedEObject.and("newCorrespondence")
				]
			]
	}

	def private reactionForParticipationCreate(ParticipationClass participationClass) {
		create.reaction('''«participationClass.name»Create''')
			.afterElement(participationClass.changeClass).created
			.call [
				action [
					// execute [registerDomainProvider(commonalityFile)]
					vall("newCorrespondence").create(commonalityFile.changeClass).andInitialize [
						assignStagingId("newCorrespondence")
					]
					addCorrespondenceBetween.affectedEObject.and("newCorrespondence")
				// execute [unregisterDomainProvider(commonalityFile)]
				]
			]
	}

	def private reactionForCommonalityRootInsert() {
	}

	def private reactionForParticipationRootInsert(ParticipationClass participationClass) {
		create.reaction('''«participationClass.name»RootInsert''').
			afterElement(participationClass.changeClass).insertedAsRoot
			.call [
				match [
					vall("inserted").retrieve(commonalityFile.changeClass).correspondingTo.newValue
				]
				action [
					vall("intermediateRoot").create(commonalityFile.intermediateModelRootType).andInitialize [
						insertNonRootElement(variable("intermediateRoot"), variable("inserted"))
					]
					execute [createNewIntermediateModel(variable("intermediateRoot"), newValue)]
				]
			]
	}

	def insertNonRootElement(extension TypeProvider typeProvider, XFeatureCall root, XFeatureCall nonRoot) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			feature = typeProvider.findMethod(IntermediateModelUtil, 'addIntermediateModelNonRoot').staticExtensionImported
			memberCallTarget = root
			memberCallArguments += nonRoot //
		]
	}

	def private getChangeClass(ParticipationClass participationClass) {
		participationClass.superMetaclass.changeClass
	}

	def private commonalityChangeReactions(Participation participation) {
		participation.classes.flatMap[#[reactionForCommonalityDelete, reactionForCommonalityCreate]]
	}

	def private participationChangeReactions(Participation participation) {
		participation.classes.flatMap [
			#[reactionForParticipationDelete, reactionForParticipationCreate, reactionForParticipationRootInsert]
		]
	}

	def private domainProviderIsRegistered(extension TypeProvider typeProvider, CommonalityFile commonalityFile) {
		#[
			registerDomainProvider(typeProvider, commonalityFile),
			XbaseFactory.eINSTANCE.createXReturnExpression => [
				expression = XbaseFactory.eINSTANCE.createXBooleanLiteral => [
					isTrue = true
				]
			]
		]
	}

	def private registerDomainProvider(extension TypeProvider typeProvider, CommonalityFile commonalityFile) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			explicitOperationCall = true
			staticWithDeclaringType = true
			feature = typeProvider.findMethod(VitruvDomainProviderRegistry, 'registerDomainProvider')
			memberCallTarget = XbaseFactory.eINSTANCE.createXFeatureCall => [
				feature = typeProvider.findType(VitruvDomainProviderRegistry)
				typeLiteral = true
			]
			memberCallArguments += Arrays.asList(
				XbaseFactory.eINSTANCE.createXStringLiteral => [
					value = commonalityFile.conceptDomainName
				],
				XbaseFactory.eINSTANCE.createXConstructorCall => [
					constructor = commonalityFile.conceptDomainProviderType.defaultConstructor.imported
					explicitConstructorCall = true
				]
			)
		]
	}
	
	def private assignStagingId(extension TypeProvider typeProvider, String elementVariableName) {
		XbaseFactory.eINSTANCE.createXFeatureCall => [
			explicitOperationCall = true
			feature = typeProvider.findMethod(IntermediateModelUtil, 'assignStagingId')
			featureCallArguments += variable(elementVariableName)
		]
	}

	def private createNewIntermediateModel(extension TypeProvider typeProvider, XFeatureCall root, XFeatureCall existingElement) {
		XbaseFactory.eINSTANCE.createXFeatureCall => [
			feature = routineUserExecutionType.findMethod('persistProjectRelative')
			implicitReceiver = routineUserExecution
			featureCallArguments += Arrays.asList(
				existingElement,
				root,
				XbaseFactory.eINSTANCE.createXFeatureCall => [
					explicitOperationCall = true
					feature = typeProvider.findMethod(IntermediateModelUtil, 'computeNewIntermediateModelName').staticImported
					featureCallArguments += XbaseFactory.eINSTANCE.createXStringLiteral => [
						value = commonalityFile.intermediateModelFileExtension
					]
				]
			)
		]
	}

	def private unregisterDomainProvider(extension TypeProvider typeProvider, CommonalityFile commonalityFile) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			explicitOperationCall = true
			staticWithDeclaringType = true
			feature = typeProvider.findMethod(VitruvDomainProviderRegistry, 'unregisterDomainProvider')
			memberCallTarget = XbaseFactory.eINSTANCE.createXFeatureCall => [
				feature = typeProvider.findType(VitruvDomainProviderRegistry)
				typeLiteral = true
			]
			memberCallArguments += XbaseFactory.eINSTANCE.createXStringLiteral => [
				value = commonalityFile.conceptDomainName
			]
		]
	}

	def private static findType(IJvmTypeProvider typeProvider, Class<?> clazz) {
		val result = typeProvider.findTypeByName(clazz.canonicalName)
		if (result !== null) {
			return result
		}
		throw new IllegalStateException('''Could not find type “«clazz.canonicalName»”!''')
	}

	def private static checkGenericType(JvmType type) {
		if (type instanceof JvmGenericType) {
			return type
		}
		if (type === null) {
			throw new IllegalStateException('''Type not found!''')
		}
		throw new IllegalStateException('''Expected “«type.qualifiedName»” to resolve to a JvmGenericType!''')
	}

	def private static getDefaultConstructor(JvmGenericType type) {
		val result = type.members.filter(JvmConstructor).findFirst[parameters.length == 0]
		if (result !== null) {
			return result
		}
		throw new IllegalStateException('''Could not find a zero argument constructor in “«type.qualifiedName»”!''')
	}

	def private static findMethod(JvmDeclaredType type, String methodName) {
		var result = type.getOptionalMethod(methodName)
		if (result !== null) {
			return result
		}
		throw new IllegalStateException('''Could not find the method “«methodName»” in “«type.qualifiedName»”!''')
	}
	
	def private static JvmOperation getOptionalMethod(JvmDeclaredType declaredType, String methodName) {
		var result = declaredType.members.filter(JvmOperation).findFirst [
			simpleName == methodName
		]
		if (result !== null) {
			return result
		}
		return declaredType.superTypes.map [type].filter(JvmDeclaredType).map [getOptionalMethod(methodName)].filterNull.head
	}

	def private static findMethod(IJvmTypeProvider typeProvider, Class<?> clazz, String methodName) {
		typeProvider.findType(clazz).checkGenericType.findMethod(methodName)
	}

	def private static findMethod(IJvmTypeProvider typeProvider, String className, String methodName) {
		typeProvider.findTypeByName(className).checkGenericType.findMethod(methodName)
	}
}
