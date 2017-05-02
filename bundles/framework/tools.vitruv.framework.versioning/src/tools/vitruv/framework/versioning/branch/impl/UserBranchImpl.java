/**
 */
package tools.vitruv.framework.versioning.branch.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import tools.vitruv.framework.versioning.Author;
import tools.vitruv.framework.versioning.VersioningPackage;

import tools.vitruv.framework.versioning.branch.Branch;
import tools.vitruv.framework.versioning.branch.BranchPackage;
import tools.vitruv.framework.versioning.branch.UserBranch;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>User Branch</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.branch.impl.UserBranchImpl#getOwner <em>Owner</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.branch.impl.UserBranchImpl#getBranchedFrom <em>Branched From</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UserBranchImpl extends BranchImpl implements UserBranch {
	/**
	 * The cached value of the '{@link #getOwner() <em>Owner</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwner()
	 * @generated
	 * @ordered
	 */
	protected Author owner;

	/**
	 * The cached value of the '{@link #getBranchedFrom() <em>Branched From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBranchedFrom()
	 * @generated
	 * @ordered
	 */
	protected Branch branchedFrom;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UserBranchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BranchPackage.Literals.USER_BRANCH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Author getOwner() {
		if (owner != null && owner.eIsProxy()) {
			InternalEObject oldOwner = (InternalEObject)owner;
			owner = (Author)eResolveProxy(oldOwner);
			if (owner != oldOwner) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BranchPackage.USER_BRANCH__OWNER, oldOwner, owner));
			}
		}
		return owner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Author basicGetOwner() {
		return owner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOwner(Author newOwner, NotificationChain msgs) {
		Author oldOwner = owner;
		owner = newOwner;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BranchPackage.USER_BRANCH__OWNER, oldOwner, newOwner);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwner(Author newOwner) {
		if (newOwner != owner) {
			NotificationChain msgs = null;
			if (owner != null)
				msgs = ((InternalEObject)owner).eInverseRemove(this, VersioningPackage.AUTHOR__OWNED_BRANCHES, Author.class, msgs);
			if (newOwner != null)
				msgs = ((InternalEObject)newOwner).eInverseAdd(this, VersioningPackage.AUTHOR__OWNED_BRANCHES, Author.class, msgs);
			msgs = basicSetOwner(newOwner, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BranchPackage.USER_BRANCH__OWNER, newOwner, newOwner));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Branch getBranchedFrom() {
		if (branchedFrom != null && branchedFrom.eIsProxy()) {
			InternalEObject oldBranchedFrom = (InternalEObject)branchedFrom;
			branchedFrom = (Branch)eResolveProxy(oldBranchedFrom);
			if (branchedFrom != oldBranchedFrom) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BranchPackage.USER_BRANCH__BRANCHED_FROM, oldBranchedFrom, branchedFrom));
			}
		}
		return branchedFrom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Branch basicGetBranchedFrom() {
		return branchedFrom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBranchedFrom(Branch newBranchedFrom, NotificationChain msgs) {
		Branch oldBranchedFrom = branchedFrom;
		branchedFrom = newBranchedFrom;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BranchPackage.USER_BRANCH__BRANCHED_FROM, oldBranchedFrom, newBranchedFrom);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBranchedFrom(Branch newBranchedFrom) {
		if (newBranchedFrom != branchedFrom) {
			NotificationChain msgs = null;
			if (branchedFrom != null)
				msgs = ((InternalEObject)branchedFrom).eInverseRemove(this, BranchPackage.BRANCH__CHILD_BRANCHES, Branch.class, msgs);
			if (newBranchedFrom != null)
				msgs = ((InternalEObject)newBranchedFrom).eInverseAdd(this, BranchPackage.BRANCH__CHILD_BRANCHES, Branch.class, msgs);
			msgs = basicSetBranchedFrom(newBranchedFrom, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BranchPackage.USER_BRANCH__BRANCHED_FROM, newBranchedFrom, newBranchedFrom));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BranchPackage.USER_BRANCH__OWNER:
				if (owner != null)
					msgs = ((InternalEObject)owner).eInverseRemove(this, VersioningPackage.AUTHOR__OWNED_BRANCHES, Author.class, msgs);
				return basicSetOwner((Author)otherEnd, msgs);
			case BranchPackage.USER_BRANCH__BRANCHED_FROM:
				if (branchedFrom != null)
					msgs = ((InternalEObject)branchedFrom).eInverseRemove(this, BranchPackage.BRANCH__CHILD_BRANCHES, Branch.class, msgs);
				return basicSetBranchedFrom((Branch)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BranchPackage.USER_BRANCH__OWNER:
				return basicSetOwner(null, msgs);
			case BranchPackage.USER_BRANCH__BRANCHED_FROM:
				return basicSetBranchedFrom(null, msgs);
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
			case BranchPackage.USER_BRANCH__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case BranchPackage.USER_BRANCH__BRANCHED_FROM:
				if (resolve) return getBranchedFrom();
				return basicGetBranchedFrom();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BranchPackage.USER_BRANCH__OWNER:
				setOwner((Author)newValue);
				return;
			case BranchPackage.USER_BRANCH__BRANCHED_FROM:
				setBranchedFrom((Branch)newValue);
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
			case BranchPackage.USER_BRANCH__OWNER:
				setOwner((Author)null);
				return;
			case BranchPackage.USER_BRANCH__BRANCHED_FROM:
				setBranchedFrom((Branch)null);
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
			case BranchPackage.USER_BRANCH__OWNER:
				return owner != null;
			case BranchPackage.USER_BRANCH__BRANCHED_FROM:
				return branchedFrom != null;
		}
		return super.eIsSet(featureID);
	}

} //UserBranchImpl
