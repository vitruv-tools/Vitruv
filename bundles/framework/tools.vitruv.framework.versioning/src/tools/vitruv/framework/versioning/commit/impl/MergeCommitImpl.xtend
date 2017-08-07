package tools.vitruv.framework.versioning.commit.impl

import java.util.List
import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.versioning.commit.MergeCommit
import tools.vitruv.framework.versioning.commit.impl.CommitImpl
import tools.vitruv.framework.versioning.extensions.EChangeSerializer

@Data
class MergeCommitImpl extends CommitImpl implements MergeCommit {
	public static val SOURCE_COMMIT_KEY = "sourceCommit"
	public static val TARGET_COMMIT_KEY = "targetCommit"
	static extension EChangeSerializer = EChangeSerializer::instance
	List<String> sourceCommit
	List<String> targetCommit

	override getSerialization() '''
		{
			"«CHANGES_KEY»": «changes.serializeAll»,
			"«COMMITMESSAGE_KEY»": «commitmessage.serialization»,
			"«IDENTIFIER_KEY»": "«identifier»",
			"«NUMBEROFCHANGES_KEY»": «numberOfChanges»",
			"«SOURCE_COMMIT_KEY»": «FOR s : sourceCommit BEFORE '[' SEPARATOR ', ' AFTER ']'»«s»«ENDFOR»,
			"«TARGET_COMMIT_KEY»": «FOR s : targetCommit BEFORE '[' SEPARATOR ', ' AFTER ']'»«s»«ENDFOR»
		}
	'''

}
