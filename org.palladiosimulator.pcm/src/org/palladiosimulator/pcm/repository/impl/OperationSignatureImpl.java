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
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.repository.util.RepositoryValidator;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Operation Signature</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.impl.OperationSignatureImpl#getInterface__OperationSignature
 * <em>Interface Operation Signature</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.impl.OperationSignatureImpl#getParameters__OperationSignature
 * <em>Parameters Operation Signature</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.impl.OperationSignatureImpl#getReturnType__OperationSignature
 * <em>Return Type Operation Signature</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OperationSignatureImpl extends SignatureImpl implements OperationSignature {

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
    protected OperationSignatureImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RepositoryPackage.Literals.OPERATION_SIGNATURE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public OperationInterface getInterface__OperationSignature() {
        return (OperationInterface) this.eDynamicGet(
                RepositoryPackage.OPERATION_SIGNATURE__INTERFACE_OPERATION_SIGNATURE,
                RepositoryPackage.Literals.OPERATION_SIGNATURE__INTERFACE_OPERATION_SIGNATURE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetInterface__OperationSignature(
            final OperationInterface newInterface__OperationSignature, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newInterface__OperationSignature,
                RepositoryPackage.OPERATION_SIGNATURE__INTERFACE_OPERATION_SIGNATURE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setInterface__OperationSignature(final OperationInterface newInterface__OperationSignature) {
        this.eDynamicSet(RepositoryPackage.OPERATION_SIGNATURE__INTERFACE_OPERATION_SIGNATURE,
                RepositoryPackage.Literals.OPERATION_SIGNATURE__INTERFACE_OPERATION_SIGNATURE,
                newInterface__OperationSignature);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<Parameter> getParameters__OperationSignature() {
        return (EList<Parameter>) this.eDynamicGet(
                RepositoryPackage.OPERATION_SIGNATURE__PARAMETERS_OPERATION_SIGNATURE,
                RepositoryPackage.Literals.OPERATION_SIGNATURE__PARAMETERS_OPERATION_SIGNATURE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DataType getReturnType__OperationSignature() {
        return (DataType) this.eDynamicGet(RepositoryPackage.OPERATION_SIGNATURE__RETURN_TYPE_OPERATION_SIGNATURE,
                RepositoryPackage.Literals.OPERATION_SIGNATURE__RETURN_TYPE_OPERATION_SIGNATURE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public DataType basicGetReturnType__OperationSignature() {
        return (DataType) this.eDynamicGet(RepositoryPackage.OPERATION_SIGNATURE__RETURN_TYPE_OPERATION_SIGNATURE,
                RepositoryPackage.Literals.OPERATION_SIGNATURE__RETURN_TYPE_OPERATION_SIGNATURE, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setReturnType__OperationSignature(final DataType newReturnType__OperationSignature) {
        this.eDynamicSet(RepositoryPackage.OPERATION_SIGNATURE__RETURN_TYPE_OPERATION_SIGNATURE,
                RepositoryPackage.Literals.OPERATION_SIGNATURE__RETURN_TYPE_OPERATION_SIGNATURE,
                newReturnType__OperationSignature);
    }

    /**
     * The cached OCL expression body for the '
     * {@link #ParameterNamesHaveToBeUniqueForASignature(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Parameter Names Have To Be Unique For ASignature</em>}' operation. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #ParameterNamesHaveToBeUniqueForASignature(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static final String PARAMETER_NAMES_HAVE_TO_BE_UNIQUE_FOR_ASIGNATURE__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP = "self.parameters__OperationSignature->isUnique(p : Parameter |\n"
            + "	p.parameterName\n" + ")";
    /**
     * The cached OCL invariant for the '
     * {@link #ParameterNamesHaveToBeUniqueForASignature(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Parameter Names Have To Be Unique For ASignature</em>}' invariant operation. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #ParameterNamesHaveToBeUniqueForASignature(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static Constraint PARAMETER_NAMES_HAVE_TO_BE_UNIQUE_FOR_ASIGNATURE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean ParameterNamesHaveToBeUniqueForASignature(final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (PARAMETER_NAMES_HAVE_TO_BE_UNIQUE_FOR_ASIGNATURE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV == null) {
            final OCL.Helper helper = EOCL_ENV.createOCLHelper();
            helper.setContext(RepositoryPackage.Literals.OPERATION_SIGNATURE);
            try {
                PARAMETER_NAMES_HAVE_TO_BE_UNIQUE_FOR_ASIGNATURE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV = helper
                        .createInvariant(
                                PARAMETER_NAMES_HAVE_TO_BE_UNIQUE_FOR_ASIGNATURE__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP);
            } catch (final ParserException pe) {
                throw new UnsupportedOperationException(pe.getLocalizedMessage());
            }
        }
        if (!EOCL_ENV.createQuery(PARAMETER_NAMES_HAVE_TO_BE_UNIQUE_FOR_ASIGNATURE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV)
                .check(this)) {
            if (diagnostics != null) {
                diagnostics
                        .add(new BasicDiagnostic(Diagnostic.ERROR, RepositoryValidator.DIAGNOSTIC_SOURCE,
                                RepositoryValidator.OPERATION_SIGNATURE__PARAMETER_NAMES_HAVE_TO_BE_UNIQUE_FOR_ASIGNATURE,
                                EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic",
                                        new Object[] { "ParameterNamesHaveToBeUniqueForASignature",
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
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case RepositoryPackage.OPERATION_SIGNATURE__INTERFACE_OPERATION_SIGNATURE:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetInterface__OperationSignature((OperationInterface) otherEnd, msgs);
        case RepositoryPackage.OPERATION_SIGNATURE__PARAMETERS_OPERATION_SIGNATURE:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getParameters__OperationSignature())
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
        case RepositoryPackage.OPERATION_SIGNATURE__INTERFACE_OPERATION_SIGNATURE:
            return this.basicSetInterface__OperationSignature(null, msgs);
        case RepositoryPackage.OPERATION_SIGNATURE__PARAMETERS_OPERATION_SIGNATURE:
            return ((InternalEList<?>) this.getParameters__OperationSignature()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(final NotificationChain msgs) {
        switch (this.eContainerFeatureID()) {
        case RepositoryPackage.OPERATION_SIGNATURE__INTERFACE_OPERATION_SIGNATURE:
            return this.eInternalContainer().eInverseRemove(this,
                    RepositoryPackage.OPERATION_INTERFACE__SIGNATURES_OPERATION_INTERFACE, OperationInterface.class,
                    msgs);
        }
        return super.eBasicRemoveFromContainerFeature(msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
        case RepositoryPackage.OPERATION_SIGNATURE__INTERFACE_OPERATION_SIGNATURE:
            return this.getInterface__OperationSignature();
        case RepositoryPackage.OPERATION_SIGNATURE__PARAMETERS_OPERATION_SIGNATURE:
            return this.getParameters__OperationSignature();
        case RepositoryPackage.OPERATION_SIGNATURE__RETURN_TYPE_OPERATION_SIGNATURE:
            if (resolve) {
                return this.getReturnType__OperationSignature();
            }
            return this.basicGetReturnType__OperationSignature();
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
        case RepositoryPackage.OPERATION_SIGNATURE__INTERFACE_OPERATION_SIGNATURE:
            this.setInterface__OperationSignature((OperationInterface) newValue);
            return;
        case RepositoryPackage.OPERATION_SIGNATURE__PARAMETERS_OPERATION_SIGNATURE:
            this.getParameters__OperationSignature().clear();
            this.getParameters__OperationSignature().addAll((Collection<? extends Parameter>) newValue);
            return;
        case RepositoryPackage.OPERATION_SIGNATURE__RETURN_TYPE_OPERATION_SIGNATURE:
            this.setReturnType__OperationSignature((DataType) newValue);
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
        case RepositoryPackage.OPERATION_SIGNATURE__INTERFACE_OPERATION_SIGNATURE:
            this.setInterface__OperationSignature((OperationInterface) null);
            return;
        case RepositoryPackage.OPERATION_SIGNATURE__PARAMETERS_OPERATION_SIGNATURE:
            this.getParameters__OperationSignature().clear();
            return;
        case RepositoryPackage.OPERATION_SIGNATURE__RETURN_TYPE_OPERATION_SIGNATURE:
            this.setReturnType__OperationSignature((DataType) null);
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
        case RepositoryPackage.OPERATION_SIGNATURE__INTERFACE_OPERATION_SIGNATURE:
            return this.getInterface__OperationSignature() != null;
        case RepositoryPackage.OPERATION_SIGNATURE__PARAMETERS_OPERATION_SIGNATURE:
            return !this.getParameters__OperationSignature().isEmpty();
        case RepositoryPackage.OPERATION_SIGNATURE__RETURN_TYPE_OPERATION_SIGNATURE:
            return this.basicGetReturnType__OperationSignature() != null;
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

} // OperationSignatureImpl
