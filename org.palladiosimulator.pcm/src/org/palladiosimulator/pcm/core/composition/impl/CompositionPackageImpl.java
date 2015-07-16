/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.palladiosimulator.pcm.PcmPackage;
import org.palladiosimulator.pcm.allocation.AllocationPackage;
import org.palladiosimulator.pcm.allocation.impl.AllocationPackageImpl;
import org.palladiosimulator.pcm.core.CorePackage;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.AssemblyEventConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.CompositionPackage;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.composition.DelegationConnector;
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
import org.palladiosimulator.pcm.core.composition.util.CompositionValidator;
import org.palladiosimulator.pcm.core.entity.EntityPackage;
import org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl;
import org.palladiosimulator.pcm.core.impl.CorePackageImpl;
import org.palladiosimulator.pcm.impl.PcmPackageImpl;
import org.palladiosimulator.pcm.parameter.ParameterPackage;
import org.palladiosimulator.pcm.parameter.impl.ParameterPackageImpl;
import org.palladiosimulator.pcm.protocol.ProtocolPackage;
import org.palladiosimulator.pcm.protocol.impl.ProtocolPackageImpl;
import org.palladiosimulator.pcm.qosannotations.QosannotationsPackage;
import org.palladiosimulator.pcm.qosannotations.impl.QosannotationsPackageImpl;
import org.palladiosimulator.pcm.qosannotations.qos_performance.QosPerformancePackage;
import org.palladiosimulator.pcm.qosannotations.qos_performance.impl.QosPerformancePackageImpl;
import org.palladiosimulator.pcm.qosannotations.qos_reliability.QosReliabilityPackage;
import org.palladiosimulator.pcm.qosannotations.qos_reliability.impl.QosReliabilityPackageImpl;
import org.palladiosimulator.pcm.reliability.ReliabilityPackage;
import org.palladiosimulator.pcm.reliability.impl.ReliabilityPackageImpl;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.repository.impl.RepositoryPackageImpl;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentPackage;
import org.palladiosimulator.pcm.resourceenvironment.impl.ResourceenvironmentPackageImpl;
import org.palladiosimulator.pcm.resourcetype.ResourcetypePackage;
import org.palladiosimulator.pcm.resourcetype.impl.ResourcetypePackageImpl;
import org.palladiosimulator.pcm.seff.SeffPackage;
import org.palladiosimulator.pcm.seff.impl.SeffPackageImpl;
import org.palladiosimulator.pcm.seff.seff_performance.SeffPerformancePackage;
import org.palladiosimulator.pcm.seff.seff_performance.impl.SeffPerformancePackageImpl;
import org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityPackage;
import org.palladiosimulator.pcm.seff.seff_reliability.impl.SeffReliabilityPackageImpl;
import org.palladiosimulator.pcm.subsystem.SubsystemPackage;
import org.palladiosimulator.pcm.subsystem.impl.SubsystemPackageImpl;
import org.palladiosimulator.pcm.system.SystemPackage;
import org.palladiosimulator.pcm.system.impl.SystemPackageImpl;
import org.palladiosimulator.pcm.usagemodel.UsagemodelPackage;
import org.palladiosimulator.pcm.usagemodel.impl.UsagemodelPackageImpl;

import de.uka.ipd.sdq.identifier.IdentifierPackage;
import de.uka.ipd.sdq.stoex.StoexPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class CompositionPackageImpl extends EPackageImpl implements CompositionPackage {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass delegationConnectorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass connectorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass composedStructureEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass resourceRequiredDelegationConnectorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass eventChannelEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass eventChannelSourceConnectorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass eventChannelSinkConnectorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass providedDelegationConnectorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass requiredDelegationConnectorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass assemblyConnectorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass assemblyEventConnectorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass sourceDelegationConnectorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass sinkDelegationConnectorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass assemblyInfrastructureConnectorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass providedInfrastructureDelegationConnectorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass requiredInfrastructureDelegationConnectorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass requiredResourceDelegationConnectorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass assemblyContextEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package package URI
     * value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init
     * init()}, which also performs initialization of the package, or returns the registered
     * package, if one already exists. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private CompositionPackageImpl() {
        super(eNS_URI, CompositionFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others
     * upon which it depends.
     *
     * <p>
     * This method is used to initialize {@link CompositionPackage#eINSTANCE} when that field is
     * accessed. Clients should not invoke it directly. Instead, they should simply access that
     * field to obtain the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static CompositionPackage init() {
        if (isInited) {
            return (CompositionPackage) EPackage.Registry.INSTANCE.getEPackage(CompositionPackage.eNS_URI);
        }

        // Obtain or create and register package
        final CompositionPackageImpl theCompositionPackage = (CompositionPackageImpl) (EPackage.Registry.INSTANCE
                .get(eNS_URI) instanceof CompositionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
                        : new CompositionPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        IdentifierPackage.eINSTANCE.eClass();
        StoexPackage.eINSTANCE.eClass();

        // Obtain or create and register interdependencies
        final PcmPackageImpl thePcmPackage = (PcmPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(PcmPackage.eNS_URI) instanceof PcmPackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(PcmPackage.eNS_URI) : PcmPackage.eINSTANCE);
        final CorePackageImpl theCorePackage = (CorePackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(CorePackage.eNS_URI) instanceof CorePackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(CorePackage.eNS_URI) : CorePackage.eINSTANCE);
        final EntityPackageImpl theEntityPackage = (EntityPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(EntityPackage.eNS_URI) instanceof EntityPackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(EntityPackage.eNS_URI) : EntityPackage.eINSTANCE);
        final UsagemodelPackageImpl theUsagemodelPackage = (UsagemodelPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(UsagemodelPackage.eNS_URI) instanceof UsagemodelPackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(UsagemodelPackage.eNS_URI)
                        : UsagemodelPackage.eINSTANCE);
        final RepositoryPackageImpl theRepositoryPackage = (RepositoryPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(RepositoryPackage.eNS_URI) instanceof RepositoryPackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(RepositoryPackage.eNS_URI)
                        : RepositoryPackage.eINSTANCE);
        final ResourcetypePackageImpl theResourcetypePackage = (ResourcetypePackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(ResourcetypePackage.eNS_URI) instanceof ResourcetypePackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(ResourcetypePackage.eNS_URI)
                        : ResourcetypePackage.eINSTANCE);
        final ProtocolPackageImpl theProtocolPackage = (ProtocolPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(ProtocolPackage.eNS_URI) instanceof ProtocolPackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(ProtocolPackage.eNS_URI) : ProtocolPackage.eINSTANCE);
        final ParameterPackageImpl theParameterPackage = (ParameterPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(ParameterPackage.eNS_URI) instanceof ParameterPackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(ParameterPackage.eNS_URI)
                        : ParameterPackage.eINSTANCE);
        final ReliabilityPackageImpl theReliabilityPackage = (ReliabilityPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(ReliabilityPackage.eNS_URI) instanceof ReliabilityPackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(ReliabilityPackage.eNS_URI)
                        : ReliabilityPackage.eINSTANCE);
        final SeffPackageImpl theSeffPackage = (SeffPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(SeffPackage.eNS_URI) instanceof SeffPackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(SeffPackage.eNS_URI) : SeffPackage.eINSTANCE);
        final SeffPerformancePackageImpl theSeffPerformancePackage = (SeffPerformancePackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(SeffPerformancePackage.eNS_URI) instanceof SeffPerformancePackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(SeffPerformancePackage.eNS_URI)
                        : SeffPerformancePackage.eINSTANCE);
        final SeffReliabilityPackageImpl theSeffReliabilityPackage = (SeffReliabilityPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(SeffReliabilityPackage.eNS_URI) instanceof SeffReliabilityPackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(SeffReliabilityPackage.eNS_URI)
                        : SeffReliabilityPackage.eINSTANCE);
        final QosannotationsPackageImpl theQosannotationsPackage = (QosannotationsPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(QosannotationsPackage.eNS_URI) instanceof QosannotationsPackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(QosannotationsPackage.eNS_URI)
                        : QosannotationsPackage.eINSTANCE);
        final QosPerformancePackageImpl theQosPerformancePackage = (QosPerformancePackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(QosPerformancePackage.eNS_URI) instanceof QosPerformancePackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(QosPerformancePackage.eNS_URI)
                        : QosPerformancePackage.eINSTANCE);
        final QosReliabilityPackageImpl theQosReliabilityPackage = (QosReliabilityPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(QosReliabilityPackage.eNS_URI) instanceof QosReliabilityPackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(QosReliabilityPackage.eNS_URI)
                        : QosReliabilityPackage.eINSTANCE);
        final SystemPackageImpl theSystemPackage = (SystemPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(SystemPackage.eNS_URI) instanceof SystemPackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(SystemPackage.eNS_URI) : SystemPackage.eINSTANCE);
        final ResourceenvironmentPackageImpl theResourceenvironmentPackage = (ResourceenvironmentPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(ResourceenvironmentPackage.eNS_URI) instanceof ResourceenvironmentPackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(ResourceenvironmentPackage.eNS_URI)
                        : ResourceenvironmentPackage.eINSTANCE);
        final AllocationPackageImpl theAllocationPackage = (AllocationPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(AllocationPackage.eNS_URI) instanceof AllocationPackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(AllocationPackage.eNS_URI)
                        : AllocationPackage.eINSTANCE);
        final SubsystemPackageImpl theSubsystemPackage = (SubsystemPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(SubsystemPackage.eNS_URI) instanceof SubsystemPackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(SubsystemPackage.eNS_URI)
                        : SubsystemPackage.eINSTANCE);

        // Create package meta-data objects
        theCompositionPackage.createPackageContents();
        thePcmPackage.createPackageContents();
        theCorePackage.createPackageContents();
        theEntityPackage.createPackageContents();
        theUsagemodelPackage.createPackageContents();
        theRepositoryPackage.createPackageContents();
        theResourcetypePackage.createPackageContents();
        theProtocolPackage.createPackageContents();
        theParameterPackage.createPackageContents();
        theReliabilityPackage.createPackageContents();
        theSeffPackage.createPackageContents();
        theSeffPerformancePackage.createPackageContents();
        theSeffReliabilityPackage.createPackageContents();
        theQosannotationsPackage.createPackageContents();
        theQosPerformancePackage.createPackageContents();
        theQosReliabilityPackage.createPackageContents();
        theSystemPackage.createPackageContents();
        theResourceenvironmentPackage.createPackageContents();
        theAllocationPackage.createPackageContents();
        theSubsystemPackage.createPackageContents();

        // Initialize created meta-data
        theCompositionPackage.initializePackageContents();
        thePcmPackage.initializePackageContents();
        theCorePackage.initializePackageContents();
        theEntityPackage.initializePackageContents();
        theUsagemodelPackage.initializePackageContents();
        theRepositoryPackage.initializePackageContents();
        theResourcetypePackage.initializePackageContents();
        theProtocolPackage.initializePackageContents();
        theParameterPackage.initializePackageContents();
        theReliabilityPackage.initializePackageContents();
        theSeffPackage.initializePackageContents();
        theSeffPerformancePackage.initializePackageContents();
        theSeffReliabilityPackage.initializePackageContents();
        theQosannotationsPackage.initializePackageContents();
        theQosPerformancePackage.initializePackageContents();
        theQosReliabilityPackage.initializePackageContents();
        theSystemPackage.initializePackageContents();
        theResourceenvironmentPackage.initializePackageContents();
        theAllocationPackage.initializePackageContents();
        theSubsystemPackage.initializePackageContents();

        // Register package validator
        EValidator.Registry.INSTANCE.put(theCompositionPackage, new EValidator.Descriptor() {

            @Override
            public EValidator getEValidator() {
                return CompositionValidator.INSTANCE;
            }
        });

        // Mark meta-data to indicate it can't be changed
        theCompositionPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(CompositionPackage.eNS_URI, theCompositionPackage);
        return theCompositionPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDelegationConnector() {
        return this.delegationConnectorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConnector() {
        return this.connectorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getConnector_ParentStructure__Connector() {
        return (EReference) this.connectorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getComposedStructure() {
        return this.composedStructureEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getComposedStructure_AssemblyContexts__ComposedStructure() {
        return (EReference) this.composedStructureEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getComposedStructure_ResourceRequiredDelegationConnectors_ComposedStructure() {
        return (EReference) this.composedStructureEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getComposedStructure_EventChannel__ComposedStructure() {
        return (EReference) this.composedStructureEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getComposedStructure_Connectors__ComposedStructure() {
        return (EReference) this.composedStructureEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getResourceRequiredDelegationConnector() {
        return this.resourceRequiredDelegationConnectorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getResourceRequiredDelegationConnector_InnerResourceRequiredRole_ResourceRequiredDelegationConnector() {
        return (EReference) this.resourceRequiredDelegationConnectorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getResourceRequiredDelegationConnector_OuterResourceRequiredRole_ResourceRequiredDelegationConnector() {
        return (EReference) this.resourceRequiredDelegationConnectorEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getResourceRequiredDelegationConnector_ParentStructure_ResourceRequiredDelegationConnector() {
        return (EReference) this.resourceRequiredDelegationConnectorEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEventChannel() {
        return this.eventChannelEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEventChannel_EventGroup__EventChannel() {
        return (EReference) this.eventChannelEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEventChannel_EventChannelSourceConnector__EventChannel() {
        return (EReference) this.eventChannelEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEventChannel_EventChannelSinkConnector__EventChannel() {
        return (EReference) this.eventChannelEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEventChannel_ParentStructure__EventChannel() {
        return (EReference) this.eventChannelEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEventChannelSourceConnector() {
        return this.eventChannelSourceConnectorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEventChannelSourceConnector_SourceRole__EventChannelSourceRole() {
        return (EReference) this.eventChannelSourceConnectorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEventChannelSourceConnector_AssemblyContext__EventChannelSourceConnector() {
        return (EReference) this.eventChannelSourceConnectorEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEventChannelSourceConnector_EventChannel__EventChannelSourceConnector() {
        return (EReference) this.eventChannelSourceConnectorEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEventChannelSinkConnector() {
        return this.eventChannelSinkConnectorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEventChannelSinkConnector_SinkRole__EventChannelSinkConnector() {
        return (EReference) this.eventChannelSinkConnectorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEventChannelSinkConnector_FilterCondition__EventChannelSinkConnector() {
        return (EReference) this.eventChannelSinkConnectorEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEventChannelSinkConnector_AssemblyContext__EventChannelSinkConnector() {
        return (EReference) this.eventChannelSinkConnectorEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEventChannelSinkConnector_EventChannel__EventChannelSinkConnector() {
        return (EReference) this.eventChannelSinkConnectorEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getProvidedDelegationConnector() {
        return this.providedDelegationConnectorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getProvidedDelegationConnector_InnerProvidedRole_ProvidedDelegationConnector() {
        return (EReference) this.providedDelegationConnectorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getProvidedDelegationConnector_OuterProvidedRole_ProvidedDelegationConnector() {
        return (EReference) this.providedDelegationConnectorEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getProvidedDelegationConnector_AssemblyContext_ProvidedDelegationConnector() {
        return (EReference) this.providedDelegationConnectorEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRequiredDelegationConnector() {
        return this.requiredDelegationConnectorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRequiredDelegationConnector_InnerRequiredRole_RequiredDelegationConnector() {
        return (EReference) this.requiredDelegationConnectorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRequiredDelegationConnector_OuterRequiredRole_RequiredDelegationConnector() {
        return (EReference) this.requiredDelegationConnectorEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRequiredDelegationConnector_AssemblyContext_RequiredDelegationConnector() {
        return (EReference) this.requiredDelegationConnectorEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getAssemblyConnector() {
        return this.assemblyConnectorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAssemblyConnector_RequiringAssemblyContext_AssemblyConnector() {
        return (EReference) this.assemblyConnectorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAssemblyConnector_ProvidingAssemblyContext_AssemblyConnector() {
        return (EReference) this.assemblyConnectorEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAssemblyConnector_ProvidedRole_AssemblyConnector() {
        return (EReference) this.assemblyConnectorEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAssemblyConnector_RequiredRole_AssemblyConnector() {
        return (EReference) this.assemblyConnectorEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getAssemblyEventConnector() {
        return this.assemblyEventConnectorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAssemblyEventConnector_SinkRole__AssemblyEventConnector() {
        return (EReference) this.assemblyEventConnectorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAssemblyEventConnector_SourceRole__AssemblyEventConnector() {
        return (EReference) this.assemblyEventConnectorEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAssemblyEventConnector_SinkAssemblyContext__AssemblyEventConnector() {
        return (EReference) this.assemblyEventConnectorEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAssemblyEventConnector_SourceAssemblyContext__AssemblyEventConnector() {
        return (EReference) this.assemblyEventConnectorEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAssemblyEventConnector_FilterCondition__AssemblyEventConnector() {
        return (EReference) this.assemblyEventConnectorEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getSourceDelegationConnector() {
        return this.sourceDelegationConnectorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSourceDelegationConnector_InnerSourceRole__SourceRole() {
        return (EReference) this.sourceDelegationConnectorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSourceDelegationConnector_OuterSourceRole__SourceRole() {
        return (EReference) this.sourceDelegationConnectorEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSourceDelegationConnector_AssemblyContext__SourceDelegationConnector() {
        return (EReference) this.sourceDelegationConnectorEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getSinkDelegationConnector() {
        return this.sinkDelegationConnectorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSinkDelegationConnector_AssemblyContext__SinkDelegationConnector() {
        return (EReference) this.sinkDelegationConnectorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSinkDelegationConnector_InnerSinkRole__SinkRole() {
        return (EReference) this.sinkDelegationConnectorEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSinkDelegationConnector_OuterSinkRole__SinkRole() {
        return (EReference) this.sinkDelegationConnectorEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getAssemblyInfrastructureConnector() {
        return this.assemblyInfrastructureConnectorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAssemblyInfrastructureConnector_ProvidedRole__AssemblyInfrastructureConnector() {
        return (EReference) this.assemblyInfrastructureConnectorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAssemblyInfrastructureConnector_RequiredRole__AssemblyInfrastructureConnector() {
        return (EReference) this.assemblyInfrastructureConnectorEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAssemblyInfrastructureConnector_ProvidingAssemblyContext__AssemblyInfrastructureConnector() {
        return (EReference) this.assemblyInfrastructureConnectorEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAssemblyInfrastructureConnector_RequiringAssemblyContext__AssemblyInfrastructureConnector() {
        return (EReference) this.assemblyInfrastructureConnectorEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getProvidedInfrastructureDelegationConnector() {
        return this.providedInfrastructureDelegationConnectorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getProvidedInfrastructureDelegationConnector_InnerProvidedRole__ProvidedInfrastructureDelegationConnector() {
        return (EReference) this.providedInfrastructureDelegationConnectorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getProvidedInfrastructureDelegationConnector_OuterProvidedRole__ProvidedInfrastructureDelegationConnector() {
        return (EReference) this.providedInfrastructureDelegationConnectorEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getProvidedInfrastructureDelegationConnector_AssemblyContext__ProvidedInfrastructureDelegationConnector() {
        return (EReference) this.providedInfrastructureDelegationConnectorEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRequiredInfrastructureDelegationConnector() {
        return this.requiredInfrastructureDelegationConnectorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRequiredInfrastructureDelegationConnector_InnerRequiredRole__RequiredInfrastructureDelegationConnector() {
        return (EReference) this.requiredInfrastructureDelegationConnectorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRequiredInfrastructureDelegationConnector_OuterRequiredRole__RequiredInfrastructureDelegationConnector() {
        return (EReference) this.requiredInfrastructureDelegationConnectorEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRequiredInfrastructureDelegationConnector_AssemblyContext__RequiredInfrastructureDelegationConnector() {
        return (EReference) this.requiredInfrastructureDelegationConnectorEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRequiredResourceDelegationConnector() {
        return this.requiredResourceDelegationConnectorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRequiredResourceDelegationConnector_AssemblyContext__RequiredResourceDelegationConnector() {
        return (EReference) this.requiredResourceDelegationConnectorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRequiredResourceDelegationConnector_InnerRequiredRole__RequiredResourceDelegationConnector() {
        return (EReference) this.requiredResourceDelegationConnectorEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRequiredResourceDelegationConnector_OuterRequiredRole__RequiredResourceDelegationConnector() {
        return (EReference) this.requiredResourceDelegationConnectorEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getAssemblyContext() {
        return this.assemblyContextEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAssemblyContext_ParentStructure__AssemblyContext() {
        return (EReference) this.assemblyContextEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAssemblyContext_EncapsulatedComponent__AssemblyContext() {
        return (EReference) this.assemblyContextEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAssemblyContext_ConfigParameterUsages__AssemblyContext() {
        return (EReference) this.assemblyContextEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CompositionFactory getCompositionFactory() {
        return (CompositionFactory) this.getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package. This method is guarded to have no affect on
     * any invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void createPackageContents() {
        if (this.isCreated) {
            return;
        }
        this.isCreated = true;

        // Create classes and their features
        this.delegationConnectorEClass = this.createEClass(DELEGATION_CONNECTOR);

        this.connectorEClass = this.createEClass(CONNECTOR);
        this.createEReference(this.connectorEClass, CONNECTOR__PARENT_STRUCTURE_CONNECTOR);

        this.composedStructureEClass = this.createEClass(COMPOSED_STRUCTURE);
        this.createEReference(this.composedStructureEClass, COMPOSED_STRUCTURE__ASSEMBLY_CONTEXTS_COMPOSED_STRUCTURE);
        this.createEReference(this.composedStructureEClass,
                COMPOSED_STRUCTURE__RESOURCE_REQUIRED_DELEGATION_CONNECTORS_COMPOSED_STRUCTURE);
        this.createEReference(this.composedStructureEClass, COMPOSED_STRUCTURE__EVENT_CHANNEL_COMPOSED_STRUCTURE);
        this.createEReference(this.composedStructureEClass, COMPOSED_STRUCTURE__CONNECTORS_COMPOSED_STRUCTURE);

        this.resourceRequiredDelegationConnectorEClass = this.createEClass(RESOURCE_REQUIRED_DELEGATION_CONNECTOR);
        this.createEReference(this.resourceRequiredDelegationConnectorEClass,
                RESOURCE_REQUIRED_DELEGATION_CONNECTOR__INNER_RESOURCE_REQUIRED_ROLE_RESOURCE_REQUIRED_DELEGATION_CONNECTOR);
        this.createEReference(this.resourceRequiredDelegationConnectorEClass,
                RESOURCE_REQUIRED_DELEGATION_CONNECTOR__OUTER_RESOURCE_REQUIRED_ROLE_RESOURCE_REQUIRED_DELEGATION_CONNECTOR);
        this.createEReference(this.resourceRequiredDelegationConnectorEClass,
                RESOURCE_REQUIRED_DELEGATION_CONNECTOR__PARENT_STRUCTURE_RESOURCE_REQUIRED_DELEGATION_CONNECTOR);

        this.eventChannelEClass = this.createEClass(EVENT_CHANNEL);
        this.createEReference(this.eventChannelEClass, EVENT_CHANNEL__EVENT_GROUP_EVENT_CHANNEL);
        this.createEReference(this.eventChannelEClass, EVENT_CHANNEL__EVENT_CHANNEL_SOURCE_CONNECTOR_EVENT_CHANNEL);
        this.createEReference(this.eventChannelEClass, EVENT_CHANNEL__EVENT_CHANNEL_SINK_CONNECTOR_EVENT_CHANNEL);
        this.createEReference(this.eventChannelEClass, EVENT_CHANNEL__PARENT_STRUCTURE_EVENT_CHANNEL);

        this.eventChannelSourceConnectorEClass = this.createEClass(EVENT_CHANNEL_SOURCE_CONNECTOR);
        this.createEReference(this.eventChannelSourceConnectorEClass,
                EVENT_CHANNEL_SOURCE_CONNECTOR__SOURCE_ROLE_EVENT_CHANNEL_SOURCE_ROLE);
        this.createEReference(this.eventChannelSourceConnectorEClass,
                EVENT_CHANNEL_SOURCE_CONNECTOR__ASSEMBLY_CONTEXT_EVENT_CHANNEL_SOURCE_CONNECTOR);
        this.createEReference(this.eventChannelSourceConnectorEClass,
                EVENT_CHANNEL_SOURCE_CONNECTOR__EVENT_CHANNEL_EVENT_CHANNEL_SOURCE_CONNECTOR);

        this.eventChannelSinkConnectorEClass = this.createEClass(EVENT_CHANNEL_SINK_CONNECTOR);
        this.createEReference(this.eventChannelSinkConnectorEClass,
                EVENT_CHANNEL_SINK_CONNECTOR__SINK_ROLE_EVENT_CHANNEL_SINK_CONNECTOR);
        this.createEReference(this.eventChannelSinkConnectorEClass,
                EVENT_CHANNEL_SINK_CONNECTOR__FILTER_CONDITION_EVENT_CHANNEL_SINK_CONNECTOR);
        this.createEReference(this.eventChannelSinkConnectorEClass,
                EVENT_CHANNEL_SINK_CONNECTOR__ASSEMBLY_CONTEXT_EVENT_CHANNEL_SINK_CONNECTOR);
        this.createEReference(this.eventChannelSinkConnectorEClass,
                EVENT_CHANNEL_SINK_CONNECTOR__EVENT_CHANNEL_EVENT_CHANNEL_SINK_CONNECTOR);

        this.providedDelegationConnectorEClass = this.createEClass(PROVIDED_DELEGATION_CONNECTOR);
        this.createEReference(this.providedDelegationConnectorEClass,
                PROVIDED_DELEGATION_CONNECTOR__INNER_PROVIDED_ROLE_PROVIDED_DELEGATION_CONNECTOR);
        this.createEReference(this.providedDelegationConnectorEClass,
                PROVIDED_DELEGATION_CONNECTOR__OUTER_PROVIDED_ROLE_PROVIDED_DELEGATION_CONNECTOR);
        this.createEReference(this.providedDelegationConnectorEClass,
                PROVIDED_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_PROVIDED_DELEGATION_CONNECTOR);

        this.requiredDelegationConnectorEClass = this.createEClass(REQUIRED_DELEGATION_CONNECTOR);
        this.createEReference(this.requiredDelegationConnectorEClass,
                REQUIRED_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_DELEGATION_CONNECTOR);
        this.createEReference(this.requiredDelegationConnectorEClass,
                REQUIRED_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_DELEGATION_CONNECTOR);
        this.createEReference(this.requiredDelegationConnectorEClass,
                REQUIRED_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_DELEGATION_CONNECTOR);

        this.assemblyConnectorEClass = this.createEClass(ASSEMBLY_CONNECTOR);
        this.createEReference(this.assemblyConnectorEClass,
                ASSEMBLY_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR);
        this.createEReference(this.assemblyConnectorEClass,
                ASSEMBLY_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR);
        this.createEReference(this.assemblyConnectorEClass, ASSEMBLY_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_CONNECTOR);
        this.createEReference(this.assemblyConnectorEClass, ASSEMBLY_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_CONNECTOR);

        this.assemblyEventConnectorEClass = this.createEClass(ASSEMBLY_EVENT_CONNECTOR);
        this.createEReference(this.assemblyEventConnectorEClass,
                ASSEMBLY_EVENT_CONNECTOR__SINK_ROLE_ASSEMBLY_EVENT_CONNECTOR);
        this.createEReference(this.assemblyEventConnectorEClass,
                ASSEMBLY_EVENT_CONNECTOR__SOURCE_ROLE_ASSEMBLY_EVENT_CONNECTOR);
        this.createEReference(this.assemblyEventConnectorEClass,
                ASSEMBLY_EVENT_CONNECTOR__SINK_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR);
        this.createEReference(this.assemblyEventConnectorEClass,
                ASSEMBLY_EVENT_CONNECTOR__SOURCE_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR);
        this.createEReference(this.assemblyEventConnectorEClass,
                ASSEMBLY_EVENT_CONNECTOR__FILTER_CONDITION_ASSEMBLY_EVENT_CONNECTOR);

        this.sourceDelegationConnectorEClass = this.createEClass(SOURCE_DELEGATION_CONNECTOR);
        this.createEReference(this.sourceDelegationConnectorEClass,
                SOURCE_DELEGATION_CONNECTOR__INNER_SOURCE_ROLE_SOURCE_ROLE);
        this.createEReference(this.sourceDelegationConnectorEClass,
                SOURCE_DELEGATION_CONNECTOR__OUTER_SOURCE_ROLE_SOURCE_ROLE);
        this.createEReference(this.sourceDelegationConnectorEClass,
                SOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_SOURCE_DELEGATION_CONNECTOR);

        this.sinkDelegationConnectorEClass = this.createEClass(SINK_DELEGATION_CONNECTOR);
        this.createEReference(this.sinkDelegationConnectorEClass,
                SINK_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_SINK_DELEGATION_CONNECTOR);
        this.createEReference(this.sinkDelegationConnectorEClass, SINK_DELEGATION_CONNECTOR__INNER_SINK_ROLE_SINK_ROLE);
        this.createEReference(this.sinkDelegationConnectorEClass, SINK_DELEGATION_CONNECTOR__OUTER_SINK_ROLE_SINK_ROLE);

        this.assemblyInfrastructureConnectorEClass = this.createEClass(ASSEMBLY_INFRASTRUCTURE_CONNECTOR);
        this.createEReference(this.assemblyInfrastructureConnectorEClass,
                ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR);
        this.createEReference(this.assemblyInfrastructureConnectorEClass,
                ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR);
        this.createEReference(this.assemblyInfrastructureConnectorEClass,
                ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR);
        this.createEReference(this.assemblyInfrastructureConnectorEClass,
                ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR);

        this.providedInfrastructureDelegationConnectorEClass = this
                .createEClass(PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR);
        this.createEReference(this.providedInfrastructureDelegationConnectorEClass,
                PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR__INNER_PROVIDED_ROLE_PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR);
        this.createEReference(this.providedInfrastructureDelegationConnectorEClass,
                PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR__OUTER_PROVIDED_ROLE_PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR);
        this.createEReference(this.providedInfrastructureDelegationConnectorEClass,
                PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR);

        this.requiredInfrastructureDelegationConnectorEClass = this
                .createEClass(REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR);
        this.createEReference(this.requiredInfrastructureDelegationConnectorEClass,
                REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR);
        this.createEReference(this.requiredInfrastructureDelegationConnectorEClass,
                REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR);
        this.createEReference(this.requiredInfrastructureDelegationConnectorEClass,
                REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR);

        this.requiredResourceDelegationConnectorEClass = this.createEClass(REQUIRED_RESOURCE_DELEGATION_CONNECTOR);
        this.createEReference(this.requiredResourceDelegationConnectorEClass,
                REQUIRED_RESOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_RESOURCE_DELEGATION_CONNECTOR);
        this.createEReference(this.requiredResourceDelegationConnectorEClass,
                REQUIRED_RESOURCE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR);
        this.createEReference(this.requiredResourceDelegationConnectorEClass,
                REQUIRED_RESOURCE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR);

        this.assemblyContextEClass = this.createEClass(ASSEMBLY_CONTEXT);
        this.createEReference(this.assemblyContextEClass, ASSEMBLY_CONTEXT__PARENT_STRUCTURE_ASSEMBLY_CONTEXT);
        this.createEReference(this.assemblyContextEClass, ASSEMBLY_CONTEXT__ENCAPSULATED_COMPONENT_ASSEMBLY_CONTEXT);
        this.createEReference(this.assemblyContextEClass, ASSEMBLY_CONTEXT__CONFIG_PARAMETER_USAGES_ASSEMBLY_CONTEXT);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model. This method is guarded to have
     * no affect on any invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void initializePackageContents() {
        if (this.isInitialized) {
            return;
        }
        this.isInitialized = true;

        // Initialize package
        this.setName(eNAME);
        this.setNsPrefix(eNS_PREFIX);
        this.setNsURI(eNS_URI);

        // Obtain other dependent packages
        final EntityPackage theEntityPackage = (EntityPackage) EPackage.Registry.INSTANCE
                .getEPackage(EntityPackage.eNS_URI);
        final RepositoryPackage theRepositoryPackage = (RepositoryPackage) EPackage.Registry.INSTANCE
                .getEPackage(RepositoryPackage.eNS_URI);
        final CorePackage theCorePackage = (CorePackage) EPackage.Registry.INSTANCE.getEPackage(CorePackage.eNS_URI);
        final ParameterPackage theParameterPackage = (ParameterPackage) EPackage.Registry.INSTANCE
                .getEPackage(ParameterPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.delegationConnectorEClass.getESuperTypes().add(this.getConnector());
        this.connectorEClass.getESuperTypes().add(theEntityPackage.getEntity());
        this.composedStructureEClass.getESuperTypes().add(theEntityPackage.getEntity());
        this.eventChannelEClass.getESuperTypes().add(theEntityPackage.getEntity());
        this.eventChannelSourceConnectorEClass.getESuperTypes().add(this.getConnector());
        this.eventChannelSinkConnectorEClass.getESuperTypes().add(this.getConnector());
        this.providedDelegationConnectorEClass.getESuperTypes().add(this.getDelegationConnector());
        this.requiredDelegationConnectorEClass.getESuperTypes().add(this.getDelegationConnector());
        this.assemblyConnectorEClass.getESuperTypes().add(this.getConnector());
        this.assemblyEventConnectorEClass.getESuperTypes().add(this.getConnector());
        this.sourceDelegationConnectorEClass.getESuperTypes().add(this.getDelegationConnector());
        this.sinkDelegationConnectorEClass.getESuperTypes().add(this.getDelegationConnector());
        this.assemblyInfrastructureConnectorEClass.getESuperTypes().add(this.getConnector());
        this.providedInfrastructureDelegationConnectorEClass.getESuperTypes().add(this.getDelegationConnector());
        this.requiredInfrastructureDelegationConnectorEClass.getESuperTypes().add(this.getDelegationConnector());
        this.requiredResourceDelegationConnectorEClass.getESuperTypes().add(this.getDelegationConnector());
        this.assemblyContextEClass.getESuperTypes().add(theEntityPackage.getEntity());

        // Initialize classes and features; add operations and parameters
        this.initEClass(this.delegationConnectorEClass, DelegationConnector.class, "DelegationConnector", IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.connectorEClass, Connector.class, "Connector", IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getConnector_ParentStructure__Connector(), this.getComposedStructure(),
                this.getComposedStructure_Connectors__ComposedStructure(), "parentStructure__Connector", null, 1, 1,
                Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.composedStructureEClass, ComposedStructure.class, "ComposedStructure", IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getComposedStructure_AssemblyContexts__ComposedStructure(), this.getAssemblyContext(),
                this.getAssemblyContext_ParentStructure__AssemblyContext(), "assemblyContexts__ComposedStructure", null,
                0, -1, ComposedStructure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getComposedStructure_ResourceRequiredDelegationConnectors_ComposedStructure(),
                this.getResourceRequiredDelegationConnector(),
                this.getResourceRequiredDelegationConnector_ParentStructure_ResourceRequiredDelegationConnector(),
                "resourceRequiredDelegationConnectors_ComposedStructure", null, 0, -1, ComposedStructure.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getComposedStructure_EventChannel__ComposedStructure(), this.getEventChannel(),
                this.getEventChannel_ParentStructure__EventChannel(), "eventChannel__ComposedStructure", null, 0, -1,
                ComposedStructure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getComposedStructure_Connectors__ComposedStructure(), this.getConnector(),
                this.getConnector_ParentStructure__Connector(), "connectors__ComposedStructure", null, 0, -1,
                ComposedStructure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        EOperation op = this.addEOperation(this.composedStructureEClass, this.ecorePackage.getEBoolean(),
                "MultipleConnectorsConstraint", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        EGenericType g1 = this.createEGenericType(this.ecorePackage.getEMap());
        EGenericType g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = this.addEOperation(this.composedStructureEClass, this.ecorePackage.getEBoolean(),
                "MultipleConnectorsConstraintForAssemblyConnectors", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.resourceRequiredDelegationConnectorEClass, ResourceRequiredDelegationConnector.class,
                "ResourceRequiredDelegationConnector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(
                this.getResourceRequiredDelegationConnector_InnerResourceRequiredRole_ResourceRequiredDelegationConnector(),
                theEntityPackage.getResourceRequiredRole(), null,
                "innerResourceRequiredRole_ResourceRequiredDelegationConnector", null, 1, 1,
                ResourceRequiredDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(
                this.getResourceRequiredDelegationConnector_OuterResourceRequiredRole_ResourceRequiredDelegationConnector(),
                theEntityPackage.getResourceRequiredRole(), null,
                "outerResourceRequiredRole_ResourceRequiredDelegationConnector", null, 1, 1,
                ResourceRequiredDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(
                this.getResourceRequiredDelegationConnector_ParentStructure_ResourceRequiredDelegationConnector(),
                this.getComposedStructure(),
                this.getComposedStructure_ResourceRequiredDelegationConnectors_ComposedStructure(),
                "parentStructure_ResourceRequiredDelegationConnector", null, 1, 1,
                ResourceRequiredDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.eventChannelEClass, EventChannel.class, "EventChannel", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getEventChannel_EventGroup__EventChannel(), theRepositoryPackage.getEventGroup(), null,
                "eventGroup__EventChannel", null, 1, 1, EventChannel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getEventChannel_EventChannelSourceConnector__EventChannel(),
                this.getEventChannelSourceConnector(),
                this.getEventChannelSourceConnector_EventChannel__EventChannelSourceConnector(),
                "eventChannelSourceConnector__EventChannel", null, 0, -1, EventChannel.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);
        this.initEReference(this.getEventChannel_EventChannelSinkConnector__EventChannel(),
                this.getEventChannelSinkConnector(),
                this.getEventChannelSinkConnector_EventChannel__EventChannelSinkConnector(),
                "eventChannelSinkConnector__EventChannel", null, 0, -1, EventChannel.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getEventChannel_ParentStructure__EventChannel(), this.getComposedStructure(),
                this.getComposedStructure_EventChannel__ComposedStructure(), "parentStructure__EventChannel", null, 1,
                1, EventChannel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.eventChannelSourceConnectorEClass, EventChannelSourceConnector.class,
                "EventChannelSourceConnector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getEventChannelSourceConnector_SourceRole__EventChannelSourceRole(),
                theRepositoryPackage.getSourceRole(), null, "sourceRole__EventChannelSourceRole", null, 1, 1,
                EventChannelSourceConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getEventChannelSourceConnector_AssemblyContext__EventChannelSourceConnector(),
                this.getAssemblyContext(), null, "assemblyContext__EventChannelSourceConnector", null, 1, 1,
                EventChannelSourceConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getEventChannelSourceConnector_EventChannel__EventChannelSourceConnector(),
                this.getEventChannel(), this.getEventChannel_EventChannelSourceConnector__EventChannel(),
                "eventChannel__EventChannelSourceConnector", null, 1, 1, EventChannelSourceConnector.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.eventChannelSinkConnectorEClass, EventChannelSinkConnector.class,
                "EventChannelSinkConnector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getEventChannelSinkConnector_SinkRole__EventChannelSinkConnector(),
                theRepositoryPackage.getSinkRole(), null, "sinkRole__EventChannelSinkConnector", null, 1, 1,
                EventChannelSinkConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getEventChannelSinkConnector_FilterCondition__EventChannelSinkConnector(),
                theCorePackage.getPCMRandomVariable(),
                theCorePackage.getPCMRandomVariable_EventChannelSinkConnector__FilterCondition(),
                "filterCondition__EventChannelSinkConnector", null, 0, 1, EventChannelSinkConnector.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getEventChannelSinkConnector_AssemblyContext__EventChannelSinkConnector(),
                this.getAssemblyContext(), null, "assemblyContext__EventChannelSinkConnector", null, 1, 1,
                EventChannelSinkConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getEventChannelSinkConnector_EventChannel__EventChannelSinkConnector(),
                this.getEventChannel(), this.getEventChannel_EventChannelSinkConnector__EventChannel(),
                "eventChannel__EventChannelSinkConnector", null, 1, 1, EventChannelSinkConnector.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);

        this.initEClass(this.providedDelegationConnectorEClass, ProvidedDelegationConnector.class,
                "ProvidedDelegationConnector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getProvidedDelegationConnector_InnerProvidedRole_ProvidedDelegationConnector(),
                theRepositoryPackage.getOperationProvidedRole(), null, "innerProvidedRole_ProvidedDelegationConnector",
                null, 1, 1, ProvidedDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getProvidedDelegationConnector_OuterProvidedRole_ProvidedDelegationConnector(),
                theRepositoryPackage.getOperationProvidedRole(), null, "outerProvidedRole_ProvidedDelegationConnector",
                null, 1, 1, ProvidedDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getProvidedDelegationConnector_AssemblyContext_ProvidedDelegationConnector(),
                this.getAssemblyContext(), null, "assemblyContext_ProvidedDelegationConnector", null, 1, 1,
                ProvidedDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        op = this.addEOperation(this.providedDelegationConnectorEClass, this.ecorePackage.getEBoolean(),
                "ProvidedDelegationConnectorandtheconnectedComponentmustbepartofthesamecompositestructure", 0, 1,
                IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = this.addEOperation(this.providedDelegationConnectorEClass, this.ecorePackage.getEBoolean(),
                "ComponentOfAssemblyContextAndInnerRoleProvidingComponentNeedToBeTheSame", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.requiredDelegationConnectorEClass, RequiredDelegationConnector.class,
                "RequiredDelegationConnector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getRequiredDelegationConnector_InnerRequiredRole_RequiredDelegationConnector(),
                theRepositoryPackage.getOperationRequiredRole(), null, "innerRequiredRole_RequiredDelegationConnector",
                null, 1, 1, RequiredDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getRequiredDelegationConnector_OuterRequiredRole_RequiredDelegationConnector(),
                theRepositoryPackage.getOperationRequiredRole(), null, "outerRequiredRole_RequiredDelegationConnector",
                null, 1, 1, RequiredDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getRequiredDelegationConnector_AssemblyContext_RequiredDelegationConnector(),
                this.getAssemblyContext(), null, "assemblyContext_RequiredDelegationConnector", null, 1, 1,
                RequiredDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        op = this.addEOperation(this.requiredDelegationConnectorEClass, this.ecorePackage.getEBoolean(),
                "RequiredDelegationConnectorandtheconnectedComponentmustbepartofthesamecompositestructure", 0, 1,
                IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = this.addEOperation(this.requiredDelegationConnectorEClass, this.ecorePackage.getEBoolean(),
                "ComponentOfAssemblyContextAndInnerRoleRequiringComponentNeedToBeTheSame", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = this.addEOperation(this.requiredDelegationConnectorEClass, this.ecorePackage.getEBoolean(),
                "RequiringEntityOfOuterRequiredRoleMustBeTheSameAsTheParentOfTheRequiredDelegationConnector", 0, 1,
                IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.assemblyConnectorEClass, AssemblyConnector.class, "AssemblyConnector", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getAssemblyConnector_RequiringAssemblyContext_AssemblyConnector(),
                this.getAssemblyContext(), null, "requiringAssemblyContext_AssemblyConnector", null, 1, 1,
                AssemblyConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getAssemblyConnector_ProvidingAssemblyContext_AssemblyConnector(),
                this.getAssemblyContext(), null, "providingAssemblyContext_AssemblyConnector", null, 1, 1,
                AssemblyConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getAssemblyConnector_ProvidedRole_AssemblyConnector(),
                theRepositoryPackage.getOperationProvidedRole(), null, "providedRole_AssemblyConnector", null, 1, 1,
                AssemblyConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getAssemblyConnector_RequiredRole_AssemblyConnector(),
                theRepositoryPackage.getOperationRequiredRole(), null, "requiredRole_AssemblyConnector", null, 1, 1,
                AssemblyConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        op = this.addEOperation(this.assemblyConnectorEClass, this.ecorePackage.getEBoolean(),
                "AssemblyConnectorsReferencedProvidedRolesAndChildContextMustMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = this.addEOperation(this.assemblyConnectorEClass, this.ecorePackage.getEBoolean(),
                "AssemblyConnectorsReferencedRequiredRoleAndChildContextMustMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = this.addEOperation(this.assemblyConnectorEClass, this.ecorePackage.getEBoolean(),
                "AssemblyConnectorsReferencedInterfacesMustMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.assemblyEventConnectorEClass, AssemblyEventConnector.class, "AssemblyEventConnector",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getAssemblyEventConnector_SinkRole__AssemblyEventConnector(),
                theRepositoryPackage.getSinkRole(), null, "sinkRole__AssemblyEventConnector", null, 1, 1,
                AssemblyEventConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getAssemblyEventConnector_SourceRole__AssemblyEventConnector(),
                theRepositoryPackage.getSourceRole(), null, "sourceRole__AssemblyEventConnector", null, 1, 1,
                AssemblyEventConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getAssemblyEventConnector_SinkAssemblyContext__AssemblyEventConnector(),
                this.getAssemblyContext(), null, "sinkAssemblyContext__AssemblyEventConnector", null, 1, 1,
                AssemblyEventConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getAssemblyEventConnector_SourceAssemblyContext__AssemblyEventConnector(),
                this.getAssemblyContext(), null, "sourceAssemblyContext__AssemblyEventConnector", null, 1, 1,
                AssemblyEventConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getAssemblyEventConnector_FilterCondition__AssemblyEventConnector(),
                theCorePackage.getPCMRandomVariable(),
                theCorePackage.getPCMRandomVariable_AssemblyEventConnector__FilterCondition(),
                "filterCondition__AssemblyEventConnector", null, 0, 1, AssemblyEventConnector.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);

        this.initEClass(this.sourceDelegationConnectorEClass, SourceDelegationConnector.class,
                "SourceDelegationConnector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getSourceDelegationConnector_InnerSourceRole__SourceRole(),
                theRepositoryPackage.getSourceRole(), null, "innerSourceRole__SourceRole", null, 1, 1,
                SourceDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getSourceDelegationConnector_OuterSourceRole__SourceRole(),
                theRepositoryPackage.getSourceRole(), null, "outerSourceRole__SourceRole", null, 1, 1,
                SourceDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getSourceDelegationConnector_AssemblyContext__SourceDelegationConnector(),
                this.getAssemblyContext(), null, "assemblyContext__SourceDelegationConnector", null, 1, 1,
                SourceDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.sinkDelegationConnectorEClass, SinkDelegationConnector.class, "SinkDelegationConnector",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getSinkDelegationConnector_AssemblyContext__SinkDelegationConnector(),
                this.getAssemblyContext(), null, "assemblyContext__SinkDelegationConnector", null, 1, 1,
                SinkDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getSinkDelegationConnector_InnerSinkRole__SinkRole(),
                theRepositoryPackage.getSinkRole(), null, "innerSinkRole__SinkRole", null, 1, 1,
                SinkDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getSinkDelegationConnector_OuterSinkRole__SinkRole(),
                theRepositoryPackage.getSinkRole(), null, "outerSinkRole__SinkRole", null, 1, 1,
                SinkDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.assemblyInfrastructureConnectorEClass, AssemblyInfrastructureConnector.class,
                "AssemblyInfrastructureConnector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getAssemblyInfrastructureConnector_ProvidedRole__AssemblyInfrastructureConnector(),
                theRepositoryPackage.getInfrastructureProvidedRole(), null,
                "providedRole__AssemblyInfrastructureConnector", null, 1, 1, AssemblyInfrastructureConnector.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getAssemblyInfrastructureConnector_RequiredRole__AssemblyInfrastructureConnector(),
                theRepositoryPackage.getInfrastructureRequiredRole(), null,
                "requiredRole__AssemblyInfrastructureConnector", null, 1, 1, AssemblyInfrastructureConnector.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(
                this.getAssemblyInfrastructureConnector_ProvidingAssemblyContext__AssemblyInfrastructureConnector(),
                this.getAssemblyContext(), null, "providingAssemblyContext__AssemblyInfrastructureConnector", null, 1,
                1, AssemblyInfrastructureConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(
                this.getAssemblyInfrastructureConnector_RequiringAssemblyContext__AssemblyInfrastructureConnector(),
                this.getAssemblyContext(), null, "requiringAssemblyContext__AssemblyInfrastructureConnector", null, 0,
                1, AssemblyInfrastructureConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.providedInfrastructureDelegationConnectorEClass,
                ProvidedInfrastructureDelegationConnector.class, "ProvidedInfrastructureDelegationConnector",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(
                this.getProvidedInfrastructureDelegationConnector_InnerProvidedRole__ProvidedInfrastructureDelegationConnector(),
                theRepositoryPackage.getInfrastructureProvidedRole(), null,
                "innerProvidedRole__ProvidedInfrastructureDelegationConnector", null, 1, 1,
                ProvidedInfrastructureDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(
                this.getProvidedInfrastructureDelegationConnector_OuterProvidedRole__ProvidedInfrastructureDelegationConnector(),
                theRepositoryPackage.getInfrastructureProvidedRole(), null,
                "outerProvidedRole__ProvidedInfrastructureDelegationConnector", null, 1, 1,
                ProvidedInfrastructureDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(
                this.getProvidedInfrastructureDelegationConnector_AssemblyContext__ProvidedInfrastructureDelegationConnector(),
                this.getAssemblyContext(), null, "assemblyContext__ProvidedInfrastructureDelegationConnector", null, 1,
                1, ProvidedInfrastructureDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.requiredInfrastructureDelegationConnectorEClass,
                RequiredInfrastructureDelegationConnector.class, "RequiredInfrastructureDelegationConnector",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(
                this.getRequiredInfrastructureDelegationConnector_InnerRequiredRole__RequiredInfrastructureDelegationConnector(),
                theRepositoryPackage.getInfrastructureRequiredRole(), null,
                "innerRequiredRole__RequiredInfrastructureDelegationConnector", null, 1, 1,
                RequiredInfrastructureDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(
                this.getRequiredInfrastructureDelegationConnector_OuterRequiredRole__RequiredInfrastructureDelegationConnector(),
                theRepositoryPackage.getInfrastructureRequiredRole(), null,
                "outerRequiredRole__RequiredInfrastructureDelegationConnector", null, 1, 1,
                RequiredInfrastructureDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(
                this.getRequiredInfrastructureDelegationConnector_AssemblyContext__RequiredInfrastructureDelegationConnector(),
                this.getAssemblyContext(), null, "assemblyContext__RequiredInfrastructureDelegationConnector", null, 1,
                1, RequiredInfrastructureDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.requiredResourceDelegationConnectorEClass, RequiredResourceDelegationConnector.class,
                "RequiredResourceDelegationConnector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(
                this.getRequiredResourceDelegationConnector_AssemblyContext__RequiredResourceDelegationConnector(),
                this.getAssemblyContext(), null, "assemblyContext__RequiredResourceDelegationConnector", null, 1, 1,
                RequiredResourceDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(
                this.getRequiredResourceDelegationConnector_InnerRequiredRole__RequiredResourceDelegationConnector(),
                theEntityPackage.getResourceRequiredRole(), null,
                "innerRequiredRole__RequiredResourceDelegationConnector", null, 1, 1,
                RequiredResourceDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(
                this.getRequiredResourceDelegationConnector_OuterRequiredRole__RequiredResourceDelegationConnector(),
                theEntityPackage.getResourceRequiredRole(), null,
                "outerRequiredRole__RequiredResourceDelegationConnector", null, 1, 1,
                RequiredResourceDelegationConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.assemblyContextEClass, AssemblyContext.class, "AssemblyContext", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getAssemblyContext_ParentStructure__AssemblyContext(), this.getComposedStructure(),
                this.getComposedStructure_AssemblyContexts__ComposedStructure(), "parentStructure__AssemblyContext",
                null, 1, 1, AssemblyContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getAssemblyContext_EncapsulatedComponent__AssemblyContext(),
                theRepositoryPackage.getRepositoryComponent(), null, "encapsulatedComponent__AssemblyContext", null, 1,
                1, AssemblyContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getAssemblyContext_ConfigParameterUsages__AssemblyContext(),
                theParameterPackage.getVariableUsage(),
                theParameterPackage.getVariableUsage_AssemblyContext__VariableUsage(),
                "configParameterUsages__AssemblyContext", null, 0, -1, AssemblyContext.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);
    }

} // CompositionPackageImpl
