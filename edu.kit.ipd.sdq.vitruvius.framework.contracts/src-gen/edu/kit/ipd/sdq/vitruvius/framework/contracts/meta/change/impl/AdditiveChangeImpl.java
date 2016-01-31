/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Additive Change</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class AdditiveChangeImpl<T extends Object> extends EChangeImpl implements AdditiveChange<T> {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AdditiveChangeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ChangePackage.Literals.ADDITIVE_CHANGE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public T getNewValue() {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
        switch (operationID) {
            case ChangePackage.ADDITIVE_CHANGE___GET_NEW_VALUE:
                return getNewValue();
        }
        return super.eInvoke(operationID, arguments);
    }

} //AdditiveChangeImpl
