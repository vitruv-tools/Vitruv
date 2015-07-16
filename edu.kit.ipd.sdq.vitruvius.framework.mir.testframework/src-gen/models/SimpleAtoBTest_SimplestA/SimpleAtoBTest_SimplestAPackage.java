/**
 */
package SimpleAtoBTest_SimplestA;

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
 * @see SimpleAtoBTest_SimplestA.SimpleAtoBTest_SimplestAFactory
 * @model kind="package"
 * @generated
 */
public interface SimpleAtoBTest_SimplestAPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "SimpleAtoBTest_SimplestA";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://edu.kit.ipd.sdq.vitruvius/mir/tests/SimpleAtoB/SimplestA/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "simpleAtoBTest_simplestA";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SimpleAtoBTest_SimplestAPackage eINSTANCE = SimpleAtoBTest_SimplestA.impl.SimpleAtoBTest_SimplestAPackageImpl.init();

	/**
	 * The meta object id for the '{@link SimpleAtoBTest_SimplestA.impl.AImpl <em>A</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SimpleAtoBTest_SimplestA.impl.AImpl
	 * @see SimpleAtoBTest_SimplestA.impl.SimpleAtoBTest_SimplestAPackageImpl#getA()
	 * @generated
	 */
	int A = 0;

	/**
	 * The feature id for the '<em><b>Aname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int A__ANAME = 0;

	/**
	 * The feature id for the '<em><b>Achildren</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int A__ACHILDREN = 1;

	/**
	 * The number of structural features of the '<em>A</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int A_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>A</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int A_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link SimpleAtoBTest_SimplestA.impl.AChildImpl <em>AChild</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SimpleAtoBTest_SimplestA.impl.AChildImpl
	 * @see SimpleAtoBTest_SimplestA.impl.SimpleAtoBTest_SimplestAPackageImpl#getAChild()
	 * @generated
	 */
	int ACHILD = 1;

	/**
	 * The feature id for the '<em><b>AChild name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACHILD__ACHILD_NAME = 0;

	/**
	 * The number of structural features of the '<em>AChild</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACHILD_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>AChild</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACHILD_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link SimpleAtoBTest_SimplestA.A <em>A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>A</em>'.
	 * @see SimpleAtoBTest_SimplestA.A
	 * @generated
	 */
	EClass getA();

	/**
	 * Returns the meta object for the attribute '{@link SimpleAtoBTest_SimplestA.A#getA_name <em>Aname</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Aname</em>'.
	 * @see SimpleAtoBTest_SimplestA.A#getA_name()
	 * @see #getA()
	 * @generated
	 */
	EAttribute getA_A_name();

	/**
	 * Returns the meta object for the containment reference list '{@link SimpleAtoBTest_SimplestA.A#getA_children <em>Achildren</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Achildren</em>'.
	 * @see SimpleAtoBTest_SimplestA.A#getA_children()
	 * @see #getA()
	 * @generated
	 */
	EReference getA_A_children();

	/**
	 * Returns the meta object for class '{@link SimpleAtoBTest_SimplestA.AChild <em>AChild</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>AChild</em>'.
	 * @see SimpleAtoBTest_SimplestA.AChild
	 * @generated
	 */
	EClass getAChild();

	/**
	 * Returns the meta object for the attribute '{@link SimpleAtoBTest_SimplestA.AChild#getAChild_name <em>AChild name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>AChild name</em>'.
	 * @see SimpleAtoBTest_SimplestA.AChild#getAChild_name()
	 * @see #getAChild()
	 * @generated
	 */
	EAttribute getAChild_AChild_name();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SimpleAtoBTest_SimplestAFactory getSimpleAtoBTest_SimplestAFactory();

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
		 * The meta object literal for the '{@link SimpleAtoBTest_SimplestA.impl.AImpl <em>A</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SimpleAtoBTest_SimplestA.impl.AImpl
		 * @see SimpleAtoBTest_SimplestA.impl.SimpleAtoBTest_SimplestAPackageImpl#getA()
		 * @generated
		 */
		EClass A = eINSTANCE.getA();

		/**
		 * The meta object literal for the '<em><b>Aname</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute A__ANAME = eINSTANCE.getA_A_name();

		/**
		 * The meta object literal for the '<em><b>Achildren</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference A__ACHILDREN = eINSTANCE.getA_A_children();

		/**
		 * The meta object literal for the '{@link SimpleAtoBTest_SimplestA.impl.AChildImpl <em>AChild</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SimpleAtoBTest_SimplestA.impl.AChildImpl
		 * @see SimpleAtoBTest_SimplestA.impl.SimpleAtoBTest_SimplestAPackageImpl#getAChild()
		 * @generated
		 */
		EClass ACHILD = eINSTANCE.getAChild();

		/**
		 * The meta object literal for the '<em><b>AChild name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACHILD__ACHILD_NAME = eINSTANCE.getAChild_AChild_name();

	}

} //SimpleAtoBTest_SimplestAPackage
