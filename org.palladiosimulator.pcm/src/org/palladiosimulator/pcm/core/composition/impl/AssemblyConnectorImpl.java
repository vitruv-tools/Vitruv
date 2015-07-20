/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition.impl;

import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.OCL;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionPackage;
import org.palladiosimulator.pcm.core.composition.util.CompositionValidator;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Assembly Connector</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyConnectorImpl#getRequiringAssemblyContext_AssemblyConnector
 * <em>Requiring Assembly Context Assembly Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyConnectorImpl#getProvidingAssemblyContext_AssemblyConnector
 * <em>Providing Assembly Context Assembly Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyConnectorImpl#getProvidedRole_AssemblyConnector
 * <em>Provided Role Assembly Connector</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyConnectorImpl#getRequiredRole_AssemblyConnector
 * <em>Required Role Assembly Connector</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AssemblyConnectorImpl extends ConnectorImpl implements AssemblyConnector {

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
    protected AssemblyConnectorImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CompositionPackage.Literals.ASSEMBLY_CONNECTOR;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AssemblyContext getRequiringAssemblyContext_AssemblyConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR, true,
                true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public AssemblyContext basicGetRequiringAssemblyContext_AssemblyConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR, false,
                true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setRequiringAssemblyContext_AssemblyConnector(
            final AssemblyContext newRequiringAssemblyContext_AssemblyConnector) {
        this.eDynamicSet(CompositionPackage.ASSEMBLY_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR,
                newRequiringAssemblyContext_AssemblyConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AssemblyContext getProvidingAssemblyContext_AssemblyConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR, true,
                true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public AssemblyContext basicGetProvidingAssemblyContext_AssemblyConnector() {
        return (AssemblyContext) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR, false,
                true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setProvidingAssemblyContext_AssemblyConnector(
            final AssemblyContext newProvidingAssemblyContext_AssemblyConnector) {
        this.eDynamicSet(CompositionPackage.ASSEMBLY_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR,
                newProvidingAssemblyContext_AssemblyConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public OperationProvidedRole getProvidedRole_AssemblyConnector() {
        return (OperationProvidedRole) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_CONNECTOR, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public OperationProvidedRole basicGetProvidedRole_AssemblyConnector() {
        return (OperationProvidedRole) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_CONNECTOR, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setProvidedRole_AssemblyConnector(final OperationProvidedRole newProvidedRole_AssemblyConnector) {
        this.eDynamicSet(CompositionPackage.ASSEMBLY_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_CONNECTOR,
                newProvidedRole_AssemblyConnector);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public OperationRequiredRole getRequiredRole_AssemblyConnector() {
        return (OperationRequiredRole) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_CONNECTOR, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public OperationRequiredRole basicGetRequiredRole_AssemblyConnector() {
        return (OperationRequiredRole) this.eDynamicGet(
                CompositionPackage.ASSEMBLY_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_CONNECTOR, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setRequiredRole_AssemblyConnector(final OperationRequiredRole newRequiredRole_AssemblyConnector) {
        this.eDynamicSet(CompositionPackage.ASSEMBLY_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_CONNECTOR,
                CompositionPackage.Literals.ASSEMBLY_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_CONNECTOR,
                newRequiredRole_AssemblyConnector);
    }

    /**
     * The cached OCL expression body for the '
     * {@link #AssemblyConnectorsReferencedProvidedRolesAndChildContextMustMatch(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Assembly Connectors Referenced Provided Roles And Child Context Must Match</em>}'
     * operation. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #AssemblyConnectorsReferencedProvidedRolesAndChildContextMustMatch(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static final String ASSEMBLY_CONNECTORS_REFERENCED_PROVIDED_ROLES_AND_CHILD_CONTEXT_MUST_MATCH__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP = "self.providingAssemblyContext_AssemblyConnector.encapsulatedComponent__AssemblyContext.providedRoles_InterfaceProvidingEntity->includes(self.providedRole_AssemblyConnector)\n"
            + "\n" + "";
    /**
     * The cached OCL invariant for the '
     * {@link #AssemblyConnectorsReferencedProvidedRolesAndChildContextMustMatch(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Assembly Connectors Referenced Provided Roles And Child Context Must Match</em>}'
     * invariant operation. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #AssemblyConnectorsReferencedProvidedRolesAndChildContextMustMatch(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static Constraint ASSEMBLY_CONNECTORS_REFERENCED_PROVIDED_ROLES_AND_CHILD_CONTEXT_MUST_MATCH__DIAGNOSTIC_CHAIN_MAP__EOCL_INV;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean AssemblyConnectorsReferencedProvidedRolesAndChildContextMustMatch(final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (ASSEMBLY_CONNECTORS_REFERENCED_PROVIDED_ROLES_AND_CHILD_CONTEXT_MUST_MATCH__DIAGNOSTIC_CHAIN_MAP__EOCL_INV == null) {
            final OCL.Helper helper = EOCL_ENV.createOCLHelper();
            helper.setContext(CompositionPackage.Literals.ASSEMBLY_CONNECTOR);
            try {
                ASSEMBLY_CONNECTORS_REFERENCED_PROVIDED_ROLES_AND_CHILD_CONTEXT_MUST_MATCH__DIAGNOSTIC_CHAIN_MAP__EOCL_INV = helper
                        .createInvariant(
                                ASSEMBLY_CONNECTORS_REFERENCED_PROVIDED_ROLES_AND_CHILD_CONTEXT_MUST_MATCH__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP);
            } catch (final ParserException pe) {
                throw new UnsupportedOperationException(pe.getLocalizedMessage());
            }
        }
        if (!EOCL_ENV
                .createQuery(
                        ASSEMBLY_CONNECTORS_REFERENCED_PROVIDED_ROLES_AND_CHILD_CONTEXT_MUST_MATCH__DIAGNOSTIC_CHAIN_MAP__EOCL_INV)
                .check(this)) {
            if (diagnostics != null) {
                diagnostics
                        .add(new BasicDiagnostic(Diagnostic.ERROR, CompositionValidator.DIAGNOSTIC_SOURCE,
                                CompositionValidator.ASSEMBLY_CONNECTOR__ASSEMBLY_CONNECTORS_REFERENCED_PROVIDED_ROLES_AND_CHILD_CONTEXT_MUST_MATCH,
                                EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic",
                                        new Object[] {
                                                "AssemblyConnectorsReferencedProvidedRolesAndChildContextMustMatch",
                                                EObjectValidator.getObjectLabel(this, context) }),
                        new Object[] { this }));
            }
            return false;
        }
        return true;
    }

    /**
     * The cached OCL expression body for the '
     * {@link #AssemblyConnectorsReferencedRequiredRoleAndChildContextMustMatch(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Assembly Connectors Referenced Required Role And Child Context Must Match</em>}'
     * operation. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #AssemblyConnectorsReferencedRequiredRoleAndChildContextMustMatch(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static final String ASSEMBLY_CONNECTORS_REFERENCED_REQUIRED_ROLE_AND_CHILD_CONTEXT_MUST_MATCH__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP = "self.requiringAssemblyContext_AssemblyConnector.encapsulatedComponent__AssemblyContext.requiredRoles_InterfaceRequiringEntity->includes(self.requiredRole_AssemblyConnector)\n"
            + "\n" + "";
    /**
     * The cached OCL invariant for the '
     * {@link #AssemblyConnectorsReferencedRequiredRoleAndChildContextMustMatch(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Assembly Connectors Referenced Required Role And Child Context Must Match</em>}'
     * invariant operation. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #AssemblyConnectorsReferencedRequiredRoleAndChildContextMustMatch(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static Constraint ASSEMBLY_CONNECTORS_REFERENCED_REQUIRED_ROLE_AND_CHILD_CONTEXT_MUST_MATCH__DIAGNOSTIC_CHAIN_MAP__EOCL_INV;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean AssemblyConnectorsReferencedRequiredRoleAndChildContextMustMatch(final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (ASSEMBLY_CONNECTORS_REFERENCED_REQUIRED_ROLE_AND_CHILD_CONTEXT_MUST_MATCH__DIAGNOSTIC_CHAIN_MAP__EOCL_INV == null) {
            final OCL.Helper helper = EOCL_ENV.createOCLHelper();
            helper.setContext(CompositionPackage.Literals.ASSEMBLY_CONNECTOR);
            try {
                ASSEMBLY_CONNECTORS_REFERENCED_REQUIRED_ROLE_AND_CHILD_CONTEXT_MUST_MATCH__DIAGNOSTIC_CHAIN_MAP__EOCL_INV = helper
                        .createInvariant(
                                ASSEMBLY_CONNECTORS_REFERENCED_REQUIRED_ROLE_AND_CHILD_CONTEXT_MUST_MATCH__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP);
            } catch (final ParserException pe) {
                throw new UnsupportedOperationException(pe.getLocalizedMessage());
            }
        }
        if (!EOCL_ENV
                .createQuery(
                        ASSEMBLY_CONNECTORS_REFERENCED_REQUIRED_ROLE_AND_CHILD_CONTEXT_MUST_MATCH__DIAGNOSTIC_CHAIN_MAP__EOCL_INV)
                .check(this)) {
            if (diagnostics != null) {
                diagnostics
                        .add(new BasicDiagnostic(Diagnostic.ERROR, CompositionValidator.DIAGNOSTIC_SOURCE,
                                CompositionValidator.ASSEMBLY_CONNECTOR__ASSEMBLY_CONNECTORS_REFERENCED_REQUIRED_ROLE_AND_CHILD_CONTEXT_MUST_MATCH,
                                EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic",
                                        new Object[] {
                                                "AssemblyConnectorsReferencedRequiredRoleAndChildContextMustMatch",
                                                EObjectValidator.getObjectLabel(this, context) }),
                        new Object[] { this }));
            }
            return false;
        }
        return true;
    }

    /**
     * The cached OCL expression body for the '
     * {@link #AssemblyConnectorsReferencedInterfacesMustMatch(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Assembly Connectors Referenced Interfaces Must Match</em>}' operation. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #AssemblyConnectorsReferencedInterfacesMustMatch(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static final String ASSEMBLY_CONNECTORS_REFERENCED_INTERFACES_MUST_MATCH__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP = "self.providedRole_AssemblyConnector.providedInterface__OperationProvidedRole = self.requiredRole_AssemblyConnector.requiredInterface__OperationRequiredRole";
    /**
     * The cached OCL invariant for the '
     * {@link #AssemblyConnectorsReferencedInterfacesMustMatch(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Assembly Connectors Referenced Interfaces Must Match</em>}' invariant operation. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #AssemblyConnectorsReferencedInterfacesMustMatch(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static Constraint ASSEMBLY_CONNECTORS_REFERENCED_INTERFACES_MUST_MATCH__DIAGNOSTIC_CHAIN_MAP__EOCL_INV;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean AssemblyConnectorsReferencedInterfacesMustMatch(final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (ASSEMBLY_CONNECTORS_REFERENCED_INTERFACES_MUST_MATCH__DIAGNOSTIC_CHAIN_MAP__EOCL_INV == null) {
            final OCL.Helper helper = EOCL_ENV.createOCLHelper();
            helper.setContext(CompositionPackage.Literals.ASSEMBLY_CONNECTOR);
            try {
                ASSEMBLY_CONNECTORS_REFERENCED_INTERFACES_MUST_MATCH__DIAGNOSTIC_CHAIN_MAP__EOCL_INV = helper
                        .createInvariant(
                                ASSEMBLY_CONNECTORS_REFERENCED_INTERFACES_MUST_MATCH__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP);
            } catch (final ParserException pe) {
                throw new UnsupportedOperationException(pe.getLocalizedMessage());
            }
        }
        if (!EOCL_ENV.createQuery(ASSEMBLY_CONNECTORS_REFERENCED_INTERFACES_MUST_MATCH__DIAGNOSTIC_CHAIN_MAP__EOCL_INV)
                .check(this)) {
            if (diagnostics != null) {
                diagnostics
                        .add(new BasicDiagnostic(Diagnostic.ERROR, CompositionValidator.DIAGNOSTIC_SOURCE,
                                CompositionValidator.ASSEMBLY_CONNECTOR__ASSEMBLY_CONNECTORS_REFERENCED_INTERFACES_MUST_MATCH,
                                EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic",
                                        new Object[] { "AssemblyConnectorsReferencedInterfacesMustMatch",
                                                EObjectValidator.getObjectLabel(this, context) }),
                        new Object[] { this }));
            }
            return false;
        }
        return true;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case CompositionPackage.ASSEMBLY_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR:
            if (resolve) {
                return this.getRequiringAssemblyContext_AssemblyConnector();
            }
            return this.basicGetRequiringAssemblyContext_AssemblyConnector();
        case CompositionPackage.ASSEMBLY_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR:
            if (resolve) {
                return this.getProvidingAssemblyContext_AssemblyConnector();
            }
            return this.basicGetProvidingAssemblyContext_AssemblyConnector();
        case CompositionPackage.ASSEMBLY_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_CONNECTOR:
            if (resolve) {
                return this.getProvidedRole_AssemblyConnector();
            }
            return this.basicGetProvidedRole_AssemblyConnector();
        case CompositionPackage.ASSEMBLY_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_CONNECTOR:
            if (resolve) {
                return this.getRequiredRole_AssemblyConnector();
            }
            return this.basicGetRequiredRole_AssemblyConnector();
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
        case CompositionPackage.ASSEMBLY_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR:
            this.setRequiringAssemblyContext_AssemblyConnector((AssemblyContext) newValue);
            return;
        case CompositionPackage.ASSEMBLY_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR:
            this.setProvidingAssemblyContext_AssemblyConnector((AssemblyContext) newValue);
            return;
        case CompositionPackage.ASSEMBLY_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_CONNECTOR:
            this.setProvidedRole_AssemblyConnector((OperationProvidedRole) newValue);
            return;
        case CompositionPackage.ASSEMBLY_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_CONNECTOR:
            this.setRequiredRole_AssemblyConnector((OperationRequiredRole) newValue);
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
        case CompositionPackage.ASSEMBLY_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR:
            this.setRequiringAssemblyContext_AssemblyConnector((AssemblyContext) null);
            return;
        case CompositionPackage.ASSEMBLY_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR:
            this.setProvidingAssemblyContext_AssemblyConnector((AssemblyContext) null);
            return;
        case CompositionPackage.ASSEMBLY_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_CONNECTOR:
            this.setProvidedRole_AssemblyConnector((OperationProvidedRole) null);
            return;
        case CompositionPackage.ASSEMBLY_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_CONNECTOR:
            this.setRequiredRole_AssemblyConnector((OperationRequiredRole) null);
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
        case CompositionPackage.ASSEMBLY_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR:
            return this.basicGetRequiringAssemblyContext_AssemblyConnector() != null;
        case CompositionPackage.ASSEMBLY_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR:
            return this.basicGetProvidingAssemblyContext_AssemblyConnector() != null;
        case CompositionPackage.ASSEMBLY_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_CONNECTOR:
            return this.basicGetProvidedRole_AssemblyConnector() != null;
        case CompositionPackage.ASSEMBLY_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_CONNECTOR:
            return this.basicGetRequiredRole_AssemblyConnector() != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * The cached environment for evaluating OCL expressions. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected static final OCL EOCL_ENV = OCL.newInstance();

} // AssemblyConnectorImpl
