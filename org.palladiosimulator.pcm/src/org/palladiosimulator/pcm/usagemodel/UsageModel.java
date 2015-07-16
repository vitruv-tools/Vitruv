/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.usagemodel;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Usage Model</b></em>'. <!--
 * end-user-doc -->
 *
 * <!-- begin-model-doc --> The UsageModel specifies the whole user interaction with a system from a
 * performance viewpoint. It consists of a number of concurrently executed UsageScenarios and a set
 * of global UserData specifications. Each UsageScenario includes a workload and a scenario
 * behaviour. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.usagemodel.UsageModel#getUsageScenario_UsageModel
 * <em>Usage Scenario Usage Model</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.usagemodel.UsageModel#getUserData_UsageModel
 * <em>User Data Usage Model</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.usagemodel.UsagemodelPackage#getUsageModel()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface UsageModel extends CDOObject {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Usage Scenario Usage Model</b></em>' containment reference
     * list. The list contents are of type
     * {@link org.palladiosimulator.pcm.usagemodel.UsageScenario}. It is bidirectional and its
     * opposite is '
     * {@link org.palladiosimulator.pcm.usagemodel.UsageScenario#getUsageModel_UsageScenario
     * <em>Usage Model Usage Scenario</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Usage Scenario Usage Model</em>' containment reference list isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Usage Scenario Usage Model</em>' containment reference list.
     * @see org.palladiosimulator.pcm.usagemodel.UsagemodelPackage#getUsageModel_UsageScenario_UsageModel()
     * @see org.palladiosimulator.pcm.usagemodel.UsageScenario#getUsageModel_UsageScenario
     * @model opposite="usageModel_UsageScenario" containment="true" ordered="false"
     * @generated
     */
    EList<UsageScenario> getUsageScenario_UsageModel();

    /**
     * Returns the value of the '<em><b>User Data Usage Model</b></em>' containment reference list.
     * The list contents are of type {@link org.palladiosimulator.pcm.usagemodel.UserData}. It is
     * bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.usagemodel.UserData#getUsageModel_UserData
     * <em>Usage Model User Data</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>User Data Usage Model</em>' containment reference list isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>User Data Usage Model</em>' containment reference list.
     * @see org.palladiosimulator.pcm.usagemodel.UsagemodelPackage#getUsageModel_UserData_UsageModel()
     * @see org.palladiosimulator.pcm.usagemodel.UserData#getUsageModel_UserData
     * @model opposite="usageModel_UserData" containment="true" ordered="false"
     * @generated
     */
    EList<UserData> getUserData_UsageModel();

} // UsageModel
