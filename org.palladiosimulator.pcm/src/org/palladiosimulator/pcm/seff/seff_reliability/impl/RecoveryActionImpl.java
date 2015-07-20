/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.seff_reliability.impl;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.OCL;
import org.palladiosimulator.pcm.seff.impl.AbstractInternalControlFlowActionImpl;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryAction;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour;
import org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityPackage;
import org.palladiosimulator.pcm.seff.seff_reliability.util.SeffReliabilityValidator;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Recovery Action</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.seff_reliability.impl.RecoveryActionImpl#getPrimaryBehaviour__RecoveryAction
 * <em>Primary Behaviour Recovery Action</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.seff_reliability.impl.RecoveryActionImpl#getRecoveryActionBehaviours__RecoveryAction
 * <em>Recovery Action Behaviours Recovery Action</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RecoveryActionImpl extends AbstractInternalControlFlowActionImpl implements RecoveryAction {

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
    protected RecoveryActionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SeffReliabilityPackage.Literals.RECOVERY_ACTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public RecoveryActionBehaviour getPrimaryBehaviour__RecoveryAction() {
        return (RecoveryActionBehaviour) this.eDynamicGet(
                SeffReliabilityPackage.RECOVERY_ACTION__PRIMARY_BEHAVIOUR_RECOVERY_ACTION,
                SeffReliabilityPackage.Literals.RECOVERY_ACTION__PRIMARY_BEHAVIOUR_RECOVERY_ACTION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public RecoveryActionBehaviour basicGetPrimaryBehaviour__RecoveryAction() {
        return (RecoveryActionBehaviour) this.eDynamicGet(
                SeffReliabilityPackage.RECOVERY_ACTION__PRIMARY_BEHAVIOUR_RECOVERY_ACTION,
                SeffReliabilityPackage.Literals.RECOVERY_ACTION__PRIMARY_BEHAVIOUR_RECOVERY_ACTION, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setPrimaryBehaviour__RecoveryAction(final RecoveryActionBehaviour newPrimaryBehaviour__RecoveryAction) {
        this.eDynamicSet(SeffReliabilityPackage.RECOVERY_ACTION__PRIMARY_BEHAVIOUR_RECOVERY_ACTION,
                SeffReliabilityPackage.Literals.RECOVERY_ACTION__PRIMARY_BEHAVIOUR_RECOVERY_ACTION,
                newPrimaryBehaviour__RecoveryAction);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<RecoveryActionBehaviour> getRecoveryActionBehaviours__RecoveryAction() {
        return (EList<RecoveryActionBehaviour>) this.eDynamicGet(
                SeffReliabilityPackage.RECOVERY_ACTION__RECOVERY_ACTION_BEHAVIOURS_RECOVERY_ACTION,
                SeffReliabilityPackage.Literals.RECOVERY_ACTION__RECOVERY_ACTION_BEHAVIOURS_RECOVERY_ACTION, true,
                true);
    }

    /**
     * The cached OCL expression body for the '
     * {@link #PrimaryBehaviourOfRecoveryActionMustBeSet(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Primary Behaviour Of Recovery Action Must Be Set</em>}' operation. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @see #PrimaryBehaviourOfRecoveryActionMustBeSet(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static final String PRIMARY_BEHAVIOUR_OF_RECOVERY_ACTION_MUST_BE_SET__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP = "self.primaryBehaviour__RecoveryAction <> null";
    /**
     * The cached OCL invariant for the '
     * {@link #PrimaryBehaviourOfRecoveryActionMustBeSet(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Primary Behaviour Of Recovery Action Must Be Set</em>}' invariant operation. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #PrimaryBehaviourOfRecoveryActionMustBeSet(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static Constraint PRIMARY_BEHAVIOUR_OF_RECOVERY_ACTION_MUST_BE_SET__DIAGNOSTIC_CHAIN_MAP__EOCL_INV;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean PrimaryBehaviourOfRecoveryActionMustBeSet(final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (PRIMARY_BEHAVIOUR_OF_RECOVERY_ACTION_MUST_BE_SET__DIAGNOSTIC_CHAIN_MAP__EOCL_INV == null) {
            final OCL.Helper helper = EOCL_ENV.createOCLHelper();
            helper.setContext(SeffReliabilityPackage.Literals.RECOVERY_ACTION);
            try {
                PRIMARY_BEHAVIOUR_OF_RECOVERY_ACTION_MUST_BE_SET__DIAGNOSTIC_CHAIN_MAP__EOCL_INV = helper
                        .createInvariant(
                                PRIMARY_BEHAVIOUR_OF_RECOVERY_ACTION_MUST_BE_SET__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP);
            } catch (final ParserException pe) {
                throw new UnsupportedOperationException(pe.getLocalizedMessage());
            }
        }
        if (!EOCL_ENV.createQuery(PRIMARY_BEHAVIOUR_OF_RECOVERY_ACTION_MUST_BE_SET__DIAGNOSTIC_CHAIN_MAP__EOCL_INV)
                .check(this)) {
            if (diagnostics != null) {
                diagnostics
                        .add(new BasicDiagnostic(Diagnostic.ERROR, SeffReliabilityValidator.DIAGNOSTIC_SOURCE,
                                SeffReliabilityValidator.RECOVERY_ACTION__PRIMARY_BEHAVIOUR_OF_RECOVERY_ACTION_MUST_BE_SET,
                                EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic",
                                        new Object[] { "PrimaryBehaviourOfRecoveryActionMustBeSet",
                                                EObjectValidator.getObjectLabel(this, context) }),
                        new Object[] { this }));
            }
            return false;
        }
        return true;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID,
            final NotificationChain msgs) {
        switch (featureID) {
        case SeffReliabilityPackage.RECOVERY_ACTION__RECOVERY_ACTION_BEHAVIOURS_RECOVERY_ACTION:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this
                    .getRecoveryActionBehaviours__RecoveryAction()).basicAdd(otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID,
            final NotificationChain msgs) {
        switch (featureID) {
        case SeffReliabilityPackage.RECOVERY_ACTION__RECOVERY_ACTION_BEHAVIOURS_RECOVERY_ACTION:
            return ((InternalEList<?>) this.getRecoveryActionBehaviours__RecoveryAction()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case SeffReliabilityPackage.RECOVERY_ACTION__PRIMARY_BEHAVIOUR_RECOVERY_ACTION:
            if (resolve) {
                return this.getPrimaryBehaviour__RecoveryAction();
            }
            return this.basicGetPrimaryBehaviour__RecoveryAction();
        case SeffReliabilityPackage.RECOVERY_ACTION__RECOVERY_ACTION_BEHAVIOURS_RECOVERY_ACTION:
            return this.getRecoveryActionBehaviours__RecoveryAction();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(final int featureID, final Object newValue) {
        switch (featureID) {
        case SeffReliabilityPackage.RECOVERY_ACTION__PRIMARY_BEHAVIOUR_RECOVERY_ACTION:
            this.setPrimaryBehaviour__RecoveryAction((RecoveryActionBehaviour) newValue);
            return;
        case SeffReliabilityPackage.RECOVERY_ACTION__RECOVERY_ACTION_BEHAVIOURS_RECOVERY_ACTION:
            this.getRecoveryActionBehaviours__RecoveryAction().clear();
            this.getRecoveryActionBehaviours__RecoveryAction()
                    .addAll((Collection<? extends RecoveryActionBehaviour>) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eUnset(final int featureID) {
        switch (featureID) {
        case SeffReliabilityPackage.RECOVERY_ACTION__PRIMARY_BEHAVIOUR_RECOVERY_ACTION:
            this.setPrimaryBehaviour__RecoveryAction((RecoveryActionBehaviour) null);
            return;
        case SeffReliabilityPackage.RECOVERY_ACTION__RECOVERY_ACTION_BEHAVIOURS_RECOVERY_ACTION:
            this.getRecoveryActionBehaviours__RecoveryAction().clear();
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean eIsSet(final int featureID) {
        switch (featureID) {
        case SeffReliabilityPackage.RECOVERY_ACTION__PRIMARY_BEHAVIOUR_RECOVERY_ACTION:
            return this.basicGetPrimaryBehaviour__RecoveryAction() != null;
        case SeffReliabilityPackage.RECOVERY_ACTION__RECOVERY_ACTION_BEHAVIOURS_RECOVERY_ACTION:
            return !this.getRecoveryActionBehaviours__RecoveryAction().isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * The cached environment for evaluating OCL expressions. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected static final OCL EOCL_ENV = OCL.newInstance();

} // RecoveryActionImpl
