/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.ObjectFactory
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
    ObjectPackage eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.ObjectPackageImpl.init();

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.EObjectChangeImpl <em>EObject Change</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.EObjectChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.ObjectPackageImpl#getEObjectChange()
     * @generated
     */
    int EOBJECT_CHANGE = 0;

    /**
     * The number of structural features of the '<em>EObject Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EOBJECT_CHANGE_FEATURE_COUNT = ChangePackage.ECHANGE_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>EObject Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EOBJECT_CHANGE_OPERATION_COUNT = ChangePackage.ECHANGE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.CreateEObjectImpl <em>Create EObject</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.CreateEObjectImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.ObjectPackageImpl#getCreateEObject()
     * @generated
     */
    int CREATE_EOBJECT = 1;

    /**
     * The feature id for the '<em><b>New Value</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CREATE_EOBJECT__NEW_VALUE = EOBJECT_CHANGE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Create EObject</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CREATE_EOBJECT_FEATURE_COUNT = EOBJECT_CHANGE_FEATURE_COUNT + 1;

    /**
     * The operation id for the '<em>Get New Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CREATE_EOBJECT___GET_NEW_VALUE = EOBJECT_CHANGE_OPERATION_COUNT + 0;

    /**
     * The number of operations of the '<em>Create EObject</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CREATE_EOBJECT_OPERATION_COUNT = EOBJECT_CHANGE_OPERATION_COUNT + 1;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.DeleteEObjectImpl <em>Delete EObject</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.DeleteEObjectImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.ObjectPackageImpl#getDeleteEObject()
     * @generated
     */
    int DELETE_EOBJECT = 2;

    /**
     * The feature id for the '<em><b>Old TUID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT__OLD_TUID = EOBJECT_CHANGE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Feature Name2 Old Value Map</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT__FEATURE_NAME2_OLD_VALUE_MAP = EOBJECT_CHANGE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Delete EObject</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_FEATURE_COUNT = EOBJECT_CHANGE_FEATURE_COUNT + 2;

    /**
     * The operation id for the '<em>Get Old Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT___GET_OLD_VALUE = EOBJECT_CHANGE_OPERATION_COUNT + 0;

    /**
     * The number of operations of the '<em>Delete EObject</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_OPERATION_COUNT = EOBJECT_CHANGE_OPERATION_COUNT + 1;


    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.EObjectChange <em>EObject Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>EObject Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.EObjectChange
     * @generated
     */
    EClass getEObjectChange();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.CreateEObject <em>Create EObject</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Create EObject</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.CreateEObject
     * @generated
     */
    EClass getCreateEObject();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.DeleteEObject <em>Delete EObject</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete EObject</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.DeleteEObject
     * @generated
     */
    EClass getDeleteEObject();

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
     *   <li>each operation of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.EObjectChangeImpl <em>EObject Change</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.EObjectChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.ObjectPackageImpl#getEObjectChange()
         * @generated
         */
        EClass EOBJECT_CHANGE = eINSTANCE.getEObjectChange();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.CreateEObjectImpl <em>Create EObject</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.CreateEObjectImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.ObjectPackageImpl#getCreateEObject()
         * @generated
         */
        EClass CREATE_EOBJECT = eINSTANCE.getCreateEObject();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.DeleteEObjectImpl <em>Delete EObject</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.DeleteEObjectImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.ObjectPackageImpl#getDeleteEObject()
         * @generated
         */
        EClass DELETE_EOBJECT = eINSTANCE.getDeleteEObject();

    }

} //ObjectPackage
