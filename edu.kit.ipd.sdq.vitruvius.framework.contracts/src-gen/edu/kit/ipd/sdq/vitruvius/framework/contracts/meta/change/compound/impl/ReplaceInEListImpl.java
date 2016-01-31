/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ReplaceInEList;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.InsertInEList;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.RemoveFromEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Replace In EList</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.ReplaceInEListImpl#getRemoveChange
 * <em>Remove Change</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.ReplaceInEListImpl#getInsertChange
 * <em>Insert Change</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReplaceInEListImpl<T extends Object> extends ECompoundChangeImpl implements ReplaceInEList<T> {
    /**
     * The cached value of the '{@link #getRemoveChange() <em>Remove Change</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getRemoveChange()
     * @generated
     * @ordered
     */
    protected RemoveFromEList<T> removeChange;

    /**
     * The cached value of the '{@link #getInsertChange() <em>Insert Change</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getInsertChange()
     * @generated
     * @ordered
     */
    protected InsertInEList<T> insertChange;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected ReplaceInEListImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
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
    public EList<EChange> getComposedChanges() {
        BasicEList<EChange> list = new BasicEList<EChange>();
        list.add(getRemoveChange());
        list.add(getInsertChange());
        return list;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public RemoveFromEList<T> getRemoveChange() {
        return this.removeChange;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetRemoveChange(final RemoveFromEList<T> newRemoveChange, NotificationChain msgs) {
        RemoveFromEList<T> oldRemoveChange = this.removeChange;
        this.removeChange = newRemoveChange;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    CompoundPackage.REPLACE_IN_ELIST__REMOVE_CHANGE, oldRemoveChange, newRemoveChange);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setRemoveChange(final RemoveFromEList<T> newRemoveChange) {
        if (newRemoveChange != this.removeChange) {
            NotificationChain msgs = null;
            if (this.removeChange != null)
                msgs = ((InternalEObject) this.removeChange).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - CompoundPackage.REPLACE_IN_ELIST__REMOVE_CHANGE, null, msgs);
            if (newRemoveChange != null)
                msgs = ((InternalEObject) newRemoveChange).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - CompoundPackage.REPLACE_IN_ELIST__REMOVE_CHANGE, null, msgs);
            msgs = basicSetRemoveChange(newRemoveChange, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.REPLACE_IN_ELIST__REMOVE_CHANGE,
                    newRemoveChange, newRemoveChange));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public InsertInEList<T> getInsertChange() {
        return this.insertChange;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetInsertChange(final InsertInEList<T> newInsertChange, NotificationChain msgs) {
        InsertInEList<T> oldInsertChange = this.insertChange;
        this.insertChange = newInsertChange;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    CompoundPackage.REPLACE_IN_ELIST__INSERT_CHANGE, oldInsertChange, newInsertChange);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setInsertChange(final InsertInEList<T> newInsertChange) {
        if (newInsertChange != this.insertChange) {
            NotificationChain msgs = null;
            if (this.insertChange != null)
                msgs = ((InternalEObject) this.insertChange).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - CompoundPackage.REPLACE_IN_ELIST__INSERT_CHANGE, null, msgs);
            if (newInsertChange != null)
                msgs = ((InternalEObject) newInsertChange).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - CompoundPackage.REPLACE_IN_ELIST__INSERT_CHANGE, null, msgs);
            msgs = basicSetInsertChange(newInsertChange, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.REPLACE_IN_ELIST__INSERT_CHANGE,
                    newInsertChange, newInsertChange));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID,
            final NotificationChain msgs) {
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
     * 
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
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
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(final int featureID, final Object newValue) {
        switch (featureID) {
        case CompoundPackage.REPLACE_IN_ELIST__REMOVE_CHANGE:
            setRemoveChange((RemoveFromEList<T>) newValue);
            return;
        case CompoundPackage.REPLACE_IN_ELIST__INSERT_CHANGE:
            setInsertChange((InsertInEList<T>) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eUnset(final int featureID) {
        switch (featureID) {
        case CompoundPackage.REPLACE_IN_ELIST__REMOVE_CHANGE:
            setRemoveChange((RemoveFromEList<T>) null);
            return;
        case CompoundPackage.REPLACE_IN_ELIST__INSERT_CHANGE:
            setInsertChange((InsertInEList<T>) null);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean eIsSet(final int featureID) {
        switch (featureID) {
        case CompoundPackage.REPLACE_IN_ELIST__REMOVE_CHANGE:
            return this.removeChange != null;
        case CompoundPackage.REPLACE_IN_ELIST__INSERT_CHANGE:
            return this.insertChange != null;
        }
        return super.eIsSet(featureID);
    }

} // ReplaceInEListImpl
