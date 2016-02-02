/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Subtractive EChange</b></em>'. <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class SubtractiveEChangeImpl<T extends Object> extends EAtomicChangeImpl
        implements SubtractiveEChange<T> {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected SubtractiveEChangeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ChangePackage.Literals.SUBTRACTIVE_ECHANGE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public T getOldValue() {
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
        switch (operationID) {
            case ChangePackage.SUBTRACTIVE_ECHANGE___GET_OLD_VALUE:
                return getOldValue();
        }
        return super.eInvoke(operationID, arguments);
    }

} // SubtractiveEChangeImpl
