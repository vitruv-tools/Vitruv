/**
 */
package model;

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
 * @see model.ModelFactory
 * @model kind="package"
 * @generated
 */
public interface ModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model";

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
	ModelPackage eINSTANCE = model.impl.ModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link model.impl.NamedImpl <em>Named</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see model.impl.NamedImpl
	 * @see model.impl.ModelPackageImpl#getNamed()
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
	 * The meta object id for the '{@link model.impl.AuthorImpl <em>Author</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see model.impl.AuthorImpl
	 * @see model.impl.ModelPackageImpl#getAuthor()
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
	 * The number of structural features of the '<em>Author</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR_FEATURE_COUNT = NAMED_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Author</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR_OPERATION_COUNT = NAMED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link model.impl.TagImpl <em>Tag</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see model.impl.TagImpl
	 * @see model.impl.ModelPackageImpl#getTag()
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
	 * The meta object id for the '{@link model.impl.SignedImpl <em>Signed</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see model.impl.SignedImpl
	 * @see model.impl.ModelPackageImpl#getSigned()
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
	 * The meta object id for the '{@link model.impl.SignatureImpl <em>Signature</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see model.impl.SignatureImpl
	 * @see model.impl.ModelPackageImpl#getSignature()
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
	 * Returns the meta object for class '{@link model.Author <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Author</em>'.
	 * @see model.Author
	 * @generated
	 */
	EClass getAuthor();

	/**
	 * Returns the meta object for the attribute '{@link model.Author#getEmail <em>Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Email</em>'.
	 * @see model.Author#getEmail()
	 * @see #getAuthor()
	 * @generated
	 */
	EAttribute getAuthor_Email();

	/**
	 * Returns the meta object for the reference list '{@link model.Author#getOwnedBranches <em>Owned Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Owned Branches</em>'.
	 * @see model.Author#getOwnedBranches()
	 * @see #getAuthor()
	 * @generated
	 */
	EReference getAuthor_OwnedBranches();

	/**
	 * Returns the meta object for the reference list '{@link model.Author#getContributedBranches <em>Contributed Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Contributed Branches</em>'.
	 * @see model.Author#getContributedBranches()
	 * @see #getAuthor()
	 * @generated
	 */
	EReference getAuthor_ContributedBranches();

	/**
	 * Returns the meta object for class '{@link model.Named <em>Named</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named</em>'.
	 * @see model.Named
	 * @generated
	 */
	EClass getNamed();

	/**
	 * Returns the meta object for the attribute '{@link model.Named#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see model.Named#getName()
	 * @see #getNamed()
	 * @generated
	 */
	EAttribute getNamed_Name();

	/**
	 * Returns the meta object for class '{@link model.Tag <em>Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tag</em>'.
	 * @see model.Tag
	 * @generated
	 */
	EClass getTag();

	/**
	 * Returns the meta object for the reference '{@link model.Tag#getCommit <em>Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Commit</em>'.
	 * @see model.Tag#getCommit()
	 * @see #getTag()
	 * @generated
	 */
	EReference getTag_Commit();

	/**
	 * Returns the meta object for class '{@link model.Signed <em>Signed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Signed</em>'.
	 * @see model.Signed
	 * @generated
	 */
	EClass getSigned();

	/**
	 * Returns the meta object for the reference '{@link model.Signed#getSignature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Signature</em>'.
	 * @see model.Signed#getSignature()
	 * @see #getSigned()
	 * @generated
	 */
	EReference getSigned_Signature();

	/**
	 * Returns the meta object for class '{@link model.Signature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Signature</em>'.
	 * @see model.Signature
	 * @generated
	 */
	EClass getSignature();

	/**
	 * Returns the meta object for the reference '{@link model.Signature#getSigner <em>Signer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Signer</em>'.
	 * @see model.Signature#getSigner()
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
	ModelFactory getModelFactory();

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
		 * The meta object literal for the '{@link model.impl.AuthorImpl <em>Author</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see model.impl.AuthorImpl
		 * @see model.impl.ModelPackageImpl#getAuthor()
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
		 * The meta object literal for the '{@link model.impl.NamedImpl <em>Named</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see model.impl.NamedImpl
		 * @see model.impl.ModelPackageImpl#getNamed()
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
		 * The meta object literal for the '{@link model.impl.TagImpl <em>Tag</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see model.impl.TagImpl
		 * @see model.impl.ModelPackageImpl#getTag()
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
		 * The meta object literal for the '{@link model.impl.SignedImpl <em>Signed</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see model.impl.SignedImpl
		 * @see model.impl.ModelPackageImpl#getSigned()
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
		 * The meta object literal for the '{@link model.impl.SignatureImpl <em>Signature</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see model.impl.SignatureImpl
		 * @see model.impl.ModelPackageImpl#getSignature()
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

} //ModelPackage
