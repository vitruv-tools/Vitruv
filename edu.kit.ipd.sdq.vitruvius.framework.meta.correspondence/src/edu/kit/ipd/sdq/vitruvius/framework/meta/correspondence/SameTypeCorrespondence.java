/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence;


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
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence#getElementATUID <em>Element ATUID</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence#getElementBTUID <em>Element BTUID</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getSameTypeCorrespondence()
 * @model abstract="true"
 * @generated
 */
public interface SameTypeCorrespondence extends Correspondence {
	/**
	 * Returns the value of the '<em><b>Element ATUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element ATUID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element ATUID</em>' attribute.
	 * @see #setElementATUID(String)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getSameTypeCorrespondence_ElementATUID()
	 * @model required="true"
	 * @generated
	 */
	String getElementATUID();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence#getElementATUID <em>Element ATUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element ATUID</em>' attribute.
	 * @see #getElementATUID()
	 * @generated
	 */
	void setElementATUID(String value);

	/**
	 * Returns the value of the '<em><b>Element BTUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element BTUID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element BTUID</em>' attribute.
	 * @see #setElementBTUID(String)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getSameTypeCorrespondence_ElementBTUID()
	 * @model required="true"
	 * @generated
	 */
	String getElementBTUID();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence#getElementBTUID <em>Element BTUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element BTUID</em>' attribute.
	 * @see #getElementBTUID()
	 * @generated
	 */
	void setElementBTUID(String value);

} // SameTypeCorrespondence
