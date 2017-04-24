/**
 */
package model.branch;

import model.commit.Commit;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Diff</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link model.branch.BranchDiff#getSourceCommits <em>Source Commits</em>}</li>
 *   <li>{@link model.branch.BranchDiff#getTargetCommits <em>Target Commits</em>}</li>
 *   <li>{@link model.branch.BranchDiff#getSource <em>Source</em>}</li>
 *   <li>{@link model.branch.BranchDiff#getTarget <em>Target</em>}</li>
 *   <li>{@link model.branch.BranchDiff#getLastCommonAncestor <em>Last Common Ancestor</em>}</li>
 * </ul>
 *
 * @see model.branch.BranchPackage#getBranchDiff()
 * @model
 * @generated
 */
public interface BranchDiff extends EObject {
	/**
	 * Returns the value of the '<em><b>Source Commits</b></em>' reference list.
	 * The list contents are of type {@link model.commit.Commit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Commits</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Commits</em>' reference list.
	 * @see model.branch.BranchPackage#getBranchDiff_SourceCommits()
	 * @model
	 * @generated
	 */
	EList<Commit> getSourceCommits();

	/**
	 * Returns the value of the '<em><b>Target Commits</b></em>' reference list.
	 * The list contents are of type {@link model.commit.Commit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Commits</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Commits</em>' reference list.
	 * @see model.branch.BranchPackage#getBranchDiff_TargetCommits()
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
	 * @see #setSource(Branch)
	 * @see model.branch.BranchPackage#getBranchDiff_Source()
	 * @model required="true"
	 * @generated
	 */
	Branch getSource();

	/**
	 * Sets the value of the '{@link model.branch.BranchDiff#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(Branch value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(Branch)
	 * @see model.branch.BranchPackage#getBranchDiff_Target()
	 * @model required="true"
	 * @generated
	 */
	Branch getTarget();

	/**
	 * Sets the value of the '{@link model.branch.BranchDiff#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Branch value);

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
	 * @see model.branch.BranchPackage#getBranchDiff_LastCommonAncestor()
	 * @model required="true"
	 * @generated
	 */
	Commit getLastCommonAncestor();

	/**
	 * Sets the value of the '{@link model.branch.BranchDiff#getLastCommonAncestor <em>Last Common Ancestor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Common Ancestor</em>' reference.
	 * @see #getLastCommonAncestor()
	 * @generated
	 */
	void setLastCommonAncestor(Commit value);

} // BranchDiff
