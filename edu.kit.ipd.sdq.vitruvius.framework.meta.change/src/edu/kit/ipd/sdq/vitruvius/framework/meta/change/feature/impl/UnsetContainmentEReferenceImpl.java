/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetContainmentEReference;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Unset Containment EReference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class UnsetContainmentEReferenceImpl<T extends EObject> extends UnsetEReferenceImpl<T> implements UnsetContainmentEReference<T> {
	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected UnsetContainmentEReferenceImpl() {
        super();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected EClass eStaticClass() {
        return FeaturePackage.Literals.UNSET_CONTAINMENT_EREFERENCE;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * This is specialized for the more specific type known in this context.
     * @generated
     */
	@Override
	public void setOldValue(T newOldValue) {
        super.setOldValue(newOldValue);
    }

} //UnsetContainmentEReferenceImpl
