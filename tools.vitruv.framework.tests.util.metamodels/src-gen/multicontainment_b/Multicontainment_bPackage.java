/**
 */
package multicontainment_b;

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
 * @see multicontainment_b.Multicontainment_bFactory
 * @model kind="package"
 * @generated
 */
public interface Multicontainment_bPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "multicontainment_b";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv.tests.metamodels.multicontainment_b";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "multicontainment_b";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Multicontainment_bPackage eINSTANCE = multicontainment_b.impl.Multicontainment_bPackageImpl.init();

	/**
	 * The meta object id for the '{@link multicontainment_b.impl.IdentifiedImpl <em>Identified</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see multicontainment_b.impl.IdentifiedImpl
	 * @see multicontainment_b.impl.Multicontainment_bPackageImpl#getIdentified()
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
	 * The meta object id for the '{@link multicontainment_b.impl.RootBImpl <em>Root B</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see multicontainment_b.impl.RootBImpl
	 * @see multicontainment_b.impl.Multicontainment_bPackageImpl#getRootB()
	 * @generated
	 */
	int ROOT_B = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_B__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Children B1a</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_B__CHILDREN_B1A = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Children B1b</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_B__CHILDREN_B1B = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Children B2a</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_B__CHILDREN_B2A = IDENTIFIED_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Root B</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_B_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Root B</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_B_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link multicontainment_b.impl.ChildB1Impl <em>Child B1</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see multicontainment_b.impl.ChildB1Impl
	 * @see multicontainment_b.impl.Multicontainment_bPackageImpl#getChildB1()
	 * @generated
	 */
	int CHILD_B1 = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_B1__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_B1__NAME = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Child B1</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_B1_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Child B1</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_B1_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link multicontainment_b.impl.ChildB2Impl <em>Child B2</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see multicontainment_b.impl.ChildB2Impl
	 * @see multicontainment_b.impl.Multicontainment_bPackageImpl#getChildB2()
	 * @generated
	 */
	int CHILD_B2 = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_B2__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_B2__NAME = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Child B2</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_B2_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Child B2</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_B2_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link multicontainment_b.RootB <em>Root B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Root B</em>'.
	 * @see multicontainment_b.RootB
	 * @generated
	 */
	EClass getRootB();

	/**
	 * Returns the meta object for the containment reference '{@link multicontainment_b.RootB#getChildrenB1a <em>Children B1a</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Children B1a</em>'.
	 * @see multicontainment_b.RootB#getChildrenB1a()
	 * @see #getRootB()
	 * @generated
	 */
	EReference getRootB_ChildrenB1a();

	/**
	 * Returns the meta object for the containment reference '{@link multicontainment_b.RootB#getChildrenB1b <em>Children B1b</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Children B1b</em>'.
	 * @see multicontainment_b.RootB#getChildrenB1b()
	 * @see #getRootB()
	 * @generated
	 */
	EReference getRootB_ChildrenB1b();

	/**
	 * Returns the meta object for the containment reference '{@link multicontainment_b.RootB#getChildrenB2a <em>Children B2a</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Children B2a</em>'.
	 * @see multicontainment_b.RootB#getChildrenB2a()
	 * @see #getRootB()
	 * @generated
	 */
	EReference getRootB_ChildrenB2a();

	/**
	 * Returns the meta object for class '{@link multicontainment_b.ChildB1 <em>Child B1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Child B1</em>'.
	 * @see multicontainment_b.ChildB1
	 * @generated
	 */
	EClass getChildB1();

	/**
	 * Returns the meta object for the attribute '{@link multicontainment_b.ChildB1#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see multicontainment_b.ChildB1#getName()
	 * @see #getChildB1()
	 * @generated
	 */
	EAttribute getChildB1_Name();

	/**
	 * Returns the meta object for class '{@link multicontainment_b.ChildB2 <em>Child B2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Child B2</em>'.
	 * @see multicontainment_b.ChildB2
	 * @generated
	 */
	EClass getChildB2();

	/**
	 * Returns the meta object for the attribute '{@link multicontainment_b.ChildB2#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see multicontainment_b.ChildB2#getName()
	 * @see #getChildB2()
	 * @generated
	 */
	EAttribute getChildB2_Name();

	/**
	 * Returns the meta object for class '{@link multicontainment_b.Identified <em>Identified</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identified</em>'.
	 * @see multicontainment_b.Identified
	 * @generated
	 */
	EClass getIdentified();

	/**
	 * Returns the meta object for the attribute '{@link multicontainment_b.Identified#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see multicontainment_b.Identified#getId()
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
	Multicontainment_bFactory getMulticontainment_bFactory();

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
		 * The meta object literal for the '{@link multicontainment_b.impl.RootBImpl <em>Root B</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see multicontainment_b.impl.RootBImpl
		 * @see multicontainment_b.impl.Multicontainment_bPackageImpl#getRootB()
		 * @generated
		 */
		EClass ROOT_B = eINSTANCE.getRootB();

		/**
		 * The meta object literal for the '<em><b>Children B1a</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_B__CHILDREN_B1A = eINSTANCE.getRootB_ChildrenB1a();

		/**
		 * The meta object literal for the '<em><b>Children B1b</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_B__CHILDREN_B1B = eINSTANCE.getRootB_ChildrenB1b();

		/**
		 * The meta object literal for the '<em><b>Children B2a</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_B__CHILDREN_B2A = eINSTANCE.getRootB_ChildrenB2a();

		/**
		 * The meta object literal for the '{@link multicontainment_b.impl.ChildB1Impl <em>Child B1</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see multicontainment_b.impl.ChildB1Impl
		 * @see multicontainment_b.impl.Multicontainment_bPackageImpl#getChildB1()
		 * @generated
		 */
		EClass CHILD_B1 = eINSTANCE.getChildB1();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHILD_B1__NAME = eINSTANCE.getChildB1_Name();

		/**
		 * The meta object literal for the '{@link multicontainment_b.impl.ChildB2Impl <em>Child B2</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see multicontainment_b.impl.ChildB2Impl
		 * @see multicontainment_b.impl.Multicontainment_bPackageImpl#getChildB2()
		 * @generated
		 */
		EClass CHILD_B2 = eINSTANCE.getChildB2();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHILD_B2__NAME = eINSTANCE.getChildB2_Name();

		/**
		 * The meta object literal for the '{@link multicontainment_b.impl.IdentifiedImpl <em>Identified</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see multicontainment_b.impl.IdentifiedImpl
		 * @see multicontainment_b.impl.Multicontainment_bPackageImpl#getIdentified()
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

} //Multicontainment_bPackage
