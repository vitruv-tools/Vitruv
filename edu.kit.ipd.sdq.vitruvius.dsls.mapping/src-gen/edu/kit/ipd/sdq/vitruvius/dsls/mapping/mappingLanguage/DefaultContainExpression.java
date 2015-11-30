/**
 */
package edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Default Contain Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.DefaultContainExpression#getTarget <em>Target</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.DefaultContainExpression#getSource <em>Source</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.DefaultContainExpression#getResource <em>Resource</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage#getDefaultContainExpression()
 * @model
 * @generated
 */
public interface DefaultContainExpression extends ConstraintExpression
{
  /**
   * Returns the value of the '<em><b>Target</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Target</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Target</em>' containment reference.
   * @see #setTarget(ContextVariable)
   * @see edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage#getDefaultContainExpression_Target()
   * @model containment="true"
   * @generated
   */
  ContextVariable getTarget();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.DefaultContainExpression#getTarget <em>Target</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Target</em>' containment reference.
   * @see #getTarget()
   * @generated
   */
  void setTarget(ContextVariable value);

  /**
   * Returns the value of the '<em><b>Source</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Source</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Source</em>' containment reference.
   * @see #setSource(FeatureOfContextVariable)
   * @see edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage#getDefaultContainExpression_Source()
   * @model containment="true"
   * @generated
   */
  FeatureOfContextVariable getSource();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.DefaultContainExpression#getSource <em>Source</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Source</em>' containment reference.
   * @see #getSource()
   * @generated
   */
  void setSource(FeatureOfContextVariable value);

  /**
   * Returns the value of the '<em><b>Resource</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Resource</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Resource</em>' attribute.
   * @see #setResource(String)
   * @see edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage#getDefaultContainExpression_Resource()
   * @model
   * @generated
   */
  String getResource();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.DefaultContainExpression#getResource <em>Resource</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Resource</em>' attribute.
   * @see #getResource()
   * @generated
   */
  void setResource(String value);

} // DefaultContainExpression
