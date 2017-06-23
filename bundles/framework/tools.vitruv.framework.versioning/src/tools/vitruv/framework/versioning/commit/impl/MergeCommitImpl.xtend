/** 
 */
package tools.vitruv.framework.versioning.commit.impl

import java.util.Collection
import org.eclipse.emf.common.notify.NotificationChain
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList
import org.eclipse.emf.ecore.util.InternalEList
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.CommitPackage
import tools.vitruv.framework.versioning.commit.MergeCommit

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Merge Commit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.commit.impl.MergeCommitImpl#getCommitsMergedToThis <em>Commits Merged To This</em>}</li>
 * </ul>
 * @generated
 */
class MergeCommitImpl extends CommitImpl implements MergeCommit {
	/** 
	 * The cached value of the '{@link #getCommitsMergedToThis() <em>Commits Merged To This</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommitsMergedToThis()
	 * @generated
	 * @ordered
	 */
	protected EList<Commit> commitsMergedToThis

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
		return CommitPackage.Literals.MERGE_COMMIT
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EList<Commit> getCommitsMergedToThis() {
		if (commitsMergedToThis === null) {
			commitsMergedToThis = new EObjectWithInverseResolvingEList.ManyInverse<Commit>(Commit, this,
				CommitPackage.MERGE_COMMIT__COMMITS_MERGED_TO_THIS, CommitPackage.COMMIT__COMMITS_MERGED_FROM_THIS)
		}
		return commitsMergedToThis
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	override NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {

		switch (featureID) {
			case CommitPackage.MERGE_COMMIT__COMMITS_MERGED_TO_THIS: {
				return (((getCommitsMergedToThis() as InternalEList<?>) as InternalEList<InternalEObject>)).basicAdd(
					otherEnd, msgs)
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
			case CommitPackage.MERGE_COMMIT__COMMITS_MERGED_TO_THIS: {
				return ((getCommitsMergedToThis() as InternalEList<?>)).basicRemove(otherEnd, msgs)
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
			case CommitPackage.MERGE_COMMIT__COMMITS_MERGED_TO_THIS: {
				return getCommitsMergedToThis()
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
			case CommitPackage.MERGE_COMMIT__COMMITS_MERGED_TO_THIS: {
				getCommitsMergedToThis().clear()
				getCommitsMergedToThis().addAll((newValue as Collection<? extends Commit>))
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
			case CommitPackage.MERGE_COMMIT__COMMITS_MERGED_TO_THIS: {
				getCommitsMergedToThis().clear()
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
			case CommitPackage.MERGE_COMMIT__COMMITS_MERGED_TO_THIS: {
				return commitsMergedToThis !== null && !commitsMergedToThis.isEmpty()
			}
		}
		return super.eIsSet(featureID)
	} // MergeCommitImpl
}
