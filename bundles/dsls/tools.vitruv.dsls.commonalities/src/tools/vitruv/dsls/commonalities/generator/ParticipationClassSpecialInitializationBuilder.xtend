package tools.vitruv.dsls.commonalities.generator

import java.util.function.Function
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.commonalities.language.TupleParticipation
import tools.vitruv.dsls.commonalities.language.TupleParticipationPart
import tools.vitruv.dsls.commonalities.language.elements.ResourceMetaclass
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.resources.IntermediateResourceBridge

import static tools.vitruv.dsls.commonalities.generator.ParticipationRelationUtil.*

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

/**
 * Decides whether a created model class requires special initialization and
 * provides expressions for these initializations.
 */
package class ParticipationClassSpecialInitializationBuilder 
	extends ReactionsSubGenerator<ParticipationClassSpecialInitializationBuilder> {
	extension var RoutineTypeProvider typeProvider
	ParticipationClass participationClass
	Function<ParticipationClass, XFeatureCall> participationClassReferenceProvider
	static val AFTER_CREATED_RELATION_OPERATOR_HOOK = 'afterCreated'
	
	def package forParticipationClass(ParticipationClass participationClass) {
		this.participationClass = participationClass
		return this
	}
	
	def package hasSpecialInitialization() {
		participationClass.isForResourceMetaclass || participationClass.participation.hasSpecialInitialization
	}

	def private dispatch hasSpecialInitialization(Participation participation) {
		false
	}

	def private dispatch boolean hasSpecialInitialization(TupleParticipation declaration) {
		for (part : declaration.parts) {
			if (part.hasSpecialInitialization) {
				return true
			}
		}
		return false
	}

	def private dispatch hasSpecialInitialization(TupleParticipationPart declarationPart) {
		false
	}

	def private dispatch hasSpecialInitialization(ParticipationRelation relation) {
		relation.leftClasses.size > 0 && relation.rightClasses.size > 0 &&
			relation.rightClasses.indexOf(participationClass) == relation.rightClasses.size - 1
			&& relation.operator.findOptionalImplementedMethod(AFTER_CREATED_RELATION_OPERATOR_HOOK) !== null
	}

	def package getSpecialInitializer(RoutineTypeProvider typeProvider,
		Function<ParticipationClass, XFeatureCall> participationReference) {
		this.typeProvider = typeProvider
		this.participationClassReferenceProvider = participationReference
		getSpecialInitializer()
	}

	def private getSpecialInitializer() {
		var XExpression result
		if (participationClass.forResourceMetaclass) {
			result = registerResource(participationClassReferenceProvider.apply(participationClass),
				routineUserExecutionType)
		}
		return result.join(participationClass.participation.specialInitializer)
	}

	def private dispatch XExpression getSpecialInitializer(Participation participation) {}

	def private dispatch XExpression getSpecialInitializer(TupleParticipation declaration) {
		var XExpression result
		for (part : declaration.parts) {
			result = result.join(part.specialInitializer)
		}
		return result
	}

	def private dispatch XExpression getSpecialInitializer(ParticipationRelation relation) {
		if (relation.rightClasses.indexOf(participationClass) == relation.rightClasses.size - 1) {
			relation.afterCreatedCode
		}
	}

	def private dispatch XExpression getSpecialInitializer(TupleParticipationPart part) {}

	@Pure
	def private static isForResourceMetaclass(ParticipationClass participationClass) {
		participationClass.superMetaclass instanceof ResourceMetaclass
	}

	def private getAfterCreatedCode(ParticipationRelation participationRelation) {
		val afterCreatedMethod = participationRelation.operator.findOptionalImplementedMethod(AFTER_CREATED_RELATION_OPERATOR_HOOK)
		if (afterCreatedMethod !== null) {
			XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
				memberCallTarget = createOperatorConstructorCall(participationRelation, typeProvider, participationClassReferenceProvider)
				feature = afterCreatedMethod
				explicitOperationCall = true
			]
		}
	}

	def private registerResource(XFeatureCall resource, JvmDeclaredType userExecution) {
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions += expressions(
				XbaseFactory.eINSTANCE.createXAssignment => [
					assignable = XbaseFactory.eINSTANCE.createXFeatureCall => [
						feature = resource.feature
					]
					feature = typeProvider.findMethod(IntermediateResourceBridge, 'setResourceAccess')
					value = XbaseFactory.eINSTANCE.createXFeatureCall => [
						feature = userExecution.findAttribute('resourceAccess')
						implicitReceiver = XbaseFactory.eINSTANCE.createXFeatureCall => [
							feature = userExecution
						]
					]
				],
				XbaseFactory.eINSTANCE.createXAssignment => [
					assignable = XbaseFactory.eINSTANCE.createXFeatureCall => [
						feature = resource.feature
					]
					feature = typeProvider.findMethod(IntermediateResourceBridge, 'setCorrespondenceModel')
					value = XbaseFactory.eINSTANCE.createXFeatureCall => [
						feature = userExecution.findAttribute('correspondenceModel')
						implicitReceiver = XbaseFactory.eINSTANCE.createXFeatureCall => [
							feature = userExecution
						]
					]
				],
				XbaseFactory.eINSTANCE.createXAssignment => [
					assignable = XbaseFactory.eINSTANCE.createXFeatureCall => [
						feature = resource.feature
					]
					feature = typeProvider.findMethod(IntermediateResourceBridge, 'setFileExtension')
					value = XbaseFactory.eINSTANCE.createXStringLiteral => [
						value = participationClass.superMetaclass.domain.vitruvDomain.fileExtensions.head
					]
				]
			)
		]
	}
}
