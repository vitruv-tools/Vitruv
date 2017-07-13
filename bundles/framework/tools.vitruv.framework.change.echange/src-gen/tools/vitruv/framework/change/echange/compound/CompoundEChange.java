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
	 * Resolves the compound change like {@link resolveBefore}, but also applies the change forward.
	 * If the change was already resolved, it returns the original change and applies it forward.
	 * @param resourceSet 				The {@code ResourceSet} which contains the concrete EObjects the proxy objects of
	 * 									the unresolved should be resolved to.
	 * @return 							Returns the resolved change if the change could be applied forward. The returned change
	 * 									is a copy of the change or, if the change was already resolved, the original change.
	 * 									If the change could not be resolved and / or applied or the
	 * 									resource set is {@code null}, it returns {@code null}
	 * @throws IllegalStateException 	The change is already resolved.
	 * <!-- end-model-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.compound.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return <%tools.vitruv.framework.change.echange.resolve.EChangeResolver%>.resolveCopy(this, resourceSet, true, false);'"
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
	 * @param resourceSet 				The {@code ResourceSet} which contains the concrete EObjects the proxy objects of
	 * 									the unresolved should be resolved to.
	 * @return 							Returns the resolved change if the change could be applied backward. The returned change
	 * 									is a copy of the change or, if the change was already resolved, the original change.
	 * 									If the change could not be resolved and / or applied or the resource set is {@code null},
	 * 									it returns {@code null}
	 * @throws IllegalStateException 	The change is already resolved.
	 * <!-- end-model-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.compound.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return <%tools.vitruv.framework.change.echange.resolve.EChangeResolver%>.resolveCopy(this, resourceSet, false, false);'"
	 * @generated
	 */
	EChange resolveAfterAndApplyBackward(ResourceSet resourceSet);

} // CompoundEChange
