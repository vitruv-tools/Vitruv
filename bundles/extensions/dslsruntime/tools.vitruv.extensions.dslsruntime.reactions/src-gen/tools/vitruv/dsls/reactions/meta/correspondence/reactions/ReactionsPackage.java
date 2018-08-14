/**
 */
package tools.vitruv.dsls.reactions.meta.correspondence.reactions;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import tools.vitruv.framework.correspondence.CorrespondencePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see tools.vitruv.dsls.reactions.meta.correspondence.reactions.ReactionsFactory
 * @model kind="package"
 * @generated
 */
public interface ReactionsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "reactions";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv/Correspondence/Reactions/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "reactions";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ReactionsPackage eINSTANCE = tools.vitruv.dsls.reactions.meta.correspondence.reactions.impl.ReactionsPackageImpl.init();

	/**
	 * The meta object id for the '{@link tools.vitruv.dsls.reactions.meta.correspondence.reactions.impl.ReactionsCorrespondenceImpl <em>Correspondence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.dsls.reactions.meta.correspondence.reactions.impl.ReactionsCorrespondenceImpl
	 * @see tools.vitruv.dsls.reactions.meta.correspondence.reactions.impl.ReactionsPackageImpl#getReactionsCorrespondence()
	 * @generated
	 */
	int REACTIONS_CORRESPONDENCE = 0;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REACTIONS_CORRESPONDENCE__PARENT = CorrespondencePackage.CORRESPONDENCE__PARENT;

	/**
	 * The feature id for the '<em><b>Depends On</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REACTIONS_CORRESPONDENCE__DEPENDS_ON = CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON;

	/**
	 * The feature id for the '<em><b>Depended On By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REACTIONS_CORRESPONDENCE__DEPENDED_ON_BY = CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY;

	/**
	 * The feature id for the '<em><b>ATuids</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REACTIONS_CORRESPONDENCE__ATUIDS = CorrespondencePackage.CORRESPONDENCE__ATUIDS;

	/**
	 * The feature id for the '<em><b>BTuids</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REACTIONS_CORRESPONDENCE__BTUIDS = CorrespondencePackage.CORRESPONDENCE__BTUIDS;

	/**
	 * The feature id for the '<em><b>Tag</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REACTIONS_CORRESPONDENCE__TAG = CorrespondencePackage.CORRESPONDENCE__TAG;

	/**
	 * The number of structural features of the '<em>Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REACTIONS_CORRESPONDENCE_FEATURE_COUNT = CorrespondencePackage.CORRESPONDENCE_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link tools.vitruv.dsls.reactions.meta.correspondence.reactions.ReactionsCorrespondence <em>Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Correspondence</em>'.
	 * @see tools.vitruv.dsls.reactions.meta.correspondence.reactions.ReactionsCorrespondence
	 * @generated
	 */
	EClass getReactionsCorrespondence();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ReactionsFactory getReactionsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link tools.vitruv.dsls.reactions.meta.correspondence.reactions.impl.ReactionsCorrespondenceImpl <em>Correspondence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.dsls.reactions.meta.correspondence.reactions.impl.ReactionsCorrespondenceImpl
		 * @see tools.vitruv.dsls.reactions.meta.correspondence.reactions.impl.ReactionsPackageImpl#getReactionsCorrespondence()
		 * @generated
		 */
		EClass REACTIONS_CORRESPONDENCE = eINSTANCE.getReactionsCorrespondence();

	}

} //ReactionsPackage
