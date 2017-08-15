/**
 */
package tools.vitruv.framework.change.echange.eobject;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.AdditiveEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EObject Added EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Abstract EChange which inserts an EObject into a resource or reference.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange#getNewValue <em>New Value</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange#getNewValueID <em>New Value ID</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.eobject.EobjectPackage#getEObjectAddedEChange()
 * @model abstract="true" TBounds="tools.vitruv.framework.change.echange.eobject.EObj"
 * @generated
 */
public interface EObjectAddedEChange<T extends EObject> extends AdditiveEChange<T> {
	/**
	 * Returns the value of the '<em><b>New Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>New Value</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New Value</em>' reference.
	 * @see #setNewValue(EObject)
	 * @see tools.vitruv.framework.change.echange.eobject.EobjectPackage#getEObjectAddedEChange_NewValue()
	 * @model kind="reference" required="true"
	 * @generated
	 */
	T getNewValue();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange#getNewValue <em>New Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New Value</em>' reference.
	 * @see #getNewValue()
	 * @generated
	 */
	void setNewValue(T value);

	/**
	 * Returns the value of the '<em><b>New Value ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>New Value ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New Value ID</em>' attribute.
	 * @see #setNewValueID(String)
	 * @see tools.vitruv.framework.change.echange.eobject.EobjectPackage#getEObjectAddedEChange_NewValueID()
	 * @model dataType="tools.vitruv.framework.change.uuid.Uuid"
	 * @generated
	 */
	String getNewValueID();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange#getNewValueID <em>New Value ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New Value ID</em>' attribute.
	 * @see #getNewValueID()
	 * @generated
	 */
	void setNewValueID(String value);

} // EObjectAddedEChange
