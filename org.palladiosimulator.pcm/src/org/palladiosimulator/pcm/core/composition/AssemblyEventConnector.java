/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition;

import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Assembly Event Connector</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> An AssemblyConnector is a bidirectional link of two assembly contexts.
 * Intuitively, an AssemblyEventConnector connects a sink and a source. AssemblyContext must refer
 * to the tuple (Role,AssemblyContext) in order to uniquely identify which component sink and source
 * roles communicate with each other. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getSinkRole__AssemblyEventConnector
 * <em>Sink Role Assembly Event Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getSourceRole__AssemblyEventConnector
 * <em>Source Role Assembly Event Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getSinkAssemblyContext__AssemblyEventConnector
 * <em>Sink Assembly Context Assembly Event Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getSourceAssemblyContext__AssemblyEventConnector
 * <em>Source Assembly Context Assembly Event Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getFilterCondition__AssemblyEventConnector
 * <em>Filter Condition Assembly Event Connector</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getAssemblyEventConnector()
 * @model
 * @generated
 */
public interface AssemblyEventConnector extends Connector {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Sink Role Assembly Event Connector</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Sink Role Assembly Event Connector</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Sink Role Assembly Event Connector</em>' reference.
     * @see #setSinkRole__AssemblyEventConnector(SinkRole)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getAssemblyEventConnector_SinkRole__AssemblyEventConnector()
     * @model required="true"
     * @generated
     */
    SinkRole getSinkRole__AssemblyEventConnector();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getSinkRole__AssemblyEventConnector
     * <em>Sink Role Assembly Event Connector</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Sink Role Assembly Event Connector</em>' reference.
     * @see #getSinkRole__AssemblyEventConnector()
     * @generated
     */
    void setSinkRole__AssemblyEventConnector(SinkRole value);

    /**
     * Returns the value of the '<em><b>Source Role Assembly Event Connector</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Source Role Assembly Event Connector</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Source Role Assembly Event Connector</em>' reference.
     * @see #setSourceRole__AssemblyEventConnector(SourceRole)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getAssemblyEventConnector_SourceRole__AssemblyEventConnector()
     * @model required="true" ordered="false"
     * @generated
     */
    SourceRole getSourceRole__AssemblyEventConnector();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getSourceRole__AssemblyEventConnector
     * <em>Source Role Assembly Event Connector</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Source Role Assembly Event Connector</em>' reference.
     * @see #getSourceRole__AssemblyEventConnector()
     * @generated
     */
    void setSourceRole__AssemblyEventConnector(SourceRole value);

    /**
     * Returns the value of the '<em><b>Sink Assembly Context Assembly Event Connector</b></em>'
     * reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Sink Assembly Context Assembly Event Connector</em>' reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Sink Assembly Context Assembly Event Connector</em>' reference.
     * @see #setSinkAssemblyContext__AssemblyEventConnector(AssemblyContext)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getAssemblyEventConnector_SinkAssemblyContext__AssemblyEventConnector()
     * @model required="true" ordered="false"
     * @generated
     */
    AssemblyContext getSinkAssemblyContext__AssemblyEventConnector();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getSinkAssemblyContext__AssemblyEventConnector
     * <em>Sink Assembly Context Assembly Event Connector</em>}' reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Sink Assembly Context Assembly Event Connector</em>'
     *            reference.
     * @see #getSinkAssemblyContext__AssemblyEventConnector()
     * @generated
     */
    void setSinkAssemblyContext__AssemblyEventConnector(AssemblyContext value);

    /**
     * Returns the value of the '<em><b>Source Assembly Context Assembly Event Connector</b></em>'
     * reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Source Assembly Context Assembly Event Connector</em>' reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Source Assembly Context Assembly Event Connector</em>'
     *         reference.
     * @see #setSourceAssemblyContext__AssemblyEventConnector(AssemblyContext)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getAssemblyEventConnector_SourceAssemblyContext__AssemblyEventConnector()
     * @model required="true" ordered="false"
     * @generated
     */
    AssemblyContext getSourceAssemblyContext__AssemblyEventConnector();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getSourceAssemblyContext__AssemblyEventConnector
     * <em>Source Assembly Context Assembly Event Connector</em>}' reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Source Assembly Context Assembly Event Connector</em>'
     *            reference.
     * @see #getSourceAssemblyContext__AssemblyEventConnector()
     * @generated
     */
    void setSourceAssemblyContext__AssemblyEventConnector(AssemblyContext value);

    /**
     * Returns the value of the '<em><b>Filter Condition Assembly Event Connector</b></em>'
     * containment reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.core.PCMRandomVariable#getAssemblyEventConnector__FilterCondition
     * <em>Assembly Event Connector Filter Condition</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Filter Condition Assembly Event Connector</em>' containment
     * reference isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Filter Condition Assembly Event Connector</em>' containment
     *         reference.
     * @see #setFilterCondition__AssemblyEventConnector(PCMRandomVariable)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getAssemblyEventConnector_FilterCondition__AssemblyEventConnector()
     * @see org.palladiosimulator.pcm.core.PCMRandomVariable#getAssemblyEventConnector__FilterCondition
     * @model opposite="assemblyEventConnector__FilterCondition" containment="true" ordered="false"
     * @generated
     */
    PCMRandomVariable getFilterCondition__AssemblyEventConnector();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getFilterCondition__AssemblyEventConnector
     * <em>Filter Condition Assembly Event Connector</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Filter Condition Assembly Event Connector</em>'
     *            containment reference.
     * @see #getFilterCondition__AssemblyEventConnector()
     * @generated
     */
    void setFilterCondition__AssemblyEventConnector(PCMRandomVariable value);

} // AssemblyEventConnector
