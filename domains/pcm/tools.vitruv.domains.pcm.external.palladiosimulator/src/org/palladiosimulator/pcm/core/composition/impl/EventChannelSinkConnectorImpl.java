/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionPackage;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector;
import org.palladiosimulator.pcm.repository.SinkRole;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Event Channel Sink Connector</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.EventChannelSinkConnectorImpl#getSinkRole__EventChannelSinkConnector
 * <em>Sink Role Event Channel Sink Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.EventChannelSinkConnectorImpl#getFilterCondition__EventChannelSinkConnector
 * <em>Filter Condition Event Channel Sink Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.EventChannelSinkConnectorImpl#getAssemblyContext__EventChannelSinkConnector
 * <em>Assembly Context Event Channel Sink Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.EventChannelSinkConnectorImpl#getEventChannel__EventChannelSinkConnector
 * <em>Event Channel Event Channel Sink Connector</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EventChannelSinkConnectorImpl extends ConnectorImpl implements EventChannelSinkConnector {

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
    protected EventChannelSinkConnectorImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CompositionPackage.Literals.EVENT_CHANNEL_SINK_CONNECTOR;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public SinkRole getSinkRole__EventChannelSinkConnector() {
        return (SinkRole) this.eDynamicGet(
                CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__SINK_ROLE_EVENT_CHANNEL_SINK_CONNECTOR,
                CompositionPackage.Literals.EVENT_CHANNEL_SINK_CONNECTOR__SINK_ROLE_EVENT_CHANNEL_SINK_CONNECTOR, true,
                true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public SinkRole basicGetSinkRole__EventChannelSinkConnector() {
        return (SinkRole) this.eDynamicGet(
                CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__SINK_ROLE_EVENT_CHANNEL_SINK_CONNECTOR,
                CompositionPackage.Literals.EVENT_CHANNEL_SINK_CONNECTOR__SINK_ROLE_EVENT_CHANNEL_SINK_CONNECTOR, false,
                true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setSinkRole__EventChannelSinkConnector(final SinkRole newSinkRole__EventChannelSinkConnector) {
        this.eDynamicSet(CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__SINK_ROLE_EVENT_CHANNEL_SINK_CONNECTOR,
                CompositionPackage.Literals.EVENT_CHANNEL_SINK_CONNECTOR__SINK_ROLE_EVENT_CHANNEL_SINK_CONNECTOR,
                newSinkRole__EventChannelSinkConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public PCMRandomVariable getFilterCondition__EventChannelSinkConnector() {
        return (PCMRandomVariable) this.eDynamicGet(
                CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__FILTER_CONDITION_EVENT_CHANNEL_SINK_CONNECTOR,
                CompositionPackage.Literals.EVENT_CHANNEL_SINK_CONNECTOR__FILTER_CONDITION_EVENT_CHANNEL_SINK_CONNECTOR,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetFilterCondition__EventChannelSinkConnector(
            final PCMRandomVariable newFilterCondition__EventChannelSinkConnector, NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newFilterCondition__EventChannelSinkConnector,
                CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__FILTER_CONDITION_EVENT_CHANNEL_SINK_CONNECTOR, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setFilterCondition__EventChannelSinkConnector(
            final PCMRandomVariable newFilterCondition__EventChannelSinkConnector) {
        this.eDynamicSet(CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__FILTER_CONDITION_EVENT_CHANNEL_SINK_CONNECTOR,
                CompositionPackage.Literals.EVENT_CHANNEL_SINK_CONNECTOR__FILTER_CONDITION_EVENT_CHANNEL_SINK_CONNECTOR,
                newFilterCondition__EventChannelSinkConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AssemblyContext getAssemblyContext__EventChannelSinkConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__ASSEMBLY_CONTEXT_EVENT_CHANNEL_SINK_CONNECTOR,
                CompositionPackage.Literals.EVENT_CHANNEL_SINK_CONNECTOR__ASSEMBLY_CONTEXT_EVENT_CHANNEL_SINK_CONNECTOR,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public AssemblyContext basicGetAssemblyContext__EventChannelSinkConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__ASSEMBLY_CONTEXT_EVENT_CHANNEL_SINK_CONNECTOR,
                CompositionPackage.Literals.EVENT_CHANNEL_SINK_CONNECTOR__ASSEMBLY_CONTEXT_EVENT_CHANNEL_SINK_CONNECTOR,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setAssemblyContext__EventChannelSinkConnector(
            final AssemblyContext newAssemblyContext__EventChannelSinkConnector) {
        this.eDynamicSet(CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__ASSEMBLY_CONTEXT_EVENT_CHANNEL_SINK_CONNECTOR,
                CompositionPackage.Literals.EVENT_CHANNEL_SINK_CONNECTOR__ASSEMBLY_CONTEXT_EVENT_CHANNEL_SINK_CONNECTOR,
                newAssemblyContext__EventChannelSinkConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EventChannel getEventChannel__EventChannelSinkConnector() {
        return (EventChannel) this.eDynamicGet(
                CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__EVENT_CHANNEL_EVENT_CHANNEL_SINK_CONNECTOR,
                CompositionPackage.Literals.EVENT_CHANNEL_SINK_CONNECTOR__EVENT_CHANNEL_EVENT_CHANNEL_SINK_CONNECTOR,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public EventChannel basicGetEventChannel__EventChannelSinkConnector() {
        return (EventChannel) this.eDynamicGet(
                CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__EVENT_CHANNEL_EVENT_CHANNEL_SINK_CONNECTOR,
                CompositionPackage.Literals.EVENT_CHANNEL_SINK_CONNECTOR__EVENT_CHANNEL_EVENT_CHANNEL_SINK_CONNECTOR,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetEventChannel__EventChannelSinkConnector(
            final EventChannel newEventChannel__EventChannelSinkConnector, NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newEventChannel__EventChannelSinkConnector,
                CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__EVENT_CHANNEL_EVENT_CHANNEL_SINK_CONNECTOR, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setEventChannel__EventChannelSinkConnector(
            final EventChannel newEventChannel__EventChannelSinkConnector) {
        this.eDynamicSet(CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__EVENT_CHANNEL_EVENT_CHANNEL_SINK_CONNECTOR,
                CompositionPackage.Literals.EVENT_CHANNEL_SINK_CONNECTOR__EVENT_CHANNEL_EVENT_CHANNEL_SINK_CONNECTOR,
                newEventChannel__EventChannelSinkConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__FILTER_CONDITION_EVENT_CHANNEL_SINK_CONNECTOR:
            final PCMRandomVariable filterCondition__EventChannelSinkConnector = this
                    .getFilterCondition__EventChannelSinkConnector();
            if (filterCondition__EventChannelSinkConnector != null) {
                msgs = ((InternalEObject) filterCondition__EventChannelSinkConnector).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__FILTER_CONDITION_EVENT_CHANNEL_SINK_CONNECTOR,
                        null, msgs);
            }
            return this.basicSetFilterCondition__EventChannelSinkConnector((PCMRandomVariable) otherEnd, msgs);
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__EVENT_CHANNEL_EVENT_CHANNEL_SINK_CONNECTOR:
            final EventChannel eventChannel__EventChannelSinkConnector = this
                    .basicGetEventChannel__EventChannelSinkConnector();
            if (eventChannel__EventChannelSinkConnector != null) {
                msgs = ((InternalEObject) eventChannel__EventChannelSinkConnector).eInverseRemove(this,
                        CompositionPackage.EVENT_CHANNEL__EVENT_CHANNEL_SINK_CONNECTOR_EVENT_CHANNEL,
                        EventChannel.class, msgs);
            }
            return this.basicSetEventChannel__EventChannelSinkConnector((EventChannel) otherEnd, msgs);
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
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__FILTER_CONDITION_EVENT_CHANNEL_SINK_CONNECTOR:
            return this.basicSetFilterCondition__EventChannelSinkConnector(null, msgs);
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__EVENT_CHANNEL_EVENT_CHANNEL_SINK_CONNECTOR:
            return this.basicSetEventChannel__EventChannelSinkConnector(null, msgs);
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
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__SINK_ROLE_EVENT_CHANNEL_SINK_CONNECTOR:
            if (resolve) {
                return this.getSinkRole__EventChannelSinkConnector();
            }
            return this.basicGetSinkRole__EventChannelSinkConnector();
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__FILTER_CONDITION_EVENT_CHANNEL_SINK_CONNECTOR:
            return this.getFilterCondition__EventChannelSinkConnector();
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__ASSEMBLY_CONTEXT_EVENT_CHANNEL_SINK_CONNECTOR:
            if (resolve) {
                return this.getAssemblyContext__EventChannelSinkConnector();
            }
            return this.basicGetAssemblyContext__EventChannelSinkConnector();
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__EVENT_CHANNEL_EVENT_CHANNEL_SINK_CONNECTOR:
            if (resolve) {
                return this.getEventChannel__EventChannelSinkConnector();
            }
            return this.basicGetEventChannel__EventChannelSinkConnector();
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
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__SINK_ROLE_EVENT_CHANNEL_SINK_CONNECTOR:
            this.setSinkRole__EventChannelSinkConnector((SinkRole) newValue);
            return;
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__FILTER_CONDITION_EVENT_CHANNEL_SINK_CONNECTOR:
            this.setFilterCondition__EventChannelSinkConnector((PCMRandomVariable) newValue);
            return;
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__ASSEMBLY_CONTEXT_EVENT_CHANNEL_SINK_CONNECTOR:
            this.setAssemblyContext__EventChannelSinkConnector((AssemblyContext) newValue);
            return;
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__EVENT_CHANNEL_EVENT_CHANNEL_SINK_CONNECTOR:
            this.setEventChannel__EventChannelSinkConnector((EventChannel) newValue);
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
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__SINK_ROLE_EVENT_CHANNEL_SINK_CONNECTOR:
            this.setSinkRole__EventChannelSinkConnector((SinkRole) null);
            return;
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__FILTER_CONDITION_EVENT_CHANNEL_SINK_CONNECTOR:
            this.setFilterCondition__EventChannelSinkConnector((PCMRandomVariable) null);
            return;
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__ASSEMBLY_CONTEXT_EVENT_CHANNEL_SINK_CONNECTOR:
            this.setAssemblyContext__EventChannelSinkConnector((AssemblyContext) null);
            return;
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__EVENT_CHANNEL_EVENT_CHANNEL_SINK_CONNECTOR:
            this.setEventChannel__EventChannelSinkConnector((EventChannel) null);
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
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__SINK_ROLE_EVENT_CHANNEL_SINK_CONNECTOR:
            return this.basicGetSinkRole__EventChannelSinkConnector() != null;
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__FILTER_CONDITION_EVENT_CHANNEL_SINK_CONNECTOR:
            return this.getFilterCondition__EventChannelSinkConnector() != null;
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__ASSEMBLY_CONTEXT_EVENT_CHANNEL_SINK_CONNECTOR:
            return this.basicGetAssemblyContext__EventChannelSinkConnector() != null;
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR__EVENT_CHANNEL_EVENT_CHANNEL_SINK_CONNECTOR:
            return this.basicGetEventChannel__EventChannelSinkConnector() != null;
        }
        return super.eIsSet(featureID);
    }

} // EventChannelSinkConnectorImpl
