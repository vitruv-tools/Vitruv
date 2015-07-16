/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.util;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
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
import org.palladiosimulator.pcm.seff.SeffPackage;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.palladiosimulator.pcm.seff.SetVariableAction;
import org.palladiosimulator.pcm.seff.StartAction;
import org.palladiosimulator.pcm.seff.StopAction;
import org.palladiosimulator.pcm.seff.SynchronisationPoint;

/**
 * <!-- begin-user-doc --> The <b>Validator</b> for the model. <!-- end-user-doc -->
 *
 * @see org.palladiosimulator.pcm.seff.SeffPackage
 * @generated
 */
public class SeffValidator extends EObjectValidator {

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
    public static final SeffValidator INSTANCE = new SeffValidator();

    /**
     * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of
     * diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.common.util.Diagnostic#getSource()
     * @see org.eclipse.emf.common.util.Diagnostic#getCode()
     * @generated
     */
    public static final String DIAGNOSTIC_SOURCE = "org.palladiosimulator.pcm.seff";

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Stop Action
     * Successor Must Not Be Defined' of 'Stop Action'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    public static final int STOP_ACTION__STOP_ACTION_SUCCESSOR_MUST_NOT_BE_DEFINED = 1;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Exactly One
     * Stop Action' of 'Resource Demanding Behaviour'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int RESOURCE_DEMANDING_BEHAVIOUR__EXACTLY_ONE_STOP_ACTION = 2;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Exactly One
     * Start Action' of 'Resource Demanding Behaviour'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    public static final int RESOURCE_DEMANDING_BEHAVIOUR__EXACTLY_ONE_START_ACTION = 3;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Each Action
     * Except Start Actionand Stop Action Must Hhave APredecessor And Successor' of 'Resource
     * Demanding Behaviour'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int RESOURCE_DEMANDING_BEHAVIOUR__EACH_ACTION_EXCEPT_START_ACTIONAND_STOP_ACTION_MUST_HHAVE_APREDECESSOR_AND_SUCCESSOR = 4;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Either
     * Guarded Branches Or Probabilisitic Branch Transitions' of 'Branch Action'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int BRANCH_ACTION__EITHER_GUARDED_BRANCHES_OR_PROBABILISITIC_BRANCH_TRANSITIONS = 5;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'All
     * Probabilistic Branch Probabilities Must Sum Up To1' of 'Branch Action'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int BRANCH_ACTION__ALL_PROBABILISTIC_BRANCH_PROBABILITIES_MUST_SUM_UP_TO1 = 6;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Start
     * Action Predecessor Must Not Be Defined' of 'Start Action'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    public static final int START_ACTION__START_ACTION_PREDECESSOR_MUST_NOT_BE_DEFINED = 7;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Referenced
     * Signature Must Belong To Interface Referenced By Provided Role' of 'Service Effect
     * Specification'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int SERVICE_EFFECT_SPECIFICATION__REFERENCED_SIGNATURE_MUST_BELONG_TO_INTERFACE_REFERENCED_BY_PROVIDED_ROLE = 8;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Signature
     * Belongs To Role' of 'External Call Action'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int EXTERNAL_CALL_ACTION__SIGNATURE_BELONGS_TO_ROLE = 9;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Operation
     * Required Role Must Be Referenced By Container' of 'External Call Action'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int EXTERNAL_CALL_ACTION__OPERATION_REQUIRED_ROLE_MUST_BE_REFERENCED_BY_CONTAINER = 10;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Timeout
     * Value Of Acquire Action Must Not Be Negative' of 'Acquire Action'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int ACQUIRE_ACTION__TIMEOUT_VALUE_OF_ACQUIRE_ACTION_MUST_NOT_BE_NEGATIVE = 11;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Multiple
     * Internal Occurrence Descriptions Per Failure Type Not Allowed' of 'Internal Action'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int INTERNAL_ACTION__MULTIPLE_INTERNAL_OCCURRENCE_DESCRIPTIONS_PER_FAILURE_TYPE_NOT_ALLOWED = 12;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Sum Of
     * Internal Action Failure Probabilities Must Not Exceed1' of 'Internal Action'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int INTERNAL_ACTION__SUM_OF_INTERNAL_ACTION_FAILURE_PROBABILITIES_MUST_NOT_EXCEED1 = 13;

    /**
     * A constant with a fixed name that can be used as the base value for additional hand written
     * constants. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 13;

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
    public SeffValidator() {
        super();
    }

    /**
     * Returns the package of this validator switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EPackage getEPackage() {
        return SeffPackage.eINSTANCE;
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
        case SeffPackage.STOP_ACTION:
            return this.validateStopAction((StopAction) value, diagnostics, context);
        case SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION:
            return this.validateAbstractInternalControlFlowAction((AbstractInternalControlFlowAction) value,
                    diagnostics, context);
        case SeffPackage.ABSTRACT_ACTION:
            return this.validateAbstractAction((AbstractAction) value, diagnostics, context);
        case SeffPackage.RESOURCE_DEMANDING_BEHAVIOUR:
            return this.validateResourceDemandingBehaviour((ResourceDemandingBehaviour) value, diagnostics, context);
        case SeffPackage.ABSTRACT_LOOP_ACTION:
            return this.validateAbstractLoopAction((AbstractLoopAction) value, diagnostics, context);
        case SeffPackage.ABSTRACT_BRANCH_TRANSITION:
            return this.validateAbstractBranchTransition((AbstractBranchTransition) value, diagnostics, context);
        case SeffPackage.BRANCH_ACTION:
            return this.validateBranchAction((BranchAction) value, diagnostics, context);
        case SeffPackage.CALL_ACTION:
            return this.validateCallAction((CallAction) value, diagnostics, context);
        case SeffPackage.START_ACTION:
            return this.validateStartAction((StartAction) value, diagnostics, context);
        case SeffPackage.SERVICE_EFFECT_SPECIFICATION:
            return this.validateServiceEffectSpecification((ServiceEffectSpecification) value, diagnostics, context);
        case SeffPackage.RESOURCE_DEMANDING_SEFF:
            return this.validateResourceDemandingSEFF((ResourceDemandingSEFF) value, diagnostics, context);
        case SeffPackage.RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR:
            return this.validateResourceDemandingInternalBehaviour((ResourceDemandingInternalBehaviour) value,
                    diagnostics, context);
        case SeffPackage.RELEASE_ACTION:
            return this.validateReleaseAction((ReleaseAction) value, diagnostics, context);
        case SeffPackage.LOOP_ACTION:
            return this.validateLoopAction((LoopAction) value, diagnostics, context);
        case SeffPackage.FORK_ACTION:
            return this.validateForkAction((ForkAction) value, diagnostics, context);
        case SeffPackage.FORKED_BEHAVIOUR:
            return this.validateForkedBehaviour((ForkedBehaviour) value, diagnostics, context);
        case SeffPackage.SYNCHRONISATION_POINT:
            return this.validateSynchronisationPoint((SynchronisationPoint) value, diagnostics, context);
        case SeffPackage.EXTERNAL_CALL_ACTION:
            return this.validateExternalCallAction((ExternalCallAction) value, diagnostics, context);
        case SeffPackage.CALL_RETURN_ACTION:
            return this.validateCallReturnAction((CallReturnAction) value, diagnostics, context);
        case SeffPackage.PROBABILISTIC_BRANCH_TRANSITION:
            return this.validateProbabilisticBranchTransition((ProbabilisticBranchTransition) value, diagnostics,
                    context);
        case SeffPackage.ACQUIRE_ACTION:
            return this.validateAcquireAction((AcquireAction) value, diagnostics, context);
        case SeffPackage.COLLECTION_ITERATOR_ACTION:
            return this.validateCollectionIteratorAction((CollectionIteratorAction) value, diagnostics, context);
        case SeffPackage.GUARDED_BRANCH_TRANSITION:
            return this.validateGuardedBranchTransition((GuardedBranchTransition) value, diagnostics, context);
        case SeffPackage.SET_VARIABLE_ACTION:
            return this.validateSetVariableAction((SetVariableAction) value, diagnostics, context);
        case SeffPackage.INTERNAL_CALL_ACTION:
            return this.validateInternalCallAction((InternalCallAction) value, diagnostics, context);
        case SeffPackage.EMIT_EVENT_ACTION:
            return this.validateEmitEventAction((EmitEventAction) value, diagnostics, context);
        case SeffPackage.INTERNAL_ACTION:
            return this.validateInternalAction((InternalAction) value, diagnostics, context);
        default:
            return true;
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateStopAction(final StopAction stopAction, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(stopAction, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(stopAction, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(stopAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(stopAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(stopAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(stopAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(stopAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(stopAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateStopAction_StopActionSuccessorMustNotBeDefined(stopAction, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the StopActionSuccessorMustNotBeDefined constraint of '<em>Stop Action</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateStopAction_StopActionSuccessorMustNotBeDefined(final StopAction stopAction,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return stopAction.StopActionSuccessorMustNotBeDefined(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateAbstractInternalControlFlowAction(
            final AbstractInternalControlFlowAction abstractInternalControlFlowAction,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(abstractInternalControlFlowAction, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateAbstractAction(final AbstractAction abstractAction, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(abstractAction, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateResourceDemandingBehaviour(final ResourceDemandingBehaviour resourceDemandingBehaviour,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(resourceDemandingBehaviour, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(resourceDemandingBehaviour, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(resourceDemandingBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(resourceDemandingBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(resourceDemandingBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(resourceDemandingBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(resourceDemandingBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(resourceDemandingBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateResourceDemandingBehaviour_ExactlyOneStopAction(resourceDemandingBehaviour,
                    diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateResourceDemandingBehaviour_ExactlyOneStartAction(resourceDemandingBehaviour,
                    diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this
                    .validateResourceDemandingBehaviour_EachActionExceptStartActionandStopActionMustHhaveAPredecessorAndSuccessor(
                            resourceDemandingBehaviour, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the ExactlyOneStopAction constraint of '<em>Resource Demanding Behaviour</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateResourceDemandingBehaviour_ExactlyOneStopAction(
            final ResourceDemandingBehaviour resourceDemandingBehaviour, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return resourceDemandingBehaviour.ExactlyOneStopAction(diagnostics, context);
    }

    /**
     * Validates the ExactlyOneStartAction constraint of '<em>Resource Demanding Behaviour</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateResourceDemandingBehaviour_ExactlyOneStartAction(
            final ResourceDemandingBehaviour resourceDemandingBehaviour, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return resourceDemandingBehaviour.ExactlyOneStartAction(diagnostics, context);
    }

    /**
     * Validates the EachActionExceptStartActionandStopActionMustHhaveAPredecessorAndSuccessor
     * constraint of '<em>Resource Demanding Behaviour</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    public boolean validateResourceDemandingBehaviour_EachActionExceptStartActionandStopActionMustHhaveAPredecessorAndSuccessor(
            final ResourceDemandingBehaviour resourceDemandingBehaviour, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return resourceDemandingBehaviour
                .EachActionExceptStartActionandStopActionMustHhaveAPredecessorAndSuccessor(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateAbstractLoopAction(final AbstractLoopAction abstractLoopAction,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(abstractLoopAction, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateAbstractBranchTransition(final AbstractBranchTransition abstractBranchTransition,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(abstractBranchTransition, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateBranchAction(final BranchAction branchAction, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(branchAction, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(branchAction, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(branchAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(branchAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(branchAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(branchAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(branchAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(branchAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateBranchAction_EitherGuardedBranchesOrProbabilisiticBranchTransitions(branchAction,
                    diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateBranchAction_AllProbabilisticBranchProbabilitiesMustSumUpTo1(branchAction,
                    diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the EitherGuardedBranchesOrProbabilisiticBranchTransitions constraint of '
     * <em>Branch Action</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateBranchAction_EitherGuardedBranchesOrProbabilisiticBranchTransitions(
            final BranchAction branchAction, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return branchAction.EitherGuardedBranchesOrProbabilisiticBranchTransitions(diagnostics, context);
    }

    /**
     * Validates the AllProbabilisticBranchProbabilitiesMustSumUpTo1 constraint of '
     * <em>Branch Action</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateBranchAction_AllProbabilisticBranchProbabilitiesMustSumUpTo1(final BranchAction branchAction,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return branchAction.AllProbabilisticBranchProbabilitiesMustSumUpTo1(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateCallAction(final CallAction callAction, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(callAction, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateStartAction(final StartAction startAction, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(startAction, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(startAction, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(startAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(startAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(startAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(startAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(startAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(startAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateStartAction_StartActionPredecessorMustNotBeDefined(startAction, diagnostics,
                    context);
        }
        return result;
    }

    /**
     * Validates the StartActionPredecessorMustNotBeDefined constraint of '<em>Start Action</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateStartAction_StartActionPredecessorMustNotBeDefined(final StartAction startAction,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return startAction.StartActionPredecessorMustNotBeDefined(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateServiceEffectSpecification(final ServiceEffectSpecification serviceEffectSpecification,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(serviceEffectSpecification, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(serviceEffectSpecification, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(serviceEffectSpecification, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(serviceEffectSpecification, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(serviceEffectSpecification, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(serviceEffectSpecification, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(serviceEffectSpecification, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(serviceEffectSpecification, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this
                    .validateServiceEffectSpecification_ReferencedSignatureMustBelongToInterfaceReferencedByProvidedRole(
                            serviceEffectSpecification, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the ReferencedSignatureMustBelongToInterfaceReferencedByProvidedRole constraint of
     * '<em>Service Effect Specification</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateServiceEffectSpecification_ReferencedSignatureMustBelongToInterfaceReferencedByProvidedRole(
            final ServiceEffectSpecification serviceEffectSpecification, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return serviceEffectSpecification.ReferencedSignatureMustBelongToInterfaceReferencedByProvidedRole(diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateResourceDemandingSEFF(final ResourceDemandingSEFF resourceDemandingSEFF,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(resourceDemandingSEFF, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(resourceDemandingSEFF, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(resourceDemandingSEFF, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(resourceDemandingSEFF, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(resourceDemandingSEFF, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(resourceDemandingSEFF, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(resourceDemandingSEFF, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(resourceDemandingSEFF, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this
                    .validateServiceEffectSpecification_ReferencedSignatureMustBelongToInterfaceReferencedByProvidedRole(
                            resourceDemandingSEFF, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateResourceDemandingBehaviour_ExactlyOneStopAction(resourceDemandingSEFF, diagnostics,
                    context);
        }
        if (result || diagnostics != null) {
            result &= this.validateResourceDemandingBehaviour_ExactlyOneStartAction(resourceDemandingSEFF, diagnostics,
                    context);
        }
        if (result || diagnostics != null) {
            result &= this
                    .validateResourceDemandingBehaviour_EachActionExceptStartActionandStopActionMustHhaveAPredecessorAndSuccessor(
                            resourceDemandingSEFF, diagnostics, context);
        }
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateResourceDemandingInternalBehaviour(
            final ResourceDemandingInternalBehaviour resourceDemandingInternalBehaviour,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(resourceDemandingInternalBehaviour, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(resourceDemandingInternalBehaviour, diagnostics,
                context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(resourceDemandingInternalBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(resourceDemandingInternalBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(resourceDemandingInternalBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(resourceDemandingInternalBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(resourceDemandingInternalBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(resourceDemandingInternalBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateResourceDemandingBehaviour_ExactlyOneStopAction(resourceDemandingInternalBehaviour,
                    diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateResourceDemandingBehaviour_ExactlyOneStartAction(resourceDemandingInternalBehaviour,
                    diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this
                    .validateResourceDemandingBehaviour_EachActionExceptStartActionandStopActionMustHhaveAPredecessorAndSuccessor(
                            resourceDemandingInternalBehaviour, diagnostics, context);
        }
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateReleaseAction(final ReleaseAction releaseAction, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(releaseAction, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateLoopAction(final LoopAction loopAction, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(loopAction, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateForkAction(final ForkAction forkAction, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(forkAction, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateForkedBehaviour(final ForkedBehaviour forkedBehaviour, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(forkedBehaviour, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(forkedBehaviour, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(forkedBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(forkedBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(forkedBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(forkedBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(forkedBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(forkedBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateResourceDemandingBehaviour_ExactlyOneStopAction(forkedBehaviour, diagnostics,
                    context);
        }
        if (result || diagnostics != null) {
            result &= this.validateResourceDemandingBehaviour_ExactlyOneStartAction(forkedBehaviour, diagnostics,
                    context);
        }
        if (result || diagnostics != null) {
            result &= this
                    .validateResourceDemandingBehaviour_EachActionExceptStartActionandStopActionMustHhaveAPredecessorAndSuccessor(
                            forkedBehaviour, diagnostics, context);
        }
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateSynchronisationPoint(final SynchronisationPoint synchronisationPoint,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(synchronisationPoint, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateExternalCallAction(final ExternalCallAction externalCallAction,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(externalCallAction, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(externalCallAction, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(externalCallAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(externalCallAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(externalCallAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(externalCallAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(externalCallAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(externalCallAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateExternalCallAction_SignatureBelongsToRole(externalCallAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateExternalCallAction_OperationRequiredRoleMustBeReferencedByContainer(
                    externalCallAction, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the SignatureBelongsToRole constraint of '<em>External Call Action</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateExternalCallAction_SignatureBelongsToRole(final ExternalCallAction externalCallAction,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return externalCallAction.SignatureBelongsToRole(diagnostics, context);
    }

    /**
     * Validates the OperationRequiredRoleMustBeReferencedByContainer constraint of '
     * <em>External Call Action</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateExternalCallAction_OperationRequiredRoleMustBeReferencedByContainer(
            final ExternalCallAction externalCallAction, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return externalCallAction.OperationRequiredRoleMustBeReferencedByContainer(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateCallReturnAction(final CallReturnAction callReturnAction, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(callReturnAction, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateProbabilisticBranchTransition(
            final ProbabilisticBranchTransition probabilisticBranchTransition, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(probabilisticBranchTransition, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateAcquireAction(final AcquireAction acquireAction, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(acquireAction, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(acquireAction, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(acquireAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(acquireAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(acquireAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(acquireAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(acquireAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(acquireAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateAcquireAction_TimeoutValueOfAcquireActionMustNotBeNegative(acquireAction,
                    diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the TimeoutValueOfAcquireActionMustNotBeNegative constraint of '
     * <em>Acquire Action</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateAcquireAction_TimeoutValueOfAcquireActionMustNotBeNegative(final AcquireAction acquireAction,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return acquireAction.TimeoutValueOfAcquireActionMustNotBeNegative(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateCollectionIteratorAction(final CollectionIteratorAction collectionIteratorAction,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(collectionIteratorAction, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateGuardedBranchTransition(final GuardedBranchTransition guardedBranchTransition,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(guardedBranchTransition, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateSetVariableAction(final SetVariableAction setVariableAction,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(setVariableAction, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateInternalCallAction(final InternalCallAction internalCallAction,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(internalCallAction, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateEmitEventAction(final EmitEventAction emitEventAction, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(emitEventAction, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateInternalAction(final InternalAction internalAction, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(internalAction, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(internalAction, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(internalAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(internalAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(internalAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(internalAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(internalAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(internalAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateInternalAction_MultipleInternalOccurrenceDescriptionsPerFailureTypeNotAllowed(
                    internalAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateInternalAction_SumOfInternalActionFailureProbabilitiesMustNotExceed1(internalAction,
                    diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the MultipleInternalOccurrenceDescriptionsPerFailureTypeNotAllowed constraint of '
     * <em>Internal Action</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateInternalAction_MultipleInternalOccurrenceDescriptionsPerFailureTypeNotAllowed(
            final InternalAction internalAction, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return internalAction.MultipleInternalOccurrenceDescriptionsPerFailureTypeNotAllowed(diagnostics, context);
    }

    /**
     * Validates the SumOfInternalActionFailureProbabilitiesMustNotExceed1 constraint of '
     * <em>Internal Action</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateInternalAction_SumOfInternalActionFailureProbabilitiesMustNotExceed1(
            final InternalAction internalAction, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return internalAction.SumOfInternalActionFailureProbabilitiesMustNotExceed1(diagnostics, context);
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

} // SeffValidator
