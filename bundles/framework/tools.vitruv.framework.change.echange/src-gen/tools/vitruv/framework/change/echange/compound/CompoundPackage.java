/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ECHANGE___IS_RESOLVED = EChangePackage.ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Get Involved EObjects</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ECHANGE___GET_INVOLVED_EOBJECTS = EChangePackage.ECHANGE___GET_INVOLVED_EOBJECTS;

	/**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ECHANGE___GET_ATOMIC_CHANGES = EChangePackage.ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ECHANGE_OPERATION_COUNT = EChangePackage.ECHANGE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEFeatureImpl <em>Explicit Unset EFeature</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEFeatureImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getExplicitUnsetEFeature()
	 * @generated
	 */
	int EXPLICIT_UNSET_EFEATURE = 1;

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
	 * The feature id for the '<em><b>Affected EObject ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EFEATURE__AFFECTED_EOBJECT_ID = COMPOUND_ECHANGE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Explicit Unset EFeature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EFEATURE_FEATURE_COUNT = COMPOUND_ECHANGE_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EFEATURE___IS_RESOLVED = COMPOUND_ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Get Involved EObjects</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EFEATURE___GET_INVOLVED_EOBJECTS = COMPOUND_ECHANGE___GET_INVOLVED_EOBJECTS;

	/**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EFEATURE___GET_ATOMIC_CHANGES = COMPOUND_ECHANGE___GET_ATOMIC_CHANGES;

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
	int COMPOUND_SUBTRACTION = 4;

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
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_SUBTRACTION___IS_RESOLVED = COMPOUND_ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Get Involved EObjects</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_SUBTRACTION___GET_INVOLVED_EOBJECTS = COMPOUND_ECHANGE___GET_INVOLVED_EOBJECTS;

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
	int EXPLICIT_UNSET_EATTRIBUTE = 2;

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
	 * The feature id for the '<em><b>Affected EObject ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EATTRIBUTE__AFFECTED_EOBJECT_ID = COMPOUND_SUBTRACTION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Explicit Unset EAttribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EATTRIBUTE_FEATURE_COUNT = COMPOUND_SUBTRACTION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EATTRIBUTE___IS_RESOLVED = COMPOUND_SUBTRACTION___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Get Involved EObjects</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EATTRIBUTE___GET_INVOLVED_EOBJECTS = COMPOUND_SUBTRACTION___GET_INVOLVED_EOBJECTS;

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
	int EXPLICIT_UNSET_EREFERENCE = 3;

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
	 * The feature id for the '<em><b>Affected EObject ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EREFERENCE__AFFECTED_EOBJECT_ID = EXPLICIT_UNSET_EFEATURE__AFFECTED_EOBJECT_ID;

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
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EREFERENCE___IS_RESOLVED = EXPLICIT_UNSET_EFEATURE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Get Involved EObjects</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPLICIT_UNSET_EREFERENCE___GET_INVOLVED_EOBJECTS = EXPLICIT_UNSET_EFEATURE___GET_INVOLVED_EOBJECTS;

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
	int COMPOUND_ADDITION = 5;

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
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ADDITION___IS_RESOLVED = COMPOUND_ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Get Involved EObjects</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOUND_ADDITION___GET_INVOLVED_EOBJECTS = COMPOUND_ECHANGE___GET_INVOLVED_EOBJECTS;

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
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature#getAffectedEObjectID <em>Affected EObject ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Affected EObject ID</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature#getAffectedEObjectID()
	 * @see #getExplicitUnsetEFeature()
	 * @generated
	 */
	EAttribute getExplicitUnsetEFeature_AffectedEObjectID();

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
		 * The meta object literal for the '<em><b>Affected EObject ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPLICIT_UNSET_EFEATURE__AFFECTED_EOBJECT_ID = eINSTANCE.getExplicitUnsetEFeature_AffectedEObjectID();

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

	}

} //CompoundPackage
