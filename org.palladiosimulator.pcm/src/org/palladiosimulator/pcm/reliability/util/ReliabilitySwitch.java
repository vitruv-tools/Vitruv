/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.reliability.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.reliability.ExternalFailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.FailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.reliability.HardwareInducedFailureType;
import org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.NetworkInducedFailureType;
import org.palladiosimulator.pcm.reliability.ReliabilityPackage;
import org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType;
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;

import de.uka.ipd.sdq.identifier.Identifier;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the
 * call {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for
 * each class of the model, starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the result of the switch.
 * <!-- end-user-doc -->
 *
 * @see org.palladiosimulator.pcm.reliability.ReliabilityPackage
 * @generated
 */
public class ReliabilitySwitch<T> {

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
    protected static ReliabilityPackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ReliabilitySwitch() {
        if (modelPackage == null) {
            modelPackage = ReliabilityPackage.eINSTANCE;
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
        case ReliabilityPackage.FAILURE_OCCURRENCE_DESCRIPTION: {
            final FailureOccurrenceDescription failureOccurrenceDescription = (FailureOccurrenceDescription) theEObject;
            T result = this.caseFailureOccurrenceDescription(failureOccurrenceDescription);
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case ReliabilityPackage.HARDWARE_INDUCED_FAILURE_TYPE: {
            final HardwareInducedFailureType hardwareInducedFailureType = (HardwareInducedFailureType) theEObject;
            T result = this.caseHardwareInducedFailureType(hardwareInducedFailureType);
            if (result == null) {
                result = this.caseFailureType(hardwareInducedFailureType);
            }
            if (result == null) {
                result = this.caseEntity(hardwareInducedFailureType);
            }
            if (result == null) {
                result = this.caseIdentifier(hardwareInducedFailureType);
            }
            if (result == null) {
                result = this.caseNamedElement(hardwareInducedFailureType);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case ReliabilityPackage.SOFTWARE_INDUCED_FAILURE_TYPE: {
            final SoftwareInducedFailureType softwareInducedFailureType = (SoftwareInducedFailureType) theEObject;
            T result = this.caseSoftwareInducedFailureType(softwareInducedFailureType);
            if (result == null) {
                result = this.caseFailureType(softwareInducedFailureType);
            }
            if (result == null) {
                result = this.caseEntity(softwareInducedFailureType);
            }
            if (result == null) {
                result = this.caseIdentifier(softwareInducedFailureType);
            }
            if (result == null) {
                result = this.caseNamedElement(softwareInducedFailureType);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case ReliabilityPackage.INTERNAL_FAILURE_OCCURRENCE_DESCRIPTION: {
            final InternalFailureOccurrenceDescription internalFailureOccurrenceDescription = (InternalFailureOccurrenceDescription) theEObject;
            T result = this.caseInternalFailureOccurrenceDescription(internalFailureOccurrenceDescription);
            if (result == null) {
                result = this.caseFailureOccurrenceDescription(internalFailureOccurrenceDescription);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case ReliabilityPackage.NETWORK_INDUCED_FAILURE_TYPE: {
            final NetworkInducedFailureType networkInducedFailureType = (NetworkInducedFailureType) theEObject;
            T result = this.caseNetworkInducedFailureType(networkInducedFailureType);
            if (result == null) {
                result = this.caseFailureType(networkInducedFailureType);
            }
            if (result == null) {
                result = this.caseEntity(networkInducedFailureType);
            }
            if (result == null) {
                result = this.caseIdentifier(networkInducedFailureType);
            }
            if (result == null) {
                result = this.caseNamedElement(networkInducedFailureType);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case ReliabilityPackage.EXTERNAL_FAILURE_OCCURRENCE_DESCRIPTION: {
            final ExternalFailureOccurrenceDescription externalFailureOccurrenceDescription = (ExternalFailureOccurrenceDescription) theEObject;
            T result = this.caseExternalFailureOccurrenceDescription(externalFailureOccurrenceDescription);
            if (result == null) {
                result = this.caseFailureOccurrenceDescription(externalFailureOccurrenceDescription);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case ReliabilityPackage.RESOURCE_TIMEOUT_FAILURE_TYPE: {
            final ResourceTimeoutFailureType resourceTimeoutFailureType = (ResourceTimeoutFailureType) theEObject;
            T result = this.caseResourceTimeoutFailureType(resourceTimeoutFailureType);
            if (result == null) {
                result = this.caseSoftwareInducedFailureType(resourceTimeoutFailureType);
            }
            if (result == null) {
                result = this.caseFailureType(resourceTimeoutFailureType);
            }
            if (result == null) {
                result = this.caseEntity(resourceTimeoutFailureType);
            }
            if (result == null) {
                result = this.caseIdentifier(resourceTimeoutFailureType);
            }
            if (result == null) {
                result = this.caseNamedElement(resourceTimeoutFailureType);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case ReliabilityPackage.FAILURE_TYPE: {
            final FailureType failureType = (FailureType) theEObject;
            T result = this.caseFailureType(failureType);
            if (result == null) {
                result = this.caseEntity(failureType);
            }
            if (result == null) {
                result = this.caseIdentifier(failureType);
            }
            if (result == null) {
                result = this.caseNamedElement(failureType);
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
     * <em>Failure Occurrence Description</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Failure Occurrence Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFailureOccurrenceDescription(final FailureOccurrenceDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Hardware Induced Failure Type</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Hardware Induced Failure Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseHardwareInducedFailureType(final HardwareInducedFailureType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Software Induced Failure Type</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Software Induced Failure Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSoftwareInducedFailureType(final SoftwareInducedFailureType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Internal Failure Occurrence Description</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Internal Failure Occurrence Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInternalFailureOccurrenceDescription(final InternalFailureOccurrenceDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Network Induced Failure Type</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Network Induced Failure Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNetworkInducedFailureType(final NetworkInducedFailureType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>External Failure Occurrence Description</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>External Failure Occurrence Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseExternalFailureOccurrenceDescription(final ExternalFailureOccurrenceDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Resource Timeout Failure Type</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Resource Timeout Failure Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResourceTimeoutFailureType(final ResourceTimeoutFailureType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Failure Type</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Failure Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFailureType(final FailureType object) {
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

} // ReliabilitySwitch
