/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Basic Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> This entity represents a black-box component implementation. Basic
 * components are atomic building blocks of a software architecture. They cannot be further
 * subdivided into smaller components and are built from scratch, i.e, not by assembling other
 * components. Component developers specify basic components by associating interfaces to them in a
 * providing or requiring role. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.BasicComponent#getServiceEffectSpecifications__BasicComponent
 * <em>Service Effect Specifications Basic Component</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.repository.BasicComponent#getPassiveResource_BasicComponent
 * <em>Passive Resource Basic Component</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.BasicComponent#getResourceDemandingInternalBehaviours__BasicComponent
 * <em>Resource Demanding Internal Behaviours Basic Component</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getBasicComponent()
 * @model
 * @generated
 */
public interface BasicComponent extends ImplementationComponentType {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Service Effect Specifications Basic Component</b></em>'
     * containment reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.seff.ServiceEffectSpecification}. It is bidirectional and
     * its opposite is '
     * {@link org.palladiosimulator.pcm.seff.ServiceEffectSpecification#getBasicComponent_ServiceEffectSpecification
     * <em>Basic Component Service Effect Specification</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc --> <!-- begin-model-doc --> This property contains the service effect
     * specification for services provided by this basic component. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Service Effect Specifications Basic Component</em>' containment
     *         reference list.
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getBasicComponent_ServiceEffectSpecifications__BasicComponent()
     * @see org.palladiosimulator.pcm.seff.ServiceEffectSpecification#getBasicComponent_ServiceEffectSpecification
     * @model opposite="basicComponent_ServiceEffectSpecification" containment="true"
     *        ordered="false"
     * @generated
     */
    EList<ServiceEffectSpecification> getServiceEffectSpecifications__BasicComponent();

    /**
     * Returns the value of the '<em><b>Passive Resource Basic Component</b></em>' containment
     * reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.repository.PassiveResource}. It is bidirectional and its
     * opposite is '
     * {@link org.palladiosimulator.pcm.repository.PassiveResource#getBasicComponent_PassiveResource
     * <em>Basic Component Passive Resource</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * <!-- begin-model-doc --> This property represents the passive resources, e.g., semaphores,
     * that are owned by this basic component. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Passive Resource Basic Component</em>' containment reference
     *         list.
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getBasicComponent_PassiveResource_BasicComponent()
     * @see org.palladiosimulator.pcm.repository.PassiveResource#getBasicComponent_PassiveResource
     * @model opposite="basicComponent_PassiveResource" containment="true" ordered="false"
     * @generated
     */
    EList<PassiveResource> getPassiveResource_BasicComponent();

    /**
     * Returns the value of the '
     * <em><b>Resource Demanding Internal Behaviours Basic Component</b></em>' containment reference
     * list. The list contents are of type
     * {@link org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour}. It is
     * bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour#getBasicComponent_ResourceDemandingInternalBehaviour
     * <em>Basic Component Resource Demanding Internal Behaviour</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Demanding Internal Behaviours Basic Component</em>'
     * containment reference list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Resource Demanding Internal Behaviours Basic Component</em>'
     *         containment reference list.
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getBasicComponent_ResourceDemandingInternalBehaviours__BasicComponent()
     * @see org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour#getBasicComponent_ResourceDemandingInternalBehaviour
     * @model opposite="basicComponent_ResourceDemandingInternalBehaviour" containment="true"
     *        ordered="false"
     * @generated
     */
    EList<ResourceDemandingInternalBehaviour> getResourceDemandingInternalBehaviours__BasicComponent();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.serviceEffectSpecifications__BasicComponent->forAll(p1, p2 |\r\n  p1 <> p2 implies (p1.describedService__SEFF = p2.describedService__SEFF implies p1.seffTypeID <> p2.seffTypeID))'"
     * @generated
     */
    boolean NoSeffTypeUsedTwice(DiagnosticChain diagnostics, Map<Object, Object> context);

} // BasicComponent
