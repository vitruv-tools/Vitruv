package tools.vitruv.dsls.reactions.builder

import org.eclipse.emf.ecore.EClass
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.TypesFactory
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.xbase.XbaseFactory
import org.junit.Test
import org.junit.runner.RunWith
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.dsls.reactions.tests.ReactionsLanguageInjectorProvider

import static org.hamcrest.MatcherAssert.assertThat

@RunWith(XtextRunner)
@InjectWith(ReactionsLanguageInjectorProvider)
class ComplexRoutineLogicTests extends FluentReactionsBuilderTest {

	private def findMethod(RoutineTypeProvider provider, EClass metaclass, String method) {
		val package = metaclass.instanceTypeName
		val type = provider.findTypeByName(package) as JvmDeclaredType
		type.members.findFirst[simpleName == method]
	}

	@Test
	def void ts() {
		val builder = create.reactionsFile('createRootTest') +=
			create.reactionsSegment('simpleChangesRootTests').inReactionToChangesIn(AllElementTypes).
				executeActionsIn(AllElementTypes) += create.reaction('CreateRootTest').afterElement(Root).created.call [
				input[affectedEObject.apply(Root)]
				action [
					execute[ provider |
						XbaseFactory.eINSTANCE.createXBlockExpression => [
							/*val firstValue= XbaseFactory.eINSTANCE.createXVariableDeclaration => [
							 * 	name = 'a'
							 * 	right = XbaseFactory.eINSTANCE.createXNumberLiteral => [
							 * 		value = '20'
							 * 	]
							 * 	writeable = true
							 * ]
							 expressions += firstValue*/
							val varA = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
								name = 'varA'
								writeable = true
								right = XbaseFactory.eINSTANCE.createXNumberLiteral => [
									value = ' 10'
								]
							]
							expressions += varA
							expressions += XbaseFactory.eINSTANCE.createXAssignment => [
								feature = TypesFactory.eINSTANCE.createJvmFormalParameter => [
									name = 'varA'
								]
								value = XbaseFactory.eINSTANCE.createXNumberLiteral => [
									value = ' 15'
								]
							]

//							val loopVariable = TypesFactory.eINSTANCE.createJvmFormalParameter => [
//								name = '_references'
//							]
//							expressions += XbaseFactory.eINSTANCE.createXVariableDeclaration => [
//								name = '_references'
//								right = XbaseFactory.eINSTANCE.createXFeatureCall => [
//									feature = loopVariable
//								]
//							]
//							expressions += XbaseFactory.eINSTANCE.createXForLoopExpression => [
//								it.declaredParam = loopVariable
//								it.forExpression = XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
//									memberCallTarget = provider.variable('affectedEObject')
//									//feature = provider.findMethod(Root, 'getMultiValuedContainmentEReference')
//									feature = TypesFactory.eINSTANCE.createJv => [
//										name = 'multiValuedContainmentEReference'
//									]
//								]
//								it.eachExpression = XbaseFactory.eINSTANCE.createXBlockExpression => [
//									/*expressions += XbaseFactory.eINSTANCE.createXAssignment => [
//										feature = loopVariable
//										value = XbaseFactory.eINSTANCE.createXNumberLiteral => [
//											value = '20'
//										]
//									]*/
//								]
//							]
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
				call createRootTestRepair(affectedEObject)
			}
			
			routine createRootTestRepair(allElementTypes::Root affectedEObject) {
				action {
					execute {
						var varA = 10
						varA = 15
					}	
				}
			}
		'''

		assertThat(builder, builds(reactionResult))
	}

}
