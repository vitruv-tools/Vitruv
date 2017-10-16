package tools.vitruv.framework.versioning.impl

import java.util.Collection
import java.util.List
import java.util.Map
import java.util.Set
import java.util.stream.Collectors
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.lib.Functions.Function1
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.BranchDiff
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.ConflictDetectionStrategy
import tools.vitruv.framework.versioning.ConflictDetector
import tools.vitruv.framework.versioning.DependencyGraphCreator
import tools.vitruv.framework.versioning.EChangeGraph
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.IsomorphismTesterAlgorithm
import tools.vitruv.framework.versioning.SimpleChangeConflict
import tools.vitruv.framework.versioning.extensions.EChangeCompareUtil
import tools.vitruv.framework.versioning.extensions.EChangeEdge
import tools.vitruv.framework.versioning.extensions.EChangeNode

class ConflictDetectorImpl implements ConflictDetector {
	// Extensions.
	static extension DependencyGraphCreator = DependencyGraphCreator::instance
	static extension EChangeCompareUtil = EChangeCompareUtil::instance
	static extension Logger = Logger::getLogger(ConflictDetectorImpl)

	// Static functions.
	static val getEChanges = [ Function1<Iterable<PropagatedChange>, Iterable<VitruviusChange>> toEChange, Iterable<PropagatedChange> changeIt |
		toEChange.apply(changeIt).map[EChanges].flatten
	]
	static val originalEChangesFunction = getEChanges.curry([Iterable<PropagatedChange> x|x.map[originalChange]])
	static val triggeredEChangesFunction = getEChanges.curry([ Iterable<PropagatedChange> x |
		x.map[consequentialChanges]
	])
	
	static val toEChangeSet = [Set<EChangeNode> nodes|nodes.map[EChange].toSet]
	
	static val filterFunction = [ Collection<EChange> unpairedChanges, Iterable<EChange> echangeIterator |
		echangeIterator.filter[!unpairedChanges.contains(it)]
	]	
	
	static val functionEChangesWithoutConflict = [Set<EChangeNode> nodeSet|nodeSet.filter[!conflicting].map[EChange].toSet]
	static val isNotConflicting = [Set<EChange> nonConflictingEChangeSet, EChange e|nonConflictingEChangeSet.contains(e)]
	static val filterEChangeListAgainFunction = [ List<EChange> listToFilter, List<EChange> listToCompareAgainst, List<EChange> listToAddIfTrue, List<EChange> listToAddIfFalse |
			listToFilter.forEach [ myEChange |
				val correspondentEChange = listToCompareAgainst.findFirst[myEChange.isEChangeEqual(it)]
				if (null !== correspondentEChange)
					listToAddIfTrue += myEChange
				else
					listToAddIfFalse += myEChange
			]
		]
	static val isNotInCommon = [ Collection<EChange> common, EChange e|
			!common.exists [ o |e.isEChangeEqual(o)]
		]
	
	// Values.
	@Accessors(PUBLIC_GETTER)
	val List<Conflict> conflicts
	@Accessors(PUBLIC_GETTER)
	val List<EChange> theirConflictFreeOriginalEChanges
	@Accessors(PUBLIC_GETTER)
	val List<EChange> theirConflictFreeTriggeredEChanges
	@Accessors(PUBLIC_GETTER)
	val List<EChange> myConflictFreeOriginalEChanges
	@Accessors(PUBLIC_GETTER)
	val List<EChange> myConflictFreeTriggeredEChanges
	@Accessors(PUBLIC_GETTER)
	val List<EChange> commonConflictFreeOriginalEChanges
	@Accessors(PUBLIC_GETTER)
	val List<EChange> commonConflictFreeTriggeredEChanges

	val List<List<?>> lists	
	val ConflictDetectionStrategy conflictDetectionStrategy
	
	// Variables.
	BranchDiff branchDiff
	VURI myVURI
	VURI theirVURI

	new() {
		conflictDetectionStrategy = new ConflictDetectionStrategyImpl
		conflicts = newArrayList
		commonConflictFreeOriginalEChanges = newArrayList
		commonConflictFreeTriggeredEChanges = newArrayList
		myConflictFreeOriginalEChanges = newArrayList
		myConflictFreeTriggeredEChanges = newArrayList
		theirConflictFreeOriginalEChanges = newArrayList
		theirConflictFreeTriggeredEChanges = newArrayList
		lists = #[
			commonConflictFreeOriginalEChanges,
			commonConflictFreeTriggeredEChanges,
			myConflictFreeOriginalEChanges,
			myConflictFreeTriggeredEChanges,
			theirConflictFreeOriginalEChanges,
			theirConflictFreeTriggeredEChanges
		]
	}
	// Static methods.
	private static def getOriginalEChanges(Iterable<PropagatedChange> propagatedChanges) {
		originalEChangesFunction.apply(propagatedChanges)
	}

	private static def getTriggeredEChanges(Iterable<PropagatedChange> propagatedChanges) {
		triggeredEChangesFunction.apply(propagatedChanges)
	}

	private static def getEChangeSet(Set<EChangeNode> nodes) {
		toEChangeSet.apply(nodes)
	}
	private static def getEChangesWithoutConflict(Set<EChangeNode> nodes) {
		functionEChangesWithoutConflict.apply(nodes)
	}
	
	// Overridden methods.
	override getConflictFreeOriginalEChanges() {
		(commonConflictFreeOriginalEChanges + myConflictFreeOriginalEChanges +
			theirConflictFreeOriginalEChanges).toList
	}
	
	override addIdToIdPair(Pair<String, String> idPair) {
		addIdPair(idPair)
	}
	
	override init(BranchDiff currentbranchDiff) {
		this.branchDiff = currentbranchDiff
		conflicts.clear
		lists.forEach[clear]
		myVURI = branchDiff.baseChanges.get(0).originalChange.URI
		theirVURI = branchDiff.compareChanges.get(0).originalChange.URI
	}

	override compute() {
		// TODO PS Remove 
		level = Level::DEBUG
		val List<Conflict> naiveConflicts = newArrayList
	
		// PS Create dependency graphs.
		val graph1 = createDependencyGraphFromChangeMatches(branchDiff.baseChanges)
		val graph2 = createDependencyGraphFromChangeMatches(branchDiff.compareChanges)

		// PS Determine my EChanges.
		val myOriginalEChanges = branchDiff.baseChanges.originalEChanges
		val myTriggeredEChanges = branchDiff.baseChanges.triggeredEChanges
//		val myChanges = myOriginalEChanges + myTriggeredEChanges

		// PS Determine their EChanges.
		val theirOriginalEChanges = branchDiff.compareChanges.originalEChanges
		val theirTriggeredEChanges = branchDiff.compareChanges.triggeredEChanges
//		val theirChanges = theirOriginalEChanges + theirTriggeredEChanges

		val tester = IsomorphismTesterAlgorithm::createIsomorphismTester
		tester.init(graph1, graph2)
		tester.compute

		val combinedGraph = tester.combinedGraph

		val myUnpairedChanges = tester.unmatchedOfGraph1.EChangeSet
		val theirUnpairedChanges = tester.unmatchedOfGraph2.EChangeSet

		// PS Create two filter predicates. The predicate returns true, if 
		// the given EChange is not in the unpaired set.
		val myFilterFunction = filterFunction.curry(myUnpairedChanges)
		val theirFilterFunction = filterFunction.curry(theirUnpairedChanges)

		val myOriginalEChangesWithIsomorphicChange = myFilterFunction.apply(myOriginalEChanges)
		val myTriggeredEChangesWithIsomorphiChange = myFilterFunction.apply(myTriggeredEChanges)

		val theirOriginalEChangesWithIsomorphicChange = theirFilterFunction.apply(theirOriginalEChanges)
		val theirTriggeredEChangesWithIsomorphiChange = theirFilterFunction.apply(theirTriggeredEChanges)

		if (myOriginalEChangesWithIsomorphicChange.length !== theirOriginalEChangesWithIsomorphicChange.length)
			throw new IllegalStateException
		if (myTriggeredEChangesWithIsomorphiChange.length !== theirTriggeredEChangesWithIsomorphiChange.length)
			throw new IllegalStateException

		// PS Add the EChanges with partner on the other side to the set of EChanges without conflict.
		commonConflictFreeOriginalEChanges += myOriginalEChangesWithIsomorphicChange
		commonConflictFreeTriggeredEChanges += myTriggeredEChangesWithIsomorphiChange

		if (tester.isIsomorphic)
			return;

		// PS Traverse through my unpaired changes to find conflicts in their changes. 
		myUnpairedChanges.forEach [ myChange |
			theirUnpairedChanges.filter[conflictDetectionStrategy.conflicts(myChange, it)].forEach [
				processConflict(myChange, it, naiveConflicts)
				combinedGraph.addEdge(myChange, it, EdgeType::CONFLICTS)
			]
		]
		// PS Traverse through their unpaired changes to find conflicts in my changes.
		theirUnpairedChanges.forEach [ theirChange |
			// PS Exclude already found conflicts
			myUnpairedChanges.filter[!combinedGraph.checkIfEdgeExists(theirChange, it, EdgeType::CONFLICTS)].filter [
				conflictDetectionStrategy.conflicts(theirChange, it)
			].forEach [
				processConflict(it, theirChange, naiveConflicts)
				combinedGraph.addEdge(it, theirChange, EdgeType::CONFLICTS)
			]
		]
		
		val myEChangesWithoutConflict = tester.unmatchedOfGraph1.EChangesWithoutConflict
		val theirEChangesWithoutConflict = tester.unmatchedOfGraph2.EChangesWithoutConflict

		// PS Create two filter predicates. The predicate returns true, if 
		// the given EChange is not conflicting.		
		val myIsNotConflicting = isNotConflicting.curry(myEChangesWithoutConflict)
		val theirIsNotConflicting = isNotConflicting.curry(theirEChangesWithoutConflict)

		val myOriginalEChangesWithoutConflict = myOriginalEChanges.filter(myIsNotConflicting).toList
		val myTriggeredEChangesWithouConflict = myTriggeredEChanges.filter(myIsNotConflicting).toList
		val theirOriginalEChangesWithoutConflict = theirOriginalEChanges.filter(theirIsNotConflicting).toList
		val theirTriggeredEChangesWithouConflict = theirTriggeredEChanges.filter(theirIsNotConflicting).toList

		val myOriginalEChangesToAdd = newArrayList
		val myTriggeredEChangesToAdd = newArrayList
		
		filterEChangeListAgainFunction.apply(myOriginalEChangesWithoutConflict, theirOriginalEChangesWithoutConflict,
			commonConflictFreeOriginalEChanges, myOriginalEChangesToAdd)

		filterEChangeListAgainFunction.apply(myTriggeredEChangesWithouConflict, theirTriggeredEChangesWithouConflict,
			commonConflictFreeTriggeredEChanges, myTriggeredEChangesToAdd)

		// PS Add my changes to the conflict free EChanges.
		myConflictFreeOriginalEChanges += myOriginalEChangesToAdd
		myConflictFreeTriggeredEChanges += myTriggeredEChangesToAdd
		
		// PS Create two filter predicates. The predicate returns true, if 
		// the given EChange is not in the common list.
		val original = isNotInCommon.curry(commonConflictFreeOriginalEChanges)
		val triggered = isNotInCommon.curry(commonConflictFreeTriggeredEChanges)
		
		val theirOriginalEChangesWithoutConflictFiltered = theirOriginalEChangesWithoutConflict.filter(original)
		val theirTriggeredEChangesWithouConflictFiltered = theirTriggeredEChangesWithouConflict.filter(triggered)

		// PS Add their changes which are not already in the conflict free EChanges set.
		theirConflictFreeOriginalEChanges += theirOriginalEChangesWithoutConflictFiltered
		theirConflictFreeTriggeredEChanges += theirTriggeredEChangesWithouConflictFiltered

		// PS Get the conflicted changes.
		val myConflictedChanges = naiveConflicts.filter(SimpleChangeConflict).map[sourceChange].toSet
		val theirConflictedChanges = naiveConflicts.filter(SimpleChangeConflict).map[targetChange].toSet
		
		val leaves1 = graph1.getLeaves(myConflictedChanges)
		val leaves2 = graph2.getLeaves(theirConflictedChanges)
		processConflicts(combinedGraph, leaves1, leaves2)
	}
	
	
	override addMap(Map<String, String> rootToRootMap) {
		rootToRootMap.entrySet.map[key -> value].forEach[addPair]
	}
	
	// Private methods.
	private def processConflict(EChange e1, EChange e2, List<Conflict> currentConflicts) {
		val type = conflictDetectionStrategy.getConflictType(e1, e2)
		val solvability = conflictDetectionStrategy.getConflictSolvability(e1, e2, type)
		val conflict = new SimpleChangeConflictImpl(type, solvability, myVURI, theirVURI, #[], #[], e1, e2)
		currentConflicts += conflict
	}
	
	private def void processConflicts(EChangeGraph combinedGraph, Iterable<EChangeNode> leaves1, Iterable<EChangeNode> leaves2) {
		val List<Conflict> cleanedConflicts = newArrayList
		combinedGraph.savePicture("combined_graph")
		// Find correspondences in the two subgraphs. 
		leaves1.forEach [ myLeave |
			val myEchange = myLeave.EChange
			// PS Try to find a conflict of type 1.
			val theirCorrespondentLeave = leaves2.findFirst [
				val theirEChange = EChange
				return combinedGraph.checkIfEdgeExists(myEchange, theirEChange, EdgeType::CONFLICTS)
			]
			if (null !== theirCorrespondentLeave) {
				// PS This is an conflict of type 1. The original changes respectively the EChanges of the leaves of 
				// the subgraphs are conflicting each other.
				processConflictType1Leave(myLeave, theirCorrespondentLeave, cleanedConflicts)
			} else {
				
				// PS Try to find a conflict of type 3
				val otherLeave = leaves2.findFirst[theirLeave|
					myLeave.<EChangeNode>breadthFirstIterator.forall[myNode|
						theirLeave.<EChangeNode>breadthFirstIterator.exists[theirNode|
							return combinedGraph.checkIfEdgeExists(myNode.EChange, theirNode.EChange, EdgeType::CONFLICTS)
						]
					]		
				]
				if (null !== otherLeave){
					processConflictType3(myLeave, otherLeave, combinedGraph, cleanedConflicts)
				}else {
					throw new UnsupportedOperationException("")
				}
			}
		]
		conflicts += cleanedConflicts
	}
	
	private def getLeaves(EChangeGraph graph, Set<EChange> eChanges) {
		// PS Get the subgraphs consisting of the conflicted changes.
		val subgraph = graph.getSubgraphContainingEChanges(eChanges)
		return subgraph.leaves
	} 
	
	private def processConflictType1Leave(EChangeNode leave1, EChangeNode leave2, Collection<Conflict> currentConflicts) {
		val e1 = leave1.EChange
		val e2 = leave2.EChange
		val type = conflictDetectionStrategy.getConflictType(e1, e2)
		val solvability = conflictDetectionStrategy.getConflictSolvability(e1, e2, type)
		if (leave1.breadthFirstIterator.size == 1 && leave2.breadthFirstIterator.size == 1) {
			val conflict = new SimpleChangeConflictImpl(type, solvability, myVURI, theirVURI, #[], #[], e1, e2)

			currentConflicts += conflict
		} else {
			val myEchanges = newArrayList
			val myTriggeredEChanges = newArrayList
			val theirEChanges = newArrayList
			val theirTriggeredEChanges = newArrayList
			val myTriggeredVuris = newArrayList
			val theirTriggeredVuris = newArrayList
			
			fillListsType1(leave1, myEchanges, myTriggeredEChanges, myTriggeredVuris)
			fillListsType1(leave2, theirEChanges, theirTriggeredEChanges, theirTriggeredVuris)
			val conflict = new MultiChangeConflictImpl(
				type, solvability, e1, e2, myEchanges, theirEChanges, myVURI,
				theirVURI, myTriggeredEChanges, theirTriggeredEChanges, myTriggeredVuris, theirTriggeredVuris
				)
			currentConflicts += conflict
		}
	}
	
	private def processConflictType3(
		EChangeNode myLeave, EChangeNode theirLeave, EChangeGraph combinedGraph, Collection<Conflict> currentConflicts
	) {
		val myEchanges = newArrayList
		val myTriggeredEChanges = newArrayList
		val theirEChanges = newArrayList
		val theirTriggeredEChanges = newArrayList
		val myVURIs = newArrayList
		val theirVURIs = newArrayList
		val myTriggeredVuris = newArrayList
		val theirTriggeredVuris = newArrayList
		fillListsType3(myLeave,combinedGraph,myEchanges,theirEChanges, myVURIs, theirVURIs)
		fillListsType3(theirLeave,combinedGraph,theirTriggeredEChanges, myTriggeredEChanges, theirTriggeredVuris, myTriggeredVuris)
		val e1 = myLeave.EChange
		val e2 = getCorrespondentEChange(e1, combinedGraph)
		val type = conflictDetectionStrategy.getConflictType(e1, e2)
		val solvability = conflictDetectionStrategy.getConflictSolvability(e1, e2, type)
		val conflict = new MultiChangeConflictImpl(
			type, solvability, e1, e2, myEchanges, theirEChanges, myVURIs.get(0),
			theirVURIs.get(0),
			myTriggeredEChanges, theirTriggeredEChanges, myTriggeredVuris, theirTriggeredVuris
		)
		currentConflicts += conflict
	}
	private static def void fillListsType1 ( EChangeNode leave, List<EChange> originalList, List<EChange> triggeredList, List<VURI> vuris) {
				leave.<EChangeNode>breadthFirstIterator.forEach [ node |
					if (node.triggered) {
						triggeredList += node.EChange
						vuris += node.vuri
					} else
						originalList += node.EChange
				]
			}
	
	private static  def EChange getCorrespondentEChange(EChange e, EChangeGraph combinedGraph) {
		val nodeInCombinedGraph = combinedGraph.getNode(e)
		return nodeInCombinedGraph.<EChangeEdge>leavingEdgeSet.findFirst[type === EdgeType::CONFLICTS]
		.<EChangeNode>targetNode.EChange
	}
	
	private static def void fillListsType3(
		EChangeNode leave, EChangeGraph combinedGraph, Collection<EChange> myEChanges,Collection<EChange> theirEChanges,
		Collection<VURI> myVURIs, 
		Collection<VURI> theirVURIs 
	) {
		val e1 = leave.EChange
		val nodeInCombinedGraph = combinedGraph.getNode(e1)
		myVURIs += nodeInCombinedGraph.vuri
	  	val nodesInOtherGraph = nodeInCombinedGraph.<EChangeEdge>edgeSet.stream.filter[type === EdgeType::CONFLICTS].map[
			if (nodeInCombinedGraph === sourceNode) <EChangeNode>targetNode else  <EChangeNode>sourceNode
		].collect(Collectors::toSet)
		myEChanges += e1
		theirEChanges += nodesInOtherGraph.map[EChange]
		theirVURIs += nodesInOtherGraph.map[vuri]
	}

}
