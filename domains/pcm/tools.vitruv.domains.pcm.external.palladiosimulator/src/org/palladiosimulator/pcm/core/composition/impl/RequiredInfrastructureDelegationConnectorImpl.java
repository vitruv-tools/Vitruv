/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition.impl;

import org.eclipse.emf.ecore.EClass;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionPackage;
import org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Required Infrastructure Delegation Connector</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.RequiredInfrastructureDelegationConnectorImpl#getInnerRequiredRole__RequiredInfrastructureDelegationConnector
 * <em>Inner Required Role Required Infrastructure Delegation Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.RequiredInfrastructureDelegationConnectorImpl#getOuterRequiredRole__RequiredInfrastructureDelegationConnector
 * <em>Outer Required Role Required Infrastructure Delegation Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.RequiredInfrastructureDelegationConnectorImpl#getAssemblyContext__RequiredInfrastructureDelegationConnector
 * <em>Assembly Context Required Infrastructure Delegation Connector</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RequiredInfrastructureDelegationConnectorImpl extends DelegationConnectorImpl
        implements RequiredInfrastructureDelegationConnector {

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
    protected RequiredInfrastructureDelegationConnectorImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CompositionPackage.Literals.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public InfrastructureRequiredRole getInnerRequiredRole__RequiredInfrastructureDelegationConnector() {
        return (InfrastructureRequiredRole) this.eDynamicGet(
                CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public InfrastructureRequiredRole basicGetInnerRequiredRole__RequiredInfrastructureDelegationConnector() {
        return (InfrastructureRequiredRole) this.eDynamicGet(
                CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setInnerRequiredRole__RequiredInfrastructureDelegationConnector(
            final InfrastructureRequiredRole newInnerRequiredRole__RequiredInfrastructureDelegationConnector) {
        this.eDynamicSet(
                CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR,
                newInnerRequiredRole__RequiredInfrastructureDelegationConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public InfrastructureRequiredRole getOuterRequiredRole__RequiredInfrastructureDelegationConnector() {
        return (InfrastructureRequiredRole) this.eDynamicGet(
                CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public InfrastructureRequiredRole basicGetOuterRequiredRole__RequiredInfrastructureDelegationConnector() {
        return (InfrastructureRequiredRole) this.eDynamicGet(
                CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setOuterRequiredRole__RequiredInfrastructureDelegationConnector(
            final InfrastructureRequiredRole newOuterRequiredRole__RequiredInfrastructureDelegationConnector) {
        this.eDynamicSet(
                CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR,
                newOuterRequiredRole__RequiredInfrastructureDelegationConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AssemblyContext getAssemblyContext__RequiredInfrastructureDelegationConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public AssemblyContext basicGetAssemblyContext__RequiredInfrastructureDelegationConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setAssemblyContext__RequiredInfrastructureDelegationConnector(
            final AssemblyContext newAssemblyContext__RequiredInfrastructureDelegationConnector) {
        this.eDynamicSet(
                CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR,
                newAssemblyContext__RequiredInfrastructureDelegationConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR:
            if (resolve) {
                return this.getInnerRequiredRole__RequiredInfrastructureDelegationConnector();
            }
            return this.basicGetInnerRequiredRole__RequiredInfrastructureDelegationConnector();
        case CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR:
            if (resolve) {
                return this.getOuterRequiredRole__RequiredInfrastructureDelegationConnector();
            }
            return this.basicGetOuterRequiredRole__RequiredInfrastructureDelegationConnector();
        case CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR:
            if (resolve) {
                return this.getAssemblyContext__RequiredInfrastructureDelegationConnector();
            }
            return this.basicGetAssemblyContext__RequiredInfrastructureDelegationConnector();
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
        case CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR:
            this.setInnerRequiredRole__RequiredInfrastructureDelegationConnector((InfrastructureRequiredRole) newValue);
            return;
        case CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR:
            this.setOuterRequiredRole__RequiredInfrastructureDelegationConnector((InfrastructureRequiredRole) newValue);
            return;
        case CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR:
            this.setAssemblyContext__RequiredInfrastructureDelegationConnector((AssemblyContext) newValue);
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
        case CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR:
            this.setInnerRequiredRole__RequiredInfrastructureDelegationConnector((InfrastructureRequiredRole) null);
            return;
        case CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR:
            this.setOuterRequiredRole__RequiredInfrastructureDelegationConnector((InfrastructureRequiredRole) null);
            return;
        case CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR:
            this.setAssemblyContext__RequiredInfrastructureDelegationConnector((AssemblyContext) null);
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
        case CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR:
            return this.basicGetInnerRequiredRole__RequiredInfrastructureDelegationConnector() != null;
        case CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR:
            return this.basicGetOuterRequiredRole__RequiredInfrastructureDelegationConnector() != null;
        case CompositionPackage.REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR:
            return this.basicGetAssemblyContext__RequiredInfrastructureDelegationConnector() != null;
        }
        return super.eIsSet(featureID);
    }

} // RequiredInfrastructureDelegationConnectorImpl
