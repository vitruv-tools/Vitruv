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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return (super.isResolved() && (!this.getNewValue().eIsProxy()));'"
	 * @generated
	 */
	boolean isResolved();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.root.ResourceSet" resourceSetUnique="false" applyChangeUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\t<%tools.vitruv.framework.change.echange.EChange%> _resolve = super.resolve(resourceSet, applyChange);\n\tfinal <%tools.vitruv.framework.change.echange.root.InsertRootEObject%><T> resolvedChange = ((<%tools.vitruv.framework.change.echange.root.InsertRootEObject%><T>) _resolve);\n\tboolean _equals = <%com.google.common.base.Objects%>.equal(resolvedChange, null);\n\tif (_equals)\n\t{\n\t\treturn null;\n\t}\n\tif (applyChange)\n\t{\n\t\tresolvedChange.setNewValue(((T) <%tools.vitruv.framework.change.echange.util.EChangeUtil%>.objectInProgress));\n\t}\n\telse\n\t{\n\t\tT _newValue = this.getNewValue();\n\t\t<%org.eclipse.emf.ecore.EObject%> _resolveProxy = <%tools.vitruv.framework.change.echange.util.EChangeUtil%>.resolveProxy(_newValue, resourceSet);\n\t\tresolvedChange.setNewValue(((T) _resolveProxy));\n\t}\n\tif (((!<%com.google.common.base.Objects%>.equal(resolvedChange.getNewValue(), null)) && (!resolvedChange.getNewValue().eIsProxy())))\n\t{\n\t\tif (applyChange)\n\t\t{\n\t\t\t<%tools.vitruv.framework.change.echange.util.EChangeUtil%>.objectInProgress = null;\n\t\t}\n\t\telse\n\t\t{\n\t\t\tT _newValue_1 = resolvedChange.getNewValue();\n\t\t\t<%tools.vitruv.framework.change.echange.util.EChangeUtil%>.objectInProgress = _newValue_1;\n\t\t}\n\t\treturn resolvedChange;\n\t}\n}\nreturn this;'"
	 * @generated
	 */
	EChange resolve(ResourceSet resourceSet, boolean applyChange);

} // InsertRootEObject
