/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
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
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangeFactory
 * @model kind="package"
 * @generated
 */
public interface ChangePackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "change";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://edu.kit.ipd.sdq.vitruvius/Change/1.0";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "change";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    ChangePackage eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl.init();

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.EChangeImpl <em>EChange</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.EChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getEChange()
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
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.EAtomicChangeImpl <em>EAtomic Change</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.EAtomicChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getEAtomicChange()
     * @generated
     */
    int EATOMIC_CHANGE = 1;

    /**
     * The number of structural features of the '<em>EAtomic Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EATOMIC_CHANGE_FEATURE_COUNT = ECHANGE_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>EAtomic Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EATOMIC_CHANGE_OPERATION_COUNT = ECHANGE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveEChangeImpl <em>Additive EChange</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveEChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getAdditiveEChange()
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
    int ADDITIVE_ECHANGE_FEATURE_COUNT = EATOMIC_CHANGE_FEATURE_COUNT + 0;

    /**
     * The operation id for the '<em>Get New Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_ECHANGE___GET_NEW_VALUE = EATOMIC_CHANGE_OPERATION_COUNT + 0;

    /**
     * The number of operations of the '<em>Additive EChange</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_ECHANGE_OPERATION_COUNT = EATOMIC_CHANGE_OPERATION_COUNT + 1;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveEChangeImpl <em>Subtractive EChange</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveEChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getSubtractiveEChange()
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
    int SUBTRACTIVE_ECHANGE_FEATURE_COUNT = EATOMIC_CHANGE_FEATURE_COUNT + 0;

    /**
     * The operation id for the '<em>Get Old Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_ECHANGE___GET_OLD_VALUE = EATOMIC_CHANGE_OPERATION_COUNT + 0;

    /**
     * The number of operations of the '<em>Subtractive EChange</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_ECHANGE_OPERATION_COUNT = EATOMIC_CHANGE_OPERATION_COUNT + 1;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveEAttributeChangeImpl <em>Additive EAttribute Change</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveEAttributeChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getAdditiveEAttributeChange()
     * @generated
     */
    int ADDITIVE_EATTRIBUTE_CHANGE = 4;

    /**
     * The feature id for the '<em><b>New Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_EATTRIBUTE_CHANGE__NEW_VALUE = ADDITIVE_ECHANGE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Additive EAttribute Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_EATTRIBUTE_CHANGE_FEATURE_COUNT = ADDITIVE_ECHANGE_FEATURE_COUNT + 1;

    /**
     * The operation id for the '<em>Get New Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_EATTRIBUTE_CHANGE___GET_NEW_VALUE = ADDITIVE_ECHANGE___GET_NEW_VALUE;

    /**
     * The number of operations of the '<em>Additive EAttribute Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_EATTRIBUTE_CHANGE_OPERATION_COUNT = ADDITIVE_ECHANGE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveEAttributeChangeImpl <em>Subtractive EAttribute Change</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveEAttributeChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getSubtractiveEAttributeChange()
     * @generated
     */
    int SUBTRACTIVE_EATTRIBUTE_CHANGE = 5;

    /**
     * The feature id for the '<em><b>Old Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_EATTRIBUTE_CHANGE__OLD_VALUE = SUBTRACTIVE_ECHANGE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Subtractive EAttribute Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_EATTRIBUTE_CHANGE_FEATURE_COUNT = SUBTRACTIVE_ECHANGE_FEATURE_COUNT + 1;

    /**
     * The operation id for the '<em>Get Old Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_EATTRIBUTE_CHANGE___GET_OLD_VALUE = SUBTRACTIVE_ECHANGE___GET_OLD_VALUE;

    /**
     * The number of operations of the '<em>Subtractive EAttribute Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_EATTRIBUTE_CHANGE_OPERATION_COUNT = SUBTRACTIVE_ECHANGE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveEReferenceChangeImpl <em>Additive EReference Change</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveEReferenceChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getAdditiveEReferenceChange()
     * @generated
     */
    int ADDITIVE_EREFERENCE_CHANGE = 6;

    /**
     * The feature id for the '<em><b>New Value</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_EREFERENCE_CHANGE__NEW_VALUE = ADDITIVE_ECHANGE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Is Create</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_EREFERENCE_CHANGE__IS_CREATE = ADDITIVE_ECHANGE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Additive EReference Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_EREFERENCE_CHANGE_FEATURE_COUNT = ADDITIVE_ECHANGE_FEATURE_COUNT + 2;

    /**
     * The operation id for the '<em>Get New Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_EREFERENCE_CHANGE___GET_NEW_VALUE = ADDITIVE_ECHANGE___GET_NEW_VALUE;

    /**
     * The number of operations of the '<em>Additive EReference Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_EREFERENCE_CHANGE_OPERATION_COUNT = ADDITIVE_ECHANGE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveEReferenceChangeImpl <em>Subtractive EReference Change</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveEReferenceChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getSubtractiveEReferenceChange()
     * @generated
     */
    int SUBTRACTIVE_EREFERENCE_CHANGE = 7;

    /**
     * The feature id for the '<em><b>Old TUID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_EREFERENCE_CHANGE__OLD_TUID = SUBTRACTIVE_ECHANGE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Feature2 Old Value Map</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_EREFERENCE_CHANGE__FEATURE2_OLD_VALUE_MAP = SUBTRACTIVE_ECHANGE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Is Delete</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_EREFERENCE_CHANGE__IS_DELETE = SUBTRACTIVE_ECHANGE_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Subtractive EReference Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_EREFERENCE_CHANGE_FEATURE_COUNT = SUBTRACTIVE_ECHANGE_FEATURE_COUNT + 3;

    /**
     * The operation id for the '<em>Get Old Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_EREFERENCE_CHANGE___GET_OLD_VALUE = SUBTRACTIVE_ECHANGE___GET_OLD_VALUE;

    /**
     * The number of operations of the '<em>Subtractive EReference Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_EREFERENCE_CHANGE_OPERATION_COUNT = SUBTRACTIVE_ECHANGE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '<em>TUID</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getTUID()
     * @generated
     */
    int TUID = 8;


    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange <em>EChange</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>EChange</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
     * @generated
     */
    EClass getEChange();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EAtomicChange <em>EAtomic Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>EAtomic Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EAtomicChange
     * @generated
     */
    EClass getEAtomicChange();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange <em>Additive EChange</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Additive EChange</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange
     * @generated
     */
    EClass getAdditiveEChange();

    /**
     * Returns the meta object for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange#getNewValue() <em>Get New Value</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the '<em>Get New Value</em>' operation.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange#getNewValue()
     * @generated
     */
    EOperation getAdditiveEChange__GetNewValue();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange <em>Subtractive EChange</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Subtractive EChange</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange
     * @generated
     */
    EClass getSubtractiveEChange();

    /**
     * Returns the meta object for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange#getOldValue() <em>Get Old Value</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the '<em>Get Old Value</em>' operation.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange#getOldValue()
     * @generated
     */
    EOperation getSubtractiveEChange__GetOldValue();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEAttributeChange <em>Additive EAttribute Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Additive EAttribute Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEAttributeChange
     * @generated
     */
    EClass getAdditiveEAttributeChange();

    /**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEAttributeChange#getNewValue <em>New Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>New Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEAttributeChange#getNewValue()
     * @see #getAdditiveEAttributeChange()
     * @generated
     */
    EAttribute getAdditiveEAttributeChange_NewValue();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEAttributeChange <em>Subtractive EAttribute Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Subtractive EAttribute Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEAttributeChange
     * @generated
     */
    EClass getSubtractiveEAttributeChange();

    /**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEAttributeChange#getOldValue <em>Old Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Old Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEAttributeChange#getOldValue()
     * @see #getSubtractiveEAttributeChange()
     * @generated
     */
    EAttribute getSubtractiveEAttributeChange_OldValue();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEReferenceChange <em>Additive EReference Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Additive EReference Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEReferenceChange
     * @generated
     */
    EClass getAdditiveEReferenceChange();

    /**
     * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEReferenceChange#getNewValue <em>New Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>New Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEReferenceChange#getNewValue()
     * @see #getAdditiveEReferenceChange()
     * @generated
     */
    EReference getAdditiveEReferenceChange_NewValue();

    /**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEReferenceChange#isIsCreate <em>Is Create</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Is Create</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEReferenceChange#isIsCreate()
     * @see #getAdditiveEReferenceChange()
     * @generated
     */
    EAttribute getAdditiveEReferenceChange_IsCreate();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange <em>Subtractive EReference Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Subtractive EReference Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange
     * @generated
     */
    EClass getSubtractiveEReferenceChange();

    /**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange#getOldTUID <em>Old TUID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Old TUID</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange#getOldTUID()
     * @see #getSubtractiveEReferenceChange()
     * @generated
     */
    EAttribute getSubtractiveEReferenceChange_OldTUID();

    /**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange#getFeature2OldValueMap <em>Feature2 Old Value Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Feature2 Old Value Map</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange#getFeature2OldValueMap()
     * @see #getSubtractiveEReferenceChange()
     * @generated
     */
    EAttribute getSubtractiveEReferenceChange_Feature2OldValueMap();

    /**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange#isIsDelete <em>Is Delete</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Is Delete</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange#isIsDelete()
     * @see #getSubtractiveEReferenceChange()
     * @generated
     */
    EAttribute getSubtractiveEReferenceChange_IsDelete();

    /**
     * Returns the meta object for data type '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID <em>TUID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>TUID</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
     * @model instanceClass="edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID"
     * @generated
     */
    EDataType getTUID();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    ChangeFactory getChangeFactory();

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
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.EChangeImpl <em>EChange</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.EChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getEChange()
         * @generated
         */
        EClass ECHANGE = eINSTANCE.getEChange();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.EAtomicChangeImpl <em>EAtomic Change</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.EAtomicChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getEAtomicChange()
         * @generated
         */
        EClass EATOMIC_CHANGE = eINSTANCE.getEAtomicChange();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveEChangeImpl <em>Additive EChange</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveEChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getAdditiveEChange()
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
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveEChangeImpl <em>Subtractive EChange</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveEChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getSubtractiveEChange()
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
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveEAttributeChangeImpl <em>Additive EAttribute Change</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveEAttributeChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getAdditiveEAttributeChange()
         * @generated
         */
        EClass ADDITIVE_EATTRIBUTE_CHANGE = eINSTANCE.getAdditiveEAttributeChange();

        /**
         * The meta object literal for the '<em><b>New Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ADDITIVE_EATTRIBUTE_CHANGE__NEW_VALUE = eINSTANCE.getAdditiveEAttributeChange_NewValue();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveEAttributeChangeImpl <em>Subtractive EAttribute Change</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveEAttributeChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getSubtractiveEAttributeChange()
         * @generated
         */
        EClass SUBTRACTIVE_EATTRIBUTE_CHANGE = eINSTANCE.getSubtractiveEAttributeChange();

        /**
         * The meta object literal for the '<em><b>Old Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SUBTRACTIVE_EATTRIBUTE_CHANGE__OLD_VALUE = eINSTANCE.getSubtractiveEAttributeChange_OldValue();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveEReferenceChangeImpl <em>Additive EReference Change</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveEReferenceChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getAdditiveEReferenceChange()
         * @generated
         */
        EClass ADDITIVE_EREFERENCE_CHANGE = eINSTANCE.getAdditiveEReferenceChange();

        /**
         * The meta object literal for the '<em><b>New Value</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ADDITIVE_EREFERENCE_CHANGE__NEW_VALUE = eINSTANCE.getAdditiveEReferenceChange_NewValue();

        /**
         * The meta object literal for the '<em><b>Is Create</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ADDITIVE_EREFERENCE_CHANGE__IS_CREATE = eINSTANCE.getAdditiveEReferenceChange_IsCreate();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveEReferenceChangeImpl <em>Subtractive EReference Change</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveEReferenceChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getSubtractiveEReferenceChange()
         * @generated
         */
        EClass SUBTRACTIVE_EREFERENCE_CHANGE = eINSTANCE.getSubtractiveEReferenceChange();

        /**
         * The meta object literal for the '<em><b>Old TUID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SUBTRACTIVE_EREFERENCE_CHANGE__OLD_TUID = eINSTANCE.getSubtractiveEReferenceChange_OldTUID();

        /**
         * The meta object literal for the '<em><b>Feature2 Old Value Map</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SUBTRACTIVE_EREFERENCE_CHANGE__FEATURE2_OLD_VALUE_MAP = eINSTANCE.getSubtractiveEReferenceChange_Feature2OldValueMap();

        /**
         * The meta object literal for the '<em><b>Is Delete</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SUBTRACTIVE_EREFERENCE_CHANGE__IS_DELETE = eINSTANCE.getSubtractiveEReferenceChange_IsDelete();

        /**
         * The meta object literal for the '<em>TUID</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getTUID()
         * @generated
         */
        EDataType TUID = eINSTANCE.getTUID();

    }

} //ChangePackage
