/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.resourceenvironment.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.palladiosimulator.pcm.core.entity.impl.EntityImpl;
import org.palladiosimulator.pcm.resourceenvironment.CommunicationLinkResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Linking Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.resourceenvironment.impl.LinkingResourceImpl#getConnectedResourceContainers_LinkingResource
 * <em>Connected Resource Containers Linking Resource</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.resourceenvironment.impl.LinkingResourceImpl#getCommunicationLinkResourceSpecifications_LinkingResource
 * <em>Communication Link Resource Specifications Linking Resource</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.resourceenvironment.impl.LinkingResourceImpl#getResourceEnvironment_LinkingResource
 * <em>Resource Environment Linking Resource</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LinkingResourceImpl extends EntityImpl implements LinkingResource {

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
    protected LinkingResourceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ResourceenvironmentPackage.Literals.LINKING_RESOURCE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<ResourceContainer> getConnectedResourceContainers_LinkingResource() {
        return (EList<ResourceContainer>) this.eDynamicGet(
                ResourceenvironmentPackage.LINKING_RESOURCE__CONNECTED_RESOURCE_CONTAINERS_LINKING_RESOURCE,
                ResourceenvironmentPackage.Literals.LINKING_RESOURCE__CONNECTED_RESOURCE_CONTAINERS_LINKING_RESOURCE,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public CommunicationLinkResourceSpecification getCommunicationLinkResourceSpecifications_LinkingResource() {
        return (CommunicationLinkResourceSpecification) this.eDynamicGet(
                ResourceenvironmentPackage.LINKING_RESOURCE__COMMUNICATION_LINK_RESOURCE_SPECIFICATIONS_LINKING_RESOURCE,
                ResourceenvironmentPackage.Literals.LINKING_RESOURCE__COMMUNICATION_LINK_RESOURCE_SPECIFICATIONS_LINKING_RESOURCE,
                true, true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetCommunicationLinkResourceSpecifications_LinkingResource(
            final CommunicationLinkResourceSpecification newCommunicationLinkResourceSpecifications_LinkingResource,
            NotificationChain msgs) {
        msgs = this.eDynamicInverseAdd((InternalEObject) newCommunicationLinkResourceSpecifications_LinkingResource,
                ResourceenvironmentPackage.LINKING_RESOURCE__COMMUNICATION_LINK_RESOURCE_SPECIFICATIONS_LINKING_RESOURCE,
                msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setCommunicationLinkResourceSpecifications_LinkingResource(
            final CommunicationLinkResourceSpecification newCommunicationLinkResourceSpecifications_LinkingResource) {
        this.eDynamicSet(
                ResourceenvironmentPackage.LINKING_RESOURCE__COMMUNICATION_LINK_RESOURCE_SPECIFICATIONS_LINKING_RESOURCE,
                ResourceenvironmentPackage.Literals.LINKING_RESOURCE__COMMUNICATION_LINK_RESOURCE_SPECIFICATIONS_LINKING_RESOURCE,
                newCommunicationLinkResourceSpecifications_LinkingResource);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceEnvironment getResourceEnvironment_LinkingResource() {
        return (ResourceEnvironment) this.eDynamicGet(
                ResourceenvironmentPackage.LINKING_RESOURCE__RESOURCE_ENVIRONMENT_LINKING_RESOURCE,
                ResourceenvironmentPackage.Literals.LINKING_RESOURCE__RESOURCE_ENVIRONMENT_LINKING_RESOURCE, true,
                true);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetResourceEnvironment_LinkingResource(
            final ResourceEnvironment newResourceEnvironment_LinkingResource, NotificationChain msgs) {
        msgs = this.eBasicSetContainer((InternalEObject) newResourceEnvironment_LinkingResource,
                ResourceenvironmentPackage.LINKING_RESOURCE__RESOURCE_ENVIRONMENT_LINKING_RESOURCE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setResourceEnvironment_LinkingResource(
            final ResourceEnvironment newResourceEnvironment_LinkingResource) {
        this.eDynamicSet(ResourceenvironmentPackage.LINKING_RESOURCE__RESOURCE_ENVIRONMENT_LINKING_RESOURCE,
                ResourceenvironmentPackage.Literals.LINKING_RESOURCE__RESOURCE_ENVIRONMENT_LINKING_RESOURCE,
                newResourceEnvironment_LinkingResource);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ResourceenvironmentPackage.LINKING_RESOURCE__COMMUNICATION_LINK_RESOURCE_SPECIFICATIONS_LINKING_RESOURCE:
            final CommunicationLinkResourceSpecification communicationLinkResourceSpecifications_LinkingResource = this
                    .getCommunicationLinkResourceSpecifications_LinkingResource();
            if (communicationLinkResourceSpecifications_LinkingResource != null) {
                msgs = ((InternalEObject) communicationLinkResourceSpecifications_LinkingResource).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - ResourceenvironmentPackage.LINKING_RESOURCE__COMMUNICATION_LINK_RESOURCE_SPECIFICATIONS_LINKING_RESOURCE,
                        null, msgs);
            }
            return this.basicSetCommunicationLinkResourceSpecifications_LinkingResource(
                    (CommunicationLinkResourceSpecification) otherEnd, msgs);
        case ResourceenvironmentPackage.LINKING_RESOURCE__RESOURCE_ENVIRONMENT_LINKING_RESOURCE:
            if (this.eInternalContainer() != null) {
                msgs = this.eBasicRemoveFromContainer(msgs);
            }
            return this.basicSetResourceEnvironment_LinkingResource((ResourceEnvironment) otherEnd, msgs);
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
        case ResourceenvironmentPackage.LINKING_RESOURCE__COMMUNICATION_LINK_RESOURCE_SPECIFICATIONS_LINKING_RESOURCE:
            return this.basicSetCommunicationLinkResourceSpecifications_LinkingResource(null, msgs);
        case ResourceenvironmentPackage.LINKING_RESOURCE__RESOURCE_ENVIRONMENT_LINKING_RESOURCE:
            return this.basicSetResourceEnvironment_LinkingResource(null, msgs);
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
        case ResourceenvironmentPackage.LINKING_RESOURCE__RESOURCE_ENVIRONMENT_LINKING_RESOURCE:
            return this.eInternalContainer().eInverseRemove(this,
                    ResourceenvironmentPackage.RESOURCE_ENVIRONMENT__LINKING_RESOURCES_RESOURCE_ENVIRONMENT,
                    ResourceEnvironment.class, msgs);
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
        case ResourceenvironmentPackage.LINKING_RESOURCE__CONNECTED_RESOURCE_CONTAINERS_LINKING_RESOURCE:
            return this.getConnectedResourceContainers_LinkingResource();
        case ResourceenvironmentPackage.LINKING_RESOURCE__COMMUNICATION_LINK_RESOURCE_SPECIFICATIONS_LINKING_RESOURCE:
            return this.getCommunicationLinkResourceSpecifications_LinkingResource();
        case ResourceenvironmentPackage.LINKING_RESOURCE__RESOURCE_ENVIRONMENT_LINKING_RESOURCE:
            return this.getResourceEnvironment_LinkingResource();
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
        case ResourceenvironmentPackage.LINKING_RESOURCE__CONNECTED_RESOURCE_CONTAINERS_LINKING_RESOURCE:
            this.getConnectedResourceContainers_LinkingResource().clear();
            this.getConnectedResourceContainers_LinkingResource()
                    .addAll((Collection<? extends ResourceContainer>) newValue);
            return;
        case ResourceenvironmentPackage.LINKING_RESOURCE__COMMUNICATION_LINK_RESOURCE_SPECIFICATIONS_LINKING_RESOURCE:
            this.setCommunicationLinkResourceSpecifications_LinkingResource(
                    (CommunicationLinkResourceSpecification) newValue);
            return;
        case ResourceenvironmentPackage.LINKING_RESOURCE__RESOURCE_ENVIRONMENT_LINKING_RESOURCE:
            this.setResourceEnvironment_LinkingResource((ResourceEnvironment) newValue);
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
        case ResourceenvironmentPackage.LINKING_RESOURCE__CONNECTED_RESOURCE_CONTAINERS_LINKING_RESOURCE:
            this.getConnectedResourceContainers_LinkingResource().clear();
            return;
        case ResourceenvironmentPackage.LINKING_RESOURCE__COMMUNICATION_LINK_RESOURCE_SPECIFICATIONS_LINKING_RESOURCE:
            this.setCommunicationLinkResourceSpecifications_LinkingResource(
                    (CommunicationLinkResourceSpecification) null);
            return;
        case ResourceenvironmentPackage.LINKING_RESOURCE__RESOURCE_ENVIRONMENT_LINKING_RESOURCE:
            this.setResourceEnvironment_LinkingResource((ResourceEnvironment) null);
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
        case ResourceenvironmentPackage.LINKING_RESOURCE__CONNECTED_RESOURCE_CONTAINERS_LINKING_RESOURCE:
            return !this.getConnectedResourceContainers_LinkingResource().isEmpty();
        case ResourceenvironmentPackage.LINKING_RESOURCE__COMMUNICATION_LINK_RESOURCE_SPECIFICATIONS_LINKING_RESOURCE:
            return this.getCommunicationLinkResourceSpecifications_LinkingResource() != null;
        case ResourceenvironmentPackage.LINKING_RESOURCE__RESOURCE_ENVIRONMENT_LINKING_RESOURCE:
            return this.getResourceEnvironment_LinkingResource() != null;
        }
        return super.eIsSet(featureID);
    }

} // LinkingResourceImpl
