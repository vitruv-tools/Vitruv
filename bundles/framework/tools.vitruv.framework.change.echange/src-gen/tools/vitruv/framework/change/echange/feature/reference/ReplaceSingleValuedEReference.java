/**
 */
package tools.vitruv.framework.change.echange.feature.reference;

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
public interface ReplaceSingleValuedEReference<A extends EObject, T extends EObject> extends ReplaceSingleValuedFeatureEChange<A, EReference, T>, AdditiveReferenceEChange<A, T>, SubtractiveReferenceEChange<A, T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((super.isResolved() && (<%com.google.common.base.Objects%>.equal(this.getOldValue(), null) || (!this.getOldValue().eIsProxy()))) && (<%com.google.common.base.Objects%>.equal(this.getNewValue(), null) || (!this.getNewValue().eIsProxy())));'"
	 * @generated
	 */
	boolean isResolved();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.feature.reference.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.resolve(resourceSet, true);'"
	 * @generated
	 */
	EChange resolveApply(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.feature.reference.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.resolve(resourceSet, false);'"
	 * @generated
	 */
	EChange resolveRevert(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.feature.reference.ResourceSet" resourceSetUnique="false" applyChangeUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\t<%tools.vitruv.framework.change.echange.EChange%> _resolveApply = super.resolveApply(resourceSet);\n\tfinal <%tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference%><A, T> resolvedChange = ((<%tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference%><A, T>) _resolveApply);\n\tboolean _equals = <%com.google.common.base.Objects%>.equal(resolvedChange, null);\n\tif (_equals)\n\t{\n\t\treturn null;\n\t}\n\tboolean _isContainment = this.isContainment();\n\tif (_isContainment)\n\t{\n\t\tif (applyChange)\n\t\t{\n\t\t\tresolvedChange.setNewValue(((T) <%tools.vitruv.framework.change.echange.util.EChangeUtil%>.objectInProgress));\n\t\t\tT _oldValue = this.getOldValue();\n\t\t\t<%org.eclipse.emf.ecore.EObject%> _resolveProxy = <%tools.vitruv.framework.change.echange.util.EChangeUtil%>.resolveProxy(_oldValue, resourceSet);\n\t\t\tresolvedChange.setOldValue(((T) _resolveProxy));\n\t\t}\n\t\telse\n\t\t{\n\t\t\tT _newValue = this.getNewValue();\n\t\t\t<%org.eclipse.emf.ecore.EObject%> _resolveProxy_1 = <%tools.vitruv.framework.change.echange.util.EChangeUtil%>.resolveProxy(_newValue, resourceSet);\n\t\t\tresolvedChange.setNewValue(((T) _resolveProxy_1));\n\t\t\tresolvedChange.setOldValue(((T) <%tools.vitruv.framework.change.echange.util.EChangeUtil%>.objectInProgress));\n\t\t}\n\t}\n\telse\n\t{\n\t\tT _oldValue_1 = this.getOldValue();\n\t\t<%org.eclipse.emf.ecore.EObject%> _resolveProxy_2 = <%tools.vitruv.framework.change.echange.util.EChangeUtil%>.resolveProxy(_oldValue_1, resourceSet);\n\t\tresolvedChange.setOldValue(((T) _resolveProxy_2));\n\t\tT _newValue_1 = this.getNewValue();\n\t\t<%org.eclipse.emf.ecore.EObject%> _resolveProxy_3 = <%tools.vitruv.framework.change.echange.util.EChangeUtil%>.resolveProxy(_newValue_1, resourceSet);\n\t\tresolvedChange.setNewValue(((T) _resolveProxy_3));\n\t}\n\tif (((<%com.google.common.base.Objects%>.equal(resolvedChange.getNewValue(), null) || (!resolvedChange.getNewValue().eIsProxy())) && (<%com.google.common.base.Objects%>.equal(resolvedChange.getOldValue(), null) || (!resolvedChange.getOldValue().eIsProxy()))))\n\t{\n\t\tboolean _isContainment_1 = this.isContainment();\n\t\tif (_isContainment_1)\n\t\t{\n\t\t\tif (applyChange)\n\t\t\t{\n\t\t\t\tT _oldValue_2 = resolvedChange.getOldValue();\n\t\t\t\t<%tools.vitruv.framework.change.echange.util.EChangeUtil%>.objectInProgress = _oldValue_2;\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tT _newValue_2 = resolvedChange.getNewValue();\n\t\t\t\t<%tools.vitruv.framework.change.echange.util.EChangeUtil%>.objectInProgress = _newValue_2;\n\t\t\t}\n\t\t}\n\t\treturn resolvedChange;\n\t}\n}\nreturn this;'"
	 * @generated
	 */
	EChange resolve(ResourceSet resourceSet, boolean applyChange);

} // ReplaceSingleValuedEReference
