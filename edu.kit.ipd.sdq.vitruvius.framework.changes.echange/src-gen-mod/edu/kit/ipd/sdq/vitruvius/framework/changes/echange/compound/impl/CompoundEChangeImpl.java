/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.changes.echange.compound.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.AtomicEChange;
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.compound.CompoundEChange;
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.compound.CompoundPackage;
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.impl.EChangeImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>EChange</b></em>'. <!--
 * end-user-doc -->
 *
 * @generated
 */
public abstract class CompoundEChangeImpl extends EChangeImpl implements CompoundEChange {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected CompoundEChangeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CompoundPackage.Literals.COMPOUND_ECHANGE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public EList<AtomicEChange> getAtomicChanges() {
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
        case CompoundPackage.COMPOUND_ECHANGE___GET_ATOMIC_CHANGES:
            return getAtomicChanges();
        }
        return super.eInvoke(operationID, arguments);
    }

} // CompoundEChangeImpl
