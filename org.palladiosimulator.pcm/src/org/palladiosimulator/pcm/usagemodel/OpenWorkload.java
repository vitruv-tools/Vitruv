/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.usagemodel;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.palladiosimulator.pcm.core.PCMRandomVariable;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Open Workload</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> OpenWorkload specifies usage intensity with an inter-arrival time (i.e.,
 * the time between two user arrivals at the system) as a RandomVariable with an arbitrary
 * probability distribution. It models that an infinite stream of users arrives at a system. The
 * users execute their scenario, and then leave the system. The user population (i.e., the number of
 * users concurrently present in a system) is not fixed in an OpenWorkload. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.usagemodel.OpenWorkload#getInterArrivalTime_OpenWorkload
 * <em>Inter Arrival Time Open Workload</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.usagemodel.UsagemodelPackage#getOpenWorkload()
 * @model
 * @generated
 */
public interface OpenWorkload extends Workload {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Inter Arrival Time Open Workload</b></em>' containment
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.core.PCMRandomVariable#getOpenWorkload_PCMRandomVariable
     * <em>Open Workload PCM Random Variable</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Inter Arrival Time Open Workload</em>' containment reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Inter Arrival Time Open Workload</em>' containment reference.
     * @see #setInterArrivalTime_OpenWorkload(PCMRandomVariable)
     * @see org.palladiosimulator.pcm.usagemodel.UsagemodelPackage#getOpenWorkload_InterArrivalTime_OpenWorkload()
     * @see org.palladiosimulator.pcm.core.PCMRandomVariable#getOpenWorkload_PCMRandomVariable
     * @model opposite="openWorkload_PCMRandomVariable" containment="true" required="true"
     *        ordered="false"
     * @generated
     */
    PCMRandomVariable getInterArrivalTime_OpenWorkload();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.usagemodel.OpenWorkload#getInterArrivalTime_OpenWorkload
     * <em>Inter Arrival Time Open Workload</em>}' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Inter Arrival Time Open Workload</em>' containment
     *            reference.
     * @see #getInterArrivalTime_OpenWorkload()
     * @generated
     */
    void setInterArrivalTime_OpenWorkload(PCMRandomVariable value);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='not self.interArrivalTime_OpenWorkload.oclIsUndefined() and self.interArrivalTime_OpenWorkload.specification <> \'\''"
     * @generated
     */
    boolean InterArrivalTimeInOpenWorkloadNeedsToBeSpecified(DiagnosticChain diagnostics, Map<Object, Object> context);

} // OpenWorkload
