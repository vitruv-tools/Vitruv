/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.reliability.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.HardwareInducedFailureType;
import org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.NetworkInducedFailureType;
import org.palladiosimulator.pcm.reliability.ReliabilityFactory;
import org.palladiosimulator.pcm.reliability.ReliabilityPackage;
import org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType;
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class ReliabilityFactoryImpl extends EFactoryImpl implements ReliabilityFactory {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static ReliabilityFactory init() {
        try {
            final ReliabilityFactory theReliabilityFactory = (ReliabilityFactory) EPackage.Registry.INSTANCE
                    .getEFactory(ReliabilityPackage.eNS_URI);
            if (theReliabilityFactory != null) {
                return theReliabilityFactory;
            }
        } catch (final Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new ReliabilityFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ReliabilityFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EObject create(final EClass eClass) {
        switch (eClass.getClassifierID()) {
        case ReliabilityPackage.HARDWARE_INDUCED_FAILURE_TYPE:
            return this.createHardwareInducedFailureType();
        case ReliabilityPackage.SOFTWARE_INDUCED_FAILURE_TYPE:
            return this.createSoftwareInducedFailureType();
        case ReliabilityPackage.INTERNAL_FAILURE_OCCURRENCE_DESCRIPTION:
            return this.createInternalFailureOccurrenceDescription();
        case ReliabilityPackage.NETWORK_INDUCED_FAILURE_TYPE:
            return this.createNetworkInducedFailureType();
        case ReliabilityPackage.EXTERNAL_FAILURE_OCCURRENCE_DESCRIPTION:
            return this.createExternalFailureOccurrenceDescription();
        case ReliabilityPackage.RESOURCE_TIMEOUT_FAILURE_TYPE:
            return this.createResourceTimeoutFailureType();
        default:
            throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public HardwareInducedFailureType createHardwareInducedFailureType() {
        final HardwareInducedFailureTypeImpl hardwareInducedFailureType = new HardwareInducedFailureTypeImpl();
        return hardwareInducedFailureType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SoftwareInducedFailureType createSoftwareInducedFailureType() {
        final SoftwareInducedFailureTypeImpl softwareInducedFailureType = new SoftwareInducedFailureTypeImpl();
        return softwareInducedFailureType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public InternalFailureOccurrenceDescription createInternalFailureOccurrenceDescription() {
        final InternalFailureOccurrenceDescriptionImpl internalFailureOccurrenceDescription = new InternalFailureOccurrenceDescriptionImpl();
        return internalFailureOccurrenceDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NetworkInducedFailureType createNetworkInducedFailureType() {
        final NetworkInducedFailureTypeImpl networkInducedFailureType = new NetworkInducedFailureTypeImpl();
        return networkInducedFailureType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ExternalFailureOccurrenceDescription createExternalFailureOccurrenceDescription() {
        final ExternalFailureOccurrenceDescriptionImpl externalFailureOccurrenceDescription = new ExternalFailureOccurrenceDescriptionImpl();
        return externalFailureOccurrenceDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceTimeoutFailureType createResourceTimeoutFailureType() {
        final ResourceTimeoutFailureTypeImpl resourceTimeoutFailureType = new ResourceTimeoutFailureTypeImpl();
        return resourceTimeoutFailureType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ReliabilityPackage getReliabilityPackage() {
        return (ReliabilityPackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @deprecated
     * @generated
     */
    @Deprecated
    public static ReliabilityPackage getPackage() {
        return ReliabilityPackage.eINSTANCE;
    }

} // ReliabilityFactoryImpl
