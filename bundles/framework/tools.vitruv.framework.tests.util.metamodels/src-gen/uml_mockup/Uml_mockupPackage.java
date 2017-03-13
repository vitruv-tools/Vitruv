/**
 */
package uml_mockup;

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
 * @see uml_mockup.Uml_mockupFactory
 * @model kind="package"
 * @generated
 */
public interface Uml_mockupPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "uml_mockup";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv.framework.tests.util.metamodels";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "uml_mockup";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Uml_mockupPackage eINSTANCE = uml_mockup.impl.Uml_mockupPackageImpl.init();

	/**
	 * The meta object id for the '{@link uml_mockup.impl.IdentifiedImpl <em>Identified</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uml_mockup.impl.IdentifiedImpl
	 * @see uml_mockup.impl.Uml_mockupPackageImpl#getIdentified()
	 * @generated
	 */
	int IDENTIFIED = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIED__ID = 0;

	/**
	 * The number of structural features of the '<em>Identified</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIED_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Identified</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIED_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link uml_mockup.impl.UPackageImpl <em>UPackage</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uml_mockup.impl.UPackageImpl
	 * @see uml_mockup.impl.Uml_mockupPackageImpl#getUPackage()
	 * @generated
	 */
	int UPACKAGE = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPACKAGE__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPACKAGE__NAME = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Interfaces</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPACKAGE__INTERFACES = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Classes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPACKAGE__CLASSES = IDENTIFIED_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>UPackage</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPACKAGE_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>UPackage</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPACKAGE_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link uml_mockup.impl.UInterfaceImpl <em>UInterface</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uml_mockup.impl.UInterfaceImpl
	 * @see uml_mockup.impl.Uml_mockupPackageImpl#getUInterface()
	 * @generated
	 */
	int UINTERFACE = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UINTERFACE__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UINTERFACE__NAME = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Methods</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UINTERFACE__METHODS = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>UInterface</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UINTERFACE_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>UInterface</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UINTERFACE_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link uml_mockup.impl.UClassImpl <em>UClass</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uml_mockup.impl.UClassImpl
	 * @see uml_mockup.impl.Uml_mockupPackageImpl#getUClass()
	 * @generated
	 */
	int UCLASS = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UCLASS__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UCLASS__NAME = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Class Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UCLASS__CLASS_COUNT = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UCLASS__ATTRIBUTES = IDENTIFIED_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>UClass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UCLASS_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>UClass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UCLASS_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;


	/**
	 * The meta object id for the '{@link uml_mockup.impl.UNamedElementImpl <em>UNamed Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uml_mockup.impl.UNamedElementImpl
	 * @see uml_mockup.impl.Uml_mockupPackageImpl#getUNamedElement()
	 * @generated
	 */
	int UNAMED_ELEMENT = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNAMED_ELEMENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>UNamed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNAMED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>UNamed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNAMED_ELEMENT_OPERATION_COUNT = 0;


	/**
	 * The meta object id for the '{@link uml_mockup.impl.UMethodImpl <em>UMethod</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uml_mockup.impl.UMethodImpl
	 * @see uml_mockup.impl.Uml_mockupPackageImpl#getUMethod()
	 * @generated
	 */
	int UMETHOD = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMETHOD__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMETHOD__NAME = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>UMethod</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMETHOD_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>UMethod</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMETHOD_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;


	/**
	 * The meta object id for the '{@link uml_mockup.impl.UAttributeImpl <em>UAttribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uml_mockup.impl.UAttributeImpl
	 * @see uml_mockup.impl.Uml_mockupPackageImpl#getUAttribute()
	 * @generated
	 */
	int UATTRIBUTE = 6;

	/**
	 * The feature id for the '<em><b>Attribute Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UATTRIBUTE__ATTRIBUTE_NAME = 0;

	/**
	 * The number of structural features of the '<em>UAttribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UATTRIBUTE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>UAttribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UATTRIBUTE_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link uml_mockup.Identified <em>Identified</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identified</em>'.
	 * @see uml_mockup.Identified
	 * @generated
	 */
	EClass getIdentified();

	/**
	 * Returns the meta object for the attribute '{@link uml_mockup.Identified#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see uml_mockup.Identified#getId()
	 * @see #getIdentified()
	 * @generated
	 */
	EAttribute getIdentified_Id();

	/**
	 * Returns the meta object for class '{@link uml_mockup.UPackage <em>UPackage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>UPackage</em>'.
	 * @see uml_mockup.UPackage
	 * @generated
	 */
	EClass getUPackage();

	/**
	 * Returns the meta object for the containment reference list '{@link uml_mockup.UPackage#getInterfaces <em>Interfaces</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Interfaces</em>'.
	 * @see uml_mockup.UPackage#getInterfaces()
	 * @see #getUPackage()
	 * @generated
	 */
	EReference getUPackage_Interfaces();

	/**
	 * Returns the meta object for the containment reference list '{@link uml_mockup.UPackage#getClasses <em>Classes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Classes</em>'.
	 * @see uml_mockup.UPackage#getClasses()
	 * @see #getUPackage()
	 * @generated
	 */
	EReference getUPackage_Classes();

	/**
	 * Returns the meta object for class '{@link uml_mockup.UInterface <em>UInterface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>UInterface</em>'.
	 * @see uml_mockup.UInterface
	 * @generated
	 */
	EClass getUInterface();

	/**
	 * Returns the meta object for the containment reference list '{@link uml_mockup.UInterface#getMethods <em>Methods</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Methods</em>'.
	 * @see uml_mockup.UInterface#getMethods()
	 * @see #getUInterface()
	 * @generated
	 */
	EReference getUInterface_Methods();

	/**
	 * Returns the meta object for class '{@link uml_mockup.UClass <em>UClass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>UClass</em>'.
	 * @see uml_mockup.UClass
	 * @generated
	 */
	EClass getUClass();

	/**
	 * Returns the meta object for the attribute '{@link uml_mockup.UClass#getClassCount <em>Class Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Count</em>'.
	 * @see uml_mockup.UClass#getClassCount()
	 * @see #getUClass()
	 * @generated
	 */
	EAttribute getUClass_ClassCount();

	/**
	 * Returns the meta object for the reference list '{@link uml_mockup.UClass#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Attributes</em>'.
	 * @see uml_mockup.UClass#getAttributes()
	 * @see #getUClass()
	 * @generated
	 */
	EReference getUClass_Attributes();

	/**
	 * Returns the meta object for class '{@link uml_mockup.UNamedElement <em>UNamed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>UNamed Element</em>'.
	 * @see uml_mockup.UNamedElement
	 * @generated
	 */
	EClass getUNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link uml_mockup.UNamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see uml_mockup.UNamedElement#getName()
	 * @see #getUNamedElement()
	 * @generated
	 */
	EAttribute getUNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link uml_mockup.UMethod <em>UMethod</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>UMethod</em>'.
	 * @see uml_mockup.UMethod
	 * @generated
	 */
	EClass getUMethod();

	/**
	 * Returns the meta object for class '{@link uml_mockup.UAttribute <em>UAttribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>UAttribute</em>'.
	 * @see uml_mockup.UAttribute
	 * @generated
	 */
	EClass getUAttribute();

	/**
	 * Returns the meta object for the attribute '{@link uml_mockup.UAttribute#getAttributeName <em>Attribute Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute Name</em>'.
	 * @see uml_mockup.UAttribute#getAttributeName()
	 * @see #getUAttribute()
	 * @generated
	 */
	EAttribute getUAttribute_AttributeName();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	Uml_mockupFactory getUml_mockupFactory();

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
		 * The meta object literal for the '{@link uml_mockup.impl.IdentifiedImpl <em>Identified</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uml_mockup.impl.IdentifiedImpl
		 * @see uml_mockup.impl.Uml_mockupPackageImpl#getIdentified()
		 * @generated
		 */
		EClass IDENTIFIED = eINSTANCE.getIdentified();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFIED__ID = eINSTANCE.getIdentified_Id();

		/**
		 * The meta object literal for the '{@link uml_mockup.impl.UPackageImpl <em>UPackage</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uml_mockup.impl.UPackageImpl
		 * @see uml_mockup.impl.Uml_mockupPackageImpl#getUPackage()
		 * @generated
		 */
		EClass UPACKAGE = eINSTANCE.getUPackage();

		/**
		 * The meta object literal for the '<em><b>Interfaces</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UPACKAGE__INTERFACES = eINSTANCE.getUPackage_Interfaces();

		/**
		 * The meta object literal for the '<em><b>Classes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UPACKAGE__CLASSES = eINSTANCE.getUPackage_Classes();

		/**
		 * The meta object literal for the '{@link uml_mockup.impl.UInterfaceImpl <em>UInterface</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uml_mockup.impl.UInterfaceImpl
		 * @see uml_mockup.impl.Uml_mockupPackageImpl#getUInterface()
		 * @generated
		 */
		EClass UINTERFACE = eINSTANCE.getUInterface();

		/**
		 * The meta object literal for the '<em><b>Methods</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UINTERFACE__METHODS = eINSTANCE.getUInterface_Methods();

		/**
		 * The meta object literal for the '{@link uml_mockup.impl.UClassImpl <em>UClass</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uml_mockup.impl.UClassImpl
		 * @see uml_mockup.impl.Uml_mockupPackageImpl#getUClass()
		 * @generated
		 */
		EClass UCLASS = eINSTANCE.getUClass();

		/**
		 * The meta object literal for the '<em><b>Class Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UCLASS__CLASS_COUNT = eINSTANCE.getUClass_ClassCount();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UCLASS__ATTRIBUTES = eINSTANCE.getUClass_Attributes();

		/**
		 * The meta object literal for the '{@link uml_mockup.impl.UNamedElementImpl <em>UNamed Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uml_mockup.impl.UNamedElementImpl
		 * @see uml_mockup.impl.Uml_mockupPackageImpl#getUNamedElement()
		 * @generated
		 */
		EClass UNAMED_ELEMENT = eINSTANCE.getUNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNAMED_ELEMENT__NAME = eINSTANCE.getUNamedElement_Name();

		/**
		 * The meta object literal for the '{@link uml_mockup.impl.UMethodImpl <em>UMethod</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uml_mockup.impl.UMethodImpl
		 * @see uml_mockup.impl.Uml_mockupPackageImpl#getUMethod()
		 * @generated
		 */
		EClass UMETHOD = eINSTANCE.getUMethod();

		/**
		 * The meta object literal for the '{@link uml_mockup.impl.UAttributeImpl <em>UAttribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uml_mockup.impl.UAttributeImpl
		 * @see uml_mockup.impl.Uml_mockupPackageImpl#getUAttribute()
		 * @generated
		 */
		EClass UATTRIBUTE = eINSTANCE.getUAttribute();

		/**
		 * The meta object literal for the '<em><b>Attribute Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UATTRIBUTE__ATTRIBUTE_NAME = eINSTANCE.getUAttribute_AttributeName();

	}

} //Uml_mockupPackage
