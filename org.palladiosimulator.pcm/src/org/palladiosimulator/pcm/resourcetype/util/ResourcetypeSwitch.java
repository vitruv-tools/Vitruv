/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.resourcetype.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingEntity;
import org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.resourcetype.ResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourcetypePackage;
import org.palladiosimulator.pcm.resourcetype.SchedulingPolicy;

import de.uka.ipd.sdq.identifier.Identifier;
import de.uka.ipd.sdq.units.UnitCarryingElement;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the
 * call {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for
 * each class of the model, starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the result of the switch.
 * <!-- end-user-doc -->
 *
 * @see org.palladiosimulator.pcm.resourcetype.ResourcetypePackage
 * @generated
 */
public class ResourcetypeSwitch<T> {

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
    protected static ResourcetypePackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ResourcetypeSwitch() {
        if (modelPackage == null) {
            modelPackage = ResourcetypePackage.eINSTANCE;
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
        case ResourcetypePackage.RESOURCE_SIGNATURE: {
            final ResourceSignature resourceSignature = (ResourceSignature) theEObject;
            T result = this.caseResourceSignature(resourceSignature);
            if (result == null) {
                result = this.caseEntity(resourceSignature);
            }
            if (result == null) {
                result = this.caseIdentifier(resourceSignature);
            }
            if (result == null) {
                result = this.caseNamedElement(resourceSignature);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case ResourcetypePackage.PROCESSING_RESOURCE_TYPE: {
            final ProcessingResourceType processingResourceType = (ProcessingResourceType) theEObject;
            T result = this.caseProcessingResourceType(processingResourceType);
            if (result == null) {
                result = this.caseResourceType(processingResourceType);
            }
            if (result == null) {
                result = this.caseUnitCarryingElement(processingResourceType);
            }
            if (result == null) {
                result = this.caseResourceInterfaceProvidingEntity(processingResourceType);
            }
            if (result == null) {
                result = this.caseEntity(processingResourceType);
            }
            if (result == null) {
                result = this.caseIdentifier(processingResourceType);
            }
            if (result == null) {
                result = this.caseNamedElement(processingResourceType);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case ResourcetypePackage.RESOURCE_TYPE: {
            final ResourceType resourceType = (ResourceType) theEObject;
            T result = this.caseResourceType(resourceType);
            if (result == null) {
                result = this.caseUnitCarryingElement(resourceType);
            }
            if (result == null) {
                result = this.caseResourceInterfaceProvidingEntity(resourceType);
            }
            if (result == null) {
                result = this.caseEntity(resourceType);
            }
            if (result == null) {
                result = this.caseIdentifier(resourceType);
            }
            if (result == null) {
                result = this.caseNamedElement(resourceType);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case ResourcetypePackage.RESOURCE_REPOSITORY: {
            final ResourceRepository resourceRepository = (ResourceRepository) theEObject;
            T result = this.caseResourceRepository(resourceRepository);
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case ResourcetypePackage.SCHEDULING_POLICY: {
            final SchedulingPolicy schedulingPolicy = (SchedulingPolicy) theEObject;
            T result = this.caseSchedulingPolicy(schedulingPolicy);
            if (result == null) {
                result = this.caseEntity(schedulingPolicy);
            }
            if (result == null) {
                result = this.caseIdentifier(schedulingPolicy);
            }
            if (result == null) {
                result = this.caseNamedElement(schedulingPolicy);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case ResourcetypePackage.COMMUNICATION_LINK_RESOURCE_TYPE: {
            final CommunicationLinkResourceType communicationLinkResourceType = (CommunicationLinkResourceType) theEObject;
            T result = this.caseCommunicationLinkResourceType(communicationLinkResourceType);
            if (result == null) {
                result = this.caseResourceType(communicationLinkResourceType);
            }
            if (result == null) {
                result = this.caseUnitCarryingElement(communicationLinkResourceType);
            }
            if (result == null) {
                result = this.caseResourceInterfaceProvidingEntity(communicationLinkResourceType);
            }
            if (result == null) {
                result = this.caseEntity(communicationLinkResourceType);
            }
            if (result == null) {
                result = this.caseIdentifier(communicationLinkResourceType);
            }
            if (result == null) {
                result = this.caseNamedElement(communicationLinkResourceType);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case ResourcetypePackage.RESOURCE_INTERFACE: {
            final ResourceInterface resourceInterface = (ResourceInterface) theEObject;
            T result = this.caseResourceInterface(resourceInterface);
            if (result == null) {
                result = this.caseEntity(resourceInterface);
            }
            if (result == null) {
                result = this.caseIdentifier(resourceInterface);
            }
            if (result == null) {
                result = this.caseNamedElement(resourceInterface);
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
     * Returns the result of interpreting the object as an instance of '<em>Resource Signature</em>
     * '. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Resource Signature</em>
     *         '.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResourceSignature(final ResourceSignature object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Processing Resource Type</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Processing Resource Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseProcessingResourceType(final ProcessingResourceType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Resource Type</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Resource Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResourceType(final ResourceType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Resource Repository</em>
     * '. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Resource Repository</em>
     *         '.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResourceRepository(final ResourceRepository object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Scheduling Policy</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Scheduling Policy</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSchedulingPolicy(final SchedulingPolicy object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Communication Link Resource Type</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Communication Link Resource Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCommunicationLinkResourceType(final CommunicationLinkResourceType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Resource Interface</em>
     * '. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Resource Interface</em>
     *         '.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResourceInterface(final ResourceInterface object) {
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
     * Returns the result of interpreting the object as an instance of '
     * <em>Unit Carrying Element</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Unit Carrying Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUnitCarryingElement(final UnitCarryingElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Resource Interface Providing Entity</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Resource Interface Providing Entity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResourceInterfaceProvidingEntity(final ResourceInterfaceProvidingEntity object) {
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

} // ResourcetypeSwitch
