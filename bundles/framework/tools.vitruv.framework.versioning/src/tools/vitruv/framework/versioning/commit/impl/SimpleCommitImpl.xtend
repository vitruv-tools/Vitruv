package tools.vitruv.framework.versioning.commit.impl

import org.eclipse.xtend.lib.annotations.Data

import tools.vitruv.framework.versioning.commit.SimpleCommit
import tools.vitruv.framework.versioning.commit.impl.CommitImpl
import tools.vitruv.framework.versioning.extensions.EChangeSerializer

@Data
class SimpleCommitImpl extends CommitImpl implements SimpleCommit {
	public static val PARENT_KEY = "parent"

	static extension EChangeSerializer = EChangeSerializer::instance
	String parent

	override isInitialCommit() {
		null !== parent
	}

	override getSerialization() '''
		{
			"«CHANGES_KEY»": «changes.serializeAll»,
			"«COMMITMESSAGE_KEY»": «commitmessage.serialization»,
			"«IDENTIFIER_KEY»": "«identifier»",
			"«NUMBEROFCHANGES_KEY»": «numberOfChanges»,
			"«PARENT_KEY»": "«parent»",
			"«TYPE_KEY»": "«SimpleCommitImpl.name»"
		}
	'''

}
