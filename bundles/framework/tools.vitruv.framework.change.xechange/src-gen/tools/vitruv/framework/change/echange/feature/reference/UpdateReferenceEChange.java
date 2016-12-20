/**
 */
package tools.vitruv.framework.change.echange.feature.reference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import tools.vitruv.framework.change.echange.feature.FeatureEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Update Reference EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.feature.reference.ReferencePackage#getUpdateReferenceEChange()
 * @model abstract="true"
 * @generated
 */
public interface UpdateReferenceEChange<A extends EObject> extends FeatureEChange<A, EReference> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return getAffectedFeature().isContainment();'"
	 * @generated
	 */
	boolean isContainment();

} // UpdateReferenceEChange
