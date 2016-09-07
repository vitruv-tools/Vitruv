/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Argument</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.TypeArgument#getType <em>Type</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.TypeArgument#isWildcard <em>Wildcard</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.TypeArgument#isExtends <em>Extends</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.TypeArgument#isSuper <em>Super</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getTypeArgument()
 * @model
 * @generated
 */
public interface TypeArgument extends EObject
{
  /**
   * Returns the value of the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' containment reference.
   * @see #setType(Type)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getTypeArgument_Type()
   * @model containment="true"
   * @generated
   */
  Type getType();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.TypeArgument#getType <em>Type</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' containment reference.
   * @see #getType()
   * @generated
   */
  void setType(Type value);

  /**
   * Returns the value of the '<em><b>Wildcard</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Wildcard</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Wildcard</em>' attribute.
   * @see #setWildcard(boolean)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getTypeArgument_Wildcard()
   * @model
   * @generated
   */
  boolean isWildcard();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.TypeArgument#isWildcard <em>Wildcard</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Wildcard</em>' attribute.
   * @see #isWildcard()
   * @generated
   */
  void setWildcard(boolean value);

  /**
   * Returns the value of the '<em><b>Extends</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Extends</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Extends</em>' attribute.
   * @see #setExtends(boolean)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getTypeArgument_Extends()
   * @model
   * @generated
   */
  boolean isExtends();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.TypeArgument#isExtends <em>Extends</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Extends</em>' attribute.
   * @see #isExtends()
   * @generated
   */
  void setExtends(boolean value);

  /**
   * Returns the value of the '<em><b>Super</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Super</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Super</em>' attribute.
   * @see #setSuper(boolean)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getTypeArgument_Super()
   * @model
   * @generated
   */
  boolean isSuper();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.TypeArgument#isSuper <em>Super</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Super</em>' attribute.
   * @see #isSuper()
   * @generated
   */
  void setSuper(boolean value);

} // TypeArgument
