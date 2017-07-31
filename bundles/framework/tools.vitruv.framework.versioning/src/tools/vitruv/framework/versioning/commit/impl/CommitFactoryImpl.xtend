package tools.vitruv.framework.versioning.commit.impl

import java.util.Date
import java.util.List
import org.apache.commons.codec.digest.DigestUtils
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.commit.CommitFactory
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

	override createInitialCommit() {
		val commitMessage = createCommitMessage("Initial commit", null)
		return new SimpleCommitImpl(#[], 0, commitMessage, newArrayList, newArrayList, initialCommitHash, null)
	}

	override createCommitMessage(String message, Author author) {
		new CommitMessageImpl(new Date, message, author)
	}

	override createSimpleCommit(
		List<PropagatedChange> changes,
		String message,
		Author author,
		String parent
	) {
		val commitMessage = createCommitMessage(message, author)
		val oldInfosToHash = '''
			«commitMessage.date.toString»
			«message»
			«author»
			«parent»
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
		return new SimpleCommitImpl(changes, changes.length, commitMessage, newArrayList, newArrayList, hash, parent)

	}

	override createMergeCommit(
		List<PropagatedChange> changes,
		String message,
		Author author,
		List<String> sources,
		List<String> targets
	) {
		val commitMessage = createCommitMessage(message, author)
		val oldInfosToHash = '''
			«commitMessage.date.toString»
			«message»
			«author»
			Sources
			«FOR source : sources»
				«source»
			«ENDFOR»
			Targets
			«FOR target : targets»
				«target»
			«ENDFOR»
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
		return new MergeCommitImpl(changes, changes.length, commitMessage, newArrayList, newArrayList, hash, sources,
			targets)
	}

}
