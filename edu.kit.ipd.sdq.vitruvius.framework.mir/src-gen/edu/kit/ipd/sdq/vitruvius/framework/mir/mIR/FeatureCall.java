/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Call</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall#getRef <em>Ref</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall#getTail <em>Tail</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall#getType <em>Type</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getFeatureCall()
 * @model
 * @generated
 */
public interface FeatureCall extends TypedElement
{
  /**
   * Returns the value of the '<em><b>Ref</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Ref</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Ref</em>' containment reference.
   * @see #setRef(TypedElement)
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getFeatureCall_Ref()
   * @model containment="true"
   * @generated
   */
  TypedElement getRef();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall#getRef <em>Ref</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Ref</em>' containment reference.
   * @see #getRef()
   * @generated
   */
  void setRef(TypedElement value);

  /**
   * Returns the value of the '<em><b>Tail</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Tail</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Tail</em>' reference.
   * @see #setTail(EStructuralFeature)
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getFeatureCall_Tail()
   * @model
   * @generated
   */
  EStructuralFeature getTail();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall#getTail <em>Tail</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Tail</em>' reference.
   * @see #getTail()
   * @generated
   */
  void setTail(EStructuralFeature value);

  /**
   * Returns the value of the '<em><b>Type</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' reference.
   * @see #setType(EClassifier)
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getFeatureCall_Type()
   * @model
   * @generated
   */
  EClassifier getType();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall#getType <em>Type</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' reference.
   * @see #getType()
   * @generated
   */
  void setType(EClassifier value);

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
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getFeatureCall_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

} // FeatureCall
