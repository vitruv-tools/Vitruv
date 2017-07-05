package tools.vitruv.framework.versioning.repository

import java.util.List
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.InitialCommit

interface Repository {
//	def List<Tag> getTags()
	def List<Commit> getCommits()

	def Commit getHead()

	def List<Branch> getBranches()

	def InitialCommit getInitialCommit()

	def void add(Commit c)

	def List<VitruviusChange> getCopiedEChanges(VURI vuri, Pair<String, String> pair)
//	def MasterBranch getMaster()
//	def void setMaster(MasterBranch value)
}
