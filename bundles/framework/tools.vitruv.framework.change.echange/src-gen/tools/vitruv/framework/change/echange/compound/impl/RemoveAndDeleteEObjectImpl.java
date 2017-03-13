/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import tools.vitruv.framework.change.echange.AtomicEChange;

import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteEObject;

import tools.vitruv.framework.change.echange.eobject.DeleteEObject;
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Remove And Delete EObject</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.impl.RemoveAndDeleteEObjectImpl#getRemoveChange <em>Remove Change</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.impl.RemoveAndDeleteEObjectImpl#getDeleteChange <em>Delete Change</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class RemoveAndDeleteEObjectImpl<T extends EObject, C extends EObjectSubtractedEChange<T>> extends CompoundEChangeImpl implements RemoveAndDeleteEObject<T, C> {
	/**
	 * The cached value of the '{@link #getRemoveChange() <em>Remove Change</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemoveChange()
	 * @generated
	 * @ordered
	 */
	protected C removeChange;

	/**
	 * The cached value of the '{@link #getDeleteChange() <em>Delete Change</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeleteChange()
	 * @generated
	 * @ordered
	 */
	protected DeleteEObject<T> deleteChange;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RemoveAndDeleteEObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompoundPackage.Literals.REMOVE_AND_DELETE_EOBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public C getRemoveChange() {
		return removeChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRemoveChange(C newRemoveChange, NotificationChain msgs) {
		C oldRemoveChange = removeChange;
		removeChange = newRemoveChange;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CompoundPackage.REMOVE_AND_DELETE_EOBJECT__REMOVE_CHANGE, oldRemoveChange, newRemoveChange);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRemoveChange(C newRemoveChange) {
		if (newRemoveChange != removeChange) {
			NotificationChain msgs = null;
			if (removeChange != null)
				msgs = ((InternalEObject)removeChange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.REMOVE_AND_DELETE_EOBJECT__REMOVE_CHANGE, null, msgs);
			if (newRemoveChange != null)
				msgs = ((InternalEObject)newRemoveChange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.REMOVE_AND_DELETE_EOBJECT__REMOVE_CHANGE, null, msgs);
			msgs = basicSetRemoveChange(newRemoveChange, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.REMOVE_AND_DELETE_EOBJECT__REMOVE_CHANGE, newRemoveChange, newRemoveChange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeleteEObject<T> getDeleteChange() {
		return deleteChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeleteChange(DeleteEObject<T> newDeleteChange, NotificationChain msgs) {
		DeleteEObject<T> oldDeleteChange = deleteChange;
		deleteChange = newDeleteChange;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CompoundPackage.REMOVE_AND_DELETE_EOBJECT__DELETE_CHANGE, oldDeleteChange, newDeleteChange);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeleteChange(DeleteEObject<T> newDeleteChange) {
		if (newDeleteChange != deleteChange) {
			NotificationChain msgs = null;
			if (deleteChange != null)
				msgs = ((InternalEObject)deleteChange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.REMOVE_AND_DELETE_EOBJECT__DELETE_CHANGE, null, msgs);
			if (newDeleteChange != null)
				msgs = ((InternalEObject)newDeleteChange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.REMOVE_AND_DELETE_EOBJECT__DELETE_CHANGE, null, msgs);
			msgs = basicSetDeleteChange(newDeleteChange, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.REMOVE_AND_DELETE_EOBJECT__DELETE_CHANGE, newDeleteChange, newDeleteChange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AtomicEChange> getAtomicChanges() {
		final BasicEList<AtomicEChange> result = new BasicEList<AtomicEChange>();
		C _removeChange = this.getRemoveChange();
		result.add(_removeChange);
		DeleteEObject<T> _deleteChange = this.getDeleteChange();
		result.add(_deleteChange);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CompoundPackage.REMOVE_AND_DELETE_EOBJECT__REMOVE_CHANGE:
				return basicSetRemoveChange(null, msgs);
			case CompoundPackage.REMOVE_AND_DELETE_EOBJECT__DELETE_CHANGE:
				return basicSetDeleteChange(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CompoundPackage.REMOVE_AND_DELETE_EOBJECT__REMOVE_CHANGE:
				return getRemoveChange();
			case CompoundPackage.REMOVE_AND_DELETE_EOBJECT__DELETE_CHANGE:
				return getDeleteChange();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CompoundPackage.REMOVE_AND_DELETE_EOBJECT__REMOVE_CHANGE:
				setRemoveChange((C)newValue);
				return;
			case CompoundPackage.REMOVE_AND_DELETE_EOBJECT__DELETE_CHANGE:
				setDeleteChange((DeleteEObject<T>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CompoundPackage.REMOVE_AND_DELETE_EOBJECT__REMOVE_CHANGE:
				setRemoveChange((C)null);
				return;
			case CompoundPackage.REMOVE_AND_DELETE_EOBJECT__DELETE_CHANGE:
				setDeleteChange((DeleteEObject<T>)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CompoundPackage.REMOVE_AND_DELETE_EOBJECT__REMOVE_CHANGE:
				return removeChange != null;
			case CompoundPackage.REMOVE_AND_DELETE_EOBJECT__DELETE_CHANGE:
				return deleteChange != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case CompoundPackage.REMOVE_AND_DELETE_EOBJECT___GET_ATOMIC_CHANGES:
				return getAtomicChanges();
		}
		return super.eInvoke(operationID, arguments);
	}

} //RemoveAndDeleteEObjectImpl
