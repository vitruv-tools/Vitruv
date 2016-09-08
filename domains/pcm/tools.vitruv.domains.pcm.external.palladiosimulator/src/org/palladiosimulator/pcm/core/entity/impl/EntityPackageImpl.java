/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.entity.impl;

import org.eclipse.emf.ecore.EAttribute;
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
import org.palladiosimulator.pcm.core.composition.CompositionPackage;
import org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.core.entity.EntityFactory;
import org.palladiosimulator.pcm.core.entity.EntityPackage;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingEntity;
import org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.ResourceInterfaceRequiringEntity;
import org.palladiosimulator.pcm.core.entity.ResourceProvidedRole;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.core.entity.util.EntityValidator;
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
public class EntityPackageImpl extends EPackageImpl implements EntityPackage {

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
    private EClass resourceProvidedRoleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass interfaceProvidingRequiringEntityEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass interfaceProvidingEntityEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass interfaceRequiringEntityEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass resourceInterfaceRequiringEntityEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass resourceRequiredRoleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass resourceInterfaceProvidingEntityEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass composedProvidingRequiringEntityEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass namedElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass resourceInterfaceProvidingRequiringEntityEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass entityEClass = null;

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
     * @see org.palladiosimulator.pcm.core.entity.EntityPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private EntityPackageImpl() {
        super(eNS_URI, EntityFactory.eINSTANCE);
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
     * This method is used to initialize {@link EntityPackage#eINSTANCE} when that field is
     * accessed. Clients should not invoke it directly. Instead, they should simply access that
     * field to obtain the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static EntityPackage init() {
        if (isInited) {
            return (EntityPackage) EPackage.Registry.INSTANCE.getEPackage(EntityPackage.eNS_URI);
        }

        // Obtain or create and register package
        final EntityPackageImpl theEntityPackage = (EntityPackageImpl) (EPackage.Registry.INSTANCE
                .get(eNS_URI) instanceof EntityPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
                        : new EntityPackageImpl());

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
        theEntityPackage.createPackageContents();
        thePcmPackage.createPackageContents();
        theCorePackage.createPackageContents();
        theCompositionPackage.createPackageContents();
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
        theEntityPackage.initializePackageContents();
        thePcmPackage.initializePackageContents();
        theCorePackage.initializePackageContents();
        theCompositionPackage.initializePackageContents();
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
        EValidator.Registry.INSTANCE.put(theEntityPackage, new EValidator.Descriptor() {
            @Override
            public EValidator getEValidator() {
                return EntityValidator.INSTANCE;
            }
        });

        // Mark meta-data to indicate it can't be changed
        theEntityPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(EntityPackage.eNS_URI, theEntityPackage);
        return theEntityPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getResourceProvidedRole() {
        return this.resourceProvidedRoleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getResourceProvidedRole_ResourceInterfaceProvidingEntity__ResourceProvidedRole() {
        return (EReference) this.resourceProvidedRoleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getResourceProvidedRole_ProvidedResourceInterface__ResourceProvidedRole() {
        return (EReference) this.resourceProvidedRoleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getInterfaceProvidingRequiringEntity() {
        return this.interfaceProvidingRequiringEntityEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getInterfaceProvidingEntity() {
        return this.interfaceProvidingEntityEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getInterfaceProvidingEntity_ProvidedRoles_InterfaceProvidingEntity() {
        return (EReference) this.interfaceProvidingEntityEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getInterfaceRequiringEntity() {
        return this.interfaceRequiringEntityEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getInterfaceRequiringEntity_RequiredRoles_InterfaceRequiringEntity() {
        return (EReference) this.interfaceRequiringEntityEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getResourceInterfaceRequiringEntity() {
        return this.resourceInterfaceRequiringEntityEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getResourceInterfaceRequiringEntity_ResourceRequiredRoles__ResourceInterfaceRequiringEntity() {
        return (EReference) this.resourceInterfaceRequiringEntityEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getResourceRequiredRole() {
        return this.resourceRequiredRoleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getResourceRequiredRole_RequiredResourceInterface__ResourceRequiredRole() {
        return (EReference) this.resourceRequiredRoleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getResourceRequiredRole_ResourceInterfaceRequiringEntity__ResourceRequiredRole() {
        return (EReference) this.resourceRequiredRoleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getResourceInterfaceProvidingEntity() {
        return this.resourceInterfaceProvidingEntityEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getResourceInterfaceProvidingEntity_ResourceProvidedRoles__ResourceInterfaceProvidingEntity() {
        return (EReference) this.resourceInterfaceProvidingEntityEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getComposedProvidingRequiringEntity() {
        return this.composedProvidingRequiringEntityEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getNamedElement() {
        return this.namedElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EAttribute getNamedElement_EntityName() {
        return (EAttribute) this.namedElementEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getResourceInterfaceProvidingRequiringEntity() {
        return this.resourceInterfaceProvidingRequiringEntityEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getEntity() {
        return this.entityEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EntityFactory getEntityFactory() {
        return (EntityFactory) this.getEFactoryInstance();
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
        this.resourceProvidedRoleEClass = this.createEClass(RESOURCE_PROVIDED_ROLE);
        this.createEReference(this.resourceProvidedRoleEClass,
                RESOURCE_PROVIDED_ROLE__RESOURCE_INTERFACE_PROVIDING_ENTITY_RESOURCE_PROVIDED_ROLE);
        this.createEReference(this.resourceProvidedRoleEClass,
                RESOURCE_PROVIDED_ROLE__PROVIDED_RESOURCE_INTERFACE_RESOURCE_PROVIDED_ROLE);

        this.interfaceProvidingRequiringEntityEClass = this.createEClass(INTERFACE_PROVIDING_REQUIRING_ENTITY);

        this.interfaceProvidingEntityEClass = this.createEClass(INTERFACE_PROVIDING_ENTITY);
        this.createEReference(this.interfaceProvidingEntityEClass,
                INTERFACE_PROVIDING_ENTITY__PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY);

        this.interfaceRequiringEntityEClass = this.createEClass(INTERFACE_REQUIRING_ENTITY);
        this.createEReference(this.interfaceRequiringEntityEClass,
                INTERFACE_REQUIRING_ENTITY__REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY);

        this.resourceInterfaceRequiringEntityEClass = this.createEClass(RESOURCE_INTERFACE_REQUIRING_ENTITY);
        this.createEReference(this.resourceInterfaceRequiringEntityEClass,
                RESOURCE_INTERFACE_REQUIRING_ENTITY__RESOURCE_REQUIRED_ROLES_RESOURCE_INTERFACE_REQUIRING_ENTITY);

        this.resourceRequiredRoleEClass = this.createEClass(RESOURCE_REQUIRED_ROLE);
        this.createEReference(this.resourceRequiredRoleEClass,
                RESOURCE_REQUIRED_ROLE__REQUIRED_RESOURCE_INTERFACE_RESOURCE_REQUIRED_ROLE);
        this.createEReference(this.resourceRequiredRoleEClass,
                RESOURCE_REQUIRED_ROLE__RESOURCE_INTERFACE_REQUIRING_ENTITY_RESOURCE_REQUIRED_ROLE);

        this.resourceInterfaceProvidingEntityEClass = this.createEClass(RESOURCE_INTERFACE_PROVIDING_ENTITY);
        this.createEReference(this.resourceInterfaceProvidingEntityEClass,
                RESOURCE_INTERFACE_PROVIDING_ENTITY__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY);

        this.composedProvidingRequiringEntityEClass = this.createEClass(COMPOSED_PROVIDING_REQUIRING_ENTITY);

        this.namedElementEClass = this.createEClass(NAMED_ELEMENT);
        this.createEAttribute(this.namedElementEClass, NAMED_ELEMENT__ENTITY_NAME);

        this.resourceInterfaceProvidingRequiringEntityEClass = this
                .createEClass(RESOURCE_INTERFACE_PROVIDING_REQUIRING_ENTITY);

        this.entityEClass = this.createEClass(ENTITY);
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
        final RepositoryPackage theRepositoryPackage = (RepositoryPackage) EPackage.Registry.INSTANCE
                .getEPackage(RepositoryPackage.eNS_URI);
        final ResourcetypePackage theResourcetypePackage = (ResourcetypePackage) EPackage.Registry.INSTANCE
                .getEPackage(ResourcetypePackage.eNS_URI);
        final CompositionPackage theCompositionPackage = (CompositionPackage) EPackage.Registry.INSTANCE
                .getEPackage(CompositionPackage.eNS_URI);
        final IdentifierPackage theIdentifierPackage = (IdentifierPackage) EPackage.Registry.INSTANCE
                .getEPackage(IdentifierPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.resourceProvidedRoleEClass.getESuperTypes().add(theRepositoryPackage.getRole());
        this.interfaceProvidingRequiringEntityEClass.getESuperTypes().add(this.getInterfaceProvidingEntity());
        this.interfaceProvidingRequiringEntityEClass.getESuperTypes().add(this.getInterfaceRequiringEntity());
        this.interfaceProvidingEntityEClass.getESuperTypes().add(this.getEntity());
        this.interfaceRequiringEntityEClass.getESuperTypes().add(this.getEntity());
        this.interfaceRequiringEntityEClass.getESuperTypes().add(this.getResourceInterfaceRequiringEntity());
        this.resourceInterfaceRequiringEntityEClass.getESuperTypes().add(this.getEntity());
        this.resourceRequiredRoleEClass.getESuperTypes().add(theRepositoryPackage.getRole());
        this.resourceInterfaceProvidingEntityEClass.getESuperTypes().add(this.getEntity());
        this.composedProvidingRequiringEntityEClass.getESuperTypes().add(theCompositionPackage.getComposedStructure());
        this.composedProvidingRequiringEntityEClass.getESuperTypes().add(this.getInterfaceProvidingRequiringEntity());
        this.resourceInterfaceProvidingRequiringEntityEClass.getESuperTypes()
                .add(this.getResourceInterfaceRequiringEntity());
        this.resourceInterfaceProvidingRequiringEntityEClass.getESuperTypes()
                .add(this.getResourceInterfaceProvidingEntity());
        this.entityEClass.getESuperTypes().add(theIdentifierPackage.getIdentifier());
        this.entityEClass.getESuperTypes().add(this.getNamedElement());

        // Initialize classes and features; add operations and parameters
        this.initEClass(this.resourceProvidedRoleEClass, ResourceProvidedRole.class, "ResourceProvidedRole",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getResourceProvidedRole_ResourceInterfaceProvidingEntity__ResourceProvidedRole(),
                this.getResourceInterfaceProvidingEntity(),
                this.getResourceInterfaceProvidingEntity_ResourceProvidedRoles__ResourceInterfaceProvidingEntity(),
                "resourceInterfaceProvidingEntity__ResourceProvidedRole", null, 1, 1, ResourceProvidedRole.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getResourceProvidedRole_ProvidedResourceInterface__ResourceProvidedRole(),
                theResourcetypePackage.getResourceInterface(), null, "providedResourceInterface__ResourceProvidedRole",
                null, 1, 1, ResourceProvidedRole.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.interfaceProvidingRequiringEntityEClass, InterfaceProvidingRequiringEntity.class,
                "InterfaceProvidingRequiringEntity", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.interfaceProvidingEntityEClass, InterfaceProvidingEntity.class, "InterfaceProvidingEntity",
                IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getInterfaceProvidingEntity_ProvidedRoles_InterfaceProvidingEntity(),
                theRepositoryPackage.getProvidedRole(),
                theRepositoryPackage.getProvidedRole_ProvidingEntity_ProvidedRole(),
                "providedRoles_InterfaceProvidingEntity", null, 0, -1, InterfaceProvidingEntity.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);

        this.initEClass(this.interfaceRequiringEntityEClass, InterfaceRequiringEntity.class, "InterfaceRequiringEntity",
                IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getInterfaceRequiringEntity_RequiredRoles_InterfaceRequiringEntity(),
                theRepositoryPackage.getRequiredRole(),
                theRepositoryPackage.getRequiredRole_RequiringEntity_RequiredRole(),
                "requiredRoles_InterfaceRequiringEntity", null, 0, -1, InterfaceRequiringEntity.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);

        this.initEClass(this.resourceInterfaceRequiringEntityEClass, ResourceInterfaceRequiringEntity.class,
                "ResourceInterfaceRequiringEntity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(
                this.getResourceInterfaceRequiringEntity_ResourceRequiredRoles__ResourceInterfaceRequiringEntity(),
                this.getResourceRequiredRole(),
                this.getResourceRequiredRole_ResourceInterfaceRequiringEntity__ResourceRequiredRole(),
                "resourceRequiredRoles__ResourceInterfaceRequiringEntity", null, 0, -1,
                ResourceInterfaceRequiringEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.resourceRequiredRoleEClass, ResourceRequiredRole.class, "ResourceRequiredRole",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getResourceRequiredRole_RequiredResourceInterface__ResourceRequiredRole(),
                theResourcetypePackage.getResourceInterface(), null, "requiredResourceInterface__ResourceRequiredRole",
                null, 1, 1, ResourceRequiredRole.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getResourceRequiredRole_ResourceInterfaceRequiringEntity__ResourceRequiredRole(),
                this.getResourceInterfaceRequiringEntity(),
                this.getResourceInterfaceRequiringEntity_ResourceRequiredRoles__ResourceInterfaceRequiringEntity(),
                "resourceInterfaceRequiringEntity__ResourceRequiredRole", null, 1, 1, ResourceRequiredRole.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.resourceInterfaceProvidingEntityEClass, ResourceInterfaceProvidingEntity.class,
                "ResourceInterfaceProvidingEntity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(
                this.getResourceInterfaceProvidingEntity_ResourceProvidedRoles__ResourceInterfaceProvidingEntity(),
                this.getResourceProvidedRole(),
                this.getResourceProvidedRole_ResourceInterfaceProvidingEntity__ResourceProvidedRole(),
                "resourceProvidedRoles__ResourceInterfaceProvidingEntity", null, 0, -1,
                ResourceInterfaceProvidingEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.composedProvidingRequiringEntityEClass, ComposedProvidingRequiringEntity.class,
                "ComposedProvidingRequiringEntity", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        final EOperation op = this.addEOperation(this.composedProvidingRequiringEntityEClass,
                this.ecorePackage.getEBoolean(), "ProvidedRolesMustBeBound", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        final EGenericType g1 = this.createEGenericType(this.ecorePackage.getEMap());
        EGenericType g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.namedElementEClass, NamedElement.class, "NamedElement", IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getNamedElement_EntityName(), this.ecorePackage.getEString(), "entityName", "aName", 1,
                1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.resourceInterfaceProvidingRequiringEntityEClass,
                ResourceInterfaceProvidingRequiringEntity.class, "ResourceInterfaceProvidingRequiringEntity",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.entityEClass, Entity.class, "Entity", IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
    }

} // EntityPackageImpl
