/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.usagemodel;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Stop</b></em>'. <!--
 * end-user-doc -->
 *
 * <!-- begin-model-doc --> Each ScenarioBehaviour has exactly one Stop action which marks the
 * action where the control flows ends. Stop actions have no successor. <!-- end-model-doc -->
 *
 *
 * @see org.palladiosimulator.pcm.usagemodel.UsagemodelPackage#getStop()
 * @model
 * @generated
 */
public interface Stop extends AbstractUserAction {

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
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.successor.oclIsUndefined()'"
     * @generated
     */
    boolean StopHasNoSuccessor(DiagnosticChain diagnostics, Map<Object, Object> context);

} // Stop
