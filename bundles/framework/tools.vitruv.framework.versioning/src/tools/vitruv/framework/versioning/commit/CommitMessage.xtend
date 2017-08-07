package tools.vitruv.framework.versioning.commit

import java.util.Date

import tools.vitruv.framework.versioning.JSONSerializable

interface CommitMessage extends JSONSerializable {

	def Date getDate()

	def String getMessage()

	def String getAuthorName()

	def String getAuthorEMail()
}
