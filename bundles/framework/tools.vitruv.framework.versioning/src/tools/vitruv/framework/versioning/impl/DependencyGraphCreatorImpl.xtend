package tools.vitruv.framework.versioning.impl

import java.util.ArrayList
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.DependencyGraphCreator
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EChangeRequireExtension
import tools.vitruv.framework.versioning.extensions.GraphExtension
import org.graphstream.graph.Graph
import tools.vitruv.framework.versioning.ChangeMatch

class DependencyGraphCreatorImpl implements DependencyGraphCreator {
	static extension EChangeRequireExtension = EChangeRequireExtension::instance
	static extension GraphExtension = GraphExtension::instance

	static def DependencyGraphCreator init() {
		new DependencyGraphCreatorImpl
	}

	private new() {
	}

	override createDependencyGraph(List<TransactionalChange> changes) {
		val graph = GraphExtension::createNewEChangeGraph
		createDependencyGraph(graph, changes, true)
		return graph
	}

	override createDependencyGraphFromChangeMatches(List<ChangeMatch> changeMatches) {
		val graph = GraphExtension::createNewEChangeGraph
		val originalChanges = changeMatches.map[originalChange].toList
		createDependencyGraph(graph, originalChanges, false)

		val List<VURI> vuris = changeMatches.get(0).targetToCorrespondentChanges.keySet.toList
		vuris.forEach [ vuri |
			val targetChanges = changeMatches.map [ c |
				c.targetToCorrespondentChanges.get(vuri)
			].flatten.toList
			createDependencyGraph(graph, targetChanges, false)
		]
		changeMatches.forEach [ c |
			c.originalChange.EChanges.forEach [ echange, i |
				c.targetToCorrespondentChanges.values.forEach [ transChanges |
					transChanges.forEach [ transChange |
						val triggeredEchange = transChange.EChanges.get(i)
						graph.addEdge(echange, triggeredEchange, EdgeType::TRIGGERS)
					]
				]
			]
		]
		graph.savePicture
		return graph
	}

	private def createDependencyGraph(Graph graph, List<TransactionalChange> changes, boolean print) {
		val resourceSet = new ResourceSetImpl
		// PS Do not use the java 8 or xtend function methods here.
		// Their laziness can cause problems while applying
		// changes back or forward.
		val List<EChange> echanges = new ArrayList
		changes.forEach [
			echanges += EChanges
		]
		echanges.forEach[graph.addNode(it)]
		if (echanges.exists[resolved])
			throw new IllegalStateException("A change was resolved")
		val Map<EChange, EChange> unresolvedToResolvedMap = newHashMap
		echanges.forEach [
			unresolvedToResolvedMap.put(it, resolveAfter(resourceSet))
		]
		echanges.forEach [ echange |
			val resolved = unresolvedToResolvedMap.get(echange)
			echanges.filter[it !== echange].forEach [ otherEchange |
				val otherResolved = unresolvedToResolvedMap.get(otherEchange)
				val isParent = checkForRequireEdge(resolved, otherResolved)
				if (isParent)
					graph.addEdge(echange, otherEchange, EdgeType::PROVIDES)
			]
		]
		// TODO PS Remove
		if (print) graph.savePicture
	}

}
