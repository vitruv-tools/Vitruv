/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Fork Action</b></em>'. <!--
 * end-user-doc -->
 *
 * <!-- begin-model-doc --> Fork Action Splits the RDSEFF control flow with an AND-semantic, meaning
 * that it invokes several ForkedBehaviours concurrently. ForkActions allow both asynchronously and
 * synchronously forked behaviours. Synchronously ForkedBehaviours execute concurrently and the
 * control flow waits for each of these behaviours to terminate before continuing. Each
 * ForkedBehaviour can be considered as a program thread. All parameter characterisations from the
 * surrounding RDSEFF are also valid inside the ForkedBehaviours and can be used to parameterise
 * resource demands or control flow constructs. The parameter characterisations are the same in each
 * ForkedBehaviour. Component developers can use a SynchronisationPoint to join synchronously
 * ForkedBehaviours and specify a result of the computations with its attached VariableUsages.
 * Asynchronously ForkedBehaviours also execute concurrently, but the control flow does not wait for
 * them to terminate and continues immediately after their invocation with the successor action of
 * the ForkAction. Therefore, there is no need for a SynchronisationPoint in this case. It is
 * furthermore not possible to refer to results or output parameters of asynchronously
 * ForkedBehaviours in the rest of the RDSEFF, as it is unclear when these results will be
 * available. The same Fork Action can contain asynchronous and synchronousForkedbehaviours at the
 * same time. In that case, all forked behaviours are started. The control flow waits for all
 * synchronous behaviours to finish execution and only then continues. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.seff.ForkAction#getAsynchronousForkedBehaviours_ForkAction
 * <em>Asynchronous Forked Behaviours Fork Action</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.seff.ForkAction#getSynchronisingBehaviours_ForkAction
 * <em>Synchronising Behaviours Fork Action</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.SeffPackage#getForkAction()
 * @model
 * @generated
 */
public interface ForkAction extends AbstractInternalControlFlowAction {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Asynchronous Forked Behaviours Fork Action</b></em>'
     * containment reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.seff.ForkedBehaviour}. It is bidirectional and its opposite
     * is '{@link org.palladiosimulator.pcm.seff.ForkedBehaviour#getForkAction_ForkedBehaivour
     * <em>Fork Action Forked Behaivour</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Asynchronous Forked Behaviours Fork Action</em>' containment
     * reference list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Asynchronous Forked Behaviours Fork Action</em>' containment
     *         reference list.
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getForkAction_AsynchronousForkedBehaviours_ForkAction()
     * @see org.palladiosimulator.pcm.seff.ForkedBehaviour#getForkAction_ForkedBehaivour
     * @model opposite="forkAction_ForkedBehaivour" containment="true" ordered="false"
     * @generated
     */
    EList<ForkedBehaviour> getAsynchronousForkedBehaviours_ForkAction();

    /**
     * Returns the value of the '<em><b>Synchronising Behaviours Fork Action</b></em>' containment
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.seff.SynchronisationPoint#getForkAction_SynchronisationPoint
     * <em>Fork Action Synchronisation Point</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Synchronising Behaviours Fork Action</em>' containment reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Synchronising Behaviours Fork Action</em>' containment
     *         reference.
     * @see #setSynchronisingBehaviours_ForkAction(SynchronisationPoint)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getForkAction_SynchronisingBehaviours_ForkAction()
     * @see org.palladiosimulator.pcm.seff.SynchronisationPoint#getForkAction_SynchronisationPoint
     * @model opposite="forkAction_SynchronisationPoint" containment="true" ordered="false"
     * @generated
     */
    SynchronisationPoint getSynchronisingBehaviours_ForkAction();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.ForkAction#getSynchronisingBehaviours_ForkAction
     * <em>Synchronising Behaviours Fork Action</em>}' containment reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Synchronising Behaviours Fork Action</em>' containment
     *            reference.
     * @see #getSynchronisingBehaviours_ForkAction()
     * @generated
     */
    void setSynchronisingBehaviours_ForkAction(SynchronisationPoint value);

} // ForkAction
