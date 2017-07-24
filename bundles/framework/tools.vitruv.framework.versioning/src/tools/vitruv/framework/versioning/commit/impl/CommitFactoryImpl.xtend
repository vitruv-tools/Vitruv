package tools.vitruv.framework.versioning.commit.impl

import tools.vitruv.framework.versioning.commit.CommitFactory
import java.util.Date
import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.commit.Commit
import org.apache.commons.codec.digest.DigestUtils
import tools.vitruv.framework.versioning.extensions.EChangeExtension

class CommitFactoryImpl implements CommitFactory {
	static val prefix = "blob "
	static val initialCommitHash = "2aae6c35c94fcfb415dbe95f408b9ce91ee846ed"
	static extension EChangeExtension = EChangeExtension::instance

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
		val date = new Date
		return new SimpleCommitImpl(#[], commitMessage, newArrayList, newArrayList, initialCommitHash, date, null)
	}

	override createCommitMessage(String message, Author author) {
		new CommitMessageImpl(new Date, message, author)
	}

	override createSimpleCommit(List<PropagatedChange> changes, String message, Author author, Commit parent) {
		val commitMessage = createCommitMessage(message, author)
		val date = new Date
		val oldInfosToHash = '''
			«date.toString»
			«message»
			«author»
			«parent.identifier»
				«FOR change : changes»
					«change.originalChange.URI»«change.originalChange»
					«FOR echange: change.originalChange.EChanges»
						«echange.fullString »
					«ENDFOR»
					«change.consequentialChanges.URI»«change.consequentialChanges»
					«FOR echange: change.consequentialChanges.EChanges»
						«echange.fullString »
					«ENDFOR»
				«ENDFOR»
		'''
		val stringToHash = '''«prefix»«oldInfosToHash.length»«oldInfosToHash»'''
		val hash = DigestUtils::sha512Hex(stringToHash)
		return new SimpleCommitImpl(changes, commitMessage, newArrayList, newArrayList, hash, date, parent)

	}

}
