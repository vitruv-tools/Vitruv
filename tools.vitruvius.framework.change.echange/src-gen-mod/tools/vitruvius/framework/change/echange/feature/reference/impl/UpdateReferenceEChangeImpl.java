/**
 */
package tools.vitruvius.framework.change.echange.feature.reference.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import tools.vitruvius.framework.change.echange.feature.reference.ReferencePackage;
import tools.vitruvius.framework.change.echange.feature.reference.UpdateReferenceEChange;
import tools.vitruvius.framework.change.echange.feature.impl.FeatureEChangeImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Update Reference
 * EChange</b></em>'. <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class UpdateReferenceEChangeImpl<A extends EObject> extends FeatureEChangeImpl<A, EReference>
        implements UpdateReferenceEChange<A> {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected UpdateReferenceEChangeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ReferencePackage.Literals.UPDATE_REFERENCE_ECHANGE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> This is specialized for the more specific type
     * known in this context.
     *
     * @generated
     */
    @Override
    public void setAffectedFeature(final EReference newAffectedFeature) {
        super.setAffectedFeature(newAffectedFeature);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public boolean isContainment() {
        return getAffectedFeature().isContainment();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eInvoke(final int operationID, final EList<?> arguments) throws InvocationTargetException {
        switch (operationID) {
        case ReferencePackage.UPDATE_REFERENCE_ECHANGE___IS_CONTAINMENT:
            return isContainment();
        }
        return super.eInvoke(operationID, arguments);
    }

} // UpdateReferenceEChangeImpl
