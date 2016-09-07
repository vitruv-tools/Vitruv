/**
 */
package allElementTypes;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see allElementTypes.AllElementTypesPackage
 * @generated
 */
public interface AllElementTypesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AllElementTypesFactory eINSTANCE = allElementTypes.impl.AllElementTypesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Root</em>'.
	 * @generated
	 */
	Root createRoot();

	/**
	 * Returns a new object of class '<em>Non Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Non Root</em>'.
	 * @generated
	 */
	NonRoot createNonRoot();

	/**
	 * Returns a new object of class '<em>Non Root Object Container Helper</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Non Root Object Container Helper</em>'.
	 * @generated
	 */
	NonRootObjectContainerHelper createNonRootObjectContainerHelper();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	AllElementTypesPackage getAllElementTypesPackage();

} //AllElementTypesFactory
