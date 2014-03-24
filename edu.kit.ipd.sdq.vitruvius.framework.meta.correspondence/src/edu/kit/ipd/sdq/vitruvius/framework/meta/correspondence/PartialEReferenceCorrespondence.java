/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Partial EReference Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueA <em>Value A</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueB <em>Value B</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getPartialEReferenceCorrespondence()
 * @model
 * @generated
 */
public interface PartialEReferenceCorrespondence<TValue extends EObject> extends PartialEFeatureCorrespondence<TValue>, EReferenceCorrespondence {
	/**
     * Returns the value of the '<em><b>Value A</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value A</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Value A</em>' reference.
     * @see #setValueA(EObject)
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getPartialEReferenceCorrespondence_ValueA()
     * @model required="true"
     * @generated
     */
	TValue getValueA();

	/**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueA <em>Value A</em>}' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Value A</em>' reference.
     * @see #getValueA()
     * @generated
     */
	void setValueA(TValue value);

	/**
     * Returns the value of the '<em><b>Value B</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value B</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Value B</em>' reference.
     * @see #setValueB(EObject)
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getPartialEReferenceCorrespondence_ValueB()
     * @model required="true"
     * @generated
     */
	TValue getValueB();

	/**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueB <em>Value B</em>}' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Value B</em>' reference.
     * @see #getValueB()
     * @generated
     */
	void setValueB(TValue value);

} // PartialEReferenceCorrespondence
