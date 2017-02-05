/**
 */
package tools.vitruv.framework.change.echange.feature.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.framework.change.echange.feature.FeaturePackage;
import tools.vitruv.framework.change.echange.feature.UpdateMultiValuedFeatureEChange;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Update Multi Valued Feature EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class UpdateMultiValuedFeatureEChangeImpl<A extends EObject, F extends EStructuralFeature> extends FeatureEChangeImpl<A, F> implements UpdateMultiValuedFeatureEChange<A, F> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UpdateMultiValuedFeatureEChangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FeaturePackage.Literals.UPDATE_MULTI_VALUED_FEATURE_ECHANGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public void setAffectedFeature(F newAffectedFeature) {
		super.setAffectedFeature(newAffectedFeature);
	}

} //UpdateMultiValuedFeatureEChangeImpl
