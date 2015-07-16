/**
 */
package SimpleAtoBTest_SimplestA;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see SimpleAtoBTest_SimplestA.SimpleAtoBTest_SimplestAPackage
 * @generated
 */
public interface SimpleAtoBTest_SimplestAFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SimpleAtoBTest_SimplestAFactory eINSTANCE = SimpleAtoBTest_SimplestA.impl.SimpleAtoBTest_SimplestAFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>A</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>A</em>'.
	 * @generated
	 */
	A createA();

	/**
	 * Returns a new object of class '<em>AChild</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>AChild</em>'.
	 * @generated
	 */
	AChild createAChild();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SimpleAtoBTest_SimplestAPackage getSimpleAtoBTest_SimplestAPackage();

} //SimpleAtoBTest_SimplestAFactory
