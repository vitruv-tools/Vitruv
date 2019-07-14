package tools.vitruv.dsls.mappings.generator.conditions

import java.util.List
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AttributeReplacedReactionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.InsertedReactionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.RemovedReactionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueConditionOperator
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder
import tools.vitruv.dsls.mappings.generator.reactions.DeletedReactionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.ElementReplacedReactionGenerator

class MultiValueConditionGenerator extends AbstractSingleSidedCondition<MultiValueCondition> {

	private MultiValueConditionOperator operator
	private EObject leftSide
	private MetaclassFeatureReference rightSide

	new(MultiValueCondition condition) {
		super(condition)
		operator = condition.operator
		val featureCondition = FeatureConditionGeneratorUtils.getFeatureCondition(condition)
		leftSide = featureCondition.left
		rightSide = featureCondition.feature
	}
	
	override feasibleForGenerator(AbstractReactionTypeGenerator generator) {
		rightSide.metaclass == generator.metaclass
	}
		
	override protected constructReactionTriggers(List<AbstractReactionTypeGenerator> triggers) {
		if (operator == MultiValueConditionOperator.IN) {
			if(leftSide instanceof MetaclassReference){
				//check if its a collection or just a normal element
				if(rightSide.feature.many)
				{
					triggers.add(new InsertedReactionGenerator(leftSide, rightSide))
					triggers.add(new RemovedReactionGenerator(leftSide, rightSide))		
					triggers.add(new DeletedReactionGenerator(leftSide))				
				}
				else{
					triggers.add(new ElementReplacedReactionGenerator(rightSide, leftSide));
				}
			}
		}
		if(rightSide.feature instanceof EAttribute){
			triggers.add(new AttributeReplacedReactionGenerator(rightSide))			
		}
	}

	override generate(UndecidedMatcherStatementBuilder builder) {
		var negated = false
		if (condition.negated !== null) {
			// negated
			negated = true
		}
		if (operator == MultiValueConditionOperator.EQUALS) {
			builder.generateEquals(leftSide, rightSide, negated)
		}
	}

	private def generateEquals(UndecidedMatcherStatementBuilder builder, EObject leftSide,
		MetaclassFeatureReference rightSide, boolean negated) {
	/*  builder.vall('''test''').retrieve(rightSide.metaclass).correspondingTo.affectedEObject	
		builder.check[typeProvider |
			//typeProvider.variable("test")
			val eobject = typeProvider.affectedEObject
		//	println('''>>>>>>>>>>>>>>>>>>>>>>> «eobject»   «leftSide» == «rightSide»''')
	 		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = null
			feature = null
			explicitOperationCall = true
			]
			
		]*/
	}
	
	

}
