/**
 */
package tools.vitruv.framework.demonstration.persons.model.persons;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.demonstration.persons.model.persons.PersonsPackage
 * @generated
 */
public interface PersonsFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  PersonsFactory eINSTANCE = tools.vitruv.framework.demonstration.persons.model.persons.impl.PersonsFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Male</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Male</em>'.
   * @generated
   */
  Male createMale();

  /**
   * Returns a new object of class '<em>Female</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Female</em>'.
   * @generated
   */
  Female createFemale();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  PersonsPackage getPersonsPackage();

} //PersonsFactory
