/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import com.google.common.base.Objects;

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
import tools.vitruv.framework.change.echange.compound.MoveEObject;

import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange;
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange;

import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Move EObject</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.impl.MoveEObjectImpl#getSubtractWhereChange <em>Subtract Where Change</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.impl.MoveEObjectImpl#getSubtractWhatChange <em>Subtract What Change</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.impl.MoveEObjectImpl#getAddWhereChange <em>Add Where Change</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.impl.MoveEObjectImpl#getAddWhatChange <em>Add What Change</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MoveEObjectImpl<A extends EObject, B extends EObject, T extends EObject> extends CompoundEChangeImpl implements MoveEObject<A, B, T> {
	/**
	 * The cached value of the '{@link #getSubtractWhereChange() <em>Subtract Where Change</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubtractWhereChange()
	 * @generated
	 * @ordered
	 */
	protected UpdateReferenceEChange<A> subtractWhereChange;

	/**
	 * The cached value of the '{@link #getSubtractWhatChange() <em>Subtract What Change</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubtractWhatChange()
	 * @generated
	 * @ordered
	 */
	protected EObjectSubtractedEChange<T> subtractWhatChange;

	/**
	 * The cached value of the '{@link #getAddWhereChange() <em>Add Where Change</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddWhereChange()
	 * @generated
	 * @ordered
	 */
	protected UpdateReferenceEChange<B> addWhereChange;

	/**
	 * The cached value of the '{@link #getAddWhatChange() <em>Add What Change</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddWhatChange()
	 * @generated
	 * @ordered
	 */
	protected EObjectAddedEChange<T> addWhatChange;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MoveEObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompoundPackage.Literals.MOVE_EOBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public UpdateReferenceEChange<A> getSubtractWhereChange() {
		if (subtractWhereChange != null && subtractWhereChange.eIsProxy()) {
			InternalEObject oldSubtractWhereChange = (InternalEObject)subtractWhereChange;
			subtractWhereChange = (UpdateReferenceEChange<A>)eResolveProxy(oldSubtractWhereChange);
			if (subtractWhereChange != oldSubtractWhereChange) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHERE_CHANGE, oldSubtractWhereChange, subtractWhereChange));
			}
		}
		return subtractWhereChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UpdateReferenceEChange<A> basicGetSubtractWhereChange() {
		return subtractWhereChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubtractWhereChange(UpdateReferenceEChange<A> newSubtractWhereChange) {
		UpdateReferenceEChange<A> oldSubtractWhereChange = subtractWhereChange;
		subtractWhereChange = newSubtractWhereChange;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHERE_CHANGE, oldSubtractWhereChange, subtractWhereChange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectSubtractedEChange<T> getSubtractWhatChange() {
		return subtractWhatChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubtractWhatChange(EObjectSubtractedEChange<T> newSubtractWhatChange, NotificationChain msgs) {
		EObjectSubtractedEChange<T> oldSubtractWhatChange = subtractWhatChange;
		subtractWhatChange = newSubtractWhatChange;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE, oldSubtractWhatChange, newSubtractWhatChange);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubtractWhatChange(EObjectSubtractedEChange<T> newSubtractWhatChange) {
		if (newSubtractWhatChange != subtractWhatChange) {
			NotificationChain msgs = null;
			if (subtractWhatChange != null)
				msgs = ((InternalEObject)subtractWhatChange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE, null, msgs);
			if (newSubtractWhatChange != null)
				msgs = ((InternalEObject)newSubtractWhatChange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE, null, msgs);
			msgs = basicSetSubtractWhatChange(newSubtractWhatChange, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE, newSubtractWhatChange, newSubtractWhatChange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public UpdateReferenceEChange<B> getAddWhereChange() {
		if (addWhereChange != null && addWhereChange.eIsProxy()) {
			InternalEObject oldAddWhereChange = (InternalEObject)addWhereChange;
			addWhereChange = (UpdateReferenceEChange<B>)eResolveProxy(oldAddWhereChange);
			if (addWhereChange != oldAddWhereChange) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CompoundPackage.MOVE_EOBJECT__ADD_WHERE_CHANGE, oldAddWhereChange, addWhereChange));
			}
		}
		return addWhereChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UpdateReferenceEChange<B> basicGetAddWhereChange() {
		return addWhereChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAddWhereChange(UpdateReferenceEChange<B> newAddWhereChange) {
		UpdateReferenceEChange<B> oldAddWhereChange = addWhereChange;
		addWhereChange = newAddWhereChange;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.MOVE_EOBJECT__ADD_WHERE_CHANGE, oldAddWhereChange, addWhereChange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectAddedEChange<T> getAddWhatChange() {
		return addWhatChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAddWhatChange(EObjectAddedEChange<T> newAddWhatChange, NotificationChain msgs) {
		EObjectAddedEChange<T> oldAddWhatChange = addWhatChange;
		addWhatChange = newAddWhatChange;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CompoundPackage.MOVE_EOBJECT__ADD_WHAT_CHANGE, oldAddWhatChange, newAddWhatChange);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAddWhatChange(EObjectAddedEChange<T> newAddWhatChange) {
		if (newAddWhatChange != addWhatChange) {
			NotificationChain msgs = null;
			if (addWhatChange != null)
				msgs = ((InternalEObject)addWhatChange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.MOVE_EOBJECT__ADD_WHAT_CHANGE, null, msgs);
			if (newAddWhatChange != null)
				msgs = ((InternalEObject)newAddWhatChange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.MOVE_EOBJECT__ADD_WHAT_CHANGE, null, msgs);
			msgs = basicSetAddWhatChange(newAddWhatChange, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.MOVE_EOBJECT__ADD_WHAT_CHANGE, newAddWhatChange, newAddWhatChange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AtomicEChange> getAtomicChanges() {
		final BasicEList<AtomicEChange> list = new BasicEList<AtomicEChange>();
		final UpdateReferenceEChange<A> subWhereChange = this.getSubtractWhereChange();
		boolean _notEquals = (!Objects.equal(subWhereChange, null));
		if (_notEquals) {
			list.add(subWhereChange);
		}
		EObjectSubtractedEChange<T> _subtractWhatChange = this.getSubtractWhatChange();
		list.add(_subtractWhatChange);
		final UpdateReferenceEChange<B> addWhereChange = this.getAddWhereChange();
		boolean _notEquals_1 = (!Objects.equal(addWhereChange, null));
		if (_notEquals_1) {
			list.add(addWhereChange);
		}
		EObjectAddedEChange<T> _addWhatChange = this.getAddWhatChange();
		list.add(_addWhatChange);
		return list;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE:
				return basicSetSubtractWhatChange(null, msgs);
			case CompoundPackage.MOVE_EOBJECT__ADD_WHAT_CHANGE:
				return basicSetAddWhatChange(null, msgs);
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
			case CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHERE_CHANGE:
				if (resolve) return getSubtractWhereChange();
				return basicGetSubtractWhereChange();
			case CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE:
				return getSubtractWhatChange();
			case CompoundPackage.MOVE_EOBJECT__ADD_WHERE_CHANGE:
				if (resolve) return getAddWhereChange();
				return basicGetAddWhereChange();
			case CompoundPackage.MOVE_EOBJECT__ADD_WHAT_CHANGE:
				return getAddWhatChange();
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
			case CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHERE_CHANGE:
				setSubtractWhereChange((UpdateReferenceEChange<A>)newValue);
				return;
			case CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE:
				setSubtractWhatChange((EObjectSubtractedEChange<T>)newValue);
				return;
			case CompoundPackage.MOVE_EOBJECT__ADD_WHERE_CHANGE:
				setAddWhereChange((UpdateReferenceEChange<B>)newValue);
				return;
			case CompoundPackage.MOVE_EOBJECT__ADD_WHAT_CHANGE:
				setAddWhatChange((EObjectAddedEChange<T>)newValue);
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
			case CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHERE_CHANGE:
				setSubtractWhereChange((UpdateReferenceEChange<A>)null);
				return;
			case CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE:
				setSubtractWhatChange((EObjectSubtractedEChange<T>)null);
				return;
			case CompoundPackage.MOVE_EOBJECT__ADD_WHERE_CHANGE:
				setAddWhereChange((UpdateReferenceEChange<B>)null);
				return;
			case CompoundPackage.MOVE_EOBJECT__ADD_WHAT_CHANGE:
				setAddWhatChange((EObjectAddedEChange<T>)null);
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
			case CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHERE_CHANGE:
				return subtractWhereChange != null;
			case CompoundPackage.MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE:
				return subtractWhatChange != null;
			case CompoundPackage.MOVE_EOBJECT__ADD_WHERE_CHANGE:
				return addWhereChange != null;
			case CompoundPackage.MOVE_EOBJECT__ADD_WHAT_CHANGE:
				return addWhatChange != null;
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
			case CompoundPackage.MOVE_EOBJECT___GET_ATOMIC_CHANGES:
				return getAtomicChanges();
		}
		return super.eInvoke(operationID, arguments);
	}

} //MoveEObjectImpl
