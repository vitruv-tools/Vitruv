package tools.vitruv.framework.versioning

import java.util.List
import org.graphstream.graph.Graph
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.versioning.impl.DependencyGraphCreatorImpl

interface DependencyGraphCreator {
	static DependencyGraphCreator instance = DependencyGraphCreatorImpl::init

	def Graph createDependencyGraph(List<TransactionalChange> echanges)

	def Graph createDependencyGraphFromChangeMatches(List<ChangeMatch> changeMatches)
}
