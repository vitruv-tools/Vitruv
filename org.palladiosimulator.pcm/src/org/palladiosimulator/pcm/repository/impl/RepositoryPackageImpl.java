/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.ComponentType;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.ExceptionType;
import org.palladiosimulator.pcm.repository.ImplementationComponentType;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidesComponentType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.repository.RequiredCharacterisation;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.repository.Role;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;
import org.palladiosimulator.pcm.repository.util.RepositoryValidator;
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
public class RepositoryPackageImpl extends EPackageImpl implements RepositoryPackage {

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
    private EClass passiveResourceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass basicComponentEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass implementationComponentTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass repositoryComponentEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass providedRoleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass parameterEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass dataTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass repositoryEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass interfaceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass requiredCharacterisationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass eventGroupEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass eventTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass signatureEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass exceptionTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass infrastructureSignatureEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass infrastructureInterfaceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass infrastructureRequiredRoleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass requiredRoleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass operationSignatureEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass operationInterfaceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass operationRequiredRoleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass sourceRoleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass sinkRoleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass operationProvidedRoleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass infrastructureProvidedRoleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass completeComponentTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass providesComponentTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass compositeComponentEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass primitiveDataTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass collectionDataTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass compositeDataTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass innerDeclarationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass roleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum parameterModifierEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum componentTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum primitiveTypeEnumEEnum = null;

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
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private RepositoryPackageImpl() {
        super(eNS_URI, RepositoryFactory.eINSTANCE);
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
     * This method is used to initialize {@link RepositoryPackage#eINSTANCE} when that field is
     * accessed. Clients should not invoke it directly. Instead, they should simply access that
     * field to obtain the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static RepositoryPackage init() {
        if (isInited) {
            return (RepositoryPackage) EPackage.Registry.INSTANCE.getEPackage(RepositoryPackage.eNS_URI);
        }

        // Obtain or create and register package
        final RepositoryPackageImpl theRepositoryPackage = (RepositoryPackageImpl) (EPackage.Registry.INSTANCE
                .get(eNS_URI) instanceof RepositoryPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
                        : new RepositoryPackageImpl());

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
        theRepositoryPackage.createPackageContents();
        thePcmPackage.createPackageContents();
        theCorePackage.createPackageContents();
        theEntityPackage.createPackageContents();
        theCompositionPackage.createPackageContents();
        theUsagemodelPackage.createPackageContents();
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
        theRepositoryPackage.initializePackageContents();
        thePcmPackage.initializePackageContents();
        theCorePackage.initializePackageContents();
        theEntityPackage.initializePackageContents();
        theCompositionPackage.initializePackageContents();
        theUsagemodelPackage.initializePackageContents();
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
        EValidator.Registry.INSTANCE.put(theRepositoryPackage, new EValidator.Descriptor() {

            @Override
            public EValidator getEValidator() {
                return RepositoryValidator.INSTANCE;
            }
        });

        // Mark meta-data to indicate it can't be changed
        theRepositoryPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(RepositoryPackage.eNS_URI, theRepositoryPackage);
        return theRepositoryPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getPassiveResource() {
        return this.passiveResourceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPassiveResource_Capacity_PassiveResource() {
        return (EReference) this.passiveResourceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPassiveResource_BasicComponent_PassiveResource() {
        return (EReference) this.passiveResourceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPassiveResource_ResourceTimeoutFailureType__PassiveResource() {
        return (EReference) this.passiveResourceEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getBasicComponent() {
        return this.basicComponentEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getBasicComponent_ServiceEffectSpecifications__BasicComponent() {
        return (EReference) this.basicComponentEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getBasicComponent_PassiveResource_BasicComponent() {
        return (EReference) this.basicComponentEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getImplementationComponentType() {
        return this.implementationComponentTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getImplementationComponentType_ParentCompleteComponentTypes() {
        return (EReference) this.implementationComponentTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getImplementationComponentType_ComponentParameterUsage_ImplementationComponentType() {
        return (EReference) this.implementationComponentTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getImplementationComponentType_ComponentType() {
        return (EAttribute) this.implementationComponentTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRepositoryComponent() {
        return this.repositoryComponentEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRepositoryComponent_Repository__RepositoryComponent() {
        return (EReference) this.repositoryComponentEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getProvidedRole() {
        return this.providedRoleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getProvidedRole_ProvidingEntity_ProvidedRole() {
        return (EReference) this.providedRoleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getParameter() {
        return this.parameterEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getParameter_DataType__Parameter() {
        return (EReference) this.parameterEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getParameter_InfrastructureSignature__Parameter() {
        return (EReference) this.parameterEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getParameter_OperationSignature__Parameter() {
        return (EReference) this.parameterEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getParameter_EventType__Parameter() {
        return (EReference) this.parameterEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getParameter_ParameterName() {
        return (EAttribute) this.parameterEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getParameter_Modifier__Parameter() {
        return (EAttribute) this.parameterEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getParameter_ResourceSignature__Parameter() {
        return (EReference) this.parameterEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDataType() {
        return this.dataTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDataType_Repository__DataType() {
        return (EReference) this.dataTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRepository() {
        return this.repositoryEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRepository_RepositoryDescription() {
        return (EAttribute) this.repositoryEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRepository_Components__Repository() {
        return (EReference) this.repositoryEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRepository_Interfaces__Repository() {
        return (EReference) this.repositoryEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRepository_FailureTypes__Repository() {
        return (EReference) this.repositoryEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRepository_DataTypes__Repository() {
        return (EReference) this.repositoryEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getInterface() {
        return this.interfaceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInterface_ParentInterfaces__Interface() {
        return (EReference) this.interfaceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInterface_Protocols__Interface() {
        return (EReference) this.interfaceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInterface_RequiredCharacterisations() {
        return (EReference) this.interfaceEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInterface_Repository__Interface() {
        return (EReference) this.interfaceEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRequiredCharacterisation() {
        return this.requiredCharacterisationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRequiredCharacterisation_Type() {
        return (EAttribute) this.requiredCharacterisationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRequiredCharacterisation_Parameter() {
        return (EReference) this.requiredCharacterisationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRequiredCharacterisation_Interface_RequiredCharacterisation() {
        return (EReference) this.requiredCharacterisationEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEventGroup() {
        return this.eventGroupEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEventGroup_EventTypes__EventGroup() {
        return (EReference) this.eventGroupEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEventType() {
        return this.eventTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEventType_Parameter__EventType() {
        return (EReference) this.eventTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEventType_EventGroup__EventType() {
        return (EReference) this.eventTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getSignature() {
        return this.signatureEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSignature_Exceptions__Signature() {
        return (EReference) this.signatureEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSignature_FailureType() {
        return (EReference) this.signatureEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getExceptionType() {
        return this.exceptionTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getExceptionType_ExceptionName() {
        return (EAttribute) this.exceptionTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getExceptionType_ExceptionMessage() {
        return (EAttribute) this.exceptionTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getInfrastructureSignature() {
        return this.infrastructureSignatureEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInfrastructureSignature_Parameters__InfrastructureSignature() {
        return (EReference) this.infrastructureSignatureEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInfrastructureSignature_InfrastructureInterface__InfrastructureSignature() {
        return (EReference) this.infrastructureSignatureEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getInfrastructureInterface() {
        return this.infrastructureInterfaceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInfrastructureInterface_InfrastructureSignatures__InfrastructureInterface() {
        return (EReference) this.infrastructureInterfaceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getInfrastructureRequiredRole() {
        return this.infrastructureRequiredRoleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInfrastructureRequiredRole_RequiredInterface__InfrastructureRequiredRole() {
        return (EReference) this.infrastructureRequiredRoleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRequiredRole() {
        return this.requiredRoleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRequiredRole_RequiringEntity_RequiredRole() {
        return (EReference) this.requiredRoleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getOperationSignature() {
        return this.operationSignatureEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getOperationSignature_Interface__OperationSignature() {
        return (EReference) this.operationSignatureEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getOperationSignature_Parameters__OperationSignature() {
        return (EReference) this.operationSignatureEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getOperationSignature_ReturnType__OperationSignature() {
        return (EReference) this.operationSignatureEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getOperationInterface() {
        return this.operationInterfaceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getOperationInterface_Signatures__OperationInterface() {
        return (EReference) this.operationInterfaceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getOperationRequiredRole() {
        return this.operationRequiredRoleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getOperationRequiredRole_RequiredInterface__OperationRequiredRole() {
        return (EReference) this.operationRequiredRoleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getSourceRole() {
        return this.sourceRoleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSourceRole_EventGroup__SourceRole() {
        return (EReference) this.sourceRoleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getSinkRole() {
        return this.sinkRoleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSinkRole_EventGroup__SinkRole() {
        return (EReference) this.sinkRoleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getOperationProvidedRole() {
        return this.operationProvidedRoleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getOperationProvidedRole_ProvidedInterface__OperationProvidedRole() {
        return (EReference) this.operationProvidedRoleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getInfrastructureProvidedRole() {
        return this.infrastructureProvidedRoleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInfrastructureProvidedRole_ProvidedInterface__InfrastructureProvidedRole() {
        return (EReference) this.infrastructureProvidedRoleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getCompleteComponentType() {
        return this.completeComponentTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCompleteComponentType_ParentProvidesComponentTypes() {
        return (EReference) this.completeComponentTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getProvidesComponentType() {
        return this.providesComponentTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getCompositeComponent() {
        return this.compositeComponentEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getPrimitiveDataType() {
        return this.primitiveDataTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPrimitiveDataType_Type() {
        return (EAttribute) this.primitiveDataTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getCollectionDataType() {
        return this.collectionDataTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCollectionDataType_InnerType_CollectionDataType() {
        return (EReference) this.collectionDataTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getCompositeDataType() {
        return this.compositeDataTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCompositeDataType_ParentType_CompositeDataType() {
        return (EReference) this.compositeDataTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCompositeDataType_InnerDeclaration_CompositeDataType() {
        return (EReference) this.compositeDataTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getInnerDeclaration() {
        return this.innerDeclarationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInnerDeclaration_Datatype_InnerDeclaration() {
        return (EReference) this.innerDeclarationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInnerDeclaration_CompositeDataType_InnerDeclaration() {
        return (EReference) this.innerDeclarationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRole() {
        return this.roleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getParameterModifier() {
        return this.parameterModifierEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getComponentType() {
        return this.componentTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getPrimitiveTypeEnum() {
        return this.primitiveTypeEnumEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public RepositoryFactory getRepositoryFactory() {
        return (RepositoryFactory) this.getEFactoryInstance();
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
        this.passiveResourceEClass = this.createEClass(PASSIVE_RESOURCE);
        this.createEReference(this.passiveResourceEClass, PASSIVE_RESOURCE__CAPACITY_PASSIVE_RESOURCE);
        this.createEReference(this.passiveResourceEClass, PASSIVE_RESOURCE__BASIC_COMPONENT_PASSIVE_RESOURCE);
        this.createEReference(this.passiveResourceEClass,
                PASSIVE_RESOURCE__RESOURCE_TIMEOUT_FAILURE_TYPE_PASSIVE_RESOURCE);

        this.basicComponentEClass = this.createEClass(BASIC_COMPONENT);
        this.createEReference(this.basicComponentEClass,
                BASIC_COMPONENT__SERVICE_EFFECT_SPECIFICATIONS_BASIC_COMPONENT);
        this.createEReference(this.basicComponentEClass, BASIC_COMPONENT__PASSIVE_RESOURCE_BASIC_COMPONENT);

        this.implementationComponentTypeEClass = this.createEClass(IMPLEMENTATION_COMPONENT_TYPE);
        this.createEReference(this.implementationComponentTypeEClass,
                IMPLEMENTATION_COMPONENT_TYPE__PARENT_COMPLETE_COMPONENT_TYPES);
        this.createEReference(this.implementationComponentTypeEClass,
                IMPLEMENTATION_COMPONENT_TYPE__COMPONENT_PARAMETER_USAGE_IMPLEMENTATION_COMPONENT_TYPE);
        this.createEAttribute(this.implementationComponentTypeEClass, IMPLEMENTATION_COMPONENT_TYPE__COMPONENT_TYPE);

        this.repositoryComponentEClass = this.createEClass(REPOSITORY_COMPONENT);
        this.createEReference(this.repositoryComponentEClass, REPOSITORY_COMPONENT__REPOSITORY_REPOSITORY_COMPONENT);

        this.providedRoleEClass = this.createEClass(PROVIDED_ROLE);
        this.createEReference(this.providedRoleEClass, PROVIDED_ROLE__PROVIDING_ENTITY_PROVIDED_ROLE);

        this.parameterEClass = this.createEClass(PARAMETER);
        this.createEReference(this.parameterEClass, PARAMETER__DATA_TYPE_PARAMETER);
        this.createEReference(this.parameterEClass, PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER);
        this.createEReference(this.parameterEClass, PARAMETER__OPERATION_SIGNATURE_PARAMETER);
        this.createEReference(this.parameterEClass, PARAMETER__EVENT_TYPE_PARAMETER);
        this.createEAttribute(this.parameterEClass, PARAMETER__PARAMETER_NAME);
        this.createEAttribute(this.parameterEClass, PARAMETER__MODIFIER_PARAMETER);
        this.createEReference(this.parameterEClass, PARAMETER__RESOURCE_SIGNATURE_PARAMETER);

        this.dataTypeEClass = this.createEClass(DATA_TYPE);
        this.createEReference(this.dataTypeEClass, DATA_TYPE__REPOSITORY_DATA_TYPE);

        this.repositoryEClass = this.createEClass(REPOSITORY);
        this.createEAttribute(this.repositoryEClass, REPOSITORY__REPOSITORY_DESCRIPTION);
        this.createEReference(this.repositoryEClass, REPOSITORY__COMPONENTS_REPOSITORY);
        this.createEReference(this.repositoryEClass, REPOSITORY__INTERFACES_REPOSITORY);
        this.createEReference(this.repositoryEClass, REPOSITORY__FAILURE_TYPES_REPOSITORY);
        this.createEReference(this.repositoryEClass, REPOSITORY__DATA_TYPES_REPOSITORY);

        this.interfaceEClass = this.createEClass(INTERFACE);
        this.createEReference(this.interfaceEClass, INTERFACE__PARENT_INTERFACES_INTERFACE);
        this.createEReference(this.interfaceEClass, INTERFACE__PROTOCOLS_INTERFACE);
        this.createEReference(this.interfaceEClass, INTERFACE__REQUIRED_CHARACTERISATIONS);
        this.createEReference(this.interfaceEClass, INTERFACE__REPOSITORY_INTERFACE);

        this.requiredCharacterisationEClass = this.createEClass(REQUIRED_CHARACTERISATION);
        this.createEAttribute(this.requiredCharacterisationEClass, REQUIRED_CHARACTERISATION__TYPE);
        this.createEReference(this.requiredCharacterisationEClass, REQUIRED_CHARACTERISATION__PARAMETER);
        this.createEReference(this.requiredCharacterisationEClass,
                REQUIRED_CHARACTERISATION__INTERFACE_REQUIRED_CHARACTERISATION);

        this.eventGroupEClass = this.createEClass(EVENT_GROUP);
        this.createEReference(this.eventGroupEClass, EVENT_GROUP__EVENT_TYPES_EVENT_GROUP);

        this.eventTypeEClass = this.createEClass(EVENT_TYPE);
        this.createEReference(this.eventTypeEClass, EVENT_TYPE__PARAMETER_EVENT_TYPE);
        this.createEReference(this.eventTypeEClass, EVENT_TYPE__EVENT_GROUP_EVENT_TYPE);

        this.signatureEClass = this.createEClass(SIGNATURE);
        this.createEReference(this.signatureEClass, SIGNATURE__EXCEPTIONS_SIGNATURE);
        this.createEReference(this.signatureEClass, SIGNATURE__FAILURE_TYPE);

        this.exceptionTypeEClass = this.createEClass(EXCEPTION_TYPE);
        this.createEAttribute(this.exceptionTypeEClass, EXCEPTION_TYPE__EXCEPTION_NAME);
        this.createEAttribute(this.exceptionTypeEClass, EXCEPTION_TYPE__EXCEPTION_MESSAGE);

        this.infrastructureSignatureEClass = this.createEClass(INFRASTRUCTURE_SIGNATURE);
        this.createEReference(this.infrastructureSignatureEClass,
                INFRASTRUCTURE_SIGNATURE__PARAMETERS_INFRASTRUCTURE_SIGNATURE);
        this.createEReference(this.infrastructureSignatureEClass,
                INFRASTRUCTURE_SIGNATURE__INFRASTRUCTURE_INTERFACE_INFRASTRUCTURE_SIGNATURE);

        this.infrastructureInterfaceEClass = this.createEClass(INFRASTRUCTURE_INTERFACE);
        this.createEReference(this.infrastructureInterfaceEClass,
                INFRASTRUCTURE_INTERFACE__INFRASTRUCTURE_SIGNATURES_INFRASTRUCTURE_INTERFACE);

        this.infrastructureRequiredRoleEClass = this.createEClass(INFRASTRUCTURE_REQUIRED_ROLE);
        this.createEReference(this.infrastructureRequiredRoleEClass,
                INFRASTRUCTURE_REQUIRED_ROLE__REQUIRED_INTERFACE_INFRASTRUCTURE_REQUIRED_ROLE);

        this.requiredRoleEClass = this.createEClass(REQUIRED_ROLE);
        this.createEReference(this.requiredRoleEClass, REQUIRED_ROLE__REQUIRING_ENTITY_REQUIRED_ROLE);

        this.operationSignatureEClass = this.createEClass(OPERATION_SIGNATURE);
        this.createEReference(this.operationSignatureEClass, OPERATION_SIGNATURE__INTERFACE_OPERATION_SIGNATURE);
        this.createEReference(this.operationSignatureEClass, OPERATION_SIGNATURE__PARAMETERS_OPERATION_SIGNATURE);
        this.createEReference(this.operationSignatureEClass, OPERATION_SIGNATURE__RETURN_TYPE_OPERATION_SIGNATURE);

        this.operationInterfaceEClass = this.createEClass(OPERATION_INTERFACE);
        this.createEReference(this.operationInterfaceEClass, OPERATION_INTERFACE__SIGNATURES_OPERATION_INTERFACE);

        this.operationRequiredRoleEClass = this.createEClass(OPERATION_REQUIRED_ROLE);
        this.createEReference(this.operationRequiredRoleEClass,
                OPERATION_REQUIRED_ROLE__REQUIRED_INTERFACE_OPERATION_REQUIRED_ROLE);

        this.sourceRoleEClass = this.createEClass(SOURCE_ROLE);
        this.createEReference(this.sourceRoleEClass, SOURCE_ROLE__EVENT_GROUP_SOURCE_ROLE);

        this.sinkRoleEClass = this.createEClass(SINK_ROLE);
        this.createEReference(this.sinkRoleEClass, SINK_ROLE__EVENT_GROUP_SINK_ROLE);

        this.operationProvidedRoleEClass = this.createEClass(OPERATION_PROVIDED_ROLE);
        this.createEReference(this.operationProvidedRoleEClass,
                OPERATION_PROVIDED_ROLE__PROVIDED_INTERFACE_OPERATION_PROVIDED_ROLE);

        this.infrastructureProvidedRoleEClass = this.createEClass(INFRASTRUCTURE_PROVIDED_ROLE);
        this.createEReference(this.infrastructureProvidedRoleEClass,
                INFRASTRUCTURE_PROVIDED_ROLE__PROVIDED_INTERFACE_INFRASTRUCTURE_PROVIDED_ROLE);

        this.completeComponentTypeEClass = this.createEClass(COMPLETE_COMPONENT_TYPE);
        this.createEReference(this.completeComponentTypeEClass,
                COMPLETE_COMPONENT_TYPE__PARENT_PROVIDES_COMPONENT_TYPES);

        this.providesComponentTypeEClass = this.createEClass(PROVIDES_COMPONENT_TYPE);

        this.compositeComponentEClass = this.createEClass(COMPOSITE_COMPONENT);

        this.primitiveDataTypeEClass = this.createEClass(PRIMITIVE_DATA_TYPE);
        this.createEAttribute(this.primitiveDataTypeEClass, PRIMITIVE_DATA_TYPE__TYPE);

        this.collectionDataTypeEClass = this.createEClass(COLLECTION_DATA_TYPE);
        this.createEReference(this.collectionDataTypeEClass, COLLECTION_DATA_TYPE__INNER_TYPE_COLLECTION_DATA_TYPE);

        this.compositeDataTypeEClass = this.createEClass(COMPOSITE_DATA_TYPE);
        this.createEReference(this.compositeDataTypeEClass, COMPOSITE_DATA_TYPE__PARENT_TYPE_COMPOSITE_DATA_TYPE);
        this.createEReference(this.compositeDataTypeEClass, COMPOSITE_DATA_TYPE__INNER_DECLARATION_COMPOSITE_DATA_TYPE);

        this.innerDeclarationEClass = this.createEClass(INNER_DECLARATION);
        this.createEReference(this.innerDeclarationEClass, INNER_DECLARATION__DATATYPE_INNER_DECLARATION);
        this.createEReference(this.innerDeclarationEClass, INNER_DECLARATION__COMPOSITE_DATA_TYPE_INNER_DECLARATION);

        this.roleEClass = this.createEClass(ROLE);

        // Create enums
        this.parameterModifierEEnum = this.createEEnum(PARAMETER_MODIFIER);
        this.componentTypeEEnum = this.createEEnum(COMPONENT_TYPE);
        this.primitiveTypeEnumEEnum = this.createEEnum(PRIMITIVE_TYPE_ENUM);
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
        final CorePackage theCorePackage = (CorePackage) EPackage.Registry.INSTANCE.getEPackage(CorePackage.eNS_URI);
        final ReliabilityPackage theReliabilityPackage = (ReliabilityPackage) EPackage.Registry.INSTANCE
                .getEPackage(ReliabilityPackage.eNS_URI);
        final SeffPackage theSeffPackage = (SeffPackage) EPackage.Registry.INSTANCE.getEPackage(SeffPackage.eNS_URI);
        final ParameterPackage theParameterPackage = (ParameterPackage) EPackage.Registry.INSTANCE
                .getEPackage(ParameterPackage.eNS_URI);
        final ResourcetypePackage theResourcetypePackage = (ResourcetypePackage) EPackage.Registry.INSTANCE
                .getEPackage(ResourcetypePackage.eNS_URI);
        final ProtocolPackage theProtocolPackage = (ProtocolPackage) EPackage.Registry.INSTANCE
                .getEPackage(ProtocolPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.passiveResourceEClass.getESuperTypes().add(theEntityPackage.getEntity());
        this.basicComponentEClass.getESuperTypes().add(this.getImplementationComponentType());
        this.implementationComponentTypeEClass.getESuperTypes().add(this.getRepositoryComponent());
        this.repositoryComponentEClass.getESuperTypes().add(theEntityPackage.getInterfaceProvidingRequiringEntity());
        this.providedRoleEClass.getESuperTypes().add(this.getRole());
        this.repositoryEClass.getESuperTypes().add(theEntityPackage.getEntity());
        this.interfaceEClass.getESuperTypes().add(theEntityPackage.getEntity());
        this.eventGroupEClass.getESuperTypes().add(this.getInterface());
        this.eventTypeEClass.getESuperTypes().add(this.getSignature());
        this.signatureEClass.getESuperTypes().add(theEntityPackage.getEntity());
        this.infrastructureSignatureEClass.getESuperTypes().add(this.getSignature());
        this.infrastructureInterfaceEClass.getESuperTypes().add(this.getInterface());
        this.infrastructureRequiredRoleEClass.getESuperTypes().add(this.getRequiredRole());
        this.requiredRoleEClass.getESuperTypes().add(this.getRole());
        this.operationSignatureEClass.getESuperTypes().add(this.getSignature());
        this.operationInterfaceEClass.getESuperTypes().add(this.getInterface());
        this.operationRequiredRoleEClass.getESuperTypes().add(this.getRequiredRole());
        this.sourceRoleEClass.getESuperTypes().add(this.getRequiredRole());
        this.sinkRoleEClass.getESuperTypes().add(this.getProvidedRole());
        this.operationProvidedRoleEClass.getESuperTypes().add(this.getProvidedRole());
        this.infrastructureProvidedRoleEClass.getESuperTypes().add(this.getProvidedRole());
        this.completeComponentTypeEClass.getESuperTypes().add(this.getRepositoryComponent());
        this.providesComponentTypeEClass.getESuperTypes().add(this.getRepositoryComponent());
        this.compositeComponentEClass.getESuperTypes().add(theEntityPackage.getComposedProvidingRequiringEntity());
        this.compositeComponentEClass.getESuperTypes().add(this.getImplementationComponentType());
        this.primitiveDataTypeEClass.getESuperTypes().add(this.getDataType());
        this.collectionDataTypeEClass.getESuperTypes().add(theEntityPackage.getEntity());
        this.collectionDataTypeEClass.getESuperTypes().add(this.getDataType());
        this.compositeDataTypeEClass.getESuperTypes().add(theEntityPackage.getEntity());
        this.compositeDataTypeEClass.getESuperTypes().add(this.getDataType());
        this.innerDeclarationEClass.getESuperTypes().add(theEntityPackage.getNamedElement());
        this.roleEClass.getESuperTypes().add(theEntityPackage.getEntity());

        // Initialize classes and features; add operations and parameters
        this.initEClass(this.passiveResourceEClass, PassiveResource.class, "PassiveResource", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getPassiveResource_Capacity_PassiveResource(), theCorePackage.getPCMRandomVariable(),
                theCorePackage.getPCMRandomVariable_PassiveResource_capacity_PCMRandomVariable(),
                "capacity_PassiveResource", null, 1, 1, PassiveResource.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getPassiveResource_BasicComponent_PassiveResource(), this.getBasicComponent(),
                this.getBasicComponent_PassiveResource_BasicComponent(), "basicComponent_PassiveResource", null, 1, 1,
                PassiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getPassiveResource_ResourceTimeoutFailureType__PassiveResource(),
                theReliabilityPackage.getResourceTimeoutFailureType(),
                theReliabilityPackage.getResourceTimeoutFailureType_PassiveResource__ResourceTimeoutFailureType(),
                "resourceTimeoutFailureType__PassiveResource", null, 0, 1, PassiveResource.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);

        this.initEClass(this.basicComponentEClass, BasicComponent.class, "BasicComponent", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getBasicComponent_ServiceEffectSpecifications__BasicComponent(),
                theSeffPackage.getServiceEffectSpecification(),
                theSeffPackage.getServiceEffectSpecification_BasicComponent_ServiceEffectSpecification(),
                "serviceEffectSpecifications__BasicComponent", null, 0, -1, BasicComponent.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);
        this.initEReference(this.getBasicComponent_PassiveResource_BasicComponent(), this.getPassiveResource(),
                this.getPassiveResource_BasicComponent_PassiveResource(), "passiveResource_BasicComponent", null, 0, -1,
                BasicComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        EOperation op = this.addEOperation(this.basicComponentEClass, this.ecorePackage.getEBoolean(),
                "NoSeffTypeUsedTwice", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        EGenericType g1 = this.createEGenericType(this.ecorePackage.getEMap());
        EGenericType g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.implementationComponentTypeEClass, ImplementationComponentType.class,
                "ImplementationComponentType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getImplementationComponentType_ParentCompleteComponentTypes(),
                this.getCompleteComponentType(), null, "parentCompleteComponentTypes", null, 0, -1,
                ImplementationComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getImplementationComponentType_ComponentParameterUsage_ImplementationComponentType(),
                theParameterPackage.getVariableUsage(), null, "componentParameterUsage_ImplementationComponentType",
                null, 0, -1, ImplementationComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEAttribute(this.getImplementationComponentType_ComponentType(), this.getComponentType(),
                "componentType", "BUSINESS_COMPONENT", 1, 1, ImplementationComponentType.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        op = this.addEOperation(this.implementationComponentTypeEClass, this.ecorePackage.getEBoolean(),
                "RequiredInterfacesHaveToConformToCompleteType", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = this.addEOperation(this.implementationComponentTypeEClass, this.ecorePackage.getEBoolean(),
                "providedInterfacesHaveToConformToCompleteType", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = this.addEOperation(this.implementationComponentTypeEClass, this.ecorePackage.getEBoolean(),
                "ProvidedInterfaceHaveToConformToComponentType", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = this.addEOperation(this.implementationComponentTypeEClass, this.ecorePackage.getEBoolean(),
                "ProvideSameOrMoreInterfacesAsCompleteComponentType", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = this.addEOperation(this.implementationComponentTypeEClass, this.ecorePackage.getEBoolean(),
                "RequireSameOrFewerInterfacesAsCompleteComponentType", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.repositoryComponentEClass, RepositoryComponent.class, "RepositoryComponent", IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getRepositoryComponent_Repository__RepositoryComponent(), this.getRepository(),
                this.getRepository_Components__Repository(), "repository__RepositoryComponent", null, 1, 1,
                RepositoryComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.providedRoleEClass, ProvidedRole.class, "ProvidedRole", IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getProvidedRole_ProvidingEntity_ProvidedRole(),
                theEntityPackage.getInterfaceProvidingEntity(),
                theEntityPackage.getInterfaceProvidingEntity_ProvidedRoles_InterfaceProvidingEntity(),
                "providingEntity_ProvidedRole", null, 1, 1, ProvidedRole.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getParameter_DataType__Parameter(), this.getDataType(), null, "dataType__Parameter",
                null, 1, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getParameter_InfrastructureSignature__Parameter(), this.getInfrastructureSignature(),
                this.getInfrastructureSignature_Parameters__InfrastructureSignature(),
                "infrastructureSignature__Parameter", null, 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getParameter_OperationSignature__Parameter(), this.getOperationSignature(),
                this.getOperationSignature_Parameters__OperationSignature(), "operationSignature__Parameter", null, 0,
                1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getParameter_EventType__Parameter(), this.getEventType(),
                this.getEventType_Parameter__EventType(), "eventType__Parameter", null, 0, 1, Parameter.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEAttribute(this.getParameter_ParameterName(), this.ecorePackage.getEString(), "parameterName", null, 1,
                1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, !IS_ORDERED);
        this.initEAttribute(this.getParameter_Modifier__Parameter(), this.getParameterModifier(), "modifier__Parameter",
                null, 1, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getParameter_ResourceSignature__Parameter(),
                theResourcetypePackage.getResourceSignature(),
                theResourcetypePackage.getResourceSignature_Parameter__ResourceSignature(),
                "resourceSignature__Parameter", null, 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.dataTypeEClass, DataType.class, "DataType", IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getDataType_Repository__DataType(), this.getRepository(),
                this.getRepository_DataTypes__Repository(), "repository__DataType", null, 1, 1, DataType.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.repositoryEClass, Repository.class, "Repository", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getRepository_RepositoryDescription(), this.ecorePackage.getEString(),
                "repositoryDescription", null, 0, 1, Repository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getRepository_Components__Repository(), this.getRepositoryComponent(),
                this.getRepositoryComponent_Repository__RepositoryComponent(), "components__Repository", null, 0, -1,
                Repository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getRepository_Interfaces__Repository(), this.getInterface(),
                this.getInterface_Repository__Interface(), "interfaces__Repository", null, 0, -1, Repository.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getRepository_FailureTypes__Repository(), theReliabilityPackage.getFailureType(),
                theReliabilityPackage.getFailureType_Repository__FailureType(), "failureTypes__Repository", null, 0, -1,
                Repository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getRepository_DataTypes__Repository(), this.getDataType(),
                this.getDataType_Repository__DataType(), "dataTypes__Repository", null, 0, -1, Repository.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.interfaceEClass, Interface.class, "Interface", IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getInterface_ParentInterfaces__Interface(), this.getInterface(), null,
                "parentInterfaces__Interface", null, 0, -1, Interface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getInterface_Protocols__Interface(), theProtocolPackage.getProtocol(), null,
                "protocols__Interface", null, 0, -1, Interface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getInterface_RequiredCharacterisations(), this.getRequiredCharacterisation(),
                this.getRequiredCharacterisation_Interface_RequiredCharacterisation(), "requiredCharacterisations",
                null, 0, -1, Interface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getInterface_Repository__Interface(), this.getRepository(),
                this.getRepository_Interfaces__Repository(), "repository__Interface", null, 1, 1, Interface.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        op = this.addEOperation(this.interfaceEClass, this.ecorePackage.getEBoolean(), "NoProtocolTypeIDUsedTwice", 0,
                1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.requiredCharacterisationEClass, RequiredCharacterisation.class, "RequiredCharacterisation",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getRequiredCharacterisation_Type(),
                theParameterPackage.getVariableCharacterisationType(), "type", null, 1, 1,
                RequiredCharacterisation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getRequiredCharacterisation_Parameter(), this.getParameter(), null, "parameter", null,
                1, 1, RequiredCharacterisation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getRequiredCharacterisation_Interface_RequiredCharacterisation(), this.getInterface(),
                this.getInterface_RequiredCharacterisations(), "interface_RequiredCharacterisation", null, 1, 1,
                RequiredCharacterisation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.eventGroupEClass, EventGroup.class, "EventGroup", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getEventGroup_EventTypes__EventGroup(), this.getEventType(),
                this.getEventType_EventGroup__EventType(), "eventTypes__EventGroup", null, 0, -1, EventGroup.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.eventTypeEClass, EventType.class, "EventType", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getEventType_Parameter__EventType(), this.getParameter(),
                this.getParameter_EventType__Parameter(), "parameter__EventType", null, 1, 1, EventType.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getEventType_EventGroup__EventType(), this.getEventGroup(),
                this.getEventGroup_EventTypes__EventGroup(), "eventGroup__EventType", null, 1, 1, EventType.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.signatureEClass, Signature.class, "Signature", IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getSignature_Exceptions__Signature(), this.getExceptionType(), null,
                "exceptions__Signature", null, 0, -1, Signature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getSignature_FailureType(), theReliabilityPackage.getFailureType(), null,
                "failureType", null, 0, -1, Signature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.exceptionTypeEClass, ExceptionType.class, "ExceptionType", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getExceptionType_ExceptionName(), this.ecorePackage.getEString(), "exceptionName",
                null, 1, 1, ExceptionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEAttribute(this.getExceptionType_ExceptionMessage(), this.ecorePackage.getEString(),
                "exceptionMessage", null, 1, 1, ExceptionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.infrastructureSignatureEClass, InfrastructureSignature.class, "InfrastructureSignature",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getInfrastructureSignature_Parameters__InfrastructureSignature(), this.getParameter(),
                this.getParameter_InfrastructureSignature__Parameter(), "parameters__InfrastructureSignature", null, 0,
                -1, InfrastructureSignature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getInfrastructureSignature_InfrastructureInterface__InfrastructureSignature(),
                this.getInfrastructureInterface(),
                this.getInfrastructureInterface_InfrastructureSignatures__InfrastructureInterface(),
                "infrastructureInterface__InfrastructureSignature", null, 1, 1, InfrastructureSignature.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.infrastructureInterfaceEClass, InfrastructureInterface.class, "InfrastructureInterface",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getInfrastructureInterface_InfrastructureSignatures__InfrastructureInterface(),
                this.getInfrastructureSignature(),
                this.getInfrastructureSignature_InfrastructureInterface__InfrastructureSignature(),
                "infrastructureSignatures__InfrastructureInterface", null, 0, -1, InfrastructureInterface.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.infrastructureRequiredRoleEClass, InfrastructureRequiredRole.class,
                "InfrastructureRequiredRole", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getInfrastructureRequiredRole_RequiredInterface__InfrastructureRequiredRole(),
                this.getInfrastructureInterface(), null, "requiredInterface__InfrastructureRequiredRole", null, 1, 1,
                InfrastructureRequiredRole.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.requiredRoleEClass, RequiredRole.class, "RequiredRole", IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getRequiredRole_RequiringEntity_RequiredRole(),
                theEntityPackage.getInterfaceRequiringEntity(),
                theEntityPackage.getInterfaceRequiringEntity_RequiredRoles_InterfaceRequiringEntity(),
                "requiringEntity_RequiredRole", null, 1, 1, RequiredRole.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.operationSignatureEClass, OperationSignature.class, "OperationSignature", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getOperationSignature_Interface__OperationSignature(), this.getOperationInterface(),
                this.getOperationInterface_Signatures__OperationInterface(), "interface__OperationSignature", null, 1,
                1, OperationSignature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getOperationSignature_Parameters__OperationSignature(), this.getParameter(),
                this.getParameter_OperationSignature__Parameter(), "parameters__OperationSignature", null, 0, -1,
                OperationSignature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getOperationSignature_ReturnType__OperationSignature(), this.getDataType(), null,
                "returnType__OperationSignature", null, 0, 1, OperationSignature.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        op = this.addEOperation(this.operationSignatureEClass, this.ecorePackage.getEBoolean(),
                "ParameterNamesHaveToBeUniqueForASignature", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.operationInterfaceEClass, OperationInterface.class, "OperationInterface", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getOperationInterface_Signatures__OperationInterface(), this.getOperationSignature(),
                this.getOperationSignature_Interface__OperationSignature(), "signatures__OperationInterface", null, 0,
                -1, OperationInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        op = this.addEOperation(this.operationInterfaceEClass, this.ecorePackage.getEBoolean(),
                "SignaturesHaveToBeUniqueForAnInterface", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.operationRequiredRoleEClass, OperationRequiredRole.class, "OperationRequiredRole",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getOperationRequiredRole_RequiredInterface__OperationRequiredRole(),
                this.getOperationInterface(), null, "requiredInterface__OperationRequiredRole", null, 1, 1,
                OperationRequiredRole.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.sourceRoleEClass, SourceRole.class, "SourceRole", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getSourceRole_EventGroup__SourceRole(), this.getEventGroup(), null,
                "eventGroup__SourceRole", null, 1, 1, SourceRole.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.sinkRoleEClass, SinkRole.class, "SinkRole", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getSinkRole_EventGroup__SinkRole(), this.getEventGroup(), null, "eventGroup__SinkRole",
                null, 1, 1, SinkRole.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.operationProvidedRoleEClass, OperationProvidedRole.class, "OperationProvidedRole",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getOperationProvidedRole_ProvidedInterface__OperationProvidedRole(),
                this.getOperationInterface(), null, "providedInterface__OperationProvidedRole", null, 1, 1,
                OperationProvidedRole.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.infrastructureProvidedRoleEClass, InfrastructureProvidedRole.class,
                "InfrastructureProvidedRole", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getInfrastructureProvidedRole_ProvidedInterface__InfrastructureProvidedRole(),
                this.getInfrastructureInterface(), null, "providedInterface__InfrastructureProvidedRole", null, 1, 1,
                InfrastructureProvidedRole.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.completeComponentTypeEClass, CompleteComponentType.class, "CompleteComponentType",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getCompleteComponentType_ParentProvidesComponentTypes(),
                this.getProvidesComponentType(), null, "parentProvidesComponentTypes", null, 0, -1,
                CompleteComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        op = this.addEOperation(this.completeComponentTypeEClass, this.ecorePackage.getEBoolean(),
                "AtLeastOneInterfaceHasToBeProvidedOrRequiredByAUsefullCompleteComponentType", 0, 1, IS_UNIQUE,
                IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = this.addEOperation(this.completeComponentTypeEClass, this.ecorePackage.getEBoolean(),
                "providedInterfacesHaveToConformToProvidedType2", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.providesComponentTypeEClass, ProvidesComponentType.class, "ProvidesComponentType",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        op = this.addEOperation(this.providesComponentTypeEClass, this.ecorePackage.getEBoolean(),
                "AtLeastOneInterfaceHasToBeProvidedByAUsefullProvidesComponentType", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.compositeComponentEClass, CompositeComponent.class, "CompositeComponent", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.primitiveDataTypeEClass, PrimitiveDataType.class, "PrimitiveDataType", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getPrimitiveDataType_Type(), this.getPrimitiveTypeEnum(), "type", null, 1, 1,
                PrimitiveDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.collectionDataTypeEClass, CollectionDataType.class, "CollectionDataType", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getCollectionDataType_InnerType_CollectionDataType(), this.getDataType(), null,
                "innerType_CollectionDataType", null, 1, 1, CollectionDataType.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.compositeDataTypeEClass, CompositeDataType.class, "CompositeDataType", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getCompositeDataType_ParentType_CompositeDataType(), this.getCompositeDataType(), null,
                "parentType_CompositeDataType", null, 0, -1, CompositeDataType.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getCompositeDataType_InnerDeclaration_CompositeDataType(), this.getInnerDeclaration(),
                this.getInnerDeclaration_CompositeDataType_InnerDeclaration(), "innerDeclaration_CompositeDataType",
                null, 0, -1, CompositeDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.innerDeclarationEClass, InnerDeclaration.class, "InnerDeclaration", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getInnerDeclaration_Datatype_InnerDeclaration(), this.getDataType(), null,
                "datatype_InnerDeclaration", null, 1, 1, InnerDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getInnerDeclaration_CompositeDataType_InnerDeclaration(), this.getCompositeDataType(),
                this.getCompositeDataType_InnerDeclaration_CompositeDataType(), "compositeDataType_InnerDeclaration",
                null, 1, 1, InnerDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.roleEClass, Role.class, "Role", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        // Initialize enums and add enum literals
        this.initEEnum(this.parameterModifierEEnum, ParameterModifier.class, "ParameterModifier");
        this.addEEnumLiteral(this.parameterModifierEEnum, ParameterModifier.NONE);
        this.addEEnumLiteral(this.parameterModifierEEnum, ParameterModifier.IN);
        this.addEEnumLiteral(this.parameterModifierEEnum, ParameterModifier.OUT);
        this.addEEnumLiteral(this.parameterModifierEEnum, ParameterModifier.INOUT);

        this.initEEnum(this.componentTypeEEnum, ComponentType.class, "ComponentType");
        this.addEEnumLiteral(this.componentTypeEEnum, ComponentType.BUSINESS_COMPONENT);
        this.addEEnumLiteral(this.componentTypeEEnum, ComponentType.INFRASTRUCTURE_COMPONENT);

        this.initEEnum(this.primitiveTypeEnumEEnum, PrimitiveTypeEnum.class, "PrimitiveTypeEnum");
        this.addEEnumLiteral(this.primitiveTypeEnumEEnum, PrimitiveTypeEnum.INT);
        this.addEEnumLiteral(this.primitiveTypeEnumEEnum, PrimitiveTypeEnum.STRING);
        this.addEEnumLiteral(this.primitiveTypeEnumEEnum, PrimitiveTypeEnum.BOOL);
        this.addEEnumLiteral(this.primitiveTypeEnumEEnum, PrimitiveTypeEnum.DOUBLE);
        this.addEEnumLiteral(this.primitiveTypeEnumEEnum, PrimitiveTypeEnum.CHAR);
        this.addEEnumLiteral(this.primitiveTypeEnumEEnum, PrimitiveTypeEnum.BYTE);
        this.addEEnumLiteral(this.primitiveTypeEnumEEnum, PrimitiveTypeEnum.LONG);
    }

} // RepositoryPackageImpl
