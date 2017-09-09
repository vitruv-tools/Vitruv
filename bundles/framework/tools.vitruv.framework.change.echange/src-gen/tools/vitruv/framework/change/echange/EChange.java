/**
 */
package tools.vitruv.framework.change.echange;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

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
	 * Returns if all IDs in this chang eare resolved to {@link EObjects}.
	 * Needs to be true to apply the change.
	 * @return	Whether elements in this change are resolved or not.
	 * <!-- end-model-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return tools.vitruv.framework.change.echange.resolve.ResolutionChecker.isResolved(this);'"
	 * @generated
	 */
	boolean isResolved();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the objects involved in this change, which includes all affected objects, new values and old values.
	 * @return	All elements involved in this change.
	 * <!-- end-model-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='org.eclipse.emf.common.util.EList&lt;EObject&gt; result = new org.eclipse.emf.common.util.BasicEList&lt;EObject&gt;();\r\nfor (EObject involvedEObject : tools.vitruv.framework.change.echange.EChangeInformationExtractor.getInvolvedEObjects(this)) {\r\n\tresult.add(involvedEObject);\r\n}\r\nreturn result;'"
	 * @generated
	 */
	EList<EObject> getInvolvedEObjects();

} // EChange
