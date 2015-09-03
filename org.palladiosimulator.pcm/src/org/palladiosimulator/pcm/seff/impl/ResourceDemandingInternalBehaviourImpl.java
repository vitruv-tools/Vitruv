/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.SeffPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Resource Demanding Internal Behaviour</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.impl.ResourceDemandingInternalBehaviourImpl#getBasicComponent_ResourceDemandingInternalBehaviour
 * <em>Basic Component Resource Demanding Internal Behaviour</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ResourceDemandingInternalBehaviourImpl extends ResourceDemandingBehaviourImpl
        implements ResourceDemandingInternalBehaviour {

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
    protected ResourceDemandingInternalBehaviourImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SeffPackage.Literals.RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public BasicComponent getBasicComponent_ResourceDemandingInternalBehaviour() {
        return (BasicComponent) this.eDynamicGet(
                SeffPackage.RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR__BASIC_COMPONENT_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR,
                SeffPackage.Literals.RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR__BASIC_COMPONENT_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetBasicComponent_ResourceDemandingInternalBehaviour(
            final BasicComponent newBasicComponent_ResourceDemandingInternalBehaviour, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newBasicComponent_ResourceDemandingInternalBehaviour,
                SeffPackage.RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR__BASIC_COMPONENT_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR,
                msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setBasicComponent_ResourceDemandingInternalBehaviour(
            final BasicComponent newBasicComponent_ResourceDemandingInternalBehaviour) {
        this.eDynamicSet(
                SeffPackage.RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR__BASIC_COMPONENT_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR,
                SeffPackage.Literals.RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR__BASIC_COMPONENT_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR,
                newBasicComponent_ResourceDemandingInternalBehaviour);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case SeffPackage.RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR__BASIC_COMPONENT_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetBasicComponent_ResourceDemandingInternalBehaviour((BasicComponent) otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID,
            final NotificationChain msgs) {
        switch (featureID) {
        case SeffPackage.RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR__BASIC_COMPONENT_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR:
            return this.basicSetBasicComponent_ResourceDemandingInternalBehaviour(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(final NotificationChain msgs) {
        switch (this.eContainerFeatureID()) {
        case SeffPackage.RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR__BASIC_COMPONENT_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR:
            return this.eInternalContainer().eInverseRemove(this,
                    RepositoryPackage.BASIC_COMPONENT__RESOURCE_DEMANDING_INTERNAL_BEHAVIOURS_BASIC_COMPONENT,
                    BasicComponent.class, msgs);
        }
        return super.eBasicRemoveFromContainerFeature(msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case SeffPackage.RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR__BASIC_COMPONENT_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR:
            return this.getBasicComponent_ResourceDemandingInternalBehaviour();
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
        case SeffPackage.RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR__BASIC_COMPONENT_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR:
            this.setBasicComponent_ResourceDemandingInternalBehaviour((BasicComponent) newValue);
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
        case SeffPackage.RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR__BASIC_COMPONENT_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR:
            this.setBasicComponent_ResourceDemandingInternalBehaviour((BasicComponent) null);
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
        case SeffPackage.RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR__BASIC_COMPONENT_RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR:
            return this.getBasicComponent_ResourceDemandingInternalBehaviour() != null;
        }
        return super.eIsSet(featureID);
    }

} // ResourceDemandingInternalBehaviourImpl
