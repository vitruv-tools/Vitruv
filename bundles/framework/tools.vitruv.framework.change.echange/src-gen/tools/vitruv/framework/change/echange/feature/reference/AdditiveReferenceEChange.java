/**
 */
package tools.vitruv.framework.change.echange.feature.reference;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Additive Reference EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.feature.reference.ReferencePackage#getAdditiveReferenceEChange()
 * @model abstract="true" ABounds="tools.vitruv.framework.change.echange.feature.reference.EObj" TBounds="tools.vitruv.framework.change.echange.feature.reference.EObj"
 * @generated
 */
public interface AdditiveReferenceEChange<A extends EObject, T extends EObject> extends UpdateReferenceEChange<A>, EObjectAddedEChange<T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.feature.reference.ResourceSet" resourceSetUnique="false" resolveBeforeUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if ((resolveBefore && this.isContainment()))\n{\n\tfinal <%org.eclipse.emf.ecore.resource.Resource%> stagingArea = <%tools.vitruv.framework.change.echange.util.StagingArea%>.getStagingArea(resourceSet);\n\t<%org.eclipse.emf.common.util.EList%><<%org.eclipse.emf.ecore.EObject%>> _contents = stagingArea.getContents();\n\treturn _contents.get(0);\n}\nelse\n{\n\tT _newValue = this.getNewValue();\n\treturn <%tools.vitruv.framework.change.echange.util.EChangeUtil%>.resolveProxy(_newValue, resourceSet);\n}'"
	 * @generated
	 */
	EObject resolveNewValue(ResourceSet resourceSet, boolean resolveBefore);

} // AdditiveReferenceEChange
