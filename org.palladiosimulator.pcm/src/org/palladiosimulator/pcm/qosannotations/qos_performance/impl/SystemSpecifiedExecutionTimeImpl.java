/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.qosannotations.qos_performance.impl;

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
import org.palladiosimulator.pcm.qosannotations.qos_performance.QosPerformancePackage;
import org.palladiosimulator.pcm.qosannotations.qos_performance.SystemSpecifiedExecutionTime;
import org.palladiosimulator.pcm.qosannotations.qos_performance.util.QosPerformanceValidator;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>System Specified Execution Time</b></em>'. <!-- end-user-doc -->
 *
 * @generated
 */
public class SystemSpecifiedExecutionTimeImpl extends SpecifiedExecutionTimeImpl
        implements SystemSpecifiedExecutionTime {

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
    protected SystemSpecifiedExecutionTimeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return QosPerformancePackage.Literals.SYSTEM_SPECIFIED_EXECUTION_TIME;
    }

    /**
     * The cached OCL expression body for the '
     * {@link #SystemSpecifiedExecutionTimeMustReferenceRequiredRoleOfASystem(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>System Specified Execution Time Must Reference Required Role Of ASystem</em>}' operation.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #SystemSpecifiedExecutionTimeMustReferenceRequiredRoleOfASystem(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static final String SYSTEM_SPECIFIED_EXECUTION_TIME_MUST_REFERENCE_REQUIRED_ROLE_OF_ASYSTEM__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP = "(self.role_SpecifiedQoSAnnotation.oclIsTypeOf(pcm::repository::OperationRequiredRole)) and (self.role_SpecifiedQoSAnnotation.oclAsType(pcm::repository::OperationRequiredRole).requiringEntity_RequiredRole.oclIsTypeOf(pcm::system::System))\n"
            + "";
    /**
     * The cached OCL invariant for the '
     * {@link #SystemSpecifiedExecutionTimeMustReferenceRequiredRoleOfASystem(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>System Specified Execution Time Must Reference Required Role Of ASystem</em>}' invariant
     * operation. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #SystemSpecifiedExecutionTimeMustReferenceRequiredRoleOfASystem(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static Constraint SYSTEM_SPECIFIED_EXECUTION_TIME_MUST_REFERENCE_REQUIRED_ROLE_OF_ASYSTEM__DIAGNOSTIC_CHAIN_MAP__EOCL_INV;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean SystemSpecifiedExecutionTimeMustReferenceRequiredRoleOfASystem(final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (SYSTEM_SPECIFIED_EXECUTION_TIME_MUST_REFERENCE_REQUIRED_ROLE_OF_ASYSTEM__DIAGNOSTIC_CHAIN_MAP__EOCL_INV == null) {
            final OCL.Helper helper = EOCL_ENV.createOCLHelper();
            helper.setContext(QosPerformancePackage.Literals.SYSTEM_SPECIFIED_EXECUTION_TIME);
            try {
                SYSTEM_SPECIFIED_EXECUTION_TIME_MUST_REFERENCE_REQUIRED_ROLE_OF_ASYSTEM__DIAGNOSTIC_CHAIN_MAP__EOCL_INV = helper
                        .createInvariant(
                                SYSTEM_SPECIFIED_EXECUTION_TIME_MUST_REFERENCE_REQUIRED_ROLE_OF_ASYSTEM__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP);
            } catch (final ParserException pe) {
                throw new UnsupportedOperationException(pe.getLocalizedMessage());
            }
        }
        if (!EOCL_ENV
                .createQuery(
                        SYSTEM_SPECIFIED_EXECUTION_TIME_MUST_REFERENCE_REQUIRED_ROLE_OF_ASYSTEM__DIAGNOSTIC_CHAIN_MAP__EOCL_INV)
                .check(this)) {
            if (diagnostics != null) {
                diagnostics
                        .add(new BasicDiagnostic(Diagnostic.ERROR, QosPerformanceValidator.DIAGNOSTIC_SOURCE,
                                QosPerformanceValidator.SYSTEM_SPECIFIED_EXECUTION_TIME__SYSTEM_SPECIFIED_EXECUTION_TIME_MUST_REFERENCE_REQUIRED_ROLE_OF_ASYSTEM,
                                EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic",
                                        new Object[] { "SystemSpecifiedExecutionTimeMustReferenceRequiredRoleOfASystem",
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

} // SystemSpecifiedExecutionTimeImpl
