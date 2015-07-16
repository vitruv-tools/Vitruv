/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.impl;

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
import org.palladiosimulator.pcm.seff.SeffPackage;
import org.palladiosimulator.pcm.seff.StartAction;
import org.palladiosimulator.pcm.seff.util.SeffValidator;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Start Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class StartActionImpl extends AbstractInternalControlFlowActionImpl implements StartAction {

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
    protected StartActionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SeffPackage.Literals.START_ACTION;
    }

    /**
     * The cached OCL expression body for the '
     * {@link #StartActionPredecessorMustNotBeDefined(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Start Action Predecessor Must Not Be Defined</em>}' operation. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #StartActionPredecessorMustNotBeDefined(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static final String START_ACTION_PREDECESSOR_MUST_NOT_BE_DEFINED__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP = "self.predecessor_AbstractAction.oclIsUndefined()";
    /**
     * The cached OCL invariant for the '
     * {@link #StartActionPredecessorMustNotBeDefined(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Start Action Predecessor Must Not Be Defined</em>}' invariant operation. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #StartActionPredecessorMustNotBeDefined(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static Constraint START_ACTION_PREDECESSOR_MUST_NOT_BE_DEFINED__DIAGNOSTIC_CHAIN_MAP__EOCL_INV;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean StartActionPredecessorMustNotBeDefined(final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (START_ACTION_PREDECESSOR_MUST_NOT_BE_DEFINED__DIAGNOSTIC_CHAIN_MAP__EOCL_INV == null) {
            final OCL.Helper helper = EOCL_ENV.createOCLHelper();
            helper.setContext(SeffPackage.Literals.START_ACTION);
            try {
                START_ACTION_PREDECESSOR_MUST_NOT_BE_DEFINED__DIAGNOSTIC_CHAIN_MAP__EOCL_INV = helper
                        .createInvariant(START_ACTION_PREDECESSOR_MUST_NOT_BE_DEFINED__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP);
            } catch (final ParserException pe) {
                throw new UnsupportedOperationException(pe.getLocalizedMessage());
            }
        }
        if (!EOCL_ENV.createQuery(START_ACTION_PREDECESSOR_MUST_NOT_BE_DEFINED__DIAGNOSTIC_CHAIN_MAP__EOCL_INV)
                .check(this)) {
            if (diagnostics != null) {
                diagnostics
                        .add(new BasicDiagnostic(Diagnostic.ERROR, SeffValidator.DIAGNOSTIC_SOURCE,
                                SeffValidator.START_ACTION__START_ACTION_PREDECESSOR_MUST_NOT_BE_DEFINED,
                                EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic",
                                        new Object[] { "StartActionPredecessorMustNotBeDefined",
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

} // StartActionImpl
