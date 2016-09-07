/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

import org.eclipse.xtext.common.types.JvmTypeReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constructor Call</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.ConstructorCall#getConstructor <em>Constructor</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.ConstructorCall#getTypeArguments <em>Type Arguments</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.ConstructorCall#isExplicitConstructorCall <em>Explicit Constructor Call</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.ConstructorCall#getArguments <em>Arguments</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getConstructorCall()
 * @model
 * @generated
 */
public interface ConstructorCall extends Expression
{
  /**
   * Returns the value of the '<em><b>Constructor</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Constructor</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Constructor</em>' attribute.
   * @see #setConstructor(String)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getConstructorCall_Constructor()
   * @model
   * @generated
   */
  String getConstructor();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.ConstructorCall#getConstructor <em>Constructor</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Constructor</em>' attribute.
   * @see #getConstructor()
   * @generated
   */
  void setConstructor(String value);

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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getConstructorCall_TypeArguments()
   * @model containment="true"
   * @generated
   */
  EList<JvmTypeReference> getTypeArguments();

  /**
   * Returns the value of the '<em><b>Explicit Constructor Call</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Explicit Constructor Call</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Explicit Constructor Call</em>' attribute.
   * @see #setExplicitConstructorCall(boolean)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getConstructorCall_ExplicitConstructorCall()
   * @model
   * @generated
   */
  boolean isExplicitConstructorCall();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.ConstructorCall#isExplicitConstructorCall <em>Explicit Constructor Call</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Explicit Constructor Call</em>' attribute.
   * @see #isExplicitConstructorCall()
   * @generated
   */
  void setExplicitConstructorCall(boolean value);

  /**
   * Returns the value of the '<em><b>Arguments</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.Expression}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Arguments</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Arguments</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getConstructorCall_Arguments()
   * @model containment="true"
   * @generated
   */
  EList<Expression> getArguments();

} // ConstructorCall
