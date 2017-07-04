package tools.vitruv.dsls.reactions.tests

import tools.vitruv.framework.versioning.DependencyGraphCreator

abstract class AbstractConflictNotExistsGraphTest extends AbstractConflictNotExistsTest {

	override setup() {
		super.setup
		changes = branchDiff.baseChanges.map[originalChange].toList
		echanges = changes.map[EChanges].flatten.toList
		dependencyGraphCreator = DependencyGraphCreator::createDependencyGraphCreator
	}
}
