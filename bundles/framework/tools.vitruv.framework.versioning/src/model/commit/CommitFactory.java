/**
 */
package model.commit;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see model.commit.CommitPackage
 * @generated
 */
public interface CommitFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CommitFactory eINSTANCE = model.commit.impl.CommitFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Merge Commit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Merge Commit</em>'.
	 * @generated
	 */
	MergeCommit createMergeCommit();

	/**
	 * Returns a new object of class '<em>Simple Commit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simple Commit</em>'.
	 * @generated
	 */
	SimpleCommit createSimpleCommit();

	/**
	 * Returns a new object of class '<em>Message</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Message</em>'.
	 * @generated
	 */
	CommitMessage createCommitMessage();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CommitPackage getCommitPackage();

} //CommitFactory
