/**
 */
package tools.vitruv.framework.demonstration.persons.model.persons.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import tools.vitruv.framework.demonstration.persons.model.persons.Female;
import tools.vitruv.framework.demonstration.persons.model.persons.Male;
import tools.vitruv.framework.demonstration.persons.model.persons.Person;
import tools.vitruv.framework.demonstration.persons.model.persons.PersonsFactory;
import tools.vitruv.framework.demonstration.persons.model.persons.PersonsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PersonsPackageImpl extends EPackageImpl implements PersonsPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass personEClass = null;
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass maleEClass = null;
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass femaleEClass = null;
  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see tools.vitruv.framework.demonstration.persons.model.persons.PersonsPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private PersonsPackageImpl() {
    super(eNS_URI, PersonsFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * 
   * <p>This method is used to initialize {@link PersonsPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static PersonsPackage init() {
    if (isInited) return (PersonsPackage)EPackage.Registry.INSTANCE.getEPackage(PersonsPackage.eNS_URI);

    // Obtain or create and register package
    PersonsPackageImpl thePersonsPackage = (PersonsPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof PersonsPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new PersonsPackageImpl());

    isInited = true;

    // Create package meta-data objects
    thePersonsPackage.createPackageContents();

    // Initialize created meta-data
    thePersonsPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    thePersonsPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(PersonsPackage.eNS_URI, thePersonsPackage);
    return thePersonsPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getPerson() {
    return personEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getPerson_FullName() {
    return (EAttribute)personEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMale() {
    return maleEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getFemale() {
    return femaleEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PersonsFactory getPersonsFactory() {
    return (PersonsFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents() {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    personEClass = createEClass(PERSON);
    createEAttribute(personEClass, PERSON__FULL_NAME);

    maleEClass = createEClass(MALE);

    femaleEClass = createEClass(FEMALE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents() {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    maleEClass.getESuperTypes().add(this.getPerson());
    femaleEClass.getESuperTypes().add(this.getPerson());

    // Initialize classes, features, and operations; add parameters
    initEClass(personEClass, Person.class, "Person", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getPerson_FullName(), ecorePackage.getEString(), "fullName", null, 0, 1, Person.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(maleEClass, Male.class, "Male", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(femaleEClass, Female.class, "Female", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    // Create resource
    createResource(eNS_URI);
  }

} //PersonsPackageImpl
