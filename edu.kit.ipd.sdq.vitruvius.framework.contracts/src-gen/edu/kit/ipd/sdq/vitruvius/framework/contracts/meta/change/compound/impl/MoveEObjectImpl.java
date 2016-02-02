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

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EAtomicChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.UpdateEReference;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Move EObject</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.MoveEObjectImpl#getSubtractWhereChange
 * <em>Subtract Where Change</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.MoveEObjectImpl#getSubtractWhatChange
 * <em>Subtract What Change</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.MoveEObjectImpl#getAddWhereChange
 * <em>Add Where Change</em>}</li>
 * <li>
 * {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.MoveEObjectImpl#getAddWhatChange
 * <em>Add What Change</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MoveEObjectImpl<A extends EObject, B extends EObject, T extends EObject> extends ECompoundChangeImpl
        implements MoveEObject<A, B, T> {
    /**
     * The cached value of the '{@link #getSubtractWhereChange() <em>Subtract Where Change</em>}'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getSubtractWhereChange()
     * @generated
     * @ordered
     */
    protected UpdateEReference<A> subtractWhereChange;

    /**
     * The cached value of the '{@link #getSubtractWhatChange() <em>Subtract What Change</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getSubtractWhatChange()
     * @generated
     * @ordered
     */
    protected SubtractiveEReferenceChange subtractWhatChange;

    /**
     * The cached value of the '{@link #getAddWhereChange() <em>Add Where Change</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getAddWhereChange()
     * @generated
     * @ordered
     */
    protected UpdateEReference<B> addWhereChange;

    /**
     * The cached value of the '{@link #getAddWhatChange() <em>Add What Change</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getAddWhatChange()
     * @generated
     * @ordered
     */
    protected AdditiveEReferenceChange<T> addWhatChange;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected MoveEObjectImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CompoundPackage.Literals.MOVE_EOBJECT;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public UpdateEReference<A> getSubtractWhereChange() {
        if (this.subtractWhereChange != null && this.subtractWhereChange.eIsProxy()) {
            InternalEObject oldSubtractWhereChange = (InternalEObject) this.subtractWhereChange;
            this.subtractWhereChange = (UpdateEReference<A>) eResolveProxy(oldSubtractWhereChange);
            if (this.subtractWhereChange != oldSubtractWhereChange) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHERE_CHANGE, oldSubtractWhereChange,
                            this.subtractWhereChange));
            }
        }
        return this.subtractWhereChange;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public UpdateEReference<A> basicGetSubtractWhereChange() {
        return this.subtractWhereChange;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setSubtractWhereChange(final UpdateEReference<A> newSubtractWhereChange) {
        UpdateEReference<A> oldSubtractWhereChange = this.subtractWhereChange;
        this.subtractWhereChange = newSubtractWhereChange;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHERE_CHANGE,
                    oldSubtractWhereChange, this.subtractWhereChange));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public SubtractiveEReferenceChange getSubtractWhatChange() {
        return this.subtractWhatChange;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetSubtractWhatChange(final SubtractiveEReferenceChange newSubtractWhatChange,
            NotificationChain msgs) {
        SubtractiveEReferenceChange oldSubtractWhatChange = this.subtractWhatChange;
        this.subtractWhatChange = newSubtractWhatChange;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE, oldSubtractWhatChange, newSubtractWhatChange);
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
    public void setSubtractWhatChange(final SubtractiveEReferenceChange newSubtractWhatChange) {
        if (newSubtractWhatChange != this.subtractWhatChange) {
            NotificationChain msgs = null;
            if (this.subtractWhatChange != null)
                msgs = ((InternalEObject) this.subtractWhatChange).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE, null, msgs);
            if (newSubtractWhatChange != null)
                msgs = ((InternalEObject) newSubtractWhatChange).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE, null, msgs);
            msgs = basicSetSubtractWhatChange(newSubtractWhatChange, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE,
                    newSubtractWhatChange, newSubtractWhatChange));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public UpdateEReference<B> getAddWhereChange() {
        if (this.addWhereChange != null && this.addWhereChange.eIsProxy()) {
            InternalEObject oldAddWhereChange = (InternalEObject) this.addWhereChange;
            this.addWhereChange = (UpdateEReference<B>) eResolveProxy(oldAddWhereChange);
            if (this.addWhereChange != oldAddWhereChange) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            CompoundPackage.MOVE_EOBJECT__ADD_WHERE_CHANGE, oldAddWhereChange, this.addWhereChange));
            }
        }
        return this.addWhereChange;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public UpdateEReference<B> basicGetAddWhereChange() {
        return this.addWhereChange;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setAddWhereChange(final UpdateEReference<B> newAddWhereChange) {
        UpdateEReference<B> oldAddWhereChange = this.addWhereChange;
        this.addWhereChange = newAddWhereChange;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.MOVE_EOBJECT__ADD_WHERE_CHANGE,
                    oldAddWhereChange, this.addWhereChange));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AdditiveEReferenceChange<T> getAddWhatChange() {
        return this.addWhatChange;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetAddWhatChange(final AdditiveEReferenceChange<T> newAddWhatChange,
            NotificationChain msgs) {
        AdditiveEReferenceChange<T> oldAddWhatChange = this.addWhatChange;
        this.addWhatChange = newAddWhatChange;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    CompoundPackage.MOVE_EOBJECT__ADD_WHAT_CHANGE, oldAddWhatChange, newAddWhatChange);
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
    public void setAddWhatChange(final AdditiveEReferenceChange<T> newAddWhatChange) {
        if (newAddWhatChange != this.addWhatChange) {
            NotificationChain msgs = null;
            if (this.addWhatChange != null)
                msgs = ((InternalEObject) this.addWhatChange).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - CompoundPackage.MOVE_EOBJECT__ADD_WHAT_CHANGE, null, msgs);
            if (newAddWhatChange != null)
                msgs = ((InternalEObject) newAddWhatChange).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - CompoundPackage.MOVE_EOBJECT__ADD_WHAT_CHANGE, null, msgs);
            msgs = basicSetAddWhatChange(newAddWhatChange, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.MOVE_EOBJECT__ADD_WHAT_CHANGE,
                    newAddWhatChange, newAddWhatChange));
    }

    /**
     * @generated NOT
     */
    @Override
    public EList<EAtomicChange> getAtomicChanges() {
        BasicEList<EAtomicChange> list = new BasicEList<EAtomicChange>();
        list.add(getSubtractWhatChange());
        list.add(getAddWhatChange());
        return list;
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
        case CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE:
            return basicSetSubtractWhatChange(null, msgs);
        case CompoundPackage.MOVE_EOBJECT__ADD_WHAT_CHANGE:
            return basicSetAddWhatChange(null, msgs);
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
        case CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHERE_CHANGE:
            if (resolve)
                return getSubtractWhereChange();
            return basicGetSubtractWhereChange();
        case CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE:
            return getSubtractWhatChange();
        case CompoundPackage.MOVE_EOBJECT__ADD_WHERE_CHANGE:
            if (resolve)
                return getAddWhereChange();
            return basicGetAddWhereChange();
        case CompoundPackage.MOVE_EOBJECT__ADD_WHAT_CHANGE:
            return getAddWhatChange();
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
        case CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHERE_CHANGE:
            setSubtractWhereChange((UpdateEReference<A>) newValue);
            return;
        case CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE:
            setSubtractWhatChange((SubtractiveEReferenceChange) newValue);
            return;
        case CompoundPackage.MOVE_EOBJECT__ADD_WHERE_CHANGE:
            setAddWhereChange((UpdateEReference<B>) newValue);
            return;
        case CompoundPackage.MOVE_EOBJECT__ADD_WHAT_CHANGE:
            setAddWhatChange((AdditiveEReferenceChange<T>) newValue);
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
        case CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHERE_CHANGE:
            setSubtractWhereChange((UpdateEReference<A>) null);
            return;
        case CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE:
            setSubtractWhatChange((SubtractiveEReferenceChange) null);
            return;
        case CompoundPackage.MOVE_EOBJECT__ADD_WHERE_CHANGE:
            setAddWhereChange((UpdateEReference<B>) null);
            return;
        case CompoundPackage.MOVE_EOBJECT__ADD_WHAT_CHANGE:
            setAddWhatChange((AdditiveEReferenceChange<T>) null);
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
        case CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHERE_CHANGE:
            return this.subtractWhereChange != null;
        case CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE:
            return this.subtractWhatChange != null;
        case CompoundPackage.MOVE_EOBJECT__ADD_WHERE_CHANGE:
            return this.addWhereChange != null;
        case CompoundPackage.MOVE_EOBJECT__ADD_WHAT_CHANGE:
            return this.addWhatChange != null;
        }
        return super.eIsSet(featureID);
    }

} // MoveEObjectImpl
