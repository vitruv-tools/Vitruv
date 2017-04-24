/**
 */
package model.conflict;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see model.conflict.ConflictFactory
 * @model kind="package"
 * @generated
 */
public interface ConflictPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "conflict";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv/versioning/1.0/conflict";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "tools.vitruv.framework.versioning";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ConflictPackage eINSTANCE = model.conflict.impl.ConflictPackageImpl.init();

	/**
	 * The meta object id for the '{@link model.conflict.impl.ConflictImpl <em>Conflict</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see model.conflict.impl.ConflictImpl
	 * @see model.conflict.impl.ConflictPackageImpl#getConflict()
	 * @generated
	 */
	int CONFLICT = 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Solvability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT__SOLVABILITY = 1;

	/**
	 * The number of structural features of the '<em>Conflict</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Conflict</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link model.conflict.impl.SimpleChangeConflictImpl <em>Simple Change Conflict</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see model.conflict.impl.SimpleChangeConflictImpl
	 * @see model.conflict.impl.ConflictPackageImpl#getSimpleChangeConflict()
	 * @generated
	 */
	int SIMPLE_CHANGE_CONFLICT = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_CHANGE_CONFLICT__TYPE = CONFLICT__TYPE;

	/**
	 * The feature id for the '<em><b>Solvability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_CHANGE_CONFLICT__SOLVABILITY = CONFLICT__SOLVABILITY;

	/**
	 * The feature id for the '<em><b>Source Change</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_CHANGE_CONFLICT__SOURCE_CHANGE = CONFLICT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target Change</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_CHANGE_CONFLICT__TARGET_CHANGE = CONFLICT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Simple Change Conflict</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_CHANGE_CONFLICT_FEATURE_COUNT = CONFLICT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Simple Change Conflict</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_CHANGE_CONFLICT_OPERATION_COUNT = CONFLICT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link model.conflict.impl.MultiChangeConflictImpl <em>Multi Change Conflict</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see model.conflict.impl.MultiChangeConflictImpl
	 * @see model.conflict.impl.ConflictPackageImpl#getMultiChangeConflict()
	 * @generated
	 */
	int MULTI_CHANGE_CONFLICT = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_CHANGE_CONFLICT__TYPE = CONFLICT__TYPE;

	/**
	 * The feature id for the '<em><b>Solvability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_CHANGE_CONFLICT__SOLVABILITY = CONFLICT__SOLVABILITY;

	/**
	 * The feature id for the '<em><b>Source Changes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_CHANGE_CONFLICT__SOURCE_CHANGES = CONFLICT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target Changes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_CHANGE_CONFLICT__TARGET_CHANGES = CONFLICT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Multi Change Conflict</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_CHANGE_CONFLICT_FEATURE_COUNT = CONFLICT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Multi Change Conflict</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_CHANGE_CONFLICT_OPERATION_COUNT = CONFLICT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link model.conflict.impl.ConflictDetectorImpl <em>Detector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see model.conflict.impl.ConflictDetectorImpl
	 * @see model.conflict.impl.ConflictPackageImpl#getConflictDetector()
	 * @generated
	 */
	int CONFLICT_DETECTOR = 2;

	/**
	 * The number of structural features of the '<em>Detector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT_DETECTOR_FEATURE_COUNT = 0;

	/**
	 * The operation id for the '<em>Detect Conflicts</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT_DETECTOR___DETECT_CONFLICTS__BRANCHDIFF = 0;

	/**
	 * The number of operations of the '<em>Detector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT_DETECTOR_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link model.conflict.ConflictType <em>Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see model.conflict.ConflictType
	 * @see model.conflict.impl.ConflictPackageImpl#getConflictType()
	 * @generated
	 */
	int CONFLICT_TYPE = 4;

	/**
	 * The meta object id for the '{@link model.conflict.ConflictSolvability <em>Solvability</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see model.conflict.ConflictSolvability
	 * @see model.conflict.impl.ConflictPackageImpl#getConflictSolvability()
	 * @generated
	 */
	int CONFLICT_SOLVABILITY = 5;


	/**
	 * Returns the meta object for class '{@link model.conflict.SimpleChangeConflict <em>Simple Change Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Change Conflict</em>'.
	 * @see model.conflict.SimpleChangeConflict
	 * @generated
	 */
	EClass getSimpleChangeConflict();

	/**
	 * Returns the meta object for the reference '{@link model.conflict.SimpleChangeConflict#getSourceChange <em>Source Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Change</em>'.
	 * @see model.conflict.SimpleChangeConflict#getSourceChange()
	 * @see #getSimpleChangeConflict()
	 * @generated
	 */
	EReference getSimpleChangeConflict_SourceChange();

	/**
	 * Returns the meta object for the reference '{@link model.conflict.SimpleChangeConflict#getTargetChange <em>Target Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Change</em>'.
	 * @see model.conflict.SimpleChangeConflict#getTargetChange()
	 * @see #getSimpleChangeConflict()
	 * @generated
	 */
	EReference getSimpleChangeConflict_TargetChange();

	/**
	 * Returns the meta object for class '{@link model.conflict.MultiChangeConflict <em>Multi Change Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Multi Change Conflict</em>'.
	 * @see model.conflict.MultiChangeConflict
	 * @generated
	 */
	EClass getMultiChangeConflict();

	/**
	 * Returns the meta object for the reference list '{@link model.conflict.MultiChangeConflict#getSourceChanges <em>Source Changes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Source Changes</em>'.
	 * @see model.conflict.MultiChangeConflict#getSourceChanges()
	 * @see #getMultiChangeConflict()
	 * @generated
	 */
	EReference getMultiChangeConflict_SourceChanges();

	/**
	 * Returns the meta object for the reference list '{@link model.conflict.MultiChangeConflict#getTargetChanges <em>Target Changes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Target Changes</em>'.
	 * @see model.conflict.MultiChangeConflict#getTargetChanges()
	 * @see #getMultiChangeConflict()
	 * @generated
	 */
	EReference getMultiChangeConflict_TargetChanges();

	/**
	 * Returns the meta object for class '{@link model.conflict.ConflictDetector <em>Detector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Detector</em>'.
	 * @see model.conflict.ConflictDetector
	 * @generated
	 */
	EClass getConflictDetector();

	/**
	 * Returns the meta object for the '{@link model.conflict.ConflictDetector#detectConflicts(model.branch.BranchDiff) <em>Detect Conflicts</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Detect Conflicts</em>' operation.
	 * @see model.conflict.ConflictDetector#detectConflicts(model.branch.BranchDiff)
	 * @generated
	 */
	EOperation getConflictDetector__DetectConflicts__BranchDiff();

	/**
	 * Returns the meta object for class '{@link model.conflict.Conflict <em>Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Conflict</em>'.
	 * @see model.conflict.Conflict
	 * @generated
	 */
	EClass getConflict();

	/**
	 * Returns the meta object for the attribute '{@link model.conflict.Conflict#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see model.conflict.Conflict#getType()
	 * @see #getConflict()
	 * @generated
	 */
	EAttribute getConflict_Type();

	/**
	 * Returns the meta object for the attribute '{@link model.conflict.Conflict#getSolvability <em>Solvability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Solvability</em>'.
	 * @see model.conflict.Conflict#getSolvability()
	 * @see #getConflict()
	 * @generated
	 */
	EAttribute getConflict_Solvability();

	/**
	 * Returns the meta object for enum '{@link model.conflict.ConflictType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Type</em>'.
	 * @see model.conflict.ConflictType
	 * @generated
	 */
	EEnum getConflictType();

	/**
	 * Returns the meta object for enum '{@link model.conflict.ConflictSolvability <em>Solvability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Solvability</em>'.
	 * @see model.conflict.ConflictSolvability
	 * @generated
	 */
	EEnum getConflictSolvability();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ConflictFactory getConflictFactory();

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
		 * The meta object literal for the '{@link model.conflict.impl.SimpleChangeConflictImpl <em>Simple Change Conflict</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see model.conflict.impl.SimpleChangeConflictImpl
		 * @see model.conflict.impl.ConflictPackageImpl#getSimpleChangeConflict()
		 * @generated
		 */
		EClass SIMPLE_CHANGE_CONFLICT = eINSTANCE.getSimpleChangeConflict();

		/**
		 * The meta object literal for the '<em><b>Source Change</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMPLE_CHANGE_CONFLICT__SOURCE_CHANGE = eINSTANCE.getSimpleChangeConflict_SourceChange();

		/**
		 * The meta object literal for the '<em><b>Target Change</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMPLE_CHANGE_CONFLICT__TARGET_CHANGE = eINSTANCE.getSimpleChangeConflict_TargetChange();

		/**
		 * The meta object literal for the '{@link model.conflict.impl.MultiChangeConflictImpl <em>Multi Change Conflict</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see model.conflict.impl.MultiChangeConflictImpl
		 * @see model.conflict.impl.ConflictPackageImpl#getMultiChangeConflict()
		 * @generated
		 */
		EClass MULTI_CHANGE_CONFLICT = eINSTANCE.getMultiChangeConflict();

		/**
		 * The meta object literal for the '<em><b>Source Changes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MULTI_CHANGE_CONFLICT__SOURCE_CHANGES = eINSTANCE.getMultiChangeConflict_SourceChanges();

		/**
		 * The meta object literal for the '<em><b>Target Changes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MULTI_CHANGE_CONFLICT__TARGET_CHANGES = eINSTANCE.getMultiChangeConflict_TargetChanges();

		/**
		 * The meta object literal for the '{@link model.conflict.impl.ConflictDetectorImpl <em>Detector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see model.conflict.impl.ConflictDetectorImpl
		 * @see model.conflict.impl.ConflictPackageImpl#getConflictDetector()
		 * @generated
		 */
		EClass CONFLICT_DETECTOR = eINSTANCE.getConflictDetector();

		/**
		 * The meta object literal for the '<em><b>Detect Conflicts</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation CONFLICT_DETECTOR___DETECT_CONFLICTS__BRANCHDIFF = eINSTANCE.getConflictDetector__DetectConflicts__BranchDiff();

		/**
		 * The meta object literal for the '{@link model.conflict.impl.ConflictImpl <em>Conflict</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see model.conflict.impl.ConflictImpl
		 * @see model.conflict.impl.ConflictPackageImpl#getConflict()
		 * @generated
		 */
		EClass CONFLICT = eINSTANCE.getConflict();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFLICT__TYPE = eINSTANCE.getConflict_Type();

		/**
		 * The meta object literal for the '<em><b>Solvability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFLICT__SOLVABILITY = eINSTANCE.getConflict_Solvability();

		/**
		 * The meta object literal for the '{@link model.conflict.ConflictType <em>Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see model.conflict.ConflictType
		 * @see model.conflict.impl.ConflictPackageImpl#getConflictType()
		 * @generated
		 */
		EEnum CONFLICT_TYPE = eINSTANCE.getConflictType();

		/**
		 * The meta object literal for the '{@link model.conflict.ConflictSolvability <em>Solvability</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see model.conflict.ConflictSolvability
		 * @see model.conflict.impl.ConflictPackageImpl#getConflictSolvability()
		 * @generated
		 */
		EEnum CONFLICT_SOLVABILITY = eINSTANCE.getConflictSolvability();

	}

} //ConflictPackage
