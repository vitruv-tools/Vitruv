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
     * The operation id for the '<em>Get Composed Changes</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ECOMPOUND_CHANGE___GET_COMPOSED_CHANGES = ChangePackage.ECHANGE_OPERATION_COUNT + 0;

    /**
     * The number of operations of the '<em>ECompound Change</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ECOMPOUND_CHANGE_OPERATION_COUNT = ChangePackage.ECHANGE_OPERATION_COUNT + 1;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CreateEObjectAndAddImpl <em>Create EObject And Add</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CreateEObjectAndAddImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl#getCreateEObjectAndAdd()
     * @generated
     */
    int CREATE_EOBJECT_AND_ADD = 1;

    /**
     * The feature id for the '<em><b>Create Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CREATE_EOBJECT_AND_ADD__CREATE_CHANGE = ECOMPOUND_CHANGE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Add Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CREATE_EOBJECT_AND_ADD__ADD_CHANGE = ECOMPOUND_CHANGE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Create EObject And Add</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CREATE_EOBJECT_AND_ADD_FEATURE_COUNT = ECOMPOUND_CHANGE_FEATURE_COUNT + 2;

    /**
     * The operation id for the '<em>Get Composed Changes</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CREATE_EOBJECT_AND_ADD___GET_COMPOSED_CHANGES = ECOMPOUND_CHANGE___GET_COMPOSED_CHANGES;

    /**
     * The number of operations of the '<em>Create EObject And Add</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CREATE_EOBJECT_AND_ADD_OPERATION_COUNT = ECOMPOUND_CHANGE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectAndSubtractImpl <em>Delete EObject And Subtract</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectAndSubtractImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl#getDeleteEObjectAndSubtract()
     * @generated
     */
    int DELETE_EOBJECT_AND_SUBTRACT = 2;

    /**
     * The feature id for the '<em><b>Delete Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_AND_SUBTRACT__DELETE_CHANGE = ECOMPOUND_CHANGE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Subtract Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_AND_SUBTRACT__SUBTRACT_CHANGE = ECOMPOUND_CHANGE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Delete EObject And Subtract</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_AND_SUBTRACT_FEATURE_COUNT = ECOMPOUND_CHANGE_FEATURE_COUNT + 2;

    /**
     * The operation id for the '<em>Get Composed Changes</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_AND_SUBTRACT___GET_COMPOSED_CHANGES = ECOMPOUND_CHANGE___GET_COMPOSED_CHANGES;

    /**
     * The number of operations of the '<em>Delete EObject And Subtract</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_AND_SUBTRACT_OPERATION_COUNT = ECOMPOUND_CHANGE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectCreateEObjectAndReplaceImpl <em>Delete EObject Create EObject And Replace</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectCreateEObjectAndReplaceImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl#getDeleteEObjectCreateEObjectAndReplace()
     * @generated
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE = 3;

    /**
     * The feature id for the '<em><b>Delete Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__DELETE_CHANGE = ECOMPOUND_CHANGE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Create Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__CREATE_CHANGE = ECOMPOUND_CHANGE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Replace Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__REPLACE_CHANGE = ECOMPOUND_CHANGE_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Delete EObject Create EObject And Replace</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_FEATURE_COUNT = ECOMPOUND_CHANGE_FEATURE_COUNT + 3;

    /**
     * The operation id for the '<em>Get Composed Changes</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE___GET_COMPOSED_CHANGES = ECOMPOUND_CHANGE___GET_COMPOSED_CHANGES;

    /**
     * The number of operations of the '<em>Delete EObject Create EObject And Replace</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_OPERATION_COUNT = ECOMPOUND_CHANGE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectCreateEObjectAndReplaceSingleImpl <em>Delete EObject Create EObject And Replace Single</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectCreateEObjectAndReplaceSingleImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl#getDeleteEObjectCreateEObjectAndReplaceSingle()
     * @generated
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_SINGLE = 4;

    /**
     * The feature id for the '<em><b>Delete Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_SINGLE__DELETE_CHANGE = DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__DELETE_CHANGE;

    /**
     * The feature id for the '<em><b>Create Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_SINGLE__CREATE_CHANGE = DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__CREATE_CHANGE;

    /**
     * The feature id for the '<em><b>Replace Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_SINGLE__REPLACE_CHANGE = DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__REPLACE_CHANGE;

    /**
     * The number of structural features of the '<em>Delete EObject Create EObject And Replace Single</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_SINGLE_FEATURE_COUNT = DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_FEATURE_COUNT + 0;

    /**
     * The operation id for the '<em>Get Composed Changes</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_SINGLE___GET_COMPOSED_CHANGES = DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE___GET_COMPOSED_CHANGES;

    /**
     * The number of operations of the '<em>Delete EObject Create EObject And Replace Single</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_SINGLE_OPERATION_COUNT = DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectCreateEObjectAndReplaceInListImpl <em>Delete EObject Create EObject And Replace In List</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectCreateEObjectAndReplaceInListImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl#getDeleteEObjectCreateEObjectAndReplaceInList()
     * @generated
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_IN_LIST = 5;

    /**
     * The feature id for the '<em><b>Delete Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_IN_LIST__DELETE_CHANGE = DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__DELETE_CHANGE;

    /**
     * The feature id for the '<em><b>Create Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_IN_LIST__CREATE_CHANGE = DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__CREATE_CHANGE;

    /**
     * The feature id for the '<em><b>Replace Change</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_IN_LIST__REPLACE_CHANGE = DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__REPLACE_CHANGE;

    /**
     * The number of structural features of the '<em>Delete EObject Create EObject And Replace In List</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_IN_LIST_FEATURE_COUNT = DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_FEATURE_COUNT + 0;

    /**
     * The operation id for the '<em>Get Composed Changes</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_IN_LIST___GET_COMPOSED_CHANGES = DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE___GET_COMPOSED_CHANGES;

    /**
     * The number of operations of the '<em>Delete EObject Create EObject And Replace In List</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_IN_LIST_OPERATION_COUNT = DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.MoveEObjectImpl <em>Move EObject</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.MoveEObjectImpl
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl#getMoveEObject()
     * @generated
     */
    int MOVE_EOBJECT = 6;

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
     * The operation id for the '<em>Get Composed Changes</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MOVE_EOBJECT___GET_COMPOSED_CHANGES = ECOMPOUND_CHANGE___GET_COMPOSED_CHANGES;

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
    int REPLACE_IN_ELIST = 7;

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
     * The operation id for the '<em>Get Composed Changes</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_IN_ELIST___GET_COMPOSED_CHANGES = ECOMPOUND_CHANGE___GET_COMPOSED_CHANGES;

    /**
     * The number of operations of the '<em>Replace In EList</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLACE_IN_ELIST_OPERATION_COUNT = ECOMPOUND_CHANGE_OPERATION_COUNT + 0;


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
     * Returns the meta object for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ECompoundChange#getComposedChanges() <em>Get Composed Changes</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the '<em>Get Composed Changes</em>' operation.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ECompoundChange#getComposedChanges()
     * @generated
     */
    EOperation getECompoundChange__GetComposedChanges();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CreateEObjectAndAdd <em>Create EObject And Add</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Create EObject And Add</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CreateEObjectAndAdd
     * @generated
     */
    EClass getCreateEObjectAndAdd();

    /**
     * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CreateEObjectAndAdd#getCreateChange <em>Create Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Create Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CreateEObjectAndAdd#getCreateChange()
     * @see #getCreateEObjectAndAdd()
     * @generated
     */
    EReference getCreateEObjectAndAdd_CreateChange();

    /**
     * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CreateEObjectAndAdd#getAddChange <em>Add Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Add Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CreateEObjectAndAdd#getAddChange()
     * @see #getCreateEObjectAndAdd()
     * @generated
     */
    EReference getCreateEObjectAndAdd_AddChange();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectAndSubtract <em>Delete EObject And Subtract</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete EObject And Subtract</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectAndSubtract
     * @generated
     */
    EClass getDeleteEObjectAndSubtract();

    /**
     * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectAndSubtract#getDeleteChange <em>Delete Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Delete Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectAndSubtract#getDeleteChange()
     * @see #getDeleteEObjectAndSubtract()
     * @generated
     */
    EReference getDeleteEObjectAndSubtract_DeleteChange();

    /**
     * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectAndSubtract#getSubtractChange <em>Subtract Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Subtract Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectAndSubtract#getSubtractChange()
     * @see #getDeleteEObjectAndSubtract()
     * @generated
     */
    EReference getDeleteEObjectAndSubtract_SubtractChange();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplace <em>Delete EObject Create EObject And Replace</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete EObject Create EObject And Replace</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplace
     * @generated
     */
    EClass getDeleteEObjectCreateEObjectAndReplace();

    /**
     * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplace#getDeleteChange <em>Delete Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Delete Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplace#getDeleteChange()
     * @see #getDeleteEObjectCreateEObjectAndReplace()
     * @generated
     */
    EReference getDeleteEObjectCreateEObjectAndReplace_DeleteChange();

    /**
     * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplace#getCreateChange <em>Create Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Create Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplace#getCreateChange()
     * @see #getDeleteEObjectCreateEObjectAndReplace()
     * @generated
     */
    EReference getDeleteEObjectCreateEObjectAndReplace_CreateChange();

    /**
     * Returns the meta object for the containment reference '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplace#getReplaceChange <em>Replace Change</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Replace Change</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplace#getReplaceChange()
     * @see #getDeleteEObjectCreateEObjectAndReplace()
     * @generated
     */
    EReference getDeleteEObjectCreateEObjectAndReplace_ReplaceChange();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplaceSingle <em>Delete EObject Create EObject And Replace Single</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete EObject Create EObject And Replace Single</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplaceSingle
     * @generated
     */
    EClass getDeleteEObjectCreateEObjectAndReplaceSingle();

    /**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplaceInList <em>Delete EObject Create EObject And Replace In List</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete EObject Create EObject And Replace In List</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplaceInList
     * @generated
     */
    EClass getDeleteEObjectCreateEObjectAndReplaceInList();

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
         * The meta object literal for the '<em><b>Get Composed Changes</b></em>' operation.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EOperation ECOMPOUND_CHANGE___GET_COMPOSED_CHANGES = eINSTANCE.getECompoundChange__GetComposedChanges();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CreateEObjectAndAddImpl <em>Create EObject And Add</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CreateEObjectAndAddImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl#getCreateEObjectAndAdd()
         * @generated
         */
        EClass CREATE_EOBJECT_AND_ADD = eINSTANCE.getCreateEObjectAndAdd();

        /**
         * The meta object literal for the '<em><b>Create Change</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CREATE_EOBJECT_AND_ADD__CREATE_CHANGE = eINSTANCE.getCreateEObjectAndAdd_CreateChange();

        /**
         * The meta object literal for the '<em><b>Add Change</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CREATE_EOBJECT_AND_ADD__ADD_CHANGE = eINSTANCE.getCreateEObjectAndAdd_AddChange();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectAndSubtractImpl <em>Delete EObject And Subtract</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectAndSubtractImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl#getDeleteEObjectAndSubtract()
         * @generated
         */
        EClass DELETE_EOBJECT_AND_SUBTRACT = eINSTANCE.getDeleteEObjectAndSubtract();

        /**
         * The meta object literal for the '<em><b>Delete Change</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DELETE_EOBJECT_AND_SUBTRACT__DELETE_CHANGE = eINSTANCE.getDeleteEObjectAndSubtract_DeleteChange();

        /**
         * The meta object literal for the '<em><b>Subtract Change</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DELETE_EOBJECT_AND_SUBTRACT__SUBTRACT_CHANGE = eINSTANCE.getDeleteEObjectAndSubtract_SubtractChange();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectCreateEObjectAndReplaceImpl <em>Delete EObject Create EObject And Replace</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectCreateEObjectAndReplaceImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl#getDeleteEObjectCreateEObjectAndReplace()
         * @generated
         */
        EClass DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE = eINSTANCE.getDeleteEObjectCreateEObjectAndReplace();

        /**
         * The meta object literal for the '<em><b>Delete Change</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__DELETE_CHANGE = eINSTANCE.getDeleteEObjectCreateEObjectAndReplace_DeleteChange();

        /**
         * The meta object literal for the '<em><b>Create Change</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__CREATE_CHANGE = eINSTANCE.getDeleteEObjectCreateEObjectAndReplace_CreateChange();

        /**
         * The meta object literal for the '<em><b>Replace Change</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE__REPLACE_CHANGE = eINSTANCE.getDeleteEObjectCreateEObjectAndReplace_ReplaceChange();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectCreateEObjectAndReplaceSingleImpl <em>Delete EObject Create EObject And Replace Single</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectCreateEObjectAndReplaceSingleImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl#getDeleteEObjectCreateEObjectAndReplaceSingle()
         * @generated
         */
        EClass DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_SINGLE = eINSTANCE.getDeleteEObjectCreateEObjectAndReplaceSingle();

        /**
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectCreateEObjectAndReplaceInListImpl <em>Delete EObject Create EObject And Replace In List</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.DeleteEObjectCreateEObjectAndReplaceInListImpl
         * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundPackageImpl#getDeleteEObjectCreateEObjectAndReplaceInList()
         * @generated
         */
        EClass DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_IN_LIST = eINSTANCE.getDeleteEObjectCreateEObjectAndReplaceInList();

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

    }

} //CompoundPackage
