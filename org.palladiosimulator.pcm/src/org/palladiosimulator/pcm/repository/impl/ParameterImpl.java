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
 *   <li>{@link org.palladiosimulator.pcm.repository.impl.ParameterImpl#getDataType__Parameter <em>Data Type Parameter</em>}</li>
 *   <li>{@link org.palladiosimulator.pcm.repository.impl.ParameterImpl#getInfrastructureSignature__Parameter <em>Infrastructure Signature Parameter</em>}</li>
 *   <li>{@link org.palladiosimulator.pcm.repository.impl.ParameterImpl#getOperationSignature__Parameter <em>Operation Signature Parameter</em>}</li>
 *   <li>{@link org.palladiosimulator.pcm.repository.impl.ParameterImpl#getEventType__Parameter <em>Event Type Parameter</em>}</li>
 *   <li>{@link org.palladiosimulator.pcm.repository.impl.ParameterImpl#getParameterName <em>Parameter Name</em>}</li>
 *   <li>{@link org.palladiosimulator.pcm.repository.impl.ParameterImpl#getModifier__Parameter <em>Modifier Parameter</em>}</li>
 *   <li>{@link org.palladiosimulator.pcm.repository.impl.ParameterImpl#getResourceSignature__Parameter <em>Resource Signature Parameter</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParameterImpl extends NamedElementImpl implements Parameter {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

	/**
	 * The default value of the '{@link #getParameterName() <em>Parameter Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getParameterName()
	 * @generated
	 * @ordered
	 */
	protected static final String PARAMETER_NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getModifier__Parameter() <em>Modifier Parameter</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getModifier__Parameter()
	 * @generated
	 * @ordered
	 */
	protected static final ParameterModifier MODIFIER_PARAMETER_EDEFAULT = ParameterModifier.NONE;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ParameterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryPackage.Literals.PARAMETER;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DataType getDataType__Parameter() {
		return (DataType) eDynamicGet(RepositoryPackage.PARAMETER__DATA_TYPE_PARAMETER,
				RepositoryPackage.Literals.PARAMETER__DATA_TYPE_PARAMETER, true, true);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public DataType basicGetDataType__Parameter() {
		return (DataType) eDynamicGet(RepositoryPackage.PARAMETER__DATA_TYPE_PARAMETER,
				RepositoryPackage.Literals.PARAMETER__DATA_TYPE_PARAMETER, false, true);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDataType__Parameter(DataType newDataType__Parameter) {
		eDynamicSet(RepositoryPackage.PARAMETER__DATA_TYPE_PARAMETER,
				RepositoryPackage.Literals.PARAMETER__DATA_TYPE_PARAMETER, newDataType__Parameter);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InfrastructureSignature getInfrastructureSignature__Parameter() {
		return (InfrastructureSignature) eDynamicGet(RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER,
				RepositoryPackage.Literals.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER, true, true);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInfrastructureSignature__Parameter(
			InfrastructureSignature newInfrastructureSignature__Parameter, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newInfrastructureSignature__Parameter,
				RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInfrastructureSignature__Parameter(InfrastructureSignature newInfrastructureSignature__Parameter) {
		eDynamicSet(RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER,
				RepositoryPackage.Literals.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER,
				newInfrastructureSignature__Parameter);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OperationSignature getOperationSignature__Parameter() {
		return (OperationSignature) eDynamicGet(RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER,
				RepositoryPackage.Literals.PARAMETER__OPERATION_SIGNATURE_PARAMETER, true, true);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOperationSignature__Parameter(OperationSignature newOperationSignature__Parameter,
			NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newOperationSignature__Parameter,
				RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOperationSignature__Parameter(OperationSignature newOperationSignature__Parameter) {
		eDynamicSet(RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER,
				RepositoryPackage.Literals.PARAMETER__OPERATION_SIGNATURE_PARAMETER, newOperationSignature__Parameter);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EventType getEventType__Parameter() {
		return (EventType) eDynamicGet(RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER,
				RepositoryPackage.Literals.PARAMETER__EVENT_TYPE_PARAMETER, true, true);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEventType__Parameter(EventType newEventType__Parameter, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newEventType__Parameter,
				RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEventType__Parameter(EventType newEventType__Parameter) {
		eDynamicSet(RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER,
				RepositoryPackage.Literals.PARAMETER__EVENT_TYPE_PARAMETER, newEventType__Parameter);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getParameterName() {
		return (String) eDynamicGet(RepositoryPackage.PARAMETER__PARAMETER_NAME,
				RepositoryPackage.Literals.PARAMETER__PARAMETER_NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setParameterName(String newParameterName) {
		eDynamicSet(RepositoryPackage.PARAMETER__PARAMETER_NAME, RepositoryPackage.Literals.PARAMETER__PARAMETER_NAME,
				newParameterName);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ParameterModifier getModifier__Parameter() {
		return (ParameterModifier) eDynamicGet(RepositoryPackage.PARAMETER__MODIFIER_PARAMETER,
				RepositoryPackage.Literals.PARAMETER__MODIFIER_PARAMETER, true, true);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModifier__Parameter(ParameterModifier newModifier__Parameter) {
		eDynamicSet(RepositoryPackage.PARAMETER__MODIFIER_PARAMETER,
				RepositoryPackage.Literals.PARAMETER__MODIFIER_PARAMETER, newModifier__Parameter);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceSignature getResourceSignature__Parameter() {
		return (ResourceSignature) eDynamicGet(RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER,
				RepositoryPackage.Literals.PARAMETER__RESOURCE_SIGNATURE_PARAMETER, true, true);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetResourceSignature__Parameter(ResourceSignature newResourceSignature__Parameter,
			NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newResourceSignature__Parameter,
				RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResourceSignature__Parameter(ResourceSignature newResourceSignature__Parameter) {
		eDynamicSet(RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER,
				RepositoryPackage.Literals.PARAMETER__RESOURCE_SIGNATURE_PARAMETER, newResourceSignature__Parameter);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetInfrastructureSignature__Parameter((InfrastructureSignature) otherEnd, msgs);
		case RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetOperationSignature__Parameter((OperationSignature) otherEnd, msgs);
		case RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetEventType__Parameter((EventType) otherEnd, msgs);
		case RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetResourceSignature__Parameter((ResourceSignature) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER:
			return basicSetInfrastructureSignature__Parameter(null, msgs);
		case RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER:
			return basicSetOperationSignature__Parameter(null, msgs);
		case RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER:
			return basicSetEventType__Parameter(null, msgs);
		case RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER:
			return basicSetResourceSignature__Parameter(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
		case RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER:
			return eInternalContainer().eInverseRemove(this,
					RepositoryPackage.INFRASTRUCTURE_SIGNATURE__PARAMETERS_INFRASTRUCTURE_SIGNATURE,
					InfrastructureSignature.class, msgs);
		case RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER:
			return eInternalContainer().eInverseRemove(this,
					RepositoryPackage.OPERATION_SIGNATURE__PARAMETERS_OPERATION_SIGNATURE, OperationSignature.class,
					msgs);
		case RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER:
			return eInternalContainer().eInverseRemove(this, RepositoryPackage.EVENT_TYPE__PARAMETER_EVENT_TYPE,
					EventType.class, msgs);
		case RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER:
			return eInternalContainer().eInverseRemove(this,
					ResourcetypePackage.RESOURCE_SIGNATURE__PARAMETER_RESOURCE_SIGNATURE, ResourceSignature.class,
					msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case RepositoryPackage.PARAMETER__DATA_TYPE_PARAMETER:
			if (resolve)
				return getDataType__Parameter();
			return basicGetDataType__Parameter();
		case RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER:
			return getInfrastructureSignature__Parameter();
		case RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER:
			return getOperationSignature__Parameter();
		case RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER:
			return getEventType__Parameter();
		case RepositoryPackage.PARAMETER__PARAMETER_NAME:
			return getParameterName();
		case RepositoryPackage.PARAMETER__MODIFIER_PARAMETER:
			return getModifier__Parameter();
		case RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER:
			return getResourceSignature__Parameter();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case RepositoryPackage.PARAMETER__DATA_TYPE_PARAMETER:
			setDataType__Parameter((DataType) newValue);
			return;
		case RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER:
			setInfrastructureSignature__Parameter((InfrastructureSignature) newValue);
			return;
		case RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER:
			setOperationSignature__Parameter((OperationSignature) newValue);
			return;
		case RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER:
			setEventType__Parameter((EventType) newValue);
			return;
		case RepositoryPackage.PARAMETER__PARAMETER_NAME:
			setParameterName((String) newValue);
			return;
		case RepositoryPackage.PARAMETER__MODIFIER_PARAMETER:
			setModifier__Parameter((ParameterModifier) newValue);
			return;
		case RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER:
			setResourceSignature__Parameter((ResourceSignature) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case RepositoryPackage.PARAMETER__DATA_TYPE_PARAMETER:
			setDataType__Parameter((DataType) null);
			return;
		case RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER:
			setInfrastructureSignature__Parameter((InfrastructureSignature) null);
			return;
		case RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER:
			setOperationSignature__Parameter((OperationSignature) null);
			return;
		case RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER:
			setEventType__Parameter((EventType) null);
			return;
		case RepositoryPackage.PARAMETER__PARAMETER_NAME:
			setParameterName(PARAMETER_NAME_EDEFAULT);
			return;
		case RepositoryPackage.PARAMETER__MODIFIER_PARAMETER:
			setModifier__Parameter(MODIFIER_PARAMETER_EDEFAULT);
			return;
		case RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER:
			setResourceSignature__Parameter((ResourceSignature) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case RepositoryPackage.PARAMETER__DATA_TYPE_PARAMETER:
			return basicGetDataType__Parameter() != null;
		case RepositoryPackage.PARAMETER__INFRASTRUCTURE_SIGNATURE_PARAMETER:
			return getInfrastructureSignature__Parameter() != null;
		case RepositoryPackage.PARAMETER__OPERATION_SIGNATURE_PARAMETER:
			return getOperationSignature__Parameter() != null;
		case RepositoryPackage.PARAMETER__EVENT_TYPE_PARAMETER:
			return getEventType__Parameter() != null;
		case RepositoryPackage.PARAMETER__PARAMETER_NAME:
			return PARAMETER_NAME_EDEFAULT == null ? getParameterName() != null
					: !PARAMETER_NAME_EDEFAULT.equals(getParameterName());
		case RepositoryPackage.PARAMETER__MODIFIER_PARAMETER:
			return getModifier__Parameter() != MODIFIER_PARAMETER_EDEFAULT;
		case RepositoryPackage.PARAMETER__RESOURCE_SIGNATURE_PARAMETER:
			return getResourceSignature__Parameter() != null;
		}
		return super.eIsSet(featureID);
	}

} // ParameterImpl
