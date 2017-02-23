/**
 */
package tools.vitruv.framework.change.echange.eobject;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.AtomicEChange;
import tools.vitruv.framework.change.echange.EChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EObject Existence EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange#getAffectedEObject <em>Affected EObject</em>}</li>
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
	 * <p>
	 * If the meaning of the '<em>Affected EObject</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((!<%com.google.common.base.Objects%>.equal(this.getAffectedEObject(), null)) && (!this.getAffectedEObject().eIsProxy()));'"
	 * @generated
	 */
	boolean isResolved();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.eobject.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.resolve(resourceSet, null);'"
	 * @generated
	 */
	EChange resolve(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.eobject.ResourceSet" resourceSetUnique="false" resolvedObjectUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\t<%tools.vitruv.framework.change.echange.EChange%> _resolve = super.resolve(resourceSet);\n\tfinal <%tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange%><A> resolvedChange = ((<%tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange%><A>) _resolve);\n\tboolean _equals = <%com.google.common.base.Objects%>.equal(resolvedChange, null);\n\tif (_equals)\n\t{\n\t\treturn null;\n\t}\n\tA _affectedEObject = this.getAffectedEObject();\n\tboolean _equals_1 = <%com.google.common.base.Objects%>.equal(_affectedEObject, null);\n\tif (_equals_1)\n\t{\n\t\tA _affectedEObject_1 = this.getAffectedEObject();\n\t\t<%org.eclipse.emf.ecore.EObject%> _resolveProxy = <%tools.vitruv.framework.change.echange.util.EChangeUtil%>.resolveProxy(_affectedEObject_1, resourceSet);\n\t\tresolvedChange.setAffectedEObject(((A) _resolveProxy));\n\t}\n\telse\n\t{\n\t\tresolvedChange.setAffectedEObject(resolvedObject);\n\t}\n\tif (((!<%com.google.common.base.Objects%>.equal(resolvedChange.getAffectedEObject(), null)) && (!resolvedChange.getAffectedEObject().eIsProxy())))\n\t{\n\t\treturn resolvedChange;\n\t}\n}\nreturn this;'"
	 * @generated
	 */
	EChange resolve(ResourceSet resourceSet, A resolvedObject);

} // EObjectExistenceEChange
