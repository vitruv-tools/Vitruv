/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.palladiosimulator.pcm.repository.PassiveResource;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Acquire Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * <p>
 * In&nbsp;an&nbsp;RDSEFF,&nbsp;component&nbsp;developers&nbsp;can&nbsp;specify&nbsp;an&nbsp;
 * AcquireAction,&nbsp;which&nbsp;references&nbsp;a&nbsp;passive&nbsp;resource&nbsp;types.&nbsp;Once
 * &nbsp;analysis&nbsp;tools&nbsp;execute&nbsp;this&nbsp;action,&nbsp;they&nbsp;decrease&nbsp;the&
 * nbsp;amount&nbsp;of&nbsp;items&nbsp;available&nbsp;from&nbsp;the&nbsp;referenced&nbsp;passive&
 * nbsp;resource&nbsp;type&nbsp;by&nbsp;one,&nbsp;if&nbsp;at&nbsp;least&nbsp;one&nbsp;item&nbsp;is&
 * nbsp;available.&nbsp;If&nbsp;none&nbsp;item&nbsp;is&nbsp;available,&nbsp;because&nbsp;other,&nbsp
 * ;concurrently&nbsp;executed&nbsp;requests&nbsp;have&nbsp;acquired&nbsp;all&nbsp;of&nbsp;them,&
 * nbsp;analysis&nbsp;tools&nbsp;enqueue&nbsp;the&nbsp;current&nbsp;request&nbsp;(first-come-first-
 * serve&nbsp;scheduling&nbsp;policy)&nbsp;and&nbsp;block&nbsp;it's&nbsp;further&nbsp;execution.
 * </p>
 * <p>
 * Acquisition&nbsp;and&nbsp;release&nbsp;of&nbsp;passive&nbsp;resources&nbsp;happen&nbsp;
 * instantaneously&nbsp;and&nbsp;do&nbsp;not&nbsp;consume&nbsp;any&nbsp;time&nbsp;except&nbsp;for&
 * nbsp;waiting&nbsp;delays&nbsp;before&nbsp;actual&nbsp;acquisition.&nbsp;Resource&nbsp;locking&
 * nbsp;may&nbsp;introduce&nbsp;deadlocks&nbsp;when&nbsp;simulating&nbsp;the&nbsp;model,&nbsp;
 * however,&nbsp;for&nbsp;performance&nbsp;analysis&nbsp;with&nbsp;the&nbsp;PCM&nbsp;it&nbsp;is&nbsp
 * ;assumed&nbsp;that&nbsp;no&nbsp;deadlocks&nbsp;occur.&nbsp;Otherwise,&nbsp;the&nbsp;model&nbsp;
 * first&nbsp;needs&nbsp;to&nbsp;be&nbsp;fixed&nbsp;accordingly&nbsp;before&nbsp;carrying&nbsp;out&
 * nbsp;the&nbsp;performance&nbsp;prediction.
 * </p>
 * <p>
 * A reliability-related extension is the timeout mechanism that may be associated to an
 * AcquireAction through the "timeout" and "timeoutValue" attributes. See the documentation of the
 * "timeout" attribute for further information.
 * </p>
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.seff.AcquireAction#getPassiveresource_AcquireAction
 * <em>Passiveresource Acquire Action</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.seff.AcquireAction#isTimeout <em>Timeout</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.seff.AcquireAction#getTimeoutValue <em>Timeout Value</em>}
 * </li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.SeffPackage#getAcquireAction()
 * @model
 * @generated
 */
public interface AcquireAction extends AbstractInternalControlFlowAction {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Passiveresource Acquire Action</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Passiveresource Acquire Action</em>' reference isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Passiveresource Acquire Action</em>' reference.
     * @see #setPassiveresource_AcquireAction(PassiveResource)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getAcquireAction_Passiveresource_AcquireAction()
     * @model required="true"
     * @generated
     */
    PassiveResource getPassiveresource_AcquireAction();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.AcquireAction#getPassiveresource_AcquireAction
     * <em>Passiveresource Acquire Action</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Passiveresource Acquire Action</em>' reference.
     * @see #getPassiveresource_AcquireAction()
     * @generated
     */
    void setPassiveresource_AcquireAction(PassiveResource value);

    /**
     * Returns the value of the '<em><b>Timeout</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc --> <!-- begin-model-doc -->
     * <p>
     * Indicates&nbsp;if&nbsp;the&nbsp;AcquireAction&nbsp;has&nbsp;a&nbsp;timeout.&nbsp;If&nbsp;yes,
     * a simulation through SimuCom with the "simulate failures" option switched on will consider
     * the timeout as follows:
     * Any&nbsp;acquiring&nbsp;thread&nbsp;waits&nbsp;no&nbsp;longer&nbsp;than&nbsp;the&nbsp;
     * duration&nbsp;specified&nbsp;in
     * "timeoutValue".&nbsp;If&nbsp;the&nbsp;maximum&nbsp;waiting&nbsp;time&nbsp;is&nbsp;reached,&
     * nbsp;the&nbsp;thread&nbsp;is&nbsp;removed&nbsp;from&nbsp;the&nbsp;waiting&nbsp;queue&nbsp;and
     * &nbsp;cancelled&nbsp;by&nbsp;a timeout&nbsp;failure.
     * </p>
     * <p>
     * The reliability Markov analysis does not consider AcquireAction timeouts.
     * </p>
     * <p>
     * As&nbsp;any&nbsp;failure-on-demand&nbsp;occurrence,&nbsp;timeout&nbsp;failures&nbsp;can&nbsp;
     * be&nbsp;handled&nbsp;by&nbsp;"RecoveryBlockActions"&nbsp;at&nbsp;any&nbsp;higher&nbsp;level
     * in&nbsp;the&nbsp;caller&nbsp;hierarchy. To achieve this, the modeller must specify a
     * "ResourceTimeoutFailureType" pointing to the "PassiveResource" that the AcquireAction is
     * related to. The specified "ResourceTimeoutFailureType" can then be added to the list of
     * handled failure types of any "RecoveryBlockAlternativeBehaviour".
     * </p>
     * <p>
     * Please notice the following limitation of the current implementation of timeout handling:
     * Timeouts are only considered by SimuCom if the executing software component is allocated to a
     * resource container with an operating system set to "ABSTRACT" (i.e. no specific OS schedulers
     * are supported). The timeout handling implementation can be found in
     * de.uka.ipd.sdq.simucomframework.resources.SimSimpleFairPassiveResource.
     * </p>
     * <!-- end-model-doc -->
     *
     * @return the value of the '<em>Timeout</em>' attribute.
     * @see #setTimeout(boolean)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getAcquireAction_Timeout()
     * @model required="true"
     * @generated
     */
    boolean isTimeout();

    /**
     * Sets the value of the '{@link org.palladiosimulator.pcm.seff.AcquireAction#isTimeout
     * <em>Timeout</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Timeout</em>' attribute.
     * @see #isTimeout()
     * @generated
     */
    void setTimeout(boolean value);

    /**
     * Returns the value of the '<em><b>Timeout Value</b></em>' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc --> <!-- begin-model-doc -->
     * <p>
     * Specifies a timeout value, in case the AcquireAction has a timeout (see the documentation of
     * the "timeout" attribute for further information).
     * </p>
     * <!-- end-model-doc -->
     *
     * @return the value of the '<em>Timeout Value</em>' attribute.
     * @see #setTimeoutValue(double)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getAcquireAction_TimeoutValue()
     * @model required="true"
     * @generated
     */
    double getTimeoutValue();

    /**
     * Sets the value of the '{@link org.palladiosimulator.pcm.seff.AcquireAction#getTimeoutValue
     * <em>Timeout Value</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Timeout Value</em>' attribute.
     * @see #getTimeoutValue()
     * @generated
     */
    void setTimeoutValue(double value);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.timeoutValue.oclAsType(Real) >= 0.0'"
     * @generated
     */
    boolean TimeoutValueOfAcquireActionMustNotBeNegative(DiagnosticChain diagnostics, Map<Object, Object> context);

} // AcquireAction
