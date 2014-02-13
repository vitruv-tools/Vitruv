/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CorrespondenceFactoryImpl extends EFactoryImpl implements CorrespondenceFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CorrespondenceFactory init() {
		try {
			CorrespondenceFactory theCorrespondenceFactory = (CorrespondenceFactory)EPackage.Registry.INSTANCE.getEFactory("http://edu.kit.ipd.sdq.vitruvius/Correspondence/1.0"); 
			if (theCorrespondenceFactory != null) {
				return theCorrespondenceFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CorrespondenceFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CorrespondenceFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case CorrespondencePackage.CORRESPONDENCES: return createCorrespondences();
			case CorrespondencePackage.EOBJECT_CORRESPONDENCE: return createEObjectCorrespondence();
			case CorrespondencePackage.EATTRIBUTE_CORRESPONDENCE: return createEAttributeCorrespondence();
			case CorrespondencePackage.ECONTAINMENT_REFERENCE_CORRESPONDENCE: return createEContainmentReferenceCorrespondence();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Correspondences createCorrespondences() {
		CorrespondencesImpl correspondences = new CorrespondencesImpl();
		return correspondences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectCorrespondence createEObjectCorrespondence() {
		EObjectCorrespondenceImpl eObjectCorrespondence = new EObjectCorrespondenceImpl();
		return eObjectCorrespondence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttributeCorrespondence createEAttributeCorrespondence() {
		EAttributeCorrespondenceImpl eAttributeCorrespondence = new EAttributeCorrespondenceImpl();
		return eAttributeCorrespondence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EContainmentReferenceCorrespondence createEContainmentReferenceCorrespondence() {
		EContainmentReferenceCorrespondenceImpl eContainmentReferenceCorrespondence = new EContainmentReferenceCorrespondenceImpl();
		return eContainmentReferenceCorrespondence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CorrespondencePackage getCorrespondencePackage() {
		return (CorrespondencePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CorrespondencePackage getPackage() {
		return CorrespondencePackage.eINSTANCE;
	}

} //CorrespondenceFactoryImpl
