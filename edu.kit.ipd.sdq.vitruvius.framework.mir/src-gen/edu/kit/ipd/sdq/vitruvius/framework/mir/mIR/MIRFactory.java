/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage
 * @generated
 */
public interface MIRFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  MIRFactory eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRFactoryImpl.init();

  /**
   * Returns a new object of class '<em>File</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>File</em>'.
   * @generated
   */
  MIRFile createMIRFile();

  /**
   * Returns a new object of class '<em>Import</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Import</em>'.
   * @generated
   */
  Import createImport();

  /**
   * Returns a new object of class '<em>Bundle</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Bundle</em>'.
   * @generated
   */
  Bundle createBundle();

  /**
   * Returns a new object of class '<em>Mapping Body</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Mapping Body</em>'.
   * @generated
   */
  MappingBody createMappingBody();

  /**
   * Returns a new object of class '<em>Mapping</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Mapping</em>'.
   * @generated
   */
  Mapping createMapping();

  /**
   * Returns a new object of class '<em>Typed Element</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Typed Element</em>'.
   * @generated
   */
  TypedElement createTypedElement();

  /**
   * Returns a new object of class '<em>Typed Element Ref</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Typed Element Ref</em>'.
   * @generated
   */
  TypedElementRef createTypedElementRef();

  /**
   * Returns a new object of class '<em>Class Mapping</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Class Mapping</em>'.
   * @generated
   */
  ClassMapping createClassMapping();

  /**
   * Returns a new object of class '<em>Feature Mapping</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Feature Mapping</em>'.
   * @generated
   */
  FeatureMapping createFeatureMapping();

  /**
   * Returns a new object of class '<em>Named EClass</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Named EClass</em>'.
   * @generated
   */
  NamedEClass createNamedEClass();

  /**
   * Returns a new object of class '<em>Feature Call</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Feature Call</em>'.
   * @generated
   */
  FeatureCall createFeatureCall();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  MIRPackage getMIRPackage();

} //MIRFactory
