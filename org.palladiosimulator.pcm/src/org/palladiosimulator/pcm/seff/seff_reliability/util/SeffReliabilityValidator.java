/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.seff_reliability.util;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.palladiosimulator.pcm.seff.seff_reliability.FailureHandlingEntity;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryAction;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour;
import org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityPackage;
import org.palladiosimulator.pcm.seff.util.SeffValidator;

/**
 * <!-- begin-user-doc --> The <b>Validator</b> for the model. <!-- end-user-doc -->
 *
 * @see org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityPackage
 * @generated
 */
public class SeffReliabilityValidator extends EObjectValidator {

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
    public static final SeffReliabilityValidator INSTANCE = new SeffReliabilityValidator();

    /**
     * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of
     * diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.common.util.Diagnostic#getSource()
     * @see org.eclipse.emf.common.util.Diagnostic#getCode()
     * @generated
     */
    public static final String DIAGNOSTIC_SOURCE = "org.palladiosimulator.pcm.seff.seff_reliability";

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Recovery
     * Action Behaviour Has Only One Predecessor' of 'Recovery Action Behaviour'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int RECOVERY_ACTION_BEHAVIOUR__RECOVERY_ACTION_BEHAVIOUR_HAS_ONLY_ONE_PREDECESSOR = 1;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Recovery
     * Action Behaviour Is Not Successor Of Itself' of 'Recovery Action Behaviour'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int RECOVERY_ACTION_BEHAVIOUR__RECOVERY_ACTION_BEHAVIOUR_IS_NOT_SUCCESSOR_OF_ITSELF = 2;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Successors
     * Of Recovery Action Behaviour Handle Disjoint Failure Types' of 'Recovery Action Behaviour'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final int RECOVERY_ACTION_BEHAVIOUR__SUCCESSORS_OF_RECOVERY_ACTION_BEHAVIOUR_HANDLE_DISJOINT_FAILURE_TYPES = 3;

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Primary
     * Behaviour Of Recovery Action Must Be Set' of 'Recovery Action'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    public static final int RECOVERY_ACTION__PRIMARY_BEHAVIOUR_OF_RECOVERY_ACTION_MUST_BE_SET = 4;

    /**
     * A constant with a fixed name that can be used as the base value for additional hand written
     * constants. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 4;

    /**
     * A constant with a fixed name that can be used as the base value for additional hand written
     * constants in a derived class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

    /**
     * The cached base package validator. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected SeffValidator seffValidator;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public SeffReliabilityValidator() {
        super();
        this.seffValidator = SeffValidator.INSTANCE;
    }

    /**
     * Returns the package of this validator switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EPackage getEPackage() {
        return SeffReliabilityPackage.eINSTANCE;
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
        case SeffReliabilityPackage.RECOVERY_ACTION_BEHAVIOUR:
            return this.validateRecoveryActionBehaviour((RecoveryActionBehaviour) value, diagnostics, context);
        case SeffReliabilityPackage.RECOVERY_ACTION:
            return this.validateRecoveryAction((RecoveryAction) value, diagnostics, context);
        case SeffReliabilityPackage.FAILURE_HANDLING_ENTITY:
            return this.validateFailureHandlingEntity((FailureHandlingEntity) value, diagnostics, context);
        default:
            return true;
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateRecoveryActionBehaviour(final RecoveryActionBehaviour recoveryActionBehaviour,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(recoveryActionBehaviour, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(recoveryActionBehaviour, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(recoveryActionBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(recoveryActionBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(recoveryActionBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(recoveryActionBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(recoveryActionBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(recoveryActionBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.seffValidator.validateResourceDemandingBehaviour_ExactlyOneStopAction(
                    recoveryActionBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.seffValidator.validateResourceDemandingBehaviour_ExactlyOneStartAction(
                    recoveryActionBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.seffValidator
                    .validateResourceDemandingBehaviour_EachActionExceptStartActionandStopActionMustHhaveAPredecessorAndSuccessor(
                            recoveryActionBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateRecoveryActionBehaviour_RecoveryActionBehaviourHasOnlyOnePredecessor(
                    recoveryActionBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateRecoveryActionBehaviour_RecoveryActionBehaviourIsNotSuccessorOfItself(
                    recoveryActionBehaviour, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this
                    .validateRecoveryActionBehaviour_SuccessorsOfRecoveryActionBehaviourHandleDisjointFailureTypes(
                            recoveryActionBehaviour, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the RecoveryActionBehaviourHasOnlyOnePredecessor constraint of '
     * <em>Recovery Action Behaviour</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateRecoveryActionBehaviour_RecoveryActionBehaviourHasOnlyOnePredecessor(
            final RecoveryActionBehaviour recoveryActionBehaviour, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return recoveryActionBehaviour.RecoveryActionBehaviourHasOnlyOnePredecessor(diagnostics, context);
    }

    /**
     * Validates the RecoveryActionBehaviourIsNotSuccessorOfItself constraint of '
     * <em>Recovery Action Behaviour</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateRecoveryActionBehaviour_RecoveryActionBehaviourIsNotSuccessorOfItself(
            final RecoveryActionBehaviour recoveryActionBehaviour, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return recoveryActionBehaviour.RecoveryActionBehaviourIsNotSuccessorOfItself(diagnostics, context);
    }

    /**
     * Validates the SuccessorsOfRecoveryActionBehaviourHandleDisjointFailureTypes constraint of '
     * <em>Recovery Action Behaviour</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateRecoveryActionBehaviour_SuccessorsOfRecoveryActionBehaviourHandleDisjointFailureTypes(
            final RecoveryActionBehaviour recoveryActionBehaviour, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return recoveryActionBehaviour.SuccessorsOfRecoveryActionBehaviourHandleDisjointFailureTypes(diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateRecoveryAction(final RecoveryAction recoveryAction, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(recoveryAction, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(recoveryAction, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(recoveryAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(recoveryAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(recoveryAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(recoveryAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(recoveryAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(recoveryAction, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateRecoveryAction_PrimaryBehaviourOfRecoveryActionMustBeSet(recoveryAction, diagnostics,
                    context);
        }
        return result;
    }

    /**
     * Validates the PrimaryBehaviourOfRecoveryActionMustBeSet constraint of '
     * <em>Recovery Action</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateRecoveryAction_PrimaryBehaviourOfRecoveryActionMustBeSet(final RecoveryAction recoveryAction,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return recoveryAction.PrimaryBehaviourOfRecoveryActionMustBeSet(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateFailureHandlingEntity(final FailureHandlingEntity failureHandlingEntity,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(failureHandlingEntity, diagnostics, context);
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

} // SeffReliabilityValidator
