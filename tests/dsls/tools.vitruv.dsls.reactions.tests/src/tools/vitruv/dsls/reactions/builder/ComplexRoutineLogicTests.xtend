package tools.vitruv.dsls.reactions.builder

import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.xbase.XbaseFactory
import org.junit.Test
import org.junit.runner.RunWith
import tools.vitruv.dsls.reactions.tests.ReactionsLanguageInjectorProvider
import static org.hamcrest.MatcherAssert.assertThat
import org.eclipse.xtext.common.types.TypesFactory

@RunWith(XtextRunner)
@InjectWith(ReactionsLanguageInjectorProvider)
class ComplexRoutineLogicTests extends FluentReactionsLanguageBuilderTests {

	@Test
	def void ts() {
		val builder = create.reactionsFile('createRootTest') +=
			create.reactionsSegment('simpleChangesRootTests').inReactionToChangesIn(AllElementTypes).
				executeActionsIn(AllElementTypes) += create.reaction('CreateRootTest').afterElement(Root).created.call [
				action [
					execute[ provider |
						XbaseFactory.eINSTANCE.createXBlockExpression => [
							/*val firstValue= XbaseFactory.eINSTANCE.createXVariableDeclaration => [
								name = 'a'
								right = XbaseFactory.eINSTANCE.createXNumberLiteral => [
									value = '20'
								]
								writeable = true
							]
							expressions += firstValue*/
							expressions += XbaseFactory.eINSTANCE.createXForLoopExpression => [
								it.declaredParam = TypesFactory.eINSTANCE.createJvmFormalParameter => [
									name = 'b'
								]
								it.forExpression = XbaseFactory.eINSTANCE.createXListLiteral => [
									it.elements += XbaseFactory.eINSTANCE.createXNumberLiteral => [
										value = '10'
									]
								]
								it.eachExpression = XbaseFactory.eINSTANCE.createXBlockExpression => [
									expressions += XbaseFactory.eINSTANCE.createXAssignment => [
										feature = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
											name = 'b'
										]
										value = XbaseFactory.eINSTANCE.createXNumberLiteral => [
											value = '20'
										]
									]
								]
							]
						/* 
						 expressions += */
						]
					]
				]
			]

		val reactionResult = '''
			import "http://tools.vitruv.testutils.metamodels.allElementTypes" as allElementTypes
			
			reactions: simpleChangesRootTests in reaction to changes in AllElementTypes
			execute actions in AllElementTypes
			
			reaction CreateRootTest {
				after element allElementTypes::Root created
				call createRootTestRepair()
			}
			
			routine createRootTestRepair() {
				action {
					execute {
						for ( b : #[10]){ b = 20}
					}	
				}
			}
		'''

		assertThat(builder, builds(reactionResult))
	}

}
