/**
 */
package SimpleAtoBTest_SimplestB;

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
 * @see SimpleAtoBTest_SimplestB.SimpleAtoBTest_SimplestBFactory
 * @model kind="package"
 * @generated
 */
public interface SimpleAtoBTest_SimplestBPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "SimpleAtoBTest_SimplestB";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://edu.kit.ipd.sdq.vitruvius/mir/tests/SimpleAtoB/SimplestB/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "simpleAtoBTest_simplestB";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SimpleAtoBTest_SimplestBPackage eINSTANCE = SimpleAtoBTest_SimplestB.impl.SimpleAtoBTest_SimplestBPackageImpl.init();

	/**
	 * The meta object id for the '{@link SimpleAtoBTest_SimplestB.impl.BImpl <em>B</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SimpleAtoBTest_SimplestB.impl.BImpl
	 * @see SimpleAtoBTest_SimplestB.impl.SimpleAtoBTest_SimplestBPackageImpl#getB()
	 * @generated
	 */
	int B = 0;

	/**
	 * The feature id for the '<em><b>Bname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int B__BNAME = 0;

	/**
	 * The feature id for the '<em><b>Bchildren</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int B__BCHILDREN = 1;

	/**
	 * The number of structural features of the '<em>B</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int B_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>B</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int B_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link SimpleAtoBTest_SimplestB.impl.BChildImpl <em>BChild</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SimpleAtoBTest_SimplestB.impl.BChildImpl
	 * @see SimpleAtoBTest_SimplestB.impl.SimpleAtoBTest_SimplestBPackageImpl#getBChild()
	 * @generated
	 */
	int BCHILD = 1;

	/**
	 * The feature id for the '<em><b>BChild name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCHILD__BCHILD_NAME = 0;

	/**
	 * The number of structural features of the '<em>BChild</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCHILD_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>BChild</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BCHILD_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link SimpleAtoBTest_SimplestB.B <em>B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>B</em>'.
	 * @see SimpleAtoBTest_SimplestB.B
	 * @generated
	 */
	EClass getB();

	/**
	 * Returns the meta object for the attribute '{@link SimpleAtoBTest_SimplestB.B#getB_name <em>Bname</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bname</em>'.
	 * @see SimpleAtoBTest_SimplestB.B#getB_name()
	 * @see #getB()
	 * @generated
	 */
	EAttribute getB_B_name();

	/**
	 * Returns the meta object for the reference list '{@link SimpleAtoBTest_SimplestB.B#getB_children <em>Bchildren</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Bchildren</em>'.
	 * @see SimpleAtoBTest_SimplestB.B#getB_children()
	 * @see #getB()
	 * @generated
	 */
	EReference getB_B_children();

	/**
	 * Returns the meta object for class '{@link SimpleAtoBTest_SimplestB.BChild <em>BChild</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>BChild</em>'.
	 * @see SimpleAtoBTest_SimplestB.BChild
	 * @generated
	 */
	EClass getBChild();

	/**
	 * Returns the meta object for the attribute '{@link SimpleAtoBTest_SimplestB.BChild#getBChild_name <em>BChild name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>BChild name</em>'.
	 * @see SimpleAtoBTest_SimplestB.BChild#getBChild_name()
	 * @see #getBChild()
	 * @generated
	 */
	EAttribute getBChild_BChild_name();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SimpleAtoBTest_SimplestBFactory getSimpleAtoBTest_SimplestBFactory();

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
		 * The meta object literal for the '{@link SimpleAtoBTest_SimplestB.impl.BImpl <em>B</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SimpleAtoBTest_SimplestB.impl.BImpl
		 * @see SimpleAtoBTest_SimplestB.impl.SimpleAtoBTest_SimplestBPackageImpl#getB()
		 * @generated
		 */
		EClass B = eINSTANCE.getB();

		/**
		 * The meta object literal for the '<em><b>Bname</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute B__BNAME = eINSTANCE.getB_B_name();

		/**
		 * The meta object literal for the '<em><b>Bchildren</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference B__BCHILDREN = eINSTANCE.getB_B_children();

		/**
		 * The meta object literal for the '{@link SimpleAtoBTest_SimplestB.impl.BChildImpl <em>BChild</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SimpleAtoBTest_SimplestB.impl.BChildImpl
		 * @see SimpleAtoBTest_SimplestB.impl.SimpleAtoBTest_SimplestBPackageImpl#getBChild()
		 * @generated
		 */
		EClass BCHILD = eINSTANCE.getBChild();

		/**
		 * The meta object literal for the '<em><b>BChild name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BCHILD__BCHILD_NAME = eINSTANCE.getBChild_BChild_name();

	}

} //SimpleAtoBTest_SimplestBPackage
