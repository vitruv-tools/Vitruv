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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.resolve(resourceSet, true, true);'"
	 * @generated
	 */
	boolean resolveBefore(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.compound.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.resolve(resourceSet, false, true);'"
	 * @generated
	 */
	boolean resolveAfter(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.compound.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.resolve(resourceSet, true, false);'"
	 * @generated
	 */
	boolean resolveBeforeAndApply(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.compound.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.resolve(resourceSet, false, false);'"
	 * @generated
	 */
	boolean resolveAfterAndApply(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.compound.ResourceSet" resourceSetUnique="false" resolveBeforeUnique="false" revertAfterResolvingUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\tboolean _resolveBefore = super.resolveBefore(resourceSet);\n\tboolean _not_1 = (!_resolveBefore);\n\tif (_not_1)\n\t{\n\t\treturn false;\n\t}\n\tfinal <%org.eclipse.emf.common.util.BasicEList%><<%tools.vitruv.framework.change.echange.EChange%>> changesMade = new <%org.eclipse.emf.common.util.BasicEList%><<%tools.vitruv.framework.change.echange.EChange%>>();\n\tif (resolveBefore)\n\t{\n\t\t<%org.eclipse.emf.common.util.EList%><<%tools.vitruv.framework.change.echange.AtomicEChange%>> _atomicChanges = this.getAtomicChanges();\n\t\tfor (final <%tools.vitruv.framework.change.echange.EChange%> change : _atomicChanges)\n\t\t{\n\t\t\tboolean _resolveBeforeAndApplyForward = change.resolveBeforeAndApplyForward(resourceSet);\n\t\t\tboolean _not_2 = (!_resolveBeforeAndApplyForward);\n\t\t\tif (_not_2)\n\t\t\t{\n\t\t\t\t<%org.eclipse.emf.common.util.EList%><<%tools.vitruv.framework.change.echange.EChange%>> _reverse = <%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%>.<<%tools.vitruv.framework.change.echange.EChange%>>reverse(changesMade);\n\t\t\t\tfor (final <%tools.vitruv.framework.change.echange.EChange%> changed : _reverse)\n\t\t\t\t{\n\t\t\t\t\tchanged.applyBackward();\n\t\t\t\t}\n\t\t\t\treturn false;\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tchangesMade.add(change);\n\t\t\t}\n\t\t}\n\t}\n\telse\n\t{\n\t\t<%org.eclipse.emf.common.util.EList%><<%tools.vitruv.framework.change.echange.AtomicEChange%>> _atomicChanges_1 = this.getAtomicChanges();\n\t\t<%org.eclipse.emf.common.util.EList%><<%tools.vitruv.framework.change.echange.AtomicEChange%>> _reverse_1 = <%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%>.<<%tools.vitruv.framework.change.echange.AtomicEChange%>>reverse(_atomicChanges_1);\n\t\tfor (final <%tools.vitruv.framework.change.echange.EChange%> change_1 : _reverse_1)\n\t\t{\n\t\t\tboolean _resolveAfterAndApplyBackward = change_1.resolveAfterAndApplyBackward(resourceSet);\n\t\t\tboolean _not_3 = (!_resolveAfterAndApplyBackward);\n\t\t\tif (_not_3)\n\t\t\t{\n\t\t\t\t<%org.eclipse.emf.common.util.EList%><<%tools.vitruv.framework.change.echange.EChange%>> _reverse_2 = <%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%>.<<%tools.vitruv.framework.change.echange.EChange%>>reverse(changesMade);\n\t\t\t\tfor (final <%tools.vitruv.framework.change.echange.EChange%> changed_1 : _reverse_2)\n\t\t\t\t{\n\t\t\t\t\tchanged_1.applyForward();\n\t\t\t\t}\n\t\t\t\treturn false;\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tchangesMade.add(change_1);\n\t\t\t}\n\t\t}\n\t}\n\tif (revertAfterResolving)\n\t{\n\t\tif (resolveBefore)\n\t\t{\n\t\t\tthis.applyBackward();\n\t\t}\n\t\telse\n\t\t{\n\t\t\tthis.applyForward();\n\t\t}\n\t}\n}\nreturn true;'"
	 * @generated
	 */
	boolean resolve(ResourceSet resourceSet, boolean resolveBefore, boolean revertAfterResolving);

} // CompoundEChange
