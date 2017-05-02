/**
 */
package tools.vitruv.framework.versioning.branch.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import tools.vitruv.framework.versioning.Author;
import tools.vitruv.framework.versioning.VersioningPackage;

import tools.vitruv.framework.versioning.branch.Branch;
import tools.vitruv.framework.versioning.branch.BranchPackage;

import tools.vitruv.framework.versioning.branch.UserBranch;
import tools.vitruv.framework.versioning.commit.Commit;

import tools.vitruv.framework.versioning.impl.NamedImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Branch</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.branch.impl.BranchImpl#getCurrentHeadCommit <em>Current Head Commit</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.branch.impl.BranchImpl#getContributors <em>Contributors</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.branch.impl.BranchImpl#getChildBranches <em>Child Branches</em>}</li>
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
	 * The cached value of the '{@link #getContributors() <em>Contributors</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContributors()
	 * @generated
	 * @ordered
	 */
	protected EList<Author> contributors;

	/**
	 * The cached value of the '{@link #getChildBranches() <em>Child Branches</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildBranches()
	 * @generated
	 * @ordered
	 */
	protected EList<UserBranch> childBranches;

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
	public EList<Author> getContributors() {
		if (contributors == null) {
			contributors = new EObjectWithInverseResolvingEList.ManyInverse<Author>(Author.class, this, BranchPackage.BRANCH__CONTRIBUTORS, VersioningPackage.AUTHOR__CONTRIBUTED_BRANCHES);
		}
		return contributors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UserBranch> getChildBranches() {
		if (childBranches == null) {
			childBranches = new EObjectWithInverseResolvingEList<UserBranch>(UserBranch.class, this, BranchPackage.BRANCH__CHILD_BRANCHES, BranchPackage.USER_BRANCH__BRANCHED_FROM);
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
			case BranchPackage.BRANCH__CONTRIBUTORS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getContributors()).basicAdd(otherEnd, msgs);
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
			case BranchPackage.BRANCH__CONTRIBUTORS:
				return ((InternalEList<?>)getContributors()).basicRemove(otherEnd, msgs);
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
			case BranchPackage.BRANCH__CONTRIBUTORS:
				return getContributors();
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
			case BranchPackage.BRANCH__CONTRIBUTORS:
				getContributors().clear();
				getContributors().addAll((Collection<? extends Author>)newValue);
				return;
			case BranchPackage.BRANCH__CHILD_BRANCHES:
				getChildBranches().clear();
				getChildBranches().addAll((Collection<? extends UserBranch>)newValue);
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
			case BranchPackage.BRANCH__CONTRIBUTORS:
				getContributors().clear();
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
			case BranchPackage.BRANCH__CONTRIBUTORS:
				return contributors != null && !contributors.isEmpty();
			case BranchPackage.BRANCH__CHILD_BRANCHES:
				return childBranches != null && !childBranches.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //BranchImpl
