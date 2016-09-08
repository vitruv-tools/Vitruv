/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.impl;

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
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.AbstractBranchTransition;
import org.palladiosimulator.pcm.seff.AbstractInternalControlFlowAction;
import org.palladiosimulator.pcm.seff.AbstractLoopAction;
import org.palladiosimulator.pcm.seff.AcquireAction;
import org.palladiosimulator.pcm.seff.BranchAction;
import org.palladiosimulator.pcm.seff.CallAction;
import org.palladiosimulator.pcm.seff.CallReturnAction;
import org.palladiosimulator.pcm.seff.CollectionIteratorAction;
import org.palladiosimulator.pcm.seff.EmitEventAction;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.ForkAction;
import org.palladiosimulator.pcm.seff.ForkedBehaviour;
import org.palladiosimulator.pcm.seff.GuardedBranchTransition;
import org.palladiosimulator.pcm.seff.InternalAction;
import org.palladiosimulator.pcm.seff.InternalCallAction;
import org.palladiosimulator.pcm.seff.LoopAction;
import org.palladiosimulator.pcm.seff.ProbabilisticBranchTransition;
import org.palladiosimulator.pcm.seff.ReleaseAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.SeffPackage;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.palladiosimulator.pcm.seff.SetVariableAction;
import org.palladiosimulator.pcm.seff.StartAction;
import org.palladiosimulator.pcm.seff.StopAction;
import org.palladiosimulator.pcm.seff.SynchronisationPoint;
import org.palladiosimulator.pcm.seff.seff_performance.SeffPerformancePackage;
import org.palladiosimulator.pcm.seff.seff_performance.impl.SeffPerformancePackageImpl;
import org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityPackage;
import org.palladiosimulator.pcm.seff.seff_reliability.impl.SeffReliabilityPackageImpl;
import org.palladiosimulator.pcm.seff.util.SeffValidator;
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
public class SeffPackageImpl extends EPackageImpl implements SeffPackage {

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
    private EClass stopActionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass abstractInternalControlFlowActionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass abstractActionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass resourceDemandingBehaviourEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass abstractLoopActionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass abstractBranchTransitionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass branchActionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass callActionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass startActionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass serviceEffectSpecificationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass resourceDemandingSEFFEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass resourceDemandingInternalBehaviourEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass releaseActionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass loopActionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass forkActionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass forkedBehaviourEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass synchronisationPointEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass externalCallActionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass callReturnActionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass probabilisticBranchTransitionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass acquireActionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass collectionIteratorActionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass guardedBranchTransitionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass setVariableActionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass internalCallActionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass emitEventActionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass internalActionEClass = null;

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
     * @see org.palladiosimulator.pcm.seff.SeffPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private SeffPackageImpl() {
        super(eNS_URI, SeffFactory.eINSTANCE);
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
     * This method is used to initialize {@link SeffPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to
     * obtain the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static SeffPackage init() {
        if (isInited) {
            return (SeffPackage) EPackage.Registry.INSTANCE.getEPackage(SeffPackage.eNS_URI);
        }

        // Obtain or create and register package
        final SeffPackageImpl theSeffPackage = (SeffPackageImpl) (EPackage.Registry.INSTANCE
                .get(eNS_URI) instanceof SeffPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
                        : new SeffPackageImpl());

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
        theSeffPackage.createPackageContents();
        thePcmPackage.createPackageContents();
        theCorePackage.createPackageContents();
        theEntityPackage.createPackageContents();
        theCompositionPackage.createPackageContents();
        theUsagemodelPackage.createPackageContents();
        theRepositoryPackage.createPackageContents();
        theResourcetypePackage.createPackageContents();
        theProtocolPackage.createPackageContents();
        theParameterPackage.createPackageContents();
        theReliabilityPackage.createPackageContents();
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
        theSeffPackage.initializePackageContents();
        thePcmPackage.initializePackageContents();
        theCorePackage.initializePackageContents();
        theEntityPackage.initializePackageContents();
        theCompositionPackage.initializePackageContents();
        theUsagemodelPackage.initializePackageContents();
        theRepositoryPackage.initializePackageContents();
        theResourcetypePackage.initializePackageContents();
        theProtocolPackage.initializePackageContents();
        theParameterPackage.initializePackageContents();
        theReliabilityPackage.initializePackageContents();
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
        EValidator.Registry.INSTANCE.put(theSeffPackage, new EValidator.Descriptor() {
            @Override
            public EValidator getEValidator() {
                return SeffValidator.INSTANCE;
            }
        });

        // Mark meta-data to indicate it can't be changed
        theSeffPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(SeffPackage.eNS_URI, theSeffPackage);
        return theSeffPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getStopAction() {
        return this.stopActionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getAbstractInternalControlFlowAction() {
        return this.abstractInternalControlFlowActionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getAbstractInternalControlFlowAction_ResourceDemand_Action() {
        return (EReference) this.abstractInternalControlFlowActionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getAbstractInternalControlFlowAction_InfrastructureCall__Action() {
        return (EReference) this.abstractInternalControlFlowActionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getAbstractInternalControlFlowAction_ResourceCall__Action() {
        return (EReference) this.abstractInternalControlFlowActionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getAbstractAction() {
        return this.abstractActionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getAbstractAction_Predecessor_AbstractAction() {
        return (EReference) this.abstractActionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getAbstractAction_Successor_AbstractAction() {
        return (EReference) this.abstractActionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getAbstractAction_ResourceDemandingBehaviour_AbstractAction() {
        return (EReference) this.abstractActionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getResourceDemandingBehaviour() {
        return this.resourceDemandingBehaviourEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getResourceDemandingBehaviour_AbstractLoopAction_ResourceDemandingBehaviour() {
        return (EReference) this.resourceDemandingBehaviourEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getResourceDemandingBehaviour_AbstractBranchTransition_ResourceDemandingBehaviour() {
        return (EReference) this.resourceDemandingBehaviourEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getResourceDemandingBehaviour_Steps_Behaviour() {
        return (EReference) this.resourceDemandingBehaviourEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getAbstractLoopAction() {
        return this.abstractLoopActionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getAbstractLoopAction_BodyBehaviour_Loop() {
        return (EReference) this.abstractLoopActionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getAbstractBranchTransition() {
        return this.abstractBranchTransitionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getAbstractBranchTransition_BranchAction_AbstractBranchTransition() {
        return (EReference) this.abstractBranchTransitionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getAbstractBranchTransition_BranchBehaviour_BranchTransition() {
        return (EReference) this.abstractBranchTransitionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getBranchAction() {
        return this.branchActionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getBranchAction_Branches_Branch() {
        return (EReference) this.branchActionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getCallAction() {
        return this.callActionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getCallAction_InputVariableUsages__CallAction() {
        return (EReference) this.callActionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getStartAction() {
        return this.startActionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getServiceEffectSpecification() {
        return this.serviceEffectSpecificationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EAttribute getServiceEffectSpecification_SeffTypeID() {
        return (EAttribute) this.serviceEffectSpecificationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getServiceEffectSpecification_DescribedService__SEFF() {
        return (EReference) this.serviceEffectSpecificationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getServiceEffectSpecification_BasicComponent_ServiceEffectSpecification() {
        return (EReference) this.serviceEffectSpecificationEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getResourceDemandingSEFF() {
        return this.resourceDemandingSEFFEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getResourceDemandingInternalBehaviour() {
        return this.resourceDemandingInternalBehaviourEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getResourceDemandingInternalBehaviour_BasicComponent_ResourceDemandingInternalBehaviour() {
        return (EReference) this.resourceDemandingInternalBehaviourEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getReleaseAction() {
        return this.releaseActionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getReleaseAction_PassiveResource_ReleaseAction() {
        return (EReference) this.releaseActionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getLoopAction() {
        return this.loopActionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getLoopAction_IterationCount_LoopAction() {
        return (EReference) this.loopActionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getForkAction() {
        return this.forkActionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getForkAction_AsynchronousForkedBehaviours_ForkAction() {
        return (EReference) this.forkActionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getForkAction_SynchronisingBehaviours_ForkAction() {
        return (EReference) this.forkActionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getForkedBehaviour() {
        return this.forkedBehaviourEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getForkedBehaviour_SynchronisationPoint_ForkedBehaviour() {
        return (EReference) this.forkedBehaviourEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getForkedBehaviour_ForkAction_ForkedBehaivour() {
        return (EReference) this.forkedBehaviourEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getSynchronisationPoint() {
        return this.synchronisationPointEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getSynchronisationPoint_OutputParameterUsage_SynchronisationPoint() {
        return (EReference) this.synchronisationPointEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getSynchronisationPoint_ForkAction_SynchronisationPoint() {
        return (EReference) this.synchronisationPointEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getSynchronisationPoint_SynchronousForkedBehaviours_SynchronisationPoint() {
        return (EReference) this.synchronisationPointEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getExternalCallAction() {
        return this.externalCallActionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getExternalCallAction_CalledService_ExternalService() {
        return (EReference) this.externalCallActionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getExternalCallAction_Role_ExternalService() {
        return (EReference) this.externalCallActionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EAttribute getExternalCallAction_RetryCount() {
        return (EAttribute) this.externalCallActionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getCallReturnAction() {
        return this.callReturnActionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getCallReturnAction_ReturnVariableUsage__CallReturnAction() {
        return (EReference) this.callReturnActionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getProbabilisticBranchTransition() {
        return this.probabilisticBranchTransitionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EAttribute getProbabilisticBranchTransition_BranchProbability() {
        return (EAttribute) this.probabilisticBranchTransitionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getAcquireAction() {
        return this.acquireActionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getAcquireAction_Passiveresource_AcquireAction() {
        return (EReference) this.acquireActionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EAttribute getAcquireAction_Timeout() {
        return (EAttribute) this.acquireActionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EAttribute getAcquireAction_TimeoutValue() {
        return (EAttribute) this.acquireActionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getCollectionIteratorAction() {
        return this.collectionIteratorActionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getCollectionIteratorAction_Parameter_CollectionIteratorAction() {
        return (EReference) this.collectionIteratorActionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getGuardedBranchTransition() {
        return this.guardedBranchTransitionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getGuardedBranchTransition_BranchCondition_GuardedBranchTransition() {
        return (EReference) this.guardedBranchTransitionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getSetVariableAction() {
        return this.setVariableActionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getSetVariableAction_LocalVariableUsages_SetVariableAction() {
        return (EReference) this.setVariableActionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getInternalCallAction() {
        return this.internalCallActionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getInternalCallAction_CalledResourceDemandingInternalBehaviour() {
        return (EReference) this.internalCallActionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getEmitEventAction() {
        return this.emitEventActionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getEmitEventAction_EventType__EmitEventAction() {
        return (EReference) this.emitEventActionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getEmitEventAction_SourceRole__EmitEventAction() {
        return (EReference) this.emitEventActionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getInternalAction() {
        return this.internalActionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getInternalAction_InternalFailureOccurrenceDescriptions__InternalAction() {
        return (EReference) this.internalActionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public SeffFactory getSeffFactory() {
        return (SeffFactory) this.getEFactoryInstance();
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
        this.stopActionEClass = this.createEClass(STOP_ACTION);

        this.abstractInternalControlFlowActionEClass = this.createEClass(ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION);
        this.createEReference(this.abstractInternalControlFlowActionEClass,
                ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__RESOURCE_DEMAND_ACTION);
        this.createEReference(this.abstractInternalControlFlowActionEClass,
                ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__INFRASTRUCTURE_CALL_ACTION);
        this.createEReference(this.abstractInternalControlFlowActionEClass,
                ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__RESOURCE_CALL_ACTION);

        this.abstractActionEClass = this.createEClass(ABSTRACT_ACTION);
        this.createEReference(this.abstractActionEClass, ABSTRACT_ACTION__PREDECESSOR_ABSTRACT_ACTION);
        this.createEReference(this.abstractActionEClass, ABSTRACT_ACTION__SUCCESSOR_ABSTRACT_ACTION);
        this.createEReference(this.abstractActionEClass, ABSTRACT_ACTION__RESOURCE_DEMANDING_BEHAVIOUR_ABSTRACT_ACTION);

        this.resourceDemandingBehaviourEClass = this.createEClass(RESOURCE_DEMANDING_BEHAVIOUR);
        this.createEReference(this.resourceDemandingBehaviourEClass,
                RESOURCE_DEMANDING_BEHAVIOUR__ABSTRACT_LOOP_ACTION_RESOURCE_DEMANDING_BEHAVIOUR);
        this.createEReference(this.resourceDemandingBehaviourEClass,
                RESOURCE_DEMANDING_BEHAVIOUR__ABSTRACT_BRANCH_TRANSITION_RESOURCE_DEMANDING_BEHAVIOUR);
        this.createEReference(this.resourceDemandingBehaviourEClass, RESOURCE_DEMANDING_BEHAVIOUR__STEPS_BEHAVIOUR);

        this.abstractLoopActionEClass = this.createEClass(ABSTRACT_LOOP_ACTION);
        this.createEReference(this.abstractLoopActionEClass, ABSTRACT_LOOP_ACTION__BODY_BEHAVIOUR_LOOP);

        this.abstractBranchTransitionEClass = this.createEClass(ABSTRACT_BRANCH_TRANSITION);
        this.createEReference(this.abstractBranchTransitionEClass,
                ABSTRACT_BRANCH_TRANSITION__BRANCH_ACTION_ABSTRACT_BRANCH_TRANSITION);
        this.createEReference(this.abstractBranchTransitionEClass,
                ABSTRACT_BRANCH_TRANSITION__BRANCH_BEHAVIOUR_BRANCH_TRANSITION);

        this.branchActionEClass = this.createEClass(BRANCH_ACTION);
        this.createEReference(this.branchActionEClass, BRANCH_ACTION__BRANCHES_BRANCH);

        this.callActionEClass = this.createEClass(CALL_ACTION);
        this.createEReference(this.callActionEClass, CALL_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION);

        this.startActionEClass = this.createEClass(START_ACTION);

        this.serviceEffectSpecificationEClass = this.createEClass(SERVICE_EFFECT_SPECIFICATION);
        this.createEAttribute(this.serviceEffectSpecificationEClass, SERVICE_EFFECT_SPECIFICATION__SEFF_TYPE_ID);
        this.createEReference(this.serviceEffectSpecificationEClass,
                SERVICE_EFFECT_SPECIFICATION__DESCRIBED_SERVICE_SEFF);
        this.createEReference(this.serviceEffectSpecificationEClass,
                SERVICE_EFFECT_SPECIFICATION__BASIC_COMPONENT_SERVICE_EFFECT_SPECIFICATION);

        this.resourceDemandingSEFFEClass = this.createEClass(RESOURCE_DEMANDING_SEFF);

        this.resourceDemandingInternalBehaviourEClass = this.createEClass(RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR);
        this.createEReference(this.resourceDemandingInternalBehaviourEClass,
                RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR__BASIC_COMPONENT_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR);

        this.releaseActionEClass = this.createEClass(RELEASE_ACTION);
        this.createEReference(this.releaseActionEClass, RELEASE_ACTION__PASSIVE_RESOURCE_RELEASE_ACTION);

        this.loopActionEClass = this.createEClass(LOOP_ACTION);
        this.createEReference(this.loopActionEClass, LOOP_ACTION__ITERATION_COUNT_LOOP_ACTION);

        this.forkActionEClass = this.createEClass(FORK_ACTION);
        this.createEReference(this.forkActionEClass, FORK_ACTION__ASYNCHRONOUS_FORKED_BEHAVIOURS_FORK_ACTION);
        this.createEReference(this.forkActionEClass, FORK_ACTION__SYNCHRONISING_BEHAVIOURS_FORK_ACTION);

        this.forkedBehaviourEClass = this.createEClass(FORKED_BEHAVIOUR);
        this.createEReference(this.forkedBehaviourEClass, FORKED_BEHAVIOUR__SYNCHRONISATION_POINT_FORKED_BEHAVIOUR);
        this.createEReference(this.forkedBehaviourEClass, FORKED_BEHAVIOUR__FORK_ACTION_FORKED_BEHAIVOUR);

        this.synchronisationPointEClass = this.createEClass(SYNCHRONISATION_POINT);
        this.createEReference(this.synchronisationPointEClass,
                SYNCHRONISATION_POINT__OUTPUT_PARAMETER_USAGE_SYNCHRONISATION_POINT);
        this.createEReference(this.synchronisationPointEClass,
                SYNCHRONISATION_POINT__FORK_ACTION_SYNCHRONISATION_POINT);
        this.createEReference(this.synchronisationPointEClass,
                SYNCHRONISATION_POINT__SYNCHRONOUS_FORKED_BEHAVIOURS_SYNCHRONISATION_POINT);

        this.externalCallActionEClass = this.createEClass(EXTERNAL_CALL_ACTION);
        this.createEReference(this.externalCallActionEClass, EXTERNAL_CALL_ACTION__CALLED_SERVICE_EXTERNAL_SERVICE);
        this.createEReference(this.externalCallActionEClass, EXTERNAL_CALL_ACTION__ROLE_EXTERNAL_SERVICE);
        this.createEAttribute(this.externalCallActionEClass, EXTERNAL_CALL_ACTION__RETRY_COUNT);

        this.callReturnActionEClass = this.createEClass(CALL_RETURN_ACTION);
        this.createEReference(this.callReturnActionEClass,
                CALL_RETURN_ACTION__RETURN_VARIABLE_USAGE_CALL_RETURN_ACTION);

        this.probabilisticBranchTransitionEClass = this.createEClass(PROBABILISTIC_BRANCH_TRANSITION);
        this.createEAttribute(this.probabilisticBranchTransitionEClass,
                PROBABILISTIC_BRANCH_TRANSITION__BRANCH_PROBABILITY);

        this.acquireActionEClass = this.createEClass(ACQUIRE_ACTION);
        this.createEReference(this.acquireActionEClass, ACQUIRE_ACTION__PASSIVERESOURCE_ACQUIRE_ACTION);
        this.createEAttribute(this.acquireActionEClass, ACQUIRE_ACTION__TIMEOUT);
        this.createEAttribute(this.acquireActionEClass, ACQUIRE_ACTION__TIMEOUT_VALUE);

        this.collectionIteratorActionEClass = this.createEClass(COLLECTION_ITERATOR_ACTION);
        this.createEReference(this.collectionIteratorActionEClass,
                COLLECTION_ITERATOR_ACTION__PARAMETER_COLLECTION_ITERATOR_ACTION);

        this.guardedBranchTransitionEClass = this.createEClass(GUARDED_BRANCH_TRANSITION);
        this.createEReference(this.guardedBranchTransitionEClass,
                GUARDED_BRANCH_TRANSITION__BRANCH_CONDITION_GUARDED_BRANCH_TRANSITION);

        this.setVariableActionEClass = this.createEClass(SET_VARIABLE_ACTION);
        this.createEReference(this.setVariableActionEClass,
                SET_VARIABLE_ACTION__LOCAL_VARIABLE_USAGES_SET_VARIABLE_ACTION);

        this.internalCallActionEClass = this.createEClass(INTERNAL_CALL_ACTION);
        this.createEReference(this.internalCallActionEClass,
                INTERNAL_CALL_ACTION__CALLED_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR);

        this.emitEventActionEClass = this.createEClass(EMIT_EVENT_ACTION);
        this.createEReference(this.emitEventActionEClass, EMIT_EVENT_ACTION__EVENT_TYPE_EMIT_EVENT_ACTION);
        this.createEReference(this.emitEventActionEClass, EMIT_EVENT_ACTION__SOURCE_ROLE_EMIT_EVENT_ACTION);

        this.internalActionEClass = this.createEClass(INTERNAL_ACTION);
        this.createEReference(this.internalActionEClass,
                INTERNAL_ACTION__INTERNAL_FAILURE_OCCURRENCE_DESCRIPTIONS_INTERNAL_ACTION);
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
        final SeffPerformancePackage theSeffPerformancePackage = (SeffPerformancePackage) EPackage.Registry.INSTANCE
                .getEPackage(SeffPerformancePackage.eNS_URI);
        final SeffReliabilityPackage theSeffReliabilityPackage = (SeffReliabilityPackage) EPackage.Registry.INSTANCE
                .getEPackage(SeffReliabilityPackage.eNS_URI);
        final EntityPackage theEntityPackage = (EntityPackage) EPackage.Registry.INSTANCE
                .getEPackage(EntityPackage.eNS_URI);
        final IdentifierPackage theIdentifierPackage = (IdentifierPackage) EPackage.Registry.INSTANCE
                .getEPackage(IdentifierPackage.eNS_URI);
        final ParameterPackage theParameterPackage = (ParameterPackage) EPackage.Registry.INSTANCE
                .getEPackage(ParameterPackage.eNS_URI);
        final RepositoryPackage theRepositoryPackage = (RepositoryPackage) EPackage.Registry.INSTANCE
                .getEPackage(RepositoryPackage.eNS_URI);
        final CorePackage theCorePackage = (CorePackage) EPackage.Registry.INSTANCE.getEPackage(CorePackage.eNS_URI);
        final ReliabilityPackage theReliabilityPackage = (ReliabilityPackage) EPackage.Registry.INSTANCE
                .getEPackage(ReliabilityPackage.eNS_URI);

        // Add subpackages
        this.getESubpackages().add(theSeffPerformancePackage);
        this.getESubpackages().add(theSeffReliabilityPackage);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.stopActionEClass.getESuperTypes().add(this.getAbstractInternalControlFlowAction());
        this.abstractInternalControlFlowActionEClass.getESuperTypes().add(this.getAbstractAction());
        this.abstractActionEClass.getESuperTypes().add(theEntityPackage.getEntity());
        this.resourceDemandingBehaviourEClass.getESuperTypes().add(theIdentifierPackage.getIdentifier());
        this.abstractLoopActionEClass.getESuperTypes().add(this.getAbstractInternalControlFlowAction());
        this.abstractBranchTransitionEClass.getESuperTypes().add(theEntityPackage.getEntity());
        this.branchActionEClass.getESuperTypes().add(this.getAbstractInternalControlFlowAction());
        this.callActionEClass.getESuperTypes().add(this.getAbstractAction());
        this.startActionEClass.getESuperTypes().add(this.getAbstractInternalControlFlowAction());
        this.resourceDemandingSEFFEClass.getESuperTypes().add(theIdentifierPackage.getIdentifier());
        this.resourceDemandingSEFFEClass.getESuperTypes().add(this.getServiceEffectSpecification());
        this.resourceDemandingSEFFEClass.getESuperTypes().add(this.getResourceDemandingBehaviour());
        this.resourceDemandingInternalBehaviourEClass.getESuperTypes().add(this.getResourceDemandingBehaviour());
        this.resourceDemandingInternalBehaviourEClass.getESuperTypes().add(theEntityPackage.getEntity());
        this.releaseActionEClass.getESuperTypes().add(this.getAbstractInternalControlFlowAction());
        this.loopActionEClass.getESuperTypes().add(this.getAbstractLoopAction());
        this.forkActionEClass.getESuperTypes().add(this.getAbstractInternalControlFlowAction());
        this.forkedBehaviourEClass.getESuperTypes().add(this.getResourceDemandingBehaviour());
        this.synchronisationPointEClass.getESuperTypes().add(theIdentifierPackage.getIdentifier());
        this.externalCallActionEClass.getESuperTypes().add(this.getAbstractAction());
        this.externalCallActionEClass.getESuperTypes().add(this.getCallReturnAction());
        this.externalCallActionEClass.getESuperTypes().add(theSeffReliabilityPackage.getFailureHandlingEntity());
        this.callReturnActionEClass.getESuperTypes().add(this.getCallAction());
        this.probabilisticBranchTransitionEClass.getESuperTypes().add(this.getAbstractBranchTransition());
        this.acquireActionEClass.getESuperTypes().add(this.getAbstractInternalControlFlowAction());
        this.collectionIteratorActionEClass.getESuperTypes().add(this.getAbstractLoopAction());
        this.guardedBranchTransitionEClass.getESuperTypes().add(this.getAbstractBranchTransition());
        this.setVariableActionEClass.getESuperTypes().add(this.getAbstractInternalControlFlowAction());
        this.internalCallActionEClass.getESuperTypes().add(this.getCallAction());
        this.internalCallActionEClass.getESuperTypes().add(this.getAbstractInternalControlFlowAction());
        this.emitEventActionEClass.getESuperTypes().add(this.getAbstractAction());
        this.emitEventActionEClass.getESuperTypes().add(this.getCallAction());
        this.internalActionEClass.getESuperTypes().add(this.getAbstractInternalControlFlowAction());

        // Initialize classes and features; add operations and parameters
        this.initEClass(this.stopActionEClass, StopAction.class, "StopAction", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        EOperation op = this.addEOperation(this.stopActionEClass, this.ecorePackage.getEBoolean(),
                "StopActionSuccessorMustNotBeDefined", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        EGenericType g1 = this.createEGenericType(this.ecorePackage.getEMap());
        EGenericType g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.abstractInternalControlFlowActionEClass, AbstractInternalControlFlowAction.class,
                "AbstractInternalControlFlowAction", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getAbstractInternalControlFlowAction_ResourceDemand_Action(),
                theSeffPerformancePackage.getParametricResourceDemand(),
                theSeffPerformancePackage.getParametricResourceDemand_Action_ParametricResourceDemand(),
                "resourceDemand_Action", null, 0, -1, AbstractInternalControlFlowAction.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);
        this.initEReference(this.getAbstractInternalControlFlowAction_InfrastructureCall__Action(),
                theSeffPerformancePackage.getInfrastructureCall(),
                theSeffPerformancePackage.getInfrastructureCall_Action__InfrastructureCall(),
                "infrastructureCall__Action", null, 0, -1, AbstractInternalControlFlowAction.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);
        this.initEReference(this.getAbstractInternalControlFlowAction_ResourceCall__Action(),
                theSeffPerformancePackage.getResourceCall(),
                theSeffPerformancePackage.getResourceCall_Action__ResourceCall(), "resourceCall__Action", null, 0, -1,
                AbstractInternalControlFlowAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.abstractActionEClass, AbstractAction.class, "AbstractAction", IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getAbstractAction_Predecessor_AbstractAction(), this.getAbstractAction(),
                this.getAbstractAction_Successor_AbstractAction(), "predecessor_AbstractAction", null, 0, 1,
                AbstractAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getAbstractAction_Successor_AbstractAction(), this.getAbstractAction(),
                this.getAbstractAction_Predecessor_AbstractAction(), "successor_AbstractAction", null, 0, 1,
                AbstractAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getAbstractAction_ResourceDemandingBehaviour_AbstractAction(),
                this.getResourceDemandingBehaviour(), this.getResourceDemandingBehaviour_Steps_Behaviour(),
                "resourceDemandingBehaviour_AbstractAction", null, 0, 1, AbstractAction.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);

        this.initEClass(this.resourceDemandingBehaviourEClass, ResourceDemandingBehaviour.class,
                "ResourceDemandingBehaviour", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getResourceDemandingBehaviour_AbstractLoopAction_ResourceDemandingBehaviour(),
                this.getAbstractLoopAction(), this.getAbstractLoopAction_BodyBehaviour_Loop(),
                "abstractLoopAction_ResourceDemandingBehaviour", null, 0, 1, ResourceDemandingBehaviour.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getResourceDemandingBehaviour_AbstractBranchTransition_ResourceDemandingBehaviour(),
                this.getAbstractBranchTransition(), this.getAbstractBranchTransition_BranchBehaviour_BranchTransition(),
                "abstractBranchTransition_ResourceDemandingBehaviour", null, 0, 1, ResourceDemandingBehaviour.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getResourceDemandingBehaviour_Steps_Behaviour(), this.getAbstractAction(),
                this.getAbstractAction_ResourceDemandingBehaviour_AbstractAction(), "steps_Behaviour", null, 0, -1,
                ResourceDemandingBehaviour.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        op = this.addEOperation(this.resourceDemandingBehaviourEClass, this.ecorePackage.getEBoolean(),
                "ExactlyOneStopAction", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = this.addEOperation(this.resourceDemandingBehaviourEClass, this.ecorePackage.getEBoolean(),
                "ExactlyOneStartAction", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = this.addEOperation(this.resourceDemandingBehaviourEClass, this.ecorePackage.getEBoolean(),
                "EachActionExceptStartActionandStopActionMustHhaveAPredecessorAndSuccessor", 0, 1, IS_UNIQUE,
                IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.abstractLoopActionEClass, AbstractLoopAction.class, "AbstractLoopAction", IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getAbstractLoopAction_BodyBehaviour_Loop(), this.getResourceDemandingBehaviour(),
                this.getResourceDemandingBehaviour_AbstractLoopAction_ResourceDemandingBehaviour(),
                "bodyBehaviour_Loop", null, 1, 1, AbstractLoopAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.abstractBranchTransitionEClass, AbstractBranchTransition.class, "AbstractBranchTransition",
                IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getAbstractBranchTransition_BranchAction_AbstractBranchTransition(),
                this.getBranchAction(), this.getBranchAction_Branches_Branch(), "branchAction_AbstractBranchTransition",
                null, 1, 1, AbstractBranchTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getAbstractBranchTransition_BranchBehaviour_BranchTransition(),
                this.getResourceDemandingBehaviour(),
                this.getResourceDemandingBehaviour_AbstractBranchTransition_ResourceDemandingBehaviour(),
                "branchBehaviour_BranchTransition", null, 1, 1, AbstractBranchTransition.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);

        this.initEClass(this.branchActionEClass, BranchAction.class, "BranchAction", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getBranchAction_Branches_Branch(), this.getAbstractBranchTransition(),
                this.getAbstractBranchTransition_BranchAction_AbstractBranchTransition(), "branches_Branch", null, 0,
                -1, BranchAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        op = this.addEOperation(this.branchActionEClass, this.ecorePackage.getEBoolean(),
                "EitherGuardedBranchesOrProbabilisiticBranchTransitions", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = this.addEOperation(this.branchActionEClass, this.ecorePackage.getEBoolean(),
                "AllProbabilisticBranchProbabilitiesMustSumUpTo1", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.callActionEClass, CallAction.class, "CallAction", IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getCallAction_InputVariableUsages__CallAction(),
                theParameterPackage.getVariableUsage(),
                theParameterPackage.getVariableUsage_CallAction__VariableUsage(), "inputVariableUsages__CallAction",
                null, 0, -1, CallAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.startActionEClass, StartAction.class, "StartAction", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        op = this.addEOperation(this.startActionEClass, this.ecorePackage.getEBoolean(),
                "StartActionPredecessorMustNotBeDefined", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.serviceEffectSpecificationEClass, ServiceEffectSpecification.class,
                "ServiceEffectSpecification", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getServiceEffectSpecification_SeffTypeID(), this.ecorePackage.getEString(),
                "seffTypeID", "1", 1, 1, ServiceEffectSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getServiceEffectSpecification_DescribedService__SEFF(),
                theRepositoryPackage.getSignature(), null, "describedService__SEFF", null, 1, 1,
                ServiceEffectSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getServiceEffectSpecification_BasicComponent_ServiceEffectSpecification(),
                theRepositoryPackage.getBasicComponent(),
                theRepositoryPackage.getBasicComponent_ServiceEffectSpecifications__BasicComponent(),
                "basicComponent_ServiceEffectSpecification", null, 1, 1, ServiceEffectSpecification.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        op = this.addEOperation(this.serviceEffectSpecificationEClass, this.ecorePackage.getEBoolean(),
                "ReferencedSignatureMustBelongToInterfaceReferencedByProvidedRole", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.resourceDemandingSEFFEClass, ResourceDemandingSEFF.class, "ResourceDemandingSEFF",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.resourceDemandingInternalBehaviourEClass, ResourceDemandingInternalBehaviour.class,
                "ResourceDemandingInternalBehaviour", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(
                this.getResourceDemandingInternalBehaviour_BasicComponent_ResourceDemandingInternalBehaviour(),
                theRepositoryPackage.getBasicComponent(),
                theRepositoryPackage.getBasicComponent_ResourceDemandingInternalBehaviours__BasicComponent(),
                "basicComponent_ResourceDemandingInternalBehaviour", null, 1, 1,
                ResourceDemandingInternalBehaviour.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.releaseActionEClass, ReleaseAction.class, "ReleaseAction", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getReleaseAction_PassiveResource_ReleaseAction(),
                theRepositoryPackage.getPassiveResource(), null, "passiveResource_ReleaseAction", null, 1, 1,
                ReleaseAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.loopActionEClass, LoopAction.class, "LoopAction", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getLoopAction_IterationCount_LoopAction(), theCorePackage.getPCMRandomVariable(),
                theCorePackage.getPCMRandomVariable_LoopAction_PCMRandomVariable(), "iterationCount_LoopAction", null,
                1, 1, LoopAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.forkActionEClass, ForkAction.class, "ForkAction", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getForkAction_AsynchronousForkedBehaviours_ForkAction(), this.getForkedBehaviour(),
                this.getForkedBehaviour_ForkAction_ForkedBehaivour(), "asynchronousForkedBehaviours_ForkAction", null,
                0, -1, ForkAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getForkAction_SynchronisingBehaviours_ForkAction(), this.getSynchronisationPoint(),
                this.getSynchronisationPoint_ForkAction_SynchronisationPoint(), "synchronisingBehaviours_ForkAction",
                null, 0, 1, ForkAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.forkedBehaviourEClass, ForkedBehaviour.class, "ForkedBehaviour", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getForkedBehaviour_SynchronisationPoint_ForkedBehaviour(),
                this.getSynchronisationPoint(),
                this.getSynchronisationPoint_SynchronousForkedBehaviours_SynchronisationPoint(),
                "synchronisationPoint_ForkedBehaviour", null, 0, 1, ForkedBehaviour.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getForkedBehaviour_ForkAction_ForkedBehaivour(), this.getForkAction(),
                this.getForkAction_AsynchronousForkedBehaviours_ForkAction(), "forkAction_ForkedBehaivour", null, 0, 1,
                ForkedBehaviour.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.synchronisationPointEClass, SynchronisationPoint.class, "SynchronisationPoint",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getSynchronisationPoint_OutputParameterUsage_SynchronisationPoint(),
                theParameterPackage.getVariableUsage(),
                theParameterPackage.getVariableUsage_SynchronisationPoint_VariableUsage(),
                "outputParameterUsage_SynchronisationPoint", null, 0, -1, SynchronisationPoint.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);
        this.initEReference(this.getSynchronisationPoint_ForkAction_SynchronisationPoint(), this.getForkAction(),
                this.getForkAction_SynchronisingBehaviours_ForkAction(), "forkAction_SynchronisationPoint", null, 1, 1,
                SynchronisationPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getSynchronisationPoint_SynchronousForkedBehaviours_SynchronisationPoint(),
                this.getForkedBehaviour(), this.getForkedBehaviour_SynchronisationPoint_ForkedBehaviour(),
                "synchronousForkedBehaviours_SynchronisationPoint", null, 1, -1, SynchronisationPoint.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.externalCallActionEClass, ExternalCallAction.class, "ExternalCallAction", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getExternalCallAction_CalledService_ExternalService(),
                theRepositoryPackage.getOperationSignature(), null, "calledService_ExternalService", null, 1, 1,
                ExternalCallAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getExternalCallAction_Role_ExternalService(),
                theRepositoryPackage.getOperationRequiredRole(), null, "role_ExternalService", null, 1, 1,
                ExternalCallAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEAttribute(this.getExternalCallAction_RetryCount(), this.ecorePackage.getEInt(), "retryCount", null, 1,
                1, ExternalCallAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        op = this.addEOperation(this.externalCallActionEClass, this.ecorePackage.getEBoolean(),
                "SignatureBelongsToRole", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = this.addEOperation(this.externalCallActionEClass, this.ecorePackage.getEBoolean(),
                "OperationRequiredRoleMustBeReferencedByContainer", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.callReturnActionEClass, CallReturnAction.class, "CallReturnAction", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getCallReturnAction_ReturnVariableUsage__CallReturnAction(),
                theParameterPackage.getVariableUsage(),
                theParameterPackage.getVariableUsage_CallReturnAction__VariableUsage(),
                "returnVariableUsage__CallReturnAction", null, 0, -1, CallReturnAction.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);

        this.initEClass(this.probabilisticBranchTransitionEClass, ProbabilisticBranchTransition.class,
                "ProbabilisticBranchTransition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getProbabilisticBranchTransition_BranchProbability(), this.ecorePackage.getEDouble(),
                "branchProbability", null, 1, 1, ProbabilisticBranchTransition.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.acquireActionEClass, AcquireAction.class, "AcquireAction", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getAcquireAction_Passiveresource_AcquireAction(),
                theRepositoryPackage.getPassiveResource(), null, "passiveresource_AcquireAction", null, 1, 1,
                AcquireAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEAttribute(this.getAcquireAction_Timeout(), this.ecorePackage.getEBoolean(), "timeout", null, 1, 1,
                AcquireAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, !IS_ORDERED);
        this.initEAttribute(this.getAcquireAction_TimeoutValue(), this.ecorePackage.getEDouble(), "timeoutValue", null,
                1, 1, AcquireAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        op = this.addEOperation(this.acquireActionEClass, this.ecorePackage.getEBoolean(),
                "TimeoutValueOfAcquireActionMustNotBeNegative", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.collectionIteratorActionEClass, CollectionIteratorAction.class, "CollectionIteratorAction",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getCollectionIteratorAction_Parameter_CollectionIteratorAction(),
                theRepositoryPackage.getParameter(), null, "parameter_CollectionIteratorAction", null, 1, 1,
                CollectionIteratorAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.guardedBranchTransitionEClass, GuardedBranchTransition.class, "GuardedBranchTransition",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getGuardedBranchTransition_BranchCondition_GuardedBranchTransition(),
                theCorePackage.getPCMRandomVariable(),
                theCorePackage.getPCMRandomVariable_GuardedBranchTransition_PCMRandomVariable(),
                "branchCondition_GuardedBranchTransition", null, 1, 1, GuardedBranchTransition.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);

        this.initEClass(this.setVariableActionEClass, SetVariableAction.class, "SetVariableAction", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getSetVariableAction_LocalVariableUsages_SetVariableAction(),
                theParameterPackage.getVariableUsage(),
                theParameterPackage.getVariableUsage_SetVariableAction_VariableUsage(),
                "localVariableUsages_SetVariableAction", null, 0, -1, SetVariableAction.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);

        this.initEClass(this.internalCallActionEClass, InternalCallAction.class, "InternalCallAction", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getInternalCallAction_CalledResourceDemandingInternalBehaviour(),
                this.getResourceDemandingBehaviour(), null, "calledResourceDemandingInternalBehaviour", null, 1, 1,
                InternalCallAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.emitEventActionEClass, EmitEventAction.class, "EmitEventAction", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getEmitEventAction_EventType__EmitEventAction(), theRepositoryPackage.getEventType(),
                null, "eventType__EmitEventAction", null, 1, 1, EmitEventAction.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getEmitEventAction_SourceRole__EmitEventAction(), theRepositoryPackage.getSourceRole(),
                null, "sourceRole__EmitEventAction", null, 1, 1, EmitEventAction.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        this.initEClass(this.internalActionEClass, InternalAction.class, "InternalAction", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getInternalAction_InternalFailureOccurrenceDescriptions__InternalAction(),
                theReliabilityPackage.getInternalFailureOccurrenceDescription(),
                theReliabilityPackage
                        .getInternalFailureOccurrenceDescription_InternalAction__InternalFailureOccurrenceDescription(),
                "internalFailureOccurrenceDescriptions__InternalAction", null, 0, -1, InternalAction.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        op = this.addEOperation(this.internalActionEClass, this.ecorePackage.getEBoolean(),
                "MultipleInternalOccurrenceDescriptionsPerFailureTypeNotAllowed", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = this.addEOperation(this.internalActionEClass, this.ecorePackage.getEBoolean(),
                "SumOfInternalActionFailureProbabilitiesMustNotExceed1", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        g1 = this.createEGenericType(this.ecorePackage.getEMap());
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);
    }

} // SeffPackageImpl
