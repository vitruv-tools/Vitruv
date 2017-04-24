/**
 */
package tools.vitruv.framework.change.echange.feature.single;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

import tools.vitruv.framework.change.echange.feature.FeaturePackage;

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
 * @see tools.vitruv.framework.change.echange.feature.single.SingleFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel modelName='Change' basePackage='tools.vitruv.framework.change.echange.feature'"
 * @generated
 */
public interface SinglePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "single";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv/EChange/Feature/Single/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "single";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SinglePackage eINSTANCE = tools.vitruv.framework.change.echange.feature.single.impl.SinglePackageImpl.init();

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.feature.single.impl.ReplaceSingleValuedFeatureEChangeImpl <em>Replace Single Valued Feature EChange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.feature.single.impl.ReplaceSingleValuedFeatureEChangeImpl
	 * @see tools.vitruv.framework.change.echange.feature.single.impl.SinglePackageImpl#getReplaceSingleValuedFeatureEChange()
	 * @generated
	 */
	int REPLACE_SINGLE_VALUED_FEATURE_ECHANGE = 0;

	/**
	 * The feature id for the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_FEATURE_ECHANGE__AFFECTED_FEATURE = FeaturePackage.UPDATE_SINGLE_VALUED_FEATURE_ECHANGE__AFFECTED_FEATURE;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_FEATURE_ECHANGE__AFFECTED_EOBJECT = FeaturePackage.UPDATE_SINGLE_VALUED_FEATURE_ECHANGE__AFFECTED_EOBJECT;

	/**
	 * The number of structural features of the '<em>Replace Single Valued Feature EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_FEATURE_ECHANGE_FEATURE_COUNT = FeaturePackage.UPDATE_SINGLE_VALUED_FEATURE_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Resolve Before</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___RESOLVE_BEFORE__RESOURCESET = FeaturePackage.UPDATE_SINGLE_VALUED_FEATURE_ECHANGE___RESOLVE_BEFORE__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___RESOLVE_AFTER__RESOURCESET = FeaturePackage.UPDATE_SINGLE_VALUED_FEATURE_ECHANGE___RESOLVE_AFTER__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve Before And Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET = FeaturePackage.UPDATE_SINGLE_VALUED_FEATURE_ECHANGE___RESOLVE_BEFORE_AND_APPLY_FORWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Resolve After And Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET = FeaturePackage.UPDATE_SINGLE_VALUED_FEATURE_ECHANGE___RESOLVE_AFTER_AND_APPLY_BACKWARD__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply Forward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___APPLY_FORWARD = FeaturePackage.UPDATE_SINGLE_VALUED_FEATURE_ECHANGE___APPLY_FORWARD;

	/**
	 * The operation id for the '<em>Apply Backward</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___APPLY_BACKWARD = FeaturePackage.UPDATE_SINGLE_VALUED_FEATURE_ECHANGE___APPLY_BACKWARD;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___IS_RESOLVED = FeaturePackage.UPDATE_SINGLE_VALUED_FEATURE_ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Get New Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___GET_NEW_VALUE = FeaturePackage.UPDATE_SINGLE_VALUED_FEATURE_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Old Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___GET_OLD_VALUE = FeaturePackage.UPDATE_SINGLE_VALUED_FEATURE_ECHANGE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is From Non Default Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___IS_FROM_NON_DEFAULT_VALUE = FeaturePackage.UPDATE_SINGLE_VALUED_FEATURE_ECHANGE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is To Non Default Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___IS_TO_NON_DEFAULT_VALUE = FeaturePackage.UPDATE_SINGLE_VALUED_FEATURE_ECHANGE_OPERATION_COUNT + 3;

	/**
	 * The number of operations of the '<em>Replace Single Valued Feature EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_SINGLE_VALUED_FEATURE_ECHANGE_OPERATION_COUNT = FeaturePackage.UPDATE_SINGLE_VALUED_FEATURE_ECHANGE_OPERATION_COUNT + 4;

	/**
	 * The meta object id for the '<em>EObj</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EObject
	 * @see tools.vitruv.framework.change.echange.feature.single.impl.SinglePackageImpl#getEObj()
	 * @generated
	 */
	int EOBJ = 1;

	/**
	 * The meta object id for the '<em>EFeat</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EStructuralFeature
	 * @see tools.vitruv.framework.change.echange.feature.single.impl.SinglePackageImpl#getEFeat()
	 * @generated
	 */
	int EFEAT = 2;


	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange <em>Replace Single Valued Feature EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Replace Single Valued Feature EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange
	 * @generated
	 */
	EClass getReplaceSingleValuedFeatureEChange();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange#isFromNonDefaultValue() <em>Is From Non Default Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is From Non Default Value</em>' operation.
	 * @see tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange#isFromNonDefaultValue()
	 * @generated
	 */
	EOperation getReplaceSingleValuedFeatureEChange__IsFromNonDefaultValue();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange#isToNonDefaultValue() <em>Is To Non Default Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is To Non Default Value</em>' operation.
	 * @see tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange#isToNonDefaultValue()
	 * @generated
	 */
	EOperation getReplaceSingleValuedFeatureEChange__IsToNonDefaultValue();

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
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SingleFactory getSingleFactory();

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
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.feature.single.impl.ReplaceSingleValuedFeatureEChangeImpl <em>Replace Single Valued Feature EChange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.feature.single.impl.ReplaceSingleValuedFeatureEChangeImpl
		 * @see tools.vitruv.framework.change.echange.feature.single.impl.SinglePackageImpl#getReplaceSingleValuedFeatureEChange()
		 * @generated
		 */
		EClass REPLACE_SINGLE_VALUED_FEATURE_ECHANGE = eINSTANCE.getReplaceSingleValuedFeatureEChange();

		/**
		 * The meta object literal for the '<em><b>Is From Non Default Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___IS_FROM_NON_DEFAULT_VALUE = eINSTANCE.getReplaceSingleValuedFeatureEChange__IsFromNonDefaultValue();

		/**
		 * The meta object literal for the '<em><b>Is To Non Default Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REPLACE_SINGLE_VALUED_FEATURE_ECHANGE___IS_TO_NON_DEFAULT_VALUE = eINSTANCE.getReplaceSingleValuedFeatureEChange__IsToNonDefaultValue();

		/**
		 * The meta object literal for the '<em>EObj</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecore.EObject
		 * @see tools.vitruv.framework.change.echange.feature.single.impl.SinglePackageImpl#getEObj()
		 * @generated
		 */
		EDataType EOBJ = eINSTANCE.getEObj();

		/**
		 * The meta object literal for the '<em>EFeat</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecore.EStructuralFeature
		 * @see tools.vitruv.framework.change.echange.feature.single.impl.SinglePackageImpl#getEFeat()
		 * @generated
		 */
		EDataType EFEAT = eINSTANCE.getEFeat();

	}

} //SinglePackage
