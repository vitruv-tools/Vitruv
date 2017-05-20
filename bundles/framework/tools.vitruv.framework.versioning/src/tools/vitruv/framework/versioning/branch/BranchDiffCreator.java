/**
 */
package tools.vitruv.framework.versioning.branch;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Diff Creator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.branch.BranchDiffCreator#getSource <em>Source</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.branch.BranchDiffCreator#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getBranchDiffCreator()
 * @model
 * @generated
 */
public interface BranchDiffCreator extends EObject {
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
	 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getBranchDiffCreator_Source()
	 * @model required="true"
	 * @generated
	 */
	UserBranch getSource();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.branch.BranchDiffCreator#getSource <em>Source</em>}' reference.
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
	 * @see tools.vitruv.framework.versioning.branch.BranchPackage#getBranchDiffCreator_Target()
	 * @model required="true"
	 * @generated
	 */
	UserBranch getTarget();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.branch.BranchDiffCreator#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(UserBranch value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	BranchDiff createVersionDiff();

} // BranchDiffCreator
