package tools.vitruv.dsls.reactions.builder

import org.eclipse.xtext.common.types.TypesFactory
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.reactions.tests.ReactionsLanguageInjectorProvider

import static org.hamcrest.MatcherAssert.assertThat
import allElementTypes.AllElementTypesPackage
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.junit.jupiter.api.^extension.ExtendWith
import org.junit.jupiter.api.Test

@ExtendWith(InjectionExtension)
@InjectWith(ReactionsLanguageInjectorProvider)
class ComplexRoutineLogicTests extends FluentReactionsBuilderTest {

@Test
	def void ts() {
		val builder = create.reactionsFile('createRootTest') +=
			create.reactionsSegment('simpleChangesRootTests').inReactionToChangesIn(AllElementTypesPackage.eINSTANCE).
				executeActionsIn(AllElementTypesPackage.eINSTANCE) += create.reaction('CreateRootTest').afterElement(Root).created.call [
				action [
					execute[ provider |
						XbaseFactory.eINSTANCE.createXBlockExpression => [
							val loopVariable = TypesFactory.eINSTANCE.createJvmFormalParameter => [
									name = 'b'
								]
							expressions += XbaseFactory.eINSTANCE.createXForLoopExpression => [
								it.declaredParam = loopVariable
								it.forExpression = XbaseFactory.eINSTANCE.createXListLiteral => [
									it.elements += XbaseFactory.eINSTANCE.createXNumberLiteral => [
										value = '10'
									]
								]
								it.eachExpression = XbaseFactory.eINSTANCE.createXBlockExpression => [
									expressions += XbaseFactory.eINSTANCE.createXAssignment => [
										feature = loopVariable
										value = XbaseFactory.eINSTANCE.createXNumberLiteral => [
											value = '20'
										]
									]
								]
							]
						]
					]
				]
			]

		val reactionResult = '''
			import "http://tools.vitruv.testutils.metamodels.allElementTypes" as allElementTypes
			
			reactions: simpleChangesRootTests in reaction to changes in allElementTypes
			execute actions in allElementTypes
			
			reaction CreateRootTest {
				after element allElementTypes::Root created
				call createRootTestRepair()
			}
			
			routine createRootTestRepair() {
				action {
					execute {
						for ( b : # [ 10 ] ) { b = 20 }
					}	
				}
			}
		'''

		assertThat(builder, builds(reactionResult))
	}

}
