/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.EReferenceChange;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EReference Change</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class EReferenceChangeImpl extends EFeatureChangeImpl<EReference> implements EReferenceChange {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EReferenceChangeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ChangePackage.Literals.EREFERENCE_CHANGE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * This is specialized for the more specific type known in this context.
     * @generated
     */
    @Override
    public void setAffectedFeature(EReference newAffectedFeature) {
        super.setAffectedFeature(newAffectedFeature);
    }

} //EReferenceChangeImpl
