/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.allocation;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Allocation</b></em>'. <!--
 * end-user-doc -->
 *
 * <!-- begin-model-doc --> The allocation repository holding all available allocation contexts of a
 * model. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.allocation.Allocation#getTargetResourceEnvironment_Allocation
 * <em>Target Resource Environment Allocation</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.allocation.Allocation#getSystem_Allocation
 * <em>System Allocation</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.allocation.Allocation#getAllocationContexts_Allocation
 * <em>Allocation Contexts Allocation</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.allocation.AllocationPackage#getAllocation()
 * @model
 * @generated
 */
public interface Allocation extends Entity {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Target Resource Environment Allocation</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Target Resource Environment Allocation</em>' reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Target Resource Environment Allocation</em>' reference.
     * @see #setTargetResourceEnvironment_Allocation(ResourceEnvironment)
     * @see org.palladiosimulator.pcm.allocation.AllocationPackage#getAllocation_TargetResourceEnvironment_Allocation()
     * @model ordered="false"
     * @generated
     */
    ResourceEnvironment getTargetResourceEnvironment_Allocation();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.allocation.Allocation#getTargetResourceEnvironment_Allocation
     * <em>Target Resource Environment Allocation</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Target Resource Environment Allocation</em>' reference.
     * @see #getTargetResourceEnvironment_Allocation()
     * @generated
     */
    void setTargetResourceEnvironment_Allocation(ResourceEnvironment value);

    /**
     * Returns the value of the '<em><b>System Allocation</b></em>' reference. <!-- begin-user-doc
     * -->
     * <p>
     * If the meaning of the '<em>System Allocation</em>' reference isn't clear, there really should
     * be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>System Allocation</em>' reference.
     * @see #setSystem_Allocation(org.palladiosimulator.pcm.system.System)
     * @see org.palladiosimulator.pcm.allocation.AllocationPackage#getAllocation_System_Allocation()
     * @model required="true" ordered="false"
     * @generated
     */
    org.palladiosimulator.pcm.system.System getSystem_Allocation();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.allocation.Allocation#getSystem_Allocation
     * <em>System Allocation</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>System Allocation</em>' reference.
     * @see #getSystem_Allocation()
     * @generated
     */
    void setSystem_Allocation(org.palladiosimulator.pcm.system.System value);

    /**
     * Returns the value of the '<em><b>Allocation Contexts Allocation</b></em>' containment
     * reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.allocation.AllocationContext}. It is bidirectional and its
     * opposite is '
     * {@link org.palladiosimulator.pcm.allocation.AllocationContext#getAllocation_AllocationContext
     * <em>Allocation Allocation Context</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Allocation Contexts Allocation</em>' containment reference list
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Allocation Contexts Allocation</em>' containment reference
     *         list.
     * @see org.palladiosimulator.pcm.allocation.AllocationPackage#getAllocation_AllocationContexts_Allocation()
     * @see org.palladiosimulator.pcm.allocation.AllocationContext#getAllocation_AllocationContext
     * @model opposite="allocation_AllocationContext" containment="true" ordered="false"
     * @generated
     */
    EList<AllocationContext> getAllocationContexts_Allocation();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> Each Assembly of
     * BasicComponents and CompositeComponents used in the referenced System must be allocated.
     *
     * Things are complicated by the introduction of SubSystems. Here, the Assembly of the SubSystem
     * itself does not have to be allocated. If it is not allocated, all BasicComponents and
     * CompositeComponents contained in this SubSystem (also transitively over several nested and
     * not-allocated SubSystems) need to be allocated.
     *
     * The constraint is realised wth a closure over the AssemblyContext contained in a
     * ComposedStructure.
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='-- Get all AssemblyContexts used by this system, that is \n-- 1) the AssemblyContexts directly used in the system and \nself.system_Allocation.assemblyContexts__ComposedStructure\n-- 2) the AssemblyContexts used by SubSystems in the System. Note that if a SubSystem also contains other Subsystems,\n-- we need to get those AssemblyContexts too: Thus, we use a closure here\n->union(self.system_Allocation.assemblyContexts__ComposedStructure->closure(\nencapsulatedComponent__AssemblyContext->select(composites|composites.oclIsTypeOf(pcm::subsystem::SubSystem)).oclAsType(pcm::subsystem::SubSystem)\n.assemblyContexts__ComposedStructure))\n--Now, after we collected all AssemblyContexts somehow used, we check whether they need to be allocated \n--and if yes, if they are allocated.\n->forAll(assemblyCtx|\n--AssemblyContexts that contain SubSystems do not need to be allocated\nassemblyCtx.encapsulatedComponent__AssemblyContext.oclIsTypeOf(pcm::subsystem::SubSystem) or\n--All others need to be allocated. \nself.allocationContexts_Allocation->select(allocationCtx|\nallocationCtx.assemblyContext_AllocationContext = assemblyCtx)->size() = 1)'"
     * @generated
     */
    boolean EachAssemblyContextWithinSystemHasToBeAllocatedExactlyOnce(DiagnosticChain diagnostics,
            Map<Object, Object> context);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.allocationContexts_Allocation->forAll(a | self.allocationContexts_Allocation->forAll(b | \r\n    --- if a and b are not on the same server\r\n    (a.resourceContainer_AllocationContext <> b.resourceContainer_AllocationContext \r\n    and\r\n    --  and if the assembly contexts of a and b are connected\r\n      self.system_Allocation.connectors__ComposedStructure->select(conn | conn.oclIsTypeOf(pcm::core::composition::AssemblyConnector)).oclAsType(pcm::core::composition::AssemblyConnector)->exists(conn | \r\n         (conn.providingAssemblyContext_AssemblyConnector = a.assemblyContext_AllocationContext  \r\n         and \r\n         conn.requiringAssemblyContext_AssemblyConnector = b.assemblyContext_AllocationContext )\r\n         or \r\n          (conn.providingAssemblyContext_AssemblyConnector = b.assemblyContext_AllocationContext  \r\n         and \r\n         conn.requiringAssemblyContext_AssemblyConnector = a.assemblyContext_AllocationContext )\r\n       )\r\n     )\r\n     -- then the servers have to be connected by a linking resource\r\n     implies \r\n     self.targetResourceEnvironment_Allocation.linkingResources__ResourceEnvironment->exists(l | \r\n        -- l connects the two\r\n        l.connectedResourceContainers_LinkingResource->includes(a.resourceContainer_AllocationContext)\r\n        and \r\n        l.connectedResourceContainers_LinkingResource->includes(b.resourceContainer_AllocationContext)\r\n     )\r\n  ))'"
     * @generated
     */
    boolean CommunicatingServersHaveToBeConnectedByLinkingResource(DiagnosticChain diagnostics,
            Map<Object, Object> context);

} // Allocation
