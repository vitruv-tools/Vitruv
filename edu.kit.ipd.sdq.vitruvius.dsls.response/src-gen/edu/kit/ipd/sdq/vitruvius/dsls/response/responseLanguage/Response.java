/**
 * generated by Xtext 2.10.0
 */
package edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Response</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response#getTrigger <em>Trigger</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response#getRoutine <em>Routine</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response#getResponsesSegment <em>Responses Segment</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguagePackage#getResponse()
 * @model
 * @generated
 */
public interface Response extends EObject
{
  /**
   * Returns the value of the '<em><b>Documentation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Documentation</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Documentation</em>' attribute.
   * @see #setDocumentation(String)
   * @see edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguagePackage#getResponse_Documentation()
   * @model
   * @generated
   */
  String getDocumentation();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response#getDocumentation <em>Documentation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Documentation</em>' attribute.
   * @see #getDocumentation()
   * @generated
   */
  void setDocumentation(String value);

  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguagePackage#getResponse_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Trigger</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Trigger</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Trigger</em>' containment reference.
   * @see #setTrigger(Trigger)
   * @see edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguagePackage#getResponse_Trigger()
   * @model containment="true"
   * @generated
   */
  Trigger getTrigger();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response#getTrigger <em>Trigger</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Trigger</em>' containment reference.
   * @see #getTrigger()
   * @generated
   */
  void setTrigger(Trigger value);

  /**
   * Returns the value of the '<em><b>Routine</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Routine</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Routine</em>' containment reference.
   * @see #setRoutine(ImplicitRoutine)
   * @see edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguagePackage#getResponse_Routine()
   * @model containment="true"
   * @generated
   */
  ImplicitRoutine getRoutine();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response#getRoutine <em>Routine</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Routine</em>' containment reference.
   * @see #getRoutine()
   * @generated
   */
  void setRoutine(ImplicitRoutine value);

  /**
   * Returns the value of the '<em><b>Responses Segment</b></em>' container reference.
   * It is bidirectional and its opposite is '{@link edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponsesSegment#getResponses <em>Responses</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Responses Segment</em>' container reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Responses Segment</em>' container reference.
   * @see #setResponsesSegment(ResponsesSegment)
   * @see edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguagePackage#getResponse_ResponsesSegment()
   * @see edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponsesSegment#getResponses
   * @model opposite="responses" required="true" transient="false"
   * @generated
   */
  ResponsesSegment getResponsesSegment();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response#getResponsesSegment <em>Responses Segment</em>}' container reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Responses Segment</em>' container reference.
   * @see #getResponsesSegment()
   * @generated
   */
  void setResponsesSegment(ResponsesSegment value);

} // Response
