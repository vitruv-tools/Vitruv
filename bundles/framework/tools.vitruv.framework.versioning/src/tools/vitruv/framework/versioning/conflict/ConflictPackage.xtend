/** 
 */
package tools.vitruv.framework.versioning.conflict

import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EEnum
import org.eclipse.emf.ecore.EOperation
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference

/** 
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.versioning.conflict.ConflictFactory
 * @model kind="package"
 * @generated
 */
interface ConflictPackage extends EPackage {
	/** 
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "conflict"
	/** 
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv/versioning/1.0/conflict"
	/** 
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "tools.vitruv.framework.versioning"
	/** 
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ConflictPackage eINSTANCE = tools.vitruv.framework.versioning.conflict.impl.ConflictPackageImpl::init()
	/** 
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.conflict.impl.ConflictImpl <em>Conflict</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.conflict.impl.ConflictImpl
	 * @see tools.vitruv.framework.versioning.conflict.impl.ConflictPackageImpl#getConflict()
	 * @generated
	 */
	int CONFLICT = 3
	/** 
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT__TYPE = 0
	/** 
	 * The feature id for the '<em><b>Solvability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT__SOLVABILITY = 1
	/** 
	 * The feature id for the '<em><b>Original Changes Levenshtein Distance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT__ORIGINAL_CHANGES_LEVENSHTEIN_DISTANCE = 2
	/** 
	 * The number of structural features of the '<em>Conflict</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT_FEATURE_COUNT = 3
	/** 
	 * The operation id for the '<em>Resolve Conflict</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT___RESOLVE_CONFLICT__ELIST_ELIST = 0
	/** 
	 * The number of operations of the '<em>Conflict</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT_OPERATION_COUNT = 1
	/** 
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.conflict.impl.SimpleChangeConflictImpl <em>Simple Change Conflict</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.conflict.impl.SimpleChangeConflictImpl
	 * @see tools.vitruv.framework.versioning.conflict.impl.ConflictPackageImpl#getSimpleChangeConflict()
	 * @generated
	 */
	int SIMPLE_CHANGE_CONFLICT = 0
	/** 
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_CHANGE_CONFLICT__TYPE = CONFLICT__TYPE
	/** 
	 * The feature id for the '<em><b>Solvability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_CHANGE_CONFLICT__SOLVABILITY = CONFLICT__SOLVABILITY
	/** 
	 * The feature id for the '<em><b>Original Changes Levenshtein Distance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_CHANGE_CONFLICT__ORIGINAL_CHANGES_LEVENSHTEIN_DISTANCE = CONFLICT__ORIGINAL_CHANGES_LEVENSHTEIN_DISTANCE
	/** 
	 * The feature id for the '<em><b>Source Change</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_CHANGE_CONFLICT__SOURCE_CHANGE = CONFLICT_FEATURE_COUNT + 0
	/** 
	 * The feature id for the '<em><b>Target Change</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_CHANGE_CONFLICT__TARGET_CHANGE = CONFLICT_FEATURE_COUNT + 1
	/** 
	 * The number of structural features of the '<em>Simple Change Conflict</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_CHANGE_CONFLICT_FEATURE_COUNT = CONFLICT_FEATURE_COUNT + 2
	/** 
	 * The operation id for the '<em>Resolve Conflict</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_CHANGE_CONFLICT___RESOLVE_CONFLICT__ELIST_ELIST = CONFLICT___RESOLVE_CONFLICT__ELIST_ELIST
	/** 
	 * The number of operations of the '<em>Simple Change Conflict</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_CHANGE_CONFLICT_OPERATION_COUNT = CONFLICT_OPERATION_COUNT + 0
	/** 
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.conflict.impl.MultiChangeConflictImpl <em>Multi Change Conflict</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.conflict.impl.MultiChangeConflictImpl
	 * @see tools.vitruv.framework.versioning.conflict.impl.ConflictPackageImpl#getMultiChangeConflict()
	 * @generated
	 */
	int MULTI_CHANGE_CONFLICT = 1
	/** 
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_CHANGE_CONFLICT__TYPE = CONFLICT__TYPE
	/** 
	 * The feature id for the '<em><b>Solvability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_CHANGE_CONFLICT__SOLVABILITY = CONFLICT__SOLVABILITY
	/** 
	 * The feature id for the '<em><b>Original Changes Levenshtein Distance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_CHANGE_CONFLICT__ORIGINAL_CHANGES_LEVENSHTEIN_DISTANCE = CONFLICT__ORIGINAL_CHANGES_LEVENSHTEIN_DISTANCE
	/** 
	 * The feature id for the '<em><b>Source Changes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_CHANGE_CONFLICT__SOURCE_CHANGES = CONFLICT_FEATURE_COUNT + 0
	/** 
	 * The feature id for the '<em><b>Target Changes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_CHANGE_CONFLICT__TARGET_CHANGES = CONFLICT_FEATURE_COUNT + 1
	/** 
	 * The number of structural features of the '<em>Multi Change Conflict</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_CHANGE_CONFLICT_FEATURE_COUNT = CONFLICT_FEATURE_COUNT + 2
	/** 
	 * The operation id for the '<em>Resolve Conflict</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_CHANGE_CONFLICT___RESOLVE_CONFLICT__ELIST_ELIST = CONFLICT___RESOLVE_CONFLICT__ELIST_ELIST
	/** 
	 * The number of operations of the '<em>Multi Change Conflict</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_CHANGE_CONFLICT_OPERATION_COUNT = CONFLICT_OPERATION_COUNT + 0
	/** 
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.conflict.impl.ConflictDetectorImpl <em>Detector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.conflict.impl.ConflictDetectorImpl
	 * @see tools.vitruv.framework.versioning.conflict.impl.ConflictPackageImpl#getConflictDetector()
	 * @generated
	 */
	int CONFLICT_DETECTOR = 2
	/** 
	 * The number of structural features of the '<em>Detector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT_DETECTOR_FEATURE_COUNT = 0
	/** 
	 * The operation id for the '<em>Detect Conflicts</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT_DETECTOR___DETECT_CONFLICTS__BRANCHDIFF = 0
	/** 
	 * The number of operations of the '<em>Detector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT_DETECTOR_OPERATION_COUNT = 1
	/** 
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.conflict.ConflictType <em>Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.conflict.ConflictType
	 * @see tools.vitruv.framework.versioning.conflict.impl.ConflictPackageImpl#getConflictType()
	 * @generated
	 */
	int CONFLICT_TYPE = 4
	/** 
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.conflict.ConflictSolvability <em>Solvability</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.conflict.ConflictSolvability
	 * @see tools.vitruv.framework.versioning.conflict.impl.ConflictPackageImpl#getConflictSolvability()
	 * @generated
	 */
	int CONFLICT_SOLVABILITY = 5

	/** 
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.conflict.SimpleChangeConflict <em>Simple Change Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Change Conflict</em>'.
	 * @see tools.vitruv.framework.versioning.conflict.SimpleChangeConflict
	 * @generated
	 */
	def EClass getSimpleChangeConflict()

	/** 
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.conflict.SimpleChangeConflict#getSourceChange <em>Source Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Change</em>'.
	 * @see tools.vitruv.framework.versioning.conflict.SimpleChangeConflict#getSourceChange()
	 * @see #getSimpleChangeConflict()
	 * @generated
	 */
	def EReference getSimpleChangeConflict_SourceChange()

	/** 
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.conflict.SimpleChangeConflict#getTargetChange <em>Target Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Change</em>'.
	 * @see tools.vitruv.framework.versioning.conflict.SimpleChangeConflict#getTargetChange()
	 * @see #getSimpleChangeConflict()
	 * @generated
	 */
	def EReference getSimpleChangeConflict_TargetChange()

	/** 
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.conflict.MultiChangeConflict <em>Multi Change Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Multi Change Conflict</em>'.
	 * @see tools.vitruv.framework.versioning.conflict.MultiChangeConflict
	 * @generated
	 */
	def EClass getMultiChangeConflict()

	/** 
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.versioning.conflict.MultiChangeConflict#getSourceChanges <em>Source Changes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Source Changes</em>'.
	 * @see tools.vitruv.framework.versioning.conflict.MultiChangeConflict#getSourceChanges()
	 * @see #getMultiChangeConflict()
	 * @generated
	 */
	def EReference getMultiChangeConflict_SourceChanges()

	/** 
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.versioning.conflict.MultiChangeConflict#getTargetChanges <em>Target Changes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Target Changes</em>'.
	 * @see tools.vitruv.framework.versioning.conflict.MultiChangeConflict#getTargetChanges()
	 * @see #getMultiChangeConflict()
	 * @generated
	 */
	def EReference getMultiChangeConflict_TargetChanges()

	/** 
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.conflict.ConflictDetector <em>Detector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Detector</em>'.
	 * @see tools.vitruv.framework.versioning.conflict.ConflictDetector
	 * @generated
	 */
	def EClass getConflictDetector()

	/** 
	 * Returns the meta object for the '{@link tools.vitruv.framework.versioning.conflict.ConflictDetector#detectConflicts(tools.vitruv.framework.versioning.branch.BranchDiff) <em>Detect Conflicts</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Detect Conflicts</em>' operation.
	 * @see tools.vitruv.framework.versioning.conflict.ConflictDetector#detectConflicts(tools.vitruv.framework.versioning.branch.BranchDiff)
	 * @generated
	 */
	def EOperation getConflictDetector__DetectConflicts__BranchDiff()

	/** 
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.conflict.Conflict <em>Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Conflict</em>'.
	 * @see tools.vitruv.framework.versioning.conflict.Conflict
	 * @generated
	 */
	def EClass getConflict()

	/** 
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.versioning.conflict.Conflict#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see tools.vitruv.framework.versioning.conflict.Conflict#getType()
	 * @see #getConflict()
	 * @generated
	 */
	def EAttribute getConflict_Type()

	/** 
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.versioning.conflict.Conflict#getSolvability <em>Solvability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Solvability</em>'.
	 * @see tools.vitruv.framework.versioning.conflict.Conflict#getSolvability()
	 * @see #getConflict()
	 * @generated
	 */
	def EAttribute getConflict_Solvability()

	/** 
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.versioning.conflict.Conflict#getOriginalChangesLevenshteinDistance <em>Original Changes Levenshtein Distance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Original Changes Levenshtein Distance</em>'.
	 * @see tools.vitruv.framework.versioning.conflict.Conflict#getOriginalChangesLevenshteinDistance()
	 * @see #getConflict()
	 * @generated
	 */
	def EAttribute getConflict_OriginalChangesLevenshteinDistance()

	/** 
	 * Returns the meta object for the '{@link tools.vitruv.framework.versioning.conflict.Conflict#resolveConflict(org.eclipse.emf.common.util.EList, org.eclipse.emf.common.util.EList) <em>Resolve Conflict</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Resolve Conflict</em>' operation.
	 * @see tools.vitruv.framework.versioning.conflict.Conflict#resolveConflict(org.eclipse.emf.common.util.EList, org.eclipse.emf.common.util.EList)
	 * @generated
	 */
	def EOperation getConflict__ResolveConflict__EList_EList()

	/** 
	 * Returns the meta object for enum '{@link tools.vitruv.framework.versioning.conflict.ConflictType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Type</em>'.
	 * @see tools.vitruv.framework.versioning.conflict.ConflictType
	 * @generated
	 */
	def EEnum getConflictType()

	/** 
	 * Returns the meta object for enum '{@link tools.vitruv.framework.versioning.conflict.ConflictSolvability <em>Solvability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Solvability</em>'.
	 * @see tools.vitruv.framework.versioning.conflict.ConflictSolvability
	 * @generated
	 */
	def EEnum getConflictSolvability()

	/** 
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	def ConflictFactory getConflictFactory()

	/** 
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/** 
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.conflict.impl.SimpleChangeConflictImpl <em>Simple Change Conflict</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.conflict.impl.SimpleChangeConflictImpl
		 * @see tools.vitruv.framework.versioning.conflict.impl.ConflictPackageImpl#getSimpleChangeConflict()
		 * @generated
		 */
		EClass SIMPLE_CHANGE_CONFLICT = eINSTANCE.getSimpleChangeConflict()
		/** 
		 * The meta object literal for the '<em><b>Source Change</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMPLE_CHANGE_CONFLICT__SOURCE_CHANGE = eINSTANCE.getSimpleChangeConflict_SourceChange()
		/** 
		 * The meta object literal for the '<em><b>Target Change</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMPLE_CHANGE_CONFLICT__TARGET_CHANGE = eINSTANCE.getSimpleChangeConflict_TargetChange()
		/** 
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.conflict.impl.MultiChangeConflictImpl <em>Multi Change Conflict</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.conflict.impl.MultiChangeConflictImpl
		 * @see tools.vitruv.framework.versioning.conflict.impl.ConflictPackageImpl#getMultiChangeConflict()
		 * @generated
		 */
		EClass MULTI_CHANGE_CONFLICT = eINSTANCE.getMultiChangeConflict()
		/** 
		 * The meta object literal for the '<em><b>Source Changes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MULTI_CHANGE_CONFLICT__SOURCE_CHANGES = eINSTANCE.getMultiChangeConflict_SourceChanges()
		/** 
		 * The meta object literal for the '<em><b>Target Changes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MULTI_CHANGE_CONFLICT__TARGET_CHANGES = eINSTANCE.getMultiChangeConflict_TargetChanges()
		/** 
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.conflict.impl.ConflictDetectorImpl <em>Detector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.conflict.impl.ConflictDetectorImpl
		 * @see tools.vitruv.framework.versioning.conflict.impl.ConflictPackageImpl#getConflictDetector()
		 * @generated
		 */
		EClass CONFLICT_DETECTOR = eINSTANCE.getConflictDetector()
		/** 
		 * The meta object literal for the '<em><b>Detect Conflicts</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation CONFLICT_DETECTOR___DETECT_CONFLICTS__BRANCHDIFF = eINSTANCE.
			getConflictDetector__DetectConflicts__BranchDiff()
		/** 
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.conflict.impl.ConflictImpl <em>Conflict</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.conflict.impl.ConflictImpl
		 * @see tools.vitruv.framework.versioning.conflict.impl.ConflictPackageImpl#getConflict()
		 * @generated
		 */
		EClass CONFLICT = eINSTANCE.getConflict()
		/** 
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFLICT__TYPE = eINSTANCE.getConflict_Type()
		/** 
		 * The meta object literal for the '<em><b>Solvability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFLICT__SOLVABILITY = eINSTANCE.getConflict_Solvability()
		/** 
		 * The meta object literal for the '<em><b>Original Changes Levenshtein Distance</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFLICT__ORIGINAL_CHANGES_LEVENSHTEIN_DISTANCE = eINSTANCE.
			getConflict_OriginalChangesLevenshteinDistance()
		/** 
		 * The meta object literal for the '<em><b>Resolve Conflict</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation CONFLICT___RESOLVE_CONFLICT__ELIST_ELIST = eINSTANCE.getConflict__ResolveConflict__EList_EList()
		/** 
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.conflict.ConflictType <em>Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.conflict.ConflictType
		 * @see tools.vitruv.framework.versioning.conflict.impl.ConflictPackageImpl#getConflictType()
		 * @generated
		 */
		EEnum CONFLICT_TYPE = eINSTANCE.getConflictType()
		/** 
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.conflict.ConflictSolvability <em>Solvability</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.conflict.ConflictSolvability
		 * @see tools.vitruv.framework.versioning.conflict.impl.ConflictPackageImpl#getConflictSolvability()
		 * @generated
		 */
		EEnum CONFLICT_SOLVABILITY = eINSTANCE.getConflictSolvability() // ConflictPackage
	}
}
