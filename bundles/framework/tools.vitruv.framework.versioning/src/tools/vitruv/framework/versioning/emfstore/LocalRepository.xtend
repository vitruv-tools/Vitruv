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

interface LocalRepository extends AbstractRepository, VVObjectContainer<VVModelElementId> {

	def Author getAuthor()

	def SimpleCommit commit(String s, List<PropagatedChange> changes)

	def void checkout(VirtualModel virtualModel, VURI vuri)

	def VVRemoteProject getRemoteProject()

	def VVRemoteProject shareProject(VVServer server)

	def void delete()

	def void setAuthor(Author author)

	def void setRemoteProject(VVRemoteProject remote)

	def void update()

	def void update(Function1<List<Conflict>, Boolean> conflictCallback)
}
