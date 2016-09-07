/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

import org.eclipse.xtext.common.types.JvmTypeReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Member Feature Call</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#getMemberCallTarget <em>Member Call Target</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#isNullSafe <em>Null Safe</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#isExplicitStatic <em>Explicit Static</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#getTypeArguments <em>Type Arguments</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#getFeature <em>Feature</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#isExplicitOperationCall <em>Explicit Operation Call</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#getMemberCallArguments <em>Member Call Arguments</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getMemberFeatureCall()
 * @model
 * @generated
 */
public interface MemberFeatureCall extends Expression
{
  /**
   * Returns the value of the '<em><b>Member Call Target</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Member Call Target</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Member Call Target</em>' containment reference.
   * @see #setMemberCallTarget(Expression)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getMemberFeatureCall_MemberCallTarget()
   * @model containment="true"
   * @generated
   */
  Expression getMemberCallTarget();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#getMemberCallTarget <em>Member Call Target</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Member Call Target</em>' containment reference.
   * @see #getMemberCallTarget()
   * @generated
   */
  void setMemberCallTarget(Expression value);

  /**
   * Returns the value of the '<em><b>Null Safe</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Null Safe</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Null Safe</em>' attribute.
   * @see #setNullSafe(boolean)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getMemberFeatureCall_NullSafe()
   * @model
   * @generated
   */
  boolean isNullSafe();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#isNullSafe <em>Null Safe</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Null Safe</em>' attribute.
   * @see #isNullSafe()
   * @generated
   */
  void setNullSafe(boolean value);

  /**
   * Returns the value of the '<em><b>Explicit Static</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Explicit Static</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Explicit Static</em>' attribute.
   * @see #setExplicitStatic(boolean)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getMemberFeatureCall_ExplicitStatic()
   * @model
   * @generated
   */
  boolean isExplicitStatic();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#isExplicitStatic <em>Explicit Static</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Explicit Static</em>' attribute.
   * @see #isExplicitStatic()
   * @generated
   */
  void setExplicitStatic(boolean value);

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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getMemberFeatureCall_TypeArguments()
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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getMemberFeatureCall_Feature()
   * @model
   * @generated
   */
  String getFeature();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#getFeature <em>Feature</em>}' attribute.
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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getMemberFeatureCall_ExplicitOperationCall()
   * @model
   * @generated
   */
  boolean isExplicitOperationCall();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall#isExplicitOperationCall <em>Explicit Operation Call</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Explicit Operation Call</em>' attribute.
   * @see #isExplicitOperationCall()
   * @generated
   */
  void setExplicitOperationCall(boolean value);

  /**
   * Returns the value of the '<em><b>Member Call Arguments</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.Expression}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Member Call Arguments</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Member Call Arguments</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getMemberFeatureCall_MemberCallArguments()
   * @model containment="true"
   * @generated
   */
  EList<Expression> getMemberCallArguments();

} // MemberFeatureCall
