/**
 */
package tools.vitruv.framework.change.echange.feature.reference;

import org.eclipse.emf.common.command.Command;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.EChange;

import tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Remove EReference</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.feature.reference.ReferencePackage#getRemoveEReference()
 * @model ABounds="tools.vitruv.framework.change.echange.feature.reference.EObj" TBounds="tools.vitruv.framework.change.echange.feature.reference.EObj"
 * @generated
 */
public interface RemoveEReference<A extends EObject, T extends EObject> extends RemoveFromListEChange<A, EReference, T>, SubtractiveReferenceEChange<A, T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((super.isResolved() && \n\t(!<%com.google.common.base.Objects%>.equal(this.getOldValue(), null))) && (!this.getOldValue().eIsProxy()));'"
	 * @generated
	 */
	boolean isResolved();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.feature.reference.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='T _oldValue = this.getOldValue();\nboolean _equals = <%com.google.common.base.Objects%>.equal(_oldValue, null);\nif (_equals)\n{\n\treturn null;\n}\nboolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\t<%tools.vitruv.framework.change.echange.EChange%> _resolve = super.resolve(resourceSet);\n\tfinal <%tools.vitruv.framework.change.echange.feature.reference.RemoveEReference%><A, T> resolvedChange = ((<%tools.vitruv.framework.change.echange.feature.reference.RemoveEReference%><A, T>) _resolve);\n\tboolean _equals_1 = <%com.google.common.base.Objects%>.equal(resolvedChange, null);\n\tif (_equals_1)\n\t{\n\t\treturn null;\n\t}\n\tT _oldValue_1 = this.getOldValue();\n\t<%org.eclipse.emf.ecore.EObject%> _resolve_1 = <%org.eclipse.emf.ecore.util.EcoreUtil%>.resolve(_oldValue_1, resourceSet);\n\tresolvedChange.setOldValue(((T) _resolve_1));\n\tT _oldValue_2 = resolvedChange.getOldValue();\n\tboolean _eIsProxy = _oldValue_2.eIsProxy();\n\tif (_eIsProxy)\n\t{\n\t\treturn this;\n\t}\n\treturn resolvedChange;\n}\nreturn this;'"
	 * @generated
	 */
	EChange resolve(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="tools.vitruv.framework.change.echange.feature.reference.Command" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.edit.provider.ComposedAdapterFactory%> _composedAdapterFactory = new <%org.eclipse.emf.edit.provider.ComposedAdapterFactory%>();\nfinal <%org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain%> editingDomain = new <%org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain%>(_composedAdapterFactory, null);\nA _affectedEObject = this.getAffectedEObject();\n<%org.eclipse.emf.ecore.EReference%> _affectedFeature = this.getAffectedFeature();\nT _oldValue = this.getOldValue();\nreturn <%org.eclipse.emf.edit.command.RemoveCommand%>.create(editingDomain, _affectedEObject, _affectedFeature, _oldValue);'"
	 * @generated
	 */
	Command getApplyCommand();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="tools.vitruv.framework.change.echange.feature.reference.Command" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.edit.provider.ComposedAdapterFactory%> _composedAdapterFactory = new <%org.eclipse.emf.edit.provider.ComposedAdapterFactory%>();\nfinal <%org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain%> editingDomain = new <%org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain%>(_composedAdapterFactory, null);\nA _affectedEObject = this.getAffectedEObject();\n<%org.eclipse.emf.ecore.EReference%> _affectedFeature = this.getAffectedFeature();\nT _oldValue = this.getOldValue();\nint _index = this.getIndex();\nreturn <%org.eclipse.emf.edit.command.AddCommand%>.create(editingDomain, _affectedEObject, _affectedFeature, _oldValue, _index);'"
	 * @generated
	 */
	Command getRevertCommand();

} // RemoveEReference
