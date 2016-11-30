/**
 */
package attribute_to_structure_struct_1;

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
 * @see attribute_to_structure_struct_1.Attribute_to_structure_struct_1Factory
 * @model kind="package"
 * @generated
 */
public interface Attribute_to_structure_struct_1Package extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "attribute_to_structure_struct_1";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv.tests.metamodels.attribute_to_structure_struct_1";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "attribute_to_structure_struct_1";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Attribute_to_structure_struct_1Package eINSTANCE = attribute_to_structure_struct_1.impl.Attribute_to_structure_struct_1PackageImpl.init();

	/**
	 * The meta object id for the '{@link attribute_to_structure_struct_1.impl.IdentifiedImpl <em>Identified</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see attribute_to_structure_struct_1.impl.IdentifiedImpl
	 * @see attribute_to_structure_struct_1.impl.Attribute_to_structure_struct_1PackageImpl#getIdentified()
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
	 * The meta object id for the '{@link attribute_to_structure_struct_1.impl.ModelBImpl <em>Model B</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see attribute_to_structure_struct_1.impl.ModelBImpl
	 * @see attribute_to_structure_struct_1.impl.Attribute_to_structure_struct_1PackageImpl#getModelB()
	 * @generated
	 */
	int MODEL_B = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_B__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Content</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_B__CONTENT = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Model B</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_B_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Model B</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_B_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link attribute_to_structure_struct_1.impl.StructuredImpl <em>Structured</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see attribute_to_structure_struct_1.impl.StructuredImpl
	 * @see attribute_to_structure_struct_1.impl.Attribute_to_structure_struct_1PackageImpl#getStructured()
	 * @generated
	 */
	int STRUCTURED = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED__NAME = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Int Container</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED__INT_CONTAINER = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Str Container</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED__STR_CONTAINER = IDENTIFIED_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Float Container</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED__FLOAT_CONTAINER = IDENTIFIED_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Structured</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Structured</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link attribute_to_structure_struct_1.impl.IntContainerImpl <em>Int Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see attribute_to_structure_struct_1.impl.IntContainerImpl
	 * @see attribute_to_structure_struct_1.impl.Attribute_to_structure_struct_1PackageImpl#getIntContainer()
	 * @generated
	 */
	int INT_CONTAINER = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_CONTAINER__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_CONTAINER__VALUE = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Int Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_CONTAINER_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Int Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_CONTAINER_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link attribute_to_structure_struct_1.impl.StrContainerImpl <em>Str Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see attribute_to_structure_struct_1.impl.StrContainerImpl
	 * @see attribute_to_structure_struct_1.impl.Attribute_to_structure_struct_1PackageImpl#getStrContainer()
	 * @generated
	 */
	int STR_CONTAINER = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STR_CONTAINER__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STR_CONTAINER__VALUE = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Str Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STR_CONTAINER_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Str Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STR_CONTAINER_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link attribute_to_structure_struct_1.impl.FloatContainerImpl <em>Float Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see attribute_to_structure_struct_1.impl.FloatContainerImpl
	 * @see attribute_to_structure_struct_1.impl.Attribute_to_structure_struct_1PackageImpl#getFloatContainer()
	 * @generated
	 */
	int FLOAT_CONTAINER = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_CONTAINER__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_CONTAINER__VALUE = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Float Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_CONTAINER_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Float Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_CONTAINER_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link attribute_to_structure_struct_1.Identified <em>Identified</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identified</em>'.
	 * @see attribute_to_structure_struct_1.Identified
	 * @generated
	 */
	EClass getIdentified();

	/**
	 * Returns the meta object for the attribute '{@link attribute_to_structure_struct_1.Identified#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see attribute_to_structure_struct_1.Identified#getId()
	 * @see #getIdentified()
	 * @generated
	 */
	EAttribute getIdentified_Id();

	/**
	 * Returns the meta object for class '{@link attribute_to_structure_struct_1.ModelB <em>Model B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model B</em>'.
	 * @see attribute_to_structure_struct_1.ModelB
	 * @generated
	 */
	EClass getModelB();

	/**
	 * Returns the meta object for the containment reference list '{@link attribute_to_structure_struct_1.ModelB#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Content</em>'.
	 * @see attribute_to_structure_struct_1.ModelB#getContent()
	 * @see #getModelB()
	 * @generated
	 */
	EReference getModelB_Content();

	/**
	 * Returns the meta object for class '{@link attribute_to_structure_struct_1.Structured <em>Structured</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Structured</em>'.
	 * @see attribute_to_structure_struct_1.Structured
	 * @generated
	 */
	EClass getStructured();

	/**
	 * Returns the meta object for the attribute '{@link attribute_to_structure_struct_1.Structured#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see attribute_to_structure_struct_1.Structured#getName()
	 * @see #getStructured()
	 * @generated
	 */
	EAttribute getStructured_Name();

	/**
	 * Returns the meta object for the containment reference '{@link attribute_to_structure_struct_1.Structured#getIntContainer <em>Int Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Int Container</em>'.
	 * @see attribute_to_structure_struct_1.Structured#getIntContainer()
	 * @see #getStructured()
	 * @generated
	 */
	EReference getStructured_IntContainer();

	/**
	 * Returns the meta object for the containment reference '{@link attribute_to_structure_struct_1.Structured#getStrContainer <em>Str Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Str Container</em>'.
	 * @see attribute_to_structure_struct_1.Structured#getStrContainer()
	 * @see #getStructured()
	 * @generated
	 */
	EReference getStructured_StrContainer();

	/**
	 * Returns the meta object for the containment reference '{@link attribute_to_structure_struct_1.Structured#getFloatContainer <em>Float Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Float Container</em>'.
	 * @see attribute_to_structure_struct_1.Structured#getFloatContainer()
	 * @see #getStructured()
	 * @generated
	 */
	EReference getStructured_FloatContainer();

	/**
	 * Returns the meta object for class '{@link attribute_to_structure_struct_1.IntContainer <em>Int Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Int Container</em>'.
	 * @see attribute_to_structure_struct_1.IntContainer
	 * @generated
	 */
	EClass getIntContainer();

	/**
	 * Returns the meta object for the attribute '{@link attribute_to_structure_struct_1.IntContainer#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see attribute_to_structure_struct_1.IntContainer#getValue()
	 * @see #getIntContainer()
	 * @generated
	 */
	EAttribute getIntContainer_Value();

	/**
	 * Returns the meta object for class '{@link attribute_to_structure_struct_1.StrContainer <em>Str Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Str Container</em>'.
	 * @see attribute_to_structure_struct_1.StrContainer
	 * @generated
	 */
	EClass getStrContainer();

	/**
	 * Returns the meta object for the attribute '{@link attribute_to_structure_struct_1.StrContainer#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see attribute_to_structure_struct_1.StrContainer#getValue()
	 * @see #getStrContainer()
	 * @generated
	 */
	EAttribute getStrContainer_Value();

	/**
	 * Returns the meta object for class '{@link attribute_to_structure_struct_1.FloatContainer <em>Float Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Float Container</em>'.
	 * @see attribute_to_structure_struct_1.FloatContainer
	 * @generated
	 */
	EClass getFloatContainer();

	/**
	 * Returns the meta object for the attribute '{@link attribute_to_structure_struct_1.FloatContainer#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see attribute_to_structure_struct_1.FloatContainer#getValue()
	 * @see #getFloatContainer()
	 * @generated
	 */
	EAttribute getFloatContainer_Value();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	Attribute_to_structure_struct_1Factory getAttribute_to_structure_struct_1Factory();

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
		 * The meta object literal for the '{@link attribute_to_structure_struct_1.impl.IdentifiedImpl <em>Identified</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see attribute_to_structure_struct_1.impl.IdentifiedImpl
		 * @see attribute_to_structure_struct_1.impl.Attribute_to_structure_struct_1PackageImpl#getIdentified()
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
		 * The meta object literal for the '{@link attribute_to_structure_struct_1.impl.ModelBImpl <em>Model B</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see attribute_to_structure_struct_1.impl.ModelBImpl
		 * @see attribute_to_structure_struct_1.impl.Attribute_to_structure_struct_1PackageImpl#getModelB()
		 * @generated
		 */
		EClass MODEL_B = eINSTANCE.getModelB();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_B__CONTENT = eINSTANCE.getModelB_Content();

		/**
		 * The meta object literal for the '{@link attribute_to_structure_struct_1.impl.StructuredImpl <em>Structured</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see attribute_to_structure_struct_1.impl.StructuredImpl
		 * @see attribute_to_structure_struct_1.impl.Attribute_to_structure_struct_1PackageImpl#getStructured()
		 * @generated
		 */
		EClass STRUCTURED = eINSTANCE.getStructured();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRUCTURED__NAME = eINSTANCE.getStructured_Name();

		/**
		 * The meta object literal for the '<em><b>Int Container</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCTURED__INT_CONTAINER = eINSTANCE.getStructured_IntContainer();

		/**
		 * The meta object literal for the '<em><b>Str Container</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCTURED__STR_CONTAINER = eINSTANCE.getStructured_StrContainer();

		/**
		 * The meta object literal for the '<em><b>Float Container</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCTURED__FLOAT_CONTAINER = eINSTANCE.getStructured_FloatContainer();

		/**
		 * The meta object literal for the '{@link attribute_to_structure_struct_1.impl.IntContainerImpl <em>Int Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see attribute_to_structure_struct_1.impl.IntContainerImpl
		 * @see attribute_to_structure_struct_1.impl.Attribute_to_structure_struct_1PackageImpl#getIntContainer()
		 * @generated
		 */
		EClass INT_CONTAINER = eINSTANCE.getIntContainer();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INT_CONTAINER__VALUE = eINSTANCE.getIntContainer_Value();

		/**
		 * The meta object literal for the '{@link attribute_to_structure_struct_1.impl.StrContainerImpl <em>Str Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see attribute_to_structure_struct_1.impl.StrContainerImpl
		 * @see attribute_to_structure_struct_1.impl.Attribute_to_structure_struct_1PackageImpl#getStrContainer()
		 * @generated
		 */
		EClass STR_CONTAINER = eINSTANCE.getStrContainer();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STR_CONTAINER__VALUE = eINSTANCE.getStrContainer_Value();

		/**
		 * The meta object literal for the '{@link attribute_to_structure_struct_1.impl.FloatContainerImpl <em>Float Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see attribute_to_structure_struct_1.impl.FloatContainerImpl
		 * @see attribute_to_structure_struct_1.impl.Attribute_to_structure_struct_1PackageImpl#getFloatContainer()
		 * @generated
		 */
		EClass FLOAT_CONTAINER = eINSTANCE.getFloatContainer();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOAT_CONTAINER__VALUE = eINSTANCE.getFloatContainer_Value();

	}

} //Attribute_to_structure_struct_1Package
