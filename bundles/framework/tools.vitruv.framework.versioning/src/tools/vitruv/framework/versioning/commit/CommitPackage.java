/**
 */
package tools.vitruv.framework.versioning.commit;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see tools.vitruv.framework.versioning.commit.CommitFactory
 * @model kind="package"
 * @generated
 */
public interface CommitPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "commit";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv/versioning/1.0/commit";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "tools.vitruv.framework.versioning.commit";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CommitPackage eINSTANCE = tools.vitruv.framework.versioning.commit.impl.CommitPackageImpl.init();

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.commit.impl.CommitImpl <em>Commit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.commit.impl.CommitImpl
	 * @see tools.vitruv.framework.versioning.commit.impl.CommitPackageImpl#getCommit()
	 * @generated
	 */
	int COMMIT = 2;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT__SIGNATURE = VersioningPackage.SIGNED__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Checksum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT__CHECKSUM = VersioningPackage.SIGNED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Changes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT__CHANGES = VersioningPackage.SIGNED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Commitmessage</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT__COMMITMESSAGE = VersioningPackage.SIGNED_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Commits Branched From This</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT__COMMITS_BRANCHED_FROM_THIS = VersioningPackage.SIGNED_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Commits Merged From This</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT__COMMITS_MERGED_FROM_THIS = VersioningPackage.SIGNED_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT__IDENTIFIER = VersioningPackage.SIGNED_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Commit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT_FEATURE_COUNT = VersioningPackage.SIGNED_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>Commit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT_OPERATION_COUNT = VersioningPackage.SIGNED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.commit.impl.MergeCommitImpl <em>Merge Commit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.commit.impl.MergeCommitImpl
	 * @see tools.vitruv.framework.versioning.commit.impl.CommitPackageImpl#getMergeCommit()
	 * @generated
	 */
	int MERGE_COMMIT = 0;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_COMMIT__SIGNATURE = COMMIT__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Checksum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_COMMIT__CHECKSUM = COMMIT__CHECKSUM;

	/**
	 * The feature id for the '<em><b>Changes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_COMMIT__CHANGES = COMMIT__CHANGES;

	/**
	 * The feature id for the '<em><b>Commitmessage</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_COMMIT__COMMITMESSAGE = COMMIT__COMMITMESSAGE;

	/**
	 * The feature id for the '<em><b>Commits Branched From This</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_COMMIT__COMMITS_BRANCHED_FROM_THIS = COMMIT__COMMITS_BRANCHED_FROM_THIS;

	/**
	 * The feature id for the '<em><b>Commits Merged From This</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_COMMIT__COMMITS_MERGED_FROM_THIS = COMMIT__COMMITS_MERGED_FROM_THIS;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_COMMIT__IDENTIFIER = COMMIT__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Commits Merged To This</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_COMMIT__COMMITS_MERGED_TO_THIS = COMMIT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Merge Commit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_COMMIT_FEATURE_COUNT = COMMIT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Merge Commit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_COMMIT_OPERATION_COUNT = COMMIT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.commit.impl.SimpleCommitImpl <em>Simple Commit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.commit.impl.SimpleCommitImpl
	 * @see tools.vitruv.framework.versioning.commit.impl.CommitPackageImpl#getSimpleCommit()
	 * @generated
	 */
	int SIMPLE_COMMIT = 1;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_COMMIT__SIGNATURE = COMMIT__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Checksum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_COMMIT__CHECKSUM = COMMIT__CHECKSUM;

	/**
	 * The feature id for the '<em><b>Changes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_COMMIT__CHANGES = COMMIT__CHANGES;

	/**
	 * The feature id for the '<em><b>Commitmessage</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_COMMIT__COMMITMESSAGE = COMMIT__COMMITMESSAGE;

	/**
	 * The feature id for the '<em><b>Commits Branched From This</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_COMMIT__COMMITS_BRANCHED_FROM_THIS = COMMIT__COMMITS_BRANCHED_FROM_THIS;

	/**
	 * The feature id for the '<em><b>Commits Merged From This</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_COMMIT__COMMITS_MERGED_FROM_THIS = COMMIT__COMMITS_MERGED_FROM_THIS;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_COMMIT__IDENTIFIER = COMMIT__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_COMMIT__PARENT = COMMIT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Simple Commit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_COMMIT_FEATURE_COUNT = COMMIT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Simple Commit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_COMMIT_OPERATION_COUNT = COMMIT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.commit.impl.CommitMessageImpl <em>Message</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.commit.impl.CommitMessageImpl
	 * @see tools.vitruv.framework.versioning.commit.impl.CommitPackageImpl#getCommitMessage()
	 * @generated
	 */
	int COMMIT_MESSAGE = 3;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT_MESSAGE__DATE = 0;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT_MESSAGE__MESSAGE = 1;

	/**
	 * The feature id for the '<em><b>Author</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT_MESSAGE__AUTHOR = 2;

	/**
	 * The number of structural features of the '<em>Message</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT_MESSAGE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Message</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT_MESSAGE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.commit.impl.InitialCommitImpl <em>Initial Commit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.commit.impl.InitialCommitImpl
	 * @see tools.vitruv.framework.versioning.commit.impl.CommitPackageImpl#getInitialCommit()
	 * @generated
	 */
	int INITIAL_COMMIT = 4;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_COMMIT__SIGNATURE = COMMIT__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Checksum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_COMMIT__CHECKSUM = COMMIT__CHECKSUM;

	/**
	 * The feature id for the '<em><b>Changes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_COMMIT__CHANGES = COMMIT__CHANGES;

	/**
	 * The feature id for the '<em><b>Commitmessage</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_COMMIT__COMMITMESSAGE = COMMIT__COMMITMESSAGE;

	/**
	 * The feature id for the '<em><b>Commits Branched From This</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_COMMIT__COMMITS_BRANCHED_FROM_THIS = COMMIT__COMMITS_BRANCHED_FROM_THIS;

	/**
	 * The feature id for the '<em><b>Commits Merged From This</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_COMMIT__COMMITS_MERGED_FROM_THIS = COMMIT__COMMITS_MERGED_FROM_THIS;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_COMMIT__IDENTIFIER = COMMIT__IDENTIFIER;

	/**
	 * The number of structural features of the '<em>Initial Commit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_COMMIT_FEATURE_COUNT = COMMIT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Initial Commit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIAL_COMMIT_OPERATION_COUNT = COMMIT_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.commit.MergeCommit <em>Merge Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Merge Commit</em>'.
	 * @see tools.vitruv.framework.versioning.commit.MergeCommit
	 * @generated
	 */
	EClass getMergeCommit();

	/**
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.versioning.commit.MergeCommit#getCommitsMergedToThis <em>Commits Merged To This</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Commits Merged To This</em>'.
	 * @see tools.vitruv.framework.versioning.commit.MergeCommit#getCommitsMergedToThis()
	 * @see #getMergeCommit()
	 * @generated
	 */
	EReference getMergeCommit_CommitsMergedToThis();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.commit.SimpleCommit <em>Simple Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Commit</em>'.
	 * @see tools.vitruv.framework.versioning.commit.SimpleCommit
	 * @generated
	 */
	EClass getSimpleCommit();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.commit.SimpleCommit#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see tools.vitruv.framework.versioning.commit.SimpleCommit#getParent()
	 * @see #getSimpleCommit()
	 * @generated
	 */
	EReference getSimpleCommit_Parent();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.commit.Commit <em>Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Commit</em>'.
	 * @see tools.vitruv.framework.versioning.commit.Commit
	 * @generated
	 */
	EClass getCommit();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.versioning.commit.Commit#getChecksum <em>Checksum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Checksum</em>'.
	 * @see tools.vitruv.framework.versioning.commit.Commit#getChecksum()
	 * @see #getCommit()
	 * @generated
	 */
	EAttribute getCommit_Checksum();

	/**
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.versioning.commit.Commit#getChanges <em>Changes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Changes</em>'.
	 * @see tools.vitruv.framework.versioning.commit.Commit#getChanges()
	 * @see #getCommit()
	 * @generated
	 */
	EReference getCommit_Changes();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.commit.Commit#getCommitmessage <em>Commitmessage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Commitmessage</em>'.
	 * @see tools.vitruv.framework.versioning.commit.Commit#getCommitmessage()
	 * @see #getCommit()
	 * @generated
	 */
	EReference getCommit_Commitmessage();

	/**
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.versioning.commit.Commit#getCommitsBranchedFromThis <em>Commits Branched From This</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Commits Branched From This</em>'.
	 * @see tools.vitruv.framework.versioning.commit.Commit#getCommitsBranchedFromThis()
	 * @see #getCommit()
	 * @generated
	 */
	EReference getCommit_CommitsBranchedFromThis();

	/**
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.versioning.commit.Commit#getCommitsMergedFromThis <em>Commits Merged From This</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Commits Merged From This</em>'.
	 * @see tools.vitruv.framework.versioning.commit.Commit#getCommitsMergedFromThis()
	 * @see #getCommit()
	 * @generated
	 */
	EReference getCommit_CommitsMergedFromThis();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.versioning.commit.Commit#getIdentifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Identifier</em>'.
	 * @see tools.vitruv.framework.versioning.commit.Commit#getIdentifier()
	 * @see #getCommit()
	 * @generated
	 */
	EAttribute getCommit_Identifier();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.commit.CommitMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Message</em>'.
	 * @see tools.vitruv.framework.versioning.commit.CommitMessage
	 * @generated
	 */
	EClass getCommitMessage();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.versioning.commit.CommitMessage#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see tools.vitruv.framework.versioning.commit.CommitMessage#getDate()
	 * @see #getCommitMessage()
	 * @generated
	 */
	EAttribute getCommitMessage_Date();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.versioning.commit.CommitMessage#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see tools.vitruv.framework.versioning.commit.CommitMessage#getMessage()
	 * @see #getCommitMessage()
	 * @generated
	 */
	EAttribute getCommitMessage_Message();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.commit.CommitMessage#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Author</em>'.
	 * @see tools.vitruv.framework.versioning.commit.CommitMessage#getAuthor()
	 * @see #getCommitMessage()
	 * @generated
	 */
	EReference getCommitMessage_Author();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.commit.InitialCommit <em>Initial Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Initial Commit</em>'.
	 * @see tools.vitruv.framework.versioning.commit.InitialCommit
	 * @generated
	 */
	EClass getInitialCommit();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CommitFactory getCommitFactory();

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
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.commit.impl.MergeCommitImpl <em>Merge Commit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.commit.impl.MergeCommitImpl
		 * @see tools.vitruv.framework.versioning.commit.impl.CommitPackageImpl#getMergeCommit()
		 * @generated
		 */
		EClass MERGE_COMMIT = eINSTANCE.getMergeCommit();

		/**
		 * The meta object literal for the '<em><b>Commits Merged To This</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MERGE_COMMIT__COMMITS_MERGED_TO_THIS = eINSTANCE.getMergeCommit_CommitsMergedToThis();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.commit.impl.SimpleCommitImpl <em>Simple Commit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.commit.impl.SimpleCommitImpl
		 * @see tools.vitruv.framework.versioning.commit.impl.CommitPackageImpl#getSimpleCommit()
		 * @generated
		 */
		EClass SIMPLE_COMMIT = eINSTANCE.getSimpleCommit();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMPLE_COMMIT__PARENT = eINSTANCE.getSimpleCommit_Parent();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.commit.impl.CommitImpl <em>Commit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.commit.impl.CommitImpl
		 * @see tools.vitruv.framework.versioning.commit.impl.CommitPackageImpl#getCommit()
		 * @generated
		 */
		EClass COMMIT = eINSTANCE.getCommit();

		/**
		 * The meta object literal for the '<em><b>Checksum</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMIT__CHECKSUM = eINSTANCE.getCommit_Checksum();

		/**
		 * The meta object literal for the '<em><b>Changes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMIT__CHANGES = eINSTANCE.getCommit_Changes();

		/**
		 * The meta object literal for the '<em><b>Commitmessage</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMIT__COMMITMESSAGE = eINSTANCE.getCommit_Commitmessage();

		/**
		 * The meta object literal for the '<em><b>Commits Branched From This</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMIT__COMMITS_BRANCHED_FROM_THIS = eINSTANCE.getCommit_CommitsBranchedFromThis();

		/**
		 * The meta object literal for the '<em><b>Commits Merged From This</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMIT__COMMITS_MERGED_FROM_THIS = eINSTANCE.getCommit_CommitsMergedFromThis();

		/**
		 * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMIT__IDENTIFIER = eINSTANCE.getCommit_Identifier();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.commit.impl.CommitMessageImpl <em>Message</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.commit.impl.CommitMessageImpl
		 * @see tools.vitruv.framework.versioning.commit.impl.CommitPackageImpl#getCommitMessage()
		 * @generated
		 */
		EClass COMMIT_MESSAGE = eINSTANCE.getCommitMessage();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMIT_MESSAGE__DATE = eINSTANCE.getCommitMessage_Date();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMIT_MESSAGE__MESSAGE = eINSTANCE.getCommitMessage_Message();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMIT_MESSAGE__AUTHOR = eINSTANCE.getCommitMessage_Author();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.commit.impl.InitialCommitImpl <em>Initial Commit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.commit.impl.InitialCommitImpl
		 * @see tools.vitruv.framework.versioning.commit.impl.CommitPackageImpl#getInitialCommit()
		 * @generated
		 */
		EClass INITIAL_COMMIT = eINSTANCE.getInitialCommit();

	}

} //CommitPackage
