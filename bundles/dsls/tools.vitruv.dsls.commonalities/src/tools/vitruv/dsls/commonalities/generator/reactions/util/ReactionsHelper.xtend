package tools.vitruv.dsls.commonalities.generator.reactions.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.TriggerBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper
import tools.vitruv.dsls.reactions.reactionsLanguage.ExecuteActionStatement
import tools.vitruv.dsls.reactions.reactionsLanguage.RoutineCallStatement
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage

import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper.*

@Utility
class ReactionsHelper {

	// This takes ReferenceClassNameAdapters into account. This is necessary
	// for the retrieval of instance class names for EClasses of the generated
	// intermediate models, because the generated EPackage code hasn't been
	// compiled and initialized yet.
	static def String getJavaClassName(EClassifier classifier) {
		return ReactionsLanguageHelper.getJavaClassName(classifier)
	}

	// Picks the correct trigger depending on whether the given change class
	// belongs to a commonality or a regular participation class
	static def afterElementInsertedAsRoot(TriggerBuilder reactionTriggerBuilder, EClass changeClass) {
		if (changeClass.ESuperTypes.contains(IntermediateModelBasePackage.eINSTANCE.intermediate)) {
			// commonality class:
			reactionTriggerBuilder.afterElement(changeClass).insertedIn(
				IntermediateModelBasePackage.eINSTANCE.root_Intermediates)
		} else {
			// non-commonality participation class:
			reactionTriggerBuilder.afterElement(changeClass).insertedAsRoot
		}
	}

	// Picks the correct trigger depending on whether the given change class
	// belongs to a commonality or a regular participation class
	static def afterElementRemovedAsRoot(TriggerBuilder reactionTriggerBuilder, EClass changeClass) {
		if (changeClass.ESuperTypes.contains(IntermediateModelBasePackage.eINSTANCE.intermediate)) {
			// commonality class:
			reactionTriggerBuilder.afterElement(changeClass).removedFrom(
				IntermediateModelBasePackage.eINSTANCE.root_Intermediates)
		} else {
			// non-commonality participation class:
			reactionTriggerBuilder.afterElement(changeClass).removedAsRoot
		}
	}

	static def callGetMetadataModelURI(extension TypeProvider typeProvider, Iterable<String> metadataKey) {
		resourceAccess.memberFeatureCall => [
			feature = resourceAccessType.findMethod('getMetadataModelURI')
			explicitOperationCall = true
			memberCallArguments += metadataKey.map[stringLiteral]
		]
	}

	static def callGetModelResource(extension TypeProvider typeProvider, XFeatureCall vuri) {
		resourceAccess.memberFeatureCall => [
			feature = resourceAccessType.findMethod('getModelResource')
			explicitOperationCall = true
			memberCallArguments += vuri
		]
	}

	/**
	 * Keeps track of the calling routine and the caller context.
	 * <p>
	 * This is useful when making routine calls from within execution code
	 * blocks.
	 */
	static class RoutineCallContext {

		var FluentRoutineBuilder caller
		var XExpression callerContext

		new() {
		}

		def setCaller(FluentRoutineBuilder caller) {
			this.caller = caller
			return caller
		}

		def setCallerContext(RoutineCallStatement callerContext) {
			this.callerContext = callerContext?.code
			return callerContext
		}

		def setCallerContext(ExecuteActionStatement callerContext) {
			this.callerContext = callerContext?.code
			return callerContext
		}

		private def checkReady() {
			if (caller === null) {
				throw new IllegalStateException("Caller has not been set yet for the RoutineCallContext!")
			}
			if (callerContext === null) {
				throw new IllegalStateException("CallerContext has not been set yet for the RoutineCallContext!")
			}
			return this
		}
	}

	static def createRoutineCall(RoutineCallContext callerContext,
		TypeProvider typeProvider, FluentRoutineBuilder routine, XExpression... parameters) {
		callerContext.checkReady()
		return createRoutineCall(callerContext.caller, callerContext.callerContext, typeProvider, routine, parameters)
	}

	static def createRoutineCall(FluentRoutineBuilder caller, RoutineCallStatement callerContext,
		TypeProvider typeProvider, FluentRoutineBuilder routine, XExpression... parameters) {
		return createRoutineCall(caller, callerContext.code, typeProvider, routine, parameters)
	}

	static def createRoutineCall(FluentRoutineBuilder caller, ExecuteActionStatement callerContext,
		TypeProvider typeProvider, FluentRoutineBuilder routine, XExpression... parameters) {
		return createRoutineCall(caller, callerContext.code, typeProvider, routine, parameters)
	}

	static def createRoutineCall(FluentRoutineBuilder caller, XExpression callerContext,
		TypeProvider typeProvider, FluentRoutineBuilder routine, XExpression... parameters) {
		return XbaseFactory.eINSTANCE.createXFeatureCall => [
			explicitOperationCall = true
			feature = routine.jvmOperation
			implicitReceiver = caller.getJvmOperationRoutineFacade(callerContext)
			featureCallArguments += parameters
		]
	}
}
