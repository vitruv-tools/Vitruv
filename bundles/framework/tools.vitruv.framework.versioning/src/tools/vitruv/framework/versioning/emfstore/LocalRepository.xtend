package tools.vitruv.framework.versioning.emfstore

import java.util.List
import java.util.Set
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.branch.LocalBranch
import tools.vitruv.framework.versioning.branch.RemoteBranch
import tools.vitruv.framework.versioning.commit.SimpleCommit
import tools.vitruv.framework.versioning.exceptions.CommitNotExceptedException
import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.versioning.branch.Branch
import org.eclipse.xtext.xbase.lib.Functions.Function1
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.change.echange.EChange

interface LocalRepository extends AbstractRepository {

	def Author getAuthor()

	def LocalBranch createLocalBranch(String name)

	def LocalBranch getCurrentBranch()

	def RemoteBranch createRemoteBranch(String name, RemoteRepository remoteRepository)

	def RemoteRepository getRemoteProject()

	def Set<LocalBranch> getLocalBranches()

	def Set<RemoteBranch> getRemoteBranches()

	def SimpleCommit commit(String s, List<PropagatedChange> changes)

	def SimpleCommit commit(String s, VirtualModel virtualModel, VURI vuri)

	def void addOrigin(LocalBranch branch, RemoteRepository remoteRepository)

	def void addRemoteRepository(RemoteRepository remoteRepository)

	def void checkout(VirtualModel virtualModel, VURI vuri)

	def void merge(
		Branch source,
		Branch target,
		Function1<Conflict, List<EChange>> originalCallback,
		Function1<Conflict, List<EChange>> triggeredCallback,
		VirtualModel virtualModel
	)

	def void pull()

	def void pull(LocalBranch branch)

	def void push() throws CommitNotExceptedException

	def void push(LocalBranch localBranch) throws CommitNotExceptedException

	def void setAuthor(Author author)

	def void setCurrentBranch(LocalBranch branch)

	def void setRemoteProject(RemoteRepository remote)

}
