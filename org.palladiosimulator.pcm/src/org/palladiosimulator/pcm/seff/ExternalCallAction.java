/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.seff.seff_reliability.FailureHandlingEntity;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>External Call Action</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * ExternalCallAction&nbsp;models&nbsp;the&nbsp;invocation&nbsp;of&nbsp;a&nbsp;service&nbsp;
 * specified&nbsp;in&nbsp;a&nbsp;required&nbsp;interface.&nbsp;Therefore,&nbsp;it&nbsp;references&
 * nbsp;a&nbsp;Role,&nbsp;from&nbsp;which&nbsp;the&nbsp;providing&nbsp;component&nbsp;can&nbsp;be&
 * nbsp;derived,&nbsp;and&nbsp;a&nbsp;Signature&nbsp;to&nbsp;specify&nbsp;the&nbsp;called&nbsp;
 * service.&nbsp;ExternalCallActions&nbsp;model&nbsp;synchronous&nbsp;calls&nbsp;to&nbsp;required&
 * nbsp;services,&nbsp;i.e.,&nbsp;the&nbsp;caller&nbsp;waits&nbsp;until&nbsp;the&nbsp;called&nbsp;
 * service&nbsp;finishes&nbsp;execution&nbsp;before&nbsp;continuing&nbsp;execution&nbsp;itself.&nbsp
 * ;The&nbsp;PCM&nbsp;allows&nbsp;modelling&nbsp;asynchronous&nbsp;calls&nbsp;to&nbsp;required&nbsp;
 * services&nbsp;by&nbsp;using&nbsp;an&nbsp;ExternalCallAction&nbsp;inside&nbsp;a&nbsp;
 * ForkedBehaviour.<br />
 * ExternalCallActions&nbsp;do&nbsp;not&nbsp;have&nbsp;resource&nbsp;demands&nbsp;by&nbsp;themselves
 * .&nbsp;Component&nbsp;developers&nbsp;need&nbsp;to&nbsp;specify&nbsp;the&nbsp;resource&nbsp;
 * demand&nbsp;of&nbsp;the&nbsp;called&nbsp;service&nbsp;in&nbsp;the&nbsp;RDSEFF&nbsp;of&nbsp;that&
 * nbsp;service.&nbsp;The&nbsp;resource&nbsp;demand&nbsp;can&nbsp;also&nbsp;be&nbsp;calculated&nbsp;
 * by&nbsp;analysing&nbsp;the&nbsp;providing&nbsp;component.&nbsp;This&nbsp;keeps&nbsp;the&nbsp;
 * RDSEFF&nbsp;specification&nbsp;of&nbsp;different&nbsp;component&nbsp;developers&nbsp;independent&
 * nbsp;from&nbsp;each&nbsp;other&nbsp;and&nbsp;makes&nbsp;them&nbsp;replaceable&nbsp;in&nbsp;an&
 * nbsp;architectural&nbsp;model.<br />
 * ExternalCallActions&nbsp;may&nbsp;contain&nbsp;two&nbsp;sets&nbsp;of&nbsp;VariableUsages&nbsp;
 * specifying&nbsp;input&nbsp;parameter&nbsp;characterisations&nbsp;and&nbsp;output&nbsp;parameter&
 * nbsp;characterisations&nbsp;respectively.&nbsp;VariableUsages&nbsp;for&nbsp;input&nbsp;parameters
 * &nbsp;may&nbsp;only&nbsp;reference&nbsp;IN&nbsp;or&nbsp;INOUT&nbsp;parameters&nbsp;of&nbsp;the&
 * nbsp;call's&nbsp;referenced&nbsp;signature.&nbsp;The&nbsp;random&nbsp;variable&nbsp;
 * characterisation&nbsp;inside&nbsp;such&nbsp;a&nbsp;VariableUsage&nbsp;may&nbsp;be&nbsp;constants,
 * &nbsp;probability&nbsp;distribution&nbsp;functions,&nbsp;or&nbsp;include&nbsp;a&nbsp;stochastic&
 * nbsp;expression&nbsp;involving&nbsp;for&nbsp;example&nbsp;arithmetic&nbsp;operations.&nbsp;The&
 * nbsp;latter&nbsp;models&nbsp;a&nbsp;dependency&nbsp;between&nbsp;the&nbsp;current&nbsp;service's&
 * nbsp;own&nbsp;input&nbsp;parameters&nbsp;and&nbsp;the&nbsp;input&nbsp;parameters&nbsp;of&nbsp;the
 * &nbsp;required&nbsp;service. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.seff.ExternalCallAction#getCalledService_ExternalService
 * <em>Called Service External Service</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.seff.ExternalCallAction#getRole_ExternalService
 * <em>Role External Service</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.seff.ExternalCallAction#getRetryCount <em>Retry Count</em>}
 * </li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.SeffPackage#getExternalCallAction()
 * @model
 * @generated
 */
public interface ExternalCallAction extends AbstractAction, CallReturnAction, FailureHandlingEntity {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Called Service External Service</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Called Service External Service</em>' reference isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Called Service External Service</em>' reference.
     * @see #setCalledService_ExternalService(OperationSignature)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getExternalCallAction_CalledService_ExternalService()
     * @model required="true"
     * @generated
     */
    OperationSignature getCalledService_ExternalService();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.ExternalCallAction#getCalledService_ExternalService
     * <em>Called Service External Service</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Called Service External Service</em>' reference.
     * @see #getCalledService_ExternalService()
     * @generated
     */
    void setCalledService_ExternalService(OperationSignature value);

    /**
     * Returns the value of the '<em><b>Role External Service</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Role External Service</em>' reference isn't clear, there really
     * should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Role External Service</em>' reference.
     * @see #setRole_ExternalService(OperationRequiredRole)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getExternalCallAction_Role_ExternalService()
     * @model required="true"
     * @generated
     */
    OperationRequiredRole getRole_ExternalService();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.ExternalCallAction#getRole_ExternalService
     * <em>Role External Service</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Role External Service</em>' reference.
     * @see #getRole_ExternalService()
     * @generated
     */
    void setRole_ExternalService(OperationRequiredRole value);

    /**
     * Returns the value of the '<em><b>Retry Count</b></em>' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc --> <!-- begin-model-doc --> Specifies&nbsp;the&nbsp;number of&nbsp;retries
     * this ExternalCallAction shoul be re-executed&nbsp; in case of failure occurence. <!--
     * end-model-doc -->
     *
     * @return the value of the '<em>Retry Count</em>' attribute.
     * @see #setRetryCount(int)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getExternalCallAction_RetryCount()
     * @model required="true"
     * @generated
     */
    int getRetryCount();

    /**
     * Sets the value of the '{@link org.palladiosimulator.pcm.seff.ExternalCallAction#getRetryCount
     * <em>Retry Count</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Retry Count</em>' attribute.
     * @see #getRetryCount()
     * @generated
     */
    void setRetryCount(int value);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * <pre id="comment_text_0" class="bz_comment_text">
     * check if the signature
     * (declared in calledService_ExternalService attribute) belongs to the role
     * (declared in role_ExternalService attribute)
     * </pre>
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='self.role_ExternalService.requiredInterface__OperationRequiredRole.signatures__OperationInterface->includes(self.calledService_ExternalService)'"
     * @generated
     */
    boolean SignatureBelongsToRole(DiagnosticChain diagnostics, Map<Object, Object> context);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.oclAsType(ecore::EObject)->closure(eContainer())->select( entity | entity.oclIsKindOf(pcm::core::entity::InterfaceRequiringEntity)).oclAsType(pcm::core::entity::InterfaceRequiringEntity).requiredRoles_InterfaceRequiringEntity->includes(self.role_ExternalService)'"
     * @generated
     */
    boolean OperationRequiredRoleMustBeReferencedByContainer(DiagnosticChain diagnostics, Map<Object, Object> context);

} // ExternalCallAction
