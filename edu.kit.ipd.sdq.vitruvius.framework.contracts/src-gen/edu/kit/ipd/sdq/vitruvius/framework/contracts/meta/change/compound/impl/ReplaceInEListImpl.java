/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EAtomicChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ReplaceInEList;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.InsertInEList;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.RemoveFromEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Replace In EList</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.ReplaceInEListImpl#getRemoveChange <em>Remove Change</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.ReplaceInEListImpl#getInsertChange <em>Insert Change</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReplaceInEListImpl<A extends EObject, F extends EStructuralFeature, T extends EObject, R extends RemoveFromEList & EFeatureChange<A, F> & SubtractiveEChange<T>, I extends InsertInEList & EFeatureChange<A, F> & AdditiveEChange<T>> extends ECompoundChangeImpl implements ReplaceInEList<A, F, T, R, I> {
    /**
     * The cached value of the '{@link #getRemoveChange() <em>Remove Change</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getRemoveChange()
     * @generated
     * @ordered
     */
    protected R removeChange;

    /**
     * The cached value of the '{@link #getInsertChange() <em>Insert Change</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getInsertChange()
     * @generated
     * @ordered
     */
    protected I insertChange;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ReplaceInEListImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CompoundPackage.Literals.REPLACE_IN_ELIST;
    }

    /**
     * @generated NOT
     */
    @Override
    public EList<EAtomicChange> getAtomicChanges() {
        BasicEList<EAtomicChange> list = new BasicEList<EAtomicChange>();
        list.add(getRemoveChange());
        list.add(getInsertChange());
        return list;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public R getRemoveChange() {
        return removeChange;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRemoveChange(R newRemoveChange, NotificationChain msgs) {
        R oldRemoveChange = removeChange;
        removeChange = newRemoveChange;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CompoundPackage.REPLACE_IN_ELIST__REMOVE_CHANGE, oldRemoveChange, newRemoveChange);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRemoveChange(R newRemoveChange) {
        if (newRemoveChange != removeChange) {
            NotificationChain msgs = null;
            if (removeChange != null)
                msgs = ((InternalEObject)removeChange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.REPLACE_IN_ELIST__REMOVE_CHANGE, null, msgs);
            if (newRemoveChange != null)
                msgs = ((InternalEObject)newRemoveChange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.REPLACE_IN_ELIST__REMOVE_CHANGE, null, msgs);
            msgs = basicSetRemoveChange(newRemoveChange, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.REPLACE_IN_ELIST__REMOVE_CHANGE, newRemoveChange, newRemoveChange));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public I getInsertChange() {
        return insertChange;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetInsertChange(I newInsertChange, NotificationChain msgs) {
        I oldInsertChange = insertChange;
        insertChange = newInsertChange;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CompoundPackage.REPLACE_IN_ELIST__INSERT_CHANGE, oldInsertChange, newInsertChange);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setInsertChange(I newInsertChange) {
        if (newInsertChange != insertChange) {
            NotificationChain msgs = null;
            if (insertChange != null)
                msgs = ((InternalEObject)insertChange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.REPLACE_IN_ELIST__INSERT_CHANGE, null, msgs);
            if (newInsertChange != null)
                msgs = ((InternalEObject)newInsertChange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.REPLACE_IN_ELIST__INSERT_CHANGE, null, msgs);
            msgs = basicSetInsertChange(newInsertChange, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.REPLACE_IN_ELIST__INSERT_CHANGE, newInsertChange, newInsertChange));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case CompoundPackage.REPLACE_IN_ELIST__REMOVE_CHANGE:
                return basicSetRemoveChange(null, msgs);
            case CompoundPackage.REPLACE_IN_ELIST__INSERT_CHANGE:
                return basicSetInsertChange(null, msgs);
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
            case CompoundPackage.REPLACE_IN_ELIST__REMOVE_CHANGE:
                return getRemoveChange();
            case CompoundPackage.REPLACE_IN_ELIST__INSERT_CHANGE:
                return getInsertChange();
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
            case CompoundPackage.REPLACE_IN_ELIST__REMOVE_CHANGE:
                setRemoveChange((R)newValue);
                return;
            case CompoundPackage.REPLACE_IN_ELIST__INSERT_CHANGE:
                setInsertChange((I)newValue);
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
            case CompoundPackage.REPLACE_IN_ELIST__REMOVE_CHANGE:
                setRemoveChange((R)null);
                return;
            case CompoundPackage.REPLACE_IN_ELIST__INSERT_CHANGE:
                setInsertChange((I)null);
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
            case CompoundPackage.REPLACE_IN_ELIST__REMOVE_CHANGE:
                return removeChange != null;
            case CompoundPackage.REPLACE_IN_ELIST__INSERT_CHANGE:
                return insertChange != null;
        }
        return super.eIsSet(featureID);
    }

} // ReplaceInEListImpl
