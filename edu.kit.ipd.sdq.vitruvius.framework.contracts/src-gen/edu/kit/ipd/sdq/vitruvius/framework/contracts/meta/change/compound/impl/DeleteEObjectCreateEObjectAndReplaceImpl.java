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
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplace;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.CreateEObject;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.DeleteEObject;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Delete EObject Create EObject And Replace</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectCreateEObjectAndReplaceImpl#getDeleteChange <em>Delete Change</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectCreateEObjectAndReplaceImpl#getCreateChange <em>Create Change</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectCreateEObjectAndReplaceImpl#getReplaceChange <em>Replace Change</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class DeleteEObjectCreateEObjectAndReplaceImpl<T extends EObject, R extends EChange>
        extends ECompoundChangeImpl implements DeleteEObjectCreateEObjectAndReplace<T, R> {
    /**
     * The cached value of the '{@link #getDeleteChange() <em>Delete Change</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDeleteChange()
     * @generated
     * @ordered
     */
    protected DeleteEObject<T> deleteChange;

    /**
     * The cached value of the '{@link #getCreateChange() <em>Create Change</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getCreateChange()
     * @generated
     * @ordered
     */
    protected CreateEObject<T> createChange;

    /**
     * The cached value of the '{@link #getReplaceChange() <em>Replace Change</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getReplaceChange()
     * @generated
     * @ordered
     */
    protected R replaceChange;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected DeleteEObjectCreateEObjectAndReplaceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CompoundPackage.Literals.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE;
    }

    /**
     * @generated NOT
     */
    @Override
    public EList<EChange> getComposedChanges() {
        BasicEList<EChange> list = new BasicEList<EChange>();
        list.add(getDeleteChange());
        list.add(getCreateChange());
        list.add(getReplaceChange());
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__DELETE_CHANGE, oldDeleteChange, newDeleteChange);
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
                msgs = ((InternalEObject)deleteChange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__DELETE_CHANGE, null, msgs);
            if (newDeleteChange != null)
                msgs = ((InternalEObject)newDeleteChange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__DELETE_CHANGE, null, msgs);
            msgs = basicSetDeleteChange(newDeleteChange, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__DELETE_CHANGE, newDeleteChange, newDeleteChange));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public CreateEObject<T> getCreateChange() {
        return createChange;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCreateChange(CreateEObject<T> newCreateChange, NotificationChain msgs) {
        CreateEObject<T> oldCreateChange = createChange;
        createChange = newCreateChange;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__CREATE_CHANGE, oldCreateChange, newCreateChange);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setCreateChange(CreateEObject<T> newCreateChange) {
        if (newCreateChange != createChange) {
            NotificationChain msgs = null;
            if (createChange != null)
                msgs = ((InternalEObject)createChange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__CREATE_CHANGE, null, msgs);
            if (newCreateChange != null)
                msgs = ((InternalEObject)newCreateChange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__CREATE_CHANGE, null, msgs);
            msgs = basicSetCreateChange(newCreateChange, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__CREATE_CHANGE, newCreateChange, newCreateChange));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public R getReplaceChange() {
        return replaceChange;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetReplaceChange(R newReplaceChange, NotificationChain msgs) {
        R oldReplaceChange = replaceChange;
        replaceChange = newReplaceChange;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__REPLACE_CHANGE, oldReplaceChange, newReplaceChange);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setReplaceChange(R newReplaceChange) {
        if (newReplaceChange != replaceChange) {
            NotificationChain msgs = null;
            if (replaceChange != null)
                msgs = ((InternalEObject)replaceChange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__REPLACE_CHANGE, null, msgs);
            if (newReplaceChange != null)
                msgs = ((InternalEObject)newReplaceChange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__REPLACE_CHANGE, null, msgs);
            msgs = basicSetReplaceChange(newReplaceChange, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__REPLACE_CHANGE, newReplaceChange, newReplaceChange));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__DELETE_CHANGE:
                return basicSetDeleteChange(null, msgs);
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__CREATE_CHANGE:
                return basicSetCreateChange(null, msgs);
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__REPLACE_CHANGE:
                return basicSetReplaceChange(null, msgs);
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
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__DELETE_CHANGE:
                return getDeleteChange();
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__CREATE_CHANGE:
                return getCreateChange();
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__REPLACE_CHANGE:
                return getReplaceChange();
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
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__DELETE_CHANGE:
                setDeleteChange((DeleteEObject<T>)newValue);
                return;
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__CREATE_CHANGE:
                setCreateChange((CreateEObject<T>)newValue);
                return;
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__REPLACE_CHANGE:
                setReplaceChange((R)newValue);
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
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__DELETE_CHANGE:
                setDeleteChange((DeleteEObject<T>)null);
                return;
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__CREATE_CHANGE:
                setCreateChange((CreateEObject<T>)null);
                return;
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__REPLACE_CHANGE:
                setReplaceChange((R)null);
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
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__DELETE_CHANGE:
                return deleteChange != null;
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__CREATE_CHANGE:
                return createChange != null;
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__REPLACE_CHANGE:
                return replaceChange != null;
        }
        return super.eIsSet(featureID);
    }

} // DeleteEObjectCreateEObjectAndReplaceImpl
