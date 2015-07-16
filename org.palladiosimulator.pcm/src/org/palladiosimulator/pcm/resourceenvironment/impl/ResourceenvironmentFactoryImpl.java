/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.resourceenvironment.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.pcm.resourceenvironment.CommunicationLinkResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentFactory;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class ResourceenvironmentFactoryImpl extends EFactoryImpl implements ResourceenvironmentFactory {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static ResourceenvironmentFactory init() {
        try {
            final ResourceenvironmentFactory theResourceenvironmentFactory = (ResourceenvironmentFactory) EPackage.Registry.INSTANCE
                    .getEFactory(ResourceenvironmentPackage.eNS_URI);
            if (theResourceenvironmentFactory != null) {
                return theResourceenvironmentFactory;
            }
        } catch (final Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new ResourceenvironmentFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ResourceenvironmentFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EObject create(final EClass eClass) {
        switch (eClass.getClassifierID()) {
        case ResourceenvironmentPackage.RESOURCE_ENVIRONMENT:
            return this.createResourceEnvironment();
        case ResourceenvironmentPackage.LINKING_RESOURCE:
            return this.createLinkingResource();
        case ResourceenvironmentPackage.RESOURCE_CONTAINER:
            return this.createResourceContainer();
        case ResourceenvironmentPackage.PROCESSING_RESOURCE_SPECIFICATION:
            return this.createProcessingResourceSpecification();
        case ResourceenvironmentPackage.COMMUNICATION_LINK_RESOURCE_SPECIFICATION:
            return this.createCommunicationLinkResourceSpecification();
        default:
            throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceEnvironment createResourceEnvironment() {
        final ResourceEnvironmentImpl resourceEnvironment = new ResourceEnvironmentImpl();
        return resourceEnvironment;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LinkingResource createLinkingResource() {
        final LinkingResourceImpl linkingResource = new LinkingResourceImpl();
        return linkingResource;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceContainer createResourceContainer() {
        final ResourceContainerImpl resourceContainer = new ResourceContainerImpl();
        return resourceContainer;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ProcessingResourceSpecification createProcessingResourceSpecification() {
        final ProcessingResourceSpecificationImpl processingResourceSpecification = new ProcessingResourceSpecificationImpl();
        return processingResourceSpecification;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CommunicationLinkResourceSpecification createCommunicationLinkResourceSpecification() {
        final CommunicationLinkResourceSpecificationImpl communicationLinkResourceSpecification = new CommunicationLinkResourceSpecificationImpl();
        return communicationLinkResourceSpecification;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceenvironmentPackage getResourceenvironmentPackage() {
        return (ResourceenvironmentPackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @deprecated
     * @generated
     */
    @Deprecated
    public static ResourceenvironmentPackage getPackage() {
        return ResourceenvironmentPackage.eINSTANCE;
    }

} // ResourceenvironmentFactoryImpl
