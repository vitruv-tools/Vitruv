/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Forked Behaviour</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> A ForkedBehaviour can be considered as a program thread. All parameter
 * characterisations from the surrounding RDSEFF are also valid inside the ForkedBehaviours and can
 * be used to parameterise resource demands or control flow constructs. The parameter
 * characterisations are the same in each ForkedBehaviour. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.seff.ForkedBehaviour#getSynchronisationPoint_ForkedBehaviour
 * <em>Synchronisation Point Forked Behaviour</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.seff.ForkedBehaviour#getForkAction_ForkedBehaivour
 * <em>Fork Action Forked Behaivour</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.SeffPackage#getForkedBehaviour()
 * @model
 * @generated
 */
public interface ForkedBehaviour extends ResourceDemandingBehaviour {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Synchronisation Point Forked Behaviour</b></em>' container
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.seff.SynchronisationPoint#getSynchronousForkedBehaviours_SynchronisationPoint
     * <em>Synchronous Forked Behaviours Synchronisation Point</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Synchronisation Point Forked Behaviour</em>' container reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Synchronisation Point Forked Behaviour</em>' container
     *         reference.
     * @see #setSynchronisationPoint_ForkedBehaviour(SynchronisationPoint)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getForkedBehaviour_SynchronisationPoint_ForkedBehaviour()
     * @see org.palladiosimulator.pcm.seff.SynchronisationPoint#getSynchronousForkedBehaviours_SynchronisationPoint
     * @model opposite="synchronousForkedBehaviours_SynchronisationPoint" transient="false"
     *        ordered="false"
     * @generated
     */
    SynchronisationPoint getSynchronisationPoint_ForkedBehaviour();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.ForkedBehaviour#getSynchronisationPoint_ForkedBehaviour
     * <em>Synchronisation Point Forked Behaviour</em>}' container reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Synchronisation Point Forked Behaviour</em>' container
     *            reference.
     * @see #getSynchronisationPoint_ForkedBehaviour()
     * @generated
     */
    void setSynchronisationPoint_ForkedBehaviour(SynchronisationPoint value);

    /**
     * Returns the value of the '<em><b>Fork Action Forked Behaivour</b></em>' container reference.
     * It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.seff.ForkAction#getAsynchronousForkedBehaviours_ForkAction
     * <em>Asynchronous Forked Behaviours Fork Action</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Fork Action Forked Behaivour</em>' container reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Fork Action Forked Behaivour</em>' container reference.
     * @see #setForkAction_ForkedBehaivour(ForkAction)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getForkedBehaviour_ForkAction_ForkedBehaivour()
     * @see org.palladiosimulator.pcm.seff.ForkAction#getAsynchronousForkedBehaviours_ForkAction
     * @model opposite="asynchronousForkedBehaviours_ForkAction" transient="false" ordered="false"
     * @generated
     */
    ForkAction getForkAction_ForkedBehaivour();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.ForkedBehaviour#getForkAction_ForkedBehaivour
     * <em>Fork Action Forked Behaivour</em>}' container reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Fork Action Forked Behaivour</em>' container reference.
     * @see #getForkAction_ForkedBehaivour()
     * @generated
     */
    void setForkAction_ForkedBehaivour(ForkAction value);

} // ForkedBehaviour
