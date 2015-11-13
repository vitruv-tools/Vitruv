/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence;

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
	 * The feature id for the '<em><b>Tuid CAR For As</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCES__TUID_CAR_FOR_AS = 1;

	/**
	 * The feature id for the '<em><b>Tuid CAR For Bs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCES__TUID_CAR_FOR_BS = 2;

	/**
	 * The number of structural features of the '<em>Correspondences</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCES_FEATURE_COUNT = 3;

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
	 * The meta object id for the '<em>TUID Calculator And Resolver</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUIDCalculatorAndResolver
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getTUIDCalculatorAndResolver()
	 * @generated
	 */
	int TUID_CALCULATOR_AND_RESOLVER = 2;

	/**
	 * The meta object id for the '<em>TUID</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getTUID()
	 * @generated
	 */
	int TUID = 3;


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
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences#getTuidCARForAs <em>Tuid CAR For As</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tuid CAR For As</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences#getTuidCARForAs()
	 * @see #getCorrespondences()
	 * @generated
	 */
	EAttribute getCorrespondences_TuidCARForAs();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences#getTuidCARForBs <em>Tuid CAR For Bs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tuid CAR For Bs</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences#getTuidCARForBs()
	 * @see #getCorrespondences()
	 * @generated
	 */
	EAttribute getCorrespondences_TuidCARForBs();

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
	 * Returns the meta object for the reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getDependsOn <em>Depends On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Depends On</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getDependsOn()
	 * @see #getCorrespondence()
	 * @generated
	 */
	EReference getCorrespondence_DependsOn();

	/**
	 * Returns the meta object for the reference list '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getDependedOnBy <em>Depended On By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Depended On By</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getDependedOnBy()
	 * @see #getCorrespondence()
	 * @generated
	 */
	EReference getCorrespondence_DependedOnBy();

	/**
	 * Returns the meta object for the attribute list '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getATUIDs <em>ATUI Ds</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>ATUI Ds</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getATUIDs()
	 * @see #getCorrespondence()
	 * @generated
	 */
	EAttribute getCorrespondence_ATUIDs();

	/**
	 * Returns the meta object for the attribute list '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getBTUIDs <em>BTUI Ds</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>BTUI Ds</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getBTUIDs()
	 * @see #getCorrespondence()
	 * @generated
	 */
	EAttribute getCorrespondence_BTUIDs();

	/**
	 * Returns the meta object for data type '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUIDCalculatorAndResolver <em>TUID Calculator And Resolver</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>TUID Calculator And Resolver</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUIDCalculatorAndResolver
	 * @model instanceClass="edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUIDCalculatorAndResolver" serializeable="false"
	 * @generated
	 */
	EDataType getTUIDCalculatorAndResolver();

	/**
	 * Returns the meta object for data type '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID <em>TUID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>TUID</em>'.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID
	 * @model instanceClass="edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID"
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
		 * The meta object literal for the '<em><b>Tuid CAR For As</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CORRESPONDENCES__TUID_CAR_FOR_AS = eINSTANCE.getCorrespondences_TuidCARForAs();

		/**
		 * The meta object literal for the '<em><b>Tuid CAR For Bs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CORRESPONDENCES__TUID_CAR_FOR_BS = eINSTANCE.getCorrespondences_TuidCARForBs();

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
		 * The meta object literal for the '<em>TUID Calculator And Resolver</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUIDCalculatorAndResolver
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getTUIDCalculatorAndResolver()
		 * @generated
		 */
		EDataType TUID_CALCULATOR_AND_RESOLVER = eINSTANCE.getTUIDCalculatorAndResolver();

		/**
		 * The meta object literal for the '<em>TUID</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID
		 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl.CorrespondencePackageImpl#getTUID()
		 * @generated
		 */
		EDataType TUID = eINSTANCE.getTUID();

	}

} //CorrespondencePackage
