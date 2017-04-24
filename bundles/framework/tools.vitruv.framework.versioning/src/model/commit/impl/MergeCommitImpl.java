/**
 */
package model.commit.impl;

import java.util.Collection;

import model.commit.Commit;
import model.commit.CommitPackage;
import model.commit.MergeCommit;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Merge Commit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link model.commit.impl.MergeCommitImpl#getCommitsMergedToThis <em>Commits Merged To This</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MergeCommitImpl extends CommitImpl implements MergeCommit {
	/**
	 * The cached value of the '{@link #getCommitsMergedToThis() <em>Commits Merged To This</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommitsMergedToThis()
	 * @generated
	 * @ordered
	 */
	protected EList<Commit> commitsMergedToThis;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MergeCommitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CommitPackage.Literals.MERGE_COMMIT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Commit> getCommitsMergedToThis() {
		if (commitsMergedToThis == null) {
			commitsMergedToThis = new EObjectWithInverseResolvingEList.ManyInverse<Commit>(Commit.class, this, CommitPackage.MERGE_COMMIT__COMMITS_MERGED_TO_THIS, CommitPackage.COMMIT__COMMITS_MERGED_FROM_THIS);
		}
		return commitsMergedToThis;
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
			case CommitPackage.MERGE_COMMIT__COMMITS_MERGED_TO_THIS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getCommitsMergedToThis()).basicAdd(otherEnd, msgs);
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
			case CommitPackage.MERGE_COMMIT__COMMITS_MERGED_TO_THIS:
				return ((InternalEList<?>)getCommitsMergedToThis()).basicRemove(otherEnd, msgs);
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
			case CommitPackage.MERGE_COMMIT__COMMITS_MERGED_TO_THIS:
				return getCommitsMergedToThis();
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
			case CommitPackage.MERGE_COMMIT__COMMITS_MERGED_TO_THIS:
				getCommitsMergedToThis().clear();
				getCommitsMergedToThis().addAll((Collection<? extends Commit>)newValue);
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
			case CommitPackage.MERGE_COMMIT__COMMITS_MERGED_TO_THIS:
				getCommitsMergedToThis().clear();
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
			case CommitPackage.MERGE_COMMIT__COMMITS_MERGED_TO_THIS:
				return commitsMergedToThis != null && !commitsMergedToThis.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MergeCommitImpl
