/**
 */
package tools.vitruvius.dsls.response.meta.correspondence.response;

import tools.vitruvius.framework.correspondence.CorrespondencePackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

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
 * @see tools.vitruvius.dsls.response.meta.correspondence.response.ResponseFactory
 * @model kind="package"
 * @generated
 */
public interface ResponsePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "response";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruvius/Correspondence/Response/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "response";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ResponsePackage eINSTANCE = tools.vitruvius.dsls.response.meta.correspondence.response.impl.ResponsePackageImpl.init();

	/**
	 * The meta object id for the '{@link tools.vitruvius.dsls.response.meta.correspondence.response.impl.ResponseCorrespondenceImpl <em>Correspondence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruvius.dsls.response.meta.correspondence.response.impl.ResponseCorrespondenceImpl
	 * @see tools.vitruvius.dsls.response.meta.correspondence.response.impl.ResponsePackageImpl#getResponseCorrespondence()
	 * @generated
	 */
	int RESPONSE_CORRESPONDENCE = 0;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_CORRESPONDENCE__PARENT = CorrespondencePackage.CORRESPONDENCE__PARENT;

	/**
	 * The feature id for the '<em><b>Depends On</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_CORRESPONDENCE__DEPENDS_ON = CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON;

	/**
	 * The feature id for the '<em><b>Depended On By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_CORRESPONDENCE__DEPENDED_ON_BY = CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY;

	/**
	 * The feature id for the '<em><b>ATUI Ds</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_CORRESPONDENCE__ATUI_DS = CorrespondencePackage.CORRESPONDENCE__ATUI_DS;

	/**
	 * The feature id for the '<em><b>BTUI Ds</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_CORRESPONDENCE__BTUI_DS = CorrespondencePackage.CORRESPONDENCE__BTUI_DS;

	/**
	 * The feature id for the '<em><b>Tag</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_CORRESPONDENCE__TAG = CorrespondencePackage.CORRESPONDENCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_CORRESPONDENCE_FEATURE_COUNT = CorrespondencePackage.CORRESPONDENCE_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link tools.vitruvius.dsls.response.meta.correspondence.response.ResponseCorrespondence <em>Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Correspondence</em>'.
	 * @see tools.vitruvius.dsls.response.meta.correspondence.response.ResponseCorrespondence
	 * @generated
	 */
	EClass getResponseCorrespondence();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruvius.dsls.response.meta.correspondence.response.ResponseCorrespondence#getTag <em>Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tag</em>'.
	 * @see tools.vitruvius.dsls.response.meta.correspondence.response.ResponseCorrespondence#getTag()
	 * @see #getResponseCorrespondence()
	 * @generated
	 */
	EAttribute getResponseCorrespondence_Tag();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ResponseFactory getResponseFactory();

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
		 * The meta object literal for the '{@link tools.vitruvius.dsls.response.meta.correspondence.response.impl.ResponseCorrespondenceImpl <em>Correspondence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruvius.dsls.response.meta.correspondence.response.impl.ResponseCorrespondenceImpl
		 * @see tools.vitruvius.dsls.response.meta.correspondence.response.impl.ResponsePackageImpl#getResponseCorrespondence()
		 * @generated
		 */
		EClass RESPONSE_CORRESPONDENCE = eINSTANCE.getResponseCorrespondence();

		/**
		 * The meta object literal for the '<em><b>Tag</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESPONSE_CORRESPONDENCE__TAG = eINSTANCE.getResponseCorrespondence_Tag();

	}

} //ResponsePackage
