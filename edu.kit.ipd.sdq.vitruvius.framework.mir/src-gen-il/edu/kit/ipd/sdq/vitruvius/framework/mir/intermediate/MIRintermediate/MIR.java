/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>MIR</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR#getClassMappings <em>Class Mappings</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR#getFeatureMappings <em>Feature Mappings</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR#getPredicates <em>Predicates</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR#getConfiguration <em>Configuration</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR#getPackages <em>Packages</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage#getMIR()
 * @model
 * @generated
 */
public interface MIR extends EObject {
	/**
	 * Returns the value of the '<em><b>Class Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassifierMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class Mappings</em>' containment reference list.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage#getMIR_ClassMappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<ClassifierMapping> getClassMappings();

	/**
	 * Returns the value of the '<em><b>Feature Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Mappings</em>' containment reference list.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage#getMIR_FeatureMappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<FeatureMapping> getFeatureMappings();

	/**
	 * Returns the value of the '<em><b>Predicates</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Predicate}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Predicates</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Predicates</em>' containment reference list.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage#getMIR_Predicates()
	 * @model containment="true"
	 * @generated
	 */
	EList<Predicate> getPredicates();

	/**
	 * Returns the value of the '<em><b>Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Configuration</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Configuration</em>' containment reference.
	 * @see #setConfiguration(Configuration)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage#getMIR_Configuration()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Configuration getConfiguration();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR#getConfiguration <em>Configuration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Configuration</em>' containment reference.
	 * @see #getConfiguration()
	 * @generated
	 */
	void setConfiguration(Configuration value);

	/**
	 * Returns the value of the '<em><b>Packages</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EPackage}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Packages</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Packages</em>' reference list.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage#getMIR_Packages()
	 * @model lower="2" upper="2"
	 * @generated
	 */
	EList<EPackage> getPackages();

} // MIR
