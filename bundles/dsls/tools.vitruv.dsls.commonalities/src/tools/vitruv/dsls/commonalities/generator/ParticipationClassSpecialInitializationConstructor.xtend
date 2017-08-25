package tools.vitruv.dsls.commonalities.generator

import java.util.function.Function
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.ParticipationDeclaration
import tools.vitruv.dsls.commonalities.language.ParticipationRelationDeclaration
import tools.vitruv.dsls.commonalities.language.TupleParticipationDeclaration
import tools.vitruv.dsls.commonalities.language.TupleParticipationDeclarationPart
import tools.vitruv.dsls.commonalities.language.elements.ParticipationClass
import tools.vitruv.dsls.commonalities.language.elements.ResourceMetaclass
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.resources.IntermediateResourceBridge

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*

/**
 * Decides whether a created model class requires special initialization and
 * provides expressions for these initializations.
 */
package class ParticipationClassSpecialInitializationConstructor {
	extension var RoutineTypeProvider typeProvider
	val ParticipationClass participationClass
	var Function<ParticipationClass, XFeatureCall> participationClassReferenceProvider

	new(ParticipationClass participationClass) {
		this.participationClass = participationClass
	}

	def package hasSpecialInitialization() {
		participationClass.isForResourceMetaclass || participationClass.participation.hasSpecialInitialization
	}

	def private dispatch hasSpecialInitialization(ParticipationDeclaration declaration) {
		false
	}

	def private dispatch boolean hasSpecialInitialization(TupleParticipationDeclaration declaration) {
		for (part : declaration.parts) {
			if (part.hasSpecialInitialization) {
				return true
			}
		}
		return false
	}

	def private dispatch hasSpecialInitialization(TupleParticipationDeclarationPart declarationPart) {
		false
	}

	def private dispatch hasSpecialInitialization(ParticipationRelationDeclaration relation) {
		relation.leftClasses.size > 0 && relation.rightClasses.size > 0 &&
			relation.rightClasses.indexOf(participationClass) == relation.rightClasses.size - 1
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

	def private dispatch XExpression getSpecialInitializer(ParticipationDeclaration delaration) {}

	def private dispatch XExpression getSpecialInitializer(TupleParticipationDeclaration declaration) {
		var XExpression result
		for (part : declaration.parts) {
			result = result.join(part.specialInitializer)
		}
		return result
	}

	def private dispatch XExpression getSpecialInitializer(ParticipationRelationDeclaration relation) {
		if (relation.rightClasses.indexOf(participationClass) == relation.rightClasses.size - 1) {
			relation.afterCreatedCode
		}
	}

	def private dispatch XExpression getSpecialInitializer(TupleParticipationDeclarationPart part) {}

	@Pure
	def private static isForResourceMetaclass(ParticipationClass participationClass) {
		participationClass.superMetaclass instanceof ResourceMetaclass
	}

	def private getAfterCreatedCode(ParticipationRelationDeclaration participationRelation) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = participationRelation.createRelationOperationInstance
			feature = participationRelation.operator.findMethod('afterCreated')
			explicitOperationCall = true
		]
	}

	def private createRelationOperationInstance(ParticipationRelationDeclaration participationRelation) {
		XbaseFactory.eINSTANCE.createXConstructorCall => [
			constructor = participationRelation.operator.imported.findConstructor(EObject.arrayClass, EObject.arrayClass)
			explicitConstructorCall = true
			arguments += expressions(
				XbaseFactory.eINSTANCE.createXListLiteral => [
					elements += participationRelation.leftClasses.map(participationClassReferenceProvider)
				],
				XbaseFactory.eINSTANCE.createXListLiteral => [
					elements += participationRelation.leftClasses.map(participationClassReferenceProvider)
				]
			)
		]
	}

	def private registerResource(XFeatureCall resource, JvmDeclaredType userExecution) {
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions += expressions(
				XbaseFactory.eINSTANCE.createXAssignment => [
					assignable = XbaseFactory.eINSTANCE.createXFeatureCall => [
						feature = resource.feature
					]
					feature = typeProvider.findMethod(IntermediateResourceBridge, 'setPropagationResult')
					value = XbaseFactory.eINSTANCE.createXFeatureCall => [
						feature = userExecution.findAttribute('transformationResult')
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
