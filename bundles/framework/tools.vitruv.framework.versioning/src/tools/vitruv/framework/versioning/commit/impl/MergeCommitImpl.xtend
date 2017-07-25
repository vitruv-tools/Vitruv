package tools.vitruv.framework.versioning.commit.impl

import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.versioning.commit.MergeCommit
import tools.vitruv.framework.versioning.commit.impl.CommitImpl
import java.util.List

@Data
class MergeCommitImpl extends CommitImpl implements MergeCommit {
	List<String> sourceCommit
	List<String> targetCommit
}
