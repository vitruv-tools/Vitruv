package tools.vitruv.framework.versioning

import java.util.List
import tools.vitruv.framework.change.description.TransactionalChange
import org.graphstream.graph.Graph

interface DependencyGraphCreator {
	def Graph createDependencyGraph(List<TransactionalChange> echanges)
}
