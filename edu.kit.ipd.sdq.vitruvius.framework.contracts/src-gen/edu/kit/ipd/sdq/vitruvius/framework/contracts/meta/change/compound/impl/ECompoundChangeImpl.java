/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ECompoundChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.EChangeImpl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ECompound Change</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class ECompoundChangeImpl extends EChangeImpl implements ECompoundChange {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ECompoundChangeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CompoundPackage.Literals.ECOMPOUND_CHANGE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<EChange> getComposedChanges() {
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
            case CompoundPackage.ECOMPOUND_CHANGE___GET_COMPOSED_CHANGES:
                return getComposedChanges();
        }
        return super.eInvoke(operationID, arguments);
    }

} //ECompoundChangeImpl
