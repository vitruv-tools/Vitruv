/**
 */
package tools.vitruv.framework.change.echange;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECHANGE___IS_RESOLVED = 0;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECHANGE___RESOLVE_BEFORE__OBJECT = 1;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECHANGE___RESOLVE_AFTER__OBJECT = 2;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__OBJECT = 3;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__OBJECT = 4;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECHANGE___APPLY_FORWARD = 5;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECHANGE___APPLY_BACKWARD = 6;

	/**
	 * The number of operations of the '<em>EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECHANGE_OPERATION_COUNT = 7;

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
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_ECHANGE___IS_RESOLVED = ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_ECHANGE___RESOLVE_BEFORE__RESOURCESET = ECHANGE___RESOLVE_BEFORE__OBJECT;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_ECHANGE___RESOLVE_AFTER__RESOURCESET = ECHANGE___RESOLVE_AFTER__OBJECT;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__OBJECT;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__OBJECT;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_ECHANGE___APPLY_FORWARD = ECHANGE___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_ECHANGE___APPLY_BACKWARD = ECHANGE___APPLY_BACKWARD;

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
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_ECHANGE___IS_RESOLVED = ATOMIC_ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_ECHANGE___RESOLVE_BEFORE__RESOURCESET = ATOMIC_ECHANGE___RESOLVE_BEFORE__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_ECHANGE___RESOLVE_AFTER__RESOURCESET = ATOMIC_ECHANGE___RESOLVE_AFTER__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = ATOMIC_ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = ATOMIC_ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_ECHANGE___APPLY_FORWARD = ATOMIC_ECHANGE___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_ECHANGE___APPLY_BACKWARD = ATOMIC_ECHANGE___APPLY_BACKWARD;

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
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTRACTIVE_ECHANGE___IS_RESOLVED = ATOMIC_ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTRACTIVE_ECHANGE___RESOLVE_BEFORE__RESOURCESET = ATOMIC_ECHANGE___RESOLVE_BEFORE__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTRACTIVE_ECHANGE___RESOLVE_AFTER__RESOURCESET = ATOMIC_ECHANGE___RESOLVE_AFTER__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTRACTIVE_ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = ATOMIC_ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTRACTIVE_ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = ATOMIC_ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTRACTIVE_ECHANGE___APPLY_FORWARD = ATOMIC_ECHANGE___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBTRACTIVE_ECHANGE___APPLY_BACKWARD = ATOMIC_ECHANGE___APPLY_BACKWARD;

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
	 * The meta object id for the '<em>Command</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.command.Command
	 * @see tools.vitruv.framework.change.echange.impl.EChangePackageImpl#getCommand()
	 * @generated
	 */
	int COMMAND = 4;

	/**
	 * The meta object id for the '<em>Resource Set</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.resource.ResourceSet
	 * @see tools.vitruv.framework.change.echange.impl.EChangePackageImpl#getResourceSet()
	 * @generated
	 */
	int RESOURCE_SET = 5;


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
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.EChange#isResolved() <em>Is Resolved</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Resolved</em>' operation.
	 * @see tools.vitruv.framework.change.echange.EChange#isResolved()
	 * @generated
	 */
	EOperation getEChange__IsResolved();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.EChange#resolveBefore(org.eclipse.emf.ecore.resource.ResourceSet) <em>Resolve Before</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Resolve Before</em>' operation.
	 * @see tools.vitruv.framework.change.echange.EChange#resolveBefore(org.eclipse.emf.ecore.resource.ResourceSet)
	 * @generated
	 */
	EOperation getEChange__ResolveBefore__Object();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.EChange#resolveAfter(org.eclipse.emf.ecore.resource.ResourceSet) <em>Resolve After</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Resolve After</em>' operation.
	 * @see tools.vitruv.framework.change.echange.EChange#resolveAfter(org.eclipse.emf.ecore.resource.ResourceSet)
	 * @generated
	 */
	EOperation getEChange__ResolveAfter__Object();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.EChange#resolveBeforeAndApplyForward(org.eclipse.emf.ecore.resource.ResourceSet) <em>Resolve Before And Apply Forward</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * @see tools.vitruv.framework.change.echange.EChange#resolveBeforeAndApplyForward(org.eclipse.emf.ecore.resource.ResourceSet)
	 * @generated
	 */
	EOperation getEChange__ResolveBeforeAndApplyForward__Object();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.EChange#resolveAfterAndApplyBackward(org.eclipse.emf.ecore.resource.ResourceSet) <em>Resolve After And Apply Backward</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Resolve After And Apply Backward</em>' operation.
	 * @see tools.vitruv.framework.change.echange.EChange#resolveAfterAndApplyBackward(org.eclipse.emf.ecore.resource.ResourceSet)
	 * @generated
	 */
	EOperation getEChange__ResolveAfterAndApplyBackward__Object();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.EChange#applyForward() <em>Apply Forward</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Apply Forward</em>' operation.
	 * @see tools.vitruv.framework.change.echange.EChange#applyForward()
	 * @generated
	 */
	EOperation getEChange__ApplyForward();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.EChange#applyBackward() <em>Apply Backward</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Apply Backward</em>' operation.
	 * @see tools.vitruv.framework.change.echange.EChange#applyBackward()
	 * @generated
	 */
	EOperation getEChange__ApplyBackward();

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
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.command.Command <em>Command</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Command</em>'.
	 * @see org.eclipse.emf.common.command.Command
	 * @model instanceClass="org.eclipse.emf.common.command.Command"
	 * @generated
	 */
	EDataType getCommand();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.ecore.resource.ResourceSet <em>Resource Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Resource Set</em>'.
	 * @see org.eclipse.emf.ecore.resource.ResourceSet
	 * @model instanceClass="org.eclipse.emf.ecore.resource.ResourceSet"
	 * @generated
	 */
	EDataType getResourceSet();

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
		 * The meta object literal for the '<em><b>Is Resolved</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ECHANGE___IS_RESOLVED = eINSTANCE.getEChange__IsResolved();

		/**
		 * The meta object literal for the '<em><b>Resolve Before</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ECHANGE___RESOLVE_BEFORE__OBJECT = eINSTANCE.getEChange__ResolveBefore__Object();

		/**
		 * The meta object literal for the '<em><b>Resolve After</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ECHANGE___RESOLVE_AFTER__OBJECT = eINSTANCE.getEChange__ResolveAfter__Object();

		/**
		 * The meta object literal for the '<em><b>Resolve Before And Apply Forward</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__OBJECT = eINSTANCE.getEChange__ResolveBeforeAndApplyForward__Object();

		/**
		 * The meta object literal for the '<em><b>Resolve After And Apply Backward</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__OBJECT = eINSTANCE.getEChange__ResolveAfterAndApplyBackward__Object();

		/**
		 * The meta object literal for the '<em><b>Apply Forward</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ECHANGE___APPLY_FORWARD = eINSTANCE.getEChange__ApplyForward();

		/**
		 * The meta object literal for the '<em><b>Apply Backward</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ECHANGE___APPLY_BACKWARD = eINSTANCE.getEChange__ApplyBackward();

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

		/**
		 * The meta object literal for the '<em>Command</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.command.Command
		 * @see tools.vitruv.framework.change.echange.impl.EChangePackageImpl#getCommand()
		 * @generated
		 */
		EDataType COMMAND = eINSTANCE.getCommand();

		/**
		 * The meta object literal for the '<em>Resource Set</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecore.resource.ResourceSet
		 * @see tools.vitruv.framework.change.echange.impl.EChangePackageImpl#getResourceSet()
		 * @generated
		 */
		EDataType RESOURCE_SET = eINSTANCE.getResourceSet();

	}

} //EChangePackage
