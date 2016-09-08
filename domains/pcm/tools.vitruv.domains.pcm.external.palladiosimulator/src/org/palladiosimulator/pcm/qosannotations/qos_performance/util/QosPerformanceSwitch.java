/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.qosannotations.qos_performance.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.qosannotations.SpecifiedQoSAnnotation;
import org.palladiosimulator.pcm.qosannotations.qos_performance.ComponentSpecifiedExecutionTime;
import org.palladiosimulator.pcm.qosannotations.qos_performance.QosPerformancePackage;
import org.palladiosimulator.pcm.qosannotations.qos_performance.SpecifiedExecutionTime;
import org.palladiosimulator.pcm.qosannotations.qos_performance.SystemSpecifiedExecutionTime;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the
 * call {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for
 * each class of the model, starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the result of the switch.
 * <!-- end-user-doc -->
 * 
 * @see org.palladiosimulator.pcm.qosannotations.qos_performance.QosPerformancePackage
 * @generated
 */
public class QosPerformanceSwitch<T> {

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
    protected static QosPerformancePackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public QosPerformanceSwitch() {
        if (modelPackage == null) {
            modelPackage = QosPerformancePackage.eINSTANCE;
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
        case QosPerformancePackage.SYSTEM_SPECIFIED_EXECUTION_TIME: {
            final SystemSpecifiedExecutionTime systemSpecifiedExecutionTime = (SystemSpecifiedExecutionTime) theEObject;
            T result = this.caseSystemSpecifiedExecutionTime(systemSpecifiedExecutionTime);
            if (result == null) {
                result = this.caseSpecifiedExecutionTime(systemSpecifiedExecutionTime);
            }
            if (result == null) {
                result = this.caseSpecifiedQoSAnnotation(systemSpecifiedExecutionTime);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case QosPerformancePackage.SPECIFIED_EXECUTION_TIME: {
            final SpecifiedExecutionTime specifiedExecutionTime = (SpecifiedExecutionTime) theEObject;
            T result = this.caseSpecifiedExecutionTime(specifiedExecutionTime);
            if (result == null) {
                result = this.caseSpecifiedQoSAnnotation(specifiedExecutionTime);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case QosPerformancePackage.COMPONENT_SPECIFIED_EXECUTION_TIME: {
            final ComponentSpecifiedExecutionTime componentSpecifiedExecutionTime = (ComponentSpecifiedExecutionTime) theEObject;
            T result = this.caseComponentSpecifiedExecutionTime(componentSpecifiedExecutionTime);
            if (result == null) {
                result = this.caseSpecifiedExecutionTime(componentSpecifiedExecutionTime);
            }
            if (result == null) {
                result = this.caseSpecifiedQoSAnnotation(componentSpecifiedExecutionTime);
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
     * <em>System Specified Execution Time</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>System Specified Execution Time</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSystemSpecifiedExecutionTime(final SystemSpecifiedExecutionTime object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Specified Execution Time</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Specified Execution Time</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSpecifiedExecutionTime(final SpecifiedExecutionTime object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Component Specified Execution Time</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Component Specified Execution Time</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseComponentSpecifiedExecutionTime(final ComponentSpecifiedExecutionTime object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Specified Qo SAnnotation</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Specified Qo SAnnotation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSpecifiedQoSAnnotation(final SpecifiedQoSAnnotation object) {
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

} // QosPerformanceSwitch
