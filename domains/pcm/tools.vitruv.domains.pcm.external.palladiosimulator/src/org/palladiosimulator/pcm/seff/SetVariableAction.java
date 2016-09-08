/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.parameter.VariableUsage;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Set Variable Action</b></em>
 * '. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> Set Variable Action Assigns a variable characterisation to an OUT
 * parameter, INOUT parameter, or return value of the service. It ensures that performance-relevant
 * output parameter characterisations of a component service are specified to use them to
 * parameterise the calling RDSEFF. A SetVariableAction must only use output parameters on the left
 * hand side of the assignment and must not use input parameter or local variable names, because
 * input parameters cannot be returned and local names should not be exposed to adhere the black box
 * principle. The action is only intended to allow proper data flow modelling (i.e., output
 * parameter passing) between different component services, but not to reveal additional internals
 * of the service the current RDSEFF models. Thus, the assigned characterisation is not accessible
 * in subsequent actions of the current RDSEFF. Notice, that the stochastic expression used in this
 * assignment must characterise the result of the whole computation of the current service. For
 * non-trivial components, this requires a substantial stochastic approximation based on manual
 * abstraction. However, recall that not the actual result of a component service needs to be
 * specified, but only its performance-relevant attributes. For example, to model the return value
 * of a component service compressing a file, using its file size divided by the compression factor
 * as the stochastic expression is usually sufficient, while the value of the compressed file is not
 * of interest in a performance model. Multiple SetVariableActions assigning to the same output
 * parameter might occur at different locations of the control flow in an RDSEFF. In the case of
 * sequences, loops, and fork, the last assignment overwrites the former assignments and gets
 * transferred back to the calling RDSEFF. Therefore, analysis tools may ignore the former
 * assignments. In the case of using a SetVariableAction in two different branches of a
 * BranchAction, only the assignment in the chosen branch is valid and gets transferred back to the
 * caller. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.SetVariableAction#getLocalVariableUsages_SetVariableAction
 * <em>Local Variable Usages Set Variable Action</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.SeffPackage#getSetVariableAction()
 * @model
 * @generated
 */
public interface SetVariableAction extends AbstractInternalControlFlowAction {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Local Variable Usages Set Variable Action</b></em>'
     * containment reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage}. It is bidirectional and its
     * opposite is '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getSetVariableAction_VariableUsage
     * <em>Set Variable Action Variable Usage</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Local Variable Usages Set Variable Action</em>' containment
     * reference list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Local Variable Usages Set Variable Action</em>' containment
     *         reference list.
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getSetVariableAction_LocalVariableUsages_SetVariableAction()
     * @see org.palladiosimulator.pcm.parameter.VariableUsage#getSetVariableAction_VariableUsage
     * @model opposite="setVariableAction_VariableUsage" containment="true" ordered="false"
     * @generated
     */
    EList<VariableUsage> getLocalVariableUsages_SetVariableAction();

} // SetVariableAction
