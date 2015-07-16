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
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.repository.util.RepositoryValidator;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Operation Interface</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.impl.OperationInterfaceImpl#getSignatures__OperationInterface
 * <em>Signatures Operation Interface</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OperationInterfaceImpl extends InterfaceImpl implements OperationInterface {

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
    protected OperationInterfaceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RepositoryPackage.Literals.OPERATION_INTERFACE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<OperationSignature> getSignatures__OperationInterface() {
        return (EList<OperationSignature>) this.eDynamicGet(
                RepositoryPackage.OPERATION_INTERFACE__SIGNATURES_OPERATION_INTERFACE,
                RepositoryPackage.Literals.OPERATION_INTERFACE__SIGNATURES_OPERATION_INTERFACE, true, true);
    }

    /**
     * The cached OCL expression body for the '
     * {@link #SignaturesHaveToBeUniqueForAnInterface(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Signatures Have To Be Unique For An Interface</em>}' operation. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #SignaturesHaveToBeUniqueForAnInterface(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static final String SIGNATURES_HAVE_TO_BE_UNIQUE_FOR_AN_INTERFACE__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP = "-- full signature has to be unique \n"
            + "-- (use of ocl-tupels) #\n" + "let sigs : Bag(\n"
            + "	-- parameters: Sequence of DataType, NOT name #\n" + "	-- exceptions have not to be considered #\n"
            + "	Tuple(returnType : DataType, serviceName : String, parameters : Sequence(DataType) ) \n" + ") = \n"
            + "self.signatures__OperationInterface->collect(sig : OperationSignature |\n" + "	Tuple{\n"
            + "		returnType : DataType = sig.returnType__OperationSignature,\n"
            + "		serviceName : String = sig.entityName,\n"
            + "		parameters : Sequence(DataType) = sig.parameters__OperationSignature.dataType__Parameter\n" + "	}\n"
            + ")\n" + "in\n" + "sigs->isUnique(s|s)";
    /**
     * The cached OCL invariant for the '
     * {@link #SignaturesHaveToBeUniqueForAnInterface(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
     * <em>Signatures Have To Be Unique For An Interface</em>}' invariant operation. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #SignaturesHaveToBeUniqueForAnInterface(org.eclipse.emf.common.util.DiagnosticChain,
     *      java.util.Map)
     * @generated
     * @ordered
     */
    protected static Constraint SIGNATURES_HAVE_TO_BE_UNIQUE_FOR_AN_INTERFACE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean SignaturesHaveToBeUniqueForAnInterface(final DiagnosticChain diagnostics,
            final Map<Object, Object> context) {
        if (SIGNATURES_HAVE_TO_BE_UNIQUE_FOR_AN_INTERFACE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV == null) {
            final OCL.Helper helper = EOCL_ENV.createOCLHelper();
            helper.setContext(RepositoryPackage.Literals.OPERATION_INTERFACE);
            try {
                SIGNATURES_HAVE_TO_BE_UNIQUE_FOR_AN_INTERFACE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV = helper
                        .createInvariant(SIGNATURES_HAVE_TO_BE_UNIQUE_FOR_AN_INTERFACE__DIAGNOSTIC_CHAIN_MAP__EOCL_EXP);
            } catch (final ParserException pe) {
                throw new UnsupportedOperationException(pe.getLocalizedMessage());
            }
        }
        if (!EOCL_ENV.createQuery(SIGNATURES_HAVE_TO_BE_UNIQUE_FOR_AN_INTERFACE__DIAGNOSTIC_CHAIN_MAP__EOCL_INV)
                .check(this)) {
            if (diagnostics != null) {
                diagnostics
                        .add(new BasicDiagnostic(Diagnostic.ERROR, RepositoryValidator.DIAGNOSTIC_SOURCE,
                                RepositoryValidator.OPERATION_INTERFACE__SIGNATURES_HAVE_TO_BE_UNIQUE_FOR_AN_INTERFACE,
                                EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic",
                                        new Object[] { "SignaturesHaveToBeUniqueForAnInterface",
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
        case RepositoryPackage.OPERATION_INTERFACE__SIGNATURES_OPERATION_INTERFACE:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getSignatures__OperationInterface())
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
        case RepositoryPackage.OPERATION_INTERFACE__SIGNATURES_OPERATION_INTERFACE:
            return ((InternalEList<?>) this.getSignatures__OperationInterface()).basicRemove(otherEnd, msgs);
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
        case RepositoryPackage.OPERATION_INTERFACE__SIGNATURES_OPERATION_INTERFACE:
            return this.getSignatures__OperationInterface();
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
        case RepositoryPackage.OPERATION_INTERFACE__SIGNATURES_OPERATION_INTERFACE:
            this.getSignatures__OperationInterface().clear();
            this.getSignatures__OperationInterface().addAll((Collection<? extends OperationSignature>) newValue);
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
        case RepositoryPackage.OPERATION_INTERFACE__SIGNATURES_OPERATION_INTERFACE:
            this.getSignatures__OperationInterface().clear();
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
        case RepositoryPackage.OPERATION_INTERFACE__SIGNATURES_OPERATION_INTERFACE:
            return !this.getSignatures__OperationInterface().isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // OperationInterfaceImpl
