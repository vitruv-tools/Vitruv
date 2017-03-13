/**
 */
package tools.vitruv.framework.demonstration.families.model.families;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see tools.vitruv.framework.demonstration.families.model.families.FamiliesFactory
 * @model kind="package"
 * @generated
 */
public interface FamiliesPackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "families";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.example.org/families";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "families";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  FamiliesPackage eINSTANCE = tools.vitruv.framework.demonstration.families.model.families.impl.FamiliesPackageImpl.init();

  /**
   * The meta object id for the '{@link tools.vitruv.framework.demonstration.families.model.families.impl.FamilyImpl <em>Family</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruv.framework.demonstration.families.model.families.impl.FamilyImpl
   * @see tools.vitruv.framework.demonstration.families.model.families.impl.FamiliesPackageImpl#getFamily()
   * @generated
   */
  int FAMILY = 0;

  /**
   * The feature id for the '<em><b>Last Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY__LAST_NAME = 0;

  /**
   * The feature id for the '<em><b>Father</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY__FATHER = 1;

  /**
   * The feature id for the '<em><b>Mother</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY__MOTHER = 2;

  /**
   * The feature id for the '<em><b>Sons</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY__SONS = 3;

  /**
   * The feature id for the '<em><b>Daughters</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY__DAUGHTERS = 4;

  /**
   * The number of structural features of the '<em>Family</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY_FEATURE_COUNT = 5;

  /**
   * The number of operations of the '<em>Family</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link tools.vitruv.framework.demonstration.families.model.families.impl.MemberImpl <em>Member</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see tools.vitruv.framework.demonstration.families.model.families.impl.MemberImpl
   * @see tools.vitruv.framework.demonstration.families.model.families.impl.FamiliesPackageImpl#getMember()
   * @generated
   */
  int MEMBER = 1;

  /**
   * The feature id for the '<em><b>First Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER__FIRST_NAME = 0;

  /**
   * The number of structural features of the '<em>Member</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Member</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_OPERATION_COUNT = 0;


  /**
   * Returns the meta object for class '{@link tools.vitruv.framework.demonstration.families.model.families.Family <em>Family</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Family</em>'.
   * @see tools.vitruv.framework.demonstration.families.model.families.Family
   * @generated
   */
  EClass getFamily();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruv.framework.demonstration.families.model.families.Family#getLastName <em>Last Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Last Name</em>'.
   * @see tools.vitruv.framework.demonstration.families.model.families.Family#getLastName()
   * @see #getFamily()
   * @generated
   */
  EAttribute getFamily_LastName();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruv.framework.demonstration.families.model.families.Family#getFather <em>Father</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Father</em>'.
   * @see tools.vitruv.framework.demonstration.families.model.families.Family#getFather()
   * @see #getFamily()
   * @generated
   */
  EReference getFamily_Father();

  /**
   * Returns the meta object for the containment reference '{@link tools.vitruv.framework.demonstration.families.model.families.Family#getMother <em>Mother</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Mother</em>'.
   * @see tools.vitruv.framework.demonstration.families.model.families.Family#getMother()
   * @see #getFamily()
   * @generated
   */
  EReference getFamily_Mother();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruv.framework.demonstration.families.model.families.Family#getSons <em>Sons</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Sons</em>'.
   * @see tools.vitruv.framework.demonstration.families.model.families.Family#getSons()
   * @see #getFamily()
   * @generated
   */
  EReference getFamily_Sons();

  /**
   * Returns the meta object for the containment reference list '{@link tools.vitruv.framework.demonstration.families.model.families.Family#getDaughters <em>Daughters</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Daughters</em>'.
   * @see tools.vitruv.framework.demonstration.families.model.families.Family#getDaughters()
   * @see #getFamily()
   * @generated
   */
  EReference getFamily_Daughters();

  /**
   * Returns the meta object for class '{@link tools.vitruv.framework.demonstration.families.model.families.Member <em>Member</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Member</em>'.
   * @see tools.vitruv.framework.demonstration.families.model.families.Member
   * @generated
   */
  EClass getMember();

  /**
   * Returns the meta object for the attribute '{@link tools.vitruv.framework.demonstration.families.model.families.Member#getFirstName <em>First Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>First Name</em>'.
   * @see tools.vitruv.framework.demonstration.families.model.families.Member#getFirstName()
   * @see #getMember()
   * @generated
   */
  EAttribute getMember_FirstName();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  FamiliesFactory getFamiliesFactory();

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
     * The meta object literal for the '{@link tools.vitruv.framework.demonstration.families.model.families.impl.FamilyImpl <em>Family</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruv.framework.demonstration.families.model.families.impl.FamilyImpl
     * @see tools.vitruv.framework.demonstration.families.model.families.impl.FamiliesPackageImpl#getFamily()
     * @generated
     */
    EClass FAMILY = eINSTANCE.getFamily();

    /**
     * The meta object literal for the '<em><b>Last Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FAMILY__LAST_NAME = eINSTANCE.getFamily_LastName();

    /**
     * The meta object literal for the '<em><b>Father</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FAMILY__FATHER = eINSTANCE.getFamily_Father();

    /**
     * The meta object literal for the '<em><b>Mother</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FAMILY__MOTHER = eINSTANCE.getFamily_Mother();

    /**
     * The meta object literal for the '<em><b>Sons</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FAMILY__SONS = eINSTANCE.getFamily_Sons();

    /**
     * The meta object literal for the '<em><b>Daughters</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FAMILY__DAUGHTERS = eINSTANCE.getFamily_Daughters();

    /**
     * The meta object literal for the '{@link tools.vitruv.framework.demonstration.families.model.families.impl.MemberImpl <em>Member</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see tools.vitruv.framework.demonstration.families.model.families.impl.MemberImpl
     * @see tools.vitruv.framework.demonstration.families.model.families.impl.FamiliesPackageImpl#getMember()
     * @generated
     */
    EClass MEMBER = eINSTANCE.getMember();

    /**
     * The meta object literal for the '<em><b>First Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MEMBER__FIRST_NAME = eINSTANCE.getMember_FirstName();

  }

} //FamiliesPackage
