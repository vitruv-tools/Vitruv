/**
 */
package tools.vitruv.framework.versioning;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see tools.vitruv.framework.versioning.VersioningFactory
 * @model kind="package"
 * @generated
 */
public interface VersioningPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "versioning";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv/versioning/1.0";

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
	VersioningPackage eINSTANCE = tools.vitruv.framework.versioning.impl.VersioningPackageImpl.init();

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.impl.NamedImpl <em>Named</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.impl.NamedImpl
	 * @see tools.vitruv.framework.versioning.impl.VersioningPackageImpl#getNamed()
	 * @generated
	 */
	int NAMED = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED__NAME = 0;

	/**
	 * The number of structural features of the '<em>Named</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Named</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.impl.AuthorImpl <em>Author</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.impl.AuthorImpl
	 * @see tools.vitruv.framework.versioning.impl.VersioningPackageImpl#getAuthor()
	 * @generated
	 */
	int AUTHOR = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR__NAME = NAMED__NAME;

	/**
	 * The feature id for the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR__EMAIL = NAMED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Owned Branches</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR__OWNED_BRANCHES = NAMED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Contributed Branches</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR__CONTRIBUTED_BRANCHES = NAMED_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Commits</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR__COMMITS = NAMED_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Author</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR_FEATURE_COUNT = NAMED_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Author</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR_OPERATION_COUNT = NAMED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.impl.TagImpl <em>Tag</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.impl.TagImpl
	 * @see tools.vitruv.framework.versioning.impl.VersioningPackageImpl#getTag()
	 * @generated
	 */
	int TAG = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__NAME = NAMED__NAME;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__SIGNATURE = NAMED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Commit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__COMMIT = NAMED_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_FEATURE_COUNT = NAMED_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_OPERATION_COUNT = NAMED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.impl.SignedImpl <em>Signed</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.impl.SignedImpl
	 * @see tools.vitruv.framework.versioning.impl.VersioningPackageImpl#getSigned()
	 * @generated
	 */
	int SIGNED = 3;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNED__SIGNATURE = 0;

	/**
	 * The number of structural features of the '<em>Signed</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNED_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Signed</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNED_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.versioning.impl.SignatureImpl <em>Signature</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.impl.SignatureImpl
	 * @see tools.vitruv.framework.versioning.impl.VersioningPackageImpl#getSignature()
	 * @generated
	 */
	int SIGNATURE = 4;

	/**
	 * The feature id for the '<em><b>Signer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE__SIGNER = 0;

	/**
	 * The number of structural features of the '<em>Signature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Signature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.Author <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Author</em>'.
	 * @see tools.vitruv.framework.versioning.Author
	 * @generated
	 */
	EClass getAuthor();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.versioning.Author#getEmail <em>Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Email</em>'.
	 * @see tools.vitruv.framework.versioning.Author#getEmail()
	 * @see #getAuthor()
	 * @generated
	 */
	EAttribute getAuthor_Email();

	/**
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.versioning.Author#getOwnedBranches <em>Owned Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Owned Branches</em>'.
	 * @see tools.vitruv.framework.versioning.Author#getOwnedBranches()
	 * @see #getAuthor()
	 * @generated
	 */
	EReference getAuthor_OwnedBranches();

	/**
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.versioning.Author#getContributedBranches <em>Contributed Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Contributed Branches</em>'.
	 * @see tools.vitruv.framework.versioning.Author#getContributedBranches()
	 * @see #getAuthor()
	 * @generated
	 */
	EReference getAuthor_ContributedBranches();

	/**
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.versioning.Author#getCommits <em>Commits</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Commits</em>'.
	 * @see tools.vitruv.framework.versioning.Author#getCommits()
	 * @see #getAuthor()
	 * @generated
	 */
	EReference getAuthor_Commits();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.Named <em>Named</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named</em>'.
	 * @see tools.vitruv.framework.versioning.Named
	 * @generated
	 */
	EClass getNamed();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.versioning.Named#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see tools.vitruv.framework.versioning.Named#getName()
	 * @see #getNamed()
	 * @generated
	 */
	EAttribute getNamed_Name();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.Tag <em>Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tag</em>'.
	 * @see tools.vitruv.framework.versioning.Tag
	 * @generated
	 */
	EClass getTag();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.Tag#getCommit <em>Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Commit</em>'.
	 * @see tools.vitruv.framework.versioning.Tag#getCommit()
	 * @see #getTag()
	 * @generated
	 */
	EReference getTag_Commit();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.Signed <em>Signed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Signed</em>'.
	 * @see tools.vitruv.framework.versioning.Signed
	 * @generated
	 */
	EClass getSigned();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.Signed#getSignature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Signature</em>'.
	 * @see tools.vitruv.framework.versioning.Signed#getSignature()
	 * @see #getSigned()
	 * @generated
	 */
	EReference getSigned_Signature();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.versioning.Signature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Signature</em>'.
	 * @see tools.vitruv.framework.versioning.Signature
	 * @generated
	 */
	EClass getSignature();

	/**
	 * Returns the meta object for the reference '{@link tools.vitruv.framework.versioning.Signature#getSigner <em>Signer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Signer</em>'.
	 * @see tools.vitruv.framework.versioning.Signature#getSigner()
	 * @see #getSignature()
	 * @generated
	 */
	EReference getSignature_Signer();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VersioningFactory getVersioningFactory();

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
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.impl.AuthorImpl <em>Author</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.impl.AuthorImpl
		 * @see tools.vitruv.framework.versioning.impl.VersioningPackageImpl#getAuthor()
		 * @generated
		 */
		EClass AUTHOR = eINSTANCE.getAuthor();

		/**
		 * The meta object literal for the '<em><b>Email</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHOR__EMAIL = eINSTANCE.getAuthor_Email();

		/**
		 * The meta object literal for the '<em><b>Owned Branches</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHOR__OWNED_BRANCHES = eINSTANCE.getAuthor_OwnedBranches();

		/**
		 * The meta object literal for the '<em><b>Contributed Branches</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHOR__CONTRIBUTED_BRANCHES = eINSTANCE.getAuthor_ContributedBranches();

		/**
		 * The meta object literal for the '<em><b>Commits</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTHOR__COMMITS = eINSTANCE.getAuthor_Commits();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.impl.NamedImpl <em>Named</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.impl.NamedImpl
		 * @see tools.vitruv.framework.versioning.impl.VersioningPackageImpl#getNamed()
		 * @generated
		 */
		EClass NAMED = eINSTANCE.getNamed();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED__NAME = eINSTANCE.getNamed_Name();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.impl.TagImpl <em>Tag</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.impl.TagImpl
		 * @see tools.vitruv.framework.versioning.impl.VersioningPackageImpl#getTag()
		 * @generated
		 */
		EClass TAG = eINSTANCE.getTag();

		/**
		 * The meta object literal for the '<em><b>Commit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TAG__COMMIT = eINSTANCE.getTag_Commit();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.impl.SignedImpl <em>Signed</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.impl.SignedImpl
		 * @see tools.vitruv.framework.versioning.impl.VersioningPackageImpl#getSigned()
		 * @generated
		 */
		EClass SIGNED = eINSTANCE.getSigned();

		/**
		 * The meta object literal for the '<em><b>Signature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIGNED__SIGNATURE = eINSTANCE.getSigned_Signature();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.versioning.impl.SignatureImpl <em>Signature</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.versioning.impl.SignatureImpl
		 * @see tools.vitruv.framework.versioning.impl.VersioningPackageImpl#getSignature()
		 * @generated
		 */
		EClass SIGNATURE = eINSTANCE.getSignature();

		/**
		 * The meta object literal for the '<em><b>Signer</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIGNATURE__SIGNER = eINSTANCE.getSignature_Signer();

	}

} //VersioningPackage
