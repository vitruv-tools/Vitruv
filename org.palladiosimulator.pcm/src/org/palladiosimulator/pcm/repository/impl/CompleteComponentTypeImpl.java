/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository.impl;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.OCL;
import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.ProvidesComponentType;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.repository.util.RepositoryValidator;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Complete Component Type</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.impl.CompleteComponentTypeImpl#getParentProvidesComponentTypes
 * <em>Parent Provides Component Types</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompleteComponentTypeImpl extends RepositoryComponentImpl implements CompleteComponentType {

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
    protected CompleteComponentTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RepositoryPackage.Literals.COMPLETE_COMPONENT_TYPE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<ProvidesComponentType> getParentProvidesComponentTypes() {
        return (EList<ProvidesComponentType>) this.eDynamicGet(
                RepositoryPackage.COMPLETE_COMPONENT_TYPE__PARENT_PROVIDES_COMPONENT_TYPES,
                RepositoryPackage.Literals.COMPLETE_COMPONENT_TYPE__PARENT_PROVIDES_COMPONENT_TYPES, true, true);
    }

    /**
     * The cached OCL expression body for the '
     * {@link #AtLeastOneInterfaceHasToBeProvidedOrRequiredByAUsefullCompleteComponentType(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>At Least One Interface Has To Be Provided Or Required By AUsefull Complete Component Type</em>
     * }' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #AtLeastOneInterfaceHasToBeProvidedOrRequiredByAUsefullCompleteComponentType(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static final String AT_LEAST_ONE_INTERFACE_HAS_TO_BE_PROVIDED_OR_REQUIRED_BY_AUSEFULL_COMPLETE_COMPONENT_TYPE__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP = "(\n"
            + "	self.oclIsTypeOf(CompleteComponentType)\n" + "	or\n"
            + "	self.oclIsTypeOf(ImplementationComponentType)\n" + "	or\n"
            + "	self.oclIsTypeOf(CompositeComponent)\n" + "	or\n" + "	self.oclIsTypeOf(BasicComponent)\n" + ")\n"
            + "implies\n" + "(\n" + "	self.providedRoles_InterfaceProvidingEntity->size() >= 1\n" + "	or\n"
            + "	self.requiredRoles_InterfaceRequiringEntity->size() >= 1\n" + ")";
    /**
     * The cached OCL invariant for the '
     * {@link #AtLeastOneInterfaceHasToBeProvidedOrRequiredByAUsefullCompleteComponentType(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>At Least One Interface Has To Be Provided Or Required By AUsefull Complete Component Type</em>
     * }' invariant operation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #AtLeastOneInterfaceHasToBeProvidedOrRequiredByAUsefullCompleteComponentType(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static Constraint AT_LEAST_ONE_INTERFACE_HAS_TO_BE_PROVIDED_OR_REQUIRED_BY_AUSEFULL_COMPLETE_COMPONENT_TYPE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean AtLeastOneInterfaceHasToBeProvidedOrRequiredByAUsefullCompleteComponentType(
            final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (AT_LEAST_ONE_INTERFACE_HAS_TO_BE_PROVIDED_OR_REQUIRED_BY_AUSEFULL_COMPLETE_COMPONENT_TYPE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV == null) {
            final OCL.Helper helper = EOCL_ENV.createOCLHelper();
            helper.setContext(RepositoryPackage.Literals.COMPLETE_COMPONENT_TYPE);
            try {
                AT_LEAST_ONE_INTERFACE_HAS_TO_BE_PROVIDED_OR_REQUIRED_BY_AUSEFULL_COMPLETE_COMPONENT_TYPE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV = helper
                        .createInvariant(
                                AT_LEAST_ONE_INTERFACE_HAS_TO_BE_PROVIDED_OR_REQUIRED_BY_AUSEFULL_COMPLETE_COMPONENT_TYPE__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP);
            } catch (final ParserException pe) {
                throw new UnsupportedOperationException(pe.getLocalizedMessage());
            }
        }
        if (!EOCL_ENV
                .createQuery(
                        AT_LEAST_ONE_INTERFACE_HAS_TO_BE_PROVIDED_OR_REQUIRED_BY_AUSEFULL_COMPLETE_COMPONENT_TYPE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV)
                .check(this)) {
            if (diagnostics != null) {
                diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, RepositoryValidator.DIAGNOSTIC_SOURCE,
                        RepositoryValidator.COMPLETE_COMPONENT_TYPE__AT_LEAST_ONE_INTERFACE_HAS_TO_BE_PROVIDED_OR_REQUIRED_BY_AUSEFULL_COMPLETE_COMPONENT_TYPE,
                        EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic",
                                new Object[] {
                                        "AtLeastOneInterfaceHasToBeProvidedOrRequiredByAUsefullCompleteComponentType",
                                        EObjectValidator.getObjectLabel(this, context) }),
                        new Object[] { this }));
            }
            return false;
        }
        return true;
    }

    /**
     * The cached OCL expression body for the '
     * {@link #providedInterfacesHaveToConformToProvidedType2(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Provided Interfaces Have To Conform To Provided Type2</em>}' operation. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #providedInterfacesHaveToConformToProvidedType2(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static final String PROVIDED_INTERFACES_HAVE_TO_CONFORM_TO_PROVIDED_TYPE2__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP = "-- CompleteTypes provided Interfaces have to be a superset\n"
            + "-- of ProvidesComponentType provided Interfaces #\n" + "--\n"
            + "-- ACCx are used to accumulate Sets/Bags; usually only the very inner ACCx is used at all.\n" + "--\n"
            + "-- Recursive Query for parent Interface IDs\n"
            + "-- see 'lpar2005.pdf' (Second-order principles in specification languages for Object-Oriented Programs; Beckert, Tretelman) pp. 11 #\n"
            + "--let parentInterfaces : Bag(Interface) =\n"
            + "--	self.providedRoles->iterate(r : ProvidedRole; acc2 : Bag(Interface) = Bag{} |\n"
            + "--		acc2->union(r.providedInterface.parentInterface->asBag()) -- asBag required to allow Set operations #\n"
            + "--	) in\n" + "--let anchestorInterfaces : Bag(Interface) =\n"
            + "--	self.providedRoles->iterate(r : ProvidedRole; acc4 : Bag(Interface) = Bag{} |\n"
            + "--		acc4->union(r.providedInterface.parentInterface->asBag()) -- asBag required to allow Set operations #\n"
            + "--	)->union( -- union with anchestors found in former recursion #\n"
            + "--		self.providedRoles->iterate(r : ProvidedRole; acc6 : Bag(Interface) = Bag{} |\n"
            + "--			acc6->union(r.providedInterface.parentInterface.anchestorInterfaces) --already Set/Bag\n"
            + "--		)\n" + "--	) in\n"
            + "--	-- Directly provided anchestorInterfaces need to be a superset of provided interfaces of Supertype #\n"
            + "--	anchestorInterfaces.identifier.id->includesAll(\n"
            + "--		self.parentProvidesComponentTypes->iterate(pt : ProvidesComponentType; acc1 : Bag(String) = Bag{} |\n"
            + "--			pt.providedRoles->iterate(r : ProvidedRole; acc2 : Bag(String) = Bag{} |\n"
            + "--				acc2->union(r.providedInterface.identifier.id->asBag()) -- asBag required to allow Set operations #\n"
            + "--			)\n" + "--		)\n" + "--	)\n" + "true";
    /**
     * The cached OCL invariant for the '
     * {@link #providedInterfacesHaveToConformToProvidedType2(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Provided Interfaces Have To Conform To Provided Type2</em>}' invariant operation. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #providedInterfacesHaveToConformToProvidedType2(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static Constraint PROVIDED_INTERFACES_HAVE_TO_CONFORM_TO_PROVIDED_TYPE2__DIAGNOSTIC_CHAIN_MAP__EOCL_INV;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean providedInterfacesHaveToConformToProvidedType2(final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (PROVIDED_INTERFACES_HAVE_TO_CONFORM_TO_PROVIDED_TYPE2__DIAGNOSTIC_CHAIN_MAP__EOCL_INV == null) {
            final OCL.Helper helper = EOCL_ENV.createOCLHelper();
            helper.setContext(RepositoryPackage.Literals.COMPLETE_COMPONENT_TYPE);
            try {
                PROVIDED_INTERFACES_HAVE_TO_CONFORM_TO_PROVIDED_TYPE2__DIAGNOSTIC_CHAIN_MAP__EOCL_INV = helper
                        .createInvariant(
                                PROVIDED_INTERFACES_HAVE_TO_CONFORM_TO_PROVIDED_TYPE2__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP);
            } catch (final ParserException pe) {
                throw new UnsupportedOperationException(pe.getLocalizedMessage());
            }
        }
        if (!EOCL_ENV.createQuery(PROVIDED_INTERFACES_HAVE_TO_CONFORM_TO_PROVIDED_TYPE2__DIAGNOSTIC_CHAIN_MAP__EOCL_INV)
                .check(this)) {
            if (diagnostics != null) {
                diagnostics
                        .add(new BasicDiagnostic(Diagnostic.ERROR, RepositoryValidator.DIAGNOSTIC_SOURCE,
                                RepositoryValidator.COMPLETE_COMPONENT_TYPE__PROVIDED_INTERFACES_HAVE_TO_CONFORM_TO_PROVIDED_TYPE2,
                                EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic",
                                        new Object[] { "providedInterfacesHaveToConformToProvidedType2",
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
        case RepositoryPackage.COMPLETE_COMPONENT_TYPE__PARENT_PROVIDES_COMPONENT_TYPES:
            return this.getParentProvidesComponentTypes();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(final int featureID, final Object newValue) {
        switch (featureID) {
        case RepositoryPackage.COMPLETE_COMPONENT_TYPE__PARENT_PROVIDES_COMPONENT_TYPES:
            this.getParentProvidesComponentTypes().clear();
            this.getParentProvidesComponentTypes().addAll((Collection<? extends ProvidesComponentType>) newValue);
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
        case RepositoryPackage.COMPLETE_COMPONENT_TYPE__PARENT_PROVIDES_COMPONENT_TYPES:
            this.getParentProvidesComponentTypes().clear();
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
        case RepositoryPackage.COMPLETE_COMPONENT_TYPE__PARENT_PROVIDES_COMPONENT_TYPES:
            return !this.getParentProvidesComponentTypes().isEmpty();
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

} // CompleteComponentTypeImpl
