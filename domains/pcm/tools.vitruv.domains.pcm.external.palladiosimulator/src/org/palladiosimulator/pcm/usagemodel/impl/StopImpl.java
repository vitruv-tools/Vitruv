/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.usagemodel.impl;

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
import org.palladiosimulator.pcm.usagemodel.Stop;
import org.palladiosimulator.pcm.usagemodel.UsagemodelPackage;
import org.palladiosimulator.pcm.usagemodel.util.UsagemodelValidator;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Stop</b></em>'. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class StopImpl extends AbstractUserActionImpl implements Stop {

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
    protected StopImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return UsagemodelPackage.Literals.STOP;
    }

    /**
     * The cached OCL expression body for the '
     * {@link #StopHasNoSuccessor(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Stop Has No Successor</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #StopHasNoSuccessor(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * @generated
     * @ordered
     */
    protected static final String STOP_HAS_NO_SUCCESSOR__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP = "self.successor.oclIsUndefined()";
    /**
     * The cached OCL invariant for the '
     * {@link #StopHasNoSuccessor(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Stop Has No Successor</em>}' invariant operation. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #StopHasNoSuccessor(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * @generated
     * @ordered
     */
    protected static Constraint STOP_HAS_NO_SUCCESSOR__DIAGNOSTIC_CHAIN_MAP__EOCL_INV;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean StopHasNoSuccessor(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (STOP_HAS_NO_SUCCESSOR__DIAGNOSTIC_CHAIN_MAP__EOCL_INV == null) {
            final OCL.Helper helper = EOCL_ENV.createOCLHelper();
            helper.setContext(UsagemodelPackage.Literals.STOP);
            try {
                STOP_HAS_NO_SUCCESSOR__DIAGNOSTIC_CHAIN_MAP__EOCL_INV = helper
                        .createInvariant(STOP_HAS_NO_SUCCESSOR__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP);
            } catch (final ParserException pe) {
                throw new UnsupportedOperationException(pe.getLocalizedMessage());
            }
        }
        if (!EOCL_ENV.createQuery(STOP_HAS_NO_SUCCESSOR__DIAGNOSTIC_CHAIN_MAP__EOCL_INV).check(this)) {
            if (diagnostics != null) {
                diagnostics
                        .add(new BasicDiagnostic(Diagnostic.ERROR, UsagemodelValidator.DIAGNOSTIC_SOURCE,
                                UsagemodelValidator.STOP__STOP_HAS_NO_SUCCESSOR,
                                EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic",
                                        new Object[] { "StopHasNoSuccessor",
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

} // StopImpl
