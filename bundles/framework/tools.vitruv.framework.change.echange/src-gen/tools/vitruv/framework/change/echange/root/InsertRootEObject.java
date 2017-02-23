/**
 */
package tools.vitruv.framework.change.echange.root;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.EChange;

import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Insert Root EObject</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.root.RootPackage#getInsertRootEObject()
 * @model TBounds="tools.vitruv.framework.change.echange.root.EObj"
 * @generated
 */
public interface InsertRootEObject<T extends EObject> extends RootEChange, EObjectAddedEChange<T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((!this.getNewValue().eIsProxy()) && (!<%com.google.common.base.Objects%>.equal(this.getResource(), null)));'"
	 * @generated
	 */
	boolean isResolved();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.root.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.resolve(resourceSet, null);'"
	 * @generated
	 */
	EChange resolve(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.root.ResourceSet" resourceSetUnique="false" newRootObjectUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\t<%tools.vitruv.framework.change.echange.EChange%> _resolve = super.resolve(resourceSet);\n\tfinal <%tools.vitruv.framework.change.echange.root.InsertRootEObject%><T> resolvedChange = ((<%tools.vitruv.framework.change.echange.root.InsertRootEObject%><T>) _resolve);\n\tboolean _equals = <%com.google.common.base.Objects%>.equal(resolvedChange, null);\n\tif (_equals)\n\t{\n\t\treturn null;\n\t}\n\tboolean _equals_1 = <%com.google.common.base.Objects%>.equal(newRootObject, null);\n\tif (_equals_1)\n\t{\n\t\tT _newValue = this.getNewValue();\n\t\t<%org.eclipse.emf.ecore.EObject%> _resolveProxy = <%tools.vitruv.framework.change.echange.util.EChangeUtil%>.resolveProxy(_newValue, resourceSet);\n\t\tresolvedChange.setNewValue(((T) _resolveProxy));\n\t}\n\telse\n\t{\n\t\tresolvedChange.setNewValue(newRootObject);\n\t}\n\t<%org.eclipse.emf.common.util.URI%> _uri = this.getUri();\n\t<%org.eclipse.emf.ecore.resource.Resource%> _resource = resourceSet.getResource(_uri, false);\n\tresolvedChange.setResource(_resource);\n\tif ((((!<%com.google.common.base.Objects%>.equal(resolvedChange.getNewValue(), null)) && (!resolvedChange.getNewValue().eIsProxy())) && (!<%com.google.common.base.Objects%>.equal(resolvedChange.getResource(), null))))\n\t{\n\t\treturn resolvedChange;\n\t}\n}\nreturn this;'"
	 * @generated
	 */
	EChange resolve(ResourceSet resourceSet, T newRootObject);

} // InsertRootEObject
