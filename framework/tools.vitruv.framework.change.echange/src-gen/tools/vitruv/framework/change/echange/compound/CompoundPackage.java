/**
 */
package tools.vitruv.framework.change.echange.compound;

import tools.vitruv.framework.change.echange.EChangePackage;
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
 * @see tools.vitruv.framework.change.echange.compound.CompoundFactory
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
    String eNS_URI = "http://tools.vitruv/EChange/Compound/1.0";

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
    CompoundPackage eINSTANCE = tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl.init();

    /**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.CompoundEChangeImpl <em>EChange</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundEChangeImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCompoundEChange()
	 * @generated
	 */
    int COMPOUND_ECHANGE = 0;

    /**
	 * The number of structural features of the '<em>EChange</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPOUND_ECHANGE_FEATURE_COUNT = EChangePackage.ECHANGE_FEATURE_COUNT + 0;

    /**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPOUND_ECHANGE___GET_ATOMIC_CHANGES = EChangePackage.ECHANGE_OPERATION_COUNT + 0;

    /**
	 * The number of operations of the '<em>EChange</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPOUND_ECHANGE_OPERATION_COUNT = EChangePackage.ECHANGE_OPERATION_COUNT + 1;

    /**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.MoveEObjectImpl <em>Move EObject</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.MoveEObjectImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getMoveEObject()
	 * @generated
	 */
    int MOVE_EOBJECT = 1;

    /**
	 * The feature id for the '<em><b>Subtract Where Change</b></em>' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MOVE_EOBJECT__SUBTRACT_WHERE_CHANGE = COMPOUND_ECHANGE_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Subtract What Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE = COMPOUND_ECHANGE_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Add Where Change</b></em>' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MOVE_EOBJECT__ADD_WHERE_CHANGE = COMPOUND_ECHANGE_FEATURE_COUNT + 2;

    /**
	 * The feature id for the '<em><b>Add What Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MOVE_EOBJECT__ADD_WHAT_CHANGE = COMPOUND_ECHANGE_FEATURE_COUNT + 3;

    /**
	 * The number of structural features of the '<em>Move EObject</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MOVE_EOBJECT_FEATURE_COUNT = COMPOUND_ECHANGE_FEATURE_COUNT + 4;

    /**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MOVE_EOBJECT___GET_ATOMIC_CHANGES = COMPOUND_ECHANGE_OPERATION_COUNT + 0;

    /**
	 * The number of operations of the '<em>Move EObject</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MOVE_EOBJECT_OPERATION_COUNT = COMPOUND_ECHANGE_OPERATION_COUNT + 1;

    /**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEFeatureImpl <em>Explicit Unset EFeature</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEFeatureImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getExplicitUnsetEFeature()
	 * @generated
	 */
    int EXPLICIT_UNSET_EFEATURE = 2;

    /**
	 * The feature id for the '<em><b>Subtractive Changes</b></em>' reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EXPLICIT_UNSET_EFEATURE__SUBTRACTIVE_CHANGES = COMPOUND_ECHANGE_FEATURE_COUNT + 0;

    /**
	 * The number of structural features of the '<em>Explicit Unset EFeature</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EXPLICIT_UNSET_EFEATURE_FEATURE_COUNT = COMPOUND_ECHANGE_FEATURE_COUNT + 1;

    /**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EXPLICIT_UNSET_EFEATURE___GET_ATOMIC_CHANGES = COMPOUND_ECHANGE___GET_ATOMIC_CHANGES;

    /**
	 * The number of operations of the '<em>Explicit Unset EFeature</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EXPLICIT_UNSET_EFEATURE_OPERATION_COUNT = COMPOUND_ECHANGE_OPERATION_COUNT + 0;


    /**
	 * The meta object id for the '{@link tools.vitruv.framework.change.echange.compound.impl.ReplaceInEListImpl <em>Replace In EList</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.change.echange.compound.impl.ReplaceInEListImpl
	 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getReplaceInEList()
	 * @generated
	 */
	int REPLACE_IN_ELIST = 3;

				/**
	 * The feature id for the '<em><b>Remove Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_IN_ELIST__REMOVE_CHANGE = COMPOUND_ECHANGE_FEATURE_COUNT + 0;

				/**
	 * The feature id for the '<em><b>Insert Change</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_IN_ELIST__INSERT_CHANGE = COMPOUND_ECHANGE_FEATURE_COUNT + 1;

				/**
	 * The number of structural features of the '<em>Replace In EList</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_IN_ELIST_FEATURE_COUNT = COMPOUND_ECHANGE_FEATURE_COUNT + 2;

				/**
	 * The operation id for the '<em>Get Atomic Changes</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_IN_ELIST___GET_ATOMIC_CHANGES = COMPOUND_ECHANGE___GET_ATOMIC_CHANGES;

				/**
	 * The number of operations of the '<em>Replace In EList</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPLACE_IN_ELIST_OPERATION_COUNT = COMPOUND_ECHANGE_OPERATION_COUNT + 0;


				/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.CompoundEChange <em>EChange</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EChange</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.CompoundEChange
	 * @generated
	 */
    EClass getCompoundEChange();

    /**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.compound.CompoundEChange#getAtomicChanges() <em>Get Atomic Changes</em>}' operation.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Atomic Changes</em>' operation.
	 * @see tools.vitruv.framework.change.echange.compound.CompoundEChange#getAtomicChanges()
	 * @generated
	 */
    EOperation getCompoundEChange__GetAtomicChanges();

    /**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.MoveEObject <em>Move EObject</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Move EObject</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.MoveEObject
	 * @generated
	 */
    EClass getMoveEObject();

    /**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.change.echange.compound.MoveEObject#getSubtractWhereChange <em>Subtract Where Change</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Subtract Where Change</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.MoveEObject#getSubtractWhereChange()
	 * @see #getMoveEObject()
	 * @generated
	 */
    EReference getMoveEObject_SubtractWhereChange();

    /**
	 * Returns the meta object for the containment reference '{@link tools.vitruv.framework.change.echange.compound.MoveEObject#getSubtractWhatChange <em>Subtract What Change</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Subtract What Change</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.MoveEObject#getSubtractWhatChange()
	 * @see #getMoveEObject()
	 * @generated
	 */
    EReference getMoveEObject_SubtractWhatChange();

    /**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.change.echange.compound.MoveEObject#getAddWhereChange <em>Add Where Change</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Add Where Change</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.MoveEObject#getAddWhereChange()
	 * @see #getMoveEObject()
	 * @generated
	 */
    EReference getMoveEObject_AddWhereChange();

    /**
	 * Returns the meta object for the containment reference '{@link tools.vitruv.framework.change.echange.compound.MoveEObject#getAddWhatChange <em>Add What Change</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Add What Change</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.MoveEObject#getAddWhatChange()
	 * @see #getMoveEObject()
	 * @generated
	 */
    EReference getMoveEObject_AddWhatChange();

    /**
	 * Returns the meta object for the '{@link tools.vitruv.framework.change.echange.compound.MoveEObject#getAtomicChanges() <em>Get Atomic Changes</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Atomic Changes</em>' operation.
	 * @see tools.vitruv.framework.change.echange.compound.MoveEObject#getAtomicChanges()
	 * @generated
	 */
	EOperation getMoveEObject__GetAtomicChanges();

				/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature <em>Explicit Unset EFeature</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Explicit Unset EFeature</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature
	 * @generated
	 */
    EClass getExplicitUnsetEFeature();

    /**
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature#getSubtractiveChanges <em>Subtractive Changes</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Subtractive Changes</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature#getSubtractiveChanges()
	 * @see #getExplicitUnsetEFeature()
	 * @generated
	 */
    EReference getExplicitUnsetEFeature_SubtractiveChanges();

    /**
	 * Returns the meta object for class '{@link tools.vitruv.framework.change.echange.compound.ReplaceInEList <em>Replace In EList</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Replace In EList</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.ReplaceInEList
	 * @generated
	 */
	EClass getReplaceInEList();

				/**
	 * Returns the meta object for the containment reference '{@link tools.vitruv.framework.change.echange.compound.ReplaceInEList#getRemoveChange <em>Remove Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Remove Change</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.ReplaceInEList#getRemoveChange()
	 * @see #getReplaceInEList()
	 * @generated
	 */
	EReference getReplaceInEList_RemoveChange();

				/**
	 * Returns the meta object for the containment reference '{@link tools.vitruv.framework.change.echange.compound.ReplaceInEList#getInsertChange <em>Insert Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Insert Change</em>'.
	 * @see tools.vitruv.framework.change.echange.compound.ReplaceInEList#getInsertChange()
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
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.CompoundEChangeImpl <em>EChange</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundEChangeImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getCompoundEChange()
		 * @generated
		 */
        EClass COMPOUND_ECHANGE = eINSTANCE.getCompoundEChange();

        /**
		 * The meta object literal for the '<em><b>Get Atomic Changes</b></em>' operation.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EOperation COMPOUND_ECHANGE___GET_ATOMIC_CHANGES = eINSTANCE.getCompoundEChange__GetAtomicChanges();

        /**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.MoveEObjectImpl <em>Move EObject</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.MoveEObjectImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getMoveEObject()
		 * @generated
		 */
        EClass MOVE_EOBJECT = eINSTANCE.getMoveEObject();

        /**
		 * The meta object literal for the '<em><b>Subtract Where Change</b></em>' reference feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference MOVE_EOBJECT__SUBTRACT_WHERE_CHANGE = eINSTANCE.getMoveEObject_SubtractWhereChange();

        /**
		 * The meta object literal for the '<em><b>Subtract What Change</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE = eINSTANCE.getMoveEObject_SubtractWhatChange();

        /**
		 * The meta object literal for the '<em><b>Add Where Change</b></em>' reference feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference MOVE_EOBJECT__ADD_WHERE_CHANGE = eINSTANCE.getMoveEObject_AddWhereChange();

        /**
		 * The meta object literal for the '<em><b>Add What Change</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference MOVE_EOBJECT__ADD_WHAT_CHANGE = eINSTANCE.getMoveEObject_AddWhatChange();

        /**
		 * The meta object literal for the '<em><b>Get Atomic Changes</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MOVE_EOBJECT___GET_ATOMIC_CHANGES = eINSTANCE.getMoveEObject__GetAtomicChanges();

								/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEFeatureImpl <em>Explicit Unset EFeature</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEFeatureImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getExplicitUnsetEFeature()
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

								/**
		 * The meta object literal for the '{@link tools.vitruv.framework.change.echange.compound.impl.ReplaceInEListImpl <em>Replace In EList</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.change.echange.compound.impl.ReplaceInEListImpl
		 * @see tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl#getReplaceInEList()
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
