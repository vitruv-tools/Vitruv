package tools.vitruv.framework.views.selectors;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ModifiableViewSelection;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.impl.ViewCreatingViewType;
import tools.vitruv.framework.views.selection.ElementViewSelection;

/**
 * A view selector that provides a selection of elements being view objects at
 * the same time. This means, there is no indirection between selected elements
 * and view elements (such as selecting types but providing instances in the
 * view),
 * but a selection is performed on the view elements themselves.
 */
public class DirectViewElementSelector<Id> implements ViewSelector {
    private final ModifiableViewSelection viewSelection;
    private final ChangeableViewSource viewSource;
    private final ViewCreatingViewType<DirectViewElementSelector<Id>, Id> viewType;

    /**
     * Creates a new selector based on the given collection of selectable elements
     * for the given {@link ViewType} and {@link ChangeableViewSource}. All
     * arguments
     * must not be <code>null</code>.
     * All elements will be unselected after creation.
     * 
     * @param viewType           - the {@link ViewType} to create a view for when
     *                           calling {@link createView}
     * @param viewSource         - the {@link ChangeableViewSource} to create a view
     *                           from
     * @param selectableElements - the elements to select from to be used by the
     *                           {@link ViewType} when creating a view
     */
    public DirectViewElementSelector(ViewCreatingViewType<DirectViewElementSelector<Id>, Id> viewType,
            ChangeableViewSource viewSource, Collection<EObject> selectableElements) {
        checkArgument(selectableElements != null, "selectable elements must not be null");
        checkArgument(viewType != null, "view type must not be null");
        checkArgument(viewSource != null, "view source must not be null");
        this.viewType = viewType;
        this.viewSource = viewSource;
        this.viewSelection = new ElementViewSelection(selectableElements);
    }

    @Override
    public View createView() {
        checkState(isValid(), "the current selection is invalid, thus a view cannot be created");
        return viewType.createView(this);
    }

    /**
     * {@link DirectViewElementSelector}s are always valid.
     */
    @Override
    public boolean isValid() {
        // A basic selection is always valid. In particular, it does not require at
        // least one element to be selected
        // because it must be possible to create empty views upon creation of a
        // (virtual) model.
        return true;
    }

    @Override
    public ModifiableViewSelection getSelection() {
        return new ElementViewSelection(viewSelection);
    }

    /**
     * Returns the changeable view source associated with this selector.
     * 
     * @return The changeable view source.
     */
    public ChangeableViewSource getViewSource() {
        return viewSource;
    }

    /**
     * Returns the view type associated with this selector.
     * 
     * @return The view type.
     */
    public ViewCreatingViewType<DirectViewElementSelector<Id>, Id> getViewType() {
        return viewType;
    }

    @Override
    public Collection<EObject> getSelectableElements() {
        return viewSelection.getSelectableElements();
    }

    @Override
    public boolean isSelected(EObject eObject) {
        return viewSelection.isSelected(eObject);
    }

    @Override
    public boolean isSelectable(EObject eObject) {
        return viewSelection.isSelectable(eObject);
    }

    @Override
    public void setSelected(EObject eObject, boolean selected) {
        viewSelection.setSelected(eObject, selected);
    }

    @Override
    public boolean isViewObjectSelected(EObject eObject) {
        return isSelected(eObject);
    }
}