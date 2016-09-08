/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.allocation.impl;

import java.util.Map;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.OCL;
import org.palladiosimulator.pcm.allocation.Allocation;
import org.palladiosimulator.pcm.allocation.AllocationContext;
import org.palladiosimulator.pcm.allocation.AllocationPackage;
import org.palladiosimulator.pcm.allocation.util.AllocationValidator;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Context</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.allocation.impl.AllocationContextImpl#getResourceContainer_AllocationContext
 * <em>Resource Container Allocation Context</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.allocation.impl.AllocationContextImpl#getAssemblyContext_AllocationContext
 * <em>Assembly Context Allocation Context</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.allocation.impl.AllocationContextImpl#getAllocation_AllocationContext
 * <em>Allocation Allocation Context</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.allocation.impl.AllocationContextImpl#getEventChannel__AllocationContext
 * <em>Event Channel Allocation Context</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AllocationContextImpl extends EntityImpl implements AllocationContext {

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
    protected AllocationContextImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return AllocationPackage.Literals.ALLOCATION_CONTEXT;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceContainer getResourceContainer_AllocationContext() {
        return (ResourceContainer) this.eDynamicGet(
                AllocationPackage.ALLOCATION_CONTEXT__RESOURCE_CONTAINER_ALLOCATION_CONTEXT,
                AllocationPackage.Literals.ALLOCATION_CONTEXT__RESOURCE_CONTAINER_ALLOCATION_CONTEXT, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ResourceContainer basicGetResourceContainer_AllocationContext() {
        return (ResourceContainer) this.eDynamicGet(
                AllocationPackage.ALLOCATION_CONTEXT__RESOURCE_CONTAINER_ALLOCATION_CONTEXT,
                AllocationPackage.Literals.ALLOCATION_CONTEXT__RESOURCE_CONTAINER_ALLOCATION_CONTEXT, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setResourceContainer_AllocationContext(final ResourceContainer newResourceContainer_AllocationContext) {
        this.eDynamicSet(AllocationPackage.ALLOCATION_CONTEXT__RESOURCE_CONTAINER_ALLOCATION_CONTEXT,
                AllocationPackage.Literals.ALLOCATION_CONTEXT__RESOURCE_CONTAINER_ALLOCATION_CONTEXT,
                newResourceContainer_AllocationContext);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AssemblyContext getAssemblyContext_AllocationContext() {
        return (AssemblyContext) this.eDynamicGet(
                AllocationPackage.ALLOCATION_CONTEXT__ASSEMBLY_CONTEXT_ALLOCATION_CONTEXT,
                AllocationPackage.Literals.ALLOCATION_CONTEXT__ASSEMBLY_CONTEXT_ALLOCATION_CONTEXT, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public AssemblyContext basicGetAssemblyContext_AllocationContext() {
        return (AssemblyContext) this.eDynamicGet(
                AllocationPackage.ALLOCATION_CONTEXT__ASSEMBLY_CONTEXT_ALLOCATION_CONTEXT,
                AllocationPackage.Literals.ALLOCATION_CONTEXT__ASSEMBLY_CONTEXT_ALLOCATION_CONTEXT, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setAssemblyContext_AllocationContext(final AssemblyContext newAssemblyContext_AllocationContext) {
        this.eDynamicSet(AllocationPackage.ALLOCATION_CONTEXT__ASSEMBLY_CONTEXT_ALLOCATION_CONTEXT,
                AllocationPackage.Literals.ALLOCATION_CONTEXT__ASSEMBLY_CONTEXT_ALLOCATION_CONTEXT,
                newAssemblyContext_AllocationContext);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Allocation getAllocation_AllocationContext() {
        return (Allocation) this.eDynamicGet(AllocationPackage.ALLOCATION_CONTEXT__ALLOCATION_ALLOCATION_CONTEXT,
                AllocationPackage.Literals.ALLOCATION_CONTEXT__ALLOCATION_ALLOCATION_CONTEXT, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetAllocation_AllocationContext(final Allocation newAllocation_AllocationContext,
            NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newAllocation_AllocationContext,
                AllocationPackage.ALLOCATION_CONTEXT__ALLOCATION_ALLOCATION_CONTEXT, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setAllocation_AllocationContext(final Allocation newAllocation_AllocationContext) {
        this.eDynamicSet(AllocationPackage.ALLOCATION_CONTEXT__ALLOCATION_ALLOCATION_CONTEXT,
                AllocationPackage.Literals.ALLOCATION_CONTEXT__ALLOCATION_ALLOCATION_CONTEXT,
                newAllocation_AllocationContext);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EventChannel getEventChannel__AllocationContext() {
        return (EventChannel) this.eDynamicGet(AllocationPackage.ALLOCATION_CONTEXT__EVENT_CHANNEL_ALLOCATION_CONTEXT,
                AllocationPackage.Literals.ALLOCATION_CONTEXT__EVENT_CHANNEL_ALLOCATION_CONTEXT, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public EventChannel basicGetEventChannel__AllocationContext() {
        return (EventChannel) this.eDynamicGet(AllocationPackage.ALLOCATION_CONTEXT__EVENT_CHANNEL_ALLOCATION_CONTEXT,
                AllocationPackage.Literals.ALLOCATION_CONTEXT__EVENT_CHANNEL_ALLOCATION_CONTEXT, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setEventChannel__AllocationContext(final EventChannel newEventChannel__AllocationContext) {
        this.eDynamicSet(AllocationPackage.ALLOCATION_CONTEXT__EVENT_CHANNEL_ALLOCATION_CONTEXT,
                AllocationPackage.Literals.ALLOCATION_CONTEXT__EVENT_CHANNEL_ALLOCATION_CONTEXT,
                newEventChannel__AllocationContext);
    }

    /**
     * The cached OCL expression body for the '
     * {@link #OneAssemblyContextOrOneEventChannelShouldBeReferred(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>One Assembly Context Or One Event Channel Should Be Referred</em>}' operation. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #OneAssemblyContextOrOneEventChannelShouldBeReferred(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static final String ONE_ASSEMBLY_CONTEXT_OR_ONE_EVENT_CHANNEL_SHOULD_BE_REFERRED__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP = "not(self.assemblyContext_AllocationContext.oclIsUndefined()) xor not(self.eventChannel__AllocationContext.oclIsUndefined())";
    /**
     * The cached OCL invariant for the '
     * {@link #OneAssemblyContextOrOneEventChannelShouldBeReferred(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>One Assembly Context Or One Event Channel Should Be Referred</em>}' invariant operation.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #OneAssemblyContextOrOneEventChannelShouldBeReferred(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static Constraint ONE_ASSEMBLY_CONTEXT_OR_ONE_EVENT_CHANNEL_SHOULD_BE_REFERRED__DIAGNOSTIC_CHAIN_MAP__EOCL_INV;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean OneAssemblyContextOrOneEventChannelShouldBeReferred(final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (ONE_ASSEMBLY_CONTEXT_OR_ONE_EVENT_CHANNEL_SHOULD_BE_REFERRED__DIAGNOSTIC_CHAIN_MAP__EOCL_INV == null) {
            final OCL.Helper helper = EOCL_ENV.createOCLHelper();
            helper.setContext(AllocationPackage.Literals.ALLOCATION_CONTEXT);
            try {
                ONE_ASSEMBLY_CONTEXT_OR_ONE_EVENT_CHANNEL_SHOULD_BE_REFERRED__DIAGNOSTIC_CHAIN_MAP__EOCL_INV = helper
                        .createInvariant(
                                ONE_ASSEMBLY_CONTEXT_OR_ONE_EVENT_CHANNEL_SHOULD_BE_REFERRED__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP);
            } catch (final ParserException pe) {
                throw new UnsupportedOperationException(pe.getLocalizedMessage());
            }
        }
        if (!EOCL_ENV
                .createQuery(
                        ONE_ASSEMBLY_CONTEXT_OR_ONE_EVENT_CHANNEL_SHOULD_BE_REFERRED__DIAGNOSTIC_CHAIN_MAP__EOCL_INV)
                .check(this)) {
            if (diagnostics != null) {
                diagnostics
                        .add(new BasicDiagnostic(Diagnostic.ERROR, AllocationValidator.DIAGNOSTIC_SOURCE,
                                AllocationValidator.ALLOCATION_CONTEXT__ONE_ASSEMBLY_CONTEXT_OR_ONE_EVENT_CHANNEL_SHOULD_BE_REFERRED,
                                EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic",
                                        new Object[] { "OneAssemblyContextOrOneEventChannelShouldBeReferred",
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
        case AllocationPackage.ALLOCATION_CONTEXT__ALLOCATION_ALLOCATION_CONTEXT:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetAllocation_AllocationContext((Allocation) otherEnd, msgs);
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
        case AllocationPackage.ALLOCATION_CONTEXT__ALLOCATION_ALLOCATION_CONTEXT:
            return this.basicSetAllocation_AllocationContext(null, msgs);
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
        case AllocationPackage.ALLOCATION_CONTEXT__ALLOCATION_ALLOCATION_CONTEXT:
            return this.eInternalContainer().eInverseRemove(this,
                    AllocationPackage.ALLOCATION__ALLOCATION_CONTEXTS_ALLOCATION, Allocation.class, msgs);
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
        case AllocationPackage.ALLOCATION_CONTEXT__RESOURCE_CONTAINER_ALLOCATION_CONTEXT:
            if (resolve) {
                return this.getResourceContainer_AllocationContext();
            }
            return this.basicGetResourceContainer_AllocationContext();
        case AllocationPackage.ALLOCATION_CONTEXT__ASSEMBLY_CONTEXT_ALLOCATION_CONTEXT:
            if (resolve) {
                return this.getAssemblyContext_AllocationContext();
            }
            return this.basicGetAssemblyContext_AllocationContext();
        case AllocationPackage.ALLOCATION_CONTEXT__ALLOCATION_ALLOCATION_CONTEXT:
            return this.getAllocation_AllocationContext();
        case AllocationPackage.ALLOCATION_CONTEXT__EVENT_CHANNEL_ALLOCATION_CONTEXT:
            if (resolve) {
                return this.getEventChannel__AllocationContext();
            }
            return this.basicGetEventChannel__AllocationContext();
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
        case AllocationPackage.ALLOCATION_CONTEXT__RESOURCE_CONTAINER_ALLOCATION_CONTEXT:
            this.setResourceContainer_AllocationContext((ResourceContainer) newValue);
            return;
        case AllocationPackage.ALLOCATION_CONTEXT__ASSEMBLY_CONTEXT_ALLOCATION_CONTEXT:
            this.setAssemblyContext_AllocationContext((AssemblyContext) newValue);
            return;
        case AllocationPackage.ALLOCATION_CONTEXT__ALLOCATION_ALLOCATION_CONTEXT:
            this.setAllocation_AllocationContext((Allocation) newValue);
            return;
        case AllocationPackage.ALLOCATION_CONTEXT__EVENT_CHANNEL_ALLOCATION_CONTEXT:
            this.setEventChannel__AllocationContext((EventChannel) newValue);
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
        case AllocationPackage.ALLOCATION_CONTEXT__RESOURCE_CONTAINER_ALLOCATION_CONTEXT:
            this.setResourceContainer_AllocationContext((ResourceContainer) null);
            return;
        case AllocationPackage.ALLOCATION_CONTEXT__ASSEMBLY_CONTEXT_ALLOCATION_CONTEXT:
            this.setAssemblyContext_AllocationContext((AssemblyContext) null);
            return;
        case AllocationPackage.ALLOCATION_CONTEXT__ALLOCATION_ALLOCATION_CONTEXT:
            this.setAllocation_AllocationContext((Allocation) null);
            return;
        case AllocationPackage.ALLOCATION_CONTEXT__EVENT_CHANNEL_ALLOCATION_CONTEXT:
            this.setEventChannel__AllocationContext((EventChannel) null);
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
        case AllocationPackage.ALLOCATION_CONTEXT__RESOURCE_CONTAINER_ALLOCATION_CONTEXT:
            return this.basicGetResourceContainer_AllocationContext() != null;
        case AllocationPackage.ALLOCATION_CONTEXT__ASSEMBLY_CONTEXT_ALLOCATION_CONTEXT:
            return this.basicGetAssemblyContext_AllocationContext() != null;
        case AllocationPackage.ALLOCATION_CONTEXT__ALLOCATION_ALLOCATION_CONTEXT:
            return this.getAllocation_AllocationContext() != null;
        case AllocationPackage.ALLOCATION_CONTEXT__EVENT_CHANNEL_ALLOCATION_CONTEXT:
            return this.basicGetEventChannel__AllocationContext() != null;
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

} // AllocationContextImpl
