/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RootFactory
 * @model kind="package"
 * @generated
 */
public interface RootPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "root";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://edu.kit.ipd.sdq.vitruvius/Change/Root/1.0";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "root";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    RootPackage eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl.RootPackageImpl.init();

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl.InsertRootEObjectImpl <em>Insert Root EObject</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl.InsertRootEObjectImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl.RootPackageImpl#getInsertRootEObject()
     * @generated
     */
    int INSERT_ROOT_EOBJECT = 0;

    /**
     * The feature id for the '<em><b>New Value</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_ROOT_EOBJECT__NEW_VALUE = ChangePackage.ADDITIVE_REFERENCE_CHANGE__NEW_VALUE;

    /**
     * The number of structural features of the '<em>Insert Root EObject</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_ROOT_EOBJECT_FEATURE_COUNT = ChangePackage.ADDITIVE_REFERENCE_CHANGE_FEATURE_COUNT + 0;

    /**
     * The operation id for the '<em>Get New Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_ROOT_EOBJECT___GET_NEW_VALUE = ChangePackage.ADDITIVE_REFERENCE_CHANGE___GET_NEW_VALUE;

    /**
     * The number of operations of the '<em>Insert Root EObject</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INSERT_ROOT_EOBJECT_OPERATION_COUNT = ChangePackage.ADDITIVE_REFERENCE_CHANGE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl.RemoveRootEObjectImpl <em>Remove Root EObject</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl.RemoveRootEObjectImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl.RootPackageImpl#getRemoveRootEObject()
     * @generated
     */
    int REMOVE_ROOT_EOBJECT = 1;

    /**
     * The feature id for the '<em><b>Old TUID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_ROOT_EOBJECT__OLD_TUID = ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__OLD_TUID;

    /**
     * The feature id for the '<em><b>Feature Name2 Old Value Map</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_ROOT_EOBJECT__FEATURE_NAME2_OLD_VALUE_MAP = ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE__FEATURE_NAME2_OLD_VALUE_MAP;

    /**
     * The number of structural features of the '<em>Remove Root EObject</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_ROOT_EOBJECT_FEATURE_COUNT = ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE_FEATURE_COUNT + 0;

    /**
     * The operation id for the '<em>Get Old Value</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_ROOT_EOBJECT___GET_OLD_VALUE = ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE___GET_OLD_VALUE;

    /**
     * The number of operations of the '<em>Remove Root EObject</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_ROOT_EOBJECT_OPERATION_COUNT = ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE_OPERATION_COUNT + 0;


    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.InsertRootEObject <em>Insert Root EObject</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Insert Root EObject</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.InsertRootEObject
     * @generated
     */
    EClass getInsertRootEObject();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RemoveRootEObject <em>Remove Root EObject</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Remove Root EObject</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RemoveRootEObject
     * @generated
     */
    EClass getRemoveRootEObject();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    RootFactory getRootFactory();

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
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl.InsertRootEObjectImpl <em>Insert Root EObject</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl.InsertRootEObjectImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl.RootPackageImpl#getInsertRootEObject()
         * @generated
         */
        EClass INSERT_ROOT_EOBJECT = eINSTANCE.getInsertRootEObject();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl.RemoveRootEObjectImpl <em>Remove Root EObject</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl.RemoveRootEObjectImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl.RootPackageImpl#getRemoveRootEObject()
         * @generated
         */
        EClass REMOVE_ROOT_EOBJECT = eINSTANCE.getRemoveRootEObject();

    }

} //RootPackage
