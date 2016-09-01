/**
 */
package edu.kit.ipd.sdq.vitruvius.dsls.response.meta.correspondence.response;

import edu.kit.ipd.sdq.vitruvius.framework.correspondence.Correspondence;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.response.meta.correspondence.response.ResponseCorrespondence#getTag <em>Tag</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.dsls.response.meta.correspondence.response.ResponsePackage#getResponseCorrespondence()
 * @model
 * @generated
 */
public interface ResponseCorrespondence extends Correspondence {
	/**
	 * Returns the value of the '<em><b>Tag</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tag</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tag</em>' attribute.
	 * @see #setTag(String)
	 * @see edu.kit.ipd.sdq.vitruvius.dsls.response.meta.correspondence.response.ResponsePackage#getResponseCorrespondence_Tag()
	 * @model
	 * @generated
	 */
	String getTag();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.dsls.response.meta.correspondence.response.ResponseCorrespondence#getTag <em>Tag</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tag</em>' attribute.
	 * @see #getTag()
	 * @generated
	 */
	void setTag(String value);

} // ResponseCorrespondence
