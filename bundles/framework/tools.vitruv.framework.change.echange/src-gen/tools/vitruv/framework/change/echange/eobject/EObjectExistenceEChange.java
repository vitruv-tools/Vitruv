/**
 */
package tools.vitruv.framework.change.echange.eobject;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.AtomicEChange;

import tools.vitruv.framework.change.echange.resolve.StagingArea;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EObject Existence EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Abstract EChange which creates or deletes an EObject.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange#getAffectedEObject <em>Affected EObject</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange#getStagingArea <em>Staging Area</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange#getIdAttributeValue <em>Id Attribute Value</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange#getAffectedEObjectID <em>Affected EObject ID</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.eobject.EobjectPackage#getEObjectExistenceEChange()
 * @model abstract="true" ABounds="tools.vitruv.framework.change.echange.eobject.EObj"
 * @generated
 */
public interface EObjectExistenceEChange<A extends EObject> extends AtomicEChange {
	/**
	 * Returns the value of the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The created or deleted EObject.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Affected EObject</em>' reference.
	 * @see #setAffectedEObject(EObject)
	 * @see tools.vitruv.framework.change.echange.eobject.EobjectPackage#getEObjectExistenceEChange_AffectedEObject()
	 * @model kind="reference" required="true"
	 * @generated
	 */
	A getAffectedEObject();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange#getAffectedEObject <em>Affected EObject</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Affected EObject</em>' reference.
	 * @see #getAffectedEObject()
	 * @generated
	 */
	void setAffectedEObject(A value);

	/**
	 * Returns the value of the '<em><b>Staging Area</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The staging area where the created object will be placed or the deleted
	 * object be removed from.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Staging Area</em>' attribute.
	 * @see #setStagingArea(StagingArea)
	 * @see tools.vitruv.framework.change.echange.eobject.EobjectPackage#getEObjectExistenceEChange_StagingArea()
	 * @model unique="false" dataType="tools.vitruv.framework.change.echange.eobject.StagingArea"
	 * @generated
	 */
	StagingArea getStagingArea();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange#getStagingArea <em>Staging Area</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Staging Area</em>' attribute.
	 * @see #getStagingArea()
	 * @generated
	 */
	void setStagingArea(StagingArea value);

	/**
	 * Returns the value of the '<em><b>Id Attribute Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id Attribute Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id Attribute Value</em>' attribute.
	 * @see #setIdAttributeValue(String)
	 * @see tools.vitruv.framework.change.echange.eobject.EobjectPackage#getEObjectExistenceEChange_IdAttributeValue()
	 * @model
	 * @generated
	 */
	String getIdAttributeValue();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange#getIdAttributeValue <em>Id Attribute Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Attribute Value</em>' attribute.
	 * @see #getIdAttributeValue()
	 * @generated
	 */
	void setIdAttributeValue(String value);

	/**
	 * Returns the value of the '<em><b>Affected EObject ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Affected EObject ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Affected EObject ID</em>' attribute.
	 * @see #setAffectedEObjectID(String)
	 * @see tools.vitruv.framework.change.echange.eobject.EobjectPackage#getEObjectExistenceEChange_AffectedEObjectID()
	 * @model dataType="tools.vitruv.framework.change.uuid.Uuid"
	 * @generated
	 */
	String getAffectedEObjectID();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange#getAffectedEObjectID <em>Affected EObject ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Affected EObject ID</em>' attribute.
	 * @see #getAffectedEObjectID()
	 * @generated
	 */
	void setAffectedEObjectID(String value);

} // EObjectExistenceEChange
