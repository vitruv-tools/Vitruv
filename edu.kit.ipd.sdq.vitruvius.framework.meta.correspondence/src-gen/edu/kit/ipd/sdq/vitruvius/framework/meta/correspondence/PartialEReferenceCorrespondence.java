/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Partial EReference Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueATUID <em>Value ATUID</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueBTUID <em>Value BTUID</em>}</li>
 * </ul>
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
	 * @see #setValueATUID(TUID)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getPartialEReferenceCorrespondence_ValueATUID()
	 * @model dataType="edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.TUID" required="true"
	 * @generated
	 */
	TUID getValueATUID();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueATUID <em>Value ATUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value ATUID</em>' attribute.
	 * @see #getValueATUID()
	 * @generated
	 */
	void setValueATUID(TUID value);

	/**
	 * Returns the value of the '<em><b>Value BTUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value BTUID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value BTUID</em>' attribute.
	 * @see #setValueBTUID(TUID)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getPartialEReferenceCorrespondence_ValueBTUID()
	 * @model dataType="edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.TUID" required="true"
	 * @generated
	 */
	TUID getValueBTUID();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.PartialEReferenceCorrespondence#getValueBTUID <em>Value BTUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value BTUID</em>' attribute.
	 * @see #getValueBTUID()
	 * @generated
	 */
	void setValueBTUID(TUID value);

} // PartialEReferenceCorrespondence
