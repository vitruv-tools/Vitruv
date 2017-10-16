package tools.vitruv.framework.versioning.mococo

import tools.vitruv.framework.versioning.common.commit.SimpleCommit
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.VersioningVirtualModel

interface InternalTestLocalRepository<T> extends LocalRepository<T> {
	def void checkout(VURI vuri)

	def void checkout(VersioningVirtualModel virtualModel)

	def void checkout(VersioningVirtualModel virtualModel, VURI vuri)

	def SimpleCommit commit(String s, VURI vuri)

	def SimpleCommit commit(String s, VersioningVirtualModel virtualModel)

	def SimpleCommit commit(
		String s,
		VersioningVirtualModel virtualModel,
		VURI vuri
	)

	def void setAllFlag(boolean allFlag)
}
