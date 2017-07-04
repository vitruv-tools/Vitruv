package tools.vitruv.dsls.reactions.tests

import java.util.List
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.DependencyGraphCreator
import tools.vitruv.framework.versioning.extensions.GraphExtension
import org.graphstream.graph.Graph

abstract class AbstractConflictNotExistsGraphTest extends AbstractConflictNotExistsTest {
	protected DependencyGraphCreator dependencyGraphCreator
	protected Graph graph
	protected List<EChange> echanges
	protected List<TransactionalChange> changes
	protected static extension GraphExtension = GraphExtension::newManager

	override setup() {
		super.setup
		changes = branchDiff.baseChanges.map[originalChange].toList
		echanges = changes.map[EChanges].flatten.toList
		dependencyGraphCreator = DependencyGraphCreator::createDependencyGraphCreator
	}
}
