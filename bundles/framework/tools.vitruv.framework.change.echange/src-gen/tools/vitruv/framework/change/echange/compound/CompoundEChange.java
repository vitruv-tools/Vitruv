/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.AtomicEChange;
import tools.vitruv.framework.change.echange.EChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getCompoundEChange()
 * @model abstract="true"
 * @generated
 */
public interface CompoundEChange extends EChange {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	EList<AtomicEChange> getAtomicChanges();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%tools.vitruv.framework.change.echange.AtomicEChange%>> _atomicChanges = this.getAtomicChanges();\nfor (final <%tools.vitruv.framework.change.echange.AtomicEChange%> change : _atomicChanges)\n{\n\tboolean _isResolved = change.isResolved();\n\tboolean _not = (!_isResolved);\n\tif (_not)\n\t{\n\t\treturn false;\n\t}\n}\nreturn super.isResolved();'"
	 * @generated
	 */
	boolean isResolved();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.compound.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.resolve(resourceSet, true);'"
	 * @generated
	 */
	EChange resolveApply(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.compound.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.resolve(resourceSet, false);'"
	 * @generated
	 */
	EChange resolveRevert(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.compound.ResourceSet" resourceSetUnique="false" applyChangeUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\t<%tools.vitruv.framework.change.echange.EChange%> _resolveApply = super.resolveApply(resourceSet);\n\tfinal <%tools.vitruv.framework.change.echange.compound.CompoundEChange%> resolvedChange = ((<%tools.vitruv.framework.change.echange.compound.CompoundEChange%>) _resolveApply);\n\tboolean _equals = <%com.google.common.base.Objects%>.equal(resolvedChange, null);\n\tif (_equals)\n\t{\n\t\treturn null;\n\t}\n\tresolvedChange.resolveAtomicChanges(resourceSet, applyChange);\n\t<%org.eclipse.emf.common.util.EList%><<%tools.vitruv.framework.change.echange.AtomicEChange%>> _atomicChanges = resolvedChange.getAtomicChanges();\n\tfor (final <%tools.vitruv.framework.change.echange.AtomicEChange%> change : _atomicChanges)\n\t{\n\t\tboolean _isResolved_1 = change.isResolved();\n\t\tboolean _not_1 = (!_isResolved_1);\n\t\tif (_not_1)\n\t\t{\n\t\t\treturn this;\n\t\t}\n\t}\n\treturn resolvedChange;\n}\nreturn this;'"
	 * @generated
	 */
	EChange resolve(ResourceSet resourceSet, boolean applyChange);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Resolves all atomic changes in the compound change
	 * in the correct order.
	 * @param resourceSet The resource set which the proxy elements will be resolved to.
	 * @param applyChange Indicates whether the change will be applied or reverted
	 * 		after resolving. The changes needs to be resolved from the first to the last change.
	 * 		If {@code applyChange} is {@code false} the changes needs to be resolved
	 * 		from the last change to the first change.
	 * <!-- end-model-doc -->
	 * @model resourceSetDataType="tools.vitruv.framework.change.echange.compound.ResourceSet" resourceSetUnique="false" applyChangeUnique="false"
	 * @generated
	 */
	void resolveAtomicChanges(ResourceSet resourceSet, boolean applyChange);

} // CompoundEChange
