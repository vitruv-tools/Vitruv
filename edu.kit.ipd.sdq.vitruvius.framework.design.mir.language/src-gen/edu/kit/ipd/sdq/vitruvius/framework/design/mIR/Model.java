/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.design.mIR;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.xtext.xtype.XImportSection;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model#getMmImports <em>Mm Imports</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model#getJavaImportSection <em>Java Import Section</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model#getMappings <em>Mappings</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model#getInvariants <em>Invariants</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model#getGlobalVariables <em>Global Variables</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model#getRespones <em>Respones</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getModel()
 * @model
 * @generated
 */
public interface Model extends EObject
{
  /**
   * Returns the value of the '<em><b>Mm Imports</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Import}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Mm Imports</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mm Imports</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getModel_MmImports()
   * @model containment="true"
   * @generated
   */
  EList<Import> getMmImports();

  /**
   * Returns the value of the '<em><b>Java Import Section</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Java Import Section</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Java Import Section</em>' containment reference.
   * @see #setJavaImportSection(XImportSection)
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getModel_JavaImportSection()
   * @model containment="true"
   * @generated
   */
  XImportSection getJavaImportSection();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model#getJavaImportSection <em>Java Import Section</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Java Import Section</em>' containment reference.
   * @see #getJavaImportSection()
   * @generated
   */
  void setJavaImportSection(XImportSection value);

  /**
   * Returns the value of the '<em><b>Mappings</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Mapping}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Mappings</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mappings</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getModel_Mappings()
   * @model containment="true"
   * @generated
   */
  EList<Mapping> getMappings();

  /**
   * Returns the value of the '<em><b>Invariants</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Invariant}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Invariants</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Invariants</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getModel_Invariants()
   * @model containment="true"
   * @generated
   */
  EList<Invariant> getInvariants();

  /**
   * Returns the value of the '<em><b>Global Variables</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.GlobalVariable}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Global Variables</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Global Variables</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getModel_GlobalVariables()
   * @model containment="true"
   * @generated
   */
  EList<GlobalVariable> getGlobalVariables();

  /**
   * Returns the value of the '<em><b>Respones</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Response}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Respones</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Respones</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getModel_Respones()
   * @model containment="true"
   * @generated
   */
  EList<Response> getRespones();

} // Model
