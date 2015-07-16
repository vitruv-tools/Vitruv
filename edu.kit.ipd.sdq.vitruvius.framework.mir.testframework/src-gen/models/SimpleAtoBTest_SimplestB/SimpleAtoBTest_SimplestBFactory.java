/**
 */
package SimpleAtoBTest_SimplestB;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see SimpleAtoBTest_SimplestB.SimpleAtoBTest_SimplestBPackage
 * @generated
 */
public interface SimpleAtoBTest_SimplestBFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SimpleAtoBTest_SimplestBFactory eINSTANCE = SimpleAtoBTest_SimplestB.impl.SimpleAtoBTest_SimplestBFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>B</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>B</em>'.
	 * @generated
	 */
	B createB();

	/**
	 * Returns a new object of class '<em>BChild</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>BChild</em>'.
	 * @generated
	 */
	BChild createBChild();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SimpleAtoBTest_SimplestBPackage getSimpleAtoBTest_SimplestBPackage();

} //SimpleAtoBTest_SimplestBFactory
