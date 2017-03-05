/**
 */
package tools.vitruv.framework.change.echange.eobject;

import org.eclipse.emf.ecore.EAttribute;
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
 * @see tools.vitruv.framework.change.echange.eobject.EobjectFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel modelName='Change' basePackage='tools.vitruv.framework.change.echange'"
 * @generated
 */
public interface EobjectPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "eobject";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv/EChange/EObject/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "eobject";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EobjectPackage eINSTANCE = tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl.init();

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.eobject.impl.EObjectAddedEChangeImpl <em>EObject Added EChange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.eobject.impl.EObjectAddedEChangeImpl
	 * @see tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl#getEObjectAddedEChange()
	 * @generated
	 */
	int EOBJECT_ADDED_ECHANGE = 0;

	/**
	 * The feature id for the '<em><b>New Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_ADDED_ECHANGE__NEW_VALUE = EChangePackage.ADDITIVE_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EObject Added EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_ADDED_ECHANGE_FEATURE_COUNT = EChangePackage.ADDITIVE_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_ADDED_ECHANGE___IS_RESOLVED = EChangePackage.ADDITIVE_ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_ADDED_ECHANGE___RESOLVE__RESOURCESET = EChangePackage.ADDITIVE_ECHANGE___RESOLVE__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_ADDED_ECHANGE___APPLY = EChangePackage.ADDITIVE_ECHANGE___APPLY;

	/**
	 * The operation id for the '<em>Revert</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_ADDED_ECHANGE___REVERT = EChangePackage.ADDITIVE_ECHANGE___REVERT;

	/**
	 * The operation id for the '<em>Get New Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_ADDED_ECHANGE___GET_NEW_VALUE = EChangePackage.ADDITIVE_ECHANGE___GET_NEW_VALUE;

	/**
	 * The number of operations of the '<em>EObject Added EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_ADDED_ECHANGE_OPERATION_COUNT = EChangePackage.ADDITIVE_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.eobject.impl.EObjectSubtractedEChangeImpl <em>EObject Subtracted EChange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.eobject.impl.EObjectSubtractedEChangeImpl
	 * @see tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl#getEObjectSubtractedEChange()
	 * @generated
	 */
	int EOBJECT_SUBTRACTED_ECHANGE = 1;

	/**
	 * The feature id for the '<em><b>Old Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE = EChangePackage.SUBTRACTIVE_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EObject Subtracted EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_SUBTRACTED_ECHANGE_FEATURE_COUNT = EChangePackage.SUBTRACTIVE_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_SUBTRACTED_ECHANGE___IS_RESOLVED = EChangePackage.SUBTRACTIVE_ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_SUBTRACTED_ECHANGE___RESOLVE__RESOURCESET = EChangePackage.SUBTRACTIVE_ECHANGE___RESOLVE__RESOURCESET;

	/**
	 * The operation id for the '<em>Apply</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_SUBTRACTED_ECHANGE___APPLY = EChangePackage.SUBTRACTIVE_ECHANGE___APPLY;

	/**
	 * The operation id for the '<em>Revert</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_SUBTRACTED_ECHANGE___REVERT = EChangePackage.SUBTRACTIVE_ECHANGE___REVERT;

	/**
	 * The operation id for the '<em>Get Old Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_SUBTRACTED_ECHANGE___GET_OLD_VALUE = EChangePackage.SUBTRACTIVE_ECHANGE___GET_OLD_VALUE;

	/**
	 * The number of operations of the '<em>EObject Subtracted EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_SUBTRACTED_ECHANGE_OPERATION_COUNT = EChangePackage.SUBTRACTIVE_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.eobject.impl.EObjectExistenceEChangeImpl <em>EObject Existence EChange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.eobject.impl.EObjectExistenceEChangeImpl
	 * @see tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl#getEObjectExistenceEChange()
	 * @generated
	 */
	int EOBJECT_EXISTENCE_ECHANGE = 2;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_EXISTENCE_ECHANGE__AFFECTED_EOBJECT = EChangePackage.ATOMIC_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Staging Area</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_EXISTENCE_ECHANGE__STAGING_AREA = EChangePackage.ATOMIC_ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>EObject Existence EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_EXISTENCE_ECHANGE_FEATURE_COUNT = EChangePackage.ATOMIC_ECHANGE_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Apply</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_EXISTENCE_ECHANGE___APPLY = EChangePackage.ATOMIC_ECHANGE___APPLY;

	/**
	 * The operation id for the '<em>Revert</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_EXISTENCE_ECHANGE___REVERT = EChangePackage.ATOMIC_ECHANGE___REVERT;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_EXISTENCE_ECHANGE___IS_RESOLVED = EChangePackage.ATOMIC_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Resolve</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_EXISTENCE_ECHANGE___RESOLVE__RESOURCESET = EChangePackage.ATOMIC_ECHANGE_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>EObject Existence EChange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_EXISTENCE_ECHANGE_OPERATION_COUNT = EChangePackage.ATOMIC_ECHANGE_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.eobject.impl.CreateEObjectImpl <em>Create EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.eobject.impl.CreateEObjectImpl
	 * @see tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl#getCreateEObject()
	 * @generated
	 */
	int CREATE_EOBJECT = 3;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_EOBJECT__AFFECTED_EOBJECT = EOBJECT_EXISTENCE_ECHANGE__AFFECTED_EOBJECT;

	/**
	 * The feature id for the '<em><b>Staging Area</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_EOBJECT__STAGING_AREA = EOBJECT_EXISTENCE_ECHANGE__STAGING_AREA;

	/**
	 * The number of structural features of the '<em>Create EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_EOBJECT_FEATURE_COUNT = EOBJECT_EXISTENCE_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Apply</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_EOBJECT___APPLY = EOBJECT_EXISTENCE_ECHANGE___APPLY;

	/**
	 * The operation id for the '<em>Revert</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_EOBJECT___REVERT = EOBJECT_EXISTENCE_ECHANGE___REVERT;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_EOBJECT___IS_RESOLVED = EOBJECT_EXISTENCE_ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_EOBJECT___RESOLVE__RESOURCESET = EOBJECT_EXISTENCE_ECHANGE___RESOLVE__RESOURCESET;

	/**
	 * The number of operations of the '<em>Create EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_EOBJECT_OPERATION_COUNT = EOBJECT_EXISTENCE_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.eobject.impl.DeleteEObjectImpl <em>Delete EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.eobject.impl.DeleteEObjectImpl
	 * @see tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl#getDeleteEObject()
	 * @generated
	 */
	int DELETE_EOBJECT = 4;

	/**
	 * The feature id for the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_EOBJECT__AFFECTED_EOBJECT = EOBJECT_EXISTENCE_ECHANGE__AFFECTED_EOBJECT;

	/**
	 * The feature id for the '<em><b>Staging Area</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_EOBJECT__STAGING_AREA = EOBJECT_EXISTENCE_ECHANGE__STAGING_AREA;

	/**
	 * The number of structural features of the '<em>Delete EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_EOBJECT_FEATURE_COUNT = EOBJECT_EXISTENCE_ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Apply</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_EOBJECT___APPLY = EOBJECT_EXISTENCE_ECHANGE___APPLY;

	/**
	 * The operation id for the '<em>Revert</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_EOBJECT___REVERT = EOBJECT_EXISTENCE_ECHANGE___REVERT;

	/**
	 * The operation id for the '<em>Is Resolved</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_EOBJECT___IS_RESOLVED = EOBJECT_EXISTENCE_ECHANGE___IS_RESOLVED;

	/**
	 * The operation id for the '<em>Resolve</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_EOBJECT___RESOLVE__RESOURCESET = EOBJECT_EXISTENCE_ECHANGE___RESOLVE__RESOURCESET;

	/**
	 * The number of operations of the '<em>Delete EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_EOBJECT_OPERATION_COUNT = EOBJECT_EXISTENCE_ECHANGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '<em>EObj</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EObject
	 * @see tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl#getEObj()
	 * @generated
	 */
	int EOBJ = 5;

	/**
	 * The meta object id for the '<em>Resource Set</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.resource.ResourceSet
	 * @see tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl#getResourceSet()
	 * @generated
	 */
	int RESOURCE_SET = 6;

	/**
	 * The meta object id for the '<em>Resource</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.resource.Resource
	 * @see tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl#getResource()
	 * @generated
	 */
	int RESOURCE = 7;


	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange <em>EObject Added EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EObject Added EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
	 * @generated
	 */
	EClass getEObjectAddedEChange();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange#getNewValue <em>New Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>New Value</em>'.
	 * @see tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange#getNewValue()
	 * @see #getEObjectAddedEChange()
	 * @generated
	 */
	EReference getEObjectAddedEChange_NewValue();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange <em>EObject Subtracted EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EObject Subtracted EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
	 * @generated
	 */
	EClass getEObjectSubtractedEChange();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange#getOldValue <em>Old Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Old Value</em>'.
	 * @see tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange#getOldValue()
	 * @see #getEObjectSubtractedEChange()
	 * @generated
	 */
	EReference getEObjectSubtractedEChange_OldValue();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange <em>EObject Existence EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EObject Existence EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
	 * @generated
	 */
	EClass getEObjectExistenceEChange();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange#getAffectedEObject <em>Affected EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Affected EObject</em>'.
	 * @see tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange#getAffectedEObject()
	 * @see #getEObjectExistenceEChange()
	 * @generated
	 */
	EReference getEObjectExistenceEChange_AffectedEObject();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange#getStagingArea <em>Staging Area</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Staging Area</em>'.
	 * @see tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange#getStagingArea()
	 * @see #getEObjectExistenceEChange()
	 * @generated
	 */
	EAttribute getEObjectExistenceEChange_StagingArea();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange#isResolved() <em>Is Resolved</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Resolved</em>' operation.
	 * @see tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange#isResolved()
	 * @generated
	 */
	EOperation getEObjectExistenceEChange__IsResolved();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange#resolve(org.eclipse.emf.ecore.resource.ResourceSet) <em>Resolve</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Resolve</em>' operation.
	 * @see tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange#resolve(org.eclipse.emf.ecore.resource.ResourceSet)
	 * @generated
	 */
	EOperation getEObjectExistenceEChange__Resolve__ResourceSet();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.eobject.CreateEObject <em>Create EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Create EObject</em>'.
	 * @see tools.vitruv.framework.change.echange.eobject.CreateEObject
	 * @generated
	 */
	EClass getCreateEObject();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.eobject.DeleteEObject <em>Delete EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Delete EObject</em>'.
	 * @see tools.vitruv.framework.change.echange.eobject.DeleteEObject
	 * @generated
	 */
	EClass getDeleteEObject();

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
	 * Returns the meta object for data type '{@link org.eclipse.emf.ecore.resource.Resource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Resource</em>'.
	 * @see org.eclipse.emf.ecore.resource.Resource
	 * @model instanceClass="org.eclipse.emf.ecore.resource.Resource"
	 * @generated
	 */
	EDataType getResource();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EobjectFactory getEobjectFactory();

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
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.eobject.impl.EObjectAddedEChangeImpl <em>EObject Added EChange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.eobject.impl.EObjectAddedEChangeImpl
		 * @see tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl#getEObjectAddedEChange()
		 * @generated
		 */
		EClass EOBJECT_ADDED_ECHANGE = eINSTANCE.getEObjectAddedEChange();

		/**
		 * The meta object literal for the '<em><b>New Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EOBJECT_ADDED_ECHANGE__NEW_VALUE = eINSTANCE.getEObjectAddedEChange_NewValue();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.eobject.impl.EObjectSubtractedEChangeImpl <em>EObject Subtracted EChange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.eobject.impl.EObjectSubtractedEChangeImpl
		 * @see tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl#getEObjectSubtractedEChange()
		 * @generated
		 */
		EClass EOBJECT_SUBTRACTED_ECHANGE = eINSTANCE.getEObjectSubtractedEChange();

		/**
		 * The meta object literal for the '<em><b>Old Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EOBJECT_SUBTRACTED_ECHANGE__OLD_VALUE = eINSTANCE.getEObjectSubtractedEChange_OldValue();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.eobject.impl.EObjectExistenceEChangeImpl <em>EObject Existence EChange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.eobject.impl.EObjectExistenceEChangeImpl
		 * @see tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl#getEObjectExistenceEChange()
		 * @generated
		 */
		EClass EOBJECT_EXISTENCE_ECHANGE = eINSTANCE.getEObjectExistenceEChange();

		/**
		 * The meta object literal for the '<em><b>Affected EObject</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EOBJECT_EXISTENCE_ECHANGE__AFFECTED_EOBJECT = eINSTANCE.getEObjectExistenceEChange_AffectedEObject();

		/**
		 * The meta object literal for the '<em><b>Staging Area</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EOBJECT_EXISTENCE_ECHANGE__STAGING_AREA = eINSTANCE.getEObjectExistenceEChange_StagingArea();

		/**
		 * The meta object literal for the '<em><b>Is Resolved</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EOBJECT_EXISTENCE_ECHANGE___IS_RESOLVED = eINSTANCE.getEObjectExistenceEChange__IsResolved();

		/**
		 * The meta object literal for the '<em><b>Resolve</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EOBJECT_EXISTENCE_ECHANGE___RESOLVE__RESOURCESET = eINSTANCE.getEObjectExistenceEChange__Resolve__ResourceSet();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.eobject.impl.CreateEObjectImpl <em>Create EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.eobject.impl.CreateEObjectImpl
		 * @see tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl#getCreateEObject()
		 * @generated
		 */
		EClass CREATE_EOBJECT = eINSTANCE.getCreateEObject();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.eobject.impl.DeleteEObjectImpl <em>Delete EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.eobject.impl.DeleteEObjectImpl
		 * @see tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl#getDeleteEObject()
		 * @generated
		 */
		EClass DELETE_EOBJECT = eINSTANCE.getDeleteEObject();

		/**
		 * The meta object literal for the '<em>EObj</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecore.EObject
		 * @see tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl#getEObj()
		 * @generated
		 */
		EDataType EOBJ = eINSTANCE.getEObj();

		/**
		 * The meta object literal for the '<em>Resource Set</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecore.resource.ResourceSet
		 * @see tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl#getResourceSet()
		 * @generated
		 */
		EDataType RESOURCE_SET = eINSTANCE.getResourceSet();

		/**
		 * The meta object literal for the '<em>Resource</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecore.resource.Resource
		 * @see tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl#getResource()
		 * @generated
		 */
		EDataType RESOURCE = eINSTANCE.getResource();

	}

} //EobjectPackage
