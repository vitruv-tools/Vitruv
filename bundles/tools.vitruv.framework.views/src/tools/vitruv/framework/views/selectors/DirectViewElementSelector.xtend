package tools.vitruv.framework.views.selectors

import java.util.Collection
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.framework.views.ChangeableViewSource
import tools.vitruv.framework.views.ModifiableViewSelection
import tools.vitruv.framework.views.ViewSelector
import tools.vitruv.framework.views.ViewType
import tools.vitruv.framework.views.impl.ViewCreatingViewType
import tools.vitruv.framework.views.selection.ElementViewSelection

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState

/**
 * A view selector that provides a selection of elements being view objects at
 * the same time. This means, there is no indirection between selected elements
 * and view elements (such as selecting types but providing instances in the view), 
 * but a selection is performed on the view elements themselves.
 */
class DirectViewElementSelector implements ViewSelector {
	@Delegate
	val ModifiableViewSelection viewSelection

	@Accessors(PUBLIC_GETTER)
	val ChangeableViewSource viewSource

	@Accessors(PUBLIC_GETTER)
	val ViewCreatingViewType<DirectViewElementSelector> viewType

	/**
	 * Creates a new selector based on the given collection of selectable elements
	 * for the given {@link ViewType} and {@link ChangeableViewSource}. All arguments
	 * must not be <code>null</code>.
	 * All elements will be unselected after creation.
	 * 
	 * @param viewType -			the {@link ViewType} to create a view for when 
	 * 								calling {@link createView}
	 * @param viewSource -			the {@link ChangeableViewSource} to create a view 
	 * 								from
	 * @param selectableElements -	the elements to select from to be used by the 
	 * 								{@link ViewType} when creating a view
	 */
	new(ViewCreatingViewType<DirectViewElementSelector> viewType, ChangeableViewSource viewSource,
		Collection<EObject> selectableElements) {
		checkArgument(selectableElements !== null, "selectable elements must not be null")
		checkArgument(viewType !== null, "view type must not be null")
		checkArgument(viewSource !== null, "view source must not be null")
		this.viewType = viewType
		this.viewSource = viewSource
		this.viewSelection = new ElementViewSelection(selectableElements)
	}

	override createView() {
		checkState(isValid(), "the current selection is invalid, thus a view cannot be created")
		return viewType.createView(this)
	}

	/**
	 * {@link DirectViewElementSelector}s are always valid.
	 */
	override boolean isValid() {
		// A basic selection is always valid. In particular, it does not require at least one element to be selected
		// because it must be possible to create empty views upon creation of a (virtual) model.
		true
	}

	override getSelection() {
		return new ElementViewSelection(viewSelection)
	}

}
