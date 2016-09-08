/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;
import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.repository.RequiredCharacterisation;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Required Characterisation</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.repository.impl.RequiredCharacterisationImpl#getType
 * <em>Type</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.repository.impl.RequiredCharacterisationImpl#getParameter
 * <em>Parameter</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.impl.RequiredCharacterisationImpl#getInterface_RequiredCharacterisation
 * <em>Interface Required Characterisation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RequiredCharacterisationImpl extends CDOObjectImpl implements RequiredCharacterisation {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The default value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final VariableCharacterisationType TYPE_EDEFAULT = VariableCharacterisationType.STRUCTURE;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected RequiredCharacterisationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RepositoryPackage.Literals.REQUIRED_CHARACTERISATION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected int eStaticFeatureCount() {
        return 0;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public VariableCharacterisationType getType() {
        return (VariableCharacterisationType) this.eDynamicGet(RepositoryPackage.REQUIRED_CHARACTERISATION__TYPE,
                RepositoryPackage.Literals.REQUIRED_CHARACTERISATION__TYPE, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setType(final VariableCharacterisationType newType) {
        this.eDynamicSet(RepositoryPackage.REQUIRED_CHARACTERISATION__TYPE,
                RepositoryPackage.Literals.REQUIRED_CHARACTERISATION__TYPE, newType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Parameter getParameter() {
        return (Parameter) this.eDynamicGet(RepositoryPackage.REQUIRED_CHARACTERISATION__PARAMETER,
                RepositoryPackage.Literals.REQUIRED_CHARACTERISATION__PARAMETER, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Parameter basicGetParameter() {
        return (Parameter) this.eDynamicGet(RepositoryPackage.REQUIRED_CHARACTERISATION__PARAMETER,
                RepositoryPackage.Literals.REQUIRED_CHARACTERISATION__PARAMETER, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setParameter(final Parameter newParameter) {
        this.eDynamicSet(RepositoryPackage.REQUIRED_CHARACTERISATION__PARAMETER,
                RepositoryPackage.Literals.REQUIRED_CHARACTERISATION__PARAMETER, newParameter);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Interface getInterface_RequiredCharacterisation() {
        return (Interface) this.eDynamicGet(
                RepositoryPackage.REQUIRED_CHARACTERISATION__INTERFACE_REQUIRED_CHARACTERISATION,
                RepositoryPackage.Literals.REQUIRED_CHARACTERISATION__INTERFACE_REQUIRED_CHARACTERISATION, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetInterface_RequiredCharacterisation(
            final Interface newInterface_RequiredCharacterisation, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newInterface_RequiredCharacterisation,
                RepositoryPackage.REQUIRED_CHARACTERISATION__INTERFACE_REQUIRED_CHARACTERISATION, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setInterface_RequiredCharacterisation(final Interface newInterface_RequiredCharacterisation) {
        this.eDynamicSet(RepositoryPackage.REQUIRED_CHARACTERISATION__INTERFACE_REQUIRED_CHARACTERISATION,
                RepositoryPackage.Literals.REQUIRED_CHARACTERISATION__INTERFACE_REQUIRED_CHARACTERISATION,
                newInterface_RequiredCharacterisation);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case RepositoryPackage.REQUIRED_CHARACTERISATION__INTERFACE_REQUIRED_CHARACTERISATION:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetInterface_RequiredCharacterisation((Interface) otherEnd, msgs);
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
        case RepositoryPackage.REQUIRED_CHARACTERISATION__INTERFACE_REQUIRED_CHARACTERISATION:
            return this.basicSetInterface_RequiredCharacterisation(null, msgs);
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
        case RepositoryPackage.REQUIRED_CHARACTERISATION__INTERFACE_REQUIRED_CHARACTERISATION:
            return this.eInternalContainer().eInverseRemove(this,
                    RepositoryPackage.INTERFACE__REQUIRED_CHARACTERISATIONS, Interface.class, msgs);
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
        case RepositoryPackage.REQUIRED_CHARACTERISATION__TYPE:
            return this.getType();
        case RepositoryPackage.REQUIRED_CHARACTERISATION__PARAMETER:
            if (resolve) {
                return this.getParameter();
            }
            return this.basicGetParameter();
        case RepositoryPackage.REQUIRED_CHARACTERISATION__INTERFACE_REQUIRED_CHARACTERISATION:
            return this.getInterface_RequiredCharacterisation();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eSet(final int featureID, final Object newValue) {
        switch (featureID) {
        case RepositoryPackage.REQUIRED_CHARACTERISATION__TYPE:
            this.setType((VariableCharacterisationType) newValue);
            return;
        case RepositoryPackage.REQUIRED_CHARACTERISATION__PARAMETER:
            this.setParameter((Parameter) newValue);
            return;
        case RepositoryPackage.REQUIRED_CHARACTERISATION__INTERFACE_REQUIRED_CHARACTERISATION:
            this.setInterface_RequiredCharacterisation((Interface) newValue);
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
        case RepositoryPackage.REQUIRED_CHARACTERISATION__TYPE:
            this.setType(TYPE_EDEFAULT);
            return;
        case RepositoryPackage.REQUIRED_CHARACTERISATION__PARAMETER:
            this.setParameter((Parameter) null);
            return;
        case RepositoryPackage.REQUIRED_CHARACTERISATION__INTERFACE_REQUIRED_CHARACTERISATION:
            this.setInterface_RequiredCharacterisation((Interface) null);
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
        case RepositoryPackage.REQUIRED_CHARACTERISATION__TYPE:
            return this.getType() != TYPE_EDEFAULT;
        case RepositoryPackage.REQUIRED_CHARACTERISATION__PARAMETER:
            return this.basicGetParameter() != null;
        case RepositoryPackage.REQUIRED_CHARACTERISATION__INTERFACE_REQUIRED_CHARACTERISATION:
            return this.getInterface_RequiredCharacterisation() != null;
        }
        return super.eIsSet(featureID);
    }

} // RequiredCharacterisationImpl
