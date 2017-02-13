/**
 */
package tools.vitruv.framework.change.echange.feature.reference;

import org.eclipse.emf.common.command.Command;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.EChange;

import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Replace Single Valued EReference</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.feature.reference.ReferencePackage#getReplaceSingleValuedEReference()
 * @model ABounds="tools.vitruv.framework.change.echange.feature.reference.EObj" TBounds="tools.vitruv.framework.change.echange.feature.reference.EObj"
 * @generated
 */
public interface ReplaceSingleValuedEReference<A extends EObject, T extends EObject> extends AdditiveReferenceEChange<A, T>, SubtractiveReferenceEChange<A, T>, ReplaceSingleValuedFeatureEChange<A, EReference, T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((((super.isResolved() && \n\t(!<%com.google.common.base.Objects%>.equal(this.getOldValue(), null))) && (!this.getOldValue().eIsProxy())) && \n\t(!<%com.google.common.base.Objects%>.equal(this.getNewValue(), null))) && (!this.getNewValue().eIsProxy()));'"
	 * @generated
	 */
	boolean isResolved();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.feature.reference.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if ((<%com.google.common.base.Objects%>.equal(this.getOldValue(), null) || <%com.google.common.base.Objects%>.equal(this.getNewValue(), null)))\n{\n\treturn null;\n}\nboolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\t<%tools.vitruv.framework.change.echange.EChange%> _resolve = super.resolve(resourceSet);\n\tfinal <%tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference%><A, T> resolvedChange = ((<%tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference%><A, T>) _resolve);\n\tboolean _equals = <%com.google.common.base.Objects%>.equal(resolvedChange, null);\n\tif (_equals)\n\t{\n\t\treturn null;\n\t}\n\tT _oldValue = this.getOldValue();\n\t<%org.eclipse.emf.ecore.EObject%> _resolve_1 = <%org.eclipse.emf.ecore.util.EcoreUtil%>.resolve(_oldValue, resourceSet);\n\tresolvedChange.setOldValue(((T) _resolve_1));\n\tT _newValue = this.getNewValue();\n\t<%org.eclipse.emf.ecore.EObject%> _resolve_2 = <%org.eclipse.emf.ecore.util.EcoreUtil%>.resolve(_newValue, resourceSet);\n\tresolvedChange.setNewValue(((T) _resolve_2));\n\tif ((resolvedChange.getNewValue().eIsProxy() || resolvedChange.getOldValue().eIsProxy()))\n\t{\n\t\treturn this;\n\t}\n\treturn resolvedChange;\n}\nreturn this;'"
	 * @generated
	 */
	EChange resolve(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="tools.vitruv.framework.change.echange.feature.reference.Command" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.edit.provider.ComposedAdapterFactory%> _composedAdapterFactory = new <%org.eclipse.emf.edit.provider.ComposedAdapterFactory%>();\nfinal <%org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain%> editingDomain = new <%org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain%>(_composedAdapterFactory, null);\nA _affectedEObject = this.getAffectedEObject();\n<%org.eclipse.emf.ecore.EReference%> _affectedFeature = this.getAffectedFeature();\nT _newValue = this.getNewValue();\nreturn <%org.eclipse.emf.edit.command.SetCommand%>.create(editingDomain, _affectedEObject, _affectedFeature, _newValue);'"
	 * @generated
	 */
	Command getApplyCommand();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="tools.vitruv.framework.change.echange.feature.reference.Command" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.edit.provider.ComposedAdapterFactory%> _composedAdapterFactory = new <%org.eclipse.emf.edit.provider.ComposedAdapterFactory%>();\nfinal <%org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain%> editingDomain = new <%org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain%>(_composedAdapterFactory, null);\nA _affectedEObject = this.getAffectedEObject();\n<%org.eclipse.emf.ecore.EReference%> _affectedFeature = this.getAffectedFeature();\nT _oldValue = this.getOldValue();\nreturn <%org.eclipse.emf.edit.command.SetCommand%>.create(editingDomain, _affectedEObject, _affectedFeature, _oldValue);'"
	 * @generated
	 */
	Command getRevertCommand();

} // ReplaceSingleValuedEReference
