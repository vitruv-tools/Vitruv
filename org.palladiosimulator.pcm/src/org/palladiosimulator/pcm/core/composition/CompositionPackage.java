/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.palladiosimulator.pcm.core.entity.EntityPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta
 * objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc --> <!-- begin-model-doc --> A package holding all composable entities <!--
 * end-model-doc -->
 *
 * @see org.palladiosimulator.pcm.core.composition.CompositionFactory
 * @model kind="package" annotation=
 *        "http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface CompositionPackage extends EPackage {

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
    String eNAME = "composition";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://palladiosimulator.org/PalladioComponentModel/Core/Composition/5.1";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "composition";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    CompositionPackage eINSTANCE = org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl.init();

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.composition.impl.ConnectorImpl <em>Connector</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.core.composition.impl.ConnectorImpl
     * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getConnector()
     * @generated
     */
    int CONNECTOR = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONNECTOR__ID = EntityPackage.ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONNECTOR__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Parent Structure Connector</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONNECTOR__PARENT_STRUCTURE_CONNECTOR = EntityPackage.ENTITY_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Connector</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONNECTOR_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.composition.impl.DelegationConnectorImpl
     * <em>Delegation Connector</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.core.composition.impl.DelegationConnectorImpl
     * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getDelegationConnector()
     * @generated
     */
    int DELEGATION_CONNECTOR = 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELEGATION_CONNECTOR__ID = CONNECTOR__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELEGATION_CONNECTOR__ENTITY_NAME = CONNECTOR__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Parent Structure Connector</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELEGATION_CONNECTOR__PARENT_STRUCTURE_CONNECTOR = CONNECTOR__PARENT_STRUCTURE_CONNECTOR;

    /**
     * The number of structural features of the '<em>Delegation Connector</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELEGATION_CONNECTOR_FEATURE_COUNT = CONNECTOR_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.composition.impl.ComposedStructureImpl
     * <em>Composed Structure</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.core.composition.impl.ComposedStructureImpl
     * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getComposedStructure()
     * @generated
     */
    int COMPOSED_STRUCTURE = 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMPOSED_STRUCTURE__ID = EntityPackage.ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMPOSED_STRUCTURE__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Assembly Contexts Composed Structure</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMPOSED_STRUCTURE__ASSEMBLY_CONTEXTS_COMPOSED_STRUCTURE = EntityPackage.ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '
     * <em><b>Resource Required Delegation Connectors Composed Structure</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMPOSED_STRUCTURE__RESOURCE_REQUIRED_DELEGATION_CONNECTORS_COMPOSED_STRUCTURE = EntityPackage.ENTITY_FEATURE_COUNT
            + 1;

    /**
     * The feature id for the '<em><b>Event Channel Composed Structure</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMPOSED_STRUCTURE__EVENT_CHANNEL_COMPOSED_STRUCTURE = EntityPackage.ENTITY_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Connectors Composed Structure</b></em>' containment reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMPOSED_STRUCTURE__CONNECTORS_COMPOSED_STRUCTURE = EntityPackage.ENTITY_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Composed Structure</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMPOSED_STRUCTURE_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 4;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.composition.impl.ResourceRequiredDelegationConnectorImpl
     * <em>Resource Required Delegation Connector</em>}' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.core.composition.impl.ResourceRequiredDelegationConnectorImpl
     * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getResourceRequiredDelegationConnector()
     * @generated
     */
    int RESOURCE_REQUIRED_DELEGATION_CONNECTOR = 3;

    /**
     * The feature id for the '
     * <em><b>Inner Resource Required Role Resource Required Delegation Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_REQUIRED_DELEGATION_CONNECTOR__INNER_RESOURCE_REQUIRED_ROLE_RESOURCE_REQUIRED_DELEGATION_CONNECTOR = 0;

    /**
     * The feature id for the '
     * <em><b>Outer Resource Required Role Resource Required Delegation Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_REQUIRED_DELEGATION_CONNECTOR__OUTER_RESOURCE_REQUIRED_ROLE_RESOURCE_REQUIRED_DELEGATION_CONNECTOR = 1;

    /**
     * The feature id for the '
     * <em><b>Parent Structure Resource Required Delegation Connector</b></em>' container reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_REQUIRED_DELEGATION_CONNECTOR__PARENT_STRUCTURE_RESOURCE_REQUIRED_DELEGATION_CONNECTOR = 2;

    /**
     * The number of structural features of the '<em>Resource Required Delegation Connector</em>'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_REQUIRED_DELEGATION_CONNECTOR_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.composition.impl.EventChannelImpl
     * <em>Event Channel</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.core.composition.impl.EventChannelImpl
     * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getEventChannel()
     * @generated
     */
    int EVENT_CHANNEL = 4;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL__ID = EntityPackage.ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Event Group Event Channel</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL__EVENT_GROUP_EVENT_CHANNEL = EntityPackage.ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Event Channel Source Connector Event Channel</b></em>'
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL__EVENT_CHANNEL_SOURCE_CONNECTOR_EVENT_CHANNEL = EntityPackage.ENTITY_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Event Channel Sink Connector Event Channel</b></em>' reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL__EVENT_CHANNEL_SINK_CONNECTOR_EVENT_CHANNEL = EntityPackage.ENTITY_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Parent Structure Event Channel</b></em>' container reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL__PARENT_STRUCTURE_EVENT_CHANNEL = EntityPackage.ENTITY_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Event Channel</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 4;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.composition.impl.EventChannelSourceConnectorImpl
     * <em>Event Channel Source Connector</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see org.palladiosimulator.pcm.core.composition.impl.EventChannelSourceConnectorImpl
     * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getEventChannelSourceConnector()
     * @generated
     */
    int EVENT_CHANNEL_SOURCE_CONNECTOR = 5;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL_SOURCE_CONNECTOR__ID = CONNECTOR__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL_SOURCE_CONNECTOR__ENTITY_NAME = CONNECTOR__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Parent Structure Connector</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL_SOURCE_CONNECTOR__PARENT_STRUCTURE_CONNECTOR = CONNECTOR__PARENT_STRUCTURE_CONNECTOR;

    /**
     * The feature id for the '<em><b>Source Role Event Channel Source Role</b></em>' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL_SOURCE_CONNECTOR__SOURCE_ROLE_EVENT_CHANNEL_SOURCE_ROLE = CONNECTOR_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Assembly Context Event Channel Source Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL_SOURCE_CONNECTOR__ASSEMBLY_CONTEXT_EVENT_CHANNEL_SOURCE_CONNECTOR = CONNECTOR_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Event Channel Event Channel Source Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL_SOURCE_CONNECTOR__EVENT_CHANNEL_EVENT_CHANNEL_SOURCE_CONNECTOR = CONNECTOR_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Event Channel Source Connector</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL_SOURCE_CONNECTOR_FEATURE_COUNT = CONNECTOR_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.composition.impl.EventChannelSinkConnectorImpl
     * <em>Event Channel Sink Connector</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.core.composition.impl.EventChannelSinkConnectorImpl
     * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getEventChannelSinkConnector()
     * @generated
     */
    int EVENT_CHANNEL_SINK_CONNECTOR = 6;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL_SINK_CONNECTOR__ID = CONNECTOR__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL_SINK_CONNECTOR__ENTITY_NAME = CONNECTOR__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Parent Structure Connector</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL_SINK_CONNECTOR__PARENT_STRUCTURE_CONNECTOR = CONNECTOR__PARENT_STRUCTURE_CONNECTOR;

    /**
     * The feature id for the '<em><b>Sink Role Event Channel Sink Connector</b></em>' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL_SINK_CONNECTOR__SINK_ROLE_EVENT_CHANNEL_SINK_CONNECTOR = CONNECTOR_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Filter Condition Event Channel Sink Connector</b></em>'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL_SINK_CONNECTOR__FILTER_CONDITION_EVENT_CHANNEL_SINK_CONNECTOR = CONNECTOR_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Assembly Context Event Channel Sink Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL_SINK_CONNECTOR__ASSEMBLY_CONTEXT_EVENT_CHANNEL_SINK_CONNECTOR = CONNECTOR_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Event Channel Event Channel Sink Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL_SINK_CONNECTOR__EVENT_CHANNEL_EVENT_CHANNEL_SINK_CONNECTOR = CONNECTOR_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Event Channel Sink Connector</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EVENT_CHANNEL_SINK_CONNECTOR_FEATURE_COUNT = CONNECTOR_FEATURE_COUNT + 4;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.composition.impl.ProvidedDelegationConnectorImpl
     * <em>Provided Delegation Connector</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.core.composition.impl.ProvidedDelegationConnectorImpl
     * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getProvidedDelegationConnector()
     * @generated
     */
    int PROVIDED_DELEGATION_CONNECTOR = 7;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROVIDED_DELEGATION_CONNECTOR__ID = DELEGATION_CONNECTOR__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROVIDED_DELEGATION_CONNECTOR__ENTITY_NAME = DELEGATION_CONNECTOR__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Parent Structure Connector</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROVIDED_DELEGATION_CONNECTOR__PARENT_STRUCTURE_CONNECTOR = DELEGATION_CONNECTOR__PARENT_STRUCTURE_CONNECTOR;

    /**
     * The feature id for the '<em><b>Inner Provided Role Provided Delegation Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROVIDED_DELEGATION_CONNECTOR__INNER_PROVIDED_ROLE_PROVIDED_DELEGATION_CONNECTOR = DELEGATION_CONNECTOR_FEATURE_COUNT
            + 0;

    /**
     * The feature id for the '<em><b>Outer Provided Role Provided Delegation Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROVIDED_DELEGATION_CONNECTOR__OUTER_PROVIDED_ROLE_PROVIDED_DELEGATION_CONNECTOR = DELEGATION_CONNECTOR_FEATURE_COUNT
            + 1;

    /**
     * The feature id for the '<em><b>Assembly Context Provided Delegation Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROVIDED_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_PROVIDED_DELEGATION_CONNECTOR = DELEGATION_CONNECTOR_FEATURE_COUNT
            + 2;

    /**
     * The number of structural features of the '<em>Provided Delegation Connector</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROVIDED_DELEGATION_CONNECTOR_FEATURE_COUNT = DELEGATION_CONNECTOR_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.composition.impl.RequiredDelegationConnectorImpl
     * <em>Required Delegation Connector</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.core.composition.impl.RequiredDelegationConnectorImpl
     * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getRequiredDelegationConnector()
     * @generated
     */
    int REQUIRED_DELEGATION_CONNECTOR = 8;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_DELEGATION_CONNECTOR__ID = DELEGATION_CONNECTOR__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_DELEGATION_CONNECTOR__ENTITY_NAME = DELEGATION_CONNECTOR__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Parent Structure Connector</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_DELEGATION_CONNECTOR__PARENT_STRUCTURE_CONNECTOR = DELEGATION_CONNECTOR__PARENT_STRUCTURE_CONNECTOR;

    /**
     * The feature id for the '<em><b>Inner Required Role Required Delegation Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_DELEGATION_CONNECTOR = DELEGATION_CONNECTOR_FEATURE_COUNT
            + 0;

    /**
     * The feature id for the '<em><b>Outer Required Role Required Delegation Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_DELEGATION_CONNECTOR = DELEGATION_CONNECTOR_FEATURE_COUNT
            + 1;

    /**
     * The feature id for the '<em><b>Assembly Context Required Delegation Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_DELEGATION_CONNECTOR = DELEGATION_CONNECTOR_FEATURE_COUNT
            + 2;

    /**
     * The number of structural features of the '<em>Required Delegation Connector</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_DELEGATION_CONNECTOR_FEATURE_COUNT = DELEGATION_CONNECTOR_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyConnectorImpl
     * <em>Assembly Connector</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.core.composition.impl.AssemblyConnectorImpl
     * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getAssemblyConnector()
     * @generated
     */
    int ASSEMBLY_CONNECTOR = 9;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_CONNECTOR__ID = CONNECTOR__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_CONNECTOR__ENTITY_NAME = CONNECTOR__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Parent Structure Connector</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_CONNECTOR__PARENT_STRUCTURE_CONNECTOR = CONNECTOR__PARENT_STRUCTURE_CONNECTOR;

    /**
     * The feature id for the '<em><b>Requiring Assembly Context Assembly Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR = CONNECTOR_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Providing Assembly Context Assembly Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR = CONNECTOR_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Provided Role Assembly Connector</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_CONNECTOR = CONNECTOR_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Required Role Assembly Connector</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_CONNECTOR = CONNECTOR_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Assembly Connector</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_CONNECTOR_FEATURE_COUNT = CONNECTOR_FEATURE_COUNT + 4;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyEventConnectorImpl
     * <em>Assembly Event Connector</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.core.composition.impl.AssemblyEventConnectorImpl
     * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getAssemblyEventConnector()
     * @generated
     */
    int ASSEMBLY_EVENT_CONNECTOR = 10;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_EVENT_CONNECTOR__ID = CONNECTOR__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_EVENT_CONNECTOR__ENTITY_NAME = CONNECTOR__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Parent Structure Connector</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_EVENT_CONNECTOR__PARENT_STRUCTURE_CONNECTOR = CONNECTOR__PARENT_STRUCTURE_CONNECTOR;

    /**
     * The feature id for the '<em><b>Sink Role Assembly Event Connector</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_EVENT_CONNECTOR__SINK_ROLE_ASSEMBLY_EVENT_CONNECTOR = CONNECTOR_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Source Role Assembly Event Connector</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_EVENT_CONNECTOR__SOURCE_ROLE_ASSEMBLY_EVENT_CONNECTOR = CONNECTOR_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Sink Assembly Context Assembly Event Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_EVENT_CONNECTOR__SINK_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR = CONNECTOR_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Source Assembly Context Assembly Event Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_EVENT_CONNECTOR__SOURCE_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR = CONNECTOR_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Filter Condition Assembly Event Connector</b></em>'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_EVENT_CONNECTOR__FILTER_CONDITION_ASSEMBLY_EVENT_CONNECTOR = CONNECTOR_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Assembly Event Connector</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_EVENT_CONNECTOR_FEATURE_COUNT = CONNECTOR_FEATURE_COUNT + 5;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.composition.impl.SourceDelegationConnectorImpl
     * <em>Source Delegation Connector</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.core.composition.impl.SourceDelegationConnectorImpl
     * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getSourceDelegationConnector()
     * @generated
     */
    int SOURCE_DELEGATION_CONNECTOR = 11;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_DELEGATION_CONNECTOR__ID = DELEGATION_CONNECTOR__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_DELEGATION_CONNECTOR__ENTITY_NAME = DELEGATION_CONNECTOR__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Parent Structure Connector</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_DELEGATION_CONNECTOR__PARENT_STRUCTURE_CONNECTOR = DELEGATION_CONNECTOR__PARENT_STRUCTURE_CONNECTOR;

    /**
     * The feature id for the '<em><b>Inner Source Role Source Role</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_DELEGATION_CONNECTOR__INNER_SOURCE_ROLE_SOURCE_ROLE = DELEGATION_CONNECTOR_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Outer Source Role Source Role</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_DELEGATION_CONNECTOR__OUTER_SOURCE_ROLE_SOURCE_ROLE = DELEGATION_CONNECTOR_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Assembly Context Source Delegation Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_SOURCE_DELEGATION_CONNECTOR = DELEGATION_CONNECTOR_FEATURE_COUNT
            + 2;

    /**
     * The number of structural features of the '<em>Source Delegation Connector</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_DELEGATION_CONNECTOR_FEATURE_COUNT = DELEGATION_CONNECTOR_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.composition.impl.SinkDelegationConnectorImpl
     * <em>Sink Delegation Connector</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.core.composition.impl.SinkDelegationConnectorImpl
     * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getSinkDelegationConnector()
     * @generated
     */
    int SINK_DELEGATION_CONNECTOR = 12;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SINK_DELEGATION_CONNECTOR__ID = DELEGATION_CONNECTOR__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SINK_DELEGATION_CONNECTOR__ENTITY_NAME = DELEGATION_CONNECTOR__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Parent Structure Connector</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SINK_DELEGATION_CONNECTOR__PARENT_STRUCTURE_CONNECTOR = DELEGATION_CONNECTOR__PARENT_STRUCTURE_CONNECTOR;

    /**
     * The feature id for the '<em><b>Assembly Context Sink Delegation Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SINK_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_SINK_DELEGATION_CONNECTOR = DELEGATION_CONNECTOR_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Inner Sink Role Sink Role</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SINK_DELEGATION_CONNECTOR__INNER_SINK_ROLE_SINK_ROLE = DELEGATION_CONNECTOR_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Outer Sink Role Sink Role</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SINK_DELEGATION_CONNECTOR__OUTER_SINK_ROLE_SINK_ROLE = DELEGATION_CONNECTOR_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Sink Delegation Connector</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SINK_DELEGATION_CONNECTOR_FEATURE_COUNT = DELEGATION_CONNECTOR_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyInfrastructureConnectorImpl
     * <em>Assembly Infrastructure Connector</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see org.palladiosimulator.pcm.core.composition.impl.AssemblyInfrastructureConnectorImpl
     * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getAssemblyInfrastructureConnector()
     * @generated
     */
    int ASSEMBLY_INFRASTRUCTURE_CONNECTOR = 13;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_INFRASTRUCTURE_CONNECTOR__ID = CONNECTOR__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_INFRASTRUCTURE_CONNECTOR__ENTITY_NAME = CONNECTOR__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Parent Structure Connector</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PARENT_STRUCTURE_CONNECTOR = CONNECTOR__PARENT_STRUCTURE_CONNECTOR;

    /**
     * The feature id for the '<em><b>Provided Role Assembly Infrastructure Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR = CONNECTOR_FEATURE_COUNT
            + 0;

    /**
     * The feature id for the '<em><b>Required Role Assembly Infrastructure Connector</b></em>'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR = CONNECTOR_FEATURE_COUNT
            + 1;

    /**
     * The feature id for the '
     * <em><b>Providing Assembly Context Assembly Infrastructure Connector</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR = CONNECTOR_FEATURE_COUNT
            + 2;

    /**
     * The feature id for the '
     * <em><b>Requiring Assembly Context Assembly Infrastructure Connector</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR = CONNECTOR_FEATURE_COUNT
            + 3;

    /**
     * The number of structural features of the '<em>Assembly Infrastructure Connector</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_INFRASTRUCTURE_CONNECTOR_FEATURE_COUNT = CONNECTOR_FEATURE_COUNT + 4;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.composition.impl.ProvidedInfrastructureDelegationConnectorImpl
     * <em>Provided Infrastructure Delegation Connector</em>}' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.core.composition.impl.
     *      ProvidedInfrastructureDelegationConnectorImpl
     * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getProvidedInfrastructureDelegationConnector()
     * @generated
     */
    int PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR = 14;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ID = DELEGATION_CONNECTOR__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ENTITY_NAME = DELEGATION_CONNECTOR__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Parent Structure Connector</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR__PARENT_STRUCTURE_CONNECTOR = DELEGATION_CONNECTOR__PARENT_STRUCTURE_CONNECTOR;

    /**
     * The feature id for the '
     * <em><b>Inner Provided Role Provided Infrastructure Delegation Connector</b></em>' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR__INNER_PROVIDED_ROLE_PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR = DELEGATION_CONNECTOR_FEATURE_COUNT
            + 0;

    /**
     * The feature id for the '
     * <em><b>Outer Provided Role Provided Infrastructure Delegation Connector</b></em>' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR__OUTER_PROVIDED_ROLE_PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR = DELEGATION_CONNECTOR_FEATURE_COUNT
            + 1;

    /**
     * The feature id for the '
     * <em><b>Assembly Context Provided Infrastructure Delegation Connector</b></em>' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR = DELEGATION_CONNECTOR_FEATURE_COUNT
            + 2;

    /**
     * The number of structural features of the '
     * <em>Provided Infrastructure Delegation Connector</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR_FEATURE_COUNT = DELEGATION_CONNECTOR_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.composition.impl.RequiredInfrastructureDelegationConnectorImpl
     * <em>Required Infrastructure Delegation Connector</em>}' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.core.composition.impl.
     *      RequiredInfrastructureDelegationConnectorImpl
     * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getRequiredInfrastructureDelegationConnector()
     * @generated
     */
    int REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR = 15;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ID = DELEGATION_CONNECTOR__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ENTITY_NAME = DELEGATION_CONNECTOR__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Parent Structure Connector</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__PARENT_STRUCTURE_CONNECTOR = DELEGATION_CONNECTOR__PARENT_STRUCTURE_CONNECTOR;

    /**
     * The feature id for the '
     * <em><b>Inner Required Role Required Infrastructure Delegation Connector</b></em>' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR = DELEGATION_CONNECTOR_FEATURE_COUNT
            + 0;

    /**
     * The feature id for the '
     * <em><b>Outer Required Role Required Infrastructure Delegation Connector</b></em>' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR = DELEGATION_CONNECTOR_FEATURE_COUNT
            + 1;

    /**
     * The feature id for the '
     * <em><b>Assembly Context Required Infrastructure Delegation Connector</b></em>' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR = DELEGATION_CONNECTOR_FEATURE_COUNT
            + 2;

    /**
     * The number of structural features of the '
     * <em>Required Infrastructure Delegation Connector</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR_FEATURE_COUNT = DELEGATION_CONNECTOR_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.composition.impl.RequiredResourceDelegationConnectorImpl
     * <em>Required Resource Delegation Connector</em>}' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.core.composition.impl.RequiredResourceDelegationConnectorImpl
     * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getRequiredResourceDelegationConnector()
     * @generated
     */
    int REQUIRED_RESOURCE_DELEGATION_CONNECTOR = 16;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_RESOURCE_DELEGATION_CONNECTOR__ID = DELEGATION_CONNECTOR__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_RESOURCE_DELEGATION_CONNECTOR__ENTITY_NAME = DELEGATION_CONNECTOR__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Parent Structure Connector</b></em>' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_RESOURCE_DELEGATION_CONNECTOR__PARENT_STRUCTURE_CONNECTOR = DELEGATION_CONNECTOR__PARENT_STRUCTURE_CONNECTOR;

    /**
     * The feature id for the '
     * <em><b>Assembly Context Required Resource Delegation Connector</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_RESOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_RESOURCE_DELEGATION_CONNECTOR = DELEGATION_CONNECTOR_FEATURE_COUNT
            + 0;

    /**
     * The feature id for the '
     * <em><b>Inner Required Role Required Resource Delegation Connector</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_RESOURCE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR = DELEGATION_CONNECTOR_FEATURE_COUNT
            + 1;

    /**
     * The feature id for the '
     * <em><b>Outer Required Role Required Resource Delegation Connector</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_RESOURCE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR = DELEGATION_CONNECTOR_FEATURE_COUNT
            + 2;

    /**
     * The number of structural features of the '<em>Required Resource Delegation Connector</em>'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REQUIRED_RESOURCE_DELEGATION_CONNECTOR_FEATURE_COUNT = DELEGATION_CONNECTOR_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyContextImpl
     * <em>Assembly Context</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.core.composition.impl.AssemblyContextImpl
     * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getAssemblyContext()
     * @generated
     */
    int ASSEMBLY_CONTEXT = 17;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_CONTEXT__ID = EntityPackage.ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_CONTEXT__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Parent Structure Assembly Context</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_CONTEXT__PARENT_STRUCTURE_ASSEMBLY_CONTEXT = EntityPackage.ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Encapsulated Component Assembly Context</b></em>' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_CONTEXT__ENCAPSULATED_COMPONENT_ASSEMBLY_CONTEXT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Config Parameter Usages Assembly Context</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_CONTEXT__CONFIG_PARAMETER_USAGES_ASSEMBLY_CONTEXT = EntityPackage.ENTITY_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Assembly Context</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ASSEMBLY_CONTEXT_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 3;

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.composition.DelegationConnector
     * <em>Delegation Connector</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.DelegationConnector
     * @generated
     */
    EClass getDelegationConnector();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.composition.Connector <em>Connector</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.Connector
     * @generated
     */
    EClass getConnector();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.core.composition.Connector#getParentStructure__Connector
     * <em>Parent Structure Connector</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the container reference '<em>Parent Structure Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.Connector#getParentStructure__Connector()
     * @see #getConnector()
     * @generated
     */
    EReference getConnector_ParentStructure__Connector();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.composition.ComposedStructure
     * <em>Composed Structure</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Composed Structure</em>'.
     * @see org.palladiosimulator.pcm.core.composition.ComposedStructure
     * @generated
     */
    EClass getComposedStructure();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.core.composition.ComposedStructure#getAssemblyContexts__ComposedStructure
     * <em>Assembly Contexts Composed Structure</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the containment reference list '
     *         <em>Assembly Contexts Composed Structure</em>'.
     * @see org.palladiosimulator.pcm.core.composition.ComposedStructure#getAssemblyContexts__ComposedStructure()
     * @see #getComposedStructure()
     * @generated
     */
    EReference getComposedStructure_AssemblyContexts__ComposedStructure();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.core.composition.ComposedStructure#getResourceRequiredDelegationConnectors_ComposedStructure
     * <em>Resource Required Delegation Connectors Composed Structure</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '
     *         <em>Resource Required Delegation Connectors Composed Structure</em>'.
     * @see org.palladiosimulator.pcm.core.composition.ComposedStructure#getResourceRequiredDelegationConnectors_ComposedStructure()
     * @see #getComposedStructure()
     * @generated
     */
    EReference getComposedStructure_ResourceRequiredDelegationConnectors_ComposedStructure();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.core.composition.ComposedStructure#getEventChannel__ComposedStructure
     * <em>Event Channel Composed Structure</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '
     *         <em>Event Channel Composed Structure</em>'.
     * @see org.palladiosimulator.pcm.core.composition.ComposedStructure#getEventChannel__ComposedStructure()
     * @see #getComposedStructure()
     * @generated
     */
    EReference getComposedStructure_EventChannel__ComposedStructure();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.core.composition.ComposedStructure#getConnectors__ComposedStructure
     * <em>Connectors Composed Structure</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '
     *         <em>Connectors Composed Structure</em>'.
     * @see org.palladiosimulator.pcm.core.composition.ComposedStructure#getConnectors__ComposedStructure()
     * @see #getComposedStructure()
     * @generated
     */
    EReference getComposedStructure_Connectors__ComposedStructure();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector
     * <em>Resource Required Delegation Connector</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for class '<em>Resource Required Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector
     * @generated
     */
    EClass getResourceRequiredDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector#getInnerResourceRequiredRole_ResourceRequiredDelegationConnector
     * <em>Inner Resource Required Role Resource Required Delegation Connector</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Inner Resource Required Role Resource Required Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector#getInnerResourceRequiredRole_ResourceRequiredDelegationConnector()
     * @see #getResourceRequiredDelegationConnector()
     * @generated
     */
    EReference getResourceRequiredDelegationConnector_InnerResourceRequiredRole_ResourceRequiredDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector#getOuterResourceRequiredRole_ResourceRequiredDelegationConnector
     * <em>Outer Resource Required Role Resource Required Delegation Connector</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Outer Resource Required Role Resource Required Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector#getOuterResourceRequiredRole_ResourceRequiredDelegationConnector()
     * @see #getResourceRequiredDelegationConnector()
     * @generated
     */
    EReference getResourceRequiredDelegationConnector_OuterResourceRequiredRole_ResourceRequiredDelegationConnector();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector#getParentStructure_ResourceRequiredDelegationConnector
     * <em>Parent Structure Resource Required Delegation Connector</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the container reference '
     *         <em>Parent Structure Resource Required Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector#getParentStructure_ResourceRequiredDelegationConnector()
     * @see #getResourceRequiredDelegationConnector()
     * @generated
     */
    EReference getResourceRequiredDelegationConnector_ParentStructure_ResourceRequiredDelegationConnector();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannel <em>Event Channel</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Event Channel</em>'.
     * @see org.palladiosimulator.pcm.core.composition.EventChannel
     * @generated
     */
    EClass getEventChannel();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannel#getEventGroup__EventChannel
     * <em>Event Group Event Channel</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Event Group Event Channel</em>'.
     * @see org.palladiosimulator.pcm.core.composition.EventChannel#getEventGroup__EventChannel()
     * @see #getEventChannel()
     * @generated
     */
    EReference getEventChannel_EventGroup__EventChannel();

    /**
     * Returns the meta object for the reference list '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannel#getEventChannelSourceConnector__EventChannel
     * <em>Event Channel Source Connector Event Channel</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference list '
     *         <em>Event Channel Source Connector Event Channel</em>'.
     * @see org.palladiosimulator.pcm.core.composition.EventChannel#getEventChannelSourceConnector__EventChannel()
     * @see #getEventChannel()
     * @generated
     */
    EReference getEventChannel_EventChannelSourceConnector__EventChannel();

    /**
     * Returns the meta object for the reference list '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannel#getEventChannelSinkConnector__EventChannel
     * <em>Event Channel Sink Connector Event Channel</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference list '
     *         <em>Event Channel Sink Connector Event Channel</em>'.
     * @see org.palladiosimulator.pcm.core.composition.EventChannel#getEventChannelSinkConnector__EventChannel()
     * @see #getEventChannel()
     * @generated
     */
    EReference getEventChannel_EventChannelSinkConnector__EventChannel();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannel#getParentStructure__EventChannel
     * <em>Parent Structure Event Channel</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the container reference '<em>Parent Structure Event Channel</em>
     *         '.
     * @see org.palladiosimulator.pcm.core.composition.EventChannel#getParentStructure__EventChannel()
     * @see #getEventChannel()
     * @generated
     */
    EReference getEventChannel_ParentStructure__EventChannel();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector
     * <em>Event Channel Source Connector</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Event Channel Source Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector
     * @generated
     */
    EClass getEventChannelSourceConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector#getSourceRole__EventChannelSourceRole
     * <em>Source Role Event Channel Source Role</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the reference '<em>Source Role Event Channel Source Role</em>'.
     * @see org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector#getSourceRole__EventChannelSourceRole()
     * @see #getEventChannelSourceConnector()
     * @generated
     */
    EReference getEventChannelSourceConnector_SourceRole__EventChannelSourceRole();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector#getAssemblyContext__EventChannelSourceConnector
     * <em>Assembly Context Event Channel Source Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Assembly Context Event Channel Source Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector#getAssemblyContext__EventChannelSourceConnector()
     * @see #getEventChannelSourceConnector()
     * @generated
     */
    EReference getEventChannelSourceConnector_AssemblyContext__EventChannelSourceConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector#getEventChannel__EventChannelSourceConnector
     * <em>Event Channel Event Channel Source Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Event Channel Event Channel Source Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector#getEventChannel__EventChannelSourceConnector()
     * @see #getEventChannelSourceConnector()
     * @generated
     */
    EReference getEventChannelSourceConnector_EventChannel__EventChannelSourceConnector();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
     * <em>Event Channel Sink Connector</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Event Channel Sink Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
     * @generated
     */
    EClass getEventChannelSinkConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector#getSinkRole__EventChannelSinkConnector
     * <em>Sink Role Event Channel Sink Connector</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the reference '<em>Sink Role Event Channel Sink Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector#getSinkRole__EventChannelSinkConnector()
     * @see #getEventChannelSinkConnector()
     * @generated
     */
    EReference getEventChannelSinkConnector_SinkRole__EventChannelSinkConnector();

    /**
     * Returns the meta object for the containment reference '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector#getFilterCondition__EventChannelSinkConnector
     * <em>Filter Condition Event Channel Sink Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the containment reference '
     *         <em>Filter Condition Event Channel Sink Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector#getFilterCondition__EventChannelSinkConnector()
     * @see #getEventChannelSinkConnector()
     * @generated
     */
    EReference getEventChannelSinkConnector_FilterCondition__EventChannelSinkConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector#getAssemblyContext__EventChannelSinkConnector
     * <em>Assembly Context Event Channel Sink Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Assembly Context Event Channel Sink Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector#getAssemblyContext__EventChannelSinkConnector()
     * @see #getEventChannelSinkConnector()
     * @generated
     */
    EReference getEventChannelSinkConnector_AssemblyContext__EventChannelSinkConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector#getEventChannel__EventChannelSinkConnector
     * <em>Event Channel Event Channel Sink Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Event Channel Event Channel Sink Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector#getEventChannel__EventChannelSinkConnector()
     * @see #getEventChannelSinkConnector()
     * @generated
     */
    EReference getEventChannelSinkConnector_EventChannel__EventChannelSinkConnector();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector
     * <em>Provided Delegation Connector</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Provided Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector
     * @generated
     */
    EClass getProvidedDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector#getInnerProvidedRole_ProvidedDelegationConnector
     * <em>Inner Provided Role Provided Delegation Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Inner Provided Role Provided Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector#getInnerProvidedRole_ProvidedDelegationConnector()
     * @see #getProvidedDelegationConnector()
     * @generated
     */
    EReference getProvidedDelegationConnector_InnerProvidedRole_ProvidedDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector#getOuterProvidedRole_ProvidedDelegationConnector
     * <em>Outer Provided Role Provided Delegation Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Outer Provided Role Provided Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector#getOuterProvidedRole_ProvidedDelegationConnector()
     * @see #getProvidedDelegationConnector()
     * @generated
     */
    EReference getProvidedDelegationConnector_OuterProvidedRole_ProvidedDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector#getAssemblyContext_ProvidedDelegationConnector
     * <em>Assembly Context Provided Delegation Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Assembly Context Provided Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector#getAssemblyContext_ProvidedDelegationConnector()
     * @see #getProvidedDelegationConnector()
     * @generated
     */
    EReference getProvidedDelegationConnector_AssemblyContext_ProvidedDelegationConnector();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector
     * <em>Required Delegation Connector</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Required Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector
     * @generated
     */
    EClass getRequiredDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector#getInnerRequiredRole_RequiredDelegationConnector
     * <em>Inner Required Role Required Delegation Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Inner Required Role Required Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector#getInnerRequiredRole_RequiredDelegationConnector()
     * @see #getRequiredDelegationConnector()
     * @generated
     */
    EReference getRequiredDelegationConnector_InnerRequiredRole_RequiredDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector#getOuterRequiredRole_RequiredDelegationConnector
     * <em>Outer Required Role Required Delegation Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Outer Required Role Required Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector#getOuterRequiredRole_RequiredDelegationConnector()
     * @see #getRequiredDelegationConnector()
     * @generated
     */
    EReference getRequiredDelegationConnector_OuterRequiredRole_RequiredDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector#getAssemblyContext_RequiredDelegationConnector
     * <em>Assembly Context Required Delegation Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Assembly Context Required Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector#getAssemblyContext_RequiredDelegationConnector()
     * @see #getRequiredDelegationConnector()
     * @generated
     */
    EReference getRequiredDelegationConnector_AssemblyContext_RequiredDelegationConnector();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyConnector
     * <em>Assembly Connector</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Assembly Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyConnector
     * @generated
     */
    EClass getAssemblyConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyConnector#getRequiringAssemblyContext_AssemblyConnector
     * <em>Requiring Assembly Context Assembly Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Requiring Assembly Context Assembly Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyConnector#getRequiringAssemblyContext_AssemblyConnector()
     * @see #getAssemblyConnector()
     * @generated
     */
    EReference getAssemblyConnector_RequiringAssemblyContext_AssemblyConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyConnector#getProvidingAssemblyContext_AssemblyConnector
     * <em>Providing Assembly Context Assembly Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Providing Assembly Context Assembly Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyConnector#getProvidingAssemblyContext_AssemblyConnector()
     * @see #getAssemblyConnector()
     * @generated
     */
    EReference getAssemblyConnector_ProvidingAssemblyContext_AssemblyConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyConnector#getProvidedRole_AssemblyConnector
     * <em>Provided Role Assembly Connector</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Provided Role Assembly Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyConnector#getProvidedRole_AssemblyConnector()
     * @see #getAssemblyConnector()
     * @generated
     */
    EReference getAssemblyConnector_ProvidedRole_AssemblyConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyConnector#getRequiredRole_AssemblyConnector
     * <em>Required Role Assembly Connector</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Required Role Assembly Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyConnector#getRequiredRole_AssemblyConnector()
     * @see #getAssemblyConnector()
     * @generated
     */
    EReference getAssemblyConnector_RequiredRole_AssemblyConnector();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector
     * <em>Assembly Event Connector</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Assembly Event Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyEventConnector
     * @generated
     */
    EClass getAssemblyEventConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getSinkRole__AssemblyEventConnector
     * <em>Sink Role Assembly Event Connector</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Sink Role Assembly Event Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getSinkRole__AssemblyEventConnector()
     * @see #getAssemblyEventConnector()
     * @generated
     */
    EReference getAssemblyEventConnector_SinkRole__AssemblyEventConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getSourceRole__AssemblyEventConnector
     * <em>Source Role Assembly Event Connector</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the reference '<em>Source Role Assembly Event Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getSourceRole__AssemblyEventConnector()
     * @see #getAssemblyEventConnector()
     * @generated
     */
    EReference getAssemblyEventConnector_SourceRole__AssemblyEventConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getSinkAssemblyContext__AssemblyEventConnector
     * <em>Sink Assembly Context Assembly Event Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Sink Assembly Context Assembly Event Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getSinkAssemblyContext__AssemblyEventConnector()
     * @see #getAssemblyEventConnector()
     * @generated
     */
    EReference getAssemblyEventConnector_SinkAssemblyContext__AssemblyEventConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getSourceAssemblyContext__AssemblyEventConnector
     * <em>Source Assembly Context Assembly Event Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Source Assembly Context Assembly Event Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getSourceAssemblyContext__AssemblyEventConnector()
     * @see #getAssemblyEventConnector()
     * @generated
     */
    EReference getAssemblyEventConnector_SourceAssemblyContext__AssemblyEventConnector();

    /**
     * Returns the meta object for the containment reference '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getFilterCondition__AssemblyEventConnector
     * <em>Filter Condition Assembly Event Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the containment reference '
     *         <em>Filter Condition Assembly Event Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyEventConnector#getFilterCondition__AssemblyEventConnector()
     * @see #getAssemblyEventConnector()
     * @generated
     */
    EReference getAssemblyEventConnector_FilterCondition__AssemblyEventConnector();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.composition.SourceDelegationConnector
     * <em>Source Delegation Connector</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Source Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.SourceDelegationConnector
     * @generated
     */
    EClass getSourceDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.SourceDelegationConnector#getInnerSourceRole__SourceRole
     * <em>Inner Source Role Source Role</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Inner Source Role Source Role</em>'.
     * @see org.palladiosimulator.pcm.core.composition.SourceDelegationConnector#getInnerSourceRole__SourceRole()
     * @see #getSourceDelegationConnector()
     * @generated
     */
    EReference getSourceDelegationConnector_InnerSourceRole__SourceRole();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.SourceDelegationConnector#getOuterSourceRole__SourceRole
     * <em>Outer Source Role Source Role</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Outer Source Role Source Role</em>'.
     * @see org.palladiosimulator.pcm.core.composition.SourceDelegationConnector#getOuterSourceRole__SourceRole()
     * @see #getSourceDelegationConnector()
     * @generated
     */
    EReference getSourceDelegationConnector_OuterSourceRole__SourceRole();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.SourceDelegationConnector#getAssemblyContext__SourceDelegationConnector
     * <em>Assembly Context Source Delegation Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Assembly Context Source Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.SourceDelegationConnector#getAssemblyContext__SourceDelegationConnector()
     * @see #getSourceDelegationConnector()
     * @generated
     */
    EReference getSourceDelegationConnector_AssemblyContext__SourceDelegationConnector();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.composition.SinkDelegationConnector
     * <em>Sink Delegation Connector</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Sink Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.SinkDelegationConnector
     * @generated
     */
    EClass getSinkDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.SinkDelegationConnector#getAssemblyContext__SinkDelegationConnector
     * <em>Assembly Context Sink Delegation Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Assembly Context Sink Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.SinkDelegationConnector#getAssemblyContext__SinkDelegationConnector()
     * @see #getSinkDelegationConnector()
     * @generated
     */
    EReference getSinkDelegationConnector_AssemblyContext__SinkDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.SinkDelegationConnector#getInnerSinkRole__SinkRole
     * <em>Inner Sink Role Sink Role</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Inner Sink Role Sink Role</em>'.
     * @see org.palladiosimulator.pcm.core.composition.SinkDelegationConnector#getInnerSinkRole__SinkRole()
     * @see #getSinkDelegationConnector()
     * @generated
     */
    EReference getSinkDelegationConnector_InnerSinkRole__SinkRole();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.SinkDelegationConnector#getOuterSinkRole__SinkRole
     * <em>Outer Sink Role Sink Role</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Outer Sink Role Sink Role</em>'.
     * @see org.palladiosimulator.pcm.core.composition.SinkDelegationConnector#getOuterSinkRole__SinkRole()
     * @see #getSinkDelegationConnector()
     * @generated
     */
    EReference getSinkDelegationConnector_OuterSinkRole__SinkRole();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector
     * <em>Assembly Infrastructure Connector</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Assembly Infrastructure Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector
     * @generated
     */
    EClass getAssemblyInfrastructureConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector#getProvidedRole__AssemblyInfrastructureConnector
     * <em>Provided Role Assembly Infrastructure Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Provided Role Assembly Infrastructure Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector#getProvidedRole__AssemblyInfrastructureConnector()
     * @see #getAssemblyInfrastructureConnector()
     * @generated
     */
    EReference getAssemblyInfrastructureConnector_ProvidedRole__AssemblyInfrastructureConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector#getRequiredRole__AssemblyInfrastructureConnector
     * <em>Required Role Assembly Infrastructure Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Required Role Assembly Infrastructure Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector#getRequiredRole__AssemblyInfrastructureConnector()
     * @see #getAssemblyInfrastructureConnector()
     * @generated
     */
    EReference getAssemblyInfrastructureConnector_RequiredRole__AssemblyInfrastructureConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector#getProvidingAssemblyContext__AssemblyInfrastructureConnector
     * <em>Providing Assembly Context Assembly Infrastructure Connector</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Providing Assembly Context Assembly Infrastructure Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector#getProvidingAssemblyContext__AssemblyInfrastructureConnector()
     * @see #getAssemblyInfrastructureConnector()
     * @generated
     */
    EReference getAssemblyInfrastructureConnector_ProvidingAssemblyContext__AssemblyInfrastructureConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector#getRequiringAssemblyContext__AssemblyInfrastructureConnector
     * <em>Requiring Assembly Context Assembly Infrastructure Connector</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Requiring Assembly Context Assembly Infrastructure Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector#getRequiringAssemblyContext__AssemblyInfrastructureConnector()
     * @see #getAssemblyInfrastructureConnector()
     * @generated
     */
    EReference getAssemblyInfrastructureConnector_RequiringAssemblyContext__AssemblyInfrastructureConnector();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector
     * <em>Provided Infrastructure Delegation Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for class '<em>Provided Infrastructure Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector
     * @generated
     */
    EClass getProvidedInfrastructureDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector#getInnerProvidedRole__ProvidedInfrastructureDelegationConnector
     * <em>Inner Provided Role Provided Infrastructure Delegation Connector</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Inner Provided Role Provided Infrastructure Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector#getInnerProvidedRole__ProvidedInfrastructureDelegationConnector()
     * @see #getProvidedInfrastructureDelegationConnector()
     * @generated
     */
    EReference getProvidedInfrastructureDelegationConnector_InnerProvidedRole__ProvidedInfrastructureDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector#getOuterProvidedRole__ProvidedInfrastructureDelegationConnector
     * <em>Outer Provided Role Provided Infrastructure Delegation Connector</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Outer Provided Role Provided Infrastructure Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector#getOuterProvidedRole__ProvidedInfrastructureDelegationConnector()
     * @see #getProvidedInfrastructureDelegationConnector()
     * @generated
     */
    EReference getProvidedInfrastructureDelegationConnector_OuterProvidedRole__ProvidedInfrastructureDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector#getAssemblyContext__ProvidedInfrastructureDelegationConnector
     * <em>Assembly Context Provided Infrastructure Delegation Connector</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Assembly Context Provided Infrastructure Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector#getAssemblyContext__ProvidedInfrastructureDelegationConnector()
     * @see #getProvidedInfrastructureDelegationConnector()
     * @generated
     */
    EReference getProvidedInfrastructureDelegationConnector_AssemblyContext__ProvidedInfrastructureDelegationConnector();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector
     * <em>Required Infrastructure Delegation Connector</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for class '<em>Required Infrastructure Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector
     * @generated
     */
    EClass getRequiredInfrastructureDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector#getInnerRequiredRole__RequiredInfrastructureDelegationConnector
     * <em>Inner Required Role Required Infrastructure Delegation Connector</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Inner Required Role Required Infrastructure Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector#getInnerRequiredRole__RequiredInfrastructureDelegationConnector()
     * @see #getRequiredInfrastructureDelegationConnector()
     * @generated
     */
    EReference getRequiredInfrastructureDelegationConnector_InnerRequiredRole__RequiredInfrastructureDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector#getOuterRequiredRole__RequiredInfrastructureDelegationConnector
     * <em>Outer Required Role Required Infrastructure Delegation Connector</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Outer Required Role Required Infrastructure Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector#getOuterRequiredRole__RequiredInfrastructureDelegationConnector()
     * @see #getRequiredInfrastructureDelegationConnector()
     * @generated
     */
    EReference getRequiredInfrastructureDelegationConnector_OuterRequiredRole__RequiredInfrastructureDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector#getAssemblyContext__RequiredInfrastructureDelegationConnector
     * <em>Assembly Context Required Infrastructure Delegation Connector</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Assembly Context Required Infrastructure Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector#getAssemblyContext__RequiredInfrastructureDelegationConnector()
     * @see #getRequiredInfrastructureDelegationConnector()
     * @generated
     */
    EReference getRequiredInfrastructureDelegationConnector_AssemblyContext__RequiredInfrastructureDelegationConnector();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector
     * <em>Required Resource Delegation Connector</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for class '<em>Required Resource Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector
     * @generated
     */
    EClass getRequiredResourceDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector#getAssemblyContext__RequiredResourceDelegationConnector
     * <em>Assembly Context Required Resource Delegation Connector</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Assembly Context Required Resource Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector#getAssemblyContext__RequiredResourceDelegationConnector()
     * @see #getRequiredResourceDelegationConnector()
     * @generated
     */
    EReference getRequiredResourceDelegationConnector_AssemblyContext__RequiredResourceDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector#getInnerRequiredRole__RequiredResourceDelegationConnector
     * <em>Inner Required Role Required Resource Delegation Connector</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Inner Required Role Required Resource Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector#getInnerRequiredRole__RequiredResourceDelegationConnector()
     * @see #getRequiredResourceDelegationConnector()
     * @generated
     */
    EReference getRequiredResourceDelegationConnector_InnerRequiredRole__RequiredResourceDelegationConnector();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector#getOuterRequiredRole__RequiredResourceDelegationConnector
     * <em>Outer Required Role Required Resource Delegation Connector</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '
     *         <em>Outer Required Role Required Resource Delegation Connector</em>'.
     * @see org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector#getOuterRequiredRole__RequiredResourceDelegationConnector()
     * @see #getRequiredResourceDelegationConnector()
     * @generated
     */
    EReference getRequiredResourceDelegationConnector_OuterRequiredRole__RequiredResourceDelegationConnector();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext <em>Assembly Context</em>}
     * '. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Assembly Context</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @generated
     */
    EClass getAssemblyContext();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext#getParentStructure__AssemblyContext
     * <em>Parent Structure Assembly Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the container reference '
     *         <em>Parent Structure Assembly Context</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext#getParentStructure__AssemblyContext()
     * @see #getAssemblyContext()
     * @generated
     */
    EReference getAssemblyContext_ParentStructure__AssemblyContext();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext#getEncapsulatedComponent__AssemblyContext
     * <em>Encapsulated Component Assembly Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the reference '<em>Encapsulated Component Assembly Context</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext#getEncapsulatedComponent__AssemblyContext()
     * @see #getAssemblyContext()
     * @generated
     */
    EReference getAssemblyContext_EncapsulatedComponent__AssemblyContext();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext#getConfigParameterUsages__AssemblyContext
     * <em>Config Parameter Usages Assembly Context</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the containment reference list '
     *         <em>Config Parameter Usages Assembly Context</em>'.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext#getConfigParameterUsages__AssemblyContext()
     * @see #getAssemblyContext()
     * @generated
     */
    EReference getAssemblyContext_ConfigParameterUsages__AssemblyContext();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    CompositionFactory getCompositionFactory();

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
         * {@link org.palladiosimulator.pcm.core.composition.impl.DelegationConnectorImpl
         * <em>Delegation Connector</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.core.composition.impl.DelegationConnectorImpl
         * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getDelegationConnector()
         * @generated
         */
        EClass DELEGATION_CONNECTOR = eINSTANCE.getDelegationConnector();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.composition.impl.ConnectorImpl <em>Connector</em>}'
         * class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.core.composition.impl.ConnectorImpl
         * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getConnector()
         * @generated
         */
        EClass CONNECTOR = eINSTANCE.getConnector();

        /**
         * The meta object literal for the '<em><b>Parent Structure Connector</b></em>' container
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CONNECTOR__PARENT_STRUCTURE_CONNECTOR = eINSTANCE.getConnector_ParentStructure__Connector();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.composition.impl.ComposedStructureImpl
         * <em>Composed Structure</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.core.composition.impl.ComposedStructureImpl
         * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getComposedStructure()
         * @generated
         */
        EClass COMPOSED_STRUCTURE = eINSTANCE.getComposedStructure();

        /**
         * The meta object literal for the '<em><b>Assembly Contexts Composed Structure</b></em>'
         * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference COMPOSED_STRUCTURE__ASSEMBLY_CONTEXTS_COMPOSED_STRUCTURE = eINSTANCE
                .getComposedStructure_AssemblyContexts__ComposedStructure();

        /**
         * The meta object literal for the '
         * <em><b>Resource Required Delegation Connectors Composed Structure</b></em>' containment
         * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference COMPOSED_STRUCTURE__RESOURCE_REQUIRED_DELEGATION_CONNECTORS_COMPOSED_STRUCTURE = eINSTANCE
                .getComposedStructure_ResourceRequiredDelegationConnectors_ComposedStructure();

        /**
         * The meta object literal for the '<em><b>Event Channel Composed Structure</b></em>'
         * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference COMPOSED_STRUCTURE__EVENT_CHANNEL_COMPOSED_STRUCTURE = eINSTANCE
                .getComposedStructure_EventChannel__ComposedStructure();

        /**
         * The meta object literal for the '<em><b>Connectors Composed Structure</b></em>'
         * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference COMPOSED_STRUCTURE__CONNECTORS_COMPOSED_STRUCTURE = eINSTANCE
                .getComposedStructure_Connectors__ComposedStructure();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.composition.impl.ResourceRequiredDelegationConnectorImpl
         * <em>Resource Required Delegation Connector</em>}' class. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.core.composition.impl.
         *      ResourceRequiredDelegationConnectorImpl
         * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getResourceRequiredDelegationConnector()
         * @generated
         */
        EClass RESOURCE_REQUIRED_DELEGATION_CONNECTOR = eINSTANCE.getResourceRequiredDelegationConnector();

        /**
         * The meta object literal for the '
         * <em><b>Inner Resource Required Role Resource Required Delegation Connector</b></em>'
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RESOURCE_REQUIRED_DELEGATION_CONNECTOR__INNER_RESOURCE_REQUIRED_ROLE_RESOURCE_REQUIRED_DELEGATION_CONNECTOR = eINSTANCE
                .getResourceRequiredDelegationConnector_InnerResourceRequiredRole_ResourceRequiredDelegationConnector();

        /**
         * The meta object literal for the '
         * <em><b>Outer Resource Required Role Resource Required Delegation Connector</b></em>'
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RESOURCE_REQUIRED_DELEGATION_CONNECTOR__OUTER_RESOURCE_REQUIRED_ROLE_RESOURCE_REQUIRED_DELEGATION_CONNECTOR = eINSTANCE
                .getResourceRequiredDelegationConnector_OuterResourceRequiredRole_ResourceRequiredDelegationConnector();

        /**
         * The meta object literal for the '
         * <em><b>Parent Structure Resource Required Delegation Connector</b></em>' container
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RESOURCE_REQUIRED_DELEGATION_CONNECTOR__PARENT_STRUCTURE_RESOURCE_REQUIRED_DELEGATION_CONNECTOR = eINSTANCE
                .getResourceRequiredDelegationConnector_ParentStructure_ResourceRequiredDelegationConnector();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.composition.impl.EventChannelImpl
         * <em>Event Channel</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.core.composition.impl.EventChannelImpl
         * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getEventChannel()
         * @generated
         */
        EClass EVENT_CHANNEL = eINSTANCE.getEventChannel();

        /**
         * The meta object literal for the '<em><b>Event Group Event Channel</b></em>' reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EVENT_CHANNEL__EVENT_GROUP_EVENT_CHANNEL = eINSTANCE.getEventChannel_EventGroup__EventChannel();

        /**
         * The meta object literal for the '
         * <em><b>Event Channel Source Connector Event Channel</b></em>' reference list feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EVENT_CHANNEL__EVENT_CHANNEL_SOURCE_CONNECTOR_EVENT_CHANNEL = eINSTANCE
                .getEventChannel_EventChannelSourceConnector__EventChannel();

        /**
         * The meta object literal for the '
         * <em><b>Event Channel Sink Connector Event Channel</b></em>' reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EVENT_CHANNEL__EVENT_CHANNEL_SINK_CONNECTOR_EVENT_CHANNEL = eINSTANCE
                .getEventChannel_EventChannelSinkConnector__EventChannel();

        /**
         * The meta object literal for the '<em><b>Parent Structure Event Channel</b></em>'
         * container reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EVENT_CHANNEL__PARENT_STRUCTURE_EVENT_CHANNEL = eINSTANCE
                .getEventChannel_ParentStructure__EventChannel();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.composition.impl.EventChannelSourceConnectorImpl
         * <em>Event Channel Source Connector</em>}' class. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.core.composition.impl.EventChannelSourceConnectorImpl
         * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getEventChannelSourceConnector()
         * @generated
         */
        EClass EVENT_CHANNEL_SOURCE_CONNECTOR = eINSTANCE.getEventChannelSourceConnector();

        /**
         * The meta object literal for the '<em><b>Source Role Event Channel Source Role</b></em>'
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EVENT_CHANNEL_SOURCE_CONNECTOR__SOURCE_ROLE_EVENT_CHANNEL_SOURCE_ROLE = eINSTANCE
                .getEventChannelSourceConnector_SourceRole__EventChannelSourceRole();

        /**
         * The meta object literal for the '
         * <em><b>Assembly Context Event Channel Source Connector</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EVENT_CHANNEL_SOURCE_CONNECTOR__ASSEMBLY_CONTEXT_EVENT_CHANNEL_SOURCE_CONNECTOR = eINSTANCE
                .getEventChannelSourceConnector_AssemblyContext__EventChannelSourceConnector();

        /**
         * The meta object literal for the '
         * <em><b>Event Channel Event Channel Source Connector</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EVENT_CHANNEL_SOURCE_CONNECTOR__EVENT_CHANNEL_EVENT_CHANNEL_SOURCE_CONNECTOR = eINSTANCE
                .getEventChannelSourceConnector_EventChannel__EventChannelSourceConnector();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.composition.impl.EventChannelSinkConnectorImpl
         * <em>Event Channel Sink Connector</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         *
         * @see org.palladiosimulator.pcm.core.composition.impl.EventChannelSinkConnectorImpl
         * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getEventChannelSinkConnector()
         * @generated
         */
        EClass EVENT_CHANNEL_SINK_CONNECTOR = eINSTANCE.getEventChannelSinkConnector();

        /**
         * The meta object literal for the '<em><b>Sink Role Event Channel Sink Connector</b></em>'
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EVENT_CHANNEL_SINK_CONNECTOR__SINK_ROLE_EVENT_CHANNEL_SINK_CONNECTOR = eINSTANCE
                .getEventChannelSinkConnector_SinkRole__EventChannelSinkConnector();

        /**
         * The meta object literal for the '
         * <em><b>Filter Condition Event Channel Sink Connector</b></em>' containment reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EVENT_CHANNEL_SINK_CONNECTOR__FILTER_CONDITION_EVENT_CHANNEL_SINK_CONNECTOR = eINSTANCE
                .getEventChannelSinkConnector_FilterCondition__EventChannelSinkConnector();

        /**
         * The meta object literal for the '
         * <em><b>Assembly Context Event Channel Sink Connector</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EVENT_CHANNEL_SINK_CONNECTOR__ASSEMBLY_CONTEXT_EVENT_CHANNEL_SINK_CONNECTOR = eINSTANCE
                .getEventChannelSinkConnector_AssemblyContext__EventChannelSinkConnector();

        /**
         * The meta object literal for the '
         * <em><b>Event Channel Event Channel Sink Connector</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EVENT_CHANNEL_SINK_CONNECTOR__EVENT_CHANNEL_EVENT_CHANNEL_SINK_CONNECTOR = eINSTANCE
                .getEventChannelSinkConnector_EventChannel__EventChannelSinkConnector();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.composition.impl.ProvidedDelegationConnectorImpl
         * <em>Provided Delegation Connector</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         *
         * @see org.palladiosimulator.pcm.core.composition.impl.ProvidedDelegationConnectorImpl
         * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getProvidedDelegationConnector()
         * @generated
         */
        EClass PROVIDED_DELEGATION_CONNECTOR = eINSTANCE.getProvidedDelegationConnector();

        /**
         * The meta object literal for the '
         * <em><b>Inner Provided Role Provided Delegation Connector</b></em>' reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PROVIDED_DELEGATION_CONNECTOR__INNER_PROVIDED_ROLE_PROVIDED_DELEGATION_CONNECTOR = eINSTANCE
                .getProvidedDelegationConnector_InnerProvidedRole_ProvidedDelegationConnector();

        /**
         * The meta object literal for the '
         * <em><b>Outer Provided Role Provided Delegation Connector</b></em>' reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PROVIDED_DELEGATION_CONNECTOR__OUTER_PROVIDED_ROLE_PROVIDED_DELEGATION_CONNECTOR = eINSTANCE
                .getProvidedDelegationConnector_OuterProvidedRole_ProvidedDelegationConnector();

        /**
         * The meta object literal for the '
         * <em><b>Assembly Context Provided Delegation Connector</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PROVIDED_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_PROVIDED_DELEGATION_CONNECTOR = eINSTANCE
                .getProvidedDelegationConnector_AssemblyContext_ProvidedDelegationConnector();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.composition.impl.RequiredDelegationConnectorImpl
         * <em>Required Delegation Connector</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         *
         * @see org.palladiosimulator.pcm.core.composition.impl.RequiredDelegationConnectorImpl
         * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getRequiredDelegationConnector()
         * @generated
         */
        EClass REQUIRED_DELEGATION_CONNECTOR = eINSTANCE.getRequiredDelegationConnector();

        /**
         * The meta object literal for the '
         * <em><b>Inner Required Role Required Delegation Connector</b></em>' reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference REQUIRED_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_DELEGATION_CONNECTOR = eINSTANCE
                .getRequiredDelegationConnector_InnerRequiredRole_RequiredDelegationConnector();

        /**
         * The meta object literal for the '
         * <em><b>Outer Required Role Required Delegation Connector</b></em>' reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference REQUIRED_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_DELEGATION_CONNECTOR = eINSTANCE
                .getRequiredDelegationConnector_OuterRequiredRole_RequiredDelegationConnector();

        /**
         * The meta object literal for the '
         * <em><b>Assembly Context Required Delegation Connector</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference REQUIRED_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_DELEGATION_CONNECTOR = eINSTANCE
                .getRequiredDelegationConnector_AssemblyContext_RequiredDelegationConnector();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyConnectorImpl
         * <em>Assembly Connector</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.core.composition.impl.AssemblyConnectorImpl
         * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getAssemblyConnector()
         * @generated
         */
        EClass ASSEMBLY_CONNECTOR = eINSTANCE.getAssemblyConnector();

        /**
         * The meta object literal for the '
         * <em><b>Requiring Assembly Context Assembly Connector</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ASSEMBLY_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR = eINSTANCE
                .getAssemblyConnector_RequiringAssemblyContext_AssemblyConnector();

        /**
         * The meta object literal for the '
         * <em><b>Providing Assembly Context Assembly Connector</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ASSEMBLY_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_CONNECTOR = eINSTANCE
                .getAssemblyConnector_ProvidingAssemblyContext_AssemblyConnector();

        /**
         * The meta object literal for the '<em><b>Provided Role Assembly Connector</b></em>'
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ASSEMBLY_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_CONNECTOR = eINSTANCE
                .getAssemblyConnector_ProvidedRole_AssemblyConnector();

        /**
         * The meta object literal for the '<em><b>Required Role Assembly Connector</b></em>'
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ASSEMBLY_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_CONNECTOR = eINSTANCE
                .getAssemblyConnector_RequiredRole_AssemblyConnector();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyEventConnectorImpl
         * <em>Assembly Event Connector</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.core.composition.impl.AssemblyEventConnectorImpl
         * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getAssemblyEventConnector()
         * @generated
         */
        EClass ASSEMBLY_EVENT_CONNECTOR = eINSTANCE.getAssemblyEventConnector();

        /**
         * The meta object literal for the '<em><b>Sink Role Assembly Event Connector</b></em>'
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ASSEMBLY_EVENT_CONNECTOR__SINK_ROLE_ASSEMBLY_EVENT_CONNECTOR = eINSTANCE
                .getAssemblyEventConnector_SinkRole__AssemblyEventConnector();

        /**
         * The meta object literal for the '<em><b>Source Role Assembly Event Connector</b></em>'
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ASSEMBLY_EVENT_CONNECTOR__SOURCE_ROLE_ASSEMBLY_EVENT_CONNECTOR = eINSTANCE
                .getAssemblyEventConnector_SourceRole__AssemblyEventConnector();

        /**
         * The meta object literal for the '
         * <em><b>Sink Assembly Context Assembly Event Connector</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ASSEMBLY_EVENT_CONNECTOR__SINK_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR = eINSTANCE
                .getAssemblyEventConnector_SinkAssemblyContext__AssemblyEventConnector();

        /**
         * The meta object literal for the '
         * <em><b>Source Assembly Context Assembly Event Connector</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ASSEMBLY_EVENT_CONNECTOR__SOURCE_ASSEMBLY_CONTEXT_ASSEMBLY_EVENT_CONNECTOR = eINSTANCE
                .getAssemblyEventConnector_SourceAssemblyContext__AssemblyEventConnector();

        /**
         * The meta object literal for the '
         * <em><b>Filter Condition Assembly Event Connector</b></em>' containment reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ASSEMBLY_EVENT_CONNECTOR__FILTER_CONDITION_ASSEMBLY_EVENT_CONNECTOR = eINSTANCE
                .getAssemblyEventConnector_FilterCondition__AssemblyEventConnector();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.composition.impl.SourceDelegationConnectorImpl
         * <em>Source Delegation Connector</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         *
         * @see org.palladiosimulator.pcm.core.composition.impl.SourceDelegationConnectorImpl
         * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getSourceDelegationConnector()
         * @generated
         */
        EClass SOURCE_DELEGATION_CONNECTOR = eINSTANCE.getSourceDelegationConnector();

        /**
         * The meta object literal for the '<em><b>Inner Source Role Source Role</b></em>' reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference SOURCE_DELEGATION_CONNECTOR__INNER_SOURCE_ROLE_SOURCE_ROLE = eINSTANCE
                .getSourceDelegationConnector_InnerSourceRole__SourceRole();

        /**
         * The meta object literal for the '<em><b>Outer Source Role Source Role</b></em>' reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference SOURCE_DELEGATION_CONNECTOR__OUTER_SOURCE_ROLE_SOURCE_ROLE = eINSTANCE
                .getSourceDelegationConnector_OuterSourceRole__SourceRole();

        /**
         * The meta object literal for the '
         * <em><b>Assembly Context Source Delegation Connector</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference SOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_SOURCE_DELEGATION_CONNECTOR = eINSTANCE
                .getSourceDelegationConnector_AssemblyContext__SourceDelegationConnector();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.composition.impl.SinkDelegationConnectorImpl
         * <em>Sink Delegation Connector</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.core.composition.impl.SinkDelegationConnectorImpl
         * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getSinkDelegationConnector()
         * @generated
         */
        EClass SINK_DELEGATION_CONNECTOR = eINSTANCE.getSinkDelegationConnector();

        /**
         * The meta object literal for the '
         * <em><b>Assembly Context Sink Delegation Connector</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference SINK_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_SINK_DELEGATION_CONNECTOR = eINSTANCE
                .getSinkDelegationConnector_AssemblyContext__SinkDelegationConnector();

        /**
         * The meta object literal for the '<em><b>Inner Sink Role Sink Role</b></em>' reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference SINK_DELEGATION_CONNECTOR__INNER_SINK_ROLE_SINK_ROLE = eINSTANCE
                .getSinkDelegationConnector_InnerSinkRole__SinkRole();

        /**
         * The meta object literal for the '<em><b>Outer Sink Role Sink Role</b></em>' reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference SINK_DELEGATION_CONNECTOR__OUTER_SINK_ROLE_SINK_ROLE = eINSTANCE
                .getSinkDelegationConnector_OuterSinkRole__SinkRole();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyInfrastructureConnectorImpl
         * <em>Assembly Infrastructure Connector</em>}' class. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.core.composition.impl.AssemblyInfrastructureConnectorImpl
         * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getAssemblyInfrastructureConnector()
         * @generated
         */
        EClass ASSEMBLY_INFRASTRUCTURE_CONNECTOR = eINSTANCE.getAssemblyInfrastructureConnector();

        /**
         * The meta object literal for the '
         * <em><b>Provided Role Assembly Infrastructure Connector</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR = eINSTANCE
                .getAssemblyInfrastructureConnector_ProvidedRole__AssemblyInfrastructureConnector();

        /**
         * The meta object literal for the '
         * <em><b>Required Role Assembly Infrastructure Connector</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRED_ROLE_ASSEMBLY_INFRASTRUCTURE_CONNECTOR = eINSTANCE
                .getAssemblyInfrastructureConnector_RequiredRole__AssemblyInfrastructureConnector();

        /**
         * The meta object literal for the '
         * <em><b>Providing Assembly Context Assembly Infrastructure Connector</b></em>' reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ASSEMBLY_INFRASTRUCTURE_CONNECTOR__PROVIDING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR = eINSTANCE
                .getAssemblyInfrastructureConnector_ProvidingAssemblyContext__AssemblyInfrastructureConnector();

        /**
         * The meta object literal for the '
         * <em><b>Requiring Assembly Context Assembly Infrastructure Connector</b></em>' reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ASSEMBLY_INFRASTRUCTURE_CONNECTOR__REQUIRING_ASSEMBLY_CONTEXT_ASSEMBLY_INFRASTRUCTURE_CONNECTOR = eINSTANCE
                .getAssemblyInfrastructureConnector_RequiringAssemblyContext__AssemblyInfrastructureConnector();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.composition.impl.ProvidedInfrastructureDelegationConnectorImpl
         * <em>Provided Infrastructure Delegation Connector</em>}' class. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.core.composition.impl.
         *      ProvidedInfrastructureDelegationConnectorImpl
         * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getProvidedInfrastructureDelegationConnector()
         * @generated
         */
        EClass PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR = eINSTANCE.getProvidedInfrastructureDelegationConnector();

        /**
         * The meta object literal for the '
         * <em><b>Inner Provided Role Provided Infrastructure Delegation Connector</b></em>'
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR__INNER_PROVIDED_ROLE_PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR = eINSTANCE
                .getProvidedInfrastructureDelegationConnector_InnerProvidedRole__ProvidedInfrastructureDelegationConnector();

        /**
         * The meta object literal for the '
         * <em><b>Outer Provided Role Provided Infrastructure Delegation Connector</b></em>'
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR__OUTER_PROVIDED_ROLE_PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR = eINSTANCE
                .getProvidedInfrastructureDelegationConnector_OuterProvidedRole__ProvidedInfrastructureDelegationConnector();

        /**
         * The meta object literal for the '
         * <em><b>Assembly Context Provided Infrastructure Delegation Connector</b></em>' reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_PROVIDED_INFRASTRUCTURE_DELEGATION_CONNECTOR = eINSTANCE
                .getProvidedInfrastructureDelegationConnector_AssemblyContext__ProvidedInfrastructureDelegationConnector();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.composition.impl.RequiredInfrastructureDelegationConnectorImpl
         * <em>Required Infrastructure Delegation Connector</em>}' class. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.core.composition.impl.
         *      RequiredInfrastructureDelegationConnectorImpl
         * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getRequiredInfrastructureDelegationConnector()
         * @generated
         */
        EClass REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR = eINSTANCE.getRequiredInfrastructureDelegationConnector();

        /**
         * The meta object literal for the '
         * <em><b>Inner Required Role Required Infrastructure Delegation Connector</b></em>'
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR = eINSTANCE
                .getRequiredInfrastructureDelegationConnector_InnerRequiredRole__RequiredInfrastructureDelegationConnector();

        /**
         * The meta object literal for the '
         * <em><b>Outer Required Role Required Infrastructure Delegation Connector</b></em>'
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR = eINSTANCE
                .getRequiredInfrastructureDelegationConnector_OuterRequiredRole__RequiredInfrastructureDelegationConnector();

        /**
         * The meta object literal for the '
         * <em><b>Assembly Context Required Infrastructure Delegation Connector</b></em>' reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_INFRASTRUCTURE_DELEGATION_CONNECTOR = eINSTANCE
                .getRequiredInfrastructureDelegationConnector_AssemblyContext__RequiredInfrastructureDelegationConnector();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.composition.impl.RequiredResourceDelegationConnectorImpl
         * <em>Required Resource Delegation Connector</em>}' class. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.core.composition.impl.
         *      RequiredResourceDelegationConnectorImpl
         * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getRequiredResourceDelegationConnector()
         * @generated
         */
        EClass REQUIRED_RESOURCE_DELEGATION_CONNECTOR = eINSTANCE.getRequiredResourceDelegationConnector();

        /**
         * The meta object literal for the '
         * <em><b>Assembly Context Required Resource Delegation Connector</b></em>' reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference REQUIRED_RESOURCE_DELEGATION_CONNECTOR__ASSEMBLY_CONTEXT_REQUIRED_RESOURCE_DELEGATION_CONNECTOR = eINSTANCE
                .getRequiredResourceDelegationConnector_AssemblyContext__RequiredResourceDelegationConnector();

        /**
         * The meta object literal for the '
         * <em><b>Inner Required Role Required Resource Delegation Connector</b></em>' reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference REQUIRED_RESOURCE_DELEGATION_CONNECTOR__INNER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR = eINSTANCE
                .getRequiredResourceDelegationConnector_InnerRequiredRole__RequiredResourceDelegationConnector();

        /**
         * The meta object literal for the '
         * <em><b>Outer Required Role Required Resource Delegation Connector</b></em>' reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference REQUIRED_RESOURCE_DELEGATION_CONNECTOR__OUTER_REQUIRED_ROLE_REQUIRED_RESOURCE_DELEGATION_CONNECTOR = eINSTANCE
                .getRequiredResourceDelegationConnector_OuterRequiredRole__RequiredResourceDelegationConnector();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.core.composition.impl.AssemblyContextImpl
         * <em>Assembly Context</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.core.composition.impl.AssemblyContextImpl
         * @see org.palladiosimulator.pcm.core.composition.impl.CompositionPackageImpl#getAssemblyContext()
         * @generated
         */
        EClass ASSEMBLY_CONTEXT = eINSTANCE.getAssemblyContext();

        /**
         * The meta object literal for the '<em><b>Parent Structure Assembly Context</b></em>'
         * container reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ASSEMBLY_CONTEXT__PARENT_STRUCTURE_ASSEMBLY_CONTEXT = eINSTANCE
                .getAssemblyContext_ParentStructure__AssemblyContext();

        /**
         * The meta object literal for the '<em><b>Encapsulated Component Assembly Context</b></em>'
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ASSEMBLY_CONTEXT__ENCAPSULATED_COMPONENT_ASSEMBLY_CONTEXT = eINSTANCE
                .getAssemblyContext_EncapsulatedComponent__AssemblyContext();

        /**
         * The meta object literal for the '<em><b>Config Parameter Usages Assembly Context</b></em>
         * ' containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ASSEMBLY_CONTEXT__CONFIG_PARAMETER_USAGES_ASSEMBLY_CONTEXT = eINSTANCE
                .getAssemblyContext_ConfigParameterUsages__AssemblyContext();

    }

} // CompositionPackage
