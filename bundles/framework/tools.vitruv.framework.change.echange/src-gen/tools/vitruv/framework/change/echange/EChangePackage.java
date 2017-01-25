/**
 */
package tools.vitruv.framework.change.echange;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

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
 * @see tools.vitruv.framework.change.echange.EChangeFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel modelName='Change' prefix='EChange' basePackage='tools.vitruv.framework.change'"
 * @generated
 */
public interface EChangePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "echange";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv/EChange/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "echange";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EChangePackage eINSTANCE = tools.vitruv.framework.change.echange.impl.EChangePackageImpl.init();

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.impl.EChangeImpl <em>EChange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.impl.EChangeImpl
	 * @see tools.vitruv.framework.change.echange.impl.EChangePackageImpl#getEChange()
	 * @generated
	 */
	int ECHANGE = 0;

	/**
	 * The number of structural features of the '<em>EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECHANGE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECHANGE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.impl.AtomicEChangeImpl <em>Atomic EChange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.impl.AtomicEChangeImpl
	 * @see tools.vitruv.framework.change.echange.impl.EChangePackageImpl#getAtomicEChange()
	 * @generated
	 */
	int ATOMIC_ECHANGE = 1;

	/**
	 * The number of structural features of the '<em>Atomic EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_ECHANGE_FEATURE_COUNT = ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Atomic EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_ECHANGE_OPERATION_COUNT = ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.impl.AdditiveEChangeImpl <em>Additive EChange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.impl.AdditiveEChangeImpl
	 * @see tools.vitruv.framework.change.echange.impl.EChangePackageImpl#getAdditiveEChange()
	 * @generated
	 */
	int ADDITIVE_ECHANGE = 2;

	/**
	 * The number of structural features of the '<em>Additive EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_ECHANGE_FEATURE_COUNT = ATOMIC_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get New Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_ECHANGE___GET_NEW_VALUE = ATOMIC_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Additive EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_ECHANGE_OPERATION_COUNT = ATOMIC_ECHANGE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.impl.SubtractiveEChangeImpl <em>Subtractive EChange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.impl.SubtractiveEChangeImpl
	 * @see tools.vitruv.framework.change.echange.impl.EChangePackageImpl#getSubtractiveEChange()
	 * @generated
	 */
	int SUBTRACTIVE_ECHANGE = 3;

	/**
	 * The number of structural features of the '<em>Subtractive EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTRACTIVE_ECHANGE_FEATURE_COUNT = ATOMIC_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Old Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTRACTIVE_ECHANGE___GET_OLD_VALUE = ATOMIC_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Subtractive EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTRACTIVE_ECHANGE_OPERATION_COUNT = ATOMIC_ECHANGE_OPERATION_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.EChange <em>EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.EChange
	 * @generated
	 */
	EClass getEChange();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.AtomicEChange <em>Atomic EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Atomic EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.AtomicEChange
	 * @generated
	 */
	EClass getAtomicEChange();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.AdditiveEChange <em>Additive EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Additive EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.AdditiveEChange
	 * @generated
	 */
	EClass getAdditiveEChange();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.AdditiveEChange#getNewValue() <em>Get New Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get New Value</em>' operation.
	 * @see tools.vitruv.framework.change.echange.AdditiveEChange#getNewValue()
	 * @generated
	 */
	EOperation getAdditiveEChange__GetNewValue();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.SubtractiveEChange <em>Subtractive EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subtractive EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.SubtractiveEChange
	 * @generated
	 */
	EClass getSubtractiveEChange();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.SubtractiveEChange#getOldValue() <em>Get Old Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Old Value</em>' operation.
	 * @see tools.vitruv.framework.change.echange.SubtractiveEChange#getOldValue()
	 * @generated
	 */
	EOperation getSubtractiveEChange__GetOldValue();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EChangeFactory getEChangeFactory();

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
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.impl.EChangeImpl <em>EChange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.impl.EChangeImpl
		 * @see tools.vitruv.framework.change.echange.impl.EChangePackageImpl#getEChange()
		 * @generated
		 */
		EClass ECHANGE = eINSTANCE.getEChange();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.impl.AtomicEChangeImpl <em>Atomic EChange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.impl.AtomicEChangeImpl
		 * @see tools.vitruv.framework.change.echange.impl.EChangePackageImpl#getAtomicEChange()
		 * @generated
		 */
		EClass ATOMIC_ECHANGE = eINSTANCE.getAtomicEChange();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.impl.AdditiveEChangeImpl <em>Additive EChange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.impl.AdditiveEChangeImpl
		 * @see tools.vitruv.framework.change.echange.impl.EChangePackageImpl#getAdditiveEChange()
		 * @generated
		 */
		EClass ADDITIVE_ECHANGE = eINSTANCE.getAdditiveEChange();

		/**
		 * The meta object literal for the '<em><b>Get New Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ADDITIVE_ECHANGE___GET_NEW_VALUE = eINSTANCE.getAdditiveEChange__GetNewValue();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.impl.SubtractiveEChangeImpl <em>Subtractive EChange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.impl.SubtractiveEChangeImpl
		 * @see tools.vitruv.framework.change.echange.impl.EChangePackageImpl#getSubtractiveEChange()
		 * @generated
		 */
		EClass SUBTRACTIVE_ECHANGE = eINSTANCE.getSubtractiveEChange();

		/**
		 * The meta object literal for the '<em><b>Get Old Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SUBTRACTIVE_ECHANGE___GET_OLD_VALUE = eINSTANCE.getSubtractiveEChange__GetOldValue();

	}

} //EChangePackage
