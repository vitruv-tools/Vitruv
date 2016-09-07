/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

import org.eclipse.xtext.common.types.JvmTypeReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Call</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.FeatureCall#getTypeArguments <em>Type Arguments</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.FeatureCall#getFeature <em>Feature</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.FeatureCall#isExplicitOperationCall <em>Explicit Operation Call</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.FeatureCall#getFeatureCallArguments <em>Feature Call Arguments</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getFeatureCall()
 * @model
 * @generated
 */
public interface FeatureCall extends Expression
{
  /**
   * Returns the value of the '<em><b>Type Arguments</b></em>' containment reference list.
   * The list contents are of type {@link org.eclipse.xtext.common.types.JvmTypeReference}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type Arguments</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type Arguments</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getFeatureCall_TypeArguments()
   * @model containment="true"
   * @generated
   */
  EList<JvmTypeReference> getTypeArguments();

  /**
   * Returns the value of the '<em><b>Feature</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Feature</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Feature</em>' attribute.
   * @see #setFeature(String)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getFeatureCall_Feature()
   * @model
   * @generated
   */
  String getFeature();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.FeatureCall#getFeature <em>Feature</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Feature</em>' attribute.
   * @see #getFeature()
   * @generated
   */
  void setFeature(String value);

  /**
   * Returns the value of the '<em><b>Explicit Operation Call</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Explicit Operation Call</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Explicit Operation Call</em>' attribute.
   * @see #setExplicitOperationCall(boolean)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getFeatureCall_ExplicitOperationCall()
   * @model
   * @generated
   */
  boolean isExplicitOperationCall();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.FeatureCall#isExplicitOperationCall <em>Explicit Operation Call</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Explicit Operation Call</em>' attribute.
   * @see #isExplicitOperationCall()
   * @generated
   */
  void setExplicitOperationCall(boolean value);

  /**
   * Returns the value of the '<em><b>Feature Call Arguments</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.Expression}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Feature Call Arguments</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Feature Call Arguments</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getFeatureCall_FeatureCallArguments()
   * @model containment="true"
   * @generated
   */
  EList<Expression> getFeatureCallArguments();

} // FeatureCall
