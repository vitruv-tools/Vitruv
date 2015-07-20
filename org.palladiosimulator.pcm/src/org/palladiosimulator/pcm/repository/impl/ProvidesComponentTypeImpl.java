/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository.impl;

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
import org.palladiosimulator.pcm.repository.ProvidesComponentType;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.repository.util.RepositoryValidator;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Provides Component Type</b></em>'. <!-- end-user-doc -->
 *
 * @generated
 */
public class ProvidesComponentTypeImpl extends RepositoryComponentImpl implements ProvidesComponentType {

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
    protected ProvidesComponentTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RepositoryPackage.Literals.PROVIDES_COMPONENT_TYPE;
    }

    /**
     * The cached OCL expression body for the '
     * {@link #AtLeastOneInterfaceHasToBeProvidedByAUsefullProvidesComponentType(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>At Least One Interface Has To Be Provided By AUsefull Provides Component Type</em>}'
     * operation. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #AtLeastOneInterfaceHasToBeProvidedByAUsefullProvidesComponentType(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static final String AT_LEAST_ONE_INTERFACE_HAS_TO_BE_PROVIDED_BY_AUSEFULL_PROVIDES_COMPONENT_TYPE__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP = "self.oclIsTypeOf(ProvidesComponentType)\n"
            + "implies\n" + "self.providedRoles_InterfaceProvidingEntity->size() >= 1";
    /**
     * The cached OCL invariant for the '
     * {@link #AtLeastOneInterfaceHasToBeProvidedByAUsefullProvidesComponentType(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>At Least One Interface Has To Be Provided By AUsefull Provides Component Type</em>}'
     * invariant operation. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #AtLeastOneInterfaceHasToBeProvidedByAUsefullProvidesComponentType(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static Constraint AT_LEAST_ONE_INTERFACE_HAS_TO_BE_PROVIDED_BY_AUSEFULL_PROVIDES_COMPONENT_TYPE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean AtLeastOneInterfaceHasToBeProvidedByAUsefullProvidesComponentType(final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (AT_LEAST_ONE_INTERFACE_HAS_TO_BE_PROVIDED_BY_AUSEFULL_PROVIDES_COMPONENT_TYPE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV == null) {
            final OCL.Helper helper = EOCL_ENV.createOCLHelper();
            helper.setContext(RepositoryPackage.Literals.PROVIDES_COMPONENT_TYPE);
            try {
                AT_LEAST_ONE_INTERFACE_HAS_TO_BE_PROVIDED_BY_AUSEFULL_PROVIDES_COMPONENT_TYPE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV = helper
                        .createInvariant(
                                AT_LEAST_ONE_INTERFACE_HAS_TO_BE_PROVIDED_BY_AUSEFULL_PROVIDES_COMPONENT_TYPE__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP);
            } catch (final ParserException pe) {
                throw new UnsupportedOperationException(pe.getLocalizedMessage());
            }
        }
        if (!EOCL_ENV
                .createQuery(
                        AT_LEAST_ONE_INTERFACE_HAS_TO_BE_PROVIDED_BY_AUSEFULL_PROVIDES_COMPONENT_TYPE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV)
                .check(this)) {
            if (diagnostics != null) {
                diagnostics
                        .add(new BasicDiagnostic(Diagnostic.ERROR, RepositoryValidator.DIAGNOSTIC_SOURCE,
                                RepositoryValidator.PROVIDES_COMPONENT_TYPE__AT_LEAST_ONE_INTERFACE_HAS_TO_BE_PROVIDED_BY_AUSEFULL_PROVIDES_COMPONENT_TYPE,
                                EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic",
                                        new Object[] {
                                                "AtLeastOneInterfaceHasToBeProvidedByAUsefullProvidesComponentType",
                                                EObjectValidator.getObjectLabel(this, context) }),
                        new Object[] { this }));
            }
            return false;
        }
        return true;
    }

    /**
     * The cached environment for evaluating OCL expressions. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected static final OCL EOCL_ENV = OCL.newInstance();

} // ProvidesComponentTypeImpl
