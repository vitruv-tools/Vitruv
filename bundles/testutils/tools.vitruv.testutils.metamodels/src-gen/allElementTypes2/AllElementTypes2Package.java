/**
 */
package allElementTypes2;

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
 * @see allElementTypes2.AllElementTypes2Factory
 * @model kind="package"
 * @generated
 */
public interface AllElementTypes2Package extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "allElementTypes2";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv.tests.metamodels.allElementTypes2";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "allElementTypes2";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AllElementTypes2Package eINSTANCE = allElementTypes2.impl.AllElementTypes2PackageImpl.init();

	/**
	 * The meta object id for the '{@link allElementTypes2.impl.Identified2Impl <em>Identified2</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see allElementTypes2.impl.Identified2Impl
	 * @see allElementTypes2.impl.AllElementTypes2PackageImpl#getIdentified2()
	 * @generated
	 */
	int IDENTIFIED2 = 3;

	/**
	 * The feature id for the '<em><b>Id2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIED2__ID2 = 0;

	/**
	 * The number of structural features of the '<em>Identified2</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIED2_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Identified2</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIED2_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link allElementTypes2.impl.Root2Impl <em>Root2</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see allElementTypes2.impl.Root2Impl
	 * @see allElementTypes2.impl.AllElementTypes2PackageImpl#getRoot2()
	 * @generated
	 */
	int ROOT2 = 0;

	/**
	 * The feature id for the '<em><b>Id2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT2__ID2 = IDENTIFIED2__ID2;

	/**
	 * The feature id for the '<em><b>Single Valued EAttribute2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT2__SINGLE_VALUED_EATTRIBUTE2 = IDENTIFIED2_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Single Valued Non Containment EReference2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT2__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE2 = IDENTIFIED2_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Single Valued Containment EReference2</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT2__SINGLE_VALUED_CONTAINMENT_EREFERENCE2 = IDENTIFIED2_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Multi Valued EAttribute2</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT2__MULTI_VALUED_EATTRIBUTE2 = IDENTIFIED2_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Multi Valued Non Containment EReference2</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT2__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE2 = IDENTIFIED2_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Multi Valued Containment EReference2</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT2__MULTI_VALUED_CONTAINMENT_EREFERENCE2 = IDENTIFIED2_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Non Root Object Container Helper</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT2__NON_ROOT_OBJECT_CONTAINER_HELPER = IDENTIFIED2_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Root2</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT2_FEATURE_COUNT = IDENTIFIED2_FEATURE_COUNT + 7;

	/**
	 * The number of operations of the '<em>Root2</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT2_OPERATION_COUNT = IDENTIFIED2_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link allElementTypes2.impl.NonRoot2Impl <em>Non Root2</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see allElementTypes2.impl.NonRoot2Impl
	 * @see allElementTypes2.impl.AllElementTypes2PackageImpl#getNonRoot2()
	 * @generated
	 */
	int NON_ROOT2 = 1;

	/**
	 * The feature id for the '<em><b>Id2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NON_ROOT2__ID2 = IDENTIFIED2__ID2;

	/**
	 * The number of structural features of the '<em>Non Root2</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NON_ROOT2_FEATURE_COUNT = IDENTIFIED2_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Non Root2</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NON_ROOT2_OPERATION_COUNT = IDENTIFIED2_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link allElementTypes2.impl.NonRootObjectContainerHelper2Impl <em>Non Root Object Container Helper2</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see allElementTypes2.impl.NonRootObjectContainerHelper2Impl
	 * @see allElementTypes2.impl.AllElementTypes2PackageImpl#getNonRootObjectContainerHelper2()
	 * @generated
	 */
	int NON_ROOT_OBJECT_CONTAINER_HELPER2 = 2;

	/**
	 * The feature id for the '<em><b>Id2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NON_ROOT_OBJECT_CONTAINER_HELPER2__ID2 = IDENTIFIED2__ID2;

	/**
	 * The feature id for the '<em><b>Non Root Objects Containment2</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NON_ROOT_OBJECT_CONTAINER_HELPER2__NON_ROOT_OBJECTS_CONTAINMENT2 = IDENTIFIED2_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Non Root Object Container Helper2</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NON_ROOT_OBJECT_CONTAINER_HELPER2_FEATURE_COUNT = IDENTIFIED2_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Non Root Object Container Helper2</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NON_ROOT_OBJECT_CONTAINER_HELPER2_OPERATION_COUNT = IDENTIFIED2_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link allElementTypes2.Root2 <em>Root2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Root2</em>'.
	 * @see allElementTypes2.Root2
	 * @generated
	 */
	EClass getRoot2();

	/**
	 * Returns the meta object for the attribute '{@link allElementTypes2.Root2#getSingleValuedEAttribute2 <em>Single Valued EAttribute2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Single Valued EAttribute2</em>'.
	 * @see allElementTypes2.Root2#getSingleValuedEAttribute2()
	 * @see #getRoot2()
	 * @generated
	 */
	EAttribute getRoot2_SingleValuedEAttribute2();

	/**
	 * Returns the meta object for the reference '{@link allElementTypes2.Root2#getSingleValuedNonContainmentEReference2 <em>Single Valued Non Containment EReference2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Single Valued Non Containment EReference2</em>'.
	 * @see allElementTypes2.Root2#getSingleValuedNonContainmentEReference2()
	 * @see #getRoot2()
	 * @generated
	 */
	EReference getRoot2_SingleValuedNonContainmentEReference2();

	/**
	 * Returns the meta object for the containment reference '{@link allElementTypes2.Root2#getSingleValuedContainmentEReference2 <em>Single Valued Containment EReference2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Single Valued Containment EReference2</em>'.
	 * @see allElementTypes2.Root2#getSingleValuedContainmentEReference2()
	 * @see #getRoot2()
	 * @generated
	 */
	EReference getRoot2_SingleValuedContainmentEReference2();

	/**
	 * Returns the meta object for the attribute list '{@link allElementTypes2.Root2#getMultiValuedEAttribute2 <em>Multi Valued EAttribute2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Multi Valued EAttribute2</em>'.
	 * @see allElementTypes2.Root2#getMultiValuedEAttribute2()
	 * @see #getRoot2()
	 * @generated
	 */
	EAttribute getRoot2_MultiValuedEAttribute2();

	/**
	 * Returns the meta object for the reference list '{@link allElementTypes2.Root2#getMultiValuedNonContainmentEReference2 <em>Multi Valued Non Containment EReference2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Multi Valued Non Containment EReference2</em>'.
	 * @see allElementTypes2.Root2#getMultiValuedNonContainmentEReference2()
	 * @see #getRoot2()
	 * @generated
	 */
	EReference getRoot2_MultiValuedNonContainmentEReference2();

	/**
	 * Returns the meta object for the containment reference list '{@link allElementTypes2.Root2#getMultiValuedContainmentEReference2 <em>Multi Valued Containment EReference2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Multi Valued Containment EReference2</em>'.
	 * @see allElementTypes2.Root2#getMultiValuedContainmentEReference2()
	 * @see #getRoot2()
	 * @generated
	 */
	EReference getRoot2_MultiValuedContainmentEReference2();

	/**
	 * Returns the meta object for the containment reference '{@link allElementTypes2.Root2#getNonRootObjectContainerHelper <em>Non Root Object Container Helper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Non Root Object Container Helper</em>'.
	 * @see allElementTypes2.Root2#getNonRootObjectContainerHelper()
	 * @see #getRoot2()
	 * @generated
	 */
	EReference getRoot2_NonRootObjectContainerHelper();

	/**
	 * Returns the meta object for class '{@link allElementTypes2.NonRoot2 <em>Non Root2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Non Root2</em>'.
	 * @see allElementTypes2.NonRoot2
	 * @generated
	 */
	EClass getNonRoot2();

	/**
	 * Returns the meta object for class '{@link allElementTypes2.NonRootObjectContainerHelper2 <em>Non Root Object Container Helper2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Non Root Object Container Helper2</em>'.
	 * @see allElementTypes2.NonRootObjectContainerHelper2
	 * @generated
	 */
	EClass getNonRootObjectContainerHelper2();

	/**
	 * Returns the meta object for the containment reference list '{@link allElementTypes2.NonRootObjectContainerHelper2#getNonRootObjectsContainment2 <em>Non Root Objects Containment2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Non Root Objects Containment2</em>'.
	 * @see allElementTypes2.NonRootObjectContainerHelper2#getNonRootObjectsContainment2()
	 * @see #getNonRootObjectContainerHelper2()
	 * @generated
	 */
	EReference getNonRootObjectContainerHelper2_NonRootObjectsContainment2();

	/**
	 * Returns the meta object for class '{@link allElementTypes2.Identified2 <em>Identified2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identified2</em>'.
	 * @see allElementTypes2.Identified2
	 * @generated
	 */
	EClass getIdentified2();

	/**
	 * Returns the meta object for the attribute '{@link allElementTypes2.Identified2#getId2 <em>Id2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id2</em>'.
	 * @see allElementTypes2.Identified2#getId2()
	 * @see #getIdentified2()
	 * @generated
	 */
	EAttribute getIdentified2_Id2();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AllElementTypes2Factory getAllElementTypes2Factory();

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
		 * The meta object literal for the '{@link allElementTypes2.impl.Root2Impl <em>Root2</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see allElementTypes2.impl.Root2Impl
		 * @see allElementTypes2.impl.AllElementTypes2PackageImpl#getRoot2()
		 * @generated
		 */
		EClass ROOT2 = eINSTANCE.getRoot2();

		/**
		 * The meta object literal for the '<em><b>Single Valued EAttribute2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROOT2__SINGLE_VALUED_EATTRIBUTE2 = eINSTANCE.getRoot2_SingleValuedEAttribute2();

		/**
		 * The meta object literal for the '<em><b>Single Valued Non Containment EReference2</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT2__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE2 = eINSTANCE.getRoot2_SingleValuedNonContainmentEReference2();

		/**
		 * The meta object literal for the '<em><b>Single Valued Containment EReference2</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT2__SINGLE_VALUED_CONTAINMENT_EREFERENCE2 = eINSTANCE.getRoot2_SingleValuedContainmentEReference2();

		/**
		 * The meta object literal for the '<em><b>Multi Valued EAttribute2</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROOT2__MULTI_VALUED_EATTRIBUTE2 = eINSTANCE.getRoot2_MultiValuedEAttribute2();

		/**
		 * The meta object literal for the '<em><b>Multi Valued Non Containment EReference2</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT2__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE2 = eINSTANCE.getRoot2_MultiValuedNonContainmentEReference2();

		/**
		 * The meta object literal for the '<em><b>Multi Valued Containment EReference2</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT2__MULTI_VALUED_CONTAINMENT_EREFERENCE2 = eINSTANCE.getRoot2_MultiValuedContainmentEReference2();

		/**
		 * The meta object literal for the '<em><b>Non Root Object Container Helper</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT2__NON_ROOT_OBJECT_CONTAINER_HELPER = eINSTANCE.getRoot2_NonRootObjectContainerHelper();

		/**
		 * The meta object literal for the '{@link allElementTypes2.impl.NonRoot2Impl <em>Non Root2</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see allElementTypes2.impl.NonRoot2Impl
		 * @see allElementTypes2.impl.AllElementTypes2PackageImpl#getNonRoot2()
		 * @generated
		 */
		EClass NON_ROOT2 = eINSTANCE.getNonRoot2();

		/**
		 * The meta object literal for the '{@link allElementTypes2.impl.NonRootObjectContainerHelper2Impl <em>Non Root Object Container Helper2</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see allElementTypes2.impl.NonRootObjectContainerHelper2Impl
		 * @see allElementTypes2.impl.AllElementTypes2PackageImpl#getNonRootObjectContainerHelper2()
		 * @generated
		 */
		EClass NON_ROOT_OBJECT_CONTAINER_HELPER2 = eINSTANCE.getNonRootObjectContainerHelper2();

		/**
		 * The meta object literal for the '<em><b>Non Root Objects Containment2</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NON_ROOT_OBJECT_CONTAINER_HELPER2__NON_ROOT_OBJECTS_CONTAINMENT2 = eINSTANCE.getNonRootObjectContainerHelper2_NonRootObjectsContainment2();

		/**
		 * The meta object literal for the '{@link allElementTypes2.impl.Identified2Impl <em>Identified2</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see allElementTypes2.impl.Identified2Impl
		 * @see allElementTypes2.impl.AllElementTypes2PackageImpl#getIdentified2()
		 * @generated
		 */
		EClass IDENTIFIED2 = eINSTANCE.getIdentified2();

		/**
		 * The meta object literal for the '<em><b>Id2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFIED2__ID2 = eINSTANCE.getIdentified2_Id2();

	}

} //AllElementTypes2Package
