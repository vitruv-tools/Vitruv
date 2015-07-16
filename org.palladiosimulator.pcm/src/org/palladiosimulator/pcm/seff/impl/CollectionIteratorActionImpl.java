/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.impl;

import org.eclipse.emf.ecore.EClass;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.seff.CollectionIteratorAction;
import org.palladiosimulator.pcm.seff.SeffPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Collection Iterator Action</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.impl.CollectionIteratorActionImpl#getParameter_CollectionIteratorAction
 * <em>Parameter Collection Iterator Action</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CollectionIteratorActionImpl extends AbstractLoopActionImpl implements CollectionIteratorAction {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected CollectionIteratorActionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SeffPackage.Literals.COLLECTION_ITERATOR_ACTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Parameter getParameter_CollectionIteratorAction() {
        return (Parameter) this.eDynamicGet(
                SeffPackage.COLLECTION_ITERATOR_ACTION__PARAMETER_COLLECTION_ITERATOR_ACTION,
                SeffPackage.Literals.COLLECTION_ITERATOR_ACTION__PARAMETER_COLLECTION_ITERATOR_ACTION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public Parameter basicGetParameter_CollectionIteratorAction() {
        return (Parameter) this.eDynamicGet(
                SeffPackage.COLLECTION_ITERATOR_ACTION__PARAMETER_COLLECTION_ITERATOR_ACTION,
                SeffPackage.Literals.COLLECTION_ITERATOR_ACTION__PARAMETER_COLLECTION_ITERATOR_ACTION, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setParameter_CollectionIteratorAction(final Parameter newParameter_CollectionIteratorAction) {
        this.eDynamicSet(SeffPackage.COLLECTION_ITERATOR_ACTION__PARAMETER_COLLECTION_ITERATOR_ACTION,
                SeffPackage.Literals.COLLECTION_ITERATOR_ACTION__PARAMETER_COLLECTION_ITERATOR_ACTION,
                newParameter_CollectionIteratorAction);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case SeffPackage.COLLECTION_ITERATOR_ACTION__PARAMETER_COLLECTION_ITERATOR_ACTION:
            if (resolve) {
                return this.getParameter_CollectionIteratorAction();
            }
            return this.basicGetParameter_CollectionIteratorAction();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eSet(final int featureID, final Object newValue) {
        switch (featureID) {
        case SeffPackage.COLLECTION_ITERATOR_ACTION__PARAMETER_COLLECTION_ITERATOR_ACTION:
            this.setParameter_CollectionIteratorAction((Parameter) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(final int featureID) {
        switch (featureID) {
        case SeffPackage.COLLECTION_ITERATOR_ACTION__PARAMETER_COLLECTION_ITERATOR_ACTION:
            this.setParameter_CollectionIteratorAction((Parameter) null);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(final int featureID) {
        switch (featureID) {
        case SeffPackage.COLLECTION_ITERATOR_ACTION__PARAMETER_COLLECTION_ITERATOR_ACTION:
            return this.basicGetParameter_CollectionIteratorAction() != null;
        }
        return super.eIsSet(featureID);
    }

} // CollectionIteratorActionImpl
