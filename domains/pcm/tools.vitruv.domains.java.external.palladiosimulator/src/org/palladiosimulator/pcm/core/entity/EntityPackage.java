/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.entity;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.palladiosimulator.pcm.core.composition.CompositionPackage;
import org.palladiosimulator.pcm.repository.RepositoryPackage;

import de.uka.ipd.sdq.identifier.IdentifierPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta
 * objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc --> <!-- begin-model-doc --> This set of abstract meta-classes gives a
 * conceptual view on interfaces, entities and their relationships: Two roles can be identified a
 * software entity can take relative to an interface. Any entity can claim to implement the
 * functionality specified in an interface as well as to request that functionality.
 *
 * Base of the inheritance hierarchy are Identifier and NamedElement, both of which Entity and all
 * inheriting classes are derived from.
 *
 * The relationship of Entities and Interfaces is described with the three meta classes
 * InterfaceProvidingEntity, InterfaceRequiringEntity, and InterfaceProvidingRequiringEntity. The
 * abstract meta-class InterfaceProvidingEntity is inherited by all entities which can potentially
 * offer interface implementations. Similarly, the meta-class InterfaceRequiringEntity is inherited
 * by all entities which are allowed to request functionality offer by entities providing this
 * functionality. InterfaceProvidingRequiringEntity inherits from both of them and thus combines
 * their properties. <!-- end-model-doc -->
 *
 * @see org.palladiosimulator.pcm.core.entity.EntityFactory
 * @model kind="package" annotation=
 *        "http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface EntityPackage extends EPackage {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String eNAME = "entity";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String eNS_URI = "http://palladiosimulator.org/PalladioComponentModel/Core/Entity/5.1";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String eNS_PREFIX = "entity";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    EntityPackage eINSTANCE = org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl.init();

    /**
     * The meta object id for the '{@link org.palladiosimulator.pcm.core.entity.impl.EntityImpl
     * <em>Entity</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.core.entity.impl.EntityImpl
     * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getEntity()
     * @generated
     */
    int ENTITY = 10;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ENTITY__ID = IdentifierPackage.IDENTIFIER__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ENTITY__ENTITY_NAME = IdentifierPackage.IDENTIFIER_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Entity</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ENTITY_FEATURE_COUNT = IdentifierPackage.IDENTIFIER_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.entity.impl.ResourceProvidedRoleImpl
     * <em>Resource Provided Role</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.core.entity.impl.ResourceProvidedRoleImpl
     * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getResourceProvidedRole()
     * @generated
     */
    int RESOURCE_PROVIDED_ROLE = 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_PROVIDED_ROLE__ID = RepositoryPackage.ROLE__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_PROVIDED_ROLE__ENTITY_NAME = RepositoryPackage.ROLE__ENTITY_NAME;

    /**
     * The feature id for the '
     * <em><b>Resource Interface Providing Entity Resource Provided Role</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_PROVIDED_ROLE__RESOURCE_INTERFACE_PROVIDING_ENTITY_RESOURCE_PROVIDED_ROLE = RepositoryPackage.ROLE_FEATURE_COUNT
            + 0;

    /**
     * The feature id for the '<em><b>Provided Resource Interface Resource Provided Role</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_PROVIDED_ROLE__PROVIDED_RESOURCE_INTERFACE_RESOURCE_PROVIDED_ROLE = RepositoryPackage.ROLE_FEATURE_COUNT
            + 1;

    /**
     * The number of structural features of the '<em>Resource Provided Role</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_PROVIDED_ROLE_FEATURE_COUNT = RepositoryPackage.ROLE_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.entity.impl.InterfaceProvidingEntityImpl
     * <em>Interface Providing Entity</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.core.entity.impl.InterfaceProvidingEntityImpl
     * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getInterfaceProvidingEntity()
     * @generated
     */
    int INTERFACE_PROVIDING_ENTITY = 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int INTERFACE_PROVIDING_ENTITY__ID = ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int INTERFACE_PROVIDING_ENTITY__ENTITY_NAME = ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Provided Roles Interface Providing Entity</b></em>'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int INTERFACE_PROVIDING_ENTITY__PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY = ENTITY_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Interface Providing Entity</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INTERFACE_PROVIDING_ENTITY_FEATURE_COUNT = ENTITY_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.entity.impl.InterfaceProvidingRequiringEntityImpl
     * <em>Interface Providing Requiring Entity</em>}' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.core.entity.impl.InterfaceProvidingRequiringEntityImpl
     * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getInterfaceProvidingRequiringEntity()
     * @generated
     */
    int INTERFACE_PROVIDING_REQUIRING_ENTITY = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int INTERFACE_PROVIDING_REQUIRING_ENTITY__ID = INTERFACE_PROVIDING_ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int INTERFACE_PROVIDING_REQUIRING_ENTITY__ENTITY_NAME = INTERFACE_PROVIDING_ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Provided Roles Interface Providing Entity</b></em>'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int INTERFACE_PROVIDING_REQUIRING_ENTITY__PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY = INTERFACE_PROVIDING_ENTITY__PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY;

    /**
     * The feature id for the '
     * <em><b>Resource Required Roles Resource Interface Requiring Entity</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int INTERFACE_PROVIDING_REQUIRING_ENTITY__RESOURCE_REQUIRED_ROLES_RESOURCE_INTERFACE_REQUIRING_ENTITY = INTERFACE_PROVIDING_ENTITY_FEATURE_COUNT
            + 0;

    /**
     * The feature id for the '<em><b>Required Roles Interface Requiring Entity</b></em>'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int INTERFACE_PROVIDING_REQUIRING_ENTITY__REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY = INTERFACE_PROVIDING_ENTITY_FEATURE_COUNT
            + 1;

    /**
     * The number of structural features of the '<em>Interface Providing Requiring Entity</em>'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int INTERFACE_PROVIDING_REQUIRING_ENTITY_FEATURE_COUNT = INTERFACE_PROVIDING_ENTITY_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.entity.impl.InterfaceRequiringEntityImpl
     * <em>Interface Requiring Entity</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.core.entity.impl.InterfaceRequiringEntityImpl
     * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getInterfaceRequiringEntity()
     * @generated
     */
    int INTERFACE_REQUIRING_ENTITY = 3;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int INTERFACE_REQUIRING_ENTITY__ID = ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int INTERFACE_REQUIRING_ENTITY__ENTITY_NAME = ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '
     * <em><b>Resource Required Roles Resource Interface Requiring Entity</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int INTERFACE_REQUIRING_ENTITY__RESOURCE_REQUIRED_ROLES_RESOURCE_INTERFACE_REQUIRING_ENTITY = ENTITY_FEATURE_COUNT
            + 0;

    /**
     * The feature id for the '<em><b>Required Roles Interface Requiring Entity</b></em>'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int INTERFACE_REQUIRING_ENTITY__REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY = ENTITY_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Interface Requiring Entity</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INTERFACE_REQUIRING_ENTITY_FEATURE_COUNT = ENTITY_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.entity.impl.ResourceInterfaceRequiringEntityImpl
     * <em>Resource Interface Requiring Entity</em>}' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.core.entity.impl.ResourceInterfaceRequiringEntityImpl
     * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getResourceInterfaceRequiringEntity()
     * @generated
     */
    int RESOURCE_INTERFACE_REQUIRING_ENTITY = 4;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_INTERFACE_REQUIRING_ENTITY__ID = ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_INTERFACE_REQUIRING_ENTITY__ENTITY_NAME = ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '
     * <em><b>Resource Required Roles Resource Interface Requiring Entity</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_INTERFACE_REQUIRING_ENTITY__RESOURCE_REQUIRED_ROLES_RESOURCE_INTERFACE_REQUIRING_ENTITY = ENTITY_FEATURE_COUNT
            + 0;

    /**
     * The number of structural features of the '<em>Resource Interface Requiring Entity</em>'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_INTERFACE_REQUIRING_ENTITY_FEATURE_COUNT = ENTITY_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.entity.impl.ResourceRequiredRoleImpl
     * <em>Resource Required Role</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.core.entity.impl.ResourceRequiredRoleImpl
     * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getResourceRequiredRole()
     * @generated
     */
    int RESOURCE_REQUIRED_ROLE = 5;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_REQUIRED_ROLE__ID = RepositoryPackage.ROLE__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_REQUIRED_ROLE__ENTITY_NAME = RepositoryPackage.ROLE__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Required Resource Interface Resource Required Role</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_REQUIRED_ROLE__REQUIRED_RESOURCE_INTERFACE_RESOURCE_REQUIRED_ROLE = RepositoryPackage.ROLE_FEATURE_COUNT
            + 0;

    /**
     * The feature id for the '
     * <em><b>Resource Interface Requiring Entity Resource Required Role</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_REQUIRED_ROLE__RESOURCE_INTERFACE_REQUIRING_ENTITY_RESOURCE_REQUIRED_ROLE = RepositoryPackage.ROLE_FEATURE_COUNT
            + 1;

    /**
     * The number of structural features of the '<em>Resource Required Role</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_REQUIRED_ROLE_FEATURE_COUNT = RepositoryPackage.ROLE_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.entity.impl.ResourceInterfaceProvidingEntityImpl
     * <em>Resource Interface Providing Entity</em>}' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.core.entity.impl.ResourceInterfaceProvidingEntityImpl
     * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getResourceInterfaceProvidingEntity()
     * @generated
     */
    int RESOURCE_INTERFACE_PROVIDING_ENTITY = 6;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_INTERFACE_PROVIDING_ENTITY__ID = ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_INTERFACE_PROVIDING_ENTITY__ENTITY_NAME = ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '
     * <em><b>Resource Provided Roles Resource Interface Providing Entity</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_INTERFACE_PROVIDING_ENTITY__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY = ENTITY_FEATURE_COUNT
            + 0;

    /**
     * The number of structural features of the '<em>Resource Interface Providing Entity</em>'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_INTERFACE_PROVIDING_ENTITY_FEATURE_COUNT = ENTITY_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.entity.impl.ComposedProvidingRequiringEntityImpl
     * <em>Composed Providing Requiring Entity</em>}' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.core.entity.impl.ComposedProvidingRequiringEntityImpl
     * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getComposedProvidingRequiringEntity()
     * @generated
     */
    int COMPOSED_PROVIDING_REQUIRING_ENTITY = 7;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int COMPOSED_PROVIDING_REQUIRING_ENTITY__ID = CompositionPackage.COMPOSED_STRUCTURE__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int COMPOSED_PROVIDING_REQUIRING_ENTITY__ENTITY_NAME = CompositionPackage.COMPOSED_STRUCTURE__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Assembly Contexts Composed Structure</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int COMPOSED_PROVIDING_REQUIRING_ENTITY__ASSEMBLY_CONTEXTS_COMPOSED_STRUCTURE = CompositionPackage.COMPOSED_STRUCTURE__ASSEMBLY_CONTEXTS_COMPOSED_STRUCTURE;

    /**
     * The feature id for the '
     * <em><b>Resource Required Delegation Connectors Composed Structure</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int COMPOSED_PROVIDING_REQUIRING_ENTITY__RESOURCE_REQUIRED_DELEGATION_CONNECTORS_COMPOSED_STRUCTURE = CompositionPackage.COMPOSED_STRUCTURE__RESOURCE_REQUIRED_DELEGATION_CONNECTORS_COMPOSED_STRUCTURE;

    /**
     * The feature id for the '<em><b>Event Channel Composed Structure</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int COMPOSED_PROVIDING_REQUIRING_ENTITY__EVENT_CHANNEL_COMPOSED_STRUCTURE = CompositionPackage.COMPOSED_STRUCTURE__EVENT_CHANNEL_COMPOSED_STRUCTURE;

    /**
     * The feature id for the '<em><b>Connectors Composed Structure</b></em>' containment reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int COMPOSED_PROVIDING_REQUIRING_ENTITY__CONNECTORS_COMPOSED_STRUCTURE = CompositionPackage.COMPOSED_STRUCTURE__CONNECTORS_COMPOSED_STRUCTURE;

    /**
     * The feature id for the '<em><b>Provided Roles Interface Providing Entity</b></em>'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int COMPOSED_PROVIDING_REQUIRING_ENTITY__PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY = CompositionPackage.COMPOSED_STRUCTURE_FEATURE_COUNT
            + 0;

    /**
     * The feature id for the '
     * <em><b>Resource Required Roles Resource Interface Requiring Entity</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int COMPOSED_PROVIDING_REQUIRING_ENTITY__RESOURCE_REQUIRED_ROLES_RESOURCE_INTERFACE_REQUIRING_ENTITY = CompositionPackage.COMPOSED_STRUCTURE_FEATURE_COUNT
            + 1;

    /**
     * The feature id for the '<em><b>Required Roles Interface Requiring Entity</b></em>'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int COMPOSED_PROVIDING_REQUIRING_ENTITY__REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY = CompositionPackage.COMPOSED_STRUCTURE_FEATURE_COUNT
            + 2;

    /**
     * The number of structural features of the '<em>Composed Providing Requiring Entity</em>'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int COMPOSED_PROVIDING_REQUIRING_ENTITY_FEATURE_COUNT = CompositionPackage.COMPOSED_STRUCTURE_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.entity.impl.NamedElementImpl <em>Named Element</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.core.entity.impl.NamedElementImpl
     * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getNamedElement()
     * @generated
     */
    int NAMED_ELEMENT = 8;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT__ENTITY_NAME = 0;

    /**
     * The number of structural features of the '<em>Named Element</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.entity.impl.ResourceInterfaceProvidingRequiringEntityImpl
     * <em>Resource Interface Providing Requiring Entity</em>}' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.core.entity.impl.ResourceInterfaceProvidingRequiringEntityImpl
     * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getResourceInterfaceProvidingRequiringEntity()
     * @generated
     */
    int RESOURCE_INTERFACE_PROVIDING_REQUIRING_ENTITY = 9;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_INTERFACE_PROVIDING_REQUIRING_ENTITY__ID = RESOURCE_INTERFACE_REQUIRING_ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_INTERFACE_PROVIDING_REQUIRING_ENTITY__ENTITY_NAME = RESOURCE_INTERFACE_REQUIRING_ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '
     * <em><b>Resource Required Roles Resource Interface Requiring Entity</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_INTERFACE_PROVIDING_REQUIRING_ENTITY__RESOURCE_REQUIRED_ROLES_RESOURCE_INTERFACE_REQUIRING_ENTITY = RESOURCE_INTERFACE_REQUIRING_ENTITY__RESOURCE_REQUIRED_ROLES_RESOURCE_INTERFACE_REQUIRING_ENTITY;

    /**
     * The feature id for the '
     * <em><b>Resource Provided Roles Resource Interface Providing Entity</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_INTERFACE_PROVIDING_REQUIRING_ENTITY__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY = RESOURCE_INTERFACE_REQUIRING_ENTITY_FEATURE_COUNT
            + 0;

    /**
     * The number of structural features of the '
     * <em>Resource Interface Providing Requiring Entity</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int RESOURCE_INTERFACE_PROVIDING_REQUIRING_ENTITY_FEATURE_COUNT = RESOURCE_INTERFACE_REQUIRING_ENTITY_FEATURE_COUNT
            + 1;

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceProvidedRole
     * <em>Resource Provided Role</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Resource Provided Role</em>'.
     * @see org.palladiosimulator.pcm.core.entity.ResourceProvidedRole
     * @generated
     */
    EClass getResourceProvidedRole();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceProvidedRole#getResourceInterfaceProvidingEntity__ResourceProvidedRole
     * <em>Resource Interface Providing Entity Resource Provided Role</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return the meta object for the container reference '
     *         <em>Resource Interface Providing Entity Resource Provided Role</em>'.
     * @see org.palladiosimulator.pcm.core.entity.ResourceProvidedRole#getResourceInterfaceProvidingEntity__ResourceProvidedRole()
     * @see #getResourceProvidedRole()
     * @generated
     */
    EReference getResourceProvidedRole_ResourceInterfaceProvidingEntity__ResourceProvidedRole();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceProvidedRole#getProvidedResourceInterface__ResourceProvidedRole
     * <em>Provided Resource Interface Resource Provided Role</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the meta object for the reference '
     *         <em>Provided Resource Interface Resource Provided Role</em>'.
     * @see org.palladiosimulator.pcm.core.entity.ResourceProvidedRole#getProvidedResourceInterface__ResourceProvidedRole()
     * @see #getResourceProvidedRole()
     * @generated
     */
    EReference getResourceProvidedRole_ProvidedResourceInterface__ResourceProvidedRole();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity
     * <em>Interface Providing Requiring Entity</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for class '<em>Interface Providing Requiring Entity</em>'.
     * @see org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity
     * @generated
     */
    EClass getInterfaceProvidingRequiringEntity();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity
     * <em>Interface Providing Entity</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Interface Providing Entity</em>'.
     * @see org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity
     * @generated
     */
    EClass getInterfaceProvidingEntity();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity#getProvidedRoles_InterfaceProvidingEntity
     * <em>Provided Roles Interface Providing Entity</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the meta object for the containment reference list '
     *         <em>Provided Roles Interface Providing Entity</em>'.
     * @see org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity#getProvidedRoles_InterfaceProvidingEntity()
     * @see #getInterfaceProvidingEntity()
     * @generated
     */
    EReference getInterfaceProvidingEntity_ProvidedRoles_InterfaceProvidingEntity();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity
     * <em>Interface Requiring Entity</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Interface Requiring Entity</em>'.
     * @see org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity
     * @generated
     */
    EClass getInterfaceRequiringEntity();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity#getRequiredRoles_InterfaceRequiringEntity
     * <em>Required Roles Interface Requiring Entity</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the meta object for the containment reference list '
     *         <em>Required Roles Interface Requiring Entity</em>'.
     * @see org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity#getRequiredRoles_InterfaceRequiringEntity()
     * @see #getInterfaceRequiringEntity()
     * @generated
     */
    EReference getInterfaceRequiringEntity_RequiredRoles_InterfaceRequiringEntity();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceInterfaceRequiringEntity
     * <em>Resource Interface Requiring Entity</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Resource Interface Requiring Entity</em>'.
     * @see org.palladiosimulator.pcm.core.entity.ResourceInterfaceRequiringEntity
     * @generated
     */
    EClass getResourceInterfaceRequiringEntity();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceInterfaceRequiringEntity#getResourceRequiredRoles__ResourceInterfaceRequiringEntity
     * <em>Resource Required Roles Resource Interface Requiring Entity</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return the meta object for the containment reference list '
     *         <em>Resource Required Roles Resource Interface Requiring Entity</em>'.
     * @see org.palladiosimulator.pcm.core.entity.ResourceInterfaceRequiringEntity#getResourceRequiredRoles__ResourceInterfaceRequiringEntity()
     * @see #getResourceInterfaceRequiringEntity()
     * @generated
     */
    EReference getResourceInterfaceRequiringEntity_ResourceRequiredRoles__ResourceInterfaceRequiringEntity();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * <em>Resource Required Role</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Resource Required Role</em>'.
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * @generated
     */
    EClass getResourceRequiredRole();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole#getRequiredResourceInterface__ResourceRequiredRole
     * <em>Required Resource Interface Resource Required Role</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the meta object for the reference '
     *         <em>Required Resource Interface Resource Required Role</em>'.
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole#getRequiredResourceInterface__ResourceRequiredRole()
     * @see #getResourceRequiredRole()
     * @generated
     */
    EReference getResourceRequiredRole_RequiredResourceInterface__ResourceRequiredRole();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole#getResourceInterfaceRequiringEntity__ResourceRequiredRole
     * <em>Resource Interface Requiring Entity Resource Required Role</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return the meta object for the container reference '
     *         <em>Resource Interface Requiring Entity Resource Required Role</em>'.
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole#getResourceInterfaceRequiringEntity__ResourceRequiredRole()
     * @see #getResourceRequiredRole()
     * @generated
     */
    EReference getResourceRequiredRole_ResourceInterfaceRequiringEntity__ResourceRequiredRole();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingEntity
     * <em>Resource Interface Providing Entity</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Resource Interface Providing Entity</em>'.
     * @see org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingEntity
     * @generated
     */
    EClass getResourceInterfaceProvidingEntity();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingEntity#getResourceProvidedRoles__ResourceInterfaceProvidingEntity
     * <em>Resource Provided Roles Resource Interface Providing Entity</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return the meta object for the containment reference list '
     *         <em>Resource Provided Roles Resource Interface Providing Entity</em>'.
     * @see org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingEntity#getResourceProvidedRoles__ResourceInterfaceProvidingEntity()
     * @see #getResourceInterfaceProvidingEntity()
     * @generated
     */
    EReference getResourceInterfaceProvidingEntity_ResourceProvidedRoles__ResourceInterfaceProvidingEntity();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity
     * <em>Composed Providing Requiring Entity</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Composed Providing Requiring Entity</em>'.
     * @see org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity
     * @generated
     */
    EClass getComposedProvidingRequiringEntity();

    /**
     * Returns the meta object for class '{@link org.palladiosimulator.pcm.core.entity.NamedElement
     * <em>Named Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Named Element</em>'.
     * @see org.palladiosimulator.pcm.core.entity.NamedElement
     * @generated
     */
    EClass getNamedElement();

    /**
     * Returns the meta object for the attribute '
     * {@link org.palladiosimulator.pcm.core.entity.NamedElement#getEntityName <em>Entity Name</em>}
     * '. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the attribute '<em>Entity Name</em>'.
     * @see org.palladiosimulator.pcm.core.entity.NamedElement#getEntityName()
     * @see #getNamedElement()
     * @generated
     */
    EAttribute getNamedElement_EntityName();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingRequiringEntity
     * <em>Resource Interface Providing Requiring Entity</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the meta object for class '<em>Resource Interface Providing Requiring Entity</em>'.
     * @see org.palladiosimulator.pcm.core.entity.ResourceInterfaceProvidingRequiringEntity
     * @generated
     */
    EClass getResourceInterfaceProvidingRequiringEntity();

    /**
     * Returns the meta object for class '{@link org.palladiosimulator.pcm.core.entity.Entity
     * <em>Entity</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Entity</em>'.
     * @see org.palladiosimulator.pcm.core.entity.Entity
     * @generated
     */
    EClass getEntity();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the factory that creates the instances of the model.
     * @generated
     */
    EntityFactory getEntityFactory();

    /**
     * <!-- begin-user-doc --> Defines literals for the meta objects that represent
     * <ul>
     * <li>each class,</li>
     * <li>each feature of each class,</li>
     * <li>each enum,</li>
     * <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    interface Literals {

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.entity.impl.ResourceProvidedRoleImpl
         * <em>Resource Provided Role</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.core.entity.impl.ResourceProvidedRoleImpl
         * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getResourceProvidedRole()
         * @generated
         */
        EClass RESOURCE_PROVIDED_ROLE = eINSTANCE.getResourceProvidedRole();

        /**
         * The meta object literal for the '
         * <em><b>Resource Interface Providing Entity Resource Provided Role</b></em>' container
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference RESOURCE_PROVIDED_ROLE__RESOURCE_INTERFACE_PROVIDING_ENTITY_RESOURCE_PROVIDED_ROLE = eINSTANCE
                .getResourceProvidedRole_ResourceInterfaceProvidingEntity__ResourceProvidedRole();

        /**
         * The meta object literal for the '
         * <em><b>Provided Resource Interface Resource Provided Role</b></em>' reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference RESOURCE_PROVIDED_ROLE__PROVIDED_RESOURCE_INTERFACE_RESOURCE_PROVIDED_ROLE = eINSTANCE
                .getResourceProvidedRole_ProvidedResourceInterface__ResourceProvidedRole();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.entity.impl.InterfaceProvidingRequiringEntityImpl
         * <em>Interface Providing Requiring Entity</em>}' class. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.core.entity.impl.InterfaceProvidingRequiringEntityImpl
         * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getInterfaceProvidingRequiringEntity()
         * @generated
         */
        EClass INTERFACE_PROVIDING_REQUIRING_ENTITY = eINSTANCE.getInterfaceProvidingRequiringEntity();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.entity.impl.InterfaceProvidingEntityImpl
         * <em>Interface Providing Entity</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         *
         * @see org.palladiosimulator.pcm.core.entity.impl.InterfaceProvidingEntityImpl
         * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getInterfaceProvidingEntity()
         * @generated
         */
        EClass INTERFACE_PROVIDING_ENTITY = eINSTANCE.getInterfaceProvidingEntity();

        /**
         * The meta object literal for the '
         * <em><b>Provided Roles Interface Providing Entity</b></em>' containment reference list
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference INTERFACE_PROVIDING_ENTITY__PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY = eINSTANCE
                .getInterfaceProvidingEntity_ProvidedRoles_InterfaceProvidingEntity();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.entity.impl.InterfaceRequiringEntityImpl
         * <em>Interface Requiring Entity</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         *
         * @see org.palladiosimulator.pcm.core.entity.impl.InterfaceRequiringEntityImpl
         * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getInterfaceRequiringEntity()
         * @generated
         */
        EClass INTERFACE_REQUIRING_ENTITY = eINSTANCE.getInterfaceRequiringEntity();

        /**
         * The meta object literal for the '
         * <em><b>Required Roles Interface Requiring Entity</b></em>' containment reference list
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference INTERFACE_REQUIRING_ENTITY__REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY = eINSTANCE
                .getInterfaceRequiringEntity_RequiredRoles_InterfaceRequiringEntity();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.entity.impl.ResourceInterfaceRequiringEntityImpl
         * <em>Resource Interface Requiring Entity</em>}' class. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.core.entity.impl.ResourceInterfaceRequiringEntityImpl
         * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getResourceInterfaceRequiringEntity()
         * @generated
         */
        EClass RESOURCE_INTERFACE_REQUIRING_ENTITY = eINSTANCE.getResourceInterfaceRequiringEntity();

        /**
         * The meta object literal for the '
         * <em><b>Resource Required Roles Resource Interface Requiring Entity</b></em>' containment
         * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference RESOURCE_INTERFACE_REQUIRING_ENTITY__RESOURCE_REQUIRED_ROLES_RESOURCE_INTERFACE_REQUIRING_ENTITY = eINSTANCE
                .getResourceInterfaceRequiringEntity_ResourceRequiredRoles__ResourceInterfaceRequiringEntity();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.entity.impl.ResourceRequiredRoleImpl
         * <em>Resource Required Role</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.core.entity.impl.ResourceRequiredRoleImpl
         * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getResourceRequiredRole()
         * @generated
         */
        EClass RESOURCE_REQUIRED_ROLE = eINSTANCE.getResourceRequiredRole();

        /**
         * The meta object literal for the '
         * <em><b>Required Resource Interface Resource Required Role</b></em>' reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference RESOURCE_REQUIRED_ROLE__REQUIRED_RESOURCE_INTERFACE_RESOURCE_REQUIRED_ROLE = eINSTANCE
                .getResourceRequiredRole_RequiredResourceInterface__ResourceRequiredRole();

        /**
         * The meta object literal for the '
         * <em><b>Resource Interface Requiring Entity Resource Required Role</b></em>' container
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference RESOURCE_REQUIRED_ROLE__RESOURCE_INTERFACE_REQUIRING_ENTITY_RESOURCE_REQUIRED_ROLE = eINSTANCE
                .getResourceRequiredRole_ResourceInterfaceRequiringEntity__ResourceRequiredRole();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.entity.impl.ResourceInterfaceProvidingEntityImpl
         * <em>Resource Interface Providing Entity</em>}' class. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.core.entity.impl.ResourceInterfaceProvidingEntityImpl
         * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getResourceInterfaceProvidingEntity()
         * @generated
         */
        EClass RESOURCE_INTERFACE_PROVIDING_ENTITY = eINSTANCE.getResourceInterfaceProvidingEntity();

        /**
         * The meta object literal for the '
         * <em><b>Resource Provided Roles Resource Interface Providing Entity</b></em>' containment
         * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference RESOURCE_INTERFACE_PROVIDING_ENTITY__RESOURCE_PROVIDED_ROLES_RESOURCE_INTERFACE_PROVIDING_ENTITY = eINSTANCE
                .getResourceInterfaceProvidingEntity_ResourceProvidedRoles__ResourceInterfaceProvidingEntity();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.entity.impl.ComposedProvidingRequiringEntityImpl
         * <em>Composed Providing Requiring Entity</em>}' class. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.core.entity.impl.ComposedProvidingRequiringEntityImpl
         * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getComposedProvidingRequiringEntity()
         * @generated
         */
        EClass COMPOSED_PROVIDING_REQUIRING_ENTITY = eINSTANCE.getComposedProvidingRequiringEntity();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.entity.impl.NamedElementImpl <em>Named Element</em>
         * }' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.core.entity.impl.NamedElementImpl
         * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getNamedElement()
         * @generated
         */
        EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

        /**
         * The meta object literal for the '<em><b>Entity Name</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute NAMED_ELEMENT__ENTITY_NAME = eINSTANCE.getNamedElement_EntityName();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.entity.impl.ResourceInterfaceProvidingRequiringEntityImpl
         * <em>Resource Interface Providing Requiring Entity</em>}' class. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.core.entity.impl.
         *      ResourceInterfaceProvidingRequiringEntityImpl
         * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getResourceInterfaceProvidingRequiringEntity()
         * @generated
         */
        EClass RESOURCE_INTERFACE_PROVIDING_REQUIRING_ENTITY = eINSTANCE.getResourceInterfaceProvidingRequiringEntity();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.entity.impl.EntityImpl <em>Entity</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.core.entity.impl.EntityImpl
         * @see org.palladiosimulator.pcm.core.entity.impl.EntityPackageImpl#getEntity()
         * @generated
         */
        EClass ENTITY = eINSTANCE.getEntity();

    }

} // EntityPackage
