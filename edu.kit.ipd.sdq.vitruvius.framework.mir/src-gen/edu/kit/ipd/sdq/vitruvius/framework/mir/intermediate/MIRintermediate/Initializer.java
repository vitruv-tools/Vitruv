/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Initializer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Initializer#getCallStatement <em>Call Statement</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage#getInitializer()
 * @model
 * @generated
 */
public interface Initializer extends EObject {
	/**
	 * Returns the value of the '<em><b>Call Statement</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Call Statement</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Call Statement</em>' attribute.
	 * @see #setCallStatement(String)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage#getInitializer_CallStatement()
	 * @model default=""
	 * @generated
	 */
	String getCallStatement();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Initializer#getCallStatement <em>Call Statement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Call Statement</em>' attribute.
	 * @see #getCallStatement()
	 * @generated
	 */
	void setCallStatement(String value);

} // Initializer
