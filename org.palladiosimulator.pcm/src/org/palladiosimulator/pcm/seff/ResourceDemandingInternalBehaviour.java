/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff;

import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.repository.BasicComponent;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Resource Demanding Internal Behaviour</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> Class representing component-internal behaviour not accessible from the
 * component's interface. Comparable to internal method in object-oriented programming. This
 * behaviour can be called from within a resource demanding behaviour using an InternalCallAction.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour#getBasicComponent_ResourceDemandingInternalBehaviour
 * <em>Basic Component Resource Demanding Internal Behaviour</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.SeffPackage#getResourceDemandingInternalBehaviour()
 * @model
 * @generated
 */
public interface ResourceDemandingInternalBehaviour extends ResourceDemandingBehaviour, Entity {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '
     * <em><b>Basic Component Resource Demanding Internal Behaviour</b></em>' container reference.
     * It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.repository.BasicComponent#getResourceDemandingInternalBehaviours__BasicComponent
     * <em>Resource Demanding Internal Behaviours Basic Component</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Basic Component Resource Demanding Internal Behaviour</em>'
     * container reference isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Basic Component Resource Demanding Internal Behaviour</em>'
     *         container reference.
     * @see #setBasicComponent_ResourceDemandingInternalBehaviour(BasicComponent)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getResourceDemandingInternalBehaviour_BasicComponent_ResourceDemandingInternalBehaviour()
     * @see org.palladiosimulator.pcm.repository.BasicComponent#getResourceDemandingInternalBehaviours__BasicComponent
     * @model opposite="resourceDemandingInternalBehaviours__BasicComponent" required="true"
     *        transient="false" ordered="false"
     * @generated
     */
    BasicComponent getBasicComponent_ResourceDemandingInternalBehaviour();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour#getBasicComponent_ResourceDemandingInternalBehaviour
     * <em>Basic Component Resource Demanding Internal Behaviour</em>}' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '
     *            <em>Basic Component Resource Demanding Internal Behaviour</em>' container
     *            reference.
     * @see #getBasicComponent_ResourceDemandingInternalBehaviour()
     * @generated
     */
    void setBasicComponent_ResourceDemandingInternalBehaviour(BasicComponent value);

} // ResourceDemandingInternalBehaviour
