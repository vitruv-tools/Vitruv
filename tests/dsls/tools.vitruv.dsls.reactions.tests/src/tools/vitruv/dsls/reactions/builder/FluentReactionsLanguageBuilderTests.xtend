package tools.vitruv.dsls.reactions.builder

import org.junit.runner.RunWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.InjectWith
import tools.vitruv.dsls.reactions.tests.ReactionsLanguageInjectorProvider
import com.google.inject.Inject
import org.junit.Test
import tools.vitruv.framework.testutils.domains.AllElementTypesDomainProvider
import static org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.rules.ExpectedException
import tools.vitruv.framework.testutils.domains.AllElementTypes2DomainProvider
import org.eclipse.emf.ecore.EcorePackage
import allElementTypes.AllElementTypesPackage
import allElementTypes2.AllElementTypes2Package
import org.junit.Ignore

@RunWith(XtextRunner)
@InjectWith(ReactionsLanguageInjectorProvider)
class FluentReactionsLanguageBuilderTests {
	static val AllElementTypes = new AllElementTypesDomainProvider().domain
	static val AllElementTypes2 = new AllElementTypes2DomainProvider().domain
	static val Root = AllElementTypesPackage.eINSTANCE.root
	static val NonRoot = AllElementTypesPackage.eINSTANCE.nonRoot
	static val Root2 = AllElementTypes2Package.eINSTANCE.root2
	static val EObject = EcorePackage.eINSTANCE.EObject

	@Rule
	public val ExpectedException thrown = ExpectedException.none()

	@Inject GeneratedReactionsMatcherBuilder matcher
	@Inject FluentReactionsLanguageBuilder create

	@Test
	def void createRoot() {
		val builder = create.reactionsFile('createRootTest') +=
			create.reactionsSegment('simpleChangesRootTests')
				.inReactionToChangesIn(AllElementTypes)
				.executeActionsIn(AllElementTypes) +=
				create.reaction('CreateRootTest')
					.afterElement(Root).created
					.call [
						action [
						vall('newRoot').create(Root)
						addCorrespondenceBetween('newRoot').and.affectedEObject
					]
				]

		val reactionResult = '''
			import "http://tools.vitruv.tests.metamodels.allElementTypes" as allElementTypes
			
			reactions: simpleChangesRootTests
			in reaction to changes in AllElementTypes
			execute actions in AllElementTypes
			
			reaction CreateRootTest {
				after element allElementTypes::Root created
				call createRootTestRepair(affectedEObject)
			}
			
			routine createRootTestRepair(allElementTypes::Root affectedEObject) {
				action {
					val newRoot = create allElementTypes::Root
					add correspondence between newRoot and affectedEObject
				}
			}
		'''

		assertThat(builder, builds(reactionResult))
	}

	@Test
	def void removeRoot() {
		val builder = create.reactionsFile('deleteRootTest') +=
			create.reactionsSegment('simpleChangesRootTests')
				.inReactionToChangesIn(AllElementTypes)
				.executeActionsIn(AllElementTypes) += 
				create.reaction('DeleteRootTest')
					.afterElement(Root).deleted
					.call [
						match [
							vall('toDelete').retrieve(Root).correspondingTo.affectedEObject
						].action [
							delete('toDelete')
						]
					]

		val reactionResult = '''
			import "http://tools.vitruv.tests.metamodels.allElementTypes" as allElementTypes
			
			reactions: simpleChangesRootTests
			in reaction to changes in AllElementTypes
			execute actions in AllElementTypes
			
			reaction DeleteRootTest {
				after element allElementTypes::Root deleted
				call deleteRootTestRepair(affectedEObject)
			}
			
			routine deleteRootTestRepair(allElementTypes::Root affectedEObject) {
				match {
					val toDelete = retrieve allElementTypes::Root corresponding to affectedEObject
				}
				action {
					delete toDelete
				}
			}
		'''

		assertThat(builder, builds(reactionResult))
	}

	@Test
	def void noEmptyReactionsFile() {
		thrown.expectMessage("No reactions segments")
		thrown.expect(IllegalStateException)
		
		val builder = create.reactionsFile('empty')
		matcher.build(builder)
	}
	
	@Test
	def void noEmptyReactionsSegment() {
		thrown.expectMessage("Neither routines nor reactions")
		thrown.expect(IllegalStateException)
		
		val builder = create.reactionsFile('Test') +=
			create.reactionsSegment('empty')
				.inReactionToChangesIn(AllElementTypes)
				.executeActionsIn(AllElementTypes)
		matcher.build(builder)
	}
	
	@Test
	def void routineArgument() {
		val builder = create.reactionsFile('createRootTest') +=
			create.reactionsSegment('simpleChangesRootTests')
				.inReactionToChangesIn(AllElementTypes)
				.executeActionsIn(AllElementTypes) +=
				create.routine('withArguments')
					.input [
						model(Root, 'rootParameter')
						model(NonRoot, 'nonRootParameter')
					]
					.action [
						addCorrespondenceBetween('rootParameter').and('nonRootParameter')
					]
		
		val expectedReaction = '''
			import "http://tools.vitruv.tests.metamodels.allElementTypes" as allElementTypes
			
			reactions: simpleChangesRootTests
			in reaction to changes in AllElementTypes
			execute actions in AllElementTypes
			
			routine withArguments(allElementTypes::Root rootParameter, allElementTypes::NonRoot nonRootParameter) {
				action {
					add correspondence between rootParameter and nonRootParameter
				}
			}
		'''
		
		assertThat(builder, builds(expectedReaction))
	}
	
	@Test
	def void routineForTwoReactionsImplicitlyAdded() {
		val commonRoutine = create.routine('commonRootCreate')
			.input [
				model(EObject, 'affectedEObject')
			]
			.action [
				vall('newRoot').create(Root)
				addCorrespondenceBetween('newRoot').and.affectedEObject
			]
			
		val reactionsFile = create.reactionsFile('createRootTest')
		val reactionsSegment = create.reactionsSegment('simpleChangesRootTests')
				.inReactionToChangesIn(AllElementTypes)
				.executeActionsIn(AllElementTypes) += #[
				create.reaction('CreateRootTest')
					.afterElement(Root).created
					.call(commonRoutine),
				create.reaction('CreateNonRootTest')
					.afterElement(NonRoot).created
					.call(commonRoutine)
			]
					
		reactionsFile += reactionsSegment
		
		val expectedReaction = '''
			import "http://tools.vitruv.tests.metamodels.allElementTypes" as allElementTypes
			import "http://www.eclipse.org/emf/2002/Ecore" as ecore
			
			reactions: simpleChangesRootTests
			in reaction to changes in AllElementTypes
			execute actions in AllElementTypes
			
			reaction CreateRootTest {
				after element allElementTypes::Root created
				call commonRootCreate(affectedEObject)
			}
			
			reaction CreateNonRootTest {
				after element allElementTypes::NonRoot created
				call commonRootCreate(affectedEObject)
			}
			
			routine commonRootCreate(ecore::EObject affectedEObject) {
				action {
					val newRoot = create allElementTypes::Root
					add correspondence between newRoot and affectedEObject
				}
			}
		'''
		
		assertThat(reactionsFile, builds(expectedReaction))
	}
	
	@Test
	def void routineForTwoReactionsExplicitlyAdded() {
		val commonRoutine = create.routine('commonRootCreate')
			.input [
				model(EObject, 'affectedEObject')
			]
			.action [
				vall('newRoot').create(Root)
				addCorrespondenceBetween('newRoot').and.affectedEObject
			]
			
		val reactionsFile = create.reactionsFile('createRootTest')
		val reactionsSegment = create.reactionsSegment('simpleChangesRootTests')
				.inReactionToChangesIn(AllElementTypes)
				.executeActionsIn(AllElementTypes)
				
		reactionsSegment += commonRoutine
		
		reactionsSegment += 
			create.reaction('CreateRootTest')
				.afterElement(Root).created
				.call(commonRoutine)
				
		reactionsSegment += 
			create.reaction('CreateNonRootTest')
				.afterElement(NonRoot).created
				.call(commonRoutine)
					
		reactionsFile += reactionsSegment
		
		val expectedReaction = '''
			import "http://www.eclipse.org/emf/2002/Ecore" as ecore
			import "http://tools.vitruv.tests.metamodels.allElementTypes" as allElementTypes
			
			reactions: simpleChangesRootTests
			in reaction to changes in AllElementTypes
			execute actions in AllElementTypes
			
			reaction CreateRootTest {
				after element allElementTypes::Root created
				call commonRootCreate(affectedEObject)
			}
			
			reaction CreateNonRootTest {
				after element allElementTypes::NonRoot created
				call commonRootCreate(affectedEObject)
			}
			
			routine commonRootCreate(ecore::EObject affectedEObject) {
				action {
					val newRoot = create allElementTypes::Root
					add correspondence between newRoot and affectedEObject
				}
			}
		'''
		
		assertThat(reactionsFile, builds(expectedReaction))
		}
	
	@Test
	@Ignore // not supported from text right now
	def void routineFromDifferentSegmentWithImplicitSegment() {
		val commonRoutine = create.routine('commonRootCreate')
			.input [
				model(EObject, 'affectedEObject')
			]
			.action [
				vall('newRoot').create(Root)
				addCorrespondenceBetween('newRoot').and.affectedEObject
			]
			
		val reactionsFile = create.reactionsFile('createRootTest')
		
		reactionsFile += 
			create.reactionsSegment('simpleChangesRootTests')
				.inReactionToChangesIn(AllElementTypes)
				.executeActionsIn(AllElementTypes) +=
				create.reaction('CreateRootTest')
					.afterElement(Root).created
					.call(commonRoutine)
					
		reactionsFile +=
			create.reactionsSegment('simpleChangesRoot2Tests')
				.inReactionToChangesIn(AllElementTypes2)
				.executeActionsIn(AllElementTypes) +=
				create.reaction('CreateRoot2Test')
					.afterElement(Root2).created
					.call(commonRoutine)
				
		val expectedReaction = '''
			import "http://tools.vitruv.tests.metamodels.allElementTypes" as allElementTypes
			import "http://www.eclipse.org/emf/2002/Ecore" as ecore
			import "http://tools.vitruv.tests.metamodels.allElementTypes2" as allElementTypes2
			
			
			reactions: simpleChangesRootTests
			in reaction to changes in AllElementTypes
			execute actions in AllElementTypes
			
			reaction CreateRootTest {
				after element allElementTypes::Root created
				call commonRootCreate(affectedEObject)
			}
			
			routine commonRootCreate(ecore::EObject affectedEObject) {
				action {
					val newRoot = create allElementTypes::Root
					add correspondence between newRoot and affectedEObject
				}
			}
			
			
			reactions: simpleChangesRoot2Tests
			in reaction to changes in AllElementTypes2
			execute actions in AllElementTypes
			
			reaction CreateRoot2Test {
				after element allElementTypes2::Root2 created
				call commonRootCreate(affectedEObject)
			}
		'''
		
		assertThat(reactionsFile, builds(expectedReaction))
	}
	
	@Test
	@Ignore // not supported from text right now
	def void routineFromDifferentSegmentWithExplicitSegment() {
				val commonRoutine = create.routine('commonRootCreate')
			.input [
				model(EObject, 'affectedEObject')
			]
			.action [
				vall('newRoot').create(Root)
				addCorrespondenceBetween('newRoot').and.affectedEObject
			]
			
		val reactionsFile = create.reactionsFile('createRootTest')
		
		reactionsFile += 
			create.reactionsSegment('simpleChangesRootTests')
				.inReactionToChangesIn(AllElementTypes)
				.executeActionsIn(AllElementTypes) +=
				create.reaction('CreateRootTest')
					.afterElement(Root).created
					.call(commonRoutine)
					
		val secondSegment = 
			create.reactionsSegment('simpleChangesRoot2Tests')
				.inReactionToChangesIn(AllElementTypes2)
				.executeActionsIn(AllElementTypes) +=
				create.reaction('CreateRoot2Test')
					.afterElement(Root2).created
					.call(commonRoutine)
					
		secondSegment += commonRoutine
					
		reactionsFile += secondSegment
			
				
		val expectedReaction = '''
		import "http://tools.vitruv.tests.metamodels.allElementTypes" as allElementTypes
		import "http://tools.vitruv.tests.metamodels.allElementTypes2" as allElementTypes2
		import "http://www.eclipse.org/emf/2002/Ecore" as ecore
		
		
		reactions: simpleChangesRootTests
		in reaction to changes in AllElementTypes
		execute actions in AllElementTypes
		
		reaction CreateRootTest {
			after element allElementTypes::Root created
			call commonRootCreate(affectedEObject)
		}
		
		
		reactions: simpleChangesRoot2Tests
		in reaction to changes in AllElementTypes2
		execute actions in AllElementTypes
		
		reaction CreateRoot2Test {
			after element allElementTypes2::Root2 created
			call commonRootCreate(affectedEObject)
		}
				
		routine commonRootCreate(ecore::EObject affectedEObject) {
			action {
				val newRoot = create allElementTypes::Root
				add correspondence between newRoot and affectedEObject
			}
		}
		'''
		
		assertThat(reactionsFile, builds(expectedReaction))
	}

	
	def private builds(String reactionsTest) {
		matcher.builds(reactionsTest)
	}
}