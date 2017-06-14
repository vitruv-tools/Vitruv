package tools.vitruv.framework.versioning.impl

import java.util.List
import java.util.Map
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.ChangeMatch

/**
 * 
 * @author Patrick Stoeckle
 * @version 0.1.0
 * @since 2017-05-30
 */
@Data
class ChangeMatchImpl implements ChangeMatch {
	@Accessors(PUBLIC_GETTER)
	static val serialVersionUID = 1L
	VURI originalVURI
	TransactionalChange originalChange
	Map<VURI, List<TransactionalChange>> targetToCorrespondentChanges
}
