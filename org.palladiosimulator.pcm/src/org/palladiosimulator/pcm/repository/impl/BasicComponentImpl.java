/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository.impl;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.OCL;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.repository.util.RepositoryValidator;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Basic Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.impl.BasicComponentImpl#getServiceEffectSpecifications__BasicComponent
 * <em>Service Effect Specifications Basic Component</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.impl.BasicComponentImpl#getPassiveResource_BasicComponent
 * <em>Passive Resource Basic Component</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BasicComponentImpl extends ImplementationComponentTypeImpl implements BasicComponent {

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
    protected BasicComponentImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RepositoryPackage.Literals.BASIC_COMPONENT;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<ServiceEffectSpecification> getServiceEffectSpecifications__BasicComponent() {
        return (EList<ServiceEffectSpecification>) this.eDynamicGet(
                RepositoryPackage.BASIC_COMPONENT__SERVICE_EFFECT_SPECIFICATIONS_BASIC_COMPONENT,
                RepositoryPackage.Literals.BASIC_COMPONENT__SERVICE_EFFECT_SPECIFICATIONS_BASIC_COMPONENT, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<PassiveResource> getPassiveResource_BasicComponent() {
        return (EList<PassiveResource>) this.eDynamicGet(
                RepositoryPackage.BASIC_COMPONENT__PASSIVE_RESOURCE_BASIC_COMPONENT,
                RepositoryPackage.Literals.BASIC_COMPONENT__PASSIVE_RESOURCE_BASIC_COMPONENT, true, true);
    }

    /**
     * The cached OCL expression body for the '
     * {@link #NoSeffTypeUsedTwice(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>No Seff Type Used Twice</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #NoSeffTypeUsedTwice(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * @generated
     * @ordered
     */
    protected static final String NO_SEFF_TYPE_USED_TWICE__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP = "self.serviceEffectSpecifications__BasicComponent->forAll(p1, p2 |\n"
            + "  p1 <> p2 implies (p1.describedService__SEFF = p2.describedService__SEFF implies p1.seffTypeID <> p2.seffTypeID))";
    /**
     * The cached OCL invariant for the '
     * {@link #NoSeffTypeUsedTwice(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>No Seff Type Used Twice</em>}' invariant operation. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #NoSeffTypeUsedTwice(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * @generated
     * @ordered
     */
    protected static Constraint NO_SEFF_TYPE_USED_TWICE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean NoSeffTypeUsedTwice(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
        if (NO_SEFF_TYPE_USED_TWICE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV == null) {
            final OCL.Helper helper = EOCL_ENV.createOCLHelper();
            helper.setContext(RepositoryPackage.Literals.BASIC_COMPONENT);
            try {
                NO_SEFF_TYPE_USED_TWICE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV = helper
                        .createInvariant(NO_SEFF_TYPE_USED_TWICE__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP);
            } catch (final ParserException pe) {
                throw new UnsupportedOperationException(pe.getLocalizedMessage());
            }
        }
        if (!EOCL_ENV.createQuery(NO_SEFF_TYPE_USED_TWICE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV).check(this)) {
            if (diagnostics != null) {
                diagnostics
                        .add(new BasicDiagnostic(Diagnostic.ERROR, RepositoryValidator.DIAGNOSTIC_SOURCE,
                                RepositoryValidator.BASIC_COMPONENT__NO_SEFF_TYPE_USED_TWICE,
                                EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic",
                                        new Object[] { "NoSeffTypeUsedTwice",
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
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID,
            final NotificationChain msgs) {
        switch (featureID) {
        case RepositoryPackage.BASIC_COMPONENT__SERVICE_EFFECT_SPECIFICATIONS_BASIC_COMPONENT:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this
                    .getServiceEffectSpecifications__BasicComponent()).basicAdd(otherEnd, msgs);
        case RepositoryPackage.BASIC_COMPONENT__PASSIVE_RESOURCE_BASIC_COMPONENT:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getPassiveResource_BasicComponent())
                    .basicAdd(otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID,
            final NotificationChain msgs) {
        switch (featureID) {
        case RepositoryPackage.BASIC_COMPONENT__SERVICE_EFFECT_SPECIFICATIONS_BASIC_COMPONENT:
            return ((InternalEList<?>) this.getServiceEffectSpecifications__BasicComponent()).basicRemove(otherEnd,
                    msgs);
        case RepositoryPackage.BASIC_COMPONENT__PASSIVE_RESOURCE_BASIC_COMPONENT:
            return ((InternalEList<?>) this.getPassiveResource_BasicComponent()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case RepositoryPackage.BASIC_COMPONENT__SERVICE_EFFECT_SPECIFICATIONS_BASIC_COMPONENT:
            return this.getServiceEffectSpecifications__BasicComponent();
        case RepositoryPackage.BASIC_COMPONENT__PASSIVE_RESOURCE_BASIC_COMPONENT:
            return this.getPassiveResource_BasicComponent();
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
        case RepositoryPackage.BASIC_COMPONENT__SERVICE_EFFECT_SPECIFICATIONS_BASIC_COMPONENT:
            this.getServiceEffectSpecifications__BasicComponent().clear();
            this.getServiceEffectSpecifications__BasicComponent()
                    .addAll((Collection<? extends ServiceEffectSpecification>) newValue);
            return;
        case RepositoryPackage.BASIC_COMPONENT__PASSIVE_RESOURCE_BASIC_COMPONENT:
            this.getPassiveResource_BasicComponent().clear();
            this.getPassiveResource_BasicComponent().addAll((Collection<? extends PassiveResource>) newValue);
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
        case RepositoryPackage.BASIC_COMPONENT__SERVICE_EFFECT_SPECIFICATIONS_BASIC_COMPONENT:
            this.getServiceEffectSpecifications__BasicComponent().clear();
            return;
        case RepositoryPackage.BASIC_COMPONENT__PASSIVE_RESOURCE_BASIC_COMPONENT:
            this.getPassiveResource_BasicComponent().clear();
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
        case RepositoryPackage.BASIC_COMPONENT__SERVICE_EFFECT_SPECIFICATIONS_BASIC_COMPONENT:
            return !this.getServiceEffectSpecifications__BasicComponent().isEmpty();
        case RepositoryPackage.BASIC_COMPONENT__PASSIVE_RESOURCE_BASIC_COMPONENT:
            return !this.getPassiveResource_BasicComponent().isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // BasicComponentImpl
