/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.design.mIR;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mapping Base</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getNameA <em>Name A</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getMetaclassA <em>Metaclass A</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getNameB <em>Name B</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getMetaclassB <em>Metaclass B</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getWhens <em>Whens</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getWiths <em>Withs</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getMappingBase()
 * @model
 * @generated
 */
public interface MappingBase extends Mapping, With
{
  /**
   * Returns the value of the '<em><b>Name A</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name A</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name A</em>' attribute.
   * @see #setNameA(String)
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getMappingBase_NameA()
   * @model
   * @generated
   */
  String getNameA();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getNameA <em>Name A</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name A</em>' attribute.
   * @see #getNameA()
   * @generated
   */
  void setNameA(String value);

  /**
   * Returns the value of the '<em><b>Metaclass A</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Metaclass A</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Metaclass A</em>' reference.
   * @see #setMetaclassA(EClass)
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getMappingBase_MetaclassA()
   * @model
   * @generated
   */
  EClass getMetaclassA();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getMetaclassA <em>Metaclass A</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Metaclass A</em>' reference.
   * @see #getMetaclassA()
   * @generated
   */
  void setMetaclassA(EClass value);

  /**
   * Returns the value of the '<em><b>Name B</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name B</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name B</em>' attribute.
   * @see #setNameB(String)
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getMappingBase_NameB()
   * @model
   * @generated
   */
  String getNameB();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getNameB <em>Name B</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name B</em>' attribute.
   * @see #getNameB()
   * @generated
   */
  void setNameB(String value);

  /**
   * Returns the value of the '<em><b>Metaclass B</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Metaclass B</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Metaclass B</em>' reference.
   * @see #setMetaclassB(EClass)
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getMappingBase_MetaclassB()
   * @model
   * @generated
   */
  EClass getMetaclassB();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase#getMetaclassB <em>Metaclass B</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Metaclass B</em>' reference.
   * @see #getMetaclassB()
   * @generated
   */
  void setMetaclassB(EClass value);

  /**
   * Returns the value of the '<em><b>Whens</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.When}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Whens</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Whens</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getMappingBase_Whens()
   * @model containment="true"
   * @generated
   */
  EList<When> getWhens();

  /**
   * Returns the value of the '<em><b>Withs</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.With}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Withs</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Withs</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getMappingBase_Withs()
   * @model containment="true"
   * @generated
   */
  EList<With> getWiths();

} // MappingBase
