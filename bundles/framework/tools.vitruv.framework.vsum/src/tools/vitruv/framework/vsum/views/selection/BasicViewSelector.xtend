package tools.vitruv.framework.vsum.views.selection

import java.util.Collection
import org.eclipse.emf.ecore.EObject

import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.vsum.views.impl.ViewCreatingViewType
import tools.vitruv.framework.vsum.views.ChangeableViewSource
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.framework.vsum.views.ViewSelector
import tools.vitruv.framework.vsum.views.ModifiableViewSelection
import tools.vitruv.framework.vsum.views.selection.impl.ViewSelectionImpl
import static com.google.common.base.Preconditions.checkState

/**
 * Basic view selector for a view that represents a set of model elements.
 * 
 * After creation, all elements will be unselected.
 */
class BasicViewSelector implements ViewSelector {
	@Delegate
	val ModifiableViewSelection viewSelection

	@Accessors(PUBLIC_GETTER)
	val ChangeableViewSource viewSource

	@Accessors(PUBLIC_GETTER)
	val ViewCreatingViewType<BasicViewSelector> viewType

	new(ViewCreatingViewType<BasicViewSelector> viewType, ChangeableViewSource viewSource,
		Collection<EObject> elements) {
		this.viewType = viewType
		this.viewSource = viewSource
		this.viewSelection = new ViewSelectionImpl(elements)
	}

	override createView() {
		checkState(isValid(), "the current selection is invalid, thus a view cannot be created")
		return viewType.createView(this)
	}

	/**
	 * Basic selectors are always valid.
	 */
	override boolean isValid() {
		// A basic selection is always valid. In particular, it does not require at least one element to be selected
		// because it must be possible to create empty views upon creation of a (virtual) model.
		true
	}
}
