/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.usagemodel;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Usage Scenario</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> UsageScenarios are concurrently executed behaviours of users within one
 * UsageModel. It describes which services are directly invoked by users in one specific use case
 * and models the possible sequences of calling them. Each UsageScenario includes a workload and a
 * scenario behaviour. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.usagemodel.UsageScenario#getUsageModel_UsageScenario
 * <em>Usage Model Usage Scenario</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.usagemodel.UsageScenario#getScenarioBehaviour_UsageScenario
 * <em>Scenario Behaviour Usage Scenario</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.usagemodel.UsageScenario#getWorkload_UsageScenario
 * <em>Workload Usage Scenario</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.usagemodel.UsagemodelPackage#getUsageScenario()
 * @model
 * @generated
 */
public interface UsageScenario extends Entity {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Usage Model Usage Scenario</b></em>' container reference. It
     * is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.usagemodel.UsageModel#getUsageScenario_UsageModel
     * <em>Usage Scenario Usage Model</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Usage Model Usage Scenario</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Usage Model Usage Scenario</em>' container reference.
     * @see #setUsageModel_UsageScenario(UsageModel)
     * @see org.palladiosimulator.pcm.usagemodel.UsagemodelPackage#getUsageScenario_UsageModel_UsageScenario()
     * @see org.palladiosimulator.pcm.usagemodel.UsageModel#getUsageScenario_UsageModel
     * @model opposite="usageScenario_UsageModel" required="true" transient="false" ordered="false"
     * @generated
     */
    UsageModel getUsageModel_UsageScenario();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.usagemodel.UsageScenario#getUsageModel_UsageScenario
     * <em>Usage Model Usage Scenario</em>}' container reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Usage Model Usage Scenario</em>' container reference.
     * @see #getUsageModel_UsageScenario()
     * @generated
     */
    void setUsageModel_UsageScenario(UsageModel value);

    /**
     * Returns the value of the '<em><b>Scenario Behaviour Usage Scenario</b></em>' containment
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour#getUsageScenario_SenarioBehaviour
     * <em>Usage Scenario Senario Behaviour</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Scenario Behaviour Usage Scenario</em>' containment reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Scenario Behaviour Usage Scenario</em>' containment reference.
     * @see #setScenarioBehaviour_UsageScenario(ScenarioBehaviour)
     * @see org.palladiosimulator.pcm.usagemodel.UsagemodelPackage#getUsageScenario_ScenarioBehaviour_UsageScenario()
     * @see org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour#getUsageScenario_SenarioBehaviour
     * @model opposite="usageScenario_SenarioBehaviour" containment="true" required="true"
     *        ordered="false"
     * @generated
     */
    ScenarioBehaviour getScenarioBehaviour_UsageScenario();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.usagemodel.UsageScenario#getScenarioBehaviour_UsageScenario
     * <em>Scenario Behaviour Usage Scenario</em>}' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Scenario Behaviour Usage Scenario</em>' containment
     *            reference.
     * @see #getScenarioBehaviour_UsageScenario()
     * @generated
     */
    void setScenarioBehaviour_UsageScenario(ScenarioBehaviour value);

    /**
     * Returns the value of the '<em><b>Workload Usage Scenario</b></em>' containment reference. It
     * is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.usagemodel.Workload#getUsageScenario_Workload
     * <em>Usage Scenario Workload</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Workload Usage Scenario</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Workload Usage Scenario</em>' containment reference.
     * @see #setWorkload_UsageScenario(Workload)
     * @see org.palladiosimulator.pcm.usagemodel.UsagemodelPackage#getUsageScenario_Workload_UsageScenario()
     * @see org.palladiosimulator.pcm.usagemodel.Workload#getUsageScenario_Workload
     * @model opposite="usageScenario_Workload" containment="true" required="true" ordered="false"
     * @generated
     */
    Workload getWorkload_UsageScenario();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.usagemodel.UsageScenario#getWorkload_UsageScenario
     * <em>Workload Usage Scenario</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Workload Usage Scenario</em>' containment reference.
     * @see #getWorkload_UsageScenario()
     * @generated
     */
    void setWorkload_UsageScenario(Workload value);

} // UsageScenario
