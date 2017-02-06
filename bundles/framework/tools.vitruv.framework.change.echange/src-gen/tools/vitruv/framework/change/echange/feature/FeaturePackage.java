/**
 */
package tools.vitruv.framework.change.echange.feature;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
 * @see tools.vitruv.framework.change.echange.feature.FeatureFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel modelName='Change' commentFormatting='true' featureMapWrapperInternalInterface='' classNamePattern='' basePackage='tools.vitruv.framework.change.echange'"
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
	String eNS_URI = "http://tools.vitruv/EChange/Feature/1.0";

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
	FeaturePackage eINSTANCE = tools.vitruv.framework.change.echange.feature.impl.FeaturePackageImpl.init();

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.feature.impl.FeatureEChangeImpl <em>EChange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.feature.impl.FeatureEChangeImpl
	 * @see tools.vitruv.framework.change.echange.feature.impl.FeaturePackageImpl#getFeatureEChange()
	 * @generated
	 */
	int FEATURE_ECHANGE = 0;

	/**
	 * The feature id for the '<em><b>Resolved</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_ECHANGE__RESOLVED = EChangePackage.ATOMIC_ECHANGE__RESOLVED;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_ECHANGE__AFFECTED_FEATURE = EChangePackage.ATOMIC_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_ECHANGE__AFFECTED_EOBJECT = EChangePackage.ATOMIC_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Proxy Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_ECHANGE__PROXY_OBJECT = EChangePackage.ATOMIC_ECHANGE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_ECHANGE_FEATURE_COUNT = EChangePackage.ATOMIC_ECHANGE_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Resolve</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_ECHANGE___RESOLVE__RESOURCESET = EChangePackage.ATOMIC_ECHANGE___RESOLVE__RESOURCESET;

	/**
	 * The operation id for the '<em>Get Apply Command</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_ECHANGE___GET_APPLY_COMMAND = EChangePackage.ATOMIC_ECHANGE___GET_APPLY_COMMAND;

	/**
	 * The operation id for the '<em>Apply</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_ECHANGE___APPLY = EChangePackage.ATOMIC_ECHANGE___APPLY;

	/**
	 * The operation id for the '<em>Get Revert Command</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_ECHANGE___GET_REVERT_COMMAND = EChangePackage.ATOMIC_ECHANGE___GET_REVERT_COMMAND;

	/**
	 * The operation id for the '<em>Revert</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_ECHANGE___REVERT = EChangePackage.ATOMIC_ECHANGE___REVERT;

	/**
	 * The number of operations of the '<em>EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_ECHANGE_OPERATION_COUNT = EChangePackage.ATOMIC_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.feature.impl.UpdateMultiValuedFeatureEChangeImpl <em>Update Multi Valued Feature EChange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.feature.impl.UpdateMultiValuedFeatureEChangeImpl
	 * @see tools.vitruv.framework.change.echange.feature.impl.FeaturePackageImpl#getUpdateMultiValuedFeatureEChange()
	 * @generated
	 */
	int UPDATE_MULTI_VALUED_FEATURE_ECHANGE = 1;

	/**
	 * The feature id for the '<em><b>Resolved</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_MULTI_VALUED_FEATURE_ECHANGE__RESOLVED = FEATURE_ECHANGE__RESOLVED;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_MULTI_VALUED_FEATURE_ECHANGE__AFFECTED_FEATURE = FEATURE_ECHANGE__AFFECTED_FEATURE;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_MULTI_VALUED_FEATURE_ECHANGE__AFFECTED_EOBJECT = FEATURE_ECHANGE__AFFECTED_EOBJECT;

	/**
	 * The feature id for the '<em><b>Proxy Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_MULTI_VALUED_FEATURE_ECHANGE__PROXY_OBJECT = FEATURE_ECHANGE__PROXY_OBJECT;

	/**
	 * The number of structural features of the '<em>Update Multi Valued Feature EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_MULTI_VALUED_FEATURE_ECHANGE_FEATURE_COUNT = FEATURE_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Resolve</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_MULTI_VALUED_FEATURE_ECHANGE___RESOLVE__RESOURCESET = FEATURE_ECHANGE___RESOLVE__RESOURCESET;

	/**
	 * The operation id for the '<em>Get Apply Command</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_MULTI_VALUED_FEATURE_ECHANGE___GET_APPLY_COMMAND = FEATURE_ECHANGE___GET_APPLY_COMMAND;

	/**
	 * The operation id for the '<em>Apply</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_MULTI_VALUED_FEATURE_ECHANGE___APPLY = FEATURE_ECHANGE___APPLY;

	/**
	 * The operation id for the '<em>Get Revert Command</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_MULTI_VALUED_FEATURE_ECHANGE___GET_REVERT_COMMAND = FEATURE_ECHANGE___GET_REVERT_COMMAND;

	/**
	 * The operation id for the '<em>Revert</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_MULTI_VALUED_FEATURE_ECHANGE___REVERT = FEATURE_ECHANGE___REVERT;

	/**
	 * The number of operations of the '<em>Update Multi Valued Feature EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_MULTI_VALUED_FEATURE_ECHANGE_OPERATION_COUNT = FEATURE_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.feature.impl.UpdateSingleValuedFeatureEChangeImpl <em>Update Single Valued Feature EChange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.feature.impl.UpdateSingleValuedFeatureEChangeImpl
	 * @see tools.vitruv.framework.change.echange.feature.impl.FeaturePackageImpl#getUpdateSingleValuedFeatureEChange()
	 * @generated
	 */
	int UPDATE_SINGLE_VALUED_FEATURE_ECHANGE = 2;

	/**
	 * The feature id for the '<em><b>Resolved</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_FEATURE_ECHANGE__RESOLVED = FEATURE_ECHANGE__RESOLVED;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_FEATURE_ECHANGE__AFFECTED_FEATURE = FEATURE_ECHANGE__AFFECTED_FEATURE;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_FEATURE_ECHANGE__AFFECTED_EOBJECT = FEATURE_ECHANGE__AFFECTED_EOBJECT;

	/**
	 * The feature id for the '<em><b>Proxy Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_FEATURE_ECHANGE__PROXY_OBJECT = FEATURE_ECHANGE__PROXY_OBJECT;

	/**
	 * The number of structural features of the '<em>Update Single Valued Feature EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_FEATURE_ECHANGE_FEATURE_COUNT = FEATURE_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Resolve</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_FEATURE_ECHANGE___RESOLVE__RESOURCESET = FEATURE_ECHANGE___RESOLVE__RESOURCESET;

	/**
	 * The operation id for the '<em>Get Apply Command</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_FEATURE_ECHANGE___GET_APPLY_COMMAND = FEATURE_ECHANGE___GET_APPLY_COMMAND;

	/**
	 * The operation id for the '<em>Apply</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_FEATURE_ECHANGE___APPLY = FEATURE_ECHANGE___APPLY;

	/**
	 * The operation id for the '<em>Get Revert Command</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_FEATURE_ECHANGE___GET_REVERT_COMMAND = FEATURE_ECHANGE___GET_REVERT_COMMAND;

	/**
	 * The operation id for the '<em>Revert</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_FEATURE_ECHANGE___REVERT = FEATURE_ECHANGE___REVERT;

	/**
	 * The number of operations of the '<em>Update Single Valued Feature EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_SINGLE_VALUED_FEATURE_ECHANGE_OPERATION_COUNT = FEATURE_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '<em>EObj</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EObject
	 * @see tools.vitruv.framework.change.echange.feature.impl.FeaturePackageImpl#getEObj()
	 * @generated
	 */
	int EOBJ = 3;

	/**
	 * The meta object id for the '<em>EFeat</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EStructuralFeature
	 * @see tools.vitruv.framework.change.echange.feature.impl.FeaturePackageImpl#getEFeat()
	 * @generated
	 */
	int EFEAT = 4;

	/**
	 * The meta object id for the '<em>Proxy</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.InternalEObject
	 * @see tools.vitruv.framework.change.echange.feature.impl.FeaturePackageImpl#getProxy()
	 * @generated
	 */
	int PROXY = 5;


	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.feature.FeatureEChange <em>EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.feature.FeatureEChange
	 * @generated
	 */
	EClass getFeatureEChange();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.change.echange.feature.FeatureEChange#getAffectedFeature <em>Affected Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Affected Feature</em>'.
	 * @see tools.vitruv.framework.change.echange.feature.FeatureEChange#getAffectedFeature()
	 * @see #getFeatureEChange()
	 * @generated
	 */
	EReference getFeatureEChange_AffectedFeature();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.change.echange.feature.FeatureEChange#getAffectedEObject <em>Affected EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Affected EObject</em>'.
	 * @see tools.vitruv.framework.change.echange.feature.FeatureEChange#getAffectedEObject()
	 * @see #getFeatureEChange()
	 * @generated
	 */
	EReference getFeatureEChange_AffectedEObject();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.change.echange.feature.FeatureEChange#getProxyObject <em>Proxy Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Proxy Object</em>'.
	 * @see tools.vitruv.framework.change.echange.feature.FeatureEChange#getProxyObject()
	 * @see #getFeatureEChange()
	 * @generated
	 */
	EAttribute getFeatureEChange_ProxyObject();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.feature.UpdateMultiValuedFeatureEChange <em>Update Multi Valued Feature EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Update Multi Valued Feature EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.feature.UpdateMultiValuedFeatureEChange
	 * @generated
	 */
	EClass getUpdateMultiValuedFeatureEChange();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.feature.UpdateSingleValuedFeatureEChange <em>Update Single Valued Feature EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Update Single Valued Feature EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.feature.UpdateSingleValuedFeatureEChange
	 * @generated
	 */
	EClass getUpdateSingleValuedFeatureEChange();

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
	 * Returns the meta object for data type '{@link org.eclipse.emf.ecore.EStructuralFeature <em>EFeat</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>EFeat</em>'.
	 * @see org.eclipse.emf.ecore.EStructuralFeature
	 * @model instanceClass="org.eclipse.emf.ecore.EStructuralFeature"
	 * @generated
	 */
	EDataType getEFeat();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.ecore.InternalEObject <em>Proxy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Proxy</em>'.
	 * @see org.eclipse.emf.ecore.InternalEObject
	 * @model instanceClass="org.eclipse.emf.ecore.InternalEObject"
	 * @generated
	 */
	EDataType getProxy();

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
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.feature.impl.FeatureEChangeImpl <em>EChange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.feature.impl.FeatureEChangeImpl
		 * @see tools.vitruv.framework.change.echange.feature.impl.FeaturePackageImpl#getFeatureEChange()
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
		 * The meta object literal for the '<em><b>Proxy Object</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_ECHANGE__PROXY_OBJECT = eINSTANCE.getFeatureEChange_ProxyObject();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.feature.impl.UpdateMultiValuedFeatureEChangeImpl <em>Update Multi Valued Feature EChange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.feature.impl.UpdateMultiValuedFeatureEChangeImpl
		 * @see tools.vitruv.framework.change.echange.feature.impl.FeaturePackageImpl#getUpdateMultiValuedFeatureEChange()
		 * @generated
		 */
		EClass UPDATE_MULTI_VALUED_FEATURE_ECHANGE = eINSTANCE.getUpdateMultiValuedFeatureEChange();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.feature.impl.UpdateSingleValuedFeatureEChangeImpl <em>Update Single Valued Feature EChange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.feature.impl.UpdateSingleValuedFeatureEChangeImpl
		 * @see tools.vitruv.framework.change.echange.feature.impl.FeaturePackageImpl#getUpdateSingleValuedFeatureEChange()
		 * @generated
		 */
		EClass UPDATE_SINGLE_VALUED_FEATURE_ECHANGE = eINSTANCE.getUpdateSingleValuedFeatureEChange();

		/**
		 * The meta object literal for the '<em>EObj</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecore.EObject
		 * @see tools.vitruv.framework.change.echange.feature.impl.FeaturePackageImpl#getEObj()
		 * @generated
		 */
		EDataType EOBJ = eINSTANCE.getEObj();

		/**
		 * The meta object literal for the '<em>EFeat</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecore.EStructuralFeature
		 * @see tools.vitruv.framework.change.echange.feature.impl.FeaturePackageImpl#getEFeat()
		 * @generated
		 */
		EDataType EFEAT = eINSTANCE.getEFeat();

		/**
		 * The meta object literal for the '<em>Proxy</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecore.InternalEObject
		 * @see tools.vitruv.framework.change.echange.feature.impl.FeaturePackageImpl#getProxy()
		 * @generated
		 */
		EDataType PROXY = eINSTANCE.getProxy();

	}

} //FeaturePackage
