/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.system.util;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.palladiosimulator.pcm.core.composition.util.CompositionValidator;
import org.palladiosimulator.pcm.core.entity.util.EntityValidator;
import org.palladiosimulator.pcm.system.SystemPackage;

/**
 * <!-- begin-user-doc --> The <b>Validator</b> for the model. <!-- end-user-doc -->
 * 
 * @see org.palladiosimulator.pcm.system.SystemPackage
 * @generated
 */
public class SystemValidator extends EObjectValidator {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final SystemValidator INSTANCE = new SystemValidator();

    /**
     * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of
     * diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.emf.common.util.Diagnostic#getSource()
     * @see org.eclipse.emf.common.util.Diagnostic#getCode()
     * @generated
     */
    public static final String DIAGNOSTIC_SOURCE = "org.palladiosimulator.pcm.system";

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'System Must
     * Have At Least One Provided Role' of 'System'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final int SYSTEM__SYSTEM_MUST_HAVE_AT_LEAST_ONE_PROVIDED_ROLE = 1;

    /**
     * A constant with a fixed name that can be used as the base value for additional hand written
     * constants. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 1;

    /**
     * A constant with a fixed name that can be used as the base value for additional hand written
     * constants in a derived class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

    /**
     * The cached base package validator. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected CompositionValidator compositionValidator;

    /**
     * The cached base package validator. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected EntityValidator entityValidator;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public SystemValidator() {
        super();
        this.compositionValidator = CompositionValidator.INSTANCE;
        this.entityValidator = EntityValidator.INSTANCE;
    }

    /**
     * Returns the package of this validator switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EPackage getEPackage() {
        return SystemPackage.eINSTANCE;
    }

    /**
     * Calls <code>validateXXX</code> for the corresponding classifier of the model. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected boolean validate(final int classifierID, final Object value, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        switch (classifierID) {
        case SystemPackage.SYSTEM:
            return this.validateSystem((org.palladiosimulator.pcm.system.System) value, diagnostics, context);
        default:
            return true;
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validateSystem(final org.palladiosimulator.pcm.system.System system,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(system, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(system, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(system, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(system, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(system, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(system, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(system, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(system, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.compositionValidator.validateComposedStructure_MultipleConnectorsConstraint(system,
                    diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.compositionValidator
                    .validateComposedStructure_MultipleConnectorsConstraintForAssemblyConnectors(system, diagnostics,
                            context);
        }
        if (result || diagnostics != null) {
            result &= this.entityValidator.validateComposedProvidingRequiringEntity_ProvidedRolesMustBeBound(system,
                    diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateSystem_SystemMustHaveAtLeastOneProvidedRole(system, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the SystemMustHaveAtLeastOneProvidedRole constraint of '<em>System</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateSystem_SystemMustHaveAtLeastOneProvidedRole(
            final org.palladiosimulator.pcm.system.System system, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return system.SystemMustHaveAtLeastOneProvidedRole(diagnostics, context);
    }

    /**
     * Returns the resource locator that will be used to fetch messages for this validator's
     * diagnostics. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        // TODO
        // Specialize this to return a resource locator for messages specific to this validator.
        // Ensure that you remove @generated or mark it @generated NOT
        return super.getResourceLocator();
    }

} // SystemValidator
