/**
 */
package multicontainment_a;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see multicontainment_a.Multicontainment_aPackage
 * @generated
 */
public interface Multicontainment_aFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Multicontainment_aFactory eINSTANCE = multicontainment_a.impl.Multicontainment_aFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Root A</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Root A</em>'.
	 * @generated
	 */
	RootA createRootA();

	/**
	 * Returns a new object of class '<em>Child A1</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Child A1</em>'.
	 * @generated
	 */
	ChildA1 createChildA1();

	/**
	 * Returns a new object of class '<em>Child A2</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Child A2</em>'.
	 * @generated
	 */
	ChildA2 createChildA2();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	Multicontainment_aPackage getMulticontainment_aPackage();

} //Multicontainment_aFactory
