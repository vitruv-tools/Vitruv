package tools.vitruv.framework.versioning

import java.util.List
import org.graphstream.graph.Graph
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.versioning.impl.DependencyGraphCreatorImpl
import tools.vitruv.framework.change.description.PropagatedChange

interface DependencyGraphCreator {
	DependencyGraphCreator instance = DependencyGraphCreatorImpl::init

	def Graph createDependencyGraph(List<VitruviusChange> echanges)

	def Graph createDependencyGraphFromChangeMatches(List<PropagatedChange> changeMatches)
}
