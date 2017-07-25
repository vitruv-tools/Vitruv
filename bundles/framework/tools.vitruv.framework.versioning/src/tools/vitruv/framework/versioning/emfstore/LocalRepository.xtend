package tools.vitruv.framework.versioning.emfstore

import java.util.List
import java.util.Set
import org.eclipse.xtext.xbase.lib.Functions.Function1
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.branch.LocalBranch
import tools.vitruv.framework.versioning.branch.RemoteBranch
import tools.vitruv.framework.versioning.commit.SimpleCommit
import tools.vitruv.framework.versioning.exceptions.CommitNotExceptedException
import tools.vitruv.framework.vsum.VirtualModel

interface LocalRepository extends AbstractRepository {
	def LocalBranch getCurrentBranch()

	def void setCurrentBranch(LocalBranch branch)

	def LocalBranch createLocalBranch(String name)

	def RemoteBranch createRemoteBranch(String name, RemoteRepository remoteRepository)

	def Set<RemoteBranch> getRemoteBranches()

	def Set<LocalBranch> getLocalBranches()

	def Author getAuthor()

	def SimpleCommit commit(String s, List<PropagatedChange> changes)

	def SimpleCommit commit(String s, VirtualModel virtualModel, VURI vuri)

	def void checkout(VirtualModel virtualModel, VURI vuri)

	def RemoteRepository getRemoteProject()

	def void delete()

	def void setAuthor(Author author)

	def void setRemoteProject(RemoteRepository remote)

	def void update()

	def void pull()

	def void pull(LocalBranch branch)

	def void addOrigin(LocalBranch branch, RemoteRepository remoteRepository)

	def void push(LocalBranch localBranch) throws CommitNotExceptedException

	def void push() throws CommitNotExceptedException

	def void update(Function1<List<Conflict>, Boolean> conflictCallback)

	def void addRemoteRepository(RemoteRepository remoteRepository)
}
