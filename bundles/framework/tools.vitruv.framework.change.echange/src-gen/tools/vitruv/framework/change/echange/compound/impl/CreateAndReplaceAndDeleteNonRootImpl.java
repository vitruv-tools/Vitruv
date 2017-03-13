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
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot;

import tools.vitruv.framework.change.echange.eobject.CreateEObject;
import tools.vitruv.framework.change.echange.eobject.DeleteEObject;

import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Create And Replace And Delete Non Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.impl.CreateAndReplaceAndDeleteNonRootImpl#getCreateChange <em>Create Change</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.impl.CreateAndReplaceAndDeleteNonRootImpl#getReplaceChange <em>Replace Change</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.impl.CreateAndReplaceAndDeleteNonRootImpl#getDeleteChange <em>Delete Change</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CreateAndReplaceAndDeleteNonRootImpl<A extends EObject, T extends EObject> extends CompoundEChangeImpl implements CreateAndReplaceAndDeleteNonRoot<A, T> {
	/**
	 * The cached value of the '{@link #getCreateChange() <em>Create Change</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreateChange()
	 * @generated
	 * @ordered
	 */
	protected CreateEObject<T> createChange;

	/**
	 * The cached value of the '{@link #getReplaceChange() <em>Replace Change</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReplaceChange()
	 * @generated
	 * @ordered
	 */
	protected ReplaceSingleValuedEReference<A, T> replaceChange;

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
	protected CreateAndReplaceAndDeleteNonRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompoundPackage.Literals.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CreateEObject<T> getCreateChange() {
		return createChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCreateChange(CreateEObject<T> newCreateChange, NotificationChain msgs) {
		CreateEObject<T> oldCreateChange = createChange;
		createChange = newCreateChange;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__CREATE_CHANGE, oldCreateChange, newCreateChange);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreateChange(CreateEObject<T> newCreateChange) {
		if (newCreateChange != createChange) {
			NotificationChain msgs = null;
			if (createChange != null)
				msgs = ((InternalEObject)createChange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__CREATE_CHANGE, null, msgs);
			if (newCreateChange != null)
				msgs = ((InternalEObject)newCreateChange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__CREATE_CHANGE, null, msgs);
			msgs = basicSetCreateChange(newCreateChange, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__CREATE_CHANGE, newCreateChange, newCreateChange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReplaceSingleValuedEReference<A, T> getReplaceChange() {
		return replaceChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReplaceChange(ReplaceSingleValuedEReference<A, T> newReplaceChange, NotificationChain msgs) {
		ReplaceSingleValuedEReference<A, T> oldReplaceChange = replaceChange;
		replaceChange = newReplaceChange;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__REPLACE_CHANGE, oldReplaceChange, newReplaceChange);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReplaceChange(ReplaceSingleValuedEReference<A, T> newReplaceChange) {
		if (newReplaceChange != replaceChange) {
			NotificationChain msgs = null;
			if (replaceChange != null)
				msgs = ((InternalEObject)replaceChange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__REPLACE_CHANGE, null, msgs);
			if (newReplaceChange != null)
				msgs = ((InternalEObject)newReplaceChange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__REPLACE_CHANGE, null, msgs);
			msgs = basicSetReplaceChange(newReplaceChange, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__REPLACE_CHANGE, newReplaceChange, newReplaceChange));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__DELETE_CHANGE, oldDeleteChange, newDeleteChange);
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
				msgs = ((InternalEObject)deleteChange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__DELETE_CHANGE, null, msgs);
			if (newDeleteChange != null)
				msgs = ((InternalEObject)newDeleteChange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__DELETE_CHANGE, null, msgs);
			msgs = basicSetDeleteChange(newDeleteChange, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__DELETE_CHANGE, newDeleteChange, newDeleteChange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AtomicEChange> getAtomicChanges() {
		final BasicEList<AtomicEChange> result = new BasicEList<AtomicEChange>();
		CreateEObject<T> _createChange = this.getCreateChange();
		result.add(_createChange);
		ReplaceSingleValuedEReference<A, T> _replaceChange = this.getReplaceChange();
		result.add(_replaceChange);
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
			case CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__CREATE_CHANGE:
				return basicSetCreateChange(null, msgs);
			case CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__REPLACE_CHANGE:
				return basicSetReplaceChange(null, msgs);
			case CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__DELETE_CHANGE:
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
			case CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__CREATE_CHANGE:
				return getCreateChange();
			case CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__REPLACE_CHANGE:
				return getReplaceChange();
			case CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__DELETE_CHANGE:
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
			case CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__CREATE_CHANGE:
				setCreateChange((CreateEObject<T>)newValue);
				return;
			case CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__REPLACE_CHANGE:
				setReplaceChange((ReplaceSingleValuedEReference<A, T>)newValue);
				return;
			case CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__DELETE_CHANGE:
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
			case CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__CREATE_CHANGE:
				setCreateChange((CreateEObject<T>)null);
				return;
			case CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__REPLACE_CHANGE:
				setReplaceChange((ReplaceSingleValuedEReference<A, T>)null);
				return;
			case CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__DELETE_CHANGE:
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
			case CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__CREATE_CHANGE:
				return createChange != null;
			case CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__REPLACE_CHANGE:
				return replaceChange != null;
			case CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__DELETE_CHANGE:
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
			case CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT___GET_ATOMIC_CHANGES:
				return getAtomicChanges();
		}
		return super.eInvoke(operationID, arguments);
	}

} //CreateAndReplaceAndDeleteNonRootImpl
