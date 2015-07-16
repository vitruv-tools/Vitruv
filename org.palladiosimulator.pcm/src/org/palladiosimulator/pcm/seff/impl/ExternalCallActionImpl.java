/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.impl;

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
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.seff.CallAction;
import org.palladiosimulator.pcm.seff.CallReturnAction;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.SeffPackage;
import org.palladiosimulator.pcm.seff.seff_reliability.FailureHandlingEntity;
import org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityPackage;
import org.palladiosimulator.pcm.seff.util.SeffValidator;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>External Call Action</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.impl.ExternalCallActionImpl#getInputVariableUsages__CallAction
 * <em>Input Variable Usages Call Action</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.impl.ExternalCallActionImpl#getReturnVariableUsage__CallReturnAction
 * <em>Return Variable Usage Call Return Action</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.impl.ExternalCallActionImpl#getFailureTypes_FailureHandlingEntity
 * <em>Failure Types Failure Handling Entity</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.impl.ExternalCallActionImpl#getCalledService_ExternalService
 * <em>Called Service External Service</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.seff.impl.ExternalCallActionImpl#getRole_ExternalService
 * <em>Role External Service</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.seff.impl.ExternalCallActionImpl#getRetryCount
 * <em>Retry Count</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExternalCallActionImpl extends AbstractActionImpl implements ExternalCallAction {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The default value of the '{@link #getRetryCount() <em>Retry Count</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getRetryCount()
     * @generated
     * @ordered
     */
    protected static final int RETRY_COUNT_EDEFAULT = 0;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ExternalCallActionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SeffPackage.Literals.EXTERNAL_CALL_ACTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<VariableUsage> getInputVariableUsages__CallAction() {
        return (EList<VariableUsage>) this.eDynamicGet(
                SeffPackage.EXTERNAL_CALL_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION,
                SeffPackage.Literals.CALL_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<VariableUsage> getReturnVariableUsage__CallReturnAction() {
        return (EList<VariableUsage>) this.eDynamicGet(
                SeffPackage.EXTERNAL_CALL_ACTION__RETURN_VARIABLE_USAGE_CALL_RETURN_ACTION,
                SeffPackage.Literals.CALL_RETURN_ACTION__RETURN_VARIABLE_USAGE_CALL_RETURN_ACTION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<FailureType> getFailureTypes_FailureHandlingEntity() {
        return (EList<FailureType>) this.eDynamicGet(
                SeffPackage.EXTERNAL_CALL_ACTION__FAILURE_TYPES_FAILURE_HANDLING_ENTITY,
                SeffReliabilityPackage.Literals.FAILURE_HANDLING_ENTITY__FAILURE_TYPES_FAILURE_HANDLING_ENTITY, true,
                true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public OperationSignature getCalledService_ExternalService() {
        return (OperationSignature) this.eDynamicGet(SeffPackage.EXTERNAL_CALL_ACTION__CALLED_SERVICE_EXTERNAL_SERVICE,
                SeffPackage.Literals.EXTERNAL_CALL_ACTION__CALLED_SERVICE_EXTERNAL_SERVICE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public OperationSignature basicGetCalledService_ExternalService() {
        return (OperationSignature) this.eDynamicGet(SeffPackage.EXTERNAL_CALL_ACTION__CALLED_SERVICE_EXTERNAL_SERVICE,
                SeffPackage.Literals.EXTERNAL_CALL_ACTION__CALLED_SERVICE_EXTERNAL_SERVICE, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setCalledService_ExternalService(final OperationSignature newCalledService_ExternalService) {
        this.eDynamicSet(SeffPackage.EXTERNAL_CALL_ACTION__CALLED_SERVICE_EXTERNAL_SERVICE,
                SeffPackage.Literals.EXTERNAL_CALL_ACTION__CALLED_SERVICE_EXTERNAL_SERVICE,
                newCalledService_ExternalService);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public OperationRequiredRole getRole_ExternalService() {
        return (OperationRequiredRole) this.eDynamicGet(SeffPackage.EXTERNAL_CALL_ACTION__ROLE_EXTERNAL_SERVICE,
                SeffPackage.Literals.EXTERNAL_CALL_ACTION__ROLE_EXTERNAL_SERVICE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public OperationRequiredRole basicGetRole_ExternalService() {
        return (OperationRequiredRole) this.eDynamicGet(SeffPackage.EXTERNAL_CALL_ACTION__ROLE_EXTERNAL_SERVICE,
                SeffPackage.Literals.EXTERNAL_CALL_ACTION__ROLE_EXTERNAL_SERVICE, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setRole_ExternalService(final OperationRequiredRole newRole_ExternalService) {
        this.eDynamicSet(SeffPackage.EXTERNAL_CALL_ACTION__ROLE_EXTERNAL_SERVICE,
                SeffPackage.Literals.EXTERNAL_CALL_ACTION__ROLE_EXTERNAL_SERVICE, newRole_ExternalService);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int getRetryCount() {
        return (Integer) this.eDynamicGet(SeffPackage.EXTERNAL_CALL_ACTION__RETRY_COUNT,
                SeffPackage.Literals.EXTERNAL_CALL_ACTION__RETRY_COUNT, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setRetryCount(final int newRetryCount) {
        this.eDynamicSet(SeffPackage.EXTERNAL_CALL_ACTION__RETRY_COUNT,
                SeffPackage.Literals.EXTERNAL_CALL_ACTION__RETRY_COUNT, newRetryCount);
    }

    /**
     * The cached OCL expression body for the '
     * {@link #SignatureBelongsToRole(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Signature Belongs To Role</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #SignatureBelongsToRole(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * @generated
     * @ordered
     */
    protected static final String SIGNATURE_BELONGS_TO_ROLE__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP = "self.role_ExternalService.requiredInterface__OperationRequiredRole.signatures__OperationInterface->includes(self.calledService_ExternalService)";

    /**
     * The cached OCL invariant for the '
     * {@link #SignatureBelongsToRole(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Signature Belongs To Role</em>}' invariant operation. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #SignatureBelongsToRole(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * @generated
     * @ordered
     */
    protected static Constraint SIGNATURE_BELONGS_TO_ROLE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean SignatureBelongsToRole(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (SIGNATURE_BELONGS_TO_ROLE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV == null) {
            final OCL.Helper helper = EOCL_ENV.createOCLHelper();
            helper.setContext(SeffPackage.Literals.EXTERNAL_CALL_ACTION);
            try {
                SIGNATURE_BELONGS_TO_ROLE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV = helper
                        .createInvariant(SIGNATURE_BELONGS_TO_ROLE__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP);
            } catch (final ParserException pe) {
                throw new UnsupportedOperationException(pe.getLocalizedMessage());
            }
        }
        if (!EOCL_ENV.createQuery(SIGNATURE_BELONGS_TO_ROLE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV).check(this)) {
            if (diagnostics != null) {
                diagnostics
                        .add(new BasicDiagnostic(Diagnostic.ERROR, SeffValidator.DIAGNOSTIC_SOURCE,
                                SeffValidator.EXTERNAL_CALL_ACTION__SIGNATURE_BELONGS_TO_ROLE,
                                EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic",
                                        new Object[] { "SignatureBelongsToRole",
                                                EObjectValidator.getObjectLabel(this, context) }),
                        new Object[] { this }));
            }
            return false;
        }
        return true;
    }

    /**
     * The cached OCL expression body for the '
     * {@link #OperationRequiredRoleMustBeReferencedByContainer(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Operation Required Role Must Be Referenced By Container</em>}' operation. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #OperationRequiredRoleMustBeReferencedByContainer(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static final String OPERATION_REQUIRED_ROLE_MUST_BE_REFERENCED_BY_CONTAINER__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP = "self.oclAsType(ecore::EObject)->closure(eContainer())->select( entity | entity.oclIsKindOf(pcm::core::entity::InterfaceRequiringEntity)).oclAsType(pcm::core::entity::InterfaceRequiringEntity).requiredRoles_InterfaceRequiringEntity->includes(self.role_ExternalService)";

    /**
     * The cached OCL invariant for the '
     * {@link #OperationRequiredRoleMustBeReferencedByContainer(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Operation Required Role Must Be Referenced By Container</em>}' invariant operation. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #OperationRequiredRoleMustBeReferencedByContainer(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static Constraint OPERATION_REQUIRED_ROLE_MUST_BE_REFERENCED_BY_CONTAINER__DIAGNOSTIC_CHAIN_MAP__EOCL_INV;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean OperationRequiredRoleMustBeReferencedByContainer(final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (OPERATION_REQUIRED_ROLE_MUST_BE_REFERENCED_BY_CONTAINER__DIAGNOSTIC_CHAIN_MAP__EOCL_INV == null) {
            final OCL.Helper helper = EOCL_ENV.createOCLHelper();
            helper.setContext(SeffPackage.Literals.EXTERNAL_CALL_ACTION);
            try {
                OPERATION_REQUIRED_ROLE_MUST_BE_REFERENCED_BY_CONTAINER__DIAGNOSTIC_CHAIN_MAP__EOCL_INV = helper
                        .createInvariant(
                                OPERATION_REQUIRED_ROLE_MUST_BE_REFERENCED_BY_CONTAINER__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP);
            } catch (final ParserException pe) {
                throw new UnsupportedOperationException(pe.getLocalizedMessage());
            }
        }
        if (!EOCL_ENV
                .createQuery(OPERATION_REQUIRED_ROLE_MUST_BE_REFERENCED_BY_CONTAINER__DIAGNOSTIC_CHAIN_MAP__EOCL_INV)
                .check(this)) {
            if (diagnostics != null) {
                diagnostics
                        .add(new BasicDiagnostic(Diagnostic.ERROR, SeffValidator.DIAGNOSTIC_SOURCE,
                                SeffValidator.EXTERNAL_CALL_ACTION__OPERATION_REQUIRED_ROLE_MUST_BE_REFERENCED_BY_CONTAINER,
                                EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic",
                                        new Object[] { "OperationRequiredRoleMustBeReferencedByContainer",
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
        case SeffPackage.EXTERNAL_CALL_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getInputVariableUsages__CallAction())
                    .basicAdd(otherEnd, msgs);
        case SeffPackage.EXTERNAL_CALL_ACTION__RETURN_VARIABLE_USAGE_CALL_RETURN_ACTION:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getReturnVariableUsage__CallReturnAction())
                    .basicAdd(otherEnd, msgs);
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
        case SeffPackage.EXTERNAL_CALL_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION:
            return ((InternalEList<?>) this.getInputVariableUsages__CallAction()).basicRemove(otherEnd, msgs);
        case SeffPackage.EXTERNAL_CALL_ACTION__RETURN_VARIABLE_USAGE_CALL_RETURN_ACTION:
            return ((InternalEList<?>) this.getReturnVariableUsage__CallReturnAction()).basicRemove(otherEnd, msgs);
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
        case SeffPackage.EXTERNAL_CALL_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION:
            return this.getInputVariableUsages__CallAction();
        case SeffPackage.EXTERNAL_CALL_ACTION__RETURN_VARIABLE_USAGE_CALL_RETURN_ACTION:
            return this.getReturnVariableUsage__CallReturnAction();
        case SeffPackage.EXTERNAL_CALL_ACTION__FAILURE_TYPES_FAILURE_HANDLING_ENTITY:
            return this.getFailureTypes_FailureHandlingEntity();
        case SeffPackage.EXTERNAL_CALL_ACTION__CALLED_SERVICE_EXTERNAL_SERVICE:
            if (resolve) {
                return this.getCalledService_ExternalService();
            }
            return this.basicGetCalledService_ExternalService();
        case SeffPackage.EXTERNAL_CALL_ACTION__ROLE_EXTERNAL_SERVICE:
            if (resolve) {
                return this.getRole_ExternalService();
            }
            return this.basicGetRole_ExternalService();
        case SeffPackage.EXTERNAL_CALL_ACTION__RETRY_COUNT:
            return this.getRetryCount();
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
        case SeffPackage.EXTERNAL_CALL_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION:
            this.getInputVariableUsages__CallAction().clear();
            this.getInputVariableUsages__CallAction().addAll((Collection<? extends VariableUsage>) newValue);
            return;
        case SeffPackage.EXTERNAL_CALL_ACTION__RETURN_VARIABLE_USAGE_CALL_RETURN_ACTION:
            this.getReturnVariableUsage__CallReturnAction().clear();
            this.getReturnVariableUsage__CallReturnAction().addAll((Collection<? extends VariableUsage>) newValue);
            return;
        case SeffPackage.EXTERNAL_CALL_ACTION__FAILURE_TYPES_FAILURE_HANDLING_ENTITY:
            this.getFailureTypes_FailureHandlingEntity().clear();
            this.getFailureTypes_FailureHandlingEntity().addAll((Collection<? extends FailureType>) newValue);
            return;
        case SeffPackage.EXTERNAL_CALL_ACTION__CALLED_SERVICE_EXTERNAL_SERVICE:
            this.setCalledService_ExternalService((OperationSignature) newValue);
            return;
        case SeffPackage.EXTERNAL_CALL_ACTION__ROLE_EXTERNAL_SERVICE:
            this.setRole_ExternalService((OperationRequiredRole) newValue);
            return;
        case SeffPackage.EXTERNAL_CALL_ACTION__RETRY_COUNT:
            this.setRetryCount((Integer) newValue);
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
        case SeffPackage.EXTERNAL_CALL_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION:
            this.getInputVariableUsages__CallAction().clear();
            return;
        case SeffPackage.EXTERNAL_CALL_ACTION__RETURN_VARIABLE_USAGE_CALL_RETURN_ACTION:
            this.getReturnVariableUsage__CallReturnAction().clear();
            return;
        case SeffPackage.EXTERNAL_CALL_ACTION__FAILURE_TYPES_FAILURE_HANDLING_ENTITY:
            this.getFailureTypes_FailureHandlingEntity().clear();
            return;
        case SeffPackage.EXTERNAL_CALL_ACTION__CALLED_SERVICE_EXTERNAL_SERVICE:
            this.setCalledService_ExternalService((OperationSignature) null);
            return;
        case SeffPackage.EXTERNAL_CALL_ACTION__ROLE_EXTERNAL_SERVICE:
            this.setRole_ExternalService((OperationRequiredRole) null);
            return;
        case SeffPackage.EXTERNAL_CALL_ACTION__RETRY_COUNT:
            this.setRetryCount(RETRY_COUNT_EDEFAULT);
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
        case SeffPackage.EXTERNAL_CALL_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION:
            return !this.getInputVariableUsages__CallAction().isEmpty();
        case SeffPackage.EXTERNAL_CALL_ACTION__RETURN_VARIABLE_USAGE_CALL_RETURN_ACTION:
            return !this.getReturnVariableUsage__CallReturnAction().isEmpty();
        case SeffPackage.EXTERNAL_CALL_ACTION__FAILURE_TYPES_FAILURE_HANDLING_ENTITY:
            return !this.getFailureTypes_FailureHandlingEntity().isEmpty();
        case SeffPackage.EXTERNAL_CALL_ACTION__CALLED_SERVICE_EXTERNAL_SERVICE:
            return this.basicGetCalledService_ExternalService() != null;
        case SeffPackage.EXTERNAL_CALL_ACTION__ROLE_EXTERNAL_SERVICE:
            return this.basicGetRole_ExternalService() != null;
        case SeffPackage.EXTERNAL_CALL_ACTION__RETRY_COUNT:
            return this.getRetryCount() != RETRY_COUNT_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(final int derivedFeatureID, final Class<?> baseClass) {
        if (baseClass == CallAction.class) {
            switch (derivedFeatureID) {
            case SeffPackage.EXTERNAL_CALL_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION:
                return SeffPackage.CALL_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION;
            default:
                return -1;
            }
        }
        if (baseClass == CallReturnAction.class) {
            switch (derivedFeatureID) {
            case SeffPackage.EXTERNAL_CALL_ACTION__RETURN_VARIABLE_USAGE_CALL_RETURN_ACTION:
                return SeffPackage.CALL_RETURN_ACTION__RETURN_VARIABLE_USAGE_CALL_RETURN_ACTION;
            default:
                return -1;
            }
        }
        if (baseClass == FailureHandlingEntity.class) {
            switch (derivedFeatureID) {
            case SeffPackage.EXTERNAL_CALL_ACTION__FAILURE_TYPES_FAILURE_HANDLING_ENTITY:
                return SeffReliabilityPackage.FAILURE_HANDLING_ENTITY__FAILURE_TYPES_FAILURE_HANDLING_ENTITY;
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(final int baseFeatureID, final Class<?> baseClass) {
        if (baseClass == CallAction.class) {
            switch (baseFeatureID) {
            case SeffPackage.CALL_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION:
                return SeffPackage.EXTERNAL_CALL_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION;
            default:
                return -1;
            }
        }
        if (baseClass == CallReturnAction.class) {
            switch (baseFeatureID) {
            case SeffPackage.CALL_RETURN_ACTION__RETURN_VARIABLE_USAGE_CALL_RETURN_ACTION:
                return SeffPackage.EXTERNAL_CALL_ACTION__RETURN_VARIABLE_USAGE_CALL_RETURN_ACTION;
            default:
                return -1;
            }
        }
        if (baseClass == FailureHandlingEntity.class) {
            switch (baseFeatureID) {
            case SeffReliabilityPackage.FAILURE_HANDLING_ENTITY__FAILURE_TYPES_FAILURE_HANDLING_ENTITY:
                return SeffPackage.EXTERNAL_CALL_ACTION__FAILURE_TYPES_FAILURE_HANDLING_ENTITY;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

    /**
     * The cached environment for evaluating OCL expressions. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    protected static final OCL EOCL_ENV = OCL.newInstance();

} // ExternalCallActionImpl
