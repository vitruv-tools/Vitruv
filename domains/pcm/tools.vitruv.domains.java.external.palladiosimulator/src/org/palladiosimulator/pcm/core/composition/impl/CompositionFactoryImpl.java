/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.AssemblyEventConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.CompositionPackage;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector;
import org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector;
import org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector;
import org.palladiosimulator.pcm.core.composition.SinkDelegationConnector;
import org.palladiosimulator.pcm.core.composition.SourceDelegationConnector;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class CompositionFactoryImpl extends EFactoryImpl implements CompositionFactory {

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
    public static CompositionFactory init() {
        try {
            final CompositionFactory theCompositionFactory = (CompositionFactory) EPackage.Registry.INSTANCE
                    .getEFactory(CompositionPackage.eNS_URI);
            if (theCompositionFactory != null) {
                return theCompositionFactory;
            }
        } catch (final Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new CompositionFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public CompositionFactoryImpl() {
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
        case CompositionPackage.RESOURCE_REQUIRED_DELEGATION_CONNECTOR:
            return this.createResourceRequiredDelegationConnector();
        case CompositionPackage.EVENT_CHANNEL:
            return this.createEventChannel();
        case CompositionPackage.EVENT_CHANNEL_SOURCE_CONNECTOR:
            return this.createEventChannelSourceConnector();
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR:
            return this.createEventChannelSinkConnector();
        case CompositionPackage.PROVIDED_DELEGATION_CONNECTOR:
            return this.createProvidedDelegationConnector();
        case CompositionPackage.REQUIRED_DELEGATION_CONNECTOR:
            return this.createRequiredDelegationConnector();
        case CompositionPackage.ASSEMBLY_CONNECTOR:
            return this.createAssemblyConnector();
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR:
            return this.createAssemblyEventConnector();
        case CompositionPackage.SOURCE_DELEGATION_CONNECTOR:
            return this.createSourceDelegationConnector();
        case CompositionPackage.SINK_DELEGATION_CONNECTOR:
            return this.createSinkDelegationConnector();
        case CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR:
            return this.createAssemblyInfrastructureConnector();
        case CompositionPackage.PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR:
            return this.createProvidedInfrastructureDelegationConnector();
        case CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR:
            return this.createRequiredInfrastructureDelegationConnector();
        case CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR:
            return this.createRequiredResourceDelegationConnector();
        case CompositionPackage.ASSEMBLY_CONTEXT:
            return this.createAssemblyContext();
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
    public ResourceRequiredDelegationConnector createResourceRequiredDelegationConnector() {
        final ResourceRequiredDelegationConnectorImpl resourceRequiredDelegationConnector = new ResourceRequiredDelegationConnectorImpl();
        return resourceRequiredDelegationConnector;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EventChannel createEventChannel() {
        final EventChannelImpl eventChannel = new EventChannelImpl();
        return eventChannel;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EventChannelSourceConnector createEventChannelSourceConnector() {
        final EventChannelSourceConnectorImpl eventChannelSourceConnector = new EventChannelSourceConnectorImpl();
        return eventChannelSourceConnector;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EventChannelSinkConnector createEventChannelSinkConnector() {
        final EventChannelSinkConnectorImpl eventChannelSinkConnector = new EventChannelSinkConnectorImpl();
        return eventChannelSinkConnector;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ProvidedDelegationConnector createProvidedDelegationConnector() {
        final ProvidedDelegationConnectorImpl providedDelegationConnector = new ProvidedDelegationConnectorImpl();
        return providedDelegationConnector;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public RequiredDelegationConnector createRequiredDelegationConnector() {
        final RequiredDelegationConnectorImpl requiredDelegationConnector = new RequiredDelegationConnectorImpl();
        return requiredDelegationConnector;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AssemblyConnector createAssemblyConnector() {
        final AssemblyConnectorImpl assemblyConnector = new AssemblyConnectorImpl();
        return assemblyConnector;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AssemblyEventConnector createAssemblyEventConnector() {
        final AssemblyEventConnectorImpl assemblyEventConnector = new AssemblyEventConnectorImpl();
        return assemblyEventConnector;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public SourceDelegationConnector createSourceDelegationConnector() {
        final SourceDelegationConnectorImpl sourceDelegationConnector = new SourceDelegationConnectorImpl();
        return sourceDelegationConnector;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public SinkDelegationConnector createSinkDelegationConnector() {
        final SinkDelegationConnectorImpl sinkDelegationConnector = new SinkDelegationConnectorImpl();
        return sinkDelegationConnector;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AssemblyInfrastructureConnector createAssemblyInfrastructureConnector() {
        final AssemblyInfrastructureConnectorImpl assemblyInfrastructureConnector = new AssemblyInfrastructureConnectorImpl();
        return assemblyInfrastructureConnector;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ProvidedInfrastructureDelegationConnector createProvidedInfrastructureDelegationConnector() {
        final ProvidedInfrastructureDelegationConnectorImpl providedInfrastructureDelegationConnector = new ProvidedInfrastructureDelegationConnectorImpl();
        return providedInfrastructureDelegationConnector;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public RequiredInfrastructureDelegationConnector createRequiredInfrastructureDelegationConnector() {
        final RequiredInfrastructureDelegationConnectorImpl requiredInfrastructureDelegationConnector = new RequiredInfrastructureDelegationConnectorImpl();
        return requiredInfrastructureDelegationConnector;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public RequiredResourceDelegationConnector createRequiredResourceDelegationConnector() {
        final RequiredResourceDelegationConnectorImpl requiredResourceDelegationConnector = new RequiredResourceDelegationConnectorImpl();
        return requiredResourceDelegationConnector;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AssemblyContext createAssemblyContext() {
        final AssemblyContextImpl assemblyContext = new AssemblyContextImpl();
        return assemblyContext;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public CompositionPackage getCompositionPackage() {
        return (CompositionPackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @deprecated
     * @generated
     */
    @Deprecated
    public static CompositionPackage getPackage() {
        return CompositionPackage.eINSTANCE;
    }

} // CompositionFactoryImpl
