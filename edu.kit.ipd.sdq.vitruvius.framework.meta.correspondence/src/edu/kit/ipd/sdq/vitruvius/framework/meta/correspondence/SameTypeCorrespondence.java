/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Same Type Correspondence</b></em>'.
 * <!-- end-user-doc -->
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
 * @model abstract="true" TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface SameTypeCorrespondence<T extends Object> extends Correspondence {
	/**
	 * Returns the value of the '<em><b>Element A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element A</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element A</em>' reference.
	 * @see #setElementA(Object)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getSameTypeCorrespondence_ElementA()
	 * @model kind="reference" required="true"
	 * @generated
	 */
	T getElementA();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence#getElementA <em>Element A</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element A</em>' reference.
	 * @see #getElementA()
	 * @generated
	 */
	void setElementA(T value);

	/**
	 * Returns the value of the '<em><b>Element B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element B</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element B</em>' reference.
	 * @see #setElementB(Object)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getSameTypeCorrespondence_ElementB()
	 * @model kind="reference" required="true"
	 * @generated
	 */
	T getElementB();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence#getElementB <em>Element B</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element B</em>' reference.
	 * @see #getElementB()
	 * @generated
	 */
	void setElementB(T value);

} // SameTypeCorrespondence
