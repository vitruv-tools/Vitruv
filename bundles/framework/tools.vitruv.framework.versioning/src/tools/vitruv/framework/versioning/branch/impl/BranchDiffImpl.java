/**
 */
package tools.vitruv.framework.versioning.branch.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import tools.vitruv.framework.versioning.branch.BranchDiff;
import tools.vitruv.framework.versioning.branch.BranchPackage;

import tools.vitruv.framework.versioning.branch.UserBranch;
import tools.vitruv.framework.versioning.commit.Commit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Diff</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.branch.impl.BranchDiffImpl#getSourceCommits <em>Source Commits</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.branch.impl.BranchDiffImpl#getTargetCommits <em>Target Commits</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.branch.impl.BranchDiffImpl#getSource <em>Source</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.branch.impl.BranchDiffImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.branch.impl.BranchDiffImpl#getLastCommonAncestor <em>Last Common Ancestor</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BranchDiffImpl extends MinimalEObjectImpl.Container implements BranchDiff {
	/**
	 * The cached value of the '{@link #getSourceCommits() <em>Source Commits</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceCommits()
	 * @generated
	 * @ordered
	 */
	protected EList<Commit> sourceCommits;

	/**
	 * The cached value of the '{@link #getTargetCommits() <em>Target Commits</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetCommits()
	 * @generated
	 * @ordered
	 */
	protected EList<Commit> targetCommits;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected UserBranch source;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected UserBranch target;

	/**
	 * The cached value of the '{@link #getLastCommonAncestor() <em>Last Common Ancestor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastCommonAncestor()
	 * @generated
	 * @ordered
	 */
	protected Commit lastCommonAncestor;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BranchDiffImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BranchPackage.Literals.BRANCH_DIFF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Commit> getSourceCommits() {
		if (sourceCommits == null) {
			sourceCommits = new EObjectResolvingEList<Commit>(Commit.class, this, BranchPackage.BRANCH_DIFF__SOURCE_COMMITS);
		}
		return sourceCommits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Commit> getTargetCommits() {
		if (targetCommits == null) {
			targetCommits = new EObjectResolvingEList<Commit>(Commit.class, this, BranchPackage.BRANCH_DIFF__TARGET_COMMITS);
		}
		return targetCommits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserBranch getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject)source;
			source = (UserBranch)eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BranchPackage.BRANCH_DIFF__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserBranch basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(UserBranch newSource) {
		UserBranch oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BranchPackage.BRANCH_DIFF__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserBranch getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (UserBranch)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BranchPackage.BRANCH_DIFF__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserBranch basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(UserBranch newTarget) {
		UserBranch oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BranchPackage.BRANCH_DIFF__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Commit getLastCommonAncestor() {
		if (lastCommonAncestor != null && lastCommonAncestor.eIsProxy()) {
			InternalEObject oldLastCommonAncestor = (InternalEObject)lastCommonAncestor;
			lastCommonAncestor = (Commit)eResolveProxy(oldLastCommonAncestor);
			if (lastCommonAncestor != oldLastCommonAncestor) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BranchPackage.BRANCH_DIFF__LAST_COMMON_ANCESTOR, oldLastCommonAncestor, lastCommonAncestor));
			}
		}
		return lastCommonAncestor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Commit basicGetLastCommonAncestor() {
		return lastCommonAncestor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastCommonAncestor(Commit newLastCommonAncestor) {
		Commit oldLastCommonAncestor = lastCommonAncestor;
		lastCommonAncestor = newLastCommonAncestor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BranchPackage.BRANCH_DIFF__LAST_COMMON_ANCESTOR, oldLastCommonAncestor, lastCommonAncestor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BranchPackage.BRANCH_DIFF__SOURCE_COMMITS:
				return getSourceCommits();
			case BranchPackage.BRANCH_DIFF__TARGET_COMMITS:
				return getTargetCommits();
			case BranchPackage.BRANCH_DIFF__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case BranchPackage.BRANCH_DIFF__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case BranchPackage.BRANCH_DIFF__LAST_COMMON_ANCESTOR:
				if (resolve) return getLastCommonAncestor();
				return basicGetLastCommonAncestor();
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
			case BranchPackage.BRANCH_DIFF__SOURCE_COMMITS:
				getSourceCommits().clear();
				getSourceCommits().addAll((Collection<? extends Commit>)newValue);
				return;
			case BranchPackage.BRANCH_DIFF__TARGET_COMMITS:
				getTargetCommits().clear();
				getTargetCommits().addAll((Collection<? extends Commit>)newValue);
				return;
			case BranchPackage.BRANCH_DIFF__SOURCE:
				setSource((UserBranch)newValue);
				return;
			case BranchPackage.BRANCH_DIFF__TARGET:
				setTarget((UserBranch)newValue);
				return;
			case BranchPackage.BRANCH_DIFF__LAST_COMMON_ANCESTOR:
				setLastCommonAncestor((Commit)newValue);
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
			case BranchPackage.BRANCH_DIFF__SOURCE_COMMITS:
				getSourceCommits().clear();
				return;
			case BranchPackage.BRANCH_DIFF__TARGET_COMMITS:
				getTargetCommits().clear();
				return;
			case BranchPackage.BRANCH_DIFF__SOURCE:
				setSource((UserBranch)null);
				return;
			case BranchPackage.BRANCH_DIFF__TARGET:
				setTarget((UserBranch)null);
				return;
			case BranchPackage.BRANCH_DIFF__LAST_COMMON_ANCESTOR:
				setLastCommonAncestor((Commit)null);
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
			case BranchPackage.BRANCH_DIFF__SOURCE_COMMITS:
				return sourceCommits != null && !sourceCommits.isEmpty();
			case BranchPackage.BRANCH_DIFF__TARGET_COMMITS:
				return targetCommits != null && !targetCommits.isEmpty();
			case BranchPackage.BRANCH_DIFF__SOURCE:
				return source != null;
			case BranchPackage.BRANCH_DIFF__TARGET:
				return target != null;
			case BranchPackage.BRANCH_DIFF__LAST_COMMON_ANCESTOR:
				return lastCommonAncestor != null;
		}
		return super.eIsSet(featureID);
	}

} //BranchDiffImpl
