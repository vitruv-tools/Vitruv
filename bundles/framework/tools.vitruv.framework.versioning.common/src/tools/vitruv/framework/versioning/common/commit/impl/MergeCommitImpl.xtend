package tools.vitruv.framework.versioning.common.commit.impl

import org.eclipse.xtend.lib.annotations.Data

import tools.vitruv.framework.versioning.common.EChangeSerializer
import tools.vitruv.framework.versioning.common.commit.MergeCommit

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
			"«NUMBEROFCHANGES_KEY»": «numberOfChanges»,
			"«SOURCE_COMMIT_KEY»": «sourceCommit»,
			"«TARGET_COMMIT_KEY»": «targetCommit»,
			"«USERINTERACTIONS_KEY»": [«FOR u : userInteractions SEPARATOR ", "» «u»«ENDFOR»]
		}
	'''

}
