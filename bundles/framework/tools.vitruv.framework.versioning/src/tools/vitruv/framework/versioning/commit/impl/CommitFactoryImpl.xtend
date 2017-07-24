package tools.vitruv.framework.versioning.commit.impl

import tools.vitruv.framework.versioning.commit.CommitFactory
import java.util.Date
import java.util.List
import java.util.Random
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.commit.Commit

class CommitFactoryImpl implements CommitFactory {
	static extension Random = new Random

	static def CommitFactory init() {
		new CommitFactoryImpl
	}

	private new() {
	}

	override createMergeCommit() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override createInitialCommit() {
		val commitMessage = createCommitMessage("Initial commit", null)
		return new InitialCommitImpl(#[], commitMessage, newArrayList, newArrayList, nextInt)
	}

	override createCommitMessage(String message, Author author) {
		new CommitMessageImpl(new Date, message, author)
	}

	override createSimpleCommit(List<PropagatedChange> changes, String message, Author author, Commit parent) {
		val commitMessage = createCommitMessage(message, author)
		return new SimpleCommitImpl(changes, commitMessage, newArrayList, newArrayList, nextInt, parent)

	}

}
