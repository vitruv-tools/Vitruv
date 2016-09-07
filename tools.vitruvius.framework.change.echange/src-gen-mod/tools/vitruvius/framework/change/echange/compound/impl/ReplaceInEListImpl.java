/**
 */
package tools.vitruvius.framework.change.echange.compound.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import tools.vitruvius.framework.change.echange.compound.impl.CompoundEChangeImpl;
import tools.vitruvius.framework.change.echange.AdditiveEChange;
import tools.vitruvius.framework.change.echange.AtomicEChange;
import tools.vitruvius.framework.change.echange.SubtractiveEChange;
import tools.vitruvius.framework.change.echange.compound.CompoundPackage;
import tools.vitruvius.framework.change.echange.compound.ReplaceInEList;
import tools.vitruvius.framework.change.echange.feature.FeatureEChange;
import tools.vitruvius.framework.change.echange.feature.list.InsertInListEChange;
import tools.vitruvius.framework.change.echange.feature.list.RemoveFromListEChange;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Replace In EList</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link tools.vitruvius.framework.change.echange.compound.impl.ReplaceInEListImpl#getRemoveChange
 * <em>Remove Change</em>}</li>
 * <li>
 * {@link tools.vitruvius.framework.change.echange.compound.impl.ReplaceInEListImpl#getInsertChange
 * <em>Insert Change</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReplaceInEListImpl<A extends EObject, F extends EStructuralFeature, T extends EObject, R extends RemoveFromListEChange<A, F> & FeatureEChange<A, F> & SubtractiveEChange<T>, I extends InsertInListEChange<A, F> & FeatureEChange<A, F> & AdditiveEChange<T>>
        extends CompoundEChangeImpl implements ReplaceInEList<A, F, T, R, I> {
    /**
     * The cached value of the '{@link #getRemoveChange() <em>Remove Change</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getRemoveChange()
     * @generated
     * @ordered
     */
    protected R removeChange;

    /**
     * The cached value of the '{@link #getInsertChange() <em>Insert Change</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getInsertChange()
     * @generated
     * @ordered
     */
    protected I insertChange;

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
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public R getRemoveChange() {
        return this.removeChange;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetRemoveChange(final R newRemoveChange, NotificationChain msgs) {
        R oldRemoveChange = this.removeChange;
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
    public void setRemoveChange(final R newRemoveChange) {
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
    public I getInsertChange() {
        return this.insertChange;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetInsertChange(final I newInsertChange, NotificationChain msgs) {
        I oldInsertChange = this.insertChange;
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
    public void setInsertChange(final I newInsertChange) {
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
            setRemoveChange((R) newValue);
            return;
        case CompoundPackage.REPLACE_IN_ELIST__INSERT_CHANGE:
            setInsertChange((I) newValue);
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
            setRemoveChange((R) null);
            return;
        case CompoundPackage.REPLACE_IN_ELIST__INSERT_CHANGE:
            setInsertChange((I) null);
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

    /**
     * @generated NOT
     */
    @Override
    public EList<AtomicEChange> getAtomicChanges() {
        BasicEList<AtomicEChange> list = new BasicEList<AtomicEChange>();
        list.add(getRemoveChange());
        list.add(getInsertChange());
        return list;
    }
} // ReplaceInEListImpl
