/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.seff_performance;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.AbstractInternalControlFlowAction;
import org.palladiosimulator.pcm.seff.CallAction;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Resource Call</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.seff.seff_performance.ResourceCall#getAction__ResourceCall
 * <em>Action Resource Call</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.seff_performance.ResourceCall#getResourceRequiredRole__ResourceCall
 * <em>Resource Required Role Resource Call</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.seff_performance.ResourceCall#getSignature__ResourceCall
 * <em>Signature Resource Call</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.seff_performance.ResourceCall#getNumberOfCalls__ResourceCall
 * <em>Number Of Calls Resource Call</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.seff_performance.SeffPerformancePackage#getResourceCall()
 * @model
 * @generated
 */
public interface ResourceCall extends CallAction {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Action Resource Call</b></em>' container reference. It is
     * bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.seff.AbstractInternalControlFlowAction#getResourceCall__Action
     * <em>Resource Call Action</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Action Resource Call</em>' container reference isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Action Resource Call</em>' container reference.
     * @see #setAction__ResourceCall(AbstractInternalControlFlowAction)
     * @see org.palladiosimulator.pcm.seff.seff_performance.SeffPerformancePackage#getResourceCall_Action__ResourceCall()
     * @see org.palladiosimulator.pcm.seff.AbstractInternalControlFlowAction#getResourceCall__Action
     * @model opposite="resourceCall__Action" required="true" transient="false" ordered="false"
     * @generated
     */
    AbstractInternalControlFlowAction getAction__ResourceCall();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.seff_performance.ResourceCall#getAction__ResourceCall
     * <em>Action Resource Call</em>}' container reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Action Resource Call</em>' container reference.
     * @see #getAction__ResourceCall()
     * @generated
     */
    void setAction__ResourceCall(AbstractInternalControlFlowAction value);

    /**
     * Returns the value of the '<em><b>Resource Required Role Resource Call</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Required Role Resource Call</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Resource Required Role Resource Call</em>' reference.
     * @see #setResourceRequiredRole__ResourceCall(ResourceRequiredRole)
     * @see org.palladiosimulator.pcm.seff.seff_performance.SeffPerformancePackage#getResourceCall_ResourceRequiredRole__ResourceCall()
     * @model required="true" ordered="false"
     * @generated
     */
    ResourceRequiredRole getResourceRequiredRole__ResourceCall();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.seff_performance.ResourceCall#getResourceRequiredRole__ResourceCall
     * <em>Resource Required Role Resource Call</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Resource Required Role Resource Call</em>' reference.
     * @see #getResourceRequiredRole__ResourceCall()
     * @generated
     */
    void setResourceRequiredRole__ResourceCall(ResourceRequiredRole value);

    /**
     * Returns the value of the '<em><b>Signature Resource Call</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Signature Resource Call</em>' reference isn't clear, there really
     * should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Signature Resource Call</em>' reference.
     * @see #setSignature__ResourceCall(ResourceSignature)
     * @see org.palladiosimulator.pcm.seff.seff_performance.SeffPerformancePackage#getResourceCall_Signature__ResourceCall()
     * @model required="true"
     * @generated
     */
    ResourceSignature getSignature__ResourceCall();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.seff_performance.ResourceCall#getSignature__ResourceCall
     * <em>Signature Resource Call</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Signature Resource Call</em>' reference.
     * @see #getSignature__ResourceCall()
     * @generated
     */
    void setSignature__ResourceCall(ResourceSignature value);

    /**
     * Returns the value of the '<em><b>Number Of Calls Resource Call</b></em>' containment
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.core.PCMRandomVariable#getResourceCall__PCMRandomVariable
     * <em>Resource Call PCM Random Variable</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Number Of Calls Resource Call</em>' containment reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Number Of Calls Resource Call</em>' containment reference.
     * @see #setNumberOfCalls__ResourceCall(PCMRandomVariable)
     * @see org.palladiosimulator.pcm.seff.seff_performance.SeffPerformancePackage#getResourceCall_NumberOfCalls__ResourceCall()
     * @see org.palladiosimulator.pcm.core.PCMRandomVariable#getResourceCall__PCMRandomVariable
     * @model opposite="resourceCall__PCMRandomVariable" containment="true" required="true"
     *        ordered="false"
     * @generated
     */
    PCMRandomVariable getNumberOfCalls__ResourceCall();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.seff_performance.ResourceCall#getNumberOfCalls__ResourceCall
     * <em>Number Of Calls Resource Call</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Number Of Calls Resource Call</em>' containment
     *            reference.
     * @see #getNumberOfCalls__ResourceCall()
     * @generated
     */
    void setNumberOfCalls__ResourceCall(PCMRandomVariable value);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.resourceRequiredRole__ResourceCall.requiredResourceInterface__ResourceRequiredRole.resourceSignatures__ResourceInterface->includes(self.signature__ResourceCall)'"
     * @generated
     */
    boolean ResourceSignatureBelongsToResourceRequiredRole(DiagnosticChain diagnostics, Map<Object, Object> context);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.oclAsType(ecore::EObject)->closure(eContainer())->select( entity | entity.oclIsKindOf(pcm::core::entity::ResourceInterfaceRequiringEntity)).oclAsType(pcm::core::entity::ResourceInterfaceRequiringEntity).resourceRequiredRoles__ResourceInterfaceRequiringEntity->includes(self.resourceRequiredRole__ResourceCall)'"
     * @generated
     */
    boolean ResourceRequiredRoleMustBeReferencedByComponent(DiagnosticChain diagnostics, Map<Object, Object> context);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.action__ResourceCall.resourceCall__Action->select(call | call.resourceRequiredRole__ResourceCall=self.resourceRequiredRole__ResourceCall and call.signature__ResourceCall=self.signature__ResourceCall)->size() = 1'"
     * @generated
     */
    boolean SignatureRoleCombinationMustBeUniqueWithinAbstractInternalControlFlowAction(DiagnosticChain diagnostics,
            Map<Object, Object> context);

} // ResourceCall
