/**
 */
package tools.vitruv.framework.change.echange.eobject;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.resource.Resource;
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
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.eobject.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if (((!<%com.google.common.base.Objects%>.equal(this.getAffectedEObject(), null)) && (!this.isResolved())))\n{\n\t<%tools.vitruv.framework.change.echange.EChange%> _resolve = super.resolve(resourceSet);\n\tfinal <%tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange%><A> resolvedChange = ((<%tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange%><A>) _resolve);\n\tboolean _equals = <%com.google.common.base.Objects%>.equal(resolvedChange, null);\n\tif (_equals)\n\t{\n\t\treturn null;\n\t}\n\t<%org.eclipse.emf.ecore.resource.Resource%> _stagingArea = <%tools.vitruv.framework.change.echange.util.StagingArea%>.getStagingArea(resourceSet);\n\tresolvedChange.setStagingArea(_stagingArea);\n\t<%org.eclipse.emf.ecore.resource.Resource%> _stagingArea_1 = resolvedChange.getStagingArea();\n\t<%org.eclipse.emf.common.util.EList%><<%org.eclipse.emf.ecore.EObject%>> _contents = _stagingArea_1.getContents();\n\tboolean _isEmpty = _contents.isEmpty();\n\tboolean _not = (!_isEmpty);\n\tif (_not)\n\t{\n\t\t<%org.eclipse.emf.ecore.resource.Resource%> _stagingArea_2 = resolvedChange.getStagingArea();\n\t\t<%org.eclipse.emf.common.util.EList%><<%org.eclipse.emf.ecore.EObject%>> _contents_1 = _stagingArea_2.getContents();\n\t\t<%org.eclipse.emf.ecore.EObject%> _get = _contents_1.get(0);\n\t\tresolvedChange.setAffectedEObject(((A) _get));\n\t}\n\telse\n\t{\n\t\tA _affectedEObject = this.getAffectedEObject();\n\t\tA _copy = <%org.eclipse.emf.ecore.util.EcoreUtil%>.<A>copy(_affectedEObject);\n\t\tresolvedChange.setAffectedEObject(_copy);\n\t\tA _affectedEObject_1 = resolvedChange.getAffectedEObject();\n\t\t((<%org.eclipse.emf.ecore.InternalEObject%>) _affectedEObject_1).eSetProxyURI(null);\n\t}\n\tif ((((!<%com.google.common.base.Objects%>.equal(resolvedChange.getAffectedEObject(), null)) && (!resolvedChange.getAffectedEObject().eIsProxy())) && (!<%com.google.common.base.Objects%>.equal(resolvedChange.getStagingArea(), null))))\n\t{\n\t\treturn resolvedChange;\n\t}\n}\nreturn this;'"
	 * @generated
	 */
	EChange resolve(ResourceSet resourceSet);

} // EObjectExistenceEChange
