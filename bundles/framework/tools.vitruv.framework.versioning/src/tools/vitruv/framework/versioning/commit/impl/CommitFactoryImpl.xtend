package tools.vitruv.framework.versioning.commit.impl

import tools.vitruv.framework.versioning.commit.CommitFactory
import java.util.List
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.author.Author
import java.util.Date
import tools.vitruv.framework.versioning.commit.Commit
import java.util.Random

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

	override createSimpleCommit(List<EChange> changes, String message, Author author, Commit parent) {
		val commitMessage = createCommitMessage(message, author)
		return new SimpleCommitImpl(changes, commitMessage, newArrayList, newArrayList, nextInt, parent)

	}

}
