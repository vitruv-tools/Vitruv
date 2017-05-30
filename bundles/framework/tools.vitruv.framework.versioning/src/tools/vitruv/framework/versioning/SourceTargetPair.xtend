package tools.vitruv.framework.versioning

import java.util.List

import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.util.datatypes.VURI

@Data
class SourceTargetPair {
	VURI source
	List<VURI> targets
}
