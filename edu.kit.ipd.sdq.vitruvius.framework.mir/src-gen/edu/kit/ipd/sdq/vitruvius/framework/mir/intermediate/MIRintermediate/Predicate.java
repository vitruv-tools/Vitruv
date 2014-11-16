/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Predicate</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Predicate#getCheckStatement <em>Check Statement</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage#getPredicate()
 * @model
 * @generated
 */
public interface Predicate extends EObject {
	/**
	 * Returns the value of the '<em><b>Check Statement</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Check Statement</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Check Statement</em>' attribute.
	 * @see #setCheckStatement(String)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage#getPredicate_CheckStatement()
	 * @model default=""
	 * @generated
	 */
	String getCheckStatement();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Predicate#getCheckStatement <em>Check Statement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Check Statement</em>' attribute.
	 * @see #getCheckStatement()
	 * @generated
	 */
	void setCheckStatement(String value);

} // Predicate
