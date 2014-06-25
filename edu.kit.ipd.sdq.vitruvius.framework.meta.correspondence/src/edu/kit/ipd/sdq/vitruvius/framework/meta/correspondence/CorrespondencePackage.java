/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory
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
	String eNS_URI = "http://edu.kit.ipd.sdq.vitruvius/Correspondence/1.0";

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
	CorrespondencePackage eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencesImpl <em>Correspondences</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencesImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getCorrespondences()
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
	 * The number of structural features of the '<em>Correspondences</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCES_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondenceImpl <em>Correspondence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondenceImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getCorrespondence()
	 * @generated
	 */
	int CORRESPONDENCE = 1;

	/**
	 * The feature id for the '<em><b>Dependent Correspondences</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE__DEPENDENT_CORRESPONDENCES = 0;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE__PARENT = 1;

	/**
	 * The number of structural features of the '<em>Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.SameTypeCorrespondenceImpl <em>Same Type Correspondence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.SameTypeCorrespondenceImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getSameTypeCorrespondence()
	 * @generated
	 */
	int SAME_TYPE_CORRESPONDENCE = 2;

	/**
	 * The feature id for the '<em><b>Dependent Correspondences</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAME_TYPE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES = CORRESPONDENCE__DEPENDENT_CORRESPONDENCES;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAME_TYPE_CORRESPONDENCE__PARENT = CORRESPONDENCE__PARENT;

	/**
	 * The feature id for the '<em><b>Element A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAME_TYPE_CORRESPONDENCE__ELEMENT_A = CORRESPONDENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Element ATUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAME_TYPE_CORRESPONDENCE__ELEMENT_ATUID = CORRESPONDENCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Element B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAME_TYPE_CORRESPONDENCE__ELEMENT_B = CORRESPONDENCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Element BTUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAME_TYPE_CORRESPONDENCE__ELEMENT_BTUID = CORRESPONDENCE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Same Type Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAME_TYPE_CORRESPONDENCE_FEATURE_COUNT = CORRESPONDENCE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EObjectCorrespondenceImpl <em>EObject Correspondence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EObjectCorrespondenceImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getEObjectCorrespondence()
	 * @generated
	 */
	int EOBJECT_CORRESPONDENCE = 3;

	/**
	 * The feature id for the '<em><b>Dependent Correspondences</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES = SAME_TYPE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_CORRESPONDENCE__PARENT = SAME_TYPE_CORRESPONDENCE__PARENT;

	/**
	 * The feature id for the '<em><b>Element A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_CORRESPONDENCE__ELEMENT_A = SAME_TYPE_CORRESPONDENCE__ELEMENT_A;

	/**
	 * The feature id for the '<em><b>Element ATUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_CORRESPONDENCE__ELEMENT_ATUID = SAME_TYPE_CORRESPONDENCE__ELEMENT_ATUID;

	/**
	 * The feature id for the '<em><b>Element B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_CORRESPONDENCE__ELEMENT_B = SAME_TYPE_CORRESPONDENCE__ELEMENT_B;

	/**
	 * The feature id for the '<em><b>Element BTUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_CORRESPONDENCE__ELEMENT_BTUID = SAME_TYPE_CORRESPONDENCE__ELEMENT_BTUID;

	/**
	 * The number of structural features of the '<em>EObject Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_CORRESPONDENCE_FEATURE_COUNT = SAME_TYPE_CORRESPONDENCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EFeatureCorrespondenceImpl <em>EFeature Correspondence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EFeatureCorrespondenceImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getEFeatureCorrespondence()
	 * @generated
	 */
	int EFEATURE_CORRESPONDENCE = 4;

	/**
	 * The feature id for the '<em><b>Dependent Correspondences</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFEATURE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES = SAME_TYPE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFEATURE_CORRESPONDENCE__PARENT = SAME_TYPE_CORRESPONDENCE__PARENT;

	/**
	 * The feature id for the '<em><b>Element A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFEATURE_CORRESPONDENCE__ELEMENT_A = SAME_TYPE_CORRESPONDENCE__ELEMENT_A;

	/**
	 * The feature id for the '<em><b>Element ATUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFEATURE_CORRESPONDENCE__ELEMENT_ATUID = SAME_TYPE_CORRESPONDENCE__ELEMENT_ATUID;

	/**
	 * The feature id for the '<em><b>Element B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFEATURE_CORRESPONDENCE__ELEMENT_B = SAME_TYPE_CORRESPONDENCE__ELEMENT_B;

	/**
	 * The feature id for the '<em><b>Element BTUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFEATURE_CORRESPONDENCE__ELEMENT_BTUID = SAME_TYPE_CORRESPONDENCE__ELEMENT_BTUID;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFEATURE_CORRESPONDENCE__TYPE = SAME_TYPE_CORRESPONDENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Feature A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFEATURE_CORRESPONDENCE__FEATURE_A = SAME_TYPE_CORRESPONDENCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Feature B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFEATURE_CORRESPONDENCE__FEATURE_B = SAME_TYPE_CORRESPONDENCE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>EFeature Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFEATURE_CORRESPONDENCE_FEATURE_COUNT = SAME_TYPE_CORRESPONDENCE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EAttributeCorrespondenceImpl <em>EAttribute Correspondence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EAttributeCorrespondenceImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getEAttributeCorrespondence()
	 * @generated
	 */
	int EATTRIBUTE_CORRESPONDENCE = 5;

	/**
	 * The feature id for the '<em><b>Dependent Correspondences</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EATTRIBUTE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES = EFEATURE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EATTRIBUTE_CORRESPONDENCE__PARENT = EFEATURE_CORRESPONDENCE__PARENT;

	/**
	 * The feature id for the '<em><b>Element A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EATTRIBUTE_CORRESPONDENCE__ELEMENT_A = EFEATURE_CORRESPONDENCE__ELEMENT_A;

	/**
	 * The feature id for the '<em><b>Element ATUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EATTRIBUTE_CORRESPONDENCE__ELEMENT_ATUID = EFEATURE_CORRESPONDENCE__ELEMENT_ATUID;

	/**
	 * The feature id for the '<em><b>Element B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EATTRIBUTE_CORRESPONDENCE__ELEMENT_B = EFEATURE_CORRESPONDENCE__ELEMENT_B;

	/**
	 * The feature id for the '<em><b>Element BTUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EATTRIBUTE_CORRESPONDENCE__ELEMENT_BTUID = EFEATURE_CORRESPONDENCE__ELEMENT_BTUID;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EATTRIBUTE_CORRESPONDENCE__TYPE = EFEATURE_CORRESPONDENCE__TYPE;

	/**
	 * The feature id for the '<em><b>Feature A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EATTRIBUTE_CORRESPONDENCE__FEATURE_A = EFEATURE_CORRESPONDENCE__FEATURE_A;

	/**
	 * The feature id for the '<em><b>Feature B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EATTRIBUTE_CORRESPONDENCE__FEATURE_B = EFEATURE_CORRESPONDENCE__FEATURE_B;

	/**
	 * The number of structural features of the '<em>EAttribute Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EATTRIBUTE_CORRESPONDENCE_FEATURE_COUNT = EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EReferenceCorrespondenceImpl <em>EReference Correspondence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EReferenceCorrespondenceImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getEReferenceCorrespondence()
	 * @generated
	 */
	int EREFERENCE_CORRESPONDENCE = 6;

	/**
	 * The feature id for the '<em><b>Dependent Correspondences</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EREFERENCE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES = EFEATURE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EREFERENCE_CORRESPONDENCE__PARENT = EFEATURE_CORRESPONDENCE__PARENT;

	/**
	 * The feature id for the '<em><b>Element A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EREFERENCE_CORRESPONDENCE__ELEMENT_A = EFEATURE_CORRESPONDENCE__ELEMENT_A;

	/**
	 * The feature id for the '<em><b>Element ATUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EREFERENCE_CORRESPONDENCE__ELEMENT_ATUID = EFEATURE_CORRESPONDENCE__ELEMENT_ATUID;

	/**
	 * The feature id for the '<em><b>Element B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EREFERENCE_CORRESPONDENCE__ELEMENT_B = EFEATURE_CORRESPONDENCE__ELEMENT_B;

	/**
	 * The feature id for the '<em><b>Element BTUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EREFERENCE_CORRESPONDENCE__ELEMENT_BTUID = EFEATURE_CORRESPONDENCE__ELEMENT_BTUID;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EREFERENCE_CORRESPONDENCE__TYPE = EFEATURE_CORRESPONDENCE__TYPE;

	/**
	 * The feature id for the '<em><b>Feature A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EREFERENCE_CORRESPONDENCE__FEATURE_A = EFEATURE_CORRESPONDENCE__FEATURE_A;

	/**
	 * The feature id for the '<em><b>Feature B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EREFERENCE_CORRESPONDENCE__FEATURE_B = EFEATURE_CORRESPONDENCE__FEATURE_B;

	/**
	 * The number of structural features of the '<em>EReference Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EREFERENCE_CORRESPONDENCE_FEATURE_COUNT = EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EContainmentReferenceCorrespondenceImpl <em>EContainment Reference Correspondence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EContainmentReferenceCorrespondenceImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getEContainmentReferenceCorrespondence()
	 * @generated
	 */
	int ECONTAINMENT_REFERENCE_CORRESPONDENCE = 7;

	/**
	 * The feature id for the '<em><b>Dependent Correspondences</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECONTAINMENT_REFERENCE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES = EREFERENCE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECONTAINMENT_REFERENCE_CORRESPONDENCE__PARENT = EREFERENCE_CORRESPONDENCE__PARENT;

	/**
	 * The feature id for the '<em><b>Element A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECONTAINMENT_REFERENCE_CORRESPONDENCE__ELEMENT_A = EREFERENCE_CORRESPONDENCE__ELEMENT_A;

	/**
	 * The feature id for the '<em><b>Element ATUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECONTAINMENT_REFERENCE_CORRESPONDENCE__ELEMENT_ATUID = EREFERENCE_CORRESPONDENCE__ELEMENT_ATUID;

	/**
	 * The feature id for the '<em><b>Element B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECONTAINMENT_REFERENCE_CORRESPONDENCE__ELEMENT_B = EREFERENCE_CORRESPONDENCE__ELEMENT_B;

	/**
	 * The feature id for the '<em><b>Element BTUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECONTAINMENT_REFERENCE_CORRESPONDENCE__ELEMENT_BTUID = EREFERENCE_CORRESPONDENCE__ELEMENT_BTUID;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECONTAINMENT_REFERENCE_CORRESPONDENCE__TYPE = EREFERENCE_CORRESPONDENCE__TYPE;

	/**
	 * The feature id for the '<em><b>Feature A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECONTAINMENT_REFERENCE_CORRESPONDENCE__FEATURE_A = EREFERENCE_CORRESPONDENCE__FEATURE_A;

	/**
	 * The feature id for the '<em><b>Feature B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECONTAINMENT_REFERENCE_CORRESPONDENCE__FEATURE_B = EREFERENCE_CORRESPONDENCE__FEATURE_B;

	/**
	 * The number of structural features of the '<em>EContainment Reference Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECONTAINMENT_REFERENCE_CORRESPONDENCE_FEATURE_COUNT = EREFERENCE_CORRESPONDENCE_FEATURE_COUNT + 0;


	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEFeatureCorrespondenceImpl <em>Partial EFeature Correspondence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEFeatureCorrespondenceImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getPartialEFeatureCorrespondence()
	 * @generated
	 */
	int PARTIAL_EFEATURE_CORRESPONDENCE = 8;

	/**
	 * The number of structural features of the '<em>Partial EFeature Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEAttributeCorrespondenceImpl <em>Partial EAttribute Correspondence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEAttributeCorrespondenceImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getPartialEAttributeCorrespondence()
	 * @generated
	 */
	int PARTIAL_EATTRIBUTE_CORRESPONDENCE = 9;

	/**
	 * The feature id for the '<em><b>Dependent Correspondences</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EATTRIBUTE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EATTRIBUTE_CORRESPONDENCE__PARENT = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Element A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EATTRIBUTE_CORRESPONDENCE__ELEMENT_A = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Element ATUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EATTRIBUTE_CORRESPONDENCE__ELEMENT_ATUID = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Element B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EATTRIBUTE_CORRESPONDENCE__ELEMENT_B = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Element BTUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EATTRIBUTE_CORRESPONDENCE__ELEMENT_BTUID = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EATTRIBUTE_CORRESPONDENCE__TYPE = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Feature A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EATTRIBUTE_CORRESPONDENCE__FEATURE_A = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Feature B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EATTRIBUTE_CORRESPONDENCE__FEATURE_B = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Value A</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EATTRIBUTE_CORRESPONDENCE__VALUE_A = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Value B</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EATTRIBUTE_CORRESPONDENCE__VALUE_B = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Partial EAttribute Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EATTRIBUTE_CORRESPONDENCE_FEATURE_COUNT = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 11;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEReferenceCorrespondenceImpl <em>Partial EReference Correspondence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEReferenceCorrespondenceImpl
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getPartialEReferenceCorrespondence()
	 * @generated
	 */
	int PARTIAL_EREFERENCE_CORRESPONDENCE = 10;

	/**
	 * The feature id for the '<em><b>Dependent Correspondences</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EREFERENCE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EREFERENCE_CORRESPONDENCE__PARENT = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Element A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_A = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Element ATUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_ATUID = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Element B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_B = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Element BTUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EREFERENCE_CORRESPONDENCE__ELEMENT_BTUID = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EREFERENCE_CORRESPONDENCE__TYPE = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Feature A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EREFERENCE_CORRESPONDENCE__FEATURE_A = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Feature B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EREFERENCE_CORRESPONDENCE__FEATURE_B = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Value A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_A = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Value ATUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_ATUID = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Value B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_B = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Value BTUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_BTUID = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 12;

	/**
	 * The number of structural features of the '<em>Partial EReference Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTIAL_EREFERENCE_CORRESPONDENCE_FEATURE_COUNT = PARTIAL_EFEATURE_CORRESPONDENCE_FEATURE_COUNT + 13;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceType <em>Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceType
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getCorrespondenceType()
	 * @generated
	 */
	int CORRESPONDENCE_TYPE = 11;


	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences <em>Correspondences</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Correspondences</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences
	 * @generated
	 */
	EClass getCorrespondences();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences#getCorrespondences <em>Correspondences</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Correspondences</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences#getCorrespondences()
	 * @see #getCorrespondences()
	 * @generated
	 */
	EReference getCorrespondences_Correspondences();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence <em>Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Correspondence</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
	 * @generated
	 */
	EClass getCorrespondence();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getDependentCorrespondences <em>Dependent Correspondences</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dependent Correspondences</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getDependentCorrespondences()
	 * @see #getCorrespondence()
	 * @generated
	 */
	EReference getCorrespondence_DependentCorrespondences();

	/**
	 * Returns the meta object for the container reference '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getParent()
	 * @see #getCorrespondence()
	 * @generated
	 */
	EReference getCorrespondence_Parent();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence <em>Same Type Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Same Type Correspondence</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence
	 * @generated
	 */
	EClass getSameTypeCorrespondence();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence#getElementA <em>Element A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Element A</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence#getElementA()
	 * @see #getSameTypeCorrespondence()
	 * @generated
	 */
	EReference getSameTypeCorrespondence_ElementA();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence#getElementATUID <em>Element ATUID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Element ATUID</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence#getElementATUID()
	 * @see #getSameTypeCorrespondence()
	 * @generated
	 */
	EAttribute getSameTypeCorrespondence_ElementATUID();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence#getElementB <em>Element B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Element B</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence#getElementB()
	 * @see #getSameTypeCorrespondence()
	 * @generated
	 */
	EReference getSameTypeCorrespondence_ElementB();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence#getElementBTUID <em>Element BTUID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Element BTUID</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence#getElementBTUID()
	 * @see #getSameTypeCorrespondence()
	 * @generated
	 */
	EAttribute getSameTypeCorrespondence_ElementBTUID();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence <em>EObject Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EObject Correspondence</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence
	 * @generated
	 */
	EClass getEObjectCorrespondence();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence <em>EFeature Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EFeature Correspondence</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence
	 * @generated
	 */
	EClass getEFeatureCorrespondence();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence#getType()
	 * @see #getEFeatureCorrespondence()
	 * @generated
	 */
	EAttribute getEFeatureCorrespondence_Type();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence#getFeatureA <em>Feature A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Feature A</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence#getFeatureA()
	 * @see #getEFeatureCorrespondence()
	 * @generated
	 */
	EReference getEFeatureCorrespondence_FeatureA();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence#getFeatureB <em>Feature B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Feature B</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence#getFeatureB()
	 * @see #getEFeatureCorrespondence()
	 * @generated
	 */
	EReference getEFeatureCorrespondence_FeatureB();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EAttributeCorrespondence <em>EAttribute Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EAttribute Correspondence</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EAttributeCorrespondence
	 * @generated
	 */
	EClass getEAttributeCorrespondence();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EReferenceCorrespondence <em>EReference Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EReference Correspondence</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EReferenceCorrespondence
	 * @generated
	 */
	EClass getEReferenceCorrespondence();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EContainmentReferenceCorrespondence <em>EContainment Reference Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EContainment Reference Correspondence</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EContainmentReferenceCorrespondence
	 * @generated
	 */
	EClass getEContainmentReferenceCorrespondence();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEFeatureCorrespondence <em>Partial EFeature Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Partial EFeature Correspondence</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEFeatureCorrespondence
	 * @generated
	 */
	EClass getPartialEFeatureCorrespondence();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEAttributeCorrespondence <em>Partial EAttribute Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Partial EAttribute Correspondence</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEAttributeCorrespondence
	 * @generated
	 */
	EClass getPartialEAttributeCorrespondence();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEAttributeCorrespondence#getValueA <em>Value A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value A</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEAttributeCorrespondence#getValueA()
	 * @see #getPartialEAttributeCorrespondence()
	 * @generated
	 */
	EAttribute getPartialEAttributeCorrespondence_ValueA();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEAttributeCorrespondence#getValueB <em>Value B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value B</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEAttributeCorrespondence#getValueB()
	 * @see #getPartialEAttributeCorrespondence()
	 * @generated
	 */
	EAttribute getPartialEAttributeCorrespondence_ValueB();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence <em>Partial EReference Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Partial EReference Correspondence</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence
	 * @generated
	 */
	EClass getPartialEReferenceCorrespondence();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueA <em>Value A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value A</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueA()
	 * @see #getPartialEReferenceCorrespondence()
	 * @generated
	 */
	EReference getPartialEReferenceCorrespondence_ValueA();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueATUID <em>Value ATUID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value ATUID</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueATUID()
	 * @see #getPartialEReferenceCorrespondence()
	 * @generated
	 */
	EAttribute getPartialEReferenceCorrespondence_ValueATUID();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueB <em>Value B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value B</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueB()
	 * @see #getPartialEReferenceCorrespondence()
	 * @generated
	 */
	EReference getPartialEReferenceCorrespondence_ValueB();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueBTUID <em>Value BTUID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value BTUID</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueBTUID()
	 * @see #getPartialEReferenceCorrespondence()
	 * @generated
	 */
	EAttribute getPartialEReferenceCorrespondence_ValueBTUID();

	/**
	 * Returns the meta object for enum '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Type</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceType
	 * @generated
	 */
	EEnum getCorrespondenceType();

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
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencesImpl <em>Correspondences</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencesImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getCorrespondences()
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
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondenceImpl <em>Correspondence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondenceImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getCorrespondence()
		 * @generated
		 */
		EClass CORRESPONDENCE = eINSTANCE.getCorrespondence();

		/**
		 * The meta object literal for the '<em><b>Dependent Correspondences</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORRESPONDENCE__DEPENDENT_CORRESPONDENCES = eINSTANCE.getCorrespondence_DependentCorrespondences();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORRESPONDENCE__PARENT = eINSTANCE.getCorrespondence_Parent();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.SameTypeCorrespondenceImpl <em>Same Type Correspondence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.SameTypeCorrespondenceImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getSameTypeCorrespondence()
		 * @generated
		 */
		EClass SAME_TYPE_CORRESPONDENCE = eINSTANCE.getSameTypeCorrespondence();

		/**
		 * The meta object literal for the '<em><b>Element A</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SAME_TYPE_CORRESPONDENCE__ELEMENT_A = eINSTANCE.getSameTypeCorrespondence_ElementA();

		/**
		 * The meta object literal for the '<em><b>Element ATUID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SAME_TYPE_CORRESPONDENCE__ELEMENT_ATUID = eINSTANCE.getSameTypeCorrespondence_ElementATUID();

		/**
		 * The meta object literal for the '<em><b>Element B</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SAME_TYPE_CORRESPONDENCE__ELEMENT_B = eINSTANCE.getSameTypeCorrespondence_ElementB();

		/**
		 * The meta object literal for the '<em><b>Element BTUID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SAME_TYPE_CORRESPONDENCE__ELEMENT_BTUID = eINSTANCE.getSameTypeCorrespondence_ElementBTUID();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EObjectCorrespondenceImpl <em>EObject Correspondence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EObjectCorrespondenceImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getEObjectCorrespondence()
		 * @generated
		 */
		EClass EOBJECT_CORRESPONDENCE = eINSTANCE.getEObjectCorrespondence();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EFeatureCorrespondenceImpl <em>EFeature Correspondence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EFeatureCorrespondenceImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getEFeatureCorrespondence()
		 * @generated
		 */
		EClass EFEATURE_CORRESPONDENCE = eINSTANCE.getEFeatureCorrespondence();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EFEATURE_CORRESPONDENCE__TYPE = eINSTANCE.getEFeatureCorrespondence_Type();

		/**
		 * The meta object literal for the '<em><b>Feature A</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EFEATURE_CORRESPONDENCE__FEATURE_A = eINSTANCE.getEFeatureCorrespondence_FeatureA();

		/**
		 * The meta object literal for the '<em><b>Feature B</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EFEATURE_CORRESPONDENCE__FEATURE_B = eINSTANCE.getEFeatureCorrespondence_FeatureB();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EAttributeCorrespondenceImpl <em>EAttribute Correspondence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EAttributeCorrespondenceImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getEAttributeCorrespondence()
		 * @generated
		 */
		EClass EATTRIBUTE_CORRESPONDENCE = eINSTANCE.getEAttributeCorrespondence();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EReferenceCorrespondenceImpl <em>EReference Correspondence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EReferenceCorrespondenceImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getEReferenceCorrespondence()
		 * @generated
		 */
		EClass EREFERENCE_CORRESPONDENCE = eINSTANCE.getEReferenceCorrespondence();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EContainmentReferenceCorrespondenceImpl <em>EContainment Reference Correspondence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.EContainmentReferenceCorrespondenceImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getEContainmentReferenceCorrespondence()
		 * @generated
		 */
		EClass ECONTAINMENT_REFERENCE_CORRESPONDENCE = eINSTANCE.getEContainmentReferenceCorrespondence();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEFeatureCorrespondenceImpl <em>Partial EFeature Correspondence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEFeatureCorrespondenceImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getPartialEFeatureCorrespondence()
		 * @generated
		 */
		EClass PARTIAL_EFEATURE_CORRESPONDENCE = eINSTANCE.getPartialEFeatureCorrespondence();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEAttributeCorrespondenceImpl <em>Partial EAttribute Correspondence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEAttributeCorrespondenceImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getPartialEAttributeCorrespondence()
		 * @generated
		 */
		EClass PARTIAL_EATTRIBUTE_CORRESPONDENCE = eINSTANCE.getPartialEAttributeCorrespondence();

		/**
		 * The meta object literal for the '<em><b>Value A</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARTIAL_EATTRIBUTE_CORRESPONDENCE__VALUE_A = eINSTANCE.getPartialEAttributeCorrespondence_ValueA();

		/**
		 * The meta object literal for the '<em><b>Value B</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARTIAL_EATTRIBUTE_CORRESPONDENCE__VALUE_B = eINSTANCE.getPartialEAttributeCorrespondence_ValueB();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEReferenceCorrespondenceImpl <em>Partial EReference Correspondence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.PartialEReferenceCorrespondenceImpl
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getPartialEReferenceCorrespondence()
		 * @generated
		 */
		EClass PARTIAL_EREFERENCE_CORRESPONDENCE = eINSTANCE.getPartialEReferenceCorrespondence();

		/**
		 * The meta object literal for the '<em><b>Value A</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_A = eINSTANCE.getPartialEReferenceCorrespondence_ValueA();

		/**
		 * The meta object literal for the '<em><b>Value ATUID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_ATUID = eINSTANCE.getPartialEReferenceCorrespondence_ValueATUID();

		/**
		 * The meta object literal for the '<em><b>Value B</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_B = eINSTANCE.getPartialEReferenceCorrespondence_ValueB();

		/**
		 * The meta object literal for the '<em><b>Value BTUID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARTIAL_EREFERENCE_CORRESPONDENCE__VALUE_BTUID = eINSTANCE.getPartialEReferenceCorrespondence_ValueBTUID();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceType <em>Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceType
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getCorrespondenceType()
		 * @generated
		 */
		EEnum CORRESPONDENCE_TYPE = eINSTANCE.getCorrespondenceType();

	}

} //CorrespondencePackage
