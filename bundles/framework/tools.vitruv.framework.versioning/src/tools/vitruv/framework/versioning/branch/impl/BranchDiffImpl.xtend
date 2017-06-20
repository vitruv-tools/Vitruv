/** 
 */
package tools.vitruv.framework.versioning.branch.impl

import java.util.Collection
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.impl.ENotificationImpl
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl
import org.eclipse.emf.ecore.util.EObjectResolvingEList
import tools.vitruv.framework.versioning.branch.BranchDiff
import tools.vitruv.framework.versioning.branch.BranchPackage
import tools.vitruv.framework.versioning.branch.UserBranch
import tools.vitruv.framework.versioning.commit.Commit

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Diff</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.branch.impl.BranchDiffImpl#getSourceCommits <em>Source Commits</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.branch.impl.BranchDiffImpl#getTargetCommits <em>Target Commits</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.branch.impl.BranchDiffImpl#getSource <em>Source</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.branch.impl.BranchDiffImpl#getTarget <em>Target</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.branch.impl.BranchDiffImpl#getLastCommonAncestor <em>Last Common Ancestor</em>}</li>
 * </ul>
 * @generated
 */
class BranchDiffImpl extends MinimalEObjectImpl.Container implements BranchDiff {
	/** 
	 * The cached value of the '{@link #getSourceCommits() <em>Source Commits</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceCommits()
	 * @generated
	 * @ordered
	 */
	protected EList<Commit> sourceCommits
	/** 
	 * The cached value of the '{@link #getTargetCommits() <em>Target Commits</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetCommits()
	 * @generated
	 * @ordered
	 */
	protected EList<Commit> targetCommits
	/** 
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected UserBranch source
	/** 
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected UserBranch target
	/** 
	 * The cached value of the '{@link #getLastCommonAncestor() <em>Last Common Ancestor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastCommonAncestor()
	 * @generated
	 * @ordered
	 */
	protected Commit lastCommonAncestor

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
		return BranchPackage.Literals.BRANCH_DIFF
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EList<Commit> getSourceCommits() {
		if (sourceCommits === null) {
			sourceCommits = new EObjectResolvingEList<Commit>(Commit, this, BranchPackage.BRANCH_DIFF__SOURCE_COMMITS)
		}
		return sourceCommits
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EList<Commit> getTargetCommits() {
		if (targetCommits === null) {
			targetCommits = new EObjectResolvingEList<Commit>(Commit, this, BranchPackage.BRANCH_DIFF__TARGET_COMMITS)
		}
		return targetCommits
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override UserBranch getSource() {
		if (source !== null && source.eIsProxy()) {
			var InternalEObject oldSource = (source as InternalEObject)
			source = eResolveProxy(oldSource) as UserBranch
			if (source !== oldSource) {
				if (eNotificationRequired()) eNotify(
					new ENotificationImpl(this, Notification.RESOLVE, BranchPackage.BRANCH_DIFF__SOURCE, oldSource,
						source))
			}
		}
		return source
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def UserBranch basicGetSource() {
		return source
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setSource(UserBranch newSource) {
		var UserBranch oldSource = source
		source = newSource
		if (eNotificationRequired()) eNotify(
			new ENotificationImpl(this, Notification.SET, BranchPackage.BRANCH_DIFF__SOURCE, oldSource, source))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override UserBranch getTarget() {
		if (target !== null && target.eIsProxy()) {
			var InternalEObject oldTarget = (target as InternalEObject)
			target = eResolveProxy(oldTarget) as UserBranch
			if (target !== oldTarget) {
				if (eNotificationRequired()) eNotify(
					new ENotificationImpl(this, Notification.RESOLVE, BranchPackage.BRANCH_DIFF__TARGET, oldTarget,
						target))
			}
		}
		return target
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def UserBranch basicGetTarget() {
		return target
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setTarget(UserBranch newTarget) {
		var UserBranch oldTarget = target
		target = newTarget
		if (eNotificationRequired()) eNotify(
			new ENotificationImpl(this, Notification.SET, BranchPackage.BRANCH_DIFF__TARGET, oldTarget, target))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Commit getLastCommonAncestor() {
		if (lastCommonAncestor !== null && lastCommonAncestor.eIsProxy()) {
			var InternalEObject oldLastCommonAncestor = (lastCommonAncestor as InternalEObject)
			lastCommonAncestor = eResolveProxy(oldLastCommonAncestor) as Commit
			if (lastCommonAncestor !== oldLastCommonAncestor) {
				if (eNotificationRequired()) eNotify(
					new ENotificationImpl(this, Notification.RESOLVE, BranchPackage.BRANCH_DIFF__LAST_COMMON_ANCESTOR,
						oldLastCommonAncestor, lastCommonAncestor))
			}
		}
		return lastCommonAncestor
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def Commit basicGetLastCommonAncestor() {
		return lastCommonAncestor
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setLastCommonAncestor(Commit newLastCommonAncestor) {
		var Commit oldLastCommonAncestor = lastCommonAncestor
		lastCommonAncestor = newLastCommonAncestor
		if (eNotificationRequired()) eNotify(
			new ENotificationImpl(this, Notification.SET, BranchPackage.BRANCH_DIFF__LAST_COMMON_ANCESTOR,
				oldLastCommonAncestor, lastCommonAncestor))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Object eGet(int featureID, boolean resolve, boolean coreType) {

		switch (featureID) {
			case BranchPackage.BRANCH_DIFF__SOURCE_COMMITS: {
				return getSourceCommits()
			}
			case BranchPackage.BRANCH_DIFF__TARGET_COMMITS: {
				return getTargetCommits()
			}
			case BranchPackage.BRANCH_DIFF__SOURCE: {
				if (resolve) return getSource()
				return basicGetSource()
			}
			case BranchPackage.BRANCH_DIFF__TARGET: {
				if (resolve) return getTarget()
				return basicGetTarget()
			}
			case BranchPackage.BRANCH_DIFF__LAST_COMMON_ANCESTOR: {
				if (resolve) return getLastCommonAncestor()
				return basicGetLastCommonAncestor()
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
			case BranchPackage.BRANCH_DIFF__SOURCE_COMMITS: {
				getSourceCommits().clear()
				getSourceCommits().addAll((newValue as Collection<? extends Commit>))
				return
			}
			case BranchPackage.BRANCH_DIFF__TARGET_COMMITS: {
				getTargetCommits().clear()
				getTargetCommits().addAll((newValue as Collection<? extends Commit>))
				return
			}
			case BranchPackage.BRANCH_DIFF__SOURCE: {
				setSource((newValue as UserBranch))
				return
			}
			case BranchPackage.BRANCH_DIFF__TARGET: {
				setTarget((newValue as UserBranch))
				return
			}
			case BranchPackage.BRANCH_DIFF__LAST_COMMON_ANCESTOR: {
				setLastCommonAncestor((newValue as Commit))
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
			case BranchPackage.BRANCH_DIFF__SOURCE_COMMITS: {
				getSourceCommits().clear()
				return
			}
			case BranchPackage.BRANCH_DIFF__TARGET_COMMITS: {
				getTargetCommits().clear()
				return
			}
			case BranchPackage.BRANCH_DIFF__SOURCE: {
				setSource((null as UserBranch))
				return
			}
			case BranchPackage.BRANCH_DIFF__TARGET: {
				setTarget((null as UserBranch))
				return
			}
			case BranchPackage.BRANCH_DIFF__LAST_COMMON_ANCESTOR: {
				setLastCommonAncestor((null as Commit))
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
			case BranchPackage.BRANCH_DIFF__SOURCE_COMMITS: {
				return sourceCommits !== null && !sourceCommits.isEmpty()
			}
			case BranchPackage.BRANCH_DIFF__TARGET_COMMITS: {
				return targetCommits !== null && !targetCommits.isEmpty()
			}
			case BranchPackage.BRANCH_DIFF__SOURCE: {
				return source !== null
			}
			case BranchPackage.BRANCH_DIFF__TARGET: {
				return target !== null
			}
			case BranchPackage.BRANCH_DIFF__LAST_COMMON_ANCESTOR: {
				return lastCommonAncestor !== null
			}
		}
		return super.eIsSet(featureID)
	} // BranchDiffImpl
}
