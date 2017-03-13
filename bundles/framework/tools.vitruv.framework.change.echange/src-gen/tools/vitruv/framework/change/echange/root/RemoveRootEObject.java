/**
 */
package tools.vitruv.framework.change.echange.root;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.resource.ResourceSet;

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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((super.isResolved() && (!<%com.google.common.base.Objects%>.equal(this.getOldValue(), null))) && (!this.getOldValue().eIsProxy()));'"
	 * @generated
	 */
	boolean isResolved();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.root.ResourceSet" resourceSetUnique="false" resolveBeforeUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\tT resolvedOldValue = null;\n\tif (resolveBefore)\n\t{\n\t\tT _oldValue = this.getOldValue();\n\t\t<%org.eclipse.emf.ecore.EObject%> _resolveProxy = <%tools.vitruv.framework.change.echange.util.EChangeUtil%>.resolveProxy(_oldValue, resourceSet);\n\t\tresolvedOldValue = ((T) _resolveProxy);\n\t}\n\telse\n\t{\n\t\t<%org.eclipse.emf.ecore.resource.Resource%> stagingArea = <%tools.vitruv.framework.change.echange.util.StagingArea%>.getStagingArea(resourceSet);\n\t\t<%org.eclipse.emf.common.util.EList%><<%org.eclipse.emf.ecore.EObject%>> _contents = stagingArea.getContents();\n\t\tboolean _isEmpty = _contents.isEmpty();\n\t\tboolean _not_1 = (!_isEmpty);\n\t\tif (_not_1)\n\t\t{\n\t\t\t<%org.eclipse.emf.common.util.EList%><<%org.eclipse.emf.ecore.EObject%>> _contents_1 = stagingArea.getContents();\n\t\t\t<%org.eclipse.emf.ecore.EObject%> _get = _contents_1.get(0);\n\t\t\tresolvedOldValue = ((T) _get);\n\t\t}\n\t\telse\n\t\t{\n\t\t\treturn false;\n\t\t}\n\t}\n\tif (((<%com.google.common.base.Objects%>.equal(resolvedOldValue, null) || resolvedOldValue.eIsProxy()) || (!super.resolve(resourceSet, resolveBefore))))\n\t{\n\t\treturn false;\n\t}\n\tthis.setOldValue(resolvedOldValue);\n}\nreturn true;'"
	 * @generated
	 */
	boolean resolve(ResourceSet resourceSet, boolean resolveBefore);

} // RemoveRootEObject
