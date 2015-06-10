/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>File</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getGeneratedPackage <em>Generated Package</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getGeneratedClass <em>Generated Class</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getBundles <em>Bundles</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getImports <em>Imports</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getMappings <em>Mappings</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getMIRFile()
 * @model
 * @generated
 */
public interface MIRFile extends EObject
{
  /**
   * Returns the value of the '<em><b>Generated Package</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Generated Package</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Generated Package</em>' attribute.
   * @see #setGeneratedPackage(String)
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getMIRFile_GeneratedPackage()
   * @model
   * @generated
   */
  String getGeneratedPackage();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getGeneratedPackage <em>Generated Package</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Generated Package</em>' attribute.
   * @see #getGeneratedPackage()
   * @generated
   */
  void setGeneratedPackage(String value);

  /**
   * Returns the value of the '<em><b>Generated Class</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Generated Class</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Generated Class</em>' attribute.
   * @see #setGeneratedClass(String)
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getMIRFile_GeneratedClass()
   * @model
   * @generated
   */
  String getGeneratedClass();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile#getGeneratedClass <em>Generated Class</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Generated Class</em>' attribute.
   * @see #getGeneratedClass()
   * @generated
   */
  void setGeneratedClass(String value);

  /**
   * Returns the value of the '<em><b>Bundles</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Bundle}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Bundles</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Bundles</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getMIRFile_Bundles()
   * @model containment="true"
   * @generated
   */
  EList<Bundle> getBundles();

  /**
   * Returns the value of the '<em><b>Imports</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Import}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Imports</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Imports</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getMIRFile_Imports()
   * @model containment="true"
   * @generated
   */
  EList<Import> getImports();

  /**
   * Returns the value of the '<em><b>Mappings</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Mappings</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mappings</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getMIRFile_Mappings()
   * @model containment="true"
   * @generated
   */
  EList<Mapping> getMappings();

} // MIRFile
