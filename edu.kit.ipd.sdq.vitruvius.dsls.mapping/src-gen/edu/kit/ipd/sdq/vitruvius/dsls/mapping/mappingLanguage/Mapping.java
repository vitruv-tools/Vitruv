/**
 */
package edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping#isDefault <em>Default</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping#getSignatures <em>Signatures</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping#getRequires <em>Requires</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping#getConstraintBlocks <em>Constraint Blocks</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping#getConstraintsBody <em>Constraints Body</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping#getSubmappings <em>Submappings</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage#getMapping()
 * @model
 * @generated
 */
public interface Mapping extends EObject
{
  /**
   * Returns the value of the '<em><b>Default</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Default</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Default</em>' attribute.
   * @see #setDefault(boolean)
   * @see edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage#getMapping_Default()
   * @model
   * @generated
   */
  boolean isDefault();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping#isDefault <em>Default</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Default</em>' attribute.
   * @see #isDefault()
   * @generated
   */
  void setDefault(boolean value);

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
   * @see edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage#getMapping_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Signatures</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Signature}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Signatures</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Signatures</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage#getMapping_Signatures()
   * @model containment="true"
   * @generated
   */
  EList<Signature> getSignatures();

  /**
   * Returns the value of the '<em><b>Constraints</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.SignatureConstraintBlock}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Constraints</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Constraints</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage#getMapping_Constraints()
   * @model containment="true"
   * @generated
   */
  EList<SignatureConstraintBlock> getConstraints();

  /**
   * Returns the value of the '<em><b>Requires</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.RequiredMapping}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Requires</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Requires</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage#getMapping_Requires()
   * @model containment="true"
   * @generated
   */
  EList<RequiredMapping> getRequires();

  /**
   * Returns the value of the '<em><b>Constraint Blocks</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.SignatureConstraintBlock}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Constraint Blocks</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Constraint Blocks</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage#getMapping_ConstraintBlocks()
   * @model containment="true"
   * @generated
   */
  EList<SignatureConstraintBlock> getConstraintBlocks();

  /**
   * Returns the value of the '<em><b>Constraints Body</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Constraints Body</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Constraints Body</em>' containment reference.
   * @see #setConstraintsBody(BodyConstraintBlock)
   * @see edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage#getMapping_ConstraintsBody()
   * @model containment="true"
   * @generated
   */
  BodyConstraintBlock getConstraintsBody();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping#getConstraintsBody <em>Constraints Body</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Constraints Body</em>' containment reference.
   * @see #getConstraintsBody()
   * @generated
   */
  void setConstraintsBody(BodyConstraintBlock value);

  /**
   * Returns the value of the '<em><b>Submappings</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Submappings</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Submappings</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage#getMapping_Submappings()
   * @model containment="true"
   * @generated
   */
  EList<Mapping> getSubmappings();

} // Mapping
