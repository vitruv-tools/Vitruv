/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Start Action</b></em>'. <!--
 * end-user-doc -->
 *
 * <!-- begin-model-doc --> StartActions initiate a scenario behaviour and contain only a successor.
 * <!-- end-model-doc -->
 *
 *
 * @see org.palladiosimulator.pcm.seff.SeffPackage#getStartAction()
 * @model
 * @generated
 */
public interface StartAction extends AbstractInternalControlFlowAction {

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
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.predecessor_AbstractAction.oclIsUndefined()'"
     * @generated
     */
    boolean StartActionPredecessorMustNotBeDefined(DiagnosticChain diagnostics, Map<Object, Object> context);

} // StartAction
