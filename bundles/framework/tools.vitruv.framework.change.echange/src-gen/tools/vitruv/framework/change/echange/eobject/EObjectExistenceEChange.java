/**
 */
package tools.vitruv.framework.change.echange.eobject;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.AtomicEChange;

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
 *   <li>{@link tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange#getStagingArea <em>Staging Area</em>}</li>
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
	 * Returns the value of the '<em><b>Staging Area</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Staging Area</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Staging Area</em>' attribute.
	 * @see #setStagingArea(Resource)
	 * @see tools.vitruv.framework.change.echange.eobject.EobjectPackage#getEObjectExistenceEChange_StagingArea()
	 * @model unique="false" dataType="tools.vitruv.framework.change.echange.eobject.Resource"
	 * @generated
	 */
	Resource getStagingArea();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange#getStagingArea <em>Staging Area</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Staging Area</em>' attribute.
	 * @see #getStagingArea()
	 * @generated
	 */
	void setStagingArea(Resource value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return (((super.isResolved() && (!<%com.google.common.base.Objects%>.equal(this.getAffectedEObject(), null))) && (!this.getAffectedEObject().eIsProxy())) && (!<%com.google.common.base.Objects%>.equal(this.getStagingArea(), null)));'"
	 * @generated
	 */
	boolean isResolved();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.eobject.ResourceSet" resourceSetUnique="false" newObjectUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\tA _affectedEObject = this.getAffectedEObject();\n\tboolean _equals = <%com.google.common.base.Objects%>.equal(_affectedEObject, null);\n\tif (_equals)\n\t{\n\t\treturn false;\n\t}\n\tfinal <%org.eclipse.emf.ecore.resource.Resource%> resolvedStagingArea = <%tools.vitruv.framework.change.echange.util.StagingArea%>.getStagingArea(resourceSet);\n\tA resolvedAffectedEObject = null;\n\tif (newObject)\n\t{\n\t\tA _affectedEObject_1 = this.getAffectedEObject();\n\t\tA _copy = <%org.eclipse.emf.ecore.util.EcoreUtil%>.<A>copy(_affectedEObject_1);\n\t\tresolvedAffectedEObject = _copy;\n\t\t((<%org.eclipse.emf.ecore.InternalEObject%>) resolvedAffectedEObject).eSetProxyURI(null);\n\t}\n\telse\n\t{\n\t\t<%org.eclipse.emf.common.util.EList%><<%org.eclipse.emf.ecore.EObject%>> _contents = resolvedStagingArea.getContents();\n\t\t<%org.eclipse.emf.ecore.EObject%> _get = _contents.get(0);\n\t\tresolvedAffectedEObject = ((A) _get);\n\t}\n\tif (((<%com.google.common.base.Objects%>.equal(resolvedAffectedEObject, null) || resolvedAffectedEObject.eIsProxy()) || (<%com.google.common.base.Objects%>.equal(resolvedStagingArea, null) && (!super.resolveBefore(resourceSet)))))\n\t{\n\t\treturn false;\n\t}\n\tthis.setAffectedEObject(resolvedAffectedEObject);\n\tthis.setStagingArea(resolvedStagingArea);\n}\nreturn true;'"
	 * @generated
	 */
	boolean resolve(ResourceSet resourceSet, boolean newObject);

} // EObjectExistenceEChange
