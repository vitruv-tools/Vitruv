/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Partial EAttribute Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEAttributeCorrespondence#getValueA <em>Value A</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEAttributeCorrespondence#getValueB <em>Value B</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getPartialEAttributeCorrespondence()
 * @model TValueBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface PartialEAttributeCorrespondence<TValue extends Object> extends PartialEFeatureCorrespondence<TValue>, EAttributeCorrespondence {
	/**
	 * Returns the value of the '<em><b>Value A</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value A</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value A</em>' attribute.
	 * @see #setValueA(Object)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getPartialEAttributeCorrespondence_ValueA()
	 * @model required="true"
	 * @generated
	 */
	TValue getValueA();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEAttributeCorrespondence#getValueA <em>Value A</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value A</em>' attribute.
	 * @see #getValueA()
	 * @generated
	 */
	void setValueA(TValue value);

	/**
	 * Returns the value of the '<em><b>Value B</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value B</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value B</em>' attribute.
	 * @see #setValueB(Object)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getPartialEAttributeCorrespondence_ValueB()
	 * @model required="true"
	 * @generated
	 */
	TValue getValueB();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEAttributeCorrespondence#getValueB <em>Value B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value B</em>' attribute.
	 * @see #getValueB()
	 * @generated
	 */
	void setValueB(TValue value);

} // PartialEAttributeCorrespondence
