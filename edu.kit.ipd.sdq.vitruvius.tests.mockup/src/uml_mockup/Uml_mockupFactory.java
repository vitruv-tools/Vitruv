/**
 */
package uml_mockup;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see uml_mockup.Uml_mockupPackage
 * @generated
 */
public interface Uml_mockupFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    Uml_mockupFactory eINSTANCE = uml_mockup.impl.Uml_mockupFactoryImpl.init();

    /**
     * Returns a new object of class '<em>UPackage</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>UPackage</em>'.
     * @generated
     */
    UPackage createUPackage();

    /**
     * Returns a new object of class '<em>Interface</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Interface</em>'.
     * @generated
     */
    Interface createInterface();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    Uml_mockupPackage getUml_mockupPackage();

} //Uml_mockupFactory
