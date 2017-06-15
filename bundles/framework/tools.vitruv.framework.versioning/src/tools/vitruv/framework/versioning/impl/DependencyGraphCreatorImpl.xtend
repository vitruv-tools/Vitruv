package tools.vitruv.framework.versioning.impl

import java.util.ArrayList
import java.util.Collection
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.versioning.DependencyGraphCreator
import tools.vitruv.framework.versioning.Graph

class DependencyGraphCreatorImpl implements DependencyGraphCreator {
	static val logger = Logger::getLogger(ConflictDetectorImpl)
	Graph<EChange> graph
	List<EChange> echanges

	override createDependencyGraph(List<TransactionalChange> changes) {
		setup

		changes.forEach[EChanges.forEach[echange|graph.addVertex(echange)]]
		changes.forEach[addEgdesForTransactionChange]
		echanges = changes.map[EChanges].flatten.toList
		echanges.forEach[addEdge]
		val finalGraph = graph
		teardown
		finalGraph
	}

	private def addEgdesForTransactionChange(TransactionalChange tChange) {
		val eChangesLength = tChange.EChanges.length
		val oldEdgeCount = graph.edgeCount
		tChange.EChanges.forEach [ echange |
			val otherEchanges = tChange.EChanges.filter[it !== echange]
			if (eChangesLength - 1 !== otherEchanges.length)
				logger.warn('''Error by filtering EChange list''')
			otherEchanges.forEach[graph.addEdge(echange, it)]
		]
		if ((oldEdgeCount + (eChangesLength * (eChangesLength - 1))) !== graph.edgeCount)
			logger.warn('''Not all edges added!''')
	}

	private def addEdge(EChange e) {
		logger.debug('''CreateAndReplaceNonRoot «e»''')
		val affectedObjects = determineAffectedObjects(e)
		echanges.filter [
			!graph.containsEdge(e, it)
		].filter[it !== e].forEach [
			val affectedByTheOther = determineAffectedObjects
			val conflictedObjects = affectedByTheOther.filter[affectedObjects.contains(it)].length
			val edgeAlreadyInGraph = graph.containsEdge(e, it)
			if (edgeAlreadyInGraph)
				logger.warn("Error while filtering!")
			if (conflictedObjects > 0) {
				logger.debug('''«e» modifies the same objects as «it»''')
				graph.addEdge(e, it)
			}
		]
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
		graph = new GraphImpl
		logger.debug("End setup")
	}

	private def teardown() {
		logger.debug("Start teardown")
		graph = null
		logger.debug("End teardown")
	}
}
