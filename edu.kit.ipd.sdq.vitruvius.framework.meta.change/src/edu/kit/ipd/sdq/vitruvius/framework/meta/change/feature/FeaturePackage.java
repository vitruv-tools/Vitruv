/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage;

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
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeatureFactory
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
	FeaturePackage eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl.init();

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.EFeatureChangeImpl <em>EFeature Change</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.EFeatureChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl#getEFeatureChange()
     * @generated
     */
	int EFEATURE_CHANGE = 0;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int EFEATURE_CHANGE__AFFECTED_FEATURE = ChangePackage.ECHANGE_FEATURE_COUNT + 0;

	/**
     * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int EFEATURE_CHANGE__OLD_AFFECTED_EOBJECT = ChangePackage.ECHANGE_FEATURE_COUNT + 1;

	/**
     * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int EFEATURE_CHANGE__NEW_AFFECTED_EOBJECT = ChangePackage.ECHANGE_FEATURE_COUNT + 2;

	/**
     * The number of structural features of the '<em>EFeature Change</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int EFEATURE_CHANGE_FEATURE_COUNT = ChangePackage.ECHANGE_FEATURE_COUNT + 3;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UpdateEFeatureImpl <em>Update EFeature</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UpdateEFeatureImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl#getUpdateEFeature()
     * @generated
     */
	int UPDATE_EFEATURE = 1;

	/**
     * The number of structural features of the '<em>Update EFeature</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_EFEATURE_FEATURE_COUNT = 0;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UpdateMultiValuedEFeatureImpl <em>Update Multi Valued EFeature</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UpdateMultiValuedEFeatureImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl#getUpdateMultiValuedEFeature()
     * @generated
     */
	int UPDATE_MULTI_VALUED_EFEATURE = 2;

	/**
     * The number of structural features of the '<em>Update Multi Valued EFeature</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_MULTI_VALUED_EFEATURE_FEATURE_COUNT = UPDATE_EFEATURE_FEATURE_COUNT + 0;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UpdateSingleValuedEFeatureImpl <em>Update Single Valued EFeature</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UpdateSingleValuedEFeatureImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl#getUpdateSingleValuedEFeature()
     * @generated
     */
	int UPDATE_SINGLE_VALUED_EFEATURE = 3;

	/**
     * The number of structural features of the '<em>Update Single Valued EFeature</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_SINGLE_VALUED_EFEATURE_FEATURE_COUNT = UPDATE_EFEATURE_FEATURE_COUNT + 0;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetEFeatureImpl <em>Unset EFeature</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetEFeatureImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl#getUnsetEFeature()
     * @generated
     */
	int UNSET_EFEATURE = 4;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_EFEATURE__AFFECTED_FEATURE = EFEATURE_CHANGE__AFFECTED_FEATURE;

	/**
     * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_EFEATURE__OLD_AFFECTED_EOBJECT = EFEATURE_CHANGE__OLD_AFFECTED_EOBJECT;

	/**
     * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_EFEATURE__NEW_AFFECTED_EOBJECT = EFEATURE_CHANGE__NEW_AFFECTED_EOBJECT;

	/**
     * The number of structural features of the '<em>Unset EFeature</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_EFEATURE_FEATURE_COUNT = EFEATURE_CHANGE_FEATURE_COUNT + 0;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetEAttributeImpl <em>Unset EAttribute</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetEAttributeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl#getUnsetEAttribute()
     * @generated
     */
	int UNSET_EATTRIBUTE = 5;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_EATTRIBUTE__AFFECTED_FEATURE = UNSET_EFEATURE__AFFECTED_FEATURE;

	/**
     * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_EATTRIBUTE__OLD_AFFECTED_EOBJECT = UNSET_EFEATURE__OLD_AFFECTED_EOBJECT;

	/**
     * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_EATTRIBUTE__NEW_AFFECTED_EOBJECT = UNSET_EFEATURE__NEW_AFFECTED_EOBJECT;

	/**
     * The feature id for the '<em><b>Old Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_EATTRIBUTE__OLD_VALUE = UNSET_EFEATURE_FEATURE_COUNT + 0;

	/**
     * The number of structural features of the '<em>Unset EAttribute</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_EATTRIBUTE_FEATURE_COUNT = UNSET_EFEATURE_FEATURE_COUNT + 1;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetEReferenceImpl <em>Unset EReference</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetEReferenceImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl#getUnsetEReference()
     * @generated
     */
	int UNSET_EREFERENCE = 6;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_EREFERENCE__AFFECTED_FEATURE = UNSET_EFEATURE__AFFECTED_FEATURE;

	/**
     * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_EREFERENCE__OLD_AFFECTED_EOBJECT = UNSET_EFEATURE__OLD_AFFECTED_EOBJECT;

	/**
     * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_EREFERENCE__NEW_AFFECTED_EOBJECT = UNSET_EFEATURE__NEW_AFFECTED_EOBJECT;

	/**
     * The feature id for the '<em><b>Old Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_EREFERENCE__OLD_VALUE = UNSET_EFEATURE_FEATURE_COUNT + 0;

	/**
     * The number of structural features of the '<em>Unset EReference</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_EREFERENCE_FEATURE_COUNT = UNSET_EFEATURE_FEATURE_COUNT + 1;


	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetNonContainmentEReferenceImpl <em>Unset Non Containment EReference</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetNonContainmentEReferenceImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl#getUnsetNonContainmentEReference()
     * @generated
     */
	int UNSET_NON_CONTAINMENT_EREFERENCE = 7;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_NON_CONTAINMENT_EREFERENCE__AFFECTED_FEATURE = UNSET_EREFERENCE__AFFECTED_FEATURE;

	/**
     * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_NON_CONTAINMENT_EREFERENCE__OLD_AFFECTED_EOBJECT = UNSET_EREFERENCE__OLD_AFFECTED_EOBJECT;

	/**
     * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_NON_CONTAINMENT_EREFERENCE__NEW_AFFECTED_EOBJECT = UNSET_EREFERENCE__NEW_AFFECTED_EOBJECT;

	/**
     * The feature id for the '<em><b>Old Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_NON_CONTAINMENT_EREFERENCE__OLD_VALUE = UNSET_EREFERENCE__OLD_VALUE;

	/**
     * The number of structural features of the '<em>Unset Non Containment EReference</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_NON_CONTAINMENT_EREFERENCE_FEATURE_COUNT = UNSET_EREFERENCE_FEATURE_COUNT + 0;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetContainmentEReferenceImpl <em>Unset Containment EReference</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetContainmentEReferenceImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl#getUnsetContainmentEReference()
     * @generated
     */
	int UNSET_CONTAINMENT_EREFERENCE = 8;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_CONTAINMENT_EREFERENCE__AFFECTED_FEATURE = UNSET_EREFERENCE__AFFECTED_FEATURE;

	/**
     * The feature id for the '<em><b>Old Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_CONTAINMENT_EREFERENCE__OLD_AFFECTED_EOBJECT = UNSET_EREFERENCE__OLD_AFFECTED_EOBJECT;

	/**
     * The feature id for the '<em><b>New Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_CONTAINMENT_EREFERENCE__NEW_AFFECTED_EOBJECT = UNSET_EREFERENCE__NEW_AFFECTED_EOBJECT;

	/**
     * The feature id for the '<em><b>Old Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_CONTAINMENT_EREFERENCE__OLD_VALUE = UNSET_EREFERENCE__OLD_VALUE;

	/**
     * The number of structural features of the '<em>Unset Containment EReference</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_CONTAINMENT_EREFERENCE_FEATURE_COUNT = UNSET_EREFERENCE_FEATURE_COUNT + 0;


	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange <em>EFeature Change</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>EFeature Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange
     * @generated
     */
	EClass getEFeatureChange();

	/**
     * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange#getAffectedFeature <em>Affected Feature</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Affected Feature</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange#getAffectedFeature()
     * @see #getEFeatureChange()
     * @generated
     */
	EReference getEFeatureChange_AffectedFeature();

	/**
     * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange#getOldAffectedEObject <em>Old Affected EObject</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Old Affected EObject</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange#getOldAffectedEObject()
     * @see #getEFeatureChange()
     * @generated
     */
	EReference getEFeatureChange_OldAffectedEObject();

	/**
     * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange#getNewAffectedEObject <em>New Affected EObject</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>New Affected EObject</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange#getNewAffectedEObject()
     * @see #getEFeatureChange()
     * @generated
     */
	EReference getEFeatureChange_NewAffectedEObject();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UpdateEFeature <em>Update EFeature</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Update EFeature</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UpdateEFeature
     * @generated
     */
	EClass getUpdateEFeature();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UpdateMultiValuedEFeature <em>Update Multi Valued EFeature</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Update Multi Valued EFeature</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UpdateMultiValuedEFeature
     * @generated
     */
	EClass getUpdateMultiValuedEFeature();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UpdateSingleValuedEFeature <em>Update Single Valued EFeature</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Update Single Valued EFeature</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UpdateSingleValuedEFeature
     * @generated
     */
	EClass getUpdateSingleValuedEFeature();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEFeature <em>Unset EFeature</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Unset EFeature</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEFeature
     * @generated
     */
	EClass getUnsetEFeature();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEAttribute <em>Unset EAttribute</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Unset EAttribute</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEAttribute
     * @generated
     */
	EClass getUnsetEAttribute();

	/**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEAttribute#getOldValue <em>Old Value</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Old Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEAttribute#getOldValue()
     * @see #getUnsetEAttribute()
     * @generated
     */
	EAttribute getUnsetEAttribute_OldValue();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEReference <em>Unset EReference</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Unset EReference</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEReference
     * @generated
     */
	EClass getUnsetEReference();

	/**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEReference#getOldValue <em>Old Value</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Old Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEReference#getOldValue()
     * @see #getUnsetEReference()
     * @generated
     */
	EAttribute getUnsetEReference_OldValue();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetNonContainmentEReference <em>Unset Non Containment EReference</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Unset Non Containment EReference</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetNonContainmentEReference
     * @generated
     */
	EClass getUnsetNonContainmentEReference();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetContainmentEReference <em>Unset Containment EReference</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Unset Containment EReference</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetContainmentEReference
     * @generated
     */
	EClass getUnsetContainmentEReference();

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
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
     * @generated
     */
	interface Literals {
		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.EFeatureChangeImpl <em>EFeature Change</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.EFeatureChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl#getEFeatureChange()
         * @generated
         */
		EClass EFEATURE_CHANGE = eINSTANCE.getEFeatureChange();

		/**
         * The meta object literal for the '<em><b>Affected Feature</b></em>' reference feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference EFEATURE_CHANGE__AFFECTED_FEATURE = eINSTANCE.getEFeatureChange_AffectedFeature();

		/**
         * The meta object literal for the '<em><b>Old Affected EObject</b></em>' reference feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference EFEATURE_CHANGE__OLD_AFFECTED_EOBJECT = eINSTANCE.getEFeatureChange_OldAffectedEObject();

		/**
         * The meta object literal for the '<em><b>New Affected EObject</b></em>' reference feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference EFEATURE_CHANGE__NEW_AFFECTED_EOBJECT = eINSTANCE.getEFeatureChange_NewAffectedEObject();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UpdateEFeatureImpl <em>Update EFeature</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UpdateEFeatureImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl#getUpdateEFeature()
         * @generated
         */
		EClass UPDATE_EFEATURE = eINSTANCE.getUpdateEFeature();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UpdateMultiValuedEFeatureImpl <em>Update Multi Valued EFeature</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UpdateMultiValuedEFeatureImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl#getUpdateMultiValuedEFeature()
         * @generated
         */
		EClass UPDATE_MULTI_VALUED_EFEATURE = eINSTANCE.getUpdateMultiValuedEFeature();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UpdateSingleValuedEFeatureImpl <em>Update Single Valued EFeature</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UpdateSingleValuedEFeatureImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl#getUpdateSingleValuedEFeature()
         * @generated
         */
		EClass UPDATE_SINGLE_VALUED_EFEATURE = eINSTANCE.getUpdateSingleValuedEFeature();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetEFeatureImpl <em>Unset EFeature</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetEFeatureImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl#getUnsetEFeature()
         * @generated
         */
		EClass UNSET_EFEATURE = eINSTANCE.getUnsetEFeature();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetEAttributeImpl <em>Unset EAttribute</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetEAttributeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl#getUnsetEAttribute()
         * @generated
         */
		EClass UNSET_EATTRIBUTE = eINSTANCE.getUnsetEAttribute();

		/**
         * The meta object literal for the '<em><b>Old Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute UNSET_EATTRIBUTE__OLD_VALUE = eINSTANCE.getUnsetEAttribute_OldValue();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetEReferenceImpl <em>Unset EReference</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetEReferenceImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl#getUnsetEReference()
         * @generated
         */
		EClass UNSET_EREFERENCE = eINSTANCE.getUnsetEReference();

		/**
         * The meta object literal for the '<em><b>Old Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute UNSET_EREFERENCE__OLD_VALUE = eINSTANCE.getUnsetEReference_OldValue();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetNonContainmentEReferenceImpl <em>Unset Non Containment EReference</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetNonContainmentEReferenceImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl#getUnsetNonContainmentEReference()
         * @generated
         */
		EClass UNSET_NON_CONTAINMENT_EREFERENCE = eINSTANCE.getUnsetNonContainmentEReference();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetContainmentEReferenceImpl <em>Unset Containment EReference</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.UnsetContainmentEReferenceImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl#getUnsetContainmentEReference()
         * @generated
         */
		EClass UNSET_CONTAINMENT_EREFERENCE = eINSTANCE.getUnsetContainmentEReference();

	}

} //FeaturePackage
