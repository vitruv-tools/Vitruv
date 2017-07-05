package tools.vitruv.framework.versioning.repository

import tools.vitruv.framework.versioning.Named
import tools.vitruv.framework.versioning.commit.Commit

interface Tag extends Named {
	def Commit getCommit()

	def void setCommit(Commit value)
}
