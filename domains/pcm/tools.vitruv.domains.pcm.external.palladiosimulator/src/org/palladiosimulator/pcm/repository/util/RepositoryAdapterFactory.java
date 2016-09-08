/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.core.entity.ResourceInterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.ExceptionType;
import org.palladiosimulator.pcm.repository.ImplementationComponentType;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidesComponentType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.repository.RequiredCharacterisation;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.repository.Role;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;

import de.uka.ipd.sdq.identifier.Identifier;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter
 * <code>createXXX</code> method for each class of the model. <!-- end-user-doc -->
 * 
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage
 * @generated
 */
public class RepositoryAdapterFactory extends AdapterFactoryImpl {

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
    protected static RepositoryPackage modelPackage;

    /**
     * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public RepositoryAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = RepositoryPackage.eINSTANCE;
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
    protected RepositorySwitch<Adapter> modelSwitch = new RepositorySwitch<Adapter>() {
        @Override
        public Adapter casePassiveResource(final PassiveResource object) {
            return RepositoryAdapterFactory.this.createPassiveResourceAdapter();
        }

        @Override
        public Adapter caseBasicComponent(final BasicComponent object) {
            return RepositoryAdapterFactory.this.createBasicComponentAdapter();
        }

        @Override
        public Adapter caseImplementationComponentType(final ImplementationComponentType object) {
            return RepositoryAdapterFactory.this.createImplementationComponentTypeAdapter();
        }

        @Override
        public Adapter caseRepositoryComponent(final RepositoryComponent object) {
            return RepositoryAdapterFactory.this.createRepositoryComponentAdapter();
        }

        @Override
        public Adapter caseProvidedRole(final ProvidedRole object) {
            return RepositoryAdapterFactory.this.createProvidedRoleAdapter();
        }

        @Override
        public Adapter caseParameter(final Parameter object) {
            return RepositoryAdapterFactory.this.createParameterAdapter();
        }

        @Override
        public Adapter caseDataType(final DataType object) {
            return RepositoryAdapterFactory.this.createDataTypeAdapter();
        }

        @Override
        public Adapter caseRepository(final Repository object) {
            return RepositoryAdapterFactory.this.createRepositoryAdapter();
        }

        @Override
        public Adapter caseInterface(final Interface object) {
            return RepositoryAdapterFactory.this.createInterfaceAdapter();
        }

        @Override
        public Adapter caseRequiredCharacterisation(final RequiredCharacterisation object) {
            return RepositoryAdapterFactory.this.createRequiredCharacterisationAdapter();
        }

        @Override
        public Adapter caseEventGroup(final EventGroup object) {
            return RepositoryAdapterFactory.this.createEventGroupAdapter();
        }

        @Override
        public Adapter caseEventType(final EventType object) {
            return RepositoryAdapterFactory.this.createEventTypeAdapter();
        }

        @Override
        public Adapter caseSignature(final Signature object) {
            return RepositoryAdapterFactory.this.createSignatureAdapter();
        }

        @Override
        public Adapter caseExceptionType(final ExceptionType object) {
            return RepositoryAdapterFactory.this.createExceptionTypeAdapter();
        }

        @Override
        public Adapter caseInfrastructureSignature(final InfrastructureSignature object) {
            return RepositoryAdapterFactory.this.createInfrastructureSignatureAdapter();
        }

        @Override
        public Adapter caseInfrastructureInterface(final InfrastructureInterface object) {
            return RepositoryAdapterFactory.this.createInfrastructureInterfaceAdapter();
        }

        @Override
        public Adapter caseInfrastructureRequiredRole(final InfrastructureRequiredRole object) {
            return RepositoryAdapterFactory.this.createInfrastructureRequiredRoleAdapter();
        }

        @Override
        public Adapter caseRequiredRole(final RequiredRole object) {
            return RepositoryAdapterFactory.this.createRequiredRoleAdapter();
        }

        @Override
        public Adapter caseOperationSignature(final OperationSignature object) {
            return RepositoryAdapterFactory.this.createOperationSignatureAdapter();
        }

        @Override
        public Adapter caseOperationInterface(final OperationInterface object) {
            return RepositoryAdapterFactory.this.createOperationInterfaceAdapter();
        }

        @Override
        public Adapter caseOperationRequiredRole(final OperationRequiredRole object) {
            return RepositoryAdapterFactory.this.createOperationRequiredRoleAdapter();
        }

        @Override
        public Adapter caseSourceRole(final SourceRole object) {
            return RepositoryAdapterFactory.this.createSourceRoleAdapter();
        }

        @Override
        public Adapter caseSinkRole(final SinkRole object) {
            return RepositoryAdapterFactory.this.createSinkRoleAdapter();
        }

        @Override
        public Adapter caseOperationProvidedRole(final OperationProvidedRole object) {
            return RepositoryAdapterFactory.this.createOperationProvidedRoleAdapter();
        }

        @Override
        public Adapter caseInfrastructureProvidedRole(final InfrastructureProvidedRole object) {
            return RepositoryAdapterFactory.this.createInfrastructureProvidedRoleAdapter();
        }

        @Override
        public Adapter caseCompleteComponentType(final CompleteComponentType object) {
            return RepositoryAdapterFactory.this.createCompleteComponentTypeAdapter();
        }

        @Override
        public Adapter caseProvidesComponentType(final ProvidesComponentType object) {
            return RepositoryAdapterFactory.this.createProvidesComponentTypeAdapter();
        }

        @Override
        public Adapter caseCompositeComponent(final CompositeComponent object) {
            return RepositoryAdapterFactory.this.createCompositeComponentAdapter();
        }

        @Override
        public Adapter casePrimitiveDataType(final PrimitiveDataType object) {
            return RepositoryAdapterFactory.this.createPrimitiveDataTypeAdapter();
        }

        @Override
        public Adapter caseCollectionDataType(final CollectionDataType object) {
            return RepositoryAdapterFactory.this.createCollectionDataTypeAdapter();
        }

        @Override
        public Adapter caseCompositeDataType(final CompositeDataType object) {
            return RepositoryAdapterFactory.this.createCompositeDataTypeAdapter();
        }

        @Override
        public Adapter caseInnerDeclaration(final InnerDeclaration object) {
            return RepositoryAdapterFactory.this.createInnerDeclarationAdapter();
        }

        @Override
        public Adapter caseRole(final Role object) {
            return RepositoryAdapterFactory.this.createRoleAdapter();
        }

        @Override
        public Adapter caseIdentifier(final Identifier object) {
            return RepositoryAdapterFactory.this.createIdentifierAdapter();
        }

        @Override
        public Adapter caseNamedElement(final NamedElement object) {
            return RepositoryAdapterFactory.this.createNamedElementAdapter();
        }

        @Override
        public Adapter caseEntity(final Entity object) {
            return RepositoryAdapterFactory.this.createEntityAdapter();
        }

        @Override
        public Adapter caseInterfaceProvidingEntity(final InterfaceProvidingEntity object) {
            return RepositoryAdapterFactory.this.createInterfaceProvidingEntityAdapter();
        }

        @Override
        public Adapter caseResourceInterfaceRequiringEntity(final ResourceInterfaceRequiringEntity object) {
            return RepositoryAdapterFactory.this.createResourceInterfaceRequiringEntityAdapter();
        }

        @Override
        public Adapter caseInterfaceRequiringEntity(final InterfaceRequiringEntity object) {
            return RepositoryAdapterFactory.this.createInterfaceRequiringEntityAdapter();
        }

        @Override
        public Adapter caseInterfaceProvidingRequiringEntity(final InterfaceProvidingRequiringEntity object) {
            return RepositoryAdapterFactory.this.createInterfaceProvidingRequiringEntityAdapter();
        }

        @Override
        public Adapter caseComposedStructure(final ComposedStructure object) {
            return RepositoryAdapterFactory.this.createComposedStructureAdapter();
        }

        @Override
        public Adapter caseComposedProvidingRequiringEntity(final ComposedProvidingRequiringEntity object) {
            return RepositoryAdapterFactory.this.createComposedProvidingRequiringEntityAdapter();
        }

        @Override
        public Adapter defaultCase(final EObject object) {
            return RepositoryAdapterFactory.this.createEObjectAdapter();
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
     * {@link org.palladiosimulator.pcm.repository.PassiveResource <em>Passive Resource</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.PassiveResource
     * @generated
     */
    public Adapter createPassiveResourceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.BasicComponent <em>Basic Component</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.BasicComponent
     * @generated
     */
    public Adapter createBasicComponentAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.ImplementationComponentType
     * <em>Implementation Component Type</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.ImplementationComponentType
     * @generated
     */
    public Adapter createImplementationComponentTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.RepositoryComponent <em>Component</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.RepositoryComponent
     * @generated
     */
    public Adapter createRepositoryComponentAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.ProvidedRole <em>Provided Role</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.ProvidedRole
     * @generated
     */
    public Adapter createProvidedRoleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.Parameter <em>Parameter</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.Parameter
     * @generated
     */
    public Adapter createParameterAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.DataType <em>Data Type</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.DataType
     * @generated
     */
    public Adapter createDataTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.Repository <em>Repository</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.Repository
     * @generated
     */
    public Adapter createRepositoryAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.Interface <em>Interface</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.Interface
     * @generated
     */
    public Adapter createInterfaceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.RequiredCharacterisation
     * <em>Required Characterisation</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.RequiredCharacterisation
     * @generated
     */
    public Adapter createRequiredCharacterisationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.EventGroup <em>Event Group</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.EventGroup
     * @generated
     */
    public Adapter createEventGroupAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.EventType <em>Event Type</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.EventType
     * @generated
     */
    public Adapter createEventTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.Signature <em>Signature</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.Signature
     * @generated
     */
    public Adapter createSignatureAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.ExceptionType <em>Exception Type</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.ExceptionType
     * @generated
     */
    public Adapter createExceptionTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.InfrastructureSignature
     * <em>Infrastructure Signature</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.InfrastructureSignature
     * @generated
     */
    public Adapter createInfrastructureSignatureAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
     * <em>Infrastructure Interface</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.InfrastructureInterface
     * @generated
     */
    public Adapter createInfrastructureInterfaceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     * <em>Infrastructure Required Role</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     * @generated
     */
    public Adapter createInfrastructureRequiredRoleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.RequiredRole <em>Required Role</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.RequiredRole
     * @generated
     */
    public Adapter createRequiredRoleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.OperationSignature <em>Operation Signature</em>}
     * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.OperationSignature
     * @generated
     */
    public Adapter createOperationSignatureAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.OperationInterface <em>Operation Interface</em>}
     * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.OperationInterface
     * @generated
     */
    public Adapter createOperationInterfaceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
     * <em>Operation Required Role</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
     * @generated
     */
    public Adapter createOperationRequiredRoleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.SourceRole <em>Source Role</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.SourceRole
     * @generated
     */
    public Adapter createSourceRoleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.SinkRole <em>Sink Role</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.SinkRole
     * @generated
     */
    public Adapter createSinkRoleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
     * <em>Operation Provided Role</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     * @generated
     */
    public Adapter createOperationProvidedRoleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     * <em>Infrastructure Provided Role</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     * @generated
     */
    public Adapter createInfrastructureProvidedRoleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.CompleteComponentType
     * <em>Complete Component Type</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.CompleteComponentType
     * @generated
     */
    public Adapter createCompleteComponentTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.ProvidesComponentType
     * <em>Provides Component Type</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.ProvidesComponentType
     * @generated
     */
    public Adapter createProvidesComponentTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.CompositeComponent <em>Composite Component</em>}
     * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.CompositeComponent
     * @generated
     */
    public Adapter createCompositeComponentAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.PrimitiveDataType <em>Primitive Data Type</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.PrimitiveDataType
     * @generated
     */
    public Adapter createPrimitiveDataTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.CollectionDataType <em>Collection Data Type</em>}
     * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.CollectionDataType
     * @generated
     */
    public Adapter createCollectionDataTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.CompositeDataType <em>Composite Data Type</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.CompositeDataType
     * @generated
     */
    public Adapter createCompositeDataTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.repository.InnerDeclaration <em>Inner Declaration</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.repository.InnerDeclaration
     * @generated
     */
    public Adapter createInnerDeclarationAdapter() {
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
     * Creates a new adapter for the default case. <!-- begin-user-doc --> This default
     * implementation returns null. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} // RepositoryAdapterFactory
