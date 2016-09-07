/**
 */
package attribute_to_structure_attr;

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
 * @see attribute_to_structure_attr.Attribute_to_structure_attrFactory
 * @model kind="package"
 * @generated
 */
public interface Attribute_to_structure_attrPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "attribute_to_structure_attr";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv.tests.metamodels.attribute_to_structure_attr";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "attribute_to_structure_attr";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Attribute_to_structure_attrPackage eINSTANCE = attribute_to_structure_attr.impl.Attribute_to_structure_attrPackageImpl.init();

	/**
	 * The meta object id for the '{@link attribute_to_structure_attr.impl.IdentifiedImpl <em>Identified</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see attribute_to_structure_attr.impl.IdentifiedImpl
	 * @see attribute_to_structure_attr.impl.Attribute_to_structure_attrPackageImpl#getIdentified()
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
	 * The meta object id for the '{@link attribute_to_structure_attr.impl.ModelAImpl <em>Model A</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see attribute_to_structure_attr.impl.ModelAImpl
	 * @see attribute_to_structure_attr.impl.Attribute_to_structure_attrPackageImpl#getModelA()
	 * @generated
	 */
	int MODEL_A = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_A__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Content</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_A__CONTENT = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Model A</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_A_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Model A</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_A_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link attribute_to_structure_attr.impl.AttributedImpl <em>Attributed</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see attribute_to_structure_attr.impl.AttributedImpl
	 * @see attribute_to_structure_attr.impl.Attribute_to_structure_attrPackageImpl#getAttributed()
	 * @generated
	 */
	int ATTRIBUTED = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTED__ID = IDENTIFIED__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTED__NAME = IDENTIFIED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Int Attr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTED__INT_ATTR = IDENTIFIED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Str Attr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTED__STR_ATTR = IDENTIFIED_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Float Attr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTED__FLOAT_ATTR = IDENTIFIED_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Attributed</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTED_FEATURE_COUNT = IDENTIFIED_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Attributed</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTED_OPERATION_COUNT = IDENTIFIED_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link attribute_to_structure_attr.Identified <em>Identified</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identified</em>'.
	 * @see attribute_to_structure_attr.Identified
	 * @generated
	 */
	EClass getIdentified();

	/**
	 * Returns the meta object for the attribute '{@link attribute_to_structure_attr.Identified#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see attribute_to_structure_attr.Identified#getId()
	 * @see #getIdentified()
	 * @generated
	 */
	EAttribute getIdentified_Id();

	/**
	 * Returns the meta object for class '{@link attribute_to_structure_attr.ModelA <em>Model A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model A</em>'.
	 * @see attribute_to_structure_attr.ModelA
	 * @generated
	 */
	EClass getModelA();

	/**
	 * Returns the meta object for the containment reference list '{@link attribute_to_structure_attr.ModelA#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Content</em>'.
	 * @see attribute_to_structure_attr.ModelA#getContent()
	 * @see #getModelA()
	 * @generated
	 */
	EReference getModelA_Content();

	/**
	 * Returns the meta object for class '{@link attribute_to_structure_attr.Attributed <em>Attributed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attributed</em>'.
	 * @see attribute_to_structure_attr.Attributed
	 * @generated
	 */
	EClass getAttributed();

	/**
	 * Returns the meta object for the attribute '{@link attribute_to_structure_attr.Attributed#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see attribute_to_structure_attr.Attributed#getName()
	 * @see #getAttributed()
	 * @generated
	 */
	EAttribute getAttributed_Name();

	/**
	 * Returns the meta object for the attribute '{@link attribute_to_structure_attr.Attributed#getIntAttr <em>Int Attr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Int Attr</em>'.
	 * @see attribute_to_structure_attr.Attributed#getIntAttr()
	 * @see #getAttributed()
	 * @generated
	 */
	EAttribute getAttributed_IntAttr();

	/**
	 * Returns the meta object for the attribute '{@link attribute_to_structure_attr.Attributed#getStrAttr <em>Str Attr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Str Attr</em>'.
	 * @see attribute_to_structure_attr.Attributed#getStrAttr()
	 * @see #getAttributed()
	 * @generated
	 */
	EAttribute getAttributed_StrAttr();

	/**
	 * Returns the meta object for the attribute '{@link attribute_to_structure_attr.Attributed#getFloatAttr <em>Float Attr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Float Attr</em>'.
	 * @see attribute_to_structure_attr.Attributed#getFloatAttr()
	 * @see #getAttributed()
	 * @generated
	 */
	EAttribute getAttributed_FloatAttr();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	Attribute_to_structure_attrFactory getAttribute_to_structure_attrFactory();

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
		 * The meta object literal for the '{@link attribute_to_structure_attr.impl.IdentifiedImpl <em>Identified</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see attribute_to_structure_attr.impl.IdentifiedImpl
		 * @see attribute_to_structure_attr.impl.Attribute_to_structure_attrPackageImpl#getIdentified()
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
		 * The meta object literal for the '{@link attribute_to_structure_attr.impl.ModelAImpl <em>Model A</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see attribute_to_structure_attr.impl.ModelAImpl
		 * @see attribute_to_structure_attr.impl.Attribute_to_structure_attrPackageImpl#getModelA()
		 * @generated
		 */
		EClass MODEL_A = eINSTANCE.getModelA();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_A__CONTENT = eINSTANCE.getModelA_Content();

		/**
		 * The meta object literal for the '{@link attribute_to_structure_attr.impl.AttributedImpl <em>Attributed</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see attribute_to_structure_attr.impl.AttributedImpl
		 * @see attribute_to_structure_attr.impl.Attribute_to_structure_attrPackageImpl#getAttributed()
		 * @generated
		 */
		EClass ATTRIBUTED = eINSTANCE.getAttributed();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTED__NAME = eINSTANCE.getAttributed_Name();

		/**
		 * The meta object literal for the '<em><b>Int Attr</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTED__INT_ATTR = eINSTANCE.getAttributed_IntAttr();

		/**
		 * The meta object literal for the '<em><b>Str Attr</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTED__STR_ATTR = eINSTANCE.getAttributed_StrAttr();

		/**
		 * The meta object literal for the '<em><b>Float Attr</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTED__FLOAT_ATTR = eINSTANCE.getAttributed_FloatAttr();

	}

} //Attribute_to_structure_attrPackage
