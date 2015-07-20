/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition.impl;

import org.eclipse.emf.ecore.EClass;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionPackage;
import org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Required Resource Delegation Connector</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.RequiredResourceDelegationConnectorImpl#getAssemblyContext__RequiredResourceDelegationConnector
 * <em>Assembly Context Required Resource Delegation Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.RequiredResourceDelegationConnectorImpl#getInnerRequiredRole__RequiredResourceDelegationConnector
 * <em>Inner Required Role Required Resource Delegation Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.RequiredResourceDelegationConnectorImpl#getOuterRequiredRole__RequiredResourceDelegationConnector
 * <em>Outer Required Role Required Resource Delegation Connector</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RequiredResourceDelegationConnectorImpl extends DelegationConnectorImpl
        implements RequiredResourceDelegationConnector {

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
    protected RequiredResourceDelegationConnectorImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CompositionPackage.Literals.REQUIRED_RESOURCE_DELEGATION_CONNECTOR;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AssemblyContext getAssemblyContext__RequiredResourceDelegationConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_RESOURCE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_RESOURCE_DELEGATION_CONNECTOR,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public AssemblyContext basicGetAssemblyContext__RequiredResourceDelegationConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_RESOURCE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_RESOURCE_DELEGATION_CONNECTOR,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setAssemblyContext__RequiredResourceDelegationConnector(
            final AssemblyContext newAssemblyContext__RequiredResourceDelegationConnector) {
        this.eDynamicSet(
                CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_RESOURCE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_RESOURCE_DELEGATION_CONNECTOR,
                newAssemblyContext__RequiredResourceDelegationConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceRequiredRole getInnerRequiredRole__RequiredResourceDelegationConnector() {
        return (ResourceRequiredRole) this.eDynamicGet(
                CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ResourceRequiredRole basicGetInnerRequiredRole__RequiredResourceDelegationConnector() {
        return (ResourceRequiredRole) this.eDynamicGet(
                CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setInnerRequiredRole__RequiredResourceDelegationConnector(
            final ResourceRequiredRole newInnerRequiredRole__RequiredResourceDelegationConnector) {
        this.eDynamicSet(
                CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR,
                newInnerRequiredRole__RequiredResourceDelegationConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceRequiredRole getOuterRequiredRole__RequiredResourceDelegationConnector() {
        return (ResourceRequiredRole) this.eDynamicGet(
                CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ResourceRequiredRole basicGetOuterRequiredRole__RequiredResourceDelegationConnector() {
        return (ResourceRequiredRole) this.eDynamicGet(
                CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setOuterRequiredRole__RequiredResourceDelegationConnector(
            final ResourceRequiredRole newOuterRequiredRole__RequiredResourceDelegationConnector) {
        this.eDynamicSet(
                CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR,
                newOuterRequiredRole__RequiredResourceDelegationConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_RESOURCE_DELEGATION_CONNECTOR:
            if (resolve) {
                return this.getAssemblyContext__RequiredResourceDelegationConnector();
            }
            return this.basicGetAssemblyContext__RequiredResourceDelegationConnector();
        case CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR:
            if (resolve) {
                return this.getInnerRequiredRole__RequiredResourceDelegationConnector();
            }
            return this.basicGetInnerRequiredRole__RequiredResourceDelegationConnector();
        case CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR:
            if (resolve) {
                return this.getOuterRequiredRole__RequiredResourceDelegationConnector();
            }
            return this.basicGetOuterRequiredRole__RequiredResourceDelegationConnector();
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
        case CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_RESOURCE_DELEGATION_CONNECTOR:
            this.setAssemblyContext__RequiredResourceDelegationConnector((AssemblyContext) newValue);
            return;
        case CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR:
            this.setInnerRequiredRole__RequiredResourceDelegationConnector((ResourceRequiredRole) newValue);
            return;
        case CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR:
            this.setOuterRequiredRole__RequiredResourceDelegationConnector((ResourceRequiredRole) newValue);
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
        case CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_RESOURCE_DELEGATION_CONNECTOR:
            this.setAssemblyContext__RequiredResourceDelegationConnector((AssemblyContext) null);
            return;
        case CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR:
            this.setInnerRequiredRole__RequiredResourceDelegationConnector((ResourceRequiredRole) null);
            return;
        case CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR:
            this.setOuterRequiredRole__RequiredResourceDelegationConnector((ResourceRequiredRole) null);
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
        case CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_RESOURCE_DELEGATION_CONNECTOR:
            return this.basicGetAssemblyContext__RequiredResourceDelegationConnector() != null;
        case CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR:
            return this.basicGetInnerRequiredRole__RequiredResourceDelegationConnector() != null;
        case CompositionPackage.REQUIRED_RESOURCE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR:
            return this.basicGetOuterRequiredRole__RequiredResourceDelegationConnector() != null;
        }
        return super.eIsSet(featureID);
    }

} // RequiredResourceDelegationConnectorImpl
