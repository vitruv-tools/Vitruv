/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import tools.vitruv.framework.change.echange.EChangePackage;

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
 * @see tools.vitruv.framework.change.echange.compound.CompoundFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel modelName='Change' basePackage='tools.vitruv.framework.change.echange'"
 * @generated
 */
public interface CompoundPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "compound";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv/EChange/Compound/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "compound";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CompoundPackage eINSTANCE = tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl.init();

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.CompoundEChangeImpl <em>EChange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundEChangeImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCompoundEChange()
	 * @generated
	 */
	int COMPOUND_ECHANGE = 0;

	/**
	 * The number of structural features of the '<em>EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ECHANGE_FEATURE_COUNT = EChangePackage.ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ECHANGE___RESOLVE_BEFORE__RESOURCESET = EChangePackage.ECHANGE___RESOLVE_BEFORE__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ECHANGE___RESOLVE_AFTER__RESOURCESET = EChangePackage.ECHANGE___RESOLVE_AFTER__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ECHANGE___APPLY_FORWARD = EChangePackage.ECHANGE___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ECHANGE___APPLY_BACKWARD = EChangePackage.ECHANGE___APPLY_BACKWARD;

	/**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ECHANGE___GET_ATOMIC_CHANGES = EChangePackage.ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ECHANGE___IS_RESOLVED = EChangePackage.ECHANGE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = EChangePackage.ECHANGE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = EChangePackage.ECHANGE_OPERATION_COUNT + 3;

	/**
	 * The number of operations of the '<em>EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ECHANGE_OPERATION_COUNT = EChangePackage.ECHANGE_OPERATION_COUNT + 4;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.MoveEObjectImpl <em>Move EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.MoveEObjectImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getMoveEObject()
	 * @generated
	 */
	int MOVE_EOBJECT = 1;

	/**
	 * The feature id for the '<em><b>Subtract Where Change</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_EOBJECT__SUBTRACT_WHERE_CHANGE = COMPOUND_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Subtract What Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE = COMPOUND_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Add Where Change</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_EOBJECT__ADD_WHERE_CHANGE = COMPOUND_ECHANGE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Add What Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_EOBJECT__ADD_WHAT_CHANGE = COMPOUND_ECHANGE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Move EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_EOBJECT_FEATURE_COUNT = COMPOUND_ECHANGE_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_EOBJECT___RESOLVE_BEFORE__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_BEFORE__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_EOBJECT___RESOLVE_AFTER__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_AFTER__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_EOBJECT___APPLY_FORWARD = COMPOUND_ECHANGE___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_EOBJECT___APPLY_BACKWARD = COMPOUND_ECHANGE___APPLY_BACKWARD;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_EOBJECT___IS_RESOLVED = COMPOUND_ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_EOBJECT___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_EOBJECT___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_EOBJECT___GET_ATOMIC_CHANGES = COMPOUND_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Move EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_EOBJECT_OPERATION_COUNT = COMPOUND_ECHANGE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEFeatureImpl <em>Explicit Unset EFeature</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEFeatureImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getExplicitUnsetEFeature()
	 * @generated
	 */
	int EXPLICIT_UNSET_EFEATURE = 2;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EFEATURE__AFFECTED_EOBJECT = COMPOUND_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EFEATURE__AFFECTED_FEATURE = COMPOUND_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Explicit Unset EFeature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EFEATURE_FEATURE_COUNT = COMPOUND_ECHANGE_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EFEATURE___RESOLVE_BEFORE__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_BEFORE__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EFEATURE___RESOLVE_AFTER__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_AFTER__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EFEATURE___APPLY_FORWARD = COMPOUND_ECHANGE___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EFEATURE___APPLY_BACKWARD = COMPOUND_ECHANGE___APPLY_BACKWARD;

	/**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EFEATURE___GET_ATOMIC_CHANGES = COMPOUND_ECHANGE___GET_ATOMIC_CHANGES;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EFEATURE___IS_RESOLVED = COMPOUND_ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EFEATURE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EFEATURE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET;

	/**
	 * The number of operations of the '<em>Explicit Unset EFeature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EFEATURE_OPERATION_COUNT = COMPOUND_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.CompoundSubtractionImpl <em>Subtraction</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundSubtractionImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCompoundSubtraction()
	 * @generated
	 */
	int COMPOUND_SUBTRACTION = 5;

	/**
	 * The feature id for the '<em><b>Subtractive Changes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_SUBTRACTION__SUBTRACTIVE_CHANGES = COMPOUND_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Subtraction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_SUBTRACTION_FEATURE_COUNT = COMPOUND_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_SUBTRACTION___RESOLVE_BEFORE__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_BEFORE__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_SUBTRACTION___RESOLVE_AFTER__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_AFTER__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_SUBTRACTION___APPLY_FORWARD = COMPOUND_ECHANGE___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_SUBTRACTION___APPLY_BACKWARD = COMPOUND_ECHANGE___APPLY_BACKWARD;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_SUBTRACTION___IS_RESOLVED = COMPOUND_ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_SUBTRACTION___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_SUBTRACTION___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_SUBTRACTION___GET_ATOMIC_CHANGES = COMPOUND_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Subtraction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_SUBTRACTION_OPERATION_COUNT = COMPOUND_ECHANGE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEAttributeImpl <em>Explicit Unset EAttribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEAttributeImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getExplicitUnsetEAttribute()
	 * @generated
	 */
	int EXPLICIT_UNSET_EATTRIBUTE = 3;

	/**
	 * The feature id for the '<em><b>Subtractive Changes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EATTRIBUTE__SUBTRACTIVE_CHANGES = COMPOUND_SUBTRACTION__SUBTRACTIVE_CHANGES;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EATTRIBUTE__AFFECTED_EOBJECT = COMPOUND_SUBTRACTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EATTRIBUTE__AFFECTED_FEATURE = COMPOUND_SUBTRACTION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Explicit Unset EAttribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EATTRIBUTE_FEATURE_COUNT = COMPOUND_SUBTRACTION_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EATTRIBUTE___RESOLVE_BEFORE__RESOURCESET = COMPOUND_SUBTRACTION___RESOLVE_BEFORE__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EATTRIBUTE___RESOLVE_AFTER__RESOURCESET = COMPOUND_SUBTRACTION___RESOLVE_AFTER__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EATTRIBUTE___APPLY_FORWARD = COMPOUND_SUBTRACTION___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EATTRIBUTE___APPLY_BACKWARD = COMPOUND_SUBTRACTION___APPLY_BACKWARD;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EATTRIBUTE___IS_RESOLVED = COMPOUND_SUBTRACTION___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EATTRIBUTE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = COMPOUND_SUBTRACTION___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EATTRIBUTE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = COMPOUND_SUBTRACTION___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EATTRIBUTE___GET_ATOMIC_CHANGES = COMPOUND_SUBTRACTION_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Explicit Unset EAttribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EATTRIBUTE_OPERATION_COUNT = COMPOUND_SUBTRACTION_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEReferenceImpl <em>Explicit Unset EReference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEReferenceImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getExplicitUnsetEReference()
	 * @generated
	 */
	int EXPLICIT_UNSET_EREFERENCE = 4;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EREFERENCE__AFFECTED_EOBJECT = EXPLICIT_UNSET_EFEATURE__AFFECTED_EOBJECT;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EREFERENCE__AFFECTED_FEATURE = EXPLICIT_UNSET_EFEATURE__AFFECTED_FEATURE;

	/**
	 * The feature id for the '<em><b>Changes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EREFERENCE__CHANGES = EXPLICIT_UNSET_EFEATURE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Explicit Unset EReference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EREFERENCE_FEATURE_COUNT = EXPLICIT_UNSET_EFEATURE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EREFERENCE___RESOLVE_BEFORE__RESOURCESET = EXPLICIT_UNSET_EFEATURE___RESOLVE_BEFORE__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EREFERENCE___RESOLVE_AFTER__RESOURCESET = EXPLICIT_UNSET_EFEATURE___RESOLVE_AFTER__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EREFERENCE___APPLY_FORWARD = EXPLICIT_UNSET_EFEATURE___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EREFERENCE___APPLY_BACKWARD = EXPLICIT_UNSET_EFEATURE___APPLY_BACKWARD;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EREFERENCE___IS_RESOLVED = EXPLICIT_UNSET_EFEATURE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EREFERENCE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = EXPLICIT_UNSET_EFEATURE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EREFERENCE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = EXPLICIT_UNSET_EFEATURE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Get Contained Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EREFERENCE___GET_CONTAINED_CHANGES = EXPLICIT_UNSET_EFEATURE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EREFERENCE___GET_ATOMIC_CHANGES = EXPLICIT_UNSET_EFEATURE_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Explicit Unset EReference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EREFERENCE_OPERATION_COUNT = EXPLICIT_UNSET_EFEATURE_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.CompoundAdditionImpl <em>Addition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundAdditionImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCompoundAddition()
	 * @generated
	 */
	int COMPOUND_ADDITION = 6;

	/**
	 * The feature id for the '<em><b>Additive Changes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ADDITION__ADDITIVE_CHANGES = COMPOUND_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Addition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ADDITION_FEATURE_COUNT = COMPOUND_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ADDITION___RESOLVE_BEFORE__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_BEFORE__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ADDITION___RESOLVE_AFTER__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_AFTER__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ADDITION___APPLY_FORWARD = COMPOUND_ECHANGE___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ADDITION___APPLY_BACKWARD = COMPOUND_ECHANGE___APPLY_BACKWARD;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ADDITION___IS_RESOLVED = COMPOUND_ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ADDITION___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ADDITION___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ADDITION___GET_ATOMIC_CHANGES = COMPOUND_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Addition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ADDITION_OPERATION_COUNT = COMPOUND_ECHANGE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertEObjectImpl <em>Create And Insert EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertEObjectImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCreateAndInsertEObject()
	 * @generated
	 */
	int CREATE_AND_INSERT_EOBJECT = 7;

	/**
	 * The feature id for the '<em><b>Create Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_EOBJECT__CREATE_CHANGE = COMPOUND_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Insert Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_EOBJECT__INSERT_CHANGE = COMPOUND_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Create And Insert EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_EOBJECT_FEATURE_COUNT = COMPOUND_ECHANGE_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_EOBJECT___RESOLVE_BEFORE__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_BEFORE__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_EOBJECT___RESOLVE_AFTER__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_AFTER__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_EOBJECT___APPLY_FORWARD = COMPOUND_ECHANGE___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_EOBJECT___APPLY_BACKWARD = COMPOUND_ECHANGE___APPLY_BACKWARD;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_EOBJECT___IS_RESOLVED = COMPOUND_ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_EOBJECT___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_EOBJECT___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_EOBJECT___GET_ATOMIC_CHANGES = COMPOUND_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Create And Insert EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_EOBJECT_OPERATION_COUNT = COMPOUND_ECHANGE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.RemoveAndDeleteEObjectImpl <em>Remove And Delete EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.RemoveAndDeleteEObjectImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getRemoveAndDeleteEObject()
	 * @generated
	 */
	int REMOVE_AND_DELETE_EOBJECT = 8;

	/**
	 * The feature id for the '<em><b>Remove Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_EOBJECT__REMOVE_CHANGE = COMPOUND_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Delete Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_EOBJECT__DELETE_CHANGE = COMPOUND_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Remove And Delete EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_EOBJECT_FEATURE_COUNT = COMPOUND_ECHANGE_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_EOBJECT___RESOLVE_BEFORE__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_BEFORE__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_EOBJECT___RESOLVE_AFTER__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_AFTER__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_EOBJECT___APPLY_FORWARD = COMPOUND_ECHANGE___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_EOBJECT___APPLY_BACKWARD = COMPOUND_ECHANGE___APPLY_BACKWARD;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_EOBJECT___IS_RESOLVED = COMPOUND_ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_EOBJECT___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_EOBJECT___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_EOBJECT___GET_ATOMIC_CHANGES = COMPOUND_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Remove And Delete EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_EOBJECT_OPERATION_COUNT = COMPOUND_ECHANGE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertRootImpl <em>Create And Insert Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertRootImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCreateAndInsertRoot()
	 * @generated
	 */
	int CREATE_AND_INSERT_ROOT = 9;

	/**
	 * The feature id for the '<em><b>Create Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_ROOT__CREATE_CHANGE = CREATE_AND_INSERT_EOBJECT__CREATE_CHANGE;

	/**
	 * The feature id for the '<em><b>Insert Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_ROOT__INSERT_CHANGE = CREATE_AND_INSERT_EOBJECT__INSERT_CHANGE;

	/**
	 * The number of structural features of the '<em>Create And Insert Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_ROOT_FEATURE_COUNT = CREATE_AND_INSERT_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_ROOT___RESOLVE_BEFORE__RESOURCESET = CREATE_AND_INSERT_EOBJECT___RESOLVE_BEFORE__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_ROOT___RESOLVE_AFTER__RESOURCESET = CREATE_AND_INSERT_EOBJECT___RESOLVE_AFTER__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_ROOT___APPLY_FORWARD = CREATE_AND_INSERT_EOBJECT___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_ROOT___APPLY_BACKWARD = CREATE_AND_INSERT_EOBJECT___APPLY_BACKWARD;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_ROOT___IS_RESOLVED = CREATE_AND_INSERT_EOBJECT___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_ROOT___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = CREATE_AND_INSERT_EOBJECT___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_ROOT___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = CREATE_AND_INSERT_EOBJECT___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_ROOT___GET_ATOMIC_CHANGES = CREATE_AND_INSERT_EOBJECT___GET_ATOMIC_CHANGES;

	/**
	 * The number of operations of the '<em>Create And Insert Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_ROOT_OPERATION_COUNT = CREATE_AND_INSERT_EOBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.RemoveAndDeleteRootImpl <em>Remove And Delete Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.RemoveAndDeleteRootImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getRemoveAndDeleteRoot()
	 * @generated
	 */
	int REMOVE_AND_DELETE_ROOT = 10;

	/**
	 * The feature id for the '<em><b>Remove Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_ROOT__REMOVE_CHANGE = REMOVE_AND_DELETE_EOBJECT__REMOVE_CHANGE;

	/**
	 * The feature id for the '<em><b>Delete Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_ROOT__DELETE_CHANGE = REMOVE_AND_DELETE_EOBJECT__DELETE_CHANGE;

	/**
	 * The number of structural features of the '<em>Remove And Delete Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_ROOT_FEATURE_COUNT = REMOVE_AND_DELETE_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_ROOT___RESOLVE_BEFORE__RESOURCESET = REMOVE_AND_DELETE_EOBJECT___RESOLVE_BEFORE__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_ROOT___RESOLVE_AFTER__RESOURCESET = REMOVE_AND_DELETE_EOBJECT___RESOLVE_AFTER__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_ROOT___APPLY_FORWARD = REMOVE_AND_DELETE_EOBJECT___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_ROOT___APPLY_BACKWARD = REMOVE_AND_DELETE_EOBJECT___APPLY_BACKWARD;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_ROOT___IS_RESOLVED = REMOVE_AND_DELETE_EOBJECT___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_ROOT___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = REMOVE_AND_DELETE_EOBJECT___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_ROOT___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = REMOVE_AND_DELETE_EOBJECT___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_ROOT___GET_ATOMIC_CHANGES = REMOVE_AND_DELETE_EOBJECT___GET_ATOMIC_CHANGES;

	/**
	 * The number of operations of the '<em>Remove And Delete Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_ROOT_OPERATION_COUNT = REMOVE_AND_DELETE_EOBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertNonRootImpl <em>Create And Insert Non Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertNonRootImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCreateAndInsertNonRoot()
	 * @generated
	 */
	int CREATE_AND_INSERT_NON_ROOT = 11;

	/**
	 * The feature id for the '<em><b>Create Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_NON_ROOT__CREATE_CHANGE = CREATE_AND_INSERT_EOBJECT__CREATE_CHANGE;

	/**
	 * The feature id for the '<em><b>Insert Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_NON_ROOT__INSERT_CHANGE = CREATE_AND_INSERT_EOBJECT__INSERT_CHANGE;

	/**
	 * The number of structural features of the '<em>Create And Insert Non Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_NON_ROOT_FEATURE_COUNT = CREATE_AND_INSERT_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_NON_ROOT___RESOLVE_BEFORE__RESOURCESET = CREATE_AND_INSERT_EOBJECT___RESOLVE_BEFORE__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_NON_ROOT___RESOLVE_AFTER__RESOURCESET = CREATE_AND_INSERT_EOBJECT___RESOLVE_AFTER__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_NON_ROOT___APPLY_FORWARD = CREATE_AND_INSERT_EOBJECT___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_NON_ROOT___APPLY_BACKWARD = CREATE_AND_INSERT_EOBJECT___APPLY_BACKWARD;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_NON_ROOT___IS_RESOLVED = CREATE_AND_INSERT_EOBJECT___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_NON_ROOT___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = CREATE_AND_INSERT_EOBJECT___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_NON_ROOT___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = CREATE_AND_INSERT_EOBJECT___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_NON_ROOT___GET_ATOMIC_CHANGES = CREATE_AND_INSERT_EOBJECT___GET_ATOMIC_CHANGES;

	/**
	 * The number of operations of the '<em>Create And Insert Non Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_INSERT_NON_ROOT_OPERATION_COUNT = CREATE_AND_INSERT_EOBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.RemoveAndDeleteNonRootImpl <em>Remove And Delete Non Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.RemoveAndDeleteNonRootImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getRemoveAndDeleteNonRoot()
	 * @generated
	 */
	int REMOVE_AND_DELETE_NON_ROOT = 12;

	/**
	 * The feature id for the '<em><b>Remove Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_NON_ROOT__REMOVE_CHANGE = REMOVE_AND_DELETE_EOBJECT__REMOVE_CHANGE;

	/**
	 * The feature id for the '<em><b>Delete Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_NON_ROOT__DELETE_CHANGE = REMOVE_AND_DELETE_EOBJECT__DELETE_CHANGE;

	/**
	 * The number of structural features of the '<em>Remove And Delete Non Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_NON_ROOT_FEATURE_COUNT = REMOVE_AND_DELETE_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_NON_ROOT___RESOLVE_BEFORE__RESOURCESET = REMOVE_AND_DELETE_EOBJECT___RESOLVE_BEFORE__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_NON_ROOT___RESOLVE_AFTER__RESOURCESET = REMOVE_AND_DELETE_EOBJECT___RESOLVE_AFTER__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_NON_ROOT___APPLY_FORWARD = REMOVE_AND_DELETE_EOBJECT___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_NON_ROOT___APPLY_BACKWARD = REMOVE_AND_DELETE_EOBJECT___APPLY_BACKWARD;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_NON_ROOT___IS_RESOLVED = REMOVE_AND_DELETE_EOBJECT___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_NON_ROOT___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = REMOVE_AND_DELETE_EOBJECT___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_NON_ROOT___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = REMOVE_AND_DELETE_EOBJECT___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_NON_ROOT___GET_ATOMIC_CHANGES = REMOVE_AND_DELETE_EOBJECT___GET_ATOMIC_CHANGES;

	/**
	 * The number of operations of the '<em>Remove And Delete Non Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_AND_DELETE_NON_ROOT_OPERATION_COUNT = REMOVE_AND_DELETE_EOBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.CreateAndReplaceNonRootImpl <em>Create And Replace Non Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.CreateAndReplaceNonRootImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCreateAndReplaceNonRoot()
	 * @generated
	 */
	int CREATE_AND_REPLACE_NON_ROOT = 13;

	/**
	 * The feature id for the '<em><b>Create Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_NON_ROOT__CREATE_CHANGE = CREATE_AND_INSERT_EOBJECT__CREATE_CHANGE;

	/**
	 * The feature id for the '<em><b>Insert Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_NON_ROOT__INSERT_CHANGE = CREATE_AND_INSERT_EOBJECT__INSERT_CHANGE;

	/**
	 * The number of structural features of the '<em>Create And Replace Non Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_NON_ROOT_FEATURE_COUNT = CREATE_AND_INSERT_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_NON_ROOT___RESOLVE_BEFORE__RESOURCESET = CREATE_AND_INSERT_EOBJECT___RESOLVE_BEFORE__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_NON_ROOT___RESOLVE_AFTER__RESOURCESET = CREATE_AND_INSERT_EOBJECT___RESOLVE_AFTER__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_NON_ROOT___APPLY_FORWARD = CREATE_AND_INSERT_EOBJECT___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_NON_ROOT___APPLY_BACKWARD = CREATE_AND_INSERT_EOBJECT___APPLY_BACKWARD;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_NON_ROOT___IS_RESOLVED = CREATE_AND_INSERT_EOBJECT___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_NON_ROOT___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = CREATE_AND_INSERT_EOBJECT___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_NON_ROOT___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = CREATE_AND_INSERT_EOBJECT___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_NON_ROOT___GET_ATOMIC_CHANGES = CREATE_AND_INSERT_EOBJECT___GET_ATOMIC_CHANGES;

	/**
	 * The number of operations of the '<em>Create And Replace Non Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_NON_ROOT_OPERATION_COUNT = CREATE_AND_INSERT_EOBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.ReplaceAndDeleteNonRootImpl <em>Replace And Delete Non Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.ReplaceAndDeleteNonRootImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getReplaceAndDeleteNonRoot()
	 * @generated
	 */
	int REPLACE_AND_DELETE_NON_ROOT = 14;

	/**
	 * The feature id for the '<em><b>Remove Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_AND_DELETE_NON_ROOT__REMOVE_CHANGE = REMOVE_AND_DELETE_EOBJECT__REMOVE_CHANGE;

	/**
	 * The feature id for the '<em><b>Delete Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_AND_DELETE_NON_ROOT__DELETE_CHANGE = REMOVE_AND_DELETE_EOBJECT__DELETE_CHANGE;

	/**
	 * The number of structural features of the '<em>Replace And Delete Non Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_AND_DELETE_NON_ROOT_FEATURE_COUNT = REMOVE_AND_DELETE_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_AND_DELETE_NON_ROOT___RESOLVE_BEFORE__RESOURCESET = REMOVE_AND_DELETE_EOBJECT___RESOLVE_BEFORE__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_AND_DELETE_NON_ROOT___RESOLVE_AFTER__RESOURCESET = REMOVE_AND_DELETE_EOBJECT___RESOLVE_AFTER__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_AND_DELETE_NON_ROOT___APPLY_FORWARD = REMOVE_AND_DELETE_EOBJECT___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_AND_DELETE_NON_ROOT___APPLY_BACKWARD = REMOVE_AND_DELETE_EOBJECT___APPLY_BACKWARD;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_AND_DELETE_NON_ROOT___IS_RESOLVED = REMOVE_AND_DELETE_EOBJECT___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_AND_DELETE_NON_ROOT___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = REMOVE_AND_DELETE_EOBJECT___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_AND_DELETE_NON_ROOT___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = REMOVE_AND_DELETE_EOBJECT___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_AND_DELETE_NON_ROOT___GET_ATOMIC_CHANGES = REMOVE_AND_DELETE_EOBJECT___GET_ATOMIC_CHANGES;

	/**
	 * The number of operations of the '<em>Replace And Delete Non Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_AND_DELETE_NON_ROOT_OPERATION_COUNT = REMOVE_AND_DELETE_EOBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.CreateAndReplaceAndDeleteNonRootImpl <em>Create And Replace And Delete Non Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.CreateAndReplaceAndDeleteNonRootImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCreateAndReplaceAndDeleteNonRoot()
	 * @generated
	 */
	int CREATE_AND_REPLACE_AND_DELETE_NON_ROOT = 15;

	/**
	 * The feature id for the '<em><b>Create Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__CREATE_CHANGE = COMPOUND_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Replace Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__REPLACE_CHANGE = COMPOUND_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Delete Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__DELETE_CHANGE = COMPOUND_ECHANGE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Create And Replace And Delete Non Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_AND_DELETE_NON_ROOT_FEATURE_COUNT = COMPOUND_ECHANGE_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_AND_DELETE_NON_ROOT___RESOLVE_BEFORE__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_BEFORE__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_AND_DELETE_NON_ROOT___RESOLVE_AFTER__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_AFTER__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_AND_DELETE_NON_ROOT___APPLY_FORWARD = COMPOUND_ECHANGE___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_AND_DELETE_NON_ROOT___APPLY_BACKWARD = COMPOUND_ECHANGE___APPLY_BACKWARD;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_AND_DELETE_NON_ROOT___IS_RESOLVED = COMPOUND_ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_AND_DELETE_NON_ROOT___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_AND_DELETE_NON_ROOT___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = COMPOUND_ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_AND_DELETE_NON_ROOT___GET_ATOMIC_CHANGES = COMPOUND_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Create And Replace And Delete Non Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_AND_REPLACE_AND_DELETE_NON_ROOT_OPERATION_COUNT = COMPOUND_ECHANGE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '<em>Command</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.command.Command
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCommand()
	 * @generated
	 */
	int COMMAND = 16;

	/**
	 * The meta object id for the '<em>Resource Set</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.resource.ResourceSet
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getResourceSet()
	 * @generated
	 */
	int RESOURCE_SET = 17;

	/**
	 * The meta object id for the '<em>EObj</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EObject
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getEObj()
	 * @generated
	 */
	int EOBJ = 18;


	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.CompoundEChange <em>EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.CompoundEChange
	 * @generated
	 */
	EClass getCompoundEChange();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.compound.CompoundEChange#getAtomicChanges() <em>Get Atomic Changes</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Atomic Changes</em>' operation.
	 * @see tools.vitruv.framework.change.echange.compound.CompoundEChange#getAtomicChanges()
	 * @generated
	 */
	EOperation getCompoundEChange__GetAtomicChanges();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.compound.CompoundEChange#isResolved() <em>Is Resolved</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Resolved</em>' operation.
	 * @see tools.vitruv.framework.change.echange.compound.CompoundEChange#isResolved()
	 * @generated
	 */
	EOperation getCompoundEChange__IsResolved();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.compound.CompoundEChange#resolveBeforeAndApplyForward(org.eclipse.emf.ecore.resource.ResourceSet) <em>Resolve Before And Apply Forward</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * @see tools.vitruv.framework.change.echange.compound.CompoundEChange#resolveBeforeAndApplyForward(org.eclipse.emf.ecore.resource.ResourceSet)
	 * @generated
	 */
	EOperation getCompoundEChange__ResolveBeforeAndApplyForward__ResourceSet();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.compound.CompoundEChange#resolveAfterAndApplyBackward(org.eclipse.emf.ecore.resource.ResourceSet) <em>Resolve After And Apply Backward</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Resolve After And Apply Backward</em>' operation.
	 * @see tools.vitruv.framework.change.echange.compound.CompoundEChange#resolveAfterAndApplyBackward(org.eclipse.emf.ecore.resource.ResourceSet)
	 * @generated
	 */
	EOperation getCompoundEChange__ResolveAfterAndApplyBackward__ResourceSet();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.MoveEObject <em>Move EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Move EObject</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.MoveEObject
	 * @generated
	 */
	EClass getMoveEObject();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.change.echange.compound.MoveEObject#getSubtractWhereChange <em>Subtract Where Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Subtract Where Change</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.MoveEObject#getSubtractWhereChange()
	 * @see #getMoveEObject()
	 * @generated
	 */
	EReference getMoveEObject_SubtractWhereChange();

	/**
	 * Returns the meta object for the containment reference '{@link tools.vitruv.framework.change.echange.compound.MoveEObject#getSubtractWhatChange <em>Subtract What Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Subtract What Change</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.MoveEObject#getSubtractWhatChange()
	 * @see #getMoveEObject()
	 * @generated
	 */
	EReference getMoveEObject_SubtractWhatChange();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.change.echange.compound.MoveEObject#getAddWhereChange <em>Add Where Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Add Where Change</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.MoveEObject#getAddWhereChange()
	 * @see #getMoveEObject()
	 * @generated
	 */
	EReference getMoveEObject_AddWhereChange();

	/**
	 * Returns the meta object for the containment reference '{@link tools.vitruv.framework.change.echange.compound.MoveEObject#getAddWhatChange <em>Add What Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Add What Change</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.MoveEObject#getAddWhatChange()
	 * @see #getMoveEObject()
	 * @generated
	 */
	EReference getMoveEObject_AddWhatChange();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.compound.MoveEObject#getAtomicChanges() <em>Get Atomic Changes</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Atomic Changes</em>' operation.
	 * @see tools.vitruv.framework.change.echange.compound.MoveEObject#getAtomicChanges()
	 * @generated
	 */
	EOperation getMoveEObject__GetAtomicChanges();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature <em>Explicit Unset EFeature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Explicit Unset EFeature</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature
	 * @generated
	 */
	EClass getExplicitUnsetEFeature();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature#getAffectedEObject <em>Affected EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Affected EObject</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature#getAffectedEObject()
	 * @see #getExplicitUnsetEFeature()
	 * @generated
	 */
	EReference getExplicitUnsetEFeature_AffectedEObject();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature#getAffectedFeature <em>Affected Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Affected Feature</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature#getAffectedFeature()
	 * @see #getExplicitUnsetEFeature()
	 * @generated
	 */
	EReference getExplicitUnsetEFeature_AffectedFeature();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute <em>Explicit Unset EAttribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Explicit Unset EAttribute</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute
	 * @generated
	 */
	EClass getExplicitUnsetEAttribute();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute#getAtomicChanges() <em>Get Atomic Changes</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Atomic Changes</em>' operation.
	 * @see tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute#getAtomicChanges()
	 * @generated
	 */
	EOperation getExplicitUnsetEAttribute__GetAtomicChanges();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference <em>Explicit Unset EReference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Explicit Unset EReference</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference
	 * @generated
	 */
	EClass getExplicitUnsetEReference();

	/**
	 * Returns the meta object for the containment reference list '{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference#getChanges <em>Changes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Changes</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference#getChanges()
	 * @see #getExplicitUnsetEReference()
	 * @generated
	 */
	EReference getExplicitUnsetEReference_Changes();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference#getContainedChanges() <em>Get Contained Changes</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Contained Changes</em>' operation.
	 * @see tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference#getContainedChanges()
	 * @generated
	 */
	EOperation getExplicitUnsetEReference__GetContainedChanges();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference#getAtomicChanges() <em>Get Atomic Changes</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Atomic Changes</em>' operation.
	 * @see tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference#getAtomicChanges()
	 * @generated
	 */
	EOperation getExplicitUnsetEReference__GetAtomicChanges();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.CompoundSubtraction <em>Subtraction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subtraction</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.CompoundSubtraction
	 * @generated
	 */
	EClass getCompoundSubtraction();

	/**
	 * Returns the meta object for the containment reference list '{@link tools.vitruv.framework.change.echange.compound.CompoundSubtraction#getSubtractiveChanges <em>Subtractive Changes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Subtractive Changes</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.CompoundSubtraction#getSubtractiveChanges()
	 * @see #getCompoundSubtraction()
	 * @generated
	 */
	EReference getCompoundSubtraction_SubtractiveChanges();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.compound.CompoundSubtraction#getAtomicChanges() <em>Get Atomic Changes</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Atomic Changes</em>' operation.
	 * @see tools.vitruv.framework.change.echange.compound.CompoundSubtraction#getAtomicChanges()
	 * @generated
	 */
	EOperation getCompoundSubtraction__GetAtomicChanges();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.CompoundAddition <em>Addition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Addition</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.CompoundAddition
	 * @generated
	 */
	EClass getCompoundAddition();

	/**
	 * Returns the meta object for the containment reference list '{@link tools.vitruv.framework.change.echange.compound.CompoundAddition#getAdditiveChanges <em>Additive Changes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Additive Changes</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.CompoundAddition#getAdditiveChanges()
	 * @see #getCompoundAddition()
	 * @generated
	 */
	EReference getCompoundAddition_AdditiveChanges();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.compound.CompoundAddition#getAtomicChanges() <em>Get Atomic Changes</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Atomic Changes</em>' operation.
	 * @see tools.vitruv.framework.change.echange.compound.CompoundAddition#getAtomicChanges()
	 * @generated
	 */
	EOperation getCompoundAddition__GetAtomicChanges();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.CreateAndInsertEObject <em>Create And Insert EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Create And Insert EObject</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.CreateAndInsertEObject
	 * @generated
	 */
	EClass getCreateAndInsertEObject();

	/**
	 * Returns the meta object for the containment reference '{@link tools.vitruv.framework.change.echange.compound.CreateAndInsertEObject#getCreateChange <em>Create Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Create Change</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.CreateAndInsertEObject#getCreateChange()
	 * @see #getCreateAndInsertEObject()
	 * @generated
	 */
	EReference getCreateAndInsertEObject_CreateChange();

	/**
	 * Returns the meta object for the containment reference '{@link tools.vitruv.framework.change.echange.compound.CreateAndInsertEObject#getInsertChange <em>Insert Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Insert Change</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.CreateAndInsertEObject#getInsertChange()
	 * @see #getCreateAndInsertEObject()
	 * @generated
	 */
	EReference getCreateAndInsertEObject_InsertChange();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.compound.CreateAndInsertEObject#getAtomicChanges() <em>Get Atomic Changes</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Atomic Changes</em>' operation.
	 * @see tools.vitruv.framework.change.echange.compound.CreateAndInsertEObject#getAtomicChanges()
	 * @generated
	 */
	EOperation getCreateAndInsertEObject__GetAtomicChanges();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.RemoveAndDeleteEObject <em>Remove And Delete EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remove And Delete EObject</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.RemoveAndDeleteEObject
	 * @generated
	 */
	EClass getRemoveAndDeleteEObject();

	/**
	 * Returns the meta object for the containment reference '{@link tools.vitruv.framework.change.echange.compound.RemoveAndDeleteEObject#getRemoveChange <em>Remove Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Remove Change</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.RemoveAndDeleteEObject#getRemoveChange()
	 * @see #getRemoveAndDeleteEObject()
	 * @generated
	 */
	EReference getRemoveAndDeleteEObject_RemoveChange();

	/**
	 * Returns the meta object for the containment reference '{@link tools.vitruv.framework.change.echange.compound.RemoveAndDeleteEObject#getDeleteChange <em>Delete Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Delete Change</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.RemoveAndDeleteEObject#getDeleteChange()
	 * @see #getRemoveAndDeleteEObject()
	 * @generated
	 */
	EReference getRemoveAndDeleteEObject_DeleteChange();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.compound.RemoveAndDeleteEObject#getAtomicChanges() <em>Get Atomic Changes</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Atomic Changes</em>' operation.
	 * @see tools.vitruv.framework.change.echange.compound.RemoveAndDeleteEObject#getAtomicChanges()
	 * @generated
	 */
	EOperation getRemoveAndDeleteEObject__GetAtomicChanges();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot <em>Create And Insert Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Create And Insert Root</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
	 * @generated
	 */
	EClass getCreateAndInsertRoot();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot <em>Remove And Delete Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remove And Delete Root</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot
	 * @generated
	 */
	EClass getRemoveAndDeleteRoot();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot <em>Create And Insert Non Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Create And Insert Non Root</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
	 * @generated
	 */
	EClass getCreateAndInsertNonRoot();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot <em>Remove And Delete Non Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remove And Delete Non Root</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot
	 * @generated
	 */
	EClass getRemoveAndDeleteNonRoot();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot <em>Create And Replace Non Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Create And Replace Non Root</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
	 * @generated
	 */
	EClass getCreateAndReplaceNonRoot();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.ReplaceAndDeleteNonRoot <em>Replace And Delete Non Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Replace And Delete Non Root</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.ReplaceAndDeleteNonRoot
	 * @generated
	 */
	EClass getReplaceAndDeleteNonRoot();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot <em>Create And Replace And Delete Non Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Create And Replace And Delete Non Root</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot
	 * @generated
	 */
	EClass getCreateAndReplaceAndDeleteNonRoot();

	/**
	 * Returns the meta object for the containment reference '{@link tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot#getCreateChange <em>Create Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Create Change</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot#getCreateChange()
	 * @see #getCreateAndReplaceAndDeleteNonRoot()
	 * @generated
	 */
	EReference getCreateAndReplaceAndDeleteNonRoot_CreateChange();

	/**
	 * Returns the meta object for the containment reference '{@link tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot#getReplaceChange <em>Replace Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Replace Change</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot#getReplaceChange()
	 * @see #getCreateAndReplaceAndDeleteNonRoot()
	 * @generated
	 */
	EReference getCreateAndReplaceAndDeleteNonRoot_ReplaceChange();

	/**
	 * Returns the meta object for the containment reference '{@link tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot#getDeleteChange <em>Delete Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Delete Change</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot#getDeleteChange()
	 * @see #getCreateAndReplaceAndDeleteNonRoot()
	 * @generated
	 */
	EReference getCreateAndReplaceAndDeleteNonRoot_DeleteChange();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot#getAtomicChanges() <em>Get Atomic Changes</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Atomic Changes</em>' operation.
	 * @see tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot#getAtomicChanges()
	 * @generated
	 */
	EOperation getCreateAndReplaceAndDeleteNonRoot__GetAtomicChanges();

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
	 * Returns the meta object for data type '{@link org.eclipse.emf.ecore.EObject <em>EObj</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>EObj</em>'.
	 * @see org.eclipse.emf.ecore.EObject
	 * @model instanceClass="org.eclipse.emf.ecore.EObject"
	 * @generated
	 */
	EDataType getEObj();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CompoundFactory getCompoundFactory();

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
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.CompoundEChangeImpl <em>EChange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundEChangeImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCompoundEChange()
		 * @generated
		 */
		EClass COMPOUND_ECHANGE = eINSTANCE.getCompoundEChange();

		/**
		 * The meta object literal for the '<em><b>Get Atomic Changes</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPOUND_ECHANGE___GET_ATOMIC_CHANGES = eINSTANCE.getCompoundEChange__GetAtomicChanges();

		/**
		 * The meta object literal for the '<em><b>Is Resolved</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPOUND_ECHANGE___IS_RESOLVED = eINSTANCE.getCompoundEChange__IsResolved();

		/**
		 * The meta object literal for the '<em><b>Resolve Before And Apply Forward</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPOUND_ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = eINSTANCE.getCompoundEChange__ResolveBeforeAndApplyForward__ResourceSet();

		/**
		 * The meta object literal for the '<em><b>Resolve After And Apply Backward</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPOUND_ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = eINSTANCE.getCompoundEChange__ResolveAfterAndApplyBackward__ResourceSet();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.MoveEObjectImpl <em>Move EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.MoveEObjectImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getMoveEObject()
		 * @generated
		 */
		EClass MOVE_EOBJECT = eINSTANCE.getMoveEObject();

		/**
		 * The meta object literal for the '<em><b>Subtract Where Change</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MOVE_EOBJECT__SUBTRACT_WHERE_CHANGE = eINSTANCE.getMoveEObject_SubtractWhereChange();

		/**
		 * The meta object literal for the '<em><b>Subtract What Change</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE = eINSTANCE.getMoveEObject_SubtractWhatChange();

		/**
		 * The meta object literal for the '<em><b>Add Where Change</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MOVE_EOBJECT__ADD_WHERE_CHANGE = eINSTANCE.getMoveEObject_AddWhereChange();

		/**
		 * The meta object literal for the '<em><b>Add What Change</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MOVE_EOBJECT__ADD_WHAT_CHANGE = eINSTANCE.getMoveEObject_AddWhatChange();

		/**
		 * The meta object literal for the '<em><b>Get Atomic Changes</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MOVE_EOBJECT___GET_ATOMIC_CHANGES = eINSTANCE.getMoveEObject__GetAtomicChanges();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEFeatureImpl <em>Explicit Unset EFeature</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEFeatureImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getExplicitUnsetEFeature()
		 * @generated
		 */
		EClass EXPLICIT_UNSET_EFEATURE = eINSTANCE.getExplicitUnsetEFeature();

		/**
		 * The meta object literal for the '<em><b>Affected EObject</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPLICIT_UNSET_EFEATURE__AFFECTED_EOBJECT = eINSTANCE.getExplicitUnsetEFeature_AffectedEObject();

		/**
		 * The meta object literal for the '<em><b>Affected Feature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPLICIT_UNSET_EFEATURE__AFFECTED_FEATURE = eINSTANCE.getExplicitUnsetEFeature_AffectedFeature();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEAttributeImpl <em>Explicit Unset EAttribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEAttributeImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getExplicitUnsetEAttribute()
		 * @generated
		 */
		EClass EXPLICIT_UNSET_EATTRIBUTE = eINSTANCE.getExplicitUnsetEAttribute();

		/**
		 * The meta object literal for the '<em><b>Get Atomic Changes</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXPLICIT_UNSET_EATTRIBUTE___GET_ATOMIC_CHANGES = eINSTANCE.getExplicitUnsetEAttribute__GetAtomicChanges();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEReferenceImpl <em>Explicit Unset EReference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEReferenceImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getExplicitUnsetEReference()
		 * @generated
		 */
		EClass EXPLICIT_UNSET_EREFERENCE = eINSTANCE.getExplicitUnsetEReference();

		/**
		 * The meta object literal for the '<em><b>Changes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPLICIT_UNSET_EREFERENCE__CHANGES = eINSTANCE.getExplicitUnsetEReference_Changes();

		/**
		 * The meta object literal for the '<em><b>Get Contained Changes</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXPLICIT_UNSET_EREFERENCE___GET_CONTAINED_CHANGES = eINSTANCE.getExplicitUnsetEReference__GetContainedChanges();

		/**
		 * The meta object literal for the '<em><b>Get Atomic Changes</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXPLICIT_UNSET_EREFERENCE___GET_ATOMIC_CHANGES = eINSTANCE.getExplicitUnsetEReference__GetAtomicChanges();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.CompoundSubtractionImpl <em>Subtraction</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundSubtractionImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCompoundSubtraction()
		 * @generated
		 */
		EClass COMPOUND_SUBTRACTION = eINSTANCE.getCompoundSubtraction();

		/**
		 * The meta object literal for the '<em><b>Subtractive Changes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOUND_SUBTRACTION__SUBTRACTIVE_CHANGES = eINSTANCE.getCompoundSubtraction_SubtractiveChanges();

		/**
		 * The meta object literal for the '<em><b>Get Atomic Changes</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPOUND_SUBTRACTION___GET_ATOMIC_CHANGES = eINSTANCE.getCompoundSubtraction__GetAtomicChanges();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.CompoundAdditionImpl <em>Addition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundAdditionImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCompoundAddition()
		 * @generated
		 */
		EClass COMPOUND_ADDITION = eINSTANCE.getCompoundAddition();

		/**
		 * The meta object literal for the '<em><b>Additive Changes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOUND_ADDITION__ADDITIVE_CHANGES = eINSTANCE.getCompoundAddition_AdditiveChanges();

		/**
		 * The meta object literal for the '<em><b>Get Atomic Changes</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPOUND_ADDITION___GET_ATOMIC_CHANGES = eINSTANCE.getCompoundAddition__GetAtomicChanges();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertEObjectImpl <em>Create And Insert EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertEObjectImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCreateAndInsertEObject()
		 * @generated
		 */
		EClass CREATE_AND_INSERT_EOBJECT = eINSTANCE.getCreateAndInsertEObject();

		/**
		 * The meta object literal for the '<em><b>Create Change</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CREATE_AND_INSERT_EOBJECT__CREATE_CHANGE = eINSTANCE.getCreateAndInsertEObject_CreateChange();

		/**
		 * The meta object literal for the '<em><b>Insert Change</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CREATE_AND_INSERT_EOBJECT__INSERT_CHANGE = eINSTANCE.getCreateAndInsertEObject_InsertChange();

		/**
		 * The meta object literal for the '<em><b>Get Atomic Changes</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation CREATE_AND_INSERT_EOBJECT___GET_ATOMIC_CHANGES = eINSTANCE.getCreateAndInsertEObject__GetAtomicChanges();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.RemoveAndDeleteEObjectImpl <em>Remove And Delete EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.RemoveAndDeleteEObjectImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getRemoveAndDeleteEObject()
		 * @generated
		 */
		EClass REMOVE_AND_DELETE_EOBJECT = eINSTANCE.getRemoveAndDeleteEObject();

		/**
		 * The meta object literal for the '<em><b>Remove Change</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REMOVE_AND_DELETE_EOBJECT__REMOVE_CHANGE = eINSTANCE.getRemoveAndDeleteEObject_RemoveChange();

		/**
		 * The meta object literal for the '<em><b>Delete Change</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REMOVE_AND_DELETE_EOBJECT__DELETE_CHANGE = eINSTANCE.getRemoveAndDeleteEObject_DeleteChange();

		/**
		 * The meta object literal for the '<em><b>Get Atomic Changes</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REMOVE_AND_DELETE_EOBJECT___GET_ATOMIC_CHANGES = eINSTANCE.getRemoveAndDeleteEObject__GetAtomicChanges();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertRootImpl <em>Create And Insert Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertRootImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCreateAndInsertRoot()
		 * @generated
		 */
		EClass CREATE_AND_INSERT_ROOT = eINSTANCE.getCreateAndInsertRoot();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.RemoveAndDeleteRootImpl <em>Remove And Delete Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.RemoveAndDeleteRootImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getRemoveAndDeleteRoot()
		 * @generated
		 */
		EClass REMOVE_AND_DELETE_ROOT = eINSTANCE.getRemoveAndDeleteRoot();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertNonRootImpl <em>Create And Insert Non Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertNonRootImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCreateAndInsertNonRoot()
		 * @generated
		 */
		EClass CREATE_AND_INSERT_NON_ROOT = eINSTANCE.getCreateAndInsertNonRoot();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.RemoveAndDeleteNonRootImpl <em>Remove And Delete Non Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.RemoveAndDeleteNonRootImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getRemoveAndDeleteNonRoot()
		 * @generated
		 */
		EClass REMOVE_AND_DELETE_NON_ROOT = eINSTANCE.getRemoveAndDeleteNonRoot();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.CreateAndReplaceNonRootImpl <em>Create And Replace Non Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.CreateAndReplaceNonRootImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCreateAndReplaceNonRoot()
		 * @generated
		 */
		EClass CREATE_AND_REPLACE_NON_ROOT = eINSTANCE.getCreateAndReplaceNonRoot();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.ReplaceAndDeleteNonRootImpl <em>Replace And Delete Non Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.ReplaceAndDeleteNonRootImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getReplaceAndDeleteNonRoot()
		 * @generated
		 */
		EClass REPLACE_AND_DELETE_NON_ROOT = eINSTANCE.getReplaceAndDeleteNonRoot();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.CreateAndReplaceAndDeleteNonRootImpl <em>Create And Replace And Delete Non Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.CreateAndReplaceAndDeleteNonRootImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCreateAndReplaceAndDeleteNonRoot()
		 * @generated
		 */
		EClass CREATE_AND_REPLACE_AND_DELETE_NON_ROOT = eINSTANCE.getCreateAndReplaceAndDeleteNonRoot();

		/**
		 * The meta object literal for the '<em><b>Create Change</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__CREATE_CHANGE = eINSTANCE.getCreateAndReplaceAndDeleteNonRoot_CreateChange();

		/**
		 * The meta object literal for the '<em><b>Replace Change</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__REPLACE_CHANGE = eINSTANCE.getCreateAndReplaceAndDeleteNonRoot_ReplaceChange();

		/**
		 * The meta object literal for the '<em><b>Delete Change</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CREATE_AND_REPLACE_AND_DELETE_NON_ROOT__DELETE_CHANGE = eINSTANCE.getCreateAndReplaceAndDeleteNonRoot_DeleteChange();

		/**
		 * The meta object literal for the '<em><b>Get Atomic Changes</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation CREATE_AND_REPLACE_AND_DELETE_NON_ROOT___GET_ATOMIC_CHANGES = eINSTANCE.getCreateAndReplaceAndDeleteNonRoot__GetAtomicChanges();

		/**
		 * The meta object literal for the '<em>Command</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.command.Command
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCommand()
		 * @generated
		 */
		EDataType COMMAND = eINSTANCE.getCommand();

		/**
		 * The meta object literal for the '<em>Resource Set</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecore.resource.ResourceSet
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getResourceSet()
		 * @generated
		 */
		EDataType RESOURCE_SET = eINSTANCE.getResourceSet();

		/**
		 * The meta object literal for the '<em>EObj</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecore.EObject
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getEObj()
		 * @generated
		 */
		EDataType EOBJ = eINSTANCE.getEObj();

	}

} //CompoundPackage
