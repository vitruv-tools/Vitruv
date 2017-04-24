/**
 */
package model.branch.impl;

import java.util.Collection;

import model.Author;
import model.ModelPackage;

import model.branch.Branch;
import model.branch.BranchPackage;

import model.commit.Commit;

import model.impl.NamedImpl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Branch</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link model.branch.impl.BranchImpl#getCurrentHeadCommit <em>Current Head Commit</em>}</li>
 *   <li>{@link model.branch.impl.BranchImpl#getOwner <em>Owner</em>}</li>
 *   <li>{@link model.branch.impl.BranchImpl#getContributors <em>Contributors</em>}</li>
 *   <li>{@link model.branch.impl.BranchImpl#getBranchedFrom <em>Branched From</em>}</li>
 *   <li>{@link model.branch.impl.BranchImpl#getChildBranches <em>Child Branches</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BranchImpl extends NamedImpl implements Branch {
	/**
	 * The cached value of the '{@link #getCurrentHeadCommit() <em>Current Head Commit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentHeadCommit()
	 * @generated
	 * @ordered
	 */
	protected Commit currentHeadCommit;

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
	 * The cached value of the '{@link #getContributors() <em>Contributors</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContributors()
	 * @generated
	 * @ordered
	 */
	protected EList<Author> contributors;

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
	 * The cached value of the '{@link #getChildBranches() <em>Child Branches</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildBranches()
	 * @generated
	 * @ordered
	 */
	protected EList<Branch> childBranches;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BranchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BranchPackage.Literals.BRANCH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Commit getCurrentHeadCommit() {
		if (currentHeadCommit != null && currentHeadCommit.eIsProxy()) {
			InternalEObject oldCurrentHeadCommit = (InternalEObject)currentHeadCommit;
			currentHeadCommit = (Commit)eResolveProxy(oldCurrentHeadCommit);
			if (currentHeadCommit != oldCurrentHeadCommit) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BranchPackage.BRANCH__CURRENT_HEAD_COMMIT, oldCurrentHeadCommit, currentHeadCommit));
			}
		}
		return currentHeadCommit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Commit basicGetCurrentHeadCommit() {
		return currentHeadCommit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCurrentHeadCommit(Commit newCurrentHeadCommit) {
		Commit oldCurrentHeadCommit = currentHeadCommit;
		currentHeadCommit = newCurrentHeadCommit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BranchPackage.BRANCH__CURRENT_HEAD_COMMIT, oldCurrentHeadCommit, currentHeadCommit));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BranchPackage.BRANCH__OWNER, oldOwner, owner));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BranchPackage.BRANCH__OWNER, oldOwner, newOwner);
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
				msgs = ((InternalEObject)owner).eInverseRemove(this, ModelPackage.AUTHOR__OWNED_BRANCHES, Author.class, msgs);
			if (newOwner != null)
				msgs = ((InternalEObject)newOwner).eInverseAdd(this, ModelPackage.AUTHOR__OWNED_BRANCHES, Author.class, msgs);
			msgs = basicSetOwner(newOwner, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BranchPackage.BRANCH__OWNER, newOwner, newOwner));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Author> getContributors() {
		if (contributors == null) {
			contributors = new EObjectWithInverseResolvingEList.ManyInverse<Author>(Author.class, this, BranchPackage.BRANCH__CONTRIBUTORS, ModelPackage.AUTHOR__CONTRIBUTED_BRANCHES);
		}
		return contributors;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BranchPackage.BRANCH__BRANCHED_FROM, oldBranchedFrom, branchedFrom));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BranchPackage.BRANCH__BRANCHED_FROM, oldBranchedFrom, newBranchedFrom);
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
			eNotify(new ENotificationImpl(this, Notification.SET, BranchPackage.BRANCH__BRANCHED_FROM, newBranchedFrom, newBranchedFrom));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Branch> getChildBranches() {
		if (childBranches == null) {
			childBranches = new EObjectWithInverseResolvingEList<Branch>(Branch.class, this, BranchPackage.BRANCH__CHILD_BRANCHES, BranchPackage.BRANCH__BRANCHED_FROM);
		}
		return childBranches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BranchPackage.BRANCH__OWNER:
				if (owner != null)
					msgs = ((InternalEObject)owner).eInverseRemove(this, ModelPackage.AUTHOR__OWNED_BRANCHES, Author.class, msgs);
				return basicSetOwner((Author)otherEnd, msgs);
			case BranchPackage.BRANCH__CONTRIBUTORS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getContributors()).basicAdd(otherEnd, msgs);
			case BranchPackage.BRANCH__BRANCHED_FROM:
				if (branchedFrom != null)
					msgs = ((InternalEObject)branchedFrom).eInverseRemove(this, BranchPackage.BRANCH__CHILD_BRANCHES, Branch.class, msgs);
				return basicSetBranchedFrom((Branch)otherEnd, msgs);
			case BranchPackage.BRANCH__CHILD_BRANCHES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getChildBranches()).basicAdd(otherEnd, msgs);
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
			case BranchPackage.BRANCH__OWNER:
				return basicSetOwner(null, msgs);
			case BranchPackage.BRANCH__CONTRIBUTORS:
				return ((InternalEList<?>)getContributors()).basicRemove(otherEnd, msgs);
			case BranchPackage.BRANCH__BRANCHED_FROM:
				return basicSetBranchedFrom(null, msgs);
			case BranchPackage.BRANCH__CHILD_BRANCHES:
				return ((InternalEList<?>)getChildBranches()).basicRemove(otherEnd, msgs);
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
			case BranchPackage.BRANCH__CURRENT_HEAD_COMMIT:
				if (resolve) return getCurrentHeadCommit();
				return basicGetCurrentHeadCommit();
			case BranchPackage.BRANCH__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case BranchPackage.BRANCH__CONTRIBUTORS:
				return getContributors();
			case BranchPackage.BRANCH__BRANCHED_FROM:
				if (resolve) return getBranchedFrom();
				return basicGetBranchedFrom();
			case BranchPackage.BRANCH__CHILD_BRANCHES:
				return getChildBranches();
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
			case BranchPackage.BRANCH__CURRENT_HEAD_COMMIT:
				setCurrentHeadCommit((Commit)newValue);
				return;
			case BranchPackage.BRANCH__OWNER:
				setOwner((Author)newValue);
				return;
			case BranchPackage.BRANCH__CONTRIBUTORS:
				getContributors().clear();
				getContributors().addAll((Collection<? extends Author>)newValue);
				return;
			case BranchPackage.BRANCH__BRANCHED_FROM:
				setBranchedFrom((Branch)newValue);
				return;
			case BranchPackage.BRANCH__CHILD_BRANCHES:
				getChildBranches().clear();
				getChildBranches().addAll((Collection<? extends Branch>)newValue);
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
			case BranchPackage.BRANCH__CURRENT_HEAD_COMMIT:
				setCurrentHeadCommit((Commit)null);
				return;
			case BranchPackage.BRANCH__OWNER:
				setOwner((Author)null);
				return;
			case BranchPackage.BRANCH__CONTRIBUTORS:
				getContributors().clear();
				return;
			case BranchPackage.BRANCH__BRANCHED_FROM:
				setBranchedFrom((Branch)null);
				return;
			case BranchPackage.BRANCH__CHILD_BRANCHES:
				getChildBranches().clear();
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
			case BranchPackage.BRANCH__CURRENT_HEAD_COMMIT:
				return currentHeadCommit != null;
			case BranchPackage.BRANCH__OWNER:
				return owner != null;
			case BranchPackage.BRANCH__CONTRIBUTORS:
				return contributors != null && !contributors.isEmpty();
			case BranchPackage.BRANCH__BRANCHED_FROM:
				return branchedFrom != null;
			case BranchPackage.BRANCH__CHILD_BRANCHES:
				return childBranches != null && !childBranches.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //BranchImpl
