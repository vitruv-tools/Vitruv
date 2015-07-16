/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition.impl;

import org.eclipse.emf.ecore.EClass;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector;
import org.palladiosimulator.pcm.core.composition.CompositionPackage;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Assembly Infrastructure Connector</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyInfrastructureConnectorImpl#getProvidedRole__AssemblyInfrastructureConnector
 * <em>Provided Role Assembly Infrastructure Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyInfrastructureConnectorImpl#getRequiredRole__AssemblyInfrastructureConnector
 * <em>Required Role Assembly Infrastructure Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyInfrastructureConnectorImpl#getProvidingAssemblyContext__AssemblyInfrastructureConnector
 * <em>Providing Assembly Context Assembly Infrastructure Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyInfrastructureConnectorImpl#getRequiringAssemblyContext__AssemblyInfrastructureConnector
 * <em>Requiring Assembly Context Assembly Infrastructure Connector</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AssemblyInfrastructureConnectorImpl extends ConnectorImpl implements AssemblyInfrastructureConnector {

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
    protected AssemblyInfrastructureConnectorImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CompositionPackage.Literals.ASSEMBLY_INFRASTRUCTURE_CONNECTOR;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public InfrastructureProvidedRole getProvidedRole__AssemblyInfrastructureConnector() {
        return (InfrastructureProvidedRole) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public InfrastructureProvidedRole basicGetProvidedRole__AssemblyInfrastructureConnector() {
        return (InfrastructureProvidedRole) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setProvidedRole__AssemblyInfrastructureConnector(
            final InfrastructureProvidedRole newProvidedRole__AssemblyInfrastructureConnector) {
        this.eDynamicSet(
                CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                newProvidedRole__AssemblyInfrastructureConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public InfrastructureRequiredRole getRequiredRole__AssemblyInfrastructureConnector() {
        return (InfrastructureRequiredRole) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public InfrastructureRequiredRole basicGetRequiredRole__AssemblyInfrastructureConnector() {
        return (InfrastructureRequiredRole) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setRequiredRole__AssemblyInfrastructureConnector(
            final InfrastructureRequiredRole newRequiredRole__AssemblyInfrastructureConnector) {
        this.eDynamicSet(
                CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                newRequiredRole__AssemblyInfrastructureConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public AssemblyContext getProvidingAssemblyContext__AssemblyInfrastructureConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public AssemblyContext basicGetProvidingAssemblyContext__AssemblyInfrastructureConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setProvidingAssemblyContext__AssemblyInfrastructureConnector(
            final AssemblyContext newProvidingAssemblyContext__AssemblyInfrastructureConnector) {
        this.eDynamicSet(
                CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                newProvidingAssemblyContext__AssemblyInfrastructureConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public AssemblyContext getRequiringAssemblyContext__AssemblyInfrastructureConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public AssemblyContext basicGetRequiringAssemblyContext__AssemblyInfrastructureConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setRequiringAssemblyContext__AssemblyInfrastructureConnector(
            final AssemblyContext newRequiringAssemblyContext__AssemblyInfrastructureConnector) {
        this.eDynamicSet(
                CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR,
                newRequiringAssemblyContext__AssemblyInfrastructureConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR:
            if (resolve) {
                return this.getProvidedRole__AssemblyInfrastructureConnector();
            }
            return this.basicGetProvidedRole__AssemblyInfrastructureConnector();
        case CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR:
            if (resolve) {
                return this.getRequiredRole__AssemblyInfrastructureConnector();
            }
            return this.basicGetRequiredRole__AssemblyInfrastructureConnector();
        case CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR:
            if (resolve) {
                return this.getProvidingAssemblyContext__AssemblyInfrastructureConnector();
            }
            return this.basicGetProvidingAssemblyContext__AssemblyInfrastructureConnector();
        case CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR:
            if (resolve) {
                return this.getRequiringAssemblyContext__AssemblyInfrastructureConnector();
            }
            return this.basicGetRequiringAssemblyContext__AssemblyInfrastructureConnector();
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
        case CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR:
            this.setProvidedRole__AssemblyInfrastructureConnector((InfrastructureProvidedRole) newValue);
            return;
        case CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR:
            this.setRequiredRole__AssemblyInfrastructureConnector((InfrastructureRequiredRole) newValue);
            return;
        case CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR:
            this.setProvidingAssemblyContext__AssemblyInfrastructureConnector((AssemblyContext) newValue);
            return;
        case CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR:
            this.setRequiringAssemblyContext__AssemblyInfrastructureConnector((AssemblyContext) newValue);
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
        case CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR:
            this.setProvidedRole__AssemblyInfrastructureConnector((InfrastructureProvidedRole) null);
            return;
        case CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR:
            this.setRequiredRole__AssemblyInfrastructureConnector((InfrastructureRequiredRole) null);
            return;
        case CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR:
            this.setProvidingAssemblyContext__AssemblyInfrastructureConnector((AssemblyContext) null);
            return;
        case CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR:
            this.setRequiringAssemblyContext__AssemblyInfrastructureConnector((AssemblyContext) null);
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
        case CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR:
            return this.basicGetProvidedRole__AssemblyInfrastructureConnector() != null;
        case CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR:
            return this.basicGetRequiredRole__AssemblyInfrastructureConnector() != null;
        case CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR:
            return this.basicGetProvidingAssemblyContext__AssemblyInfrastructureConnector() != null;
        case CompositionPackage.ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR:
            return this.basicGetRequiringAssemblyContext__AssemblyInfrastructureConnector() != null;
        }
        return super.eIsSet(featureID);
    }

} // AssemblyInfrastructureConnectorImpl
