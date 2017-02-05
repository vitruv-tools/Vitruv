/**
 */
package tools.vitruv.framework.change.echange.feature.attribute;

import org.eclipse.emf.common.command.Command;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.EChange;

import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Replace Single Valued EAttribute</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.feature.attribute.AttributePackage#getReplaceSingleValuedEAttribute()
 * @model ABounds="tools.vitruv.framework.change.echange.feature.attribute.EObj" TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface ReplaceSingleValuedEAttribute<A extends EObject, T extends Object> extends AdditiveAttributeEChange<A, T>, SubtractiveAttributeEChange<A, T>, ReplaceSingleValuedFeatureEChange<A, EAttribute, T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.feature.attribute.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if (((!this.isResolved()) && this.getAffectedEObject().eIsProxy()))\n{\n\tA _affectedEObject = this.getAffectedEObject();\n\tfinal <%org.eclipse.emf.ecore.EObject%> resolvedObject = <%org.eclipse.emf.ecore.util.EcoreUtil%>.resolve(_affectedEObject, resourceSet);\n\tboolean _eIsProxy = resolvedObject.eIsProxy();\n\tif (_eIsProxy)\n\t{\n\t\treturn null;\n\t}\n\t<%org.eclipse.emf.ecore.EAttribute%> _affectedFeature = this.getAffectedFeature();\n\tT _oldValue = this.getOldValue();\n\tT _newValue = this.getNewValue();\n\tfinal <%tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute%><<%org.eclipse.emf.ecore.EObject%>, T> resolvedChange = <%tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory%>.<<%org.eclipse.emf.ecore.EObject%>, T>createReplaceSingleAttributeChange(resolvedObject, _affectedFeature, _oldValue, _newValue, false);\n\tresolvedChange.setResolved(true);\n\treturn resolvedChange;\n}\nreturn this;'"
	 * @generated
	 */
	EChange resolve(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="tools.vitruv.framework.change.echange.feature.attribute.Command" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.edit.provider.ComposedAdapterFactory%> _composedAdapterFactory = new <%org.eclipse.emf.edit.provider.ComposedAdapterFactory%>();\nfinal <%org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain%> editingDomain = new <%org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain%>(_composedAdapterFactory, null);\nA _affectedEObject = this.getAffectedEObject();\n<%org.eclipse.emf.ecore.EAttribute%> _affectedFeature = this.getAffectedFeature();\nT _newValue = this.getNewValue();\nreturn <%org.eclipse.emf.edit.command.SetCommand%>.create(editingDomain, _affectedEObject, _affectedFeature, _newValue);'"
	 * @generated
	 */
	Command getApplyCommand();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="tools.vitruv.framework.change.echange.feature.attribute.Command" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.edit.provider.ComposedAdapterFactory%> _composedAdapterFactory = new <%org.eclipse.emf.edit.provider.ComposedAdapterFactory%>();\nfinal <%org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain%> editingDomain = new <%org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain%>(_composedAdapterFactory, null);\nA _affectedEObject = this.getAffectedEObject();\n<%org.eclipse.emf.ecore.EAttribute%> _affectedFeature = this.getAffectedFeature();\nT _oldValue = this.getOldValue();\nreturn <%org.eclipse.emf.edit.command.SetCommand%>.create(editingDomain, _affectedEObject, _affectedFeature, _oldValue);'"
	 * @generated
	 */
	Command getRevertCommand();

} // ReplaceSingleValuedEAttribute
