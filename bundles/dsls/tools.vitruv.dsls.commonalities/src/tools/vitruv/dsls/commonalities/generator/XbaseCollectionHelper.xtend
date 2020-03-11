package tools.vitruv.dsls.commonalities.generator

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collection
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*

@Utility
package class XbaseCollectionHelper {

	def static addToCollection(extension TypeProvider typeProvider, XExpression collection, XExpression newValue) {
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = collection
			feature = typeProvider.findMethod(CollectionExtensions, 'operator_add', Collection, typeVariable)
			rightOperand = newValue
		]
	}

	def static addAllToCollection(extension TypeProvider typeProvider, XExpression collection, XExpression newValues) {
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = collection
			feature = typeProvider.findMethod(CollectionExtensions, 'operator_add', Collection, Iterable)
			rightOperand = newValues
		]
	}

	def static removeFromCollection(extension TypeProvider typeProvider, XExpression collection, XExpression newValue) {
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = collection
			feature = typeProvider.findMethod(CollectionExtensions, 'operator_remove', Collection, typeVariable)
			rightOperand = newValue
		]
	}

	def static clearCollection(extension TypeProvider typeProvider, XExpression collection) {
		collection.memberFeatureCall => [
			feature = typeProvider.findMethod(Collection, 'clear')
		]
	}
}
