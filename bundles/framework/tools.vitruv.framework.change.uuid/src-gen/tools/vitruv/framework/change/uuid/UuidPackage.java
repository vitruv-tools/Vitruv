/**
 */
package tools.vitruv.framework.change.uuid;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
 * @see tools.vitruv.framework.change.uuid.UuidFactory
 * @model kind="package"
 * @generated
 */
public interface UuidPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "uuid";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv/Change/Uuid";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "uuid";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UuidPackage eINSTANCE = tools.vitruv.framework.change.uuid.impl.UuidPackageImpl.init();

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.uuid.impl.UuidToEObjectRepositoryImpl <em>To EObject Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.uuid.impl.UuidToEObjectRepositoryImpl
	 * @see tools.vitruv.framework.change.uuid.impl.UuidPackageImpl#getUuidToEObjectRepository()
	 * @generated
	 */
	int UUID_TO_EOBJECT_REPOSITORY = 0;

	/**
	 * The feature id for the '<em><b>Uuid To EObject</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UUID_TO_EOBJECT_REPOSITORY__UUID_TO_EOBJECT = 0;

	/**
	 * The feature id for the '<em><b>EObject To Uuid</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UUID_TO_EOBJECT_REPOSITORY__EOBJECT_TO_UUID = 1;

	/**
	 * The number of structural features of the '<em>To EObject Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UUID_TO_EOBJECT_REPOSITORY_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>To EObject Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UUID_TO_EOBJECT_REPOSITORY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.uuid.impl.UuidToEObjectMapEntryImpl <em>To EObject Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.uuid.impl.UuidToEObjectMapEntryImpl
	 * @see tools.vitruv.framework.change.uuid.impl.UuidPackageImpl#getUuidToEObjectMapEntry()
	 * @generated
	 */
	int UUID_TO_EOBJECT_MAP_ENTRY = 1;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UUID_TO_EOBJECT_MAP_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UUID_TO_EOBJECT_MAP_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>To EObject Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UUID_TO_EOBJECT_MAP_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>To EObject Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UUID_TO_EOBJECT_MAP_ENTRY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.change.uuid.impl.EObjectToUuidMapEntryImpl <em>EObject To Uuid Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.uuid.impl.EObjectToUuidMapEntryImpl
	 * @see tools.vitruv.framework.change.uuid.impl.UuidPackageImpl#getEObjectToUuidMapEntry()
	 * @generated
	 */
	int EOBJECT_TO_UUID_MAP_ENTRY = 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_TO_UUID_MAP_ENTRY__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_TO_UUID_MAP_ENTRY__KEY = 1;

	/**
	 * The number of structural features of the '<em>EObject To Uuid Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_TO_UUID_MAP_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>EObject To Uuid Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_TO_UUID_MAP_ENTRY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '<em>Uuid</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see tools.vitruv.framework.change.uuid.impl.UuidPackageImpl#getUuid()
	 * @generated
	 */
	int UUID = 3;

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.uuid.UuidToEObjectRepository <em>To EObject Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>To EObject Repository</em>'.
	 * @see tools.vitruv.framework.change.uuid.UuidToEObjectRepository
	 * @generated
	 */
	EClass getUuidToEObjectRepository();

	/**
	 * Returns the meta object for the map '{@link tools.vitruv.framework.change.uuid.UuidToEObjectRepository#getUuidToEObject <em>Uuid To EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Uuid To EObject</em>'.
	 * @see tools.vitruv.framework.change.uuid.UuidToEObjectRepository#getUuidToEObject()
	 * @see #getUuidToEObjectRepository()
	 * @generated
	 */
	EReference getUuidToEObjectRepository_UuidToEObject();

	/**
	 * Returns the meta object for the map '{@link tools.vitruv.framework.change.uuid.UuidToEObjectRepository#getEObjectToUuid <em>EObject To Uuid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>EObject To Uuid</em>'.
	 * @see tools.vitruv.framework.change.uuid.UuidToEObjectRepository#getEObjectToUuid()
	 * @see #getUuidToEObjectRepository()
	 * @generated
	 */
	EReference getUuidToEObjectRepository_EObjectToUuid();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>To EObject Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>To EObject Map Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="tools.vitruv.framework.change.uuid.Uuid"
	 *        valueType="org.eclipse.emf.ecore.EObject"
	 * @generated
	 */
	EClass getUuidToEObjectMapEntry();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getUuidToEObjectMapEntry()
	 * @generated
	 */
	EAttribute getUuidToEObjectMapEntry_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getUuidToEObjectMapEntry()
	 * @generated
	 */
	EReference getUuidToEObjectMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>EObject To Uuid Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EObject To Uuid Map Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model features="value key" 
	 *        valueDataType="tools.vitruv.framework.change.uuid.Uuid"
	 *        keyType="org.eclipse.emf.ecore.EObject"
	 * @generated
	 */
	EClass getEObjectToUuidMapEntry();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEObjectToUuidMapEntry()
	 * @generated
	 */
	EAttribute getEObjectToUuidMapEntry_Value();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEObjectToUuidMapEntry()
	 * @generated
	 */
	EReference getEObjectToUuidMapEntry_Key();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Uuid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Uuid</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 * @generated
	 */
	EDataType getUuid();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	UuidFactory getUuidFactory();

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
		 * The meta object literal for the '{@link tools.vitruv.framework.change.uuid.impl.UuidToEObjectRepositoryImpl <em>To EObject Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.uuid.impl.UuidToEObjectRepositoryImpl
		 * @see tools.vitruv.framework.change.uuid.impl.UuidPackageImpl#getUuidToEObjectRepository()
		 * @generated
		 */
		EClass UUID_TO_EOBJECT_REPOSITORY = eINSTANCE.getUuidToEObjectRepository();

		/**
		 * The meta object literal for the '<em><b>Uuid To EObject</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UUID_TO_EOBJECT_REPOSITORY__UUID_TO_EOBJECT = eINSTANCE.getUuidToEObjectRepository_UuidToEObject();

		/**
		 * The meta object literal for the '<em><b>EObject To Uuid</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UUID_TO_EOBJECT_REPOSITORY__EOBJECT_TO_UUID = eINSTANCE.getUuidToEObjectRepository_EObjectToUuid();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.uuid.impl.UuidToEObjectMapEntryImpl <em>To EObject Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.uuid.impl.UuidToEObjectMapEntryImpl
		 * @see tools.vitruv.framework.change.uuid.impl.UuidPackageImpl#getUuidToEObjectMapEntry()
		 * @generated
		 */
		EClass UUID_TO_EOBJECT_MAP_ENTRY = eINSTANCE.getUuidToEObjectMapEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UUID_TO_EOBJECT_MAP_ENTRY__KEY = eINSTANCE.getUuidToEObjectMapEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UUID_TO_EOBJECT_MAP_ENTRY__VALUE = eINSTANCE.getUuidToEObjectMapEntry_Value();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.uuid.impl.EObjectToUuidMapEntryImpl <em>EObject To Uuid Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.uuid.impl.EObjectToUuidMapEntryImpl
		 * @see tools.vitruv.framework.change.uuid.impl.UuidPackageImpl#getEObjectToUuidMapEntry()
		 * @generated
		 */
		EClass EOBJECT_TO_UUID_MAP_ENTRY = eINSTANCE.getEObjectToUuidMapEntry();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EOBJECT_TO_UUID_MAP_ENTRY__VALUE = eINSTANCE.getEObjectToUuidMapEntry_Value();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EOBJECT_TO_UUID_MAP_ENTRY__KEY = eINSTANCE.getEObjectToUuidMapEntry_Key();

		/**
		 * The meta object literal for the '<em>Uuid</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see tools.vitruv.framework.change.uuid.impl.UuidPackageImpl#getUuid()
		 * @generated
		 */
		EDataType UUID = eINSTANCE.getUuid();

	}

} //UuidPackage
