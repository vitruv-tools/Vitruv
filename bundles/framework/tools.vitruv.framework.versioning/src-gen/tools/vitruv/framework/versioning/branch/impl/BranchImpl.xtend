/** 
 */
package tools.vitruv.framework.versioning.branch.impl

import java.util.Collection
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.NotificationChain
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.impl.ENotificationImpl
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList
import org.eclipse.emf.ecore.util.InternalEList
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.author.AuthorPackage
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.BranchPackage
import tools.vitruv.framework.versioning.branch.UserBranch
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.impl.NamedImpl

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Branch</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.branch.impl.BranchImpl#getCurrentHeadCommit <em>Current Head Commit</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.branch.impl.BranchImpl#getContributors <em>Contributors</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.branch.impl.BranchImpl#getChildBranches <em>Child Branches</em>}</li>
 * </ul>
 * @generated
 */
class BranchImpl extends NamedImpl implements Branch {
	/** 
	 * The cached value of the '{@link #getCurrentHeadCommit() <em>Current Head Commit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentHeadCommit()
	 * @generated
	 * @ordered
	 */
	protected Commit currentHeadCommit
	/** 
	 * The cached value of the '{@link #getContributors() <em>Contributors</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContributors()
	 * @generated
	 * @ordered
	 */
	protected EList<Author> contributors
	/** 
	 * The cached value of the '{@link #getChildBranches() <em>Child Branches</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildBranches()
	 * @generated
	 * @ordered
	 */
	protected EList<UserBranch> childBranches

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected new() {
		super()
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override protected EClass eStaticClass() {
		return BranchPackage.Literals.BRANCH
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Commit getCurrentHeadCommit() {
		if (currentHeadCommit !== null && currentHeadCommit.eIsProxy()) {
			var InternalEObject oldCurrentHeadCommit = (currentHeadCommit as InternalEObject)
			currentHeadCommit = eResolveProxy(oldCurrentHeadCommit) as Commit
			if (currentHeadCommit !== oldCurrentHeadCommit) {
				if (eNotificationRequired()) eNotify(
					new ENotificationImpl(this, Notification.RESOLVE, BranchPackage.BRANCH__CURRENT_HEAD_COMMIT,
						oldCurrentHeadCommit, currentHeadCommit))
			}
		}
		return currentHeadCommit
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def Commit basicGetCurrentHeadCommit() {
		return currentHeadCommit
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setCurrentHeadCommit(Commit newCurrentHeadCommit) {
		var Commit oldCurrentHeadCommit = currentHeadCommit
		currentHeadCommit = newCurrentHeadCommit
		if (eNotificationRequired()) eNotify(
			new ENotificationImpl(this, Notification.SET, BranchPackage.BRANCH__CURRENT_HEAD_COMMIT,
				oldCurrentHeadCommit, currentHeadCommit))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EList<Author> getContributors() {
		if (contributors === null) {
			contributors = new EObjectWithInverseResolvingEList.ManyInverse<Author>(Author, this,
				BranchPackage.BRANCH__CONTRIBUTORS, AuthorPackage.AUTHOR__CONTRIBUTED_BRANCHES)
		}
		return contributors
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EList<UserBranch> getChildBranches() {
		if (childBranches === null) {
			childBranches = new EObjectWithInverseResolvingEList<UserBranch>(UserBranch, this,
				BranchPackage.BRANCH__CHILD_BRANCHES, BranchPackage.USER_BRANCH__BRANCHED_FROM)
		}
		return childBranches
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	override NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {

		switch (featureID) {
			case BranchPackage.BRANCH__CONTRIBUTORS: {
				return (((getContributors() as InternalEList<?>) as InternalEList<InternalEObject>)).basicAdd(otherEnd,
					msgs)
			}
			case BranchPackage.BRANCH__CHILD_BRANCHES: {
				return (((getChildBranches() as InternalEList<?>) as InternalEList<InternalEObject>)).basicAdd(otherEnd,
					msgs)
			}
		}
		return super.eInverseAdd(otherEnd, featureID, msgs)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {

		switch (featureID) {
			case BranchPackage.BRANCH__CONTRIBUTORS: {
				return ((getContributors() as InternalEList<?>)).basicRemove(otherEnd, msgs)
			}
			case BranchPackage.BRANCH__CHILD_BRANCHES: {
				return ((getChildBranches() as InternalEList<?>)).basicRemove(otherEnd, msgs)
			}
		}
		return super.eInverseRemove(otherEnd, featureID, msgs)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Object eGet(int featureID, boolean resolve, boolean coreType) {

		switch (featureID) {
			case BranchPackage.BRANCH__CURRENT_HEAD_COMMIT: {
				if (resolve) return getCurrentHeadCommit()
				return basicGetCurrentHeadCommit()
			}
			case BranchPackage.BRANCH__CONTRIBUTORS: {
				return getContributors()
			}
			case BranchPackage.BRANCH__CHILD_BRANCHES: {
				return getChildBranches()
			}
		}
		return super.eGet(featureID, resolve, coreType)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	override void eSet(int featureID, Object newValue) {

		switch (featureID) {
			case BranchPackage.BRANCH__CURRENT_HEAD_COMMIT: {
				setCurrentHeadCommit((newValue as Commit))
				return
			}
			case BranchPackage.BRANCH__CONTRIBUTORS: {
				getContributors().clear()
				getContributors().addAll((newValue as Collection<? extends Author>))
				return
			}
			case BranchPackage.BRANCH__CHILD_BRANCHES: {
				getChildBranches().clear()
				getChildBranches().addAll((newValue as Collection<? extends UserBranch>))
				return
			}
		}
		super.eSet(featureID, newValue)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void eUnset(int featureID) {

		switch (featureID) {
			case BranchPackage.BRANCH__CURRENT_HEAD_COMMIT: {
				setCurrentHeadCommit((null as Commit))
				return
			}
			case BranchPackage.BRANCH__CONTRIBUTORS: {
				getContributors().clear()
				return
			}
			case BranchPackage.BRANCH__CHILD_BRANCHES: {
				getChildBranches().clear()
				return
			}
		}
		super.eUnset(featureID)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override boolean eIsSet(int featureID) {

		switch (featureID) {
			case BranchPackage.BRANCH__CURRENT_HEAD_COMMIT: {
				return currentHeadCommit !== null
			}
			case BranchPackage.BRANCH__CONTRIBUTORS: {
				return contributors !== null && !contributors.isEmpty()
			}
			case BranchPackage.BRANCH__CHILD_BRANCHES: {
				return childBranches !== null && !childBranches.isEmpty()
			}
		}
		return super.eIsSet(featureID)
	} // BranchImpl
}
