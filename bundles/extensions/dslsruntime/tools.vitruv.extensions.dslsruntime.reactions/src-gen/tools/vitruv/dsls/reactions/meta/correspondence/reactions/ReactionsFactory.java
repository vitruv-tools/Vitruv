/**
 */
package tools.vitruv.dsls.reactions.meta.correspondence.reactions;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.dsls.reactions.meta.correspondence.reactions.ReactionsPackage
 * @generated
 */
public interface ReactionsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ReactionsFactory eINSTANCE = tools.vitruv.dsls.reactions.meta.correspondence.reactions.impl.ReactionsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Correspondence</em>'.
	 * @generated
	 */
	ReactionsCorrespondence createReactionsCorrespondence();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ReactionsPackage getReactionsPackage();

} //ReactionsFactory
