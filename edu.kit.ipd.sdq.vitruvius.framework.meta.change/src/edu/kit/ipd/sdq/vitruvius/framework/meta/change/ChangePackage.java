/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangeFactory
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
	ChangePackage eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl.init();

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EChangeImpl <em>EChange</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getEChange()
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
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EObjectChangeImpl <em>EObject Change</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EObjectChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getEObjectChange()
     * @generated
     */
	int EOBJECT_CHANGE = 1;

	/**
     * The feature id for the '<em><b>Changed EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int EOBJECT_CHANGE__CHANGED_EOBJECT = ECHANGE_FEATURE_COUNT + 0;

	/**
     * The number of structural features of the '<em>EObject Change</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int EOBJECT_CHANGE_FEATURE_COUNT = ECHANGE_FEATURE_COUNT + 1;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EFeatureChangeImpl <em>EFeature Change</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EFeatureChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getEFeatureChange()
     * @generated
     */
	int EFEATURE_CHANGE = 2;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int EFEATURE_CHANGE__AFFECTED_FEATURE = ECHANGE_FEATURE_COUNT + 0;

	/**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int EFEATURE_CHANGE__AFFECTED_EOBJECT = ECHANGE_FEATURE_COUNT + 1;

	/**
     * The number of structural features of the '<em>EFeature Change</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int EFEATURE_CHANGE_FEATURE_COUNT = ECHANGE_FEATURE_COUNT + 2;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EAttributeChangeImpl <em>EAttribute Change</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EAttributeChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getEAttributeChange()
     * @generated
     */
	int EATTRIBUTE_CHANGE = 3;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int EATTRIBUTE_CHANGE__AFFECTED_FEATURE = EFEATURE_CHANGE__AFFECTED_FEATURE;

	/**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int EATTRIBUTE_CHANGE__AFFECTED_EOBJECT = EFEATURE_CHANGE__AFFECTED_EOBJECT;

	/**
     * The number of structural features of the '<em>EAttribute Change</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int EATTRIBUTE_CHANGE_FEATURE_COUNT = EFEATURE_CHANGE_FEATURE_COUNT + 0;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EReferenceChangeImpl <em>EReference Change</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EReferenceChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getEReferenceChange()
     * @generated
     */
	int EREFERENCE_CHANGE = 4;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int EREFERENCE_CHANGE__AFFECTED_FEATURE = EFEATURE_CHANGE__AFFECTED_FEATURE;

	/**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int EREFERENCE_CHANGE__AFFECTED_EOBJECT = EFEATURE_CHANGE__AFFECTED_EOBJECT;

	/**
     * The number of structural features of the '<em>EReference Change</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int EREFERENCE_CHANGE_FEATURE_COUNT = EFEATURE_CHANGE_FEATURE_COUNT + 0;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.CreateEObjectImpl <em>Create EObject</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.CreateEObjectImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getCreateEObject()
     * @generated
     */
	int CREATE_EOBJECT = 5;

	/**
     * The feature id for the '<em><b>Changed EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_EOBJECT__CHANGED_EOBJECT = EOBJECT_CHANGE__CHANGED_EOBJECT;

	/**
     * The number of structural features of the '<em>Create EObject</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_EOBJECT_FEATURE_COUNT = EOBJECT_CHANGE_FEATURE_COUNT + 0;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.CreateNonRootEObjectImpl <em>Create Non Root EObject</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.CreateNonRootEObjectImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getCreateNonRootEObject()
     * @generated
     */
	int CREATE_NON_ROOT_EOBJECT = 6;

	/**
     * The feature id for the '<em><b>Changed EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_NON_ROOT_EOBJECT__CHANGED_EOBJECT = CREATE_EOBJECT__CHANGED_EOBJECT;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_NON_ROOT_EOBJECT__AFFECTED_FEATURE = CREATE_EOBJECT_FEATURE_COUNT + 0;

	/**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_NON_ROOT_EOBJECT__AFFECTED_EOBJECT = CREATE_EOBJECT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>New Value</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_NON_ROOT_EOBJECT__NEW_VALUE = CREATE_EOBJECT_FEATURE_COUNT + 2;

	/**
     * The number of structural features of the '<em>Create Non Root EObject</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_NON_ROOT_EOBJECT_FEATURE_COUNT = CREATE_EOBJECT_FEATURE_COUNT + 3;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.CreateRootEObjectImpl <em>Create Root EObject</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.CreateRootEObjectImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getCreateRootEObject()
     * @generated
     */
	int CREATE_ROOT_EOBJECT = 7;

	/**
     * The feature id for the '<em><b>Changed EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_ROOT_EOBJECT__CHANGED_EOBJECT = CREATE_EOBJECT__CHANGED_EOBJECT;

	/**
     * The number of structural features of the '<em>Create Root EObject</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CREATE_ROOT_EOBJECT_FEATURE_COUNT = CREATE_EOBJECT_FEATURE_COUNT + 0;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEFeatureImpl <em>Update EFeature</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEFeatureImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getUpdateEFeature()
     * @generated
     */
	int UPDATE_EFEATURE = 8;

	/**
     * The number of structural features of the '<em>Update EFeature</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_EFEATURE_FEATURE_COUNT = 0;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UnsetEFeatureImpl <em>Unset EFeature</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UnsetEFeatureImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getUnsetEFeature()
     * @generated
     */
	int UNSET_EFEATURE = 9;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_EFEATURE__AFFECTED_FEATURE = EFEATURE_CHANGE__AFFECTED_FEATURE;

	/**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_EFEATURE__AFFECTED_EOBJECT = EFEATURE_CHANGE__AFFECTED_EOBJECT;

	/**
     * The number of structural features of the '<em>Unset EFeature</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UNSET_EFEATURE_FEATURE_COUNT = EFEATURE_CHANGE_FEATURE_COUNT + 0;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEAttributeImpl <em>Update EAttribute</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEAttributeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getUpdateEAttribute()
     * @generated
     */
	int UPDATE_EATTRIBUTE = 10;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_EATTRIBUTE__AFFECTED_FEATURE = UPDATE_EFEATURE_FEATURE_COUNT + 0;

	/**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_EATTRIBUTE__AFFECTED_EOBJECT = UPDATE_EFEATURE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>New Value</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_EATTRIBUTE__NEW_VALUE = UPDATE_EFEATURE_FEATURE_COUNT + 2;

	/**
     * The number of structural features of the '<em>Update EAttribute</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_EATTRIBUTE_FEATURE_COUNT = UPDATE_EFEATURE_FEATURE_COUNT + 3;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEReferenceImpl <em>Update EReference</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEReferenceImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getUpdateEReference()
     * @generated
     */
	int UPDATE_EREFERENCE = 11;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_EREFERENCE__AFFECTED_FEATURE = UPDATE_EFEATURE_FEATURE_COUNT + 0;

	/**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_EREFERENCE__AFFECTED_EOBJECT = UPDATE_EFEATURE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>New Value</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_EREFERENCE__NEW_VALUE = UPDATE_EFEATURE_FEATURE_COUNT + 2;

	/**
     * The number of structural features of the '<em>Update EReference</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_EREFERENCE_FEATURE_COUNT = UPDATE_EFEATURE_FEATURE_COUNT + 3;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEContainmentReferenceImpl <em>Update EContainment Reference</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEContainmentReferenceImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getUpdateEContainmentReference()
     * @generated
     */
	int UPDATE_ECONTAINMENT_REFERENCE = 12;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_ECONTAINMENT_REFERENCE__AFFECTED_FEATURE = UPDATE_EREFERENCE__AFFECTED_FEATURE;

	/**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_ECONTAINMENT_REFERENCE__AFFECTED_EOBJECT = UPDATE_EREFERENCE__AFFECTED_EOBJECT;

    /**
     * The feature id for the '<em><b>New Value</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_ECONTAINMENT_REFERENCE__NEW_VALUE = UPDATE_EREFERENCE__NEW_VALUE;

	/**
     * The number of structural features of the '<em>Update EContainment Reference</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int UPDATE_ECONTAINMENT_REFERENCE_FEATURE_COUNT = UPDATE_EREFERENCE_FEATURE_COUNT + 0;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.DeleteEObjectImpl <em>Delete EObject</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.DeleteEObjectImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getDeleteEObject()
     * @generated
     */
	int DELETE_EOBJECT = 13;

	/**
     * The feature id for the '<em><b>Changed EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_EOBJECT__CHANGED_EOBJECT = EOBJECT_CHANGE__CHANGED_EOBJECT;

	/**
     * The number of structural features of the '<em>Delete EObject</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_EOBJECT_FEATURE_COUNT = EOBJECT_CHANGE_FEATURE_COUNT + 0;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.DeleteRootEObjectImpl <em>Delete Root EObject</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.DeleteRootEObjectImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getDeleteRootEObject()
     * @generated
     */
	int DELETE_ROOT_EOBJECT = 15;

	/**
     * The feature id for the '<em><b>Changed EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_ROOT_EOBJECT__CHANGED_EOBJECT = DELETE_EOBJECT__CHANGED_EOBJECT;

	/**
     * The number of structural features of the '<em>Delete Root EObject</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_ROOT_EOBJECT_FEATURE_COUNT = DELETE_EOBJECT_FEATURE_COUNT + 0;

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.DeleteNonRootEObjectImpl <em>Delete Non Root EObject</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.DeleteNonRootEObjectImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getDeleteNonRootEObject()
     * @generated
     */
	int DELETE_NON_ROOT_EOBJECT = 14;

	/**
     * The feature id for the '<em><b>Changed EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_NON_ROOT_EOBJECT__CHANGED_EOBJECT = DELETE_ROOT_EOBJECT__CHANGED_EOBJECT;

	/**
     * The feature id for the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_NON_ROOT_EOBJECT__AFFECTED_FEATURE = DELETE_ROOT_EOBJECT_FEATURE_COUNT + 0;

	/**
     * The feature id for the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_NON_ROOT_EOBJECT__AFFECTED_EOBJECT = DELETE_ROOT_EOBJECT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>New Value</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_NON_ROOT_EOBJECT__NEW_VALUE = DELETE_ROOT_EOBJECT_FEATURE_COUNT + 2;

	/**
     * The number of structural features of the '<em>Delete Non Root EObject</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DELETE_NON_ROOT_EOBJECT_FEATURE_COUNT = DELETE_ROOT_EOBJECT_FEATURE_COUNT + 3;


	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange <em>EChange</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>EChange</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
     * @generated
     */
	EClass getEChange();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.EObjectChange <em>EObject Change</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>EObject Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.EObjectChange
     * @generated
     */
	EClass getEObjectChange();

	/**
     * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.EObjectChange#getChangedEObject <em>Changed EObject</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Changed EObject</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.EObjectChange#getChangedEObject()
     * @see #getEObjectChange()
     * @generated
     */
	EReference getEObjectChange_ChangedEObject();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.EFeatureChange <em>EFeature Change</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>EFeature Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.EFeatureChange
     * @generated
     */
	EClass getEFeatureChange();

	/**
     * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.EFeatureChange#getAffectedFeature <em>Affected Feature</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Affected Feature</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.EFeatureChange#getAffectedFeature()
     * @see #getEFeatureChange()
     * @generated
     */
	EReference getEFeatureChange_AffectedFeature();

	/**
     * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.EFeatureChange#getAffectedEObject <em>Affected EObject</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Affected EObject</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.EFeatureChange#getAffectedEObject()
     * @see #getEFeatureChange()
     * @generated
     */
	EReference getEFeatureChange_AffectedEObject();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.EAttributeChange <em>EAttribute Change</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>EAttribute Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.EAttributeChange
     * @generated
     */
	EClass getEAttributeChange();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.EReferenceChange <em>EReference Change</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>EReference Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.EReferenceChange
     * @generated
     */
	EClass getEReferenceChange();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateEObject <em>Create EObject</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Create EObject</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateEObject
     * @generated
     */
	EClass getCreateEObject();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateNonRootEObject <em>Create Non Root EObject</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Create Non Root EObject</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateNonRootEObject
     * @generated
     */
	EClass getCreateNonRootEObject();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateRootEObject <em>Create Root EObject</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Create Root EObject</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateRootEObject
     * @generated
     */
	EClass getCreateRootEObject();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEFeature <em>Update EFeature</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Update EFeature</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEFeature
     * @generated
     */
	EClass getUpdateEFeature();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.UnsetEFeature <em>Unset EFeature</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Unset EFeature</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.UnsetEFeature
     * @generated
     */
	EClass getUnsetEFeature();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEAttribute <em>Update EAttribute</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Update EAttribute</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEAttribute
     * @generated
     */
	EClass getUpdateEAttribute();

	/**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEAttribute#getNewValue <em>New Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>New Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEAttribute#getNewValue()
     * @see #getUpdateEAttribute()
     * @generated
     */
    EAttribute getUpdateEAttribute_NewValue();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEReference <em>Update EReference</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Update EReference</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEReference
     * @generated
     */
	EClass getUpdateEReference();

	/**
     * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEReference#getNewValue <em>New Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>New Value</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEReference#getNewValue()
     * @see #getUpdateEReference()
     * @generated
     */
    EReference getUpdateEReference_NewValue();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEContainmentReference <em>Update EContainment Reference</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Update EContainment Reference</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEContainmentReference
     * @generated
     */
	EClass getUpdateEContainmentReference();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteEObject <em>Delete EObject</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete EObject</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteEObject
     * @generated
     */
	EClass getDeleteEObject();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteNonRootEObject <em>Delete Non Root EObject</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete Non Root EObject</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteNonRootEObject
     * @generated
     */
	EClass getDeleteNonRootEObject();

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteRootEObject <em>Delete Root EObject</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete Root EObject</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteRootEObject
     * @generated
     */
	EClass getDeleteRootEObject();

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
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
     * @generated
     */
	interface Literals {
		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EChangeImpl <em>EChange</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getEChange()
         * @generated
         */
		EClass ECHANGE = eINSTANCE.getEChange();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EObjectChangeImpl <em>EObject Change</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EObjectChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getEObjectChange()
         * @generated
         */
		EClass EOBJECT_CHANGE = eINSTANCE.getEObjectChange();

		/**
         * The meta object literal for the '<em><b>Changed EObject</b></em>' reference feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference EOBJECT_CHANGE__CHANGED_EOBJECT = eINSTANCE.getEObjectChange_ChangedEObject();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EFeatureChangeImpl <em>EFeature Change</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EFeatureChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getEFeatureChange()
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
         * The meta object literal for the '<em><b>Affected EObject</b></em>' reference feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference EFEATURE_CHANGE__AFFECTED_EOBJECT = eINSTANCE.getEFeatureChange_AffectedEObject();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EAttributeChangeImpl <em>EAttribute Change</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EAttributeChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getEAttributeChange()
         * @generated
         */
		EClass EATTRIBUTE_CHANGE = eINSTANCE.getEAttributeChange();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EReferenceChangeImpl <em>EReference Change</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EReferenceChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getEReferenceChange()
         * @generated
         */
		EClass EREFERENCE_CHANGE = eINSTANCE.getEReferenceChange();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.CreateEObjectImpl <em>Create EObject</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.CreateEObjectImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getCreateEObject()
         * @generated
         */
		EClass CREATE_EOBJECT = eINSTANCE.getCreateEObject();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.CreateNonRootEObjectImpl <em>Create Non Root EObject</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.CreateNonRootEObjectImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getCreateNonRootEObject()
         * @generated
         */
		EClass CREATE_NON_ROOT_EOBJECT = eINSTANCE.getCreateNonRootEObject();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.CreateRootEObjectImpl <em>Create Root EObject</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.CreateRootEObjectImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getCreateRootEObject()
         * @generated
         */
		EClass CREATE_ROOT_EOBJECT = eINSTANCE.getCreateRootEObject();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEFeatureImpl <em>Update EFeature</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEFeatureImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getUpdateEFeature()
         * @generated
         */
		EClass UPDATE_EFEATURE = eINSTANCE.getUpdateEFeature();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UnsetEFeatureImpl <em>Unset EFeature</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UnsetEFeatureImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getUnsetEFeature()
         * @generated
         */
		EClass UNSET_EFEATURE = eINSTANCE.getUnsetEFeature();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEAttributeImpl <em>Update EAttribute</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEAttributeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getUpdateEAttribute()
         * @generated
         */
		EClass UPDATE_EATTRIBUTE = eINSTANCE.getUpdateEAttribute();

		/**
         * The meta object literal for the '<em><b>New Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute UPDATE_EATTRIBUTE__NEW_VALUE = eINSTANCE.getUpdateEAttribute_NewValue();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEReferenceImpl <em>Update EReference</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEReferenceImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getUpdateEReference()
         * @generated
         */
		EClass UPDATE_EREFERENCE = eINSTANCE.getUpdateEReference();

		/**
         * The meta object literal for the '<em><b>New Value</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference UPDATE_EREFERENCE__NEW_VALUE = eINSTANCE.getUpdateEReference_NewValue();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEContainmentReferenceImpl <em>Update EContainment Reference</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.UpdateEContainmentReferenceImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getUpdateEContainmentReference()
         * @generated
         */
		EClass UPDATE_ECONTAINMENT_REFERENCE = eINSTANCE.getUpdateEContainmentReference();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.DeleteEObjectImpl <em>Delete EObject</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.DeleteEObjectImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getDeleteEObject()
         * @generated
         */
		EClass DELETE_EOBJECT = eINSTANCE.getDeleteEObject();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.DeleteNonRootEObjectImpl <em>Delete Non Root EObject</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.DeleteNonRootEObjectImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getDeleteNonRootEObject()
         * @generated
         */
		EClass DELETE_NON_ROOT_EOBJECT = eINSTANCE.getDeleteNonRootEObject();

		/**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.DeleteRootEObjectImpl <em>Delete Root EObject</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.DeleteRootEObjectImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl#getDeleteRootEObject()
         * @generated
         */
		EClass DELETE_ROOT_EOBJECT = eINSTANCE.getDeleteRootEObject();

	}

} //ChangePackage
