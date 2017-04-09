/**
 */
package tools.vitruv.framework.change.echange;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Abstract change, which can be applied forward or backward, after it was resolved to a specific instance of a model.
 * <!-- end-model-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.EChangePackage#getEChange()
 * @model abstract="true"
 * @generated
 */
public interface EChange extends EObject {
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return true;'"
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
	 * 			is {@code null}, it returns {@code null}. If the change is already resolved an exception is thrown.
	 * @throws IllegalStateException The change is already resolved.
	 * <!-- end-model-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return <%tools.vitruv.framework.change.echange.resolve.EChangeResolver%>.resolveCopy(this, resourceSet, true, true);'"
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
	 * 			is {@code null}, it returns {@code null}. If the change is already resolved and exception is thrown.
	 * @throws IllegalStateException The change is already resolved.
	 * <!-- end-model-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return <%tools.vitruv.framework.change.echange.resolve.EChangeResolver%>.resolveCopy(this, resourceSet, false, true);'"
	 * @generated
	 */
	EChange resolveAfter(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Resolves the change like {@link resolveBefore}, but also applies the change forward.
	 * If the change was already resolved, it returns the original change and applies it forward.
	 * @param 	resourceSet The {@code ResourceSet} which contains the concrete EObjects the proxy objects of
	 * 			the unresolved should be resolved to.
	 * @return 	Returns the resolved change if the change could be applied forward. The returned change
	 * 			is a copy of the change or, if the change was already resolved, the original change.
	 * 			If the change could not be resolved and / or applied or the
	 * 			resource set is {@code null}, it returns {@code null}
	 * @throws IllegalStateException The change is already resolved.
	 * @throws RuntimeException The change could not be applied.
	 * <!-- end-model-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final <%tools.vitruv.framework.change.echange.EChange%> resolvedChange = this.resolveBefore(resourceSet);\nif (((!<%com.google.common.base.Objects%>.equal(resolvedChange, null)) && resolvedChange.applyForward()))\n{\n\treturn resolvedChange;\n}\nelse\n{\n\treturn null;\n}'"
	 * @generated
	 */
	EChange resolveBeforeAndApplyForward(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Resolves the change like {@link resolveAfter}, but also applies the change backward.
	 * If the change was already resolved, it returns the original change and applies it backward.
	 * @param 	resourceSet The {@code ResourceSet} which contains the concrete EObjects the proxy objects of
	 * 			the unresolved should be resolved to.
	 * @return 	Returns the resolved change if the change could be applied backward. The returned change
	 * 			is a copy of the change or, if the change was already resolved, the original change.
	 * 			If the change could not be resolved and / or applied or the resource set is {@code null},
	 * 			it returns {@code null}
	 * @throws IllegalStateException The change is already resolved.
	 * @throws RuntimeException The change could not be applied.
	 * <!-- end-model-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final <%tools.vitruv.framework.change.echange.EChange%> resolvedChange = this.resolveAfter(resourceSet);\nif (((!<%com.google.common.base.Objects%>.equal(resolvedChange, null)) && resolvedChange.applyBackward()))\n{\n\treturn resolvedChange;\n}\nelse\n{\n\treturn null;\n}'"
	 * @generated
	 */
	EChange resolveAfterAndApplyBackward(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Applies the change to the model which the change was resolved to.
	 * The change must be resolved before it can be applied.
	 * @return	Returns whether the change was successfully applied forward. If the
	 * 			change was not resolved an Exception is thrown.
	 * 			If the change could not be applied it returns {@code false}.
	 * 			Otherwise it returns {@code true}.
	 * @throws IllegalStateException The change is not resolved
	 * @throws RuntimeException The change could not be applied.
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return <%tools.vitruv.framework.change.echange.util.ApplyEChangeSwitch%>.applyEChange(this, true);'"
	 * @generated
	 */
	boolean applyForward();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Reverts the change on the model which the change was resolved to.
	 * The change must be resolved before it can be reverted.
	 * @return	Returns whether the change was successfully applied backward. If the
	 * 			change was not resolved an Exception is thrown.
	 * 			If the change could not be applied it returns {@code false}.
	 * 			Otherwise it returns {@code true}.
	 * @throws IllegalStateException The change is not resolved.
	 * @throws RuntimeException The change could not be applied.
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return <%tools.vitruv.framework.change.echange.util.ApplyEChangeSwitch%>.applyEChange(this, false);'"
	 * @generated
	 */
	boolean applyBackward();

} // EChange
