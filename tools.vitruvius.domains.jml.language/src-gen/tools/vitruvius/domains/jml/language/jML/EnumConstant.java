/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enum Constant</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.EnumConstant#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.EnumConstant#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.EnumConstant#getArguments <em>Arguments</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.EnumConstant#getClassbodydeclaration <em>Classbodydeclaration</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getEnumConstant()
 * @model
 * @generated
 */
public interface EnumConstant extends EObject
{
  /**
   * Returns the value of the '<em><b>Annotations</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Annotations</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Annotations</em>' containment reference.
   * @see #setAnnotations(Annotations)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getEnumConstant_Annotations()
   * @model containment="true"
   * @generated
   */
  Annotations getAnnotations();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.EnumConstant#getAnnotations <em>Annotations</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Annotations</em>' containment reference.
   * @see #getAnnotations()
   * @generated
   */
  void setAnnotations(Annotations value);

  /**
   * Returns the value of the '<em><b>Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Identifier</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Identifier</em>' attribute.
   * @see #setIdentifier(String)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getEnumConstant_Identifier()
   * @model
   * @generated
   */
  String getIdentifier();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.EnumConstant#getIdentifier <em>Identifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Identifier</em>' attribute.
   * @see #getIdentifier()
   * @generated
   */
  void setIdentifier(String value);

  /**
   * Returns the value of the '<em><b>Arguments</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Arguments</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Arguments</em>' containment reference.
   * @see #setArguments(Arguments)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getEnumConstant_Arguments()
   * @model containment="true"
   * @generated
   */
  Arguments getArguments();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.EnumConstant#getArguments <em>Arguments</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Arguments</em>' containment reference.
   * @see #getArguments()
   * @generated
   */
  void setArguments(Arguments value);

  /**
   * Returns the value of the '<em><b>Classbodydeclaration</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.ClassBodyDeclaration}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Classbodydeclaration</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Classbodydeclaration</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getEnumConstant_Classbodydeclaration()
   * @model containment="true"
   * @generated
   */
  EList<ClassBodyDeclaration> getClassbodydeclaration();

} // EnumConstant
