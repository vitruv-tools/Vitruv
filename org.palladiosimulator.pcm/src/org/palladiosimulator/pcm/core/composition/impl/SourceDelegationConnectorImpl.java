/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition.impl;

import org.eclipse.emf.ecore.EClass;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionPackage;
import org.palladiosimulator.pcm.core.composition.SourceDelegationConnector;
import org.palladiosimulator.pcm.repository.SourceRole;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Source Delegation Connector</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.SourceDelegationConnectorImpl#getInnerSourceRole__SourceRole
 * <em>Inner Source Role Source Role</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.SourceDelegationConnectorImpl#getOuterSourceRole__SourceRole
 * <em>Outer Source Role Source Role</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.SourceDelegationConnectorImpl#getAssemblyContext__SourceDelegationConnector
 * <em>Assembly Context Source Delegation Connector</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SourceDelegationConnectorImpl extends DelegationConnectorImpl implements SourceDelegationConnector {

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
    protected SourceDelegationConnectorImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CompositionPackage.Literals.SOURCE_DELEGATION_CONNECTOR;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SourceRole getInnerSourceRole__SourceRole() {
        return (SourceRole) this.eDynamicGet(
                CompositionPackage.SOURCE_DELEGATION_CONNECTOR__INNER_SOURCE_ROLE_SOURCE_ROLE,
                CompositionPackage.Literals.SOURCE_DELEGATION_CONNECTOR__INNER_SOURCE_ROLE_SOURCE_ROLE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public SourceRole basicGetInnerSourceRole__SourceRole() {
        return (SourceRole) this.eDynamicGet(
                CompositionPackage.SOURCE_DELEGATION_CONNECTOR__INNER_SOURCE_ROLE_SOURCE_ROLE,
                CompositionPackage.Literals.SOURCE_DELEGATION_CONNECTOR__INNER_SOURCE_ROLE_SOURCE_ROLE, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setInnerSourceRole__SourceRole(final SourceRole newInnerSourceRole__SourceRole) {
        this.eDynamicSet(CompositionPackage.SOURCE_DELEGATION_CONNECTOR__INNER_SOURCE_ROLE_SOURCE_ROLE,
                CompositionPackage.Literals.SOURCE_DELEGATION_CONNECTOR__INNER_SOURCE_ROLE_SOURCE_ROLE,
                newInnerSourceRole__SourceRole);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SourceRole getOuterSourceRole__SourceRole() {
        return (SourceRole) this.eDynamicGet(
                CompositionPackage.SOURCE_DELEGATION_CONNECTOR__OUTER_SOURCE_ROLE_SOURCE_ROLE,
                CompositionPackage.Literals.SOURCE_DELEGATION_CONNECTOR__OUTER_SOURCE_ROLE_SOURCE_ROLE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public SourceRole basicGetOuterSourceRole__SourceRole() {
        return (SourceRole) this.eDynamicGet(
                CompositionPackage.SOURCE_DELEGATION_CONNECTOR__OUTER_SOURCE_ROLE_SOURCE_ROLE,
                CompositionPackage.Literals.SOURCE_DELEGATION_CONNECTOR__OUTER_SOURCE_ROLE_SOURCE_ROLE, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setOuterSourceRole__SourceRole(final SourceRole newOuterSourceRole__SourceRole) {
        this.eDynamicSet(CompositionPackage.SOURCE_DELEGATION_CONNECTOR__OUTER_SOURCE_ROLE_SOURCE_ROLE,
                CompositionPackage.Literals.SOURCE_DELEGATION_CONNECTOR__OUTER_SOURCE_ROLE_SOURCE_ROLE,
                newOuterSourceRole__SourceRole);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public AssemblyContext getAssemblyContext__SourceDelegationConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.SOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_SOURCE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.SOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_SOURCE_DELEGATION_CONNECTOR,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public AssemblyContext basicGetAssemblyContext__SourceDelegationConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.SOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_SOURCE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.SOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_SOURCE_DELEGATION_CONNECTOR,
                false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setAssemblyContext__SourceDelegationConnector(
            final AssemblyContext newAssemblyContext__SourceDelegationConnector) {
        this.eDynamicSet(CompositionPackage.SOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_SOURCE_DELEGATION_CONNECTOR,
                CompositionPackage.Literals.SOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_SOURCE_DELEGATION_CONNECTOR,
                newAssemblyContext__SourceDelegationConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case CompositionPackage.SOURCE_DELEGATION_CONNECTOR__INNER_SOURCE_ROLE_SOURCE_ROLE:
            if (resolve) {
                return this.getInnerSourceRole__SourceRole();
            }
            return this.basicGetInnerSourceRole__SourceRole();
        case CompositionPackage.SOURCE_DELEGATION_CONNECTOR__OUTER_SOURCE_ROLE_SOURCE_ROLE:
            if (resolve) {
                return this.getOuterSourceRole__SourceRole();
            }
            return this.basicGetOuterSourceRole__SourceRole();
        case CompositionPackage.SOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_SOURCE_DELEGATION_CONNECTOR:
            if (resolve) {
                return this.getAssemblyContext__SourceDelegationConnector();
            }
            return this.basicGetAssemblyContext__SourceDelegationConnector();
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
        case CompositionPackage.SOURCE_DELEGATION_CONNECTOR__INNER_SOURCE_ROLE_SOURCE_ROLE:
            this.setInnerSourceRole__SourceRole((SourceRole) newValue);
            return;
        case CompositionPackage.SOURCE_DELEGATION_CONNECTOR__OUTER_SOURCE_ROLE_SOURCE_ROLE:
            this.setOuterSourceRole__SourceRole((SourceRole) newValue);
            return;
        case CompositionPackage.SOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_SOURCE_DELEGATION_CONNECTOR:
            this.setAssemblyContext__SourceDelegationConnector((AssemblyContext) newValue);
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
        case CompositionPackage.SOURCE_DELEGATION_CONNECTOR__INNER_SOURCE_ROLE_SOURCE_ROLE:
            this.setInnerSourceRole__SourceRole((SourceRole) null);
            return;
        case CompositionPackage.SOURCE_DELEGATION_CONNECTOR__OUTER_SOURCE_ROLE_SOURCE_ROLE:
            this.setOuterSourceRole__SourceRole((SourceRole) null);
            return;
        case CompositionPackage.SOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_SOURCE_DELEGATION_CONNECTOR:
            this.setAssemblyContext__SourceDelegationConnector((AssemblyContext) null);
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
        case CompositionPackage.SOURCE_DELEGATION_CONNECTOR__INNER_SOURCE_ROLE_SOURCE_ROLE:
            return this.basicGetInnerSourceRole__SourceRole() != null;
        case CompositionPackage.SOURCE_DELEGATION_CONNECTOR__OUTER_SOURCE_ROLE_SOURCE_ROLE:
            return this.basicGetOuterSourceRole__SourceRole() != null;
        case CompositionPackage.SOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_SOURCE_DELEGATION_CONNECTOR:
            return this.basicGetAssemblyContext__SourceDelegationConnector() != null;
        }
        return super.eIsSet(featureID);
    }

} // SourceDelegationConnectorImpl
