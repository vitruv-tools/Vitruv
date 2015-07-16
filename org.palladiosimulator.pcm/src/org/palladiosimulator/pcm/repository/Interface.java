/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.protocol.Protocol;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Interface</b></em>'. <!--
 * end-user-doc -->
 *
 * <!-- begin-model-doc --> This entity models the interface as a set of signatures representing
 * services provided or required by a component. An interface is an abstraction of piece of software
 * (a software entity) which should contain a sufficient amount of information for a caller to
 * understand and finally request the realised functionality from any entity claiming to offer the
 * specified functionality. Note that this implies, that the specification of the interface also has
 * to contain a sufficient amount of information for the implementer to actually implement the
 * interface. Due to the inherent need of an interface to abstract the behaviour of the software
 * entity not in all cases there is sufficient information provided to use or implement an interface
 * in an unambiquious way.
 *
 * Interfaces can exist on their own, i.e., without any entity requesting or implementing the
 * specified functionality. Two roles can be identified a software entity can take relative to an
 * interface. Any entity can either claim to implement the functionality specified in an interface
 * or to request that functionality. This is reflected in the Palladio Component Model by a set of
 * abstract meta-classes giving a conceptual view on interfaces, entities, and their relationships.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.repository.Interface#getParentInterfaces__Interface
 * <em>Parent Interfaces Interface</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.repository.Interface#getProtocols__Interface
 * <em>Protocols Interface</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.repository.Interface#getRequiredCharacterisations
 * <em>Required Characterisations</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.repository.Interface#getRepository__Interface
 * <em>Repository Interface</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getInterface()
 * @model abstract="true"
 * @generated
 */
public interface Interface extends Entity {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Parent Interfaces Interface</b></em>' reference list. The
     * list contents are of type {@link org.palladiosimulator.pcm.repository.Interface}. <!--
     * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> This property represents
     * the interfaces from which this interface directly inherits. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Parent Interfaces Interface</em>' reference list.
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getInterface_ParentInterfaces__Interface()
     * @model ordered="false"
     * @generated
     */
    EList<Interface> getParentInterfaces__Interface();

    /**
     * Returns the value of the '<em><b>Protocols Interface</b></em>' containment reference list.
     * The list contents are of type {@link org.palladiosimulator.pcm.protocol.Protocol}. <!--
     * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> This property represents
     * the protocol bound to this interfaces, i.e., the way, in the sense of the order, the services
     * of this interfaces are allowed to be called. <!-- end-model-doc -->
     *
     * @return the value of the '<em>Protocols Interface</em>' containment reference list.
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getInterface_Protocols__Interface()
     * @model containment="true" ordered="false"
     * @generated
     */
    EList<Protocol> getProtocols__Interface();

    /**
     * Returns the value of the '<em><b>Required Characterisations</b></em>' containment reference
     * list. The list contents are of type
     * {@link org.palladiosimulator.pcm.repository.RequiredCharacterisation}. It is bidirectional
     * and its opposite is '
     * {@link org.palladiosimulator.pcm.repository.RequiredCharacterisation#getInterface_RequiredCharacterisation
     * <em>Interface Required Characterisation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * <!-- begin-model-doc --> documentation of required variable characterisations <!--
     * end-model-doc -->
     *
     * @return the value of the '<em>Required Characterisations</em>' containment reference list.
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getInterface_RequiredCharacterisations()
     * @see org.palladiosimulator.pcm.repository.RequiredCharacterisation#getInterface_RequiredCharacterisation
     * @model opposite="interface_RequiredCharacterisation" containment="true" ordered="false"
     * @generated
     */
    EList<RequiredCharacterisation> getRequiredCharacterisations();

    /**
     * Returns the value of the '<em><b>Repository Interface</b></em>' container reference. It is
     * bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.repository.Repository#getInterfaces__Repository
     * <em>Interfaces Repository</em>}'. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
     * begin-model-doc --> This property represents the repository where this interface is stored.
     * <!-- end-model-doc -->
     *
     * @return the value of the '<em>Repository Interface</em>' container reference.
     * @see #setRepository__Interface(Repository)
     * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getInterface_Repository__Interface()
     * @see org.palladiosimulator.pcm.repository.Repository#getInterfaces__Repository
     * @model opposite="interfaces__Repository" required="true" transient="false"
     * @generated
     */
    Repository getRepository__Interface();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.repository.Interface#getRepository__Interface
     * <em>Repository Interface</em>}' container reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Repository Interface</em>' container reference.
     * @see #getRepository__Interface()
     * @generated
     */
    void setRepository__Interface(Repository value);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.protocols__Interface->forAll(p1, p2 |\r\np1.protocolTypeID <> p2.protocolTypeID)\r\n'"
     * @generated
     */
    boolean NoProtocolTypeIDUsedTwice(DiagnosticChain diagnostics, Map<Object, Object> context);

} // Interface
