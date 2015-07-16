/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.seff_performance;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.AbstractInternalControlFlowAction;
import org.palladiosimulator.pcm.seff.CallAction;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Infrastructure Call</b></em>
 * '. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall#getSignature__InfrastructureCall
 * <em>Signature Infrastructure Call</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall#getNumberOfCalls__InfrastructureCall
 * <em>Number Of Calls Infrastructure Call</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall#getAction__InfrastructureCall
 * <em>Action Infrastructure Call</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall#getRequiredRole__InfrastructureCall
 * <em>Required Role Infrastructure Call</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.seff_performance.SeffPerformancePackage#getInfrastructureCall()
 * @model
 * @generated
 */
public interface InfrastructureCall extends CallAction {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Signature Infrastructure Call</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Signature Infrastructure Call</em>' reference isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Signature Infrastructure Call</em>' reference.
     * @see #setSignature__InfrastructureCall(InfrastructureSignature)
     * @see org.palladiosimulator.pcm.seff.seff_performance.SeffPerformancePackage#getInfrastructureCall_Signature__InfrastructureCall()
     * @model required="true"
     * @generated
     */
    InfrastructureSignature getSignature__InfrastructureCall();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall#getSignature__InfrastructureCall
     * <em>Signature Infrastructure Call</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @param value
     *            the new value of the '<em>Signature Infrastructure Call</em>' reference.
     * @see #getSignature__InfrastructureCall()
     * @generated
     */
    void setSignature__InfrastructureCall(InfrastructureSignature value);

    /**
     * Returns the value of the '<em><b>Number Of Calls Infrastructure Call</b></em>' containment
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.core.PCMRandomVariable#getInfrastructureCall__PCMRandomVariable
     * <em>Infrastructure Call PCM Random Variable</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Number Of Calls Infrastructure Call</em>' containment reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Number Of Calls Infrastructure Call</em>' containment
     *         reference.
     * @see #setNumberOfCalls__InfrastructureCall(PCMRandomVariable)
     * @see org.palladiosimulator.pcm.seff.seff_performance.SeffPerformancePackage#getInfrastructureCall_NumberOfCalls__InfrastructureCall()
     * @see org.palladiosimulator.pcm.core.PCMRandomVariable#getInfrastructureCall__PCMRandomVariable
     * @model opposite="infrastructureCall__PCMRandomVariable" containment="true" required="true"
     *        ordered="false"
     * @generated
     */
    PCMRandomVariable getNumberOfCalls__InfrastructureCall();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall#getNumberOfCalls__InfrastructureCall
     * <em>Number Of Calls Infrastructure Call</em>}' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Number Of Calls Infrastructure Call</em>' containment
     *            reference.
     * @see #getNumberOfCalls__InfrastructureCall()
     * @generated
     */
    void setNumberOfCalls__InfrastructureCall(PCMRandomVariable value);

    /**
     * Returns the value of the '<em><b>Action Infrastructure Call</b></em>' container reference. It
     * is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.seff.AbstractInternalControlFlowAction#getInfrastructureCall__Action
     * <em>Infrastructure Call Action</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Action Infrastructure Call</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Action Infrastructure Call</em>' container reference.
     * @see #setAction__InfrastructureCall(AbstractInternalControlFlowAction)
     * @see org.palladiosimulator.pcm.seff.seff_performance.SeffPerformancePackage#getInfrastructureCall_Action__InfrastructureCall()
     * @see org.palladiosimulator.pcm.seff.AbstractInternalControlFlowAction#getInfrastructureCall__Action
     * @model opposite="infrastructureCall__Action" required="true" transient="false"
     *        ordered="false"
     * @generated
     */
    AbstractInternalControlFlowAction getAction__InfrastructureCall();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall#getAction__InfrastructureCall
     * <em>Action Infrastructure Call</em>}' container reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Action Infrastructure Call</em>' container reference.
     * @see #getAction__InfrastructureCall()
     * @generated
     */
    void setAction__InfrastructureCall(AbstractInternalControlFlowAction value);

    /**
     * Returns the value of the '<em><b>Required Role Infrastructure Call</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Required Role Infrastructure Call</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Required Role Infrastructure Call</em>' reference.
     * @see #setRequiredRole__InfrastructureCall(InfrastructureRequiredRole)
     * @see org.palladiosimulator.pcm.seff.seff_performance.SeffPerformancePackage#getInfrastructureCall_RequiredRole__InfrastructureCall()
     * @model required="true"
     * @generated
     */
    InfrastructureRequiredRole getRequiredRole__InfrastructureCall();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall#getRequiredRole__InfrastructureCall
     * <em>Required Role Infrastructure Call</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Required Role Infrastructure Call</em>' reference.
     * @see #getRequiredRole__InfrastructureCall()
     * @generated
     */
    void setRequiredRole__InfrastructureCall(InfrastructureRequiredRole value);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='signature__InfrastructureCall.infrastructureInterface__InfrastructureSignature = requiredRole__InfrastructureCall.requiredInterface__InfrastructureRequiredRole'"
     * @generated
     */
    boolean SignatureMustBelongToUsedRequiredRole(DiagnosticChain diagnostics, Map<Object, Object> context);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.oclAsType(ecore::EObject)->closure(eContainer())->select( entity | entity.oclIsKindOf(pcm::core::entity::InterfaceRequiringEntity)).oclAsType(pcm::core::entity::InterfaceRequiringEntity).requiredRoles_InterfaceRequiringEntity->includes(self.requiredRole__InfrastructureCall)'"
     * @generated
     */
    boolean ReferencedRequiredRoleMustBeRequiredByComponent(DiagnosticChain diagnostics, Map<Object, Object> context);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.action__InfrastructureCall.infrastructureCall__Action->select(call | call.requiredRole__InfrastructureCall = self.requiredRole__InfrastructureCall and call.signature__InfrastructureCall=self.signature__InfrastructureCall)->size() = 1'"
     * @generated
     */
    boolean SignatureRoleCombinationMustBeUniqueWithinAbstractInternalControlFlowAction(DiagnosticChain diagnostics,
            Map<Object, Object> context);

} // InfrastructureCall
