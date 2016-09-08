/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.util;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.palladiosimulator.pcm.core.CorePackage;
import org.palladiosimulator.pcm.core.PCMRandomVariable;

/**
 * <!-- begin-user-doc --> The <b>Validator</b> for the model. <!-- end-user-doc -->
 * 
 * @see org.palladiosimulator.pcm.core.CorePackage
 * @generated
 */
public class CoreValidator extends EObjectValidator {

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
    public static final CoreValidator INSTANCE = new CoreValidator();

    /**
     * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of
     * diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.emf.common.util.Diagnostic#getSource()
     * @see org.eclipse.emf.common.util.Diagnostic#getCode()
     * @generated
     */
    public static final String DIAGNOSTIC_SOURCE = "org.palladiosimulator.pcm.core";

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint
     * 'Specification Must Not Be NULL' of 'PCM Random Variable'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public static final int PCM_RANDOM_VARIABLE__SPECIFICATION_MUST_NOT_BE_NULL = 1;

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
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public CoreValidator() {
        super();
    }

    /**
     * Returns the package of this validator switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EPackage getEPackage() {
        return CorePackage.eINSTANCE;
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
        case CorePackage.PCM_RANDOM_VARIABLE:
            return this.validatePCMRandomVariable((PCMRandomVariable) value, diagnostics, context);
        default:
            return true;
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean validatePCMRandomVariable(final PCMRandomVariable pcmRandomVariable,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(pcmRandomVariable, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(pcmRandomVariable, diagnostics, context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(pcmRandomVariable, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(pcmRandomVariable, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(pcmRandomVariable, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(pcmRandomVariable, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(pcmRandomVariable, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(pcmRandomVariable, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validatePCMRandomVariable_SpecificationMustNotBeNULL(pcmRandomVariable, diagnostics,
                    context);
        }
        return result;
    }

    /**
     * Validates the SpecificationMustNotBeNULL constraint of '<em>PCM Random Variable</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validatePCMRandomVariable_SpecificationMustNotBeNULL(final PCMRandomVariable pcmRandomVariable,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return pcmRandomVariable.SpecificationMustNotBeNULL(diagnostics, context);
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

} // CoreValidator
