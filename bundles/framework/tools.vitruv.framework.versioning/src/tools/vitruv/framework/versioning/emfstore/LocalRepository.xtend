package tools.vitruv.framework.versioning.emfstore

import java.util.List
import java.util.Set

import org.eclipse.xtext.xbase.lib.Functions.Function1

import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.LocalBranch
import tools.vitruv.framework.versioning.branch.RemoteBranch
import tools.vitruv.framework.versioning.commit.MergeCommit
import tools.vitruv.framework.versioning.commit.SimpleCommit
import tools.vitruv.framework.vsum.VersioningVirtualModel

interface LocalRepository extends AbstractRepository {
	def void setVirtualModel(VersioningVirtualModel versioningVirtualModel)

	def VersioningVirtualModel getVirtualModel()

	def Author getAuthor()

	def LocalBranch createLocalBranch(String name)

	def LocalBranch getCurrentBranch()

	def RemoteBranch createRemoteBranch(String name, RemoteRepository remoteRepository)

	def RemoteRepository getRemoteProject()

	def Set<LocalBranch> getLocalBranches()

	def Set<RemoteBranch> getRemoteBranches()

	def SimpleCommit commit(String s, List<PropagatedChange> changes)

	def SimpleCommit commit(String s)

	def SimpleCommit commit(String s, VersioningVirtualModel virtualModel)

	def SimpleCommit commit(String s, VersioningVirtualModel virtualModel, VURI vuri)

	def void addOrigin(LocalBranch branch, RemoteRepository remoteRepository)

	def void addRemoteRepository(RemoteRepository remoteRepository)

	def void checkout()

	def void checkout(VersioningVirtualModel virtualModel)

	def void checkout(VersioningVirtualModel virtualModel, VURI vuri)

	def MergeCommit merge(
		Branch source,
		Branch target,
		Function1<Conflict, List<EChange>> originalCallback,
		Function1<Conflict, List<EChange>> triggeredCallback,
		VersioningVirtualModel virtualModel
	)

	def MergeCommit merge(
		Branch source,
		Branch target,
		Function1<Conflict, List<EChange>> originalCallback,
		Function1<Conflict, List<EChange>> triggeredCallback
	)

	def void pull()

	def void pull(LocalBranch branch)

	def PushState push()

	def PushState push(LocalBranch localBranch)

	def void setAuthor(Author author)

	def void setCurrentBranch(LocalBranch branch)

	def void setRemoteProject(RemoteRepository remote)

}
