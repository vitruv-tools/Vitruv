/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UnsetEFeature;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Unset EFeature</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class UnsetEFeatureImpl<T extends EStructuralFeature> extends EFeatureChangeImpl<T> implements UnsetEFeature<T> {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected UnsetEFeatureImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FeaturePackage.Literals.UNSET_EFEATURE;
    }

} //UnsetEFeatureImpl
