package tools.vitruv.framework.vsum.views

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.vsum.views.impl.BasicViewType

@Utility
class ViewTypeFactory {
	/**
	 * Creates a basic view type presenting root elements of resources
	 */
	static def ViewType<?> createBasicViewType(String name, VirtualModel virtualModel) {
		new BasicViewType(name, virtualModel)
	}
}