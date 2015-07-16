/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.qosannotations;

import org.eclipse.emf.cdo.CDOObject;
import org.palladiosimulator.pcm.repository.Role;
import org.palladiosimulator.pcm.repository.Signature;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Specified Qo SAnnotation</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * <p>
 * SpecifiedQoSAnnotations&nbsp;(as&nbsp;an&nbsp;abstract&nbsp;class)&nbsp;associate&nbsp;specified&
 * nbsp;(see&nbsp;QoSAnnotation)&nbsp;QoS&nbsp;properties&nbsp;to&nbsp;services&nbsp;of&nbsp;
 * components.&nbsp;A&nbsp;service&nbsp;is&nbsp;thereby&nbsp;determined&nbsp;by&nbsp;a&nbsp;
 * Signature&nbsp;and&nbsp;a&nbsp;Role&nbsp;(i.e.,&nbsp;an&nbsp;interface&nbsp;bound&nbsp;to&nbsp;a&
 * nbsp;component).
 * </p>
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.qosannotations.SpecifiedQoSAnnotation#getSignature_SpecifiedQoSAnnation
 * <em>Signature Specified Qo SAnnation</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.qosannotations.SpecifiedQoSAnnotation#getRole_SpecifiedQoSAnnotation
 * <em>Role Specified Qo SAnnotation</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.qosannotations.SpecifiedQoSAnnotation#getQosAnnotations_SpecifiedQoSAnnotation
 * <em>Qos Annotations Specified Qo SAnnotation</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.qosannotations.QosannotationsPackage#getSpecifiedQoSAnnotation()
 * @model abstract="true"
 * @extends CDOObject
 * @generated
 */
public interface SpecifiedQoSAnnotation extends CDOObject {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Signature Specified Qo SAnnation</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Signature Specified Qo SAnnation</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Signature Specified Qo SAnnation</em>' reference.
     * @see #setSignature_SpecifiedQoSAnnation(Signature)
     * @see org.palladiosimulator.pcm.qosannotations.QosannotationsPackage#getSpecifiedQoSAnnotation_Signature_SpecifiedQoSAnnation()
     * @model required="true"
     * @generated
     */
    Signature getSignature_SpecifiedQoSAnnation();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.qosannotations.SpecifiedQoSAnnotation#getSignature_SpecifiedQoSAnnation
     * <em>Signature Specified Qo SAnnation</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Signature Specified Qo SAnnation</em>' reference.
     * @see #getSignature_SpecifiedQoSAnnation()
     * @generated
     */
    void setSignature_SpecifiedQoSAnnation(Signature value);

    /**
     * Returns the value of the '<em><b>Role Specified Qo SAnnotation</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Role Specified Qo SAnnotation</em>' reference isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Role Specified Qo SAnnotation</em>' reference.
     * @see #setRole_SpecifiedQoSAnnotation(Role)
     * @see org.palladiosimulator.pcm.qosannotations.QosannotationsPackage#getSpecifiedQoSAnnotation_Role_SpecifiedQoSAnnotation()
     * @model required="true"
     * @generated
     */
    Role getRole_SpecifiedQoSAnnotation();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.qosannotations.SpecifiedQoSAnnotation#getRole_SpecifiedQoSAnnotation
     * <em>Role Specified Qo SAnnotation</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @param value
     *            the new value of the '<em>Role Specified Qo SAnnotation</em>' reference.
     * @see #getRole_SpecifiedQoSAnnotation()
     * @generated
     */
    void setRole_SpecifiedQoSAnnotation(Role value);

    /**
     * Returns the value of the '<em><b>Qos Annotations Specified Qo SAnnotation</b></em>' container
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.qosannotations.QoSAnnotations#getSpecifiedQoSAnnotations_QoSAnnotations
     * <em>Specified Qo SAnnotations Qo SAnnotations</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Qos Annotations Specified Qo SAnnotation</em>' container reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Qos Annotations Specified Qo SAnnotation</em>' container
     *         reference.
     * @see #setQosAnnotations_SpecifiedQoSAnnotation(QoSAnnotations)
     * @see org.palladiosimulator.pcm.qosannotations.QosannotationsPackage#getSpecifiedQoSAnnotation_QosAnnotations_SpecifiedQoSAnnotation()
     * @see org.palladiosimulator.pcm.qosannotations.QoSAnnotations#getSpecifiedQoSAnnotations_QoSAnnotations
     * @model opposite="specifiedQoSAnnotations_QoSAnnotations" required="true" transient="false"
     *        ordered="false"
     * @generated
     */
    QoSAnnotations getQosAnnotations_SpecifiedQoSAnnotation();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.qosannotations.SpecifiedQoSAnnotation#getQosAnnotations_SpecifiedQoSAnnotation
     * <em>Qos Annotations Specified Qo SAnnotation</em>}' container reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Qos Annotations Specified Qo SAnnotation</em>' container
     *            reference.
     * @see #getQosAnnotations_SpecifiedQoSAnnotation()
     * @generated
     */
    void setQosAnnotations_SpecifiedQoSAnnotation(QoSAnnotations value);

} // SpecifiedQoSAnnotation
