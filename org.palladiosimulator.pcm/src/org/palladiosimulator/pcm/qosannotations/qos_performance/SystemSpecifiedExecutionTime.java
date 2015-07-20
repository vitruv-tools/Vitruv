/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.qosannotations.qos_performance;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>System Specified Execution Time</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> The SystemSpecifiedExecutionTime allows software architect and
 * performance analysts to specify the response time (distribution) of services called at the system
 * boundaries. This allows abstracting from the system externals and restricts the focus to the
 * software architecture under consideration.
 *
 * Note: - That's the starting point for Performance-Kennlinien I guess... <!-- end-model-doc -->
 *
 *
 * @see org.palladiosimulator.pcm.qosannotations.qos_performance.QosPerformancePackage#getSystemSpecifiedExecutionTime()
 * @model
 * @generated
 */
public interface SystemSpecifiedExecutionTime extends SpecifiedExecutionTime {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='(self.role_SpecifiedQoSAnnotation.oclIsTypeOf(pcm::repository::OperationRequiredRole)) and (self.role_SpecifiedQoSAnnotation.oclAsType(pcm::repository::OperationRequiredRole).requiringEntity_RequiredRole.oclIsTypeOf(pcm::system::System))\r\n'"
     * @generated
     */
    boolean SystemSpecifiedExecutionTimeMustReferenceRequiredRoleOfASystem(DiagnosticChain diagnostics,
            Map<Object, Object> context);

} // SystemSpecifiedExecutionTime
