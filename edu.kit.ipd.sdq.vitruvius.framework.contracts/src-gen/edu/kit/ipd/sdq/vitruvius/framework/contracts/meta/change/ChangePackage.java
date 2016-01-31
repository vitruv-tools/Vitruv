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
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveChangeImpl <em>Additive Change</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getAdditiveChange()
     * @generated
     */
    int ADDITIVE_CHANGE = 1;

    /**
     * The number of structural features of the '<em>Additive Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_CHANGE_FEATURE_COUNT = ECHANGE_FEATURE_COUNT + 0;

    /**
     * The operation id for the '<em>Get New Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_CHANGE___GET_NEW_VALUE = ECHANGE_OPERATION_COUNT + 0;

    /**
     * The number of operations of the '<em>Additive Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_CHANGE_OPERATION_COUNT = ECHANGE_OPERATION_COUNT + 1;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveChangeImpl <em>Subtractive Change</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getSubtractiveChange()
     * @generated
     */
    int SUBTRACTIVE_CHANGE = 2;

    /**
     * The number of structural features of the '<em>Subtractive Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_CHANGE_FEATURE_COUNT = ECHANGE_FEATURE_COUNT + 0;

    /**
     * The operation id for the '<em>Get Old Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_CHANGE___GET_OLD_VALUE = ECHANGE_OPERATION_COUNT + 0;

    /**
     * The number of operations of the '<em>Subtractive Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_CHANGE_OPERATION_COUNT = ECHANGE_OPERATION_COUNT + 1;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ReplaciveChangeImpl <em>Replacive Change</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ReplaciveChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getReplaciveChange()
     * @generated
     */
    int REPLACIVE_CHANGE = 3;

    /**
     * The number of structural features of the '<em>Replacive Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACIVE_CHANGE_FEATURE_COUNT = SUBTRACTIVE_CHANGE_FEATURE_COUNT + 0;

    /**
     * The operation id for the '<em>Get Old Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACIVE_CHANGE___GET_OLD_VALUE = SUBTRACTIVE_CHANGE___GET_OLD_VALUE;

    /**
     * The operation id for the '<em>Get New Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACIVE_CHANGE___GET_NEW_VALUE = SUBTRACTIVE_CHANGE_OPERATION_COUNT + 0;

    /**
     * The number of operations of the '<em>Replacive Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACIVE_CHANGE_OPERATION_COUNT = SUBTRACTIVE_CHANGE_OPERATION_COUNT + 1;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveAttributeChangeImpl <em>Additive Attribute Change</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveAttributeChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getAdditiveAttributeChange()
     * @generated
     */
    int ADDITIVE_ATTRIBUTE_CHANGE = 4;

    /**
     * The feature id for the '<em><b>New Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_ATTRIBUTE_CHANGE__NEW_VALUE = ADDITIVE_CHANGE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Additive Attribute Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_ATTRIBUTE_CHANGE_FEATURE_COUNT = ADDITIVE_CHANGE_FEATURE_COUNT + 1;

    /**
     * The operation id for the '<em>Get New Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_ATTRIBUTE_CHANGE___GET_NEW_VALUE = ADDITIVE_CHANGE___GET_NEW_VALUE;

    /**
     * The number of operations of the '<em>Additive Attribute Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_ATTRIBUTE_CHANGE_OPERATION_COUNT = ADDITIVE_CHANGE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveAttributeChangeImpl <em>Subtractive Attribute Change</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveAttributeChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getSubtractiveAttributeChange()
     * @generated
     */
    int SUBTRACTIVE_ATTRIBUTE_CHANGE = 5;

    /**
     * The feature id for the '<em><b>Old Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_ATTRIBUTE_CHANGE__OLD_VALUE = SUBTRACTIVE_CHANGE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Subtractive Attribute Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_ATTRIBUTE_CHANGE_FEATURE_COUNT = SUBTRACTIVE_CHANGE_FEATURE_COUNT + 1;

    /**
     * The operation id for the '<em>Get Old Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_ATTRIBUTE_CHANGE___GET_OLD_VALUE = SUBTRACTIVE_CHANGE___GET_OLD_VALUE;

    /**
     * The number of operations of the '<em>Subtractive Attribute Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_ATTRIBUTE_CHANGE_OPERATION_COUNT = SUBTRACTIVE_CHANGE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ReplaciveAttributeChangeImpl <em>Replacive Attribute Change</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ReplaciveAttributeChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getReplaciveAttributeChange()
     * @generated
     */
    int REPLACIVE_ATTRIBUTE_CHANGE = 6;

    /**
     * The feature id for the '<em><b>Old Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACIVE_ATTRIBUTE_CHANGE__OLD_VALUE = SUBTRACTIVE_ATTRIBUTE_CHANGE__OLD_VALUE;

    /**
     * The feature id for the '<em><b>New Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACIVE_ATTRIBUTE_CHANGE__NEW_VALUE = SUBTRACTIVE_ATTRIBUTE_CHANGE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Replacive Attribute Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACIVE_ATTRIBUTE_CHANGE_FEATURE_COUNT = SUBTRACTIVE_ATTRIBUTE_CHANGE_FEATURE_COUNT + 1;

    /**
     * The operation id for the '<em>Get Old Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACIVE_ATTRIBUTE_CHANGE___GET_OLD_VALUE = SUBTRACTIVE_ATTRIBUTE_CHANGE___GET_OLD_VALUE;

    /**
     * The operation id for the '<em>Get New Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACIVE_ATTRIBUTE_CHANGE___GET_NEW_VALUE = SUBTRACTIVE_ATTRIBUTE_CHANGE_OPERATION_COUNT + 0;

    /**
     * The number of operations of the '<em>Replacive Attribute Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACIVE_ATTRIBUTE_CHANGE_OPERATION_COUNT = SUBTRACTIVE_ATTRIBUTE_CHANGE_OPERATION_COUNT + 1;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveReferenceChangeImpl <em>Additive Reference Change</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveReferenceChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getAdditiveReferenceChange()
     * @generated
     */
    int ADDITIVE_REFERENCE_CHANGE = 7;

    /**
     * The feature id for the '<em><b>New Value</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_REFERENCE_CHANGE__NEW_VALUE = ADDITIVE_CHANGE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Additive Reference Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_REFERENCE_CHANGE_FEATURE_COUNT = ADDITIVE_CHANGE_FEATURE_COUNT + 1;

    /**
     * The operation id for the '<em>Get New Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_REFERENCE_CHANGE___GET_NEW_VALUE = ADDITIVE_CHANGE___GET_NEW_VALUE;

    /**
     * The number of operations of the '<em>Additive Reference Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADDITIVE_REFERENCE_CHANGE_OPERATION_COUNT = ADDITIVE_CHANGE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveReferenceChangeImpl <em>Subtractive Reference Change</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveReferenceChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getSubtractiveReferenceChange()
     * @generated
     */
    int SUBTRACTIVE_REFERENCE_CHANGE = 8;

    /**
     * The feature id for the '<em><b>Old TUID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_REFERENCE_CHANGE__OLD_TUID = SUBTRACTIVE_CHANGE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Feature Name2 Old Value Map</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_REFERENCE_CHANGE__FEATURE_NAME2_OLD_VALUE_MAP = SUBTRACTIVE_CHANGE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Subtractive Reference Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_REFERENCE_CHANGE_FEATURE_COUNT = SUBTRACTIVE_CHANGE_FEATURE_COUNT + 2;

    /**
     * The operation id for the '<em>Get Old Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_REFERENCE_CHANGE___GET_OLD_VALUE = SUBTRACTIVE_CHANGE___GET_OLD_VALUE;

    /**
     * The number of operations of the '<em>Subtractive Reference Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUBTRACTIVE_REFERENCE_CHANGE_OPERATION_COUNT = SUBTRACTIVE_CHANGE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ReplaciveReferenceChangeImpl <em>Replacive Reference Change</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ReplaciveReferenceChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getReplaciveReferenceChange()
     * @generated
     */
    int REPLACIVE_REFERENCE_CHANGE = 9;

    /**
     * The feature id for the '<em><b>Old TUID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACIVE_REFERENCE_CHANGE__OLD_TUID = SUBTRACTIVE_REFERENCE_CHANGE__OLD_TUID;

    /**
     * The feature id for the '<em><b>Feature Name2 Old Value Map</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACIVE_REFERENCE_CHANGE__FEATURE_NAME2_OLD_VALUE_MAP = SUBTRACTIVE_REFERENCE_CHANGE__FEATURE_NAME2_OLD_VALUE_MAP;

    /**
     * The feature id for the '<em><b>New Value</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACIVE_REFERENCE_CHANGE__NEW_VALUE = SUBTRACTIVE_REFERENCE_CHANGE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Replacive Reference Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACIVE_REFERENCE_CHANGE_FEATURE_COUNT = SUBTRACTIVE_REFERENCE_CHANGE_FEATURE_COUNT + 1;

    /**
     * The operation id for the '<em>Get Old Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACIVE_REFERENCE_CHANGE___GET_OLD_VALUE = SUBTRACTIVE_REFERENCE_CHANGE___GET_OLD_VALUE;

    /**
     * The operation id for the '<em>Get New Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACIVE_REFERENCE_CHANGE___GET_NEW_VALUE = SUBTRACTIVE_REFERENCE_CHANGE_OPERATION_COUNT + 0;

    /**
     * The number of operations of the '<em>Replacive Reference Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACIVE_REFERENCE_CHANGE_OPERATION_COUNT = SUBTRACTIVE_REFERENCE_CHANGE_OPERATION_COUNT + 1;

    /**
     * The meta object id for the '<em>TUID</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getTUID()
     * @generated
     */
    int TUID = 10;


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
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveChange <em>Additive Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Additive Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveChange
     * @generated
     */
    EClass getAdditiveChange();

    /**
     * Returns the meta object for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveChange#getNewValue() <em>Get New Value</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the '<em>Get New Value</em>' operation.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveChange#getNewValue()
     * @generated
     */
    EOperation getAdditiveChange__GetNewValue();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveChange <em>Subtractive Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Subtractive Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveChange
     * @generated
     */
    EClass getSubtractiveChange();

    /**
     * Returns the meta object for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveChange#getOldValue() <em>Get Old Value</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the '<em>Get Old Value</em>' operation.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveChange#getOldValue()
     * @generated
     */
    EOperation getSubtractiveChange__GetOldValue();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ReplaciveChange <em>Replacive Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Replacive Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ReplaciveChange
     * @generated
     */
    EClass getReplaciveChange();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveAttributeChange <em>Additive Attribute Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Additive Attribute Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveAttributeChange
     * @generated
     */
    EClass getAdditiveAttributeChange();

    /**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveAttributeChange#getNewValue <em>New Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>New Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveAttributeChange#getNewValue()
     * @see #getAdditiveAttributeChange()
     * @generated
     */
    EAttribute getAdditiveAttributeChange_NewValue();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveAttributeChange <em>Subtractive Attribute Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Subtractive Attribute Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveAttributeChange
     * @generated
     */
    EClass getSubtractiveAttributeChange();

    /**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveAttributeChange#getOldValue <em>Old Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Old Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveAttributeChange#getOldValue()
     * @see #getSubtractiveAttributeChange()
     * @generated
     */
    EAttribute getSubtractiveAttributeChange_OldValue();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ReplaciveAttributeChange <em>Replacive Attribute Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Replacive Attribute Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ReplaciveAttributeChange
     * @generated
     */
    EClass getReplaciveAttributeChange();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveReferenceChange <em>Additive Reference Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Additive Reference Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveReferenceChange
     * @generated
     */
    EClass getAdditiveReferenceChange();

    /**
     * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveReferenceChange#getNewValue <em>New Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>New Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveReferenceChange#getNewValue()
     * @see #getAdditiveReferenceChange()
     * @generated
     */
    EReference getAdditiveReferenceChange_NewValue();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange <em>Subtractive Reference Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Subtractive Reference Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange
     * @generated
     */
    EClass getSubtractiveReferenceChange();

    /**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange#getOldTUID <em>Old TUID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Old TUID</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange#getOldTUID()
     * @see #getSubtractiveReferenceChange()
     * @generated
     */
    EAttribute getSubtractiveReferenceChange_OldTUID();

    /**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange#getFeatureName2OldValueMap <em>Feature Name2 Old Value Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Feature Name2 Old Value Map</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange#getFeatureName2OldValueMap()
     * @see #getSubtractiveReferenceChange()
     * @generated
     */
    EAttribute getSubtractiveReferenceChange_FeatureName2OldValueMap();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ReplaciveReferenceChange <em>Replacive Reference Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Replacive Reference Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ReplaciveReferenceChange
     * @generated
     */
    EClass getReplaciveReferenceChange();

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
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveChangeImpl <em>Additive Change</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getAdditiveChange()
         * @generated
         */
        EClass ADDITIVE_CHANGE = eINSTANCE.getAdditiveChange();

        /**
         * The meta object literal for the '<em><b>Get New Value</b></em>' operation.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EOperation ADDITIVE_CHANGE___GET_NEW_VALUE = eINSTANCE.getAdditiveChange__GetNewValue();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveChangeImpl <em>Subtractive Change</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getSubtractiveChange()
         * @generated
         */
        EClass SUBTRACTIVE_CHANGE = eINSTANCE.getSubtractiveChange();

        /**
         * The meta object literal for the '<em><b>Get Old Value</b></em>' operation.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EOperation SUBTRACTIVE_CHANGE___GET_OLD_VALUE = eINSTANCE.getSubtractiveChange__GetOldValue();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ReplaciveChangeImpl <em>Replacive Change</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ReplaciveChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getReplaciveChange()
         * @generated
         */
        EClass REPLACIVE_CHANGE = eINSTANCE.getReplaciveChange();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveAttributeChangeImpl <em>Additive Attribute Change</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveAttributeChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getAdditiveAttributeChange()
         * @generated
         */
        EClass ADDITIVE_ATTRIBUTE_CHANGE = eINSTANCE.getAdditiveAttributeChange();

        /**
         * The meta object literal for the '<em><b>New Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ADDITIVE_ATTRIBUTE_CHANGE__NEW_VALUE = eINSTANCE.getAdditiveAttributeChange_NewValue();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveAttributeChangeImpl <em>Subtractive Attribute Change</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveAttributeChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getSubtractiveAttributeChange()
         * @generated
         */
        EClass SUBTRACTIVE_ATTRIBUTE_CHANGE = eINSTANCE.getSubtractiveAttributeChange();

        /**
         * The meta object literal for the '<em><b>Old Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SUBTRACTIVE_ATTRIBUTE_CHANGE__OLD_VALUE = eINSTANCE.getSubtractiveAttributeChange_OldValue();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ReplaciveAttributeChangeImpl <em>Replacive Attribute Change</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ReplaciveAttributeChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getReplaciveAttributeChange()
         * @generated
         */
        EClass REPLACIVE_ATTRIBUTE_CHANGE = eINSTANCE.getReplaciveAttributeChange();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveReferenceChangeImpl <em>Additive Reference Change</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.AdditiveReferenceChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getAdditiveReferenceChange()
         * @generated
         */
        EClass ADDITIVE_REFERENCE_CHANGE = eINSTANCE.getAdditiveReferenceChange();

        /**
         * The meta object literal for the '<em><b>New Value</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ADDITIVE_REFERENCE_CHANGE__NEW_VALUE = eINSTANCE.getAdditiveReferenceChange_NewValue();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveReferenceChangeImpl <em>Subtractive Reference Change</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveReferenceChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getSubtractiveReferenceChange()
         * @generated
         */
        EClass SUBTRACTIVE_REFERENCE_CHANGE = eINSTANCE.getSubtractiveReferenceChange();

        /**
         * The meta object literal for the '<em><b>Old TUID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SUBTRACTIVE_REFERENCE_CHANGE__OLD_TUID = eINSTANCE.getSubtractiveReferenceChange_OldTUID();

        /**
         * The meta object literal for the '<em><b>Feature Name2 Old Value Map</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SUBTRACTIVE_REFERENCE_CHANGE__FEATURE_NAME2_OLD_VALUE_MAP = eINSTANCE.getSubtractiveReferenceChange_FeatureName2OldValueMap();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ReplaciveReferenceChangeImpl <em>Replacive Reference Change</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ReplaciveReferenceChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl#getReplaciveReferenceChange()
         * @generated
         */
        EClass REPLACIVE_REFERENCE_CHANGE = eINSTANCE.getReplaciveReferenceChange();

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
