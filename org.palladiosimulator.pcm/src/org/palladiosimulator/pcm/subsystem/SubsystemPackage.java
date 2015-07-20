/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.subsystem;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
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
 * <!-- end-user-doc --> <!-- begin-model-doc --> Package capturing the subsystem entity <!--
 * end-model-doc -->
 *
 * @see org.palladiosimulator.pcm.subsystem.SubsystemFactory
 * @model kind="package"
 * @generated
 */
public interface SubsystemPackage extends EPackage {

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
    String eNAME = "subsystem";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String eNS_URI = "http://palladiosimulator.org/PalladioComponentModel/SubSystem/5.1";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String eNS_PREFIX = "subsystem";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    SubsystemPackage eINSTANCE = org.palladiosimulator.pcm.subsystem.impl.SubsystemPackageImpl.init();

    /**
     * The meta object id for the '{@link org.palladiosimulator.pcm.subsystem.impl.SubSystemImpl
     * <em>Sub System</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.palladiosimulator.pcm.subsystem.impl.SubSystemImpl
     * @see org.palladiosimulator.pcm.subsystem.impl.SubsystemPackageImpl#getSubSystem()
     * @generated
     */
    int SUB_SYSTEM = 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SUB_SYSTEM__ID = EntityPackage.COMPOSED_PROVIDING_REQUIRING_ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SUB_SYSTEM__ENTITY_NAME = EntityPackage.COMPOSED_PROVIDING_REQUIRING_ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Assembly Contexts Composed Structure</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SUB_SYSTEM__ASSEMBLY_CONTEXTS_COMPOSED_STRUCTURE = EntityPackage.COMPOSED_PROVIDING_REQUIRING_ENTITY__ASSEMBLY_CONTEXTS_COMPOSED_STRUCTURE;

    /**
     * The feature id for the '
     * <em><b>Resource Required Delegation Connectors Composed Structure</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SUB_SYSTEM__RESOURCE_REQUIRED_DELEGATION_CONNECTORS_COMPOSED_STRUCTURE = EntityPackage.COMPOSED_PROVIDING_REQUIRING_ENTITY__RESOURCE_REQUIRED_DELEGATION_CONNECTORS_COMPOSED_STRUCTURE;

    /**
     * The feature id for the '<em><b>Event Channel Composed Structure</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SUB_SYSTEM__EVENT_CHANNEL_COMPOSED_STRUCTURE = EntityPackage.COMPOSED_PROVIDING_REQUIRING_ENTITY__EVENT_CHANNEL_COMPOSED_STRUCTURE;

    /**
     * The feature id for the '<em><b>Connectors Composed Structure</b></em>' containment reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SUB_SYSTEM__CONNECTORS_COMPOSED_STRUCTURE = EntityPackage.COMPOSED_PROVIDING_REQUIRING_ENTITY__CONNECTORS_COMPOSED_STRUCTURE;

    /**
     * The feature id for the '<em><b>Provided Roles Interface Providing Entity</b></em>'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SUB_SYSTEM__PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY = EntityPackage.COMPOSED_PROVIDING_REQUIRING_ENTITY__PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY;

    /**
     * The feature id for the '
     * <em><b>Resource Required Roles Resource Interface Requiring Entity</b></em>' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SUB_SYSTEM__RESOURCE_REQUIRED_ROLES_RESOURCE_INTERFACE_REQUIRING_ENTITY = EntityPackage.COMPOSED_PROVIDING_REQUIRING_ENTITY__RESOURCE_REQUIRED_ROLES_RESOURCE_INTERFACE_REQUIRING_ENTITY;

    /**
     * The feature id for the '<em><b>Required Roles Interface Requiring Entity</b></em>'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SUB_SYSTEM__REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY = EntityPackage.COMPOSED_PROVIDING_REQUIRING_ENTITY__REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY;

    /**
     * The feature id for the '<em><b>Repository Repository Component</b></em>' container reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SUB_SYSTEM__REPOSITORY_REPOSITORY_COMPONENT = EntityPackage.COMPOSED_PROVIDING_REQUIRING_ENTITY_FEATURE_COUNT
            + 0;

    /**
     * The number of structural features of the '<em>Sub System</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int SUB_SYSTEM_FEATURE_COUNT = EntityPackage.COMPOSED_PROVIDING_REQUIRING_ENTITY_FEATURE_COUNT + 1;

    /**
     * Returns the meta object for class '{@link org.palladiosimulator.pcm.subsystem.SubSystem
     * <em>Sub System</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Sub System</em>'.
     * @see org.palladiosimulator.pcm.subsystem.SubSystem
     * @generated
     */
    EClass getSubSystem();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the factory that creates the instances of the model.
     * @generated
     */
    SubsystemFactory getSubsystemFactory();

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
         * {@link org.palladiosimulator.pcm.subsystem.impl.SubSystemImpl <em>Sub System</em>}'
         * class. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @see org.palladiosimulator.pcm.subsystem.impl.SubSystemImpl
         * @see org.palladiosimulator.pcm.subsystem.impl.SubsystemPackageImpl#getSubSystem()
         * @generated
         */
        EClass SUB_SYSTEM = eINSTANCE.getSubSystem();

    }

} // SubsystemPackage
