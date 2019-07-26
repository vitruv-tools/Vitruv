package tools.vitruv.dsls.mappings.tests

import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.uml2.uml.UMLFactory
import org.junit.Test
import tools.vitruv.dsls.mappings.generator.conditions.FeatureConditionGenerator
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterGraphTraverser
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterGraphTraverser.NodePath
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterGraphTraverser.TraverseStep
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterGraphTraverser.TraverseStepDown
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterGraphTraverser.TraverseStepUp
import tools.vitruv.dsls.mappings.generator.conditions.impl.InValueConditionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsLanguageFactory
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueConditionOperator
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory

import static org.junit.Assert.assertEquals
import static org.junit.Assert.fail

class MappingParameterGraphTraverserTest {

	private List<String> parameter
	private List<FeatureConditionGenerator> inConditions
	private MappingParameterGraphTraverser traverser
	private Map<String, EStructuralFeature> testFeatures = // extension is the only not containment reference
	#{'cFA' -> testFeature('ownedOperation'), 'cFB' -> testFeature('nestedClassifier'), 'cFC' ->
		testFeature('ownedReception'), 'ncF' -> testFeature('extension')}

	@Test
	def void unconnectedNodes() {
		createParams('nodeA', 'nodeB')
		createInConditions()
		try {
			initTraverser
			fail()
		} catch (IllegalStateException exception) {
			assertEquals('A MappingParameter cannot be reached by the other nodes, graph is invalid!',
				exception.message)
		}
	}
	
	@Test
	def void minimalGraph() {
		createParams('nodeA')
		createInConditions()
		initTraverser
	}

	@Test
	def void cyclicDetection() {
		/**
		 *       nodeB
		 *   cFA/      \cFB
		 *  nodeA  -   nodeC 
		 *         cFC
		 */
		createParams('nodeA', 'nodeB', 'nodeC')
		createInConditions('nodeA'.in('nodeB', 'cFA'), 'nodeB'.in('nodeC', 'cFB'), 'nodeC'.in('nodeA', 'cFC'))
		try {
			initTraverser
			fail() 
		} catch (IllegalStateException exception) {
			assertEquals('The MappingParameters in-relations contains cycles!', exception.message)
		}
	}

	@Test
	def void multipleParents() {
		/**
		 *  nodeB	nodeC
		 *   cFA\	 /ncF
		 * 	     nodeA  
		 */
		createParams('nodeA', 'nodeB', 'nodeC')
		createInConditions('nodeA'.in('nodeB', 'cFA'), 'nodeA'.in('nodeC', 'ncF'))
		initTraverser
	}

	@Test
	def void multipleContainmentParents() {
		/**
		 *  nodeB	nodeC
		 *   cFA\	 /cFB
		 * 	     nodeA  
		 */
		createParams('nodeA', 'nodeB', 'nodeC')
		createInConditions('nodeA'.in('nodeB', 'cFA'), 'nodeA'.in('nodeC', 'cFB'))
		try {
			initTraverser
			fail()
		} catch (IllegalStateException exception) {
			assertEquals('A MappingParameter can only be contained by one containment-reference!', exception.message)
		}
	}

	@Test
	def void correctPaths() {
		/**
		 *    nodeA
		 * cFA/     \cFB
		 * nodeB   nodeC
		 *           \cFC
		 *           nodeD
		 */
		createParams('nodeA', 'nodeB', 'nodeC', 'nodeD')
		createInConditions('nodeB'.in('nodeA', 'cFA'), 'nodeC'.in('nodeA', 'cFB'), 'nodeD'.in('nodeC', 'cFC'))
		initTraverser
		findPath('nodeA' -> 'nodeB').assertPath(stepDown('nodeB', 'cFA'))
		findPath('nodeB' -> 'nodeA').assertPath(stepUp('nodeA', 'cFA'))
		findPath('nodeA' -> 'nodeD').assertPath(stepDown('nodeC', 'cFB'), stepDown('nodeD', 'cFC'))
		findPath('nodeC' -> 'nodeB').assertPath(stepUp('nodeA', 'cFB'), stepDown('nodeB', 'cFA'))
	}

	@Test
	def void shortestPaths() {
		/**
		 *      nodeA
		 *  cFA/     \ncF
		 * nodeB   nodeC
		 *       cFC/   \cFB
		 *      nodeE   nodeD
		 * 		/ncF
		 * 	  nodeF	 
		 */
		createParams('nodeA', 'nodeB', 'nodeC', 'nodeD', 'nodeE', 'nodeF')
		createInConditions('nodeB'.in('nodeA', 'cFA'), 'nodeC'.in('nodeA', 'ncF'), 'nodeD'.in('nodeC', 'cFB'),
			'nodeE'.in('nodeC', 'cFC'), 'nodeF'.in('nodeE', 'ncF'))
		initTraverser
		findPath(#['nodeD'], 'nodeF').assertPath(stepUp('nodeC', 'cFB'), stepDown('nodeE', 'cFC'),
			stepDown('nodeF', 'ncF'))
		findPath(#['nodeD', 'nodeB'], 'nodeF').assertPath(stepUp('nodeC', 'cFB'), stepDown('nodeE', 'cFC'),
			stepDown('nodeF', 'ncF'))
		findPath(#['nodeD', 'nodeB', 'nodeC'], 'nodeF').assertPath(stepDown('nodeE', 'cFC'), stepDown('nodeF', 'ncF'))
	}

	@Test
	def void shortestNextNode() {
		/**
		 *    nodeA
		 * cFA/     \cFB
		 * nodeB   nodeC
		 *           \cFC
		 *           nodeD
		 */
		createParams('nodeA', 'nodeB', 'nodeC', 'nodeD')
		createInConditions('nodeB'.in('nodeA', 'cFA'), 'nodeC'.in('nodeA', 'cFB'), 'nodeD'.in('nodeC', 'cFC'))
		initTraverser
		// start with C, next node could be A or D, both would be right, the implementation goes to D
		traverser.findStepToNextNode(#['nodeC']).assertPathFrom('nodeC', stepDown('nodeD', 'cFC'))
		// now the only correct way is to go to A next
		traverser.findStepToNextNode(#['nodeC', 'nodeD']).assertPathFrom('nodeC', stepUp('nodeA', 'cFB'))
		// the last remaining step is to go to B
		traverser.findStepToNextNode(#['nodeC', 'nodeD', 'nodeA']).assertPathFrom('nodeA', stepDown('nodeB', 'cFA'))
	}

	private def stepDown(String to, String feature) {
		new TraverseStepDown(to, testFeatures.get(feature) as EReference)
	}

	private def stepUp(String to, String feature) {
		new TraverseStepUp(to, testFeatures.get(feature) as EReference)
	}

	private def assertStep(TraverseStep actualStep, TraverseStep expectedStep) {
		assertEquals('Wrong traverse step node!', expectedStep.parameter, actualStep.parameter)
		assertEquals('Wrong traverse step feature!', expectedStep.feature, actualStep.feature)
		assertEquals('Wrong traverse step type!', expectedStep.class, actualStep.class)
	}

	private def assertPath(NodePath path, TraverseStep... steps) {
		println('''Expected Path: «steps.printPath»''')
		println('''Actual Path: «path.steps.printPath»''')
		assertEquals('Path length is wrong!', steps.length, path.steps.length)
		for (var i = 0; i < steps.length; i++) {
			val expectedStep = steps.get(i)
			val actualStep = path.steps.get(i)
			actualStep.assertStep(expectedStep)
		}
	}

	private def assertPathFrom(NodePath path, String from, TraverseStep... steps) {
		assertEquals('Start-node of path is wrong!', from, path.startNode)
		path.assertPath(steps)
	}

	private def printPath(
		List<TraverseStep> steps) '''«FOR step : steps» step «IF step instanceof TraverseStepDown»down «ELSE»up «ENDIF»to «step.parameter»   «ENDFOR»'''

	private def findPath(Pair<String, String> fromTo) {
		traverser.findPath(fromTo.key, fromTo.value)
	}

	private def findPath(List<String> startPoints, String to) {
		traverser.findPath(startPoints, to)
	}

	private def initTraverser() {
		traverser = new MappingParameterGraphTraverser(inConditions, parameter)
	}

	private def in(String child, String parent, String feature) {
		val multiValue = MappingsLanguageFactory.eINSTANCE.createMultiValueCondition => [
			operator = MultiValueConditionOperator.IN
		]
		MappingsLanguageFactory.eINSTANCE.createFeatureCondition => [
			left = MappingsLanguageFactory.eINSTANCE.createMappingParameterReference => [
				parameter = child.toParameter
			]
			condition = multiValue
			feature = MappingsLanguageFactory.eINSTANCE.createFeatureConditionParameter => [
				parameter = parent.toParameter
				it.feature = testFeatures.get(feature)
			]
		]
		new InValueConditionGenerator(multiValue)
	}

	private def createInConditions(InValueConditionGenerator... generators) {
		this.inConditions = generators
	}

	private def toParameter(String param) {
		MappingsLanguageFactory.eINSTANCE.createMappingParameter => [
			value = MirBaseFactory.eINSTANCE.createNamedMetaclassReference => [
				name = param
			]
		]
	}

	private def EReference testFeature(String name) {
		val umlClass = UMLFactory.eINSTANCE.createClass
		umlClass.eClass.EStructuralFeatures.findFirst[it.name == name] as EReference
	}

	private def createParams(String... params) {
		parameter = params
	}
}
