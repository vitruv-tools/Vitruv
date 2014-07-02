package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ListChange;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;

/**
 * {@link ListChangeKind} associates types of changes to many-multiplicity features in
 * {@link EObject} objects with helper objects converting {@link ListChange} objects to lists of
 * {@link EChange} changes.
 */
enum ListChangeKind {
    ADD(new ListChangeAddHelperImpl()), REMOVE(new ListChangeRemoveHelperImpl()), MOVE(new ListChangeMoveHelperImpl());

    private final IListChangeTranslationHelper helper;

    private ListChangeKind(IListChangeTranslationHelper helper) {
        this.helper = helper;
    }

    /**
     * @return The {@link ListChange}-to-{@link EChange} converter associated with this type of
     *         change.
     */
    public IListChangeTranslationHelper getTranslationHelper() {
        return helper;
    }

    /**
     * Determines the kind of change a given {@link ListChange} represents.
     * 
     * @param change
     *            A {@link ListChange} object.
     * @return The kind of change <code>change</code> represents.
     */
    public static ListChangeKind determineChangeKind(ListChange change) {
        switch (change.getKind()) {
        case ADD_LITERAL:
            return ADD;
        case MOVE_LITERAL:
            return MOVE;
        case REMOVE_LITERAL:
            return REMOVE;
        default:
            throw new UnsupportedOperationException();
        }
    }
}
