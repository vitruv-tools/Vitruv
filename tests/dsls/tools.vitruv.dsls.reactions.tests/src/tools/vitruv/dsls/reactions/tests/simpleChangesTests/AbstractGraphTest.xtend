package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import java.util.List
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.DependencyGraphCreator
import tools.vitruv.framework.versioning.extensions.GraphManager

abstract class AbstractGraphTest extends NoConflict {
	protected DependencyGraphCreator dependencyGraphCreator
	protected List<EChange> echanges
	protected List<TransactionalChange> changes
	protected static extension GraphManager = GraphManager::newManager

	override setup() {
		super.setup
		changes = branchDiff.baseChanges.map[originalChange].toList
		echanges = changes.map[EChanges].flatten.toList
		dependencyGraphCreator = DependencyGraphCreator::createDependencyGraphCreator
	}
}
