package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.impl.PropagatedChangeImpl
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.PropagatedChangeWithCorrespondent
import org.eclipse.xtend.lib.annotations.Accessors

class PropagatedChangeWithCorrespondentImpl extends PropagatedChangeImpl implements PropagatedChangeWithCorrespondent {
	@Accessors(PUBLIC_GETTER,PUBLIC_SETTER)
	PropagatedChangeWithCorrespondent correspondent

	new(String id, VitruviusChange originalChange, VitruviusChange consequentialChanges) {
		super(id, originalChange, consequentialChanges)
	}

}
