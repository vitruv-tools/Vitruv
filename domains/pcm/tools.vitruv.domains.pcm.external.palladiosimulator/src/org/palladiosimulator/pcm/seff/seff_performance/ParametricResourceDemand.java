/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.seff_performance;

import java.util.Map;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.seff.AbstractInternalControlFlowAction;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Parametric Resource Demand</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> TODO&nbsp;(Überarbeitung&nbsp;durch&nbsp;MH)<br />
 * Parametric&nbsp;Resource&nbsp;Demand&nbsp;Specifies&nbsp;the&nbsp;amount&nbsp;of&nbsp;processing&
 * nbsp;requested&nbsp;from&nbsp;a&nbsp;certain&nbsp;type&nbsp;of&nbsp;resource&nbsp;in&nbsp;a&nbsp;
 * parametrised&nbsp;way.&nbsp;It&nbsp;assigns&nbsp;the&nbsp;demand&nbsp;specified&nbsp;as&nbsp;a&
 * nbsp;Random-Variable&nbsp;to&nbsp;an&nbsp;abstract&nbsp;ProcessingResourceType&nbsp;(e.g.,&nbsp;
 * CPU,&nbsp;hard&nbsp;disk)&nbsp;instead&nbsp;of&nbsp;a&nbsp;concrete&nbsp;
 * ProcessingResourceSpecification&nbsp;(e.g.,&nbsp;5&nbsp;Ghz&nbsp;CPU,&nbsp;20&nbsp;MByte/s&nbsp;
 * hard&nbsp;disk).&nbsp;This&nbsp;keeps&nbsp;the&nbsp;RDSEFF&nbsp;independent&nbsp;from&nbsp;a&nbsp
 * ;specific&nbsp;resource&nbsp;environment,&nbsp;and&nbsp;makes&nbsp;the&nbsp;concrete&nbsp;
 * resources&nbsp;replaceable&nbsp;to&nbsp;answer&nbsp;sizing&nbsp;questions.<br />
 * The&nbsp;demand's&nbsp;unit&nbsp;is&nbsp;equal&nbsp;for&nbsp;all&nbsp;
 * ProcessingResourceSpecifications&nbsp;referencing<br />
 * the&nbsp;same&nbsp;ProcessingResourceType.&nbsp;It&nbsp;can&nbsp;for&nbsp;example&nbsp;be&nbsp;
 * "WorkUnits"<br />
 * for&nbsp;CPUs&nbsp;[Smi02]&nbsp;or&nbsp;"BytesRead"&nbsp;for&nbsp;hard&nbsp;disks.&nbsp;Each&nbsp
 * ;ProcessingResource-<br />
 * Specification&nbsp;contains&nbsp;a&nbsp;processing&nbsp;rate&nbsp;for&nbsp;demands&nbsp;(e.g.,&
 * nbsp;1000&nbsp;WorkUnits/s,&nbsp;20<br />
 * MB/s),&nbsp;which&nbsp;analysis&nbsp;tools&nbsp;use&nbsp;to&nbsp;compute&nbsp;an&nbsp;actual&nbsp
 * ;timing&nbsp;value&nbsp;in&nbsp;seconds.&nbsp;They<br />
 * use&nbsp;this&nbsp;timing&nbsp;value&nbsp;for&nbsp;example&nbsp;as&nbsp;the&nbsp;service&nbsp;
 * demand&nbsp;on&nbsp;a&nbsp;service&nbsp;center&nbsp;in&nbsp;a&nbsp;queueing<br />
 * network&nbsp;or&nbsp;the&nbsp;firing&nbsp;delay&nbsp;of&nbsp;a&nbsp;transition&nbsp;in&nbsp;a&
 * nbsp;Petri&nbsp;net.&nbsp;As&nbsp;multiple&nbsp;component&nbsp;services<br />
 * might&nbsp;request&nbsp;processing&nbsp;on&nbsp;the&nbsp;same&nbsp;resource,&nbsp;these&nbsp;
 * analytical&nbsp;or&nbsp;simulation&nbsp;models<br />
 * allow&nbsp;determining&nbsp;the&nbsp;waiting&nbsp;delay&nbsp;induced&nbsp;by&nbsp;this&nbsp;
 * contention&nbsp;effect.<br />
 * Besides&nbsp;this&nbsp;parameterisation&nbsp;over&nbsp;different&nbsp;resource&nbsp;environments,
 * &nbsp;Parametric-<br />
 * ResourceDemands&nbsp;also&nbsp;parameterise&nbsp;over&nbsp;the&nbsp;usage&nbsp;profile.&nbsp;For&
 * nbsp;this,&nbsp;the&nbsp;stochastic&nbsp;expression<br />
 * specifying&nbsp;the&nbsp;resource&nbsp;demand&nbsp;can&nbsp;contain&nbsp;references&nbsp;to&nbsp;
 * the&nbsp;service's&nbsp;input<br />
 * parameters&nbsp;or&nbsp;the&nbsp;component&nbsp;parameters.&nbsp;Upon&nbsp;evaluating&nbsp;the&
 * nbsp;resource&nbsp;demand,&nbsp;analysis<br />
 * tools&nbsp;use&nbsp;the&nbsp;current&nbsp;characterisation&nbsp;of&nbsp;the&nbsp;referenced&nbsp;
 * input&nbsp;or&nbsp;component&nbsp;parameter<br />
 * and&nbsp;substitute&nbsp;the&nbsp;reference&nbsp;with&nbsp;this&nbsp;characterisation&nbsp;in&
 * nbsp;the&nbsp;stochastic&nbsp;expression.<br />
 * Solving&nbsp;the&nbsp;stochastic&nbsp;expression,&nbsp;which&nbsp;can&nbsp;be&nbsp;a&nbsp;
 * function&nbsp;involving&nbsp;arithmetic&nbsp;operators<br />
 * (Chapter&nbsp;3.3.6),&nbsp;then&nbsp;yields&nbsp;a&nbsp;constant&nbsp;or&nbsp;probability&nbsp;
 * function&nbsp;for&nbsp;the&nbsp;resource&nbsp;demand.<br />
 * As&nbsp;an&nbsp;example&nbsp;for&nbsp;solving&nbsp;the&nbsp;parameterisation&nbsp;over&nbsp;
 * resource&nbsp;environment&nbsp;and&nbsp;usage<br />
 * profile,&nbsp;consider&nbsp;an&nbsp;RDSEFF&nbsp;for&nbsp;a&nbsp;service&nbsp;implementing&nbsp;
 * the&nbsp;bubblesort&nbsp;algorithm.&nbsp;It<br />
 * might&nbsp;include&nbsp;a&nbsp;CPU&nbsp;demand&nbsp;specification&nbsp;of&nbsp;n22000WorkUnits&
 * nbsp;derived&nbsp;from&nbsp;complexity<br />
 * theory&nbsp;(n2)&nbsp;and&nbsp;empirical&nbsp;measurements&nbsp;(2000).&nbsp;In&nbsp;this&nbsp;
 * case&nbsp;n&nbsp;refers&nbsp;to&nbsp;the&nbsp;length&nbsp;of<br />
 * the&nbsp;list&nbsp;the&nbsp;algorithm&nbsp;shall&nbsp;sort,&nbsp;which&nbsp;is&nbsp;an&nbsp;input
 * &nbsp;parameter&nbsp;of&nbsp;the&nbsp;service.&nbsp;If&nbsp;the&nbsp;current<br />
 * characterisation&nbsp;of&nbsp;the&nbsp;list's&nbsp;length&nbsp;is&nbsp;100&nbsp;(as&nbsp;the&nbsp
 * ;modelled&nbsp;usage&nbsp;profile),&nbsp;analysis&nbsp;tools<br />
 * derive&nbsp;1002&nbsp;&nbsp;2000&nbsp;&nbsp;12000&nbsp;WorkUnits&nbsp;from&nbsp;the&nbsp;
 * specification,&nbsp;thus&nbsp;resolving&nbsp;the&nbsp;usage<br />
 * profile&nbsp;dependency.&nbsp;If&nbsp;the&nbsp;CPU&nbsp;ProcessingResourceSpecification&nbsp;the&
 * nbsp;service's<br />
 * 126<br />
 * 4.3.&nbsp;Resource&nbsp;Demanding&nbsp;Service&nbsp;Effect&nbsp;Specification<br />
 * component&nbsp;is&nbsp;allocated&nbsp;on&nbsp;then&nbsp;contains&nbsp;a&nbsp;processing&nbsp;rate
 * &nbsp;of&nbsp;10000WorkUnits/s,&nbsp;analysis<br />
 * tools&nbsp;derive&nbsp;an&nbsp;execution&nbsp;time&nbsp;of&nbsp;12000&nbsp;WorkUnits&nbsp;{10000&
 * nbsp;WorkUnits/s&nbsp;=&nbsp;1:2&nbsp;s&nbsp;from&nbsp;the<br />
 * specification,&nbsp;thus&nbsp;resolving&nbsp;the&nbsp;resource&nbsp;environment&nbsp;dependency.
 * <br />
 * The&nbsp;stochastic&nbsp;expression&nbsp;for&nbsp;a&nbsp;ParametricResourceDemand&nbsp;depends&
 * nbsp;on&nbsp;the&nbsp;implementation<br />
 * of&nbsp;the&nbsp;service.&nbsp;Component&nbsp;developers&nbsp;can&nbsp;specify&nbsp;it&nbsp;using
 * &nbsp;complexity&nbsp;theory,<br />
 * estimations,&nbsp;or&nbsp;measurements.&nbsp;However,&nbsp;how&nbsp;to&nbsp;get&nbsp;data&nbsp;to
 * &nbsp;define&nbsp;such&nbsp;expressions<br />
 * accurately&nbsp;is&nbsp;beyond&nbsp;of&nbsp;the&nbsp;scope&nbsp;of&nbsp;this&nbsp;thesis.&nbsp;
 * Woodside&nbsp;et&nbsp;al.&nbsp;[WVCB01]&nbsp;and&nbsp;Krogmann<br />
 * [Kro07]&nbsp;present&nbsp;approaches&nbsp;for&nbsp;measuring&nbsp;resource&nbsp;demands&nbsp;in&
 * nbsp;dependency&nbsp;to&nbsp;input&nbsp;parameters.<br />
 * Meyerhoefer&nbsp;et&nbsp;al.&nbsp;[ML05]&nbsp;and&nbsp;Kuperberg&nbsp;et&nbsp;al.&nbsp;[KB07]&
 * nbsp;propose&nbsp;methods&nbsp;to<br />
 * establish&nbsp;resource&nbsp;demands&nbsp;independent&nbsp;from&nbsp;concrete&nbsp;resources.&
 * nbsp;For&nbsp;the&nbsp;scope&nbsp;of&nbsp;this<br />
 * thesis,&nbsp;it&nbsp;is&nbsp;assumed&nbsp;that&nbsp;these&nbsp;methods&nbsp;have&nbsp;been&nbsp;
 * applied&nbsp;and&nbsp;an&nbsp;accurate&nbsp;specification<br />
 * of&nbsp;the&nbsp;ParametricResourceDemand&nbsp;is&nbsp;available. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand#getSpecification_ParametericResourceDemand
 * <em>Specification Parameteric Resource Demand</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand#getRequiredResource_ParametricResourceDemand
 * <em>Required Resource Parametric Resource Demand</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand#getAction_ParametricResourceDemand
 * <em>Action Parametric Resource Demand</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.seff_performance.SeffPerformancePackage#getParametricResourceDemand()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ParametricResourceDemand extends CDOObject {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Specification Parameteric Resource Demand</b></em>'
     * containment reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.core.PCMRandomVariable#getParametricResourceDemand_PCMRandomVariable
     * <em>Parametric Resource Demand PCM Random Variable</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Specification Parameteric Resource Demand</em>' containment
     * reference isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Specification Parameteric Resource Demand</em>' containment
     *         reference.
     * @see #setSpecification_ParametericResourceDemand(PCMRandomVariable)
     * @see org.palladiosimulator.pcm.seff.seff_performance.SeffPerformancePackage#getParametricResourceDemand_Specification_ParametericResourceDemand()
     * @see org.palladiosimulator.pcm.core.PCMRandomVariable#getParametricResourceDemand_PCMRandomVariable
     * @model opposite="parametricResourceDemand_PCMRandomVariable" containment="true"
     *        required="true" ordered="false"
     * @generated
     */
    PCMRandomVariable getSpecification_ParametericResourceDemand();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand#getSpecification_ParametericResourceDemand
     * <em>Specification Parameteric Resource Demand</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Specification Parameteric Resource Demand</em>'
     *            containment reference.
     * @see #getSpecification_ParametericResourceDemand()
     * @generated
     */
    void setSpecification_ParametericResourceDemand(PCMRandomVariable value);

    /**
     * Returns the value of the '<em><b>Required Resource Parametric Resource Demand</b></em>'
     * reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Required Resource Parametric Resource Demand</em>' reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Required Resource Parametric Resource Demand</em>' reference.
     * @see #setRequiredResource_ParametricResourceDemand(ProcessingResourceType)
     * @see org.palladiosimulator.pcm.seff.seff_performance.SeffPerformancePackage#getParametricResourceDemand_RequiredResource_ParametricResourceDemand()
     * @model required="true" ordered="false"
     * @generated
     */
    ProcessingResourceType getRequiredResource_ParametricResourceDemand();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand#getRequiredResource_ParametricResourceDemand
     * <em>Required Resource Parametric Resource Demand</em>}' reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Required Resource Parametric Resource Demand</em>'
     *            reference.
     * @see #getRequiredResource_ParametricResourceDemand()
     * @generated
     */
    void setRequiredResource_ParametricResourceDemand(ProcessingResourceType value);

    /**
     * Returns the value of the '<em><b>Action Parametric Resource Demand</b></em>' container
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.seff.AbstractInternalControlFlowAction#getResourceDemand_Action
     * <em>Resource Demand Action</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Action Parametric Resource Demand</em>' container reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Action Parametric Resource Demand</em>' container reference.
     * @see #setAction_ParametricResourceDemand(AbstractInternalControlFlowAction)
     * @see org.palladiosimulator.pcm.seff.seff_performance.SeffPerformancePackage#getParametricResourceDemand_Action_ParametricResourceDemand()
     * @see org.palladiosimulator.pcm.seff.AbstractInternalControlFlowAction#getResourceDemand_Action
     * @model opposite="resourceDemand_Action" required="true" transient="false" ordered="false"
     * @generated
     */
    AbstractInternalControlFlowAction getAction_ParametricResourceDemand();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand#getAction_ParametricResourceDemand
     * <em>Action Parametric Resource Demand</em>}' container reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Action Parametric Resource Demand</em>' container
     *            reference.
     * @see #getAction_ParametricResourceDemand()
     * @generated
     */
    void setAction_ParametricResourceDemand(AbstractInternalControlFlowAction value);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='self.action_ParametricResourceDemand.resourceDemand_Action->select(prd | prd.requiredResource_ParametricResourceDemand=self.requiredResource_ParametricResourceDemand)->size() = 1'"
     * @generated
     */
    boolean DemandedProcessingResourceMustBeUniqueWithinAbstractInternalControlFlowAction(DiagnosticChain diagnostics,
            Map<Object, Object> context);

} // ParametricResourceDemand
