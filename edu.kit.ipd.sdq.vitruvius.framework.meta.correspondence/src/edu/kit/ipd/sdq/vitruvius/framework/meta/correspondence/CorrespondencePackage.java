/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence;

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
	 * The feature id for the '<em><b>Correspondences</b></em>' reference list.
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
	 * The feature id for the '<em><b>Dependent Correspondences</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE__DEPENDENT_CORRESPONDENCES = 0;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
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
	 * The feature id for the '<em><b>Dependent Correspondences</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAME_TYPE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES = CORRESPONDENCE__DEPENDENT_CORRESPONDENCES;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
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
	 * The feature id for the '<em><b>Element B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAME_TYPE_CORRESPONDENCE__ELEMENT_B = CORRESPONDENCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Same Type Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAME_TYPE_CORRESPONDENCE_FEATURE_COUNT = CORRESPONDENCE_FEATURE_COUNT + 2;

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
	 * The feature id for the '<em><b>Dependent Correspondences</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES = SAME_TYPE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
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
	 * The feature id for the '<em><b>Element B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_CORRESPONDENCE__ELEMENT_B = SAME_TYPE_CORRESPONDENCE__ELEMENT_B;

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
	 * The feature id for the '<em><b>Dependent Correspondences</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFEATURE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES = SAME_TYPE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
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
	 * The feature id for the '<em><b>Element B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFEATURE_CORRESPONDENCE__ELEMENT_B = SAME_TYPE_CORRESPONDENCE__ELEMENT_B;

	/**
	 * The feature id for the '<em><b>Mapped Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFEATURE_CORRESPONDENCE__MAPPED_FEATURE = SAME_TYPE_CORRESPONDENCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EFeature Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EFEATURE_CORRESPONDENCE_FEATURE_COUNT = SAME_TYPE_CORRESPONDENCE_FEATURE_COUNT + 1;

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
	 * The feature id for the '<em><b>Dependent Correspondences</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EATTRIBUTE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES = EFEATURE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
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
	 * The feature id for the '<em><b>Element B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EATTRIBUTE_CORRESPONDENCE__ELEMENT_B = EFEATURE_CORRESPONDENCE__ELEMENT_B;

	/**
	 * The feature id for the '<em><b>Mapped Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EATTRIBUTE_CORRESPONDENCE__MAPPED_FEATURE = EFEATURE_CORRESPONDENCE__MAPPED_FEATURE;

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
	 * The feature id for the '<em><b>Dependent Correspondences</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EREFERENCE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES = EFEATURE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
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
	 * The feature id for the '<em><b>Element B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EREFERENCE_CORRESPONDENCE__ELEMENT_B = EFEATURE_CORRESPONDENCE__ELEMENT_B;

	/**
	 * The feature id for the '<em><b>Mapped Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EREFERENCE_CORRESPONDENCE__MAPPED_FEATURE = EFEATURE_CORRESPONDENCE__MAPPED_FEATURE;

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
	 * The feature id for the '<em><b>Dependent Correspondences</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECONTAINMENT_REFERENCE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES = EREFERENCE_CORRESPONDENCE__DEPENDENT_CORRESPONDENCES;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
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
	 * The feature id for the '<em><b>Element B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECONTAINMENT_REFERENCE_CORRESPONDENCE__ELEMENT_B = EREFERENCE_CORRESPONDENCE__ELEMENT_B;

	/**
	 * The feature id for the '<em><b>Mapped Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECONTAINMENT_REFERENCE_CORRESPONDENCE__MAPPED_FEATURE = EREFERENCE_CORRESPONDENCE__MAPPED_FEATURE;

	/**
	 * The number of structural features of the '<em>EContainment Reference Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECONTAINMENT_REFERENCE_CORRESPONDENCE_FEATURE_COUNT = EREFERENCE_CORRESPONDENCE_FEATURE_COUNT + 0;


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
	 * Returns the meta object for the reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences#getCorrespondences <em>Correspondences</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Correspondences</em>'.
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
	 * Returns the meta object for the reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getDependentCorrespondences <em>Dependent Correspondences</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Dependent Correspondences</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getDependentCorrespondences()
	 * @see #getCorrespondence()
	 * @generated
	 */
	EReference getCorrespondence_DependentCorrespondences();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
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
	 * Returns the meta object for the reference '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence#getMappedFeature <em>Mapped Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Mapped Feature</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence#getMappedFeature()
	 * @see #getEFeatureCorrespondence()
	 * @generated
	 */
	EReference getEFeatureCorrespondence_MappedFeature();

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
		 * The meta object literal for the '<em><b>Correspondences</b></em>' reference list feature.
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
		 * The meta object literal for the '<em><b>Dependent Correspondences</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORRESPONDENCE__DEPENDENT_CORRESPONDENCES = eINSTANCE.getCorrespondence_DependentCorrespondences();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
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
		 * The meta object literal for the '<em><b>Element B</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SAME_TYPE_CORRESPONDENCE__ELEMENT_B = eINSTANCE.getSameTypeCorrespondence_ElementB();

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
		 * The meta object literal for the '<em><b>Mapped Feature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EFEATURE_CORRESPONDENCE__MAPPED_FEATURE = eINSTANCE.getEFeatureCorrespondence_MappedFeature();

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

	}

} //CorrespondencePackage
