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
	 * Needs to be true to apply or revert the change.
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
	 * Before the change can be applied or reverted all proxy objects need to be resolved.
	 * @param 	resourceSet The {@code ResourceSet} which contains the concrete EObjects the proxy objects of
	 * 			the unresolved should be resolved to.
	 * @return 	A new resolved change which EObjects references to concrete elements in the
	 * 			given {@code ResourceSet}. The returned class is the same as the resolved one.
	 * 			If not all proxy objects could be resolved it returns the original unresolved change.
	 * 			Returns {@code null} if {@link resourceSet} is {@code null}.
	 * <!-- end-model-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _equals = <%com.google.common.base.Objects%>.equal(resourceSet, null);\nif (_equals)\n{\n\treturn null;\n}\nboolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\treturn <%org.eclipse.emf.ecore.util.EcoreUtil%>.<<%tools.vitruv.framework.change.echange.EChange%>>copy(this);\n}\nreturn this;'"
	 * @generated
	 */
	EChange resolve(ResourceSet resourceSet);

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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nif (_isResolved)\n{\n\t<%tools.vitruv.framework.change.echange.util.ApplyCommandSwitch%> _applyCommandSwitch = new <%tools.vitruv.framework.change.echange.util.ApplyCommandSwitch%>();\n\tfinal <%org.eclipse.emf.common.command.Command%> command = _applyCommandSwitch.doSwitch(this);\n\tif (((!<%com.google.common.base.Objects%>.equal(command, null)) && command.canExecute()))\n\t{\n\t\tcommand.execute();\n\t\treturn true;\n\t}\n}\nreturn false;'"
	 * @generated
	 */
	boolean apply();

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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nif (_isResolved)\n{\n\t<%tools.vitruv.framework.change.echange.util.RevertCommandSwitch%> _revertCommandSwitch = new <%tools.vitruv.framework.change.echange.util.RevertCommandSwitch%>();\n\tfinal <%org.eclipse.emf.common.command.Command%> command = _revertCommandSwitch.doSwitch(this);\n\tif (((!<%com.google.common.base.Objects%>.equal(command, null)) && command.canExecute()))\n\t{\n\t\tcommand.execute();\n\t\treturn true;\n\t}\n}\nreturn false;'"
	 * @generated
	 */
	boolean revert();

} // EChange
