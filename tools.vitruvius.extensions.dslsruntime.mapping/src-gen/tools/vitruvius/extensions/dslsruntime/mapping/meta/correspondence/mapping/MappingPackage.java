/**
 */
package tools.vitruvius.extensions.dslsruntime.mapping.meta.correspondence.mapping;

import tools.vitruvius.framework.correspondence.CorrespondencePackage;

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
 * @see tools.vitruvius.extensions.dslsruntime.mapping.meta.correspondence.mapping.MappingFactory
 * @model kind="package"
 * @generated
 */
public interface MappingPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "mapping";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tools.vitruvius/Correspondence/Mapping/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "mapping";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MappingPackage eINSTANCE = tools.vitruvius.extensions.dslsruntime.mapping.meta.correspondence.mapping.impl.MappingPackageImpl.init();

	/**
	 * The meta object id for the '{@link tools.vitruvius.extensions.dslsruntime.mapping.meta.correspondence.mapping.impl.MappingCorrespondenceImpl <em>Correspondence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruvius.extensions.dslsruntime.mapping.meta.correspondence.mapping.impl.MappingCorrespondenceImpl
	 * @see tools.vitruvius.extensions.dslsruntime.mapping.meta.correspondence.mapping.impl.MappingPackageImpl#getMappingCorrespondence()
	 * @generated
	 */
	int MAPPING_CORRESPONDENCE = 0;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_CORRESPONDENCE__PARENT = CorrespondencePackage.CORRESPONDENCE__PARENT;

	/**
	 * The feature id for the '<em><b>Depends On</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_CORRESPONDENCE__DEPENDS_ON = CorrespondencePackage.CORRESPONDENCE__DEPENDS_ON;

	/**
	 * The feature id for the '<em><b>Depended On By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_CORRESPONDENCE__DEPENDED_ON_BY = CorrespondencePackage.CORRESPONDENCE__DEPENDED_ON_BY;

	/**
	 * The feature id for the '<em><b>ATUI Ds</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_CORRESPONDENCE__ATUI_DS = CorrespondencePackage.CORRESPONDENCE__ATUI_DS;

	/**
	 * The feature id for the '<em><b>BTUI Ds</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_CORRESPONDENCE__BTUI_DS = CorrespondencePackage.CORRESPONDENCE__BTUI_DS;

	/**
	 * The number of structural features of the '<em>Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_CORRESPONDENCE_FEATURE_COUNT = CorrespondencePackage.CORRESPONDENCE_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link tools.vitruvius.extensions.dslsruntime.mapping.meta.correspondence.mapping.MappingCorrespondence <em>Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Correspondence</em>'.
	 * @see tools.vitruvius.extensions.dslsruntime.mapping.meta.correspondence.mapping.MappingCorrespondence
	 * @generated
	 */
	EClass getMappingCorrespondence();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MappingFactory getMappingFactory();

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
		 * The meta object literal for the '{@link tools.vitruvius.extensions.dslsruntime.mapping.meta.correspondence.mapping.impl.MappingCorrespondenceImpl <em>Correspondence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruvius.extensions.dslsruntime.mapping.meta.correspondence.mapping.impl.MappingCorrespondenceImpl
		 * @see tools.vitruvius.extensions.dslsruntime.mapping.meta.correspondence.mapping.impl.MappingPackageImpl#getMappingCorrespondence()
		 * @generated
		 */
		EClass MAPPING_CORRESPONDENCE = eINSTANCE.getMappingCorrespondence();

	}

} //MappingPackage
