/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplaceInList;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ReplaceInEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Delete EObject Create EObject And Replace In List</b></em>'. <!-- end-user-doc -->
 *
 * @generated
 */
public class DeleteEObjectCreateEObjectAndReplaceInListImpl<T extends EObject> extends DeleteEObjectCreateEObjectAndReplaceImpl<T, ReplaceInEList<T>>
        implements DeleteEObjectCreateEObjectAndReplaceInList<T> {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected DeleteEObjectCreateEObjectAndReplaceInListImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CompoundPackage.Literals.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_IN_LIST;
    }

    /**
     * @generated NOT
     */
    @Override
    public EList<EChange> getComposedChanges() {
        EList<EChange> list = super.getComposedChanges();
        list.add(getReplaceChange());
        return list;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * This is specialized for the more specific type known in this context.
     * @generated
     */
    @Override
    public NotificationChain basicSetReplaceChange(ReplaceInEList<T> newReplaceChange, NotificationChain msgs) {
        return super.basicSetReplaceChange(newReplaceChange, msgs);
    }

} // DeleteEObjectCreateEObjectAndReplaceInListImpl
