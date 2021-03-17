package tools.vitruv.framework.vsum.views.selection;

public enum SelectionState { // TODO TS: Placeholder for the complex selection state required by a tree selector
    MANUALLY_SELECTED(true), AUTOMATICALLY_SELECTED(true), DESELECTED(false);

    private final boolean selected;

    private SelectionState(boolean selected) {
        this.selected = selected;
    }

    /**
     * Specifies whether the selection state is essentially selected or not, while
     * abstracting from the specific selection state.
     * 
     * @return true if it is selected.
     */
    public boolean isSelected() {
        return selected;
    }
}
