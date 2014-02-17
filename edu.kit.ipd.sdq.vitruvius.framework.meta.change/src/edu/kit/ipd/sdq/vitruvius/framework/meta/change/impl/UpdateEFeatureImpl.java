/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEFeature;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Update EFeature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 * 
 * @generated
 */
public abstract class UpdateEFeatureImpl<T extends Object> extends EObjectImpl implements UpdateEFeature<T> {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected UpdateEFeatureImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ChangePackage.Literals.UPDATE_EFEATURE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public abstract T getNewValue();

} // UpdateEFeatureImpl
