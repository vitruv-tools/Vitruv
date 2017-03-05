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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\t<%tools.vitruv.framework.change.echange.EChange%> _resolveApply = super.resolveApply(resourceSet);\n\tfinal <%tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference%><A, T> resolvedChange = ((<%tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference%><A, T>) _resolveApply);\n\tboolean _equals = <%com.google.common.base.Objects%>.equal(resolvedChange, null);\n\tif (_equals)\n\t{\n\t\treturn null;\n\t}\n\tT _oldValue = this.getOldValue();\n\t<%org.eclipse.emf.ecore.EObject%> _resolveProxy = <%tools.vitruv.framework.change.echange.util.EChangeUtil%>.resolveProxy(_oldValue, resourceSet);\n\tresolvedChange.setOldValue(((T) _resolveProxy));\n\tT _newValue = this.getNewValue();\n\t<%org.eclipse.emf.ecore.EObject%> _resolveProxy_1 = <%tools.vitruv.framework.change.echange.util.EChangeUtil%>.resolveProxy(_newValue, resourceSet);\n\tresolvedChange.setNewValue(((T) _resolveProxy_1));\n\tif (((<%com.google.common.base.Objects%>.equal(resolvedChange.getNewValue(), null) || (!resolvedChange.getNewValue().eIsProxy())) && (<%com.google.common.base.Objects%>.equal(resolvedChange.getOldValue(), null) || (!resolvedChange.getOldValue().eIsProxy()))))\n\t{\n\t\treturn resolvedChange;\n\t}\n}\nreturn this;'"
	 * @generated
	 */
	EChange resolveApply(ResourceSet resourceSet);

} // ReplaceSingleValuedEReference
