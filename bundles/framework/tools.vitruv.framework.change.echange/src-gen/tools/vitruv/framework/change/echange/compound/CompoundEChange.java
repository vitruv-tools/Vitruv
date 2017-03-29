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
 * <!-- begin-model-doc -->
 * *
 * Abstract EChange which is a sequence of several atomic changes.
 * <!-- end-model-doc -->
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
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the atomic changes of the compound changes, in the same order as they are
	 * resolved / applied.
	 * <!-- end-model-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	EList<AtomicEChange> getAtomicChanges();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns if all proxy EObjects of the change are resolved to concrete EObjects of a resource set.
	 * Needs to be true to apply the change.
	 * @return	All proxy EObjects are resolved to concrete EObjects.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%tools.vitruv.framework.change.echange.AtomicEChange%>> _atomicChanges = this.getAtomicChanges();\nfor (final <%tools.vitruv.framework.change.echange.AtomicEChange%> change : _atomicChanges)\n{\n\tboolean _isResolved = change.isResolved();\n\tboolean _not = (!_isResolved);\n\tif (_not)\n\t{\n\t\treturn false;\n\t}\n}\nreturn super.isResolved();'"
	 * @generated
	 */
	boolean isResolved();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Creates a copy of the change and resolves the unresolved proxy EObjects of the change to a given set of
	 * resources with concrete EObjects.
	 * The model has to be in the state before the change is applied forward. If the model is in state after the
	 * change and it will be applied backward, {@link resolveAfter} has to be called instead.
	 * Before the change can be applied all proxy objects need to be resolved.
	 * @param 	resourceSet The {@code ResourceSet} which contains the concrete EObjects the proxy objects of
	 * 			the unresolved should be resolved to.
	 * @return 	Returns a resolved copy of the change. If the copy could not be resolved or the resource set
	 * 			is {@code null}, it returns {@code null}. If the change was already resolved, it returns
	 * 			the original change.
	 * <!-- end-model-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.compound.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\tfinal <%tools.vitruv.framework.change.echange.compound.CompoundEChange%> change = <%org.eclipse.emf.ecore.util.EcoreUtil%>.<<%tools.vitruv.framework.change.echange.compound.CompoundEChange%>>copy(this);\n\tboolean _resolve = <%tools.vitruv.framework.change.echange.resolve.CompoundEChangeResolver%>.resolve(change, resourceSet, true, true);\n\tif (_resolve)\n\t{\n\t\treturn change;\n\t}\n\treturn null;\n}\nreturn this;'"
	 * @generated
	 */
	EChange resolveBefore(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Creates a copy of the change and resolves the unresolved proxy EObjects of the change to a given set
	 * of resources with concrete EObjects.
	 * The model has to be in the state after the change is applied backward. If the model is in state before
	 * the change and it will be applied forward, {@link resolveBefore} has to be called instead.
	 * Before the change can be applied all proxy objects need to be resolved.
	 * @param 	resourceSet The {@code ResourceSet} which contains the concrete EObjects the proxy objects of
	 * 			the unresolved should be resolved to.
	 * @return 	Returns a resolved copy of the change. If the copy could not be resolved or the resource set
	 * 			is {@code null}, it returns {@code null}. If the change was already resolved, it returns
	 * 			the original change.
	 * <!-- end-model-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.compound.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\tfinal <%tools.vitruv.framework.change.echange.compound.CompoundEChange%> change = <%org.eclipse.emf.ecore.util.EcoreUtil%>.<<%tools.vitruv.framework.change.echange.compound.CompoundEChange%>>copy(this);\n\tboolean _resolve = <%tools.vitruv.framework.change.echange.resolve.CompoundEChangeResolver%>.resolve(change, resourceSet, false, true);\n\tif (_resolve)\n\t{\n\t\treturn change;\n\t}\n\treturn null;\n}\nreturn this;'"
	 * @generated
	 */
	EChange resolveAfter(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Resolves the compound change like {@link resolveBefore}, but also applies the change forward.
	 * If the change was already resolved, it returns the original change and applies it forward.
	 * @param 	resourceSet The {@code ResourceSet} which contains the concrete EObjects the proxy objects of
	 * 			the unresolved should be resolved to.
	 * @return 	Returns the resolved change if the change could be applied forward. The returned change
	 * 			is a copy of the change or, if the change was already resolved, the original change.
	 * 			If the change could not be resolved and / or applied or the
	 * 			resource set is {@code null}, it returns {@code null}
	 * <!-- end-model-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.compound.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\tfinal <%tools.vitruv.framework.change.echange.compound.CompoundEChange%> change = <%org.eclipse.emf.ecore.util.EcoreUtil%>.<<%tools.vitruv.framework.change.echange.compound.CompoundEChange%>>copy(this);\n\tboolean _resolve = <%tools.vitruv.framework.change.echange.resolve.CompoundEChangeResolver%>.resolve(change, resourceSet, true, false);\n\tif (_resolve)\n\t{\n\t\treturn change;\n\t}\n}\nelse\n{\n\tboolean _applyForward = this.applyForward();\n\tif (_applyForward)\n\t{\n\t\treturn this;\n\t}\n}\nreturn null;'"
	 * @generated
	 */
	EChange resolveBeforeAndApplyForward(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Resolves the compound change like {@link resolveAfter}, but also applies the change backward.
	 * If the change was already resolved, it returns the original change and applies it backward.
	 * @param 	resourceSet The {@code ResourceSet} which contains the concrete EObjects the proxy objects of
	 * 			the unresolved should be resolved to.
	 * @return 	Returns the resolved change if the change could be applied backward. The returned change
	 * 			is a copy of the change or, if the change was already resolved, the original change.
	 * 			If the change could not be resolved and / or applied or the resource set is {@code null},
	 * 			it returns {@code null}
	 * <!-- end-model-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.compound.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\tfinal <%tools.vitruv.framework.change.echange.compound.CompoundEChange%> change = <%org.eclipse.emf.ecore.util.EcoreUtil%>.<<%tools.vitruv.framework.change.echange.compound.CompoundEChange%>>copy(this);\n\tboolean _resolve = <%tools.vitruv.framework.change.echange.resolve.CompoundEChangeResolver%>.resolve(change, resourceSet, false, false);\n\tif (_resolve)\n\t{\n\t\treturn change;\n\t}\n}\nelse\n{\n\tboolean _applyBackward = this.applyBackward();\n\tif (_applyBackward)\n\t{\n\t\treturn this;\n\t}\n}\nreturn null;'"
	 * @generated
	 */
	EChange resolveAfterAndApplyBackward(ResourceSet resourceSet);

} // CompoundEChange
