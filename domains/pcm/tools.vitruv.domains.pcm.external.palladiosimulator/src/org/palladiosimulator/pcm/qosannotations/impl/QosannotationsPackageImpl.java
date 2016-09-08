/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.qosannotations.impl;

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
import org.palladiosimulator.pcm.qosannotations.QoSAnnotations;
import org.palladiosimulator.pcm.qosannotations.QosannotationsFactory;
import org.palladiosimulator.pcm.qosannotations.QosannotationsPackage;
import org.palladiosimulator.pcm.qosannotations.SpecifiedOutputParameterAbstraction;
import org.palladiosimulator.pcm.qosannotations.SpecifiedQoSAnnotation;
import org.palladiosimulator.pcm.qosannotations.qos_performance.QosPerformancePackage;
import org.palladiosimulator.pcm.qosannotations.qos_performance.impl.QosPerformancePackageImpl;
import org.palladiosimulator.pcm.qosannotations.qos_reliability.QosReliabilityPackage;
import org.palladiosimulator.pcm.qosannotations.qos_reliability.impl.QosReliabilityPackageImpl;
import org.palladiosimulator.pcm.qosannotations.util.QosannotationsValidator;
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
public class QosannotationsPackageImpl extends EPackageImpl implements QosannotationsPackage {

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
    private EClass specifiedQoSAnnotationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass qoSAnnotationsEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass specifiedOutputParameterAbstractionEClass = null;

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
     * @see org.palladiosimulator.pcm.qosannotations.QosannotationsPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private QosannotationsPackageImpl() {
        super(eNS_URI, QosannotationsFactory.eINSTANCE);
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
     * This method is used to initialize {@link QosannotationsPackage#eINSTANCE} when that field is
     * accessed. Clients should not invoke it directly. Instead, they should simply access that
     * field to obtain the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static QosannotationsPackage init() {
        if (isInited) {
            return (QosannotationsPackage) EPackage.Registry.INSTANCE.getEPackage(QosannotationsPackage.eNS_URI);
        }

        // Obtain or create and register package
        final QosannotationsPackageImpl theQosannotationsPackage = (QosannotationsPackageImpl) (EPackage.Registry.INSTANCE
                .get(eNS_URI) instanceof QosannotationsPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
                        : new QosannotationsPackageImpl());

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
        theQosannotationsPackage.createPackageContents();
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
        theSeffPackage.createPackageContents();
        theSeffPerformancePackage.createPackageContents();
        theSeffReliabilityPackage.createPackageContents();
        theQosPerformancePackage.createPackageContents();
        theQosReliabilityPackage.createPackageContents();
        theSystemPackage.createPackageContents();
        theResourceenvironmentPackage.createPackageContents();
        theAllocationPackage.createPackageContents();
        theSubsystemPackage.createPackageContents();

        // Initialize created meta-data
        theQosannotationsPackage.initializePackageContents();
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
        theSeffPackage.initializePackageContents();
        theSeffPerformancePackage.initializePackageContents();
        theSeffReliabilityPackage.initializePackageContents();
        theQosPerformancePackage.initializePackageContents();
        theQosReliabilityPackage.initializePackageContents();
        theSystemPackage.initializePackageContents();
        theResourceenvironmentPackage.initializePackageContents();
        theAllocationPackage.initializePackageContents();
        theSubsystemPackage.initializePackageContents();

        // Register package validator
        EValidator.Registry.INSTANCE.put(theQosannotationsPackage, new EValidator.Descriptor() {
            @Override
            public EValidator getEValidator() {
                return QosannotationsValidator.INSTANCE;
            }
        });

        // Mark meta-data to indicate it can't be changed
        theQosannotationsPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(QosannotationsPackage.eNS_URI, theQosannotationsPackage);
        return theQosannotationsPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getSpecifiedQoSAnnotation() {
        return this.specifiedQoSAnnotationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getSpecifiedQoSAnnotation_Signature_SpecifiedQoSAnnation() {
        return (EReference) this.specifiedQoSAnnotationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getSpecifiedQoSAnnotation_Role_SpecifiedQoSAnnotation() {
        return (EReference) this.specifiedQoSAnnotationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getSpecifiedQoSAnnotation_QosAnnotations_SpecifiedQoSAnnotation() {
        return (EReference) this.specifiedQoSAnnotationEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getQoSAnnotations() {
        return this.qoSAnnotationsEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getQoSAnnotations_SpecifiedOutputParameterAbstractions_QoSAnnotations() {
        return (EReference) this.qoSAnnotationsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getQoSAnnotations_System_QoSAnnotations() {
        return (EReference) this.qoSAnnotationsEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getQoSAnnotations_SpecifiedQoSAnnotations_QoSAnnotations() {
        return (EReference) this.qoSAnnotationsEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getSpecifiedOutputParameterAbstraction() {
        return this.specifiedOutputParameterAbstractionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getSpecifiedOutputParameterAbstraction_Signature_SpecifiedOutputParameterAbstraction() {
        return (EReference) this.specifiedOutputParameterAbstractionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getSpecifiedOutputParameterAbstraction_Role_SpecifiedOutputParameterAbstraction() {
        return (EReference) this.specifiedOutputParameterAbstractionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getSpecifiedOutputParameterAbstraction_ExpectedExternalOutputs_SpecifiedOutputParameterAbstraction() {
        return (EReference) this.specifiedOutputParameterAbstractionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getSpecifiedOutputParameterAbstraction_QosAnnotations_SpecifiedOutputParameterAbstraction() {
        return (EReference) this.specifiedOutputParameterAbstractionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public QosannotationsFactory getQosannotationsFactory() {
        return (QosannotationsFactory) this.getEFactoryInstance();
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
        this.specifiedQoSAnnotationEClass = this.createEClass(SPECIFIED_QO_SANNOTATION);
        this.createEReference(this.specifiedQoSAnnotationEClass,
                SPECIFIED_QO_SANNOTATION__SIGNATURE_SPECIFIED_QO_SANNATION);
        this.createEReference(this.specifiedQoSAnnotationEClass,
                SPECIFIED_QO_SANNOTATION__ROLE_SPECIFIED_QO_SANNOTATION);
        this.createEReference(this.specifiedQoSAnnotationEClass,
                SPECIFIED_QO_SANNOTATION__QOS_ANNOTATIONS_SPECIFIED_QO_SANNOTATION);

        this.qoSAnnotationsEClass = this.createEClass(QO_SANNOTATIONS);
        this.createEReference(this.qoSAnnotationsEClass,
                QO_SANNOTATIONS__SPECIFIED_OUTPUT_PARAMETER_ABSTRACTIONS_QO_SANNOTATIONS);
        this.createEReference(this.qoSAnnotationsEClass, QO_SANNOTATIONS__SYSTEM_QO_SANNOTATIONS);
        this.createEReference(this.qoSAnnotationsEClass, QO_SANNOTATIONS__SPECIFIED_QO_SANNOTATIONS_QO_SANNOTATIONS);

        this.specifiedOutputParameterAbstractionEClass = this.createEClass(SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION);
        this.createEReference(this.specifiedOutputParameterAbstractionEClass,
                SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__SIGNATURE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION);
        this.createEReference(this.specifiedOutputParameterAbstractionEClass,
                SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__ROLE_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION);
        this.createEReference(this.specifiedOutputParameterAbstractionEClass,
                SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__EXPECTED_EXTERNAL_OUTPUTS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION);
        this.createEReference(this.specifiedOutputParameterAbstractionEClass,
                SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__QOS_ANNOTATIONS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION);
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
        final QosPerformancePackage theQosPerformancePackage = (QosPerformancePackage) EPackage.Registry.INSTANCE
                .getEPackage(QosPerformancePackage.eNS_URI);
        final QosReliabilityPackage theQosReliabilityPackage = (QosReliabilityPackage) EPackage.Registry.INSTANCE
                .getEPackage(QosReliabilityPackage.eNS_URI);
        final RepositoryPackage theRepositoryPackage = (RepositoryPackage) EPackage.Registry.INSTANCE
                .getEPackage(RepositoryPackage.eNS_URI);
        final EntityPackage theEntityPackage = (EntityPackage) EPackage.Registry.INSTANCE
                .getEPackage(EntityPackage.eNS_URI);
        final SystemPackage theSystemPackage = (SystemPackage) EPackage.Registry.INSTANCE
                .getEPackage(SystemPackage.eNS_URI);
        final ParameterPackage theParameterPackage = (ParameterPackage) EPackage.Registry.INSTANCE
                .getEPackage(ParameterPackage.eNS_URI);

        // Add subpackages
        this.getESubpackages().add(theQosPerformancePackage);
        this.getESubpackages().add(theQosReliabilityPackage);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.qoSAnnotationsEClass.getESuperTypes().add(theEntityPackage.getEntity());

        // Initialize classes and features; add operations and parameters
        this.initEClass(this.specifiedQoSAnnotationEClass, SpecifiedQoSAnnotation.class, "SpecifiedQoSAnnotation",
                IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getSpecifiedQoSAnnotation_Signature_SpecifiedQoSAnnation(),
                theRepositoryPackage.getSignature(), null, "signature_SpecifiedQoSAnnation", null, 1, 1,
                SpecifiedQoSAnnotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getSpecifiedQoSAnnotation_Role_SpecifiedQoSAnnotation(),
                theRepositoryPackage.getRole(), null, "role_SpecifiedQoSAnnotation", null, 1, 1,
                SpecifiedQoSAnnotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getSpecifiedQoSAnnotation_QosAnnotations_SpecifiedQoSAnnotation(),
                this.getQoSAnnotations(), this.getQoSAnnotations_SpecifiedQoSAnnotations_QoSAnnotations(),
                "qosAnnotations_SpecifiedQoSAnnotation", null, 1, 1, SpecifiedQoSAnnotation.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);

        this.initEClass(this.qoSAnnotationsEClass, QoSAnnotations.class, "QoSAnnotations", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getQoSAnnotations_SpecifiedOutputParameterAbstractions_QoSAnnotations(),
                this.getSpecifiedOutputParameterAbstraction(),
                this.getSpecifiedOutputParameterAbstraction_QosAnnotations_SpecifiedOutputParameterAbstraction(),
                "specifiedOutputParameterAbstractions_QoSAnnotations", null, 0, -1, QoSAnnotations.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);
        this.initEReference(this.getQoSAnnotations_System_QoSAnnotations(), theSystemPackage.getSystem(),
                theSystemPackage.getSystem_QosAnnotations_System(), "system_QoSAnnotations", null, 1, 1,
                QoSAnnotations.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getQoSAnnotations_SpecifiedQoSAnnotations_QoSAnnotations(),
                this.getSpecifiedQoSAnnotation(),
                this.getSpecifiedQoSAnnotation_QosAnnotations_SpecifiedQoSAnnotation(),
                "specifiedQoSAnnotations_QoSAnnotations", null, 0, -1, QoSAnnotations.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                !IS_ORDERED);

        final EOperation op = this.addEOperation(this.qoSAnnotationsEClass, this.ecorePackage.getEBoolean(),
                "MultipleReliabilityAnnotationsPerExternalCallNotAllowed", 0, 1, IS_UNIQUE, IS_ORDERED);
        this.addEParameter(op, this.ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
        final EGenericType g1 = this.createEGenericType(this.ecorePackage.getEMap());
        EGenericType g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        g2 = this.createEGenericType(this.ecorePackage.getEJavaObject());
        g1.getETypeArguments().add(g2);
        this.addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

        this.initEClass(this.specifiedOutputParameterAbstractionEClass, SpecifiedOutputParameterAbstraction.class,
                "SpecifiedOutputParameterAbstraction", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getSpecifiedOutputParameterAbstraction_Signature_SpecifiedOutputParameterAbstraction(),
                theRepositoryPackage.getSignature(), null, "signature_SpecifiedOutputParameterAbstraction", null, 1, 1,
                SpecifiedOutputParameterAbstraction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(this.getSpecifiedOutputParameterAbstraction_Role_SpecifiedOutputParameterAbstraction(),
                theRepositoryPackage.getRole(), null, "role_SpecifiedOutputParameterAbstraction", null, 1, 1,
                SpecifiedOutputParameterAbstraction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(
                this.getSpecifiedOutputParameterAbstraction_ExpectedExternalOutputs_SpecifiedOutputParameterAbstraction(),
                theParameterPackage.getVariableUsage(),
                theParameterPackage
                        .getVariableUsage_SpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage(),
                "expectedExternalOutputs_SpecifiedOutputParameterAbstraction", null, 0, -1,
                SpecifiedOutputParameterAbstraction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
        this.initEReference(
                this.getSpecifiedOutputParameterAbstraction_QosAnnotations_SpecifiedOutputParameterAbstraction(),
                this.getQoSAnnotations(), this.getQoSAnnotations_SpecifiedOutputParameterAbstractions_QoSAnnotations(),
                "qosAnnotations_SpecifiedOutputParameterAbstraction", null, 1, 1,
                SpecifiedOutputParameterAbstraction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
    }

} // QosannotationsPackageImpl
