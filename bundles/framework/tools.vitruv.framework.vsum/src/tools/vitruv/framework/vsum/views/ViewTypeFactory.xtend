package tools.vitruv.framework.vsum.views

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.framework.vsum.views.impl.BasicViewType
import tools.vitruv.framework.vsum.views.selection.BasicViewSelector

@Utility
class ViewTypeFactory {
	/**
	 * Creates a basic view type presenting root elements of resources
	 */
	static def ViewType<BasicViewSelector> createBasicViewType(String name) {
		new BasicViewType(name)
	}
}