package tools.vitruv.framework.versioning.commit.impl

import org.eclipse.xtend.lib.annotations.Data

import tools.vitruv.framework.versioning.commit.MergeCommit
import tools.vitruv.framework.versioning.commit.impl.CommitImpl
import tools.vitruv.framework.versioning.extensions.EChangeSerializer

@Data
class MergeCommitImpl extends CommitImpl implements MergeCommit {
	static extension EChangeSerializer = EChangeSerializer::instance
	public static val SOURCE_COMMIT_KEY = "sourceCommit"
	public static val TARGET_COMMIT_KEY = "targetCommit"
	String sourceCommit
	String targetCommit

	override getSerialization() '''
		{
			"«CHANGES_KEY»": «changes.serializeAll»,
			"«COMMITMESSAGE_KEY»": «commitmessage.serialization»,
			"«IDENTIFIER_KEY»": "«identifier»",
			"«NUMBEROFCHANGES_KEY»": «numberOfChanges»",
			"«SOURCE_COMMIT_KEY»": «sourceCommit»,
			"«TARGET_COMMIT_KEY»": «targetCommit»
		}
	'''

}
