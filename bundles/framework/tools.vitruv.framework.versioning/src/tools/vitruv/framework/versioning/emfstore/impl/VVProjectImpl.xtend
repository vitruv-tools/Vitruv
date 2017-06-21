package tools.vitruv.framework.versioning.emfstore.impl

import java.util.ArrayList
import java.util.List
import java.util.UUID
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.emfstore.VVProject
import tools.vitruv.framework.versioning.commit.CommitFactory

abstract class VVProjectImpl implements VVProject {
	protected List<Commit> commits

	@Accessors(PUBLIC_GETTER)
	String id

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	String name

	new() {
		id = UUID.randomUUID.toString
		commits = new ArrayList
		val initialCommit = CommitFactory::eINSTANCE.createInitialCommit
		commits += initialCommit
	}
}
