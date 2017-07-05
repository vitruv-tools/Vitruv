package tools.vitruv.framework.versioning.commit

import java.util.Date
import tools.vitruv.framework.versioning.author.Author

interface CommitMessage {

	def Date getDate()

	def String getMessage()

	def Author getAuthor()
}
