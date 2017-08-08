package tools.vitruv.framework.versioning.common.commit.impl

import java.util.Date
import java.util.List
import org.apache.commons.codec.digest.DigestUtils
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.versioning.common.commit.CommitFactory

class CommitFactoryImpl implements CommitFactory {
	static val prefix = "blob "

	static def CommitFactory init() {
		new CommitFactoryImpl
	}

	private new() {
	}

	override createInitialCommit() {
		val commitMessage = createCommitMessage("Initial commit", "", "")
		return new SimpleCommitImpl(#[], 0, commitMessage, newArrayList, newArrayList, initialCommitHash, null)
	}

	override createCommitMessage(
		String message,
		String authorName,
		String authorEMail
	) {
		new CommitMessageImpl(new Date, message, authorName, authorEMail)
	}

	override createSimpleCommit(
		List<PropagatedChange> changes,
		String message,
		String authorName,
		String authorEMail,
		String parent
	) {
		val commitMessage = createCommitMessage(message, authorName, authorEMail)
		val oldInfosToHash = '''
			«commitMessage.date.toString»
			«message»
			«authorName»
			«authorEMail»
			«parent»
				«FOR change : changes»
					«change.originalChange»«change.originalChange»
					«FOR echange: change.originalChange.EChanges»
						«echange.toString »
					«ENDFOR»
					«change.consequentialChanges»«change.consequentialChanges»
					«FOR echange: change.consequentialChanges.EChanges»
						«echange.toString »
					«ENDFOR»
				«ENDFOR»
		'''
		val stringToHash = '''«prefix»«oldInfosToHash.length»«oldInfosToHash»'''
		val hash = DigestUtils::sha512Hex(stringToHash)
		return new SimpleCommitImpl(changes, changes.length, commitMessage, newArrayList, newArrayList, hash, parent)

	}

	override createMergeCommit(
		List<PropagatedChange> changes,
		String message,
		String authorName,
		String authorEMail,
		String sources,
		String targets
	) {
		val commitMessage = createCommitMessage(message, authorName, authorEMail)
		val oldInfosToHash = '''
			«commitMessage.date.toString»
			«message»
			«authorName»
			«authorEMail»
			Sources
			«sources»
			Targets
			«targets»
				«FOR change : changes»
					«change.originalChange»«change.originalChange»
					«FOR echange: change.originalChange.EChanges»
						«echange.toString »
					«ENDFOR»
					«change.consequentialChanges»«change.consequentialChanges»
					«FOR echange: change.consequentialChanges.EChanges»
						«echange.toString »
					«ENDFOR»
				«ENDFOR»
		'''
		val stringToHash = '''«prefix»«oldInfosToHash.length»«oldInfosToHash»'''
		val hash = DigestUtils::sha512Hex(stringToHash)
		return new MergeCommitImpl(changes, changes.length, commitMessage, newArrayList, newArrayList, hash, sources,
			targets)
	}

}
