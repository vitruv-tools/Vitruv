/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.object;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectFactory
 * @model kind="package"
 * @generated
 */
public interface ObjectPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "object";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://edu.kit.ipd.sdq.vitruvius/Change/Object/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "object";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ObjectPackage eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.EObjectChangeImpl <em>EObject Change</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.EObjectChangeImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl#getEObjectChange()
	 * @generated
	 */
	int EOBJECT_CHANGE = 0;

	/**
	 * The feature id for the '<em><b>Changed EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_CHANGE__CHANGED_EOBJECT = ChangePackage.ECHANGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EObject Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_CHANGE_FEATURE_COUNT = ChangePackage.ECHANGE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.CreateEObjectImpl <em>Create EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.CreateEObjectImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl#getCreateEObject()
	 * @generated
	 */
	int CREATE_EOBJECT = 1;

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
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ReplaceEObjectImpl <em>Replace EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ReplaceEObjectImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl#getReplaceEObject()
	 * @generated
	 */
	int REPLACE_EOBJECT = 2;

	/**
	 * The feature id for the '<em><b>Changed EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_EOBJECT__CHANGED_EOBJECT = EOBJECT_CHANGE__CHANGED_EOBJECT;

	/**
	 * The number of structural features of the '<em>Replace EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_EOBJECT_FEATURE_COUNT = EOBJECT_CHANGE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.DeleteEObjectImpl <em>Delete EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.DeleteEObjectImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl#getDeleteEObject()
	 * @generated
	 */
	int DELETE_EOBJECT = 3;

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
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.CreateRootEObjectImpl <em>Create Root EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.CreateRootEObjectImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl#getCreateRootEObject()
	 * @generated
	 */
	int CREATE_ROOT_EOBJECT = 4;

	/**
	 * The feature id for the '<em><b>Changed EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_ROOT_EOBJECT__CHANGED_EOBJECT = CREATE_EOBJECT__CHANGED_EOBJECT;

	/**
	 * The feature id for the '<em><b>New Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_ROOT_EOBJECT__NEW_VALUE = CREATE_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Create Root EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_ROOT_EOBJECT_FEATURE_COUNT = CREATE_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ReplaceRootEObjectImpl <em>Replace Root EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ReplaceRootEObjectImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl#getReplaceRootEObject()
	 * @generated
	 */
	int REPLACE_ROOT_EOBJECT = 5;

	/**
	 * The feature id for the '<em><b>Changed EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_ROOT_EOBJECT__CHANGED_EOBJECT = REPLACE_EOBJECT__CHANGED_EOBJECT;

	/**
	 * The feature id for the '<em><b>New Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_ROOT_EOBJECT__NEW_VALUE = REPLACE_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Old Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_ROOT_EOBJECT__OLD_VALUE = REPLACE_EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Replace Root EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_ROOT_EOBJECT_FEATURE_COUNT = REPLACE_EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.DeleteRootEObjectImpl <em>Delete Root EObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.DeleteRootEObjectImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl#getDeleteRootEObject()
	 * @generated
	 */
	int DELETE_ROOT_EOBJECT = 6;

	/**
	 * The feature id for the '<em><b>Changed EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_ROOT_EOBJECT__CHANGED_EOBJECT = DELETE_EOBJECT__CHANGED_EOBJECT;

	/**
	 * The feature id for the '<em><b>Old Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_ROOT_EOBJECT__OLD_VALUE = DELETE_EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Delete Root EObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_ROOT_EOBJECT_FEATURE_COUNT = DELETE_EOBJECT_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.EObjectChange <em>EObject Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EObject Change</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.EObjectChange
	 * @generated
	 */
	EClass getEObjectChange();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.EObjectChange#getChangedEObject <em>Changed EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Changed EObject</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.EObjectChange#getChangedEObject()
	 * @see #getEObjectChange()
	 * @generated
	 */
	EReference getEObjectChange_ChangedEObject();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateEObject <em>Create EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Create EObject</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateEObject
	 * @generated
	 */
	EClass getCreateEObject();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceEObject <em>Replace EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Replace EObject</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceEObject
	 * @generated
	 */
	EClass getReplaceEObject();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteEObject <em>Delete EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Delete EObject</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteEObject
	 * @generated
	 */
	EClass getDeleteEObject();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject <em>Create Root EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Create Root EObject</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject
	 * @generated
	 */
	EClass getCreateRootEObject();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject#getNewValue <em>New Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>New Value</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject#getNewValue()
	 * @see #getCreateRootEObject()
	 * @generated
	 */
	EAttribute getCreateRootEObject_NewValue();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceRootEObject <em>Replace Root EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Replace Root EObject</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceRootEObject
	 * @generated
	 */
	EClass getReplaceRootEObject();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceRootEObject#getNewValue <em>New Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>New Value</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceRootEObject#getNewValue()
	 * @see #getReplaceRootEObject()
	 * @generated
	 */
	EAttribute getReplaceRootEObject_NewValue();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceRootEObject#getOldValue <em>Old Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Old Value</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceRootEObject#getOldValue()
	 * @see #getReplaceRootEObject()
	 * @generated
	 */
	EAttribute getReplaceRootEObject_OldValue();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject <em>Delete Root EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Delete Root EObject</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject
	 * @generated
	 */
	EClass getDeleteRootEObject();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject#getOldValue <em>Old Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Old Value</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject#getOldValue()
	 * @see #getDeleteRootEObject()
	 * @generated
	 */
	EAttribute getDeleteRootEObject_OldValue();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ObjectFactory getObjectFactory();

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
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.EObjectChangeImpl <em>EObject Change</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.EObjectChangeImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl#getEObjectChange()
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
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.CreateEObjectImpl <em>Create EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.CreateEObjectImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl#getCreateEObject()
		 * @generated
		 */
		EClass CREATE_EOBJECT = eINSTANCE.getCreateEObject();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ReplaceEObjectImpl <em>Replace EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ReplaceEObjectImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl#getReplaceEObject()
		 * @generated
		 */
		EClass REPLACE_EOBJECT = eINSTANCE.getReplaceEObject();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.DeleteEObjectImpl <em>Delete EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.DeleteEObjectImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl#getDeleteEObject()
		 * @generated
		 */
		EClass DELETE_EOBJECT = eINSTANCE.getDeleteEObject();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.CreateRootEObjectImpl <em>Create Root EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.CreateRootEObjectImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl#getCreateRootEObject()
		 * @generated
		 */
		EClass CREATE_ROOT_EOBJECT = eINSTANCE.getCreateRootEObject();

		/**
		 * The meta object literal for the '<em><b>New Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CREATE_ROOT_EOBJECT__NEW_VALUE = eINSTANCE.getCreateRootEObject_NewValue();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ReplaceRootEObjectImpl <em>Replace Root EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ReplaceRootEObjectImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl#getReplaceRootEObject()
		 * @generated
		 */
		EClass REPLACE_ROOT_EOBJECT = eINSTANCE.getReplaceRootEObject();

		/**
		 * The meta object literal for the '<em><b>New Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPLACE_ROOT_EOBJECT__NEW_VALUE = eINSTANCE.getReplaceRootEObject_NewValue();

		/**
		 * The meta object literal for the '<em><b>Old Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPLACE_ROOT_EOBJECT__OLD_VALUE = eINSTANCE.getReplaceRootEObject_OldValue();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.DeleteRootEObjectImpl <em>Delete Root EObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.DeleteRootEObjectImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl#getDeleteRootEObject()
		 * @generated
		 */
		EClass DELETE_ROOT_EOBJECT = eINSTANCE.getDeleteRootEObject();

		/**
		 * The meta object literal for the '<em><b>Old Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DELETE_ROOT_EOBJECT__OLD_VALUE = eINSTANCE.getDeleteRootEObject_OldValue();

	}

} //ObjectPackage
