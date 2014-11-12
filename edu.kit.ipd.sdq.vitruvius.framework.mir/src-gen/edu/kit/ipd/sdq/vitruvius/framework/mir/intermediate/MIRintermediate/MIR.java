/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

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
	 * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Mappings</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class Mappings</em>' containment reference list.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage#getMIR_ClassMappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<ClassMapping> getClassMappings();

	/**
	 * Returns the value of the '<em><b>Feature Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature Mappings</em>' reference list isn't clear,
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

} // MIR
