/**
 */
package tools.vitruv.framework.change.echange;

import org.eclipse.emf.common.command.Command;

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
	 * @model kind="operation" dataType="tools.vitruv.framework.change.echange.Command" unique="false"
	 * @generated
	 */
	Command getApplyCommand();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nif (_isResolved)\n{\n\tfinal <%org.eclipse.emf.common.command.Command%> command = this.getApplyCommand();\n\tboolean _canExecute = command.canExecute();\n\tif (_canExecute)\n\t{\n\t\tcommand.execute();\n\t\treturn true;\n\t}\n}\nreturn false;'"
	 * @generated
	 */
	boolean apply();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="tools.vitruv.framework.change.echange.Command" unique="false"
	 * @generated
	 */
	Command getRevertCommand();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nif (_isResolved)\n{\n\tfinal <%org.eclipse.emf.common.command.Command%> command = this.getRevertCommand();\n\tboolean _canExecute = command.canExecute();\n\tif (_canExecute)\n\t{\n\t\tcommand.execute();\n\t\treturn true;\n\t}\n}\nreturn false;'"
	 * @generated
	 */
	boolean revert();

} // EChange
