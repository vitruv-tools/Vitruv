/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
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
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundFactory
 * @model kind="package"
 * @generated
 */
public interface CompoundPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "compound";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://edu.kit.ipd.sdq.vitruvius/Change/Compound/1.0";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "compound";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    CompoundPackage eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl.init();

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.ECompoundChangeImpl <em>ECompound Change</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.ECompoundChangeImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl#getECompoundChange()
     * @generated
     */
    int ECOMPOUND_CHANGE = 0;

    /**
     * The number of structural features of the '<em>ECompound Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ECOMPOUND_CHANGE_FEATURE_COUNT = ChangePackage.ECHANGE_FEATURE_COUNT + 0;

    /**
     * The operation id for the '<em>Get Atomic Changes</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ECOMPOUND_CHANGE___GET_ATOMIC_CHANGES = ChangePackage.ECHANGE_OPERATION_COUNT + 0;

    /**
     * The number of operations of the '<em>ECompound Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ECOMPOUND_CHANGE_OPERATION_COUNT = ChangePackage.ECHANGE_OPERATION_COUNT + 1;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.MoveEObjectImpl <em>Move EObject</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.MoveEObjectImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl#getMoveEObject()
     * @generated
     */
    int MOVE_EOBJECT = 1;

    /**
     * The feature id for the '<em><b>Subtract Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MOVE_EOBJECT__SUBTRACT_CHANGE = ECOMPOUND_CHANGE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Add Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MOVE_EOBJECT__ADD_CHANGE = ECOMPOUND_CHANGE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Move EObject</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MOVE_EOBJECT_FEATURE_COUNT = ECOMPOUND_CHANGE_FEATURE_COUNT + 2;

    /**
     * The operation id for the '<em>Get Atomic Changes</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MOVE_EOBJECT___GET_ATOMIC_CHANGES = ECOMPOUND_CHANGE___GET_ATOMIC_CHANGES;

    /**
     * The number of operations of the '<em>Move EObject</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MOVE_EOBJECT_OPERATION_COUNT = ECOMPOUND_CHANGE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.ReplaceInEListImpl <em>Replace In EList</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.ReplaceInEListImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl#getReplaceInEList()
     * @generated
     */
    int REPLACE_IN_ELIST = 2;

    /**
     * The feature id for the '<em><b>Remove Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_IN_ELIST__REMOVE_CHANGE = ECOMPOUND_CHANGE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Insert Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_IN_ELIST__INSERT_CHANGE = ECOMPOUND_CHANGE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Replace In EList</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_IN_ELIST_FEATURE_COUNT = ECOMPOUND_CHANGE_FEATURE_COUNT + 2;

    /**
     * The operation id for the '<em>Get Atomic Changes</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_IN_ELIST___GET_ATOMIC_CHANGES = ECOMPOUND_CHANGE___GET_ATOMIC_CHANGES;

    /**
     * The number of operations of the '<em>Replace In EList</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_IN_ELIST_OPERATION_COUNT = ECOMPOUND_CHANGE_OPERATION_COUNT + 0;


    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.ExplicitUnsetEFeatureImpl <em>Explicit Unset EFeature</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.ExplicitUnsetEFeatureImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl#getExplicitUnsetEFeature()
     * @generated
     */
    int EXPLICIT_UNSET_EFEATURE = 3;

    /**
     * The feature id for the '<em><b>Subtractive Changes</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXPLICIT_UNSET_EFEATURE__SUBTRACTIVE_CHANGES = ECOMPOUND_CHANGE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Explicit Unset EFeature</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXPLICIT_UNSET_EFEATURE_FEATURE_COUNT = ECOMPOUND_CHANGE_FEATURE_COUNT + 1;

    /**
     * The operation id for the '<em>Get Atomic Changes</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXPLICIT_UNSET_EFEATURE___GET_ATOMIC_CHANGES = ECOMPOUND_CHANGE___GET_ATOMIC_CHANGES;

    /**
     * The number of operations of the '<em>Explicit Unset EFeature</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXPLICIT_UNSET_EFEATURE_OPERATION_COUNT = ECOMPOUND_CHANGE_OPERATION_COUNT + 0;


    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ECompoundChange <em>ECompound Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>ECompound Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ECompoundChange
     * @generated
     */
    EClass getECompoundChange();

    /**
     * Returns the meta object for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ECompoundChange#getAtomicChanges() <em>Get Atomic Changes</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the '<em>Get Atomic Changes</em>' operation.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ECompoundChange#getAtomicChanges()
     * @generated
     */
    EOperation getECompoundChange__GetAtomicChanges();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject <em>Move EObject</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Move EObject</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject
     * @generated
     */
    EClass getMoveEObject();

    /**
     * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject#getSubtractChange <em>Subtract Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Subtract Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject#getSubtractChange()
     * @see #getMoveEObject()
     * @generated
     */
    EReference getMoveEObject_SubtractChange();

    /**
     * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject#getAddChange <em>Add Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Add Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject#getAddChange()
     * @see #getMoveEObject()
     * @generated
     */
    EReference getMoveEObject_AddChange();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ReplaceInEList <em>Replace In EList</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Replace In EList</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ReplaceInEList
     * @generated
     */
    EClass getReplaceInEList();

    /**
     * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ReplaceInEList#getRemoveChange <em>Remove Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Remove Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ReplaceInEList#getRemoveChange()
     * @see #getReplaceInEList()
     * @generated
     */
    EReference getReplaceInEList_RemoveChange();

    /**
     * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ReplaceInEList#getInsertChange <em>Insert Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Insert Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ReplaceInEList#getInsertChange()
     * @see #getReplaceInEList()
     * @generated
     */
    EReference getReplaceInEList_InsertChange();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ExplicitUnsetEFeature <em>Explicit Unset EFeature</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Explicit Unset EFeature</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ExplicitUnsetEFeature
     * @generated
     */
    EClass getExplicitUnsetEFeature();

    /**
     * Returns the meta object for the reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ExplicitUnsetEFeature#getSubtractiveChanges <em>Subtractive Changes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Subtractive Changes</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ExplicitUnsetEFeature#getSubtractiveChanges()
     * @see #getExplicitUnsetEFeature()
     * @generated
     */
    EReference getExplicitUnsetEFeature_SubtractiveChanges();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    CompoundFactory getCompoundFactory();

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
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.ECompoundChangeImpl <em>ECompound Change</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.ECompoundChangeImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl#getECompoundChange()
         * @generated
         */
        EClass ECOMPOUND_CHANGE = eINSTANCE.getECompoundChange();

        /**
         * The meta object literal for the '<em><b>Get Atomic Changes</b></em>' operation.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EOperation ECOMPOUND_CHANGE___GET_ATOMIC_CHANGES = eINSTANCE.getECompoundChange__GetAtomicChanges();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.MoveEObjectImpl <em>Move EObject</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.MoveEObjectImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl#getMoveEObject()
         * @generated
         */
        EClass MOVE_EOBJECT = eINSTANCE.getMoveEObject();

        /**
         * The meta object literal for the '<em><b>Subtract Change</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MOVE_EOBJECT__SUBTRACT_CHANGE = eINSTANCE.getMoveEObject_SubtractChange();

        /**
         * The meta object literal for the '<em><b>Add Change</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MOVE_EOBJECT__ADD_CHANGE = eINSTANCE.getMoveEObject_AddChange();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.ReplaceInEListImpl <em>Replace In EList</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.ReplaceInEListImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl#getReplaceInEList()
         * @generated
         */
        EClass REPLACE_IN_ELIST = eINSTANCE.getReplaceInEList();

        /**
         * The meta object literal for the '<em><b>Remove Change</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REPLACE_IN_ELIST__REMOVE_CHANGE = eINSTANCE.getReplaceInEList_RemoveChange();

        /**
         * The meta object literal for the '<em><b>Insert Change</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REPLACE_IN_ELIST__INSERT_CHANGE = eINSTANCE.getReplaceInEList_InsertChange();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.ExplicitUnsetEFeatureImpl <em>Explicit Unset EFeature</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.ExplicitUnsetEFeatureImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl#getExplicitUnsetEFeature()
         * @generated
         */
        EClass EXPLICIT_UNSET_EFEATURE = eINSTANCE.getExplicitUnsetEFeature();

        /**
         * The meta object literal for the '<em><b>Subtractive Changes</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference EXPLICIT_UNSET_EFEATURE__SUBTRACTIVE_CHANGES = eINSTANCE.getExplicitUnsetEFeature_SubtractiveChanges();

    }

} //CompoundPackage
