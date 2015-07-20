/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.entity.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.core.entity.EntityPackage;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingEntity;
import org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.ResourceInterfaceRequiringEntity;
import org.palladiosimulator.pcm.core.entity.ResourceProvidedRole;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.Role;

import de.uka.ipd.sdq.identifier.Identifier;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter
 * <code>createXXX</code> method for each class of the model. <!-- end-user-doc -->
 * 
 * @see org.palladiosimulator.pcm.core.entity.EntityPackage
 * @generated
 */
public class EntityAdapterFactory extends AdapterFactoryImpl {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected static EntityPackage modelPackage;

    /**
     * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public EntityAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = EntityPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc
     * --> This implementation returns <code>true</code> if the object is either the model's package
     * or is an instance object of the model. <!-- end-user-doc -->
     * 
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(final Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject) object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    protected EntitySwitch<Adapter> modelSwitch = new EntitySwitch<Adapter>() {
        @Override
        public Adapter caseResourceProvidedRole(final ResourceProvidedRole object) {
            return EntityAdapterFactory.this.createResourceProvidedRoleAdapter();
        }

        @Override
        public Adapter caseInterfaceProvidingRequiringEntity(final InterfaceProvidingRequiringEntity object) {
            return EntityAdapterFactory.this.createInterfaceProvidingRequiringEntityAdapter();
        }

        @Override
        public Adapter caseInterfaceProvidingEntity(final InterfaceProvidingEntity object) {
            return EntityAdapterFactory.this.createInterfaceProvidingEntityAdapter();
        }

        @Override
        public Adapter caseInterfaceRequiringEntity(final InterfaceRequiringEntity object) {
            return EntityAdapterFactory.this.createInterfaceRequiringEntityAdapter();
        }

        @Override
        public Adapter caseResourceInterfaceRequiringEntity(final ResourceInterfaceRequiringEntity object) {
            return EntityAdapterFactory.this.createResourceInterfaceRequiringEntityAdapter();
        }

        @Override
        public Adapter caseResourceRequiredRole(final ResourceRequiredRole object) {
            return EntityAdapterFactory.this.createResourceRequiredRoleAdapter();
        }

        @Override
        public Adapter caseResourceInterfaceProvidingEntity(final ResourceInterfaceProvidingEntity object) {
            return EntityAdapterFactory.this.createResourceInterfaceProvidingEntityAdapter();
        }

        @Override
        public Adapter caseComposedProvidingRequiringEntity(final ComposedProvidingRequiringEntity object) {
            return EntityAdapterFactory.this.createComposedProvidingRequiringEntityAdapter();
        }

        @Override
        public Adapter caseNamedElement(final NamedElement object) {
            return EntityAdapterFactory.this.createNamedElementAdapter();
        }

        @Override
        public Adapter caseResourceInterfaceProvidingRequiringEntity(
                final ResourceInterfaceProvidingRequiringEntity object) {
            return EntityAdapterFactory.this.createResourceInterfaceProvidingRequiringEntityAdapter();
        }

        @Override
        public Adapter caseEntity(final Entity object) {
            return EntityAdapterFactory.this.createEntityAdapter();
        }

        @Override
        public Adapter caseIdentifier(final Identifier object) {
            return EntityAdapterFactory.this.createIdentifierAdapter();
        }

        @Override
        public Adapter caseRole(final Role object) {
            return EntityAdapterFactory.this.createRoleAdapter();
        }

        @Override
        public Adapter caseComposedStructure(final ComposedStructure object) {
            return EntityAdapterFactory.this.createComposedStructureAdapter();
        }

        @Override
        public Adapter defaultCase(final EObject object) {
            return EntityAdapterFactory.this.createEObjectAdapter();
        }
    };

    /**
     * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param target
     *            the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(final Notifier target) {
        return this.modelSwitch.doSwitch((EObject) target);
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceProvidedRole
     * <em>Resource Provided Role</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.entity.ResourceProvidedRole
     * @generated
     */
    public Adapter createResourceProvidedRoleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity
     * <em>Interface Providing Requiring Entity</em>}'. <!-- begin-user-doc --> This default
     * implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity
     * @generated
     */
    public Adapter createInterfaceProvidingRequiringEntityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity
     * <em>Interface Providing Entity</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity
     * @generated
     */
    public Adapter createInterfaceProvidingEntityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity
     * <em>Interface Requiring Entity</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity
     * @generated
     */
    public Adapter createInterfaceRequiringEntityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceInterfaceRequiringEntity
     * <em>Resource Interface Requiring Entity</em>}'. <!-- begin-user-doc --> This default
     * implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.entity.ResourceInterfaceRequiringEntity
     * @generated
     */
    public Adapter createResourceInterfaceRequiringEntityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * <em>Resource Required Role</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * @generated
     */
    public Adapter createResourceRequiredRoleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingEntity
     * <em>Resource Interface Providing Entity</em>}'. <!-- begin-user-doc --> This default
     * implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingEntity
     * @generated
     */
    public Adapter createResourceInterfaceProvidingEntityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity
     * <em>Composed Providing Requiring Entity</em>}'. <!-- begin-user-doc --> This default
     * implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity
     * @generated
     */
    public Adapter createComposedProvidingRequiringEntityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.entity.NamedElement <em>Named Element</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.entity.NamedElement
     * @generated
     */
    public Adapter createNamedElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingRequiringEntity
     * <em>Resource Interface Providing Requiring Entity</em>}'. <!-- begin-user-doc --> This
     * default implementation returns null so that we can easily ignore cases; it's useful to ignore
     * a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingRequiringEntity
     * @generated
     */
    public Adapter createResourceInterfaceProvidingRequiringEntityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.entity.Entity <em>Entity</em>}'. <!-- begin-user-doc
     * --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.entity.Entity
     * @generated
     */
    public Adapter createEntityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link de.uka.ipd.sdq.identifier.Identifier
     * <em>Identifier</em>}'. <!-- begin-user-doc --> This default implementation returns null so
     * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
     * the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see de.uka.ipd.sdq.identifier.Identifier
     * @generated
     */
    public Adapter createIdentifierAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.Role <em>Role</em>}'. <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.Role
     * @generated
     */
    public Adapter createRoleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.core.composition.ComposedStructure
     * <em>Composed Structure</em>}'. <!-- begin-user-doc --> This default implementation returns
     * null so that we can easily ignore cases; it's useful to ignore a case when inheritance will
     * catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.core.composition.ComposedStructure
     * @generated
     */
    public Adapter createComposedStructureAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case. <!-- begin-user-doc --> This default
     * implementation returns null. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} // EntityAdapterFactory
