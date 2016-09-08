/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.reliability;

import java.util.Map;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.DiagnosticChain;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Failure Occurrence Description</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * <p>
 * Abstract superclass that connects a failure type definition to an occurrence probability. Used to
 * describe the failure potential of certain points of failure within the control and data flow
 * (i.e. Actions within ResourceDemandingBehaviours).
 * </p>
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.reliability.FailureOccurrenceDescription#getFailureProbability
 * <em>Failure Probability</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.reliability.ReliabilityPackage#getFailureOccurrenceDescription()
 * @model abstract="true"
 * @extends CDOObject
 * @generated
 */
public interface FailureOccurrenceDescription extends CDOObject {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Failure Probability</b></em>' attribute. <!-- begin-user-doc
     * -->
     * <p>
     * If the meaning of the '<em>Failure Probability</em>' attribute isn't clear, there really
     * should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Failure Probability</em>' attribute.
     * @see #setFailureProbability(double)
     * @see org.palladiosimulator.pcm.reliability.ReliabilityPackage#getFailureOccurrenceDescription_FailureProbability()
     * @model required="true" ordered="false"
     * @generated
     */
    double getFailureProbability();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.reliability.FailureOccurrenceDescription#getFailureProbability
     * <em>Failure Probability</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Failure Probability</em>' attribute.
     * @see #getFailureProbability()
     * @generated
     */
    void setFailureProbability(double value);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='(self.failureProbability.oclAsType(Real) <= 1.0) and (self.failureProbability.oclAsType(Real) >= 0.0)'"
     * @generated
     */
    boolean EnsureValidFailureProbabilityRange(DiagnosticChain diagnostics, Map<Object, Object> context);

} // FailureOccurrenceDescription
