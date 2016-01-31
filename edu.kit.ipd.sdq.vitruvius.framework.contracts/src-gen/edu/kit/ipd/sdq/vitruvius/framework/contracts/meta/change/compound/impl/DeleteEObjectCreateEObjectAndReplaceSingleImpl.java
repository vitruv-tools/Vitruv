/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.DeleteEObjectCreateEObjectAndReplaceSingle;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReplaceSingleValuedEReference;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Delete EObject Create EObject And Replace Single</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class DeleteEObjectCreateEObjectAndReplaceSingleImpl<T extends EObject> extends DeleteEObjectCreateEObjectAndReplaceImpl<T, ReplaceSingleValuedEReference<T>> implements DeleteEObjectCreateEObjectAndReplaceSingle<T> {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DeleteEObjectCreateEObjectAndReplaceSingleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CompoundPackage.Literals.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_SINGLE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * This is specialized for the more specific type known in this context.
     * @generated
     */
    @Override
    public NotificationChain basicSetReplaceChange(ReplaceSingleValuedEReference<T> newReplaceChange, NotificationChain msgs) {
        return super.basicSetReplaceChange(newReplaceChange, msgs);
    }

} //DeleteEObjectCreateEObjectAndReplaceSingleImpl
