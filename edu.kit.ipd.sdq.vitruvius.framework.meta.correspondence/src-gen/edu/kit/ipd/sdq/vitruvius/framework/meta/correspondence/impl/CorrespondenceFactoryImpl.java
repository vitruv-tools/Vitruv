/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceType;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EAttributeCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EContainmentReferenceCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EReferenceCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEAttributeCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * @generated
 */
public class CorrespondenceFactoryImpl extends EFactoryImpl implements CorrespondenceFactory {
    /**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public static CorrespondenceFactory init() {
		try {
			CorrespondenceFactory theCorrespondenceFactory = (CorrespondenceFactory)EPackage.Registry.INSTANCE.getEFactory(CorrespondencePackage.eNS_URI);
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public CorrespondenceFactoryImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case CorrespondencePackage.CORRESPONDENCES: return createCorrespondences();
			case CorrespondencePackage.EOBJECT_CORRESPONDENCE: return createEObjectCorrespondence();
			case CorrespondencePackage.EATTRIBUTE_CORRESPONDENCE: return createEAttributeCorrespondence();
			case CorrespondencePackage.EREFERENCE_CORRESPONDENCE: return createEReferenceCorrespondence();
			case CorrespondencePackage.ECONTAINMENT_REFERENCE_CORRESPONDENCE: return createEContainmentReferenceCorrespondence();
			case CorrespondencePackage.PARTIAL_EATTRIBUTE_CORRESPONDENCE: return createPartialEAttributeCorrespondence();
			case CorrespondencePackage.PARTIAL_EREFERENCE_CORRESPONDENCE: return createPartialEReferenceCorrespondence();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case CorrespondencePackage.CORRESPONDENCE_TYPE:
				return createCorrespondenceTypeFromString(eDataType, initialValue);
			case CorrespondencePackage.TUID:
				return createTUIDFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case CorrespondencePackage.CORRESPONDENCE_TYPE:
				return convertCorrespondenceTypeToString(eDataType, instanceValue);
			case CorrespondencePackage.TUID:
				return convertTUIDToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Correspondences createCorrespondences() {
		CorrespondencesImpl correspondences = new CorrespondencesImpl();
		return correspondences;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EObjectCorrespondence createEObjectCorrespondence() {
		EObjectCorrespondenceImpl eObjectCorrespondence = new EObjectCorrespondenceImpl();
		return eObjectCorrespondence;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttributeCorrespondence createEAttributeCorrespondence() {
		EAttributeCorrespondenceImpl eAttributeCorrespondence = new EAttributeCorrespondenceImpl();
		return eAttributeCorrespondence;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReferenceCorrespondence createEReferenceCorrespondence() {
		EReferenceCorrespondenceImpl eReferenceCorrespondence = new EReferenceCorrespondenceImpl();
		return eReferenceCorrespondence;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EContainmentReferenceCorrespondence createEContainmentReferenceCorrespondence() {
		EContainmentReferenceCorrespondenceImpl eContainmentReferenceCorrespondence = new EContainmentReferenceCorrespondenceImpl();
		return eContainmentReferenceCorrespondence;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public <TValue extends Object> PartialEAttributeCorrespondence<TValue> createPartialEAttributeCorrespondence() {
		PartialEAttributeCorrespondenceImpl<TValue> partialEAttributeCorrespondence = new PartialEAttributeCorrespondenceImpl<TValue>();
		return partialEAttributeCorrespondence;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public <TValue extends EObject> PartialEReferenceCorrespondence<TValue> createPartialEReferenceCorrespondence() {
		PartialEReferenceCorrespondenceImpl<TValue> partialEReferenceCorrespondence = new PartialEReferenceCorrespondenceImpl<TValue>();
		return partialEReferenceCorrespondence;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public CorrespondenceType createCorrespondenceTypeFromString(EDataType eDataType, String initialValue) {
		CorrespondenceType result = CorrespondenceType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public String convertCorrespondenceTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

    /**
     * <!-- begin-user-doc --> Use TUID.getInstance to create a valid TUID from String <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    public TUID createTUIDFromString(final EDataType eDataType, final String initialValue) {
        return TUID.getInstance(initialValue);
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public String convertTUIDToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public CorrespondencePackage getCorrespondencePackage() {
		return (CorrespondencePackage)getEPackage();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
    @Deprecated
    public static CorrespondencePackage getPackage() {
		return CorrespondencePackage.eINSTANCE;
	}

} // CorrespondenceFactoryImpl
