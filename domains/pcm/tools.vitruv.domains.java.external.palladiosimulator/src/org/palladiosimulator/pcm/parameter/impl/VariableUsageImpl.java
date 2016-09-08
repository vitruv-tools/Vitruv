/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.parameter.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionPackage;
import org.palladiosimulator.pcm.parameter.ParameterPackage;
import org.palladiosimulator.pcm.parameter.VariableCharacterisation;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.qosannotations.QosannotationsPackage;
import org.palladiosimulator.pcm.qosannotations.SpecifiedOutputParameterAbstraction;
import org.palladiosimulator.pcm.seff.CallAction;
import org.palladiosimulator.pcm.seff.CallReturnAction;
import org.palladiosimulator.pcm.seff.SeffPackage;
import org.palladiosimulator.pcm.seff.SetVariableAction;
import org.palladiosimulator.pcm.seff.SynchronisationPoint;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.UsagemodelPackage;
import org.palladiosimulator.pcm.usagemodel.UserData;

import de.uka.ipd.sdq.stoex.AbstractNamedReference;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Variable Usage</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.parameter.impl.VariableUsageImpl#getVariableCharacterisation_VariableUsage
 * <em>Variable Characterisation Variable Usage</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.parameter.impl.VariableUsageImpl#getUserData_VariableUsage
 * <em>User Data Variable Usage</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.parameter.impl.VariableUsageImpl#getCallAction__VariableUsage
 * <em>Call Action Variable Usage</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.parameter.impl.VariableUsageImpl#getSynchronisationPoint_VariableUsage
 * <em>Synchronisation Point Variable Usage</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.parameter.impl.VariableUsageImpl#getCallReturnAction__VariableUsage
 * <em>Call Return Action Variable Usage</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.parameter.impl.VariableUsageImpl#getSetVariableAction_VariableUsage
 * <em>Set Variable Action Variable Usage</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.parameter.impl.VariableUsageImpl#getSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage
 * <em>Specified Output Parameter Abstraction expected External Outputs Variable Usage</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.parameter.impl.VariableUsageImpl#getAssemblyContext__VariableUsage
 * <em>Assembly Context Variable Usage</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.parameter.impl.VariableUsageImpl#getEntryLevelSystemCall_InputParameterUsage
 * <em>Entry Level System Call Input Parameter Usage</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.parameter.impl.VariableUsageImpl#getEntryLevelSystemCall_OutputParameterUsage
 * <em>Entry Level System Call Output Parameter Usage</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.parameter.impl.VariableUsageImpl#getNamedReference__VariableUsage
 * <em>Named Reference Variable Usage</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VariableUsageImpl extends CDOObjectImpl implements VariableUsage {

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
    protected VariableUsageImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ParameterPackage.Literals.VARIABLE_USAGE;
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
    @SuppressWarnings("unchecked")
    public EList<VariableCharacterisation> getVariableCharacterisation_VariableUsage() {
        return (EList<VariableCharacterisation>) this.eDynamicGet(
                ParameterPackage.VARIABLE_USAGE__VARIABLE_CHARACTERISATION_VARIABLE_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__VARIABLE_CHARACTERISATION_VARIABLE_USAGE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public UserData getUserData_VariableUsage() {
        return (UserData) this.eDynamicGet(ParameterPackage.VARIABLE_USAGE__USER_DATA_VARIABLE_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__USER_DATA_VARIABLE_USAGE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetUserData_VariableUsage(final UserData newUserData_VariableUsage,
            NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newUserData_VariableUsage,
                ParameterPackage.VARIABLE_USAGE__USER_DATA_VARIABLE_USAGE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setUserData_VariableUsage(final UserData newUserData_VariableUsage) {
        this.eDynamicSet(ParameterPackage.VARIABLE_USAGE__USER_DATA_VARIABLE_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__USER_DATA_VARIABLE_USAGE, newUserData_VariableUsage);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public CallAction getCallAction__VariableUsage() {
        return (CallAction) this.eDynamicGet(ParameterPackage.VARIABLE_USAGE__CALL_ACTION_VARIABLE_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__CALL_ACTION_VARIABLE_USAGE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetCallAction__VariableUsage(final CallAction newCallAction__VariableUsage,
            NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newCallAction__VariableUsage,
                ParameterPackage.VARIABLE_USAGE__CALL_ACTION_VARIABLE_USAGE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setCallAction__VariableUsage(final CallAction newCallAction__VariableUsage) {
        this.eDynamicSet(ParameterPackage.VARIABLE_USAGE__CALL_ACTION_VARIABLE_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__CALL_ACTION_VARIABLE_USAGE, newCallAction__VariableUsage);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public SynchronisationPoint getSynchronisationPoint_VariableUsage() {
        return (SynchronisationPoint) this.eDynamicGet(
                ParameterPackage.VARIABLE_USAGE__SYNCHRONISATION_POINT_VARIABLE_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__SYNCHRONISATION_POINT_VARIABLE_USAGE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetSynchronisationPoint_VariableUsage(
            final SynchronisationPoint newSynchronisationPoint_VariableUsage, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newSynchronisationPoint_VariableUsage,
                ParameterPackage.VARIABLE_USAGE__SYNCHRONISATION_POINT_VARIABLE_USAGE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setSynchronisationPoint_VariableUsage(
            final SynchronisationPoint newSynchronisationPoint_VariableUsage) {
        this.eDynamicSet(ParameterPackage.VARIABLE_USAGE__SYNCHRONISATION_POINT_VARIABLE_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__SYNCHRONISATION_POINT_VARIABLE_USAGE,
                newSynchronisationPoint_VariableUsage);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public CallReturnAction getCallReturnAction__VariableUsage() {
        return (CallReturnAction) this.eDynamicGet(ParameterPackage.VARIABLE_USAGE__CALL_RETURN_ACTION_VARIABLE_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__CALL_RETURN_ACTION_VARIABLE_USAGE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetCallReturnAction__VariableUsage(
            final CallReturnAction newCallReturnAction__VariableUsage, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newCallReturnAction__VariableUsage,
                ParameterPackage.VARIABLE_USAGE__CALL_RETURN_ACTION_VARIABLE_USAGE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setCallReturnAction__VariableUsage(final CallReturnAction newCallReturnAction__VariableUsage) {
        this.eDynamicSet(ParameterPackage.VARIABLE_USAGE__CALL_RETURN_ACTION_VARIABLE_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__CALL_RETURN_ACTION_VARIABLE_USAGE,
                newCallReturnAction__VariableUsage);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public SetVariableAction getSetVariableAction_VariableUsage() {
        return (SetVariableAction) this.eDynamicGet(ParameterPackage.VARIABLE_USAGE__SET_VARIABLE_ACTION_VARIABLE_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__SET_VARIABLE_ACTION_VARIABLE_USAGE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetSetVariableAction_VariableUsage(
            final SetVariableAction newSetVariableAction_VariableUsage, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newSetVariableAction_VariableUsage,
                ParameterPackage.VARIABLE_USAGE__SET_VARIABLE_ACTION_VARIABLE_USAGE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setSetVariableAction_VariableUsage(final SetVariableAction newSetVariableAction_VariableUsage) {
        this.eDynamicSet(ParameterPackage.VARIABLE_USAGE__SET_VARIABLE_ACTION_VARIABLE_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__SET_VARIABLE_ACTION_VARIABLE_USAGE,
                newSetVariableAction_VariableUsage);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public SpecifiedOutputParameterAbstraction getSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage() {
        return (SpecifiedOutputParameterAbstraction) this.eDynamicGet(
                ParameterPackage.VARIABLE_USAGE__SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION_EXPECTED_EXTERNAL_OUTPUTS_VARIABLE_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION_EXPECTED_EXTERNAL_OUTPUTS_VARIABLE_USAGE,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage(
            final SpecifiedOutputParameterAbstraction newSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage,
            NotificationChain msgs) {
        msgs = this.eBasicSetContainer(
                (InternalEObject) newSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage,
                ParameterPackage.VARIABLE_USAGE__SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION_EXPECTED_EXTERNAL_OUTPUTS_VARIABLE_USAGE,
                msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage(
            final SpecifiedOutputParameterAbstraction newSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage) {
        this.eDynamicSet(
                ParameterPackage.VARIABLE_USAGE__SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION_EXPECTED_EXTERNAL_OUTPUTS_VARIABLE_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION_EXPECTED_EXTERNAL_OUTPUTS_VARIABLE_USAGE,
                newSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AssemblyContext getAssemblyContext__VariableUsage() {
        return (AssemblyContext) this.eDynamicGet(ParameterPackage.VARIABLE_USAGE__ASSEMBLY_CONTEXT_VARIABLE_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__ASSEMBLY_CONTEXT_VARIABLE_USAGE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetAssemblyContext__VariableUsage(
            final AssemblyContext newAssemblyContext__VariableUsage, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newAssemblyContext__VariableUsage,
                ParameterPackage.VARIABLE_USAGE__ASSEMBLY_CONTEXT_VARIABLE_USAGE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setAssemblyContext__VariableUsage(final AssemblyContext newAssemblyContext__VariableUsage) {
        this.eDynamicSet(ParameterPackage.VARIABLE_USAGE__ASSEMBLY_CONTEXT_VARIABLE_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__ASSEMBLY_CONTEXT_VARIABLE_USAGE,
                newAssemblyContext__VariableUsage);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EntryLevelSystemCall getEntryLevelSystemCall_InputParameterUsage() {
        return (EntryLevelSystemCall) this.eDynamicGet(
                ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_INPUT_PARAMETER_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_INPUT_PARAMETER_USAGE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetEntryLevelSystemCall_InputParameterUsage(
            final EntryLevelSystemCall newEntryLevelSystemCall_InputParameterUsage, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newEntryLevelSystemCall_InputParameterUsage,
                ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_INPUT_PARAMETER_USAGE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setEntryLevelSystemCall_InputParameterUsage(
            final EntryLevelSystemCall newEntryLevelSystemCall_InputParameterUsage) {
        this.eDynamicSet(ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_INPUT_PARAMETER_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_INPUT_PARAMETER_USAGE,
                newEntryLevelSystemCall_InputParameterUsage);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EntryLevelSystemCall getEntryLevelSystemCall_OutputParameterUsage() {
        return (EntryLevelSystemCall) this.eDynamicGet(
                ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_OUTPUT_PARAMETER_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_OUTPUT_PARAMETER_USAGE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetEntryLevelSystemCall_OutputParameterUsage(
            final EntryLevelSystemCall newEntryLevelSystemCall_OutputParameterUsage, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newEntryLevelSystemCall_OutputParameterUsage,
                ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_OUTPUT_PARAMETER_USAGE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setEntryLevelSystemCall_OutputParameterUsage(
            final EntryLevelSystemCall newEntryLevelSystemCall_OutputParameterUsage) {
        this.eDynamicSet(ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_OUTPUT_PARAMETER_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_OUTPUT_PARAMETER_USAGE,
                newEntryLevelSystemCall_OutputParameterUsage);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AbstractNamedReference getNamedReference__VariableUsage() {
        return (AbstractNamedReference) this.eDynamicGet(
                ParameterPackage.VARIABLE_USAGE__NAMED_REFERENCE_VARIABLE_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__NAMED_REFERENCE_VARIABLE_USAGE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetNamedReference__VariableUsage(
            final AbstractNamedReference newNamedReference__VariableUsage, NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newNamedReference__VariableUsage,
                ParameterPackage.VARIABLE_USAGE__NAMED_REFERENCE_VARIABLE_USAGE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setNamedReference__VariableUsage(final AbstractNamedReference newNamedReference__VariableUsage) {
        this.eDynamicSet(ParameterPackage.VARIABLE_USAGE__NAMED_REFERENCE_VARIABLE_USAGE,
                ParameterPackage.Literals.VARIABLE_USAGE__NAMED_REFERENCE_VARIABLE_USAGE,
                newNamedReference__VariableUsage);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ParameterPackage.VARIABLE_USAGE__VARIABLE_CHARACTERISATION_VARIABLE_USAGE:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this
                    .getVariableCharacterisation_VariableUsage()).basicAdd(otherEnd, msgs);
        case ParameterPackage.VARIABLE_USAGE__USER_DATA_VARIABLE_USAGE:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetUserData_VariableUsage((UserData) otherEnd, msgs);
        case ParameterPackage.VARIABLE_USAGE__CALL_ACTION_VARIABLE_USAGE:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetCallAction__VariableUsage((CallAction) otherEnd, msgs);
        case ParameterPackage.VARIABLE_USAGE__SYNCHRONISATION_POINT_VARIABLE_USAGE:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetSynchronisationPoint_VariableUsage((SynchronisationPoint) otherEnd, msgs);
        case ParameterPackage.VARIABLE_USAGE__CALL_RETURN_ACTION_VARIABLE_USAGE:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetCallReturnAction__VariableUsage((CallReturnAction) otherEnd, msgs);
        case ParameterPackage.VARIABLE_USAGE__SET_VARIABLE_ACTION_VARIABLE_USAGE:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetSetVariableAction_VariableUsage((SetVariableAction) otherEnd, msgs);
        case ParameterPackage.VARIABLE_USAGE__SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION_EXPECTED_EXTERNAL_OUTPUTS_VARIABLE_USAGE:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage(
                    (SpecifiedOutputParameterAbstraction) otherEnd, msgs);
        case ParameterPackage.VARIABLE_USAGE__ASSEMBLY_CONTEXT_VARIABLE_USAGE:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetAssemblyContext__VariableUsage((AssemblyContext) otherEnd, msgs);
        case ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_INPUT_PARAMETER_USAGE:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetEntryLevelSystemCall_InputParameterUsage((EntryLevelSystemCall) otherEnd, msgs);
        case ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_OUTPUT_PARAMETER_USAGE:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetEntryLevelSystemCall_OutputParameterUsage((EntryLevelSystemCall) otherEnd, msgs);
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
        case ParameterPackage.VARIABLE_USAGE__VARIABLE_CHARACTERISATION_VARIABLE_USAGE:
            return ((InternalEList<?>) this.getVariableCharacterisation_VariableUsage()).basicRemove(otherEnd, msgs);
        case ParameterPackage.VARIABLE_USAGE__USER_DATA_VARIABLE_USAGE:
            return this.basicSetUserData_VariableUsage(null, msgs);
        case ParameterPackage.VARIABLE_USAGE__CALL_ACTION_VARIABLE_USAGE:
            return this.basicSetCallAction__VariableUsage(null, msgs);
        case ParameterPackage.VARIABLE_USAGE__SYNCHRONISATION_POINT_VARIABLE_USAGE:
            return this.basicSetSynchronisationPoint_VariableUsage(null, msgs);
        case ParameterPackage.VARIABLE_USAGE__CALL_RETURN_ACTION_VARIABLE_USAGE:
            return this.basicSetCallReturnAction__VariableUsage(null, msgs);
        case ParameterPackage.VARIABLE_USAGE__SET_VARIABLE_ACTION_VARIABLE_USAGE:
            return this.basicSetSetVariableAction_VariableUsage(null, msgs);
        case ParameterPackage.VARIABLE_USAGE__SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION_EXPECTED_EXTERNAL_OUTPUTS_VARIABLE_USAGE:
            return this.basicSetSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage(null, msgs);
        case ParameterPackage.VARIABLE_USAGE__ASSEMBLY_CONTEXT_VARIABLE_USAGE:
            return this.basicSetAssemblyContext__VariableUsage(null, msgs);
        case ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_INPUT_PARAMETER_USAGE:
            return this.basicSetEntryLevelSystemCall_InputParameterUsage(null, msgs);
        case ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_OUTPUT_PARAMETER_USAGE:
            return this.basicSetEntryLevelSystemCall_OutputParameterUsage(null, msgs);
        case ParameterPackage.VARIABLE_USAGE__NAMED_REFERENCE_VARIABLE_USAGE:
            return this.basicSetNamedReference__VariableUsage(null, msgs);
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
        case ParameterPackage.VARIABLE_USAGE__USER_DATA_VARIABLE_USAGE:
            return this.eInternalContainer().eInverseRemove(this,
                    UsagemodelPackage.USER_DATA__USER_DATA_PARAMETER_USAGES_USER_DATA, UserData.class, msgs);
        case ParameterPackage.VARIABLE_USAGE__CALL_ACTION_VARIABLE_USAGE:
            return this.eInternalContainer().eInverseRemove(this,
                    SeffPackage.CALL_ACTION__INPUT_VARIABLE_USAGES_CALL_ACTION, CallAction.class, msgs);
        case ParameterPackage.VARIABLE_USAGE__SYNCHRONISATION_POINT_VARIABLE_USAGE:
            return this.eInternalContainer().eInverseRemove(this,
                    SeffPackage.SYNCHRONISATION_POINT__OUTPUT_PARAMETER_USAGE_SYNCHRONISATION_POINT,
                    SynchronisationPoint.class, msgs);
        case ParameterPackage.VARIABLE_USAGE__CALL_RETURN_ACTION_VARIABLE_USAGE:
            return this.eInternalContainer().eInverseRemove(this,
                    SeffPackage.CALL_RETURN_ACTION__RETURN_VARIABLE_USAGE_CALL_RETURN_ACTION, CallReturnAction.class,
                    msgs);
        case ParameterPackage.VARIABLE_USAGE__SET_VARIABLE_ACTION_VARIABLE_USAGE:
            return this.eInternalContainer().eInverseRemove(this,
                    SeffPackage.SET_VARIABLE_ACTION__LOCAL_VARIABLE_USAGES_SET_VARIABLE_ACTION, SetVariableAction.class,
                    msgs);
        case ParameterPackage.VARIABLE_USAGE__SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION_EXPECTED_EXTERNAL_OUTPUTS_VARIABLE_USAGE:
            return this.eInternalContainer().eInverseRemove(this,
                    QosannotationsPackage.SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION__EXPECTED_EXTERNAL_OUTPUTS_SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION,
                    SpecifiedOutputParameterAbstraction.class, msgs);
        case ParameterPackage.VARIABLE_USAGE__ASSEMBLY_CONTEXT_VARIABLE_USAGE:
            return this.eInternalContainer().eInverseRemove(this,
                    CompositionPackage.ASSEMBLY_CONTEXT__CONFIG_PARAMETER_USAGES_ASSEMBLY_CONTEXT,
                    AssemblyContext.class, msgs);
        case ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_INPUT_PARAMETER_USAGE:
            return this.eInternalContainer().eInverseRemove(this,
                    UsagemodelPackage.ENTRY_LEVEL_SYSTEM_CALL__INPUT_PARAMETER_USAGES_ENTRY_LEVEL_SYSTEM_CALL,
                    EntryLevelSystemCall.class, msgs);
        case ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_OUTPUT_PARAMETER_USAGE:
            return this.eInternalContainer().eInverseRemove(this,
                    UsagemodelPackage.ENTRY_LEVEL_SYSTEM_CALL__OUTPUT_PARAMETER_USAGES_ENTRY_LEVEL_SYSTEM_CALL,
                    EntryLevelSystemCall.class, msgs);
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
        case ParameterPackage.VARIABLE_USAGE__VARIABLE_CHARACTERISATION_VARIABLE_USAGE:
            return this.getVariableCharacterisation_VariableUsage();
        case ParameterPackage.VARIABLE_USAGE__USER_DATA_VARIABLE_USAGE:
            return this.getUserData_VariableUsage();
        case ParameterPackage.VARIABLE_USAGE__CALL_ACTION_VARIABLE_USAGE:
            return this.getCallAction__VariableUsage();
        case ParameterPackage.VARIABLE_USAGE__SYNCHRONISATION_POINT_VARIABLE_USAGE:
            return this.getSynchronisationPoint_VariableUsage();
        case ParameterPackage.VARIABLE_USAGE__CALL_RETURN_ACTION_VARIABLE_USAGE:
            return this.getCallReturnAction__VariableUsage();
        case ParameterPackage.VARIABLE_USAGE__SET_VARIABLE_ACTION_VARIABLE_USAGE:
            return this.getSetVariableAction_VariableUsage();
        case ParameterPackage.VARIABLE_USAGE__SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION_EXPECTED_EXTERNAL_OUTPUTS_VARIABLE_USAGE:
            return this.getSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage();
        case ParameterPackage.VARIABLE_USAGE__ASSEMBLY_CONTEXT_VARIABLE_USAGE:
            return this.getAssemblyContext__VariableUsage();
        case ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_INPUT_PARAMETER_USAGE:
            return this.getEntryLevelSystemCall_InputParameterUsage();
        case ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_OUTPUT_PARAMETER_USAGE:
            return this.getEntryLevelSystemCall_OutputParameterUsage();
        case ParameterPackage.VARIABLE_USAGE__NAMED_REFERENCE_VARIABLE_USAGE:
            return this.getNamedReference__VariableUsage();
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
        case ParameterPackage.VARIABLE_USAGE__VARIABLE_CHARACTERISATION_VARIABLE_USAGE:
            this.getVariableCharacterisation_VariableUsage().clear();
            this.getVariableCharacterisation_VariableUsage()
                    .addAll((Collection<? extends VariableCharacterisation>) newValue);
            return;
        case ParameterPackage.VARIABLE_USAGE__USER_DATA_VARIABLE_USAGE:
            this.setUserData_VariableUsage((UserData) newValue);
            return;
        case ParameterPackage.VARIABLE_USAGE__CALL_ACTION_VARIABLE_USAGE:
            this.setCallAction__VariableUsage((CallAction) newValue);
            return;
        case ParameterPackage.VARIABLE_USAGE__SYNCHRONISATION_POINT_VARIABLE_USAGE:
            this.setSynchronisationPoint_VariableUsage((SynchronisationPoint) newValue);
            return;
        case ParameterPackage.VARIABLE_USAGE__CALL_RETURN_ACTION_VARIABLE_USAGE:
            this.setCallReturnAction__VariableUsage((CallReturnAction) newValue);
            return;
        case ParameterPackage.VARIABLE_USAGE__SET_VARIABLE_ACTION_VARIABLE_USAGE:
            this.setSetVariableAction_VariableUsage((SetVariableAction) newValue);
            return;
        case ParameterPackage.VARIABLE_USAGE__SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION_EXPECTED_EXTERNAL_OUTPUTS_VARIABLE_USAGE:
            this.setSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage(
                    (SpecifiedOutputParameterAbstraction) newValue);
            return;
        case ParameterPackage.VARIABLE_USAGE__ASSEMBLY_CONTEXT_VARIABLE_USAGE:
            this.setAssemblyContext__VariableUsage((AssemblyContext) newValue);
            return;
        case ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_INPUT_PARAMETER_USAGE:
            this.setEntryLevelSystemCall_InputParameterUsage((EntryLevelSystemCall) newValue);
            return;
        case ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_OUTPUT_PARAMETER_USAGE:
            this.setEntryLevelSystemCall_OutputParameterUsage((EntryLevelSystemCall) newValue);
            return;
        case ParameterPackage.VARIABLE_USAGE__NAMED_REFERENCE_VARIABLE_USAGE:
            this.setNamedReference__VariableUsage((AbstractNamedReference) newValue);
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
        case ParameterPackage.VARIABLE_USAGE__VARIABLE_CHARACTERISATION_VARIABLE_USAGE:
            this.getVariableCharacterisation_VariableUsage().clear();
            return;
        case ParameterPackage.VARIABLE_USAGE__USER_DATA_VARIABLE_USAGE:
            this.setUserData_VariableUsage((UserData) null);
            return;
        case ParameterPackage.VARIABLE_USAGE__CALL_ACTION_VARIABLE_USAGE:
            this.setCallAction__VariableUsage((CallAction) null);
            return;
        case ParameterPackage.VARIABLE_USAGE__SYNCHRONISATION_POINT_VARIABLE_USAGE:
            this.setSynchronisationPoint_VariableUsage((SynchronisationPoint) null);
            return;
        case ParameterPackage.VARIABLE_USAGE__CALL_RETURN_ACTION_VARIABLE_USAGE:
            this.setCallReturnAction__VariableUsage((CallReturnAction) null);
            return;
        case ParameterPackage.VARIABLE_USAGE__SET_VARIABLE_ACTION_VARIABLE_USAGE:
            this.setSetVariableAction_VariableUsage((SetVariableAction) null);
            return;
        case ParameterPackage.VARIABLE_USAGE__SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION_EXPECTED_EXTERNAL_OUTPUTS_VARIABLE_USAGE:
            this.setSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage(
                    (SpecifiedOutputParameterAbstraction) null);
            return;
        case ParameterPackage.VARIABLE_USAGE__ASSEMBLY_CONTEXT_VARIABLE_USAGE:
            this.setAssemblyContext__VariableUsage((AssemblyContext) null);
            return;
        case ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_INPUT_PARAMETER_USAGE:
            this.setEntryLevelSystemCall_InputParameterUsage((EntryLevelSystemCall) null);
            return;
        case ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_OUTPUT_PARAMETER_USAGE:
            this.setEntryLevelSystemCall_OutputParameterUsage((EntryLevelSystemCall) null);
            return;
        case ParameterPackage.VARIABLE_USAGE__NAMED_REFERENCE_VARIABLE_USAGE:
            this.setNamedReference__VariableUsage((AbstractNamedReference) null);
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
        case ParameterPackage.VARIABLE_USAGE__VARIABLE_CHARACTERISATION_VARIABLE_USAGE:
            return !this.getVariableCharacterisation_VariableUsage().isEmpty();
        case ParameterPackage.VARIABLE_USAGE__USER_DATA_VARIABLE_USAGE:
            return this.getUserData_VariableUsage() != null;
        case ParameterPackage.VARIABLE_USAGE__CALL_ACTION_VARIABLE_USAGE:
            return this.getCallAction__VariableUsage() != null;
        case ParameterPackage.VARIABLE_USAGE__SYNCHRONISATION_POINT_VARIABLE_USAGE:
            return this.getSynchronisationPoint_VariableUsage() != null;
        case ParameterPackage.VARIABLE_USAGE__CALL_RETURN_ACTION_VARIABLE_USAGE:
            return this.getCallReturnAction__VariableUsage() != null;
        case ParameterPackage.VARIABLE_USAGE__SET_VARIABLE_ACTION_VARIABLE_USAGE:
            return this.getSetVariableAction_VariableUsage() != null;
        case ParameterPackage.VARIABLE_USAGE__SPECIFIED_OUTPUT_PARAMETER_ABSTRACTION_EXPECTED_EXTERNAL_OUTPUTS_VARIABLE_USAGE:
            return this.getSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage() != null;
        case ParameterPackage.VARIABLE_USAGE__ASSEMBLY_CONTEXT_VARIABLE_USAGE:
            return this.getAssemblyContext__VariableUsage() != null;
        case ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_INPUT_PARAMETER_USAGE:
            return this.getEntryLevelSystemCall_InputParameterUsage() != null;
        case ParameterPackage.VARIABLE_USAGE__ENTRY_LEVEL_SYSTEM_CALL_OUTPUT_PARAMETER_USAGE:
            return this.getEntryLevelSystemCall_OutputParameterUsage() != null;
        case ParameterPackage.VARIABLE_USAGE__NAMED_REFERENCE_VARIABLE_USAGE:
            return this.getNamedReference__VariableUsage() != null;
        }
        return super.eIsSet(featureID);
    }

} // VariableUsageImpl
