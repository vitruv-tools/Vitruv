package tools.vitruv.dsls.commonalities.generator.reactions.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collection
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper.*

@Utility
class XbaseCollectionHelper {

	static def addToCollection(extension TypeProvider typeProvider, XExpression collection, XExpression newValue) {
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = collection
			feature = typeProvider.findMethod(CollectionExtensions, 'operator_add', Collection, typeVariable)
			rightOperand = newValue
		]
	}

	static def addAllToCollection(extension TypeProvider typeProvider, XExpression collection, XExpression newValues) {
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = collection
			feature = typeProvider.findMethod(CollectionExtensions, 'operator_add', Collection, Iterable)
			rightOperand = newValues
		]
	}

	static def removeFromCollection(extension TypeProvider typeProvider, XExpression collection, XExpression newValue) {
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = collection
			feature = typeProvider.findMethod(CollectionExtensions, 'operator_remove', Collection, typeVariable)
			rightOperand = newValue
		]
	}

	static def clearCollection(extension TypeProvider typeProvider, XExpression collection) {
		collection.memberFeatureCall => [
			feature = typeProvider.findMethod(Collection, 'clear')
		]
	}
}
