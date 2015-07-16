/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.seff_performance.impl;

import java.util.Map;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.OCL;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.seff.AbstractInternalControlFlowAction;
import org.palladiosimulator.pcm.seff.SeffPackage;
import org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand;
import org.palladiosimulator.pcm.seff.seff_performance.SeffPerformancePackage;
import org.palladiosimulator.pcm.seff.seff_performance.util.SeffPerformanceValidator;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Parametric Resource Demand</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.seff_performance.impl.ParametricResourceDemandImpl#getSpecification_ParametericResourceDemand
 * <em>Specification Parameteric Resource Demand</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.seff_performance.impl.ParametricResourceDemandImpl#getRequiredResource_ParametricResourceDemand
 * <em>Required Resource Parametric Resource Demand</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.seff_performance.impl.ParametricResourceDemandImpl#getAction_ParametricResourceDemand
 * <em>Action Parametric Resource Demand</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParametricResourceDemandImpl extends CDOObjectImpl implements ParametricResourceDemand {

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
    protected ParametricResourceDemandImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SeffPerformancePackage.Literals.PARAMETRIC_RESOURCE_DEMAND;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected int eStaticFeatureCount() {
        return 0;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PCMRandomVariable getSpecification_ParametericResourceDemand() {
        return (PCMRandomVariable) this.eDynamicGet(
                SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__SPECIFICATION_PARAMETERIC_RESOURCE_DEMAND,
                SeffPerformancePackage.Literals.PARAMETRIC_RESOURCE_DEMAND__SPECIFICATION_PARAMETERIC_RESOURCE_DEMAND,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetSpecification_ParametericResourceDemand(
            final PCMRandomVariable newSpecification_ParametericResourceDemand, NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newSpecification_ParametericResourceDemand,
                SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__SPECIFICATION_PARAMETERIC_RESOURCE_DEMAND, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSpecification_ParametericResourceDemand(
            final PCMRandomVariable newSpecification_ParametericResourceDemand) {
        this.eDynamicSet(SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__SPECIFICATION_PARAMETERIC_RESOURCE_DEMAND,
                SeffPerformancePackage.Literals.PARAMETRIC_RESOURCE_DEMAND__SPECIFICATION_PARAMETERIC_RESOURCE_DEMAND,
                newSpecification_ParametericResourceDemand);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ProcessingResourceType getRequiredResource_ParametricResourceDemand() {
        return (ProcessingResourceType) this.eDynamicGet(
                SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__REQUIRED_RESOURCE_PARAMETRIC_RESOURCE_DEMAND,
                SeffPerformancePackage.Literals.PARAMETRIC_RESOURCE_DEMAND__REQUIRED_RESOURCE_PARAMETRIC_RESOURCE_DEMAND,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ProcessingResourceType basicGetRequiredResource_ParametricResourceDemand() {
        return (ProcessingResourceType) this.eDynamicGet(
                SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__REQUIRED_RESOURCE_PARAMETRIC_RESOURCE_DEMAND,
                SeffPerformancePackage.Literals.PARAMETRIC_RESOURCE_DEMAND__REQUIRED_RESOURCE_PARAMETRIC_RESOURCE_DEMAND,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setRequiredResource_ParametricResourceDemand(
            final ProcessingResourceType newRequiredResource_ParametricResourceDemand) {
        this.eDynamicSet(
                SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__REQUIRED_RESOURCE_PARAMETRIC_RESOURCE_DEMAND,
                SeffPerformancePackage.Literals.PARAMETRIC_RESOURCE_DEMAND__REQUIRED_RESOURCE_PARAMETRIC_RESOURCE_DEMAND,
                newRequiredResource_ParametricResourceDemand);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public AbstractInternalControlFlowAction getAction_ParametricResourceDemand() {
        return (AbstractInternalControlFlowAction) this.eDynamicGet(
                SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__ACTION_PARAMETRIC_RESOURCE_DEMAND,
                SeffPerformancePackage.Literals.PARAMETRIC_RESOURCE_DEMAND__ACTION_PARAMETRIC_RESOURCE_DEMAND, true,
                true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetAction_ParametricResourceDemand(
            final AbstractInternalControlFlowAction newAction_ParametricResourceDemand, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newAction_ParametricResourceDemand,
                SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__ACTION_PARAMETRIC_RESOURCE_DEMAND, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setAction_ParametricResourceDemand(
            final AbstractInternalControlFlowAction newAction_ParametricResourceDemand) {
        this.eDynamicSet(SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__ACTION_PARAMETRIC_RESOURCE_DEMAND,
                SeffPerformancePackage.Literals.PARAMETRIC_RESOURCE_DEMAND__ACTION_PARAMETRIC_RESOURCE_DEMAND,
                newAction_ParametricResourceDemand);
    }

    /**
     * The cached OCL expression body for the '
     * {@link #DemandedProcessingResourceMustBeUniqueWithinAbstractInternalControlFlowAction(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Demanded Processing Resource Must Be Unique Within Abstract Internal Control Flow Action</em>
     * }' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #DemandedProcessingResourceMustBeUniqueWithinAbstractInternalControlFlowAction(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static final String DEMANDED_PROCESSING_RESOURCE_MUST_BE_UNIQUE_WITHIN_ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP = "self.action_ParametricResourceDemand.resourceDemand_Action->select(prd | prd.requiredResource_ParametricResourceDemand=self.requiredResource_ParametricResourceDemand)->size() = 1";
    /**
     * The cached OCL invariant for the '
     * {@link #DemandedProcessingResourceMustBeUniqueWithinAbstractInternalControlFlowAction(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Demanded Processing Resource Must Be Unique Within Abstract Internal Control Flow Action</em>
     * }' invariant operation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #DemandedProcessingResourceMustBeUniqueWithinAbstractInternalControlFlowAction(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static Constraint DEMANDED_PROCESSING_RESOURCE_MUST_BE_UNIQUE_WITHIN_ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__DIAGNOSTIC_CHAIN_MAP__EOCL_INV;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean DemandedProcessingResourceMustBeUniqueWithinAbstractInternalControlFlowAction(
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (DEMANDED_PROCESSING_RESOURCE_MUST_BE_UNIQUE_WITHIN_ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__DIAGNOSTIC_CHAIN_MAP__EOCL_INV == null) {
            final OCL.Helper helper = EOCL_ENV.createOCLHelper();
            helper.setContext(SeffPerformancePackage.Literals.PARAMETRIC_RESOURCE_DEMAND);
            try {
                DEMANDED_PROCESSING_RESOURCE_MUST_BE_UNIQUE_WITHIN_ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__DIAGNOSTIC_CHAIN_MAP__EOCL_INV = helper
                        .createInvariant(
                                DEMANDED_PROCESSING_RESOURCE_MUST_BE_UNIQUE_WITHIN_ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP);
            } catch (final ParserException pe) {
                throw new UnsupportedOperationException(pe.getLocalizedMessage());
            }
        }
        if (!EOCL_ENV
                .createQuery(
                        DEMANDED_PROCESSING_RESOURCE_MUST_BE_UNIQUE_WITHIN_ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__DIAGNOSTIC_CHAIN_MAP__EOCL_INV)
                .check(this)) {
            if (diagnostics != null) {
                diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, SeffPerformanceValidator.DIAGNOSTIC_SOURCE,
                        SeffPerformanceValidator.PARAMETRIC_RESOURCE_DEMAND__DEMANDED_PROCESSING_RESOURCE_MUST_BE_UNIQUE_WITHIN_ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION,
                        EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic",
                                new Object[] {
                                        "DemandedProcessingResourceMustBeUniqueWithinAbstractInternalControlFlowAction",
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
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__SPECIFICATION_PARAMETERIC_RESOURCE_DEMAND:
            final PCMRandomVariable specification_ParametericResourceDemand = this
                    .getSpecification_ParametericResourceDemand();
            if (specification_ParametericResourceDemand != null) {
                msgs = ((InternalEObject) specification_ParametericResourceDemand).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__SPECIFICATION_PARAMETERIC_RESOURCE_DEMAND,
                        null, msgs);
            }
            return this.basicSetSpecification_ParametericResourceDemand((PCMRandomVariable) otherEnd, msgs);
        case SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__ACTION_PARAMETRIC_RESOURCE_DEMAND:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetAction_ParametricResourceDemand((AbstractInternalControlFlowAction) otherEnd, msgs);
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
        case SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__SPECIFICATION_PARAMETERIC_RESOURCE_DEMAND:
            return this.basicSetSpecification_ParametericResourceDemand(null, msgs);
        case SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__ACTION_PARAMETRIC_RESOURCE_DEMAND:
            return this.basicSetAction_ParametricResourceDemand(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(final NotificationChain msgs) {
        switch (this.eContainerFeatureID()) {
        case SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__ACTION_PARAMETRIC_RESOURCE_DEMAND:
            return this.eInternalContainer().eInverseRemove(this,
                    SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__RESOURCE_DEMAND_ACTION,
                    AbstractInternalControlFlowAction.class, msgs);
        }
        return super.eBasicRemoveFromContainerFeature(msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__SPECIFICATION_PARAMETERIC_RESOURCE_DEMAND:
            return this.getSpecification_ParametericResourceDemand();
        case SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__REQUIRED_RESOURCE_PARAMETRIC_RESOURCE_DEMAND:
            if (resolve) {
                return this.getRequiredResource_ParametricResourceDemand();
            }
            return this.basicGetRequiredResource_ParametricResourceDemand();
        case SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__ACTION_PARAMETRIC_RESOURCE_DEMAND:
            return this.getAction_ParametricResourceDemand();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eSet(final int featureID, final Object newValue) {
        switch (featureID) {
        case SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__SPECIFICATION_PARAMETERIC_RESOURCE_DEMAND:
            this.setSpecification_ParametericResourceDemand((PCMRandomVariable) newValue);
            return;
        case SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__REQUIRED_RESOURCE_PARAMETRIC_RESOURCE_DEMAND:
            this.setRequiredResource_ParametricResourceDemand((ProcessingResourceType) newValue);
            return;
        case SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__ACTION_PARAMETRIC_RESOURCE_DEMAND:
            this.setAction_ParametricResourceDemand((AbstractInternalControlFlowAction) newValue);
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
        case SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__SPECIFICATION_PARAMETERIC_RESOURCE_DEMAND:
            this.setSpecification_ParametericResourceDemand((PCMRandomVariable) null);
            return;
        case SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__REQUIRED_RESOURCE_PARAMETRIC_RESOURCE_DEMAND:
            this.setRequiredResource_ParametricResourceDemand((ProcessingResourceType) null);
            return;
        case SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__ACTION_PARAMETRIC_RESOURCE_DEMAND:
            this.setAction_ParametricResourceDemand((AbstractInternalControlFlowAction) null);
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
        case SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__SPECIFICATION_PARAMETERIC_RESOURCE_DEMAND:
            return this.getSpecification_ParametericResourceDemand() != null;
        case SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__REQUIRED_RESOURCE_PARAMETRIC_RESOURCE_DEMAND:
            return this.basicGetRequiredResource_ParametricResourceDemand() != null;
        case SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND__ACTION_PARAMETRIC_RESOURCE_DEMAND:
            return this.getAction_ParametricResourceDemand() != null;
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

} // ParametricResourceDemandImpl
