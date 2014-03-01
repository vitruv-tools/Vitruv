/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.design.mIR;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Operation Restriction</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.FeatureOperationRestriction#getFeature <em>Feature</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getFeatureOperationRestriction()
 * @model
 * @generated
 */
public interface FeatureOperationRestriction extends OperationRestriction
{
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
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getFeatureOperationRestriction_Feature()
   * @model
   * @generated
   */
  String getFeature();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.FeatureOperationRestriction#getFeature <em>Feature</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Feature</em>' attribute.
   * @see #getFeature()
   * @generated
   */
  void setFeature(String value);

} // FeatureOperationRestriction
