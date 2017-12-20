/**
 */
package allElementTypes2;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see allElementTypes2.AllElementTypes2Package
 * @generated
 */
public interface AllElementTypes2Factory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AllElementTypes2Factory eINSTANCE = allElementTypes2.impl.AllElementTypes2FactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Root2</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Root2</em>'.
	 * @generated
	 */
	Root2 createRoot2();

	/**
	 * Returns a new object of class '<em>Non Root2</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Non Root2</em>'.
	 * @generated
	 */
	NonRoot2 createNonRoot2();

	/**
	 * Returns a new object of class '<em>Non Root Object Container Helper2</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Non Root Object Container Helper2</em>'.
	 * @generated
	 */
	NonRootObjectContainerHelper2 createNonRootObjectContainerHelper2();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	AllElementTypes2Package getAllElementTypes2Package();

} //AllElementTypes2Factory
