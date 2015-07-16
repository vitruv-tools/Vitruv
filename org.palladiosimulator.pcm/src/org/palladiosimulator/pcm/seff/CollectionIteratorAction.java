/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff;

import org.palladiosimulator.pcm.repository.Parameter;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Collection Iterator Action</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Collection&nbsp;Iterator&nbsp;Action&nbsp;Models&nbsp;the&nbsp;repeated&nbsp;execution&nbsp;of&
 * nbsp;its&nbsp;inner&nbsp;ResourceDemandingBehaviour&nbsp;for&nbsp;each&nbsp;element&nbsp;of&nbsp;
 * a&nbsp;collection&nbsp;data&nbsp;type.&nbsp;Therefore&nbsp;it&nbsp;contains&nbsp;a&nbsp;reference
 * &nbsp;to&nbsp;an&nbsp;input&nbsp;parameter&nbsp;of&nbsp;the&nbsp;service's&nbsp;signature,&nbsp;
 * which&nbsp;must&nbsp;be&nbsp;of&nbsp;type&nbsp;CollectionDataType.&nbsp;The&nbsp;NUMBER&nbsp;OF&
 * nbsp;ELEMENTS&nbsp;must&nbsp;be&nbsp;specified&nbsp;from&nbsp;the&nbsp;outside&nbsp;of&nbsp;the&
 * nbsp;component,&nbsp;either&nbsp;by&nbsp;another&nbsp;RDSEFF&nbsp;or&nbsp;by&nbsp;an&nbsp;usage&
 * nbsp;model&nbsp;calling&nbsp;this&nbsp;service.&nbsp;It&nbsp;can&nbsp;be&nbsp;of&nbsp;type&nbsp;
 * integer&nbsp;or&nbsp;IntPMF.&nbsp;Besides&nbsp;the&nbsp;source&nbsp;of&nbsp;the&nbsp;number&nbsp;
 * of&nbsp;iterations,&nbsp;CollectionIteratorActions&nbsp;differ&nbsp;from&nbsp;LoopAction&nbsp;
 * only&nbsp;in&nbsp;their&nbsp;allowed&nbsp;stochastic&nbsp;dependence&nbsp;of&nbsp;random&nbsp;
 * variables&nbsp;inside&nbsp;the&nbsp;loop&nbsp;body's&nbsp;ResourceDemandingBehaviour.&nbsp;If&
 * nbsp;the&nbsp;same&nbsp;random&nbsp;variable&nbsp;occurs&nbsp;twice&nbsp;in&nbsp;such&nbsp;a&nbsp
 * ;loop&nbsp;body,&nbsp;analysis&nbsp;tools&nbsp;must&nbsp;evaluate&nbsp;the&nbsp;second&nbsp;
 * occurrence&nbsp;in&nbsp;stochastic&nbsp;dependence&nbsp;to&nbsp;the&nbsp;first&nbsp;occurrence.&
 * nbsp;This&nbsp;complicates&nbsp;the&nbsp;involved&nbsp;calculation&nbsp;and&nbsp;might&nbsp;lead&
 * nbsp;to&nbsp;the&nbsp;intractability&nbsp;of&nbsp;the&nbsp;model,&nbsp;therefore&nbsp;component&
 * nbsp;developers&nbsp;should&nbsp;use&nbsp;CollectionIteratorActions&nbsp;with&nbsp;care&nbsp;and&
 * nbsp;only&nbsp;include&nbsp;them&nbsp;if&nbsp;they&nbsp;expect&nbsp;that&nbsp;the&nbsp;prediction
 * &nbsp;results&nbsp;would&nbsp;be&nbsp;vastly&nbsp;inaccurate&nbsp;without&nbsp;it. <!--
 * end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.CollectionIteratorAction#getParameter_CollectionIteratorAction
 * <em>Parameter Collection Iterator Action</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.SeffPackage#getCollectionIteratorAction()
 * @model
 * @generated
 */
public interface CollectionIteratorAction extends AbstractLoopAction {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Parameter Collection Iterator Action</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameter Collection Iterator Action</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Parameter Collection Iterator Action</em>' reference.
     * @see #setParameter_CollectionIteratorAction(Parameter)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getCollectionIteratorAction_Parameter_CollectionIteratorAction()
     * @model required="true" ordered="false"
     * @generated
     */
    Parameter getParameter_CollectionIteratorAction();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.CollectionIteratorAction#getParameter_CollectionIteratorAction
     * <em>Parameter Collection Iterator Action</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Parameter Collection Iterator Action</em>' reference.
     * @see #getParameter_CollectionIteratorAction()
     * @generated
     */
    void setParameter_CollectionIteratorAction(Parameter value);

} // CollectionIteratorAction
