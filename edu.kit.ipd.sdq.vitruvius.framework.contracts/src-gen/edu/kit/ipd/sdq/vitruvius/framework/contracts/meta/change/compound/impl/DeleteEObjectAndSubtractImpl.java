/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectAndSubtract;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.DeleteEObject;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Delete EObject And Subtract</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectAndSubtractImpl#getDeleteChange <em>Delete Change</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectAndSubtractImpl#getSubtractChange <em>Subtract Change</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DeleteEObjectAndSubtractImpl<T extends EObject> extends ECompoundChangeImpl
        implements DeleteEObjectAndSubtract<T> {
    /**
     * The cached value of the '{@link #getDeleteChange() <em>Delete Change</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDeleteChange()
     * @generated
     * @ordered
     */
    protected DeleteEObject<T> deleteChange;

    /**
     * The cached value of the '{@link #getSubtractChange() <em>Subtract Change</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getSubtractChange()
     * @generated
     * @ordered
     */
    protected SubtractiveReferenceChange subtractChange;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected DeleteEObjectAndSubtractImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CompoundPackage.Literals.DELETE_EOBJECT_AND_SUBTRACT;
    }

    /**
     * @generated NOT
     */
    @Override
    public EList<EChange> getComposedChanges() {
        BasicEList<EChange> list = new BasicEList<EChange>();
        list.add(getDeleteChange());
        list.add(getSubtractChange());
        return list;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public DeleteEObject<T> getDeleteChange() {
        return deleteChange;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDeleteChange(DeleteEObject<T> newDeleteChange, NotificationChain msgs) {
        DeleteEObject<T> oldDeleteChange = deleteChange;
        deleteChange = newDeleteChange;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT__DELETE_CHANGE, oldDeleteChange, newDeleteChange);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setDeleteChange(DeleteEObject<T> newDeleteChange) {
        if (newDeleteChange != deleteChange) {
            NotificationChain msgs = null;
            if (deleteChange != null)
                msgs = ((InternalEObject)deleteChange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT__DELETE_CHANGE, null, msgs);
            if (newDeleteChange != null)
                msgs = ((InternalEObject)newDeleteChange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT__DELETE_CHANGE, null, msgs);
            msgs = basicSetDeleteChange(newDeleteChange, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT__DELETE_CHANGE, newDeleteChange, newDeleteChange));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public SubtractiveReferenceChange getSubtractChange() {
        return subtractChange;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSubtractChange(SubtractiveReferenceChange newSubtractChange, NotificationChain msgs) {
        SubtractiveReferenceChange oldSubtractChange = subtractChange;
        subtractChange = newSubtractChange;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT__SUBTRACT_CHANGE, oldSubtractChange, newSubtractChange);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setSubtractChange(SubtractiveReferenceChange newSubtractChange) {
        if (newSubtractChange != subtractChange) {
            NotificationChain msgs = null;
            if (subtractChange != null)
                msgs = ((InternalEObject)subtractChange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT__SUBTRACT_CHANGE, null, msgs);
            if (newSubtractChange != null)
                msgs = ((InternalEObject)newSubtractChange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT__SUBTRACT_CHANGE, null, msgs);
            msgs = basicSetSubtractChange(newSubtractChange, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT__SUBTRACT_CHANGE, newSubtractChange, newSubtractChange));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT__DELETE_CHANGE:
                return basicSetDeleteChange(null, msgs);
            case CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT__SUBTRACT_CHANGE:
                return basicSetSubtractChange(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT__DELETE_CHANGE:
                return getDeleteChange();
            case CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT__SUBTRACT_CHANGE:
                return getSubtractChange();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT__DELETE_CHANGE:
                setDeleteChange((DeleteEObject<T>)newValue);
                return;
            case CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT__SUBTRACT_CHANGE:
                setSubtractChange((SubtractiveReferenceChange)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT__DELETE_CHANGE:
                setDeleteChange((DeleteEObject<T>)null);
                return;
            case CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT__SUBTRACT_CHANGE:
                setSubtractChange((SubtractiveReferenceChange)null);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT__DELETE_CHANGE:
                return deleteChange != null;
            case CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT__SUBTRACT_CHANGE:
                return subtractChange != null;
        }
        return super.eIsSet(featureID);
    }

} // DeleteEObjectAndSubtractImpl
