package tools.vitruv.framework.views

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.framework.views.impl.IdentityMappingViewType
import org.eclipse.emf.ecore.EPackage

@Utility
class ViewTypeFactory {
	/**
	 * Creates a view type that generates a view via a one-to-one (identity) mapping of
	 * selected resource root elements in a view source to a {@link View}.
	 */
	static def ViewType<? extends ViewSelector> createIdentityMappingViewType(String name) {
		new IdentityMappingViewType(name)
	}

  /**
	 * Creates a view type that generates a view via a one-to-one (identity) mapping of
	 * selected resource root elements in a view source to a {@link View}.
	 */
	static def ViewType<? extends ViewSelector> createIdentityMappingViewType(String name, EPackage metamodel) {
		new IdentityMappingViewType(name, metamodel)
	}
}
