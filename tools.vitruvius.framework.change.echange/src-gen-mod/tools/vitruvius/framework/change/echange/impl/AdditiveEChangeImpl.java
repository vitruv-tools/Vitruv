/**
 */
package tools.vitruvius.framework.change.echange.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import tools.vitruvius.framework.change.echange.AdditiveEChange;
import tools.vitruvius.framework.change.echange.ChangePackage;
import tools.vitruvius.framework.change.echange.impl.AtomicEChangeImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Additive EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class AdditiveEChangeImpl<T extends Object> extends AtomicEChangeImpl implements AdditiveEChange<T> {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected AdditiveEChangeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ChangePackage.Literals.ADDITIVE_ECHANGE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public T getNewValue() {
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eInvoke(final int operationID, final EList<?> arguments) throws InvocationTargetException {
        switch (operationID) {
        case ChangePackage.ADDITIVE_ECHANGE___GET_NEW_VALUE:
            return getNewValue();
        }
        return super.eInvoke(operationID, arguments);
    }

} // AdditiveEChangeImpl
