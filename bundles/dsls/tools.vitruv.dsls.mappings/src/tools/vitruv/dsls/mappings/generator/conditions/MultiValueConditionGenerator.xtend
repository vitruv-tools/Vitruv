package tools.vitruv.dsls.mappings.generator.conditions

import java.util.List
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AttributeReplacedReactionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.DeletedReactionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.ElementReplacedReactionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.InsertedReactionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.RemovedReactionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.FeatureCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueConditionOperator
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static extension tools.vitruv.dsls.mappings.generator.conditions.FeatureConditionGeneratorUtils.*
import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodFinder.*
import tools.vitruv.dsls.mappings.generator.utils.MappingParameterScopeFinder
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter

class MultiValueConditionGenerator extends AbstractSingleSidedCondition<MultiValueCondition> implements FeatureConditionGenerator {

	private MultiValueConditionOperator operator
	private EObject leftSide
	private MetaclassFeatureReference rightSide
	private FeatureCondition featureCondition

	new(MultiValueCondition condition) {
		super(condition)
		operator = condition.operator
		featureCondition = getFeatureCondition(condition)
		leftSide = featureCondition.left
		rightSide = featureCondition.rightFeatureReference
	}

	override feasibleForGenerator(AbstractReactionTypeGenerator generator) {
		rightSide.metaclass == generator.metaclass
	}

	override feasibleForParameter(MappingParameter parameter) {
		// atm we only generate conditions for equals
		if(operator != MultiValueConditionOperator.EQUALS) return false
		featureCondition.feature.parameter == parameter
	}

	override protected constructReactionTriggers(List<AbstractReactionTypeGenerator> triggers) {
		if (operator == MultiValueConditionOperator.IN) {
			if (leftSide instanceof MetaclassReference) {
				// check if its a collection or just a normal element
				if (rightSide.feature.many) {
					if (condition.negated === null) {
						// only create reaction triggers for not negated in conditions
						triggers.add(new InsertedReactionGenerator(leftSide, rightSide))
					//	triggers.add(new RemovedReactionGenerator(leftSide, rightSide))
					}
					//triggers.add(new DeletedReactionGenerator(leftSide))
				} else {
					triggers.add(new ElementReplacedReactionGenerator(rightSide, leftSide));
				}
			}
		}
		if (rightSide.feature instanceof EAttribute) {
//			triggers.add(new AttributeReplacedReactionGenerator(rightSide))
		}
	}

	override generate(UndecidedMatcherStatementBuilder builder) {
		// its a feature condition so this wont get called
	}

	override generateFeatureCondition(RoutineTypeProvider provider) {
		var negated = condition.negated !== null
		if (operator == MultiValueConditionOperator.EQUALS) {
			provider.generateEquals(negated)
		} else if (operator == MultiValueConditionOperator.IN) {
			// no implementation atm
		}
	}

	private def generateEquals(RoutineTypeProvider typeProvider, boolean negated) {
		// builder.builder.correspondingMethodParameter()
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = typeProvider.generateLeftFeatureConditionValue(featureCondition)
			rightOperand = typeProvider.generateRightSideCall
			if (negated) {
				feature = typeProvider.tripleNotEquals
			} else {
				feature = typeProvider.tripleEquals
			}
		]
	}

	private def generateRightSideCall(RoutineTypeProvider typeProvider) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			explicitOperationCall = true
			memberCallTarget = typeProvider.affectedEObject
			feature = typeProvider.findMetaclassMethod(rightSide)
		]
	}

}
