package tools.vitruv.dsls.commonalities.generator.reactions.participation

import java.util.Map
import java.util.function.Function
import java.util.function.Supplier
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XIfExpression
import org.eclipse.xtext.xbase.XVariableDeclaration
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.participation.ParticipationContext.ContextClass
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.matching.ParticipationObjects

import static tools.vitruv.framework.util.XtendAssertHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.participation.ParticipationContextHelper.*

/**
 * Helper methods related to interfacing with the {@link ParticipationObjects}
 * during runtime.
 */
class ParticipationObjectsHelper extends ReactionsGenerationHelper {

	package new() {
	}

	def XExpression getParticipationObject(ContextClass contextClass, XFeatureCall participationObjects,
		TypeProvider typeProvider) {
		return getParticipationObject(contextClass.participationClass, contextClass.name, participationObjects, typeProvider)
	}

	def XExpression getParticipationObject(ParticipationClass participationClass, XFeatureCall participationObjects,
		TypeProvider typeProvider) {
		return getParticipationObject(participationClass, participationClass.name, participationObjects, typeProvider)
	}

	private def XExpression getParticipationObject(ParticipationClass participationClass, String objectName,
		XFeatureCall participationObjects, TypeProvider typeProvider) {
		participationObjects.memberFeatureCall => [
			feature = typeProvider.findDeclaredType(ParticipationObjects).findMethod('getObject', String)
			typeArguments += typeProvider.jvmTypeReferenceBuilder.typeRef(participationClass.changeClass.javaClassName)
			memberCallArguments += stringLiteral(objectName)
			explicitOperationCall = true
		]
	}

	def Map<ParticipationClass, XVariableDeclaration> getParticipationObjectVars(Participation participation,
		XFeatureCall participationObjects, extension TypeProvider typeProvider) {
		return participation.allClasses.toInvertedMap [ participationClass |
			XbaseFactory.eINSTANCE.createXVariableDeclaration => [
				type = jvmTypeReferenceBuilder.typeRef(participationClass.changeClass.javaClassName)
				name = participationClass.correspondingVariableName
				// Note: The variable may be null if there is no object for the participation class in the current
				// participation context (eg. for root classes in non-root context).
				right = participationClass.getParticipationObject(participationObjects.copy, typeProvider)
			]
		]
	}

	def XExpression ifParticipationObjectsAvailable(extension TypeProvider typeProvider,
		Iterable<ParticipationClass> participationClasses,
		Function<ParticipationClass, XExpression> participationClassToObject, Supplier<XExpression> then) {
		// Since participations may exist in different contexts with different root objects, the participation objects
		// may not be available for root participation classes:
		val rootParticipationClasses = participationClasses.filter[isRootClass]
		if (rootParticipationClasses.empty) {
			// If no root participation classes are involved, we can skip these checks:
			return then.get
		}

		var XIfExpression rootIfExpr = null
		var XIfExpression currentIfExpr = null
		for (ParticipationClass participationClass : rootParticipationClasses) {
			val participationObject = participationClassToObject.apply(participationClass)
			val ifExpr = XbaseFactory.eINSTANCE.createXIfExpression => [
				^if = participationObject.notEqualsNull(typeProvider)
			]
			if (rootIfExpr === null) {
				rootIfExpr = ifExpr
			} else {
				assertTrue(currentIfExpr !== null)
				currentIfExpr.then = ifExpr
			}
			currentIfExpr = ifExpr
		}

		assertTrue(rootIfExpr !== null)
		assertTrue(currentIfExpr !== null)
		currentIfExpr.then = then.get
		return rootIfExpr
	}
}
