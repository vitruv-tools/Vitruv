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
	 * Resolves the unresolved proxy EObjects of the change to a given set of resources with concrete EObjects.
	 * The model has to be in the state before the change will applied forward. If the model is in state after the
	 * change and it will be applied backward, {@link resolveAfter} has to be called instead.
	 * Before the change can be applied all proxy objects need to be resolved.
	 * @param 	resourceSet The {@code ResourceSet} which contains the concrete EObjects the proxy objects of
	 * 			the unresolved should be resolved to.
	 * @return 	{@code true} if the change was successfully resolved.
	 * 			Returns {@code false} if the change could not be resolved or the resource set was {@code null}.
	 * <!-- end-model-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _equals = <%com.google.common.base.Objects%>.equal(resourceSet, null);\nif (_equals)\n{\n\treturn false;\n}\nreturn true;'"
	 * @generated
	 */
	boolean resolveBefore(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Resolves the unresolved proxy EObjects of the change to a given set of resources with concrete EObjects.
	 * The model has to be in the state after the change is applied forward. If the model is in state before
	 * the change and it will be applied forward, {@link resolveBefore} has to be called instead.
	 * Before the change can be applied all proxy objects need to be resolved.
	 * @param 	resourceSet The {@code ResourceSet} which contains the concrete EObjects the proxy objects of
	 * 			the unresolved should be resolved to.
	 * @return 	{@code true} if the change was successfully resolved.
	 * 			Returns {@code false} if the change could not be resolved or the resource set was {@code null}.
	 * <!-- end-model-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.resolveBefore(resourceSet);'"
	 * @generated
	 */
	boolean resolveAfter(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Resolves the change like {@link resolveBefore}, but creates a copy of the change and resolves that
	 * one instead.
	 * @param 	resourceSet The {@code ResourceSet} which contains the concrete EObjects the proxy objects of
	 * 			the unresolved should be resolved to.
	 * @return 	Returns the resolved copy of the original change if the change was successfully resolved.
	 * 			If the change could not be resolved successfully, was already resolved or the resource set
	 * 			is null it returns the original change.
	 * <!-- end-model-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\tfinal <%tools.vitruv.framework.change.echange.EChange%> resolvedChange = <%org.eclipse.emf.ecore.util.EcoreUtil%>.<<%tools.vitruv.framework.change.echange.EChange%>>copy(this);\n\tboolean _resolveBefore = resolvedChange.resolveBefore(resourceSet);\n\tif (_resolveBefore)\n\t{\n\t\treturn resolvedChange;\n\t}\n}\nreturn this;'"
	 * @generated
	 */
	EChange copyAndResolveBefore(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Resolves the change like {@link resolveAfter}, but creates a copy of the change and resolves that
	 * one instead.
	 * @param 	resourceSet The {@code ResourceSet} which contains the concrete EObjects the proxy objects of
	 * 			the unresolved should be resolved to.
	 * @return 	Returns the resolved copy of the original change if the change was successfully resolved.
	 * 			If the change could not be resolved successfully, was already resolved or the resource set
	 * 			is null it returns the original change.
	 * <!-- end-model-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\tfinal <%tools.vitruv.framework.change.echange.EChange%> resolvedChange = <%org.eclipse.emf.ecore.util.EcoreUtil%>.<<%tools.vitruv.framework.change.echange.EChange%>>copy(this);\n\tboolean _resolveAfter = resolvedChange.resolveAfter(resourceSet);\n\tif (_resolveAfter)\n\t{\n\t\treturn resolvedChange;\n\t}\n}\nreturn this;'"
	 * @generated
	 */
	EChange copyAndResolveAfter(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Resolves the change like {@link resolveBefore}, but also applies the change forward.
	 * @param 	resourceSet The {@code ResourceSet} which contains the concrete EObjects the proxy objects of
	 * 			the unresolved should be resolved to.
	 * @return 	{@code true} if the change was successfully resolved and applied forward.
	 * 			Returns {@code false} if the change could not be resolved / applied or the resource set was
	 * 			{@code null}.
	 * <!-- end-model-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _resolveBefore = this.resolveBefore(resourceSet);\nif (_resolveBefore)\n{\n\treturn this.applyForward();\n}\nreturn false;'"
	 * @generated
	 */
	boolean resolveBeforeAndApplyForward(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Resolves the change like {@link resolveAfter}, but also applies the change backward.
	 * @param 	resourceSet The {@code ResourceSet} which contains the concrete EObjects the proxy objects of
	 * 			the unresolved should be resolved to.
	 * @return 	{@code true} if the change was successfully resolved and applied backward.
	 * 			Returns {@code false} if the change could not be resolved / applied or the resource set was
	 * 			{@code null}.
	 * <!-- end-model-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _resolveAfter = this.resolveAfter(resourceSet);\nif (_resolveAfter)\n{\n\treturn this.applyBackward();\n}\nreturn false;'"
	 * @generated
	 */
	boolean resolveAfterAndApplyBackward(ResourceSet resourceSet);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Applies the change to the model which the change was resolved to.
	 * The change must be resolved before it can be applied.
	 * @return	Returns whether the change was successfully applied. If the
	 * 			change was not resolved or could not be applied it returns {@code false}
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nif (_isResolved)\n{\n\t<%tools.vitruv.framework.change.echange.util.ApplyForwardCommandSwitch%> _applyForwardCommandSwitch = new <%tools.vitruv.framework.change.echange.util.ApplyForwardCommandSwitch%>();\n\tfinal <%java.util.List%><<%org.eclipse.emf.common.command.Command%>> commands = _applyForwardCommandSwitch.doSwitch(this);\n\tboolean _notEquals = (!<%com.google.common.base.Objects%>.equal(commands, null));\n\tif (_notEquals)\n\t{\n\t\tfor (final <%org.eclipse.emf.common.command.Command%> c : commands)\n\t\t{\n\t\t\tboolean _canExecute = c.canExecute();\n\t\t\tif (_canExecute)\n\t\t\t{\n\t\t\t\tc.execute();\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\treturn false;\n\t\t\t}\n\t\t}\n\t\treturn true;\n\t}\n}\nreturn false;'"
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
	 * @return	Returns whether the change was successfully reverted. If the
	 * 			change was not resolved or could not be reverted it returns {@code false}
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nif (_isResolved)\n{\n\t<%tools.vitruv.framework.change.echange.util.ApplyBackwardCommandSwitch%> _applyBackwardCommandSwitch = new <%tools.vitruv.framework.change.echange.util.ApplyBackwardCommandSwitch%>();\n\tfinal <%java.util.List%><<%org.eclipse.emf.common.command.Command%>> commands = _applyBackwardCommandSwitch.doSwitch(this);\n\tboolean _notEquals = (!<%com.google.common.base.Objects%>.equal(commands, null));\n\tif (_notEquals)\n\t{\n\t\tfor (final <%org.eclipse.emf.common.command.Command%> c : commands)\n\t\t{\n\t\t\tboolean _canExecute = c.canExecute();\n\t\t\tif (_canExecute)\n\t\t\t{\n\t\t\t\tc.execute();\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\treturn false;\n\t\t\t}\n\t\t}\n\t\treturn true;\n\t}\n}\nreturn false;'"
	 * @generated
	 */
	boolean applyBackward();

} // EChange
