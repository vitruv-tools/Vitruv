/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EAttributeChange;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EAttribute Change</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class EAttributeChangeImpl extends EFeatureChangeImpl<EAttribute> implements EAttributeChange {
	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected EAttributeChangeImpl() {
        super();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected EClass eStaticClass() {
        return ChangePackage.Literals.EATTRIBUTE_CHANGE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * This is specialized for the more specific type known in this context.
     * @generated
     */
    @Override
    public void setAffectedFeature(EAttribute newAffectedFeature) {
        super.setAffectedFeature(newAffectedFeature);
    }

} //EAttributeChangeImpl
