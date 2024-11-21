package tools.vitruv.framework.views.impl

import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.views.ChangeableViewSource
import tools.vitruv.framework.views.View

/**
 * A view whose contents can be modified, in particular by a view type implementation.
 */
interface ModifiableView extends View {
	def void modifyContents((ResourceSet)=>void modificationFunction);

	def ChangeableViewSource getViewSource()
}
