/**
 */
package tools.vitruv.framework.versioning.branch;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.versioning.commit.Commit;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Diff</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.branch.BranchDiff#getSourceCommits <em>Source Commits</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.branch.BranchDiff#getTargetCommits <em>Target Commits</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.branch.BranchDiff#getSource <em>Source</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.branch.BranchDiff#getTarget <em>Target</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.branch.BranchDiff#getLastCommonAncestor <em>Last Common Ancestor</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getBranchDiff()
 * @model
 * @generated
 */
public interface BranchDiff extends EObject {
	/**
	 * Returns the value of the '<em><b>Source Commits</b></em>' reference list.
	 * The list contents are of type {@link tools.vitruv.framework.versioning.commit.Commit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Commits</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Commits</em>' reference list.
	 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getBranchDiff_SourceCommits()
	 * @model
	 * @generated
	 */
	EList<Commit> getSourceCommits();

	/**
	 * Returns the value of the '<em><b>Target Commits</b></em>' reference list.
	 * The list contents are of type {@link tools.vitruv.framework.versioning.commit.Commit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Commits</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Commits</em>' reference list.
	 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getBranchDiff_TargetCommits()
	 * @model
	 * @generated
	 */
	EList<Commit> getTargetCommits();

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(UserBranch)
	 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getBranchDiff_Source()
	 * @model required="true"
	 * @generated
	 */
	UserBranch getSource();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.branch.BranchDiff#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(UserBranch value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(UserBranch)
	 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getBranchDiff_Target()
	 * @model required="true"
	 * @generated
	 */
	UserBranch getTarget();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.branch.BranchDiff#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(UserBranch value);

	/**
	 * Returns the value of the '<em><b>Last Common Ancestor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Common Ancestor</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Common Ancestor</em>' reference.
	 * @see #setLastCommonAncestor(Commit)
	 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getBranchDiff_LastCommonAncestor()
	 * @model required="true"
	 * @generated
	 */
	Commit getLastCommonAncestor();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.branch.BranchDiff#getLastCommonAncestor <em>Last Common Ancestor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Common Ancestor</em>' reference.
	 * @see #getLastCommonAncestor()
	 * @generated
	 */
	void setLastCommonAncestor(Commit value);

} // BranchDiff
