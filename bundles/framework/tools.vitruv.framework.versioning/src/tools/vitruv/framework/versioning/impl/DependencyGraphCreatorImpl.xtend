package tools.vitruv.framework.versioning.impl

import java.util.List
import java.util.Map

import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl

import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.DependencyGraphCreator
import tools.vitruv.framework.versioning.EChangeGraph
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EChangeRequireExtension

class DependencyGraphCreatorImpl implements DependencyGraphCreator {
	static extension EChangeRequireExtension = EChangeRequireExtension::instance

	private new() {
	}

	static def DependencyGraphCreator init() {
		new DependencyGraphCreatorImpl
	}

	override createDependencyGraph(List<VitruviusChange> changes) {
		val vuri = changes.get(0).URI
		val graph = EChangeGraph::createEChangeGraph
		createDependencyGraph(graph, changes, true, false, vuri)
		return graph
	}

	override createDependencyGraphFromChangeMatches(List<PropagatedChange> changeMatches) {
		val graph = EChangeGraph::createEChangeGraph
		val originalChanges = changeMatches.map[originalChange].toList
		val originalVuri = originalChanges.get(0).URI
		createDependencyGraph(graph, originalChanges, false, false, originalVuri)

		val targetChanges = changeMatches.map[consequentialChanges]
		val vuri = changeMatches.get(0).consequentialChanges.URI
		createDependencyGraph(graph, targetChanges, false, true, vuri)

		changeMatches.forEach [ c |
			c.originalChange.EChanges.forEach [ echange, i |
				if(!c.consequentialChanges.EChanges.empty) {
					val triggeredEchange = c.consequentialChanges.EChanges.get(i)
					graph.addEdge(echange, triggeredEchange, EdgeType::TRIGGERS)
				}
			]
		]
		graph.savePicture
		return graph
	}

	private def createDependencyGraph(
		EChangeGraph graph,
		List<VitruviusChange> changes,
		boolean print,
		boolean isTriggered,
		VURI vuri
	) {
		val ResourceSet resourceSet = new ResourceSetImpl
		// PS Do not use the java 8 or xtend function methods here.
		// Their laziness can cause problems while applying
		// changes back or forward.
		val List<EChange> echanges = newArrayList
		changes.forEach [
			echanges += EChanges
		]
		echanges.forEach [
			val node = graph.addNode(it)
			node.triggered = isTriggered
			node.vuri = vuri
		]
		graph.savePicture
		if(echanges.exists[resolved])
			throw new IllegalStateException("A change was resolved")
		val Map<EChange, EChange> unresolvedToResolvedMap = newHashMap
		echanges.forEach [
			if(it instanceof CreateAndInsertRoot<?>) {
				val fileUri = vuri.EMFUri
				resourceSet.createResource(fileUri)
			}
			val x = resolveBefore(resourceSet)
			x.applyForward
			unresolvedToResolvedMap.put(it, x)
		]
		echanges.forEach [ echange |
			val resolved = unresolvedToResolvedMap.get(echange)
			echanges.filter[it !== echange].forEach [ otherEchange |
				val otherResolved = unresolvedToResolvedMap.get(otherEchange)
				val isParent = checkForRequireEdge(resolved, otherResolved)
				if(isParent)
					graph.addEdge(echange, otherEchange, EdgeType::REQUIRED)
			]
		]
		// TODO PS Remove
		if(print) graph.savePicture
	}
}
