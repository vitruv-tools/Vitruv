/**
 */
package multicontainment_b;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see multicontainment_b.Multicontainment_bPackage
 * @generated
 */
public interface Multicontainment_bFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Multicontainment_bFactory eINSTANCE = multicontainment_b.impl.Multicontainment_bFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Root B</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Root B</em>'.
	 * @generated
	 */
	RootB createRootB();

	/**
	 * Returns a new object of class '<em>Child B1</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Child B1</em>'.
	 * @generated
	 */
	ChildB1 createChildB1();

	/**
	 * Returns a new object of class '<em>Child B2</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Child B2</em>'.
	 * @generated
	 */
	ChildB2 createChildB2();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	Multicontainment_bPackage getMulticontainment_bPackage();

} //Multicontainment_bFactory
