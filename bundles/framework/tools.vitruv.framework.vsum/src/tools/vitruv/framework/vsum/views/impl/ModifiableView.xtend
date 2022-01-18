package tools.vitruv.framework.vsum.views.impl

import tools.vitruv.framework.vsum.views.View
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.vsum.views.ChangeableViewSource

/**
 * A view whose contents can be modified, in particular by a view type implementation.
 */
package interface ModifiableView extends View {
	def void modifyContents((ResourceSet)=>void modificationFunction);

	def ChangeableViewSource getViewSource()
}
