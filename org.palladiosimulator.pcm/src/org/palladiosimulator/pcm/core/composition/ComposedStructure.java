/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Composed Structure</b></em>
 * '. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> TODO/FIXME: The distinction between ComposedStructure and
 * ComposedProvidingRequiringStructure does not make sense at the moment, because the
 * ComposedStructure already talks about inner provided / required delegation connectors, which only
 * make sense if there are outer roles for interfaces -> ComposedProvidingRequiringStructure. IDEA:
 * Move the delegation connector attributes to ComposedProvidingRequiringStructure. I'm not sure
 * about the assembly connectors. SEE ALSO: ComposedProvidingRequiringStructure However, as
 * AssemblyContexts of ComposedStructure always contain InterfaceProvidingRequiringEntities at the
 * moment, the above might not help... -- Anne <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.ComposedStructure#getAssemblyContexts__ComposedStructure
 * <em>Assembly Contexts Composed Structure</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.ComposedStructure#getResourceRequiredDelegationConnectors_ComposedStructure
 * <em>Resource Required Delegation Connectors Composed Structure</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.ComposedStructure#getEventChannel__ComposedStructure
 * <em>Event Channel Composed Structure</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.ComposedStructure#getConnectors__ComposedStructure
 * <em>Connectors Composed Structure</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getComposedStructure()
 * @model abstract="true"
 * @generated
 */
public interface ComposedStructure extends Entity {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Assembly Contexts Composed Structure</b></em>' containment
     * reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext}. It is bidirectional and
     * its opposite is '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext#getParentStructure__AssemblyContext
     * <em>Parent Structure Assembly Context</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Assembly Contexts Composed Structure</em>' containment reference
     * list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Assembly Contexts Composed Structure</em>' containment
     *         reference list.
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getComposedStructure_AssemblyContexts__ComposedStructure()
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext#getParentStructure__AssemblyContext
     * @model opposite="parentStructure__AssemblyContext" containment="true" ordered="false"
     * @generated
     */
    EList<AssemblyContext> getAssemblyContexts__ComposedStructure();

    /**
     * Returns the value of the '
     * <em><b>Resource Required Delegation Connectors Composed Structure</b></em>' containment
     * reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector}. It is
     * bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector#getParentStructure_ResourceRequiredDelegationConnector
     * <em>Parent Structure Resource Required Delegation Connector</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Required Delegation Connectors Composed Structure</em>'
     * containment reference list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Resource Required Delegation Connectors Composed Structure</em>
     *         ' containment reference list.
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getComposedStructure_ResourceRequiredDelegationConnectors_ComposedStructure()
     * @see org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector#getParentStructure_ResourceRequiredDelegationConnector
     * @model opposite="parentStructure_ResourceRequiredDelegationConnector" containment="true"
     *        ordered="false"
     * @generated
     */
    EList<ResourceRequiredDelegationConnector> getResourceRequiredDelegationConnectors_ComposedStructure();

    /**
     * Returns the value of the '<em><b>Event Channel Composed Structure</b></em>' containment
     * reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.core.composition.EventChannel}. It is bidirectional and its
     * opposite is '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannel#getParentStructure__EventChannel
     * <em>Parent Structure Event Channel</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Event Channel Composed Structure</em>' containment reference list
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Event Channel Composed Structure</em>' containment reference
     *         list.
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getComposedStructure_EventChannel__ComposedStructure()
     * @see org.palladiosimulator.pcm.core.composition.EventChannel#getParentStructure__EventChannel
     * @model opposite="parentStructure__EventChannel" containment="true" ordered="false"
     * @generated
     */
    EList<EventChannel> getEventChannel__ComposedStructure();

    /**
     * Returns the value of the '<em><b>Connectors Composed Structure</b></em>' containment
     * reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.core.composition.Connector}. It is bidirectional and its
     * opposite is '
     * {@link org.palladiosimulator.pcm.core.composition.Connector#getParentStructure__Connector
     * <em>Parent Structure Connector</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Connectors Composed Structure</em>' containment reference list
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Connectors Composed Structure</em>' containment reference list.
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getComposedStructure_Connectors__ComposedStructure()
     * @see org.palladiosimulator.pcm.core.composition.Connector#getParentStructure__Connector
     * @model opposite="parentStructure__Connector" containment="true" ordered="false"
     * @generated
     */
    EList<Connector> getConnectors__ComposedStructure();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.connectors__ComposedStructure->select(conn | conn.oclIsTypeOf(pcm::core::composition::ProvidedDelegationConnector)).oclAsType(pcm::core::composition::ProvidedDelegationConnector)->forAll( c1, c2 | c1 <> c2 implies c1.outerProvidedRole_ProvidedDelegationConnector <> c2.outerProvidedRole_ProvidedDelegationConnector)\r\n'"
     * @generated
     */
    boolean MultipleConnectorsConstraint(DiagnosticChain diagnostics, Map<Object, Object> context);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.connectors__ComposedStructure->select(conn | conn.oclIsTypeOf(pcm::core::composition::AssemblyConnector)).oclAsType(AssemblyConnector)->forAll( c1, c2 | ( (c1 <> c2) and ( c1.requiringAssemblyContext_AssemblyConnector = c2.requiringAssemblyContext_AssemblyConnector ) ) implies c1.requiredRole_AssemblyConnector <> c2.requiredRole_AssemblyConnector )\r\n'"
     * @generated
     */
    boolean MultipleConnectorsConstraintForAssemblyConnectors(DiagnosticChain diagnostics, Map<Object, Object> context);

} // ComposedStructure
