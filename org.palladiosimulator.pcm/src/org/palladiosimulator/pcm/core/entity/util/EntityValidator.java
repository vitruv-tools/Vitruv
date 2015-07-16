/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.entity.util;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.palladiosimulator.pcm.core.composition.util.CompositionValidator;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.core.entity.EntityPackage;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingEntity;
import org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.ResourceInterfaceRequiringEntity;
import org.palladiosimulator.pcm.core.entity.ResourceProvidedRole;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;

/**
 * <!-- begin-user-doc --> The <b>Validator</b> for the model. <!-- end-user-doc -->
 *
 * @see org.palladiosimulator.pcm.core.entity.EntityPackage
 * @generated
 */
public class EntityValidator extends EObjectValidator {

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
    public static final EntityValidator INSTANCE = new EntityValidator();

    /**
     * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of
     * diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.common.util.Diagnostic#getSource()
     * @see org.eclipse.emf.common.util.Diagnostic#getCode()
     * @generated
     */
    public static final String DIAGNOSTIC_SOURCE = "org.palladiosimulator.pcm.core.entity";

    /**
     * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Provided
     * Roles Must Be Bound' of 'Composed Providing Requiring Entity'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    public static final int COMPOSED_PROVIDING_REQUIRING_ENTITY__PROVIDED_ROLES_MUST_BE_BOUND = 1;

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
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public EntityValidator() {
        super();
        this.compositionValidator = CompositionValidator.INSTANCE;
    }

    /**
     * Returns the package of this validator switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EPackage getEPackage() {
        return EntityPackage.eINSTANCE;
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
        case EntityPackage.RESOURCE_PROVIDED_ROLE:
            return this.validateResourceProvidedRole((ResourceProvidedRole) value, diagnostics, context);
        case EntityPackage.INTERFACE_PROVIDING_REQUIRING_ENTITY:
            return this.validateInterfaceProvidingRequiringEntity((InterfaceProvidingRequiringEntity) value,
                    diagnostics, context);
        case EntityPackage.INTERFACE_PROVIDING_ENTITY:
            return this.validateInterfaceProvidingEntity((InterfaceProvidingEntity) value, diagnostics, context);
        case EntityPackage.INTERFACE_REQUIRING_ENTITY:
            return this.validateInterfaceRequiringEntity((InterfaceRequiringEntity) value, diagnostics, context);
        case EntityPackage.RESOURCE_INTERFACE_REQUIRING_ENTITY:
            return this.validateResourceInterfaceRequiringEntity((ResourceInterfaceRequiringEntity) value, diagnostics,
                    context);
        case EntityPackage.RESOURCE_REQUIRED_ROLE:
            return this.validateResourceRequiredRole((ResourceRequiredRole) value, diagnostics, context);
        case EntityPackage.RESOURCE_INTERFACE_PROVIDING_ENTITY:
            return this.validateResourceInterfaceProvidingEntity((ResourceInterfaceProvidingEntity) value, diagnostics,
                    context);
        case EntityPackage.COMPOSED_PROVIDING_REQUIRING_ENTITY:
            return this.validateComposedProvidingRequiringEntity((ComposedProvidingRequiringEntity) value, diagnostics,
                    context);
        case EntityPackage.NAMED_ELEMENT:
            return this.validateNamedElement((NamedElement) value, diagnostics, context);
        case EntityPackage.RESOURCE_INTERFACE_PROVIDING_REQUIRING_ENTITY:
            return this.validateResourceInterfaceProvidingRequiringEntity(
                    (ResourceInterfaceProvidingRequiringEntity) value, diagnostics, context);
        case EntityPackage.ENTITY:
            return this.validateEntity((Entity) value, diagnostics, context);
        default:
            return true;
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateResourceProvidedRole(final ResourceProvidedRole resourceProvidedRole,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(resourceProvidedRole, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateInterfaceProvidingRequiringEntity(
            final InterfaceProvidingRequiringEntity interfaceProvidingRequiringEntity,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(interfaceProvidingRequiringEntity, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateInterfaceProvidingEntity(final InterfaceProvidingEntity interfaceProvidingEntity,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(interfaceProvidingEntity, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateInterfaceRequiringEntity(final InterfaceRequiringEntity interfaceRequiringEntity,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(interfaceRequiringEntity, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateResourceInterfaceRequiringEntity(
            final ResourceInterfaceRequiringEntity resourceInterfaceRequiringEntity, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(resourceInterfaceRequiringEntity, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateResourceRequiredRole(final ResourceRequiredRole resourceRequiredRole,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(resourceRequiredRole, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateResourceInterfaceProvidingEntity(
            final ResourceInterfaceProvidingEntity resourceInterfaceProvidingEntity, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(resourceInterfaceProvidingEntity, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateComposedProvidingRequiringEntity(
            final ComposedProvidingRequiringEntity composedProvidingRequiringEntity, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (!this.validate_NoCircularContainment(composedProvidingRequiringEntity, diagnostics, context)) {
            return false;
        }
        boolean result = this.validate_EveryMultiplicityConforms(composedProvidingRequiringEntity, diagnostics,
                context);
        if (result || diagnostics != null) {
            result &= this.validate_EveryDataValueConforms(composedProvidingRequiringEntity, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryReferenceIsContained(composedProvidingRequiringEntity, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryProxyResolves(composedProvidingRequiringEntity, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_UniqueID(composedProvidingRequiringEntity, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryKeyUnique(composedProvidingRequiringEntity, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validate_EveryMapEntryUnique(composedProvidingRequiringEntity, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.compositionValidator.validateComposedStructure_MultipleConnectorsConstraint(
                    composedProvidingRequiringEntity, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.compositionValidator
                    .validateComposedStructure_MultipleConnectorsConstraintForAssemblyConnectors(
                            composedProvidingRequiringEntity, diagnostics, context);
        }
        if (result || diagnostics != null) {
            result &= this.validateComposedProvidingRequiringEntity_ProvidedRolesMustBeBound(
                    composedProvidingRequiringEntity, diagnostics, context);
        }
        return result;
    }

    /**
     * Validates the ProvidedRolesMustBeBound constraint of '
     * <em>Composed Providing Requiring Entity</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateComposedProvidingRequiringEntity_ProvidedRolesMustBeBound(
            final ComposedProvidingRequiringEntity composedProvidingRequiringEntity, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return composedProvidingRequiringEntity.ProvidedRolesMustBeBound(diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateNamedElement(final NamedElement namedElement, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(namedElement, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateResourceInterfaceProvidingRequiringEntity(
            final ResourceInterfaceProvidingRequiringEntity resourceInterfaceProvidingRequiringEntity,
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(resourceInterfaceProvidingRequiringEntity, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean validateEntity(final Entity entity, final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        return this.validate_EveryDefaultConstraint(entity, diagnostics, context);
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

} // EntityValidator
