/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.palladiosimulator.pcm.core.entity.impl.NamedElementImpl;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.resourcetype.ResourcetypePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Parameter</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.repository.impl.ParameterImpl#getDataType__Parameter
 * <em>Data Type Parameter</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.impl.ParameterImpl#getInfrastructureSignature__Parameter
 * <em>Infrastructure Signature Parameter</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.impl.ParameterImpl#getOperationSignature__Parameter
 * <em>Operation Signature Parameter</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.repository.impl.ParameterImpl#getEventType__Parameter
 * <em>Event Type Parameter</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.repository.impl.ParameterImpl#getParameterName
 * <em>Parameter Name</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.repository.impl.ParameterImpl#getModifier__Parameter
 * <em>Modifier Parameter</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.repository.impl.ParameterImpl#getResourceSignature__Parameter
 * <em>Resource Signature Parameter</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParameterImpl extends NamedElementImpl implements Parameter {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The default value of the '{@link #getParameterName() <em>Parameter Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getParameterName()
     * @generated
     * @ordered
     */
    protected static final String PARAMETER_NAME_EDEFAULT = null;

    /**
     * The default value of the '{@link #getModifier__Parameter() <em>Modifier Parameter</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getModifier__Parameter()
     * @generated
     * @ordered
     */
    protected static final ParameterModifier MODIFIER_PARAMETER_EDEFAULT = ParameterModifier.NONE;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected ParameterImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RepositoryPackage.Literals.PARAMETER;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public DataType getDataType__Parameter() {
        return (DataType) this.eDynamicGet(RepositoryPackage.PARAMETER__DATA_TYPE_PARAMETER,
                RepositoryPackage.Literals.PARAMETER__DATA_TYPE_PARAMETER, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public DataType basicGetDataType__Parameter() {
        return (DataType) this.eDynamicGet(RepositoryPackage.PARAMETER__DATA_TYPE_PARAMETER,
                RepositoryPackage.Literals.PARAMETER__DATA_TYPE_PARAMETER, false, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setDataType__Parameter(final DataType newDataType__Parameter) {
        this.eDynamicSet(RepositoryPackage.PARAMETER__DATA_TYPE_PARAMETER,
                RepositoryPackage.Literals.PARAMETER__DATA_TYPE_PARAMETER, newDataType__Parameter);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public InfrastructureSignature getInfrastructureSignature__Parameter() {
        return (InfrastructureSignature) this.eDynamicGet(
                RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER,
                RepositoryPackage.Literals.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetInfrastructureSignature__Parameter(
            final InfrastructureSignature newInfrastructureSignature__Parameter, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newInfrastructureSignature__Parameter,
                RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setInfrastructureSignature__Parameter(
            final InfrastructureSignature newInfrastructureSignature__Parameter) {
        this.eDynamicSet(RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER,
                RepositoryPackage.Literals.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER,
                newInfrastructureSignature__Parameter);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public OperationSignature getOperationSignature__Parameter() {
        return (OperationSignature) this.eDynamicGet(RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER,
                RepositoryPackage.Literals.PARAMETER__OPERATION_SIGNATURE_PARAMETER, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetOperationSignature__Parameter(
            final OperationSignature newOperationSignature__Parameter, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newOperationSignature__Parameter,
                RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setOperationSignature__Parameter(final OperationSignature newOperationSignature__Parameter) {
        this.eDynamicSet(RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER,
                RepositoryPackage.Literals.PARAMETER__OPERATION_SIGNATURE_PARAMETER, newOperationSignature__Parameter);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EventType getEventType__Parameter() {
        return (EventType) this.eDynamicGet(RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER,
                RepositoryPackage.Literals.PARAMETER__EVENT_TYPE_PARAMETER, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetEventType__Parameter(final EventType newEventType__Parameter,
            NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newEventType__Parameter,
                RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setEventType__Parameter(final EventType newEventType__Parameter) {
        this.eDynamicSet(RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER,
                RepositoryPackage.Literals.PARAMETER__EVENT_TYPE_PARAMETER, newEventType__Parameter);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getParameterName() {
        return (String) this.eDynamicGet(RepositoryPackage.PARAMETER__PARAMETER_NAME,
                RepositoryPackage.Literals.PARAMETER__PARAMETER_NAME, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setParameterName(final String newParameterName) {
        this.eDynamicSet(RepositoryPackage.PARAMETER__PARAMETER_NAME,
                RepositoryPackage.Literals.PARAMETER__PARAMETER_NAME, newParameterName);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ParameterModifier getModifier__Parameter() {
        return (ParameterModifier) this.eDynamicGet(RepositoryPackage.PARAMETER__MODIFIER_PARAMETER,
                RepositoryPackage.Literals.PARAMETER__MODIFIER_PARAMETER, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setModifier__Parameter(final ParameterModifier newModifier__Parameter) {
        this.eDynamicSet(RepositoryPackage.PARAMETER__MODIFIER_PARAMETER,
                RepositoryPackage.Literals.PARAMETER__MODIFIER_PARAMETER, newModifier__Parameter);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceSignature getResourceSignature__Parameter() {
        return (ResourceSignature) this.eDynamicGet(RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER,
                RepositoryPackage.Literals.PARAMETER__RESOURCE_SIGNATURE_PARAMETER, true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetResourceSignature__Parameter(
            final ResourceSignature newResourceSignature__Parameter, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newResourceSignature__Parameter,
                RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setResourceSignature__Parameter(final ResourceSignature newResourceSignature__Parameter) {
        this.eDynamicSet(RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER,
                RepositoryPackage.Literals.PARAMETER__RESOURCE_SIGNATURE_PARAMETER, newResourceSignature__Parameter);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetInfrastructureSignature__Parameter((InfrastructureSignature) otherEnd, msgs);
        case RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetOperationSignature__Parameter((OperationSignature) otherEnd, msgs);
        case RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetEventType__Parameter((EventType) otherEnd, msgs);
        case RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetResourceSignature__Parameter((ResourceSignature) otherEnd, msgs);
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
        case RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER:
            return this.basicSetInfrastructureSignature__Parameter(null, msgs);
        case RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER:
            return this.basicSetOperationSignature__Parameter(null, msgs);
        case RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER:
            return this.basicSetEventType__Parameter(null, msgs);
        case RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER:
            return this.basicSetResourceSignature__Parameter(null, msgs);
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
        case RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER:
            return this.eInternalContainer().eInverseRemove(this,
                    RepositoryPackage.INFRASTRUCTURE_SIGNATURE__PARAMETERS_INFRASTRUCTURE_SIGNATURE,
                    InfrastructureSignature.class, msgs);
        case RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER:
            return this.eInternalContainer().eInverseRemove(this,
                    RepositoryPackage.OPERATION_SIGNATURE__PARAMETERS_OPERATION_SIGNATURE, OperationSignature.class,
                    msgs);
        case RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER:
            return this.eInternalContainer().eInverseRemove(this, RepositoryPackage.EVENT_TYPE__PARAMETER_EVENT_TYPE,
                    EventType.class, msgs);
        case RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER:
            return this.eInternalContainer().eInverseRemove(this,
                    ResourcetypePackage.RESOURCE_SIGNATURE__PARAMETER_RESOURCE_SIGNATURE, ResourceSignature.class,
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
        case RepositoryPackage.PARAMETER__DATA_TYPE_PARAMETER:
            if (resolve) {
                return this.getDataType__Parameter();
            }
            return this.basicGetDataType__Parameter();
        case RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER:
            return this.getInfrastructureSignature__Parameter();
        case RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER:
            return this.getOperationSignature__Parameter();
        case RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER:
            return this.getEventType__Parameter();
        case RepositoryPackage.PARAMETER__PARAMETER_NAME:
            return this.getParameterName();
        case RepositoryPackage.PARAMETER__MODIFIER_PARAMETER:
            return this.getModifier__Parameter();
        case RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER:
            return this.getResourceSignature__Parameter();
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
        case RepositoryPackage.PARAMETER__DATA_TYPE_PARAMETER:
            this.setDataType__Parameter((DataType) newValue);
            return;
        case RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER:
            this.setInfrastructureSignature__Parameter((InfrastructureSignature) newValue);
            return;
        case RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER:
            this.setOperationSignature__Parameter((OperationSignature) newValue);
            return;
        case RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER:
            this.setEventType__Parameter((EventType) newValue);
            return;
        case RepositoryPackage.PARAMETER__PARAMETER_NAME:
            this.setParameterName((String) newValue);
            return;
        case RepositoryPackage.PARAMETER__MODIFIER_PARAMETER:
            this.setModifier__Parameter((ParameterModifier) newValue);
            return;
        case RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER:
            this.setResourceSignature__Parameter((ResourceSignature) newValue);
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
        case RepositoryPackage.PARAMETER__DATA_TYPE_PARAMETER:
            this.setDataType__Parameter((DataType) null);
            return;
        case RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER:
            this.setInfrastructureSignature__Parameter((InfrastructureSignature) null);
            return;
        case RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER:
            this.setOperationSignature__Parameter((OperationSignature) null);
            return;
        case RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER:
            this.setEventType__Parameter((EventType) null);
            return;
        case RepositoryPackage.PARAMETER__PARAMETER_NAME:
            this.setParameterName(PARAMETER_NAME_EDEFAULT);
            return;
        case RepositoryPackage.PARAMETER__MODIFIER_PARAMETER:
            this.setModifier__Parameter(MODIFIER_PARAMETER_EDEFAULT);
            return;
        case RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER:
            this.setResourceSignature__Parameter((ResourceSignature) null);
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
        case RepositoryPackage.PARAMETER__DATA_TYPE_PARAMETER:
            return this.basicGetDataType__Parameter() != null;
        case RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER:
            return this.getInfrastructureSignature__Parameter() != null;
        case RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER:
            return this.getOperationSignature__Parameter() != null;
        case RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER:
            return this.getEventType__Parameter() != null;
        case RepositoryPackage.PARAMETER__PARAMETER_NAME:
            return PARAMETER_NAME_EDEFAULT == null ? this.getParameterName() != null
                    : !PARAMETER_NAME_EDEFAULT.equals(this.getParameterName());
        case RepositoryPackage.PARAMETER__MODIFIER_PARAMETER:
            return this.getModifier__Parameter() != MODIFIER_PARAMETER_EDEFAULT;
        case RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER:
            return this.getResourceSignature__Parameter() != null;
        }
        return super.eIsSet(featureID);
    }

} // ParameterImpl
