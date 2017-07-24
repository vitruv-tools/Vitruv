package tools.vitruv.framework.versioning.emfstore

import java.util.List
import org.eclipse.xtext.xbase.lib.Functions.Function1
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.commit.SimpleCommit
import tools.vitruv.framework.versioning.emfstore.VVObjectContainer
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.versioning.exceptions.CommitNotExceptedException

interface LocalRepository extends AbstractRepository, VVObjectContainer<VVModelElementId> {

	def Author getAuthor()

	def SimpleCommit commit(String s, List<PropagatedChange> changes)

	def SimpleCommit commit(String s, VirtualModel virtualModel, VURI vuri)

	def void checkout(VirtualModel virtualModel, VURI vuri)

	def RemoteRepository getRemoteProject()

	def RemoteRepository shareProject(VVServer server)

	def void delete()

	def void setAuthor(Author author)

	def void setRemoteProject(RemoteRepository remote)

	def void update()

	def void pull()

	def void push() throws CommitNotExceptedException

	def void update(Function1<List<Conflict>, Boolean> conflictCallback)

	def void addRemoteRepository(RemoteRepository remoteRepository)
}
