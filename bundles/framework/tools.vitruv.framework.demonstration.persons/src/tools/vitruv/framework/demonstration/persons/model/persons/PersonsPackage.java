/**
 */
package tools.vitruv.framework.demonstration.persons.model.persons;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.demonstration.persons.model.persons.PersonsFactory
 * @model kind="package"
 * @generated
 */
public interface PersonsPackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "persons";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.example.org/persons";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "persons";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  PersonsPackage eINSTANCE = tools.vitruv.framework.demonstration.persons.model.persons.impl.PersonsPackageImpl.init();

  /**
   * The meta object id for the '{@link tools.vitruv.framework.demonstration.persons.model.persons.impl.PersonImpl <em>Person</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruv.framework.demonstration.persons.model.persons.impl.PersonImpl
   * @see tools.vitruv.framework.demonstration.persons.model.persons.impl.PersonsPackageImpl#getPerson()
   * @generated
   */
  int PERSON = 0;

  /**
   * The feature id for the '<em><b>Full Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__FULL_NAME = 0;

  /**
   * The number of structural features of the '<em>Person</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Person</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON_OPERATION_COUNT = 0;


  /**
   * The meta object id for the '{@link tools.vitruv.framework.demonstration.persons.model.persons.impl.MaleImpl <em>Male</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruv.framework.demonstration.persons.model.persons.impl.MaleImpl
   * @see tools.vitruv.framework.demonstration.persons.model.persons.impl.PersonsPackageImpl#getMale()
   * @generated
   */
  int MALE = 1;

  /**
   * The feature id for the '<em><b>Full Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MALE__FULL_NAME = PERSON__FULL_NAME;

  /**
   * The number of structural features of the '<em>Male</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MALE_FEATURE_COUNT = PERSON_FEATURE_COUNT + 0;

  /**
   * The number of operations of the '<em>Male</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MALE_OPERATION_COUNT = PERSON_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link tools.vitruv.framework.demonstration.persons.model.persons.impl.FemaleImpl <em>Female</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruv.framework.demonstration.persons.model.persons.impl.FemaleImpl
   * @see tools.vitruv.framework.demonstration.persons.model.persons.impl.PersonsPackageImpl#getFemale()
   * @generated
   */
  int FEMALE = 2;

  /**
   * The feature id for the '<em><b>Full Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEMALE__FULL_NAME = PERSON__FULL_NAME;

  /**
   * The number of structural features of the '<em>Female</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEMALE_FEATURE_COUNT = PERSON_FEATURE_COUNT + 0;

  /**
   * The number of operations of the '<em>Female</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEMALE_OPERATION_COUNT = PERSON_OPERATION_COUNT + 0;


  /**
   * Returns the meta object for class '{@link tools.vitruv.framework.demonstration.persons.model.persons.Person <em>Person</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Person</em>'.
   * @see tools.vitruv.framework.demonstration.persons.model.persons.Person
   * @generated
   */
  EClass getPerson();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruv.framework.demonstration.persons.model.persons.Person#getFullName <em>Full Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Full Name</em>'.
   * @see tools.vitruv.framework.demonstration.persons.model.persons.Person#getFullName()
   * @see #getPerson()
   * @generated
   */
  EAttribute getPerson_FullName();

  /**
   * Returns the meta object for class '{@link tools.vitruv.framework.demonstration.persons.model.persons.Male <em>Male</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Male</em>'.
   * @see tools.vitruv.framework.demonstration.persons.model.persons.Male
   * @generated
   */
  EClass getMale();

  /**
   * Returns the meta object for class '{@link tools.vitruv.framework.demonstration.persons.model.persons.Female <em>Female</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Female</em>'.
   * @see tools.vitruv.framework.demonstration.persons.model.persons.Female
   * @generated
   */
  EClass getFemale();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  PersonsFactory getPersonsFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each operation of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals {
    /**
     * The meta object literal for the '{@link tools.vitruv.framework.demonstration.persons.model.persons.impl.PersonImpl <em>Person</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruv.framework.demonstration.persons.model.persons.impl.PersonImpl
     * @see tools.vitruv.framework.demonstration.persons.model.persons.impl.PersonsPackageImpl#getPerson()
     * @generated
     */
    EClass PERSON = eINSTANCE.getPerson();
    /**
     * The meta object literal for the '<em><b>Full Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PERSON__FULL_NAME = eINSTANCE.getPerson_FullName();
    /**
     * The meta object literal for the '{@link tools.vitruv.framework.demonstration.persons.model.persons.impl.MaleImpl <em>Male</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruv.framework.demonstration.persons.model.persons.impl.MaleImpl
     * @see tools.vitruv.framework.demonstration.persons.model.persons.impl.PersonsPackageImpl#getMale()
     * @generated
     */
    EClass MALE = eINSTANCE.getMale();
    /**
     * The meta object literal for the '{@link tools.vitruv.framework.demonstration.persons.model.persons.impl.FemaleImpl <em>Female</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruv.framework.demonstration.persons.model.persons.impl.FemaleImpl
     * @see tools.vitruv.framework.demonstration.persons.model.persons.impl.PersonsPackageImpl#getFemale()
     * @generated
     */
    EClass FEMALE = eINSTANCE.getFemale();

  }

} //PersonsPackage
