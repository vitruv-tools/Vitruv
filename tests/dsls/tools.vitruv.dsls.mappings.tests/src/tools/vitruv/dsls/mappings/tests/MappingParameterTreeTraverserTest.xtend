package tools.vitruv.dsls.mappings.tests

import java.util.List
import org.junit.Test
import tools.vitruv.dsls.mappings.generator.conditions.FeatureConditionGenerator
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterTreeTraverser
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterTreeTraverser.NodePath
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterTreeTraverser.TraverseStep
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterTreeTraverser.TraverseStepDown
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterTreeTraverser.TraverseStepUp
import tools.vitruv.dsls.mappings.generator.conditions.impl.InValueConditionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsLanguageFactory
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueConditionOperator
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory

import static org.junit.Assert.assertEquals
import static org.junit.Assert.fail

class MappingParameterTreeTraverserTest {

	private List<String> parameter
	private List<FeatureConditionGenerator> inConditions
	private MappingParameterTreeTraverser traverser

	@Test
	def void multipleTopLevelNodesDetection() {
		createParams('nodeA', 'nodeB')
		createInConditions()
		try {
			initTraverser
			fail()
		} catch (IllegalStateException exception) {
			assertEquals('The MappingParameters in-relations do not resolve to a tree structure!', exception.message)
		}
	}

	@Test
	def void cyclicDetection() {
		/**
		 *      nodeB
		 *     /      \
		 *  nodeA  - nodeC 
		 */
		createParams('nodeA', 'nodeB', 'nodeC')
		createInConditions('nodeA' -> 'nodeB', 'nodeB' -> 'nodeC', 'nodeC' -> 'nodeA')
		try {
			initTraverser
			fail()
		} catch (IllegalStateException exception) {
			assertEquals('The MappingParameters in-relations contains cycles!', exception.message)
		}
	}

	@Test
	def void correctPaths() {
		/**
		 *    nodeA
		 *   /     \
		 * nodeB   nodeC
		 *           \
		 *           nodeD
		 */
		createParams('nodeA', 'nodeB', 'nodeC', 'nodeD')
		createInConditions('nodeB' -> 'nodeA', 'nodeC' -> 'nodeA', 'nodeD' -> 'nodeC')
		initTraverser
		findPath('nodeA' -> 'nodeB').assertPath(stepDown('nodeB'))
		findPath('nodeB' -> 'nodeA').assertPath(stepUp('nodeA'))
		findPath('nodeA' -> 'nodeD').assertPath(stepDown('nodeC'), stepDown('nodeD'))
		findPath('nodeC' -> 'nodeB').assertPath(stepUp('nodeA'), stepDown('nodeB'))
	}

	@Test
	def void shortestPaths() {
		/**
		 *    nodeA
		 *   /     \
		 * nodeB   nodeC
		 *         /   \
		 *      nodeE   nodeD
		 * 		/
		 * 	  nodeF	 
		 */
		createParams('nodeA', 'nodeB', 'nodeC', 'nodeD', 'nodeE', 'nodeF')
		createInConditions('nodeB' -> 'nodeA', 'nodeC' -> 'nodeA', 'nodeD' -> 'nodeC', 'nodeE' -> 'nodeC',
			'nodeF' -> 'nodeE')
		initTraverser
		findPath(#['nodeD'], 'nodeF').assertPath(stepUp('nodeC'), stepDown('nodeE'), stepDown('nodeF'))
		findPath(#['nodeD', 'nodeB'], 'nodeF').assertPath(stepUp('nodeC'), stepDown('nodeE'), stepDown('nodeF'))
		findPath(#['nodeD', 'nodeB', 'nodeC'], 'nodeF').assertPath(stepDown('nodeE'), stepDown('nodeF'))
	}

	@Test
	def void shortestNextNode() {
		/**
		 *    nodeA
		 *   /     \
		 * nodeB   nodeC
		 *           \
		 *           nodeD
		 */
		createParams('nodeA', 'nodeB', 'nodeC', 'nodeD')
		createInConditions('nodeB' -> 'nodeA', 'nodeC' -> 'nodeA', 'nodeD' -> 'nodeC')
		initTraverser
		// start with C, next node could be A or D, both would be right, the implementation goes to D
		traverser.findPathToNextNode(#['nodeC']).assertPathFrom('nodeC', stepDown('nodeD'))
		// now the only correct way is to go to A next
		traverser.findPathToNextNode(#['nodeC', 'nodeD']).assertPathFrom('nodeC', stepUp('nodeA'))
		// the last remaining step is to go to B
		traverser.findPathToNextNode(#['nodeC', 'nodeD', 'nodeA']).assertPathFrom('nodeA', stepDown('nodeB'))
	}

	private def stepDown(String to) {
		new TraverseStepDown(to)
	}

	private def stepUp(String to) {
		new TraverseStepUp(to)
	}

	private def assertPath(NodePath path, TraverseStep... steps) {
		println('''Expected Path: «steps.printPath»''')
		println('''Actual Path: «path.steps.printPath»''')
		assertEquals('Path length is wrong!', steps.length, path.steps.length)
		for (var i = 0; i < steps.length; i++) {
			val expectedStep = steps.get(i)
			val actualStep = path.steps.get(i)
			assertEquals('Wrong traverse step node!', expectedStep.parameter, actualStep.parameter)
			assertEquals('Wrong traverse step type!', expectedStep.class, actualStep.class)
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
		traverser = new MappingParameterTreeTraverser(inConditions, parameter)
	}

	private def createInConditions(Pair<String, String>... inConditions) {
		this.inConditions = inConditions.map [ inCondition |
			val child = inCondition.key
			val parent = inCondition.value
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
				]
			]
			new InValueConditionGenerator(multiValue)
		]
	}

	private def toParameter(String param) {
		MappingsLanguageFactory.eINSTANCE.createMappingParameter => [
			value = MirBaseFactory.eINSTANCE.createNamedMetaclassReference => [
				name = param
			]
		]
	}

	private def createParams(String... params) {
		parameter = params
	}
}
