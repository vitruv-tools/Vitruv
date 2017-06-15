package tools.vitruv.framework.versioning

import java.util.List
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.description.TransactionalChange

interface DependencyGraphCreator {
	def Graph<EChange> createDependencyGraph(List<TransactionalChange> echanges)
}
