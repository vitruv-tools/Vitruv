package tools.vitruv.dsls.mappings.generator.conditions

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.mappingsLanguage.FeatureCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.NullValue
import tools.vitruv.dsls.mappings.mappingsLanguage.StringValue
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider

class FeatureConditionGeneratorUtils{
	
	def static FeatureCondition  getFeatureCondition(EObject childCondition){
		childCondition.eContainer	as FeatureCondition 	
	}
	
	def static generateLeftFeatureConditionValue(RoutineTypeProvider typeProvider, FeatureCondition featureCondition){
		val leftSide = featureCondition.left
		if(leftSide instanceof NullValue){
			return XbaseFactory.eINSTANCE.createXNullLiteral
		}
		else if(leftSide instanceof StringValue){
			return XbaseFactory.eINSTANCE.createXStringLiteral => [
				value = leftSide.value
			]
		}
		else if(leftSide instanceof MetaclassReference){
			//val package = leftSide.metaclass.class.package.name
			val className = leftSide.metaclass.class.name
			return XbaseFactory.eINSTANCE.createXTypeLiteral => [
				type = typeProvider.findTypeByName(className)
			]
		}
	}
	
}