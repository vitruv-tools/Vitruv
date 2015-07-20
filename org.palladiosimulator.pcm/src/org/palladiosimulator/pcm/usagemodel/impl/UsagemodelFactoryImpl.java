/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.usagemodel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.pcm.usagemodel.Branch;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.ClosedWorkload;
import org.palladiosimulator.pcm.usagemodel.Delay;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.Loop;
import org.palladiosimulator.pcm.usagemodel.OpenWorkload;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.Start;
import org.palladiosimulator.pcm.usagemodel.Stop;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;
import org.palladiosimulator.pcm.usagemodel.UsagemodelPackage;
import org.palladiosimulator.pcm.usagemodel.UserData;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class UsagemodelFactoryImpl extends EFactoryImpl implements UsagemodelFactory {

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
    public static UsagemodelFactory init() {
        try {
            final UsagemodelFactory theUsagemodelFactory = (UsagemodelFactory) EPackage.Registry.INSTANCE
                    .getEFactory(UsagemodelPackage.eNS_URI);
            if (theUsagemodelFactory != null) {
                return theUsagemodelFactory;
            }
        } catch (final Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new UsagemodelFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public UsagemodelFactoryImpl() {
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
        case UsagemodelPackage.USAGE_SCENARIO:
            return this.createUsageScenario();
        case UsagemodelPackage.USER_DATA:
            return this.createUserData();
        case UsagemodelPackage.USAGE_MODEL:
            return this.createUsageModel();
        case UsagemodelPackage.ENTRY_LEVEL_SYSTEM_CALL:
            return this.createEntryLevelSystemCall();
        case UsagemodelPackage.SCENARIO_BEHAVIOUR:
            return this.createScenarioBehaviour();
        case UsagemodelPackage.BRANCH_TRANSITION:
            return this.createBranchTransition();
        case UsagemodelPackage.BRANCH:
            return this.createBranch();
        case UsagemodelPackage.LOOP:
            return this.createLoop();
        case UsagemodelPackage.STOP:
            return this.createStop();
        case UsagemodelPackage.START:
            return this.createStart();
        case UsagemodelPackage.OPEN_WORKLOAD:
            return this.createOpenWorkload();
        case UsagemodelPackage.DELAY:
            return this.createDelay();
        case UsagemodelPackage.CLOSED_WORKLOAD:
            return this.createClosedWorkload();
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
    public UsageScenario createUsageScenario() {
        final UsageScenarioImpl usageScenario = new UsageScenarioImpl();
        return usageScenario;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public UserData createUserData() {
        final UserDataImpl userData = new UserDataImpl();
        return userData;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public UsageModel createUsageModel() {
        final UsageModelImpl usageModel = new UsageModelImpl();
        return usageModel;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EntryLevelSystemCall createEntryLevelSystemCall() {
        final EntryLevelSystemCallImpl entryLevelSystemCall = new EntryLevelSystemCallImpl();
        return entryLevelSystemCall;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ScenarioBehaviour createScenarioBehaviour() {
        final ScenarioBehaviourImpl scenarioBehaviour = new ScenarioBehaviourImpl();
        return scenarioBehaviour;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public BranchTransition createBranchTransition() {
        final BranchTransitionImpl branchTransition = new BranchTransitionImpl();
        return branchTransition;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Branch createBranch() {
        final BranchImpl branch = new BranchImpl();
        return branch;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Loop createLoop() {
        final LoopImpl loop = new LoopImpl();
        return loop;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Stop createStop() {
        final StopImpl stop = new StopImpl();
        return stop;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Start createStart() {
        final StartImpl start = new StartImpl();
        return start;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public OpenWorkload createOpenWorkload() {
        final OpenWorkloadImpl openWorkload = new OpenWorkloadImpl();
        return openWorkload;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Delay createDelay() {
        final DelayImpl delay = new DelayImpl();
        return delay;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ClosedWorkload createClosedWorkload() {
        final ClosedWorkloadImpl closedWorkload = new ClosedWorkloadImpl();
        return closedWorkload;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public UsagemodelPackage getUsagemodelPackage() {
        return (UsagemodelPackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @deprecated
     * @generated
     */
    @Deprecated
    public static UsagemodelPackage getPackage() {
        return UsagemodelPackage.eINSTANCE;
    }

} // UsagemodelFactoryImpl
