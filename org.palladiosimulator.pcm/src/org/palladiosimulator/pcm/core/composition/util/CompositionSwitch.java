/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
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
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the
 * call {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for
 * each class of the model, starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the result of the switch.
 * <!-- end-user-doc -->
 *
 * @see org.palladiosimulator.pcm.core.composition.CompositionPackage
 * @generated
 */
public class CompositionSwitch<T> {

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
    protected static CompositionPackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public CompositionSwitch() {
        if (modelPackage == null) {
            modelPackage = CompositionPackage.eINSTANCE;
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result;
     * it yields that result. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    public T doSwitch(final EObject theEObject) {
        return this.doSwitch(theEObject.eClass(), theEObject);
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result;
     * it yields that result. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(final EClass theEClass, final EObject theEObject) {
        if (theEClass.eContainer() == modelPackage) {
            return this.doSwitch(theEClass.getClassifierID(), theEObject);
        } else {
            final List<EClass> eSuperTypes = theEClass.getESuperTypes();
            return eSuperTypes.isEmpty() ? this.defaultCase(theEObject) : this.doSwitch(eSuperTypes.get(0), theEObject);
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result;
     * it yields that result. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(final int classifierID, final EObject theEObject) {
        switch (classifierID) {
        case CompositionPackage.DELEGATION_CONNECTOR: {
            final DelegationConnector delegationConnector = (DelegationConnector) theEObject;
            T result = this.caseDelegationConnector(delegationConnector);
            if (result == null) {
                result = this.caseConnector(delegationConnector);
            }
            if (result == null) {
                result = this.caseEntity(delegationConnector);
            }
            if (result == null) {
                result = this.caseIdentifier(delegationConnector);
            }
            if (result == null) {
                result = this.caseNamedElement(delegationConnector);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case CompositionPackage.CONNECTOR: {
            final Connector connector = (Connector) theEObject;
            T result = this.caseConnector(connector);
            if (result == null) {
                result = this.caseEntity(connector);
            }
            if (result == null) {
                result = this.caseIdentifier(connector);
            }
            if (result == null) {
                result = this.caseNamedElement(connector);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case CompositionPackage.COMPOSED_STRUCTURE: {
            final ComposedStructure composedStructure = (ComposedStructure) theEObject;
            T result = this.caseComposedStructure(composedStructure);
            if (result == null) {
                result = this.caseEntity(composedStructure);
            }
            if (result == null) {
                result = this.caseIdentifier(composedStructure);
            }
            if (result == null) {
                result = this.caseNamedElement(composedStructure);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case CompositionPackage.RESOURCE_REQUIRED_DELEGATION_CONNECTOR: {
            final ResourceRequiredDelegationConnector resourceRequiredDelegationConnector = (ResourceRequiredDelegationConnector) theEObject;
            T result = this.caseResourceRequiredDelegationConnector(resourceRequiredDelegationConnector);
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case CompositionPackage.EVENT_CHANNEL: {
            final EventChannel eventChannel = (EventChannel) theEObject;
            T result = this.caseEventChannel(eventChannel);
            if (result == null) {
                result = this.caseEntity(eventChannel);
            }
            if (result == null) {
                result = this.caseIdentifier(eventChannel);
            }
            if (result == null) {
                result = this.caseNamedElement(eventChannel);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case CompositionPackage.EVENT_CHANNEL_SOURCE_CONNECTOR: {
            final EventChannelSourceConnector eventChannelSourceConnector = (EventChannelSourceConnector) theEObject;
            T result = this.caseEventChannelSourceConnector(eventChannelSourceConnector);
            if (result == null) {
                result = this.caseConnector(eventChannelSourceConnector);
            }
            if (result == null) {
                result = this.caseEntity(eventChannelSourceConnector);
            }
            if (result == null) {
                result = this.caseIdentifier(eventChannelSourceConnector);
            }
            if (result == null) {
                result = this.caseNamedElement(eventChannelSourceConnector);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case CompositionPackage.EVENT_CHANNEL_SINK_CONNECTOR: {
            final EventChannelSinkConnector eventChannelSinkConnector = (EventChannelSinkConnector) theEObject;
            T result = this.caseEventChannelSinkConnector(eventChannelSinkConnector);
            if (result == null) {
                result = this.caseConnector(eventChannelSinkConnector);
            }
            if (result == null) {
                result = this.caseEntity(eventChannelSinkConnector);
            }
            if (result == null) {
                result = this.caseIdentifier(eventChannelSinkConnector);
            }
            if (result == null) {
                result = this.caseNamedElement(eventChannelSinkConnector);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case CompositionPackage.PROVIDED_DELEGATION_CONNECTOR: {
            final ProvidedDelegationConnector providedDelegationConnector = (ProvidedDelegationConnector) theEObject;
            T result = this.caseProvidedDelegationConnector(providedDelegationConnector);
            if (result == null) {
                result = this.caseDelegationConnector(providedDelegationConnector);
            }
            if (result == null) {
                result = this.caseConnector(providedDelegationConnector);
            }
            if (result == null) {
                result = this.caseEntity(providedDelegationConnector);
            }
            if (result == null) {
                result = this.caseIdentifier(providedDelegationConnector);
            }
            if (result == null) {
                result = this.caseNamedElement(providedDelegationConnector);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case CompositionPackage.REQUIRED_DELEGATION_CONNECTOR: {
            final RequiredDelegationConnector requiredDelegationConnector = (RequiredDelegationConnector) theEObject;
            T result = this.caseRequiredDelegationConnector(requiredDelegationConnector);
            if (result == null) {
                result = this.caseDelegationConnector(requiredDelegationConnector);
            }
            if (result == null) {
                result = this.caseConnector(requiredDelegationConnector);
            }
            if (result == null) {
                result = this.caseEntity(requiredDelegationConnector);
            }
            if (result == null) {
                result = this.caseIdentifier(requiredDelegationConnector);
            }
            if (result == null) {
                result = this.caseNamedElement(requiredDelegationConnector);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case CompositionPackage.ASSEMBLY_CONNECTOR: {
            final AssemblyConnector assemblyConnector = (AssemblyConnector) theEObject;
            T result = this.caseAssemblyConnector(assemblyConnector);
            if (result == null) {
                result = this.caseConnector(assemblyConnector);
            }
            if (result == null) {
                result = this.caseEntity(assemblyConnector);
            }
            if (result == null) {
                result = this.caseIdentifier(assemblyConnector);
            }
            if (result == null) {
                result = this.caseNamedElement(assemblyConnector);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case CompositionPackage.ASSEMBLY_EVENT_CONNECTOR: {
            final AssemblyEventConnector assemblyEventConnector = (AssemblyEventConnector) theEObject;
            T result = this.caseAssemblyEventConnector(assemblyEventConnector);
            if (result == null) {
                result = this.caseConnector(assemblyEventConnector);
            }
            if (result == null) {
                result = this.caseEntity(assemblyEventConnector);
            }
            if (result == null) {
                result = this.caseIdentifier(assemblyEventConnector);
            }
            if (result == null) {
                result = this.caseNamedElement(assemblyEventConnector);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case CompositionPackage.SOURCE_DELEGATION_CONNECTOR: {
            final SourceDelegationConnector sourceDelegationConnector = (SourceDelegationConnector) theEObject;
            T result = this.caseSourceDelegationConnector(sourceDelegationConnector);
            if (result == null) {
                result = this.caseDelegationConnector(sourceDelegationConnector);
            }
            if (result == null) {
                result = this.caseConnector(sourceDelegationConnector);
            }
            if (result == null) {
                result = this.caseEntity(sourceDelegationConnector);
            }
            if (result == null) {
                result = this.caseIdentifier(sourceDelegationConnector);
            }
            if (result == null) {
                result = this.caseNamedElement(sourceDelegationConnector);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case CompositionPackage.SINK_DELEGATION_CONNECTOR: {
            final SinkDelegationConnector sinkDelegationConnector = (SinkDelegationConnector) theEObject;
            T result = this.caseSinkDelegationConnector(sinkDelegationConnector);
            if (result == null) {
                result = this.caseDelegationConnector(sinkDelegationConnector);
            }
            if (result == null) {
                result = this.caseConnector(sinkDelegationConnector);
            }
            if (result == null) {
                result = this.caseEntity(sinkDelegationConnector);
            }
            if (result == null) {
                result = this.caseIdentifier(sinkDelegationConnector);
            }
            if (result == null) {
                result = this.caseNamedElement(sinkDelegationConnector);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR: {
            final AssemblyInfrastructureConnector assemblyInfrastructureConnector = (AssemblyInfrastructureConnector) theEObject;
            T result = this.caseAssemblyInfrastructureConnector(assemblyInfrastructureConnector);
            if (result == null) {
                result = this.caseConnector(assemblyInfrastructureConnector);
            }
            if (result == null) {
                result = this.caseEntity(assemblyInfrastructureConnector);
            }
            if (result == null) {
                result = this.caseIdentifier(assemblyInfrastructureConnector);
            }
            if (result == null) {
                result = this.caseNamedElement(assemblyInfrastructureConnector);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case CompositionPackage.PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR: {
            final ProvidedInfrastructureDelegationConnector providedInfrastructureDelegationConnector = (ProvidedInfrastructureDelegationConnector) theEObject;
            T result = this.caseProvidedInfrastructureDelegationConnector(providedInfrastructureDelegationConnector);
            if (result == null) {
                result = this.caseDelegationConnector(providedInfrastructureDelegationConnector);
            }
            if (result == null) {
                result = this.caseConnector(providedInfrastructureDelegationConnector);
            }
            if (result == null) {
                result = this.caseEntity(providedInfrastructureDelegationConnector);
            }
            if (result == null) {
                result = this.caseIdentifier(providedInfrastructureDelegationConnector);
            }
            if (result == null) {
                result = this.caseNamedElement(providedInfrastructureDelegationConnector);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR: {
            final RequiredInfrastructureDelegationConnector requiredInfrastructureDelegationConnector = (RequiredInfrastructureDelegationConnector) theEObject;
            T result = this.caseRequiredInfrastructureDelegationConnector(requiredInfrastructureDelegationConnector);
            if (result == null) {
                result = this.caseDelegationConnector(requiredInfrastructureDelegationConnector);
            }
            if (result == null) {
                result = this.caseConnector(requiredInfrastructureDelegationConnector);
            }
            if (result == null) {
                result = this.caseEntity(requiredInfrastructureDelegationConnector);
            }
            if (result == null) {
                result = this.caseIdentifier(requiredInfrastructureDelegationConnector);
            }
            if (result == null) {
                result = this.caseNamedElement(requiredInfrastructureDelegationConnector);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR: {
            final RequiredResourceDelegationConnector requiredResourceDelegationConnector = (RequiredResourceDelegationConnector) theEObject;
            T result = this.caseRequiredResourceDelegationConnector(requiredResourceDelegationConnector);
            if (result == null) {
                result = this.caseDelegationConnector(requiredResourceDelegationConnector);
            }
            if (result == null) {
                result = this.caseConnector(requiredResourceDelegationConnector);
            }
            if (result == null) {
                result = this.caseEntity(requiredResourceDelegationConnector);
            }
            if (result == null) {
                result = this.caseIdentifier(requiredResourceDelegationConnector);
            }
            if (result == null) {
                result = this.caseNamedElement(requiredResourceDelegationConnector);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case CompositionPackage.ASSEMBLY_CONTEXT: {
            final AssemblyContext assemblyContext = (AssemblyContext) theEObject;
            T result = this.caseAssemblyContext(assemblyContext);
            if (result == null) {
                result = this.caseEntity(assemblyContext);
            }
            if (result == null) {
                result = this.caseIdentifier(assemblyContext);
            }
            if (result == null) {
                result = this.caseNamedElement(assemblyContext);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        default:
            return this.defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Delegation Connector</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Delegation Connector</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDelegationConnector(final DelegationConnector object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Connector</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Connector</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConnector(final Connector object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Composed Structure</em>
     * '. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Composed Structure</em>
     *         '.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseComposedStructure(final ComposedStructure object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Resource Required Delegation Connector</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Resource Required Delegation Connector</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResourceRequiredDelegationConnector(final ResourceRequiredDelegationConnector object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Event Channel</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Event Channel</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEventChannel(final EventChannel object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Event Channel Source Connector</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Event Channel Source Connector</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEventChannelSourceConnector(final EventChannelSourceConnector object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Event Channel Sink Connector</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Event Channel Sink Connector</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEventChannelSinkConnector(final EventChannelSinkConnector object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Provided Delegation Connector</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Provided Delegation Connector</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseProvidedDelegationConnector(final ProvidedDelegationConnector object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Required Delegation Connector</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Required Delegation Connector</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRequiredDelegationConnector(final RequiredDelegationConnector object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Assembly Connector</em>
     * '. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Assembly Connector</em>
     *         '.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAssemblyConnector(final AssemblyConnector object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Assembly Event Connector</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Assembly Event Connector</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAssemblyEventConnector(final AssemblyEventConnector object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Source Delegation Connector</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Source Delegation Connector</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSourceDelegationConnector(final SourceDelegationConnector object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Sink Delegation Connector</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Sink Delegation Connector</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSinkDelegationConnector(final SinkDelegationConnector object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Assembly Infrastructure Connector</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Assembly Infrastructure Connector</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAssemblyInfrastructureConnector(final AssemblyInfrastructureConnector object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Provided Infrastructure Delegation Connector</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Provided Infrastructure Delegation Connector</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseProvidedInfrastructureDelegationConnector(final ProvidedInfrastructureDelegationConnector object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Required Infrastructure Delegation Connector</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Required Infrastructure Delegation Connector</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRequiredInfrastructureDelegationConnector(final RequiredInfrastructureDelegationConnector object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Required Resource Delegation Connector</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Required Resource Delegation Connector</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRequiredResourceDelegationConnector(final RequiredResourceDelegationConnector object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Assembly Context</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Assembly Context</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAssemblyContext(final AssemblyContext object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Identifier</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Identifier</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseIdentifier(final Identifier object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNamedElement(final NamedElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Entity</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Entity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEntity(final Entity object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch, but this is the last case anyway. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public T defaultCase(final EObject object) {
        return null;
    }

} // CompositionSwitch
