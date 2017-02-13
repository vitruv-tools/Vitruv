/**
 */
package tools.vitruv.framework.change.echange.feature.attribute;

import org.eclipse.emf.common.command.Command;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

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
