package tools.vitruv.framework.versioning.branch

import tools.vitruv.framework.versioning.author.Author

interface UserBranch extends Branch {
	def Author getOwner()

	def void setOwner(Author value)

	def Branch getBranchedFrom()

	def void setBranchedFrom(Branch value)
}
