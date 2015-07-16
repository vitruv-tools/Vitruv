/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.seff_reliability;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.palladiosimulator.pcm.core.entity.EntityPackage;
import org.palladiosimulator.pcm.seff.SeffPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta
 * objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityFactory
 * @model kind="package"
 * @generated
 */
public interface SeffReliabilityPackage extends EPackage {

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
    String eNAME = "seff_reliability";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://palladiosimulator.org/PalladioComponentModel/SEFF/SEFF_Reliability/5.1";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "seff_reliability";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    SeffReliabilityPackage eINSTANCE = org.palladiosimulator.pcm.seff.seff_reliability.impl.SeffReliabilityPackageImpl
            .init();

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.seff.seff_reliability.impl.FailureHandlingEntityImpl
     * <em>Failure Handling Entity</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.seff.seff_reliability.impl.FailureHandlingEntityImpl
     * @see org.palladiosimulator.pcm.seff.seff_reliability.impl.SeffReliabilityPackageImpl#getFailureHandlingEntity()
     * @generated
     */
    int FAILURE_HANDLING_ENTITY = 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FAILURE_HANDLING_ENTITY__ID = EntityPackage.ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FAILURE_HANDLING_ENTITY__ENTITY_NAME = EntityPackage.ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Failure Types Failure Handling Entity</b></em>' reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FAILURE_HANDLING_ENTITY__FAILURE_TYPES_FAILURE_HANDLING_ENTITY = EntityPackage.ENTITY_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Failure Handling Entity</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FAILURE_HANDLING_ENTITY_FEATURE_COUNT = EntityPackage.ENTITY_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.seff.seff_reliability.impl.RecoveryActionBehaviourImpl
     * <em>Recovery Action Behaviour</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.seff.seff_reliability.impl.RecoveryActionBehaviourImpl
     * @see org.palladiosimulator.pcm.seff.seff_reliability.impl.SeffReliabilityPackageImpl#getRecoveryActionBehaviour()
     * @generated
     */
    int RECOVERY_ACTION_BEHAVIOUR = 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION_BEHAVIOUR__ID = FAILURE_HANDLING_ENTITY__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION_BEHAVIOUR__ENTITY_NAME = FAILURE_HANDLING_ENTITY__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Failure Types Failure Handling Entity</b></em>' reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION_BEHAVIOUR__FAILURE_TYPES_FAILURE_HANDLING_ENTITY = FAILURE_HANDLING_ENTITY__FAILURE_TYPES_FAILURE_HANDLING_ENTITY;

    /**
     * The feature id for the '<em><b>Abstract Loop Action Resource Demanding Behaviour</b></em>'
     * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION_BEHAVIOUR__ABSTRACT_LOOP_ACTION_RESOURCE_DEMANDING_BEHAVIOUR = FAILURE_HANDLING_ENTITY_FEATURE_COUNT
            + 0;

    /**
     * The feature id for the '
     * <em><b>Abstract Branch Transition Resource Demanding Behaviour</b></em>' container reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION_BEHAVIOUR__ABSTRACT_BRANCH_TRANSITION_RESOURCE_DEMANDING_BEHAVIOUR = FAILURE_HANDLING_ENTITY_FEATURE_COUNT
            + 1;

    /**
     * The feature id for the '<em><b>Steps Behaviour</b></em>' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION_BEHAVIOUR__STEPS_BEHAVIOUR = FAILURE_HANDLING_ENTITY_FEATURE_COUNT + 2;

    /**
     * The feature id for the '
     * <em><b>Failure Handling Alternatives Recovery Action Behaviour</b></em>' reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION_BEHAVIOUR__FAILURE_HANDLING_ALTERNATIVES_RECOVERY_ACTION_BEHAVIOUR = FAILURE_HANDLING_ENTITY_FEATURE_COUNT
            + 3;

    /**
     * The feature id for the '<em><b>Recovery Action Recovery Action Behaviour</b></em>' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION_BEHAVIOUR__RECOVERY_ACTION_RECOVERY_ACTION_BEHAVIOUR = FAILURE_HANDLING_ENTITY_FEATURE_COUNT
            + 4;

    /**
     * The number of structural features of the '<em>Recovery Action Behaviour</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION_BEHAVIOUR_FEATURE_COUNT = FAILURE_HANDLING_ENTITY_FEATURE_COUNT + 5;

    /**
     * The meta object id for the '
     * {@link org.palladiosimulator.pcm.seff.seff_reliability.impl.RecoveryActionImpl
     * <em>Recovery Action</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.seff.seff_reliability.impl.RecoveryActionImpl
     * @see org.palladiosimulator.pcm.seff.seff_reliability.impl.SeffReliabilityPackageImpl#getRecoveryAction()
     * @generated
     */
    int RECOVERY_ACTION = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION__ID = SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__ID;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION__ENTITY_NAME = SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__ENTITY_NAME;

    /**
     * The feature id for the '<em><b>Predecessor Abstract Action</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION__PREDECESSOR_ABSTRACT_ACTION = SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__PREDECESSOR_ABSTRACT_ACTION;

    /**
     * The feature id for the '<em><b>Successor Abstract Action</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION__SUCCESSOR_ABSTRACT_ACTION = SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__SUCCESSOR_ABSTRACT_ACTION;

    /**
     * The feature id for the '<em><b>Resource Demanding Behaviour Abstract Action</b></em>'
     * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION__RESOURCE_DEMANDING_BEHAVIOUR_ABSTRACT_ACTION = SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__RESOURCE_DEMANDING_BEHAVIOUR_ABSTRACT_ACTION;

    /**
     * The feature id for the '<em><b>Resource Demand Action</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION__RESOURCE_DEMAND_ACTION = SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__RESOURCE_DEMAND_ACTION;

    /**
     * The feature id for the '<em><b>Infrastructure Call Action</b></em>' containment reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION__INFRASTRUCTURE_CALL_ACTION = SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__INFRASTRUCTURE_CALL_ACTION;

    /**
     * The feature id for the '<em><b>Resource Call Action</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION__RESOURCE_CALL_ACTION = SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION__RESOURCE_CALL_ACTION;

    /**
     * The feature id for the '<em><b>Primary Behaviour Recovery Action</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION__PRIMARY_BEHAVIOUR_RECOVERY_ACTION = SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION_FEATURE_COUNT
            + 0;

    /**
     * The feature id for the '<em><b>Recovery Action Behaviours Recovery Action</b></em>'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION__RECOVERY_ACTION_BEHAVIOURS_RECOVERY_ACTION = SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION_FEATURE_COUNT
            + 1;

    /**
     * The number of structural features of the '<em>Recovery Action</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECOVERY_ACTION_FEATURE_COUNT = SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION_FEATURE_COUNT + 2;

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour
     * <em>Recovery Action Behaviour</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Recovery Action Behaviour</em>'.
     * @see org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour
     * @generated
     */
    EClass getRecoveryActionBehaviour();

    /**
     * Returns the meta object for the reference list '
     * {@link org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour#getFailureHandlingAlternatives__RecoveryActionBehaviour
     * <em>Failure Handling Alternatives Recovery Action Behaviour</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '
     *         <em>Failure Handling Alternatives Recovery Action Behaviour</em>'.
     * @see org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour#getFailureHandlingAlternatives__RecoveryActionBehaviour()
     * @see #getRecoveryActionBehaviour()
     * @generated
     */
    EReference getRecoveryActionBehaviour_FailureHandlingAlternatives__RecoveryActionBehaviour();

    /**
     * Returns the meta object for the container reference '
     * {@link org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour#getRecoveryAction__RecoveryActionBehaviour
     * <em>Recovery Action Recovery Action Behaviour</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the container reference '
     *         <em>Recovery Action Recovery Action Behaviour</em>'.
     * @see org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour#getRecoveryAction__RecoveryActionBehaviour()
     * @see #getRecoveryActionBehaviour()
     * @generated
     */
    EReference getRecoveryActionBehaviour_RecoveryAction__RecoveryActionBehaviour();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.seff.seff_reliability.RecoveryAction
     * <em>Recovery Action</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Recovery Action</em>'.
     * @see org.palladiosimulator.pcm.seff.seff_reliability.RecoveryAction
     * @generated
     */
    EClass getRecoveryAction();

    /**
     * Returns the meta object for the reference '
     * {@link org.palladiosimulator.pcm.seff.seff_reliability.RecoveryAction#getPrimaryBehaviour__RecoveryAction
     * <em>Primary Behaviour Recovery Action</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Primary Behaviour Recovery Action</em>'.
     * @see org.palladiosimulator.pcm.seff.seff_reliability.RecoveryAction#getPrimaryBehaviour__RecoveryAction()
     * @see #getRecoveryAction()
     * @generated
     */
    EReference getRecoveryAction_PrimaryBehaviour__RecoveryAction();

    /**
     * Returns the meta object for the containment reference list '
     * {@link org.palladiosimulator.pcm.seff.seff_reliability.RecoveryAction#getRecoveryActionBehaviours__RecoveryAction
     * <em>Recovery Action Behaviours Recovery Action</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the containment reference list '
     *         <em>Recovery Action Behaviours Recovery Action</em>'.
     * @see org.palladiosimulator.pcm.seff.seff_reliability.RecoveryAction#getRecoveryActionBehaviours__RecoveryAction()
     * @see #getRecoveryAction()
     * @generated
     */
    EReference getRecoveryAction_RecoveryActionBehaviours__RecoveryAction();

    /**
     * Returns the meta object for class '
     * {@link org.palladiosimulator.pcm.seff.seff_reliability.FailureHandlingEntity
     * <em>Failure Handling Entity</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Failure Handling Entity</em>'.
     * @see org.palladiosimulator.pcm.seff.seff_reliability.FailureHandlingEntity
     * @generated
     */
    EClass getFailureHandlingEntity();

    /**
     * Returns the meta object for the reference list '
     * {@link org.palladiosimulator.pcm.seff.seff_reliability.FailureHandlingEntity#getFailureTypes_FailureHandlingEntity
     * <em>Failure Types Failure Handling Entity</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for the reference list '
     *         <em>Failure Types Failure Handling Entity</em>'.
     * @see org.palladiosimulator.pcm.seff.seff_reliability.FailureHandlingEntity#getFailureTypes_FailureHandlingEntity()
     * @see #getFailureHandlingEntity()
     * @generated
     */
    EReference getFailureHandlingEntity_FailureTypes_FailureHandlingEntity();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    SeffReliabilityFactory getSeffReliabilityFactory();

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
         * {@link org.palladiosimulator.pcm.seff.seff_reliability.impl.RecoveryActionBehaviourImpl
         * <em>Recovery Action Behaviour</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.seff.seff_reliability.impl.RecoveryActionBehaviourImpl
         * @see org.palladiosimulator.pcm.seff.seff_reliability.impl.SeffReliabilityPackageImpl#getRecoveryActionBehaviour()
         * @generated
         */
        EClass RECOVERY_ACTION_BEHAVIOUR = eINSTANCE.getRecoveryActionBehaviour();

        /**
         * The meta object literal for the '
         * <em><b>Failure Handling Alternatives Recovery Action Behaviour</b></em>' reference list
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RECOVERY_ACTION_BEHAVIOUR__FAILURE_HANDLING_ALTERNATIVES_RECOVERY_ACTION_BEHAVIOUR = eINSTANCE
                .getRecoveryActionBehaviour_FailureHandlingAlternatives__RecoveryActionBehaviour();

        /**
         * The meta object literal for the '
         * <em><b>Recovery Action Recovery Action Behaviour</b></em>' container reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RECOVERY_ACTION_BEHAVIOUR__RECOVERY_ACTION_RECOVERY_ACTION_BEHAVIOUR = eINSTANCE
                .getRecoveryActionBehaviour_RecoveryAction__RecoveryActionBehaviour();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.seff.seff_reliability.impl.RecoveryActionImpl
         * <em>Recovery Action</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.seff.seff_reliability.impl.RecoveryActionImpl
         * @see org.palladiosimulator.pcm.seff.seff_reliability.impl.SeffReliabilityPackageImpl#getRecoveryAction()
         * @generated
         */
        EClass RECOVERY_ACTION = eINSTANCE.getRecoveryAction();

        /**
         * The meta object literal for the '<em><b>Primary Behaviour Recovery Action</b></em>'
         * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RECOVERY_ACTION__PRIMARY_BEHAVIOUR_RECOVERY_ACTION = eINSTANCE
                .getRecoveryAction_PrimaryBehaviour__RecoveryAction();

        /**
         * The meta object literal for the '
         * <em><b>Recovery Action Behaviours Recovery Action</b></em>' containment reference list
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RECOVERY_ACTION__RECOVERY_ACTION_BEHAVIOURS_RECOVERY_ACTION = eINSTANCE
                .getRecoveryAction_RecoveryActionBehaviours__RecoveryAction();

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.seff.seff_reliability.impl.FailureHandlingEntityImpl
         * <em>Failure Handling Entity</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.seff.seff_reliability.impl.FailureHandlingEntityImpl
         * @see org.palladiosimulator.pcm.seff.seff_reliability.impl.SeffReliabilityPackageImpl#getFailureHandlingEntity()
         * @generated
         */
        EClass FAILURE_HANDLING_ENTITY = eINSTANCE.getFailureHandlingEntity();

        /**
         * The meta object literal for the '<em><b>Failure Types Failure Handling Entity</b></em>'
         * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference FAILURE_HANDLING_ENTITY__FAILURE_TYPES_FAILURE_HANDLING_ENTITY = eINSTANCE
                .getFailureHandlingEntity_FailureTypes_FailureHandlingEntity();

    }

} // SeffReliabilityPackage
