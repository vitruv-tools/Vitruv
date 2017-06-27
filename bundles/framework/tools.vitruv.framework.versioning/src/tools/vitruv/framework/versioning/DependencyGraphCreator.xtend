package tools.vitruv.framework.versioning

import java.util.List
import tools.vitruv.framework.change.description.TransactionalChange
import org.graphstream.graph.Graph
import tools.vitruv.framework.versioning.impl.DependencyGraphCreatorImpl

interface DependencyGraphCreator {
	static def DependencyGraphCreator createDependencyGraphCreator() {
		new DependencyGraphCreatorImpl
	}

	def Graph createDependencyGraph(List<TransactionalChange> echanges)
}
