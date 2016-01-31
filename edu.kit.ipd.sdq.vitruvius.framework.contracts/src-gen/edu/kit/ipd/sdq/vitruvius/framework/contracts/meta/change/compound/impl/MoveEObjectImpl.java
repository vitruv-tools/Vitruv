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

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Move EObject</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.MoveEObjectImpl#getSubtractChange <em>Subtract Change</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.MoveEObjectImpl#getAddChange <em>Add Change</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MoveEObjectImpl<T extends EObject> extends ECompoundChangeImpl implements MoveEObject<T> {
    /**
     * The cached value of the '{@link #getSubtractChange() <em>Subtract Change</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getSubtractChange()
     * @generated
     * @ordered
     */
    protected SubtractiveReferenceChange subtractChange;

    /**
     * The cached value of the '{@link #getAddChange() <em>Add Change</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAddChange()
     * @generated
     * @ordered
     */
    protected AdditiveReferenceChange<T> addChange;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected MoveEObjectImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CompoundPackage.Literals.MOVE_EOBJECT;
    }

    /**
     * @generated NOT
     */
    @Override
    public EList<EChange> getComposedChanges() {
        BasicEList<EChange> list = new BasicEList<EChange>();
        list.add(getSubtractChange());
        list.add(getAddChange());
        return list;
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CompoundPackage.MOVE_EOBJECT__SUBTRACT_CHANGE, oldSubtractChange, newSubtractChange);
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
                msgs = ((InternalEObject)subtractChange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.MOVE_EOBJECT__SUBTRACT_CHANGE, null, msgs);
            if (newSubtractChange != null)
                msgs = ((InternalEObject)newSubtractChange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.MOVE_EOBJECT__SUBTRACT_CHANGE, null, msgs);
            msgs = basicSetSubtractChange(newSubtractChange, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.MOVE_EOBJECT__SUBTRACT_CHANGE, newSubtractChange, newSubtractChange));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public AdditiveReferenceChange<T> getAddChange() {
        return addChange;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAddChange(AdditiveReferenceChange<T> newAddChange, NotificationChain msgs) {
        AdditiveReferenceChange<T> oldAddChange = addChange;
        addChange = newAddChange;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CompoundPackage.MOVE_EOBJECT__ADD_CHANGE, oldAddChange, newAddChange);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setAddChange(AdditiveReferenceChange<T> newAddChange) {
        if (newAddChange != addChange) {
            NotificationChain msgs = null;
            if (addChange != null)
                msgs = ((InternalEObject)addChange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.MOVE_EOBJECT__ADD_CHANGE, null, msgs);
            if (newAddChange != null)
                msgs = ((InternalEObject)newAddChange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.MOVE_EOBJECT__ADD_CHANGE, null, msgs);
            msgs = basicSetAddChange(newAddChange, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.MOVE_EOBJECT__ADD_CHANGE, newAddChange, newAddChange));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case CompoundPackage.MOVE_EOBJECT__SUBTRACT_CHANGE:
                return basicSetSubtractChange(null, msgs);
            case CompoundPackage.MOVE_EOBJECT__ADD_CHANGE:
                return basicSetAddChange(null, msgs);
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
            case CompoundPackage.MOVE_EOBJECT__SUBTRACT_CHANGE:
                return getSubtractChange();
            case CompoundPackage.MOVE_EOBJECT__ADD_CHANGE:
                return getAddChange();
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
            case CompoundPackage.MOVE_EOBJECT__SUBTRACT_CHANGE:
                setSubtractChange((SubtractiveReferenceChange)newValue);
                return;
            case CompoundPackage.MOVE_EOBJECT__ADD_CHANGE:
                setAddChange((AdditiveReferenceChange<T>)newValue);
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
            case CompoundPackage.MOVE_EOBJECT__SUBTRACT_CHANGE:
                setSubtractChange((SubtractiveReferenceChange)null);
                return;
            case CompoundPackage.MOVE_EOBJECT__ADD_CHANGE:
                setAddChange((AdditiveReferenceChange<T>)null);
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
            case CompoundPackage.MOVE_EOBJECT__SUBTRACT_CHANGE:
                return subtractChange != null;
            case CompoundPackage.MOVE_EOBJECT__ADD_CHANGE:
                return addChange != null;
        }
        return super.eIsSet(featureID);
    }

} // MoveEObjectImpl
