package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.InsertInEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.RemoveFromEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UnsetEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEReference;

/**
 * {@link EChangeDispatcher} is a dispatcher for {@link EChange} objects.
 */
final class EChangeDispatcher {

    private EChangeDispatcher() {

    }

    /**
     * Dispatches an {@link IEChangeVisitor} object, calling its appropriate
     * {@link IEChangeVisitor#visit} method.
     * 
     * @param change
     *            The {@link EChange} object getting dispatched.
     * @param visitor
     *            The visitor object.
     */
    public static void dispatch(EChange change, IEChangeVisitor visitor) {
        if (change instanceof CreateNonRootEObject<?>) {
            visitor.visit((CreateNonRootEObject<?>) change);
        } else if (change instanceof DeleteNonRootEObject<?>) {
            visitor.visit((DeleteNonRootEObject<?>) change);
        } else if (change instanceof InsertInEList<?>) {
            visitor.visit((InsertInEList<?>) change);
        } else if (change instanceof RemoveFromEList<?>) {
            visitor.visit((RemoveFromEList<?>) change);
        } else if (change instanceof UpdateEReference<?>) {
            visitor.visit((UpdateEReference<?>) change);
        } else if (change instanceof UpdateEAttribute<?>) {
            visitor.visit((UpdateEAttribute<?>) change);
        } else if (change instanceof UnsetEFeature<?>) {
            visitor.visit((UnsetEFeature<?>) change);
        } else {
            visitor.visit(change);
        }
    }
}
