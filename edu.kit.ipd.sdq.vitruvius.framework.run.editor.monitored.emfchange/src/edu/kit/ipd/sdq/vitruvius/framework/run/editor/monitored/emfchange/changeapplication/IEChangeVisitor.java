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
 * A visitor for {@link EChange} objects.
 */
interface IEChangeVisitor {
    /**
     * @param it
     *            An {@link UpdateEReference} {@link EChange} instance.
     */
    public void visit(UpdateEReference<?> it);

    /**
     * @param it
     *            An {@link UpdateEAttribute} {@link EChange} instance.
     */
    public void visit(UpdateEAttribute<?> it);

    /**
     * @param it
     *            A {@link CreateNonRootEObject} {@link EChange} instance.
     */
    public void visit(CreateNonRootEObject<?> it);

    /**
     * @param it
     *            A {@link DeleteNonRootEObject} {@link EChange} instance.
     */
    public void visit(DeleteNonRootEObject<?> it);

    /**
     * @param it
     *            A {@link InsertInEList} {@link EChange} instance.
     */
    public void visit(InsertInEList<?> it);

    /**
     * @param it
     *            A {@link RemoveFromEList} {@link EChange} instance.
     */
    public void visit(RemoveFromEList<?> it);

    /**
     * @param it
     *            A {@link UnsetEFeature} {@link EChange} instance.
     */
    public void visit(UnsetEFeature<?> it);

    /**
     * The "default" visitor method.
     * 
     * @param it
     *            An {@link EChange} instance which cannot be passed to any more specific
     *            {@link #visit} method.
     */
    public void visit(EChange it);
}
