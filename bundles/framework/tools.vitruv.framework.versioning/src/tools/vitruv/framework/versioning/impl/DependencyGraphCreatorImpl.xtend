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
import tools.vitruv.framework.versioning.commit.ChangeMatch
import tools.vitruv.framework.versioning.extensions.EChangeRequireExtension
import tools.vitruv.framework.versioning.extensions.GraphManager

class DependencyGraphCreatorImpl implements DependencyGraphCreator {
	static extension EChangeRequireExtension = EChangeRequireExtension::newManager
	extension GraphManager = GraphManager::newManager
	List<EChange> echanges

	override createDependencyGraph(List<TransactionalChange> changes) {
		createDependencyGraph(changes, true)
	}

	private def createDependencyGraph(List<TransactionalChange> changes, boolean print) {
		val resourceSet = new ResourceSetImpl
		// PS Do not use the java 8 or xtend function methods here.
		// Their laziness can cause problems while applying
		// changes back or forward.
		echanges = new ArrayList
		changes.forEach [
			echanges += EChanges
		]
		echanges.forEach[addNode]
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
					addEdge(echange, otherEchange, EdgeType::PROVIDES)
			]
		]
		// TODO PS Remove
		if (print) savePicture
		return graph
	}

	override createDependencyGraphFromChangeMatches(List<ChangeMatch> changeMatches) {
		val originalChanges = changeMatches.map[originalChange].toList
		createDependencyGraph(originalChanges, false)

		val List<VURI> vuris = changeMatches.get(0).targetToCorrespondentChanges.keySet.toList
		vuris.forEach [ vuri |
			val targetChanges = changeMatches.map [ c |
				c.targetToCorrespondentChanges.get(vuri)
			].flatten.toList
			createDependencyGraph(targetChanges, false)
		]
		changeMatches.forEach [ c |
			c.originalChange.EChanges.forEach [ echange, i |
				c.targetToCorrespondentChanges.values.forEach [ transChanges |
					transChanges.forEach [ transChange |
						val triggeredEchange = transChange.EChanges.get(i)
						addEdge(echange, triggeredEchange, EdgeType::TRIGGERS)
					]
				]
			]
		]
		savePicture
		return graph
	}

}
