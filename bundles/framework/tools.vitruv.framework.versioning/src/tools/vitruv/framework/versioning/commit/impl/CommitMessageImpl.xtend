package tools.vitruv.framework.versioning.commit.impl

import tools.vitruv.framework.versioning.commit.CommitMessage
import java.util.Date
import tools.vitruv.framework.versioning.author.Author
import org.eclipse.xtend.lib.annotations.Data

@Data
class CommitMessageImpl implements CommitMessage {
	Date date
	String message
	Author author
}
