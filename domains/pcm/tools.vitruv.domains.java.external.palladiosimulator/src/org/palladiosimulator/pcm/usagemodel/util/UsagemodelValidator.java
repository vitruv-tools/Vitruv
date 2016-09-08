/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.usagemodel.util;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
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
import org.palladiosimulator.pcm.usagemodel.UsagemodelPackage;
import org.palladiosimulator.pcm.usagemodel.UserData;
import org.palladiosimulator.pcm.usagemodel.Workload;

/**
 * <!-- begin-user-doc --> The <b>Validator</b> for the model. <!-- end-user-doc -->
 * 
 * @see org.palladiosimulator.pcm.usagemodel.UsagemodelPackage
 * @generated
 */
public class UsagemodelValidator extends EObjectValidator {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final UsagemodelValidator INSTANCE = new UsagemodelValidator();

    /**
     * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of
     * diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.emf.common.util.Diagnostic#getSource()
     * @see org.eclipse.emf.common.util.Diagnostic#getCode()
     * @generated
     */
    public static final String DIAGNOSTIC_SOURCE = "org.palladiosimulator.pcm.usagemodel";

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Entry Level
     * System Call Must Reference Provided Role Of ASystem' of 'Entry Level System Call'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int ENTRY_LEVEL_SYSTEM_CALL__ENTRY_LEVEL_SYSTEM_CALL_MUST_REFERENCE_PROVIDED_ROLE_OF_ASYSTEM = 1;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Entry Level
     * System Call Signature Must Match Its Provided Role' of 'Entry Level System Call'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int ENTRY_LEVEL_SYSTEM_CALL__ENTRY_LEVEL_SYSTEM_CALL_SIGNATURE_MUST_MATCH_ITS_PROVIDED_ROLE = 2;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint
     * 'Exactlyonestart' of 'Scenario Behaviour'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SCENARIO_BEHAVIOUR__EXACTLYONESTART = 3;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint
     * 'Exactlyonestop' of 'Scenario Behaviour'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SCENARIO_BEHAVIOUR__EXACTLYONESTOP = 4;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint
     * 'Eachuseractionexcept Startand Stopmusthaveapredecessorandsuccessor' of 'Scenario Behaviour'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SCENARIO_BEHAVIOUR__EACHUSERACTIONEXCEPT_STARTAND_STOPMUSTHAVEAPREDECESSORANDSUCCESSOR = 5;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'All Branch
     * Probabilities Must Sum Up To1' of 'Branch'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int BRANCH__ALL_BRANCH_PROBABILITIES_MUST_SUM_UP_TO1 = 6;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Stop Has No
     * Successor' of 'Stop'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int STOP__STOP_HAS_NO_SUCCESSOR = 7;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Start Has
     * No Predecessor' of 'Start'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int START__START_HAS_NO_PREDECESSOR = 8;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Inter
     * Arrival Time In Open Workload Needs To Be Specified' of 'Open Workload'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int OPEN_WORKLOAD__INTER_ARRIVAL_TIME_IN_OPEN_WORKLOAD_NEEDS_TO_BE_SPECIFIED = 9;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Population
     * In Closed Workload Needs To Be Specified' of 'Closed Workload'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public static final int CLOSED_WORKLOAD__POPULATION_IN_CLOSED_WORKLOAD_NEEDS_TO_BE_SPECIFIED = 10;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Think Time
     * In Closed Workload Needs To Be Specified' of 'Closed Workload'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public static final int CLOSED_WORKLOAD__THINK_TIME_IN_CLOSED_WORKLOAD_NEEDS_TO_BE_SPECIFIED = 11;

    /**
     * A constant with a fixed name that can be used as the base value for additional hand written
     * constants. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 11;

    /**
     * A constant with a fixed name that can be used as the base value for additional hand written
     * constants in a derived class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public UsagemodelValidator() {
        super();
    }

    /**
     * Returns the package of this validator switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EPackage getEPackage() {
        return UsagemodelPackage.eINSTANCE;
    }

    /**
     * Calls <code>validateXXX</code> for the corresponding classifier of the model. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected boolean validate(final int classifierID, final Object value, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        switch (classifierID) {
        case UsagemodelPackage.WORKLOAD:
            return this.validateWorkload((Workload) value, diagnostics, context);
        case UsagemodelPackage.USAGE_SCENARIO:
            return this.validateUsageScenario((UsageScenario) value, diagnostics, context);
        case UsagemodelPackage.USER_DATA:
            return this.validateUserData((UserData) value, diagnostics, context);
        case UsagemodelPackage.USAGE_MODEL:
            return this.validateUsageModel((UsageModel) value, diagnostics, context);
        case UsagemodelPackage.ENTRY_LEVEL_SYSTEM_CALL:
            return this.validateEntryLevelSystemCall((EntryLevelSystemCall) value, diagnostics, context);
        case UsagemodelPackage.ABSTRACT_USER_ACTION:
            return this.validateAbstractUserAction((AbstractUserAction) value, diagnostics, context);
        case UsagemodelPackage.SCENARIO_BEHAVIOUR:
            return this.validateScenarioBehaviour((ScenarioBehaviour) value, diagnostics, context);
        case UsagemodelPackage.BRANCH_TRANSITION:
            return this.validateBranchTransition((BranchTransition) value, diagnostics, context);
        case UsagemodelPackage.BRANCH:
            return this.validateBranch((Branch) value, diagnostics, context);
        case UsagemodelPackage.LOOP:
            return this.validateLoop((Loop) value, diagnostics, context);
        case UsagemodelPackage.STOP:
            return this.validateStop((Stop) value, diagnostics, context);
        case UsagemodelPackage.START:
            return this.validateStart((Start) value, diagnostics, context);
        case UsagemodelPackage.OPEN_WORKLOAD:
            return this.validateOpenWorkload((OpenWorkload) value, diagnostics, context);
        case UsagemodelPackage.DELAY:
            return this.validateDelay((Delay) value, diagnostics, context);
        case UsagemodelPackage.CLOSED_WORKLOAD:
            return this.validateClosedWorkload((ClosedWorkload) value, diagnostics, context);
        default:
            return true;
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateWorkload(final Workload workload, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(workload, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateUsageScenario(final UsageScenario usageScenario, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(usageScenario, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateUserData(final UserData userData, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(userData, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateUsageModel(final UsageModel usageModel, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(usageModel, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateEntryLevelSystemCall(final EntryLevelSystemCall entryLevelSystemCall,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(entryLevelSystemCall, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(entryLevelSystemCall, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(entryLevelSystemCall, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(entryLevelSystemCall, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(entryLevelSystemCall, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(entryLevelSystemCall, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(entryLevelSystemCall, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(entryLevelSystemCall, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateEntryLevelSystemCall_EntryLevelSystemCallMustReferenceProvidedRoleOfASystem(
                    entryLevelSystemCall, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateEntryLevelSystemCall_EntryLevelSystemCallSignatureMustMatchItsProvidedRole(
                    entryLevelSystemCall, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the EntryLevelSystemCallMustReferenceProvidedRoleOfASystem constraint of '
     * <em>Entry Level System Call</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateEntryLevelSystemCall_EntryLevelSystemCallMustReferenceProvidedRoleOfASystem(
            final EntryLevelSystemCall entryLevelSystemCall, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return entryLevelSystemCall.EntryLevelSystemCallMustReferenceProvidedRoleOfASystem(diagnostics, context);
    }

    /**
     * Validates the EntryLevelSystemCallSignatureMustMatchItsProvidedRole constraint of '
     * <em>Entry Level System Call</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateEntryLevelSystemCall_EntryLevelSystemCallSignatureMustMatchItsProvidedRole(
            final EntryLevelSystemCall entryLevelSystemCall, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return entryLevelSystemCall.EntryLevelSystemCallSignatureMustMatchItsProvidedRole(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateAbstractUserAction(final AbstractUserAction abstractUserAction,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(abstractUserAction, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateScenarioBehaviour(final ScenarioBehaviour scenarioBehaviour,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(scenarioBehaviour, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(scenarioBehaviour, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(scenarioBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(scenarioBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(scenarioBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(scenarioBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(scenarioBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(scenarioBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateScenarioBehaviour_Exactlyonestart(scenarioBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateScenarioBehaviour_Exactlyonestop(scenarioBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateScenarioBehaviour_EachuseractionexceptStartandStopmusthaveapredecessorandsuccessor(
                    scenarioBehaviour, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the Exactlyonestart constraint of '<em>Scenario Behaviour</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateScenarioBehaviour_Exactlyonestart(final ScenarioBehaviour scenarioBehaviour,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return scenarioBehaviour.Exactlyonestart(diagnostics, context);
    }

    /**
     * Validates the Exactlyonestop constraint of '<em>Scenario Behaviour</em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateScenarioBehaviour_Exactlyonestop(final ScenarioBehaviour scenarioBehaviour,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return scenarioBehaviour.Exactlyonestop(diagnostics, context);
    }

    /**
     * Validates the EachuseractionexceptStartandStopmusthaveapredecessorandsuccessor constraint of
     * '<em>Scenario Behaviour</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateScenarioBehaviour_EachuseractionexceptStartandStopmusthaveapredecessorandsuccessor(
            final ScenarioBehaviour scenarioBehaviour, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return scenarioBehaviour.EachuseractionexceptStartandStopmusthaveapredecessorandsuccessor(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateBranchTransition(final BranchTransition branchTransition, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(branchTransition, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateBranch(final Branch branch, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(branch, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(branch, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(branch, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(branch, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(branch, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(branch, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(branch, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(branch, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateBranch_AllBranchProbabilitiesMustSumUpTo1(branch, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the AllBranchProbabilitiesMustSumUpTo1 constraint of '<em>Branch</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateBranch_AllBranchProbabilitiesMustSumUpTo1(final Branch branch,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return branch.AllBranchProbabilitiesMustSumUpTo1(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateLoop(final Loop loop, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(loop, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateStop(final Stop stop, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(stop, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(stop, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(stop, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(stop, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(stop, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(stop, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(stop, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(stop, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateStop_StopHasNoSuccessor(stop, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the StopHasNoSuccessor constraint of '<em>Stop</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public boolean validateStop_StopHasNoSuccessor(final Stop stop, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return stop.StopHasNoSuccessor(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateStart(final Start start, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(start, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(start, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(start, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(start, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(start, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(start, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(start, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(start, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateStart_StartHasNoPredecessor(start, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the StartHasNoPredecessor constraint of '<em>Start</em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateStart_StartHasNoPredecessor(final Start start, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return start.StartHasNoPredecessor(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateOpenWorkload(final OpenWorkload openWorkload, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(openWorkload, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(openWorkload, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(openWorkload, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(openWorkload, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(openWorkload, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(openWorkload, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(openWorkload, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(openWorkload, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateOpenWorkload_InterArrivalTimeInOpenWorkloadNeedsToBeSpecified(openWorkload,
                    diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the InterArrivalTimeInOpenWorkloadNeedsToBeSpecified constraint of '
     * <em>Open Workload</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateOpenWorkload_InterArrivalTimeInOpenWorkloadNeedsToBeSpecified(
            final OpenWorkload openWorkload, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return openWorkload.InterArrivalTimeInOpenWorkloadNeedsToBeSpecified(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateDelay(final Delay delay, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(delay, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateClosedWorkload(final ClosedWorkload closedWorkload, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(closedWorkload, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(closedWorkload, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(closedWorkload, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(closedWorkload, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(closedWorkload, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(closedWorkload, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(closedWorkload, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(closedWorkload, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateClosedWorkload_PopulationInClosedWorkloadNeedsToBeSpecified(closedWorkload,
                    diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateClosedWorkload_ThinkTimeInClosedWorkloadNeedsToBeSpecified(closedWorkload,
                    diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the PopulationInClosedWorkloadNeedsToBeSpecified constraint of '
     * <em>Closed Workload</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateClosedWorkload_PopulationInClosedWorkloadNeedsToBeSpecified(
            final ClosedWorkload closedWorkload, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return closedWorkload.PopulationInClosedWorkloadNeedsToBeSpecified(diagnostics, context);
    }

    /**
     * Validates the ThinkTimeInClosedWorkloadNeedsToBeSpecified constraint of '
     * <em>Closed Workload</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateClosedWorkload_ThinkTimeInClosedWorkloadNeedsToBeSpecified(
            final ClosedWorkload closedWorkload, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return closedWorkload.ThinkTimeInClosedWorkloadNeedsToBeSpecified(diagnostics, context);
    }

    /**
     * Returns the resource locator that will be used to fetch messages for this validator's
     * diagnostics. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        // TODO
        // Specialize this to return a resource locator for messages specific to this validator.
        // Ensure that you remove @generated or mark it @generated NOT
        return super.getResourceLocator();
    }

} // UsagemodelValidator
