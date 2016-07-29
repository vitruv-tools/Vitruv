/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AtomicEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Atomic EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class AtomicEChangeImpl extends EChangeImpl implements AtomicEChange {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AtomicEChangeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ChangePackage.Literals.ATOMIC_ECHANGE;
    }

} //AtomicEChangeImpl
