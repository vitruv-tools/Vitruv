/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.resourcetype.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.palladiosimulator.pcm.PcmPackage;
import org.palladiosimulator.pcm.allocation.AllocationPackage;
import org.palladiosimulator.pcm.allocation.impl.AllocationPackageImpl;
import org.palladiosimulator.pcm.core.CorePackage;
import org.palladiosimulator.pcm.core.composition.CompositionPackage;
import org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl;
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
import org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.resourcetype.ResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourcetypeFactory;
import org.palladiosimulator.pcm.resourcetype.ResourcetypePackage;
import org.palladiosimulator.pcm.resourcetype.SchedulingPolicy;
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
import de.uka.ipd.sdq.units.UnitsPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class ResourcetypePackageImpl extends EPackageImpl implements ResourcetypePackage {

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
    private EClass resourceSignatureEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass processingResourceTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass resourceTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass resourceRepositoryEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass schedulingPolicyEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass communicationLinkResourceTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass resourceInterfaceEClass = null;

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
     * @see org.palladiosimulator.pcm.resourcetype.ResourcetypePackage#eNS_URI
     * @see #init()
     * @generated
     */
    private ResourcetypePackageImpl() {
        super(eNS_URI, ResourcetypeFactory.eINSTANCE);
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
     * This method is used to initialize {@link ResourcetypePackage#eINSTANCE} when that field is
     * accessed. Clients should not invoke it directly. Instead, they should simply access that
     * field to obtain the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static ResourcetypePackage init() {
        if (isInited) {
            return (ResourcetypePackage) EPackage.Registry.INSTANCE.getEPackage(ResourcetypePackage.eNS_URI);
        }

        // Obtain or create and register package
        final ResourcetypePackageImpl theResourcetypePackage = (ResourcetypePackageImpl) (EPackage.Registry.INSTANCE
                .get(eNS_URI) instanceof ResourcetypePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
                        : new ResourcetypePackageImpl());

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
        final CompositionPackageImpl theCompositionPackage = (CompositionPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(CompositionPackage.eNS_URI) instanceof CompositionPackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(CompositionPackage.eNS_URI)
                        : CompositionPackage.eINSTANCE);
        final UsagemodelPackageImpl theUsagemodelPackage = (UsagemodelPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(UsagemodelPackage.eNS_URI) instanceof UsagemodelPackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(UsagemodelPackage.eNS_URI)
                        : UsagemodelPackage.eINSTANCE);
        final RepositoryPackageImpl theRepositoryPackage = (RepositoryPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(RepositoryPackage.eNS_URI) instanceof RepositoryPackageImpl
                        ? EPackage.Registry.INSTANCE.getEPackage(RepositoryPackage.eNS_URI)
                        : RepositoryPackage.eINSTANCE);
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
        theResourcetypePackage.createPackageContents();
        thePcmPackage.createPackageContents();
        theCorePackage.createPackageContents();
        theEntityPackage.createPackageContents();
        theCompositionPackage.createPackageContents();
        theUsagemodelPackage.createPackageContents();
        theRepositoryPackage.createPackageContents();
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
        theResourcetypePackage.initializePackageContents();
        thePcmPackage.initializePackageContents();
        theCorePackage.initializePackageContents();
        theEntityPackage.initializePackageContents();
        theCompositionPackage.initializePackageContents();
        theUsagemodelPackage.initializePackageContents();
        theRepositoryPackage.initializePackageContents();
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

        // Mark meta-data to indicate it can't be changed
        theResourcetypePackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(ResourcetypePackage.eNS_URI, theResourcetypePackage);
        return theResourcetypePackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getResourceSignature() {
        return this.resourceSignatureEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getResourceSignature_Parameter__ResourceSignature() {
        return (EReference) this.resourceSignatureEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EAttribute getResourceSignature_ResourceServiceId() {
        return (EAttribute) this.resourceSignatureEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getResourceSignature_ResourceInterface__ResourceSignature() {
        return (EReference) this.resourceSignatureEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getProcessingResourceType() {
        return this.processingResourceTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getProcessingResourceType_HardwareInducedFailureType__ProcessingResourceType() {
        return (EReference) this.processingResourceTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getResourceType() {
        return this.resourceTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getResourceType_ResourceRepository_ResourceType() {
        return (EReference) this.resourceTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getResourceRepository() {
        return this.resourceRepositoryEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getResourceRepository_ResourceInterfaces__ResourceRepository() {
        return (EReference) this.resourceRepositoryEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getResourceRepository_SchedulingPolicies__ResourceRepository() {
        return (EReference) this.resourceRepositoryEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getResourceRepository_AvailableResourceTypes_ResourceRepository() {
        return (EReference) this.resourceRepositoryEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getSchedulingPolicy() {
        return this.schedulingPolicyEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getSchedulingPolicy_ResourceRepository__SchedulingPolicy() {
        return (EReference) this.schedulingPolicyEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getCommunicationLinkResourceType() {
        return this.communicationLinkResourceTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getCommunicationLinkResourceType_NetworkInducedFailureType__CommunicationLinkResourceType() {
        return (EReference) this.communicationLinkResourceTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getResourceInterface() {
        return this.resourceInterfaceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getResourceInterface_ResourceRepository__ResourceInterface() {
        return (EReference) this.resourceInterfaceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getResourceInterface_ResourceSignatures__ResourceInterface() {
        return (EReference) this.resourceInterfaceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourcetypeFactory getResourcetypeFactory() {
        return (ResourcetypeFactory) this.getEFactoryInstance();
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
        this.resourceSignatureEClass = this.createEClass(RESOURCE_SIGNATURE);
        this.createEReference(this.resourceSignatureEClass, RESOURCE_SIGNATURE__PARAMETER_RESOURCE_SIGNATURE);
        this.createEAttribute(this.resourceSignatureEClass, RESOURCE_SIGNATURE__RESOURCE_SERVICE_ID);
        this.createEReference(this.resourceSignatureEClass, RESOURCE_SIGNATURE__RESOURCE_INTERFACE_RESOURCE_SIGNATURE);

        this.processingResourceTypeEClass = this.createEClass(PROCESSING_RESOURCE_TYPE);
        this.createEReference(this.processingResourceTypeEClass,
                PROCESSING_RESOURCE_TYPE__HARDWARE_INDUCED_FAILURE_TYPE_PROCESSING_RESOURCE_TYPE);

        this.resourceTypeEClass = this.createEClass(RESOURCE_TYPE);
        this.createEReference(this.resourceTypeEClass, RESOURCE_TYPE__RESOURCE_REPOSITORY_RESOURCE_TYPE);

        this.resourceRepositoryEClass = this.createEClass(RESOURCE_REPOSITORY);
        this.createEReference(this.resourceRepositoryEClass,
                RESOURCE_REPOSITORY__RESOURCE_INTERFACES_RESOURCE_REPOSITORY);
        this.createEReference(this.resourceRepositoryEClass,
                RESOURCE_REPOSITORY__SCHEDULING_POLICIES_RESOURCE_REPOSITORY);
        this.createEReference(this.resourceRepositoryEClass,
                RESOURCE_REPOSITORY__AVAILABLE_RESOURCE_TYPES_RESOURCE_REPOSITORY);

        this.schedulingPolicyEClass = this.createEClass(SCHEDULING_POLICY);
        this.createEReference(this.schedulingPolicyEClass, SCHEDULING_POLICY__RESOURCE_REPOSITORY_SCHEDULING_POLICY);

        this.communicationLinkResourceTypeEClass = this.createEClass(COMMUNICATION_LINK_RESOURCE_TYPE);
        this.createEReference(this.communicationLinkResourceTypeEClass,
                COMMUNICATION_LINK_RESOURCE_TYPE__NETWORK_INDUCED_FAILURE_TYPE_COMMUNICATION_LINK_RESOURCE_TYPE);

        this.resourceInterfaceEClass = this.createEClass(RESOURCE_INTERFACE);
        this.createEReference(this.resourceInterfaceEClass, RESOURCE_INTERFACE__RESOURCE_REPOSITORY_RESOURCE_INTERFACE);
        this.createEReference(this.resourceInterfaceEClass, RESOURCE_INTERFACE__RESOURCE_SIGNATURES_RESOURCE_INTERFACE);
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
        final ReliabilityPackage theReliabilityPackage = (ReliabilityPackage) EPackage.Registry.INSTANCE
                .getEPackage(ReliabilityPackage.eNS_URI);
        final UnitsPackage theUnitsPackage = (UnitsPackage) EPackage.Registry.INSTANCE
                .getEPackage(UnitsPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.resourceSignatureEClass.getESuperTypes().add(theEntityPackage.getEntity());
        this.processingResourceTypeEClass.getESuperTypes().add(this.getResourceType());
        this.resourceTypeEClass.getESuperTypes().add(theEntityPackage.getEntity());
        this.resourceTypeEClass.getESuperTypes().add(theUnitsPackage.getUnitCarryingElement());
        this.resourceTypeEClass.getESuperTypes().add(theEntityPackage.getResourceInterfaceProvidingEntity());
        this.schedulingPolicyEClass.getESuperTypes().add(theEntityPackage.getEntity());
        this.communicationLinkResourceTypeEClass.getESuperTypes().add(this.getResourceType());
        this.resourceInterfaceEClass.getESuperTypes().add(theEntityPackage.getEntity());

        // Initialize classes and features; add operations and parameters
        this.initEClass(this.resourceSignatureEClass, ResourceSignature.class, "ResourceSignature", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getResourceSignature_Parameter__ResourceSignature(),
                theRepositoryPackage.getParameter(), theRepositoryPackage.getParameter_ResourceSignature__Parameter(),
                "parameter__ResourceSignature", null, 0, -1, ResourceSignature.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEAttribute(this.getResourceSignature_ResourceServiceId(), this.ecorePackage.getEInt(),
                "resourceServiceId", null, 1, 1, ResourceSignature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getResourceSignature_ResourceInterface__ResourceSignature(),
                this.getResourceInterface(), this.getResourceInterface_ResourceSignatures__ResourceInterface(),
                "resourceInterface__ResourceSignature", null, 1, 1, ResourceSignature.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);

        this.initEClass(this.processingResourceTypeEClass, ProcessingResourceType.class, "ProcessingResourceType",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getProcessingResourceType_HardwareInducedFailureType__ProcessingResourceType(),
                theReliabilityPackage.getHardwareInducedFailureType(),
                theReliabilityPackage
                        .getHardwareInducedFailureType_ProcessingResourceType__HardwareInducedFailureType(),
                "hardwareInducedFailureType__ProcessingResourceType", null, 0, 1, ProcessingResourceType.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.resourceTypeEClass, ResourceType.class, "ResourceType", IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getResourceType_ResourceRepository_ResourceType(), this.getResourceRepository(),
                this.getResourceRepository_AvailableResourceTypes_ResourceRepository(),
                "resourceRepository_ResourceType", null, 1, 1, ResourceType.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.resourceRepositoryEClass, ResourceRepository.class, "ResourceRepository", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getResourceRepository_ResourceInterfaces__ResourceRepository(),
                this.getResourceInterface(), this.getResourceInterface_ResourceRepository__ResourceInterface(),
                "resourceInterfaces__ResourceRepository", null, 0, -1, ResourceRepository.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);
        this.initEReference(this.getResourceRepository_SchedulingPolicies__ResourceRepository(),
                this.getSchedulingPolicy(), this.getSchedulingPolicy_ResourceRepository__SchedulingPolicy(),
                "schedulingPolicies__ResourceRepository", null, 0, -1, ResourceRepository.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);
        this.initEReference(this.getResourceRepository_AvailableResourceTypes_ResourceRepository(),
                this.getResourceType(), this.getResourceType_ResourceRepository_ResourceType(),
                "availableResourceTypes_ResourceRepository", null, 0, -1, ResourceRepository.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);

        this.initEClass(this.schedulingPolicyEClass, SchedulingPolicy.class, "SchedulingPolicy", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getSchedulingPolicy_ResourceRepository__SchedulingPolicy(),
                this.getResourceRepository(), this.getResourceRepository_SchedulingPolicies__ResourceRepository(),
                "resourceRepository__SchedulingPolicy", null, 1, 1, SchedulingPolicy.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.communicationLinkResourceTypeEClass, CommunicationLinkResourceType.class,
                "CommunicationLinkResourceType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(
                this.getCommunicationLinkResourceType_NetworkInducedFailureType__CommunicationLinkResourceType(),
                theReliabilityPackage.getNetworkInducedFailureType(),
                theReliabilityPackage
                        .getNetworkInducedFailureType_CommunicationLinkResourceType__NetworkInducedFailureType(),
                "networkInducedFailureType__CommunicationLinkResourceType", null, 0, 1,
                CommunicationLinkResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.resourceInterfaceEClass, ResourceInterface.class, "ResourceInterface", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getResourceInterface_ResourceRepository__ResourceInterface(),
                this.getResourceRepository(), this.getResourceRepository_ResourceInterfaces__ResourceRepository(),
                "resourceRepository__ResourceInterface", null, 1, 1, ResourceInterface.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);
        this.initEReference(this.getResourceInterface_ResourceSignatures__ResourceInterface(),
                this.getResourceSignature(), this.getResourceSignature_ResourceInterface__ResourceSignature(),
                "resourceSignatures__ResourceInterface", null, 0, -1, ResourceInterface.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);
    }

} // ResourcetypePackageImpl
