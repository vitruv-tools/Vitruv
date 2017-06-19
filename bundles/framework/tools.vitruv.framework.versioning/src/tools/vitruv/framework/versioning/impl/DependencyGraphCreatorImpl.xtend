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
import tools.vitruv.framework.versioning.GraphManager

class DependencyGraphCreatorImpl implements DependencyGraphCreator {
	extension GraphManager gm = GraphManager::newManager
	static val logger = Logger::getLogger(ConflictDetectorImpl)
	List<EChange> echanges

	override createDependencyGraph(List<TransactionalChange> changes) {
		setup

		changes.forEach[addNode]
		echanges = changes.map[EChanges].flatten.toList
		echanges.forEach[addAffectedEdgeForEchange]
		teardown
		graph.display
		graph.addAttribute("ui.screenshot", "./test.png")
		graph
	}

	private def addAffectedEdgeForEchange(EChange e) {
		logger.debug('''EChange «e»''')
		val affectedObjects = determineAffectedObjects(e)
		echanges.filter[it !== e && !checkIfEdgeExists(e)].forEach [
			val affectedByTheOther = determineAffectedObjects
			val conflictedObjects = affectedByTheOther.filter[affectedObjects.contains(it)].length
			if (conflictedObjects > 0) {
				logger.debug('''«e» modifies the same objects as «it»''')
				addAffectedEdge(e, it)
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
		logger.debug("End setup")
	}

	private def teardown() {
		logger.debug("Start teardown")
		logger.debug("End teardown")
	}
}
