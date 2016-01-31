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
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CreateEObjectAndAdd;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.CreateEObject;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Create EObject And Add</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CreateEObjectAndAddImpl#getCreateChange <em>Create Change</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CreateEObjectAndAddImpl#getAddChange <em>Add Change</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CreateEObjectAndAddImpl<T extends EObject> extends ECompoundChangeImpl implements CreateEObjectAndAdd<T> {
    /**
     * The cached value of the '{@link #getCreateChange() <em>Create Change</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getCreateChange()
     * @generated
     * @ordered
     */
    protected CreateEObject<T> createChange;

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
    protected CreateEObjectAndAddImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CompoundPackage.Literals.CREATE_EOBJECT_AND_ADD;
    }

    /**
     * @generated NOT
     */
    @Override
    public EList<EChange> getComposedChanges() {
        BasicEList<EChange> list = new BasicEList<EChange>();
        list.add(getCreateChange());
        list.add(getAddChange());
        return list;
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CompoundPackage.CREATE_EOBJECT_AND_ADD__CREATE_CHANGE, oldCreateChange, newCreateChange);
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
                msgs = ((InternalEObject)createChange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.CREATE_EOBJECT_AND_ADD__CREATE_CHANGE, null, msgs);
            if (newCreateChange != null)
                msgs = ((InternalEObject)newCreateChange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.CREATE_EOBJECT_AND_ADD__CREATE_CHANGE, null, msgs);
            msgs = basicSetCreateChange(newCreateChange, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.CREATE_EOBJECT_AND_ADD__CREATE_CHANGE, newCreateChange, newCreateChange));
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CompoundPackage.CREATE_EOBJECT_AND_ADD__ADD_CHANGE, oldAddChange, newAddChange);
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
                msgs = ((InternalEObject)addChange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.CREATE_EOBJECT_AND_ADD__ADD_CHANGE, null, msgs);
            if (newAddChange != null)
                msgs = ((InternalEObject)newAddChange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.CREATE_EOBJECT_AND_ADD__ADD_CHANGE, null, msgs);
            msgs = basicSetAddChange(newAddChange, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.CREATE_EOBJECT_AND_ADD__ADD_CHANGE, newAddChange, newAddChange));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case CompoundPackage.CREATE_EOBJECT_AND_ADD__CREATE_CHANGE:
                return basicSetCreateChange(null, msgs);
            case CompoundPackage.CREATE_EOBJECT_AND_ADD__ADD_CHANGE:
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
            case CompoundPackage.CREATE_EOBJECT_AND_ADD__CREATE_CHANGE:
                return getCreateChange();
            case CompoundPackage.CREATE_EOBJECT_AND_ADD__ADD_CHANGE:
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
            case CompoundPackage.CREATE_EOBJECT_AND_ADD__CREATE_CHANGE:
                setCreateChange((CreateEObject<T>)newValue);
                return;
            case CompoundPackage.CREATE_EOBJECT_AND_ADD__ADD_CHANGE:
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
            case CompoundPackage.CREATE_EOBJECT_AND_ADD__CREATE_CHANGE:
                setCreateChange((CreateEObject<T>)null);
                return;
            case CompoundPackage.CREATE_EOBJECT_AND_ADD__ADD_CHANGE:
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
            case CompoundPackage.CREATE_EOBJECT_AND_ADD__CREATE_CHANGE:
                return createChange != null;
            case CompoundPackage.CREATE_EOBJECT_AND_ADD__ADD_CHANGE:
                return addChange != null;
        }
        return super.eIsSet(featureID);
    }

} // CreateEObjectAndAddImpl
