/**
 */
package multicontainment_a;

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
 * @see multicontainment_a.Multicontainment_aFactory
 * @model kind="package"
 * @generated
 */
public interface Multicontainment_aPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "multicontainment_a";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv.tests.metamodels.multicontainment_a";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "multicontainment_a";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Multicontainment_aPackage eINSTANCE = multicontainment_a.impl.Multicontainment_aPackageImpl.init();

	/**
	 * The meta object id for the '{@link multicontainment_a.impl.IdentifiedImpl <em>Identified</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see multicontainment_a.impl.IdentifiedImpl
	 * @see multicontainment_a.impl.Multicontainment_aPackageImpl#getIdentified()
	 * @generated
	 */
	int IDENTIFIED = 3;

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
	 * The meta object id for the '{@link multicontainment_a.impl.RootAImpl <em>Root A</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see multicontainment_a.impl.RootAImpl
	 * @see multicontainment_a.impl.Multicontainment_aPackageImpl#getRootA()
	 * @generated
	 */
	int ROOT_A = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_A__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Children A1a</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_A__CHILDREN_A1A = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Children A1b</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_A__CHILDREN_A1B = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Children A2a</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_A__CHILDREN_A2A = IDENTIFIED_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Root A</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_A_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Root A</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_A_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link multicontainment_a.impl.ChildA1Impl <em>Child A1</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see multicontainment_a.impl.ChildA1Impl
	 * @see multicontainment_a.impl.Multicontainment_aPackageImpl#getChildA1()
	 * @generated
	 */
	int CHILD_A1 = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_A1__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_A1__NAME = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Child A1</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_A1_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Child A1</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_A1_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link multicontainment_a.impl.ChildA2Impl <em>Child A2</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see multicontainment_a.impl.ChildA2Impl
	 * @see multicontainment_a.impl.Multicontainment_aPackageImpl#getChildA2()
	 * @generated
	 */
	int CHILD_A2 = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_A2__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_A2__NAME = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Child A2</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_A2_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Child A2</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_A2_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link multicontainment_a.RootA <em>Root A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Root A</em>'.
	 * @see multicontainment_a.RootA
	 * @generated
	 */
	EClass getRootA();

	/**
	 * Returns the meta object for the containment reference '{@link multicontainment_a.RootA#getChildrenA1a <em>Children A1a</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Children A1a</em>'.
	 * @see multicontainment_a.RootA#getChildrenA1a()
	 * @see #getRootA()
	 * @generated
	 */
	EReference getRootA_ChildrenA1a();

	/**
	 * Returns the meta object for the containment reference '{@link multicontainment_a.RootA#getChildrenA1b <em>Children A1b</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Children A1b</em>'.
	 * @see multicontainment_a.RootA#getChildrenA1b()
	 * @see #getRootA()
	 * @generated
	 */
	EReference getRootA_ChildrenA1b();

	/**
	 * Returns the meta object for the containment reference '{@link multicontainment_a.RootA#getChildrenA2a <em>Children A2a</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Children A2a</em>'.
	 * @see multicontainment_a.RootA#getChildrenA2a()
	 * @see #getRootA()
	 * @generated
	 */
	EReference getRootA_ChildrenA2a();

	/**
	 * Returns the meta object for class '{@link multicontainment_a.ChildA1 <em>Child A1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Child A1</em>'.
	 * @see multicontainment_a.ChildA1
	 * @generated
	 */
	EClass getChildA1();

	/**
	 * Returns the meta object for the attribute '{@link multicontainment_a.ChildA1#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see multicontainment_a.ChildA1#getName()
	 * @see #getChildA1()
	 * @generated
	 */
	EAttribute getChildA1_Name();

	/**
	 * Returns the meta object for class '{@link multicontainment_a.ChildA2 <em>Child A2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Child A2</em>'.
	 * @see multicontainment_a.ChildA2
	 * @generated
	 */
	EClass getChildA2();

	/**
	 * Returns the meta object for the attribute '{@link multicontainment_a.ChildA2#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see multicontainment_a.ChildA2#getName()
	 * @see #getChildA2()
	 * @generated
	 */
	EAttribute getChildA2_Name();

	/**
	 * Returns the meta object for class '{@link multicontainment_a.Identified <em>Identified</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identified</em>'.
	 * @see multicontainment_a.Identified
	 * @generated
	 */
	EClass getIdentified();

	/**
	 * Returns the meta object for the attribute '{@link multicontainment_a.Identified#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see multicontainment_a.Identified#getId()
	 * @see #getIdentified()
	 * @generated
	 */
	EAttribute getIdentified_Id();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	Multicontainment_aFactory getMulticontainment_aFactory();

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
		 * The meta object literal for the '{@link multicontainment_a.impl.RootAImpl <em>Root A</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see multicontainment_a.impl.RootAImpl
		 * @see multicontainment_a.impl.Multicontainment_aPackageImpl#getRootA()
		 * @generated
		 */
		EClass ROOT_A = eINSTANCE.getRootA();

		/**
		 * The meta object literal for the '<em><b>Children A1a</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_A__CHILDREN_A1A = eINSTANCE.getRootA_ChildrenA1a();

		/**
		 * The meta object literal for the '<em><b>Children A1b</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_A__CHILDREN_A1B = eINSTANCE.getRootA_ChildrenA1b();

		/**
		 * The meta object literal for the '<em><b>Children A2a</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_A__CHILDREN_A2A = eINSTANCE.getRootA_ChildrenA2a();

		/**
		 * The meta object literal for the '{@link multicontainment_a.impl.ChildA1Impl <em>Child A1</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see multicontainment_a.impl.ChildA1Impl
		 * @see multicontainment_a.impl.Multicontainment_aPackageImpl#getChildA1()
		 * @generated
		 */
		EClass CHILD_A1 = eINSTANCE.getChildA1();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHILD_A1__NAME = eINSTANCE.getChildA1_Name();

		/**
		 * The meta object literal for the '{@link multicontainment_a.impl.ChildA2Impl <em>Child A2</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see multicontainment_a.impl.ChildA2Impl
		 * @see multicontainment_a.impl.Multicontainment_aPackageImpl#getChildA2()
		 * @generated
		 */
		EClass CHILD_A2 = eINSTANCE.getChildA2();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHILD_A2__NAME = eINSTANCE.getChildA2_Name();

		/**
		 * The meta object literal for the '{@link multicontainment_a.impl.IdentifiedImpl <em>Identified</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see multicontainment_a.impl.IdentifiedImpl
		 * @see multicontainment_a.impl.Multicontainment_aPackageImpl#getIdentified()
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

	}

} //Multicontainment_aPackage
