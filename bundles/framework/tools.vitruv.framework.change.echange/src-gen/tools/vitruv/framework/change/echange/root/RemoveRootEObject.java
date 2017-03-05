/**
 */
package tools.vitruv.framework.change.echange.root;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.EChange;

import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Remove Root EObject</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.root.RootPackage#getRemoveRootEObject()
 * @model TBounds="tools.vitruv.framework.change.echange.root.EObj"
 * @generated
 */
public interface RemoveRootEObject<T extends EObject> extends RootEChange, EObjectSubtractedEChange<T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return (super.isResolved() && (!this.getOldValue().eIsProxy()));'"
	 * @generated
	 */
	boolean isResolved();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.root.ResourceSet" resourceSetUnique="false" applyChangeUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\t<%tools.vitruv.framework.change.echange.EChange%> _resolve = super.resolve(resourceSet, applyChange);\n\tfinal <%tools.vitruv.framework.change.echange.root.RemoveRootEObject%><T> resolvedChange = ((<%tools.vitruv.framework.change.echange.root.RemoveRootEObject%><T>) _resolve);\n\tboolean _equals = <%com.google.common.base.Objects%>.equal(resolvedChange, null);\n\tif (_equals)\n\t{\n\t\treturn null;\n\t}\n\tif (applyChange)\n\t{\n\t\tT _oldValue = this.getOldValue();\n\t\t<%org.eclipse.emf.ecore.EObject%> _resolveProxy = <%tools.vitruv.framework.change.echange.util.EChangeUtil%>.resolveProxy(_oldValue, resourceSet);\n\t\tresolvedChange.setOldValue(((T) _resolveProxy));\n\t}\n\telse\n\t{\n\t\tresolvedChange.setOldValue(((T) <%tools.vitruv.framework.change.echange.util.EChangeUtil%>.objectInProgress));\n\t}\n\tif (((!<%com.google.common.base.Objects%>.equal(resolvedChange.getOldValue(), null)) && (!resolvedChange.getOldValue().eIsProxy())))\n\t{\n\t\tif (applyChange)\n\t\t{\n\t\t\tT _oldValue_1 = resolvedChange.getOldValue();\n\t\t\t<%tools.vitruv.framework.change.echange.util.EChangeUtil%>.objectInProgress = _oldValue_1;\n\t\t}\n\t\telse\n\t\t{\n\t\t\t<%tools.vitruv.framework.change.echange.util.EChangeUtil%>.objectInProgress = null;\n\t\t}\n\t\treturn resolvedChange;\n\t}\n}\nreturn this;'"
	 * @generated
	 */
	EChange resolve(ResourceSet resourceSet, boolean applyChange);

} // RemoveRootEObject
