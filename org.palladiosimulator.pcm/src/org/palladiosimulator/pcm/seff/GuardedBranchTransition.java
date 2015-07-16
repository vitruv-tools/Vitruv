/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff;

import org.palladiosimulator.pcm.core.PCMRandomVariable;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Guarded Branch Transition</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Guarded&nbsp;Branch&nbsp;Transition&nbsp;Provides&nbsp;a&nbsp;link&nbsp;between&nbsp;a&nbsp;
 * BranchAction&nbsp;and&nbsp;a&nbsp;nested&nbsp;ResourceDemandingBehaviour,&nbsp;which&nbsp;
 * includes&nbsp;the&nbsp;actions&nbsp;executed&nbsp;inside&nbsp;the&nbsp;branch.&nbsp;It&nbsp;uses&
 * nbsp;a&nbsp;guard,&nbsp;i.e.&nbsp;a&nbsp;boolean&nbsp;expression&nbsp;specified&nbsp;by&nbsp;a&
 * nbsp;RandomVariable,&nbsp;to&nbsp;determine&nbsp;whether&nbsp;the&nbsp;transition&nbsp;is&nbsp;
 * chosen.&nbsp;If&nbsp;the&nbsp;guard&nbsp;evaluates&nbsp;to&nbsp;true,&nbsp;the&nbsp;branch&nbsp;
 * is&nbsp;chosen,&nbsp;otherwise&nbsp;if&nbsp;the&nbsp;guard&nbsp;evaluates&nbsp;to&nbsp;false&nbsp
 * ;another&nbsp;branch&nbsp;transition&nbsp;must&nbsp;be&nbsp;chosen.<br />
 * The&nbsp;guard&nbsp;may&nbsp;contain&nbsp;references&nbsp;to&nbsp;the&nbsp;service's&nbsp;input&
 * nbsp;parameters&nbsp;or&nbsp;component&nbsp;parameters.&nbsp;A&nbsp;component&nbsp;developer&nbsp
 * ;can&nbsp;specify&nbsp;complex&nbsp;boolean&nbsp;expressions&nbsp;by&nbsp;using&nbsp;the&nbsp;AND
 * ,&nbsp;OR,&nbsp;and&nbsp;NOT&nbsp;operations&nbsp;provided&nbsp;by&nbsp;the&nbsp;StoEx&nbsp;
 * framework.&nbsp;As&nbsp;the&nbsp;domain&nbsp;expert&nbsp;may&nbsp;have&nbsp;characterised&nbsp;
 * the&nbsp;parameters&nbsp;used&nbsp;in&nbsp;a&nbsp;guard&nbsp;with&nbsp;probability&nbsp;
 * distributions,&nbsp;it&nbsp;might&nbsp;happen&nbsp;that&nbsp;a&nbsp;guard&nbsp;does&nbsp;not&nbsp
 * ;evaluate&nbsp;to&nbsp;true&nbsp;or&nbsp;false&nbsp;with&nbsp;a&nbsp;probability&nbsp;of&nbsp;1.0
 * .&nbsp;For&nbsp;example,&nbsp;the&nbsp;specification&nbsp;can&nbsp;express&nbsp;that&nbsp;a&nbsp;
 * guard&nbsp;evaluates&nbsp;to&nbsp;true&nbsp;with&nbsp;a&nbsp;probability&nbsp;of&nbsp;0.3,&nbsp;
 * and&nbsp;to&nbsp;false&nbsp;with&nbsp;a&nbsp;probability&nbsp;of&nbsp;0.7.&nbsp;In&nbsp;any&nbsp;
 * case,&nbsp;the&nbsp;probabilities&nbsp;of&nbsp;the&nbsp;individual&nbsp;guards&nbsp;attached&nbsp
 * ;to&nbsp;all&nbsp;GuardedBranchTransitions&nbsp;contained&nbsp;in&nbsp;a&nbsp;BranchAction&nbsp;
 * must&nbsp;sum&nbsp;up&nbsp;to&nbsp;1.0.<br />
 * There&nbsp;is&nbsp;no&nbsp;predefined&nbsp;order&nbsp;in&nbsp;evaluating&nbsp;the&nbsp;guards&
 * nbsp;attached&nbsp;to&nbsp;a&nbsp;BranchAction.&nbsp;This&nbsp;differs&nbsp;from&nbsp;programming
 * &nbsp;languages&nbsp;such&nbsp;as&nbsp;C&nbsp;or&nbsp;Java,&nbsp;where&nbsp;the&nbsp;conditions&
 * nbsp;on&nbsp;if/then/else&nbsp;statements&nbsp;are&nbsp;evaluated&nbsp;in&nbsp;the&nbsp;order&
 * nbsp;of&nbsp;their&nbsp;appearance&nbsp;in&nbsp;the&nbsp;code.&nbsp;Such&nbsp;programming&nbsp;
 * languages&nbsp;allow&nbsp;overlapping&nbsp;branching&nbsp;conditions&nbsp;(for&nbsp;example,&nbsp
 * ;if&nbsp;(X&lt;10)&nbsp;//...&nbsp;else&nbsp;if&nbsp;(X&lt;20)&nbsp;//&nbsp;...),&nbsp;which&nbsp
 * ;are&nbsp;not&nbsp;allowed&nbsp;for&nbsp;the&nbsp;guards&nbsp;in&nbsp;GuardedBranchTransitions,&
 * nbsp;because&nbsp;the&nbsp;missing&nbsp;order&nbsp;specification&nbsp;would&nbsp;lead&nbsp;to&
 * nbsp;ambiguous&nbsp;boolean&nbsp;expressions&nbsp;and&nbsp;enable&nbsp;more&nbsp;than&nbsp;one&
 * nbsp;guard&nbsp;to&nbsp;become&nbsp;true.&nbsp;If&nbsp;X&nbsp;would&nbsp;have&nbsp;the&nbsp;value
 * &nbsp;5,&nbsp;both&nbsp;conditions&nbsp;would&nbsp;evaluate&nbsp;to&nbsp;true&nbsp;if&nbsp;they&
 * nbsp;would&nbsp;be&nbsp;used&nbsp;directly&nbsp;as&nbsp;guards&nbsp;in&nbsp;
 * GuardedBranchTransitions.&nbsp;The&nbsp;correct&nbsp;specification&nbsp;of&nbsp;the&nbsp;guards&
 * nbsp;in&nbsp;this&nbsp;case&nbsp;would&nbsp;be&nbsp;X.VALUE&nbsp;10&nbsp;and&nbsp;X.VALUE&nbsp;10
 * &nbsp;AND&nbsp;X.VALUE&nbsp;20.&nbsp;Guards&nbsp;might&nbsp;lead&nbsp;to&nbsp;stochastic&nbsp;
 * dependencies&nbsp;when&nbsp;evaluating&nbsp;variable&nbsp;characterisations&nbsp;inside&nbsp;a&
 * nbsp;branched&nbsp;behaviour.&nbsp;For&nbsp;example,&nbsp;if&nbsp;the&nbsp;guard&nbsp;X.VALUE&
 * nbsp;&nbsp;&nbsp;10&nbsp;had&nbsp;formerly&nbsp;evaluated&nbsp;to&nbsp;true,&nbsp;and&nbsp;the&
 * nbsp;RDSEFF&nbsp;uses&nbsp;X.VALUE&nbsp;inside&nbsp;the&nbsp;branched&nbsp;behaviour,&nbsp;the&
 * nbsp;sample&nbsp;space&nbsp;of&nbsp;the&nbsp;random&nbsp;variable&nbsp;specifying&nbsp;the&nbsp;
 * characterisation&nbsp;must&nbsp;be&nbsp;restricted,&nbsp;as&nbsp;the&nbsp;event&nbsp;that&nbsp;X&
 * nbsp;takes&nbsp;a&nbsp;values&nbsp;greater&nbsp;than&nbsp;10&nbsp;cannot&nbsp;occur&nbsp;anymore.
 * &nbsp;Therefore&nbsp;its&nbsp;probability&nbsp;is&nbsp;zero.&nbsp;Any&nbsp;variable&nbsp;
 * characterisation&nbsp;always&nbsp;needs&nbsp;to&nbsp;be&nbsp;evaluated&nbsp;under&nbsp;the&nbsp;
 * condition&nbsp;that&nbsp;all&nbsp;guards&nbsp;in&nbsp;the&nbsp;usage&nbsp;scenario’s&nbsp;path&
 * nbsp;to&nbsp;it&nbsp;have&nbsp;evaluated&nbsp;to&nbsp;true. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.GuardedBranchTransition#getBranchCondition_GuardedBranchTransition
 * <em>Branch Condition Guarded Branch Transition</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.SeffPackage#getGuardedBranchTransition()
 * @model
 * @generated
 */
public interface GuardedBranchTransition extends AbstractBranchTransition {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Branch Condition Guarded Branch Transition</b></em>'
     * containment reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.core.PCMRandomVariable#getGuardedBranchTransition_PCMRandomVariable
     * <em>Guarded Branch Transition PCM Random Variable</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Branch Condition Guarded Branch Transition</em>' containment
     * reference isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Branch Condition Guarded Branch Transition</em>' containment
     *         reference.
     * @see #setBranchCondition_GuardedBranchTransition(PCMRandomVariable)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getGuardedBranchTransition_BranchCondition_GuardedBranchTransition()
     * @see org.palladiosimulator.pcm.core.PCMRandomVariable#getGuardedBranchTransition_PCMRandomVariable
     * @model opposite="guardedBranchTransition_PCMRandomVariable" containment="true"
     *        required="true" ordered="false"
     * @generated
     */
    PCMRandomVariable getBranchCondition_GuardedBranchTransition();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.GuardedBranchTransition#getBranchCondition_GuardedBranchTransition
     * <em>Branch Condition Guarded Branch Transition</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Branch Condition Guarded Branch Transition</em>'
     *            containment reference.
     * @see #getBranchCondition_GuardedBranchTransition()
     * @generated
     */
    void setBranchCondition_GuardedBranchTransition(PCMRandomVariable value);

} // GuardedBranchTransition
