/**
 */
package pcm_mockup;

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
 * @see pcm_mockup.Pcm_mockupFactory
 * @model kind="package"
 * @generated
 */
public interface Pcm_mockupPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "pcm_mockup";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv.tests.metamodels.pcm_mockup";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "pcm_mockup";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Pcm_mockupPackage eINSTANCE = pcm_mockup.impl.Pcm_mockupPackageImpl.init();

	/**
	 * The meta object id for the '{@link pcm_mockup.impl.IdentifiedImpl <em>Identified</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see pcm_mockup.impl.IdentifiedImpl
	 * @see pcm_mockup.impl.Pcm_mockupPackageImpl#getIdentified()
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
	 * The meta object id for the '{@link pcm_mockup.impl.RepositoryImpl <em>Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see pcm_mockup.impl.RepositoryImpl
	 * @see pcm_mockup.impl.Pcm_mockupPackageImpl#getRepository()
	 * @generated
	 */
	int REPOSITORY = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__NAME = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Interfaces</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__INTERFACES = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Components</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__COMPONENTS = IDENTIFIED_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link pcm_mockup.impl.PInterfaceImpl <em>PInterface</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see pcm_mockup.impl.PInterfaceImpl
	 * @see pcm_mockup.impl.Pcm_mockupPackageImpl#getPInterface()
	 * @generated
	 */
	int PINTERFACE = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PINTERFACE__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PINTERFACE__NAME = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Methods</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PINTERFACE__METHODS = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>PInterface</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PINTERFACE_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>PInterface</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PINTERFACE_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link pcm_mockup.impl.ComponentImpl <em>Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see pcm_mockup.impl.ComponentImpl
	 * @see pcm_mockup.impl.Pcm_mockupPackageImpl#getComponent()
	 * @generated
	 */
	int COMPONENT = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__NAME = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Provided Interface</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__PROVIDED_INTERFACE = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Repository Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__REPOSITORY_FACTOR = IDENTIFIED_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Component Exclusive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__COMPONENT_EXCLUSIVE = IDENTIFIED_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;


	/**
	 * The meta object id for the '{@link pcm_mockup.impl.PNamedElementImpl <em>PNamed Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see pcm_mockup.impl.PNamedElementImpl
	 * @see pcm_mockup.impl.Pcm_mockupPackageImpl#getPNamedElement()
	 * @generated
	 */
	int PNAMED_ELEMENT = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PNAMED_ELEMENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>PNamed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PNAMED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>PNamed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PNAMED_ELEMENT_OPERATION_COUNT = 0;


	/**
	 * The meta object id for the '{@link pcm_mockup.impl.PMethodImpl <em>PMethod</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see pcm_mockup.impl.PMethodImpl
	 * @see pcm_mockup.impl.Pcm_mockupPackageImpl#getPMethod()
	 * @generated
	 */
	int PMETHOD = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PMETHOD__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PMETHOD__NAME = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>PMethod</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PMETHOD_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>PMethod</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PMETHOD_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link pcm_mockup.Identified <em>Identified</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identified</em>'.
	 * @see pcm_mockup.Identified
	 * @generated
	 */
	EClass getIdentified();

	/**
	 * Returns the meta object for the attribute '{@link pcm_mockup.Identified#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see pcm_mockup.Identified#getId()
	 * @see #getIdentified()
	 * @generated
	 */
	EAttribute getIdentified_Id();

	/**
	 * Returns the meta object for class '{@link pcm_mockup.Repository <em>Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Repository</em>'.
	 * @see pcm_mockup.Repository
	 * @generated
	 */
	EClass getRepository();

	/**
	 * Returns the meta object for the containment reference list '{@link pcm_mockup.Repository#getInterfaces <em>Interfaces</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Interfaces</em>'.
	 * @see pcm_mockup.Repository#getInterfaces()
	 * @see #getRepository()
	 * @generated
	 */
	EReference getRepository_Interfaces();

	/**
	 * Returns the meta object for the containment reference list '{@link pcm_mockup.Repository#getComponents <em>Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Components</em>'.
	 * @see pcm_mockup.Repository#getComponents()
	 * @see #getRepository()
	 * @generated
	 */
	EReference getRepository_Components();

	/**
	 * Returns the meta object for class '{@link pcm_mockup.PInterface <em>PInterface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>PInterface</em>'.
	 * @see pcm_mockup.PInterface
	 * @generated
	 */
	EClass getPInterface();

	/**
	 * Returns the meta object for the containment reference list '{@link pcm_mockup.PInterface#getMethods <em>Methods</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Methods</em>'.
	 * @see pcm_mockup.PInterface#getMethods()
	 * @see #getPInterface()
	 * @generated
	 */
	EReference getPInterface_Methods();

	/**
	 * Returns the meta object for class '{@link pcm_mockup.Component <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component</em>'.
	 * @see pcm_mockup.Component
	 * @generated
	 */
	EClass getComponent();

	/**
	 * Returns the meta object for the reference '{@link pcm_mockup.Component#getProvidedInterface <em>Provided Interface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Provided Interface</em>'.
	 * @see pcm_mockup.Component#getProvidedInterface()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_ProvidedInterface();

	/**
	 * Returns the meta object for the attribute '{@link pcm_mockup.Component#getRepositoryFactor <em>Repository Factor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Repository Factor</em>'.
	 * @see pcm_mockup.Component#getRepositoryFactor()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_RepositoryFactor();

	/**
	 * Returns the meta object for the attribute '{@link pcm_mockup.Component#getComponentExclusive <em>Component Exclusive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Component Exclusive</em>'.
	 * @see pcm_mockup.Component#getComponentExclusive()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_ComponentExclusive();

	/**
	 * Returns the meta object for class '{@link pcm_mockup.PNamedElement <em>PNamed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>PNamed Element</em>'.
	 * @see pcm_mockup.PNamedElement
	 * @generated
	 */
	EClass getPNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link pcm_mockup.PNamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see pcm_mockup.PNamedElement#getName()
	 * @see #getPNamedElement()
	 * @generated
	 */
	EAttribute getPNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link pcm_mockup.PMethod <em>PMethod</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>PMethod</em>'.
	 * @see pcm_mockup.PMethod
	 * @generated
	 */
	EClass getPMethod();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	Pcm_mockupFactory getPcm_mockupFactory();

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
		 * The meta object literal for the '{@link pcm_mockup.impl.IdentifiedImpl <em>Identified</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see pcm_mockup.impl.IdentifiedImpl
		 * @see pcm_mockup.impl.Pcm_mockupPackageImpl#getIdentified()
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
		 * The meta object literal for the '{@link pcm_mockup.impl.RepositoryImpl <em>Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see pcm_mockup.impl.RepositoryImpl
		 * @see pcm_mockup.impl.Pcm_mockupPackageImpl#getRepository()
		 * @generated
		 */
		EClass REPOSITORY = eINSTANCE.getRepository();

		/**
		 * The meta object literal for the '<em><b>Interfaces</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY__INTERFACES = eINSTANCE.getRepository_Interfaces();

		/**
		 * The meta object literal for the '<em><b>Components</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY__COMPONENTS = eINSTANCE.getRepository_Components();

		/**
		 * The meta object literal for the '{@link pcm_mockup.impl.PInterfaceImpl <em>PInterface</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see pcm_mockup.impl.PInterfaceImpl
		 * @see pcm_mockup.impl.Pcm_mockupPackageImpl#getPInterface()
		 * @generated
		 */
		EClass PINTERFACE = eINSTANCE.getPInterface();

		/**
		 * The meta object literal for the '<em><b>Methods</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PINTERFACE__METHODS = eINSTANCE.getPInterface_Methods();

		/**
		 * The meta object literal for the '{@link pcm_mockup.impl.ComponentImpl <em>Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see pcm_mockup.impl.ComponentImpl
		 * @see pcm_mockup.impl.Pcm_mockupPackageImpl#getComponent()
		 * @generated
		 */
		EClass COMPONENT = eINSTANCE.getComponent();

		/**
		 * The meta object literal for the '<em><b>Provided Interface</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT__PROVIDED_INTERFACE = eINSTANCE.getComponent_ProvidedInterface();

		/**
		 * The meta object literal for the '<em><b>Repository Factor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT__REPOSITORY_FACTOR = eINSTANCE.getComponent_RepositoryFactor();

		/**
		 * The meta object literal for the '<em><b>Component Exclusive</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT__COMPONENT_EXCLUSIVE = eINSTANCE.getComponent_ComponentExclusive();

		/**
		 * The meta object literal for the '{@link pcm_mockup.impl.PNamedElementImpl <em>PNamed Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see pcm_mockup.impl.PNamedElementImpl
		 * @see pcm_mockup.impl.Pcm_mockupPackageImpl#getPNamedElement()
		 * @generated
		 */
		EClass PNAMED_ELEMENT = eINSTANCE.getPNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PNAMED_ELEMENT__NAME = eINSTANCE.getPNamedElement_Name();

		/**
		 * The meta object literal for the '{@link pcm_mockup.impl.PMethodImpl <em>PMethod</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see pcm_mockup.impl.PMethodImpl
		 * @see pcm_mockup.impl.Pcm_mockupPackageImpl#getPMethod()
		 * @generated
		 */
		EClass PMETHOD = eINSTANCE.getPMethod();

	}

} //Pcm_mockupPackage
