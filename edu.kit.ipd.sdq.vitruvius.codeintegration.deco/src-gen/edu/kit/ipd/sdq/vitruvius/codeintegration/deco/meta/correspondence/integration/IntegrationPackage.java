/**
 */
package edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.CorrespondencePackage;

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
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.IntegrationFactory
 * @model kind="package"
 * @generated
 */
public interface IntegrationPackage extends EPackage {
	/**
     * The package name.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	String eNAME = "integration";

	/**
     * The package namespace URI.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	String eNS_URI = "http://edu.kit.ipd.sdq.vitruvius/Correspondence/Integration/1.0";

	/**
     * The package namespace name.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	String eNS_PREFIX = "integration";

	/**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	IntegrationPackage eINSTANCE = edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.impl.IntegrationPackageImpl.init();

	/**
     * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.impl.IntegrationCorrespondenceImpl <em>Correspondence</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.impl.IntegrationCorrespondenceImpl
     * @see edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.impl.IntegrationPackageImpl#getIntegrationCorrespondence()
     * @generated
     */
	int INTEGRATION_CORRESPONDENCE = 0;

	/**
     * The feature id for the '<em><b>Parent</b></em>' container reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int INTEGRATION_CORRESPONDENCE__PARENT = CorrespondencePackage.CORRESPONDENCE__PARENT;

	/**
     * The feature id for the '<em><b>Depends On</b></em>' reference list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int INTEGRATION_CORRESPONDENCE__DEPENDS_ON = CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON;

	/**
     * The feature id for the '<em><b>Depended On By</b></em>' reference list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int INTEGRATION_CORRESPONDENCE__DEPENDED_ON_BY = CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY;

	/**
     * The feature id for the '<em><b>ATUI Ds</b></em>' attribute list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int INTEGRATION_CORRESPONDENCE__ATUI_DS = CorrespondencePackage.CORRESPONDENCE__ATUI_DS;

	/**
     * The feature id for the '<em><b>BTUI Ds</b></em>' attribute list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int INTEGRATION_CORRESPONDENCE__BTUI_DS = CorrespondencePackage.CORRESPONDENCE__BTUI_DS;

	/**
     * The feature id for the '<em><b>Created By Integration</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTEGRATION_CORRESPONDENCE__CREATED_BY_INTEGRATION = CorrespondencePackage.CORRESPONDENCE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Correspondence</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int INTEGRATION_CORRESPONDENCE_FEATURE_COUNT = CorrespondencePackage.CORRESPONDENCE_FEATURE_COUNT + 1;

	/**
     * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.IntegrationCorrespondence <em>Correspondence</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Correspondence</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.IntegrationCorrespondence
     * @generated
     */
	EClass getIntegrationCorrespondence();

	/**
     * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.IntegrationCorrespondence#isCreatedByIntegration <em>Created By Integration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Created By Integration</em>'.
     * @see edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.IntegrationCorrespondence#isCreatedByIntegration()
     * @see #getIntegrationCorrespondence()
     * @generated
     */
    EAttribute getIntegrationCorrespondence_CreatedByIntegration();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
	IntegrationFactory getIntegrationFactory();

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
         * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.impl.IntegrationCorrespondenceImpl <em>Correspondence</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.impl.IntegrationCorrespondenceImpl
         * @see edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.impl.IntegrationPackageImpl#getIntegrationCorrespondence()
         * @generated
         */
		EClass INTEGRATION_CORRESPONDENCE = eINSTANCE.getIntegrationCorrespondence();
        /**
         * The meta object literal for the '<em><b>Created By Integration</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INTEGRATION_CORRESPONDENCE__CREATED_BY_INTEGRATION = eINSTANCE.getIntegrationCorrespondence_CreatedByIntegration();

	}

} //IntegrationPackage
