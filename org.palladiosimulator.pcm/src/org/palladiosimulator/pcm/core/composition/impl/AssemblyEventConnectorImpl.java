/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.AssemblyEventConnector;
import org.palladiosimulator.pcm.core.composition.CompositionPackage;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Assembly Event Connector</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyEventConnectorImpl#getSinkRole__AssemblyEventConnector
 * <em>Sink Role Assembly Event Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyEventConnectorImpl#getSourceRole__AssemblyEventConnector
 * <em>Source Role Assembly Event Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyEventConnectorImpl#getSinkAssemblyContext__AssemblyEventConnector
 * <em>Sink Assembly Context Assembly Event Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyEventConnectorImpl#getSourceAssemblyContext__AssemblyEventConnector
 * <em>Source Assembly Context Assembly Event Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyEventConnectorImpl#getFilterCondition__AssemblyEventConnector
 * <em>Filter Condition Assembly Event Connector</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AssemblyEventConnectorImpl extends ConnectorImpl implements AssemblyEventConnector {

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
    protected AssemblyEventConnectorImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CompositionPackage.Literals.ASSEMBLY_EVENT_CONNECTOR;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public SinkRole getSinkRole__AssemblyEventConnector() {
        return (SinkRole) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SINK_ROLE_ASSEMBLY_EVENT_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_EVENT_CONNECTOR__SINK_ROLE_ASSEMBLY_EVENT_CONNECTOR, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public SinkRole basicGetSinkRole__AssemblyEventConnector() {
        return (SinkRole) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SINK_ROLE_ASSEMBLY_EVENT_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_EVENT_CONNECTOR__SINK_ROLE_ASSEMBLY_EVENT_CONNECTOR, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setSinkRole__AssemblyEventConnector(final SinkRole newSinkRole__AssemblyEventConnector) {
        this.eDynamicSet(CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SINK_ROLE_ASSEMBLY_EVENT_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_EVENT_CONNECTOR__SINK_ROLE_ASSEMBLY_EVENT_CONNECTOR,
                newSinkRole__AssemblyEventConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public SourceRole getSourceRole__AssemblyEventConnector() {
        return (SourceRole) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ROLE_ASSEMBLY_EVENT_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ROLE_ASSEMBLY_EVENT_CONNECTOR, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public SourceRole basicGetSourceRole__AssemblyEventConnector() {
        return (SourceRole) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ROLE_ASSEMBLY_EVENT_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ROLE_ASSEMBLY_EVENT_CONNECTOR, false,
                true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setSourceRole__AssemblyEventConnector(final SourceRole newSourceRole__AssemblyEventConnector) {
        this.eDynamicSet(CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ROLE_ASSEMBLY_EVENT_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ROLE_ASSEMBLY_EVENT_CONNECTOR,
                newSourceRole__AssemblyEventConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AssemblyContext getSinkAssemblyContext__AssemblyEventConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SINK_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_EVENT_CONNECTOR__SINK_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public AssemblyContext basicGetSinkAssemblyContext__AssemblyEventConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SINK_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_EVENT_CONNECTOR__SINK_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setSinkAssemblyContext__AssemblyEventConnector(
            final AssemblyContext newSinkAssemblyContext__AssemblyEventConnector) {
        this.eDynamicSet(CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SINK_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_EVENT_CONNECTOR__SINK_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR,
                newSinkAssemblyContext__AssemblyEventConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AssemblyContext getSourceAssemblyContext__AssemblyEventConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public AssemblyContext basicGetSourceAssemblyContext__AssemblyEventConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setSourceAssemblyContext__AssemblyEventConnector(
            final AssemblyContext newSourceAssemblyContext__AssemblyEventConnector) {
        this.eDynamicSet(CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR,
                newSourceAssemblyContext__AssemblyEventConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public PCMRandomVariable getFilterCondition__AssemblyEventConnector() {
        return (PCMRandomVariable) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__FILTER_CONDITION_ASSEMBLY_EVENT_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_EVENT_CONNECTOR__FILTER_CONDITION_ASSEMBLY_EVENT_CONNECTOR, true,
                true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetFilterCondition__AssemblyEventConnector(
            final PCMRandomVariable newFilterCondition__AssemblyEventConnector, NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newFilterCondition__AssemblyEventConnector,
                CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__FILTER_CONDITION_ASSEMBLY_EVENT_CONNECTOR, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setFilterCondition__AssemblyEventConnector(
            final PCMRandomVariable newFilterCondition__AssemblyEventConnector) {
        this.eDynamicSet(CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__FILTER_CONDITION_ASSEMBLY_EVENT_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_EVENT_CONNECTOR__FILTER_CONDITION_ASSEMBLY_EVENT_CONNECTOR,
                newFilterCondition__AssemblyEventConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__FILTER_CONDITION_ASSEMBLY_EVENT_CONNECTOR:
            final PCMRandomVariable filterCondition__AssemblyEventConnector = this
                    .getFilterCondition__AssemblyEventConnector();
            if (filterCondition__AssemblyEventConnector != null) {
                msgs = ((InternalEObject) filterCondition__AssemblyEventConnector).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__FILTER_CONDITION_ASSEMBLY_EVENT_CONNECTOR,
                        null, msgs);
            }
            return this.basicSetFilterCondition__AssemblyEventConnector((PCMRandomVariable) otherEnd, msgs);
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
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__FILTER_CONDITION_ASSEMBLY_EVENT_CONNECTOR:
            return this.basicSetFilterCondition__AssemblyEventConnector(null, msgs);
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
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SINK_ROLE_ASSEMBLY_EVENT_CONNECTOR:
            if (resolve) {
                return this.getSinkRole__AssemblyEventConnector();
            }
            return this.basicGetSinkRole__AssemblyEventConnector();
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ROLE_ASSEMBLY_EVENT_CONNECTOR:
            if (resolve) {
                return this.getSourceRole__AssemblyEventConnector();
            }
            return this.basicGetSourceRole__AssemblyEventConnector();
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SINK_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR:
            if (resolve) {
                return this.getSinkAssemblyContext__AssemblyEventConnector();
            }
            return this.basicGetSinkAssemblyContext__AssemblyEventConnector();
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR:
            if (resolve) {
                return this.getSourceAssemblyContext__AssemblyEventConnector();
            }
            return this.basicGetSourceAssemblyContext__AssemblyEventConnector();
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__FILTER_CONDITION_ASSEMBLY_EVENT_CONNECTOR:
            return this.getFilterCondition__AssemblyEventConnector();
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
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SINK_ROLE_ASSEMBLY_EVENT_CONNECTOR:
            this.setSinkRole__AssemblyEventConnector((SinkRole) newValue);
            return;
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ROLE_ASSEMBLY_EVENT_CONNECTOR:
            this.setSourceRole__AssemblyEventConnector((SourceRole) newValue);
            return;
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SINK_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR:
            this.setSinkAssemblyContext__AssemblyEventConnector((AssemblyContext) newValue);
            return;
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR:
            this.setSourceAssemblyContext__AssemblyEventConnector((AssemblyContext) newValue);
            return;
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__FILTER_CONDITION_ASSEMBLY_EVENT_CONNECTOR:
            this.setFilterCondition__AssemblyEventConnector((PCMRandomVariable) newValue);
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
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SINK_ROLE_ASSEMBLY_EVENT_CONNECTOR:
            this.setSinkRole__AssemblyEventConnector((SinkRole) null);
            return;
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ROLE_ASSEMBLY_EVENT_CONNECTOR:
            this.setSourceRole__AssemblyEventConnector((SourceRole) null);
            return;
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SINK_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR:
            this.setSinkAssemblyContext__AssemblyEventConnector((AssemblyContext) null);
            return;
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR:
            this.setSourceAssemblyContext__AssemblyEventConnector((AssemblyContext) null);
            return;
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__FILTER_CONDITION_ASSEMBLY_EVENT_CONNECTOR:
            this.setFilterCondition__AssemblyEventConnector((PCMRandomVariable) null);
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
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SINK_ROLE_ASSEMBLY_EVENT_CONNECTOR:
            return this.basicGetSinkRole__AssemblyEventConnector() != null;
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ROLE_ASSEMBLY_EVENT_CONNECTOR:
            return this.basicGetSourceRole__AssemblyEventConnector() != null;
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SINK_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR:
            return this.basicGetSinkAssemblyContext__AssemblyEventConnector() != null;
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__SOURCE_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR:
            return this.basicGetSourceAssemblyContext__AssemblyEventConnector() != null;
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR__FILTER_CONDITION_ASSEMBLY_EVENT_CONNECTOR:
            return this.getFilterCondition__AssemblyEventConnector() != null;
        }
        return super.eIsSet(featureID);
    }

} // AssemblyEventConnectorImpl
