package tools.vitruv.framework.versioning.impl

import java.util.ArrayList
import java.util.Collection
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.graphstream.graph.Graph
import org.graphstream.graph.implementations.MultiGraph
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.versioning.DependencyGraphCreator
import org.graphstream.graph.Node

class DependencyGraphCreatorImpl implements DependencyGraphCreator {
	static val transactionalIdentifier = "transactional"
//	static val affectedIdentifier = "transactional"
	static val logger = Logger::getLogger(ConflictDetectorImpl)
	Graph graph
	List<EChange> echanges

	override createDependencyGraph(List<TransactionalChange> changes) {
		setup

		changes.forEach[EChanges.forEach[echange|graph.addNode(echange.toString)]]
		changes.forEach[addEgdesForTransactionChange]
		echanges = changes.map[EChanges].flatten.toList
		echanges.forEach[addAffectedEdgeForEchange]
		val finalGraph = graph
		graph.display
		teardown
		finalGraph
	}

	private static def String getNodeId(EChange e) {
		e.toString
	}

	private def Node getNode(EChange e) {
		graph.getNode(e.nodeId)
	}

	private def checkIfEdgeExists(EChange e1, EChange e2) {
		val node1 = e1.node
		val node2 = e2.node
		val x = node1.hasEdgeBetween(node2)
		return x
	}

	private def addEgdesForTransactionChange(TransactionalChange tChange) {
		val eChangesLength = tChange.EChanges.length
		val oldEdgeCount = graph.edgeCount
		tChange.EChanges.forEach [ echange, index |
			val otherEchanges = tChange.EChanges.filter[it !== echange && !checkIfEdgeExists(echange)]
			if (eChangesLength - 1 - index !== otherEchanges.length)
				logger.warn('''Error by filtering EChange list''')
			otherEchanges.forEach[addTransactionalEdge(echange, it)]
		]
		if ((oldEdgeCount + (eChangesLength * (eChangesLength - 1)) / 2 ) !== graph.edgeCount)
			logger.warn('''Not all edges added!''')
	}

	private static def String createTransactionalEdgeName(EChange e1, EChange e2) {
		'''«transactionalIdentifier»: «e1» to «e2»'''
	}

//
//	private static def String createAffectedEdgeName(EChange e1, EChange e2) {
//		'''«affectedIdentifier»: «e1» to «e2»'''
//	}
	private def addTransactionalEdge(EChange e1, EChange e2) {
		addEChangeEdge(createTransactionalEdgeName(e1, e2), e1, e2)
	}

	private def addEChangeEdge(String n, EChange e1, EChange e2) {
		graph.addEdge(n, e1.toString, e2.toString, false)
	}

//	private def addAffectedEdge(EChange e1, EChange e2) {
//		addEChangeEdge(createAffectedEdgeName(e1, e2), e1, e2)
//	}
//
//	private def boolean transactionalEdgeExists(EChange e1, EChange e2) {
//		val edge = graph.getEdge(createTransactionalEdgeName(e1, e2))
//		return null !== edge
//	}
	private def addAffectedEdgeForEchange(EChange e) {
		return
//		logger.debug('''EChange «e»''')
//		val affectedObjects = determineAffectedObjects(e)
//		echanges.filter [
//			!transactionalEdgeExists(e, it)
//		].filter[it !== e].forEach [
//			val affectedByTheOther = determineAffectedObjects
//			val conflictedObjects = affectedByTheOther.filter[affectedObjects.contains(it)].length
//			if (conflictedObjects > 0) {
//				logger.debug('''«e» modifies the same objects as «it»''')
//				addAffectedEdge(e, it)
//			}
//		]
	}

	private dispatch def determineAffectedObjects(EChange e) {
		logger.debug('''Echange «e»''')
	}

	private dispatch def determineAffectedObjects(AtomicEChange e) {
		logger.debug('''AtomicEChange «e»''')
		return new ArrayList
	}

	private dispatch def determineAffectedObjects(CreateEObject<?> e) {
		logger.debug('''CreateEObject «e»''')
		#[e.affectedEObject]
	}

	private dispatch def determineAffectedObjects(InsertEReference<?, ?> e) {
		logger.debug('''InsertEReference «e»''')
		#[e.affectedEObject]
	}

	private dispatch def determineAffectedObjects(ReplaceSingleValuedEAttribute<?, ?> e) {
		logger.debug('''ReplaceSingleValuedEAttribute «e»''')
		#[e.affectedEObject]
	}

	private dispatch def determineAffectedObjects(ReplaceSingleValuedEReference<?, ?> e) {
		logger.debug('''ReplaceSingleValuedEReference «e»''')
		#[e.affectedEObject]
	}

	private dispatch def Collection<EObject> determineAffectedObjects(CompoundEChange e) {
		logger.debug('''CompoundEChange «e»''')
		e.eContents.filter[it instanceof AtomicEChange].map[it as AtomicEChange].map[determineAffectedObjects].flatten.
			toList
	}

	private def setup() {
		logger.debug("Start setup")
		graph = new MultiGraph("Test 1")
		logger.debug("End setup")
	}

	private def teardown() {
		logger.debug("Start teardown")
		graph = null
		logger.debug("End teardown")
	}
}
