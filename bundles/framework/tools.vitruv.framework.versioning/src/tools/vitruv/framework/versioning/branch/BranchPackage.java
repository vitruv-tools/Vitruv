/**
 */
package tools.vitruv.framework.versioning.branch;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import tools.vitruv.framework.versioning.VersioningPackage;

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
 * @see tools.vitruv.framework.versioning.branch.BranchFactory
 * @model kind="package"
 * @generated
 */
public interface BranchPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "branch";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv/versioning/1.0/branch";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "tools.vitruv.framework.versioning.branch";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BranchPackage eINSTANCE = tools.vitruv.framework.versioning.branch.impl.BranchPackageImpl.init();

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.branch.impl.BranchDiffCreatorImpl <em>Diff Creator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.branch.impl.BranchDiffCreatorImpl
	 * @see tools.vitruv.framework.versioning.branch.impl.BranchPackageImpl#getBranchDiffCreator()
	 * @generated
	 */
	int BRANCH_DIFF_CREATOR = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_DIFF_CREATOR__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_DIFF_CREATOR__TARGET = 1;

	/**
	 * The number of structural features of the '<em>Diff Creator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_DIFF_CREATOR_FEATURE_COUNT = 2;

	/**
	 * The operation id for the '<em>Create Version Diff</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_DIFF_CREATOR___CREATE_VERSION_DIFF = 0;

	/**
	 * The number of operations of the '<em>Diff Creator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_DIFF_CREATOR_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.branch.impl.BranchDiffImpl <em>Diff</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.branch.impl.BranchDiffImpl
	 * @see tools.vitruv.framework.versioning.branch.impl.BranchPackageImpl#getBranchDiff()
	 * @generated
	 */
	int BRANCH_DIFF = 1;

	/**
	 * The feature id for the '<em><b>Source Commits</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_DIFF__SOURCE_COMMITS = 0;

	/**
	 * The feature id for the '<em><b>Target Commits</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_DIFF__TARGET_COMMITS = 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_DIFF__SOURCE = 2;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_DIFF__TARGET = 3;

	/**
	 * The feature id for the '<em><b>Last Common Ancestor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_DIFF__LAST_COMMON_ANCESTOR = 4;

	/**
	 * The number of structural features of the '<em>Diff</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_DIFF_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Diff</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_DIFF_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.branch.impl.BranchImpl <em>Branch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.branch.impl.BranchImpl
	 * @see tools.vitruv.framework.versioning.branch.impl.BranchPackageImpl#getBranch()
	 * @generated
	 */
	int BRANCH = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH__NAME = VersioningPackage.NAMED__NAME;

	/**
	 * The feature id for the '<em><b>Current Head Commit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH__CURRENT_HEAD_COMMIT = VersioningPackage.NAMED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH__OWNER = VersioningPackage.NAMED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Contributors</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH__CONTRIBUTORS = VersioningPackage.NAMED_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Branched From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH__BRANCHED_FROM = VersioningPackage.NAMED_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Child Branches</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH__CHILD_BRANCHES = VersioningPackage.NAMED_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Branch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_FEATURE_COUNT = VersioningPackage.NAMED_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Branch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_OPERATION_COUNT = VersioningPackage.NAMED_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.branch.BranchDiffCreator <em>Diff Creator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Diff Creator</em>'.
	 * @see tools.vitruv.framework.versioning.branch.BranchDiffCreator
	 * @generated
	 */
	EClass getBranchDiffCreator();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.branch.BranchDiffCreator#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see tools.vitruv.framework.versioning.branch.BranchDiffCreator#getSource()
	 * @see #getBranchDiffCreator()
	 * @generated
	 */
	EReference getBranchDiffCreator_Source();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.branch.BranchDiffCreator#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see tools.vitruv.framework.versioning.branch.BranchDiffCreator#getTarget()
	 * @see #getBranchDiffCreator()
	 * @generated
	 */
	EReference getBranchDiffCreator_Target();

	/**
	 * Returns the meta object for the '{@link tools.vitruv.framework.versioning.branch.BranchDiffCreator#createVersionDiff() <em>Create Version Diff</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Create Version Diff</em>' operation.
	 * @see tools.vitruv.framework.versioning.branch.BranchDiffCreator#createVersionDiff()
	 * @generated
	 */
	EOperation getBranchDiffCreator__CreateVersionDiff();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.branch.BranchDiff <em>Diff</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Diff</em>'.
	 * @see tools.vitruv.framework.versioning.branch.BranchDiff
	 * @generated
	 */
	EClass getBranchDiff();

	/**
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.versioning.branch.BranchDiff#getSourceCommits <em>Source Commits</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Source Commits</em>'.
	 * @see tools.vitruv.framework.versioning.branch.BranchDiff#getSourceCommits()
	 * @see #getBranchDiff()
	 * @generated
	 */
	EReference getBranchDiff_SourceCommits();

	/**
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.versioning.branch.BranchDiff#getTargetCommits <em>Target Commits</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Target Commits</em>'.
	 * @see tools.vitruv.framework.versioning.branch.BranchDiff#getTargetCommits()
	 * @see #getBranchDiff()
	 * @generated
	 */
	EReference getBranchDiff_TargetCommits();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.branch.BranchDiff#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see tools.vitruv.framework.versioning.branch.BranchDiff#getSource()
	 * @see #getBranchDiff()
	 * @generated
	 */
	EReference getBranchDiff_Source();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.branch.BranchDiff#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see tools.vitruv.framework.versioning.branch.BranchDiff#getTarget()
	 * @see #getBranchDiff()
	 * @generated
	 */
	EReference getBranchDiff_Target();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.branch.BranchDiff#getLastCommonAncestor <em>Last Common Ancestor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Last Common Ancestor</em>'.
	 * @see tools.vitruv.framework.versioning.branch.BranchDiff#getLastCommonAncestor()
	 * @see #getBranchDiff()
	 * @generated
	 */
	EReference getBranchDiff_LastCommonAncestor();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.branch.Branch <em>Branch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Branch</em>'.
	 * @see tools.vitruv.framework.versioning.branch.Branch
	 * @generated
	 */
	EClass getBranch();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.branch.Branch#getCurrentHeadCommit <em>Current Head Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Current Head Commit</em>'.
	 * @see tools.vitruv.framework.versioning.branch.Branch#getCurrentHeadCommit()
	 * @see #getBranch()
	 * @generated
	 */
	EReference getBranch_CurrentHeadCommit();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.branch.Branch#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Owner</em>'.
	 * @see tools.vitruv.framework.versioning.branch.Branch#getOwner()
	 * @see #getBranch()
	 * @generated
	 */
	EReference getBranch_Owner();

	/**
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.versioning.branch.Branch#getContributors <em>Contributors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Contributors</em>'.
	 * @see tools.vitruv.framework.versioning.branch.Branch#getContributors()
	 * @see #getBranch()
	 * @generated
	 */
	EReference getBranch_Contributors();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.branch.Branch#getBranchedFrom <em>Branched From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Branched From</em>'.
	 * @see tools.vitruv.framework.versioning.branch.Branch#getBranchedFrom()
	 * @see #getBranch()
	 * @generated
	 */
	EReference getBranch_BranchedFrom();

	/**
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.versioning.branch.Branch#getChildBranches <em>Child Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Child Branches</em>'.
	 * @see tools.vitruv.framework.versioning.branch.Branch#getChildBranches()
	 * @see #getBranch()
	 * @generated
	 */
	EReference getBranch_ChildBranches();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BranchFactory getBranchFactory();

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
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.branch.impl.BranchDiffCreatorImpl <em>Diff Creator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.branch.impl.BranchDiffCreatorImpl
		 * @see tools.vitruv.framework.versioning.branch.impl.BranchPackageImpl#getBranchDiffCreator()
		 * @generated
		 */
		EClass BRANCH_DIFF_CREATOR = eINSTANCE.getBranchDiffCreator();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BRANCH_DIFF_CREATOR__SOURCE = eINSTANCE.getBranchDiffCreator_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BRANCH_DIFF_CREATOR__TARGET = eINSTANCE.getBranchDiffCreator_Target();

		/**
		 * The meta object literal for the '<em><b>Create Version Diff</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BRANCH_DIFF_CREATOR___CREATE_VERSION_DIFF = eINSTANCE.getBranchDiffCreator__CreateVersionDiff();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.branch.impl.BranchDiffImpl <em>Diff</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.branch.impl.BranchDiffImpl
		 * @see tools.vitruv.framework.versioning.branch.impl.BranchPackageImpl#getBranchDiff()
		 * @generated
		 */
		EClass BRANCH_DIFF = eINSTANCE.getBranchDiff();

		/**
		 * The meta object literal for the '<em><b>Source Commits</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BRANCH_DIFF__SOURCE_COMMITS = eINSTANCE.getBranchDiff_SourceCommits();

		/**
		 * The meta object literal for the '<em><b>Target Commits</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BRANCH_DIFF__TARGET_COMMITS = eINSTANCE.getBranchDiff_TargetCommits();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BRANCH_DIFF__SOURCE = eINSTANCE.getBranchDiff_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BRANCH_DIFF__TARGET = eINSTANCE.getBranchDiff_Target();

		/**
		 * The meta object literal for the '<em><b>Last Common Ancestor</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BRANCH_DIFF__LAST_COMMON_ANCESTOR = eINSTANCE.getBranchDiff_LastCommonAncestor();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.branch.impl.BranchImpl <em>Branch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.branch.impl.BranchImpl
		 * @see tools.vitruv.framework.versioning.branch.impl.BranchPackageImpl#getBranch()
		 * @generated
		 */
		EClass BRANCH = eINSTANCE.getBranch();

		/**
		 * The meta object literal for the '<em><b>Current Head Commit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BRANCH__CURRENT_HEAD_COMMIT = eINSTANCE.getBranch_CurrentHeadCommit();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BRANCH__OWNER = eINSTANCE.getBranch_Owner();

		/**
		 * The meta object literal for the '<em><b>Contributors</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BRANCH__CONTRIBUTORS = eINSTANCE.getBranch_Contributors();

		/**
		 * The meta object literal for the '<em><b>Branched From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BRANCH__BRANCHED_FROM = eINSTANCE.getBranch_BranchedFrom();

		/**
		 * The meta object literal for the '<em><b>Child Branches</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BRANCH__CHILD_BRANCHES = eINSTANCE.getBranch_ChildBranches();

	}

} //BranchPackage
