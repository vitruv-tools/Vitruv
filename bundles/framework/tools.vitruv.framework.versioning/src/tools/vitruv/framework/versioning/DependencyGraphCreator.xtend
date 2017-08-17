package tools.vitruv.framework.versioning

import java.util.List

import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.versioning.impl.DependencyGraphCreatorImpl

interface DependencyGraphCreator {
	DependencyGraphCreator instance = DependencyGraphCreatorImpl::init

	def EChangeGraph createDependencyGraph(List<VitruviusChange> echanges)

	def EChangeGraph createDependencyGraphFromChangeMatches(List<PropagatedChange> changeMatches)
}
