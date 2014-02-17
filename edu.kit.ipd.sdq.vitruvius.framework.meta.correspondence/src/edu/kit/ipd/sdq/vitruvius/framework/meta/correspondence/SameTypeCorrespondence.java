/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Same Type Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Metaclass for cases where an Object corresponds to another Object of the same metatype (i.e. both objects are either complex-typed EObjects or simple-typed EJavaObjects).
 * ElementA and elementB usually point to instances of different metaclasses but their common type T is not a problem as T will never be used more strictly than for the metatype.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence#getElementA <em>Element A</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence#getElementB <em>Element B</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getSameTypeCorrespondence()
 * @model abstract="true"
 * @generated
 */
public interface SameTypeCorrespondence extends Correspondence {
	/**
	 * Returns the value of the '<em><b>Element A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element A</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element A</em>' reference.
	 * @see #setElementA(EObject)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getSameTypeCorrespondence_ElementA()
	 * @model required="true"
	 * @generated
	 */
	EObject getElementA();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence#getElementA <em>Element A</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element A</em>' reference.
	 * @see #getElementA()
	 * @generated
	 */
	void setElementA(EObject value);

	/**
	 * Returns the value of the '<em><b>Element B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element B</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element B</em>' reference.
	 * @see #setElementB(EObject)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getSameTypeCorrespondence_ElementB()
	 * @model required="true"
	 * @generated
	 */
	EObject getElementB();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence#getElementB <em>Element B</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element B</em>' reference.
	 * @see #getElementB()
	 * @generated
	 */
	void setElementB(EObject value);

} // SameTypeCorrespondence
