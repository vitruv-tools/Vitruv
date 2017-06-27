package tools.vitruv.framework.versioning.impl

import java.util.ArrayList
import java.util.List
import java.util.Map
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.DependencyGraphCreator
import tools.vitruv.framework.versioning.GraphManager
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.EChangeRequireManager

class DependencyGraphCreatorImpl implements DependencyGraphCreator {
	extension GraphManager gm = GraphManager::newManager
	extension EChangeRequireManager erm = EChangeRequireManager::newManager
	static val logger = Logger::getLogger(ConflictDetectorImpl)
	List<EChange> echanges

	override createDependencyGraph(List<TransactionalChange> changes) {

		val resourceSet = new ResourceSetImpl
		// PS Do not use the java 8 or xtend function methods here.
		// Their laziness can cause problems while applying
		// changes back or forward.
		echanges = new ArrayList
		changes.forEach [
			echanges += EChanges
		]
		if (echanges.exists[resolved])
			throw new IllegalStateException("A change was resolved")
		val Map<EChange, EChange> unresolvedToResolvedMap = newHashMap
		echanges.forEach [
			unresolvedToResolvedMap.put(it, resolveAfter(resourceSet))
		]
		echanges.forEach[addNode]
		echanges.forEach [ echange |
			val resolved = unresolvedToResolvedMap.get(echange)
			echanges.filter[it !== echange].forEach [ otherEchange |
				val otherResolved = unresolvedToResolvedMap.get(otherEchange)
				val isParent = checkForRequireEdge(resolved, otherResolved)
				if (isParent)
					addEdge(otherEchange, echange, EdgeType.REQUIRES)
			]
		]

		logger.debug('''«unresolvedToResolvedMap.values.size»''')
		logger.debug('''«unresolvedToResolvedMap.values.filterNull.length»''')
		if (unresolvedToResolvedMap.values.empty)
			throw new IllegalStateException("No resolved changes")
		if (unresolvedToResolvedMap.values.
			exists[!resolved])
			throw new IllegalStateException('''«unresolvedToResolvedMap.values.filter[!resolved].size» of «unresolvedToResolvedMap.values.size» were not resolved''')

		graph.display
		graph.addAttribute("ui.screenshot", "./test.png")
		graph
	}

}
