/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeatureFactory
 * @model kind="package"
 * @generated
 */
public interface FeaturePackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "feature";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://edu.kit.ipd.sdq.vitruvius/Change/Feature/1.0";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "feature";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    FeaturePackage eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.FeaturePackageImpl.init();

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.FeatureEChangeImpl <em>EChange</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.FeatureEChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.FeaturePackageImpl#getFeatureEChange()
     * @generated
     */
    int FEATURE_ECHANGE = 0;

    /**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FEATURE_ECHANGE__AFFECTED_FEATURE = ChangePackage.ATOMIC_ECHANGE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FEATURE_ECHANGE__AFFECTED_EOBJECT = ChangePackage.ATOMIC_ECHANGE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>EChange</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FEATURE_ECHANGE_FEATURE_COUNT = ChangePackage.ATOMIC_ECHANGE_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>EChange</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FEATURE_ECHANGE_OPERATION_COUNT = ChangePackage.ATOMIC_ECHANGE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.UpdateMultiValuedFeatureEChangeImpl <em>Update Multi Valued Feature EChange</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.UpdateMultiValuedFeatureEChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.FeaturePackageImpl#getUpdateMultiValuedFeatureEChange()
     * @generated
     */
    int UPDATE_MULTI_VALUED_FEATURE_ECHANGE = 1;

    /**
     * The number of structural features of the '<em>Update Multi Valued Feature EChange</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPDATE_MULTI_VALUED_FEATURE_ECHANGE_FEATURE_COUNT = 0;

    /**
     * The number of operations of the '<em>Update Multi Valued Feature EChange</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPDATE_MULTI_VALUED_FEATURE_ECHANGE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.UpdateSingleValuedFeatureEChangeImpl <em>Update Single Valued Feature EChange</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.UpdateSingleValuedFeatureEChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.FeaturePackageImpl#getUpdateSingleValuedFeatureEChange()
     * @generated
     */
    int UPDATE_SINGLE_VALUED_FEATURE_ECHANGE = 2;

    /**
     * The number of structural features of the '<em>Update Single Valued Feature EChange</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPDATE_SINGLE_VALUED_FEATURE_ECHANGE_FEATURE_COUNT = 0;

    /**
     * The number of operations of the '<em>Update Single Valued Feature EChange</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPDATE_SINGLE_VALUED_FEATURE_ECHANGE_OPERATION_COUNT = 0;


    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeatureEChange <em>EChange</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>EChange</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeatureEChange
     * @generated
     */
    EClass getFeatureEChange();

    /**
     * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeatureEChange#getAffectedFeature <em>Affected Feature</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Affected Feature</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeatureEChange#getAffectedFeature()
     * @see #getFeatureEChange()
     * @generated
     */
    EReference getFeatureEChange_AffectedFeature();

    /**
     * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeatureEChange#getAffectedEObject <em>Affected EObject</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Affected EObject</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeatureEChange#getAffectedEObject()
     * @see #getFeatureEChange()
     * @generated
     */
    EReference getFeatureEChange_AffectedEObject();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateMultiValuedFeatureEChange <em>Update Multi Valued Feature EChange</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Update Multi Valued Feature EChange</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateMultiValuedFeatureEChange
     * @generated
     */
    EClass getUpdateMultiValuedFeatureEChange();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateSingleValuedFeatureEChange <em>Update Single Valued Feature EChange</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Update Single Valued Feature EChange</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateSingleValuedFeatureEChange
     * @generated
     */
    EClass getUpdateSingleValuedFeatureEChange();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    FeatureFactory getFeatureFactory();

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
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.FeatureEChangeImpl <em>EChange</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.FeatureEChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.FeaturePackageImpl#getFeatureEChange()
         * @generated
         */
        EClass FEATURE_ECHANGE = eINSTANCE.getFeatureEChange();

        /**
         * The meta object literal for the '<em><b>Affected Feature</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FEATURE_ECHANGE__AFFECTED_FEATURE = eINSTANCE.getFeatureEChange_AffectedFeature();

        /**
         * The meta object literal for the '<em><b>Affected EObject</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FEATURE_ECHANGE__AFFECTED_EOBJECT = eINSTANCE.getFeatureEChange_AffectedEObject();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.UpdateMultiValuedFeatureEChangeImpl <em>Update Multi Valued Feature EChange</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.UpdateMultiValuedFeatureEChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.FeaturePackageImpl#getUpdateMultiValuedFeatureEChange()
         * @generated
         */
        EClass UPDATE_MULTI_VALUED_FEATURE_ECHANGE = eINSTANCE.getUpdateMultiValuedFeatureEChange();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.UpdateSingleValuedFeatureEChangeImpl <em>Update Single Valued Feature EChange</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.UpdateSingleValuedFeatureEChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.FeaturePackageImpl#getUpdateSingleValuedFeatureEChange()
         * @generated
         */
        EClass UPDATE_SINGLE_VALUED_FEATURE_ECHANGE = eINSTANCE.getUpdateSingleValuedFeatureEChange();

    }

} //FeaturePackage
