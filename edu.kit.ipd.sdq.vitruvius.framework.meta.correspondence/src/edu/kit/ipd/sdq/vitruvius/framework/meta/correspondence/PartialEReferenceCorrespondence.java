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
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueATUID <em>Value ATUID</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueBTUID <em>Value BTUID</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getPartialEReferenceCorrespondence()
 * @model
 * @generated
 */
public interface PartialEReferenceCorrespondence<TValue extends EObject> extends PartialEFeatureCorrespondence<TValue>, EReferenceCorrespondence {
	/**
	 * Returns the value of the '<em><b>Value ATUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value ATUID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value ATUID</em>' attribute.
	 * @see #setValueATUID(String)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getPartialEReferenceCorrespondence_ValueATUID()
	 * @model required="true"
	 * @generated
	 */
	String getValueATUID();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueATUID <em>Value ATUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value ATUID</em>' attribute.
	 * @see #getValueATUID()
	 * @generated
	 */
	void setValueATUID(String value);

	/**
	 * Returns the value of the '<em><b>Value BTUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value BTUID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value BTUID</em>' attribute.
	 * @see #setValueBTUID(String)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getPartialEReferenceCorrespondence_ValueBTUID()
	 * @model required="true"
	 * @generated
	 */
	String getValueBTUID();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueBTUID <em>Value BTUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value BTUID</em>' attribute.
	 * @see #getValueBTUID()
	 * @generated
	 */
	void setValueBTUID(String value);

} // PartialEReferenceCorrespondence
