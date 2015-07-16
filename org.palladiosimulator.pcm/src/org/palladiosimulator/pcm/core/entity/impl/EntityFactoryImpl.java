/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.entity.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.pcm.core.entity.EntityFactory;
import org.palladiosimulator.pcm.core.entity.EntityPackage;
import org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingEntity;
import org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.ResourceInterfaceRequiringEntity;
import org.palladiosimulator.pcm.core.entity.ResourceProvidedRole;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class EntityFactoryImpl extends EFactoryImpl implements EntityFactory {

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
    public static EntityFactory init() {
        try {
            final EntityFactory theEntityFactory = (EntityFactory) EPackage.Registry.INSTANCE
                    .getEFactory(EntityPackage.eNS_URI);
            if (theEntityFactory != null) {
                return theEntityFactory;
            }
        } catch (final Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new EntityFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public EntityFactoryImpl() {
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
        case EntityPackage.RESOURCE_PROVIDED_ROLE:
            return this.createResourceProvidedRole();
        case EntityPackage.RESOURCE_INTERFACE_REQUIRING_ENTITY:
            return this.createResourceInterfaceRequiringEntity();
        case EntityPackage.RESOURCE_REQUIRED_ROLE:
            return this.createResourceRequiredRole();
        case EntityPackage.RESOURCE_INTERFACE_PROVIDING_ENTITY:
            return this.createResourceInterfaceProvidingEntity();
        case EntityPackage.RESOURCE_INTERFACE_PROVIDING_REQUIRING_ENTITY:
            return this.createResourceInterfaceProvidingRequiringEntity();
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
    public ResourceProvidedRole createResourceProvidedRole() {
        final ResourceProvidedRoleImpl resourceProvidedRole = new ResourceProvidedRoleImpl();
        return resourceProvidedRole;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceInterfaceRequiringEntity createResourceInterfaceRequiringEntity() {
        final ResourceInterfaceRequiringEntityImpl resourceInterfaceRequiringEntity = new ResourceInterfaceRequiringEntityImpl();
        return resourceInterfaceRequiringEntity;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceRequiredRole createResourceRequiredRole() {
        final ResourceRequiredRoleImpl resourceRequiredRole = new ResourceRequiredRoleImpl();
        return resourceRequiredRole;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceInterfaceProvidingEntity createResourceInterfaceProvidingEntity() {
        final ResourceInterfaceProvidingEntityImpl resourceInterfaceProvidingEntity = new ResourceInterfaceProvidingEntityImpl();
        return resourceInterfaceProvidingEntity;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceInterfaceProvidingRequiringEntity createResourceInterfaceProvidingRequiringEntity() {
        final ResourceInterfaceProvidingRequiringEntityImpl resourceInterfaceProvidingRequiringEntity = new ResourceInterfaceProvidingRequiringEntityImpl();
        return resourceInterfaceProvidingRequiringEntity;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EntityPackage getEntityPackage() {
        return (EntityPackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @deprecated
     * @generated
     */
    @Deprecated
    public static EntityPackage getPackage() {
        return EntityPackage.eINSTANCE;
    }

} // EntityFactoryImpl
