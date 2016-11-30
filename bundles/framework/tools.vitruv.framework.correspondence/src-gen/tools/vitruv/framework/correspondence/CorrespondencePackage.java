/**
 */
package tools.vitruv.framework.correspondence;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see tools.vitruv.framework.correspondence.CorrespondenceFactory
 * @model kind="package"
 * @generated
 */
public interface CorrespondencePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "correspondence";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruv/Correspondence/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "correspondence";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CorrespondencePackage eINSTANCE = tools.vitruv.framework.correspondence.impl.CorrespondencePackageImpl.init();

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.correspondence.impl.CorrespondencesImpl <em>Correspondences</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.correspondence.impl.CorrespondencesImpl
	 * @see tools.vitruv.framework.correspondence.impl.CorrespondencePackageImpl#getCorrespondences()
	 * @generated
	 */
	int CORRESPONDENCES = 0;

	/**
	 * The feature id for the '<em><b>Correspondences</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCES__CORRESPONDENCES = 0;

	/**
	 * The feature id for the '<em><b>Correspondence Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCES__CORRESPONDENCE_MODEL = 1;

	/**
	 * The number of structural features of the '<em>Correspondences</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCES_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.correspondence.impl.CorrespondenceImpl <em>Correspondence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.correspondence.impl.CorrespondenceImpl
	 * @see tools.vitruv.framework.correspondence.impl.CorrespondencePackageImpl#getCorrespondence()
	 * @generated
	 */
	int CORRESPONDENCE = 1;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE__PARENT = 0;

	/**
	 * The feature id for the '<em><b>Depends On</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE__DEPENDS_ON = 1;

	/**
	 * The feature id for the '<em><b>Depended On By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE__DEPENDED_ON_BY = 2;

	/**
	 * The feature id for the '<em><b>ATUI Ds</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE__ATUI_DS = 3;

	/**
	 * The feature id for the '<em><b>BTUI Ds</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE__BTUI_DS = 4;

	/**
	 * The number of structural features of the '<em>Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link tools.vitruv.framework.correspondence.impl.ManualCorrespondenceImpl <em>Manual Correspondence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.correspondence.impl.ManualCorrespondenceImpl
	 * @see tools.vitruv.framework.correspondence.impl.CorrespondencePackageImpl#getManualCorrespondence()
	 * @generated
	 */
	int MANUAL_CORRESPONDENCE = 2;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_CORRESPONDENCE__PARENT = CORRESPONDENCE__PARENT;

	/**
	 * The feature id for the '<em><b>Depends On</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_CORRESPONDENCE__DEPENDS_ON = CORRESPONDENCE__DEPENDS_ON;

	/**
	 * The feature id for the '<em><b>Depended On By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_CORRESPONDENCE__DEPENDED_ON_BY = CORRESPONDENCE__DEPENDED_ON_BY;

	/**
	 * The feature id for the '<em><b>ATUI Ds</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_CORRESPONDENCE__ATUI_DS = CORRESPONDENCE__ATUI_DS;

	/**
	 * The feature id for the '<em><b>BTUI Ds</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_CORRESPONDENCE__BTUI_DS = CORRESPONDENCE__BTUI_DS;

	/**
	 * The number of structural features of the '<em>Manual Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_CORRESPONDENCE_FEATURE_COUNT = CORRESPONDENCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '<em>Model</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.correspondence.CorrespondenceModel
	 * @see tools.vitruv.framework.correspondence.impl.CorrespondencePackageImpl#getCorrespondenceModel()
	 * @generated
	 */
	int CORRESPONDENCE_MODEL = 3;

	/**
	 * The meta object id for the '<em>TUID</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.tuid.TUID
	 * @see tools.vitruv.framework.correspondence.impl.CorrespondencePackageImpl#getTUID()
	 * @generated
	 */
	int TUID = 4;


	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.correspondence.Correspondences <em>Correspondences</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Correspondences</em>'.
	 * @see tools.vitruv.framework.correspondence.Correspondences
	 * @generated
	 */
	EClass getCorrespondences();

	/**
	 * Returns the meta object for the containment reference list '{@link tools.vitruv.framework.correspondence.Correspondences#getCorrespondences <em>Correspondences</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Correspondences</em>'.
	 * @see tools.vitruv.framework.correspondence.Correspondences#getCorrespondences()
	 * @see #getCorrespondences()
	 * @generated
	 */
	EReference getCorrespondences_Correspondences();

	/**
	 * Returns the meta object for the attribute '{@link tools.vitruv.framework.correspondence.Correspondences#getCorrespondenceModel <em>Correspondence Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Correspondence Model</em>'.
	 * @see tools.vitruv.framework.correspondence.Correspondences#getCorrespondenceModel()
	 * @see #getCorrespondences()
	 * @generated
	 */
	EAttribute getCorrespondences_CorrespondenceModel();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.correspondence.Correspondence <em>Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Correspondence</em>'.
	 * @see tools.vitruv.framework.correspondence.Correspondence
	 * @generated
	 */
	EClass getCorrespondence();

	/**
	 * Returns the meta object for the container reference '{@link tools.vitruv.framework.correspondence.Correspondence#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent</em>'.
	 * @see tools.vitruv.framework.correspondence.Correspondence#getParent()
	 * @see #getCorrespondence()
	 * @generated
	 */
	EReference getCorrespondence_Parent();

	/**
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.correspondence.Correspondence#getDependsOn <em>Depends On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Depends On</em>'.
	 * @see tools.vitruv.framework.correspondence.Correspondence#getDependsOn()
	 * @see #getCorrespondence()
	 * @generated
	 */
	EReference getCorrespondence_DependsOn();

	/**
	 * Returns the meta object for the reference list '{@link tools.vitruv.framework.correspondence.Correspondence#getDependedOnBy <em>Depended On By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Depended On By</em>'.
	 * @see tools.vitruv.framework.correspondence.Correspondence#getDependedOnBy()
	 * @see #getCorrespondence()
	 * @generated
	 */
	EReference getCorrespondence_DependedOnBy();

	/**
	 * Returns the meta object for the attribute list '{@link tools.vitruv.framework.correspondence.Correspondence#getATUIDs <em>ATUI Ds</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>ATUI Ds</em>'.
	 * @see tools.vitruv.framework.correspondence.Correspondence#getATUIDs()
	 * @see #getCorrespondence()
	 * @generated
	 */
	EAttribute getCorrespondence_ATUIDs();

	/**
	 * Returns the meta object for the attribute list '{@link tools.vitruv.framework.correspondence.Correspondence#getBTUIDs <em>BTUI Ds</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>BTUI Ds</em>'.
	 * @see tools.vitruv.framework.correspondence.Correspondence#getBTUIDs()
	 * @see #getCorrespondence()
	 * @generated
	 */
	EAttribute getCorrespondence_BTUIDs();

	/**
	 * Returns the meta object for class '{@link tools.vitruv.framework.correspondence.ManualCorrespondence <em>Manual Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Manual Correspondence</em>'.
	 * @see tools.vitruv.framework.correspondence.ManualCorrespondence
	 * @generated
	 */
	EClass getManualCorrespondence();

	/**
	 * Returns the meta object for data type '{@link tools.vitruv.framework.correspondence.CorrespondenceModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Model</em>'.
	 * @see tools.vitruv.framework.correspondence.CorrespondenceModel
	 * @model instanceClass="tools.vitruv.framework.correspondence.CorrespondenceModel" serializeable="false"
	 * @generated
	 */
	EDataType getCorrespondenceModel();

	/**
	 * Returns the meta object for data type '{@link tools.vitruv.framework.tuid.TUID <em>TUID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>TUID</em>'.
	 * @see tools.vitruv.framework.tuid.TUID
	 * @model instanceClass="tools.vitruv.framework.tuid.TUID"
	 * @generated
	 */
	EDataType getTUID();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CorrespondenceFactory getCorrespondenceFactory();

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
		 * The meta object literal for the '{@link tools.vitruv.framework.correspondence.impl.CorrespondencesImpl <em>Correspondences</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.correspondence.impl.CorrespondencesImpl
		 * @see tools.vitruv.framework.correspondence.impl.CorrespondencePackageImpl#getCorrespondences()
		 * @generated
		 */
		EClass CORRESPONDENCES = eINSTANCE.getCorrespondences();

		/**
		 * The meta object literal for the '<em><b>Correspondences</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORRESPONDENCES__CORRESPONDENCES = eINSTANCE.getCorrespondences_Correspondences();

		/**
		 * The meta object literal for the '<em><b>Correspondence Model</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CORRESPONDENCES__CORRESPONDENCE_MODEL = eINSTANCE.getCorrespondences_CorrespondenceModel();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.correspondence.impl.CorrespondenceImpl <em>Correspondence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.correspondence.impl.CorrespondenceImpl
		 * @see tools.vitruv.framework.correspondence.impl.CorrespondencePackageImpl#getCorrespondence()
		 * @generated
		 */
		EClass CORRESPONDENCE = eINSTANCE.getCorrespondence();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORRESPONDENCE__PARENT = eINSTANCE.getCorrespondence_Parent();

		/**
		 * The meta object literal for the '<em><b>Depends On</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORRESPONDENCE__DEPENDS_ON = eINSTANCE.getCorrespondence_DependsOn();

		/**
		 * The meta object literal for the '<em><b>Depended On By</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORRESPONDENCE__DEPENDED_ON_BY = eINSTANCE.getCorrespondence_DependedOnBy();

		/**
		 * The meta object literal for the '<em><b>ATUI Ds</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CORRESPONDENCE__ATUI_DS = eINSTANCE.getCorrespondence_ATUIDs();

		/**
		 * The meta object literal for the '<em><b>BTUI Ds</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CORRESPONDENCE__BTUI_DS = eINSTANCE.getCorrespondence_BTUIDs();

		/**
		 * The meta object literal for the '{@link tools.vitruv.framework.correspondence.impl.ManualCorrespondenceImpl <em>Manual Correspondence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.correspondence.impl.ManualCorrespondenceImpl
		 * @see tools.vitruv.framework.correspondence.impl.CorrespondencePackageImpl#getManualCorrespondence()
		 * @generated
		 */
		EClass MANUAL_CORRESPONDENCE = eINSTANCE.getManualCorrespondence();

		/**
		 * The meta object literal for the '<em>Model</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.correspondence.CorrespondenceModel
		 * @see tools.vitruv.framework.correspondence.impl.CorrespondencePackageImpl#getCorrespondenceModel()
		 * @generated
		 */
		EDataType CORRESPONDENCE_MODEL = eINSTANCE.getCorrespondenceModel();

		/**
		 * The meta object literal for the '<em>TUID</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruv.framework.tuid.TUID
		 * @see tools.vitruv.framework.correspondence.impl.CorrespondencePackageImpl#getTUID()
		 * @generated
		 */
		EDataType TUID = eINSTANCE.getTUID();

	}

} //CorrespondencePackage
