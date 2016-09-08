/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.AssemblyEventConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.composition.CompositionPackage;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.composition.DelegationConnector;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector;
import org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector;
import org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector;
import org.palladiosimulator.pcm.core.composition.SinkDelegationConnector;
import org.palladiosimulator.pcm.core.composition.SourceDelegationConnector;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.core.entity.NamedElement;

import de.uka.ipd.sdq.identifier.Identifier;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter
 * <code>createXXX</code> method for each class of the model. <!-- end-user-doc -->
 * 
 * @see org.palladiosimulator.pcm.core.composition.CompositionPackage
 * @generated
 */
public class CompositionAdapterFactory extends AdapterFactoryImpl {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected static CompositionPackage modelPackage;

    /**
     * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public CompositionAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = CompositionPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc
     * --> This implementation returns <code>true</code> if the object is either the model's package
     * or is an instance object of the model. <!-- end-user-doc -->
     * 
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(final Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject) object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    protected CompositionSwitch<Adapter> modelSwitch = new CompositionSwitch<Adapter>() {
        @Override
        public Adapter caseDelegationConnector(final DelegationConnector object) {
            return CompositionAdapterFactory.this.createDelegationConnectorAdapter();
        }

        @Override
        public Adapter caseConnector(final Connector object) {
            return CompositionAdapterFactory.this.createConnectorAdapter();
        }

        @Override
        public Adapter caseComposedStructure(final ComposedStructure object) {
            return CompositionAdapterFactory.this.createComposedStructureAdapter();
        }

        @Override
        public Adapter caseResourceRequiredDelegationConnector(final ResourceRequiredDelegationConnector object) {
            return CompositionAdapterFactory.this.createResourceRequiredDelegationConnectorAdapter();
        }

        @Override
        public Adapter caseEventChannel(final EventChannel object) {
            return CompositionAdapterFactory.this.createEventChannelAdapter();
        }

        @Override
        public Adapter caseEventChannelSourceConnector(final EventChannelSourceConnector object) {
            return CompositionAdapterFactory.this.createEventChannelSourceConnectorAdapter();
        }

        @Override
        public Adapter caseEventChannelSinkConnector(final EventChannelSinkConnector object) {
            return CompositionAdapterFactory.this.createEventChannelSinkConnectorAdapter();
        }

        @Override
        public Adapter caseProvidedDelegationConnector(final ProvidedDelegationConnector object) {
            return CompositionAdapterFactory.this.createProvidedDelegationConnectorAdapter();
        }

        @Override
        public Adapter caseRequiredDelegationConnector(final RequiredDelegationConnector object) {
            return CompositionAdapterFactory.this.createRequiredDelegationConnectorAdapter();
        }

        @Override
        public Adapter caseAssemblyConnector(final AssemblyConnector object) {
            return CompositionAdapterFactory.this.createAssemblyConnectorAdapter();
        }

        @Override
        public Adapter caseAssemblyEventConnector(final AssemblyEventConnector object) {
            return CompositionAdapterFactory.this.createAssemblyEventConnectorAdapter();
        }

        @Override
        public Adapter caseSourceDelegationConnector(final SourceDelegationConnector object) {
            return CompositionAdapterFactory.this.createSourceDelegationConnectorAdapter();
        }

        @Override
        public Adapter caseSinkDelegationConnector(final SinkDelegationConnector object) {
            return CompositionAdapterFactory.this.createSinkDelegationConnectorAdapter();
        }

        @Override
        public Adapter caseAssemblyInfrastructureConnector(final AssemblyInfrastructureConnector object) {
            return CompositionAdapterFactory.this.createAssemblyInfrastructureConnectorAdapter();
        }

        @Override
        public Adapter caseProvidedInfrastructureDelegationConnector(
                final ProvidedInfrastructureDelegationConnector object) {
            return CompositionAdapterFactory.this.createProvidedInfrastructureDelegationConnectorAdapter();
        }

        @Override
        public Adapter caseRequiredInfrastructureDelegationConnector(
                final RequiredInfrastructureDelegationConnector object) {
            return CompositionAdapterFactory.this.createRequiredInfrastructureDelegationConnectorAdapter();
        }

        @Override
        public Adapter caseRequiredResourceDelegationConnector(final RequiredResourceDelegationConnector object) {
            return CompositionAdapterFactory.this.createRequiredResourceDelegationConnectorAdapter();
        }

        @Override
        public Adapter caseAssemblyContext(final AssemblyContext object) {
            return CompositionAdapterFactory.this.createAssemblyContextAdapter();
        }

        @Override
        public Adapter caseIdentifier(final Identifier object) {
            return CompositionAdapterFactory.this.createIdentifierAdapter();
        }

        @Override
        public Adapter caseNamedElement(final NamedElement object) {
            return CompositionAdapterFactory.this.createNamedElementAdapter();
        }

        @Override
        public Adapter caseEntity(final Entity object) {
            return CompositionAdapterFactory.this.createEntityAdapter();
        }

        @Override
        public Adapter defaultCase(final EObject object) {
            return CompositionAdapterFactory.this.createEObjectAdapter();
        }
    };

    /**
     * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param target
     *            the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(final Notifier target) {
        return this.modelSwitch.doSwitch((EObject) target);
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.composition.DelegationConnector
     * <em>Delegation Connector</em>}'. <!-- begin-user-doc --> This default implementation returns
     * null so that we can easily ignore cases; it's useful to ignore a case when inheritance will
     * catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.composition.DelegationConnector
     * @generated
     */
    public Adapter createDelegationConnectorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.composition.Connector <em>Connector</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.composition.Connector
     * @generated
     */
    public Adapter createConnectorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.composition.ComposedStructure
     * <em>Composed Structure</em>}'. <!-- begin-user-doc --> This default implementation returns
     * null so that we can easily ignore cases; it's useful to ignore a case when inheritance will
     * catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.composition.ComposedStructure
     * @generated
     */
    public Adapter createComposedStructureAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector
     * <em>Resource Required Delegation Connector</em>}'. <!-- begin-user-doc --> This default
     * implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector
     * @generated
     */
    public Adapter createResourceRequiredDelegationConnectorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannel <em>Event Channel</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.composition.EventChannel
     * @generated
     */
    public Adapter createEventChannelAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector
     * <em>Event Channel Source Connector</em>}'. <!-- begin-user-doc --> This default
     * implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector
     * @generated
     */
    public Adapter createEventChannelSourceConnectorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
     * <em>Event Channel Sink Connector</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
     * @generated
     */
    public Adapter createEventChannelSinkConnectorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector
     * <em>Provided Delegation Connector</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector
     * @generated
     */
    public Adapter createProvidedDelegationConnectorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector
     * <em>Required Delegation Connector</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector
     * @generated
     */
    public Adapter createRequiredDelegationConnectorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyConnector
     * <em>Assembly Connector</em>}'. <!-- begin-user-doc --> This default implementation returns
     * null so that we can easily ignore cases; it's useful to ignore a case when inheritance will
     * catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyConnector
     * @generated
     */
    public Adapter createAssemblyConnectorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector
     * <em>Assembly Event Connector</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyEventConnector
     * @generated
     */
    public Adapter createAssemblyEventConnectorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.composition.SourceDelegationConnector
     * <em>Source Delegation Connector</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.composition.SourceDelegationConnector
     * @generated
     */
    public Adapter createSourceDelegationConnectorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.composition.SinkDelegationConnector
     * <em>Sink Delegation Connector</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.composition.SinkDelegationConnector
     * @generated
     */
    public Adapter createSinkDelegationConnectorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector
     * <em>Assembly Infrastructure Connector</em>}'. <!-- begin-user-doc --> This default
     * implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector
     * @generated
     */
    public Adapter createAssemblyInfrastructureConnectorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector
     * <em>Provided Infrastructure Delegation Connector</em>}'. <!-- begin-user-doc --> This default
     * implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector
     * @generated
     */
    public Adapter createProvidedInfrastructureDelegationConnectorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector
     * <em>Required Infrastructure Delegation Connector</em>}'. <!-- begin-user-doc --> This default
     * implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector
     * @generated
     */
    public Adapter createRequiredInfrastructureDelegationConnectorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector
     * <em>Required Resource Delegation Connector</em>}'. <!-- begin-user-doc --> This default
     * implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector
     * @generated
     */
    public Adapter createRequiredResourceDelegationConnectorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext <em>Assembly Context</em>}
     * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @generated
     */
    public Adapter createAssemblyContextAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link de.uka.ipd.sdq.identifier.Identifier
     * <em>Identifier</em>}'. <!-- begin-user-doc --> This default implementation returns null so
     * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
     * the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see de.uka.ipd.sdq.identifier.Identifier
     * @generated
     */
    public Adapter createIdentifierAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.entity.NamedElement <em>Named Element</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.entity.NamedElement
     * @generated
     */
    public Adapter createNamedElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.entity.Entity <em>Entity</em>}'. <!-- begin-user-doc
     * --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.entity.Entity
     * @generated
     */
    public Adapter createEntityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case. <!-- begin-user-doc --> This default
     * implementation returns null. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} // CompositionAdapterFactory
