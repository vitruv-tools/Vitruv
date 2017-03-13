/**
 */
package tools.vitruv.framework.change.echange.feature.reference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.resource.ResourceSet;

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
	boolean resolveBefore(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.feature.reference.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.resolve(resourceSet, false);'"
	 * @generated
	 */
	boolean resolveAfter(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.feature.reference.ResourceSet" resourceSetUnique="false" resolveBeforeUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\t<%org.eclipse.emf.ecore.EObject%> resolvedNewValue = null;\n\tboolean _isContainment = this.isContainment();\n\tif (_isContainment)\n\t{\n\t\t<%org.eclipse.emf.ecore.EObject%> _resolveOldValue = this.resolveOldValue(resourceSet, (!resolveBefore));\n\t\tresolvedNewValue = _resolveOldValue;\n\t}\n\telse\n\t{\n\t\t<%org.eclipse.emf.ecore.EObject%> _resolveNewValue = this.resolveNewValue(resourceSet, resolveBefore);\n\t\tresolvedNewValue = _resolveNewValue;\n\t}\n\tfinal <%org.eclipse.emf.ecore.EObject%> resolvedOldValue = this.resolveOldValue(resourceSet, resolveBefore);\n\tif (((((<%com.google.common.base.Objects%>.equal(resolvedNewValue, null) || resolvedNewValue.eIsProxy()) || <%com.google.common.base.Objects%>.equal(resolvedOldValue, null)) || resolvedOldValue.eIsProxy()) || (!super.resolveBefore(resourceSet))))\n\t{\n\t\treturn false;\n\t}\n\tthis.setNewValue(((T) resolvedNewValue));\n\tthis.setOldValue(((T) resolvedOldValue));\n}\nreturn true;'"
	 * @generated
	 */
	boolean resolve(ResourceSet resourceSet, boolean resolveBefore);

} // ReplaceSingleValuedEReference
